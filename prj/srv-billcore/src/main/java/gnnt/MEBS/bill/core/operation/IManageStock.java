package gnnt.MEBS.bill.core.operation;

import gnnt.MEBS.bill.core.bo.StockOutApplyBO;
import gnnt.MEBS.bill.core.bo.StockOutAuditBO;
import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.po.StockPO;
import gnnt.MEBS.bill.core.vo.AddStockResultVO;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.bill.core.vo.StockQiantityResultVO;
import java.util.List;
import java.util.Map;

public abstract interface IManageStock
{
  public abstract AddStockResultVO addStock(StockPO paramStockPO, List<GoodsPropertyPO> paramList);
  
  public abstract ResultVO registerStock(String paramString);
  
  public abstract ResultVO dismantleStock(String paramString, Double[] paramArrayOfDouble);
  
  public abstract ResultVO dismantleStock(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract ResultVO logoutStock(String paramString);
  
  public abstract ResultVO stockOutApply(StockOutApplyBO paramStockOutApplyBO);
  
  public abstract ResultVO stockOutUpdate(StockOutApplyBO paramStockOutApplyBO);
  
  public abstract ResultVO stockOutAudit(StockOutAuditBO paramStockOutAuditBO);
  
  public abstract ResultVO withdrowStockOutApply(String paramString);
  
  public abstract ResultVO stockOut(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract List<String> getUnusedStocks(long paramLong, Map<String, String> paramMap, String paramString, double paramDouble);
  
  public abstract List<String> getUnusedStocks(int paramInt, String paramString);
  
  public abstract StockQiantityResultVO getQuantityByStockIDs(List<String> paramList);
}
