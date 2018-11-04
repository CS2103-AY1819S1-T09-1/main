package seedu.address.logic.parser.calendar;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTH_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTH_POSITIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.MONTH_FEB;
import static seedu.address.logic.commands.CommandTestUtil.MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_2000;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_2001;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.calendar.ParserUtil.MESSAGE_INVALID_MONTH;
import static seedu.address.logic.parser.calendar.ParserUtil.MESSAGE_INVALID_YEAR;

import org.junit.Test;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.calendar.ShowCommand;
import seedu.address.testutil.CalendarUtil;

public class ShowCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String
            .format(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE), "");

    private ShowCommandParser parser = new ShowCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no year specified
        assertParseFailure(parser, VALID_MONTH, MESSAGE_INVALID_FORMAT);

        // no month specified
        assertParseFailure(parser, VALID_YEAR, MESSAGE_INVALID_FORMAT);

        // no year, month specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // negative year value
        assertParseFailure(parser, VALID_MONTH + INVALID_YEAR_NEGATIVE, MESSAGE_INVALID_YEAR);

        // negative month value
        assertParseFailure(parser, VALID_YEAR + INVALID_MONTH_NEGATIVE, MESSAGE_INVALID_MONTH);

        // month > 12
        assertParseFailure(parser, VALID_YEAR + INVALID_MONTH_POSITIVE, MESSAGE_INVALID_MONTH);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Pair<Index, Index> dec2018IndexPair = CalendarUtil.getYearMonthIndices(2018, 12);
        String userInput = CalendarUtil.getShowDetails(2018, 12);

        ShowCommand expectedCommand = new ShowCommand(dec2018IndexPair.getKey(), dec2018IndexPair.getValue());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Pair<Index, Index> jan2000IndexPair = CalendarUtil.getYearMonthIndices(2000, 1);
        String userInput = YEAR_2001 + YEAR_2000 + MONTH_FEB + MONTH_JAN;

        ShowCommand expectedCommand = new ShowCommand(jan2000IndexPair.getKey(), jan2000IndexPair.getValue());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {

        Pair<Index, Index> jan2000IndexPair = CalendarUtil.getYearMonthIndices(2000, 1);
        String userInput = INVALID_YEAR_NEGATIVE + YEAR_2000 + MONTH_JAN;

        ShowCommand expectedCommand = new ShowCommand(jan2000IndexPair.getKey(), jan2000IndexPair.getValue());

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
