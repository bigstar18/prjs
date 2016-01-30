package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.util.Date;

public class Firm implements Serializable {
	// private static final long serialVersionUID = 1L;
	// /**交易商账号*/
	// public String firmId;
	// /**交易商名称*/
	// public String name;
	// /**交易商全称*/
	// public String fullName;
	// /***/
	// public int type;
	// /**交易商银行*/
	// public String bank;
	// /***/
	// public String status;
	// /**交易商银行账号*/
	// public String bankAccount;
	// /***/
	// public String address;
	// /***/
	// public String contactMan;
	// /**手机*/
	// public String phone;
	// /**传真*/
	// public String fax;
	// /***/
	// public String postCode;
	// /**邮箱*/
	// public String email;
	// /***/
	public Date expiryDate;
	// /**备注*/
	// public String date;
	// /***/
	// public String zoneCode;
	// /***/
	// public String industryCode;
	// /***/
	// public String extendTata;
	// /**创建时间*/
	// public Date createTime;
	// /**修改时间*/
	// public Date modifyTime;
	// /**返回对象属性值*/
	// public String toString(){
	// String sep = "\n";
	// StringBuffer sb = new StringBuffer();
	// sb.append("**"+this.getClass().getName()+"**"+sep);
	// sb.append("firmId:"+this.firmId+sep);
	// sb.append("name:"+this.name+sep);
	// sb.append("fullName:"+this.fullName+sep);
	// sb.append("type:"+this.type+sep);
	// sb.append("bank:"+this.bank+sep);
	// sb.append("status:"+this.status+sep);
	// sb.append("bankAccount:"+this.bankAccount+sep);
	// sb.append("address:"+this.address+sep);
	// sb.append("contactMan:"+this.contactMan+sep);
	// sb.append("phone:"+this.phone+sep);
	// sb.append("fax:"+this.fax+sep);
	// sb.append("postCode:"+this.postCode+sep);
	// sb.append("email:"+this.email+sep);
	// sb.append("expiryDate:"+this.expiryDate+sep);
	// sb.append("date:"+this.date+sep);
	// sb.append("zoneCode:"+this.zoneCode+sep);
	// sb.append("industryCode:"+this.industryCode+sep);
	// sb.append("extendTata:"+this.extendTata+sep);
	// sb.append("createTime:"+this.createTime+sep);
	// sb.append("modifyTime:"+this.modifyTime+sep);
	// sb.append(sep);
	// return sb.toString();
	// }

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	private static final long serialVersionUID = 1L;
	public String firmId;
	public String name;
	public String fullName;
	public int type;
	public String bank;
	public String status;
	public String bankAccount;
	public String address;
	public String contactMan;
	public String phone;
	public String fax;
	public String postCode;
	public String email;
	public String note;
	public String date;
	public String zoneCode;
	public String industryCode;
	public String extendTata;
	public Date createTime;
	public Date modifyTime;

	public Firm() {
	}

	public String toString() {
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append((new StringBuilder("**")).append(getClass().getName()).append("**").append(sep).toString());
		sb.append((new StringBuilder("firmId:")).append(firmId).append(sep).toString());
		sb.append((new StringBuilder("name:")).append(name).append(sep).toString());
		sb.append((new StringBuilder("fullName:")).append(fullName).append(sep).toString());
		sb.append((new StringBuilder("type:")).append(type).append(sep).toString());
		sb.append((new StringBuilder("bank:")).append(bank).append(sep).toString());
		sb.append((new StringBuilder("status:")).append(status).append(sep).toString());
		sb.append((new StringBuilder("bankAccount:")).append(bankAccount).append(sep).toString());
		sb.append((new StringBuilder("address:")).append(address).append(sep).toString());
		sb.append((new StringBuilder("contactMan:")).append(contactMan).append(sep).toString());
		sb.append((new StringBuilder("phone:")).append(phone).append(sep).toString());
		sb.append((new StringBuilder("fax:")).append(fax).append(sep).toString());
		sb.append((new StringBuilder("postCode:")).append(postCode).append(sep).toString());
		sb.append((new StringBuilder("email:")).append(email).append(sep).toString());
		sb.append((new StringBuilder("note:")).append(note).append(sep).toString());
		sb.append((new StringBuilder("date:")).append(date).append(sep).toString());
		sb.append((new StringBuilder("zoneCode:")).append(zoneCode).append(sep).toString());
		sb.append((new StringBuilder("industryCode:")).append(industryCode).append(sep).toString());
		sb.append((new StringBuilder("extendTata:")).append(extendTata).append(sep).toString());
		sb.append((new StringBuilder("createTime:")).append(createTime).append(sep).toString());
		sb.append((new StringBuilder("modifyTime:")).append(modifyTime).append(sep).toString());
		sb.append(sep);
		return sb.toString();
	}

	public String getFirmId() {
		return firmId;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public int getType() {
		return type;
	}

	public String getBank() {
		return bank;
	}

	public String getStatus() {
		return status;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public String getAddress() {
		return address;
	}

	public String getContactMan() {
		return contactMan;
	}

	public String getPhone() {
		return phone;
	}

	public String getFax() {
		return fax;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getEmail() {
		return email;
	}

	public String getNote() {
		return note;
	}

	public String getDate() {
		return date;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public String getExtendTata() {
		return extendTata;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}
}
