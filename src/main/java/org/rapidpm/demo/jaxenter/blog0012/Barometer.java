package org.rapidpm.demo.jaxenter.blog0012;

import com.tinkerforge.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Sven Ruppert on 13.01.14.
 */
public class Barometer extends Application {
    private static final String host = "localhost";
    private static final int port = 4223;


    public static void main(String args[]) throws Exception {
        launch(args);
    }


    public static XYChart.Series seriesTemp = new XYChart.Series();
    public static XYChart.Series seriesLuftdruck = new XYChart.Series();
    public static XYChart.Series seriesAltitude = new XYChart.Series();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Line Chart TinkerForge Sample");

        final VBox box = new VBox();
        seriesTemp.setName("Temp");
        seriesLuftdruck.setName("Luftdruck");
        seriesAltitude.setName("Altitude");

        box.getChildren().add(createLineChart("Temp",seriesTemp));
        box.getChildren().add(createLineChart("Luftdruck", seriesLuftdruck));
        box.getChildren().add(createLineChart("Altitude",seriesAltitude));

        Scene scene = new Scene(box, 2000, 1500);

        stage.setScene(scene);
        stage.show();
        Platform.runLater(new Temp("dXj", seriesTemp));
        Platform.runLater(new Luftdruck("jY4", seriesLuftdruck));
        Platform.runLater(new Altitude("jY4", seriesAltitude));

    }


    private LineChart createLineChart(final String chartName,final XYChart.Series series ){
        final DateAxis dateAxis = new DateAxis();
        dateAxis.setLabel("Time");
        final NumberAxis yAxis = new NumberAxis();

        final LineChart<Date, Number> lineChart = new LineChart<>(dateAxis, yAxis);
        lineChart.setTitle(chartName);
        lineChart.getData().add(series);


        return lineChart;
    }


    public static class Temp implements Runnable {

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
                ipcon.connect(host, port);
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

    public static class Luftdruck implements Runnable {

        private String UID;
        private ObservableList seriesData;

        public Luftdruck(final String UID, final XYChart.Series series) {
            this.UID = UID;
            this.seriesData = series.getData();
        }

        @Override
        public void run() {
            IPConnection ipcon = new IPConnection();
            BrickletBarometer b = new BrickletBarometer(UID, ipcon);

            try {
                ipcon.connect(host, port);
                b.setAirPressureCallbackPeriod(1000);
                b.addAirPressureListener(new BrickletBarometer.AirPressureListener() {
                    public void airPressure(int airPressure) {
                        System.out.println("Air Pressure: " + airPressure / 1000.0 + " mbar");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                final double temp = airPressure / 1000.0 / 1000.0; //plus scale
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

    public static class Altitude implements Runnable {

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
                ipcon.connect(host, port);
                b.setAirPressureCallbackPeriod(1000);
                b.addAltitudeListener(new BrickletBarometer.AltitudeListener() {
                    public void altitude(int altitude) {
                        System.out.println("Altitude: " + altitude / 100.0 + " m");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                final double temp = altitude / 100.0 / 100.0; // scale
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




}
