package gnnt.MEBS.common.front.statictools.filetools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLWork
{
  private static final String encoding = "GBK";
  
  public static Object reader(Class paramClass, String paramString)
  {
    Object localObject = null;
    try
    {
      localObject = Class.forName(paramClass.getName()).newInstance();
      JAXBContext localJAXBContext = JAXBContext.newInstance(new Class[] { localObject.getClass() });
      ByteArrayInputStream localByteArrayInputStream = null;
      try
      {
        localByteArrayInputStream = new ByteArrayInputStream(paramString.getBytes("GBK"));
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        localUnsupportedEncodingException.printStackTrace();
      }
      Unmarshaller localUnmarshaller = localJAXBContext.createUnmarshaller();
      localObject = localUnmarshaller.unmarshal(localByteArrayInputStream);
    }
    catch (JAXBException localJAXBException)
    {
      localJAXBException.printStackTrace();
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    return localObject;
  }
  
  public static String writer(Object paramObject)
  {
    String str = null;
    JAXBContext localJAXBContext = null;
    try
    {
      localJAXBContext = JAXBContext.newInstance(new Class[] { paramObject.getClass() });
      Marshaller localMarshaller = localJAXBContext.createMarshaller();
      localMarshaller.setProperty("jaxb.encoding", "GBK");
      StringWriter localStringWriter = new StringWriter();
      localMarshaller.marshal(paramObject, localStringWriter);
      str = localStringWriter.toString();
    }
    catch (JAXBException localJAXBException)
    {
      localJAXBException.printStackTrace();
    }
    return str;
  }
  
  public static String readXMLFile(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    BufferedReader localBufferedReader = null;
    try
    {
      localBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(paramString)));
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
    if (localBufferedReader != null) {
      try
      {
        for (String str = localBufferedReader.readLine(); str != null; str = localBufferedReader.readLine()) {
          localStringBuilder.append(str.trim());
        }
        return localStringBuilder.toString();
      }
      catch (Exception localException)
      {
        return "";
      }
    }
    return "";
  }
}
