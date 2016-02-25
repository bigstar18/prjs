package gnnt.MEBS.delivery.command;

import java.util.HashMap;
import java.util.Map;

public class PolicySuperiorImp
  extends SuperiorExecute
  implements PolicySuperior
{
  private Translate translate;
  
  public void setTranslate(Translate paramTranslate)
  {
    this.translate = paramTranslate;
  }
  
  public Map<String, Object> policyExecuteCommand(Information paramInformation)
  {
    String str1 = "";
    int i = startExecuteCommand(paramInformation);
    HashMap localHashMap = new HashMap();
    String str2 = this.translate.transResult(i + "");
    localHashMap.put("result", Integer.valueOf(i));
    localHashMap.put("msg", str2);
    return localHashMap;
  }
}
