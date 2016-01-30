package gnnt.trade.bank.vo;

import java.io.Serializable;


/**
 * <p>Title: 字典对象类</p>
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
public class DicValue implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * 序号 
		 */
		public long id;
		/**
		 * 分类 0：摘要号 1：科目代码
		 */
		public int type;
		/**
		 * 银行代码
		 */
		public String bankID;
		/**
		 * 名称
		 */
		public String name;
		
		/**
		 * 值
		 */
		public String value;
		/**
		 * 备注
		 */
		public String note;


		
		/**
		 * 返回对象属性值
		 */
		public String toString()
		{
			String sep = "\n";
			StringBuffer sb = new StringBuffer();
			sb.append("**"+this.getClass().getName()+"**"+sep);
			sb.append("Id:" + this.id+sep);
			sb.append("type:" + this.type+sep);
			sb.append("bankID:" + this.bankID+sep);
			sb.append("name:" + this.name+sep);
			sb.append("value:" + this.value+sep);
			sb.append("note:" + this.note+sep);
			sb.append(sep);
			return sb.toString();
		}

}
