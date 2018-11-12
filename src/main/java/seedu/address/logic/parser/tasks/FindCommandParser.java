package seedu.address.logic.parser.tasks;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_KEYWORD;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TAG;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.tasks.FindCommand;
import seedu.address.logic.commands.tasks.FindCommand.TaskPredicateAssembler;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.HasTagsPredicate;
import seedu.address.model.task.MatchesEndDatePredicate;
import seedu.address.model.task.MatchesStartDatePredicate;
import seedu.address.model.task.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_TAG);

        TaskPredicateAssembler combinedPredicate = new TaskPredicateAssembler();

        if (!argMultiMap.getAllValues(PREFIX_NAME).isEmpty()) {
            List<String> keywords = argMultiMap.getAllValues(PREFIX_NAME);
            if (isAnyKeywordInvalid(keywords)) {
                throw new ParseException(
                        MESSAGE_INVALID_KEYWORD, true);
            }
            combinedPredicate.setNamePredicate(new NameContainsKeywordsPredicate(
                    argMultiMap.getAllValues(PREFIX_NAME))
            );
        }
        if (argMultiMap.getValue(PREFIX_START_DATE).isPresent()) {
            DateTime startDate = ParserUtil.parseDateToDateTime(argMultiMap.getValue(PREFIX_START_DATE).get());
            combinedPredicate.setStartDatePredicate(new MatchesStartDatePredicate(startDate));
        }
        if (argMultiMap.getValue(PREFIX_END_DATE).isPresent()) {
            DateTime endDate = ParserUtil.parseDateToDateTime(argMultiMap.getValue(PREFIX_END_DATE).get());
            combinedPredicate.setEndDatePredicate(new MatchesEndDatePredicate(endDate));
        }
        if (!argMultiMap.getAllValues(PREFIX_TAG).isEmpty()) {
            List<String> tagNames = argMultiMap.getAllValues(PREFIX_TAG);
            if (isAnyTagNameInvalid(tagNames)) {
                throw new ParseException(
                        MESSAGE_INVALID_TAG, true);
            }
            parseTagsForMatching(argMultiMap.getAllValues(PREFIX_TAG)).ifPresent((tags) ->
                    combinedPredicate.setHasTagsPredicate(new HasTagsPredicate(new ArrayList<>(tags)))
            );
        }

        if (!combinedPredicate.isAnyPredicateProvided()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), true);
        }
        return new FindCommand(combinedPredicate);
    }


    /**
     * Checks if any keyword is an empty string or contains more than 1 word.
     */
    private boolean isAnyKeywordInvalid(List<String> keywords) {
        assert keywords != null;
        return keywords.stream()
                .anyMatch(keyword -> keyword.trim().equals("") || keyword.trim().split("\\s+").length > 1);
    }

    /**
     * Checks if any tag name is an empty string contains more than 1 word.
     */
    private boolean isAnyTagNameInvalid(List<String> tagNames) {
        assert tagNames != null;
        return tagNames.stream()
                .anyMatch(tagName -> tagName.trim().equals("") || tagName.trim().split("\\s+").length > 1);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForMatching(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
