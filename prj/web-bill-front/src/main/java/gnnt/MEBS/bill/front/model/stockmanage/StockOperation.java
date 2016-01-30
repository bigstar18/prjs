package gnnt.MEBS.bill.front.model.stockmanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;

public class StockOperation
  extends StandardModel
{
  private static final long serialVersionUID = 4901122171733186296L;
  @ClassDiscription(name="关联仓单", description="对应仓单编号")
  private Stock stock;
  @ClassDiscription(name="仓单业务号", description="")
  private Integer operationId;
  
  public Stock getStock()
  {
    return this.stock;
  }
  
  public void setStock(Stock paramStock)
  {
    this.stock = paramStock;
  }
  
  public Integer getOperationId()
  {
    return this.operationId;
  }
  
  public void setOperationId(Integer paramInteger)
  {
    this.operationId = paramInteger;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
