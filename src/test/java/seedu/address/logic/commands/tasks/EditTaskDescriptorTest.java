package seedu.address.logic.commands.tasks;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SLAUGHTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_BRUSH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;

import org.junit.Test;

import seedu.address.logic.commands.tasks.EditCommand.EditTaskDescriptor;
import seedu.address.model.task.DateTime;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_SLAUGHTER);
        assertTrue(DESC_SLAUGHTER.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_SLAUGHTER.equals(DESC_SLAUGHTER));

        // null -> returns false
        assertFalse(DESC_SLAUGHTER.equals(null));

        // different types -> returns false
        assertFalse(DESC_SLAUGHTER.equals(5));

        // different values -> returns false
        assertFalse(DESC_SLAUGHTER.equals(DESC_BRUSH));

        // different name -> returns false
        EditTaskDescriptor editedSlaughter =
                new EditTaskDescriptorBuilder(DESC_SLAUGHTER).withName(VALID_NAME_BRUSH).build();
        assertFalse(DESC_SLAUGHTER.equals(editedSlaughter));

        // different startDateTime -> returns false
        editedSlaughter = new EditTaskDescriptorBuilder(DESC_SLAUGHTER)
                .withStartDateTime(new DateTime(VALID_START_DATE_BRUSH, VALID_START_TIME_BRUSH))
                .build();
        assertFalse(DESC_SLAUGHTER.equals(editedSlaughter));

        // different endDateTime -> returns false
        editedSlaughter = new EditTaskDescriptorBuilder(DESC_SLAUGHTER)
                .withEndDateTime(new DateTime(VALID_END_DATE_BRUSH, VALID_END_TIME_BRUSH))
                .build();
        assertFalse(DESC_SLAUGHTER.equals(editedSlaughter));

        // different tags -> returns false
        editedSlaughter = new EditTaskDescriptorBuilder(DESC_SLAUGHTER).withTags(VALID_TAG_URGENT).build();
        assertFalse(DESC_SLAUGHTER.equals(editedSlaughter));

    }
}
