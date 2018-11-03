package seedu.address.logic.commands.tasks;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.IsAssignedToTaskPredicate;
import seedu.address.model.task.Task;

/**
 * Selects a task identified using its displayed index,
 * and the list of persons will update to show only the persons that are assigned to the task
 */
public class AssignedCommand extends Command {

    public static final String COMMAND_WORD = "assigned";

    public static final String MESSAGE_USAGE = getCommandFormat(COMMAND_WORD)
            + ": Selects the task identified by the index number used in the displayed task "
            + "list and shows the list of persons assigned to the task.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + getCommandFormat(COMMAND_WORD) + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    private final Index targetIndex;

    public AssignedCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Task> filteredTaskList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= filteredTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        // Retrieve the desired task and update filter
        Task desiredTask = filteredTaskList.get(targetIndex.getZeroBased());
        model.updateFilteredPersonList(new IsAssignedToTaskPredicate(desiredTask));

        // Update UI (purely cosmetic for now)
        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignedCommand // instanceof handles nulls
                && targetIndex.equals(((AssignedCommand) other).targetIndex)); // state check
    }
}
