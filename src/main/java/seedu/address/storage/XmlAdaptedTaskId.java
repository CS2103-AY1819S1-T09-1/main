package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.TaskId;

/**
 * JAXB-friendly adapted version of the TaskId.
 */
public class XmlAdaptedTaskId {

    @XmlValue
    private String taskIdStr;

    /**
     * Constructs an XmlAdaptedTaskId.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTaskId() {}

    /**
     * Constructs a {@code XmlAdaptedTaskId} with the given {@code taskIdStr}.
     */
    public XmlAdaptedTaskId(String taskIdStr) {
        this.taskIdStr = taskIdStr;
    }

    /**
     * Converts a given TaskId into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTaskId(TaskId source) {
        taskIdStr = source.id;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's TaskId object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public TaskId toModelType() throws IllegalValueException {
        return new TaskId(taskIdStr);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTaskId)) {
            return false;
        }

        return taskIdStr.equals(((XmlAdaptedTaskId) other).taskIdStr);
    }
}
