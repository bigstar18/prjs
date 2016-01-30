package gnnt.MEBS.bill.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import java.sql.Date;

public class StockList
  extends StandardModel
{
  private String stockId;
  private String breedName;
  private String warehouseId;
  private Double quantity;
  private String unit;
  private String lastTime;
  private String ownerFirm;
  private String createTime;
  private String logisticsorder;
  private String company;
  private String received;
  private Date receivedDate;
  private String invoiceStatus;
  
  public String getOwnerFirm()
  {
    return this.ownerFirm;
  }
  
  public void setOwnerFirm(String ownerFirm)
  {
    this.ownerFirm = ownerFirm;
  }
  
  public String getInvoiceStatus()
  {
    return this.invoiceStatus;
  }
  
  public void setInvoiceStatus(String invoiceStatus)
  {
    this.invoiceStatus = invoiceStatus;
  }
  
  public String getStockId()
  {
    return this.stockId;
  }
  
  public void setStockId(String stockId)
  {
    this.stockId = stockId;
  }
  
  public String getBreedName()
  {
    return this.breedName;
  }
  
  public void setBreedName(String breedName)
  {
    this.breedName = breedName;
  }
  
  public String getWarehouseId()
  {
    return this.warehouseId;
  }
  
  public void setWarehouseId(String warehouseId)
  {
    this.warehouseId = warehouseId;
  }
  
  public Double getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(Double quantity)
  {
    this.quantity = quantity;
  }
  
  public String getUnit()
  {
    return this.unit;
  }
  
  public void setUnit(String unit)
  {
    this.unit = unit;
  }
  
  public String getLastTime()
  {
    return this.lastTime;
  }
  
  public void setLastTime(String lastTime)
  {
    this.lastTime = lastTime;
  }
  
  public String getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(String createTime)
  {
    this.createTime = createTime;
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
  
  public String getReceived()
  {
    return this.received;
  }
  
  public void setReceived(String received)
  {
    this.received = received;
  }
  
  public Date getReceivedDate()
  {
    return this.receivedDate;
  }
  
  public void setReceivedDate(Date receivedDate)
  {
    this.receivedDate = receivedDate;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
