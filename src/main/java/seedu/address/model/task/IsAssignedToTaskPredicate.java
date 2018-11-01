package seedu.address.model.task;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person} is assigned to the desired task (i.e. person contains id of {@code Task})
 */
public class IsAssignedToTaskPredicate implements Predicate<Person> {
    private final Task task;

    public IsAssignedToTaskPredicate(Task task) {
        this.task = task;
    }

    @Override
    public boolean test(Person person) {
        return person.getTaskIds().stream()
                .anyMatch(taskId -> task.getId().equals(taskId));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsAssignedToTaskPredicate // instanceof handles nulls
                && task.equals(((IsAssignedToTaskPredicate) other).task)); // state check
    }

}
