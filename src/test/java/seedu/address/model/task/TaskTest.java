package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SLAUGHTER;
import static seedu.address.testutil.TypicalTasks.BRUSH;
import static seedu.address.testutil.TypicalTasks.SLAUGHTER;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TaskBuilder;

public class TaskTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Task task = new TaskBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        task.getTags().remove(0);
    }

    @Test
    public void isSameTask() {
        TaskId originalId = new TaskId();
        Task brushWithId = new TaskBuilder(BRUSH).withId(originalId).build();
        // same object -> returns true
        assertTrue(brushWithId.isSameTask(brushWithId));

        // null -> returns false
        assertFalse(brushWithId.isSameTask(null));

        // same ID but different start datetime and end datetime -> returns true
        Task editedBrush = new TaskBuilder(brushWithId)
                .withStartDateTime(new DateTime(VALID_START_DATE_SLAUGHTER, VALID_START_TIME_SLAUGHTER))
                .withEndDateTime(new DateTime(VALID_END_DATE_SLAUGHTER, VALID_END_TIME_SLAUGHTER))
                .build();
        assertTrue(brushWithId.isSameTask(editedBrush));

        // same ID but different name -> returns true
        editedBrush = new TaskBuilder(brushWithId).withName(VALID_NAME_SLAUGHTER).build();
        assertTrue(brushWithId.isSameTask(editedBrush));

        // same ID, same name, same start and end datetime, different tags -> returns true
        editedBrush = new TaskBuilder(brushWithId).withTags(VALID_TAG_SLAUGHTER).build();
        assertTrue(brushWithId.isSameTask(editedBrush));

        // same ID, different name, different start and end datetime, different tags -> returns true
        editedBrush = new TaskBuilder(SLAUGHTER).withId(originalId).build();
        assertTrue(brushWithId.isSameTask(editedBrush));

        // different ID -> returns false
        editedBrush = new TaskBuilder(brushWithId).withId(new TaskId()).build();
        assertFalse(brushWithId.isSameTask(editedBrush));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task brushCopy = new TaskBuilder(BRUSH).build();
        assertTrue(BRUSH.equals(brushCopy));

        // same object -> returns true
        assertTrue(BRUSH.equals(BRUSH));

        // null -> returns false
        assertFalse(BRUSH.equals(null));

        // different type -> returns false
        assertFalse(BRUSH.equals(5));

        // different person -> returns false
        assertFalse(BRUSH.equals(SLAUGHTER));

        // different name -> returns false
        Task editedBrush = new TaskBuilder(BRUSH).withName(VALID_NAME_SLAUGHTER).build();
        assertFalse(BRUSH.equals(editedBrush));

        // different start datetime -> returns false
        editedBrush = new TaskBuilder(BRUSH)
                .withStartDateTime(new DateTime(VALID_START_DATE_SLAUGHTER, VALID_START_TIME_SLAUGHTER))
                .build();
        assertFalse(BRUSH.equals(editedBrush));

        // different end datetime -> returns false
        editedBrush = new TaskBuilder(BRUSH)
                .withEndDateTime(new DateTime(VALID_END_DATE_SLAUGHTER, VALID_END_TIME_SLAUGHTER))
                .build();
        assertFalse(BRUSH.equals(editedBrush));

        // different tags -> returns false
        editedBrush = new TaskBuilder(BRUSH).withTags(VALID_TAG_SLAUGHTER).build();
        assertFalse(BRUSH.equals(editedBrush));
    }
}
