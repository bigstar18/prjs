package gnnt.MEBS.billWarehoursInterface.kernel;

import gnnt.MEBS.billWarehoursInterface.VO.ResponseVO;
import gnnt.MEBS.billWarehoursInterface.VO.UnRegisterRequestVO;

public abstract interface IWareHouseStockServer
{
  public abstract ResponseVO stockOut(UnRegisterRequestVO paramUnRegisterRequestVO);
}
