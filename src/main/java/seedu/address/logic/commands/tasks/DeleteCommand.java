package seedu.address.logic.commands.tasks;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Deletes a task identified using its displayed index from the address book
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = getCommandFormat(COMMAND_WORD)
            + ": Deletes all tasks or the task(s) identified by the index number\n"
            + "used in the displayed task list.\n"
            + "Parameters: all or INDEX1 [INDEX2 INDEX3 INDEX4 ...] (must be a positive integer)\n"
            + "Example 1: " + getCommandFormat(COMMAND_WORD) + " 1\n"
            + "Example 2: " + getCommandFormat(COMMAND_WORD) + " 2 5 4\n"
            + "Example 3: " + getCommandFormat(COMMAND_WORD) + " all";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task(s):\n%1$s";

    private final List<Index> targetIndices;

    /**
     * @param targetIndices null to delete all, else give a list of indices to delete.
     */
    public DeleteCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> tasksToDelete = getTasksToDelete(model.getFilteredTaskList());

        for (Task taskToDelete : tasksToDelete) {
            model.deleteTask(taskToDelete);
        }
        model.commitAddressBook();

        String deletedTasksString =
                tasksToDelete
                        .stream()
                        .map(taskToDelete -> taskToDelete.toString())
                        .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTasksString));
    }

    private List<Task> getTasksToDelete(List<Task> lastShownList) throws CommandException {
        // Delete all
        if (targetIndices == null) {
            return new ArrayList<>(lastShownList);
        }

        // Check that all indices are valid
        if (targetIndices
                .stream()
                .anyMatch(targetIndex -> targetIndex.getZeroBased() >= ((ObservableList) lastShownList).size())) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        return targetIndices
                .stream()
                .map(targetIndex -> lastShownList.get(targetIndex.getZeroBased()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && (targetIndices == ((DeleteCommand) other).targetIndices
                        || targetIndices.equals(((DeleteCommand) other).targetIndices))); // state check
    }
}
