package gnnt.trade.bank.vo.bankdz.sfz.resave.child;
import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;

import java.io.Serializable;
/**出入金流水子类*/
public class TradeMoneyChild extends FileFathor implements Serializable {
	private static final long serialVersionUID = 1L;
	/**当个类的属性个数*/
	protected int preferencenum = 15;
	/**序号*/
	public int number;
	/**记账标志(1：出金 2：入金 3：挂账)*/
	public int type;
	/**处理标志(挂账才有值)*/
	public String tradeType;
	/**付款人账号*/
	public String payAccount="";
	/**收款人账号*/
	public String resaveAccount="";
	/**交易网会员代码*/
	public String firmID="";
	/**子账户*/
	public String account1="";
	/**子账户名称*/
	public String account1Name="";
	/**交易金额*/
	public double money;
	/**手续费*/
	public double fee;
	/**支付手续费子账号*/
	public String feeaccount1="";
	/**支付子账号名称*/
	public String feeaccount1Name="";
	/**交易日期*/
	public String tradeDate="";
	/**交易时间*/
	public String tradeTime="";
	/**银行前置机流水号*/
	public String funID="";
	/**获取信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("序号(number)["+number+"]"+str);
		sb.append("记账标志(1：出金 2：入金 3：挂账)(type)["+type+"]"+str);
		sb.append("处理标志(挂账才有值)(tradeType)["+tradeType+"]"+str);
		sb.append("付款人账号(payAccount)["+payAccount+"]"+str);
		sb.append("收款人账号(resaveAccount)["+resaveAccount+"]"+str);
		sb.append("交易网会员代码(firmID)["+firmID+"]"+str);
		sb.append("子账户(account1)["+account1+"]"+str);
		sb.append("子账户名称(account1Name)["+account1Name+"]"+str);
		sb.append("交易金额(money)["+money+"]"+str);
		sb.append("手续费(fee)["+fee+"]"+str);
		sb.append("支付手续费子账号(feeaccount1)["+feeaccount1+"]"+str);
		sb.append("支付子账号名称(feeaccount1Name)["+feeaccount1Name+"]"+str);
		sb.append("交易日期(tradeDate)["+tradeDate+"]"+str);
		sb.append("交易时间(tradeTime)["+tradeTime+"]"+str);
		sb.append("银行前置机流水号(funID)["+funID+"]"+str);
		sb.append(str);
		return sb.toString();
	}
	/**解析报文信息，获取字段值*/
	public void getBody(String str)throws Exception{
		if(str==null || str.trim().length()<=0){
			throw new Exception("传入的信息为空");
		}
		String[] merthods = str.split(this.separator,-1);
		if(merthods.length<preferencenum){
			throw new Exception("传入的信息个数小于需要信息个数");
		}else if(merthods.length>preferencenum+1){
			System.out.println("注意：当个传入的信息数量超出了所需的信息个数，是否为错误数据？序号："+merthods[0]);
		}
		try{
			int n = 0;
			this.number = Integer.parseInt(merthods[n++].trim());
			this.type = Integer.parseInt(merthods[n++].trim());
			this.tradeType = merthods[n++].trim();
			this.payAccount = merthods[n++].trim();
			this.resaveAccount = merthods[n++].trim();
			this.firmID = merthods[n++].trim();
			this.account1 = merthods[n++].trim();
			this.account1Name = merthods[n++].trim();
			this.money = Double.valueOf(merthods[n++].trim());
			this.fee = Double.valueOf(merthods[n++].trim());
			this.feeaccount1 = merthods[n++].trim();
			this.feeaccount1Name = merthods[n++].trim();
			this.tradeDate = merthods[n++].trim();
			this.tradeTime = merthods[n++].trim();
			this.funID = merthods[n++].trim();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	public static void main(String args[]){
		String str = "1&2&&11000097281401&11000096339501&000889&888800000009189&会员四&10000.00&0.00&&&20100611&102954&20280704702937&";
		TradeMoneyChild tm = new TradeMoneyChild();
		try{
			tm.getBody(str);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(tm.toString());
	}
}
