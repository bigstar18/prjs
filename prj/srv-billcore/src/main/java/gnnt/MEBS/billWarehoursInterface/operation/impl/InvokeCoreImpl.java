package gnnt.MEBS.billWarehoursInterface.operation.impl;

import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.service.IKernelService;
import gnnt.MEBS.bill.core.vo.AddStockResultVO;
import gnnt.MEBS.bill.core.vo.StockAddVO;
import gnnt.MEBS.billWarehoursInterface.VO.PropertyMap;
import gnnt.MEBS.billWarehoursInterface.VO.PropertyVO;
import gnnt.MEBS.billWarehoursInterface.VO.RegisterRequestVO;
import gnnt.MEBS.billWarehoursInterface.VO.RegisterResponseVO;
import gnnt.MEBS.billWarehoursInterface.VO.Result;
import gnnt.MEBS.billWarehoursInterface.operation.IInvokeCore;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InvokeCoreImpl
  implements IInvokeCore
{
  Log logger = LogFactory.getLog(InvokeCoreImpl.class);
  private IKernelService billKernelService;
  
  public void setKernelService(IKernelService billKernelService)
  {
    this.billKernelService = billKernelService;
  }
  
  public IKernelService getBillKernelService()
  {
    return this.billKernelService;
  }
  
  public void setBillKernelService(IKernelService billKernelService)
  {
    this.billKernelService = billKernelService;
  }
  
  public String addStock(RegisterRequestVO request)
  {
    RegisterResponseVO responseVO = new RegisterResponseVO();
    responseVO.setProtocolName(request.getProtocolName());
    Result result = new Result();
    if ((request.getProperty() == null) || (request.getProperty().getPropertyList() == null) || (request.getProperty().getPropertyList().size() <= 0))
    {
      result.setReturnCode(-1L);
      result.setMessage("属性集合为空");
      responseVO.setResult(result);
      return responseVO.getXMLFromVO();
    }
    StockAddVO stockVO = new StockAddVO();
    List<GoodsPropertyPO> propertyList = new ArrayList();
    stockVO.setBreedName(request.getBreedName());
    stockVO.setCategoryName(request.getCategoryName());
    stockVO.setFirmID(request.getFirmID());
    stockVO.setFirmName(request.getFirmName());
    stockVO.setQuantity(request.getQuantity());
    stockVO.setStockID(request.getStockID());
    stockVO.setUnit(request.getUnit());
    stockVO.setWareHouseID(request.getWareHouseID());
    stockVO.setWareHousePassword(request.getWareHousePassword());
    GoodsPropertyPO value = null;
    for (PropertyMap map : request.getProperty().getPropertyList())
    {
      value = new GoodsPropertyPO();
      value.setPropertyName(map.getName());
      value.setPropertyValue(map.getValue());
      propertyList.add(value);
    }
    stockVO.setPropertyList(propertyList);
    AddStockResultVO resultVO = this.billKernelService.addStockForWarehouseServer(stockVO);
    if (resultVO.getResult() > 0L) {
      result.setReturnCode(0L);
    } else {
      result.setReturnCode(-1L);
    }
    result.setMessage(resultVO.getErrorInfo());
    responseVO.setResult(result);
    return responseVO.getXMLFromVO();
  }
}
