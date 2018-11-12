package seedu.address.logic.commands.tasks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.FIND_BRUSH_BY_END_DATE;
import static seedu.address.logic.commands.CommandTestUtil.FIND_SLAUGHTER_BY_NAME_AND_START_DATE;
import static seedu.address.logic.commands.CommandTestUtil.FIND_SLAUGHTER_BY_START_DATE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.BRUSH;
import static seedu.address.testutil.TypicalTasks.SLAUGHTER;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.tasks.FindCommand.TaskPredicateAssembler;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.HasTagsPredicate;
import seedu.address.model.task.MatchesEndDatePredicate;
import seedu.address.model.task.MatchesStartDatePredicate;
import seedu.address.model.task.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {

        FindCommand findBrushCommand = new FindCommand(FIND_BRUSH_BY_END_DATE);
        FindCommand findSlaughterCompoundCommand =
                new FindCommand(FIND_SLAUGHTER_BY_START_DATE);
        FindCommand findSlaughterCommand =
                new FindCommand(FIND_SLAUGHTER_BY_NAME_AND_START_DATE);

        // same object -> returns true
        assertTrue(findBrushCommand.equals(findBrushCommand));

        // same value -> returns true
        assertTrue(findBrushCommand.equals(new FindCommand(FIND_BRUSH_BY_END_DATE)));

        // different types -> returns false
        assertFalse(findBrushCommand.equals(1));

        // null -> returns false
        assertFalse(findBrushCommand.equals(null));

        // different predicates -> returns false
        assertFalse(findBrushCommand.equals(findSlaughterCommand));

        // different compounded predicates -> returns false
        assertFalse(findSlaughterCommand.equals(findSlaughterCompoundCommand));
    }

    @Test
    public void execute_zeroName_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TaskPredicateAssembler predicateBuilder = new TaskPredicateAssembler();
        predicateBuilder.setNamePredicate(prepareNamePredicate(" "));
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_wrongStartDate_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TaskPredicateAssembler predicateBuilder = new TaskPredicateAssembler();
        predicateBuilder.setStartDatePredicate(new MatchesStartDatePredicate(new DateTime("20181231")));
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_wrongEndDate_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TaskPredicateAssembler predicateBuilder = new TaskPredicateAssembler();
        predicateBuilder.setEndDatePredicate(new MatchesEndDatePredicate(new DateTime("20181111")));
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_wrongTag_noTaskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 0);
        TaskPredicateAssembler predicateBuilder = new TaskPredicateAssembler();
        predicateBuilder.setHasTagsPredicate(
                new HasTagsPredicate(Collections.singletonList(new Tag("wrongtag")))
        );
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTaskList());
    }

    @Test
    public void execute_correctName_taskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        TaskPredicateAssembler predicateBuilder = new TaskPredicateAssembler();
        predicateBuilder.setNamePredicate(prepareNamePredicate("Brush"));
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BRUSH), model.getFilteredTaskList());
    }

    @Test
    public void execute_correctStartDate_taskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        TaskPredicateAssembler predicateBuilder = new TaskPredicateAssembler();
        predicateBuilder.setStartDatePredicate(new MatchesStartDatePredicate(new DateTime("20180228")));
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(SLAUGHTER), model.getFilteredTaskList());
    }

    @Test
    public void execute_correctEndDate_taskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        TaskPredicateAssembler predicateBuilder = new TaskPredicateAssembler();
        predicateBuilder.setEndDatePredicate(new MatchesEndDatePredicate(new DateTime("20181231")));
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BRUSH), model.getFilteredTaskList());
    }

    @Test
    public void execute_correctTag_taskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        TaskPredicateAssembler predicateBuilder = new TaskPredicateAssembler();
        predicateBuilder.setHasTagsPredicate(
                new HasTagsPredicate(Collections.singletonList(new Tag("messy")))
        );
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(SLAUGHTER), model.getFilteredTaskList());
    }

    @Test
    public void execute_compoundNameStartDate_taskFound() {
        // 1 possible outcome from name and start date
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        TaskPredicateAssembler predicateBuilder = new TaskPredicateAssembler();
        predicateBuilder.setNamePredicate(prepareNamePredicate("Slaughter"));
        predicateBuilder.setStartDatePredicate(new MatchesStartDatePredicate(new DateTime("20180228")));
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(SLAUGHTER), model.getFilteredTaskList());

        // 2 possible outcomes based on name only, start date narrows to 1.
        predicateBuilder.setNamePredicate(prepareNamePredicate("cows"));
        predicateBuilder.setStartDatePredicate(new MatchesStartDatePredicate(new DateTime("20180228")));
        command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(SLAUGHTER), model.getFilteredTaskList());
    }

    @Test
    public void execute_compoundTagEndDate_taskFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 1);
        TaskPredicateAssembler predicateBuilder = new TaskPredicateAssembler();
        predicateBuilder.setEndDatePredicate(new MatchesEndDatePredicate(new DateTime("20180228")));
        predicateBuilder.setHasTagsPredicate(
                new HasTagsPredicate(Collections.singletonList(new Tag("messy")))
        );
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(SLAUGHTER), model.getFilteredTaskList());
    }

    @Test
    public void execute_multipleName_multipleTasksFound() {
        String expectedMessage = String.format(MESSAGE_TASKS_LISTED_OVERVIEW, 2);
        TaskPredicateAssembler predicateBuilder = new TaskPredicateAssembler();
        predicateBuilder.setNamePredicate(prepareNamePredicate("cows"));
        FindCommand command = new FindCommand(predicateBuilder);
        expectedModel.updateFilteredTaskList(predicateBuilder.getCombinedPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BRUSH, SLAUGHTER), model.getFilteredTaskList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
