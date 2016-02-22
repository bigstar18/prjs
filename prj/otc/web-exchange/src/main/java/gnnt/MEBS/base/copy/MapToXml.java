package gnnt.MEBS.base.copy;

import java.util.Map;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class MapToXml
{
  public static String mapToXml(Map<String, String> extendMap)
  {
    Document document = DocumentHelper.createDocument();
    document.setXMLEncoding("GBK");
    Element booksElement = document.addElement("root");
    if ((extendMap != null) && (extendMap.size() > 0))
    {
      Set<String> keySet = extendMap.keySet();
      for (String key : keySet)
      {
        Object value = extendMap.get(key);
        if (value != null)
        {
          Element oElement = booksElement.addElement(key);
          oElement.addCDATA(value.toString());
        }
      }
    }
    String result = document.asXML();
    return result;
  }
}
