package gnnt.MEBS.timebargain.manage.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TradeTime
  extends BaseObject
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Integer SectionID;
  private String Name;
  private String StartTime;
  private String EndTime;
  private Short Status;
  private String ModifyTime;
  private String crud = "";
  private Long BreedID;
  private String BreedName;
  private Short GatherBid;
  private String BidStartTime;
  private String BidEndTime;
  private Short id;
  private String[] week;
  private String day;
  
  public String getDay()
  {
    return this.day;
  }
  
  public void setDay(String paramString)
  {
    this.day = paramString;
  }
  
  public Short getId()
  {
    return this.id;
  }
  
  public void setId(Short paramShort)
  {
    this.id = paramShort;
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
  
  public Short getGatherBid()
  {
    return this.GatherBid;
  }
  
  public void setGatherBid(Short paramShort)
  {
    this.GatherBid = paramShort;
  }
  
  public Long getBreedID()
  {
    return this.BreedID;
  }
  
  public void setBreedID(Long paramLong)
  {
    this.BreedID = paramLong;
  }
  
  public String getBreedName()
  {
    return this.BreedName;
  }
  
  public void setBreedName(String paramString)
  {
    this.BreedName = paramString;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
  
  public String getEndTime()
  {
    return this.EndTime;
  }
  
  public void setEndTime(String paramString)
  {
    this.EndTime = paramString;
  }
  
  public Integer getSectionID()
  {
    return this.SectionID;
  }
  
  public void setSectionID(Integer paramInteger)
  {
    this.SectionID = paramInteger;
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
  
  public Short getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(Short paramShort)
  {
    this.Status = paramShort;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof TradeTime)) {
      return false;
    }
    TradeTime localTradeTime = (TradeTime)paramObject;
    return this.SectionID != null ? this.SectionID.equals(localTradeTime.SectionID) : localTradeTime.SectionID == null;
  }
  
  public int hashCode()
  {
    return this.SectionID != null ? this.SectionID.hashCode() : 0;
  }
}
