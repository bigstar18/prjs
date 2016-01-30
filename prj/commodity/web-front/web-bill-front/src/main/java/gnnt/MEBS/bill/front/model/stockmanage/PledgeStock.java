package gnnt.MEBS.bill.front.model.stockmanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.util.Date;

public class PledgeStock
  extends StandardModel
{
  private static final long serialVersionUID = -2247283620860806533L;
  @ClassDiscription(name="卖仓单编号", description="")
  private Long pledgestock;
  @ClassDiscription(name="关联仓单", description="融资对应的仓单号")
  private Stock stock;
  @ClassDiscription(name="委托号", description="")
  private String orderId;
  @ClassDiscription(name="系统模块号", description="")
  private Integer moduleId;
  @ClassDiscription(name="卖仓单状态", description="卖仓单状态0:仓单使用中 1：交易成功仓单释放状态")
  private Integer status;
  @ClassDiscription(name="创建时间", description="")
  private Date createTime;
  @ClassDiscription(name="释放时间", description="")
  private Date releaseTime;
  
  public Long getPledgestock()
  {
    return this.pledgestock;
  }
  
  public void setPledgestock(Long paramLong)
  {
    this.pledgestock = paramLong;
  }
  
  public Stock getStock()
  {
    return this.stock;
  }
  
  public void setStock(Stock paramStock)
  {
    this.stock = paramStock;
  }
  
  public String getOrderId()
  {
    return this.orderId;
  }
  
  public void setOrderId(String paramString)
  {
    this.orderId = paramString;
  }
  
  public Integer getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(Integer paramInteger)
  {
    this.moduleId = paramInteger;
  }
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer paramInteger)
  {
    this.status = paramInteger;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public Date getReleaseTime()
  {
    return this.releaseTime;
  }
  
  public void setReleaseTime(Date paramDate)
  {
    this.releaseTime = paramDate;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("pledgestock", this.pledgestock);
  }
}
