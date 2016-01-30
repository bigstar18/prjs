package gnnt.mebsv.hqservice.model.pojo;

import java.sql.Date;

public class AddrVO
{
  private String addrCode;
  private String addrName;
  private Date UpdateTime;

  public String getAddrCode()
  {
    return this.addrCode;
  }

  public void setAddrCode(String paramString)
  {
    this.addrCode = paramString;
  }

  public String getAddrName()
  {
    return this.addrName;
  }

  public void setAddrName(String paramString)
  {
    this.addrName = paramString;
  }

  public Date getUpdateTime()
  {
    return this.UpdateTime;
  }

  public void setUpdateTime(Date paramDate)
  {
    this.UpdateTime = paramDate;
  }
}