package gnnt.bank.adapter.timeTask;

import gnnt.bank.adapter.BankAdapter;
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

      JobDetail jobDetail1 = 
        new JobDetail("jobDetail-s1", "jobDetailGroup-s1", GetTradeData.class);
      CronTrigger trigger1 = new CronTrigger("cronTrigger1", "triggerGroup1");
      String loginTime = BankAdapter.getConfig("GetTradeDataTime");
      String hour = null;
      String minute = null;
      String second = null;
      String[] timeArr = loginTime.split(":");
      hour = timeArr[0]; minute = timeArr[1]; second = timeArr[2];

      CronExpression cexp1 = null;
      cexp1 = new CronExpression(second + " " + minute + " " + hour + " ? * * ");
      cexp1 = new CronExpression(second + " " + minute + " " + hour + " ? * * ");

      trigger1.setCronExpression(cexp1);
      scheduler.scheduleJob(jobDetail1, trigger1);

      scheduler.start();
      BankAdapter.log("定时任务启动成功!");
    }
    catch (SchedulerException e) {
      BankAdapter.log("定时任务启动失败!");
      throw e;
    }
    catch (ParseException e) {
      BankAdapter.log("定时任务启动失败!");
      throw e;
    }
  }

  public static void main(String[] args)
  {
  }
}