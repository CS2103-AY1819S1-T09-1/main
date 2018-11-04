package seedu.address.logic.commands.calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindCommand}.
 */
public class ShowCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        Index jan = Index.fromOneBased(1);
        Index feb = Index.fromOneBased(2);

        Index year2000 = Index.fromOneBased(2000);
        Index year2001 = Index.fromOneBased(2001);

        ShowCommand showJan2000Command = new ShowCommand(year2000, jan);
        ShowCommand showFeb2000Command = new ShowCommand(year2000, feb);
        ShowCommand showJan2001Command = new ShowCommand(year2001, jan);

        // same object -> returns true
        assertTrue(showJan2000Command.equals(showJan2000Command));

        // same values -> returns true
        ShowCommand showJan2000CommandCopy = new ShowCommand(year2000, jan);
        assertTrue(showJan2000Command.equals(showJan2000CommandCopy));

        // different types -> returns false
        assertFalse(showJan2000Command.equals(1));

        // null -> returns false
        assertFalse(showJan2000Command.equals(null));

        // same year different month -> returns false
        assertFalse(showJan2000Command.equals(showFeb2000Command));

        // different year same month -> returns false
        assertFalse(showJan2000Command.equals(showJan2001Command));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        Index year = Index.fromOneBased(2018);
        Index month = Index.fromOneBased(12);
        Calendar dec2018 = new GregorianCalendar(year.getOneBased(), month.getZeroBased(), 1, 0, 0);

        ShowCommand command = new ShowCommand(year, month);
        String expectedMessage = String.format(ShowCommand.MESSAGE_SUCCESS, "December", 2018);
        expectedModel.setCalendarMonth(dec2018);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(dec2018, model.getCalendarMonth().getValue());
    }
}
