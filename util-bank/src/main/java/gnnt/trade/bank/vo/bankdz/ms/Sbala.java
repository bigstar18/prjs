package gnnt.trade.bank.vo.bankdz.ms;

import java.io.Serializable;
import java.sql.Date;

/**
 * 存管客户资金余额明细文件
 * @author:taog
 * @Date:2012-1-10下午02:19:58
 */
public class Sbala implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String bCustAcct; //存管账号/银行结算账号
	private String sCustAcct; //商品交易所资金账号
	private String MoneyType; //币种
	private double money; //商品交易所资金余额
	private double inMoney;	//未回买入资金
	private double outMoney; //未回卖出资金
	private double fetchMoney; //冻结资金
	private Date date; //日期
	
	
	public String getbCustAcct() {
		return bCustAcct;
	}
	public void setbCustAcct(String bCustAcct) {
		this.bCustAcct = bCustAcct;
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
	public double getFetchMoney() {
		return fetchMoney;
	}
	public void setFetchMoney(double fetchMoney) {
		this.fetchMoney = fetchMoney;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
}

