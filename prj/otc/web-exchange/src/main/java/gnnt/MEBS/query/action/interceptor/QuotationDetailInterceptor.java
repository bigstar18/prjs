package gnnt.MEBS.query.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.report.service.TradeDateListService;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class QuotationDetailInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(QuotationDetailInterceptor.class);
  @Autowired
  @Qualifier("tradeDateListService")
  protected TradeDateListService tradeDateListService;
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    List<Map<String, Timestamp>> tradeDateList = this.tradeDateListService.getList(null);
    Timestamp date1 = (Timestamp)((Map)tradeDateList.get(0)).get("a");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String date = df.format(date1);
    request.setAttribute(ActionConstant.GNNT_ + "primary.occurTime[=][date]", date);
    String result = invocation.invoke();
    return result;
  }
}
