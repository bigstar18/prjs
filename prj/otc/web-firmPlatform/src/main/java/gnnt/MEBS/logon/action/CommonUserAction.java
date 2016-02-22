package gnnt.MEBS.logon.action;

import gnnt.MEBS.account.model.Trader;
import gnnt.MEBS.account.service.TraderService;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.member.ActiveUser.TraderInfo;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.model.Firm;
import gnnt.MEBS.settlement.service.FirmService;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class CommonUserAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(CommonUserAction.class);
  @Autowired
  @Qualifier("traderService")
  private TraderService traderService;
  @Autowired
  @Qualifier("firmService")
  protected FirmService firmService;
  @Resource(name="returnOperationMap")
  protected Map<String, String> returnOperationMap;
  
  public InService getService()
  {
    return null;
  }
  
  public FirmService getFirmService()
  {
    return this.firmService;
  }
  
  public String commonUserLogon()
  {
    this.logger.debug("//--[CommonUserController]--enter commonUserLogon()---//");
    String result = "";
    String username = this.request.getParameter("username");
    String password = this.request.getParameter("pwd");
    LogonManager manager = LogonManager.getInstance();
    TraderInfo trader = manager.logon(username, password, "", this.request.getRemoteAddr());
    


    long sessionID = trader.auSessionId;
    

    HttpServletResponse response = ServletActionContext.getResponse();
    if (sessionID > 0L)
    {
      String firmId = trader.firmId;
      String traderId = trader.traderId;
      Trader trade = (Trader)this.traderService.getById(traderId);
      Firm firm = (Firm)this.firmService.getById(trade.getFirmID());
      String id = "C";
      if ("M".equals(firm.getFirmType()))
      {
        id = "M";
        this.request.getSession().setAttribute("MEMBERNO", firm.getFirmId());
        this.request.getSession().setAttribute("MEMBERNAME", firm.getFirmName());
      }
      this.request.getSession().setAttribute("TYPE", id);
      this.request.getSession().setAttribute("FIRMID", firmId);
      this.request.getSession().setAttribute("TRADERID", traderId);
      this.request.getSession().setAttribute("LOGINID", new Long(sessionID));
      this.request.getSession().setAttribute("username", username);
      this.request.getSession().setMaxInactiveInterval(60000);
      result = "success";
    }
    else
    {
      String resultMsg = (String)this.returnOperationMap.get(sessionID);
      this.request.setAttribute(ActionConstant.RESULTMSG, resultMsg);
      this.request.setAttribute(ActionConstant.RESULTVAULE, Long.valueOf(sessionID));
      result = "error";
    }
    return result;
  }
}
