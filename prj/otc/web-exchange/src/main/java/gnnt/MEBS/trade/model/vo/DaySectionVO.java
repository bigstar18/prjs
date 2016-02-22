package gnnt.MEBS.trade.model.vo;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.trade.model.DaySection;
import java.io.Serializable;
import java.util.List;

public class DaySectionVO
  extends Clone
{
  private String description;
  private List<DaySection> daySectionList;
  
  @ClassDiscription(name="每日交易节")
  public String getDescription()
  {
    String msg = "";
    if ((this.daySectionList != null) && (this.daySectionList.size() > 0)) {
      for (DaySection daySection : this.daySectionList)
      {
        String week = daySection.getWeekDay().intValue() - 1;
        if ("0".equals(week)) {
          week = "日";
        }
        if (daySection.getStatus().intValue() == 0) {
          msg = msg + "星期" + week + "交易节" + daySection.getSectionId() + ",";
        }
      }
    }
    return msg.substring(0, msg.length() - 1);
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public List<DaySection> getDaySectionList()
  {
    return this.daySectionList;
  }
  
  public void setDaySectionList(List<DaySection> daySectionList)
  {
    this.daySectionList = daySectionList;
  }
  
  public void setPrimary(String primary) {}
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
}
