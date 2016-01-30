package gnnt.MEBS.bill.core.po;

import java.io.Serializable;
import java.util.Date;

public class OutStockPO
  extends Clone
  implements Serializable
{
  private static final long serialVersionUID = -4848377573354299414L;
  private long outStockID;
  private String stockID;
  private String key;
  private String deliveryPerson;
  private String idnumber;
  private String address;
  private String phone;
  private int deliveryStatus;
  private int status;
  private Date createTime;
  private Date processTime;
  
  public long getOutStockID()
  {
    return this.outStockID;
  }
  
  public void setOutStockID(long outStockID)
  {
    this.outStockID = outStockID;
  }
  
  public String getStockID()
  {
    return this.stockID;
  }
  
  public void setStockID(String stockID)
  {
    this.stockID = stockID;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  public String getDeliveryPerson()
  {
    return this.deliveryPerson;
  }
  
  public void setDeliveryPerson(String deliveryPerson)
  {
    this.deliveryPerson = deliveryPerson;
  }
  
  public String getIdnumber()
  {
    return this.idnumber;
  }
  
  public void setIdnumber(String idnumber)
  {
    this.idnumber = idnumber;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int status)
  {
    this.status = status;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public Date getProcessTime()
  {
    return this.processTime;
  }
  
  public void setProcessTime(Date processTime)
  {
    this.processTime = processTime;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public Integer getDeliveryStatus()
  {
    return Integer.valueOf(this.deliveryStatus);
  }
  
  public void setDeliveryStatus(Integer deliveryStatus)
  {
    this.deliveryStatus = deliveryStatus.intValue();
  }
}
