package gnnt.MEBS.trade.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.trade.service.TQLogService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CommodityListInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(CommodityListInterceptor.class);
  @Autowired
  @Qualifier("tqLogService")
  private TQLogService tqLogService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    List commodityList = this.tqLogService.getExList();
    request.setAttribute("commodityList", commodityList);
    String result = invocation.invoke();
    return result;
  }
}
