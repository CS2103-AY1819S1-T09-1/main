package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String END_DATE_TIME_FIELD_ID = "#endDateTime";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label endDateTimeLabel;
    private final List<Label> tagLabels;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        endDateTimeLabel = getChildNode(END_DATE_TIME_FIELD_ID);
        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getEndDateTime() {
        return endDateTimeLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code task}.
     */
    public boolean equals(Task task) {
        return getName().equals(task.getName().toString())
                && getEndDateTime().equals("Due by: " + task.getEndDateTime().toString())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(task.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}
