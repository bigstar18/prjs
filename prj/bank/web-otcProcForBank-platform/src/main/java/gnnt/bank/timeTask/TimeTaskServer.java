package gnnt.bank.timeTask;

import gnnt.bank.platform.util.Tool;
import java.io.PrintStream;
import java.text.ParseException;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class TimeTaskServer
{
  public static void start()
    throws SchedulerException, ParseException
  {
    try
    {
      Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
      


      JobDetail jobDetail1 = new JobDetail("jobDetail-s1", "jobDetailGroup-s1", autoSettlement.class);
      CronTrigger trigger1 = new CronTrigger("cronTrigger1", "triggerGroup1");
      String loginTime = Tool.getConfig("qsTime");
      String hour = null;
      String minute = null;
      String second = null;
      System.out.println(loginTime);
      String[] timeArr = loginTime.split(":");
      hour = timeArr[0];minute = timeArr[1];second = timeArr[2];
      
      CronExpression cexp1 = null;
      if ("true".equalsIgnoreCase(Tool.getConfig("WeekendClearing")))
      {
        cexp1 = new CronExpression(second + " " + minute + " " + hour + " ? * MON-FRI");
        Tool.log("平台定时结算时间：" + hour + ":" + minute + ":" + second + " ? * MON-FRI");
      }
      else
      {
        cexp1 = new CronExpression(second + " " + minute + " " + hour + " ? * TUE-SAT");
        Tool.log("平台定时结算时间：" + hour + ":" + minute + ":" + second + " ? * TUE-SAT");
      }
      trigger1.setCronExpression(cexp1);
      scheduler.scheduleJob(jobDetail1, trigger1);
      
      scheduler.start();
      Tool.log("定时任务启动成功!");
    }
    catch (SchedulerException e)
    {
      Tool.log("定时任务启动失败!");
      throw e;
    }
    catch (ParseException e)
    {
      Tool.log("定时任务启动失败!");
      throw e;
    }
  }
}
