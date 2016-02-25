package gnnt.MEBS.vendue.server.vo;

import java.sql.Timestamp;

public class MoneyVO
{
  public long iD;
  public Timestamp infoDate;
  public String firmID;
  public int operation;
  public long contractNo;
  public double money;
  public double balance;
  public double paymentForGoods;
  public double overdraft;
  public double frozenCapital;
  public int type;
  public String note;
  
  public String toString()
  {
    String str = "\n";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("**" + getClass().getName() + "**" + str);
    localStringBuffer.append("ID:" + this.iD + str);
    localStringBuffer.append("InfoDate:" + this.infoDate + str);
    localStringBuffer.append("FirmID:" + this.firmID + str);
    localStringBuffer.append("Operation:" + this.operation + str);
    localStringBuffer.append("ContractNo:" + this.contractNo + str);
    localStringBuffer.append("Money:" + this.money + str);
    localStringBuffer.append("Balance:" + this.balance + str);
    localStringBuffer.append("Overdraft:" + this.overdraft + str);
    localStringBuffer.append("FrozenCaptial:" + this.frozenCapital + str);
    localStringBuffer.append("type:" + this.type + str);
    localStringBuffer.append("note:" + this.note + str);
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}
