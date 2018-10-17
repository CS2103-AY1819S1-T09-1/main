package seedu.address.logic.commands.tasks;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.contacts.CliSyntax.PREFIX_CONTACT_ID;
import static seedu.address.logic.parser.contacts.CliSyntax.PREFIX_TASK_ID;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToPersonListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskId;

/**
 * Assigns a task to a contact. Both contact and task are identified by the index number used in the displayed person
 * and task list respectively.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a task to a contact. Both contact and task are identified by the index number used in the "
            + "displayed person and task list respectively.\n"
            + "Parameters: "
            + PREFIX_CONTACT_ID + "CONTACT_INDEX "
            + PREFIX_TASK_ID + "TASK_INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CONTACT_ID + "2 "
            + PREFIX_TASK_ID + "4";

    public static final String MESSAGE_ASSIGN_TASK_SUCCESS = "Assigned Task %1$s to Person %2$s";

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

        Set<TaskId> updatedTaskIds = new HashSet<>(personToEdit.getTaskIds());
        updatedTaskIds.add(taskToAssign.getId());
        Person editedPerson = new Person(personToEdit.getId(), personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), updatedTaskIds);

        Set<PersonId> updatedPersonIds = new HashSet<>(taskToAssign.getPersonIds());
        updatedPersonIds.add(personToEdit.getId());
        Task editedTask = new Task(taskToAssign.getId(), taskToAssign.getName(), taskToAssign.getStartDateTime(),
                taskToAssign.getEndDateTime(), taskToAssign.getTags(), updatedPersonIds);

        model.updatePerson(personToEdit, editedPerson);
        model.updateTask(taskToAssign, editedTask);
        model.commitAddressBook();

        EventsCenter.getInstance().post(new JumpToPersonListRequestEvent(targetContactIndex));
        return new CommandResult(String.format(MESSAGE_ASSIGN_TASK_SUCCESS,
                targetTaskIndex.getOneBased(), targetContactIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignCommand // instanceof handles nulls
                && targetContactIndex.equals(((AssignCommand) other).targetContactIndex) // state checks
                && targetTaskIndex.equals(((AssignCommand) other).targetTaskIndex)); // state checks
    }
}
