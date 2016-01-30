package gnnt.MEBS.util.spring.rmi.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Properties;

public class RWPropertiesFile
{
  public static String readValue(String filePath, String key)
  {
    Properties props = new Properties();
    try {
      InputStream in = new BufferedInputStream(new FileInputStream(
        filePath));
      props.load(in);
      return props.getProperty(key);
    }
    catch (Exception e) {
      e.printStackTrace();
      System.err.println("RWPropertiesFileError" + e.toString());
    }return null;
  }

  public static void readProperties(String filePath)
  {
    Properties props = new Properties();
    try {
      InputStream in = new BufferedInputStream(new FileInputStream(
        filePath));
      props.load(in);
      Enumeration en = props.propertyNames();
      while (en.hasMoreElements()) {
        String key = (String)en.nextElement();
        String Property = props.getProperty(key);
        System.out.println(key + Property);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("RWPropertiesFileError" + e.toString());
    }
  }

  public static void writeProperties(String filePath, String parameterName, String parameterValue)
  {
    Properties prop = new Properties();
    try {
      InputStream fis = new FileInputStream(filePath);

      prop.load(fis);

      OutputStream fos = new FileOutputStream(filePath);
      prop.setProperty(parameterName, parameterValue);

      prop.store(fos, "Update '" + parameterName + "' value");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    String filePath = "E:\\timebargain\\SpringRMI\\bin\\rmi.properties";
    writeProperties(filePath, "espot.rmi.ip", "test1");
    System.out.println(
      readValue(filePath, "rmi.ip"));
  }
}