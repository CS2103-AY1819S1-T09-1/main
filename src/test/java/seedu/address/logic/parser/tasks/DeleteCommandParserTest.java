package seedu.address.logic.parser.tasks;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.tasks.DeleteCommand;

/**
 * Tests {@code DeleteCommandParser}
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgsAll_returnsDeleteCommand() {
        DeleteCommand deleteCommand = new DeleteCommand(null);
        assertParseSuccess(parser, "all", deleteCommand);
        assertParseSuccess(parser, " all", deleteCommand);
        assertParseSuccess(parser, "all ", deleteCommand);
        assertParseSuccess(parser, " all ", deleteCommand);
    }

    @Test
    public void parse_validArgsSingular_returnsDeleteCommand() {
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(INDEX_FIRST_TASK));
        assertParseSuccess(parser, "1", deleteCommand);
        assertParseSuccess(parser, "1 ", deleteCommand);
        assertParseSuccess(parser, " 1", deleteCommand);
        assertParseSuccess(parser, " 1 ", deleteCommand);
    }

    @Test
    public void parse_validArgsPlural_returnsDeleteCommand() {
        DeleteCommand deleteCommand = new DeleteCommand(
                Arrays.asList(INDEX_FIRST_TASK, INDEX_THIRD_TASK, INDEX_SECOND_TASK));
        assertParseSuccess(parser, "1 3 2", deleteCommand);
        assertParseSuccess(parser, "1 3 2 ", deleteCommand);
        assertParseSuccess(parser, " 1 3 2", deleteCommand);
        assertParseSuccess(parser, " 1 3 2 ", deleteCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), ""));
    }
}
