package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.PersonId;
import seedu.address.model.tag.Tag;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    public static final String MESSAGE_START_AFTER_END =
            "Start date and time cannot be later than end date and time.";

    private final TaskId id;
    private final Name name;
    private final DateTime startDateTime;
    private final DateTime endDateTime;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<PersonId> personIds = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Task(TaskId id, Name name, DateTime startDateTime, DateTime endDateTime,
                Set<Tag> tags, Set<PersonId> personIds) {
        requireAllNonNull(name, startDateTime, endDateTime, tags, personIds);
        if (id != null) {
            this.id = id;
        } else {
            this.id = new TaskId();
        }
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.tags.addAll(tags);
        this.personIds.addAll(personIds);
    }

    public TaskId getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable person ID set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<PersonId> getPersonIds() {
        return Collections.unmodifiableSet(personIds);
    }

    /**
     * Returns true if both tasks of the same name have the same start datetime and end datetime.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getId().equals(getId());
    }

    /**
     * Returns true if both tasks have the same name, start datetime, end datetime and tags.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getName().equals(getName())
                && otherTask.getStartDateTime().equals(getStartDateTime())
                && otherTask.getEndDateTime().equals(getEndDateTime())
                && otherTask.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDateTime, endDateTime, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" ID: ")
                .append(getId())
                .append(" Start date: ")
                .append(getStartDateTime().getDate())
                .append(" Start time: ")
                .append(getStartDateTime().getTime())
                .append(" End date: ")
                .append(getEndDateTime().getDate())
                .append(" End time: ")
                .append(getEndDateTime().getTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" PersonIds: ");
        getPersonIds().forEach(builder::append);
        return builder.toString();
    }

    public boolean isValidDateTimeRange() {
        return startDateTime.compareTo(endDateTime) <= 0;
    }
}
