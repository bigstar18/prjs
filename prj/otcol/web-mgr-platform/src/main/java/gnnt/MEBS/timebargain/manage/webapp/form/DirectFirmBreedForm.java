package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class DirectFirmBreedForm
  extends BaseForm
  implements Serializable
{
  private String firmId;
  private int breedId;
  private int breedTradeMode;
  
  public int getBreedTradeMode()
  {
    return this.breedTradeMode;
  }
  
  public void setBreedTradeMode(int paramInt)
  {
    this.breedTradeMode = paramInt;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public int getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(int paramInt)
  {
    this.breedId = paramInt;
  }
}
