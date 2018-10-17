package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.UUID;

/**
 * Represents a Person's ID in the address book.
 * Guarantees: immutable
 */
public class PersonId {

    public final String id;

    /**
     * Constructs a {@code PersonId} with a random ID.
     */
    public PersonId() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Constructs a {@code PersonId}.
     *
     * @param id A valid id
     */
    public PersonId(String id) {
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
                || (other instanceof PersonId // instanceof handles nulls
                && id.equals(((PersonId) other).id)); // state check
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
