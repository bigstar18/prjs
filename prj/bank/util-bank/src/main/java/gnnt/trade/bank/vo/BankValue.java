package gnnt.trade.bank.vo;

import java.io.Serializable;


/**
 * <p>Title: 银行对象类</p>
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
public class BankValue implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * 银行代码
		 */
		public String bankID;
		/**
		 * 银行名称
		 */
		public String bankName;
		/**
		 * 默认出金审核金额
		 */
		public double maxAuditMoney;
		/**
		 * 默认每日最大转账金额
		 */
		public double maxPerTransMoney;
		/**
		 * 默认单笔最大转账金额
		 */
		public double maxPerSglTransMoney;
		/**
		 * 默认每日最大转账次数
		 */
		public int maxPerTransCount;
		
		/**
		 * 适配器实现类名称
		 */
		public String adapterClassname;
		/**
		 * 是否可用状态：0：可用1：不可用
		 */
		public int validFlag;
		/**银行转账开始时间*/
		public String beginTime;
		/**
		 * 银行转账结束时间
		 */
		public String endTime;
		/**
		 * 转账交易是否收到约束
		 * 0 受到双重约束，1不受约束，2受交易日约束，3受交易时间约束
		 */
		public int control;

		
		/**
		 * 返回对象属性值
		 */
		public String toString()
		{
			String sep = "\n";
			StringBuffer sb = new StringBuffer();
			sb.append("**"+this.getClass().getName()+"**"+sep);
			sb.append("bankID:" + this.bankID+sep);
			sb.append("bankName:" + this.bankName+sep);
			sb.append("maxAuditMoney:" + this.maxAuditMoney+sep);
			sb.append("maxPerTransMoney:" + this.maxPerTransMoney+sep);
			sb.append("maxPerTransCount:" + this.maxPerTransCount+sep);
			sb.append("adapterClassname:" + this.adapterClassname+sep);
			sb.append("validFlag:" + this.validFlag+sep);
			sb.append("beginTime:" + this.beginTime+sep);
			sb.append("endTime:" + this.endTime+sep);
			sb.append("control:" + this.control+sep);
			sb.append(sep);
			return sb.toString();
		}

}
