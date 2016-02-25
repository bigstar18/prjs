package gnnt.MEBS.delivery.command;

import java.util.Map;

public abstract interface PolicySuperior
  extends Superior
{
  public abstract Map<String, Object> policyExecuteCommand(Information paramInformation);
}
