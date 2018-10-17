package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Task's ID in the address book.
 * Guarantees: immutable
 */
public class TaskId {

    public final String id;

    /**
     * Constructs a {@code TaskId}.
     *
     * @param id A valid id
     */
    public TaskId(String id) {
        requireNonNull(id);
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskId // instanceof handles nulls
                && id.equals(((TaskId) other).id)); // state check
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
