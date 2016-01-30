package gnnt.MEBS.integrated.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class StockOperation
  extends StandardModel
{
  private static final long serialVersionUID = 4901122171733186296L;
  @ClassDiscription(name="关联仓单", description="对应仓单ID")
  private Stock stock;
  @ClassDiscription(name="仓单业务编号", description="")
  private int operationId;
  
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
    return Integer.valueOf(this.operationId);
  }
  
  public void setOperationId(Integer paramInteger)
  {
    this.operationId = paramInteger.intValue();
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
