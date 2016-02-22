package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class NotTradeDay
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049926L;
  private Integer ID;
  private List weekList = new ArrayList();
  private List dayList = new ArrayList();
  private Date modifyTime;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public List getDayList()
  {
    return this.dayList;
  }
  
  public void setDayList(List dayList)
  {
    this.dayList = dayList;
  }
  
  public Integer getID()
  {
    return this.ID;
  }
  
  public void setID(Integer id)
  {
    this.ID = id;
  }
  
  public Date getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }
  
  public List getWeekList()
  {
    return this.weekList;
  }
  
  public void setWeekList(List weekList)
  {
    this.weekList = weekList;
  }
}
