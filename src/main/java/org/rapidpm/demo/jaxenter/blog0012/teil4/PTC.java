package org.rapidpm.demo.jaxenter.blog0012.teil4;

import com.tinkerforge.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.rapidpm.demo.jaxenter.blog0012.Localhost;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Sven Ruppert on 01.02.14.
 */
public class PTC implements Runnable {

    private String UID;
    private ObservableList seriesData;
    private SevenSegment sevenSegment = new SevenSegment("iW3");

    public PTC(final String UID, final XYChart.Series series) {
        this.UID = UID;
        this.seriesData = series.getData();
    }

    @Override
    public void run() {
        IPConnection ipcon = new IPConnection();
        BrickletPTC ptc = new BrickletPTC(UID, ipcon);
        try {
            ipcon.connect(Localhost.HOST, Localhost.PORT);
            ptc.setTemperatureCallbackPeriod(1000);
            ptc.addTemperatureListener(new BrickletPTC.TemperatureListener() {
                @Override
                public void temperature(int temperature) {
                    //schreibe temp in 7Seg
                    final double celcius = temperature / 100.0;
                    System.out.println("celcius = " + celcius);

                    Platform.runLater(()->{
                        final XYChart.Data data = new XYChart.Data(new Date(), celcius);
                        seriesData.add(data);
                    });
                    sevenSegment.printValue(celcius);

                }
            });
        } catch (IOException | AlreadyConnectedException | TimeoutException | NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
