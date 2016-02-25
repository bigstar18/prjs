package gnnt.trade.bank.vo.bankdz.pf.sent;

import java.util.Date;

public class TradeList
{
  public double Trade_Money;
  public String Trade_Type;
  public String B_Member_Code;
  public String B_Member_Name;
  public String S_Member_Code;
  public String S_Member_Name;
  public Date Trade_Date;
  public String Bargain_Code;
  public String Serial_Id;
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
    sb.append("交易金额[Trade_Money](" + this.Trade_Money + ")" + str);
    sb.append("交易类型[Trade_Type](" + this.Trade_Type + ")" + str);
    sb.append("买方会员号[B_Member_Code](" + this.B_Member_Code + ")" + str);
    sb.append("买方会员名称[B_Member_Name](" + this.B_Member_Name + ")" + str);
    sb.append("卖方会员号[S_Member_Code](" + this.S_Member_Code + ")" + str);
    sb.append("卖方会员名称[S_Member_Name](" + this.S_Member_Name + ")" + str);
    sb.append("交易日期[Trade_Date](" + this.Trade_Date + ")" + str);
    sb.append("成交合同号[Bargain_Code](" + this.Bargain_Code + ")" + str);
    sb.append("成交流水号[Serial_Id](" + this.Serial_Id + ")" + str);
    sb.append("货物编号[Good_Code](" + this.Good_Code + ")" + str);
    sb.append("货物名称[Good_Name](" + this.Good_Name + ")" + str);
    sb.append("货物数量[Good_Quantity](" + this.Good_Quantity + ")" + str);
    sb.append("状态[flag](" + this.flag + ")" + str);
    sb.append("备注信息[note](" + this.note + ")" + str);
    return sb.toString();
  }
}
