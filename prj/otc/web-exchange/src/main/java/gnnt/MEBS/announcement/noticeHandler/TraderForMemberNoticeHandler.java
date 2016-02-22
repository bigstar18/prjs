package gnnt.MEBS.announcement.noticeHandler;

import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.announcement.dao.NoticeProDao;
import gnnt.MEBS.config.constant.AnnouncementConstant;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TraderForMemberNoticeHandler
  extends AbstractTypeHandler
{
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  
  public int handle(Long noticeId, Element range)
  {
    int result = 0;
    String type = "";
    String trader = "";
    type = range.attributeValue("type");
    String memberIdStr = "";
    if (AnnouncementConstant.TRADERMEMBERNOTICE.equals(type))
    {
      Element group = range.element("group");
      if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllCustomers"))))
      {
        trader = group.element("trader").getText();
        if (!trader.contains("'")) {
          trader = "'" + trader.replaceAll(",", "','") + "'";
        }
      }
      else if ((group != null) && ("N".equalsIgnoreCase(group.attributeValue("isNotAllCustomers"))))
      {
        memberIdStr = range.elementText("memberId");
        if (!memberIdStr.contains("'")) {
          memberIdStr = "'" + memberIdStr.replaceAll(",", "','") + "'";
        }
      }
      this.noticeProDao.customerForMemberOKNotice("N", memberIdStr, trader, noticeId);
      result = 1;
    }
    return result;
  }
}
