package gnnt.MEBS.commodity.action;

import gnnt.MEBS.commodity.service.OrderPointService;
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

@Component("orderPointAction")
@Scope("request")
public class OrderPointAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(OrderPointAction.class);
  @Autowired
  @Qualifier("orderPointService")
  private OrderPointService orderPointService;
  @Resource(name="firmDisMap")
  private Map firmDisMap;
  
  public InService getService()
  {
    return this.orderPointService;
  }
  
  public Map getFirmDisMap()
  {
    return this.firmDisMap;
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter orderPointParameters");
    this.request.setAttribute("firmDisMap", this.firmDisMap);
  }
}
