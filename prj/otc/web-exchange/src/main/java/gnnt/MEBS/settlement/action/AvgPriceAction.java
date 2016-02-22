package gnnt.MEBS.settlement.action;

import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.query.hibernate.QueryHelper;
import gnnt.MEBS.base.util.WebUtils;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.settlement.model.AvgPrice;
import gnnt.MEBS.settlement.service.AvgPriceService;
import gnnt.MEBS.trade.model.SystemStatus;
import gnnt.MEBS.trade.service.AgencyService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class AvgPriceAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(AvgPriceAction.class);
  @Autowired
  @Qualifier("avgPriceService")
  private AvgPriceService avgPriceService;
  @Autowired
  @Qualifier("agencyService")
  private AgencyService agencyService;
  String begTime;
  String endTime;
  
  public String getBegTime()
  {
    return this.begTime;
  }
  
  public void setBegTime(String begTime)
  {
    this.begTime = begTime;
  }
  
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
  
  public InService getService()
  {
    return this.avgPriceService;
  }
  
  public String getAvg()
  {
    QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(this.request);
    SystemStatus systemStatus = (SystemStatus)this.agencyService.getList(null, null).get(0);
    Date tradeDate = systemStatus.getTradeDate();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String date = df.format(tradeDate);
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    









    Object[] avgPriceArray = { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) };
    List<AvgPrice> resultList = null;
    if (qc != null)
    {
      avgPriceArray = this.avgPriceService.getAvg(qc, null);
      resultList = this.avgPriceService.getList(qc, null);
    }
    this.request.setAttribute(ActionConstant.OLDPARAMS, WebUtils.getParametersStartingWith(this.request, ActionConstant.GNNT_));
    this.request.setAttribute("avgPriceArray", avgPriceArray);
    this.request.setAttribute("tradeDate", tradeDate);
    this.request.setAttribute("resultList", resultList);
    return getReturnValue();
  }
}
