package gnnt.MEBS.settlement.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.globalLog.service.OperateLogService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.service.QuotationService;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
public class QuotationAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(QuotationAction.class);
  @Autowired
  @Qualifier("quotationService")
  private QuotationService quotationService;
  @Autowired
  @Qualifier("globalLogService")
  private OperateLogService operateLogService;
  
  public InService getService()
  {
    return this.quotationService;
  }
  
  public String settlement()
  {
    int resultValue = 1;
    String operateContent = "";
    OperateLog operateLog = null;
    boolean flagLog = false;
    if ((ThreadStore.get(ThreadStoreConstant.ISLOG) != null) && (((Integer)ThreadStore.get(ThreadStoreConstant.ISLOG)).intValue() > 0))
    {
      flagLog = true;
      operateLog = new OperateLog();
      operateLog.setObj(null);
      operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
      operateLog.setOperator(AclCtrl.getLogonID(this.request));
      operateLog.setOperateContent("手工结算开始！");
      operateLog.setOperateDate(new Date());
      operateLog.setOperatorType("E");
      operateLog.setOperateIp(this.request.getRemoteAddr());
      this.operateLogService.add(operateLog);
    }
    try
    {
      AgencyRMIBean remObject = new AgencyRMIBean(this.request);
      resultValue = remObject.balance();
      addResultMsg(this.request, resultValue);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      if (e.getCause().getMessage() != null)
      {
        resultValue = -1;
        addResultMsg(this.request, resultValue);
        this.request.setAttribute(ActionConstant.RESULTMSG, e.getCause().getMessage());
      }
    }
    if (resultValue > 0) {
      operateContent = "结算已执行！";
    } else if (resultValue == -600) {
      operateContent = "交易已结算或应在闭市后结算！";
    } else {
      operateContent = "手工结算异常！";
    }
    if (flagLog)
    {
      operateLog.setObj(null);
      operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
      operateLog.setOperator(AclCtrl.getLogonID(this.request));
      operateLog.setOperateContent(operateContent);
      operateLog.setOperateDate(new Date());
      operateLog.setOperatorType("E");
      operateLog.setOperateIp(this.request.getRemoteAddr());
      this.operateLogService.add(operateLog);
    }
    return getReturnValue();
  }
  
  public String list()
  {
    this.logger.debug("enter list");
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "commodity.name";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    

    QueryConditions qc = getQueryConditions(map);
    this.resultList = getService().getList(qc, pageInfo);
    this.logger.debug("resultList  size:" + this.resultList.size());
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    this.obj = null;
    return getReturnValue();
  }
}
