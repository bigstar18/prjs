package gnnt.MEBS.timebargain.server.quotation.config;

public class ConfigException
  extends RuntimeException
{
  public ConfigException() {}
  
  public ConfigException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  public ConfigException(String paramString)
  {
    super(paramString);
  }
  
  public ConfigException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}
