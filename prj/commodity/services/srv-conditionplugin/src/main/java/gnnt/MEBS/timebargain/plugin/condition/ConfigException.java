package gnnt.MEBS.timebargain.plugin.condition;

public class ConfigException
  extends RuntimeException
{
  private static final long serialVersionUID = 3345599038453245478L;
  
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
