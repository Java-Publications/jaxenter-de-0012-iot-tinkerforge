package org.rapidpm.demo.jaxenter.blog0012.teil5.raspberry;

import com.tinkerforge.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.rapidpm.demo.jaxenter.blog0012.Localhost;
import org.rapidpm.demo.jaxenter.blog0012.teil5.LCD20x4;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Sven Ruppert on 09.02.14.
 */
public class Light implements Runnable {

    private String UID;
    private int callbackPeriod;
    private LCD20x4 lcd20x4 = new LCD20x4("jvX");

    public Light(final String UID, int callbackPeriod) {
        this.UID = UID;
        this.callbackPeriod = callbackPeriod;
    }

    @Override
    public void run() {
        IPConnection ipcon = new IPConnection();
        BrickletAmbientLight bricklet = new BrickletAmbientLight(UID, ipcon);
        try {
            ipcon.connect(Localhost.HOST, Localhost.PORT);

            bricklet.setIlluminanceCallbackPeriod(callbackPeriod);
            bricklet.addIlluminanceListener(illuminance -> {
                final double lux = illuminance / 10.0;
                final String text = "Lux   : " + lux + " Lux";
                lcd20x4.printLine(3, text);
            });
        } catch (IOException
                | AlreadyConnectedException
                | TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
