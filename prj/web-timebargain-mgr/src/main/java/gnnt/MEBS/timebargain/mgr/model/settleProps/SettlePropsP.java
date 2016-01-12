package gnnt.MEBS.timebargain.mgr.model.settleProps;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;

public class SettlePropsP extends StandardModel
{
  private static final long serialVersionUID = -1L;

  @ClassDiscription(name="交收属性对象id", description="")
  private Long id;

  @ClassDiscription(name="商品代码", description="")
  private String commodityId;

  @ClassDiscription(name="属性名称", description="")
  private String propertyName;

  @ClassDiscription(name="属性值", description="")
  private String propertyValue;

  @ClassDiscription(name="备注", description="")
  private String note;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getCommodityId()
  {
    return this.commodityId;
  }

  public void setCommodityId(String commodityId)
  {
    this.commodityId = commodityId;
  }

  public String getPropertyName()
  {
    return this.propertyName;
  }

  public void setPropertyName(String propertyName)
  {
    this.propertyName = propertyName;
  }

  public String getPropertyValue()
  {
    return this.propertyValue;
  }

  public void setPropertyValue(String propertyValue)
  {
    this.propertyValue = propertyValue;
  }

  public String getNote()
  {
    return this.note;
  }

  public void setNote(String note)
  {
    this.note = note;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "id", this.id);
  }
}