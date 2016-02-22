package gnnt.MEBS.commodity.action;

import gnnt.MEBS.account.service.SpecialMemberService;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.commodity.service.SpecialMemberQuotePointService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("specialMemberQuotePointAction")
@Scope("request")
public class SpecialMemberQuotePointAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(SpecialMemberQuotePointAction.class);
  @Autowired
  @Qualifier("specialMemberQuotePointService")
  private SpecialMemberQuotePointService specialQuotePointService;
  @Autowired
  @Qualifier("specialMemberService")
  private SpecialMemberService specialMemberService;
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
    return this.specialQuotePointService;
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter specialQuotePointParameters");
    this.request.setAttribute("quotePointBMap", this.quotePointBMap);
    this.request.setAttribute("firmDisMap", this.firmDisMap);
    this.request.setAttribute("delayFeeAlgrMap", this.delayFeeAlgrMap);
  }
}
