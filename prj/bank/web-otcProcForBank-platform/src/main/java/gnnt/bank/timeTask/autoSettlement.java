package gnnt.bank.timeTask;

import gnnt.bank.platform.contrl.PaltformProcessor;
import gnnt.bank.platform.util.Tool;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class autoSettlement
  implements Job
{
  public void execute(JobExecutionContext arg0)
    throws JobExecutionException
  {
    if ("true".equals(Tool.getConfig("AutoClearing"))) {
      try
      {
        PaltformProcessor pp = new PaltformProcessor();
        Date date = new Date();
        date.setDate(date.getDate() - 1);
        pp.autoSettlement(date);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    } else {
      Tool.log("定时清算任务未开启");
    }
  }
}
