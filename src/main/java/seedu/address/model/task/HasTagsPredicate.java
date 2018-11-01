package seedu.address.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Task}'s {@code Tag}s matches any of the tags given.
 */
public class HasTagsPredicate implements Predicate<Task> {
    private final List<Tag> tags;

    public HasTagsPredicate(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Task task) {
        return tags.stream()
                .anyMatch(tag -> task.getTags().contains(tag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HasTagsPredicate // instanceof handles nulls
                && tags.equals(((HasTagsPredicate) other).tags)); // state check
    }

}
