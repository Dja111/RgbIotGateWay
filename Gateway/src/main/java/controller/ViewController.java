package controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import main.Main;
import model.Model;

import static java.lang.Thread.currentThread;

public class ViewController {
    @FXML
    private Label statusLabel;

    @FXML
    private ListView ledListView;

    @FXML
    private Button onButton;

    @FXML
    private Button offButton;

    @FXML
    private Button debugButton;

    @FXML
    private Button sendButton;



    @FXML
    public void initialize(){
//        Main.LOGGER.info("UI Thread "+currentThread()+" started!");
    }


    public void connect(Model model) {
            getOffButton().setOnAction(e->model.offHandler());
            getOnButton().setOnAction(e->model.onHandler());
            getDebugButton().setOnAction(e->model.debugHandler());
            getSendButton().setOnAction(e->model.sendHandler());
            getStatusLabel().textProperty().bind(model.statusProperty());
            model.updateLedListContent();
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(Label statusLabel) {
        this.statusLabel = statusLabel;
    }

    public ListView getLedListView() {
        return ledListView;
    }

    public void setLedListView(ListView ledListView) {
        this.ledListView = ledListView;
    }

    public Button getOnButton() {
        return onButton;
    }

    public void setOnButton(Button onButton) {
        this.onButton = onButton;
    }

    public Button getOffButton() {
        return offButton;
    }

    public void setOffButton(Button offButton) {
        this.offButton = offButton;
    }

    public Button getDebugButton() {
        return debugButton;
    }

    public void setDebugButton(Button debugButton) {
        this.debugButton = debugButton;
    }

    public Button getSendButton() {
        return sendButton;
    }

    public void setSendButton(Button sendButton) {
        this.sendButton = sendButton;
    }
}
