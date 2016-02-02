package gnnt.MEBS.transformhq.server.quotation;

import gnnt.MEBS.transformhq.server.model.HQBean;
import gnnt.MEBS.transformhq.server.model.IPConfig;
import gnnt.MEBS.transformhq.server.model.InCommodity;
import java.util.List;
import java.util.Map;

public class QuotationInterFaceImpl
  implements QuotationInterFace
{
  private CheckAndSendHQBean checkAndSendHQBean;
  private QuotationCheckThread quotationCheck;
  private String clientVersion;
  
  public QuotationInterFaceImpl(CheckAndSendHQBean checkAndSendHQBean, QuotationCheckThread quotationCheck)
  {
    this.checkAndSendHQBean = checkAndSendHQBean;
    this.quotationCheck = quotationCheck;
  }
  
  public String getClientVersion()
  {
    return this.clientVersion;
  }
  
  public Map<String, InCommodity> getInCommodity()
  {
    return this.checkAndSendHQBean.getIncommodity();
  }
  
  public IPConfig getUseIPConfig()
  {
    return this.quotationCheck.getUseIPConfig();
  }
  
  public void setClientVersion(String clientVersion)
  {
    this.clientVersion = clientVersion;
  }
  
  public void setHQBean(HQBean hqBean)
  {
    this.checkAndSendHQBean.burrPriceCheck(hqBean);
  }
  
  public void start() {}
  
  public List<IPConfig> getIPConfigList()
  {
    return this.quotationCheck.getIPConfigList();
  }
}
