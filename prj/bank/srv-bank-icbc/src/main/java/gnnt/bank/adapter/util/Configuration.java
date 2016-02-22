package gnnt.bank.adapter.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
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

  public Configuration(String path)
  {
    synchronized (xmlConfigA)
    {
      this.cPath = path;
      if (!initFlag)
      {
        if (resetConfigInfo() == 0)
        {
          initFlag = true;
        }
      }
    }
  }

  public Hashtable getConfigInfo()
  {
    return configInfo;
  }

  public Properties getSection(String sectionPath)
  {
    this.propertyInfo = ((Properties)configInfo.get(sectionPath));
    return this.propertyInfo;
  }

  public int resetConfigInfo()
  {
    synchronized (xmlConfigB)
    {
      int IsParseT = 10;

      IsParseT = parseXML();

      if (IsParseT != 0)
      {
        return 1;
      }
      return 0;
    }
  }

  protected int parseXML()
  {
    Document doc = null;
    try
    {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      if ((this.cPath == null) || (this.cPath.equals("")))
      {
        System.out.println("ERROR:File .xml is null or not equals any value");
        return 1;
      }

      File f = new File(this.cPath);
      doc = db.parse(f);
      NodeList comp = null;

      if (!this.ConfigFlag)
      {
        NodeList conf = doc.getChildNodes();
        for (int i = 0; i < conf.getLength(); i++)
        {
          if (!conf.item(0).getNodeName().toUpperCase().equals("CONFIG"))
          {
            System.out.println("ERROR:Some tags are in wrong location with CONFIG in " + this.cPath);
            return 1;
          }
        }
        this.ConfigFlag = true;
        comp = conf.item(0).getChildNodes();
      }
      else
      {
        comp = doc.getChildNodes();
      }

      for (int j = 0; j < comp.getLength(); j++)
      {
        if (comp.item(j).getNodeName().toUpperCase().equals("COMPONENT"))
        {
          NodeList children = comp.item(j).getChildNodes();

          String name = null;

          String type = null;

          boolean IsNamed = false;

          boolean IsTyped = false;

          for (int k = 0; k < children.getLength(); k++)
          {
            String tagName = children.item(k).getNodeName();

            if (tagName.toUpperCase().equals("NAME"))
            {
              if (IsNamed)
              {
                System.out.println("ERROR:The name appeared twice in " + this.cPath);
                return 1;
              }
              name = children.item(k).getFirstChild().getNodeValue();

              if (this.CompName != null)
              {
                name = this.CompName + "." + name;
              }
              IsNamed = true;
            }
            else if (tagName.toUpperCase().equals("TYPE"))
            {
              if (IsTyped)
              {
                System.out.println("ERROR:The type appeared twice in " + this.cPath);
                return 1;
              }
              type = children.item(k).getFirstChild().getNodeValue();

              if (type.toLowerCase().equals("link"))
              {
                if (name == null)
                {
                  System.out.println("ERROR:The link filename is not assigned");
                  return 1;
                }

                this.cPath = (vPathToFPath() + "\\" + name);

                if (parseXML() != 0)
                {
                  System.out.println("ERROR:The link " + name + " in " + this.cPath + " is found wrong");
                  return 1;
                }
              }
              IsTyped = true;
            }
            else if (tagName.toUpperCase().equals("SECTION"))
            {
              if ((name == null) || (type == null))
              {
                System.out.println("ERROR:The component's name is not known in " + this.cPath);
                return 1;
              }
              NodeList sec = children.item(k).getChildNodes();

              String sname = null;

              Properties pr = new Properties();
              for (int n = 0; n < sec.getLength(); n++)
              {
                String secName = sec.item(n).getNodeName();

                if (secName.toUpperCase().equals("NAME"))
                {
                  sname = name + "." + sec.item(n).getFirstChild().getNodeValue();
                }
                else if (secName.toUpperCase().equals("PROPERTYS"))
                {
                  NodeList prop = sec.item(n).getChildNodes();
                  for (int m = 0; m < prop.getLength(); m++)
                  {
                    String pname = prop.item(m).getNodeName();
                    if (pname.toUpperCase().equals("PROPERTY"))
                    {
                      NodeList kv = prop.item(m).getChildNodes();
                      String key = null;
                      String value = null;
                      for (int l = 0; l < kv.getLength(); l++)
                      {
                        String kname = kv.item(l).getNodeName();

                        if (kname.toUpperCase().equals("KEY"))
                        {
                          key = kv.item(l).getFirstChild().getNodeValue();
                        }
                        else if (kname.toUpperCase().equals("VALUE"))
                        {
                          value = kv.item(l).getFirstChild().getNodeValue();
                        }
                      }
                      if ((key == null) || (value == null))
                      {
                        System.out.println("ERROR:Section " + sname + " has null key or null value in " + this.cPath);
                        return 1;
                      }
                      try
                      {
                        pr.setProperty(key, value);
                      }
                      catch (NullPointerException e)
                      {
                        System.out.println("ERROR:When you set the property " + key + " in section " + sname + " in " + this.cPath);
                      }
                    }
                  }
                }
              }

              try
              {
                configInfo.put(sname, pr);
              }
              catch (NullPointerException e)
              {
                System.out.println("ERROR:When you set the section " + sname + " into the hashtable in " + this.cPath);
              }

            }
            else if (tagName.toUpperCase().equals("COMPONENT"))
            {
              int pc = parseComponent(name, children.item(k).getChildNodes());
              if (pc != 0)
              {
                System.out.println("ERROR:False is in the inner component in " + this.cPath);
                return pc;
              }
            }
          }
        }
      }
    }
    catch (FileNotFoundException e)
    {
      System.out.println("ERROR:File " + this.cPath + " not found");
      return 1;
    }
    catch (IOException e)
    {
      System.out.println("ERROR:There is something wrong with inputStream");
      return 1;
    }
    catch (ParserConfigurationException e)
    {
      System.out.println("ERROR:There is something wrong with Parsing xml");
      return 1;
    }
    catch (SAXException e)
    {
      System.out.println("ERROR:There is something wrong with Parsing xml");
      return 1;
    }
    return 0;
  }

  protected int parseComponent(String cname, NodeList children)
  {
    String name = null;
    String type = null;

    String linkName = null;

    boolean IsNamed = false;

    boolean IsTyped = false;

    for (int k = 0; k < children.getLength(); k++)
    {
      String tagName = children.item(k).getNodeName();
      if (tagName.toUpperCase().equals("NAME"))
      {
        if (IsNamed)
        {
          System.out.println("ERROR:The name appeared twice in " + this.cPath);
          return 1;
        }
        name = cname + "." + children.item(k).getFirstChild().getNodeValue();
        linkName = children.item(k).getFirstChild().getNodeValue();
        IsNamed = true;
      }
      else if (tagName.toUpperCase().equals("TYPE"))
      {
        if (IsTyped)
        {
          System.out.println("ERROR:The type appeared twice in " + this.cPath);
          return 1;
        }
        type = children.item(k).getFirstChild().getNodeValue();

        if (type.toLowerCase().equals("link"))
        {
          if (linkName == null)
          {
            System.out.println("ERROR:The link filename is not assigned in " + this.cPath);
            return 1;
          }

          this.cPath = (vPathToFPath() + "\\" + linkName);
          name = cname;
          this.CompName = name;
          if (parseXML() != 0)
          {
            System.out.println("ERROR:The link " + name + " in " + this.cPath + " is found wrong");
            return 1;
          }
        }
        IsTyped = true;
      }
      else if (tagName.toUpperCase().equals("SECTION"))
      {
        if ((name == null) || (type == null))
        {
          System.out.println("ERROR:The component's name is not known in " + this.cPath);
          return 1;
        }
        NodeList sec = children.item(k).getChildNodes();
        String sname = null;
        Properties pr = new Properties();
        for (int n = 0; n < sec.getLength(); n++)
        {
          String secName = sec.item(n).getNodeName();

          if (secName.toUpperCase().equals("NAME"))
          {
            sname = name + "." + sec.item(n).getFirstChild().getNodeValue();
          }
          else if (secName.toUpperCase().equals("PROPERTYS"))
          {
            NodeList prop = sec.item(n).getChildNodes();
            for (int m = 0; m < prop.getLength(); m++)
            {
              String pname = prop.item(m).getNodeName();
              if (pname.toUpperCase().equals("PROPERTY"))
              {
                NodeList kv = prop.item(m).getChildNodes();
                String key = null;
                String value = null;
                for (int l = 0; l < kv.getLength(); l++)
                {
                  String kname = kv.item(l).getNodeName();

                  if (kname.toUpperCase().equals("KEY"))
                  {
                    key = kv.item(l).getFirstChild().getNodeValue();
                  }
                  else if (kname.toUpperCase().equals("VALUE"))
                  {
                    value = kv.item(l).getFirstChild().getNodeValue();
                  }
                }
                if ((key == null) || (value == null))
                {
                  System.out.println("ERROR:Section " + sname + " has null key or null value in " + this.cPath);
                  return 1;
                }
                try
                {
                  pr.setProperty(key, value);
                }
                catch (NullPointerException e)
                {
                  System.out.println("ERROR:When you set the property " + key + " in section " + sname + " in " + this.cPath);
                  return 1;
                }
              }
            }
          }
        }

        try
        {
          configInfo.put(sname, pr);
        }
        catch (NullPointerException e)
        {
          System.out.println("ERROR:When you set the section " + sname + " into the hashtable" + " in " + this.cPath);
          return 1;
        }

      }
      else if (tagName.toUpperCase().equals("COMPONENT"))
      {
        int pc = parseComponent(name, children.item(k).getChildNodes());
        if (pc != 0)
        {
          System.out.println("ERROR:False is in the inner component in " + this.cPath);
          return pc;
        }
      }
    }
    return 0;
  }

  private String vPathToFPath()
  {
    int a = this.cPath.lastIndexOf("\\");
    this.cPath = this.cPath.substring(0, a);
    return this.cPath;
  }

  private static String getDefaultPath()
  {
    Properties pr = new Properties();
    try
    {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();

      InputStream fin = loader.getResourceAsStream("gnnt.properties");

      if (fin != null)
      {
        pr.load(fin);
        fin.close();
      }
      else
      {
        System.out.println("ERROR:Properties can not be found");
      }

    }
    catch (IOException e)
    {
      System.out.println("ERROR:Wrong when gnnt.properties loading or closing");
    }
    String configPath = pr.getProperty("configPath");

    return configPath;
  }
}