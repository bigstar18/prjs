package gnnt.MEBS.commodity.action;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.commodity.model.Margin;
import gnnt.MEBS.commodity.service.CustomerMarginService;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
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

@Component("customerMarginAction")
@Scope("request")
public class CustomerMarginAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerMarginAction.class);
  @Autowired
  @Qualifier("customerMarginService")
  private CustomerMarginService customerMarginService;
  @Resource(name="commodityMarginAlgrMap")
  private Map commodityMarginAlgrMap;
  @Resource(name="commodityMarginPriceMap")
  private Map commodityMarginPriceMap;
  @Resource(name="firmDisMap")
  private Map firmDisMap;
  
  public Map getFirmDisMap()
  {
    return this.firmDisMap;
  }
  
  public Map getCommodityMarginAlgrMap()
  {
    return this.commodityMarginAlgrMap;
  }
  
  public Map getCommodityMarginPriceMap()
  {
    return this.commodityMarginPriceMap;
  }
  
  public InService getService()
  {
    return this.customerMarginService;
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter marginParameters");
    this.request.setAttribute("commodityMarginAlgrMap", this.commodityMarginAlgrMap);
    this.request.setAttribute("commodityMarginPriceMap", this.commodityMarginPriceMap);
    this.request.setAttribute("firmDisMap", this.firmDisMap);
  }
  
  public String delete()
  {
    this.logger.debug("enter delete");
    String[] ids = this.request.getParameterValues("ids");
    int resultValue = 1;
    if ((ids != null) && (ids.length > 0)) {
      for (String id : ids)
      {
        String[] primarys = id.split(",");
        Margin margin = (Margin)this.obj;
        margin.setFirmId(primarys[0]);
        margin.setCommodityId(primarys[1]);
        Clone clone = getService().get(margin);
        
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(clone);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        this.logger.debug("enter delete operateLog:" + this.obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        resultValue = getService().delete(margin);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
}
