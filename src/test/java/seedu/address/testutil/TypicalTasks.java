package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_SLAUGHTER;

import java.util.ArrayList;
import java.util.Arrays;
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
            .build();

    private TypicalTasks() {} // prevents instantiation

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(BRUSH, SLAUGHTER));
    }
}
