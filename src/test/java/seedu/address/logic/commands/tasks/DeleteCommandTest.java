package seedu.address.logic.commands.tasks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

/**
 * Contains integration tests (interaction with the Model, UndoCommand, and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_all_success() {
        List<Task> tasksToDelete = new ArrayList<Task>(model.getFilteredTaskList());
        DeleteCommand deleteCommand = new DeleteCommand(null);

        String expectedMessage = generateExpectedMessage(tasksToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (Task taskToDelete : tasksToDelete) {
            expectedModel.deleteTask(taskToDelete);
        }
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        List<Task> tasksToDelete = Arrays.asList(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(INDEX_FIRST_TASK));

        String expectedMessage = generateExpectedMessage(tasksToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (Task taskToDelete : tasksToDelete) {
            expectedModel.deleteTask(taskToDelete);
        }
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndicesUnfilteredList_success() {
        List<Task> tasksToDelete = Arrays.asList(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()),
                model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased()));
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(INDEX_FIRST_TASK, INDEX_SECOND_TASK));

        String expectedMessage = generateExpectedMessage(tasksToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (Task taskToDelete : tasksToDelete) {
            expectedModel.deleteTask(taskToDelete);
        }
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexAndinvalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        // INDEX_FIRST_TASK is valid, but outOfBoundsIndex is not
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(INDEX_FIRST_TASK, outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        List<Task> tasksToDelete = Arrays.asList(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(INDEX_FIRST_TASK));

        String expectedMessage = generateExpectedMessage(tasksToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (Task taskToDelete : tasksToDelete) {
            expectedModel.deleteTask(taskToDelete);
        }
        expectedModel.commitAddressBook();
        showNoTask(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        List<Task> tasksToDelete = Arrays.asList(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(INDEX_FIRST_TASK));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (Task taskToDelete : tasksToDelete) {
            expectedModel.deleteTask(taskToDelete);
        }
        expectedModel.commitAddressBook();

        // delete -> first task deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts cow back to previous state and filtered task list to show all tasks
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_validIndicesUnfilteredList_success() throws Exception {
        List<Task> tasksToDelete = Arrays.asList(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()),
                model.getFilteredTaskList().get(INDEX_SECOND_TASK.getZeroBased()));
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(INDEX_FIRST_TASK, INDEX_SECOND_TASK));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (Task taskToDelete : tasksToDelete) {
            expectedModel.deleteTask(taskToDelete);
        }
        expectedModel.commitAddressBook();

        // delete -> first task deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts cow back to previous state and filtered task list to show all tasks
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(outOfBoundIndex));

        // execution failed -> cow state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // single cow state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Task} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be show now. Verify that the index of the previously deleted task in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the task object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameTaskDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(INDEX_FIRST_TASK));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showTaskAtIndex(model, INDEX_SECOND_TASK);
        List<Task> tasksToDelete = Arrays.asList(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        for (Task taskToDelete : tasksToDelete) {
            expectedModel.deleteTask(taskToDelete);
        }
        expectedModel.commitAddressBook();

        // delete -> deletes second task in unfiltered task list / first task in filtered task list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts cow back to previous state and filtered task list to show all tasks
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(tasksToDelete.get(0), model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        // redo -> deletes same second task in unfiltered task list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(Arrays.asList(INDEX_FIRST_TASK));
        DeleteCommand deleteSecondCommand = new DeleteCommand(Arrays.asList(INDEX_SECOND_TASK));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(Arrays.asList(INDEX_FIRST_TASK));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * @param tasksToDelete a list of tasks to delete
     * @return expected message when successful
     */
    private String generateExpectedMessage(List<Task> tasksToDelete) {
        String deletedTasksString = tasksToDelete
                .stream()
                .map(taskToDelete -> taskToDelete.toString())
                .collect(Collectors.joining("\n"));
        return String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, deletedTasksString);
    }

    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
