package gnnt.MEBS.timebargain.broker.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RWProperty
{
  public static String readValue(String paramString1, String paramString2)
  {
    Log localLog = LogFactory.getLog(RWProperty.class);
    Properties localProperties = new Properties();
    File localFile = new File(paramString1);
    if (!localFile.exists())
      paramString1 = RWProperty.class.getResource("/").getPath() + paramString1;
    try
    {
      localLog.debug("--------filePath:" + paramString1);
      FileInputStream localFileInputStream = new FileInputStream(paramString1);
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(localFileInputStream);
      localProperties.load(localBufferedInputStream);
      String str = localProperties.getProperty(paramString2);
      return str;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localLog.debug("属性文件未找到" + localFileNotFoundException.getMessage());
      return null;
    }
    catch (IOException localIOException)
    {
      localLog.debug("IO异常" + localIOException.getMessage());
    }
    return null;
  }

  public static Hashtable readProperties(String paramString)
  {
    Hashtable localHashtable = new Hashtable();
    Properties localProperties = new Properties();
    File localFile = new File(paramString);
    if (!localFile.exists())
      paramString = RWProperty.class.getResource("/").getPath() + paramString;
    try
    {
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramString));
      localProperties.load(localBufferedInputStream);
      Enumeration localEnumeration = localProperties.propertyNames();
      while (localEnumeration.hasMoreElements())
      {
        String str1 = (String)localEnumeration.nextElement();
        String str2 = localProperties.getProperty(str1);
        try
        {
          localHashtable.put(str1, str2);
        }
        catch (NullPointerException localNullPointerException)
        {
          Log localLog = LogFactory.getLog(RWProperty.class);
          localLog.debug("ERROR:When you set the section " + str1 + " into the hashtable in " + str2);
        }
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localHashtable;
  }

  public static void writeProperties(String paramString1, String paramString2, String paramString3)
  {
    Log localLog = LogFactory.getLog(RWProperty.class);
    Properties localProperties = new Properties();
    File localFile = new File(paramString1);
    if (!localFile.exists())
      paramString1 = RWProperty.class.getResource("/").getPath() + paramString1;
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(paramString1);
      localProperties.load(localFileInputStream);
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString1);
      localProperties.setProperty(paramString2, paramString3);
      localProperties.store(localFileOutputStream, "Update '" + paramString2 + "' value");
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localLog.debug("属性文件未找到" + localFileNotFoundException.getMessage());
    }
    catch (IOException localIOException)
    {
      localLog.debug("写属性文件异常" + localIOException.getMessage());
    }
  }
}