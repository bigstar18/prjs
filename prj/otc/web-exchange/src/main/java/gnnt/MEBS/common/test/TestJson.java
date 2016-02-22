package gnnt.MEBS.common.test;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class TestJson
{
  public static void main(String[] args)
  {
    Map<String, Boolean> rightMap = new HashMap();
    rightMap.put("update", Boolean.valueOf(true));
    JSONObject jsonObject = (JSONObject)JSONSerializer.toJSON(rightMap);
    System.out.println(jsonObject);
  }
}
