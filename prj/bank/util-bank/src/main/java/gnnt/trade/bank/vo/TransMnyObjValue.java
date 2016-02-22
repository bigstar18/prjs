package gnnt.trade.bank.vo;

import java.io.Serializable;


/**
 * <p>Title: 资金划转配置对象类</p>
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
public class TransMnyObjValue implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * 序号
		 */
		public int id;
		/**
		 * 业务实现类
		 */
		public String className;
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
			sb.append("className:" + this.className+sep);
			sb.append("note:" + this.note+sep);
			sb.append(sep);
			return sb.toString();
		}

}
