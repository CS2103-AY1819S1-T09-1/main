package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.PersonId;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskId;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Math Assignment";
    public static final String DEFAULT_START_DATE = "20180123";
    public static final String DEFAULT_START_TIME = "0010";
    public static final String DEFAULT_END_DATE = "20180321";
    public static final String DEFAULT_END_TIME = "2350";

    private TaskId id;
    private Name name;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Set<Tag> tags;
    private Set<PersonId> personIds;

    public TaskBuilder() {
        id = null;
        name = new Name(DEFAULT_NAME);
        startDateTime = new DateTime(DEFAULT_START_DATE, DEFAULT_START_TIME);
        endDateTime = new DateTime(DEFAULT_END_DATE, DEFAULT_END_TIME);
        tags = new HashSet<>();
        personIds = new HashSet<>();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        id = taskToCopy.getId();
        name = taskToCopy.getName();
        startDateTime = taskToCopy.getStartDateTime();
        endDateTime = taskToCopy.getEndDateTime();
        tags = new HashSet<>(taskToCopy.getTags());
        personIds = new HashSet<>(taskToCopy.getPersonIds());
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code StartDateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withStartDateTime(DateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    /**
     * Sets the {@code StartDateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withEndDateTime(DateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }


    public Task build() {
        return new Task(id, name, startDateTime, endDateTime, tags, personIds);
    }

}
