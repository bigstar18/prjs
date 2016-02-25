package gnnt.MEBS.common.security.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Hashtable;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Configuration
{
  private static boolean initFlag;
  private static Hashtable<String, Properties> configInfo = new Hashtable();
  private Properties propertyInfo;
  private String cPath;
  private boolean ConfigFlag;
  private String CompName;
  private static Object xmlConfigA = new Object();
  private static Object xmlConfigB = new Object();
  
  public Configuration()
  {
    this(getDefaultPath());
  }
  
  public Configuration(String paramString)
  {
    synchronized (xmlConfigA)
    {
      this.cPath = paramString;
      if ((!initFlag) && (resetConfigInfo() == 0)) {
        initFlag = true;
      }
    }
  }
  
  public Hashtable getConfigInfo()
  {
    return configInfo;
  }
  
  public Properties getSection(String paramString)
  {
    this.propertyInfo = ((Properties)configInfo.get(paramString));
    return this.propertyInfo;
  }
  
  public int resetConfigInfo()
  {
    synchronized (xmlConfigB)
    {
      int i = 10;
      i = parseXML();
      if (i != 0) {
        return 1;
      }
      return 0;
    }
  }
  
  protected int parseXML()
  {
    Document localDocument = null;
    try
    {
      DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
      if ((this.cPath == null) || (this.cPath.equals("")))
      {
        System.out.println("ERROR:File .xml is null or not equals any value");
        return 1;
      }
      File localFile = new File(this.cPath);
      localDocument = localDocumentBuilder.parse(localFile);
      NodeList localNodeList1 = null;
      if (!this.ConfigFlag)
      {
        NodeList localNodeList2 = localDocument.getChildNodes();
        for (int j = 0; j < localNodeList2.getLength(); j++) {
          if (!localNodeList2.item(0).getNodeName().toUpperCase().equals("CONFIG"))
          {
            System.out.println("ERROR:Some tags are in wrong location with CONFIG in " + this.cPath);
            return 1;
          }
        }
        this.ConfigFlag = true;
        localNodeList1 = localNodeList2.item(0).getChildNodes();
      }
      else
      {
        localNodeList1 = localDocument.getChildNodes();
      }
      for (int i = 0; i < localNodeList1.getLength(); i++) {
        if (localNodeList1.item(i).getNodeName().toUpperCase().equals("COMPONENT"))
        {
          NodeList localNodeList3 = localNodeList1.item(i).getChildNodes();
          String str1 = null;
          String str2 = null;
          int k = 0;
          int m = 0;
          for (int n = 0; n < localNodeList3.getLength(); n++)
          {
            String str3 = localNodeList3.item(n).getNodeName();
            if (str3.toUpperCase().equals("NAME"))
            {
              if (k != 0)
              {
                System.out.println("ERROR:The name appeared twice in " + this.cPath);
                return 1;
              }
              str1 = localNodeList3.item(n).getFirstChild().getNodeValue();
              if (this.CompName != null) {
                str1 = this.CompName + "." + str1;
              }
              k = 1;
            }
            else if (str3.toUpperCase().equals("TYPE"))
            {
              if (m != 0)
              {
                System.out.println("ERROR:The type appeared twice in " + this.cPath);
                return 1;
              }
              str2 = localNodeList3.item(n).getFirstChild().getNodeValue();
              if (str2.toLowerCase().equals("link"))
              {
                if (str1 == null)
                {
                  System.out.println("ERROR:The link filename is not assigned");
                  return 1;
                }
                this.cPath = (vPathToFPath() + "\\" + str1);
                if (parseXML() != 0)
                {
                  System.out.println("ERROR:The link " + str1 + " in " + this.cPath + " is found wrong");
                  return 1;
                }
              }
              m = 1;
            }
            else if (str3.toUpperCase().equals("SECTION"))
            {
              if ((str1 == null) || (str2 == null))
              {
                System.out.println("ERROR:The component's name is not known in " + this.cPath);
                return 1;
              }
              NodeList localNodeList4 = localNodeList3.item(n).getChildNodes();
              String str4 = null;
              Properties localProperties = new Properties();
              for (int i2 = 0; i2 < localNodeList4.getLength(); i2++)
              {
                String str5 = localNodeList4.item(i2).getNodeName();
                if (str5.toUpperCase().equals("NAME"))
                {
                  str4 = str1 + "." + localNodeList4.item(i2).getFirstChild().getNodeValue();
                }
                else if (str5.toUpperCase().equals("PROPERTYS"))
                {
                  NodeList localNodeList5 = localNodeList4.item(i2).getChildNodes();
                  for (int i3 = 0; i3 < localNodeList5.getLength(); i3++)
                  {
                    String str6 = localNodeList5.item(i3).getNodeName();
                    if (str6.toUpperCase().equals("PROPERTY"))
                    {
                      NodeList localNodeList6 = localNodeList5.item(i3).getChildNodes();
                      String str7 = null;
                      String str8 = null;
                      for (int i4 = 0; i4 < localNodeList6.getLength(); i4++)
                      {
                        String str9 = localNodeList6.item(i4).getNodeName();
                        if (str9.toUpperCase().equals("KEY")) {
                          str7 = localNodeList6.item(i4).getFirstChild().getNodeValue();
                        } else if (str9.toUpperCase().equals("VALUE")) {
                          str8 = localNodeList6.item(i4).getFirstChild().getNodeValue();
                        }
                      }
                      if ((str7 == null) || (str8 == null))
                      {
                        System.out.println("ERROR:Section " + str4 + " has null key or null value in " + this.cPath);
                        return 1;
                      }
                      try
                      {
                        localProperties.setProperty(str7, str8);
                      }
                      catch (NullPointerException localNullPointerException2)
                      {
                        System.out.println("ERROR:When you set the property " + str7 + " in section " + str4 + " in " + this.cPath);
                      }
                    }
                  }
                }
              }
              try
              {
                configInfo.put(str4, localProperties);
              }
              catch (NullPointerException localNullPointerException1)
              {
                System.out.println("ERROR:When you set the section " + str4 + " into the hashtable in " + this.cPath);
              }
            }
            else if (str3.toUpperCase().equals("COMPONENT"))
            {
              int i1 = parseComponent(str1, localNodeList3.item(n).getChildNodes());
              if (i1 != 0)
              {
                System.out.println("ERROR:False is in the inner component in " + this.cPath);
                return i1;
              }
            }
          }
        }
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      System.out.println("ERROR:File " + this.cPath + " not found");
      return 1;
    }
    catch (IOException localIOException)
    {
      System.out.println("ERROR:There is something wrong with inputStream");
      return 1;
    }
    catch (ParserConfigurationException localParserConfigurationException)
    {
      System.out.println("ERROR:There is something wrong with Parsing xml");
      return 1;
    }
    catch (SAXException localSAXException)
    {
      System.out.println("ERROR:There is something wrong with Parsing xml");
      return 1;
    }
    return 0;
  }
  
  protected int parseComponent(String paramString, NodeList paramNodeList)
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    int i = 0;
    int j = 0;
    for (int k = 0; k < paramNodeList.getLength(); k++)
    {
      String str4 = paramNodeList.item(k).getNodeName();
      if (str4.toUpperCase().equals("NAME"))
      {
        if (i != 0)
        {
          System.out.println("ERROR:The name appeared twice in " + this.cPath);
          return 1;
        }
        str1 = paramString + "." + paramNodeList.item(k).getFirstChild().getNodeValue();
        str3 = paramNodeList.item(k).getFirstChild().getNodeValue();
        i = 1;
      }
      else if (str4.toUpperCase().equals("TYPE"))
      {
        if (j != 0)
        {
          System.out.println("ERROR:The type appeared twice in " + this.cPath);
          return 1;
        }
        str2 = paramNodeList.item(k).getFirstChild().getNodeValue();
        if (str2.toLowerCase().equals("link"))
        {
          if (str3 == null)
          {
            System.out.println("ERROR:The link filename is not assigned in " + this.cPath);
            return 1;
          }
          this.cPath = (vPathToFPath() + "\\" + str3);
          str1 = paramString;
          this.CompName = str1;
          if (parseXML() != 0)
          {
            System.out.println("ERROR:The link " + str1 + " in " + this.cPath + " is found wrong");
            return 1;
          }
        }
        j = 1;
      }
      else if (str4.toUpperCase().equals("SECTION"))
      {
        if ((str1 == null) || (str2 == null))
        {
          System.out.println("ERROR:The component's name is not known in " + this.cPath);
          return 1;
        }
        NodeList localNodeList1 = paramNodeList.item(k).getChildNodes();
        String str5 = null;
        Properties localProperties = new Properties();
        for (int n = 0; n < localNodeList1.getLength(); n++)
        {
          String str6 = localNodeList1.item(n).getNodeName();
          if (str6.toUpperCase().equals("NAME"))
          {
            str5 = str1 + "." + localNodeList1.item(n).getFirstChild().getNodeValue();
          }
          else if (str6.toUpperCase().equals("PROPERTYS"))
          {
            NodeList localNodeList2 = localNodeList1.item(n).getChildNodes();
            for (int i1 = 0; i1 < localNodeList2.getLength(); i1++)
            {
              String str7 = localNodeList2.item(i1).getNodeName();
              if (str7.toUpperCase().equals("PROPERTY"))
              {
                NodeList localNodeList3 = localNodeList2.item(i1).getChildNodes();
                String str8 = null;
                String str9 = null;
                for (int i2 = 0; i2 < localNodeList3.getLength(); i2++)
                {
                  String str10 = localNodeList3.item(i2).getNodeName();
                  if (str10.toUpperCase().equals("KEY")) {
                    str8 = localNodeList3.item(i2).getFirstChild().getNodeValue();
                  } else if (str10.toUpperCase().equals("VALUE")) {
                    str9 = localNodeList3.item(i2).getFirstChild().getNodeValue();
                  }
                }
                if ((str8 == null) || (str9 == null))
                {
                  System.out.println("ERROR:Section " + str5 + " has null key or null value in " + this.cPath);
                  return 1;
                }
                try
                {
                  localProperties.setProperty(str8, str9);
                }
                catch (NullPointerException localNullPointerException2)
                {
                  System.out.println("ERROR:When you set the property " + str8 + " in section " + str5 + " in " + this.cPath);
                  return 1;
                }
              }
            }
          }
        }
        try
        {
          configInfo.put(str5, localProperties);
        }
        catch (NullPointerException localNullPointerException1)
        {
          System.out.println("ERROR:When you set the section " + str5 + " into the hashtable" + " in " + this.cPath);
          return 1;
        }
      }
      else if (str4.toUpperCase().equals("COMPONENT"))
      {
        int m = parseComponent(str1, paramNodeList.item(k).getChildNodes());
        if (m != 0)
        {
          System.out.println("ERROR:False is in the inner component in " + this.cPath);
          return m;
        }
      }
    }
    return 0;
  }
  
  private String vPathToFPath()
  {
    int i = this.cPath.lastIndexOf("\\");
    this.cPath = this.cPath.substring(0, i);
    return this.cPath;
  }
  
  private static String getDefaultPath()
  {
    String str = "";
    try
    {
      ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
      URL localURL = localClassLoader.getResource("mgr.xml");
      if (localURL == null) {
        throw new Exception("没有找到xml文件");
      }
      str = localURL.getPath();
      str = URLDecoder.decode(str, "UTF-8");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      System.out.println("ERROR:When you get default path");
    }
    return str;
  }
}
