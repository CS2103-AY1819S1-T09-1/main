package seedu.address.model.task;

import java.util.function.Predicate;

/**
 * Tests that a {@code Task}'s end date matches the end date given.
 */
public class MatchesEndDatePredicate implements Predicate<Task> {
    private final String endDate;

    public MatchesEndDatePredicate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean test(Task task) {
        return task.getEndDateTime().getInputDate().equals(endDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchesEndDatePredicate // instanceof handles nulls
                && endDate.equals(((MatchesEndDatePredicate) other).endDate)); // state check
    }

}
