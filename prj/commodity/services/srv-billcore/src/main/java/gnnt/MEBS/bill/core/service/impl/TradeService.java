package gnnt.MEBS.bill.core.service.impl;

import gnnt.MEBS.bill.core.exception.BillCoreException;
import gnnt.MEBS.bill.core.operation.IStockTrade;
import gnnt.MEBS.bill.core.service.ITradeService;
import gnnt.MEBS.bill.core.util.Tool;
import gnnt.MEBS.bill.core.vo.DeliveryVO;
import gnnt.MEBS.bill.core.vo.FinancingApplyVO;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.bill.core.vo.TransferGoodsVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

public class TradeService
  implements ITradeService
{
  private Log log = LogFactory.getLog(TradeService.class);
  IStockTrade stockTrade;
  
  public void setStockTrade(IStockTrade paramIStockTrade)
  {
    this.stockTrade = paramIStockTrade;
  }
  
  public DeliveryVO performDelivery(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    DeliveryVO localDeliveryVO = new DeliveryVO();
    localDeliveryVO.setResult(-1L);
    try
    {
      double d = this.stockTrade.delivery(paramInt, paramString1, paramString2, paramString3);
      localDeliveryVO.setQuantity(d);
    }
    catch (DataAccessException localDataAccessException)
    {
      localDeliveryVO.addErrorInfo(-1170L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
      return localDeliveryVO;
    }
    catch (Exception localException)
    {
      localDeliveryVO.addErrorInfo(-1171L);
      this.log.error(Tool.getExceptionTrace(localException));
      return localDeliveryVO;
    }
    localDeliveryVO.setResult(1L);
    return localDeliveryVO;
  }
  
  public DeliveryVO performDelivery(int paramInt, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3)
  {
    DeliveryVO localDeliveryVO = new DeliveryVO();
    localDeliveryVO.setResult(-1L);
    try
    {
      double d = this.stockTrade.delivery(paramInt, paramString1, paramArrayOfString, paramString2, paramString3);
      localDeliveryVO.setQuantity(d);
    }
    catch (DataAccessException localDataAccessException)
    {
      localDeliveryVO.addErrorInfo(-1170L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
      return localDeliveryVO;
    }
    catch (Exception localException)
    {
      localDeliveryVO.addErrorInfo(-1171L);
      this.log.error(Tool.getExceptionTrace(localException));
      return localDeliveryVO;
    }
    localDeliveryVO.setResult(1L);
    return localDeliveryVO;
  }
  
  public ResultVO performRealeseGoods(int paramInt, String paramString)
  {
    ResultVO localResultVO = new ResultVO();
    localResultVO.setResult(-1L);
    try
    {
      this.stockTrade.realeseGoods(paramInt, paramString);
    }
    catch (DataAccessException localDataAccessException)
    {
      localResultVO.addErrorInfo(-1160L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
      return localResultVO;
    }
    catch (Exception localException)
    {
      localResultVO.addErrorInfo(-1161L);
      this.log.error(Tool.getExceptionTrace(localException));
      return localResultVO;
    }
    localResultVO.setResult(1L);
    return localResultVO;
  }
  
  public ResultVO performSellStock(int paramInt, String paramString1, String paramString2)
  {
    this.log.debug("sellStock moduleid=" + paramInt + ";stockID=" + paramString2 + ";orderID " + paramString1);
    ResultVO localResultVO = new ResultVO();
    localResultVO.setResult(-1L);
    try
    {
      localResultVO = this.stockTrade.sellStock(paramInt, paramString1, paramString2);
    }
    catch (DataAccessException localDataAccessException)
    {
      localResultVO.addErrorInfo(-1107L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
    }
    catch (Exception localException)
    {
      localResultVO.addErrorInfo(-1108L);
      this.log.error(Tool.getExceptionTrace(localException));
    }
    return localResultVO;
  }
  
  public TransferGoodsVO performSellStockToDelivery(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    TransferGoodsVO localTransferGoodsVO = new TransferGoodsVO();
    localTransferGoodsVO.setResult(-1L);
    try
    {
      localTransferGoodsVO = this.stockTrade.sellStockToDelivery(paramInt, paramString1, paramString2, paramString3);
    }
    catch (DataAccessException localDataAccessException)
    {
      localTransferGoodsVO.addErrorInfo(-1120L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
    }
    catch (Exception localException)
    {
      localTransferGoodsVO.addErrorInfo(-1121L);
      this.log.error(Tool.getExceptionTrace(localException));
    }
    return localTransferGoodsVO;
  }
  
  public ResultVO performStockChg(String[] paramArrayOfString, String paramString1, String paramString2)
  {
    ResultVO localResultVO = new ResultVO();
    localResultVO.setResult(-1L);
    try
    {
      this.stockTrade.stockChg(paramArrayOfString, paramString1, paramString2);
    }
    catch (DataAccessException localDataAccessException)
    {
      localResultVO.addErrorInfo(-1180L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
      return localResultVO;
    }
    catch (Exception localException)
    {
      localResultVO.addErrorInfo(-1181L);
      this.log.error(Tool.getExceptionTrace(localException));
      return localResultVO;
    }
    localResultVO.setResult(1L);
    return localResultVO;
  }
  
  public TransferGoodsVO performTransferGoods(int paramInt, String paramString1, String[] paramArrayOfString, String paramString2)
  {
    TransferGoodsVO localTransferGoodsVO = new TransferGoodsVO();
    localTransferGoodsVO.setResult(-1L);
    try
    {
      localTransferGoodsVO = this.stockTrade.transferGoods(paramInt, paramString1, paramArrayOfString, paramString2);
    }
    catch (DataAccessException localDataAccessException)
    {
      localTransferGoodsVO.addErrorInfo(-1130L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
    }
    catch (Exception localException)
    {
      localTransferGoodsVO.addErrorInfo(-1131L);
      this.log.error(Tool.getExceptionTrace(localException));
    }
    if (localTransferGoodsVO.getResult() < 0L) {
      return localTransferGoodsVO;
    }
    localTransferGoodsVO.setResult(1L);
    return localTransferGoodsVO;
  }
  
  public void checkStart() {}
  
  public ResultVO performWithdrawSellStock(int paramInt, String paramString)
  {
    ResultVO localResultVO = new ResultVO();
    localResultVO.setResult(-1L);
    try
    {
      this.stockTrade.withdrawSellStock(paramInt, paramString);
    }
    catch (DataAccessException localDataAccessException)
    {
      localResultVO.addErrorInfo(-1110L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
      return localResultVO;
    }
    catch (Exception localException)
    {
      localResultVO.addErrorInfo(-1111L);
      this.log.error(Tool.getExceptionTrace(localException));
      return localResultVO;
    }
    localResultVO.setResult(1L);
    return localResultVO;
  }
  
  public ResultVO performRealeseGoods(int paramInt, String paramString, String[] paramArrayOfString)
  {
    ResultVO localResultVO = new ResultVO();
    localResultVO.setResult(-1L);
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localResultVO.addErrorInfo(-1192L);
      return localResultVO;
    }
    try
    {
      this.stockTrade.realeseGoods(paramInt, paramString, paramArrayOfString);
    }
    catch (BillCoreException localBillCoreException)
    {
      localResultVO.addErrorInfo(-1000L, new Object[] { localBillCoreException.getMessage() });
      this.log.error(Tool.getExceptionTrace(localBillCoreException));
      return localResultVO;
    }
    catch (DataAccessException localDataAccessException)
    {
      localResultVO.addErrorInfo(-1190L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
      return localResultVO;
    }
    catch (Exception localException)
    {
      localResultVO.addErrorInfo(-1191L);
      this.log.error(Tool.getExceptionTrace(localException));
      return localResultVO;
    }
    localResultVO.setResult(1L);
    return localResultVO;
  }
  
  public long getFinancingStockID()
  {
    long l = 0L;
    l = this.stockTrade.getFinancingStockID();
    return l;
  }
  
  public FinancingApplyVO performStartFinancing(String paramString, long paramLong)
  {
    this.log.debug("performStartFinancing stockID " + paramString);
    FinancingApplyVO localFinancingApplyVO = new FinancingApplyVO();
    localFinancingApplyVO.setResult(-1L);
    try
    {
      localFinancingApplyVO = this.stockTrade.startFinancing(paramString, paramLong);
    }
    catch (DataAccessException localDataAccessException)
    {
      localFinancingApplyVO.addErrorInfo(-1200L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
    }
    catch (Exception localException)
    {
      localFinancingApplyVO.addErrorInfo(-1201L);
      this.log.error(Tool.getExceptionTrace(localException));
    }
    return localFinancingApplyVO;
  }
  
  public ResultVO performRejectFinancing(long paramLong)
  {
    this.log.debug("performRejectFinancing financingStockID " + paramLong);
    ResultVO localResultVO = new ResultVO();
    localResultVO.setResult(-1L);
    try
    {
      localResultVO = this.stockTrade.rejectFinancing(paramLong);
    }
    catch (DataAccessException localDataAccessException)
    {
      localResultVO.addErrorInfo(-1210L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
    }
    catch (Exception localException)
    {
      localResultVO.addErrorInfo(-1211L);
      this.log.error(Tool.getExceptionTrace(localException));
    }
    return localResultVO;
  }
  
  public ResultVO performEndFinancing(long paramLong)
  {
    this.log.debug("performEndFinancing financingStockID " + paramLong);
    ResultVO localResultVO = new ResultVO();
    localResultVO.setResult(-1L);
    try
    {
      localResultVO = this.stockTrade.endFinancing(paramLong);
    }
    catch (DataAccessException localDataAccessException)
    {
      localResultVO.addErrorInfo(-1220L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
    }
    catch (Exception localException)
    {
      localResultVO.addErrorInfo(-1221L);
      this.log.error(Tool.getExceptionTrace(localException));
    }
    return localResultVO;
  }
  
  public ResultVO frozenStocks(int paramInt, String[] paramArrayOfString)
  {
    ResultVO localResultVO = new ResultVO();
    localResultVO.setResult(-1L);
    if ((paramArrayOfString == null) || (paramArrayOfString.length <= 0))
    {
      localResultVO.addErrorInfo(-1501L);
      return localResultVO;
    }
    try
    {
      localResultVO = this.stockTrade.frozenStocks(paramInt, paramArrayOfString);
    }
    catch (BillCoreException localBillCoreException)
    {
      localResultVO.addErrorInfo(-1000L, new Object[] { localBillCoreException.getMessage() });
      this.log.error(Tool.getExceptionTrace(localBillCoreException));
    }
    catch (DataAccessException localDataAccessException)
    {
      localResultVO.addErrorInfo(-1502L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
    }
    catch (Exception localException)
    {
      localResultVO.addErrorInfo(-1503L);
      this.log.error(Tool.getExceptionTrace(localException));
    }
    return localResultVO;
  }
  
  public ResultVO unFrozenStocks(int paramInt, String[] paramArrayOfString)
  {
    ResultVO localResultVO = new ResultVO();
    localResultVO.setResult(-1L);
    if ((paramArrayOfString == null) || (paramArrayOfString.length <= 0))
    {
      localResultVO.addErrorInfo(-1601L);
      return localResultVO;
    }
    try
    {
      localResultVO = this.stockTrade.unFrozenStocks(paramInt, paramArrayOfString);
    }
    catch (BillCoreException localBillCoreException)
    {
      localResultVO.addErrorInfo(-1000L, new Object[] { localBillCoreException.getMessage() });
      this.log.error(Tool.getExceptionTrace(localBillCoreException));
    }
    catch (DataAccessException localDataAccessException)
    {
      localResultVO.addErrorInfo(-1602L);
      this.log.error(Tool.getExceptionTrace(localDataAccessException));
    }
    catch (Exception localException)
    {
      localResultVO.addErrorInfo(-1603L);
      this.log.error(Tool.getExceptionTrace(localException));
    }
    return localResultVO;
  }
}
