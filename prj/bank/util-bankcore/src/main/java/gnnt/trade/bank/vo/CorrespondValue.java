package gnnt.trade.bank.vo;

import java.io.Serializable;


/**
 * <p>Title: 交易商代码银行账户对应关系对象类</p>
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
public class CorrespondValue implements Serializable 
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
		 * 交易商代码
		 */
		public String firmID;
		/**
		 * 银行帐号(正式账号)
		 */
		public String account;
		/**
		 * 银行帐号(内部账号)
		 */
		public String account1;
		/**
		 * 账户名
		 */
		public String accountName;
		/**
		 * 银行内部账号名称
		 */
		public String accountName1;
		/**
		 * 开户行名字
		 */
		public String bankName;
		/**
		 * 银行名称 区别于开户行名字
		 * 银行名称：中国建设银行 中国工商银行等等
		 * 开户行名：建行---支行 工行---分行等等 
		 */
		//public String headOffice;
		/**
		 * 开户行省份
		 */
		public String bankProvince;
		/**
		 * 开户行市
		 */
		public String bankCity;
		/**
		 * 电话
		 */
		public String mobile;
		/**
		 * 电子邮件
		 */
		public String email;
		/**
		 * 状态：0：可用1：禁用
		 */
		public int status ;
		/**
		 * 签约 状态：0：签约失败1：签约成功
		 */
		public int isOpen ;
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
		 * 交易商冻结资金
		 */
		public double frozenFuns;
		/**
		 * 入世协议号
		 */
		public String inMarketCode;
		/**
		 * 操作标志
		 * 0-新增 1-修改 2-删除 3-校验
		 */
		public int falg;
		/**
		 * 签约时间
		 */
		public java.util.Date opentime;
		/**
		 * 解约时间
		 */
		public java.util.Date deltime;
		/**
		 * 开户行支付系统行号
		 */
		public String OpenBankCode;

		/**
		 * 银行卡密码
		 */
		public String bankCardPassword;
		/**
		 * 联系人
		 */
		public String Linkman;
		/**
		 * 移动电话
		 */
		public String Phone;
		/**
		 * 联系地址
		 */
		public String addr;
		/**
		 * 法人姓名
		 */
		public String LawName;
		/**
		 * 是否需要短信通知  1:需要 0：不需要
		 */
		public String NoteFlag;
		/**
		 * 接收短信通知手机号
		 */
		public String NotePhone;
		/**
		 * 邮编
		 */
		public String zipCode;
		/**
		 * 复合标志
		 */
		public String checkFlag;
		/**
		 * 构造函数
		 * **/
		public CorrespondValue(String bankID,String firmID,String account)
		{
			this.bankID = bankID;
			this.firmID = firmID;
			this.account = account;
		}
		public CorrespondValue()
		{
			
		}
		/**
		 * 返回对象属性值
		 */
		public String toString()
		{
			String sep = "\n";
			StringBuffer sb = new StringBuffer();
			sb.append("**"+this.getClass().getName()+"**"+sep);
			sb.append("bankID:" + this.bankID+sep);
			sb.append("firmID:" + this.firmID+sep);
			sb.append("account:" + this.account+sep);
			sb.append("account1:" + this.account1+sep);
			sb.append("accountName:" + this.accountName+sep);
			sb.append("bankName:" + this.bankName+sep);
			sb.append("bankProvince:" + this.bankProvince+sep);
			sb.append("bankCity:" + this.bankCity+sep);
			sb.append("mobile:" + this.mobile+sep);
			sb.append("Email:" + this.email+sep);
			sb.append("status:" + this.status+sep);
			sb.append("IsOpen:" + this.isOpen+sep);
			sb.append("CardType:" + this.cardType+sep);
			sb.append("Card:" + this.card+sep);
			sb.append("frozenFuns:" + this.frozenFuns+sep);
			sb.append("inMarketCode:" + this.inMarketCode+sep);
			sb.append("opentime:" + this.opentime+sep);
			sb.append("deltime:" + this.deltime +sep);
			sb.append("Linkman:" + this.Linkman +sep);
			sb.append("Phone:" + this.Phone +sep);
			sb.append("addr:" + this.addr +sep);
			sb.append("LawName:" + this.LawName +sep);
			sb.append("NoteFlag:" + this.NoteFlag +sep);
			sb.append("NotePhone:" + this.NotePhone +sep);
			sb.append("zipCode:" + this.zipCode +sep);
			sb.append("checkFlag:" + this.checkFlag +sep);
			sb.append(sep);
			return sb.toString();
		}

}
