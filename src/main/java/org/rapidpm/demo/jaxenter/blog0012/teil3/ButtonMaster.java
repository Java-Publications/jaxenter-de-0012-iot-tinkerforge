package org.rapidpm.demo.jaxenter.blog0012.teil3;

import com.tinkerforge.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by ts40 on 22.01.14.
 */
public class ButtonMaster extends Application {
    private static final String host = "localhost";
    private static final int port = 4223;
    private static final String UID = "j5K";
    public static String newline = System.getProperty("line.separator");

    private final IPConnection ipcon = new IPConnection();
    private final BrickletDualButton db = new BrickletDualButton(UID, ipcon);

    public static void main(String[] args) {
        launch(args);
    }

    public Button bL;
    public Button bR;

    public TextArea tx;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Button TinkerForge Sample");

        final VBox vBox = new VBox();
        setAnchorZero(vBox);
        final HBox hBox = new HBox();
        setAnchorZero(hBox);
        bL = new Button("LED links");
        bR = new Button("LED rechts");

        setAnchorZero(bL);
        setAnchorZero(bR);

        bL.setOnAction(actionEvent -> activateLeftButton());
        bR.setOnAction(actionEvent -> activateRightButton());

        hBox.getChildren().add(bL);
        hBox.getChildren().add(bR);

        vBox.getChildren().add(hBox);

        tx = new TextArea();
        vBox.getChildren().add(tx);


        Scene scene = new Scene(vBox);
        stage.setScene(scene);

        Platform.runLater(new Worker());

        stage.show();

    }

    private void setAnchorZero(final Node node) {
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
    }


    public class Worker implements Runnable{
        @Override
        public void run() {
            try {
                ipcon.connect(host, port);
                final BrickletDualButton.StateChangedListener changedListener
                        = (buttonL, buttonR, ledL, ledR) -> Platform.runLater(() -> {
                    String text = tx.getText();
                    System.out.println("buttonL = " + buttonL);
                    System.out.println("buttonR = " + buttonR);
                    if (buttonL == BrickletDualButton.BUTTON_STATE_PRESSED) {
                        text = "Left button pressed".concat(newline).concat(text);
                        activateLeftButton();
                    }
                    if(buttonL == BrickletDualButton.BUTTON_STATE_RELEASED){
                        text = "Left button released".concat(newline).concat(text);
                    }
                    if (buttonR == BrickletDualButton.BUTTON_STATE_PRESSED) {
                        text = "Right button pressed".concat(newline).concat(text);
                        activateRightButton();
                    }
                    if (buttonR == BrickletDualButton.BUTTON_STATE_RELEASED) {
                        text = "Right button released".concat(newline).concat(text);
                    }
                    tx.setText(text);
                });

                db.addStateChangedListener(changedListener);
                db.setLEDState(BrickletDualButton.LED_STATE_ON,BrickletDualButton.LED_STATE_ON );

            } catch (IOException | AlreadyConnectedException | TimeoutException | NotConnectedException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    private void activateRightButton() {
        try {
            db.setLEDState(BrickletDualButton.LED_STATE_OFF,BrickletDualButton.LED_STATE_ON );

            bL.setText("InActive");
            bR.setText("Active");
        } catch (TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }

    private void activateLeftButton() {
        try {
            db.setLEDState(BrickletDualButton.LED_STATE_ON,BrickletDualButton.LED_STATE_OFF );

            bL.setText("Active");
            bR.setText("InActive");

        } catch (TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }


}
