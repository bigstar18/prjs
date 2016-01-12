package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;
import java.util.List;

public class NotTradeDay extends StandardModel
{

  @ClassDiscription(name="非交易日ID", description="")
  private Long id;

  @ClassDiscription(name="星期", description="")
  private String week;

  @ClassDiscription(name="日", description="")
  private String day;

  @ClassDiscription(name="最后一次修改时间", description="")
  private Date modifyTime;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getWeek() {
    return this.week;
  }
  public void setWeek(String week) {
    this.week = week;
  }
  public void setWeeks(List weeks) {
    StringBuilder sb = new StringBuilder();
    for (int k = 0; k < weeks.size(); k++) {
      if (k != weeks.size() - 1)
        sb.append(weeks.get(k)).append(",");
      else {
        sb.append(weeks.get(k));
      }
    }
    this.week = sb.toString();
  }

  public String getDay() {
    return this.day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public Date getModifyTime() {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}