package gnnt.MEBS.timebargain.mgr.model.delay;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class CommoditySettleProp extends StandardModel
{

  @ClassDiscription(name="商品代码", description="")
  private String commodityID;

  @ClassDiscription(name="交易节编号", description="")
  private Long sectionID;

  @ClassDiscription(name="交易节编号", description="")
  private Date modifyTime;

  public String getCommodityID()
  {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }

  public Long getSectionID()
  {
    return this.sectionID;
  }

  public void setSectionID(Long sectionID)
  {
    this.sectionID = sectionID;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}