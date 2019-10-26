package model;

import communicaton.CommunicationAddress;
import communicaton.MessageBuilder;
import controller.ViewController;
import digitalWorld.LED;
import gateway.Gateway;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Main;
import remoteResourceFramework.messageHandlers.MessageSender;
import remoteResourceFramework.model.RRFMessage;

import java.util.Optional;

public class Model {

    public static Model instance;
    private SimpleStringProperty status;
    private ViewController viewController;
    public Gateway gateway;

    public Model(ViewController viewController) throws Exception {
        this.status = new SimpleStringProperty("XBee not connected");
        this.viewController = viewController;
        gateway = new Gateway(this);
        instance=this;
    }

    public void onHandler() {
        Main.LOGGER.info("Setting all LEDs.");
        int rnd=(int)(Math.random()*100+1);
        if(rnd<10){     // white
            for (LED l : Storage.getLedStripe()) {
                l.setRGB(255,255,255);
            }
        }
        else if(rnd<40){    // rainbow
            double factor=10+ Math.random()*20;
            for (LED l : Storage.getLedStripe()) {
                Color c=Color.hsb(l.getIndex()*factor,1.0f,1.0f);
                l.setRGB((int)(c.getRed()*255),(int)(c.getGreen()*255),(int)(c.getBlue()*255));
            }
        }
        else if(rnd<60){    // random
            for (LED l : Storage.getLedStripe()) {
                Color c=Color.hsb(Math.random()*255,1.0f,1.0f);
                l.setRGB((int)(c.getRed()*255),(int)(c.getGreen()*255),(int)(c.getBlue()*255));
            }
        }
        else if(rnd<80){    // ababab
            Color c1=Color.hsb(Math.random()*255,1.0f,1.0f);
            Color c2=Color.hsb(Math.random()*255,1.0f,1.0f);
            for (LED l : Storage.getLedStripe()) {
                if(l.getIndex()%2==0){
                    l.setRGB((int)(c1.getRed()*255),(int)(c1.getGreen()*255),(int)(c1.getBlue()*255));
                }else{
                    l.setRGB((int)(c2.getRed()*255),(int)(c2.getGreen()*255),(int)(c2.getBlue()*255));
                }
            }
        }
        else{    // abcabc
            Color c1=Color.hsb(Math.random()*255,1.0f,1.0f);
            Color c2=Color.hsb(Math.random()*255,1.0f,1.0f);
            Color c3=Color.hsb(Math.random()*255,1.0f,1.0f);
            for (LED l : Storage.getLedStripe()) {
                if(l.getIndex()%3==0){
                    l.setRGB((int)(c1.getRed()*255),(int)(c1.getGreen()*255),(int)(c1.getBlue()*255));
                }if(l.getIndex()%3==1){
                    l.setRGB((int)(c2.getRed()*255),(int)(c2.getGreen()*255),(int)(c2.getBlue()*255));
                }else{
                    l.setRGB((int)(c3.getRed()*255),(int)(c3.getGreen()*255),(int)(c3.getBlue()*255));
                }
            }
        }

        setStatus("LED`s are set!");
        updateLedListContent();
    }

    public void offHandler() {
        Main.LOGGER.info("Setting all LEDs to black.");
        for (LED l : Storage.getLedStripe()) {
            l.setRGB(0,0,0);
        }
        setStatus("LED`s are black!");
        updateLedListContent();
    }
    public void debugHandler() {
//        byte[] address = {(byte)255,(byte)255}; //255 255 is broadcast
////        byte[] payload = {(byte)1};
////        Message message = new Message(address,payload);
//
//        for (int i = 1; i <= 42; i++) {
//            Storage.getLedStripe().get(i).setRGB(255,255,255);
//        }
//        RRFMessage[] messages = MessageBuilder.buildMessages(address,Storage.getLedStripe().getChangedLEDs());
//        for (Message message : messages) {
//            System.out.println(message);
//        }
//
//        if(this.gateway.getIeeeGateway() != null)
//            this.gateway.getIeeeGateway().sendMessage(message);
    }
    public void sendHandler() {
        byte[] broadcastAddress = {(byte)255,(byte)255};
        CommunicationAddress communicationAddress = new CommunicationAddress(broadcastAddress);
        RRFMessage[] messages = MessageBuilder.buildMessages(communicationAddress, Storage.getLedStripe().getChangedLEDs());
        MessageSender messageSender = new MessageSender(gateway.getIeeeGateway());
        Storage.getLedStripe().setLEDsToUnchanged();
        updateLedListContent();
        for (int i = 0; i < messages.length; i++) {
            messageSender.sendMessage(messages[i]);
            Main.LOGGER.info("Message " + (i + 1) + " of " + messages.length + " send.");
            setStatus("SENDING "+(i+1)+" of "+ messages.length+"!");
        }
    }
    public void updateLedListContent() {
        Platform.runLater(() -> {
            this.viewController.getLedListView().getItems().clear();
            for (LED l : Storage.getLedStripe()) {
                Label ledLbl = new Label(String.valueOf(l.getIndex()));
                ledLbl.setAlignment(Pos.CENTER);

                Label colorLbl = new Label("   ");
                colorLbl.setBackground(new Background(new BackgroundFill(Color.color(l.getRedAsFloat(),
                        l.getGreenAsFloat(),l.getBlueAsFloat()), new CornerRadii(40),
                        new Insets(2,2,2,2))));
                colorLbl.setAlignment(Pos.CENTER);
                colorLbl.setStyle("-fx-border-color: red; -fx-border-radius: 100px;");

                VBox labelGroup = new VBox(ledLbl,colorLbl);
                if(l.isChanged()){
                    Label changedLbl = new Label("x");
                    labelGroup.getChildren().add(changedLbl);
                }

                this.viewController.getLedListView().getItems().add(labelGroup);
                this.viewController.getLedListView().setOnMouseClicked(event -> {
                    if(event.getClickCount()==2 | event.getButton()== MouseButton.SECONDARY) {
                        LED led = Storage.getLedStripe().get(Integer.parseInt(((Label)
                                ((VBox) this.viewController.getLedListView().getSelectionModel().getSelectedItem())
                                        .getChildren().get(0)).getText()));
                        if (led != null) {
                            LedDialog ledDialog = new LedDialog(led);
                            Optional<LED> result = ledDialog.showAndWait();
                            result.ifPresent(resultLED -> {
                                ObservableList<Integer> ledIndices = this.viewController.getLedListView().getSelectionModel().getSelectedIndices();
                                for(int i : ledIndices){
                                    LED selectedLED = Storage.getLedStripe().get(i+1);
                                    selectedLED.setRGB(resultLED.getRed(), resultLED.getGreen(), resultLED.getBlue());
                                }
                                updateLedListContent();
                            });
                        }
                    }
                });
            }
        });
    }
    public SimpleStringProperty statusProperty() {
        return status;
    }
    public String getStatus() {
        return status.get();
    }
    public void setStatus(String status) {
        Platform.runLater(() -> this.status.set(status));
    }
    public static Model getInstance(){
        return instance;
    }
}
