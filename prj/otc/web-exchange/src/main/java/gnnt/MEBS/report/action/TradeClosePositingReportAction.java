package gnnt.MEBS.report.action;

import gnnt.MEBS.base.query.jdbc.QueryConditions;
import gnnt.MEBS.base.query.jdbc.QueryHelper;
import gnnt.MEBS.report.service.TradeClosePositingReportService;
import gnnt.MEBS.report.service.TradeHoldPositingReportService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

public class TradeClosePositingReportAction
  extends BaseReportAction
{
  @Resource(name="flagMap")
  private Map flagMap;
  private List customerHoldList;
  private TradeClosePositingReportService tradeClosePositingReportService;
  private TradeHoldPositingReportService tradeHoldPositingReportService;
  
  public Map getFlagMap()
  {
    return this.flagMap;
  }
  
  public TradeHoldPositingReportService getTradeHoldPositingReportService()
  {
    return this.tradeHoldPositingReportService;
  }
  
  public void setTradeHoldPositingReportService(TradeHoldPositingReportService tradeHoldPositingReportService)
  {
    this.tradeHoldPositingReportService = tradeHoldPositingReportService;
  }
  
  public TradeClosePositingReportService getTradeClosePositingReportService()
  {
    return this.tradeClosePositingReportService;
  }
  
  public void setTradeClosePositingReportService(TradeClosePositingReportService tradeClosePositingReportService)
  {
    this.tradeClosePositingReportService = tradeClosePositingReportService;
  }
  
  public QueryConditions commonQueryConditions()
  {
    QueryConditions qc = QueryHelper.getQueryConditionsFromRequest(this.request);
    if (qc == null)
    {
      qc = new QueryConditions();
      qc.addCondition("1", "=", Integer.valueOf(1));
    }
    return qc;
  }
  
  public String tradeCustomerClosePositingList()
  {
    QueryConditions qc = commonQueryConditions();
    this.dataList = this.tradeClosePositingReportService.getCustomerCloseList(qc);
    this.customerHoldList = this.tradeHoldPositingReportService.getCustomerHoldList(qc);
    return getReturnValue();
  }
  
  public String tradeMemberClosePositingList()
  {
    QueryConditions qc = commonQueryConditions();
    this.dataList = this.tradeClosePositingReportService.getMemberCloseList(qc);
    return getReturnValue();
  }
  
  public String tradeMemberHoldPositingList()
  {
    QueryConditions qc = commonQueryConditions();
    this.dataList = this.tradeHoldPositingReportService.getMemberHOldList(qc);
    return getReturnValue();
  }
  
  public List getCustomerHoldList()
  {
    return this.customerHoldList;
  }
}
