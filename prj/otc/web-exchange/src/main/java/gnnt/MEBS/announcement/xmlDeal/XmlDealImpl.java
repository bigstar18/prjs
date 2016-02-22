package gnnt.MEBS.announcement.xmlDeal;

import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.noticeHandler.NoticeTypeHandler;
import gnnt.MEBS.announcement.service.NoticeService;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("xmlDeal")
public class XmlDealImpl
  implements XmlDeal
{
  @Autowired
  @Qualifier("noticeService")
  private NoticeService noticeService;
  @Autowired
  @Qualifier("brokerHandler")
  private NoticeTypeHandler noticeTypeHandler;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class, RuntimeException.class})
  public int deal(Notice notice)
  {
    notice.setIsExecute("Y");
    this.noticeService.update(notice);
    String recipientRules = notice.getRecipientRules();
    List<Element> rangeList = paseXML(recipientRules);
    if ((rangeList != null) && (rangeList.size() > 0)) {
      for (Element range : rangeList) {
        this.noticeTypeHandler.handleRequest(notice.getId(), range);
      }
    }
    return 1;
  }
  
  public List<Element> paseXML(String xml)
  {
    List<Element> rangeList = new ArrayList();
    try
    {
      Document document = DocumentHelper.parseText(xml);
      Element root = document.getRootElement();
      rangeList = root.elements("range");
    }
    catch (DocumentException e)
    {
      e.printStackTrace();
    }
    return rangeList;
  }
}
