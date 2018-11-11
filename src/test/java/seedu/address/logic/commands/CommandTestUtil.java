package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.calendar.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.calendar.CliSyntax.PREFIX_YEAR;
import static seedu.address.logic.parser.contacts.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.contacts.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.contacts.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.contacts.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.contacts.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_START_DATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.contacts.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.tasks.FindCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.HasTagsPredicate;
import seedu.address.model.task.MatchesEndDatePredicate;
import seedu.address.model.task.MatchesStartDatePredicate;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.FindTaskPredicateAssemblerBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_MONTH = " " + PREFIX_MONTH + "1";
    public static final String VALID_YEAR = " " + PREFIX_YEAR + "2000";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String YEAR_2000 = VALID_YEAR;
    public static final String YEAR_2001 = " " + PREFIX_YEAR + "2001";
    public static final String MONTH_JAN = VALID_MONTH;
    public static final String MONTH_FEB = " " + PREFIX_MONTH + "2";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_MONTH_NEGATIVE = " " + PREFIX_MONTH + "-1";
    public static final String INVALID_MONTH_POSITIVE = " " + PREFIX_MONTH + "13";
    public static final String INVALID_YEAR_NEGATIVE = " " + PREFIX_YEAR + "-1";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .build();
    }

    public static final String VALID_NAME_BRUSH = "Brush the cows";
    public static final String VALID_NAME_SLAUGHTER = "Slaughter the cows";
    public static final String VALID_START_DATE_BRUSH = "20180101";
    public static final String VALID_START_DATE_SLAUGHTER = "20180228";
    public static final String VALID_START_TIME_BRUSH = "0000";
    public static final String VALID_START_TIME_SLAUGHTER = "0700";
    public static final String VALID_END_DATE_BRUSH = "20181231";
    public static final String VALID_END_DATE_SLAUGHTER = "20180228";
    public static final String VALID_END_TIME_BRUSH = "2359";
    public static final String VALID_END_TIME_SLAUGHTER = "1830";
    public static final String VALID_TAG_SLAUGHTER = "messy";

    public static final FindCommand.TaskPredicateAssembler FIND_BRUSH_BY_NAME;
    public static final FindCommand.TaskPredicateAssembler FIND_SLAUGHTER_AND_BRUSH_BY_NAME;
    public static final FindCommand.TaskPredicateAssembler FIND_BRUSH_BY_END_DATE;
    public static final FindCommand.TaskPredicateAssembler FIND_SLAUGHTER_BY_START_DATE;
    public static final FindCommand.TaskPredicateAssembler FIND_SLAUGHTER_BY_NAME_AND_START_DATE;
    public static final FindCommand.TaskPredicateAssembler FIND_SLAUGHTER_BY_TAG;

    public static final String BRUSH_NAME_SEARCH = " " + PREFIX_NAME + "Brush";
    public static final String SLAUGHTER_NAME_SEARCH = " " + PREFIX_NAME + "Slaughter";
    public static final String SLAUGHTER_BRUSH_NAME_SEARCH = " " + PREFIX_NAME + "cows";
    public static final String SLAUGHTER_START_DATE_SEARCH = " " + PREFIX_START_DATE + VALID_START_DATE_SLAUGHTER;
    public static final String BRUSH_END_DATE_SEARCH = " " + PREFIX_END_DATE + VALID_END_DATE_BRUSH;
    public static final String SLAUGHTER_TAG_SEARCH = " " + PREFIX_TAG + VALID_TAG_SLAUGHTER;
    public static final seedu.address.model.task.NameContainsKeywordsPredicate BRUSH_NAME_PREDICATE =
            new seedu.address.model.task.NameContainsKeywordsPredicate(Arrays.asList("Brush"));
    public static final seedu.address.model.task.NameContainsKeywordsPredicate SLAUGHTER_NAME_PREDICATE =
            new seedu.address.model.task.NameContainsKeywordsPredicate(Arrays.asList("Slaughter"));
    public static final seedu.address.model.task.NameContainsKeywordsPredicate SLAUGHTER_BRUSH_NAME_PREDICATE =
            new seedu.address.model.task.NameContainsKeywordsPredicate(Arrays.asList("cows"));
    public static final MatchesStartDatePredicate BRUSH_START_DATE_PREDICATE =
            new MatchesStartDatePredicate(VALID_START_DATE_BRUSH);
    public static final MatchesEndDatePredicate BRUSH_END_DATE_PREDICATE =
            new MatchesEndDatePredicate(VALID_END_DATE_BRUSH);
    public static final MatchesStartDatePredicate SLAUGHTER_START_DATE_PREDICATE =
            new MatchesStartDatePredicate(VALID_START_DATE_SLAUGHTER);
    public static final MatchesEndDatePredicate SLAUGHTER_END_DATE_PREDICATE =
            new MatchesEndDatePredicate(VALID_END_DATE_SLAUGHTER);
    public static final HasTagsPredicate SLAUGHTER_TAG_PREDICATE =
            new HasTagsPredicate(Collections.singletonList(new Tag(VALID_TAG_SLAUGHTER)));

    static {
        FIND_BRUSH_BY_NAME = new FindTaskPredicateAssemblerBuilder().withNamePredicate(BRUSH_NAME_PREDICATE).build();
        FIND_BRUSH_BY_END_DATE =
                new FindTaskPredicateAssemblerBuilder().withEndDatePredicate(BRUSH_END_DATE_PREDICATE).build();
        FIND_SLAUGHTER_BY_START_DATE =
                new FindTaskPredicateAssemblerBuilder().withStartDatePredicate(SLAUGHTER_START_DATE_PREDICATE).build();
        FIND_SLAUGHTER_AND_BRUSH_BY_NAME =
                new FindTaskPredicateAssemblerBuilder().withNamePredicate(SLAUGHTER_BRUSH_NAME_PREDICATE).build();
        FIND_SLAUGHTER_BY_NAME_AND_START_DATE =
                new FindTaskPredicateAssemblerBuilder()
                        .withNamePredicate(SLAUGHTER_NAME_PREDICATE)
                        .withStartDatePredicate(SLAUGHTER_START_DATE_PREDICATE)
                        .build();
        FIND_SLAUGHTER_BY_TAG =
                new FindTaskPredicateAssemblerBuilder()
                        .withTagsPredicate(SLAUGHTER_TAG_PREDICATE)
                        .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel}
     * remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given
     * {@code targetIndex} in the {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from
     * {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

    /**
     * Updates {@code model}'s filtered task list to show only the task at the given {@code targetIndex} in the
     * {@code model}
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getName().name.split("\\s+");
        model.updateFilteredTaskList(
                new seedu.address.model.task.NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));
        assertEquals(1, model.getFilteredTaskList().size());
    }

}
