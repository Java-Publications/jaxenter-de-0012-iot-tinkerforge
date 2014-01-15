package org.rapidpm.demo.jaxenter.blog0012.teil2.sensoren;

import com.tinkerforge.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.rapidpm.demo.jaxenter.blog0012.teil2.Barometer;

import java.io.IOException;
import java.util.Date;

/**
* Created by Sven Ruppert on 15.01.14.
*/
public class Altitude implements Runnable {

    private String UID;
    private ObservableList seriesData;

    public Altitude(final String UID, final XYChart.Series series) {
        this.UID = UID;
        this.seriesData = series.getData();
    }

    @Override
    public void run() {
        IPConnection ipcon = new IPConnection();
        BrickletBarometer b = new BrickletBarometer(UID, ipcon);

        try {
            ipcon.connect(Barometer.host, Barometer.port);
            b.setAirPressureCallbackPeriod(1000);
            b.addAltitudeListener(new BrickletBarometer.AltitudeListener() {
                public void altitude(int altitude) {
                    System.out.println("Altitude: " + altitude / 100.0 + " m");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            final double temp = altitude / 100.0;
                            final XYChart.Data data = new XYChart.Data(new Date(), temp);
                            seriesData.add(data);
                        }
                    });
                }
            });
        } catch (IOException | AlreadyConnectedException | TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
