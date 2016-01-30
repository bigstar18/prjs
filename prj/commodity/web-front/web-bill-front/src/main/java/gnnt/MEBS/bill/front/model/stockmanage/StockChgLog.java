package gnnt.MEBS.bill.front.model.stockmanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.util.Date;

public class StockChgLog
  extends StandardModel
{
  private static final long serialVersionUID = 4786267340292396846L;
  @ClassDiscription(name="变更记录编号", description="")
  private Long id;
  @ClassDiscription(name="关联仓单", description="对应仓单编号")
  private Stock stock;
  @ClassDiscription(name="原货权人", description="")
  private String srcFirm;
  @ClassDiscription(name="新货权人", description="")
  private String tarFirm;
  @ClassDiscription(name="创建时间", description="")
  private Date createTime;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }
  
  public Stock getStock()
  {
    return this.stock;
  }
  
  public void setStock(Stock paramStock)
  {
    this.stock = paramStock;
  }
  
  public String getSrcFirm()
  {
    return this.srcFirm;
  }
  
  public void setSrcFirm(String paramString)
  {
    this.srcFirm = paramString;
  }
  
  public String getTarFirm()
  {
    return this.tarFirm;
  }
  
  public void setTarFirm(String paramString)
  {
    this.tarFirm = paramString;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}
