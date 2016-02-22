package gnnt.MEBS.commodity.action;

import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.commodity.model.HoldQty;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.commodity.service.MemberHoldQtyService;
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

@Component("memberHoldQtyAction")
@Scope("request")
public class MemberHoldQtyAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberHoldQtyAction.class);
  @Autowired
  @Qualifier("memberHoldQtyService")
  private MemberHoldQtyService memberHoldQtyService;
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Resource(name="firmDisMap")
  private Map firmDisMap;
  
  public Map getFirmDisMap()
  {
    return this.firmDisMap;
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter HoldQtyParameters");
    this.request.setAttribute("firmDisMap", this.firmDisMap);
  }
  
  public InService getService()
  {
    return this.memberHoldQtyService;
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
        HoldQty holdQuantily = (HoldQty)this.obj;
        holdQuantily.setFirmId(primarys[0]);
        holdQuantily.setCommodityId(primarys[1]);
        Clone clone = getService().get(holdQuantily);
        
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(clone);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        this.logger.debug("enter delete operateLog:" + this.obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        resultValue = getService().delete(holdQuantily);
      }
    } else {
      resultValue = -2;
    }
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
}
