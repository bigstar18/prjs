package gnnt.MEBS.zcjs.model;

import gnnt.MEBS.base.model.Clone;

public class FirmPermission
  extends Clone
{
  private String firmId;
  private String buyListing;
  private String sellListing;
  private String buyDelist;
  private String sellDelist;
  private String sellRegstock;
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String getBuyListing()
  {
    return this.buyListing;
  }
  
  public void setBuyListing(String paramString)
  {
    this.buyListing = paramString;
  }
  
  public String getSellListing()
  {
    return this.sellListing;
  }
  
  public void setSellListing(String paramString)
  {
    this.sellListing = paramString;
  }
  
  public String getBuyDelist()
  {
    return this.buyDelist;
  }
  
  public void setBuyDelist(String paramString)
  {
    this.buyDelist = paramString;
  }
  
  public String getSellDelist()
  {
    return this.sellDelist;
  }
  
  public void setSellDelist(String paramString)
  {
    this.sellDelist = paramString;
  }
  
  public String getSellRegstock()
  {
    return this.sellRegstock;
  }
  
  public void setSellRegstock(String paramString)
  {
    this.sellRegstock = paramString;
  }
  
  public void set(String paramString1, String paramString2)
  {
    if ("buyListing".equals(paramString1)) {
      setBuyListing(paramString2);
    }
    if ("sellListing".equals(paramString1)) {
      setSellListing(paramString2);
    }
    if ("buyDelist".equals(paramString1)) {
      setBuyDelist(paramString2);
    }
    if ("sellDelist".equals(paramString1)) {
      setSellDelist(paramString2);
    }
    if ("sellRegstock".equals(paramString1)) {
      setSellRegstock(paramString2);
    }
  }
}
