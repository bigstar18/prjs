package gnnt.MEBS.trade.model.vo;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.base.model.inner.ClassStatus;
import java.util.Date;

public class TradeManageVO
  extends Clone
{
  private Date tradeDate;
  private Integer status;
  private Integer sectionId;
  private String node;
  private String recoverTime;
  public int runMode;
  public String sysDate;
  
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
  public String getNode()
  {
    return this.node;
  }
  
  public void setNode(String node)
  {
    this.node = node;
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
  
  public Integer getRunMode()
  {
    return Integer.valueOf(this.runMode);
  }
  
  public void setRunMode(int runMode)
  {
    this.runMode = runMode;
  }
  
  public String getSysDate()
  {
    return this.sysDate;
  }
  
  public void setSysDate(String sysdate)
  {
    this.sysDate = sysdate;
  }
  
  public Date getId()
  {
    return this.tradeDate;
  }
  
  public void setPrimary(String primary) {}
}
