package gnnt.MEBS.delivery.command.util;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.PolicySuperior;
import gnnt.MEBS.delivery.model.LogValue;
import gnnt.MEBS.delivery.util.SysData;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommandExecute
{
  private final transient Log logger = LogFactory.getLog(CommandExecute.class);
  
  public String execute(String paramString, Object paramObject, LogValue paramLogValue)
  {
    String str1 = annotateParseTools.getAnnotateParseContext(paramString, "commandbean");
    String str2 = annotateParseTools.getAnnotateParseContext(paramString, "RECEIVEBEAN");
    if ((str1 == null) || (str1.equals(""))) {
      return "内部异常";
    }
    PolicySuperior localPolicySuperior = (PolicySuperior)SysData.getBean("w_policySuperior");
    Information localInformation = new Information();
    localInformation.setObject(paramObject);
    localInformation.setLogValue(paramLogValue);
    this.logger.debug("commandbean:" + str1);
    this.logger.debug("receivebean:" + str2);
    localInformation.setCommandName(str1);
    localInformation.setReceiveName(str2);
    Map localMap = localPolicySuperior.policyExecuteCommand(localInformation);
    String str3 = (String)localMap.get("msg");
    return str3;
  }
}
