package gnnt.MEBS.base.copy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlToMap
{
  public static Map xmlToMap(String xml)
  {
    Map map = null;
    if ((xml != null) && (!"".equals(xml)))
    {
      Document doc = null;
      try
      {
        doc = DocumentHelper.parseText(xml);
      }
      catch (DocumentException e)
      {
        e.printStackTrace();
      }
      Element root = doc.getRootElement();
      Iterator i = root.elementIterator();
      map = new HashMap();
      while (i.hasNext())
      {
        Element e = (Element)i.next();
        String key = e.getName();
        String text = e.getText();
        map.put(key, text);
      }
    }
    return map;
  }
}
