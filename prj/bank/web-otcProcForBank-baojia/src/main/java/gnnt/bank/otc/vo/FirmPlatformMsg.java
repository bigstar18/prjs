package gnnt.bank.otc.vo;

import java.io.Serializable;

public class FirmPlatformMsg
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String platFirmID;
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }
  
  public String getPlatFirmID()
  {
    return this.platFirmID;
  }
  
  public void setPlatFirmID(String platFirmID)
  {
    this.platFirmID = platFirmID;
  }
}
