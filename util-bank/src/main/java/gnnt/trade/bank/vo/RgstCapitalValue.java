package gnnt.trade.bank.vo;

import java.io.Serializable;

/**
 * 交易商签解约流水对象
 * @author zjj
 *
 */
public class RgstCapitalValue implements Serializable
{
	private static final long serialVersionUID = 1L;

	/** 记录流水号 */
	public long iD;
	/** 市场业务流水号 */
	public long actionID;
	/**
	 * 银行代码
	 */
	public String bankID;
	/**
	 * 交易商代码
	 */
	public String firmID;
	/**
	 * 银行帐号(正式账号)
	 */
	public String account;
	/**
	 * 银行帐号(内部账号)
	 */
	public String account1;
	/**
	 * 账户名
	 */
	public String accountName;
	/**
	 * 状态：0：成功 -1：失败 1：处理中
	 */
	public int status;
	/**
	 * 状态：1：签约 2：解约 
	 */
	public int type;
	/**
	 * 创建时间
	 */
	public java.sql.Timestamp createTime;
	/**
	 * 完成时间
	 */
	public java.sql.Timestamp compleTime;

	/** 
	 * 客户证件类型 1身份证,
	 * 2军官证,3国内护照,4户口本,5学员证,6退休证,
	 * 7临时身份证,8组织机构代码,9营业执照,
	 * A警官证,B解放军士兵证,C回乡证,D外国护照, 
	 * E港澳台居民身份证,F台湾通行证及其他有效旅行证,
	 * G海外客户编号,H解放军文职干部证,I武警文职干部证,
	 * J武警士兵证,X其他有效证件,Z重号身份证
	 */
	public String cardType ;
	/**
	 * 证件号
	 */
	public String card;
	
	/**
	 * 备注
	 */
	public String note;
	
	public String toString()
	{
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**" + this.getClass().getName() + "**" + sep);
		sb.append("actionID:" + this.actionID + sep);
		sb.append("bankID:" + this.bankID + sep);
		sb.append("firmID:" + this.firmID + sep);
		sb.append("account:" + this.account + sep);
		sb.append("account1:" + this.account1 + sep);
		sb.append("accountName:" + this.accountName + sep);
		sb.append("status:" + this.status + sep);
		sb.append("createTime:" + this.createTime + sep);
		sb.append("compleTime:" + this.compleTime + sep);
		sb.append("cardType:" + this.cardType + sep);
		sb.append("card:" + this.card + sep);
		return sb.toString();
	}
}
