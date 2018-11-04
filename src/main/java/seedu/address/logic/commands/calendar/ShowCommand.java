package seedu.address.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.calendar.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.calendar.CliSyntax.PREFIX_YEAR;

import java.text.DateFormatSymbols;
import java.util.GregorianCalendar;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Shows the calendar for the specified year and month.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SUCCESS = "Showed calendar for %1$s %2$d";
    public static final String MESSAGE_USAGE = getCommandFormat(COMMAND_WORD) + ": Shows the calendar view.\n"
            + "Parameters: " + PREFIX_YEAR + "YEAR " + PREFIX_MONTH + "MONTH" + "\n" + "Example: "
            + getCommandFormat(COMMAND_WORD) + " " + PREFIX_YEAR + "2018 " + PREFIX_MONTH + "12";

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
        model.setCalendarMonth(new GregorianCalendar(year.getOneBased(), month.getZeroBased(), 1, 0, 0));
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                new DateFormatSymbols().getMonths()[month.getZeroBased()], year.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                        && year.equals(((ShowCommand) other).year) // state checks
                        && month.equals(((ShowCommand) other).month)); // state checks

    }
}
