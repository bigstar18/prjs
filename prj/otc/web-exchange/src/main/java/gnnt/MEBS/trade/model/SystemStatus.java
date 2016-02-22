package gnnt.MEBS.trade.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SystemStatus
  extends Clone
{
  private Date tradeDate;
  private Integer status;
  private Integer sectionId;
  private String note;
  private String recoverTime;
  private Date lastTradeDate;
  private Date endDate;
  private Date nextTradeDate;
  
  public Date getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate)
  {
    this.endDate = endDate;
  }
  
  public Date getNextTradeDate()
  {
    return this.nextTradeDate;
  }
  
  public void setNextTradeDate(Date nextTradeDate)
  {
    this.nextTradeDate = nextTradeDate;
  }
  
  @ClassDiscription(name="上一交易日")
  public Date getLastTradeDate()
  {
    return this.lastTradeDate;
  }
  
  public void setLastTradeDate(Date lastTradeDate)
  {
    this.lastTradeDate = lastTradeDate;
  }
  
  public SystemStatus() {}
  
  public SystemStatus(Integer status)
  {
    this.status = status;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  @ClassDiscription(name="交易日期", key=true, keyWord=true)
  public Date getTradeDate()
  {
    return this.tradeDate;
  }
  
  public void setTradeDate(Date tradeDate)
  {
    this.tradeDate = tradeDate;
  }
  
  @ClassDiscription(name="状态", isStatus=true)
  @ClassStatus(status={@gnnt.MEBS.base.model.inner.StatusDiscription(key="0", value="初始化完成"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="1", value="闭市状态"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="2", value="结算中"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="3", value="财务结算完成"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="4", value="暂停交易"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="5", value="交易中"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="6", value="节间休息"), @gnnt.MEBS.base.model.inner.StatusDiscription(key="7", value="交易结束")})
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
  {
    this.status = status;
  }
  
  @ClassDiscription(name="所处交易节")
  public Integer getSectionId()
  {
    return this.sectionId;
  }
  
  public void setSectionId(Integer sectionId)
  {
    this.sectionId = sectionId;
  }
  
  @ClassDiscription(name="备注")
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  @ClassDiscription(name="暂停后自动恢复时间")
  public String getRecoverTime()
  {
    return this.recoverTime;
  }
  
  public void setRecoverTime(String recoverTime)
  {
    this.recoverTime = recoverTime;
  }
  
  public Date getId()
  {
    return this.tradeDate;
  }
  
  public void setPrimary(String primary) {}
}
