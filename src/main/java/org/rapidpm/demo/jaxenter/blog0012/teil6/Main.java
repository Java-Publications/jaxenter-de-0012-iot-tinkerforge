package org.rapidpm.demo.jaxenter.blog0012.teil6;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Sven Ruppert on 12.02.14.
 */
public class Main {

    public static void main(String[] args) {
        final Piezo piezo = new Piezo("iN2");
        piezo.piep("..--..");

        new Thread(new IRDistance("ju5", 200)).start();


        waitForCommandLine();



    }

    private static void waitForCommandLine() {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

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
