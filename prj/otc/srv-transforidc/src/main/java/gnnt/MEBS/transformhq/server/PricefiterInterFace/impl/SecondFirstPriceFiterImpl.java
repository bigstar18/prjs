package gnnt.MEBS.transformhq.server.PricefiterInterFace.impl;

import gnnt.MEBS.transformhq.server.PricefiterInterFace.SecondPriceFiterInterFace;
import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.quotation.CheckAndSendHQBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SecondFirstPriceFiterImpl
  implements SecondPriceFiterInterFace
{
  private Log log = LogFactory.getLog(SecondFirstPriceFiterImpl.class);
  private CheckAndSendHQBean checkAndSendHQBean;
  private long lastPriceTime = 0L;
  
  public void setHQBean(HQBean hqBean)
  {
    if (System.currentTimeMillis() - this.lastPriceTime > 1000L)
    {
      this.checkAndSendHQBean.offerHQBean(hqBean);
      setLastPriceTime();
    }
    else
    {
      this.log.info("abandon second msg :" + hqBean.getServerContents());
    }
  }
  
  public void init(CheckAndSendHQBean checkAndSendHQBean)
  {
    this.checkAndSendHQBean = checkAndSendHQBean;
  }
  
  public void setLastPriceTime()
  {
    this.lastPriceTime = System.currentTimeMillis();
  }
}
