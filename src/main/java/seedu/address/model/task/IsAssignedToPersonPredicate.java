package seedu.address.model.task;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person} is assigned to the desired task (i.e. person contains id of {@code Task})
 */
public class IsAssignedToPersonPredicate implements Predicate<Person> {
    private final Task task;

    public IsAssignedToPersonPredicate(Task task) {
        this.task = task;
    }

    @Override
    public boolean test(Person person) {
        return person.getTaskIds().stream()
                .anyMatch(taskId -> task.getId() == taskId);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsAssignedToPersonPredicate // instanceof handles nulls
                && task.equals(((IsAssignedToPersonPredicate) other).task)); // state check
    }

}
