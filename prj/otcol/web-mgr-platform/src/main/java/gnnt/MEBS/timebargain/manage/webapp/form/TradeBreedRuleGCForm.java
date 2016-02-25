package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

public class TradeBreedRuleGCForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049813L;
  private String groupID;
  private String groupName;
  private String uni_Cmdty_Code;
  private String breedID;
  private String Name;
  private String FeeAlgr;
  private String FeeRate_B;
  private String FeeRate_S;
  private String MarginAlgr;
  private String MarginRate_B;
  private String MarginRate_S;
  private String MarginItem1;
  private String MarginItem2;
  private String MarginItem3;
  private String MarginItem4;
  private String MarginItem5;
  private String MarginItem1_S;
  private String MarginItem2_S;
  private String MarginItem3_S;
  private String MarginItem4_S;
  private String MarginItem5_S;
  private String MarginAssure_B;
  private String MarginAssure_S;
  private String MarginItemAssure1;
  private String MarginItemAssure2;
  private String MarginItemAssure3;
  private String MarginItemAssure4;
  private String MarginItemAssure5;
  private String MarginItemAssure1_S;
  private String MarginItemAssure2_S;
  private String MarginItemAssure3_S;
  private String MarginItemAssure4_S;
  private String MarginItemAssure5_S;
  private String crud;
  private String TodayCloseFeeRate_B;
  private String TodayCloseFeeRate_S;
  private String HistoryCloseFeeRate_B;
  private String HistoryCloseFeeRate_S;
  private String SettleFeeAlgr;
  private String SettleFeeRate_B;
  private String SettleFeeRate_S;
  private String ForceCloseFeeAlgr;
  private String ForceCloseFeeRate_B;
  private String ForceCloseFeeRate_S;
  private String SettleMarginAlgr;
  private String SettleMarginRate_B;
  private String SettleMarginRate_S;
  private String settleMarginAlgr_B;
  private String settleMarginAlgr_S;
  private String payoutAlgr;
  private String payoutRate;
  private String firmID;
  private String maxHoldQty;
  private String cleanMaxHoldQty;
  
  public void reset(ActionMapping paramActionMapping, HttpServletRequest paramHttpServletRequest) {}
  
  public String getFirmID()
  {
    return this.firmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.firmID = paramString;
  }
  
  public String getSettleMarginAlgr()
  {
    return this.SettleMarginAlgr;
  }
  
  public void setSettleMarginAlgr(String paramString)
  {
    this.SettleMarginAlgr = paramString;
  }
  
  public String getSettleMarginRate_B()
  {
    return this.SettleMarginRate_B;
  }
  
  public void setSettleMarginRate_B(String paramString)
  {
    this.SettleMarginRate_B = paramString;
  }
  
  public String getSettleMarginRate_S()
  {
    return this.SettleMarginRate_S;
  }
  
  public void setSettleMarginRate_S(String paramString)
  {
    this.SettleMarginRate_S = paramString;
  }
  
  public String getForceCloseFeeAlgr()
  {
    return this.ForceCloseFeeAlgr;
  }
  
  public void setForceCloseFeeAlgr(String paramString)
  {
    this.ForceCloseFeeAlgr = paramString;
  }
  
  public String getForceCloseFeeRate_B()
  {
    return this.ForceCloseFeeRate_B;
  }
  
  public void setForceCloseFeeRate_B(String paramString)
  {
    this.ForceCloseFeeRate_B = paramString;
  }
  
  public String getForceCloseFeeRate_S()
  {
    return this.ForceCloseFeeRate_S;
  }
  
  public void setForceCloseFeeRate_S(String paramString)
  {
    this.ForceCloseFeeRate_S = paramString;
  }
  
  public String getSettleFeeAlgr()
  {
    return this.SettleFeeAlgr;
  }
  
  public void setSettleFeeAlgr(String paramString)
  {
    this.SettleFeeAlgr = paramString;
  }
  
  public String getSettleFeeRate_B()
  {
    return this.SettleFeeRate_B;
  }
  
  public void setSettleFeeRate_B(String paramString)
  {
    this.SettleFeeRate_B = paramString;
  }
  
  public String getSettleFeeRate_S()
  {
    return this.SettleFeeRate_S;
  }
  
  public void setSettleFeeRate_S(String paramString)
  {
    this.SettleFeeRate_S = paramString;
  }
  
  public String getHistoryCloseFeeRate_B()
  {
    return this.HistoryCloseFeeRate_B;
  }
  
  public void setHistoryCloseFeeRate_B(String paramString)
  {
    this.HistoryCloseFeeRate_B = paramString;
  }
  
  public String getHistoryCloseFeeRate_S()
  {
    return this.HistoryCloseFeeRate_S;
  }
  
  public void setHistoryCloseFeeRate_S(String paramString)
  {
    this.HistoryCloseFeeRate_S = paramString;
  }
  
  public String getTodayCloseFeeRate_B()
  {
    return this.TodayCloseFeeRate_B;
  }
  
  public void setTodayCloseFeeRate_B(String paramString)
  {
    this.TodayCloseFeeRate_B = paramString;
  }
  
  public String getTodayCloseFeeRate_S()
  {
    return this.TodayCloseFeeRate_S;
  }
  
  public void setTodayCloseFeeRate_S(String paramString)
  {
    this.TodayCloseFeeRate_S = paramString;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
  
  public String getGroupID()
  {
    return this.groupID;
  }
  
  public void setGroupID(String paramString)
  {
    this.groupID = paramString;
  }
  
  public String getFeeAlgr()
  {
    return this.FeeAlgr;
  }
  
  public void setFeeAlgr(String paramString)
  {
    this.FeeAlgr = paramString;
  }
  
  public String getFeeRate_B()
  {
    return this.FeeRate_B;
  }
  
  public void setFeeRate_B(String paramString)
  {
    this.FeeRate_B = paramString;
  }
  
  public String getFeeRate_S()
  {
    return this.FeeRate_S;
  }
  
  public void setFeeRate_S(String paramString)
  {
    this.FeeRate_S = paramString;
  }
  
  public String getMarginAlgr()
  {
    return this.MarginAlgr;
  }
  
  public void setMarginAlgr(String paramString)
  {
    this.MarginAlgr = paramString;
  }
  
  public String getMarginAssure_B()
  {
    return this.MarginAssure_B;
  }
  
  public void setMarginAssure_B(String paramString)
  {
    this.MarginAssure_B = paramString;
  }
  
  public String getMarginAssure_S()
  {
    return this.MarginAssure_S;
  }
  
  public void setMarginAssure_S(String paramString)
  {
    this.MarginAssure_S = paramString;
  }
  
  public String getMarginItem1()
  {
    return this.MarginItem1;
  }
  
  public void setMarginItem1(String paramString)
  {
    this.MarginItem1 = paramString;
  }
  
  public String getMarginItem1_S()
  {
    return this.MarginItem1_S;
  }
  
  public void setMarginItem1_S(String paramString)
  {
    this.MarginItem1_S = paramString;
  }
  
  public String getMarginItem2()
  {
    return this.MarginItem2;
  }
  
  public void setMarginItem2(String paramString)
  {
    this.MarginItem2 = paramString;
  }
  
  public String getMarginItem2_S()
  {
    return this.MarginItem2_S;
  }
  
  public void setMarginItem2_S(String paramString)
  {
    this.MarginItem2_S = paramString;
  }
  
  public String getMarginItem3()
  {
    return this.MarginItem3;
  }
  
  public void setMarginItem3(String paramString)
  {
    this.MarginItem3 = paramString;
  }
  
  public String getMarginItem3_S()
  {
    return this.MarginItem3_S;
  }
  
  public void setMarginItem3_S(String paramString)
  {
    this.MarginItem3_S = paramString;
  }
  
  public String getMarginItem4()
  {
    return this.MarginItem4;
  }
  
  public void setMarginItem4(String paramString)
  {
    this.MarginItem4 = paramString;
  }
  
  public String getMarginItem4_S()
  {
    return this.MarginItem4_S;
  }
  
  public void setMarginItem4_S(String paramString)
  {
    this.MarginItem4_S = paramString;
  }
  
  public String getMarginItemAssure1()
  {
    return this.MarginItemAssure1;
  }
  
  public void setMarginItemAssure1(String paramString)
  {
    this.MarginItemAssure1 = paramString;
  }
  
  public String getMarginItemAssure1_S()
  {
    return this.MarginItemAssure1_S;
  }
  
  public void setMarginItemAssure1_S(String paramString)
  {
    this.MarginItemAssure1_S = paramString;
  }
  
  public String getMarginItemAssure2()
  {
    return this.MarginItemAssure2;
  }
  
  public void setMarginItemAssure2(String paramString)
  {
    this.MarginItemAssure2 = paramString;
  }
  
  public String getMarginItemAssure2_S()
  {
    return this.MarginItemAssure2_S;
  }
  
  public void setMarginItemAssure2_S(String paramString)
  {
    this.MarginItemAssure2_S = paramString;
  }
  
  public String getMarginItemAssure3()
  {
    return this.MarginItemAssure3;
  }
  
  public void setMarginItemAssure3(String paramString)
  {
    this.MarginItemAssure3 = paramString;
  }
  
  public String getMarginItemAssure3_S()
  {
    return this.MarginItemAssure3_S;
  }
  
  public void setMarginItemAssure3_S(String paramString)
  {
    this.MarginItemAssure3_S = paramString;
  }
  
  public String getMarginItemAssure4()
  {
    return this.MarginItemAssure4;
  }
  
  public void setMarginItemAssure4(String paramString)
  {
    this.MarginItemAssure4 = paramString;
  }
  
  public String getMarginItemAssure4_S()
  {
    return this.MarginItemAssure4_S;
  }
  
  public void setMarginItemAssure4_S(String paramString)
  {
    this.MarginItemAssure4_S = paramString;
  }
  
  public String getMarginRate_B()
  {
    return this.MarginRate_B;
  }
  
  public void setMarginRate_B(String paramString)
  {
    this.MarginRate_B = paramString;
  }
  
  public String getMarginRate_S()
  {
    return this.MarginRate_S;
  }
  
  public void setMarginRate_S(String paramString)
  {
    this.MarginRate_S = paramString;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.uni_Cmdty_Code = paramString;
  }
  
  public String getGroupName()
  {
    return this.groupName;
  }
  
  public void setGroupName(String paramString)
  {
    this.groupName = paramString;
  }
  
  public String getName()
  {
    return this.Name;
  }
  
  public void setName(String paramString)
  {
    this.Name = paramString;
  }
  
  public String getSettleMarginAlgr_B()
  {
    return this.settleMarginAlgr_B;
  }
  
  public void setSettleMarginAlgr_B(String paramString)
  {
    this.settleMarginAlgr_B = paramString;
  }
  
  public String getSettleMarginAlgr_S()
  {
    return this.settleMarginAlgr_S;
  }
  
  public void setSettleMarginAlgr_S(String paramString)
  {
    this.settleMarginAlgr_S = paramString;
  }
  
  public String getBreedID()
  {
    return this.breedID;
  }
  
  public void setBreedID(String paramString)
  {
    this.breedID = paramString;
  }
  
  public String getPayoutAlgr()
  {
    return this.payoutAlgr;
  }
  
  public void setPayoutAlgr(String paramString)
  {
    this.payoutAlgr = paramString;
  }
  
  public String getPayoutRate()
  {
    return this.payoutRate;
  }
  
  public void setPayoutRate(String paramString)
  {
    this.payoutRate = paramString;
  }
  
  public String getCleanMaxHoldQty()
  {
    return this.cleanMaxHoldQty;
  }
  
  public void setCleanMaxHoldQty(String paramString)
  {
    this.cleanMaxHoldQty = paramString;
  }
  
  public String getMaxHoldQty()
  {
    return this.maxHoldQty;
  }
  
  public void setMaxHoldQty(String paramString)
  {
    this.maxHoldQty = paramString;
  }
  
  public String getMarginItem5()
  {
    return this.MarginItem5;
  }
  
  public void setMarginItem5(String paramString)
  {
    this.MarginItem5 = paramString;
  }
  
  public String getMarginItem5_S()
  {
    return this.MarginItem5_S;
  }
  
  public void setMarginItem5_S(String paramString)
  {
    this.MarginItem5_S = paramString;
  }
  
  public String getMarginItemAssure5()
  {
    return this.MarginItemAssure5;
  }
  
  public void setMarginItemAssure5(String paramString)
  {
    this.MarginItemAssure5 = paramString;
  }
  
  public String getMarginItemAssure5_S()
  {
    return this.MarginItemAssure5_S;
  }
  
  public void setMarginItemAssure5_S(String paramString)
  {
    this.MarginItemAssure5_S = paramString;
  }
}
