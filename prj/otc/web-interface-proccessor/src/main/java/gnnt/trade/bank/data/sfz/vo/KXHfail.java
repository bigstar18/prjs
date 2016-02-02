package gnnt.trade.bank.data.sfz.vo;

import java.io.Serializable;
import java.util.Vector;

public class KXHfail
  extends FileFathor
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int rowCount;
  public Vector<KXHfailChild> child = new Vector();
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    String str = "\n";
    sb.append("-------------文件首行信息-------------" + str);
    sb.append("rowCount[" + this.rowCount + "]" + str);
    for (KXHfailChild kc : this.child)
    {
      sb.append("-------------文件具体信息-------------" + str);
      sb.append(kc.toString());
    }
    return sb.toString();
  }
}
