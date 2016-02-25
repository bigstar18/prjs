package gnnt.MEBS.zcjs.util;

import gnnt.MEBS.zcjs.model.innerObject.CommodityPropertyObject;
import gnnt.MEBS.zcjs.model.innerObject.KeyValue;
import gnnt.MEBS.zcjs.model.innerObject.QualityObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ParseXML
{
  public static String getCommodityPropertyObjectXml(List<CommodityPropertyObject> paramList)
  {
    String str = null;
    if ((paramList != null) && (paramList.size() > 0))
    {
      Document localDocument = DocumentHelper.createDocument();
      localDocument.setXMLEncoding("gbk");
      Element localElement1 = localDocument.addElement("root");
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        CommodityPropertyObject localCommodityPropertyObject = (CommodityPropertyObject)localIterator.next();
        Element localElement2 = localElement1.addElement("property");
        localElement2.addAttribute("key", localCommodityPropertyObject.getKey());
        Element localElement3 = localElement2.addElement("id");
        Element localElement4 = localElement2.addElement("name");
        Element localElement5 = localElement2.addElement("value");
        localElement3.setText(localCommodityPropertyObject.getId() + "");
        localElement4.addCDATA(localCommodityPropertyObject.getName());
        localElement5.addCDATA(localCommodityPropertyObject.getValue());
      }
      str = localDocument.asXML();
    }
    return str;
  }
  
  public static List<CommodityPropertyObject> addCommodityPropertyObjectToXml(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramString != null) && (!"".equals(paramString)))
    {
      Document localDocument = null;
      try
      {
        localDocument = DocumentHelper.parseText(paramString);
      }
      catch (DocumentException localDocumentException)
      {
        localDocumentException.printStackTrace();
      }
      Element localElement1 = localDocument.getRootElement();
      Iterator localIterator = localElement1.elementIterator();
      while (localIterator.hasNext())
      {
        CommodityPropertyObject localCommodityPropertyObject = new CommodityPropertyObject();
        Element localElement2 = (Element)localIterator.next();
        String str1 = localElement2.attribute("key").getText();
        long l = Long.parseLong(localElement2.element("id").getText());
        String str2 = localElement2.element("value").getText();
        String str3 = localElement2.element("name").getText();
        localCommodityPropertyObject.setId(l);
        localCommodityPropertyObject.setValue(str2);
        localCommodityPropertyObject.setName(str3);
        localCommodityPropertyObject.setKey(str1);
        localArrayList.add(localCommodityPropertyObject);
      }
    }
    return localArrayList;
  }
  
  public static String getQualityObjectXml(List<QualityObject> paramList)
  {
    String str = null;
    if ((paramList != null) && (paramList.size() > 0))
    {
      Document localDocument = DocumentHelper.createDocument();
      localDocument.setXMLEncoding("gbk");
      Element localElement1 = localDocument.addElement("root");
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        QualityObject localQualityObject = (QualityObject)localIterator.next();
        Element localElement2 = localElement1.addElement("quality");
        localElement2.addAttribute("id", localQualityObject.getId() + "");
        Element localElement3 = localElement2.addElement("name");
        Element localElement4 = localElement2.addElement("value");
        localElement3.addCDATA(localQualityObject.getName());
        localElement4.addCDATA(localQualityObject.getValue());
      }
      str = localDocument.asXML();
    }
    return str;
  }
  
  public static List<QualityObject> addQualityObjectToXml(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramString != null) && (!"".equals(paramString)))
    {
      Document localDocument = null;
      try
      {
        localDocument = DocumentHelper.parseText(paramString);
      }
      catch (DocumentException localDocumentException)
      {
        localDocumentException.printStackTrace();
      }
      Element localElement1 = localDocument.getRootElement();
      Iterator localIterator = localElement1.elementIterator();
      while (localIterator.hasNext())
      {
        QualityObject localQualityObject = new QualityObject();
        Element localElement2 = (Element)localIterator.next();
        long l = Long.parseLong(localElement2.attribute("id").getText());
        String str1 = localElement2.element("value").getText();
        String str2 = localElement2.element("name").getText();
        localQualityObject.setId(l);
        localQualityObject.setValue(str1);
        localQualityObject.setName(str2);
        localArrayList.add(localQualityObject);
      }
    }
    return localArrayList;
  }
  
  public static List<KeyValue> addToXml(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    if ((paramString != null) && (!"".equals(paramString)))
    {
      Document localDocument = null;
      try
      {
        localDocument = DocumentHelper.parseText(paramString);
      }
      catch (DocumentException localDocumentException)
      {
        localDocumentException.printStackTrace();
      }
      Element localElement1 = localDocument.getRootElement();
      Iterator localIterator = localElement1.elementIterator();
      while (localIterator.hasNext())
      {
        Element localElement2 = (Element)localIterator.next();
        KeyValue localKeyValue = new KeyValue();
        String str1 = localElement2.element("key").getText();
        String str2 = localElement2.element("value").getText();
        localKeyValue.setKey(str1);
        localKeyValue.setValue(str2);
        localArrayList.add(localKeyValue);
      }
    }
    return localArrayList;
  }
}
