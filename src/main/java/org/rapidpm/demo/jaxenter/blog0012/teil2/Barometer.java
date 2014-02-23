package org.rapidpm.demo.jaxenter.blog0012.teil2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.rapidpm.demo.jaxenter.blog0012.DateAxis;
import org.rapidpm.demo.jaxenter.blog0012.teil2.sensoren.Altitude;
import org.rapidpm.demo.jaxenter.blog0012.teil2.sensoren.Luftdruck;
import org.rapidpm.demo.jaxenter.blog0012.teil2.sensoren.Temp;

import java.util.Date;

/**
 * Created by Sven Ruppert on 13.01.14.
 */
public class Barometer extends Application {
    public static final String host = "192.168.0.200";
    public static final int port = 4223;


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

        final ObservableList<Node> boxChildren = box.getChildren();
        boxChildren.add(createLineChart("Temp", seriesTemp));
        boxChildren.add(createLineChart("Luftdruck", seriesLuftdruck));
        boxChildren.add(createLineChart("Altitude", seriesAltitude));

        Scene scene = new Scene(box, 2000, 1500);

        stage.setScene(scene);
        stage.show();
        Platform.runLater(new Temp("dXj", seriesTemp, 1000));
        Platform.runLater(new Luftdruck("jY4", seriesLuftdruck, 1000));
        Platform.runLater(new Altitude("jY4", seriesAltitude, 1000));

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
}
