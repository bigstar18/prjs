package gnnt.MEBS.bill.core.operation;

import gnnt.MEBS.bill.core.vo.FinancingApplyVO;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.bill.core.vo.TransferGoodsVO;

public abstract interface IStockTrade
{
  public abstract ResultVO sellStock(int paramInt, String paramString1, String paramString2);
  
  public abstract void withdrawSellStock(int paramInt, String paramString);
  
  public abstract TransferGoodsVO sellStockToDelivery(int paramInt, String paramString1, String paramString2, String paramString3);
  
  public abstract TransferGoodsVO transferGoods(int paramInt, String paramString1, String[] paramArrayOfString, String paramString2);
  
  public abstract double realeseGoods(int paramInt, String paramString);
  
  public abstract double delivery(int paramInt, String paramString1, String paramString2, String paramString3);
  
  public abstract double delivery(int paramInt, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3);
  
  public abstract void stockChg(String[] paramArrayOfString, String paramString1, String paramString2);
  
  public abstract double realeseGoods(int paramInt, String paramString, String[] paramArrayOfString);
  
  public abstract long getFinancingStockID();
  
  public abstract FinancingApplyVO startFinancing(String paramString, long paramLong);
  
  public abstract ResultVO endFinancing(long paramLong);
  
  public abstract ResultVO rejectFinancing(long paramLong);
  
  public abstract ResultVO frozenStocks(int paramInt, String[] paramArrayOfString);
  
  public abstract ResultVO unFrozenStocks(int paramInt, String[] paramArrayOfString);
}
