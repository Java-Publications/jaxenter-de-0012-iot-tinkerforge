package org.rapidpm.demo.jaxenter.blog0012.teil7;

import org.rapidpm.module.iot.tinkerforge.actor.LCD20x4;
import org.rapidpm.module.iot.tinkerforge.gui.cml.WaitForQ;
import org.rapidpm.module.iot.tinkerforge.sensor.Barometer;
import org.rapidpm.module.iot.tinkerforge.sensor.Humidity;
import org.rapidpm.module.iot.tinkerforge.sensor.Light;
import org.rapidpm.module.iot.tinkerforge.sensor.Temperature;

import java.time.LocalDateTime;

/**
 * Created by Sven Ruppert on 22.04.2014.
 */
public class WeatherstationCMDLine {
  public static final String HOST = "192.168.0.200";  //wetterstation
  public static final int PORT = 4223;
  private static int callbackPeriod = 5000;

  private static final LCD20x4 lcd20x4 = new LCD20x4("jvX", "192.168.0.202", PORT);


  public static void main(String args[]) throws Exception {
    final Temperature temperature = new Temperature("dXj", callbackPeriod, PORT, HOST);
    temperature.bricklet.addTemperatureListener(sensorvalue -> {
      final double tempNorm = sensorvalue / 100.0;
      final String text = LocalDateTime.now() + " - Temp  : " + tempNorm + " °C";
      lcd20x4.printLine0(text);
      System.out.println("text = " + text);
    });

//    new Thread(temperature).start();
    temperature.run();

    final Barometer barometer = new Barometer("jY4", callbackPeriod, PORT, HOST);
    barometer.bricklet.addAirPressureListener(sensorvalue -> {
      final String text = LocalDateTime.now() + " - Air   : " + sensorvalue / 1000.0 + " mbar";
      lcd20x4.printLine1(text);
      System.out.println("text = " + text);
    });
//    new Thread(barometer).start();
    barometer.run();

    final Light light = new Light("jy2", callbackPeriod, PORT, HOST);
    light.bricklet.addIlluminanceListener(sensorvalue -> {
      final double lux = sensorvalue / 10.0;
      final String text = LocalDateTime.now() + " - Lux   : " + lux + " Lux";
      lcd20x4.printLine3(text);
      System.out.println("text = " + text);
    });
//    new Thread(light).start();
    light.run();

    final Humidity humidity = new Humidity("kfd", callbackPeriod, PORT, HOST);
    humidity.bricklet.addHumidityListener(sensorvalue -> {
      final double tempNorm = sensorvalue / 10.0;
      final String text = LocalDateTime.now() + " - RelHum: " + tempNorm + " %RH";
      lcd20x4.printLine2(text);
      System.out.println("text = " + text);
    });
//    new Thread(humidity).start();
    humidity.run();

    WaitForQ.waitForQ();
  }





}
