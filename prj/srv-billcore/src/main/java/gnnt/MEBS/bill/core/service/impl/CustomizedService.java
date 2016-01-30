package gnnt.MEBS.bill.core.service.impl;

import gnnt.MEBS.bill.core.exception.BillCoreException;
import gnnt.MEBS.bill.core.operation.ICustomized;
import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.po.StockPO;
import gnnt.MEBS.bill.core.service.ICustomizedService;
import gnnt.MEBS.bill.core.vo.AddStockResultVO;
import gnnt.MEBS.bill.core.vo.FrozenStockOutResultVO;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

public class CustomizedService
  implements ICustomizedService
{
  private Log log = LogFactory.getLog(CustomizedService.class);
  private ICustomized customized;
  
  public void setCustomized(ICustomized paramICustomized)
  {
    this.customized = paramICustomized;
  }
  
  public AddStockResultVO addFrozenStock(StockPO paramStockPO, List<GoodsPropertyPO> paramList, int paramInt)
  {
    AddStockResultVO localAddStockResultVO = new AddStockResultVO();
    localAddStockResultVO.setResult(-1L);
    try
    {
      localAddStockResultVO = this.customized.addFrozenStock(paramStockPO, paramList, paramInt);
    }
    catch (BillCoreException localBillCoreException)
    {
      localAddStockResultVO.addErrorInfo(-1000L, new Object[] { localBillCoreException.getMessage() });
      this.log.error("录入冻结仓单异常：", localBillCoreException);
    }
    catch (DataAccessException localDataAccessException)
    {
      localAddStockResultVO.addErrorInfo(-2030L);
      this.log.error("录入冻结仓单异常：", localDataAccessException);
    }
    catch (Exception localException)
    {
      localAddStockResultVO.addErrorInfo(-2031L);
      this.log.error("录入冻结仓单异常：", localException);
    }
    return localAddStockResultVO;
  }
  
  public FrozenStockOutResultVO frozenStockOut(String[] paramArrayOfString, double paramDouble, int paramInt, String paramString)
  {
    FrozenStockOutResultVO localFrozenStockOutResultVO = new FrozenStockOutResultVO();
    localFrozenStockOutResultVO.setResult(-1L);
    try
    {
      localFrozenStockOutResultVO = this.customized.frozenStockOut(paramArrayOfString, paramDouble, paramInt, paramString);
    }
    catch (BillCoreException localBillCoreException)
    {
      localFrozenStockOutResultVO.addErrorInfo(-1000L, new Object[] { localBillCoreException.getMessage() });
      this.log.error("冻结仓单出库异常：", localBillCoreException);
    }
    catch (DataAccessException localDataAccessException)
    {
      localFrozenStockOutResultVO.addErrorInfo(-2130L);
      this.log.error("冻结仓单出库异常：", localDataAccessException);
    }
    catch (Exception localException)
    {
      localFrozenStockOutResultVO.addErrorInfo(-2131L);
      this.log.error("冻结仓单出库异常：", localException);
    }
    return localFrozenStockOutResultVO;
  }
}
