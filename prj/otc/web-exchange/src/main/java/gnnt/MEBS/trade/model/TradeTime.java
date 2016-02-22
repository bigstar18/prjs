package gnnt.MEBS.trade.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.util.Date;

public class TradeTime
  extends Clone
{
  private Long sectionId;
  private String name;
  private String startTime;
  private String endTime;
  private Integer status;
  private Date modifyTime = new Date();
  private String startDate;
  private String endDate;
  
  @ClassDiscription(name="交易节编号", key=true, keyWord=true)
  public Long getSectionId()
  {
    return this.sectionId;
  }
  
  public void setSectionId(Long sectionId)
  {
    this.sectionId = sectionId;
  }
  
  @ClassDiscription(name="交易节名称", keyWord=true)
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setStartTime(String startTime)
  {
    this.startTime = startTime;
  }
  
  public void setEndTime(String endTime)
  {
    this.endTime = endTime;
  }
  
  @ClassDiscription(name="交易结束时间")
  public String getEndTime()
  {
    return this.endTime;
  }
  
  @ClassDiscription(name="交易开始时间")
  public String getStartTime()
  {
    return this.startTime;
  }
  
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="无效"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="正常")})
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
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
  
  @ClassDiscription(name="交易开始日期")
  public String getStartDate()
  {
    return this.startDate;
  }
  
  public void setStartDate(String startDate)
  {
    this.startDate = startDate;
  }
  
  @ClassDiscription(name="交易结束日期")
  public String getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(String endDate)
  {
    this.endDate = endDate;
  }
  
  public Long getId()
  {
    return this.sectionId;
  }
  
  public void setPrimary(String primary)
  {
    this.sectionId = Long.valueOf(Long.parseLong(primary));
  }
}
