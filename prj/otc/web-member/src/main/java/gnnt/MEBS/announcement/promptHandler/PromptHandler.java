package gnnt.MEBS.announcement.promptHandler;

import java.util.Map;

public abstract interface PromptHandler
{
  public abstract int handleRequest(Map paramMap, Long paramLong);
}
