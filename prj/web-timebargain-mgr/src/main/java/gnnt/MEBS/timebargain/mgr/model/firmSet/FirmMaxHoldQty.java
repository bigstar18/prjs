package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmMaxHoldQty extends StandardModel
{
  private static final long serialVersionUID = 1711421127321464640L;

  @ClassDiscription(name="交易商ID", description="")
  private String firmID;

  @ClassDiscription(name=" 商品ID", description="")
  private String commodityID;

  @ClassDiscription(name=" 最大订货量", description="")
  private Integer maxHoldQty;

  @ClassDiscription(name=" 最大净订货量", description="")
  private Integer cleanMaxHoldQty;

  @ClassDiscription(name="最大订货量", description="")
  private Date modifyTime;

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }

  public String getCommodityID()
  {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }

  public Integer getMaxHoldQty()
  {
    return this.maxHoldQty;
  }

  public void setMaxHoldQty(Integer maxHoldQty)
  {
    this.maxHoldQty = maxHoldQty;
  }

  public Integer getCleanMaxHoldQty()
  {
    return this.cleanMaxHoldQty;
  }

  public void setCleanMaxHoldQty(Integer cleanMaxHoldQty)
  {
    this.cleanMaxHoldQty = cleanMaxHoldQty;
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