package gnnt.MEBS.billWarehoursInterface.client.impl;

import gnnt.MEBS.billWarehoursInterface.VO.RequestVO;
import gnnt.MEBS.billWarehoursInterface.VO.ResponseVO;
import gnnt.MEBS.billWarehoursInterface.VO.UnRegisterRequestVO;
import gnnt.MEBS.billWarehoursInterface.VO.UnRegisterResponseVO;
import gnnt.MEBS.billWarehoursInterface.client.IClient;
import gnnt.MEBS.billWarehoursInterface.exception.WarehoursException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SuccessClient
  implements IClient
{
  protected final transient Log logger = LogFactory.getLog(SuccessClient.class);
  
  public ResponseVO communicate(RequestVO requestVO)
  {
    if (requestVO == null) {
      throw new WarehoursException("请求包为空");
    }
    switch (requestVO.getProtocolName())
    {
    case unregister: 
      break;
    default: 
      throw new WarehoursException("仓库系统不支持" + 
        requestVO.getProtocolName() + "协议");
    }
    this.logger.debug(" send:" + requestVO.getXMLFromVO());
    ResponseVO responseVO = null;
    String msg = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"yes\"?><GNNT name=\"unregister\"><RESULT><MESSAGE>success</MESSAGE><RETCODE>0</RETCODE></RESULT></GNNT>";
    switch (requestVO.getProtocolName())
    {
    case unregister: 
      responseVO = new UnRegisterResponseVO();
      
      responseVO = responseVO.getResponseVOFromXml(msg);
    }
    this.logger.debug("msg=" + msg);
    return responseVO;
  }
  
  public static void main(String[] args)
    throws Exception
  {
    IClient client = new SuccessClient();
    client.communicate(new UnRegisterRequestVO());
  }
}
