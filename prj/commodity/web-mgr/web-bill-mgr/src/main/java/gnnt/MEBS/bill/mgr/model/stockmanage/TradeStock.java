package gnnt.MEBS.bill.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class TradeStock
  extends StandardModel
{
  private static final long serialVersionUID = 1247007463542129132L;
  @ClassDiscription(name="交收仓单编号", description="")
  private Long tradeStockId;
  @ClassDiscription(name="关联仓单", description="对应仓单ID")
  private Stock stock;
  @ClassDiscription(name="合同号", description="")
  private String tradeNo;
  @ClassDiscription(name="关联仓单", description="")
  private Integer moduleId;
  @ClassDiscription(name="创建时间", description="")
  private Date createTime;
  @ClassDiscription(name="释放时间", description="")
  private Date releaseTime;
  @ClassDiscription(name="状态", description="状态 0:仓单使用中 1：交易成功仓单释放状态")
  private Integer status;
  
  public Long getTradeStockId()
  {
    return this.tradeStockId;
  }
  
  public void setTradeStockId(Long paramLong)
  {
    this.tradeStockId = paramLong;
  }
  
  public Stock getStock()
  {
    return this.stock;
  }
  
  public void setStock(Stock paramStock)
  {
    this.stock = paramStock;
  }
  
  public String getTradeNo()
  {
    return this.tradeNo;
  }
  
  public void setTradeNo(String paramString)
  {
    this.tradeNo = paramString;
  }
  
  public Integer getModuleId()
  {
    return this.moduleId;
  }
  
  public void setModuleId(Integer paramInteger)
  {
    this.moduleId = paramInteger;
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
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer paramInteger)
  {
    this.status = paramInteger;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("tradeStockId", this.tradeStockId);
  }
}
