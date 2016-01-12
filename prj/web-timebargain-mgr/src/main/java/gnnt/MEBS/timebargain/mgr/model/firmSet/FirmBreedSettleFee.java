package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmBreedSettleFee extends StandardModel
{
  private static final long serialVersionUID = 2071505137452575579L;

  @ClassDiscription(name=" 交易商ID", description="")
  private String firmID;

  @ClassDiscription(name="商品品种ID", description="")
  private Integer breedID;

  @ClassDiscription(name="交收手续费算法", description="")
  private Integer settleFeeAlgr;

  @ClassDiscription(name="交收买手续费系数", description="")
  private Double settleFeeRate_B;

  @ClassDiscription(name="交收卖手续费系数", description="")
  private Double settleFeeRate_S;

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

  public Integer getSettleFeeAlgr()
  {
    return this.settleFeeAlgr;
  }

  public void setSettleFeeAlgr(Integer settleFeeAlgr)
  {
    this.settleFeeAlgr = settleFeeAlgr;
  }

  public Double getSettleFeeRate_B()
  {
    return this.settleFeeRate_B;
  }

  public void setSettleFeeRate_B(Double settleFeeRateB)
  {
    this.settleFeeRate_B = settleFeeRateB;
  }

  public Double getSettleFeeRate_S()
  {
    return this.settleFeeRate_S;
  }

  public void setSettleFeeRate_S(Double settleFeeRateS)
  {
    this.settleFeeRate_S = settleFeeRateS;
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