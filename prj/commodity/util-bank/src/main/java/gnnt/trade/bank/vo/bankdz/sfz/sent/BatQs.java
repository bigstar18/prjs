package gnnt.trade.bank.vo.bankdz.sfz.sent;
import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import gnnt.trade.bank.vo.bankdz.sfz.sent.child.BatQsChild;
import java.io.Serializable;
import java.util.Vector;
/**
 * 清算文件【1000】
 * BatQs+交易网代码（4位）+时间（14位）.txt
 */
public class BatQs extends FileFathor implements Serializable {
	private static final long serialVersionUID = 1L;
	/**总笔数*/
	public int rowCount;
	/**信息标准类数组*/
	public Vector<BatQsChild> child = new Vector<BatQsChild>();
	/**本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("-------------文件首行信息-------------"+str);
		sb.append("rowCount["+rowCount+"]"+str);
		for(BatQsChild bcdc : child){
			sb.append("-------------文件具体信息-------------"+str);
			sb.append(bcdc.toString());
		}
		return sb.toString();
	}
}
