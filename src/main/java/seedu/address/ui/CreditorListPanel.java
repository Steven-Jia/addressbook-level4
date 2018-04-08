package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CreditorPanelNoSelectionEvent;
import seedu.address.commons.events.ui.CreditorPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.Creditor;
/**
 * Panel containing the list of creditors.
 */
public class CreditorListPanel extends UiPart<Region>  {


    private static final String FXML = "CreditorListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CreditorListPanel.class);

    @javafx.fxml.FXML
    private ListView<CreditorCard> creditorListView;

    public CreditorListPanel(ObservableList<Creditor> creditorsList) {
        super(FXML);
        setConnections(creditorsList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Creditor> creditorsList) {
        ObservableList<CreditorCard> mappedList = EasyBind.map(
                creditorsList, (creditor) -> new CreditorCard(creditor, creditorsList.indexOf(creditor) + 1));
        creditorListView.setItems(mappedList);
        creditorListView.setCellFactory(listView -> new CreditorListViewCell());;
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        creditorListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new CreditorPanelSelectionChangedEvent(newValue));
                    } else {
                        logger.fine("No person selected in the person list");
                        raise(new CreditorPanelNoSelectionEvent());
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            creditorListView.scrollTo(index);
            creditorListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class CreditorListViewCell extends ListCell<CreditorCard> {
        @Override
        protected void updateItem(CreditorCard creditor, boolean empty) {
            super.updateItem(creditor, empty);

            if (empty || creditor == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(creditor.getRoot());
            }
        }
    }
}
