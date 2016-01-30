package gnnt.trade.bank.vo.bankdz.sfz.resave;
import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.TradeMoneyChild;

import java.io.Serializable;
import java.util.Vector;
/**出入金流水类*/
public class TradeMoney extends FileFathor implements Serializable {
	private static final long serialVersionUID = 1L;
	/**当个类的属性个数*/
	protected int preferencenum = 4;
	/**流水总比数*/
	public int tradeCound;
	/**出金总金额*/
	public double outMoney;
	/**入金总金额*/
	public double inMoney;
	/**挂账总金额*/
	public double billMoney;
	/**当个文件中的流水信息*/
	public Vector<TradeMoneyChild> child = new Vector<TradeMoneyChild>();
	/**当个类信息*/
	public String toString(){
		StringBuffer sb = new StringBuffer();
		String str = "\n";
		sb.append("-------------文件首行信息-------------"+str);
		sb.append("流水总比数(tradeCound)["+tradeCound+"]"+str);
		sb.append("出金总金额(outMoney)["+outMoney+"]"+str);
		sb.append("入金总金额(inMoney)["+inMoney+"]"+str);
		sb.append("挂账总金额(billMoney)["+billMoney+"]"+str);
		sb.append(str);
		for(TradeMoneyChild tm : child){
			sb.append("-------------文件具体信息-------------"+str);
			sb.append(tm.toString());
		}
		return sb.toString();
	}
}
