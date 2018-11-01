package systemtests.contacts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ContactsParser.MODULE_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contacts.AssignedCommand;
import seedu.address.logic.commands.contacts.ClearCommand;
import seedu.address.logic.commands.contacts.FindCommand;
import seedu.address.logic.commands.contacts.ListCommand;
import systemtests.AppSystemTest;

/**
 * An extension of AppSystemTest with methods specific to contacts commands.
 */
public abstract class ContactsSystemTest extends AppSystemTest {
    /**
     * Displays all persons in the address book.
     */
    protected void showAllPersons() {
        executeCommand(MODULE_WORD + " " + ListCommand.COMMAND_WORD);
        assertEquals(getModel().getAddressBook().getPersonList().size(), getModel().getFilteredPersonList().size());
    }

    /**
     * Displays all persons with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPersonsWithName(String keyword) {
        executeCommand(MODULE_WORD + " " + FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredPersonList().size() < getModel().getAddressBook().getPersonList().size());
    }

    /**
     * Selects the person at {@code index} of the displayed list.
     */
    protected void selectPerson(Index index) {
        executeCommand(MODULE_WORD + " " + AssignedCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all persons in the address book.
     */
    protected void deleteAllPersons() {
        executeCommand(MODULE_WORD + " " + ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getAddressBook().getPersonList().size());
    }
}
