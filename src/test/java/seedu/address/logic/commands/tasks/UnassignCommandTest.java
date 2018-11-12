package seedu.address.logic.commands.tasks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.tasks.UnassignCommand.MESSAGE_NOT_ASSIGNED;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UnassignCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invalidTaskIndex_failure() {
        Index outOfBoundTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        Index personIndex = INDEX_FIRST_PERSON;
        UnassignCommand unassignCommand = new UnassignCommand(personIndex, outOfBoundTaskIndex);
        assertCommandFailure(unassignCommand, model, commandHistory, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndex_failure() {
        Index taskIndex = INDEX_FIRST_TASK;
        Index outOfBoundPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnassignCommand unassignCommand = new UnassignCommand(outOfBoundPersonIndex, taskIndex);
        assertCommandFailure(unassignCommand, model, commandHistory, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_notAssigned_failure() {
        UnassignCommand unassignCommand = new UnassignCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK);
        assertCommandFailure(unassignCommand, model, commandHistory, MESSAGE_NOT_ASSIGNED);
    }

    @Test
    public void equals() {
        final UnassignCommand standardCommand = new UnassignCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK);

        // same values -> returns true
        UnassignCommand commandWithSameValues = new UnassignCommand(INDEX_FIRST_PERSON, INDEX_FIRST_TASK);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different task index -> returns false
        assertFalse(standardCommand.equals(new UnassignCommand(INDEX_FIRST_PERSON, INDEX_SECOND_TASK)));

        // different person index -> returns false
        assertFalse(standardCommand.equals(new UnassignCommand(INDEX_SECOND_PERSON, INDEX_FIRST_TASK)));
    }
}
