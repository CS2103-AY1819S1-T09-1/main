package seedu.address.logic.parser.tasks;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_DATETIME;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FORMAT_END_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FORMAT_START_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VALUE_END_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VALUE_START_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.START_DATETIME_DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.START_DATETIME_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FARM;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FARM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tasks.EditCommand;
import seedu.address.logic.commands.tasks.EditCommand.EditTaskDescriptor;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), "");

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertInvalidFormatParseFailure(VALID_NAME_BRUSH);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertInvalidFormatParseFailure("");
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertInvalidFormatParseFailure("-5" + NAME_DESC_BRUSH);

        // zero index
        assertInvalidFormatParseFailure("0" + NAME_DESC_BRUSH);

        // invalid arguments being parsed as preamble
        assertInvalidFormatParseFailure("1 some random string");

        // invalid prefix being parsed as preamble
        assertInvalidFormatParseFailure("1 i/ string");
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        // Only providing either date or time
        assertParseFailure(parser, "1" + VALID_START_DATE_DESC, MESSAGE_MISSING_DATETIME);
        assertParseFailure(parser, "1" + VALID_START_TIME_DESC, MESSAGE_MISSING_DATETIME);
        assertParseFailure(parser, "1" + VALID_END_DATE_DESC, MESSAGE_MISSING_DATETIME);
        assertParseFailure(parser, "1" + VALID_END_TIME_DESC, MESSAGE_MISSING_DATETIME);

        // Invalid Value DateTime
        assertParseFailure(parser, "1" + INVALID_VALUE_START_DATETIME_DESC,
                DateTime.MESSAGE_DATETIME_VALUE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_VALUE_END_DATETIME_DESC,
                DateTime.MESSAGE_DATETIME_VALUE_CONSTRAINTS);

        // Invalid Format DateTime
        assertParseFailure(parser, "1" + INVALID_FORMAT_START_DATETIME_DESC,
                DateTime.MESSAGE_DATETIME_FORMAT_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_FORMAT_END_DATETIME_DESC,
                DateTime.MESSAGE_DATETIME_FORMAT_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + NAME_DESC_BRUSH
                + START_DATETIME_DESC_BRUSH + END_DATETIME_DESC_BRUSH + TAG_DESC_FARM + TAG_DESC_FRIEND;

        EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder()
                        .withName(VALID_NAME_BRUSH)
                        .withStartDateTime(new DateTime(VALID_START_DATE_BRUSH, VALID_START_TIME_BRUSH))
                        .withEndDateTime(new DateTime(VALID_END_DATE_BRUSH, VALID_END_TIME_BRUSH))
                        .withTags(VALID_TAG_FARM, VALID_TAG_FRIEND)
                        .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + NAME_DESC_SLAUGHTER + END_DATETIME_DESC_SLAUGHTER;

        EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder()
                        .withName(VALID_NAME_SLAUGHTER)
                        .withEndDateTime(new DateTime(VALID_END_DATE_SLAUGHTER, VALID_END_TIME_SLAUGHTER))
                        .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput;
        EditTaskDescriptor descriptor;
        EditCommand expectedCommand;

        // Name
        userInput = targetIndex.getOneBased() + NAME_DESC_SLAUGHTER;
        descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_SLAUGHTER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Start DateTime
        userInput = targetIndex.getOneBased() + START_DATETIME_DESC_SLAUGHTER;
        descriptor = new EditTaskDescriptorBuilder()
                .withStartDateTime(new DateTime(VALID_START_DATE_SLAUGHTER, VALID_START_TIME_SLAUGHTER))
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // End DateTime
        userInput = targetIndex.getOneBased() + END_DATETIME_DESC_SLAUGHTER;
        descriptor = new EditTaskDescriptorBuilder()
                .withEndDateTime(new DateTime(VALID_END_DATE_SLAUGHTER, VALID_END_TIME_SLAUGHTER))
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FARM;
        descriptor = new EditTaskDescriptorBuilder().withTags(VALID_TAG_FARM).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased()
                + NAME_DESC_BRUSH + START_DATETIME_DESC_BRUSH + END_DATETIME_DESC_BRUSH + TAG_DESC_FRIEND
                + NAME_DESC_SLAUGHTER + START_DATETIME_DESC_SLAUGHTER + END_DATETIME_DESC_SLAUGHTER + TAG_DESC_FARM;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withName(VALID_NAME_SLAUGHTER)
                .withStartDateTime(new DateTime(VALID_START_DATE_SLAUGHTER, VALID_START_TIME_SLAUGHTER))
                .withEndDateTime(new DateTime(VALID_END_DATE_SLAUGHTER, VALID_END_TIME_SLAUGHTER))
                .withTags(VALID_TAG_FARM, VALID_TAG_FRIEND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // No other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_FORMAT_END_DATETIME_DESC + END_DATETIME_DESC_SLAUGHTER;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withEndDateTime(new DateTime(VALID_END_DATE_SLAUGHTER, VALID_END_TIME_SLAUGHTER))
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased()
                + INVALID_FORMAT_END_DATETIME_DESC + END_DATETIME_DESC_SLAUGHTER
                + NAME_DESC_SLAUGHTER + START_DATETIME_DESC_SLAUGHTER;
        descriptor = new EditTaskDescriptorBuilder()
                .withEndDateTime(new DateTime(VALID_END_DATE_SLAUGHTER, VALID_END_TIME_SLAUGHTER))
                .withName(VALID_NAME_SLAUGHTER)
                .withStartDateTime(new DateTime(VALID_START_DATE_SLAUGHTER, VALID_START_TIME_SLAUGHTER))
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    private void assertInvalidFormatParseFailure(String userInput) {
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
}
