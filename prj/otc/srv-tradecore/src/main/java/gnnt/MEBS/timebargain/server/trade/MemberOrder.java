package gnnt.MEBS.timebargain.server.trade;

import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.model.Member;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MemberOrder
{
  private Log log = LogFactory.getLog(getClass());
  private String m_FirmID;
  private Map<String, CommodityOrder> commodityOrderMap = new HashMap();
  
  public MemberOrder(String m_FirmID)
  {
    this.m_FirmID = m_FirmID;
    for (Quotation_RT quotation_RT : ((Member)ServerInit.getMemberQueue().get(this.m_FirmID)).getQuotationMap().values())
    {
      this.log.debug("quotation_RT:" + quotation_RT);
      CommodityOrder commodityOrder = new CommodityOrder(m_FirmID, quotation_RT.getCommodityID());
      this.commodityOrderMap.put(commodityOrder.getCommodityID(), commodityOrder);
    }
  }
  
  public String getM_FirmID()
  {
    return this.m_FirmID;
  }
  
  public void setM_FirmID(String firmID)
  {
    this.m_FirmID = firmID;
  }
  
  public Map<String, CommodityOrder> getCommodityOrderMap()
  {
    return this.commodityOrderMap;
  }
  
  public void setCommodityOrderMap(Map<String, CommodityOrder> commodityOrderMap)
  {
    this.commodityOrderMap = commodityOrderMap;
  }
}
