package gnnt.MEBS.timebargain.hqweb.dao;

public abstract interface QTAcquisitionDao
{
  public abstract String loadCommodity();
  
  public abstract String loadTradeTime();
  
  public abstract String getCommodityTradeSec();
}
