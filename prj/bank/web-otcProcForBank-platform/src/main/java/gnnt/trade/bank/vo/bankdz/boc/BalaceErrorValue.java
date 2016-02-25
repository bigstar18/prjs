package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;
import java.util.Vector;

public class BalaceErrorValue
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bankId;
  public String exchangeCode;
  public String tradeDate;
  public String ccyCode;
  public String flag;
  public String ledgerAccount;
  public String fundsAccount;
  public String firmName;
  public double ledgerMoney;
  public double fundsMoney;
  public Vector<BalaceErrorValue> bev;
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("---" + getClass().getName() + "---" + str);
    sb.append("银行代码bankId:" + this.bankId);
    sb.append("交易所代码exchangeCode:" + this.exchangeCode);
    sb.append("交易日期tradeDate:" + this.tradeDate);
    sb.append("币别ccyCode:" + this.ccyCode);
    sb.append("钞汇标示flag:" + this.flag);
    sb.append("台账账号ledgerAccount:" + this.ledgerAccount);
    sb.append("证券资金账号fundsAccount:" + this.fundsAccount);
    sb.append("客户姓名firmName:" + this.firmName);
    sb.append("台账金额ledgerMoney:" + this.ledgerMoney);
    sb.append("证券资金账户金额fundsMoney:" + this.fundsMoney);
    return sb.toString();
  }
}
