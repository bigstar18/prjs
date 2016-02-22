package gnnt.MEBS.commodity.action;

import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.packaging.action.BaseAction;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BaseCommodityAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(BaseCommodityAction.class);
  @Autowired
  @Qualifier("commodityService")
  protected CommodityService commodityService;
  @Resource(name="commodityStatusMap")
  private Map commodityStatusMap;
  
  public Map getCommodityStatusMap()
  {
    return this.commodityStatusMap;
  }
  
  public String list()
  {
    this.logger.debug("enter list");
    List commodityList = this.commodityService.getList(null, null);
    this.request.setAttribute("commodityList", commodityList);
    return super.list();
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter BaseCommodityParameters");
    this.request.setAttribute("commodityStatusMap", this.commodityStatusMap);
  }
}
