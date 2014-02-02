package org.rapidpm.demo.jaxenter.blog0012.teil4;

import com.tinkerforge.*;
import org.rapidpm.demo.jaxenter.blog0012.Localhost;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by Sven Ruppert on 01.02.14.
 */
public class SevenSegment {

    private String UID;
    private BrickletSegmentDisplay4x7 sevenSegment;
    private DecimalFormat myFormatter = new DecimalFormat("0000");

    private static final byte[] digits = {0x3f,0x06,0x5b,0x4f,
            0x66,0x6d,0x7d,0x07,
            0x7f,0x6f,0x77,0x7c,
            0x39,0x5e,0x79,0x71}; // 0~9,A,b,C,d,E,F

    public SevenSegment(String UID) {
        this.UID = UID;
        final IPConnection ipcon = new IPConnection();
        sevenSegment = new BrickletSegmentDisplay4x7(UID, ipcon);
        try {
            ipcon.connect(Localhost.HOST, Localhost.PORT);

            short[] segments = {digits[4], digits[2], digits[2], digits[3]};
            sevenSegment.setSegments(segments, (short)7, false);

        } catch (IOException | AlreadyConnectedException | NotConnectedException | TimeoutException e) {
            e.printStackTrace();
        }
    }


    public void printValue(final Double value){
        int intValue = value.intValue();
        final char[] chars = myFormatter.format(intValue).toCharArray();
        System.out.println(chars);

        final int aChar = chars[0];
        final byte d1 = digits[aChar-48];
        final byte d2 = digits[chars[1]-48];
        final byte d3 = digits[chars[2]-48];
        final byte d4 = digits[chars[3]-48];
        short[] segments = {d1, d2, d3, d4};
        try {
            sevenSegment.setSegments(   segments   ,(short)7, false);
        } catch (TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }

}
