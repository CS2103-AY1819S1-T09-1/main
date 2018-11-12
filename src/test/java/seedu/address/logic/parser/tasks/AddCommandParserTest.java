package seedu.address.logic.parser.tasks;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VALUE_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VALUE_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VALUE_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VALUE_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.START_DATETIME_DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FARM;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FARM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTasks.BRUSH;
import static seedu.address.testutil.TypicalTasks.getBrushCurrentDateTime;

import org.junit.Test;

import seedu.address.logic.commands.tasks.AddCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Task expectedTask = new TaskBuilder(BRUSH).withTags(VALID_TAG_FARM).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BRUSH + START_DATETIME_DESC_BRUSH
                + END_DATETIME_DESC_BRUSH + TAG_DESC_FARM, new AddCommand(expectedTask));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_BRUSH + NAME_DESC_BRUSH + START_DATETIME_DESC_BRUSH
                + END_DATETIME_DESC_BRUSH + TAG_DESC_FARM, new AddCommand(expectedTask));

        // multiple start dates - last start date accepted
        assertParseSuccess(parser, NAME_DESC_BRUSH + START_DATE_DESC_SLAUGHTER + START_DATE_DESC_BRUSH
                + START_TIME_DESC_BRUSH + END_DATETIME_DESC_BRUSH + TAG_DESC_FARM, new AddCommand(expectedTask));

        // multiple start times - last start time accepted
        assertParseSuccess(parser, NAME_DESC_BRUSH + START_DATE_DESC_BRUSH + START_TIME_DESC_SLAUGHTER
                + START_TIME_DESC_BRUSH + END_DATETIME_DESC_BRUSH + TAG_DESC_FARM, new AddCommand(expectedTask));

        // multiple end dates - last end date accepted
        assertParseSuccess(parser, NAME_DESC_BRUSH + START_DATETIME_DESC_BRUSH + END_DATE_DESC_SLAUGHTER
                + END_DATE_DESC_BRUSH + END_TIME_DESC_BRUSH + TAG_DESC_FARM, new AddCommand(expectedTask));

        // multiple end times - last end time accepted
        assertParseSuccess(parser, NAME_DESC_BRUSH + START_DATETIME_DESC_BRUSH + END_DATE_DESC_BRUSH
                + END_TIME_DESC_SLAUGHTER + END_TIME_DESC_BRUSH + TAG_DESC_FARM, new AddCommand(expectedTask));

        // multiple tags - all accepted
        Task expectedTaskMultipleTags = new TaskBuilder(BRUSH).withTags(VALID_TAG_FARM, VALID_TAG_URGENT)
                .build();
        assertParseSuccess(parser, NAME_DESC_BRUSH + START_DATETIME_DESC_BRUSH + END_DATETIME_DESC_BRUSH
                + TAG_DESC_FARM + TAG_DESC_URGENT, new AddCommand(expectedTaskMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no start date or time and zero tags
        Task expectedTask = new TaskBuilder(getBrushCurrentDateTime()).withTags().build();
        assertParseSuccess(parser, NAME_DESC_BRUSH + END_DATETIME_DESC_BRUSH,
                new AddCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE),
                "");

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BRUSH + VALID_START_DATETIME_DESC + VALID_END_DATETIME_DESC,
                expectedMessage);

        // missing end date prefix
        assertParseFailure(parser, NAME_DESC_BRUSH + VALID_START_DATETIME_DESC + VALID_DATE
                + VALID_END_TIME_DESC, expectedMessage);

        // missing end time prefix
        assertParseFailure(parser, NAME_DESC_BRUSH + VALID_START_DATETIME_DESC + VALID_END_DATE_DESC
                + VALID_TIME, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BRUSH + VALID_DATE + VALID_TIME + VALID_DATE + VALID_TIME,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + VALID_START_DATETIME_DESC + VALID_END_DATETIME_DESC
                + TAG_DESC_FARM, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid start date
        assertParseFailure(parser, NAME_DESC_BRUSH + INVALID_VALUE_START_DATE_DESC + VALID_START_TIME_DESC
                + VALID_END_DATETIME_DESC + TAG_DESC_FARM, DateTime.MESSAGE_DATETIME_VALUE_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser, NAME_DESC_BRUSH + VALID_START_DATE_DESC + INVALID_VALUE_START_TIME_DESC
                + VALID_END_DATETIME_DESC + TAG_DESC_FARM, DateTime.MESSAGE_DATETIME_VALUE_CONSTRAINTS);

        // invalid end date
        assertParseFailure(parser, NAME_DESC_BRUSH + VALID_START_DATETIME_DESC + INVALID_VALUE_END_DATE_DESC
                + VALID_END_TIME_DESC + TAG_DESC_FARM, DateTime.MESSAGE_DATETIME_VALUE_CONSTRAINTS);

        // invalid end time
        assertParseFailure(parser, NAME_DESC_BRUSH + VALID_START_DATETIME_DESC + VALID_END_DATE_DESC
                + INVALID_VALUE_END_TIME_DESC + TAG_DESC_FARM, DateTime.MESSAGE_DATETIME_VALUE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BRUSH + VALID_START_DATETIME_DESC + VALID_END_DATETIME_DESC
                + INVALID_TAG_DESC + TAG_DESC_FARM, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + VALID_START_DATETIME_DESC + INVALID_VALUE_END_DATE_DESC
                + VALID_END_TIME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BRUSH + VALID_START_DATETIME_DESC
                        + VALID_END_DATETIME_DESC,
                String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE), ""));
    }
}
