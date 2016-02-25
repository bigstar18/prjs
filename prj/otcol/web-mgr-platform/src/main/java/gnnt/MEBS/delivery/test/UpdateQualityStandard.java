package gnnt.MEBS.delivery.test;

import gnnt.MEBS.delivery.dao.EnterWareDao;
import gnnt.MEBS.delivery.model.KeyValue;
import gnnt.MEBS.delivery.model.workflow.EnterWare;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UpdateQualityStandard
{
  public static void main(String[] paramArrayOfString)
  {
    ClassPathXmlApplicationContext localClassPathXmlApplicationContext = new ClassPathXmlApplicationContext("wareHouseBeansTest.xml");
    EnterWareDao localEnterWareDao = (EnterWareDao)localClassPathXmlApplicationContext.getBean("w_enterWareDao");
    List localList1 = localEnterWareDao.getEnterWares(null);
    Iterator localIterator = localList1.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof EnterWare))
      {
        String str1 = ((EnterWare)localObject).getQualityStandard();
        List localList2 = addToXml(str1);
        String str2 = getToXml(localList2);
        ((EnterWare)localObject).setQualityStandard(str2);
        localEnterWareDao.updateEnterWare((EnterWare)localObject);
      }
    }
  }
  
  public static List<KeyValue> addToXml(String paramString)
  {
    ArrayList localArrayList = null;
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
      localArrayList = new ArrayList();
      Element localElement1 = localDocument.getRootElement();
      Iterator localIterator = localElement1.elementIterator();
      while (localIterator.hasNext())
      {
        Element localElement2 = (Element)localIterator.next();
        KeyValue localKeyValue = new KeyValue();
        String str1 = localElement2.getName();
        String str2 = localElement2.getText();
        localKeyValue.setKey(str1);
        localKeyValue.setValue(str2);
        localArrayList.add(localKeyValue);
      }
    }
    return localArrayList;
  }
  
  public static String getToXml(List<KeyValue> paramList)
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
}
