package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Person> PREDICATE_MATCHING_NO_PERSONS = unused -> false;
    private static final Predicate<Task> PREDICATE_MATCHING_NO_TASKS = unused -> false;

    /**
     * Updates {@code model}'s filtered person list to display only {@code toDisplay}.
     */
    public static void setFilteredPersonList(Model model, List<Person> toDisplay) {
        Optional<Predicate<Person>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredPersonList(Model, List)
     */
    public static void setFilteredPersonList(Model model, Person... toDisplay) {
        setFilteredPersonList(model, Arrays.asList(toDisplay));
    }

    /**
     * Updates {@code model}'s filtered task list to display only {@code toDisplay}.
     */
    public static void setFilteredTaskList(Model model, List<Task> toDisplay) {
        Optional<Predicate<Task>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredTaskList(predicate.orElse(PREDICATE_MATCHING_NO_TASKS));
    }

    /**
     * @see ModelHelper#setFilteredTaskList(Model, List)
     */
    public static void setFilteredTaskList(Model model, Task... toDisplay) {
        setFilteredTaskList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Person} equals to {@code other}.
     */
    private static Predicate<Person> getPredicateMatching(Person other) {
        return person -> person.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Task} equals to {@code other}.
     */
    private static Predicate<Task> getPredicateMatching(Task other) {
        return person -> person.equals(other);
    }
}
