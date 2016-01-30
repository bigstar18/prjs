package gnnt.MEBS.bill.core.dao;

import gnnt.MEBS.bill.core.po.DismantlePO;
import gnnt.MEBS.bill.core.po.FinancingStockPO;
import gnnt.MEBS.bill.core.po.FrozenStockPO;
import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.po.OutStockPO;
import gnnt.MEBS.bill.core.po.PledgeStockPO;
import gnnt.MEBS.bill.core.po.StockOperationPO;
import gnnt.MEBS.bill.core.po.StockPO;
import gnnt.MEBS.bill.core.po.TradeModelPO;
import gnnt.MEBS.bill.core.po.TradeStockPO;
import gnnt.MEBS.bill.core.po.WarehousePO;
import gnnt.MEBS.bill.core.vo.StockOperation;
import gnnt.MEBS.bill.core.vo.StockVO;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract interface WareHouseStockDAO
  extends DAO
{
  public abstract TradeModelPO getTradeModel(int paramInt);
  
  public abstract StockPO getStockPOByID(String paramString);
  
  public abstract StockPO getStockPOByIDAndLocked(String paramString);
  
  public abstract List<GoodsPropertyPO> getGoodsProperty(String paramString);
  
  public abstract int[] insertGoodsProperty(List<GoodsPropertyPO> paramList);
  
  public abstract List<StockOperationPO> getStockOperation(String paramString);
  
  public abstract boolean getIncStockOperation(String paramString, StockOperation paramStockOperation);
  
  public abstract void deleteStockOperation(String paramString, StockOperation paramStockOperation);
  
  public abstract void insertStockOperation(StockOperationPO paramStockOperationPO);
  
  public abstract void insertTradeStock(TradeStockPO paramTradeStockPO);
  
  public abstract void realeseTradeStockByTN(int paramInt, String paramString);
  
  public abstract void realeseTradeStockBySI(String paramString);
  
  public abstract List<TradeStockPO> getTradeStockList(String paramString);
  
  public abstract void stockChg(String paramString1, String paramString2, String paramString3);
  
  public abstract void insertPledgeStock(PledgeStockPO paramPledgeStockPO);
  
  public abstract void realesePledgeStock(int paramInt, String paramString);
  
  public abstract List<PledgeStockPO> getPledgeStockList(String paramString);
  
  public abstract long insertStock(StockPO paramStockPO);
  
  public abstract void updateStockStatus(String paramString, int paramInt);
  
  public abstract void addOutStock(OutStockPO paramOutStockPO);
  
  public abstract void updateOutStock(OutStockPO paramOutStockPO);
  
  public abstract OutStockPO getUnAuditOutStockByStockID(String paramString);
  
  public abstract void updateOutStockStatus(long paramLong, int paramInt);
  
  public abstract WarehousePO getWarehouse(String paramString);
  
  public abstract WarehousePO getWarehouseByWarehouseName(String paramString);
  
  public abstract void insertDismantle(DismantlePO paramDismantlePO);
  
  public abstract List<DismantlePO> getDismantleList(String paramString);
  
  public abstract void dismantleSucess(long paramLong, String paramString1, String paramString2);
  
  public abstract void dismantleFail(long paramLong);
  
  public abstract FinancingStockPO getFinancingStockPOBuID(long paramLong);
  
  public abstract FinancingStockPO getFinancingStockPOBuIDAndLocked(long paramLong);
  
  public abstract void addGlobalLog(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2);
  
  public abstract long getFinancingStockID();
  
  public abstract long addFinancingStock(FinancingStockPO paramFinancingStockPO);
  
  public abstract void disableFinancingStock(long paramLong);
  
  public abstract String getFirmName(String paramString);
  
  public abstract String getBIFirmPassword(String paramString);
  
  public abstract long getBreedIDByCategoryNameBreedName(String paramString1, String paramString2);
  
  public abstract List<Map<String, Object>> getPropertyByBreedID(long paramLong);
  
  public abstract List<Map<String, Object>> getCategoryPropertyByBreedID(long paramLong);
  
  public abstract List<StockVO> getUnusedStocksVOList(long paramLong, String paramString, double paramDouble);
  
  public abstract List<StockPO> getUnusedStocksVOList(int paramInt, String paramString);
  
  public abstract long insertFrozenStockPO(FrozenStockPO paramFrozenStockPO);
  
  public abstract List<FrozenStockPO> getFrozenStockList(String paramString);
  
  public abstract void realeseFrozenStockPO(String paramString);
  
  public abstract void warehouseMoveHistory(Date paramDate);
}
