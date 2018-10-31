package seedu.address.logic.commands.calendars;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.calendars.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.calendars.CliSyntax.PREFIX_YEAR;

import java.util.GregorianCalendar;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SUCCESS = "Showed calendar";
    public static final String MESSAGE_USAGE = getCommandFormat(COMMAND_WORD) + ": Shows the calendar view.\n"
            + "Parameters: "
            + PREFIX_YEAR + "YEAR "
            + PREFIX_MONTH + "MONTH" + "\n"
            + "Example: " + getCommandFormat(COMMAND_WORD) + " "
            + PREFIX_YEAR + "2018 "
            + PREFIX_MONTH + "12";

    private final Index year;
    private final Index month;

    public ShowCommand(Index year, Index month) {
        this.year = year;
        this.month = month; // month is 0 indexed wtf
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        // Create calendar object for first day of specified month
        model.setCalendarMonth(new GregorianCalendar(this.year.getOneBased(), this.month.getZeroBased(), 1, 0, 0));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
