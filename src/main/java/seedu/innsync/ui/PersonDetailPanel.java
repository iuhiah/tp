package seedu.innsync.ui;

import static java.util.Objects.requireNonNullElse;

import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.innsync.model.person.Person;
import seedu.innsync.model.request.Request;

/**
 * Panel for displaying detailed information about a selected {@code Person}.
 */
public class PersonDetailPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailPanel.fxml";

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label memoLabel;

    @FXML
    private FlowPane detailTags;

    @FXML
    private FlowPane detailBookingTags;

    @FXML
    private VBox detailRequests;

    @FXML
    private ImageView detailStarIcon;

    @FXML
    private VBox placeholderBox;

    @FXML
    private ScrollPane scrollPane;

    /**
     * Creates a {@code PersonDetailPanel} with a default placeholder.
     */
    public PersonDetailPanel() {
        super(FXML);
        showPlaceholder();
    }

    /**
     * Updates the panel with the details of the given person.
     */
    public void setPerson(Person person) {
        if (person == null) {
            showPlaceholder();
            return;
        }

        // Hide placeholder and show details
        placeholderBox.setVisible(false);
        placeholderBox.setManaged(false);

        // Set person details
        nameLabel.setText(person.getName().fullName);
        phoneLabel.setText(person.getPhone().value);
        emailLabel.setText(person.getEmail().value);
        addressLabel.setText(person.getAddress().value);
        detailStarIcon.setVisible(person.getStarred());

        // Clear memo
        memoLabel.setText("");

        // Clear previous requests
        detailRequests.getChildren().clear();

        // Clear previous tags
        detailTags.getChildren().clear();
        detailBookingTags.getChildren().clear();

        // Add memo
        if (person.getMemo() != null) {
            memoLabel.setText(requireNonNullElse(person.getMemo().value, ""));
            memoLabel.setWrapText(true);
        }

        // Add requests with a checkbox beside each
        List<Request> requests = person.getRequests();
        for (int i = 0; i < requests.size(); i++) {
            Request request = requests.get(i);

            // Create a HBox for each request
            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.TOP_LEFT);

            // Create the checkbox for each request
            CheckBox checkBox = new CheckBox();
            checkBox.setId("checkbox_" + request.requestName);
            checkBox.setSelected(request.isCompleted());
            checkBox.setMouseTransparent(true);
            checkBox.setAlignment(Pos.TOP_LEFT);
            checkBox.setPadding(new Insets(1));

            // Create the label for each request
            Label requestLabel = new Label(String.format("%d. %s", i + 1, request.requestName));
            requestLabel.getStyleClass().add("detail-request");
            requestLabel.setWrapText(true);

            // Add the checkbox and label to the HBox
            hbox.getChildren().addAll(checkBox, requestLabel);

            // Add the HBox to the detailRequests FlowPane
            detailRequests.getChildren().add(hbox);
        }

        // Add tags
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add("detail-tag");
                    detailTags.getChildren().add(tagLabel);
                });

        // Add booking tags
        person.getBookingTags().stream()
                .sorted(Comparator.comparing(bookingTag -> bookingTag.bookingTagName))
                .forEach(bookingTag -> {
                    Label bookingTagLabel = new Label(bookingTag.toPrettier());
                    bookingTagLabel.getStyleClass().add("detail-booking-tag");
                    detailBookingTags.getChildren().add(bookingTagLabel);
                });
    }

    /**
     * Shows the placeholder when no person is selected.
     */
    private void showPlaceholder() {
        nameLabel.setText("");
        phoneLabel.setText("");
        emailLabel.setText("");
        addressLabel.setText("");
        memoLabel.setText("");
        detailStarIcon.setVisible(false);
        detailTags.getChildren().clear();
        detailBookingTags.getChildren().clear();
        detailRequests.getChildren().clear();

        placeholderBox.setVisible(true);
        placeholderBox.setManaged(true);
    }
}
