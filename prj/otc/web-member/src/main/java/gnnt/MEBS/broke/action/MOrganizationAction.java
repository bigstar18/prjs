package gnnt.MEBS.broke.action;

import gnnt.MEBS.broke.model.MOrganization;
import gnnt.MEBS.broke.model.Organization;
import gnnt.MEBS.broke.service.MOrganizationService;
import gnnt.MEBS.common.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class MOrganizationAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MOrganizationAction.class);
  @Autowired
  @Qualifier("mOrganizationService")
  private MOrganizationService mOrganizationService;
  
  public InService getService()
  {
    return this.mOrganizationService;
  }
  
  public String updateMOrganization()
  {
    this.logger.debug("enter updateMOrganization.....");
    String memberNumber = (String)this.request.getSession().getAttribute("CURRENUSERID");
    String[] subMemberNo = memberNumber.split("_");
    String memberNo = subMemberNo[0];
    List<MOrganization> list2 = new ArrayList();
    String[] str2 = this.request.getParameterValues("organizationNo");
    if ((str2 != null) && (str2.length > 0)) {
      for (int i = 0; i < str2.length; i++)
      {
        Organization organization = this.mOrganizationService.queryByNo(str2[i], memberNo);
        MOrganization mOrganization = this.mOrganizationService.change(organization);
        list2.add(mOrganization);
      }
    }
    this.mOrganizationService.updateMOrganization(memberNo, list2);
    this.request.getSession().setAttribute("msg", "success");
    return getReturnValue();
  }
  
  public String queryMOrganization()
  {
    this.logger.debug("enter queryMOrganization.....");
    String memberNumber = (String)this.request.getSession().getAttribute("CURRENUSERID");
    String[] subMemberNo = memberNumber.split("_");
    String memberNo = subMemberNo[0];
    List<Organization> list = this.mOrganizationService.queryOrganization(memberNo);
    List<MOrganization> list1 = this.mOrganizationService.queryMOrganization(memberNo);
    String selFlag = "";
    if ((list1 != null) && (list1.size() > 0)) {
      for (int i = 0; i < list1.size(); i++)
      {
        System.out.println("list1.get(i).getBrokerageNo() : " + ((MOrganization)list1.get(i)).getOrganizationNO());
        selFlag = selFlag + ((MOrganization)list1.get(i)).getOrganizationNO() + ";";
      }
    }
    this.request.setAttribute("list", list);
    this.request.setAttribute("selFlag", selFlag);
    String msg = (String)this.request.getSession().getAttribute("msg");
    if ("success".equals(msg))
    {
      this.request.setAttribute("msg", "success");
      this.request.getSession().removeAttribute("msg");
    }
    return getReturnValue();
  }
}
