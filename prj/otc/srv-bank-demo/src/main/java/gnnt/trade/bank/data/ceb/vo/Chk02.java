package gnnt.trade.bank.data.ceb.vo;
/**
 * 客户账户状态对账文件chk02
 * @author tanglt  2011-5-26
 */
public class Chk02 {
	/**
	 * 银行机构代码
	 */
	public String bankId;
	/**
	 * 券商机构代码
	 */
	public String futuresId;
	/**
	 * 证券机构分支号
	 */
	public String branchId;
	/**
	 * 交易日期
	 */
	public String transferDate;
	/**
	 * 客户银行结算账户账号
	 */
	public String bkAcct;
	/**
	 * 证券资金台账账号
	 */
	public String ftAcct;
	/**
	 * 客户名称
	 */
	public String custName;
	/**
	 * 存管状态
	 */
	public String state;
	
	
	public String toString(){
		String sep = "\n";		
		StringBuffer sb = new StringBuffer();		
		sb.append("**"+this.getClass().getName()+"**"+sep);	
		sb.append("bankId:" + this.bankId + sep);
		sb.append("futuresId:" + this.futuresId + sep);
		sb.append("branchId:" + this.branchId + sep);
		sb.append("transferDate:" + this.transferDate + sep);
		sb.append("bkAcct:" + this.bkAcct + sep);
		sb.append("ftAcct:" + this.ftAcct + sep);
		sb.append("custName:" + this.custName + sep);
		sb.append("state:" + this.state + sep);
		return sb.toString();
	}
}
