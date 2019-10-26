package model;

import digitalWorld.LED;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LedDialog extends Dialog<LED> {

    private Rectangle colorRect;
    
    public LedDialog(LED led) {
        setTitle("LED Dialog");
        setHeaderText("Set the RGB of this specific LED");

        ButtonType acceptButtonType = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(acceptButtonType, ButtonType.CANCEL);


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        colorRect = new Rectangle(30,30,30,30);
        colorRect.setFill(Color.rgb(led.getRed(),led.getGreen(),led.getBlue()));
        grid.add(colorRect, 3, 0);

        grid.add(new Label("R:"), 0, 0);
        ScrollBar sbr = new ScrollBar();
        sbr.setMin(0);
        sbr.setMax(255);
        sbr.setValue(led.getRed());
        sbr.setLayoutX(200);
        sbr.setPrefHeight(30);
        grid.add(sbr, 1, 0);

        grid.add(new Label("G:"), 0, 1);
        ScrollBar sbg = new ScrollBar();
        sbg.setMin(0);
        sbg.setMax(255);
        sbg.setValue(led.getGreen());
        sbg.setLayoutX(200);
        sbg.setPrefHeight(30);
        grid.add(sbg, 1, 1);

        grid.add(new Label("B:"), 0, 2);
        ScrollBar sbb = new ScrollBar();
        sbb.setMin(0);
        sbb.setMax(255);
        sbb.setValue(led.getBlue());
        sbb.setLayoutX(200);
        sbb.setPrefHeight(30);
        grid.add(sbb, 1, 2);

        Label lblr = new Label(String.valueOf(led.getRed()));
        lblr.setPrefSize(30,10);
        grid.add(lblr, 2, 0);
        Label lblg = new Label(String.valueOf(led.getGreen()));
        lblg.setPrefSize(30,10);
        grid.add(lblg, 2, 1);
        Label lblb = new Label(String.valueOf(led.getBlue()));
        lblb.setPrefSize(30,10);
        grid.add(lblb, 2, 2);


        getDialogPane().setContent(grid);

        ChangeListener scrollBarListener = new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                colorRect.setFill(Color.rgb((int)sbr.getValue(),(int)sbg.getValue(),(int)sbb.getValue()));
                lblr.setText(String.valueOf((int)sbr.getValue()));
                lblg.setText(String.valueOf((int)sbg.getValue()));
                lblb.setText(String.valueOf((int)sbb.getValue()));
            }
        };
        sbr.valueProperty().addListener(scrollBarListener);
        sbg.valueProperty().addListener(scrollBarListener);
        sbb.valueProperty().addListener(scrollBarListener);

        setResultConverter(dialogButton -> {
            if (dialogButton == acceptButtonType) {
                LED returnLED = new LED(led.getIndex());
                returnLED.setRGB((int)sbr.getValue(),(int)sbg.getValue(),(int)sbb.getValue());
                return returnLED;
            }
            return null;
        });
    }
}
