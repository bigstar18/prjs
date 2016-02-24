package gnnt.trade.bank.vo.bankdz.ms;

import java.io.Serializable;

/**
 * 存管客户交易资金净额清算文件
 * @author:taog
 * @Date:2012-1-10下午02:19:12
 */
public class Sbusi  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String serialNo; //顺序流水号 
	private String bOrganId; //银行类型
	private String bCustAcct; //存管账号/银行结算账号
	private String sOrganId; //商品交易所编号
	private String sBranchId; //营业部编码
	private String sCustType; //客户标识类型
	private String sCustAcct; //商品交易所资金账号
	private String MoneyType; //币种
	private double money; //清算金额
	private int TNDays;//T+N天数(为空)
	private String info;//备注(为空)
	private String procFlag;   //处理标记(为空)
	private double inMoney;//买入汇总金额(可为空)
	private double outMoney;//卖出汇总金额(可为空)
	
	
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getbOrganId() {
		return bOrganId;
	}
	public void setbOrganId(String bOrganId) {
		this.bOrganId = bOrganId;
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
	public int getTNDays() {
		return TNDays;
	}
	public void setTNDays(int tNDays) {
		TNDays = tNDays;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getProcFlag() {
		return procFlag;
	}
	public void setProcFlag(String procFlag) {
		this.procFlag = procFlag;
	}
	public double getInMoney() {
		return inMoney;
	}
	public void setInMoney(double inMoney) {
		this.inMoney = inMoney;
	}
	public double getOutMoney() {
		return outMoney;
	}
	public void setOutMoney(double outMoney) {
		this.outMoney = outMoney;
	}
	
	
}

