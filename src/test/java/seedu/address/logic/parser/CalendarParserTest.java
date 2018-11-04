package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.calendar.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.CalendarUtil;

public class CalendarParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CalendarParser parser = new CalendarParser();

    @Test
    public void parseCommand_show() throws Exception {
        Pair<Index, Index> dec2018IndexPair = CalendarUtil.getYearMonthIndices(2018, 12);

        String commandText = ShowCommand.COMMAND_WORD + " " + CalendarUtil.getShowDetails(2018, 12);
        ShowCommand command = (ShowCommand) parser.parseCommand(commandText);
        assertEquals(new ShowCommand(dec2018IndexPair.getKey(), dec2018IndexPair.getValue()), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
