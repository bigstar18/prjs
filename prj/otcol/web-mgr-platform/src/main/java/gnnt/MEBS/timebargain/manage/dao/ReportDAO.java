package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.StatQuery;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract interface ReportDAO
  extends DAO
{
  public abstract List getAlarm(QueryConditions paramQueryConditions);
  
  public abstract List getHisOrders(QueryConditions paramQueryConditions);
  
  public abstract List getHisHoldDetail(StatQuery paramStatQuery);
  
  public abstract Map getAccount(QueryConditions paramQueryConditions);
  
  public abstract Map getAccountUpdate(QueryConditions paramQueryConditions);
  
  public abstract Map getAccountUpdate(QueryConditions paramQueryConditions, String paramString);
  
  public abstract List getAccountList(QueryConditions paramQueryConditions);
  
  public abstract List getBreed(QueryConditions paramQueryConditions);
  
  public abstract List getFeeMonth(QueryConditions paramQueryConditions);
  
  public abstract List getFeeMonth1(QueryConditions paramQueryConditions);
  
  public abstract List getCmdtyHold(StatQuery paramStatQuery);
  
  public abstract List getClose_PL(QueryConditions paramQueryConditions);
  
  public abstract List getTrades(QueryConditions paramQueryConditions);
  
  public abstract List getFunds(QueryConditions paramQueryConditions);
  
  public abstract Date getMaxClearDateByTable(String paramString);
  
  public abstract List getSortFund(QueryConditions paramQueryConditions);
  
  public abstract List getReportByTypeView(QueryConditions paramQueryConditions, String paramString1, String paramString2);
  
  public abstract String getReportByTypeM_CustomerID(String paramString);
  
  public abstract List getReportByTypeTrade(QueryConditions paramQueryConditions, String paramString1, String paramString2);
  
  public abstract List getReportByTypeHold(QueryConditions paramQueryConditions, String paramString1, String paramString2);
  
  public abstract List getReportByTypeMoney(QueryConditions paramQueryConditions, String paramString1, String paramString2);
  
  public abstract List getWeek1Detail(QueryConditions paramQueryConditions, String paramString);
  
  public abstract List getHoldDetailOther(StatQuery paramStatQuery, String paramString);
  
  public abstract List getHoldDetailOther1(StatQuery paramStatQuery, String paramString);
}
