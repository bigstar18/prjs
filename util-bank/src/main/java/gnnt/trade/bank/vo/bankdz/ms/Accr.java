package gnnt.trade.bank.vo.bankdz.ms;

import java.io.Serializable;

/**
 * 存管客户批量利息入帐文件
 * @author:taog
 * @Date:2012-1-10下午02:20:32
 */
public class Accr implements Serializable{
	private static final long serialVersionUID = 1L;

	
	private String serialNo; //顺序流水号
	private String bCustAcct; //存管账号/银行结算账号
	private String sOrganId; //商品交易所编号
	private String sBranchId; //营业部编码
	private String sCustType; //客户标识类型
	private String sCustAcct; //商品交易所资金账号
	private String MoneyType; //币种
	private double money; // 利息
	private double interest; //利息税
	
	
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getbCustAcct() {
		return bCustAcct;
	}
	public void setbCustAcct(String bCustAcct) {
		this.bCustAcct = bCustAcct;
	}
	public String getsOrganId() {
		return sOrganId;
	}
	public void setsOrganId(String sOrganId) {
		this.sOrganId = sOrganId;
	}
	public String getsBranchId() {
		return sBranchId;
	}
	public void setsBranchId(String sBranchId) {
		this.sBranchId = sBranchId;
	}
	public String getsCustType() {
		return sCustType;
	}
	public void setsCustType(String sCustType) {
		this.sCustType = sCustType;
	}
	public String getsCustAcct() {
		return sCustAcct;
	}
	public void setsCustAcct(String sCustAcct) {
		this.sCustAcct = sCustAcct;
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
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	
	
	

}

