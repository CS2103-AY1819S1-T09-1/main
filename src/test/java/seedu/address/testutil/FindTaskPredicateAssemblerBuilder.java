package seedu.address.testutil;

import seedu.address.logic.commands.tasks.FindCommand.TaskPredicateAssembler;
import seedu.address.model.task.HasTagsPredicate;
import seedu.address.model.task.MatchesEndDatePredicate;
import seedu.address.model.task.MatchesStartDatePredicate;
import seedu.address.model.task.NameContainsKeywordsPredicate;

/**
 * A utility class to help with building TaskPredicateAssembler objects.
 */
public class FindTaskPredicateAssemblerBuilder {

    private TaskPredicateAssembler assembler;

    public FindTaskPredicateAssemblerBuilder() {
        assembler = new TaskPredicateAssembler();
    }

    public FindTaskPredicateAssemblerBuilder(TaskPredicateAssembler assembler) {
        this.assembler = new TaskPredicateAssembler(assembler);
    }

    /**
     * Sets the {@code HasTagPredicate} of the {@code TaskPredicateAssembler} that we are building.
     */
    public FindTaskPredicateAssemblerBuilder withTagsPredicate(HasTagsPredicate predicate) {
        assembler.setHasTagsPredicate(predicate);
        return this;
    }

    /**
     * Sets the {@code NameContainsKeywordsPredicate} of the {@code TaskPredicateAssembler} that we are building.
     */
    public FindTaskPredicateAssemblerBuilder withNamePredicate(NameContainsKeywordsPredicate predicate) {
        assembler.setNamePredicate(predicate);
        return this;
    }

    /**
     * Sets the {@code MatchesStartDatePredicate} of the {@code TaskPredicateAssembler} that we are building.
     */
    public FindTaskPredicateAssemblerBuilder withStartDatePredicate(MatchesStartDatePredicate predicate) {
        assembler.setStartDatePredicate(predicate);
        return this;
    }

    /**
     * Sets the {@code MatchesEndDatePredicate} of the {@code TaskPredicateAssembler} that we are building.
     */
    public FindTaskPredicateAssemblerBuilder withEndDatePredicate(MatchesEndDatePredicate predicate) {
        assembler.setEndDatePredicate(predicate);
        return this;
    }

    public TaskPredicateAssembler build() {
        return assembler;
    }
}
