package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_FEED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_FEED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FEED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_FEED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_FEED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SLAUGHTER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.model.task.DateTime;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task BRUSH = new TaskBuilder().withName(VALID_NAME_BRUSH)
            .withStartDateTime(new DateTime(VALID_START_DATE_BRUSH, VALID_START_TIME_BRUSH))
            .withEndDateTime(new DateTime(VALID_END_DATE_BRUSH, VALID_END_TIME_BRUSH))
            .build();

    public static final Task SLAUGHTER = new TaskBuilder().withName(VALID_NAME_SLAUGHTER)
            .withStartDateTime(new DateTime(VALID_START_DATE_SLAUGHTER, VALID_START_TIME_SLAUGHTER))
            .withEndDateTime(new DateTime(VALID_END_DATE_SLAUGHTER, VALID_END_TIME_SLAUGHTER))
            .withTags(VALID_TAG_SLAUGHTER)
            .build();

    public static final Task FEED = new TaskBuilder().withName(VALID_NAME_FEED)
            .withStartDateTime(new DateTime(VALID_START_DATE_FEED, VALID_START_TIME_FEED))
            .withEndDateTime(new DateTime(VALID_END_DATE_FEED, VALID_END_TIME_FEED))
            .build();

    public static final String KEYWORD_MATCHING_BRUSH = "Brush"; // A keyword that matches MEIER

    private TypicalTasks() {} // prevents instantiation

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(BRUSH, SLAUGHTER));
    }

    public static Task getBrushCurrentDateTime() {
        Date currentDate = new Date();
        String nowDateString = DateTime.INPUT_DATE_FORMAT.format(currentDate);
        String nowTimeString = DateTime.INPUT_TIME_FORMAT.format(currentDate);
        return new TaskBuilder(BRUSH)
                .withStartDateTime(new DateTime(nowDateString, nowTimeString))
                .build();
    }
}
