package gnnt.MEBS.announcement.noticeHandler;

import gnnt.MEBS.announcement.dao.NoticeProDao;
import gnnt.MEBS.broke.service.BrokerageService;
import gnnt.MEBS.config.constant.AnnouncementConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BrokerHandler
  extends AbstractTypeHandler
{
  private final transient Log logger = LogFactory.getLog(BrokerHandler.class);
  @Autowired
  @Qualifier("brokerageService")
  private BrokerageService brokerageService;
  
  public int handle(Long noticeId, Element range)
  {
    int result = 0;
    String type = "";
    String brokerage = "";
    String relation = "Y";
    String user = "";
    String isAll = "N";
    type = range.attributeValue("type");
    if (AnnouncementConstant.BROKER.equals(type))
    {
      Element group = range.element("group");
      if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllBrokerage"))))
      {
        brokerage = group.elementText("brokerage");
        if (!brokerage.contains("'")) {
          brokerage = "'" + brokerage.replaceAll(",", "','") + "'";
        }
        relation = group.elementText("relation");
      }
      else if ((group != null) && ("N".equalsIgnoreCase(group.attributeValue("isNotAllBrokerage"))))
      {
        isAll = "Y";
      }
      this.noticeProDao.brokerOKNotice(isAll, range.elementText("memberId"), brokerage, relation, noticeId);
      result = 1;
    }
    return result;
  }
}
