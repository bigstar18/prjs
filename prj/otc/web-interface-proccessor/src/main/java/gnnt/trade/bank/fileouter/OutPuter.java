package gnnt.trade.bank.fileouter;

import gnnt.trade.bank.util.Tool;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class OutPuter
{
  public void outputMsg(Vector<String> title, LinkedHashMap<String, Integer> types, ResultSet rs, OutputStream os)
    throws SQLException, IOException
  {
    StringBuilder sb = new StringBuilder();
    String str = ",";
    if ((title != null) && (!title.isEmpty())) {
      for (String top : title) {
        sb.append(top + str);
      }
    }
    output(sb.toString(), os);
    if ((types == null) || (types.isEmpty()) || (rs == null)) {
      return;
    }
    Vector<String> names = getKeys(types);
    try
    {
      while (rs.next())
      {
        sb = new StringBuilder();
        for (String name : names) {
          sb.append(getValue(name, ((Integer)types.get(name)).intValue(), rs) + str);
        }
        output(sb.toString(), os);
      }
    }
    catch (SQLException e)
    {
      throw e;
    }
  }
  
  private String getValue(String name, int type, ResultSet rs)
    throws SQLException
  {
    String result = null;
    try
    {
      if (type == 4) {
        result = rs.getLong(name);
      } else if (type == 8) {
        result = Tool.fmtDouble2(rs.getDouble(name));
      } else if (type == 91) {
        result = Tool.fmtDate(rs.getDate(name));
      } else if (type == 93) {
        result = Tool.fmtTime(rs.getTimestamp(name));
      } else {
        result = rs.getString(name);
      }
    }
    catch (SQLException e)
    {
      System.out.println("出问题的 name:" + name);
      throw e;
    }
    result = Tool.delNull(result);
    result.replaceAll(",", "，");
    if (result.trim().length() <= 0) {
      result = "-";
    }
    return result;
  }
  
  private Vector<String> getKeys(Map<String, Integer> map)
  {
    Vector<String> result = new Vector();
    if ((map == null) || (map.isEmpty())) {
      return result;
    }
    Iterator<String> keys = map.keySet().iterator();
    while (keys.hasNext()) {
      result.add((String)keys.next());
    }
    return result;
  }
  
  private void output(String msg, OutputStream os)
    throws IOException
  {
    if (os == null) {
      return;
    }
    if (msg == null) {
      return;
    }
    try
    {
      msg = msg + "\n";
      os.write(msg.getBytes());
    }
    catch (IOException e)
    {
      throw e;
    }
  }
}
