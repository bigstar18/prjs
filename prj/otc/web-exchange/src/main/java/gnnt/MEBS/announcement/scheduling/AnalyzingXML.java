package gnnt.MEBS.announcement.scheduling;

import gnnt.MEBS.announcement.model.Notice;
import gnnt.MEBS.announcement.service.NoticeService;
import gnnt.MEBS.announcement.xmlDeal.XmlDeal;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("analyzingXML")
public class AnalyzingXML
{
  private final transient Log logger = LogFactory.getLog(AnalyzingXML.class);
  @Autowired
  @Qualifier("noticeService")
  private NoticeService noticeService;
  @Autowired
  @Qualifier("xmlDeal")
  private XmlDeal xmlDeal;
  
  public void analyzingXML()
  {
    QueryConditions conditions = new QueryConditions();
    conditions.addCondition("filter", "filter", "(noticeType=2 or (noticeType=3 and isExecute='N' ))");
    conditions.addCondition("sendTime", "<", new Date());
    List<Notice> noticeList = this.noticeService.getList(conditions, null);
    this.logger.debug("noticeList:" + noticeList.size());
    for (Notice notice : noticeList) {
      try
      {
        this.xmlDeal.deal(notice);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
}
