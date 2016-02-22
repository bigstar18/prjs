package gnnt.MEBS.announcement.noticeHandler;

import gnnt.MEBS.announcement.dao.NoticeProDao;
import gnnt.MEBS.broke.service.OrganizationService;
import gnnt.MEBS.config.constant.AnnouncementConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class OrganizationHandler
  extends AbstractTypeHandler
{
  private final transient Log logger = LogFactory.getLog(OrganizationHandler.class);
  @Autowired
  @Qualifier("organizationService")
  private OrganizationService organizationService;
  
  public int handle(Long noticeId, Element range)
  {
    int result = 0;
    String type = "";
    String organization = "";
    String relation = "N";
    String user = "";
    String isAll = "N";
    type = range.attributeValue("type");
    if (AnnouncementConstant.ORGANIZATION.equals(type))
    {
      Element group = range.element("group");
      if ((group != null) && ("Y".equalsIgnoreCase(group.attributeValue("isNotAllOrganization"))))
      {
        organization = group.elementText("organization");
        if (!organization.contains("'")) {
          organization = "'" + organization.replaceAll(",", "','") + "'";
        }
        relation = range.elementText("relation");
      }
      else if ((group != null) && ("N".equalsIgnoreCase(group.attributeValue("isNotAllOrganization"))))
      {
        isAll = "Y";
        relation = range.elementText("relation");
      }
      this.noticeProDao.organizationOKNotice(isAll, range.elementText("memberId"), organization, relation, noticeId);
      result = 1;
    }
    return result;
  }
}
