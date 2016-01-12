package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class DaySection extends StandardModel
{
  private static final Long serialVersionUID = Long.valueOf(-1571414825204234554L);

  @ClassDiscription(name="星期几", description="")
  private Integer weekDay;

  @ClassDiscription(name="交易节编号", description="")
  private Integer sectionID;

  @ClassDiscription(name="状态", description="")
  private Integer status;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  public Integer getWeekDay() { return this.weekDay; }

  public void setWeekDay(Integer weekDay)
  {
    this.weekDay = weekDay;
  }

  public Integer getSectionID()
  {
    return this.sectionID;
  }

  public void setSectionID(Integer sectionID) {
    this.sectionID = sectionID;
  }

  public Integer getStatus()
  {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}