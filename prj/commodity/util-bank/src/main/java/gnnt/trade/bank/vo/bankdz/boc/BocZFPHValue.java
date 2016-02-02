package gnnt.trade.bank.vo.bankdz.boc;

import java.io.Serializable;

public class BocZFPHValue   implements Serializable{
	//中行总分平衡监管信息对象
	/**银行代码*/
	public String bankID;
	/**交易所代码*/
	public String exchangeCode;
	/**交易日期*/
	public String tradeDate;
	/**币别(001：人民币 013：港币 014：美元)*/
	public String ccyCode;
	/**钞汇标示(0钞 1汇)*/
	public String flag;
	/**台账汇总金额*/
	public double ledgerMoney;
	/**客户交易结算资金汇总账户金额*/
	public double accountMoney;
	/**预留备付金额 */
	public double backupMoney;
	/**买卖差标志（0：净卖差，1：净买差）*/
	public String businessFlag;
	/**代收金额*/
	public double deliveryMoney;
	
	
	public String tosString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("银行代码bankID:"+bankID);
		sb.append("交易所代码exchangeCode:"+exchangeCode);
		sb.append("交易日期tradeDate:"+tradeDate);
		sb.append("币别ccyCode:"+ccyCode);
		sb.append("钞汇标示flag："+flag);
		sb.append("台账汇总金额ledgerMoney："+ledgerMoney);
		sb.append("客户交易结算资金汇总账户金额accountMoney："+accountMoney);
		sb.append("预留备付金额："+backupMoney);
		sb.append("买卖差标志businessFlag："+businessFlag);
		sb.append("代收金额deliveryMoney："+deliveryMoney);
		return sb.toString();
	}
	
}
