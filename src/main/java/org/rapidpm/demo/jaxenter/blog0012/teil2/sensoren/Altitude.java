package org.rapidpm.demo.jaxenter.blog0012.teil2.sensoren;

import com.tinkerforge.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.rapidpm.demo.jaxenter.blog0012.teil2.Barometer;
import org.rapidpm.demo.jaxenter.blog0012.teil5.LCD20x4;

import java.io.IOException;
import java.util.Date;

/**
* Created by Sven Ruppert on 15.01.14.
*/
public class Altitude implements Runnable {

    private String UID;
    private ObservableList seriesData;
    private int callbackPeriod;
    private LCD20x4 lcd20x4 = new LCD20x4("jvX");

    public Altitude(final String UID, final XYChart.Series series,int callbackPeriod) {
        this.UID = UID;
        this.callbackPeriod = callbackPeriod;
        this.seriesData = series.getData();
    }

    @Override
    public void run() {
        IPConnection ipcon = new IPConnection();
        BrickletBarometer b = new BrickletBarometer(UID, ipcon);

        try {
            ipcon.connect(Barometer.host, Barometer.port);
            b.setAltitudeCallbackPeriod(callbackPeriod);
            b.addAltitudeListener(altitude -> {
                final String text = "Alt   : " + altitude / 100.0 + " m";
                System.out.println(text);
                lcd20x4.printLine(2, text);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        final double temp = altitude / 100.0;
                        final XYChart.Data data = new XYChart.Data(new Date(), temp);
                        seriesData.add(data);
                    }
                });
            });
        } catch (IOException | AlreadyConnectedException | TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }



}
