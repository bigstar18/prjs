package gnnt.MEBS.report.action.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import gnnt.MEBS.account.model.Trader;
import gnnt.MEBS.account.service.TraderService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.settlement.model.Firm;
import gnnt.MEBS.settlement.service.FirmService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CustomerReportInterceptor
  extends AbstractInterceptor
{
  private final transient Log logger = LogFactory.getLog(CustomerReportInterceptor.class);
  @Autowired
  @Qualifier("traderService")
  private TraderService traderService;
  @Autowired
  @Qualifier("firmService")
  protected FirmService firmService;
  
  public TraderService getTraderService()
  {
    return this.traderService;
  }
  
  public FirmService getFirmService()
  {
    return this.firmService;
  }
  
  public String intercept(ActionInvocation invocation)
    throws Exception
  {
    HttpServletRequest request = ServletActionContext.getRequest();
    String tradeId = (String)request.getSession().getAttribute("username");
    Trader trader = (Trader)this.traderService.getById(tradeId);
    Firm firm = (Firm)this.firmService.getById(trader.getFirmID());
    String type = firm.getFirmType();
    if ("M".equals(type)) {
      request.setAttribute(ActionConstant.GNNT_ + "primary.memberNo[=][String]", firm.getFirmId());
    } else {
      request.setAttribute(ActionConstant.GNNT_ + "primary.customerNo[=]", trader.getFirmID());
    }
    this.logger.debug("primary.customerNo[=]:" + request.getSession().getAttribute("username"));
    String result = invocation.invoke();
    return result;
  }
}
