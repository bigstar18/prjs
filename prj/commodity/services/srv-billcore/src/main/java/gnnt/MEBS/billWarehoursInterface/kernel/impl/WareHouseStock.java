package gnnt.MEBS.billWarehoursInterface.kernel.impl;

import gnnt.MEBS.billWarehoursInterface.VO.ResponseVO;
import gnnt.MEBS.billWarehoursInterface.VO.Result;
import gnnt.MEBS.billWarehoursInterface.VO.UnRegisterRequestVO;
import gnnt.MEBS.billWarehoursInterface.client.IClient;
import gnnt.MEBS.billWarehoursInterface.exception.WarehoursException;
import gnnt.MEBS.billWarehoursInterface.kernel.IWareHouseStockServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WareHouseStock
  implements IWareHouseStockServer
{
  Log logger = LogFactory.getLog(WareHouseStock.class);
  IClient client;
  
  public IClient getClient()
  {
    return this.client;
  }
  
  public void setClient(IClient client)
  {
    this.client = client;
  }
  
  public ResponseVO stockOut(UnRegisterRequestVO request)
  {
    ResponseVO response = null;
    try
    {
      response = this.client.communicate(request);
    }
    catch (WarehoursException e)
    {
      response = new ResponseVO();
      Result result = new Result();
      result.setReturnCode(-1L);
      result.setMessage(e.getMessage());
      response.setResult(result);
      this.logger.error(WarehoursException.getExceptionTrace(e));
    }
    return response;
  }
}
