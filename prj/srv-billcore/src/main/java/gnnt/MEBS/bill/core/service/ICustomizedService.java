package gnnt.MEBS.bill.core.service;

import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.po.StockPO;
import gnnt.MEBS.bill.core.vo.AddStockResultVO;
import gnnt.MEBS.bill.core.vo.FrozenStockOutResultVO;
import java.util.List;

public abstract interface ICustomizedService
{
  public abstract AddStockResultVO addFrozenStock(StockPO paramStockPO, List<GoodsPropertyPO> paramList, int paramInt);
  
  public abstract FrozenStockOutResultVO frozenStockOut(String[] paramArrayOfString, double paramDouble, int paramInt, String paramString);
}
