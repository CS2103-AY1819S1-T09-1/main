package seedu.address.logic.commands.tasks;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.task.HasTagsPredicate;
import seedu.address.model.task.MatchesEndDatePredicate;
import seedu.address.model.task.MatchesStartDatePredicate;
import seedu.address.model.task.NameContainsKeywordsPredicate;
import seedu.address.model.task.Task;

/**
 * Finds and lists all tasks in task list that fit the user's request based on name, start date, end date and tags
 * Keyword matching for name is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = getCommandFormat(COMMAND_WORD)
            + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive) "
            + "or by tags and start/end dates and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "KEYWORD]..."
            + "[" + PREFIX_START_DATE + "START DATE]"
            + "[" + PREFIX_END_DATE + "END DATE]"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + getCommandFormat(COMMAND_WORD) + " "
            + PREFIX_NAME + "math"
            + PREFIX_START_DATE + "20180101 "
            + PREFIX_END_DATE + "20181231 "
            + PREFIX_TAG + "school ";

    private final Predicate<Task> predicate;

    public FindCommand(Predicate<Task> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }

    /**
     * Stores the combined predicate to find tasks with. Multiple predicates can be provided and this class provides
     * the combined predicate for use
     */
    public static class TaskPredicateAssembler {
        private NameContainsKeywordsPredicate namePredicate;
        private MatchesStartDatePredicate startDatePredicate;
        private MatchesEndDatePredicate endDatePredicate;
        private HasTagsPredicate hasTagsPredicate;

        public TaskPredicateAssembler() {
        }

        /**
         * Copy constructor.
         */
        public TaskPredicateAssembler(TaskPredicateAssembler toCopy) {
            setNamePredicate(toCopy.namePredicate);
            setStartDatePredicate(toCopy.startDatePredicate);
            setEndDatePredicate(toCopy.endDatePredicate);
            setHasTagsPredicate(toCopy.hasTagsPredicate);
        }

        public boolean isAnyPredicateProvided() {
            return CollectionUtil.isAnyNonNull(namePredicate, startDatePredicate, endDatePredicate, hasTagsPredicate);
        }

        public void setNamePredicate(NameContainsKeywordsPredicate namePredicate) {
            this.namePredicate = namePredicate;
        }

        public Optional<NameContainsKeywordsPredicate> getNamePredicate() {
            return Optional.ofNullable(namePredicate);
        }

        public void setStartDatePredicate(MatchesStartDatePredicate startDatePredicate) {
            this.startDatePredicate = startDatePredicate;
        }

        public Optional<MatchesStartDatePredicate> getStartDatePredicate() {
            return Optional.ofNullable(startDatePredicate);
        }

        public void setEndDatePredicate(MatchesEndDatePredicate endDatePredicate) {
            this.endDatePredicate = endDatePredicate;
        }

        public Optional<MatchesEndDatePredicate> getEndDatePredicate() {
            return Optional.ofNullable(endDatePredicate);
        }

        public void setHasTagsPredicate(HasTagsPredicate hasTagsPredicate) {
            this.hasTagsPredicate = hasTagsPredicate;
        }

        public Optional<HasTagsPredicate> getHasTagsPredicate() {
            return Optional.ofNullable(hasTagsPredicate);
        }

        /**
         * Creates a combined predicate from the predicates passed in. If none are passed in, returns a predicate that
         * returns true for all tasks.
         */
        public Predicate<Task> getCombinedPredicate() {
            if (!isAnyPredicateProvided()) {
                return (task) -> true;
            } else {
                Predicate<Task> namePred = isNull(namePredicate) ? (task) -> true : namePredicate;
                Predicate<Task> startDatePred = isNull(startDatePredicate) ? (task) -> true : startDatePredicate;
                Predicate<Task> endDatePred = isNull(endDatePredicate) ? (task) -> true : endDatePredicate;
                Predicate<Task> hasTagsPred = isNull(hasTagsPredicate) ? (task) -> true : hasTagsPredicate;
                return (task) -> namePred.test(task)
                        && startDatePred.test(task)
                        && endDatePred.test(task)
                        && hasTagsPred.test(task);
            }
        }

        @Override
        public boolean equals(Object other) {
            // short circuit
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof TaskPredicateAssembler)) {
                return false;
            }

            // state check
            TaskPredicateAssembler e = (TaskPredicateAssembler) other;

            return getNamePredicate().equals(e.getNamePredicate())
                    && getStartDatePredicate().equals(e.getStartDatePredicate())
                    && getEndDatePredicate().equals(e.getEndDatePredicate())
                    && getHasTagsPredicate().equals(e.getHasTagsPredicate());
        }
    }
}

