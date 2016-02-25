package gnnt.trade.bank.vo.bankdz.abc;

import java.io.Serializable;
import java.util.Date;

public class ABCQSValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String firmID;
  public String bankID;
  public String account;
  public String currency = "001";
  public int type = 0;
  public double Fee;
  public double crjSum;
  public double bankErrorMoney;
  public double firmErrorMoney;
  public double cash;
  public double availableBalance;
  public double frozen;
  public double balance;
  public double firmRights;
  public Date tradeDate;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("交易商代码(firmID)[" + this.firmID + "]" + str);
    sb.append("银行编号(bankID)[" + this.bankID + "]" + str);
    sb.append("交易商银行账号(account)[" + this.account + "]" + str);
    sb.append("币种(currency)[" + this.currency + "]" + str);
    sb.append("钞汇标识(type)[" + this.type + "]" + str);
    sb.append("交易商手续费(Fee)[" + this.Fee + "]" + str);
    sb.append("出入金合计(crjSum)[" + this.crjSum + "]" + str);
    sb.append("扎差(bankErrorMoney)[" + this.bankErrorMoney + "]" + str);
    sb.append("权益变化量(firmErrorMoney)[" + this.firmErrorMoney + "]" + str);
    sb.append("冻结变化量(cash)[" + this.cash + "]" + str);
    sb.append("可用变化量(availableBalance)[" + this.availableBalance + "]" + str);
    sb.append("交易商冻结(frozen)[" + this.frozen + "]" + str);
    sb.append("交易商可用(balance)[" + this.balance + "]" + str);
    sb.append("交易商可用(firmRights)[" + this.firmRights + "]" + str);
    sb.append("交易日期(tradeDate)[" + this.tradeDate + "]" + str);
    return sb.toString();
  }
  
  public String getMessage()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("===>");
    sb.append("交易商代码[" + this.firmID + "];");
    sb.append("银行编号[" + this.bankID + "];");
    sb.append("交易商银行账号[" + this.account + "];");
    sb.append("币种[" + this.currency + "];");
    sb.append("钞汇标识[" + this.type + "];");
    sb.append("交易商手续费[" + this.Fee + "];");
    sb.append("出入金合计[" + this.crjSum + "];");
    sb.append("扎差[" + this.bankErrorMoney + "];");
    sb.append("权益变化量[" + this.firmErrorMoney + "];");
    sb.append("冻结变化量[" + this.cash + "];");
    sb.append("可用变化量[" + this.availableBalance + "];");
    sb.append("交易商冻结[" + this.frozen + "];");
    sb.append("交易商可用[" + this.balance + "];");
    sb.append("交易商可用[" + this.firmRights + "];");
    sb.append("交易日期[" + this.tradeDate + "]");
    return sb.toString();
  }
}
