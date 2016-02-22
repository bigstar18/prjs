package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.model.WxAgreement;
import gnnt.MEBS.account.model.WxCustomer;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.account.service.WxAgreementService;
import gnnt.MEBS.account.service.WxCustomerService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class WxCustomerAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(WxCustomerAction.class);
  @Resource(name="openstatusMap")
  private Map openstatusMap;
  @Autowired
  @Qualifier("wxCustomerService")
  private WxCustomerService wxCustomerService;
  @Autowired
  @Qualifier("wxAgreementService")
  private WxAgreementService wxAgreementService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Resource(name="papersTypeMap")
  private Map papersTypeMap;
  @Resource(name="papersTypeUpdateMap")
  private Map papersTypeUpdateMap;
  @Resource(name="firmStatusMap")
  private Map firmStatusMap;
  
  public Map getOpenstatusMap()
  {
    return this.openstatusMap;
  }
  
  public Map getPapersTypeMap()
  {
    return this.papersTypeMap;
  }
  
  public Map getPapersTypeUpdateMap()
  {
    return this.papersTypeUpdateMap;
  }
  
  public Map getFirmStatusMap()
  {
    return this.firmStatusMap;
  }
  
  public InService getService()
  {
    return this.wxCustomerService;
  }
  
  public String list()
  {
    return super.list();
  }
  
  public String showInformation()
  {
    List<WxAgreement> list = this.wxAgreementService.getList(null, null);
    String returnvalue = super.viewById();
    WxAgreement wxAgreement = (WxAgreement)list.get(0);
    WxCustomer wxCustomer = (WxCustomer)getObj();
    String memberno = wxCustomer.getMemberno();
    MemberInfo memberInfo = (MemberInfo)this.memberInfoService.getById(memberno);
    if (wxAgreement != null)
    {
      String index = this.request.getParameter("agreementturn");
      if (index != null) {
        if (index.equals("1"))
        {
          String str = wxAgreement.getAgreement1();
          str = str.replace("${memberInfo.name}", memberInfo.getName());
          this.request.setAttribute("content", str);
        }
        else if (index.equals("2"))
        {
          String str = wxAgreement.getAgreement2();
          this.request.setAttribute("content", str);
        }
        else if (index.equals("3"))
        {
          String str = wxAgreement.getAgreement3();
          this.request.setAttribute("content", str);
        }
        else if (index.equals("4"))
        {
          String str = wxAgreement.getAgreement4();
          this.request.setAttribute("content", str);
        }
        else if (index.equals("5"))
        {
          String str = wxAgreement.getAgreement5();
          this.request.setAttribute("content", str);
        }
      }
    }
    return "success";
  }
}
