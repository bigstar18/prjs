package gnnt.MEBS.bill.front.model.stockmanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import java.sql.Date;

public class BusinessRelationship
  extends StandardModel
{
  private static final long serialVersionUID = 7206657362675950934L;
  private String stockId;
  private String buyer;
  private String seller;
  private String received;
  private Date receivedDate;
  private Date sellTime;
  private String breedName;
  private String warehouseId;
  private Double quantity;
  private String unit;
  private String invoiceStatus;
  
  public String getInvoiceStatus()
  {
    return this.invoiceStatus;
  }
  
  public void setInvoiceStatus(String invoiceStatus)
  {
    this.invoiceStatus = invoiceStatus;
  }
  
  public Date getSellTime()
  {
    return this.sellTime;
  }
  
  public void setSellTime(Date sellTime)
  {
    this.sellTime = sellTime;
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
  
  public String getStockId()
  {
    return this.stockId;
  }
  
  public void setStockId(String stockId)
  {
    this.stockId = stockId;
  }
  
  public String getBuyer()
  {
    return this.buyer;
  }
  
  public void setBuyer(String buyer)
  {
    this.buyer = buyer;
  }
  
  public String getSeller()
  {
    return this.seller;
  }
  
  public void setSeller(String seller)
  {
    this.seller = seller;
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
