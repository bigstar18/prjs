package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>Title: 资金流水对象类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: gnnt</p>
 *
 * @author zhangzhongli
 * @version 1.0
 */
public class CapitalValue implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * 记录流水号
		 */
		public long iD;
		/**
		 * 市场业务流水号
		 */
		public long actionID;
		/**
		 * 交易商代码
		 */
		public String firmID;
		/**
		 * 银行业务流水号
		 */
		public String funID;
		/**
		 * 银行代码
		 */
		public String bankID;
		/**
		 * 借方科目代码
		 */
		public String debitID;
		/**
		 * 贷方科目代码
		 */
		public String creditID;
		/**
		 * 类型：0 入金 1 出金2 出入金手续费 3 其他资金划转 4 入金冲正 5 出金冲正
		 */
		public int type;
		/**
		 * 金额
		 */
		public double money;
		/**
		 * 业务代码
		 */
		public String oprcode;
		/**
		 * 记录创建时间
		 */
		public Timestamp createtime;
		/**
		 * 银行端操作时间
		 */
		public Timestamp bankTime;
		/**
		 * 状态：0：成功 1：失败 2：处理中 3:待审核 4:二次审核 5:银行无应答 6:市场流水号冲突 -1:流水被冲正
		 */
		public int status;
		/**
		 * 是否加急：0：正常 1：加急
		 */
		public int express;
		/**
		 * 备注
		 */
		public String note;
		/**
		 * 银行名称
		 */
		public String bankName;
		/**
		 * 账户名
		 */
		public String firmName;
		/**
		 * 银行账号
		 */
		public String account;
		
		/**
		 * 返回对象属性值
		 */
		public String toString()
		{
			String sep = "\n";
			StringBuffer sb = new StringBuffer();
			sb.append("**"+this.getClass().getName()+"**"+sep);
			sb.append("ID:" + this.iD+sep);
			sb.append("actionID:" + this.actionID+sep);
			sb.append("firmID:" + this.firmID+sep);
			sb.append("funID:" + this.funID+sep);
			sb.append("bankID:" + this.bankID+sep);
			sb.append("debitID:" + this.debitID+sep);
			sb.append("creditID:" + this.creditID+sep);
			sb.append("type:" + this.type+sep);
			sb.append("money:" + this.money+sep);
			sb.append("operator:" + this.oprcode+sep);
			sb.append("createtime:" + this.createtime+sep);
			sb.append("bankTime:" + this.bankTime+sep);
			sb.append("status:" + this.status+sep);
			sb.append("express:" + this.express+sep);
			sb.append("note:" + this.note+sep);
			sb.append("bankName:"+this.bankName+sep);
			sb.append("firmName:"+this.firmName+sep);
			sb.append("account:"+this.account+sep);
			sb.append(sep);
			return sb.toString();
		}

}
