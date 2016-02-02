package gnnt.trade.bank.vo;

public class BankQSNetChild {
	
	/** 银行名称*/
	public String bankName;
	/** 银行编号*/
	public String bankId;
	/** 上日权益*/
	public double lastDayQY;
	/** 当日权益*/
	public double toDayQY;
	/** 当日入金*/
	public double InMoney;
	/** 当日出金*/
	public double OutMoney;
	/** 当日手续费*/
	public double todayFee;
	/** 当日卖方货款*/
	public double sellerPayment;
	/** 当日买方货款*/
	public double BuyerPayment;
	/** 当日盈利*/
	public double Profit;
	/** 当日亏损*/
	public double Loss;
	/** 当日交易盈亏变化量*/
	public double QYchange;
	public String firmID;
	public String toString(){
		StringBuffer sb=new StringBuffer();
		String step="\n";
		sb.append(bankName+step);
		sb.append(bankId+step);
		sb.append(lastDayQY+step);
		sb.append(toDayQY+step);
		sb.append(InMoney+step);
		sb.append(OutMoney+step);
		sb.append(todayFee+step);
		sb.append(sellerPayment+step);
		sb.append(BuyerPayment+step);
		sb.append(Profit+step);
		sb.append(Loss+step);
		sb.append(QYchange+step);
		sb.append(firmID+step);
		return sb.toString();
	}
}
