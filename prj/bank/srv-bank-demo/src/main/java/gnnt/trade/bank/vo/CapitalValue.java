package gnnt.trade.bank.vo;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * <p>Title: 资金流水对象类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: gnnt</p>
 * @author zhangzhongli
 * @version 1.0
 */
public class CapitalValue implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 记录流水号 */
	public long iD;
	/** 操作员 */
	public String trader;
	/** 银行业务流水号 */
	public String funID;
	/** 市场业务流水号 */
	public long actionID;
	/** 交易账号代码 */
	public String firmID;
	/** 交易账号和银行绑定号 */
	public String contact;
	/** 银行代码 */
	public String bankID;
	/** 类型：0 入金 1 出金 4 入金冲正 5 出金冲正 */
	public int type;
	/** 发起方 0 市场 1 银行 */
	public int launcher;
	/** 金额 */
	public double money;
	/** 状态：0：成功 1：失败 2：处理中 5:银行无应答 6:市场流水号冲突 7:市场处理中 */
	public int status;
	/** 银行端操作时间 */
	public Timestamp bankTime;
	/** 记录创建时间 */
	public Timestamp createtime;
	/** 备注 */
	public String note;
	/** 银行流水号副本 */
	public String funID2;
	/** 记录日期 */
	public String createdate;
	/** 老的银行流水号 */
	public String oldfunID;
	/** 老的记录状态 */
	public int oldstatus=-1;


	/**银行名称*/
	public String bankName;
	/**银行账号*/
	public String account;
	/**银行账户名*/
	public String accountName;
	/**证件类型*/
	public String firmType;
	/**
	 * 返回对象属性值
	 */
	public String toString() {
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("iD:" + this.iD+sep);
		sb.append("trader:" + this.trader+sep);
		sb.append("funID:" + this.funID+sep);
		sb.append("actionID:" + this.actionID+sep);
		sb.append("firmID:" + this.firmID+sep);
		sb.append("contact:" + contact + sep);
		sb.append("bankID:" + this.bankID+sep);
//		sb.append("debitID:" + this.debitID+sep);
//		sb.append("creditID:" + this.creditID+sep);
		sb.append("type:" + this.type+sep);
		sb.append("money:" + this.money+sep);
//		sb.append("operator:" + this.operator+sep);
		sb.append("status:" + this.status+sep);
		sb.append("bankTime:" + this.bankTime+sep);
		sb.append("createtime:" + this.createtime+sep);
		sb.append("note:" + this.note+sep);
		sb.append("funID2:"+this.funID2+sep);
		sb.append("createdate:"+this.createdate+sep);
		
		sb.append("bankName:"+bankName+sep);
		sb.append("account:"+account+sep);
		sb.append("firmType:"+firmType+sep);
		sb.append(sep);
		return sb.toString();
	}

}
