package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class WithdrawOrder
  implements Serializable
{
  private static final long serialVersionUID = 1690197650654049814L;
  private String traderID;
  private String firmID;
  private Long withdrawID;
  private String consignerID;
  private String consignFirmID;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
  }
  
  public String getTraderID()
  {
    return this.traderID;
  }
  
  public void setTraderID(String traderID)
  {
    this.traderID = traderID;
  }
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public Long getWithdrawID()
  {
    return this.withdrawID;
  }
  
  public void setWithdrawID(Long withdrawID)
  {
    this.withdrawID = withdrawID;
  }
  
  public String getConsignerID()
  {
    return this.consignerID;
  }
  
  public void setConsignerID(String consignerID)
  {
    this.consignerID = consignerID;
  }
  
  public String getConsignFirmID()
  {
    return this.consignFirmID;
  }
  
  public void setConsignFirmID(String consignFirmID)
  {
    this.consignFirmID = consignFirmID;
  }
}
