package seedu.address.logic.commands.tasks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.BRUSH_START_DATE_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.FIND_BRUSH_BY_END_DATE;
import static seedu.address.logic.commands.CommandTestUtil.FIND_BRUSH_BY_NAME;
import static seedu.address.logic.commands.CommandTestUtil.FIND_SLAUGHTER_BY_NAME_AND_START_DATE;
import static seedu.address.logic.commands.CommandTestUtil.FIND_SLAUGHTER_BY_START_DATE;
import static seedu.address.logic.commands.CommandTestUtil.FIND_SLAUGHTER_BY_TAG;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_BRUSH_NAME_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_END_DATE_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_NAME_PREDICATE;
import static seedu.address.logic.commands.CommandTestUtil.SLAUGHTER_START_DATE_PREDICATE;

import org.junit.Test;

import seedu.address.logic.commands.tasks.FindCommand.TaskPredicateAssembler;
import seedu.address.testutil.FindTaskPredicateAssemblerBuilder;

public class FindTaskPredicateAssemblerTest {

    @Test
    public void equals() {
        // same values -> returns true
        TaskPredicateAssembler endDatePredicateAssembler = new TaskPredicateAssembler(FIND_BRUSH_BY_END_DATE);
        assertTrue(FIND_BRUSH_BY_END_DATE.equals(endDatePredicateAssembler));

        TaskPredicateAssembler namePredicateAssembler = new TaskPredicateAssembler(FIND_BRUSH_BY_NAME);
        assertTrue(FIND_BRUSH_BY_NAME.equals(namePredicateAssembler));

        TaskPredicateAssembler startDatePredicateAssembler = new TaskPredicateAssembler(FIND_SLAUGHTER_BY_START_DATE);
        assertTrue(FIND_SLAUGHTER_BY_START_DATE.equals(startDatePredicateAssembler));

        TaskPredicateAssembler tagPredicateAssembler = new TaskPredicateAssembler(FIND_SLAUGHTER_BY_TAG);
        assertTrue(FIND_SLAUGHTER_BY_TAG.equals(tagPredicateAssembler));

        TaskPredicateAssembler compoundPredicateAssembler =
                new TaskPredicateAssembler(FIND_SLAUGHTER_BY_NAME_AND_START_DATE);
        assertTrue(FIND_SLAUGHTER_BY_NAME_AND_START_DATE.equals(compoundPredicateAssembler));

        // same values after compounding -> returns true
        compoundPredicateAssembler =
                new FindTaskPredicateAssemblerBuilder()
                        .withNamePredicate(SLAUGHTER_NAME_PREDICATE)
                        .withStartDatePredicate(SLAUGHTER_START_DATE_PREDICATE)
                        .build();
        assertTrue(FIND_SLAUGHTER_BY_NAME_AND_START_DATE.equals(compoundPredicateAssembler));

        // same object -> returns true
        assertTrue(FIND_BRUSH_BY_END_DATE.equals(FIND_BRUSH_BY_END_DATE));

        // null -> returns false
        assertFalse(FIND_BRUSH_BY_END_DATE.equals(null));

        // different types -> returns false
        assertFalse(FIND_BRUSH_BY_END_DATE.equals(5));

        // different values -> returns false
        assertFalse(FIND_BRUSH_BY_END_DATE.equals(FIND_BRUSH_BY_NAME));

        // different name predicate -> returns false
        TaskPredicateAssembler editedNamePredicate = new FindTaskPredicateAssemblerBuilder(FIND_BRUSH_BY_NAME)
                .withNamePredicate(SLAUGHTER_BRUSH_NAME_PREDICATE)
                .build();
        assertFalse(FIND_BRUSH_BY_NAME.equals(editedNamePredicate));

        // different start date predicate -> returns false
        TaskPredicateAssembler editedStartDatePredicate =
                new FindTaskPredicateAssemblerBuilder(FIND_SLAUGHTER_BY_START_DATE)
                        .withStartDatePredicate(BRUSH_START_DATE_PREDICATE)
                        .build();
        assertFalse(FIND_SLAUGHTER_BY_START_DATE.equals(editedStartDatePredicate));

        // different end date -> returns false
        TaskPredicateAssembler editedEndDatePredicate =
                new FindTaskPredicateAssemblerBuilder(FIND_BRUSH_BY_END_DATE)
                        .withEndDatePredicate(SLAUGHTER_END_DATE_PREDICATE)
                        .build();
        assertFalse(FIND_BRUSH_BY_END_DATE.equals(editedEndDatePredicate));

        // compare standard predicate to compounded predicate -> returns false
        assertFalse(FIND_SLAUGHTER_BY_NAME_AND_START_DATE.equals(FIND_SLAUGHTER_BY_START_DATE));
    }
}
