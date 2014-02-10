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
//        new Thread(new Airpressure("jY4",  callbackPeriod)).start();
//        new Thread(new Altitude("jY4",  callbackPeriod));
        new Thread(new Barometer("jY4", callbackPeriod)).start();
        new Thread(new Light("jy2", callbackPeriod)).start();

        in = new BufferedReader(new InputStreamReader(System.in));

        Thread t1=new Thread(new avi());
        t1.start();

        System.out.println("press Q THEN ENTER to terminate");
        while(true){
            Thread.sleep(1000);
            if(quit==1) break;
        }
    }

    static BufferedReader in ;
    static int quit=0;

    private static class avi implements Runnable{


        public void run(){
            String msg = null;
            while(true){
                try{
                    msg=in.readLine();
                }catch(Exception e){}

                if(msg != null && msg.equals("Q")) {quit=1;break;}
            }
        }
    }
}