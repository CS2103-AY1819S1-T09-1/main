package seedu.address.logic.commands.tasks;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_TAG;
import static seedu.address.model.task.Task.MESSAGE_START_AFTER_END;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = getCommandFormat(COMMAND_WORD) + ": Adds a task.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_START_DATE + "START DATE" + "] "
            + "[" + PREFIX_START_TIME + "START TIME" + "] "
            + PREFIX_END_DATE + "END DATE "
            + PREFIX_END_TIME + "END TIME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + getCommandFormat(COMMAND_WORD) + " "
            + PREFIX_NAME + "Math Assignment "
            + PREFIX_START_DATE + "20180101 "
            + PREFIX_START_TIME + "0000 "
            + PREFIX_END_DATE + "20181231 "
            + PREFIX_END_TIME + "2359 "
            + PREFIX_TAG + "school "
            + PREFIX_TAG + "urgent";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists";

    private final Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public AddCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!toAdd.isValidDateTimeRange()) {
            throw new CommandException(MESSAGE_START_AFTER_END);
        }

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                        && toAdd.equals(((AddCommand) other).toAdd));
    }
}
