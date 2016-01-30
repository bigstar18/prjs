package gnnt.MEBS.bill.core.vo;

import gnnt.MEBS.bill.core.po.StockPO;
import java.util.List;

public class FrozenStockOutResultVO
  extends ResultVO
{
  private static final long serialVersionUID = 1623545512672815416L;
  private List<StockPO> stockList;
  
  public List<StockPO> getStockList()
  {
    return this.stockList;
  }
  
  public void setStockList(List<StockPO> paramList)
  {
    this.stockList = paramList;
  }
}
