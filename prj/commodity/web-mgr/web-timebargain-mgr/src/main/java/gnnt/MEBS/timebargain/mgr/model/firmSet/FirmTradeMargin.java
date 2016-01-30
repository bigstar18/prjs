package gnnt.MEBS.timebargain.mgr.model.firmSet;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.mgr.model.translate.ClassDiscription;
import java.util.Date;

public class FirmTradeMargin extends StandardModel
{
  private static final long serialVersionUID = 1238063406121245627L;

  @ClassDiscription(name="交易商ID", description="")
  private String firmID;

  @ClassDiscription(name="商品ID", description="")
  private String commodityID;

  @ClassDiscription(name="保证金算法", description="")
  private Integer marginAlgr;

  @ClassDiscription(name="买保证金系数", description="")
  private Double marginRate_B;

  @ClassDiscription(name="卖保证金系数", description="")
  private Double marginRate_S;

  @ClassDiscription(name="买保证金款项1", description="")
  private Double marginItem1;

  @ClassDiscription(name="买保证金款项2", description="")
  private Double marginItem2;

  @ClassDiscription(name="买保证金款项3", description="")
  private Double marginItem3;

  @ClassDiscription(name="买保证金款项4", description="")
  private Double marginItem4;

  @ClassDiscription(name="买保证金款项5", description="")
  private Double marginItem5;

  @ClassDiscription(name="卖保证金款项1", description="")
  private Double marginItem1_S;

  @ClassDiscription(name="卖保证金款项2", description="")
  private Double marginItem2_S;

  @ClassDiscription(name="卖保证金款项2", description="")
  private Double marginItem3_S;

  @ClassDiscription(name="卖保证金款项4", description="")
  private Double marginItem4_S;

  @ClassDiscription(name="卖保证金款项5", description="")
  private Double marginItem5_S;

  @ClassDiscription(name="买担保金系数", description="")
  private Double marginAssure_B;

  @ClassDiscription(name="卖担保金系数", description="")
  private Double marginAssure_S;

  @ClassDiscription(name="买担保金款项1", description="")
  private Double marginItemAssure1;

  @ClassDiscription(name="买担保金款项2", description="")
  private Double marginItemAssure2;

  @ClassDiscription(name="买担保金款项3", description="")
  private Double marginItemAssure3;

  @ClassDiscription(name="买担保金款项4", description="")
  private Double marginItemAssure4;

  @ClassDiscription(name="买担保金款项5", description="")
  private Double marginItemAssure5;

  @ClassDiscription(name="卖担保金款项1", description="")
  private Double marginItemAssure1_S;

  @ClassDiscription(name="卖担保金款项2", description="")
  private Double marginItemAssure2_S;

  @ClassDiscription(name="卖担保金款项3", description="")
  private Double marginItemAssure3_S;

  @ClassDiscription(name="卖担保金款项4", description="")
  private Double marginItemAssure4_S;

  @ClassDiscription(name="卖担保金款项5", description="")
  private Double marginItemAssure5_S;

  @ClassDiscription(name="修改时间", description="")
  private Date modifyTime;
  private Integer type1;
  private Integer type2;
  private Integer type3;
  private Integer type4;
  private Integer type5;

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

  public Integer getMarginAlgr()
  {
    return this.marginAlgr;
  }

  public void setMarginAlgr(Integer marginAlgr)
  {
    this.marginAlgr = marginAlgr;
  }

  public Double getMarginRate_B()
  {
    return this.marginRate_B;
  }

  public void setMarginRate_B(Double marginRateB)
  {
    this.marginRate_B = marginRateB;
  }

  public Double getMarginRate_S()
  {
    return this.marginRate_S;
  }

  public void setMarginRate_S(Double marginRateS)
  {
    this.marginRate_S = marginRateS;
  }

  public Double getMarginItem1()
  {
    return this.marginItem1;
  }

  public void setMarginItem1(Double marginItem1)
  {
    this.marginItem1 = marginItem1;
  }

  public Double getMarginItem2()
  {
    return this.marginItem2;
  }

  public void setMarginItem2(Double marginItem2)
  {
    this.marginItem2 = marginItem2;
  }

  public Double getMarginItem3()
  {
    return this.marginItem3;
  }

  public void setMarginItem3(Double marginItem3)
  {
    this.marginItem3 = marginItem3;
  }

  public Double getMarginItem4()
  {
    return this.marginItem4;
  }

  public void setMarginItem4(Double marginItem4)
  {
    this.marginItem4 = marginItem4;
  }

  public Double getMarginItem5()
  {
    return this.marginItem5;
  }

  public void setMarginItem5(Double marginItem5)
  {
    this.marginItem5 = marginItem5;
  }

  public Double getMarginItem1_S()
  {
    return this.marginItem1_S;
  }

  public void setMarginItem1_S(Double marginItem1S)
  {
    this.marginItem1_S = marginItem1S;
  }

  public Double getMarginItem2_S()
  {
    return this.marginItem2_S;
  }

  public void setMarginItem2_S(Double marginItem2S)
  {
    this.marginItem2_S = marginItem2S;
  }

  public Double getMarginItem3_S()
  {
    return this.marginItem3_S;
  }

  public void setMarginItem3_S(Double marginItem3S)
  {
    this.marginItem3_S = marginItem3S;
  }

  public Double getMarginItem4_S()
  {
    return this.marginItem4_S;
  }

  public void setMarginItem4_S(Double marginItem4S)
  {
    this.marginItem4_S = marginItem4S;
  }

  public Double getMarginItem5_S()
  {
    return this.marginItem5_S;
  }

  public void setMarginItem5_S(Double marginItem5S)
  {
    this.marginItem5_S = marginItem5S;
  }

  public Double getMarginAssure_B()
  {
    return this.marginAssure_B;
  }

  public void setMarginAssure_B(Double marginAssureB)
  {
    this.marginAssure_B = marginAssureB;
  }

  public Double getMarginAssure_S()
  {
    return this.marginAssure_S;
  }

  public void setMarginAssure_S(Double marginAssureS)
  {
    this.marginAssure_S = marginAssureS;
  }

  public Double getMarginItemAssure1()
  {
    return this.marginItemAssure1;
  }

  public void setMarginItemAssure1(Double marginItemAssure1)
  {
    this.marginItemAssure1 = marginItemAssure1;
  }

  public Double getMarginItemAssure2()
  {
    return this.marginItemAssure2;
  }

  public void setMarginItemAssure2(Double marginItemAssure2)
  {
    this.marginItemAssure2 = marginItemAssure2;
  }

  public Double getMarginItemAssure3()
  {
    return this.marginItemAssure3;
  }

  public void setMarginItemAssure3(Double marginItemAssure3)
  {
    this.marginItemAssure3 = marginItemAssure3;
  }

  public Double getMarginItemAssure4()
  {
    return this.marginItemAssure4;
  }

  public void setMarginItemAssure4(Double marginItemAssure4)
  {
    this.marginItemAssure4 = marginItemAssure4;
  }

  public Double getMarginItemAssure5()
  {
    return this.marginItemAssure5;
  }

  public void setMarginItemAssure5(Double marginItemAssure5)
  {
    this.marginItemAssure5 = marginItemAssure5;
  }

  public Double getMarginItemAssure1_S()
  {
    return this.marginItemAssure1_S;
  }

  public void setMarginItemAssure1_S(Double marginItemAssure1S)
  {
    this.marginItemAssure1_S = marginItemAssure1S;
  }

  public Double getMarginItemAssure2_S()
  {
    return this.marginItemAssure2_S;
  }

  public void setMarginItemAssure2_S(Double marginItemAssure2S)
  {
    this.marginItemAssure2_S = marginItemAssure2S;
  }

  public Double getMarginItemAssure3_S()
  {
    return this.marginItemAssure3_S;
  }

  public void setMarginItemAssure3_S(Double marginItemAssure3S)
  {
    this.marginItemAssure3_S = marginItemAssure3S;
  }

  public Double getMarginItemAssure4_S()
  {
    return this.marginItemAssure4_S;
  }

  public void setMarginItemAssure4_S(Double marginItemAssure4S)
  {
    this.marginItemAssure4_S = marginItemAssure4S;
  }

  public Double getMarginItemAssure5_S()
  {
    return this.marginItemAssure5_S;
  }

  public void setMarginItemAssure5_S(Double marginItemAssure5S)
  {
    this.marginItemAssure5_S = marginItemAssure5S;
  }

  public Date getModifyTime()
  {
    return this.modifyTime;
  }

  public void setModifyTime(Date modifyTime)
  {
    this.modifyTime = modifyTime;
  }

  public Integer getType1()
  {
    return this.type1;
  }

  public void setType1(Integer type1) {
    this.type1 = type1;
  }

  public Integer getType2() {
    return this.type2;
  }

  public void setType2(Integer type2) {
    this.type2 = type2;
  }

  public Integer getType3() {
    return this.type3;
  }

  public void setType3(Integer type3) {
    this.type3 = type3;
  }

  public Integer getType4() {
    return this.type4;
  }

  public void setType4(Integer type4) {
    this.type4 = type4;
  }

  public Integer getType5() {
    return this.type5;
  }

  public void setType5(Integer type5) {
    this.type5 = type5;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return null;
  }
}