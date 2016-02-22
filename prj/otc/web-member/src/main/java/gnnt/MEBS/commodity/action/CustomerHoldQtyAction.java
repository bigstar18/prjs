package gnnt.MEBS.commodity.action;

import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.commodity.model.CustomerHoldQty;
import gnnt.MEBS.commodity.service.CustomerHoldQtyService;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
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
public class CustomerHoldQtyAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CustomerHoldQtyAction.class);
  @Autowired
  @Qualifier("customerHoldQtyService")
  private CustomerHoldQtyService customerHoldQtyService;
  
  public InService getService()
  {
    return this.customerHoldQtyService;
  }
  
  public String delete()
  {
    this.logger.debug("enter delete");
    String[] ids = this.request.getParameterValues("ids");
    CustomerHoldQty customerHoldQty = (CustomerHoldQty)this.obj;
    int resultValue = 1;
    if ((ids != null) && (ids.length > 0)) {
      for (String id : ids)
      {
        String[] newId = id.split(":::");
        customerHoldQty.setCommodityId(newId[0]);
        customerHoldQty.setCustomerNo(newId[1]);
        CustomerHoldQty clone = (CustomerHoldQty)getService().get(customerHoldQty);
        
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(clone);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        this.logger.debug("enter delete operateLog:" + this.obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        resultValue = getService().delete(clone);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
}
