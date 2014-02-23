package org.rapidpm.demo.jaxenter.blog0012.teil6;

import com.tinkerforge.*;
import org.rapidpm.demo.jaxenter.blog0012.Localhost;
import org.rapidpm.demo.jaxenter.blog0012.teil5.LCD20x4;

import java.io.IOException;

/**
 * Created by Sven Ruppert on 12.02.14.
 */
public class IRDistance implements Runnable {

    private String UID;
    private int callbackPeriod;
    private LCD20x4 lcd20x4 = new LCD20x4("jvX");
    private Piezo piezo = new Piezo("iN2");

    public IRDistance(final String UID, int callbackPeriod) {
        this.UID = UID;
        this.callbackPeriod = callbackPeriod;
    }

    @Override
    public void run() {
        IPConnection ipcon = new IPConnection();
        BrickletDistanceIR b = new BrickletDistanceIR(UID, ipcon);


        try {
            ipcon.connect(Localhost.HOST, Localhost.PORT);
            b.setDistanceCallbackPeriod(callbackPeriod);
            b.addDistanceListener(new BrickletDistanceIR.DistanceListener() {
                @Override
                public void distance(int distance) {
                    final String text = "Dist   : " + distance / 10.0 + " cm";
                    System.out.println(text);
                    lcd20x4.printLine(1, text);
                    piezo.piep("..");

                }
            });


        } catch (IOException | AlreadyConnectedException | TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
