package gnnt.MEBS.verify.handler;

import java.util.Map;

public abstract interface VerifyHandler
{
  public abstract Map<String, Object> handle(String paramString, Map<String, String> paramMap);
}
