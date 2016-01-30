package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Title: 交易商代码银行账户对应关系对象类
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 *
 * <p>
 * Company: gnnt
 * </p>
 *
 * @author zhangzhongli
 * @version 1.0
 */
public class CorrespondValue implements Serializable {
	/**
	* 
	*/
	// private static final long serialVersionUID = 1L;
	// /**
	// * 银行代码
	// */
	// public String bankID;
	// /**
	// * 交易商代码
	// */
	// public String firmID;
	// /**
	// * 银行帐号(正式账号)
	// */
	// public String account;
	// /**
	// * 银行帐号(内部账号)
	// */
	// public String account1;
	// /**
	// * 账户名
	// */
	// public String accountName;
	// /**
	// * 银行内部账号名称
	// */
	// public String accountName1;
	// /**
	// * 开户行名字
	// */
	// public String bankName;
	// /**
	// * 银行名称 区别于开户行名字
	// * 银行名称：中国建设银行 中国工商银行等等
	// * 开户行名：建行---支行 工行---分行等等
	// */
	// //public String headOffice;
	// /**
	// * 开户行省份
	// */
	// public String bankProvince;
	// /**
	// * 开户行市
	// */
	// public String bankCity;
	// /**
	// * 电话
	// */
	// public String mobile;
	// /**
	// * 电子邮件
	// */
	// public String email;
	// /**
	// * 状态：0：可用1：禁用
	// */
	// public int status ;
	// /**
	// * 签约 状态：0：签约失败1：签约成功
	// */
	// public int isOpen ;
	// /**
	// * 客户证件类型 1身份证,
	// * 2军官证,3国内护照,4户口本,5学员证,6退休证,
	// * 7临时身份证,8组织机构代码,9营业执照,
	// * A警官证,B解放军士兵证,C回乡证,D外国护照,
	// * E港澳台居民身份证,F台湾通行证及其他有效旅行证,
	// * G海外客户编号,H解放军文职干部证,I武警文职干部证,
	// * J武警士兵证,X其他有效证件,Z重号身份证
	// */
	// public String cardType ;
	// /**
	// * 证件号
	// */
	// public String card;
	// /**
	// * 交易商冻结资金
	// */
	// public double frozenFuns;
	// /**
	// * 入世协议号
	// */
	// public String inMarketCode;
	// /**
	// * 操作标志
	// * 0-新增 1-修改 2-删除 3-校验
	// */
	// public int falg;
	// /**
	// * 签约时间
	// */
	// public java.util.Date opentime;
	// /**
	// * 解约时间
	// */
	// public java.util.Date deltime;
	// /**
	// * 开户行支付系统行号
	// */
	// public String OpenBankCode;
	//
	// /**
	// * 银行卡密码
	// */
	// public String bankCardPassword;
	// /**
	// * 联系人
	// */
	// public String Linkman;
	// /**
	// * 移动电话
	// */
	// public String Phone;
	// /**
	// * 联系地址
	// */
	// public String addr;
	// /**
	// * 法人姓名
	// */
	// public String LawName;
	// /**
	// * 是否需要短信通知 1:需要 0：不需要
	// */
	// public String NoteFlag;
	// /**
	// * 接收短信通知手机号
	// */
	// public String NotePhone;
	// /**
	// * 邮编
	// */
	// public String zipCode;
	// /**
	// * 复合标志
	// */
	// public String checkFlag;
	// /**
	// * 构造函数
	// * **/
	// public CorrespondValue(String bankID,String firmID,String account)
	// {
	// this.bankID = bankID;
	// this.firmID = firmID;
	// this.account = account;
	// }
	// public CorrespondValue()
	// {
	//
	// }

	private static final long serialVersionUID = 1L;
	public String bankID;
	public String firmID;
	public String contact;
	public String account;
	public String account1;
	public String accountName;
	public String accountName1;
	public String bankName;
	public String bankProvince;
	public String bankCity;
	public String mobile;
	public String email;
	public int status;
	public int isOpen;
	public String cardType;
	public String card;
	public double frozenFuns;
	public String inMarketCode;
	public int falg;
	public Date opentime;
	public Date deltime;
	public String OpenBankCode;
	public String bankCardPassword;
	public String Linkman;
	public String Phone;
	public String addr;
	public String LawName;
	public String NoteFlag;
	public String NotePhone;
	public String accountType;
	public String zipCode;
	public String signInfo;
	public String actionID;
	public String checkFlag;
	public String notes;
	public String accountBank;
	public String accountBankNum;
	public String isCrossLine;
	public String type;
	public double amount;
	public String systemID;
	public String sysFirmID;
	public String fundsPwd;

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBankID() {
		return this.bankID;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	public String getFirmID() {
		return this.firmID;
	}

	public void setFirmID(String firmID) {
		this.firmID = firmID;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount1() {
		return this.account1;
	}

	public void setAccount1(String account1) {
		this.account1 = account1;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountName1() {
		return this.accountName1;
	}

	public void setAccountName1(String accountName1) {
		this.accountName1 = accountName1;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankProvince() {
		return this.bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return this.bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsOpen() {
		return this.isOpen;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCard() {
		return this.card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public double getFrozenFuns() {
		return this.frozenFuns;
	}

	public void setFrozenFuns(double frozenFuns) {
		this.frozenFuns = frozenFuns;
	}

	public String getInMarketCode() {
		return this.inMarketCode;
	}

	public void setInMarketCode(String inMarketCode) {
		this.inMarketCode = inMarketCode;
	}

	public int getFalg() {
		return this.falg;
	}

	public void setFalg(int falg) {
		this.falg = falg;
	}

	public Date getOpentime() {
		return this.opentime;
	}

	public void setOpentime(Date opentime) {
		this.opentime = opentime;
	}

	public Date getDeltime() {
		return this.deltime;
	}

	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}

	public String getOpenBankCode() {
		return this.OpenBankCode;
	}

	public void setOpenBankCode(String openBankCode) {
		this.OpenBankCode = openBankCode;
	}

	public String getBankCardPassword() {
		return this.bankCardPassword;
	}

	public void setBankCardPassword(String bankCardPassword) {
		this.bankCardPassword = bankCardPassword;
	}

	public String getLinkman() {
		return this.Linkman;
	}

	public void setLinkman(String linkman) {
		this.Linkman = linkman;
	}

	public String getPhone() {
		return this.Phone;
	}

	public void setPhone(String phone) {
		this.Phone = phone;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getLawName() {
		return this.LawName;
	}

	public void setLawName(String lawName) {
		this.LawName = lawName;
	}

	public String getNoteFlag() {
		return this.NoteFlag;
	}

	public void setNoteFlag(String noteFlag) {
		this.NoteFlag = noteFlag;
	}

	public String getNotePhone() {
		return this.NotePhone;
	}

	public void setNotePhone(String notePhone) {
		this.NotePhone = notePhone;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getSignInfo() {
		return this.signInfo;
	}

	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}

	public String getActionID() {
		return this.actionID;
	}

	public void setActionID(String actionID) {
		this.actionID = actionID;
	}

	public String getCheckFlag() {
		return this.checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAccountBank() {
		return this.accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountBankNum() {
		return this.accountBankNum;
	}

	public void setAccountBankNum(String accountBankNum) {
		this.accountBankNum = accountBankNum;
	}

	public String getIsCrossLine() {
		return this.isCrossLine;
	}

	public void setIsCrossLine(String isCrossLine) {
		this.isCrossLine = isCrossLine;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getSystemID() {
		return this.systemID;
	}

	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}

	public String getSysFirmID() {
		return this.sysFirmID;
	}

	public void setSysFirmID(String sysFirmID) {
		this.sysFirmID = sysFirmID;
	}

	public String getFundsPwd() {
		return this.fundsPwd;
	}

	public void setFundsPwd(String fundsPwd) {
		this.fundsPwd = fundsPwd;
	}

	public static long getSerialversionuid() {
		return 1L;
	}

	public CorrespondValue(String bankID, String firmID, String account) {
		this.bankID = bankID;
		this.firmID = firmID;
		this.account = account;
	}

	public CorrespondValue() {
	}

	public String toString() {
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**" + getClass().getName() + "**" + sep);
		sb.append("bankID:" + this.bankID + sep);
		sb.append("firmID:" + this.firmID + sep);
		sb.append("account:" + this.account + sep);
		sb.append("account1:" + this.account1 + sep);
		sb.append("accountName:" + this.accountName + sep);
		sb.append("bankName:" + this.bankName + sep);
		sb.append("bankProvince:" + this.bankProvince + sep);
		sb.append("bankCity:" + this.bankCity + sep);
		sb.append("mobile:" + this.mobile + sep);
		sb.append("Email:" + this.email + sep);
		sb.append("status:" + this.status + sep);
		sb.append("IsOpen:" + this.isOpen + sep);
		sb.append("CardType:" + this.cardType + sep);
		sb.append("Card:" + this.card + sep);
		sb.append("frozenFuns:" + this.frozenFuns + sep);
		sb.append("inMarketCode:" + this.inMarketCode + sep);
		sb.append("opentime:" + this.opentime + sep);
		sb.append("deltime:" + this.deltime + sep);
		sb.append("Linkman:" + this.Linkman + sep);
		sb.append("Phone:" + this.Phone + sep);
		sb.append("addr:" + this.addr + sep);
		sb.append("LawName:" + this.LawName + sep);
		sb.append("NoteFlag:" + this.NoteFlag + sep);
		sb.append("NotePhone:" + this.NotePhone + sep);
		sb.append("zipCode:" + this.zipCode + sep);
		sb.append("checkFlag:" + this.checkFlag + sep);
		sb.append(sep);
		return sb.toString();
	}

}
