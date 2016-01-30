package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;
import java.util.Vector;

public class BalaceErrorValue  implements Serializable {
	//分账户余额异常信息
	/**银行代码*/
	public String bankId;
	/**交易所代码*/
	public String exchangeCode;
	/**交易日期*/
	public String tradeDate;
	/**币别(001：人民币 013：港币 014：美元)*/
	public String ccyCode;
	/**钞汇标示(0钞 1汇)*/
	public String flag;
	/**台账账号*/
	public String ledgerAccount;
	/**证券资金账号*/
	public String fundsAccount;
	/**客户姓名*/
	public String firmName;
	/**台账金额*/
	public double ledgerMoney;
	/**证券资金账户金额*/
	public double fundsMoney;
	
	public Vector<BalaceErrorValue> bev;
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("银行代码bankId:"+this.bankId);
		sb.append("交易所代码exchangeCode:"+this.exchangeCode);
		sb.append("交易日期tradeDate:"+this.tradeDate);
		sb.append("币别ccyCode:"+this.ccyCode);
		sb.append("钞汇标示flag:"+this.flag);
		sb.append("台账账号ledgerAccount:"+this.ledgerAccount);
		sb.append("证券资金账号fundsAccount:"+this.fundsAccount);
		sb.append("客户姓名firmName:"+this.firmName);
		sb.append("台账金额ledgerMoney:"+this.ledgerMoney);
		sb.append("证券资金账户金额fundsMoney:"+this.fundsMoney);
		return sb.toString();
	}
	
	
}