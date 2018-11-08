package seedu.address.logic.commands.calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.Calendar;

import org.junit.Test;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.CalendarUtil;

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
        Pair<Index, Index> jan2000IndexPair = CalendarUtil.getYearMonthIndices(2000, 1);
        Pair<Index, Index> feb2000IndexPair = CalendarUtil.getYearMonthIndices(2000, 2);
        Pair<Index, Index> jan2001IndexPair = CalendarUtil.getYearMonthIndices(2001, 1);

        ShowCommand showJan2000Command = new ShowCommand(jan2000IndexPair.getKey(), jan2000IndexPair.getValue());
        ShowCommand showFeb2000Command = new ShowCommand(feb2000IndexPair.getKey(), feb2000IndexPair.getValue());
        ShowCommand showJan2001Command = new ShowCommand(jan2001IndexPair.getKey(), jan2001IndexPair.getValue());

        // same object -> returns true
        assertTrue(showJan2000Command.equals(showJan2000Command));

        // same values -> returns true
        ShowCommand showJan2000CommandCopy = new ShowCommand(jan2000IndexPair.getKey(), jan2000IndexPair.getValue());
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
        Pair<Index, Index> dec2018IndexPair = CalendarUtil.getYearMonthIndices(2018, 12);
        Calendar dec2018 = CalendarUtil.getCalendar(2018, 12);

        ShowCommand command = new ShowCommand(dec2018IndexPair.getKey(), dec2018IndexPair.getValue());
        String expectedMessage = String.format(ShowCommand.MESSAGE_SUCCESS, "December", 2018);
        expectedModel.setCalendarMonth(dec2018);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(dec2018, model.getCalendarMonth().getValue());
    }
}
