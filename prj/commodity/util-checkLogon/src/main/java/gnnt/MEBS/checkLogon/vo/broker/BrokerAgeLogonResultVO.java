package gnnt.MEBS.checkLogon.vo.broker;

public class BrokerAgeLogonResultVO
{
  private int result;
  private String recode;
  private String message;
  private long sessionID;

  public int getResult()
  {
    return this.result;
  }

  public void setResult(int result)
  {
    this.result = result;
  }

  public String getRecode()
  {
    return this.recode;
  }

  public void setRecode(String recode)
  {
    this.recode = recode;
  }

  public String getMessage()
  {
    return this.message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public long getSessionID()
  {
    return this.sessionID;
  }

  public void setSessionID(long sessionID)
  {
    this.sessionID = sessionID;
  }
}