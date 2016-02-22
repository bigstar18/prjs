package gnnt.MEBS.trade.action;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.model.TQLog;
import gnnt.MEBS.trade.model.TQLogSet;
import gnnt.MEBS.trade.rmi.AgencyRMIBean;
import gnnt.MEBS.trade.service.TQLogService;
import gnnt.MEBS.trade.service.TQLogSetService;
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

@Component("tqLogAction")
@Scope("request")
public class TQLogAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(TQLogAction.class);
  @Autowired
  @Qualifier("tqLogService")
  private TQLogService tqLogService;
  @Autowired
  @Qualifier("tqLogSetService")
  private TQLogSetService tqLogSetService;
  
  public InService getService()
  {
    return this.tqLogService;
  }
  
  public String list()
  {
    this.logger.debug("enter list");
    String sortName = this.request.getParameter("sortName") != null ? this.request.getParameter("sortName") : "primary.actionTime";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request.getParameter("sortOrder") : "true";
    if (this.request.getAttribute("sortString") != null)
    {
      String orderString = (String)this.request.getAttribute("sortString");
      ThreadStore.put("orderString", orderString);
    }
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
  
  public String add()
  {
    this.logger.debug("enter add");
    int resultValue = 0;
    
    String commodityID = this.request.getParameter("commodityId");
    String quoprice = this.request.getParameter("quoprice");
    try
    {
      TQLog obj = new TQLog();
      obj.setCommodityId(commodityID);
      obj.setQuoprice(Double.valueOf(Double.parseDouble(quoprice)));
      obj.setOperator(AclCtrl.getLogonID(this.request));
      

      Clone clone = getService().get(obj);
      
      OperateLog operateLog = new OperateLog();
      operateLog.setObj(clone);
      operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
      this.logger.debug("enter addMarket operateLog:" + obj);
      operateLog.setOperator(AclCtrl.getLogonID(this.request));
      ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
      
      AgencyRMIBean rmi = new AgencyRMIBean(this.request);
      resultValue = this.tqLogService.add(obj, rmi);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      resultValue = -1;
    }
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public boolean judgePrice(String id, String strPrice)
  {
    boolean flag = false;
    
    Double price = Double.valueOf(Double.parseDouble(strPrice));
    TQLogSet logSet = this.tqLogSetService.get(id);
    if ((logSet != null) && 
      (price.doubleValue() >= logSet.getMinquoprice().doubleValue()) && (price.doubleValue() <= logSet.getMaxquoprice().doubleValue())) {
      flag = true;
    }
    return flag;
  }
}
