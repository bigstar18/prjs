package gnnt.trade.bank.vo;

import java.io.Serializable;


/**
 * <p>Title: 转账用户信息类</p>
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
public class TransferInfoValue implements Serializable
{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * 用户账号(正式账户)
		 */
		public String account;
		/**
		 * 账户名
		 */
		public String accountName;
		/**
		 * 用户账号(内部账户)
		 */
		public String account1;
		/**
		 * 内部账户名
		 */
		public String account1Name;
		/**
		 * 用户账户名
		 */
		public String name;
		/**
		 * 用户银行开户行
		 */
		public String bankName;
		/**
		 * 用户银行
		 */
		public String headOffice;
		/**
		 * 用户银行开户行省
		 */
		public String province;
		/**
		 * 用户银行开户行市
		 */
		public String city;
		/**
		 * 用户移动电话号码
		 */
		public String mobile;
		/**
		 * 用户电子邮件地址
		 */
		public String email;
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
		 * 返回对象属性值
		 */
		public String toString()
		{
			String sep = "\n";
			StringBuffer sb = new StringBuffer();
			sb.append("**"+this.getClass().getName()+"**"+sep);
			sb.append("depositAccount:" + this.account+sep);
			sb.append("depositAccount1:" + this.account1+sep);
			sb.append("depositName:" + this.name+sep);
			sb.append("depositBankName:" + this.name+sep);
			sb.append("depositProvince:" + this.province+sep);
			sb.append("depositMobile:" + this.mobile+sep);
			sb.append("depositEmail:" + this.email+sep);
			sb.append("cardType:" + this.cardType+sep);
			sb.append("card:" + this.card+sep);
			sb.append("headOffice:" + this.headOffice+sep);
			sb.append(sep);
			return sb.toString();
		}

}
