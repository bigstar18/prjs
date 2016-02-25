package gnnt.MEBS.delivery.model;

import gnnt.MEBS.base.model.Clone;

public class Dealer
  extends Clone
{
  private String firmId;
  private String name;
  private String linkman;
  private String tel;
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String getLinkman()
  {
    return this.linkman;
  }
  
  public void setLinkman(String paramString)
  {
    this.linkman = paramString;
  }
  
  public String getTel()
  {
    return this.tel;
  }
  
  public void setTel(String paramString)
  {
    this.tel = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
}
