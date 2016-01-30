package gnnt.MEBS.timebargain.mgr.action.settle;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.settle.ApplyTreatySettle;
import gnnt.MEBS.timebargain.mgr.service.AgreementSettleService;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("agreementSettleAction")
@Scope("request")
public class AgreementSettleAction extends EcsideAction
{
  private static final long serialVersionUID = 5124568190167465621L;

  @Resource(name="Audit_statusMap")
  private Map<String, String> Agreement_statusMap;

  @Autowired
  @Qualifier("com_agreementService")
  private AgreementSettleService agreementSettleService;

  public Map<String, String> getAgreement_statusMap()
  {
    return this.Agreement_statusMap;
  }

  public void setAgreement_statusMap(Map<String, String> paramMap)
  {
    this.Agreement_statusMap = paramMap;
  }

  public AgreementSettleService getAgreementSettleService()
  {
    return this.agreementSettleService;
  }

  public void setAgreementSettleService(AgreementSettleService paramAgreementSettleService)
  {
    this.agreementSettleService = paramAgreementSettleService;
  }

  public String addAgreement()
  {
    ApplyTreatySettle localApplyTreatySettle = (ApplyTreatySettle)this.entity;
    String str1 = ((User)this.request.getSession().getAttribute("CurrentUser")).getUserId();
    localApplyTreatySettle.setCreator(str1);
    localApplyTreatySettle.setStatus(Integer.valueOf(1));
    localApplyTreatySettle.setCreateTime(new Date());
    String str2 = localApplyTreatySettle.getCommodityID();
    String str3 = localApplyTreatySettle.getCustomerID_S();
    String str4 = localApplyTreatySettle.getCustomerID_B();
    String str5 = localApplyTreatySettle.getPrice().toString();
    String str6 = localApplyTreatySettle.getQuantity().toString();
    int i = this.agreementSettleService.addAgreement(str2, str3, str4, str5, str6, this.entity);
    if (i == 1)
      addReturnValue(1, 119901L);
    else if (i == -2)
      addReturnValue(-1, 150820L);
    else if (i == -3)
      addReturnValue(-1, 150821L);
    else if (i == -4)
      addReturnValue(-1, 150822L);
    else if (i == -5)
      addReturnValue(-1, 150823L);
    else if (i == -6)
      addReturnValue(-1, 150824L);
    else
      addReturnValue(-1, 119908L);
    return "success";
  }

  public String viewByIdAgreement()
    throws Exception
  {
    ApplyTreatySettle localApplyTreatySettle = (ApplyTreatySettle)getService().get(this.entity);
    this.entity = localApplyTreatySettle;
    return "success";
  }

  public String agreementAudit()
  {
    long l = Integer.parseInt(this.request.getParameter("status"));
    String str1 = this.request.getParameter("applyID");
    String str2 = this.request.getParameter("commodityID");
    String str3 = this.request.getParameter("customerID_B");
    String str4 = this.request.getParameter("customerID_S");
    String str5 = this.request.getParameter("price");
    String str6 = this.request.getParameter("quantity");
    int i;
    if (l == 2L)
    {
      i = this.agreementSettleService.auditSuccess(str1, str2, str3, str4, str5, str6);
      if (i == 1)
        addReturnValue(1, 119907L);
      else if (i == -1)
        addReturnValue(1, 150810L);
      else if (i == -3)
        addReturnValue(1, 150811L);
      else if (i == -5)
        addReturnValue(1, 150826L);
      else if (i == -11)
        addReturnValue(1, 150812L);
      else if (i == -13)
        addReturnValue(1, 150813L);
      else if (i == -21)
        addReturnValue(1, 150814L);
      else
        addReturnValue(1, 150815L);
    }
    else if (l == 3L)
    {
      i = this.agreementSettleService.auditFail(str1, str2, str3, str4, str5, str6);
      if (i == 1)
        addReturnValue(1, 119907L);
      else if (i == -1)
        addReturnValue(1, 150815L);
    }
    return "success";
  }
}