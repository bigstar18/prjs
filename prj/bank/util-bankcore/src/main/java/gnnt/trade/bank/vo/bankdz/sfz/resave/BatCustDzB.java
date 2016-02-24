package gnnt.trade.bank.vo.bankdz.sfz.resave;
import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzBChild;
import java.io.Serializable;
import java.util.Vector;
/**
 * 会员余额文件【1002】
 * BatCustDz+交易网代码（4位）+时间（14位）.txt
 */
public class BatCustDzB extends FileFathor implements Serializable {
	private static final long serialVersionUID = 1L;
	/**当个类的属性个数*/
	protected int preferencenum = 1;
	/**总笔数*/
	public int rowCount;
	/**详细信息列表*/
	public Vector<BatCustDzBChild> child = new Vector<BatCustDzBChild>();
	/**本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("-------------文件首行信息-------------"+str);
		sb.append("rowCount["+rowCount+"]"+str);
		for(BatCustDzBChild bcdc : child){
			sb.append("-------------文件具体信息-------------"+str);
			sb.append(bcdc.toString());
		}
		return sb.toString();
	}
}