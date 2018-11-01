package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.contacts.AddCommand;
import seedu.address.logic.commands.contacts.AssignCommand;
import seedu.address.logic.commands.contacts.AssignedCommand;
import seedu.address.logic.commands.contacts.ClearCommand;
import seedu.address.logic.commands.contacts.DeleteCommand;
import seedu.address.logic.commands.contacts.EditCommand;
import seedu.address.logic.commands.contacts.FindCommand;
import seedu.address.logic.commands.contacts.ListCommand;
import seedu.address.logic.commands.contacts.UnassignCommand;
import seedu.address.logic.parser.contacts.AddCommandParser;
import seedu.address.logic.parser.contacts.AssignCommandParser;
import seedu.address.logic.parser.contacts.AssignedCommandParser;
import seedu.address.logic.parser.contacts.DeleteCommandParser;
import seedu.address.logic.parser.contacts.EditCommandParser;
import seedu.address.logic.parser.contacts.FindCommandParser;
import seedu.address.logic.parser.contacts.UnassignCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ContactsParser {

    public static final String MODULE_WORD = "contacts";

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        try {
            switch (commandWord) {

            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);

            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);

            case AssignedCommand.COMMAND_WORD:
                return new AssignedCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

            case ListCommand.COMMAND_WORD:
                return new ListCommand();

            case AssignCommand.COMMAND_WORD:
                return new AssignCommandParser().parse(arguments);

            case UnassignCommand.COMMAND_WORD:
                return new UnassignCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        } catch (ParseException e) {
            throw new ParseException(e.getMessage(MODULE_WORD));
        }
    }

}
