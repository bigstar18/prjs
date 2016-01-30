package gnnt.trade.bank.vo.bankdz.ms;

import java.io.Serializable;
import java.sql.Date;

/**
 * 存管汇总账户资金交收汇总文件
 * @author:taog
 * @Date:2012-1-10下午02:21:06
 */
public class Spay implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String sOrganId; //商品交易所编号
	private String businType; // 业务类型
	private String businCode; //交收业务代码
	private String companyCode; //交收公司代码
	private String companyName; //交收公司名称
	private String varCode; //品种代码
	private String varName; //品种名称
	private String bCustAcct; //银行账号
	private String bCustAcctName; //银行账号名称
	private String MoneyType; //币种
	private double money; //交收金额
	private Date date;//日期
	private String info; //备注
	
	public String getsOrganId() {
		return sOrganId;
	}
	public void setsOrganId(String sOrganId) {
		this.sOrganId = sOrganId;
	}
	public String getBusinType() {
		return businType;
	}
	public void setBusinType(String businType) {
		this.businType = businType;
	}
	public String getBusinCode() {
		return businCode;
	}
	public void setBusinCode(String businCode) {
		this.businCode = businCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getVarCode() {
		return varCode;
	}
	public void setVarCode(String varCode) {
		this.varCode = varCode;
	}
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public String getbCustAcct() {
		return bCustAcct;
	}
	public void setbCustAcct(String bCustAcct) {
		this.bCustAcct = bCustAcct;
	}
	public String getbCustAcctName() {
		return bCustAcctName;
	}
	public void setbCustAcctName(String bCustAcctName) {
		this.bCustAcctName = bCustAcctName;
	}
	public String getMoneyType() {
		return MoneyType;
	}
	public void setMoneyType(String moneyType) {
		MoneyType = moneyType;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}

