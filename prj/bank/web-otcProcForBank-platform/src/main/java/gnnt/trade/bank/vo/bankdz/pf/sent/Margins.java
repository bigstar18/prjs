package gnnt.trade.bank.vo.bankdz.pf.sent;

import java.util.Date;

public class Margins
{
  public String Serial_Id;
  public String Bargain_Code;
  public String Trade_Type;
  public double Trade_Money;
  public String Member_Code;
  public String Member_Name;
  public Date Trade_Date;
  public String Good_Code;
  public String Good_Name;
  public int Good_Quantity;
  public String flag;
  public String note;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("业务流水号[Serial_Id](" + this.Serial_Id + ")" + str);
    sb.append("成交合同号[Bargain_Code](" + this.Bargain_Code + ")" + str);
    sb.append("交易类型[Trade_Type](" + this.Trade_Type + ")" + str);
    sb.append("交易金额[Trade_Money](" + this.Trade_Money + ")" + str);
    sb.append("交易会员号[Member_Code](" + this.Member_Code + ")" + str);
    sb.append("交易会员名称[Member_Name](" + this.Member_Name + ")" + str);
    sb.append("交易日期[Trade_Date](" + this.Trade_Date + ")" + str);
    sb.append("货物编号[Good_Code](" + this.Good_Code + ")" + str);
    sb.append("货物名称[Good_Name](" + this.Good_Name + ")" + str);
    sb.append("货物数量[Good_Quantity](" + this.Good_Quantity + ")" + str);
    sb.append("状态[flag](" + this.flag + ")" + str);
    sb.append("备注信息[note](" + this.note + ")" + str);
    return sb.toString();
  }
}
