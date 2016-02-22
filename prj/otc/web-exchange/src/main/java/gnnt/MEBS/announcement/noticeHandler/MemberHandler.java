package gnnt.MEBS.announcement.noticeHandler;

import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.announcement.dao.NoticeProDao;
import gnnt.MEBS.config.constant.AnnouncementConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberHandler
  extends AbstractTypeHandler
{
  private final transient Log logger = LogFactory.getLog(MemberHandler.class);
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  
  public int handle(Long noticeId, Element range)
  {
    int result = 0;
    String type = "";
    String member = "";
    String relation = "";
    String isAll = "N";
    String user = "";
    type = range.attributeValue("type");
    if (AnnouncementConstant.MEMBER.equals(type))
    {
      relation = range.elementText("relation");
      Element group = range.element("group");
      if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllMember"))))
      {
        member = group.elementText("member");
        if (!member.contains("'")) {
          member = "'" + member.replaceAll(",", "','") + "'";
        }
      }
      else if ((group != null) && ("N".equalsIgnoreCase(group.attributeValue("isNotAllMember"))))
      {
        isAll = "Y";
      }
      this.noticeProDao.memberOKNotice(isAll, member, null, relation, noticeId);
      result = 1;
    }
    return result;
  }
}
