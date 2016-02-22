package gnnt.MEBS.commodity.action;

import gnnt.MEBS.commodity.service.CommodityFeeService;
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

@Component("commodityFeeAction")
@Scope("request")
public class CommodityFeeAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CommodityFeeAction.class);
  @Autowired
  @Qualifier("commodityFeeService")
  private CommodityFeeService commodityFeeService;
  @Resource(name="commodityFeeAlgrMap")
  private Map commodityFeeAlgrMap;
  @Resource(name="commodityFeeModeMap")
  private Map commodityFeeModeMap;
  @Resource(name="firmDisMap")
  private Map firmDisMap;
  
  public Map getFirmDisMap()
  {
    return this.firmDisMap;
  }
  
  public Map getCommodityFeeAlgrMap()
  {
    return this.commodityFeeAlgrMap;
  }
  
  public Map getCommodityFeeModeMap()
  {
    return this.commodityFeeModeMap;
  }
  
  public InService getService()
  {
    return this.commodityFeeService;
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter commidityFeeParameters");
    this.request.setAttribute("commodityFeeAlgrMap", this.commodityFeeAlgrMap);
    this.request.setAttribute("commodityFeeModeMap", this.commodityFeeModeMap);
    this.request.setAttribute("firmDisMap", this.firmDisMap);
  }
}
