package gnnt.MEBS.timebargain.server;

import java.io.Serializable;
import java.text.ParseException;
import org.springframework.scheduling.quartz.CronTriggerBean;

public class InitializingCronTrigger
  extends CronTriggerBean
  implements Serializable
{
  private static final long serialVersionUID = 3744215101012986199L;
  
  public InitializingCronTrigger()
    throws ParseException
  {
    String cronExpression = getDefaultCronExpression();
    
    setCronExpression(cronExpression);
  }
  
  private String getDefaultCronExpression()
  {
    return "0 15 10 * * ? 2099";
  }
}
