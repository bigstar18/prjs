package gnnt.MEBS.timebargain.tradeweb.model;

public class RequestSTQ
  extends Request
{
  private long lastID;
  private long tradeCount;
  private byte curLogon;
  private String agencyNO;
  private String phonePWD;
  
  public RequestSTQ()
  {
    setCMD((short)2);
  }
  
  public long getLastID()
  {
    return this.lastID;
  }
  
  public void setLastID(long lastID)
  {
    this.lastID = lastID;
  }
  
  public long getTradeCount()
  {
    return this.tradeCount;
  }
  
  public void setTradeCount(long tradeCount)
  {
    this.tradeCount = tradeCount;
  }
  
  public byte getCurLogon()
  {
    return this.curLogon;
  }
  
  public void setCurLogon(byte curLogon)
  {
    this.curLogon = curLogon;
  }
  
  public String getAgencyNO()
  {
    return this.agencyNO;
  }
  
  public void setAgencyNO(String agencyNO)
  {
    this.agencyNO = agencyNO;
  }
  
  public String getPhonePWD()
  {
    return this.phonePWD;
  }
  
  public void setPhonePWD(String phonePWD)
  {
    this.phonePWD = phonePWD;
  }
}
