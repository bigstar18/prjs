package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class LogValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long logid;
  public String logtype;
  public String logopr;
  public String logoprtype;
  public String logcontent;
  public int result;
  public String contentvalue;
  public Date logtime;
  public String ip;
  public String mark;
  
  public String getMark()
  {
    return this.mark;
  }
  
  public void setMark(String mark)
  {
    this.mark = mark;
  }
  
  public LogValue() {}
  
  public LogValue(String logopr, String logcontent, Date date)
  {
    this.logopr = logopr;
    this.logcontent = logcontent;
    this.logtime = date;
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
  
  public String getLogtype()
  {
    return this.logtype;
  }
  
  public void setLogtype(String logtype)
  {
    this.logtype = logtype;
  }
  
  public String getLogoprtype()
  {
    return this.logoprtype;
  }
  
  public void setLogoprtype(String logoprtype)
  {
    this.logoprtype = logoprtype;
  }
  
  public int getResult()
  {
    return this.result;
  }
  
  public void setResult(int result)
  {
    this.result = result;
  }
  
  public String getContentvalue()
  {
    return this.contentvalue;
  }
  
  public void setContentvalue(String contentvalue)
  {
    this.contentvalue = contentvalue;
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
  
  public Date getLogtime()
  {
    return this.logtime;
  }
  
  public void setLogtime(Date logtime)
  {
    this.logtime = logtime;
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
