package gnnt.MEBS.common.broker.model;

public class LogCatalog extends StandardModel
{
  private static final long serialVersionUID = -4112756031753238119L;
  private Integer catalogID;
  private String catalogName;
  private TradeModule tradeModule;

  public Integer getCatalogID()
  {
    return this.catalogID;
  }

  public void setCatalogID(Integer paramInteger)
  {
    this.catalogID = paramInteger;
  }

  public String getCatalogName()
  {
    return this.catalogName;
  }

  public void setCatalogName(String paramString)
  {
    this.catalogName = paramString;
  }

  public TradeModule getTradeModule()
  {
    return this.tradeModule;
  }

  public void setTradeModule(TradeModule paramTradeModule)
  {
    this.tradeModule = paramTradeModule;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("catalogID", this.catalogID);
  }
}