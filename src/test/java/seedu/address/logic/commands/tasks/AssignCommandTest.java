package seedu.address.logic.commands.tasks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.tasks.AssignCommand.MESSAGE_ALREADY_ASSIGNED;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskId;

public class AssignCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndices_success() {
        Index taskIndex = INDEX_FIRST_TASK;
        Index personIndex = INDEX_FIRST_PERSON;

        Task task = model.getFilteredTaskList().get(taskIndex.getZeroBased());
        Person person = model.getFilteredPersonList().get(personIndex.getZeroBased());

        Set<TaskId> taskIdSet = new HashSet<>(Arrays.asList(task.getId()));
        Set<PersonId> personIdSet = new HashSet<>(Arrays.asList(person.getId()));
        Task editedTask = new Task(task.getId(), task.getName(), task.getStartDateTime(),
                task.getEndDateTime(), task.getTags(), personIdSet);
        Person editedPerson = new Person(person.getId(), person.getName(), person.getPhone(),
                person.getEmail(), person.getAddress(), person.getTags(), taskIdSet);
        AssignCommand assignCommand = new AssignCommand(personIndex, taskIndex);

        String expectedMessage = String.format(AssignCommand.MESSAGE_ASSIGN_TASK_SUCCESS,
                taskIndex.getOneBased(), personIndex.getOneBased());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(person, editedPerson);
        expectedModel.updateTask(task, editedTask);
        expectedModel.commitAddressBook();

        assertCommandSuccess(assignCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndex_failure() {
        Index outOfBoundTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        Index personIndex = INDEX_FIRST_PERSON;
        AssignCommand assignCommand = new AssignCommand(personIndex, outOfBoundTaskIndex);
        assertCommandFailure(assignCommand, model, commandHistory, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index taskIndex = INDEX_FIRST_TASK;
        Index outOfBoundPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AssignCommand assignCommand = new AssignCommand(outOfBoundPersonIndex, taskIndex);
        assertCommandFailure(assignCommand, model, commandHistory, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyAssigned_failure() {
        Index taskIndex = INDEX_FIRST_TASK;
        Index personIndex = INDEX_FIRST_PERSON;

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Task task = model.getFilteredTaskList().get(taskIndex.getZeroBased());
        Person person = model.getFilteredPersonList().get(personIndex.getZeroBased());

        Set<TaskId> taskIdSet = new HashSet<>(Arrays.asList(task.getId()));
        Set<PersonId> personIdSet = new HashSet<>(Arrays.asList(person.getId()));
        Task editedTask = new Task(task.getId(), task.getName(), task.getStartDateTime(),
                task.getEndDateTime(), task.getTags(), personIdSet);
        Person editedPerson = new Person(person.getId(), person.getName(), person.getPhone(),
                person.getEmail(), person.getAddress(), person.getTags(), taskIdSet);
        model.updatePerson(person, editedPerson);
        model.updateTask(task, editedTask);

        AssignCommand assignCommand = new AssignCommand(personIndex, taskIndex);
        assertCommandFailure(assignCommand, model, commandHistory, MESSAGE_ALREADY_ASSIGNED);
    }

    @Test
    public void equals() {
        final AssignCommand standardCommand = new AssignCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK);

        // same values -> returns true
        AssignCommand commandWithSameValues = new AssignCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different task index -> returns false
        assertFalse(standardCommand.equals(new AssignCommand(INDEX_FIRST_PERSON, INDEX_SECOND_TASK)));

        // different person index -> returns false
        assertFalse(standardCommand.equals(new AssignCommand(INDEX_SECOND_PERSON, INDEX_FIRST_TASK)));
    }
}
