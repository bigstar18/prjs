package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirmUserValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String account;
  public String accountname;
  public int isopen;
  public String firmID;
  public String contact;
  public String firmName;
  public int status;
  public Date registerDate;
  public Date logoutDate;
  public String passWord;
  public String cardType;
  public String card;
  public String bankID;
  public String strkey;
  public String checkSignHX;
  public String belevemember;
  public String firmType;
  private Map<String, String> _cardTypeMap = new HashMap();
  
  public FirmUserValue()
  {
    init();
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
    sb.append("**" + super.getClass().getName() + "**" + sep);
    sb.append("account:" + this.account + sep);
    sb.append("isopen:" + this.isopen + sep);
    sb.append("accountname:" + this.accountname + sep);
    sb.append("firmID:" + this.firmID + sep);
    sb.append("contact:" + this.contact + sep);
    sb.append("firmName:" + this.firmName + sep);
    sb.append("status:" + this.status + sep);
    sb.append("registerDate:" + this.registerDate + sep);
    sb.append("logoutDate:" + this.logoutDate + sep);
    sb.append("passWord:" + this.passWord + sep);
    sb.append("CardType:" + this.cardType + sep);
    sb.append("Card:" + this.card + sep);
    sb.append("checkSignHX:" + this.checkSignHX + sep);
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
