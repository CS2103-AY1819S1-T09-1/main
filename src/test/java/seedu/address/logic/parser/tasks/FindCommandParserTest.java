package seedu.address.logic.parser.tasks;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_KEYWORD;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.BRUSH_END_DATE_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.BRUSH_END_DATE_SEARCH;
import static seedu.address.logic.commands.CommandTestUtil.BRUSH_NAME_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.BRUSH_NAME_SEARCH;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_BRUSH_NAME_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_BRUSH_NAME_SEARCH;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_NAME_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_NAME_SEARCH;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_START_DATE_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_START_DATE_SEARCH;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_TAG_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_TAG_SEARCH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.contacts.CliSyntax.PREFIX_TAG;
import static seedu.address.model.task.DateTime.MESSAGE_DATE_FORMAT_CONSTRAINTS;
import static seedu.address.model.task.DateTime.MESSAGE_DATE_VALUE_CONSTRAINTS;

import org.junit.Test;

import seedu.address.logic.commands.tasks.FindCommand;
import seedu.address.logic.commands.tasks.FindCommand.TaskPredicateAssembler;
import seedu.address.testutil.FindTaskPredicateAssemblerBuilder;

public class FindCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), "");

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArgs_failure() {
        // no field specified
        assertParseFailure(parser, "         ", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_wrongDate_failure() {
        // no field specified
        assertParseFailure(parser, " sd/invalidDate", MESSAGE_DATE_FORMAT_CONSTRAINTS);
        assertParseFailure(parser, " ed/", MESSAGE_DATE_FORMAT_CONSTRAINTS);
        assertParseFailure(parser, " ed/110011", MESSAGE_DATE_FORMAT_CONSTRAINTS);
        assertParseFailure(parser, " ed/10290193", MESSAGE_DATE_VALUE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidKeyword_failure() {
        // no field specified
        assertParseFailure(parser, " n/Not A Keyword", MESSAGE_INVALID_KEYWORD);
        assertParseFailure(parser, " n/", MESSAGE_INVALID_KEYWORD);
        assertParseFailure(parser, " n/      ", MESSAGE_INVALID_KEYWORD);
    }

    @Test
    public void parse_invalidTag_failure() {
        // no field specified
        assertParseFailure(parser, " t/Not A TAG", MESSAGE_INVALID_TAG);
        assertParseFailure(parser, " t/", MESSAGE_INVALID_TAG);
        assertParseFailure(parser, " t/      ", MESSAGE_INVALID_TAG);
    }

    @Test
    public void parse_individual_successful() {
        // Name
        TaskPredicateAssembler predicateBuilder =
                new FindTaskPredicateAssemblerBuilder().withNamePredicate(BRUSH_NAME_PREDICATE).build();
        FindCommand expectedCommand = new FindCommand(predicateBuilder);
        assertParseSuccess(parser, BRUSH_NAME_SEARCH, expectedCommand);

        // Start Date
        predicateBuilder =
                new FindTaskPredicateAssemblerBuilder().withStartDatePredicate(SLAUGHTER_START_DATE_PREDICATE).build();
        expectedCommand = new FindCommand(predicateBuilder);
        assertParseSuccess(parser, SLAUGHTER_START_DATE_SEARCH, expectedCommand);

        // End Date
        predicateBuilder =
                new FindTaskPredicateAssemblerBuilder().withEndDatePredicate(BRUSH_END_DATE_PREDICATE).build();
        expectedCommand = new FindCommand(predicateBuilder);
        assertParseSuccess(parser, BRUSH_END_DATE_SEARCH, expectedCommand);

        // Tag
        predicateBuilder =
                new FindTaskPredicateAssemblerBuilder().withTagsPredicate(SLAUGHTER_TAG_PREDICATE).build();
        expectedCommand = new FindCommand(predicateBuilder);
        assertParseSuccess(parser, SLAUGHTER_TAG_SEARCH, expectedCommand);
    }


    @Test
    public void parse_compound_success() {
        String userInput = SLAUGHTER_START_DATE_SEARCH + SLAUGHTER_NAME_SEARCH;
        TaskPredicateAssembler predicateBuilder =
                new FindTaskPredicateAssemblerBuilder()
                        .withNamePredicate(SLAUGHTER_NAME_PREDICATE)
                        .withStartDatePredicate(SLAUGHTER_START_DATE_PREDICATE)
                        .build();
        FindCommand expectedCommand = new FindCommand(predicateBuilder);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = SLAUGHTER_TAG_SEARCH + BRUSH_END_DATE_SEARCH;
        predicateBuilder =
                new FindTaskPredicateAssemblerBuilder()
                        .withEndDatePredicate(BRUSH_END_DATE_PREDICATE)
                        .withTagsPredicate(SLAUGHTER_TAG_PREDICATE)
                        .build();
        expectedCommand = new FindCommand(predicateBuilder);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = SLAUGHTER_BRUSH_NAME_SEARCH + BRUSH_END_DATE_SEARCH;
        predicateBuilder =
                new FindTaskPredicateAssemblerBuilder()
                        .withNamePredicate(SLAUGHTER_BRUSH_NAME_PREDICATE)
                        .withEndDatePredicate(BRUSH_END_DATE_PREDICATE)
                        .build();
        expectedCommand = new FindCommand(predicateBuilder);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
