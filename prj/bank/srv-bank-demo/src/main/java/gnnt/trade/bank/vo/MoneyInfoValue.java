package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.*;

/**
 * <p>Title: 银行对账信息对象类</p>
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
public class MoneyInfoValue  implements Serializable 
{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * 银行流水号
		 */
		public String id;
		/**
		 * 市场流水号
		 */
		public long m_Id;
		/**
		 * 交易账号代码
		 */
		public String firmID;
		/**
		 * 银行代码
		 */
		public String bankID;
		/**
		 * 银行账号
		 */
		public String account;
		/**
		 * 对账信息类型0：出金1：入金
		 */
		public int type;
		/**
		 * 银行流水金额
		 */
		public double money;
		/**
		 * 市场流水金额
		 */
		public double m_money;
		/**
		 * 对账日期
		 */
		public Date compareDate;
		/**
		 * 备注
		 */
		public String note;
		/**
		 * 创建日期
		 */
		public Timestamp createtime;
		/**
		 * 记录状态：0 正常  1 已删除
		 */
		public int status;

		/**
		 * 返回对象属性值
		 */
		public String toString()
		{
			String sep = "\n";
			StringBuffer sb = new StringBuffer();
			sb.append("**"+this.getClass().getName()+"**"+sep);
			sb.append("Id:" + this.id+sep);
			sb.append("m_Id:" + this.m_Id+sep);
			sb.append("firmID:" + this.firmID+sep);
			sb.append("account:" + this.account+sep);
			sb.append("type:" + this.type+sep);
			sb.append("money:" + this.money+sep);
			sb.append("m_money:" + this.m_money+sep);
			sb.append("compareDate:" + this.compareDate+sep);
			sb.append("Note:" + this.note+sep);
			sb.append("Createtime:" + this.createtime+sep);
			sb.append("status:" + this.status+sep);
			sb.append(sep);
			return sb.toString();
		}
}
