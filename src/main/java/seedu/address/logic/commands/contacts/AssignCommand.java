package seedu.address.logic.commands.contacts;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.contacts.CliSyntax.PREFIX_CONTACT_ID;
import static seedu.address.logic.parser.contacts.CliSyntax.PREFIX_TASK_ID;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Assigns a contact to a task. Both contact and task are identified by the index number used in the displayed person
 * and task list respectively.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a contact to a task. Both contact and task are identified by the index number used in the "
            + "displayed person and task list respectively.\n"
            + "Parameters: "
            + PREFIX_CONTACT_ID + "CONTACT_INDEX "
            + PREFIX_TASK_ID + "TASK_INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CONTACT_ID + "2 "
            + PREFIX_TASK_ID + "4";

    public static final String MESSAGE_ASSIGN_PERSON_SUCCESS = "Assigned Person %1$s to Task %2$s";

    private final Index targetContactIndex;
    private final Index targetTaskIndex;

    public AssignCommand(Index targetContactIndex, Index targetTaskIndex) {
        this.targetContactIndex = targetContactIndex;
        this.targetTaskIndex = targetTaskIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Person> filteredPersonList = model.getFilteredPersonList();
        if (targetContactIndex.getZeroBased() >= filteredPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        List<Task> filteredTaskList = model.getFilteredTaskList();
        if (targetTaskIndex.getZeroBased() >= filteredTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Person personToEdit = filteredPersonList.get(targetContactIndex.getZeroBased());
        Task taskToAssign = filteredTaskList.get(targetTaskIndex.getZeroBased());

        Set<Task> updatedTasks = new HashSet<>(personToEdit.getTasks());
        updatedTasks.add(taskToAssign);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), updatedTasks);

        model.updatePerson(personToEdit, editedPerson);
        // TODO: Check if updateFilteredPersonList call is necessary
        // model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetContactIndex));
        return new CommandResult(String.format(MESSAGE_ASSIGN_PERSON_SUCCESS,
                targetContactIndex.getOneBased(), targetTaskIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignCommand // instanceof handles nulls
                && targetContactIndex.equals(((AssignCommand) other).targetContactIndex) // state checks
                && targetTaskIndex.equals(((AssignCommand) other).targetTaskIndex)); // state checks
    }
}
