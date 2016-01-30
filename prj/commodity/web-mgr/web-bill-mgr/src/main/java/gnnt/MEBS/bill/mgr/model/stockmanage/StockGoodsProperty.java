package gnnt.MEBS.bill.mgr.model.stockmanage;

import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class StockGoodsProperty
  extends StandardModel
{
  private static final long serialVersionUID = 1L;
  @ClassDiscription(name="关联仓单", description="对应仓单ID")
  private Stock stock;
  @ClassDiscription(name="属性名称", description="")
  private String propertyName;
  @ClassDiscription(name="属性值", description="")
  private String propertyValue;
  @ClassDiscription(name="类型编号", description="")
  private Long propertyTypeID;
  
  public Stock getStock()
  {
    return this.stock;
  }
  
  public void setStock(Stock paramStock)
  {
    this.stock = paramStock;
  }
  
  public String getPropertyName()
  {
    return this.propertyName;
  }
  
  public void setPropertyName(String paramString)
  {
    this.propertyName = paramString;
  }
  
  public String getPropertyValue()
  {
    return this.propertyValue;
  }
  
  public void setPropertyValue(String paramString)
  {
    this.propertyValue = paramString;
  }
  
  public Long getPropertyTypeID()
  {
    return this.propertyTypeID;
  }
  
  public void setPropertyTypeID(Long paramLong)
  {
    this.propertyTypeID = paramLong;
  }
  
  public GoodsPropertyPO getGoodsPropertyPO()
  {
    GoodsPropertyPO localGoodsPropertyPO = new GoodsPropertyPO();
    localGoodsPropertyPO.setStockID(getStock().getStockId());
    localGoodsPropertyPO.setPropertyName(getPropertyName());
    localGoodsPropertyPO.setPropertyValue(getPropertyValue());
    if (getPropertyTypeID() != null) {
      localGoodsPropertyPO.setPropertyTypeID(getPropertyTypeID().longValue());
    }
    return localGoodsPropertyPO;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}
