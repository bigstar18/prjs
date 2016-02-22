package gnnt.MEBS.announcement.noticeHandler;

import gnnt.MEBS.announcement.dao.NoticeProDao;
import gnnt.MEBS.config.constant.AnnouncementConstant;
import org.dom4j.Element;

public class BourseHandler
  extends AbstractTypeHandler
{
  public int handle(Long noticeId, Element range)
  {
    int result = 0;
    String type = "";
    String role = "";
    String appoint = "";
    type = range.attributeValue("type");
    if (AnnouncementConstant.EXCHANGE.equals(type))
    {
      Element group = range.element("group");
      if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAll"))))
      {
        role = group.element("role").getText();
        appoint = group.element("appoint").getText();
      }
      result = this.noticeProDao.marketOKNotice(role, appoint, noticeId);
    }
    return result;
  }
}
