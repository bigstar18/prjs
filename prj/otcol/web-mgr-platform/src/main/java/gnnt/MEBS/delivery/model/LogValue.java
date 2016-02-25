package gnnt.MEBS.delivery.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class LogValue
  extends Clone
{
  private long id;
  private int type;
  private String operator;
  private Date operatime;
  private String module;
  private String Content;
  private String matchId;
  
  public String getContent()
  {
    return this.Content;
  }
  
  public void setContent(String paramString)
  {
    this.Content = paramString;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public void setId(long paramLong)
  {
    this.id = paramLong;
  }
  
  public String getModule()
  {
    return this.module;
  }
  
  public void setModule(String paramString)
  {
    this.module = paramString;
  }
  
  public Date getOperatime()
  {
    return this.operatime;
  }
  
  public void setOperatime(Date paramDate)
  {
    this.operatime = paramDate;
  }
  
  public String getOperator()
  {
    return this.operator;
  }
  
  public void setOperator(String paramString)
  {
    this.operator = paramString;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
  }
  
  public String getMatchId()
  {
    return this.matchId;
  }
  
  public void setMatchId(String paramString)
  {
    this.matchId = paramString;
  }
}
