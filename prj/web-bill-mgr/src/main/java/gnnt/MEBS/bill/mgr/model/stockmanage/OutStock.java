package gnnt.MEBS.bill.mgr.model.stockmanage;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class OutStock
  extends StandardModel
{
  private static final long serialVersionUID = -5013524002449767064L;
  @ClassDiscription(name="仓单出库编号", description="")
  private Long outStockID;
  @ClassDiscription(name="仓单号", description="")
  private String stockID;
  @ClassDiscription(name="出库密钥", description="")
  private String key;
  @ClassDiscription(name="提货人", description="")
  private String deliveryPerson;
  @ClassDiscription(name="提货人身份证号", description="")
  private String idnumber;
  @ClassDiscription(name="状态", description="0 出库申请 1 撤销出库申请 2 出库完成")
  private Integer status;
  @ClassDiscription(name="创建时间", description="")
  private Date createTime;
  @ClassDiscription(name="处理时间", description="")
  private Date processTime;
  @ClassDiscription(name="仓单", description="")
  private Stock stock;
  @ClassDiscription(name="配送地址", description="")
  private String address;
  @ClassDiscription(name="电话", description="")
  private String phone;
  @ClassDiscription(name="提货类型", description="0 用户自提 1 配送")
  private Integer deliveryStatus;
  
  public Long getOutStockID()
  {
    return this.outStockID;
  }
  
  public void setOutStockID(Long outStockID)
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
  
  public Integer getStatus()
  {
    return this.status;
  }
  
  public void setStatus(Integer status)
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
  
  public Stock getStock()
  {
    return this.stock;
  }
  
  public void setStock(Stock stock)
  {
    this.stock = stock;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("outStockID", this.outStockID);
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
    return this.deliveryStatus;
  }
  
  public void setDeliveryStatus(Integer deliveryStatus)
  {
    this.deliveryStatus = deliveryStatus;
  }
}
