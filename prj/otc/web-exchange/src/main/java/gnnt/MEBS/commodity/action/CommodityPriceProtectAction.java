package gnnt.MEBS.commodity.action;

import gnnt.MEBS.commodity.service.CommodityPriceProtectService;
import gnnt.MEBS.packaging.service.InService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("commodityPriceProtectAction")
@Scope("request")
public class CommodityPriceProtectAction
  extends BaseCommodityAction
{
  private final transient Log logger = LogFactory.getLog(CommodityPriceProtectAction.class);
  @Autowired
  @Qualifier("commodityPriceProtectService")
  private CommodityPriceProtectService commodityPriceProtectService;
  
  public InService getService()
  {
    return this.commodityPriceProtectService;
  }
}
