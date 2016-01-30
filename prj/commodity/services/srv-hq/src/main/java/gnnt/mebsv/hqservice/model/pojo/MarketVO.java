package gnnt.mebsv.hqservice.model.pojo;

public class MarketVO
{
  private String marketID;
  private String marketName;
  private String direction;
  private String description;

  public String getMarketID()
  {
    return this.marketID;
  }

  public void setMarketID(String paramString)
  {
    this.marketID = paramString;
  }

  public String getMarketName()
  {
    return this.marketName;
  }

  public void setMarketName(String paramString)
  {
    this.marketName = paramString;
  }

  public String getDirection()
  {
    return this.direction;
  }

  public void setDirection(String paramString)
  {
    this.direction = paramString;
  }

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
}