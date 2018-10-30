package seedu.address.storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.PersonId;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskId;


/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tasks's %s field is missing!";

    @XmlElement(required = true)
    private String id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private Calendar startDateTime;
    @XmlElement(required = true)
    private Calendar endDateTime;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedPersonId> assigned = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {
    }

    /**
     * Constructs an {@code XmlAdaptedTask} with the given task details.
     */
    public XmlAdaptedTask(String id, String name, DateTime startDateTime, DateTime endDateTime,
                          List<XmlAdaptedTag> tagged, List<XmlAdaptedPersonId> assigned) {
        this.id = id;
        this.name = name;
        this.startDateTime = startDateTime.getCalendar();
        this.endDateTime = endDateTime.getCalendar();
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (assigned != null) {
            this.assigned = new ArrayList<>(assigned);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedTask(Task source) {
        id = source.getId().toString();
        name = source.getName().toString();
        startDateTime = source.getStartDateTime().getCalendar();
        endDateTime = source.getEndDateTime().getCalendar();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        assigned = source.getPersonIds().stream()
                  .map(XmlAdaptedPersonId::new)
                  .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }

        final List<PersonId> taskPersonIds = new ArrayList<>();
        for (XmlAdaptedPersonId personId : assigned) {
            taskPersonIds.add(personId.toModelType());
        }

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskId.class.getSimpleName()));
        }
        final TaskId modelId = new TaskId(id);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (startDateTime == null || endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }

        final DateTime modelStartDateTime = new DateTime(startDateTime);
        final DateTime modelEndDateTime = new DateTime(endDateTime);

        final Set<Tag> modelTags = new HashSet<>(taskTags);
        final Set<PersonId> modelPersonIds = new HashSet<>(taskPersonIds);
        return new Task(modelId, modelName, modelStartDateTime, modelEndDateTime, modelTags, modelPersonIds);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTask)) {
            return false;
        }

        XmlAdaptedTask otherPerson = (XmlAdaptedTask) other;
        return Objects.equals(id, otherPerson.id)
                && Objects.equals(name, otherPerson.name)
                && Objects.equals(startDateTime, otherPerson.startDateTime)
                && Objects.equals(endDateTime, otherPerson.endDateTime)
                && tagged.equals(otherPerson.tagged)
                && assigned.equals(otherPerson.assigned);
    }
}
