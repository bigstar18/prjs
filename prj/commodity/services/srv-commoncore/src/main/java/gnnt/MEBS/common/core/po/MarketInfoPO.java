package gnnt.MEBS.common.core.po;

import java.io.Serializable;

public class MarketInfoPO
  extends Clone
  implements Serializable
{
  private static final long serialVersionUID = -5992703755248570484L;
  private String infoName;
  private String infoValue;
  
  public String getInfoName()
  {
    return this.infoName;
  }
  
  public void setInfoName(String paramString)
  {
    this.infoName = paramString;
  }
  
  public String getInfoValue()
  {
    return this.infoValue;
  }
  
  public void setInfoValue(String paramString)
  {
    this.infoValue = paramString;
  }
}
