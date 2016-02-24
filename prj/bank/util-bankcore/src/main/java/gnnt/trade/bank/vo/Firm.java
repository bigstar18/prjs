package gnnt.trade.bank.vo;
import java.io.Serializable;
import java.util.Date;
public class Firm implements Serializable{
	private static final long serialVersionUID = 1L;
	/**交易商账号*/
	public String firmId;
	/**交易商名称*/
	public String name;
	/**交易商全称*/
	public String fullName;
	/***/
	public int type;
	/**交易商银行*/
	public String bank;
	/***/
	public String status;
	/**交易商银行账号*/
	public String bankAccount;
	/***/
	public String address;
	/***/
	public String contactMan;
	/**手机*/
	public String phone;
	/**传真*/
	public String fax;
	/***/
	public String postCode;
	/**邮箱*/
	public String email;
	/***/
	public Date expiryDate;
	/**备注*/
	public String date;
	/***/
	public String zoneCode;
	/***/
	public String industryCode;
	/***/
	public String extendTata;
	/**创建时间*/
	public Date createTime;
	/**修改时间*/
	public Date modifyTime;
	/**返回对象属性值*/
	public String toString(){
		String sep = "\n";
		StringBuffer sb = new StringBuffer();
		sb.append("**"+this.getClass().getName()+"**"+sep);
		sb.append("firmId:"+this.firmId+sep);
		sb.append("name:"+this.name+sep);
		sb.append("fullName:"+this.fullName+sep);
		sb.append("type:"+this.type+sep);
		sb.append("bank:"+this.bank+sep);
		sb.append("status:"+this.status+sep);
		sb.append("bankAccount:"+this.bankAccount+sep);
		sb.append("address:"+this.address+sep);
		sb.append("contactMan:"+this.contactMan+sep);
		sb.append("phone:"+this.phone+sep);
		sb.append("fax:"+this.fax+sep);
		sb.append("postCode:"+this.postCode+sep);
		sb.append("email:"+this.email+sep);
		sb.append("expiryDate:"+this.expiryDate+sep);
		sb.append("date:"+this.date+sep);
		sb.append("zoneCode:"+this.zoneCode+sep);
		sb.append("industryCode:"+this.industryCode+sep);
		sb.append("extendTata:"+this.extendTata+sep);
		sb.append("createTime:"+this.createTime+sep);
		sb.append("modifyTime:"+this.modifyTime+sep);
		sb.append(sep);
		return sb.toString();
	}
	/**
	 * @return the firmId
	 */
	public String getFirmId() {
		return firmId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @return the bank
	 */
	public String getBank() {
		return bank;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return the bankAccount
	 */
	public String getBankAccount() {
		return bankAccount;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @return the contactMan
	 */
	public String getContactMan() {
		return contactMan;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @return the zoneCode
	 */
	public String getZoneCode() {
		return zoneCode;
	}
	/**
	 * @return the industryCode
	 */
	public String getIndustryCode() {
		return industryCode;
	}
	/**
	 * @return the extendTata
	 */
	public String getExtendTata() {
		return extendTata;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @return the modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
}
