package gnnt.MEBS.timebargain.manage.model;

import java.util.Date;

public class CommoditySettleProp
{
  private String commodityId;
  private int sectionId;
  private Date modifyTime;
  
  public String getCommodityId()
  {
    return this.commodityId;
  }
  
  public void setCommodityId(String paramString)
  {
    this.commodityId = paramString;
  }
  
  public int getSectionId()
  {
    return this.sectionId;
  }
  
  public void setSectionId(int paramInt)
  {
    this.sectionId = paramInt;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date paramDate)
  {
    this.modifyTime = paramDate;
  }
}
