package gnnt.MEBS.trade.action;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.trade.model.TQLogSet;
import gnnt.MEBS.trade.service.TQLogSetService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("tqLogSetAction")
@Scope("request")
public class TQLogSetAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(TQLogSetAction.class);
  @Autowired
  @Qualifier("tqLogSetService")
  private TQLogSetService tqLogSetService;
  
  public InService getService()
  {
    return this.tqLogSetService;
  }
  
  public String add()
  {
    this.logger.debug("enter add");
    int resultValue = 0;
    
    String commodityId = this.request.getParameter("commodityId");
    String minPrice = this.request.getParameter("minQuoprice");
    String maxPrice = this.request.getParameter("maxQuoprice");
    
    TQLogSet obj = new TQLogSet();
    obj.setCommodityId(commodityId);
    obj.setMinquoprice(Double.valueOf(Double.parseDouble(minPrice)));
    obj.setMaxquoprice(Double.valueOf(Double.parseDouble(maxPrice)));
    
    TQLogSet set = this.tqLogSetService.get(commodityId);
    if (set != null) {
      resultValue = this.tqLogSetService.update(obj);
    } else {
      resultValue = this.tqLogSetService.add(obj);
    }
    Clone clone = getService().get(obj);
    
    OperateLog operateLog = new OperateLog();
    operateLog.setObj(clone);
    operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
    this.logger.debug("enter addMarket operateLog:" + obj);
    operateLog.setOperator(AclCtrl.getLogonID(this.request));
    ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
    
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String update()
  {
    this.logger.debug("enter update");
    
    int resultValue = 0;
    
    String commodityId = this.request.getParameter("commodityId");
    String minPrice = this.request.getParameter("minQuoprice");
    String maxPrice = this.request.getParameter("maxQuoprice");
    
    TQLogSet obj = new TQLogSet();
    obj.setCommodityId(commodityId);
    obj.setMinquoprice(Double.valueOf(Double.parseDouble(minPrice)));
    obj.setMaxquoprice(Double.valueOf(Double.parseDouble(maxPrice)));
    resultValue = this.tqLogSetService.update(obj);
    


    Clone clone = getService().get(obj);
    
    OperateLog operateLog = new OperateLog();
    operateLog.setObj(clone);
    operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
    this.logger.debug("enter updateMarket operateLog:" + obj);
    operateLog.setOperator(AclCtrl.getLogonID(this.request));
    ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
    
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
}
