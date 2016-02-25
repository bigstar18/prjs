package gnnt.trade.bank.vo.bankdz.gs.sent;

import java.io.Serializable;

public class OpenOrDelFirmValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long maketNum;
  public String firmId;
  public String firmName;
  public String openordel;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer sb = new StringBuffer();
    sb.append("maketNum(业务流水号)[" + this.maketNum + "]" + str);
    sb.append("firmId(交易商代码)[" + this.firmId + "]" + str);
    sb.append("firmName(客户姓名)[" + this.firmName + "]" + str);
    sb.append("openordel(开户或销户)[" + this.openordel + "]" + str);
    sb.append(str);
    return sb.toString();
  }
  
  public long getMaketNum()
  {
    return this.maketNum;
  }
  
  public void setMaketNum(long maketNum)
  {
    this.maketNum = maketNum;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  public String getOpenordel()
  {
    return this.openordel;
  }
  
  public void setOpenordel(String openordel)
  {
    this.openordel = openordel;
  }
}
