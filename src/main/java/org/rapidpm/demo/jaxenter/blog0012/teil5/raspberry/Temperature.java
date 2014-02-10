package org.rapidpm.demo.jaxenter.blog0012.teil5.raspberry;

import com.tinkerforge.*;
import org.rapidpm.demo.jaxenter.blog0012.Localhost;
import org.rapidpm.demo.jaxenter.blog0012.teil5.LCD20x4;

import java.io.IOException;

/**
 * Created by Sven Ruppert on 09.02.14.
 */
public class Temperature implements Runnable {

    private String UID;
    private int callbackPeriod;

    private LCD20x4 lcd20x4 = new LCD20x4("jvX");


    public Temperature(final String UID, int callbackPeriod) {
        this.UID = UID;
        this.callbackPeriod = callbackPeriod;
    }

    @Override
    public void run() {
        IPConnection ipcon = new IPConnection();
        BrickletTemperature temp = new BrickletTemperature(UID, ipcon);

        try {
            ipcon.connect(Localhost.HOST, Localhost.PORT);
            ipcon.setAutoReconnect(true);
            temp.setTemperatureCallbackPeriod(callbackPeriod);
            temp.addTemperatureListener(temperature-> {
                    final double tempNorm = temperature / 100.0;
                    final String text = "Temp  : " + tempNorm + " °C";
                    lcd20x4.printLine(0, text);
            });
        } catch (IOException | AlreadyConnectedException | TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
