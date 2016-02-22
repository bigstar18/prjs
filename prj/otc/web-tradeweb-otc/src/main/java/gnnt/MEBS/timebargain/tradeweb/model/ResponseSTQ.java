package gnnt.MEBS.timebargain.tradeweb.model;

import java.util.List;

public class ResponseSTQ
  extends Response
{
  private String curTime;
  private String curDate;
  private long currentTimeMillis;
  private long lastID;
  private byte newTrade;
  private long tradeTotalCount;
  private String tradeDate;
  private int sysStatus;
  private List<Trade> lst;
  
  public ResponseSTQ()
  {
    setCMD((short)2);
  }
  
  public String getCurTime()
  {
    return this.curTime;
  }
  
  public void setCurTime(String curTime)
  {
    this.curTime = curTime;
  }
  
  public String getCurDate()
  {
    return this.curDate;
  }
  
  public void setCurDate(String curDate)
  {
    this.curDate = curDate;
  }
  
  public long getCurrentTimeMillis()
  {
    return this.currentTimeMillis;
  }
  
  public void setCurrentTimeMillis(long currentTimeMillis)
  {
    this.currentTimeMillis = currentTimeMillis;
  }
  
  public long getLastID()
  {
    return this.lastID;
  }
  
  public void setLastID(long lastID)
  {
    this.lastID = lastID;
  }
  
  public byte getNewTrade()
  {
    return this.newTrade;
  }
  
  public void setNewTrade(byte newTrade)
  {
    this.newTrade = newTrade;
  }
  
  public long getTradeTotalCount()
  {
    return this.tradeTotalCount;
  }
  
  public void setTradeTotalCount(long tradeTotalCount)
  {
    this.tradeTotalCount = tradeTotalCount;
  }
  
  public String getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setTradeDate(String tradeDate)
  {
    this.tradeDate = tradeDate;
  }
  
  public List<Trade> getLst()
  {
    return this.lst;
  }
  
  public void setLst(List<Trade> lst)
  {
    this.lst = lst;
  }
  
  public int getSysStatus()
  {
    return this.sysStatus;
  }
  
  public void setSysStatus(int sysStatus)
  {
    this.sysStatus = sysStatus;
  }
}
