package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * A UI component for displaying the day of the week in the calendar's header.
 */
public class CalendarHeaderCell extends UiPart<Region> {
    private static final String FXML = "CalendarHeaderCell.fxml";
    private static final String[] HEADERS = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday" };

    @FXML
    private Text calendarHeaderCellText;

    public CalendarHeaderCell(int col) {
        super(FXML);

        calendarHeaderCellText.setText(HEADERS[col]);
    }

}
