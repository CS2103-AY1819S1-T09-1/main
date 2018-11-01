package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.task.Task;

/**
 * Tests that a {@code Task} is assigned to the desired person (i.e. task contains id of {@code Person})
 */
public class IsAssignedToPersonPredicate implements Predicate<Task> {
    private final Person person;

    public IsAssignedToPersonPredicate(Person person) {
        this.person = person;
    }

    @Override
    public boolean test(Task task) {
        return task.getPersonIds().stream()
                .anyMatch(personId -> person.getId().equals(personId));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsAssignedToPersonPredicate // instanceof handles nulls
                && person.equals(((IsAssignedToPersonPredicate) other).person)); // state check
    }

}
