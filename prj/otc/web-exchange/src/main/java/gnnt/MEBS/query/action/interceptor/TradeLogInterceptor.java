package gnnt.MEBS.query.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.report.service.TradeDateListService;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TradeLogInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(TradeLogInterceptor.class);
  @Autowired
  @Qualifier("tradeDateListService")
  protected TradeDateListService tradeDateListService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String date = df.format(new Date());
    String startDate = request.getParameter(ActionConstant.GNNT_ + "primary.operateTime[>=][date]");
    String endDate = request.getParameter(ActionConstant.GNNT_ + "primary.operateTime[<=][date]");
    if ((startDate == null) && (endDate == null)) {
      request.setAttribute(ActionConstant.GNNT_ + "primary.operateTime[=][date]", date);
    }
    String result = invocation.invoke();
    return result;
  }
}
