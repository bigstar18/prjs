package gnnt.trade.bank.data.ceb.vo;

public class Chk01 {

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
	 * 交易时间
	 */
	public String transfeTime;
	/**
	 * 银行流水号
	 */
	public String bkSeq;
	/**
	 * 证券流水号
	 */
	public String ftSeq;
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
	 * 发起方
	 */
	public String tradSrc;
	/**
	 * 业务功能码
	 */
	public String busCd;
	/**
	 * 币种
	 */
	public String ccy;
	/**
	 * 汇钞标志
	 */
	public String cashExCd;
	/**
	 * 转账金额
	 */
	public long trfAmt;

	
	public String toString(){
		String sep = "\n";		
		StringBuffer sb = new StringBuffer();		
		sb.append("**"+this.getClass().getName()+"**"+sep);	
		sb.append("bankId:" + this.bankId + sep);
		sb.append("futuresId:" + this.futuresId + sep);
		sb.append("transferDate:" + this.transferDate + sep);
		sb.append("transfeTime:" + this.transfeTime + sep);
		sb.append("bkSeq:" + this.bkSeq + sep);
		sb.append("ftSeq:" + this.ftSeq + sep);
		sb.append("bkAcct:" + this.bkAcct + sep);
		sb.append("ftAcct:" + this.ftAcct + sep);
		sb.append("custName:" + this.custName + sep);
		sb.append("tradSrc:" + this.tradSrc + sep);
		sb.append("busCd:" + this.busCd + sep);
		sb.append("ccy:" + this.ccy + sep);
		sb.append("cashExCd:" + this.cashExCd + sep);
		sb.append("trfAmt:" + this.trfAmt + sep);
		return sb.toString();
	}
	
}
