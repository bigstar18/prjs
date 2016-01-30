package gnnt.MEBS.timebargain.mgr.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RWProperty
{
  public static String readValue(String filePath, String key)
  {
    Log log = LogFactory.getLog(RWProperty.class);
    Properties props = new Properties();
    File file = new File(filePath);
    if (!file.exists())
      filePath = RWProperty.class.getResource("/").getPath() + filePath;
    try {
      log.debug("--------filePath:" + filePath);
      FileInputStream fileInputStream = new FileInputStream(filePath);
      InputStream ips = new BufferedInputStream(fileInputStream);
      props.load(ips);
      return props.getProperty(key);
    }
    catch (FileNotFoundException e) {
      log.debug("属性文件未找到" + e.getMessage());
      return null;
    } catch (IOException e) {
      log.debug("IO异常" + e.getMessage());
    }return null;
  }

  public static Hashtable readProperties(String filePath)
  {
    Hashtable configInfo = new Hashtable();
    Properties props = new Properties();
    File file = new File(filePath);
    if (!file.exists())
      filePath = RWProperty.class.getResource("/").getPath() + filePath;
    try
    {
      InputStream ips = new BufferedInputStream(new FileInputStream(filePath));
      props.load(ips);
      Enumeration enumeration = props.propertyNames();
      while (enumeration.hasMoreElements())
      {
        String key = (String)enumeration.nextElement();
        String value = props.getProperty(key);
        try
        {
          configInfo.put(key, value);
        }
        catch (NullPointerException e)
        {
          Log log = LogFactory.getLog(RWProperty.class);
          log.debug("ERROR:When you set the section " + key + 
            " into the hashtable in " + value);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return configInfo;
  }

  public static void writeProperties(String filePath, String paraKey, String paraValue) {
    Log log = LogFactory.getLog(RWProperty.class);
    Properties props = new Properties();
    File file = new File(filePath);
    if (!file.exists())
      filePath = RWProperty.class.getResource("/").getPath() + filePath;
    try
    {
      InputStream fis = new FileInputStream(filePath);

      props.load(fis);
      OutputStream ops = new FileOutputStream(filePath);
      props.setProperty(paraKey, paraValue);
      props.store(ops, "Update '" + paraKey + "' value");
    }
    catch (FileNotFoundException e) {
      log.debug("属性文件未找到" + e.getMessage());
    }
    catch (IOException e) {
      log.debug("写属性文件异常" + e.getMessage());
    }
  }
}