package org.rapidpm.demo.jaxenter.blog0012.teil4;

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

import java.util.Date;

/**
 * Created by Sven Ruppert on 31.01.14.
 */
public class PTCMaster extends Application {

    public static XYChart.Series seriesTemp = new XYChart.Series();

    public static void main(String args[]) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Line Chart TinkerForge Sample");

        final VBox box = new VBox();
        seriesTemp.setName("Temp");
        final ObservableList<Node> boxChildren = box.getChildren();
        boxChildren.add(createLineChart("Temp", seriesTemp));
        Scene scene = new Scene(box);

        stage.setScene(scene);
        stage.show();
        Platform.runLater(new PTC("i2J", seriesTemp));

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
