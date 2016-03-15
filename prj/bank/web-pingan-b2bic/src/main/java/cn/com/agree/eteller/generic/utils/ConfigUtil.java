package cn.com.agree.eteller.generic.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

public class ConfigUtil
{
  private Properties prop;
  
  public ConfigUtil(String resource)
  {
    this.prop = new Properties();
    try
    {
      this.prop.load(getResource(resource));
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
  
  public ConfigUtil(Properties prop)
  {
    this.prop = prop;
  }
  
  public static InputStream getResource(String path)
  {
    InputStream in = ConfigUtil.class.getClassLoader().getResourceAsStream(path);
    return in;
  }
  
  public int getInt(String name)
  {
    return Integer.parseInt(this.prop.getProperty(name));
  }
  
  public String getString(String name)
  {
    return this.prop.getProperty(name);
  }
  
  public static void main(String[] args)
  {
    System.out.println(new ConfigUtil("config/pingan.properties").getString("signMode"));
  }
}
