package gnnt.trade.bank.vo;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class AddXml
{
  private String charset = "GB2312";
  private String url;
  private Document doc;
  
  public static void main(String[] args)
  {
    try
    {
      Map<String, String> map = new HashMap();
      Map<String, String> map2 = new HashMap();
      map2.put("null", " ");
      map.put("name", "lzx");
      map.put("sex", "man");
      map.put("height", "179cm");
      map.put("weight", "85kg");
      AddXml xml = new AddXml("mywork.xml", "GNNT");
      List<Integer> father = new ArrayList();
      father.add(Integer.valueOf(2));
      xml.mkXML("gb2312");
      xml.setChildElements("gnnt.piple.words", map);
      xml.setChildElements("gnnt.piple.words", map);
      xml.setChildElements("gnnt.piple.words", map);
      xml.setChildElements("gnnt.piple.words", map);
      xml.setChildElements(father, map);
      xml.setChildElements(father, map2);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public AddXml(String url, String rootElement)
  {
    this.doc = new DocumentImpl();
    this.url = url;
    setRoot(rootElement);
  }
  
  public AddXml(String url, String rootElement, String charset)
  {
    this.doc = new DocumentImpl();
    this.url = url;
    setRoot(rootElement);
    try
    {
      mkXML(charset);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void mkXML(String charset)
    throws IOException
  {
    if ((charset != null) && (charset.trim().length() > 0)) {
      this.charset = charset.trim();
    }
    try
    {
      OutputFormat outputFormat = new OutputFormat("XML", this.charset, true);
      FileWriter fileWriter = new FileWriter(new File(this.url));
      XMLSerializer xmlSerializer = new XMLSerializer(fileWriter, outputFormat);
      xmlSerializer.asDOMSerializer();
      xmlSerializer.serialize(this.doc.getDocumentElement());
    }
    catch (IOException e)
    {
      e.printStackTrace();
      throw e;
    }
  }
  
  public void setChildElement(String father, String key, String value)
    throws Exception
  {
    Element root = this.doc.getDocumentElement();
    if ((father == null) || (father.trim().length() <= 0)) {
      father = root.getNodeName();
    }
    String[] strs = father.split("\\.");
    Element finalElement = getElement(key, value);
    for (int i = strs.length - 1; i > 0; i--) {
      finalElement = getElement(strs[i], finalElement);
    }
    if (!strs[0].equalsIgnoreCase(root.getNodeName())) {
      finalElement = getElement(strs[0], finalElement);
    }
    root.appendChild(finalElement);
    writeInFile();
  }
  
  public void setChildElements(String father, Map<String, String> map)
    throws Exception
  {
    Element root = this.doc.getDocumentElement();
    if ((father == null) || (father.trim().length() <= 0)) {
      father = root.getNodeName();
    }
    String[] strs = father.split("\\.");
    String[][] elements = chickMap(map);
    if (elements != null)
    {
      if (strs.length > 1)
      {
        Element finalElement = this.doc.createElement(strs[(strs.length - 1)]);
        for (int i = 0; i < elements.length; i++)
        {
          Element pageElement = getElement(elements[i][0], elements[i][1]);
          finalElement.appendChild(pageElement);
        }
        for (int i = strs.length - 2; i > 0; i--) {
          finalElement = getElement(strs[i], finalElement);
        }
        if (!strs[0].equalsIgnoreCase(root.getNodeName())) {
          finalElement = getElement(strs[0], finalElement);
        }
        root.normalize();
        root.appendChild(finalElement);
      }
      else if (strs.length == 1)
      {
        if (!strs[0].equalsIgnoreCase(root.getNodeName()))
        {
          Element finalElement = this.doc.createElement(strs[0]);
          for (int i = 0; i < elements.length; i++)
          {
            Element pageElement = getElement(elements[i][0], elements[i][1]);
            finalElement.appendChild(pageElement);
          }
          root.normalize();
          root.appendChild(finalElement);
        }
        else
        {
          for (int i = 0; i < elements.length; i++)
          {
            Element pageElement = getElement(elements[i][0], elements[i][1]);
            root.normalize();
            root.appendChild(pageElement);
          }
        }
      }
      writeInFile();
    }
  }
  
  public void setChildElements(List<Integer> father, Map<String, String> map)
    throws Exception
  {
    Element root = getFarthor(father);
    String[][] elements = chickMap(map);
    if (elements != null)
    {
      for (int i = 0; i < elements.length; i++)
      {
        Element pageElement = getElement(elements[i][0], elements[i][1]);
        root.appendChild(pageElement);
      }
      writeInFile();
    }
  }
  
  public Element setChildElements(String father, String array, Map<String, String> map)
    throws Exception
  {
    Element root = getFarthor(father);
    String[][] elements = chickMap(map);
    Element ele = this.doc.createElement(array);
    if (elements != null) {
      for (int i = 0; i < elements.length; i++)
      {
        Element pageElement = getElement(elements[i][0], elements[i][1]);
        ele.appendChild(pageElement);
      }
    }
    root.appendChild(ele);
    writeInFile();
    return ele;
  }
  
  public Element setChildElements(Element element, String array, Map<String, String> map)
    throws Exception
  {
    String[][] elements = chickMap(map);
    Element ele = this.doc.createElement(array);
    if (elements != null) {
      for (int i = 0; i < elements.length; i++)
      {
        Element pageElement = getElement(elements[i][0], elements[i][1]);
        ele.appendChild(pageElement);
      }
    }
    element.appendChild(ele);
    writeInFile();
    return ele;
  }
  
  public void writeInFile()
    throws TransformerConfigurationException, TransformerException
  {
    try
    {
      TransformerFactory tFactory = TransformerFactory.newInstance();
      Transformer transformer = tFactory.newTransformer();
      
      transformer.setOutputProperty("encoding", this.charset);
      DOMSource source = new DOMSource(this.doc);
      StreamResult result = new StreamResult(this.url);
      transformer.transform(source, result);
    }
    catch (TransformerConfigurationException e)
    {
      e.printStackTrace();
      throw e;
    }
    catch (TransformerException e)
    {
      e.printStackTrace();
      throw e;
    }
  }
  
  private void setRoot(String rootElement)
  {
    Element root = this.doc.createElement(rootElement);
    this.doc.appendChild(root);
  }
  
  private Element getElement(String key, Element value)
  {
    if ((key == null) || (key.trim().length() <= 0)) {
      key = "Error";
    }
    Element result = this.doc.createElement(key);
    if (value == null)
    {
      Text text = this.doc.createTextNode("Error");
      result.appendChild(text);
    }
    else
    {
      result.appendChild(value);
    }
    return result;
  }
  
  private Element getElement(String key, String value)
  {
    if ((key == null) || (key.trim().length() <= 0)) {
      key = "Error";
    }
    if (value == null) {
      value = "";
    }
    Element result = this.doc.createElement(key);
    Text text = this.doc.createTextNode(value);
    result.appendChild(text);
    return result;
  }
  
  private String[][] chickMap(Map<String, String> myMap)
  {
    String[][] result = (String[][])null;
    if (myMap == null) {
      return new String[2][];
    }
    Set<String> set = myMap.keySet();
    int num = set.size();
    result = new String[num][2];
    int i = 0;
    for (Iterator<String> it = set.iterator(); it.hasNext();)
    {
      String key = (String)it.next();
      String value = (String)myMap.get(key);
      result[i][0] = key;
      result[(i++)][1] = value;
    }
    return result;
  }
  
  private Element getFarthor(List<Integer> father)
    throws Exception
  {
    Element result = this.doc.getDocumentElement();
    if ((father == null) || (father.size() <= 0)) {
      return result;
    }
    try
    {
      for (int i = 0; i < father.size(); i++)
      {
        System.out.print(i);
        NodeList nl = result.getChildNodes();
        int n = ((Integer)father.get(i)).intValue();
        if (n < 1) {
          n = 1;
        }
        System.out.print(" " + n + " " + nl.getLength());
        if ((nl.getLength() <= 0) || (nl.getLength() < n - 1)) {
          return result;
        }
        result = (Element)nl.item(n - 1);
        System.out.println();
      }
    }
    catch (Exception e)
    {
      System.out.println();
      e.printStackTrace();
    }
    return result;
  }
  
  private Element getFarthor(String father)
    throws Exception
  {
    Element result = this.doc.getDocumentElement();
    if ((father == null) || (father.trim().length() <= 0)) {
      return result;
    }
    String[] strs = father.split("\\.");
    for (int i = 0; i < strs.length; i++)
    {
      NodeList list = result.getChildNodes();
      int j = 0;
      boolean flag = true;
      while ((flag) && (list.getLength() > j))
      {
        Element ele = (Element)list.item(j++);
        if (ele.getNodeName().equalsIgnoreCase(strs[i]))
        {
          flag = false;
          result = ele;
        }
      }
    }
    return result;
  }
}
