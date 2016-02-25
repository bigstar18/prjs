package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class BocZFPHValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankID;
  public String exchangeCode;
  public String tradeDate;
  public String ccyCode;
  public String flag;
  public double ledgerMoney;
  public double accountMoney;
  public double backupMoney;
  public String businessFlag;
  public double deliveryMoney;
  
  public String tosString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("银行代码bankID:" + this.bankID);
    sb.append("交易所代码exchangeCode:" + this.exchangeCode);
    sb.append("交易日期tradeDate:" + this.tradeDate);
    sb.append("币别ccyCode:" + this.ccyCode);
    sb.append("钞汇标示flag：" + this.flag);
    sb.append("台账汇总金额ledgerMoney：" + this.ledgerMoney);
    sb.append("客户交易结算资金汇总账户金额accountMoney：" + this.accountMoney);
    sb.append("预留备付金额：" + this.backupMoney);
    sb.append("买卖差标志businessFlag：" + this.businessFlag);
    sb.append("代收金额deliveryMoney：" + this.deliveryMoney);
    return sb.toString();
  }
}
