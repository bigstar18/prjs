package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CorrespondValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long id;
  public String bankID;
  public String firmID;
  public String contact;
  public int status;
  public String account;
  public String account1;
  public String accountName;
  public String accountName1;
  public String bankName;
  public String bankProvince;
  public String bankCity;
  public String mobile;
  public String email;
  public int isOpen;
  public String accountProp = "0";
  public String cardType;
  public String card;
  public double frozenFuns;
  public Date opentime;
  public Date deltime;
  public Date modtime;
  public String bankCardPassword;
  public String name;
  public String firmType;
  public String mainBank;
  public String belevemember;
  public String OpenBankCode;
  public String Linkman;
  public String Phone;
  public String addr;
  public String LawName;
  public String NoteFlag;
  public String NotePhone;
  public String zipCode;
  public String checkFlag;
  public String signInfo;
  public String actionID;
  public int falg;
  public String inMarketCode;
  public String opeType;
  public String isCrossline;
  public String accountBankNum;
  public String notes;
  public String accountBank;
  public String type;
  public double amount;
  private Map<String, String> _cardTypeMap = new HashMap();
  
  public CorrespondValue()
  {
    init();
  }
  
  public CorrespondValue(String bankID, String firmID, String account)
  {
    this.bankID = bankID;
    this.firmID = firmID;
    this.account = account;
  }
  
  public String getCardType()
  {
    return getCardType(this.cardType);
  }
  
  public String getCardType(String cardType)
  {
    if ((cardType != null) && (cardType.trim().length() > 0) && 
      (this._cardTypeMap.get(cardType.trim()) != null)) {
      return (String)this._cardTypeMap.get(cardType.trim());
    }
    return "";
  }
  
  public String toString()
  {
    String sep = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("**" + getClass().getName() + "**" + sep);
    sb.append("bankID:" + this.bankID + sep);
    sb.append("firmID:" + this.firmID + sep);
    sb.append("contact:" + this.contact + sep);
    sb.append("status:" + this.status + sep);
    sb.append("account:" + this.account + sep);
    sb.append("account1:" + this.account1 + sep);
    sb.append("accountName:" + this.accountName + sep);
    sb.append("accountName1:" + this.accountName1 + sep);
    sb.append("bankName:" + this.bankName + sep);
    sb.append("bankProvince:" + this.bankProvince + sep);
    sb.append("bankCity:" + this.bankCity + sep);
    sb.append("mobile:" + this.mobile + sep);
    sb.append("Email:" + this.email + sep);
    sb.append("IsOpen:" + this.isOpen + sep);
    sb.append("CardType:" + this.cardType + sep);
    sb.append("Card:" + this.card + sep);
    sb.append("frozenFuns:" + this.frozenFuns + sep);
    sb.append("opentime:" + this.opentime + sep);
    sb.append("deltime:" + this.deltime + sep);
    sb.append("modtime:" + this.modtime + sep);
    sb.append("mainBank:" + this.mainBank + sep);
    sb.append("belevemember:" + this.belevemember + sep);
    sb.append("OpenBankCode:" + this.OpenBankCode + sep);
    sb.append("Linkman:" + this.Linkman + sep);
    sb.append("Phone:" + this.Phone + sep);
    sb.append("addr:" + this.addr + sep);
    sb.append("LawName:" + this.LawName + sep);
    sb.append("NoteFlag:" + this.NoteFlag + sep);
    sb.append("NotePhone:" + this.NotePhone + sep);
    sb.append("zipCode:" + this.zipCode + sep);
    sb.append("checkFlag:" + this.checkFlag + sep);
    sb.append("signInfo:" + this.signInfo + sep);
    sb.append("actionID:" + this.actionID + sep);
    sb.append("amount:" + this.amount + sep);
    
    sb.append(sep);
    return sb.toString();
  }
  
  private void init()
  {
    this._cardTypeMap.put("1", "身份证");
    this._cardTypeMap.put("2", "军官证");
    this._cardTypeMap.put("3", "国内护照");
    this._cardTypeMap.put("4", "户口本");
    this._cardTypeMap.put("5", "学员证");
    this._cardTypeMap.put("6", "退休证");
    this._cardTypeMap.put("7", "临时身份证");
    this._cardTypeMap.put("8", "机构代码");
    this._cardTypeMap.put("9", "营业执照");
    this._cardTypeMap.put("A", "警官证");
    this._cardTypeMap.put("B", "解放军士兵证");
    this._cardTypeMap.put("C", "回乡证");
    this._cardTypeMap.put("D", "外国护照");
    this._cardTypeMap.put("E", "港澳台居民身份证");
    this._cardTypeMap.put("F", "台湾通行证及其他有效旅行证");
    this._cardTypeMap.put("G", "海外客户编号");
    this._cardTypeMap.put("H", "解放军文职干部证");
    this._cardTypeMap.put("I", "武警文职干部证");
    this._cardTypeMap.put("J", "武警士兵证");
    this._cardTypeMap.put("X", "其他有效证件");
    this._cardTypeMap.put("Z", "重号身份证");
    this._cardTypeMap.put("a", "法人代码");
  }
}
