package gnnt.bank.adapter.util;
import gnnt.bank.adapter.BankAdapter;
import gnnt.trade.bank.vo.FirmBalanceValue;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.OutMoneyMarket;
import gnnt.trade.bank.vo.ReturnValue;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.Locale;
public class ProcTryThread extends Thread {
	private static int j=0;
	private int i=0;
	private int row =1;
	private String methodName;
	private String bankID;
	private String firmID;
	private String password;
	private InMoneyMarket imm;
	private OutMoneyMarket omm;
	public ProcTryThread(String bankID,String firmID,String password,int row){
		this.bankID = bankID;
		this.firmID = firmID;
		this.password = password;
		this.row = row;
		this.methodName = "query";
	}
	public ProcTryThread(InMoneyMarket imm,int row){
		this.imm = imm;
		this.row = row;
		this.methodName = "inmoney";
	}
	public ProcTryThread(OutMoneyMarket omm,int row){
		this.omm = omm;
		this.row = row;
		this.methodName = "outmoney";
	}
	public ProcTryThread(int row){
		this.row = row;
		this.methodName = "trydouble";
	}
	public void run(){
		try {
			Thread.sleep(1);
			Method method = this.getClass().getMethod(methodName, null);
			if(method != null){
				method.invoke(this, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void trydouble(){
		double num = 100.123;
		for(;i<getrow();i++){
			if(i==5 && getrow()==10){
				System.out.println(getrow()+"中第["+i+"]个"+formatToMoney(num)+j++);
			}else{
				System.out.println(getrow()+"中第["+i+"]个"+formatToMoney2(num)+j++);
			}
		}
	}
	public static String formatToMoney(double money){
		Locale.setDefault(Locale.US);
		NumberFormat numberFormate;
		numberFormate=NumberFormat.getCurrencyInstance();
		return numberFormate.format(money);
	}
	public static String formatToMoney2(double money){
		NumberFormat numberFormate;
		numberFormate=NumberFormat.getCurrencyInstance();
		return numberFormate.format(money);
	}
	public void query(){
		if(bankID==null || bankID.trim().length()<=0 || firmID==null || firmID.trim().length()<=0){
			return;
		}
		try {
			for(;i<getrow();i++){
				FirmBalanceValue fbv = BankAdapter.getProcessor().getFirmBalance(bankID, firmID, password);
				if(fbv.bankBalance<=0){
					System.out.println(firmID+"的第[,"+i+",]条[查询余额]["+fbv.bankBalance+"],"+j++);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	public void inmoney(){
		if(imm==null){
			return;
		}
		try{
			for(;i<getrow();i++){
				ReturnValue rv = BankAdapter.getProcessor().inMoneyMarket(imm);
				if(rv.result<0){
					System.out.println(imm.firmID+"的第[,"+i+",]条[入金]["+rv.result+"]["+rv.remark+"],"+j++);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void outmoney(){
		if(omm==null){
			return;
		}
		try{
			for(;i<getrow();i++){
				ReturnValue rv = BankAdapter.getProcessor().outMoneyMarket(omm);
				if(rv.result<0){
					System.out.println(omm.firmID+"的第[,"+i+",]条[出金]["+rv.result+"]["+rv.remark+"],"+j++);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private int getrow(){
		if(row<=0){
			row = 1;
		}
		return row;
	}
}
