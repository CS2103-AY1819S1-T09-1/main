package seedu.address.logic.commands.contacts;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToPersonListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IsAssignedToPersonPredicate;
import seedu.address.model.person.Person;

/**
 * Selects a person identified using its displayed index,
 * and the list of tasks will update to show only the tasks assigned to the person
 */
public class AssignedCommand extends Command {

    public static final String COMMAND_WORD = "assigned";

    public static final String MESSAGE_USAGE = getCommandFormat(COMMAND_WORD)
            + ": Selects the person identified by the index number used in the displayed person "
            + "list and shows the list of tasks assigned to the person.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + getCommandFormat(COMMAND_WORD) + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";

    private final Index targetIndex;

    public AssignedCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> filteredPersonList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= filteredPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        // Retrieve the desired person and update filter
        Person desiredPerson = filteredPersonList.get(targetIndex.getZeroBased());
        model.updateFilteredTaskList(new IsAssignedToPersonPredicate(desiredPerson));

        // Update UI (purely cosmetic for now)
        EventsCenter.getInstance().post(new JumpToPersonListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignedCommand // instanceof handles nulls
                && targetIndex.equals(((AssignedCommand) other).targetIndex)); // state check
    }
}
