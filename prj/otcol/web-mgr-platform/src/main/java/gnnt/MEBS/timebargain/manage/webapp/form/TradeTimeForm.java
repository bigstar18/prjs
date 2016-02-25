package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class TradeTimeForm
  extends BaseForm
  implements Serializable
{
  private String SectionID;
  private String Name;
  private String StartTime;
  private String EndTime;
  private String Status;
  private String ModifyTime;
  private String crud = "";
  private String BreedID;
  private String BreedName;
  private String GatherBid;
  private String BidStartTime;
  private String BidEndTime;
  private String id;
  private String[] week;
  private String day;
  private String tradeTimeType;
  
  public String getTradeTimeType()
  {
    return this.tradeTimeType;
  }
  
  public void setTradeTimeType(String paramString)
  {
    this.tradeTimeType = paramString;
  }
  
  public String getDay()
  {
    return this.day;
  }
  
  public void setDay(String paramString)
  {
    this.day = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String[] getWeek()
  {
    return this.week;
  }
  
  public void setWeek(String[] paramArrayOfString)
  {
    this.week = paramArrayOfString;
  }
  
  public String getBidEndTime()
  {
    return this.BidEndTime;
  }
  
  public void setBidEndTime(String paramString)
  {
    this.BidEndTime = paramString;
  }
  
  public String getBidStartTime()
  {
    return this.BidStartTime;
  }
  
  public void setBidStartTime(String paramString)
  {
    this.BidStartTime = paramString;
  }
  
  public String getGatherBid()
  {
    return this.GatherBid;
  }
  
  public void setGatherBid(String paramString)
  {
    this.GatherBid = paramString;
  }
  
  public String getBreedID()
  {
    return this.BreedID;
  }
  
  public void setBreedID(String paramString)
  {
    this.BreedID = paramString;
  }
  
  public String getBreedName()
  {
    return this.BreedName;
  }
  
  public void setBreedName(String paramString)
  {
    this.BreedName = paramString;
  }
  
  public String getEndTime()
  {
    return this.EndTime;
  }
  
  public void setEndTime(String paramString)
  {
    this.EndTime = paramString;
  }
  
  public String getSectionID()
  {
    return this.SectionID;
  }
  
  public void setSectionID(String paramString)
  {
    this.SectionID = paramString;
  }
  
  public String getModifyTime()
  {
    return this.ModifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.ModifyTime = paramString;
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public void setName(String paramString)
  {
    this.Name = paramString;
  }
  
  public String getStartTime()
  {
    return this.StartTime;
  }
  
  public void setStartTime(String paramString)
  {
    this.StartTime = paramString;
  }
  
  public String getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(String paramString)
  {
    this.Status = paramString;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
}
