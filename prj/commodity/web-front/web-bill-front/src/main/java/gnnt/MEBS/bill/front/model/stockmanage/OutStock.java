package gnnt.MEBS.bill.front.model.stockmanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class OutStock
  extends StandardModel
{
  private static final long serialVersionUID = -7404775599162914954L;
  private long outStockID;
  private Stock stock;
  private String key;
  private String deliveryPerson;
  private String idnumber;
  private int status;
  private Date createTime;
  private Date processTime;
  
  public long getOutStockID()
  {
    return this.outStockID;
  }
  
  public void setOutStockID(long paramLong)
  {
    this.outStockID = paramLong;
  }
  
  public Stock getStock()
  {
    return this.stock;
  }
  
  public void setStock(Stock paramStock)
  {
    this.stock = paramStock;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String paramString)
  {
    this.key = paramString;
  }
  
  public String getDeliveryPerson()
  {
    return this.deliveryPerson;
  }
  
  public void setDeliveryPerson(String paramString)
  {
    this.deliveryPerson = paramString;
  }
  
  public String getIdnumber()
  {
    return this.idnumber;
  }
  
  public void setIdnumber(String paramString)
  {
    this.idnumber = paramString;
  }
  
  public int getStatus()
  {
    return this.status;
  }
  
  public void setStatus(int paramInt)
  {
    this.status = paramInt;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public Date getProcessTime()
  {
    return this.processTime;
  }
  
  public void setProcessTime(Date paramDate)
  {
    this.processTime = paramDate;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("outStockID", Long.valueOf(this.outStockID));
  }
}
