package gnnt.MEBS.bill.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.TradeModule;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class WarehouseLogCatalog
  extends StandardModel
{
  private static final long serialVersionUID = 2034334164533528428L;
  @ClassDiscription(name="日志类别号", description="")
  private Integer catalogID;
  @ClassDiscription(name="日志类别名称", description="")
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
