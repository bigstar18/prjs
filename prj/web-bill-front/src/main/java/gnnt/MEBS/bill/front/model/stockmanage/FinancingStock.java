package gnnt.MEBS.bill.front.model.stockmanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.util.Date;

public class FinancingStock
  extends StandardModel
{
  private static final long serialVersionUID = -2642416424582870759L;
  @ClassDiscription(name="融资仓单号", description="")
  private Long financingStockId;
  @ClassDiscription(name="关联仓单", description="融资对应的仓单号")
  private Stock stock;
  @ClassDiscription(name="状态", description="状态：Y：有效  N：无效")
  private String status;
  @ClassDiscription(name="创建时间", description="")
  private Date createTime;
  @ClassDiscription(name="释放时间", description="")
  private Date releaseTime;
  
  public Long getFinancingStockId()
  {
    return this.financingStockId;
  }
  
  public void setFinancingStockId(Long paramLong)
  {
    this.financingStockId = paramLong;
  }
  
  public Stock getStock()
  {
    return this.stock;
  }
  
  public void setStock(Stock paramStock)
  {
    this.stock = paramStock;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
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
    return new StandardModel.PrimaryInfo("financingStockId", this.financingStockId);
  }
}
