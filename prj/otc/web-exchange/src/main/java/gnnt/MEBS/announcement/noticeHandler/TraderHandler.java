package gnnt.MEBS.announcement.noticeHandler;

import gnnt.MEBS.account.service.CustomerService;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.announcement.dao.NoticeProDao;
import gnnt.MEBS.config.constant.AnnouncementConstant;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TraderHandler
  extends AbstractTypeHandler
{
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Autowired
  @Qualifier("customerService")
  private CustomerService customerService;
  
  public int handle(Long noticeId, Element range)
  {
    int result = 0;
    String type = "";
    String member = "";
    String isAll = "N";
    String customerNo = "";
    type = range.attributeValue("type");
    if (AnnouncementConstant.TRADER.equals(type))
    {
      Element group = range.element("group");
      if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllCustomers"))))
      {
        member = group.element("member").getText();
        if (!member.contains("'")) {
          member = "'" + member.replaceAll(",", "','") + "'";
        }
      }
      else if ((group != null) && ("N".equalsIgnoreCase(group.attributeValue("isNotAllCustomers"))))
      {
        isAll = "Y";
      }
      this.noticeProDao.customerOKNotice(isAll, member, null, noticeId);
      result = 1;
    }
    return result;
  }
}
