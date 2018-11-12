package systemtests.tasks;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_FEED;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_FEED;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VALUE_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VALUE_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VALUE_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VALUE_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FEED;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_FEED;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_FEED;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_DESC;
import static seedu.address.logic.parser.TasksParser.MODULE_WORD;
import static seedu.address.testutil.TypicalTasks.BRUSH;
import static seedu.address.testutil.TypicalTasks.FEED;
import static seedu.address.testutil.TypicalTasks.SLAUGHTER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.tasks.AddCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;
import systemtests.AppSystemTest;

public class AddCommandSystemTest extends TasksSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a task without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Task toAdd = FEED;
        String command = "   " + MODULE_WORD + " " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_FEED + "  "
                + START_DATE_DESC_FEED + " " + START_TIME_DESC_FEED + "   " + END_DATE_DESC_FEED + "   "
                + END_TIME_DESC_FEED + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Feed to the list -> Feed deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Feed to the list -> Feed added again */
        command = RedoCommand.COMMAND_WORD;
        model.addTask(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a task with all fields same as another task -> added */
        toAdd = new TaskBuilder(FEED).build();
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + NAME_DESC_FEED + START_DATE_DESC_FEED
                + START_TIME_DESC_FEED + END_DATE_DESC_FEED + END_TIME_DESC_FEED;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty Cow data -> added */
        clear();
        assertCommandSuccess(BRUSH);

        /* Case: add a task with tags, command with parameters in random order -> added */
        toAdd = SLAUGHTER;
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + START_DATE_DESC_SLAUGHTER + END_TIME_DESC_SLAUGHTER
                + TAG_DESC_SLAUGHTER + END_DATE_DESC_SLAUGHTER + NAME_DESC_SLAUGHTER + START_TIME_DESC_SLAUGHTER;
        assertCommandSuccess(command, toAdd);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: missing name -> rejected */
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + VALID_START_DATETIME_DESC + VALID_END_DATETIME_DESC;
        assertCommandFailure(command,
                String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE), MODULE_WORD));

        /* Case: missing end date -> rejected */
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + NAME_DESC_BRUSH + VALID_START_DATETIME_DESC
                + VALID_END_TIME_DESC;
        assertCommandFailure(command,
                String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE), MODULE_WORD));

        /* Case: missing end time -> rejected */
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + NAME_DESC_BRUSH + VALID_START_DATETIME_DESC
                + VALID_END_DATE_DESC;
        assertCommandFailure(command,
                String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE), MODULE_WORD));

        /* Case: missing end date and time -> rejected */
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + NAME_DESC_BRUSH + VALID_START_DATETIME_DESC;
        assertCommandFailure(command,
                String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE), MODULE_WORD));

        /* Case: invalid keyword -> rejected */
        command = MODULE_WORD + " " + "adds " + TaskUtil.getTaskDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + INVALID_NAME_DESC + VALID_START_DATETIME_DESC
                + VALID_END_DATETIME_DESC;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid start date -> rejected */
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + NAME_DESC_BRUSH + INVALID_VALUE_START_DATE_DESC
                + VALID_START_TIME_DESC + VALID_END_DATETIME_DESC;
        assertCommandFailure(command, DateTime.MESSAGE_DATETIME_VALUE_CONSTRAINTS);

        /* Case: invalid start time -> rejected */
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + NAME_DESC_BRUSH + VALID_START_DATE_DESC
                + INVALID_VALUE_START_TIME_DESC + VALID_END_DATETIME_DESC;
        assertCommandFailure(command, DateTime.MESSAGE_DATETIME_VALUE_CONSTRAINTS);

        /* Case: invalid end date -> rejected */
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + NAME_DESC_BRUSH + VALID_START_DATETIME_DESC
                + INVALID_VALUE_END_DATE_DESC + VALID_END_TIME_DESC;
        assertCommandFailure(command, DateTime.MESSAGE_DATETIME_VALUE_CONSTRAINTS);

        /* Case: invalid end time -> rejected */
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + NAME_DESC_BRUSH + VALID_START_DATETIME_DESC
                + VALID_END_DATE_DESC + INVALID_VALUE_END_TIME_DESC;
        assertCommandFailure(command, DateTime.MESSAGE_DATETIME_VALUE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = MODULE_WORD + " " + AddCommand.COMMAND_WORD + NAME_DESC_BRUSH + VALID_START_DATETIME_DESC
                + VALID_END_DATETIME_DESC + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Calendar view remains unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Task toAdd) {
        assertCommandSuccess(TaskUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Task)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Task)
     */
    private void assertCommandSuccess(String command, Task toAdd) {
        Model expectedModel = getModel();
        expectedModel.addTask(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Task)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Task)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code TaskListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AppSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
