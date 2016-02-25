package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;

public class CustomerForm
  extends BaseForm
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049814L;
  private String CustomerID;
  private String GroupID;
  private String Password;
  private String ConfirmPassword;
  private String CustomerName;
  private String Phone;
  private String Address;
  private String Status;
  private String Note;
  private String CreateTime;
  private String ModifyTime;
  private String marketCode;
  private String[] selectedOptionsMarket;
  private String FirmID;
  private String customerCounts;
  private String FirmName;
  private String Code;
  private String tcounts;
  private String TraderID;
  private String startCode;
  private String endCode;
  private String ApplyID;
  private String VirtualFunds;
  private String Creator;
  private String Remark1;
  private String Modifier;
  private String Remark2;
  private String type;
  private String ValidDate;
  private String id;
  private String typeID;
  private String uni_Cmdty_Code;
  private String commodityID;
  private String privilegeCode_B;
  private String privilegeCode_S;
  private String typeprivilege;
  private String kind;
  private String kindID;
  private String OldPassword;
  private String crud = "";
  
  public String getKind()
  {
    return this.kind;
  }
  
  public void setKind(String paramString)
  {
    this.kind = paramString;
  }
  
  public String getKindID()
  {
    return this.kindID;
  }
  
  public void setKindID(String paramString)
  {
    this.kindID = paramString;
  }
  
  public String getTypeprivilege()
  {
    return this.typeprivilege;
  }
  
  public void setTypeprivilege(String paramString)
  {
    this.typeprivilege = paramString;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public String getEndCode()
  {
    return this.endCode;
  }
  
  public void setEndCode(String paramString)
  {
    this.endCode = paramString;
  }
  
  public String getStartCode()
  {
    return this.startCode;
  }
  
  public void setStartCode(String paramString)
  {
    this.startCode = paramString;
  }
  
  public String getTcounts()
  {
    return this.tcounts;
  }
  
  public void setTcounts(String paramString)
  {
    this.tcounts = paramString;
  }
  
  public String getTraderID()
  {
    return this.TraderID;
  }
  
  public void setTraderID(String paramString)
  {
    this.TraderID = paramString;
  }
  
  public String getCode()
  {
    return this.Code;
  }
  
  public void setCode(String paramString)
  {
    this.Code = paramString;
  }
  
  public String getFirmName()
  {
    return this.FirmName;
  }
  
  public void setFirmName(String paramString)
  {
    this.FirmName = paramString;
  }
  
  public String getCustomerCounts()
  {
    return this.customerCounts;
  }
  
  public void setCustomerCounts(String paramString)
  {
    this.customerCounts = paramString;
  }
  
  public String getFirmID()
  {
    return this.FirmID;
  }
  
  public void setFirmID(String paramString)
  {
    this.FirmID = paramString;
  }
  
  public String getAddress()
  {
    return this.Address;
  }
  
  public void setAddress(String paramString)
  {
    this.Address = paramString;
  }
  
  public String getCreateTime()
  {
    return this.CreateTime;
  }
  
  public void setCreateTime(String paramString)
  {
    this.CreateTime = paramString;
  }
  
  public String getCrud()
  {
    return this.crud;
  }
  
  public void setCrud(String paramString)
  {
    this.crud = paramString;
  }
  
  public String getCustomerID()
  {
    return this.CustomerID;
  }
  
  public void setCustomerID(String paramString)
  {
    this.CustomerID = paramString;
  }
  
  public String getCustomerName()
  {
    return this.CustomerName;
  }
  
  public void setCustomerName(String paramString)
  {
    this.CustomerName = paramString;
  }
  
  public String getGroupID()
  {
    return this.GroupID;
  }
  
  public void setGroupID(String paramString)
  {
    this.GroupID = paramString;
  }
  
  public String getModifyTime()
  {
    return this.ModifyTime;
  }
  
  public void setModifyTime(String paramString)
  {
    this.ModifyTime = paramString;
  }
  
  public String getNote()
  {
    return this.Note;
  }
  
  public void setNote(String paramString)
  {
    this.Note = paramString;
  }
  
  public String getPassword()
  {
    return this.Password;
  }
  
  public void setPassword(String paramString)
  {
    this.Password = paramString;
  }
  
  public String getPhone()
  {
    return this.Phone;
  }
  
  public void setPhone(String paramString)
  {
    this.Phone = paramString;
  }
  
  public String getStatus()
  {
    return this.Status;
  }
  
  public void setStatus(String paramString)
  {
    this.Status = paramString;
  }
  
  public String getMarketCode()
  {
    return this.marketCode;
  }
  
  public void setMarketCode(String paramString)
  {
    this.marketCode = paramString;
  }
  
  public String[] getSelectedOptionsMarket()
  {
    return this.selectedOptionsMarket;
  }
  
  public void setSelectedOptionsMarket(String[] paramArrayOfString)
  {
    this.selectedOptionsMarket = paramArrayOfString;
  }
  
  public String getOldPassword()
  {
    return this.OldPassword;
  }
  
  public void setOldPassword(String paramString)
  {
    this.OldPassword = paramString;
  }
  
  public String getConfirmPassword()
  {
    return this.ConfirmPassword;
  }
  
  public void setConfirmPassword(String paramString)
  {
    this.ConfirmPassword = paramString;
  }
  
  public String getApplyID()
  {
    return this.ApplyID;
  }
  
  public void setApplyID(String paramString)
  {
    this.ApplyID = paramString;
  }
  
  public String getCreator()
  {
    return this.Creator;
  }
  
  public void setCreator(String paramString)
  {
    this.Creator = paramString;
  }
  
  public String getModifier()
  {
    return this.Modifier;
  }
  
  public void setModifier(String paramString)
  {
    this.Modifier = paramString;
  }
  
  public String getRemark1()
  {
    return this.Remark1;
  }
  
  public void setRemark1(String paramString)
  {
    this.Remark1 = paramString;
  }
  
  public String getRemark2()
  {
    return this.Remark2;
  }
  
  public void setRemark2(String paramString)
  {
    this.Remark2 = paramString;
  }
  
  public String getVirtualFunds()
  {
    return this.VirtualFunds;
  }
  
  public void setVirtualFunds(String paramString)
  {
    this.VirtualFunds = paramString;
  }
  
  public String getValidDate()
  {
    return this.ValidDate;
  }
  
  public void setValidDate(String paramString)
  {
    this.ValidDate = paramString;
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String paramString)
  {
    this.commodityID = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getPrivilegeCode_B()
  {
    return this.privilegeCode_B;
  }
  
  public void setPrivilegeCode_B(String paramString)
  {
    this.privilegeCode_B = paramString;
  }
  
  public String getPrivilegeCode_S()
  {
    return this.privilegeCode_S;
  }
  
  public void setPrivilegeCode_S(String paramString)
  {
    this.privilegeCode_S = paramString;
  }
  
  public String getTypeID()
  {
    return this.typeID;
  }
  
  public void setTypeID(String paramString)
  {
    this.typeID = paramString;
  }
  
  public String getUni_Cmdty_Code()
  {
    return this.uni_Cmdty_Code;
  }
  
  public void setUni_Cmdty_Code(String paramString)
  {
    this.uni_Cmdty_Code = paramString;
  }
}
