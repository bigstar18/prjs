package gnnt.trade.bank.vo.bankdz.sfz.resave;

import gnnt.trade.bank.vo.bankdz.sfz.FileFathor;
import gnnt.trade.bank.vo.bankdz.sfz.resave.child.BatCustDzFailChild;
import java.io.Serializable;
import java.util.Vector;

public class BatCustDzFail
  extends FileFathor
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  protected int preferencenum = 1;
  public int rowCount;
  public Vector<BatCustDzFailChild> child = new Vector();
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("-------------文件首行信息-------------" + str);
    sb.append("rowCount[" + this.rowCount + "]" + str);
    for (BatCustDzFailChild bcdc : this.child)
    {
      sb.append("-------------文件具体信息-------------" + str);
      sb.append(bcdc.toString());
    }
    return sb.toString();
  }
}
