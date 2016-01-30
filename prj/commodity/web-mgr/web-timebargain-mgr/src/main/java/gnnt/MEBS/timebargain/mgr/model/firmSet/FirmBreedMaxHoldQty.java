package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmBreedMaxHoldQty extends StandardModel
{
  private static final long serialVersionUID = 6875546413546567681L;

  @ClassDiscription(name="交易商ID", description="")
  private String firmID;

  @ClassDiscription(name="商品品种ID", description="")
  private Integer breedID;

  @ClassDiscription(name="最大订货量", description="")
  private Integer maxHoldQty;

  @ClassDiscription(name="最大净订货量", description="")
  private Integer cleanMaxHoldQty;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;

  public String getFirmID()
  {
    return this.firmID;
  }

  public void setFirmID(String firmID)
  {
    this.firmID = firmID;
  }

  public Integer getBreedID()
  {
    return this.breedID;
  }

  public void setBreedID(Integer breedID)
  {
    this.breedID = breedID;
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