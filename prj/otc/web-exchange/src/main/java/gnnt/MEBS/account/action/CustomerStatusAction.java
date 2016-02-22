package gnnt.MEBS.account.action;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.account.model.CustomerStatus;
import gnnt.MEBS.account.service.CustomerStatusService;
import gnnt.MEBS.account.service.CustomerVOService;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.LogConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
public class CustomerStatusAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerStatusAction.class);
  @Autowired
  @Qualifier("customerStatusService")
  private CustomerStatusService customerStatusService;
  @Autowired
  @Qualifier("customerVOService")
  private CustomerVOService customerVOService;
  @Autowired
  @Qualifier("globalLogService")
  private OperateLogService globalLogService;
  @Resource(name="papersTypeMap")
  private Map papersTypeMap;
  @Resource(name="firmStatusMap")
  private Map firmStatusMap;
  @Resource(name="openstatusMap")
  private Map openstatusMap;
  
  public CustomerVOService getCustomerVOService()
  {
    return this.customerVOService;
  }
  
  public InService getService()
  {
    return this.customerStatusService;
  }
  
  public Map getPapersTypeMap()
  {
    return this.papersTypeMap;
  }
  
  public Map getFirmStatusMap()
  {
    return this.firmStatusMap;
  }
  
  public Map getOpenstatusMap()
  {
    return this.openstatusMap;
  }
  
  public String list()
  {
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "primary.id";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    this.resultList = this.customerVOService.getList(qc, pageInfo);
    this.logger.debug("resultList  size:" + this.resultList.size());
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.obj = null;
    return getReturnValue();
  }
  
  public String customerActive()
  {
    this.logger.debug("enter update");
    String[] customerNos = this.request.getParameterValues("ids");
    int result = 1;
    String customerName = "";
    String descriptString = "对客户Id为： ";
    String desc = "";
    for (String customerNo : customerNos)
    {
      Customer customer = (Customer)this.customerStatusService.getById(customerNo);
      customer.getCustomerStatus().setStatus("N");
      result = getService().update(customer);
      if (result < 0)
      {
        customerName = customer.getName();
        break;
      }
      desc = desc + customer.getId() + "、 ";
    }
    descriptString = descriptString + desc + "的激活操作";
    addResultSessionMsg(this.request, result);
    if (result < 0) {
      this.request.getSession().setAttribute(ActionConstant.RESULTMSG, "客户 \"" + customerName + "\"" + this.request.getSession().getAttribute(ActionConstant.RESULTMSG) + ",此客户及后续客户未激活成功");
    }
    OperateLog operateLog = new OperateLog();
    operateLog.setObj(this.obj);
    this.logger.debug("enter delete operateLog:" + this.obj);
    operateLog.setOperator(AclCtrl.getLogonID(this.request));
    operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
    operateLog.setOperateContent(descriptString);
    operateLog.setOperateDate(new Date());
    operateLog.setOperateIp(this.request.getRemoteAddr());
    operateLog.setOperateLogType(3001);
    operateLog.setOperatorType(LogConstant.OPERATORTYPE);
    if (!"".equals(desc)) {
      this.globalLogService.add(operateLog);
    }
    return getReturnValue();
  }
}
