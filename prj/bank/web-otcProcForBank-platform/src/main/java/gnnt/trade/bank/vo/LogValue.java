package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class LogValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long logid;
  public String logopr;
  public String logcontent;
  public Date logdate;
  public String ip;
  
  public LogValue() {}
  
  public LogValue(String logopr, String logcontent, Date date)
  {
    this.logopr = logopr;
    this.logcontent = logcontent;
    this.logdate = date;
  }
  
  public long getLogid()
  {
    return this.logid;
  }
  
  public void setLogid(long logid)
  {
    this.logid = logid;
  }
  
  public String getLogopr()
  {
    return this.logopr;
  }
  
  public void setLogopr(String logopr)
  {
    this.logopr = logopr;
  }
  
  public String getLogcontent()
  {
    return this.logcontent;
  }
  
  public void setLogcontent(String logcontent)
  {
    this.logcontent = logcontent;
  }
  
  public Date getLogdate()
  {
    return this.logdate;
  }
  
  public void setLogdate(Date logdate)
  {
    this.logdate = logdate;
  }
  
  public void setIp(String ip)
  {
    this.ip = ip;
  }
  
  public String getIp()
  {
    return this.ip;
  }
}
