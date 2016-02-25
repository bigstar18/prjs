package gnnt.trade.bank.vo.bankdz.sfz.sent;

import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import gnnt.trade.bank.vo.bankdz.sfz.sent.child.BatCustDzChild;
import java.io.Serializable;
import java.util.Vector;

public class BatCustDz
  extends FileFathor
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected int preferencenum = 1;
  public int rowCount;
  public Vector<BatCustDzChild> child = new Vector();
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("-------------文件首行信息-------------" + str);
    sb.append("rowCount[" + this.rowCount + "]" + str);
    for (BatCustDzChild bcdc : this.child)
    {
      sb.append("-------------文件具体信息-------------" + str);
      sb.append(bcdc.toString());
    }
    return sb.toString();
  }
}
