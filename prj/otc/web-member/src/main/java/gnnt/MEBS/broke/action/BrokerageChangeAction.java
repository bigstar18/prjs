package gnnt.MEBS.broke.action;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.model.Brokerage;
import gnnt.MEBS.broke.service.BrokerageChangeService;
import gnnt.MEBS.broke.service.BrokerageService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
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
public class BrokerageChangeAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(BrokerageChangeAction.class);
  @Autowired
  @Qualifier("brokerageChangeService")
  private BrokerageChangeService brokerageChangeService;
  @Autowired
  @Qualifier("brokerageService")
  private BrokerageService brokerageService;
  
  public InService getService()
  {
    return this.brokerageService;
  }
  
  public String viewById()
  {
    this.obj = getService().get(this.obj);
    Brokerage brokerage = (Brokerage)this.obj;
    brokerage.setBrokerageNoChange(brokerage.getBrokerageNo());
    this.obj = brokerage;
    String organizationNo = (String)this.request.getSession().getAttribute(ActionConstant.ORGANIZATIONID);
    this.logger.debug("organizationNo:" + organizationNo);
    QueryConditions qc = new QueryConditions();
    if ((organizationNo != null) && (!"".equals(organizationNo)))
    {
      String brokerageNos = "(";
      brokerageNos = brokerageNos + "select view.brokerageNo from gnnt.MEBS.common.model.BrokerageRelateOrganization  view where view.organizationNo='" + organizationNo + "')";
      qc.addCondition("primary.brokerageNo", "in", brokerageNos);
    }
    List resultList = this.brokerageService.getList(qc, null);
    this.request.setAttribute("resultList", resultList);
    return getReturnValue();
  }
  
  public String update()
  {
    int returnValue = this.brokerageChangeService.update((Brokerage)this.obj);
    addResultMsg(this.request, returnValue);
    return getReturnValue();
  }
}
