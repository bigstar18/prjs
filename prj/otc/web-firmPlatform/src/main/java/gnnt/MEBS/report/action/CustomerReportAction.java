package gnnt.MEBS.report.action;

import gnnt.MEBS.account.model.Trader;
import gnnt.MEBS.account.service.TraderService;
import gnnt.MEBS.settlement.model.Firm;
import gnnt.MEBS.settlement.service.FirmService;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class CustomerReportAction
  extends BaseReportAction
{
  @Resource(name="flagMap")
  private Map flagMap;
  @Resource(name="tradeMap")
  private Map tradeMap;
  @Resource(name="codeMap")
  private Map codeMap;
  @Resource(name="whetherMap")
  private Map whetherMap;
  @Autowired
  @Qualifier("traderService")
  private TraderService traderService;
  @Autowired
  @Qualifier("firmService")
  protected FirmService firmService;
  
  public Map getFlagMap()
  {
    return this.flagMap;
  }
  
  public Map getTradeMap()
  {
    return this.tradeMap;
  }
  
  public Map getCodeMap()
  {
    return this.codeMap;
  }
  
  public Map getWhetherMap()
  {
    return this.whetherMap;
  }
  
  public TraderService getTraderService()
  {
    return this.traderService;
  }
  
  public FirmService getFirmService()
  {
    return this.firmService;
  }
  
  public String forwardReport()
  {
    String tradeId = (String)this.request.getSession().getAttribute("username");
    Trader trader = (Trader)this.traderService.getById(tradeId);
    Firm firm = (Firm)this.firmService.getById(trader.getFirmID());
    this.request.getSession().setAttribute("type", firm.getFirmType());
    return "success";
  }
}
