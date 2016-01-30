package gnnt.MEBS.billWarehoursInterface.client;

import gnnt.MEBS.billWarehoursInterface.VO.RequestVO;
import gnnt.MEBS.billWarehoursInterface.VO.ResponseVO;

public abstract interface IClient
{
  public abstract ResponseVO communicate(RequestVO paramRequestVO);
}
