package gnnt.MEBS.billWarehoursInterface.operation;

import gnnt.MEBS.billWarehoursInterface.VO.RegisterRequestVO;

public abstract interface IInvokeCore
{
  public abstract String addStock(RegisterRequestVO paramRegisterRequestVO);
}
