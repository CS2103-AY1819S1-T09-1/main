package seedu.address.logic.parser.contacts;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contacts.AssignedCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignedCommand object
 */
public class AssignedCommandParser implements Parser<AssignedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignedCommand
     * and returns an AssignedCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignedCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new AssignedCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignedCommand.MESSAGE_USAGE), pe, true);
        }
    }
}
