package gnnt.MEBS.commodity.action;

import gnnt.MEBS.account.model.MemberInfo;
import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.commodity.model.Commodity;
import gnnt.MEBS.commodity.model.QuotePoint;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.commodity.service.QuotePointService;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
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

@Component("quotePointAction")
@Scope("request")
public class QuotePointAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(QuotePointAction.class);
  @Autowired
  @Qualifier("quotePointService")
  private QuotePointService quotePointService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  @Resource(name="quotePointBMap")
  private Map quotePointBMap;
  @Resource(name="firmDisMap")
  private Map firmDisMap;
  @Resource(name="delayFeeAlgrMap")
  private Map delayFeeAlgrMap;
  
  public Map getDelayFeeAlgrMap()
  {
    return this.delayFeeAlgrMap;
  }
  
  public Map getFirmDisMap()
  {
    return this.firmDisMap;
  }
  
  public Map getQuotePointBMap()
  {
    return this.quotePointBMap;
  }
  
  public InService getService()
  {
    return this.quotePointService;
  }
  
  public String forwardAdd()
  {
    List<MemberInfo> memberList = this.memberInfoService.getList(null, null);
    List<Commodity> commodityList = this.commodityService.getList(null, null);
    this.request.setAttribute("memberList", memberList);
    this.request.setAttribute("commodityList", commodityList);
    return getReturnValue();
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
        QuotePoint quotePoint = (QuotePoint)this.obj;
        quotePoint.setM_firmId(primarys[0]);
        quotePoint.setCommodityId(primarys[1]);
        Clone clone = getService().get(quotePoint);
        
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(clone);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        this.logger.debug("enter delete operateLog:" + this.obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        resultValue = getService().delete(quotePoint);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter QuotePointParameters");
    this.request.setAttribute("quotePointBMap", this.quotePointBMap);
    this.request.setAttribute("firmDisMap", this.firmDisMap);
    this.request.setAttribute("delayFeeAlgrMap", this.delayFeeAlgrMap);
  }
}
