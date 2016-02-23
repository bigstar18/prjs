package gnnt.trade.bank.vo;

import java.io.Serializable;

/** 前台展示交易商银行信息 */
public class FirmBankMsg implements Serializable{
	private static final long serialVersionUID = 1L;
	/**交易商代码*/
	public String firmID;
	/**签约帐号*/
	public String contact;
	/**银行账号*/
	public String account;
	/**银行账号名*/
	public String accountName;
	/**内部账号*/
	public String account1;
	/**在途资金*/
	public double frozenfuns;
	/**银行编号*/
	public String bankID;
	/**银行名称*/
	public String bankName;
	/**主银行编号*/
	public String mainBank;
	/**资金划转金额*/
	public double transferFund;
	/**可用资金*/
	public double funds;
	/**当前可出资金*/
	public double canOutMoney;
}
