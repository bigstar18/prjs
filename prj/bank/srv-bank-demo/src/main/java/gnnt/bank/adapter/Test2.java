package gnnt.bank.adapter;
import gnnt.bank.adapter.util.ProcTryThread;
import gnnt.trade.bank.vo.InMoneyMarket;
import gnnt.trade.bank.vo.OutMoneyMarket;
public class Test2 {
	public static void main(String[] args) {
			for(int i=0;i<1;i++){
				query();
				inmoney();
				outmoney();
			}
		System.out.println("-------------------------------------------------------------------");
	}
	public static void query(){
		new ProcTryThread("003","999123456789004","222222",50000).start();//交通
		new ProcTryThread("003","999123456789003","222222",50000).start();//交通
	}
	public static void inmoney(){
		int number = 50000;
		InMoneyMarket imm = new InMoneyMarket();
		imm.firmID = "999123456789003";
		imm.bankID = "003";
		imm.contact = "999123456789003";
		imm.money = 0.01;
		imm.accountPwd = "222222";
		new ProcTryThread(imm,number).start();
		InMoneyMarket imm2 = new InMoneyMarket();
		imm2.firmID = "999123456789004";
		imm2.bankID = "003";
		imm2.contact = "999123456789004";
		imm2.money = 0.01;
		imm2.accountPwd = "222222";
		new ProcTryThread(imm2,number).start();
	}
	public static void outmoney(){
		int number = 50000;
		OutMoneyMarket omm = new OutMoneyMarket();
		omm.firmID = "999123456789003";
		omm.bankID = "003";
		omm.contact = "999123456789003";
		omm.money = 0.01;
		new ProcTryThread(omm,number).start();
		OutMoneyMarket omm2 = new OutMoneyMarket();
		omm2.firmID = "999123456789004";
		omm2.bankID = "003";
		omm2.contact = "999123456789004";
		omm2.money = 0.01;
		new ProcTryThread(omm2,number).start();
	}
}
