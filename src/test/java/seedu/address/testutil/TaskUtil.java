package seedu.address.testutil;

import static seedu.address.logic.parser.TasksParser.MODULE_WORD;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.tasks.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.tasks.AddCommand;
import seedu.address.logic.commands.tasks.EditCommand.EditTaskDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code Task}.
     */
    public static String getAddCommand(Task task) {
        return MODULE_WORD + " " + AddCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getName().toString() + " ");
        sb.append(PREFIX_START_DATE + task.getStartDateTime().getInputDate() + " ");
        sb.append(PREFIX_START_TIME + task.getStartDateTime().getInputTime() + " ");
        sb.append(PREFIX_END_DATE + task.getEndDateTime().getInputTime() + " ");
        sb.append(PREFIX_END_TIME + task.getEndDateTime().getInputTime() + " ");
        task.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
     */
    public static String getEditTaskDescriptorDetails(EditTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.toString()).append(" "));
        descriptor.getStartDateTime().ifPresent(startDateTime -> {
            sb.append(PREFIX_START_DATE).append(startDateTime.getInputDate()).append(" ");
            sb.append(PREFIX_START_TIME).append(startDateTime.getInputTime()).append(" ");
        });
        descriptor.getEndDateTime().ifPresent(endDateTime -> {
            sb.append(PREFIX_END_DATE).append(endDateTime.getInputDate()).append(" ");
            sb.append(PREFIX_END_TIME).append(endDateTime.getInputTime()).append(" ");
        });
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
