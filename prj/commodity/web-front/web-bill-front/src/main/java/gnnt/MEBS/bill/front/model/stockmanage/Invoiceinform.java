package gnnt.MEBS.bill.front.model.stockmanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;

public class Invoiceinform
  extends StandardModel
{
  private static final long serialVersionUID = 8284250560959508969L;
  private String stockId;
  private String invoiceType;
  private String companyName;
  private String address;
  private String dutyParagraph;
  private String bank;
  private String bankAccount;
  private String name;
  private String phone;
  private String breedName;
  private String warehouseId;
  private Double quantity;
  private String unit;
  
  public String getStockId()
  {
    return this.stockId;
  }
  
  public void setStockId(String stockId)
  {
    this.stockId = stockId;
  }
  
  public String getInvoiceType()
  {
    return this.invoiceType;
  }
  
  public void setInvoiceType(String invoiceType)
  {
    this.invoiceType = invoiceType;
  }
  
  public String getCompanyName()
  {
    return this.companyName;
  }
  
  public void setCompanyName(String companyName)
  {
    this.companyName = companyName;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getDutyParagraph()
  {
    return this.dutyParagraph;
  }
  
  public void setDutyParagraph(String dutyParagraph)
  {
    this.dutyParagraph = dutyParagraph;
  }
  
  public String getBank()
  {
    return this.bank;
  }
  
  public void setBank(String bank)
  {
    this.bank = bank;
  }
  
  public String getBankAccount()
  {
    return this.bankAccount;
  }
  
  public void setBankAccount(String bankAccount)
  {
    this.bankAccount = bankAccount;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
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
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("stockId", this.stockId);
  }
}
