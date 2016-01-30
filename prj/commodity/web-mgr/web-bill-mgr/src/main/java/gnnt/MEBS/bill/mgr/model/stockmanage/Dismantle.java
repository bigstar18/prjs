package gnnt.MEBS.bill.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class Dismantle
  extends StandardModel
{
  private static final long serialVersionUID = 5359175338209655252L;
  @ClassDiscription(name="拆单编号", description="")
  private Long dismantleId;
  @ClassDiscription(name="关联仓单", description="对应仓单编号")
  private Stock stock;
  @ClassDiscription(name="新仓单编号", description="拆单后的仓单编号")
  private String newStockId;
  @ClassDiscription(name="仓库仓单号", description="")
  private String realStockCode;
  @ClassDiscription(name="拆单数量", description="")
  private Double amount;
  @ClassDiscription(name="拆单申请时间", description="")
  private Date applyTime;
  @ClassDiscription(name="拆单处理时间", description="")
  private Date processTime;
  @ClassDiscription(name="拆单状态", description="拆单状态 0:申请中 1：拆单成功 2：拆单失败")
  private String status;
  
  public Long getDismantleId()
  {
    return this.dismantleId;
  }
  
  public void setDismantleId(Long paramLong)
  {
    this.dismantleId = paramLong;
  }
  
  public Stock getStock()
  {
    return this.stock;
  }
  
  public void setStock(Stock paramStock)
  {
    this.stock = paramStock;
  }
  
  public String getNewStockId()
  {
    return this.newStockId;
  }
  
  public void setNewStockId(String paramString)
  {
    this.newStockId = paramString;
  }
  
  public String getRealStockCode()
  {
    return this.realStockCode;
  }
  
  public void setRealStockCode(String paramString)
  {
    this.realStockCode = paramString;
  }
  
  public Double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(Double paramDouble)
  {
    this.amount = paramDouble;
  }
  
  public Date getApplyTime()
  {
    return this.applyTime;
  }
  
  public void setApplyTime(Date paramDate)
  {
    this.applyTime = paramDate;
  }
  
  public Date getProcessTime()
  {
    return this.processTime;
  }
  
  public void setProcessTime(Date paramDate)
  {
    this.processTime = paramDate;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("dismantleId", this.dismantleId);
  }
}
