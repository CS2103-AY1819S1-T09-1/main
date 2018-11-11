package seedu.address.model.task;

import java.util.function.Predicate;

/**
 * Tests that a {@code Task}'s start date matches the start date given.
 */
public class MatchesStartDatePredicate implements Predicate<Task> {
    private final DateTime startDate;

    public MatchesStartDatePredicate(DateTime startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean test(Task task) {
        return task.getStartDateTime().hasSameDate(startDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchesStartDatePredicate // instanceof handles nulls
                && startDate.equals(((MatchesStartDatePredicate) other).startDate)); // state check
    }

}
