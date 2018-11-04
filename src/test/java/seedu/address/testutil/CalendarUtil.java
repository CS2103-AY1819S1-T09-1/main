package seedu.address.testutil;

import static seedu.address.logic.parser.CalendarParser.MODULE_WORD;
import static seedu.address.logic.parser.calendar.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.calendar.CliSyntax.PREFIX_YEAR;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.calendar.ShowCommand;

/**
 * A utility class for Calendar.
 */
public class CalendarUtil {
    /**
     * Returns a show command string.
     */
    public static String getShowCommand(int year, int month) {
        return MODULE_WORD + " " + ShowCommand.COMMAND_WORD + " " + getShowDetails(year, month);
    }

    /**
     * Returns the part of the command string for the given one-indexed month and
     * year.
     */
    public static String getShowDetails(int year, int month) {
        StringBuilder sb = new StringBuilder();

        // Need leading whitespace else parser will not read first token.
        sb.append(" ");
        sb.append(PREFIX_YEAR + Integer.toString(year) + " ");
        sb.append(PREFIX_MONTH + Integer.toString(month) + " ");
        return sb.toString();
    }

    /**
     * Returns a {@code Pair<Index, Index>} whose key is one-indexed year and value
     * is one-indexed month.
     */
    public static Pair<Index, Index> getYearMonthIndices(int year, int month) {
        return new Pair<>(Index.fromOneBased(year), Index.fromOneBased(month));
    }

    /**
     * Returns a {@code Calendar} for the first day of the specified one-indexed
     * month and year.
     */
    public static Calendar getCalendar(int year, int month) {
        return new GregorianCalendar(year, month - 1, 1, 0, 0);
    }
}
