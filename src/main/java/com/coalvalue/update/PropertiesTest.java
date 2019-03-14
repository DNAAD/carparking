package com.coalvalue.update;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {

  public static void main(String[] args)  {
    Properties props = new Properties();

    String propsFileName = "./src/myconfig.properties";
    try {
      //first load old one:
      FileInputStream configStream = new FileInputStream(propsFileName);
      props.load(configStream);
      configStream.close();

      //modifies existing or adds new property
      props.setProperty("connection", "new connection settings go here");
      props.setProperty("newProperty", "newValue");

      //save modified property file
      FileOutputStream output = new FileOutputStream(propsFileName);
      props.store(output, "This description goes to the header of a file");
      output.close();

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}