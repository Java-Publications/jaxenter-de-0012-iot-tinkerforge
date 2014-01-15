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
public class Temp implements Runnable {

    private String UID;
    private ObservableList seriesData;

    public Temp(final String UID, final XYChart.Series series) {
        this.UID = UID;
        this.seriesData = series.getData();
    }

    @Override
    public void run() {
        IPConnection ipcon = new IPConnection();
        BrickletTemperature temp = new BrickletTemperature(UID, ipcon);

        try {
            ipcon.connect(Barometer.host, Barometer.port);
            temp.setTemperatureCallbackPeriod(1000);
            temp.addTemperatureListener(new BrickletTemperature.TemperatureListener() {
                public void temperature(short temperature) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            final double temp = temperature / 100.0;
                            System.out.println("Temperature: " + temp + " °C");
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
