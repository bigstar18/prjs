package gnnt.MEBS.announcement.noticeHandler;

import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.account.service.SpecialMemberUserService;
import gnnt.MEBS.announcement.dao.NoticeProDao;
import gnnt.MEBS.config.constant.AnnouncementConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SpecialMemeberHandler
  extends AbstractTypeHandler
{
  private final transient Log logger = LogFactory.getLog(SpecialMemeberHandler.class);
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  @Autowired
  @Qualifier("specialMemberUserService")
  private SpecialMemberUserService specialMemberUserService;
  
  public int handle(Long noticeId, Element range)
  {
    int result = 0;
    String type = "";
    String specialMemeber = "";
    String isAll = "N";
    String user = "";
    type = range.attributeValue("type");
    if (AnnouncementConstant.SPECIALMEMEBER.equals(type))
    {
      Element group = range.element("group");
      if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllSpecialMemeber"))))
      {
        specialMemeber = group.elementText("specialMemeber");
        if (!specialMemeber.contains("'")) {
          specialMemeber = "'" + specialMemeber.replaceAll(",", "','") + "'";
        }
      }
      else if ((group != null) && ("N".equalsIgnoreCase(group.attributeValue("isNotAllSpecialMemeber"))))
      {
        isAll = "Y";
      }
      this.noticeProDao.specialMemberOKNotice(isAll, specialMemeber, null, noticeId);
      result = 1;
    }
    return result;
  }
}
