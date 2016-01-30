package gnnt.trade.bank.vo.bankdz.hx.sent;
import gnnt.trade.bank.vo.bankdz.hx.sent.child.DZMessage;
import gnnt.trade.bank.vo.bankdz.hx.sent.child.QSMessage;

import java.io.Serializable;
/**
 * 华夏银行清算对账信息类
 */
public class HXSentQSMsgValue implements Serializable{
	private static final long serialVersionUID=1L;
	/**交易商代码*/
	public String firmID;
	/**银行编号*/
	public String bankID;
	/**银行名称*/
	public String bankName;
	/**银行账号*/
	public String account;
	/**账户名*/
	public String accountName;
	/**交易商子账号*/
	public String account1;
	/**交易商子账户名*/
	public String accountName1;
	/**证件号码*/
	public String card;
	/**证件类型1 身份证,2军官证,3国内护照,4户口本,5学员证,6退休证,7临时身份证,8组织机构代码,9营业执照,A警官证,B解放军士兵证,C回乡证,D外国护照,E港澳台居民身份证,F台湾通行证及其他有效旅行证,G海外客户编号,H解放军文职干部证,I武警文职干部证,J武警士兵证,X其他有效证件,Z重号身份证)(主要用的是 1、8,有用 9 的 其它还没有用的)*/
	public String cardType;
	/**签约状态(1: 已签约 0：未签约)*/
	public int isopen;
	/**可用状态(0：可用 1：不可用)*/
	public int status;
	/**当日交收保证金*/
	public double rsm;
	/**当日远期保证金*/
	public double rm;
	/**现货竞价保证金*/
	public double margin;
	/**当日浮亏*/
	public double rfl;
	/**浮动盈亏*/
	public double fl;
	/**当前可用资金*/
	public double todaybalance;
	/**上日可用资金*/
	public double lastbalance;
	/**当天入金金额*/
	public double inmoney;
	/**当天出金*/
	public double outmoney;
	/**清算信息*/
	public QSMessage qs;
	/**对账信息*/
	public DZMessage dz;
	/**
	 * 本类信息
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("---"+this.getClass().getName()+"---"+str);
		sb.append("交易商代码[firmID]("+firmID+")"+str);
		sb.append("银行编号[bankID]("+bankID+")"+str);
		sb.append("银行名称[bankName]("+bankName+")"+str);
		sb.append("银行账号[account]("+account+")"+str);
		sb.append("账户名[accountName]("+accountName+")"+str);
		sb.append("交易商子账号[account1]("+account1+")"+str);
		sb.append("交易商子账户名[accountName1]("+accountName1+")"+str);
		sb.append("证件号码[card]("+card+")"+str);
		sb.append("证件类型[cardType]("+cardType+")"+str);
		sb.append("签约状态[isopen]("+isopen+")"+str);
		sb.append("用户状态[status]("+status+")"+str);
		sb.append("当日交收保证金[rsm]("+rsm+")"+str);
		sb.append("当日远期保证金[rm]("+rm+")"+str);
		sb.append("现货竞价保证金[margin]("+margin+")"+str);
		sb.append("当日浮亏[rfl]("+rfl+")"+str);
		sb.append("浮动盈亏[fl]("+fl+")"+str);
		sb.append("当前可用资金[todaybalance]("+todaybalance+")"+str);
		sb.append("上日可用资金[lastbalance]("+lastbalance+")"+str);
		sb.append("当天入金金额[inmoney]("+inmoney+")"+str);
		sb.append("当天出金[outmoney]("+outmoney+")"+str);
		sb.append((qs == null ? "清算类为null" : qs.toString())+str);
		sb.append((dz == null ? "对账类为null" : dz.toString())+str);
		return sb.toString();
	}

}
