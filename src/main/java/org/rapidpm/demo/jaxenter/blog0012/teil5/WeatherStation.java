package org.rapidpm.demo.jaxenter.blog0012.teil5;

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

        final  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        final Thread t = new Thread(() -> {
            System.out.println("press Q THEN ENTER to terminate");
            int quit=0;
            while(true){
                try {
                    Thread.sleep(1000);
                    String msg = null;
                    while(true){
                        try{
                            msg=in.readLine();
                        }catch(Exception e){}
                        if(msg != null && msg.equals("Q")) { quit = 1; }
                        if(quit==1) break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }

        });
        t.start();
    }
}