package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskId;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(null, new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), new HashSet<>()),
            new Person(null, new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends"), new HashSet<>()),
            new Person(null, new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours"), new HashSet<>()),
            new Person(null, new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family"), new HashSet<>()),
            new Person(null, new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates"), new HashSet<>()),
            new Person(null, new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), new HashSet<>())
        };
    }

    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(null, new seedu.address.model.task.Name("Make v2 release"),
                    new DateTime("20181110", "1000"), new DateTime("20181130", "1000"),
                    getTagSet("CS2103"), new HashSet<>()),
            new Task(null, new seedu.address.model.task.Name("Update User Guide"),
                    new DateTime("20181101", "1000"), new DateTime("20181109", "1000"),
                    getTagSet("CS2103"), new HashSet<>()),
            new Task(null, new seedu.address.model.task.Name("Update Developer Guide"),
                    new DateTime("20181101", "1000"), new DateTime("20181109", "1000"),
                    getTagSet("CS2103"), new HashSet<>()),
            new Task(null, new seedu.address.model.task.Name("Milk the cows"),
                    new DateTime("20181115", "1500"), new DateTime("20181115", "1800"),
                    getTagSet("farm"), new HashSet<>())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        Task[] tasks = getSampleTasks();
        Person[] persons = getSamplePersons();

        final int firstIndex = 0;
        final int secondIndex = 1;
        final int lastTaskIndex = tasks.length - 1;
        final int lastPersonIndex = persons.length - 1;

        assign(tasks, persons, firstIndex, firstIndex);
        assign(tasks, persons, secondIndex, firstIndex);
        assign(tasks, persons, firstIndex, secondIndex);
        assign(tasks, persons, lastTaskIndex, lastPersonIndex);

        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : persons) {
            sampleAb.addPerson(samplePerson);
        }
        for (Task sampleTask : tasks) {
            sampleAb.addTask(sampleTask);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Assigns a specific person from an array of persons and a specific task from an array of tasks
     * in two ways so as to maintain consistency
     */
    private static void assign(Task[] tasks, Person[] persons, int taskIndex, int personIndex) {
        Task task = tasks[taskIndex];
        Person person = persons[personIndex];
        Set<TaskId> taskIds = new HashSet<>(person.getTaskIds());
        Set<PersonId> personIds = new HashSet<>(task.getPersonIds());
        taskIds.add(task.getId());
        personIds.add(person.getId());
        tasks[taskIndex] = new Task(task.getId(), task.getName(), task.getStartDateTime(), task.getEndDateTime(),
                task.getTags(), personIds);
        persons[personIndex] = new Person(person.getId(), person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTags(), taskIds);
    }
}
