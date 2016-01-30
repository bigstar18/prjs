package gnnt.MEBS.bill.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;

public class Logistics
  extends StandardModel
{
  private String stockID;
  private String logisticsorder;
  private String company;
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("StockID", this.stockID);
  }
  
  public String getStockID()
  {
    return this.stockID;
  }
  
  public void setStockID(String stockID)
  {
    this.stockID = stockID;
  }
  
  public String getLogisticsorder()
  {
    return this.logisticsorder;
  }
  
  public void setLogisticsorder(String logisticsorder)
  {
    this.logisticsorder = logisticsorder;
  }
  
  public String getCompany()
  {
    return this.company;
  }
  
  public void setCompany(String company)
  {
    this.company = company;
  }
}
