package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.testutil.TypicalTasks.getTypicalTasks;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * A utility class containing an AddressBook object to be used in tests.
 */
public class TypicalAddressBook {
    private TypicalAddressBook() {} // prevents instantiation

    /**
     * @return a typical AddressBook with all the typical Persons
     */
    public static AddressBook getTypicalAddressBookPersons() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    /**
     * @return a typical AddressBook with all the typical Tasks
     */
    public static AddressBook getTypicalAddressBookTasks() {
        AddressBook ab = new AddressBook();
        for (Task task: getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    /**
     * @return a typical AddressBook with all the typical Persons and Tasks
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }
}
