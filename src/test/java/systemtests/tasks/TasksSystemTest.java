package systemtests.tasks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.TasksParser.MODULE_WORD;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tasks.AssignedCommand;
import seedu.address.logic.commands.tasks.FindCommand;
import seedu.address.logic.commands.tasks.ListCommand;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalAddressBook;
import systemtests.AppSystemTest;

/**
 * An extension of AppSystemTest with methods specific to tasks commands.
 */
public abstract class TasksSystemTest extends AppSystemTest {
    /**
     * Displays all tasks in the address book.
     */
    protected void showAllTasks() {
        executeCommand(MODULE_WORD + " " + ListCommand.COMMAND_WORD);
        assertEquals(getModel().getAddressBook().getTaskList().size(), getModel().getFilteredTaskList().size());
    }

    /**
     * Displays all tasks with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showTasksWithName(String keyword) {
        executeCommand(MODULE_WORD + " " + FindCommand.COMMAND_WORD + " " + PREFIX_NAME + keyword);
        assertTrue(getModel().getFilteredTaskList().size() < getModel().getAddressBook().getTaskList().size());
    }

    /**
     * Selects the task at {@code index} of the displayed list.
     */
    protected void selectTask(Index index) {
        executeCommand(MODULE_WORD + " " + AssignedCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getTaskListPanel().getSelectedCardIndex());
    }

    @Override
    protected AddressBook getInitialData() {
        return TypicalAddressBook.getTypicalAddressBookTasks();
    }
}
