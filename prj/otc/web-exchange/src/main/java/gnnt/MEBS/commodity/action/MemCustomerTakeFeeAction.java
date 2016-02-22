package gnnt.MEBS.commodity.action;

import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.commodity.model.MemCustomerTakeFee;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.commodity.service.MemCustomerTakeFeeService;
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

@Component("memCustomerTakeFeeAction")
@Scope("request")
public class MemCustomerTakeFeeAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemCustomerTakeFeeAction.class);
  @Autowired
  @Qualifier("memCustomerTakeFeeService")
  private MemCustomerTakeFeeService memCustomerTakeFeeService;
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Resource(name="commodityFeeModeMap")
  private Map commodityFeeModeMap;
  @Resource(name="feeAlgrMap")
  private Map feeAlgrMap;
  
  public Map getFeeAlgrMap()
  {
    return this.feeAlgrMap;
  }
  
  public Map getTakeFeeMap()
  {
    return this.commodityFeeModeMap;
  }
  
  public InService getService()
  {
    return this.memCustomerTakeFeeService;
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
        MemCustomerTakeFee customerTakeFee = (MemCustomerTakeFee)this.obj;
        customerTakeFee.setM_FirmId(primarys[0]);
        customerTakeFee.setCommodityId(primarys[1]);
        Clone clone = getService().get(customerTakeFee);
        
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(clone);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        this.logger.debug("enter delete operateLog:" + this.obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        resultValue = getService().delete(customerTakeFee);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter customerTakeFeeParameters");
    this.request.setAttribute("takeFeeMap", this.commodityFeeModeMap);
    this.request.setAttribute("feeAlgrMap", this.feeAlgrMap);
  }
}
