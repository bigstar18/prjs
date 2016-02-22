package gnnt.trade.bank.vo.bankdz.sfz.resave;
import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatFailResultChild;
import java.io.Serializable;
import java.util.Vector;
/**
 * 清算失败文件【1001】
 * BatFailResult+交易网代码（4位）+时间（14位）.txt
 */
public class BatFailResult extends FileFathor implements Serializable {
	private static final long serialVersionUID = 1L;
	/**总笔数*/
	public int rowCount;
	/**信息标准类数组*/
	public Vector<BatFailResultChild> child = new Vector<BatFailResultChild>();
	/**本类信息*/
	public String toString(){
		StringBuilder sb = new StringBuilder();
		String str = "\n";
		sb.append("-------------文件首行信息-------------"+str);
		sb.append("rowCount["+rowCount+"]"+str);
		for(BatFailResultChild bcdc : child){
			sb.append("-------------文件具体信息-------------"+str);
			sb.append(bcdc.toString());
		}
		return sb.toString();
	}
}
