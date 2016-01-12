package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TradeTime extends StandardModel
{

  @ClassDiscription(name="", description="")
  private Integer sectionID;

  @ClassDiscription(name="", description="")
  private String name;

  @ClassDiscription(name="", description="")
  private String startTime;

  @ClassDiscription(name="", description="")
  private String endTime;

  @ClassDiscription(name="", description="")
  private Short status;

  @ClassDiscription(name="", description="")
  private Date modifyTime;

  @ClassDiscription(name="", description="")
  private String startDate;

  @ClassDiscription(name="", description="")
  private String endDate;

  @ClassDiscription(name="", description="")
  private String bidStartDate;

  @ClassDiscription(name="", description="")
  private String bidEndDate;

  @ClassDiscription(name="", description="")
  private Short gatherBid;

  @ClassDiscription(name="", description="")
  private String bidStartTime;

  @ClassDiscription(name="", description="")
  private String bidEndTime;

  @ClassDiscription(name="", description="")
  private Set commodities = new HashSet();

  private Set breeds = new HashSet();

  public Set getCommodities()
  {
    return this.commodities;
  }

  public void setCommodities(Set commodities) {
    this.commodities = commodities;
  }

  public Set getBreeds()
  {
    return this.breeds;
  }

  public void setBreeds(Set breeds) {
    this.breeds = breeds;
  }

  public Integer getSectionID() {
    return this.sectionID;
  }

  public void setSectionID(Integer sectionID)
  {
    this.sectionID = sectionID;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getStartTime()
  {
    return this.startTime;
  }

  public void setStartTime(String startTime)
  {
    this.startTime = startTime;
  }

  public String getEndTime()
  {
    return this.endTime;
  }

  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }

  public Short getStatus()
  {
    return this.status;
  }

  public void setStatus(Short status)
  {
    this.status = status;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
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

  public Short getGatherBid()
  {
    return this.gatherBid;
  }

  public void setGatherBid(Short gatherBid)
  {
    this.gatherBid = gatherBid;
  }

  public String getBidStartTime()
  {
    return this.bidStartTime;
  }

  public void setBidStartTime(String bidStartTime)
  {
    this.bidStartTime = bidStartTime;
  }

  public String getBidEndTime()
  {
    return this.bidEndTime;
  }

  public void setBidEndTime(String bidEndTime)
  {
    this.bidEndTime = bidEndTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "sectionID", this.sectionID);
  }
}