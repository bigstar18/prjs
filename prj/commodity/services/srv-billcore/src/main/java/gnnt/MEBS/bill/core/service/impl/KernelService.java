package gnnt.MEBS.bill.core.service.impl;

import gnnt.MEBS.bill.core.Server;
import gnnt.MEBS.bill.core.bo.StockOutApplyBO;
import gnnt.MEBS.bill.core.bo.StockOutAuditBO;
import gnnt.MEBS.bill.core.dao.WareHouseStockDAO;
import gnnt.MEBS.bill.core.exception.BillCoreException;
import gnnt.MEBS.bill.core.operation.IManageStock;
import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.po.StockPO;
import gnnt.MEBS.bill.core.po.WarehousePO;
import gnnt.MEBS.bill.core.service.IKernelService;
import gnnt.MEBS.bill.core.util.GnntBeanFactory;
import gnnt.MEBS.bill.core.util.MD5;
import gnnt.MEBS.bill.core.util.Tool;
import gnnt.MEBS.bill.core.vo.AddStockResultVO;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.bill.core.vo.StockAddVO;
import gnnt.MEBS.bill.core.vo.StockQiantityResultVO;
import gnnt.MEBS.bill.core.vo.TransferGoodsVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

public class KernelService
  implements IKernelService
{
  private Log log = LogFactory.getLog(KernelService.class);
  IManageStock manageStock;
  
  public void setManageStock(IManageStock manageStock)
  {
    this.manageStock = manageStock;
  }
  
  public AddStockResultVO addStock(StockPO stockPO, List<GoodsPropertyPO> propertyList)
  {
    AddStockResultVO resultVO = new AddStockResultVO();
    resultVO.setResult(-1L);
    try
    {
      resultVO = this.manageStock.addStock(stockPO, propertyList);
    }
    catch (DataAccessException dataAccessException)
    {
      resultVO.addErrorInfo(-1230L);
    }
    catch (Exception e)
    {
      resultVO.addErrorInfo(-1231L);
      this.log.error(Tool.getExceptionTrace(e));
    }
    return resultVO;
  }
  
  public AddStockResultVO addStockForWarehouseServer(StockAddVO stockAddVO)
  {
    AddStockResultVO resultVO = null;
    if (stockAddVO == null)
    {
      resultVO = new AddStockResultVO();
      resultVO.setResult(-1L);
      resultVO.addErrorInfo(-1401L);
      return resultVO;
    }
    if ((stockAddVO.getPropertyList() == null) || 
      (stockAddVO.getPropertyList().size() <= 0))
    {
      resultVO = new AddStockResultVO();
      resultVO.setResult(-1L);
      resultVO.addErrorInfo(-1402L);
      return resultVO;
    }
    WareHouseStockDAO wareHouseStockDAO = Server.getInstance()
      .getWareHouseStockDAO();
    
    String name = wareHouseStockDAO.getFirmName(stockAddVO.getFirmID());
    if (name == null)
    {
      resultVO = new AddStockResultVO();
      resultVO.setResult(-1L);
      
      resultVO.addErrorInfo(-1240L, 
        new Object[] { stockAddVO.getFirmID() });
      return resultVO;
    }
    if (!name.equals(stockAddVO.getFirmName()))
    {
      resultVO = new AddStockResultVO();
      resultVO.setResult(-1L);
      
      resultVO.addErrorInfo(-1241L, new Object[] {stockAddVO
        .getFirmName() });
      return resultVO;
    }
    String password = wareHouseStockDAO.getBIFirmPassword(stockAddVO
      .getFirmID());
    if ((stockAddVO.getWareHousePassword() == null) || 
      (!password.equals(MD5.getMD5(stockAddVO
      .getFirmID(), stockAddVO.getWareHousePassword()))))
    {
      resultVO = new AddStockResultVO();
      resultVO.setResult(-1L);
      
      resultVO.addErrorInfo(-1242L);
      return resultVO;
    }
    long breedID = wareHouseStockDAO.getBreedIDByCategoryNameBreedName(
      stockAddVO.getCategoryName(), stockAddVO.getBreedName());
    if (breedID < 0L)
    {
      resultVO = new AddStockResultVO();
      resultVO.setResult(-1L);
      
      resultVO.addErrorInfo(-1243L, new Object[] {
        stockAddVO.getCategoryName(), stockAddVO.getBreedName() });
      return resultVO;
    }
    WarehousePO warehouse = wareHouseStockDAO.getWarehouseByWarehouseName(stockAddVO.getWareHouseName());
    if (warehouse == null)
    {
      resultVO = new AddStockResultVO();
      resultVO.setResult(-1L);
      
      resultVO.addErrorInfo(-1245L, new Object[] { stockAddVO.getWareHouseName() });
      return resultVO;
    }
    List<Map<String, Object>> propertys = wareHouseStockDAO
      .getPropertyByBreedID(breedID);
    
    List<GoodsPropertyPO> propertyList = new ArrayList();
    for (Map<String, Object> map : propertys)
    {
      boolean isExist = false;
      for (GoodsPropertyPO property : stockAddVO.getPropertyList()) {
        if (map.get("propertyname").toString().equals(
          property.getPropertyName()))
        {
          isExist = true;
          GoodsPropertyPO po = new GoodsPropertyPO();
          po.setPropertyName(property.getPropertyName());
          po.setPropertyValue(property.getPropertyValue());
          po.setPropertyTypeID(property.getPropertyTypeID());
          propertyList.add(po);
          break;
        }
      }
      if (!isExist)
      {
        resultVO = new AddStockResultVO();
        resultVO.setResult(-1L);
        
        resultVO.addErrorInfo(-1244L, new Object[] {map.get(
          "propertyname").toString() });
        return resultVO;
      }
    }
    StockPO stockPO = new StockPO();
    stockPO.setBreedID(breedID);
    stockPO.setOwnerFirm(stockAddVO.getFirmID());
    stockPO.setQuantity(stockAddVO.getQuantity());
    stockPO.setRealStockCode(stockAddVO.getStockID());
    stockPO.setUnit(stockAddVO.getUnit());
    stockPO.setWarehouseID(warehouse.getWarehouseID());
    
    resultVO = addStock(stockPO, propertyList);
    return resultVO;
  }
  
  public ResultVO dismantleStock(String stockID, Double[] amountarr)
  {
    ResultVO resultVO = new TransferGoodsVO();
    resultVO.setResult(-1L);
    try
    {
      resultVO = this.manageStock.dismantleStock(stockID, amountarr);
    }
    catch (DataAccessException dataAccessException)
    {
      resultVO.addErrorInfo(-1250L);
      this.log.error(Tool.getExceptionTrace(dataAccessException));
    }
    catch (Exception e)
    {
      resultVO.addErrorInfo(-1251L);
      this.log.error(Tool.getExceptionTrace(e));
    }
    return resultVO;
  }
  
  public ResultVO dismantleStock(String stockID, boolean result, Map<String, String> dismantleMap)
  {
    ResultVO resultVO = new TransferGoodsVO();
    resultVO.setResult(-1L);
    try
    {
      resultVO = this.manageStock.dismantleStock(stockID, result, 
        dismantleMap);
    }
    catch (BillCoreException runtimeException)
    {
      resultVO.addErrorInfo(-1000L, new Object[] {runtimeException
        .getMessage() });
      this.log.error(Tool.getExceptionTrace(runtimeException));
    }
    catch (DataAccessException dataAccessException)
    {
      resultVO.addErrorInfo(-1260L);
      this.log.error(Tool.getExceptionTrace(dataAccessException));
    }
    catch (Exception e)
    {
      resultVO.addErrorInfo(-1261L);
      this.log.error(Tool.getExceptionTrace(e));
    }
    return resultVO;
  }
  
  public ResultVO logoutStock(String stockID)
  {
    ResultVO resultVO = new TransferGoodsVO();
    resultVO.setResult(-1L);
    try
    {
      resultVO = this.manageStock.logoutStock(stockID);
    }
    catch (DataAccessException dataAccessException)
    {
      resultVO.addErrorInfo(-1270L);
      this.log.error(Tool.getExceptionTrace(dataAccessException));
    }
    catch (Exception e)
    {
      resultVO.addErrorInfo(-1271L);
      this.log.error(Tool.getExceptionTrace(e));
    }
    return resultVO;
  }
  
  public ResultVO stockOutApply(StockOutApplyBO stockOutApplyBO)
  {
    ResultVO resultVO = new TransferGoodsVO();
    resultVO.setResult(-1L);
    if (stockOutApplyBO == null)
    {
      resultVO.addErrorInfo(-1701L);
      return resultVO;
    }
    if ((stockOutApplyBO.getStockID() == null) || (stockOutApplyBO.getStockID().trim().length() <= 0) || 
      (stockOutApplyBO.getDeliveryPerson() == null) || (stockOutApplyBO.getDeliveryPerson().trim().length() <= 0))
    {
      resultVO.addErrorInfo(-1702L);
      return resultVO;
    }
    try
    {
      resultVO = this.manageStock.stockOutApply(stockOutApplyBO);
    }
    catch (BillCoreException runtimeException)
    {
      resultVO.addErrorInfo(-1000L, new Object[] { runtimeException.getMessage() });
      this.log.error("仓单出库申请自定义异常", runtimeException);
    }
    catch (DataAccessException dataAccessException)
    {
      resultVO.addErrorInfo(-1790L);
      this.log.error("仓单出库申请数据库异常", dataAccessException);
    }
    catch (Exception e)
    {
      resultVO.addErrorInfo(-1791L);
      this.log.error("仓单出库申请其他异常", e);
    }
    return resultVO;
  }
  
  public ResultVO stockOutUpdate(StockOutApplyBO stockOutApplyBO)
  {
    ResultVO resultVO = new TransferGoodsVO();
    resultVO.setResult(-1L);
    if (stockOutApplyBO == null)
    {
      resultVO.addErrorInfo(-1701L);
      return resultVO;
    }
    if ((stockOutApplyBO.getStockID() == null) || (stockOutApplyBO.getStockID().trim().length() <= 0) || 
      (stockOutApplyBO.getDeliveryPerson() == null) || (stockOutApplyBO.getDeliveryPerson().trim().length() <= 0))
    {
      resultVO.addErrorInfo(-1702L);
      return resultVO;
    }
    try
    {
      resultVO = this.manageStock.stockOutUpdate(stockOutApplyBO);
    }
    catch (Exception e)
    {
      resultVO.addErrorInfo(-1791L);
      this.log.error("仓单出库申请其他异常", e);
    }
    return resultVO;
  }
  
  public ResultVO stockOutAudit(StockOutAuditBO stockOutAuditBO)
  {
    ResultVO resultVO = new TransferGoodsVO();
    resultVO.setResult(-1L);
    if (stockOutAuditBO == null)
    {
      resultVO.addErrorInfo(-1801L);
      return resultVO;
    }
    if ((stockOutAuditBO.getStockID() == null) || (stockOutAuditBO.getStockID().trim().length() <= 0) || 
      (stockOutAuditBO.getDeliveryPerson() == null) || (stockOutAuditBO.getDeliveryPerson().trim().length() <= 0) || 
      (stockOutAuditBO.getKey() == null) || (stockOutAuditBO.getKey().trim().length() <= 0))
    {
      resultVO.addErrorInfo(-1802L);
      return resultVO;
    }
    try
    {
      resultVO = this.manageStock.stockOutAudit(stockOutAuditBO);
    }
    catch (BillCoreException runtimeException)
    {
      resultVO.addErrorInfo(-1000L, new Object[] { runtimeException.getMessage() });
      this.log.error("仓单出库审核自定义异常", runtimeException);
    }
    catch (DataAccessException dataAccessException)
    {
      resultVO.addErrorInfo(-1890L);
      this.log.error("仓单出库审核数据库异常", dataAccessException);
    }
    catch (Exception e)
    {
      resultVO.addErrorInfo(-1891L);
      this.log.error("仓单出库审核其他异常", e);
    }
    return resultVO;
  }
  
  public ResultVO withdrowStockOutApply(String stockID)
  {
    ResultVO resultVO = new ResultVO();
    resultVO.setResult(-1L);
    if ((stockID == null) || (stockID.trim().length() <= 0))
    {
      resultVO.addErrorInfo(-1901L);
      return resultVO;
    }
    try
    {
      resultVO = this.manageStock.withdrowStockOutApply(stockID);
    }
    catch (BillCoreException runtimeException)
    {
      resultVO.addErrorInfo(-1000L, new Object[] { runtimeException.getMessage() });
      this.log.error("撤销仓单出库申请自定义异常", runtimeException);
    }
    catch (DataAccessException dataAccessException)
    {
      resultVO.addErrorInfo(-1990L);
      this.log.error("撤销仓单出库申请数据库异常", dataAccessException);
    }
    catch (Exception e)
    {
      resultVO.addErrorInfo(-1991L);
      this.log.error("撤销仓单出库申请其他异常", e);
    }
    return resultVO;
  }
  
  public ResultVO registerStock(String stockID)
  {
    ResultVO resultVO = new TransferGoodsVO();
    resultVO.setResult(-1L);
    try
    {
      resultVO = this.manageStock.registerStock(stockID);
    }
    catch (DataAccessException dataAccessException)
    {
      resultVO.addErrorInfo(-1280L);
      this.log.error(Tool.getExceptionTrace(dataAccessException));
    }
    catch (Exception e)
    {
      resultVO.addErrorInfo(-1281L);
      this.log.error(Tool.getExceptionTrace(e));
    }
    return resultVO;
  }
  
  public ResultVO stockOut(String stockID, String userID, String userName, String password)
  {
    ResultVO resultVO = new TransferGoodsVO();
    resultVO.setResult(-1L);
    try
    {
      resultVO = this.manageStock.stockOut(stockID, userID, userName, 
        password);
    }
    catch (BillCoreException runtimeException)
    {
      resultVO.addErrorInfo(-1000L, new Object[] {runtimeException
        .getMessage() });
      this.log.error(Tool.getExceptionTrace(runtimeException));
    }
    catch (DataAccessException dataAccessException)
    {
      resultVO.addErrorInfo(-1300L);
      this.log.error(Tool.getExceptionTrace(dataAccessException));
    }
    catch (Exception e)
    {
      resultVO.addErrorInfo(-1301L);
      this.log.error(Tool.getExceptionTrace(e));
    }
    return resultVO;
  }
  
  public String getConfigInfo(String key)
  {
    return GnntBeanFactory.getConfig(key);
  }
  
  public List<String> getUnusedStocks(long breedID, Map<String, String> propertys, String firmID, double quantity)
  {
    List<String> result = new ArrayList();
    if (breedID <= 0L) {
      return result;
    }
    if (quantity <= 0.0D) {
      return result;
    }
    if ((firmID == null) || (firmID.trim().length() <= 0)) {
      return result;
    }
    try
    {
      result = this.manageStock.getUnusedStocks(breedID, propertys, firmID, quantity);
    }
    catch (BillCoreException runtimeException)
    {
      this.log.error(Tool.getExceptionTrace(runtimeException));
    }
    catch (DataAccessException dataAccessException)
    {
      this.log.error(Tool.getExceptionTrace(dataAccessException));
    }
    catch (Exception e)
    {
      this.log.error(Tool.getExceptionTrace(e));
    }
    return result;
  }
  
  public List<String> getUnusedStocks(long breedID, Map<String, String> propertys, String firmID)
  {
    double quantity = 2.14748364799E9D;
    return getUnusedStocks(breedID, propertys, firmID, quantity);
  }
  
  public List<String> getUnusedStocks(int moduleid, String firmID)
  {
    List<String> result = new ArrayList();
    if (moduleid <= 0)
    {
      this.log.error("通过模块编号、交易商代码查询仓单列表信息，模块号不能小于等于 0 ，当前模块号为：" + moduleid);
      return result;
    }
    if ((firmID == null) || (firmID.trim().length() <= 0))
    {
      this.log.error("通过模块编号、交易商代码查询仓单列表信息，交易商代码为空");
      return result;
    }
    try
    {
      result = this.manageStock.getUnusedStocks(moduleid, firmID);
    }
    catch (BillCoreException runtimeException)
    {
      this.log.error(Tool.getExceptionTrace(runtimeException));
    }
    catch (DataAccessException dataAccessException)
    {
      this.log.error(Tool.getExceptionTrace(dataAccessException));
    }
    catch (Exception e)
    {
      this.log.error(Tool.getExceptionTrace(e));
    }
    return result;
  }
  
  public StockQiantityResultVO getQuantityByStockIDs(List<String> stockIDs)
  {
    StockQiantityResultVO result = new StockQiantityResultVO();
    result.setResult(-1L);
    if ((stockIDs == null) || (stockIDs.size() <= 0))
    {
      result.addErrorInfo(-1401L);
      return result;
    }
    try
    {
      result = this.manageStock.getQuantityByStockIDs(stockIDs);
    }
    catch (BillCoreException runtimeException)
    {
      this.log.error(Tool.getExceptionTrace(runtimeException));
      result.addErrorInfo(-1000L, new Object[] { runtimeException.getMessage() });
      return result;
    }
    catch (DataAccessException dataAccessException)
    {
      this.log.error(Tool.getExceptionTrace(dataAccessException));
      
      result.addErrorInfo(-1403L);
      return result;
    }
    catch (Exception e)
    {
      this.log.error(Tool.getExceptionTrace(e));
      
      result.addErrorInfo(-1404L);
      return result;
    }
    return result;
  }
}
