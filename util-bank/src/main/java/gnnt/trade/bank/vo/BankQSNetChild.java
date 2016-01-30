package gnnt.trade.bank.vo;

public class BankQSNetChild {
	//
	// /** 银行名称*/
	// public String bankName;
	// /** 银行编号*/
	// public String bankId;
	// /** 上日权益*/
	// public double lastDayQY;
	// /** 当日权益*/
	// public double toDayQY;
	// /** 当日入金*/
	// public double InMoney;
	// /** 当日出金*/
	// public double OutMoney;
	// /** 当日手续费*/
	// public double todayFee;
	// /** 当日卖方货款*/
	// public double sellerPayment;
	// /** 当日买方货款*/
	// public double BuyerPayment;
	// /** 当日盈利*/
	// public double Profit;
	// /** 当日亏损*/
	// public double Loss;
	// /** 当日交易盈亏变化量*/
	// public double QYchange;
	// public String firmID;
	// public String toString(){
	// StringBuffer sb=new StringBuffer();
	// String step="\n";
	// sb.append(bankName+step);
	// sb.append(bankId+step);
	// sb.append(lastDayQY+step);
	// sb.append(toDayQY+step);
	// sb.append(InMoney+step);
	// sb.append(OutMoney+step);
	// sb.append(todayFee+step);
	// sb.append(sellerPayment+step);
	// sb.append(BuyerPayment+step);
	// sb.append(Profit+step);
	// sb.append(Loss+step);
	// sb.append(QYchange+step);
	// sb.append(firmID+step);
	// return sb.toString();
	// }
	public String bankName;
	public String bankId;
	public double lastDayQY;
	public double lastKY;
	public double todayKY;
	public double toDayQY;
	public double InMoney;
	public double OutMoney;
	public double todayFee;
	public double sellerPayment;
	public double BuyerPayment;
	public double Profit;
	public double Loss;
	public double QYchange;
	public double KYchange;
	public String firmID;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		String step = "\n";
		sb.append(this.bankName + step);
		sb.append(this.bankId + step);
		sb.append(this.lastDayQY + step);
		sb.append(this.toDayQY + step);
		sb.append(this.lastKY + step);
		sb.append(this.todayKY + step);
		sb.append(this.InMoney + step);
		sb.append(this.OutMoney + step);
		sb.append(this.todayFee + step);
		sb.append(this.sellerPayment + step);
		sb.append(this.BuyerPayment + step);
		sb.append(this.Profit + step);
		sb.append(this.Loss + step);
		sb.append(this.QYchange + step);
		sb.append(this.KYchange + step);
		sb.append(this.firmID + step);
		return sb.toString();
	}

}
