package gnnt.mebsv.hqservice.model.pojo;

import java.sql.Date;

public class IndustryVO
{
  private String IndustryCode;
  private String IndustryName;
  private Date UpdateTime;

  public String getIndustryCode()
  {
    return this.IndustryCode;
  }

  public void setIndustryCode(String paramString)
  {
    this.IndustryCode = paramString;
  }

  public String getIndustryName()
  {
    return this.IndustryName;
  }

  public void setIndustryName(String paramString)
  {
    this.IndustryName = paramString;
  }

  public Date getUpdateTime()
  {
    return this.UpdateTime;
  }

  public void setUpdateTime(Date paramDate)
  {
    this.UpdateTime = paramDate;
  }

  public String toString()
  {
    return "industryCode:  " + this.IndustryCode + "   industryCode:  " + this.IndustryCode + "  updateTime:" + this.UpdateTime;
  }
}