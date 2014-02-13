package org.rapidpm.demo.jaxenter.blog0012.teil5.raspberry;

import com.tinkerforge.*;
import org.rapidpm.demo.jaxenter.blog0012.Localhost;
import org.rapidpm.demo.jaxenter.blog0012.teil5.LCD20x4;

import java.io.IOException;

/**
 * Created by Sven Ruppert on 09.02.14.
 */
public class Barometer implements Runnable {

    private String UID;
    private int callbackPeriod;
    private LCD20x4 lcd20x4 = new LCD20x4("jvX");


    public Barometer(final String UID, int callbackPeriod) {
        this.UID = UID;
        this.callbackPeriod = callbackPeriod;
    }

    @Override
    public void run() {
        IPConnection ipcon = new IPConnection();
        BrickletBarometer b = new BrickletBarometer(UID, ipcon);

        try {
            ipcon.connect(Localhost.HOST, Localhost.PORT);
            b.setAirPressureCallbackPeriod(callbackPeriod);
            b.setAltitudeCallbackPeriod(callbackPeriod);
            b.addAirPressureListener(airPressure -> {
                final String text = "Air   : " + airPressure / 1000.0 + " mbar";
                lcd20x4.printLine(1, text);
            });

            b.addAltitudeListener(altitude -> {
                final String text = "Alt   : " + altitude / 100.0 + " m";
                lcd20x4.printLine(2, text);
            });

        } catch (IOException | AlreadyConnectedException | TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
