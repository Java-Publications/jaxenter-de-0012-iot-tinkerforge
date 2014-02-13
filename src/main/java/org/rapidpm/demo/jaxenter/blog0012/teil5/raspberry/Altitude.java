package org.rapidpm.demo.jaxenter.blog0012.teil5.raspberry;

import com.tinkerforge.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.rapidpm.demo.jaxenter.blog0012.Localhost;
import org.rapidpm.demo.jaxenter.blog0012.teil2.Barometer;
import org.rapidpm.demo.jaxenter.blog0012.teil5.LCD20x4;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Sven Ruppert on 09.02.14.
 */
public class Altitude implements Runnable {

    private String UID;
    private int callbackPeriod;
    private LCD20x4 lcd20x4 = new LCD20x4("jvX");

    public Altitude(final String UID, int callbackPeriod) {
        this.UID = UID;
        this.callbackPeriod = callbackPeriod;
    }

    @Override
    public void run() {
        IPConnection ipcon = new IPConnection();
        BrickletBarometer b = new BrickletBarometer(UID, ipcon);

        try {
            ipcon.connect(Localhost.HOST, Localhost.PORT);
            b.setAltitudeCallbackPeriod(callbackPeriod);
            b.addAltitudeListener(altitude -> {
                final String text = "Alt   : " + altitude / 100.0 + " m";
                lcd20x4.printLine(2, text);
            });
        } catch (IOException | AlreadyConnectedException | TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }



}
