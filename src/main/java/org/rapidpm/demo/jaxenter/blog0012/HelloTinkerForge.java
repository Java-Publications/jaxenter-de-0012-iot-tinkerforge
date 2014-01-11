package org.rapidpm.demo.jaxenter.blog0012;

import com.tinkerforge.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Sven Ruppert on 10.01.14.
 */
public class HelloTinkerForge extends Application {

    private static final String host = "localhost";
    private static final int port = 4223;
    //    private static final String UID = "6Dct25"; // Change to your UID
    private static final String UID = "dXj"; // Change to your UID


    public static void main(String args[]) throws Exception {
        launch(args);
    }

    public static XYChart.Series series;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Line Chart TinkerForge Sample");
        final DateAxis dateAxis = new DateAxis();
        final NumberAxis yAxis = new NumberAxis();
        dateAxis.setLabel("Time of Temp");
        final LineChart<Date, Number> lineChart = new LineChart<>(dateAxis, yAxis);

        lineChart.setTitle("Temp Monitoring");

        series = new XYChart.Series();
        series.setName("My temps");
        final ObservableList seriesData = series.getData();

        lineChart.getData().add(series);
        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.show();
        new Worker(seriesData).start();

    }

    public static class Worker extends Thread {
        final ObservableList seriesData;
        public Worker(final ObservableList seriesData) {
            setDaemon(true);
            setName("Thread Temp");
            this.seriesData = seriesData;
        }

        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    IPConnection ipcon = new IPConnection();
                    BrickletTemperature temp = new BrickletTemperature(UID, ipcon);

                    try {
                        ipcon.connect(host, port);
                        temp.setTemperatureCallbackPeriod(1000);
                        temp.addTemperatureListener(new BrickletTemperature.TemperatureListener() {
                            public void temperature(short temperature) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        final double temp = temperature / 100.0;
                                        System.out.println("Temperature: "+temp+" °C");
                                        final int counter = seriesData.size() + 1;
                                        System.out.println("counter = "+counter);
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
            });
        }
    }
}

