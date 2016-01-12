package gnnt.MEBS.timebargain.mgr.model.tradeparams;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class CommodityTariff extends StandardModel
{

  @ClassDiscription(name="", description="")
  private String tariffID;

  @ClassDiscription(name="", description="")
  private String name;

  @ClassDiscription(name="", description="")
  private String commodityID;

  @ClassDiscription(name="", description="")
  private String tariffName;

  @ClassDiscription(name="", description="")
  private String tariffRate;

  @ClassDiscription(name="", description="")
  private String newFeeRate_B;

  @ClassDiscription(name="", description="")
  private String newFeeRate_S;

  @ClassDiscription(name="", description="")
  private String newTodayCloseFeeRate_B;

  @ClassDiscription(name="", description="")
  private String newTodayCloseFeeRate_S;

  @ClassDiscription(name="", description="")
  private String newHistoryCloseFeeRate_B;

  @ClassDiscription(name="", description="")
  private String newHistoryCloseFeeRate_S;

  @ClassDiscription(name="", description="")
  private String newForceCloseFeeRate_B;

  @ClassDiscription(name="", description="")
  private String newForceCloseFeeRate_S;

  @ClassDiscription(name="", description="")
  private String oldFeeRate_B;

  @ClassDiscription(name="", description="")
  private String oldFeeRate_S;

  @ClassDiscription(name="", description="")
  private String oldTodayCloseFeeRate_B;

  @ClassDiscription(name="", description="")
  private String oldTodayCloseFeeRate_S;

  @ClassDiscription(name="", description="")
  private String oldHistoryCloseFeeRate_B;

  @ClassDiscription(name="", description="")
  private String oldHistoryCloseFeeRate_S;

  @ClassDiscription(name="", description="")
  private String oldForceCloseFeeRate_B;

  @ClassDiscription(name="", description="")
  private String oldForceCloseFeeRate_S;

  @ClassDiscription(name="", description="")
  private String createUser;

  @ClassDiscription(name="", description="")
  private Date modifyTime;

  public String getTariffID()
  {
    return this.tariffID;
  }

  public void setTariffID(String tariffID)
  {
    this.tariffID = tariffID;
  }

  public String getCommodityID()
  {
    return this.commodityID;
  }

  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }

  public String getTariffName()
  {
    return this.tariffName;
  }

  public void setTariffName(String tariffName) {
    this.tariffName = tariffName;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getTariffRate()
  {
    return this.tariffRate;
  }

  public void setTariffRate(String tariffRate)
  {
    this.tariffRate = tariffRate;
  }

  public String getNewFeeRate_B()
  {
    return this.newFeeRate_B;
  }

  public void setNewFeeRate_B(String newFeeRateB)
  {
    this.newFeeRate_B = newFeeRateB;
  }

  public String getNewFeeRate_S()
  {
    return this.newFeeRate_S;
  }

  public void setNewFeeRate_S(String newFeeRateS)
  {
    this.newFeeRate_S = newFeeRateS;
  }

  public String getNewTodayCloseFeeRate_B()
  {
    return this.newTodayCloseFeeRate_B;
  }

  public void setNewTodayCloseFeeRate_B(String newTodayCloseFeeRateB)
  {
    this.newTodayCloseFeeRate_B = newTodayCloseFeeRateB;
  }

  public String getNewTodayCloseFeeRate_S()
  {
    return this.newTodayCloseFeeRate_S;
  }

  public void setNewTodayCloseFeeRate_S(String newTodayCloseFeeRateS)
  {
    this.newTodayCloseFeeRate_S = newTodayCloseFeeRateS;
  }

  public String getNewHistoryCloseFeeRate_B()
  {
    return this.newHistoryCloseFeeRate_B;
  }

  public void setNewHistoryCloseFeeRate_B(String newHistoryCloseFeeRateB)
  {
    this.newHistoryCloseFeeRate_B = newHistoryCloseFeeRateB;
  }

  public String getNewHistoryCloseFeeRate_S()
  {
    return this.newHistoryCloseFeeRate_S;
  }

  public void setNewHistoryCloseFeeRate_S(String newHistoryCloseFeeRateS)
  {
    this.newHistoryCloseFeeRate_S = newHistoryCloseFeeRateS;
  }

  public String getNewForceCloseFeeRate_B()
  {
    return this.newForceCloseFeeRate_B;
  }

  public void setNewForceCloseFeeRate_B(String newForceCloseFeeRateB)
  {
    this.newForceCloseFeeRate_B = newForceCloseFeeRateB;
  }

  public String getNewForceCloseFeeRate_S()
  {
    return this.newForceCloseFeeRate_S;
  }

  public void setNewForceCloseFeeRate_S(String newForceCloseFeeRateS)
  {
    this.newForceCloseFeeRate_S = newForceCloseFeeRateS;
  }

  public String getOldFeeRate_B()
  {
    return this.oldFeeRate_B;
  }

  public void setOldFeeRate_B(String oldFeeRateB)
  {
    this.oldFeeRate_B = oldFeeRateB;
  }

  public String getOldFeeRate_S()
  {
    return this.oldFeeRate_S;
  }

  public void setOldFeeRate_S(String oldFeeRateS)
  {
    this.oldFeeRate_S = oldFeeRateS;
  }

  public String getOldTodayCloseFeeRate_B()
  {
    return this.oldTodayCloseFeeRate_B;
  }

  public void setOldTodayCloseFeeRate_B(String oldTodayCloseFeeRateB)
  {
    this.oldTodayCloseFeeRate_B = oldTodayCloseFeeRateB;
  }

  public String getOldTodayCloseFeeRate_S()
  {
    return this.oldTodayCloseFeeRate_S;
  }

  public void setOldTodayCloseFeeRate_S(String oldTodayCloseFeeRateS)
  {
    this.oldTodayCloseFeeRate_S = oldTodayCloseFeeRateS;
  }

  public String getOldHistoryCloseFeeRate_B()
  {
    return this.oldHistoryCloseFeeRate_B;
  }

  public void setOldHistoryCloseFeeRate_B(String oldHistoryCloseFeeRateB)
  {
    this.oldHistoryCloseFeeRate_B = oldHistoryCloseFeeRateB;
  }

  public String getOldHistoryCloseFeeRate_S()
  {
    return this.oldHistoryCloseFeeRate_S;
  }

  public void setOldHistoryCloseFeeRate_S(String oldHistoryCloseFeeRateS)
  {
    this.oldHistoryCloseFeeRate_S = oldHistoryCloseFeeRateS;
  }

  public String getOldForceCloseFeeRate_B()
  {
    return this.oldForceCloseFeeRate_B;
  }

  public void setOldForceCloseFeeRate_B(String oldForceCloseFeeRateB)
  {
    this.oldForceCloseFeeRate_B = oldForceCloseFeeRateB;
  }

  public String getOldForceCloseFeeRate_S()
  {
    return this.oldForceCloseFeeRate_S;
  }

  public void setOldForceCloseFeeRate_S(String oldForceCloseFeeRateS)
  {
    this.oldForceCloseFeeRate_S = oldForceCloseFeeRateS;
  }

  public String getCreateUser()
  {
    return this.createUser;
  }

  public void setCreateUser(String createUser)
  {
    this.createUser = createUser;
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