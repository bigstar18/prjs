package gnnt.trade.bank.data.sfz.vo;

import java.io.Serializable;
import java.util.Vector;

public class BatFailResult
  extends FileFathor
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int rowCount;
  public Vector<BatFailResultChild> child = new Vector();
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("-------------文件首行信息-------------" + str);
    sb.append("rowCount[" + this.rowCount + "]" + str);
    for (BatFailResultChild bcdc : this.child)
    {
      sb.append("-------------文件具体信息-------------" + str);
      sb.append(bcdc.toString());
    }
    return sb.toString();
  }
}
