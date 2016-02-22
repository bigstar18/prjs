package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TradeTime
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049112L;
  private Integer sectionID;
  private String name;
  private String startTime;
  private String endTime;
  private Short status;
  private Short gatherBid;
  private String bidStartTime;
  private String bidEndTime;
  private Date modifyTime;
  private long startTimeMillis;
  private long endTimeMillis;
  private long bidStartTimeMillis;
  private long bidEndTimeMillis;
  private String startDate;
  private String endDate;
  private String bidStartDate;
  private String bidEndDate;
  public static final short GATHERBID_NOT = 0;
  public static final short GATHERBID_YES = 1;
  public static final short STATUS_INVALID = 0;
  public static final short STATUS_VALID = 1;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public Integer getSectionID()
  {
    return this.sectionID;
  }
  
  public void setSectionID(Integer sectionID)
  {
    this.sectionID = sectionID;
  }
  
  public String getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(String startTime)
  {
    this.startTime = startTime;
  }
  
  public Short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Short status)
  {
    this.status = status;
  }
  
  public String getBidEndTime()
  {
    return this.bidEndTime;
  }
  
  public void setBidEndTime(String bidEndTime)
  {
    this.bidEndTime = bidEndTime;
  }
  
  public String getBidStartTime()
  {
    return this.bidStartTime;
  }
  
  public void setBidStartTime(String bidStartTime)
  {
    this.bidStartTime = bidStartTime;
  }
  
  public Short getGatherBid()
  {
    return this.gatherBid;
  }
  
  public void setGatherBid(Short gatherBid)
  {
    this.gatherBid = gatherBid;
  }
  
  public long getBidEndTimeMillis()
  {
    return this.bidEndTimeMillis;
  }
  
  public void setBidEndTimeMillis(long bidEndTimeMillis)
  {
    this.bidEndTimeMillis = bidEndTimeMillis;
  }
  
  public long getBidStartTimeMillis()
  {
    return this.bidStartTimeMillis;
  }
  
  public void setBidStartTimeMillis(long bidStartTimeMillis)
  {
    this.bidStartTimeMillis = bidStartTimeMillis;
  }
  
  public long getEndTimeMillis()
  {
    return this.endTimeMillis;
  }
  
  public void setEndTimeMillis(long endTimeMillis)
  {
    this.endTimeMillis = endTimeMillis;
  }
  
  public long getStartTimeMillis()
  {
    return this.startTimeMillis;
  }
  
  public void setStartTimeMillis(long startTimeMillis)
  {
    this.startTimeMillis = startTimeMillis;
  }
  
  public String getStartDate()
  {
    return this.startDate;
  }
  
  public void setStartDate(String startDate)
  {
    this.startDate = startDate;
  }
  
  public String getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(String endDate)
  {
    this.endDate = endDate;
  }
  
  public String getBidStartDate()
  {
    return this.bidStartDate;
  }
  
  public void setBidStartDate(String bidStartDate)
  {
    this.bidStartDate = bidStartDate;
  }
  
  public String getBidEndDate()
  {
    return this.bidEndDate;
  }
  
  public void setBidEndDate(String bidEndDate)
  {
    this.bidEndDate = bidEndDate;
  }
}
