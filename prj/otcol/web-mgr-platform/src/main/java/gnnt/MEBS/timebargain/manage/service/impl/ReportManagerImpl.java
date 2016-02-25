package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.ReportDAO;
import gnnt.MEBS.timebargain.manage.model.StatQuery;
import gnnt.MEBS.timebargain.manage.service.ReportManager;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReportManagerImpl
  extends BaseManager
  implements ReportManager
{
  private ReportDAO dao;
  
  public void setReportDAO(ReportDAO paramReportDAO)
  {
    this.dao = paramReportDAO;
  }
  
  public List getAlarm(QueryConditions paramQueryConditions)
  {
    return this.dao.getAlarm(paramQueryConditions);
  }
  
  public List getHisOrders(QueryConditions paramQueryConditions)
  {
    return this.dao.getHisOrders(paramQueryConditions);
  }
  
  public List getHisHoldDetail(StatQuery paramStatQuery)
  {
    return this.dao.getHisHoldDetail(paramStatQuery);
  }
  
  public Map getAccount(QueryConditions paramQueryConditions)
  {
    return this.dao.getAccount(paramQueryConditions);
  }
  
  public Map getAccountUpdate(QueryConditions paramQueryConditions)
  {
    return this.dao.getAccountUpdate(paramQueryConditions);
  }
  
  public Map getAccountUpdate(QueryConditions paramQueryConditions, String paramString)
  {
    return this.dao.getAccountUpdate(paramQueryConditions, paramString);
  }
  
  public List getAccountList(QueryConditions paramQueryConditions)
  {
    return this.dao.getAccountList(paramQueryConditions);
  }
  
  public List getBreed(QueryConditions paramQueryConditions)
  {
    return this.dao.getBreed(paramQueryConditions);
  }
  
  public List getFeeMonth(QueryConditions paramQueryConditions)
  {
    return this.dao.getFeeMonth(paramQueryConditions);
  }
  
  public List getFeeMonth1(QueryConditions paramQueryConditions)
  {
    return this.dao.getFeeMonth1(paramQueryConditions);
  }
  
  public List getCmdtyHold(StatQuery paramStatQuery)
  {
    return this.dao.getCmdtyHold(paramStatQuery);
  }
  
  public List getClose_PL(QueryConditions paramQueryConditions)
  {
    return this.dao.getClose_PL(paramQueryConditions);
  }
  
  public List getTrades(QueryConditions paramQueryConditions)
  {
    return this.dao.getTrades(paramQueryConditions);
  }
  
  public List getFunds(QueryConditions paramQueryConditions)
  {
    return this.dao.getFunds(paramQueryConditions);
  }
  
  public Date getMaxClearDateByTable(String paramString)
  {
    return this.dao.getMaxClearDateByTable(paramString);
  }
  
  public List getSortFund(QueryConditions paramQueryConditions)
  {
    return this.dao.getSortFund(paramQueryConditions);
  }
  
  public List getReportByTypeView(QueryConditions paramQueryConditions, String paramString1, String paramString2)
  {
    return this.dao.getReportByTypeView(paramQueryConditions, paramString1, paramString2);
  }
  
  public String getReportByTypeM_CustomerID(String paramString)
  {
    return this.dao.getReportByTypeM_CustomerID(paramString);
  }
  
  public List getReportByTypeTrade(QueryConditions paramQueryConditions, String paramString1, String paramString2)
  {
    return this.dao.getReportByTypeTrade(paramQueryConditions, paramString1, paramString2);
  }
  
  public List getReportByTypeHold(QueryConditions paramQueryConditions, String paramString1, String paramString2)
  {
    return this.dao.getReportByTypeHold(paramQueryConditions, paramString1, paramString2);
  }
  
  public List getReportByTypeMoney(QueryConditions paramQueryConditions, String paramString1, String paramString2)
  {
    return this.dao.getReportByTypeMoney(paramQueryConditions, paramString1, paramString2);
  }
  
  public List getWeek1Detail(QueryConditions paramQueryConditions, String paramString)
  {
    return this.dao.getWeek1Detail(paramQueryConditions, paramString);
  }
  
  public List getHoldDetailOther(StatQuery paramStatQuery, String paramString)
  {
    return this.dao.getHoldDetailOther(paramStatQuery, paramString);
  }
  
  public List getHoldDetailOther1(StatQuery paramStatQuery, String paramString)
  {
    return this.dao.getHoldDetailOther1(paramStatQuery, paramString);
  }
}
