package gnnt.MEBS.delivery.model.workflow;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.delivery.model.KeyValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public abstract class WorkFlowClone
  extends Clone
{
  protected int actionStartStatus;
  protected int actionEndStatus;
  protected int regStockChangeStatus = -1;
  protected String note;
  
  public int getActionStartStatus()
  {
    return this.actionStartStatus;
  }
  
  public void addActionStartStatus(int paramInt)
  {
    this.actionStartStatus = paramInt;
  }
  
  public int getActionEndStatus()
  {
    return this.actionEndStatus;
  }
  
  public void addActionEndStatus(int paramInt)
  {
    this.actionEndStatus = paramInt;
  }
  
  public int getRegStockChangeStatus()
  {
    return this.regStockChangeStatus;
  }
  
  public void addRegStockChangeStatus(int paramInt)
  {
    this.regStockChangeStatus = paramInt;
  }
  
  public int getCurrentStatus()
  {
    return 0;
  }
  
  public String getBillid()
  {
    return null;
  }
  
  public String getCommId()
  {
    return null;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String paramString)
  {
    this.note = paramString;
  }
  
  public Map<String, String> addToXml(String paramString)
  {
    HashMap localHashMap = null;
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
      localHashMap = new HashMap();
      Element localElement1 = localDocument.getRootElement();
      Iterator localIterator = localElement1.elementIterator();
      while (localIterator.hasNext())
      {
        Element localElement2 = (Element)localIterator.next();
        String str1 = localElement2.getName();
        String str2 = localElement2.getText();
        localHashMap.put(str1, str2);
      }
    }
    return localHashMap;
  }
  
  public String getToXml(Map paramMap)
  {
    Set localSet = paramMap.entrySet();
    Iterator localIterator = localSet.iterator();
    Document localDocument = DocumentHelper.createDocument();
    localDocument.setXMLEncoding("gb2312");
    Element localElement1 = localDocument.addElement("root");
    while (localIterator.hasNext())
    {
      localObject = (Map.Entry)localIterator.next();
      Element localElement2 = localElement1.addElement(((Map.Entry)localObject).getKey().toString());
      localElement2.setText(((Map.Entry)localObject).getValue().toString());
    }
    Object localObject = localDocument.asXML();
    return localObject;
  }
  
  public void addToXml(String paramString, Map paramMap)
  {
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
        String str1 = localElement2.getName();
        String str2 = localElement2.getText();
        paramMap.put(str1, str2);
      }
    }
  }
  
  public String getToXml(List<KeyValue> paramList)
  {
    String str = null;
    if (paramList != null)
    {
      Document localDocument = DocumentHelper.createDocument();
      localDocument.setXMLEncoding("gb2312");
      Element localElement1 = localDocument.addElement("root");
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        KeyValue localKeyValue = (KeyValue)localIterator.next();
        Element localElement2 = localElement1.addElement("keyValue");
        Element localElement3 = localElement2.addElement("key");
        localElement3.addCDATA(localKeyValue.getKey());
        Element localElement4 = localElement2.addElement("value");
        localElement4.addCDATA(localKeyValue.getValue());
      }
      str = localDocument.asXML();
    }
    return str;
  }
  
  public List<KeyValue> addToXml(String paramString, List<KeyValue> paramList)
  {
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
      paramList = new ArrayList();
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
        paramList.add(localKeyValue);
      }
    }
    return paramList;
  }
}
