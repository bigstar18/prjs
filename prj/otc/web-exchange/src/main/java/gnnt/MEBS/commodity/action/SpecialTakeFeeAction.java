package gnnt.MEBS.commodity.action;

import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.commodity.model.TakeFee;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.commodity.service.SpecialTakeFeeService;
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

@Component("specialTakeFeeAction")
@Scope("request")
public class SpecialTakeFeeAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(SpecialTakeFeeAction.class);
  @Autowired
  @Qualifier("specialTakeFeeService")
  private SpecialTakeFeeService specialTakeFeeService;
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
  @Resource(name="takeFeeMap")
  private Map takeFeeMap;
  @Resource(name="feeAlgrMap")
  private Map feeAlgrMap;
  
  public Map getFeeAlgrMap()
  {
    return this.feeAlgrMap;
  }
  
  public Map getTakeFeeMap()
  {
    return this.takeFeeMap;
  }
  
  public InService getService()
  {
    return this.specialTakeFeeService;
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
        TakeFee takeFee = (TakeFee)this.obj;
        takeFee.setM_FirmId(primarys[0]);
        takeFee.setCommodityId(primarys[1]);
        Clone clone = getService().get(takeFee);
        
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(clone);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        this.logger.debug("enter delete operateLog:" + this.obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        resultValue = getService().delete(takeFee);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter memberTakeFeeParameters");
    this.request.setAttribute("takeFeeMap", this.takeFeeMap);
    this.request.setAttribute("feeAlgrMap", this.feeAlgrMap);
  }
}
