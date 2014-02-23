package org.rapidpm.demo.jaxenter.blog0012.teil6;

import com.tinkerforge.*;
import org.rapidpm.demo.jaxenter.blog0012.Localhost;

import java.io.IOException;

/**
 * Created by Sven Ruppert on 12.02.14.
 */
public class Piezo  {

    private String UID;

    public Piezo(final String UID) {
        this.UID = UID;
    }

    public void piep(final String morseCode) {
        IPConnection ipcon = new IPConnection();
        BrickletPiezoSpeaker b = new BrickletPiezoSpeaker(UID, ipcon);

        try {
            ipcon.connect(Localhost.HOST, Localhost.PORT);
            b.morseCode(morseCode, 1000);

        } catch (IOException | AlreadyConnectedException | TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
