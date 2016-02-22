package gnnt.trade.bank.vo.bankdz.sfz.resave;
import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.KXHfailChild;
import java.io.Serializable;
import java.util.Vector;
/**
 * 会员开销户流水文件
 * KXH+ ThirdLogNo CustLog+交易网代码（4位）+时间（14位）.txt
 */
public class KXHfail extends FileFathor implements Serializable {
	private static final long serialVersionUID = 1L;
	/**总笔数*/
	public int rowCount;
	/**信息标准类数组*/
	public Vector<KXHfailChild> child = new Vector<KXHfailChild>();
	/**本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("-------------文件首行信息-------------"+str);
		sb.append("rowCount["+rowCount+"]"+str);
		for(KXHfailChild kc : child){
			sb.append("-------------文件具体信息-------------"+str);
			sb.append(kc.toString());
		}
		return sb.toString();
	}
}
