package gnnt.MEBS.timebargain.mgr.service;

import java.util.List;

public abstract interface BrokerCountService
{
  public abstract List brokerFundsTable(String paramString1, String paramString2);

  public abstract List brokerTradeTable(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);

  public abstract List historyBrokerTradeTable(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8);

  public abstract List brokerIndentTable(String paramString1, String paramString2, String paramString3);

  public abstract List historyBrokerIndentTable(String paramString1, String paramString2, String paramString3, String paramString4);
}