package gnnt.MEBS.bill.front.model.stockmanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class VOutStock
  extends StandardModel
{
  private static final long serialVersionUID = -8950231334954432617L;
  private String stockId;
  private String realStockCode;
  private String breedName;
  private String warehouseId;
  private String quantity;
  private String unit;
  private Integer breedId;
  private String ownerFirm;
  private Date createTime;
  private Date lastTime;
  private String company;
  private String logisticsOrder;
  private String isReceived;
  private Date receivedDate;
  private String invoiceStatus;
  
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
  
  public String getRealStockCode()
  {
    return this.realStockCode;
  }
  
  public void setRealStockCode(String realStockCode)
  {
    this.realStockCode = realStockCode;
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
  
  public String getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(String quantity)
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
  
  public Integer getBreedId()
  {
    return this.breedId;
  }
  
  public void setBreedId(Integer breedId)
  {
    this.breedId = breedId;
  }
  
  public String getOwnerFirm()
  {
    return this.ownerFirm;
  }
  
  public void setOwnerFirm(String ownerFirm)
  {
    this.ownerFirm = ownerFirm;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public Date getLastTime()
  {
    return this.lastTime;
  }
  
  public void setLastTime(Date lastTime)
  {
    this.lastTime = lastTime;
  }
  
  public String getCompany()
  {
    return this.company;
  }
  
  public void setCompany(String company)
  {
    this.company = company;
  }
  
  public String getLogisticsOrder()
  {
    return this.logisticsOrder;
  }
  
  public void setLogisticsOrder(String logisticsOrder)
  {
    this.logisticsOrder = logisticsOrder;
  }
  
  public String getIsReceived()
  {
    return this.isReceived;
  }
  
  public void setIsReceived(String isReceived)
  {
    this.isReceived = isReceived;
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
