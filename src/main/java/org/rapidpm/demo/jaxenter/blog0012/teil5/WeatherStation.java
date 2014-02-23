package org.rapidpm.demo.jaxenter.blog0012.teil5;

import org.rapidpm.demo.jaxenter.blog0012.WaitForQ;
import org.rapidpm.demo.jaxenter.blog0012.teil5.raspberry.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * Created by Sven Ruppert on 09.02.14.
 */
public class WeatherStation {

    private static int callbackPeriod = 10000;

    public static void main(String args[]) throws Exception {
        new Thread(new Temperature("dXj", callbackPeriod)).start();
        new Thread(new Barometer("jY4", callbackPeriod)).start();
        new Thread(new Light("jy2", callbackPeriod)).start();

        WaitForQ.waitForQ();
    }


}