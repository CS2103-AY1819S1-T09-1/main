package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.PersonId;

/**
 * JAXB-friendly adapted version of the PersonId.
 */
public class XmlAdaptedPersonId {

    @XmlValue
    private String personIdStr;

    /**
     * Constructs an XmlAdaptedPersonId.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPersonId() {}

    /**
     * Constructs a {@code XmlAdaptedPersonId} with the given {@code personIdStr}.
     */
    public XmlAdaptedPersonId(String personIdStr) {
        this.personIdStr = personIdStr;
    }

    /**
     * Converts a given PersonId into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPersonId(PersonId source) {
        personIdStr = source.id;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's PersonId object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public PersonId toModelType() throws IllegalValueException {
        return new PersonId(personIdStr);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPersonId)) {
            return false;
        }

        return personIdStr.equals(((XmlAdaptedPersonId) other).personIdStr);
    }
}
