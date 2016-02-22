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
        new JobDetail("jobDetail-s1", "jobDetailGroup-s1", LoginBank.class);
      CronTrigger trigger1 = new CronTrigger("cronTrigger1", "triggerGroup1");
      String loginTime = BankAdapter.getConfig("loginTime");
      String hour = null;
      String minute = null;
      String second = null;
      String[] timeArr = loginTime.split(":");
      hour = timeArr[0]; minute = timeArr[1]; second = timeArr[2];

      CronExpression cexp1 = null;
      if ("true".equalsIgnoreCase(BankAdapter.getConfig("setsun")))
        cexp1 = new CronExpression(second + " " + minute + " " + hour + " ? * * ");
      else {
        cexp1 = new CronExpression(second + " " + minute + " " + hour + " ? * MON-FRI");
      }
      trigger1.setCronExpression(cexp1);
      scheduler.scheduleJob(jobDetail1, trigger1);

      JobDetail jobDetail2 = 
        new JobDetail("jobDetail-s2", "jobDetailGroup-s1", QuitBank.class);
      CronTrigger trigger2 = new CronTrigger("cronTrigger2", "triggerGroup1");
      String quitTime = BankAdapter.getConfig("quitTime");
      hour = null;
      minute = null;
      second = null;
      timeArr = quitTime.split(":");
      hour = timeArr[0]; minute = timeArr[1]; second = timeArr[2];

      CronExpression cexp2 = null;
      if ("true".equalsIgnoreCase(BankAdapter.getConfig("setsun")))
        cexp2 = new CronExpression(second + " " + minute + " " + hour + " ? * * ");
      else {
        cexp2 = new CronExpression(second + " " + minute + " " + hour + " ? * MON-FRI");
      }
      trigger2.setCronExpression(cexp2);
      scheduler.scheduleJob(jobDetail2, trigger2);

      JobDetail jobDetail3 = 
        new JobDetail("jobDetail-s3", "jobDetailGroup-s1", SendTradeData.class);
      CronTrigger trigger3 = new CronTrigger("cronTrigger3", "triggerGroup1");
      String sendTradeDataTime = BankAdapter.getConfig("sendTradeDataTime");
      hour = null;
      minute = null;
      second = null;
      timeArr = sendTradeDataTime.split(":");
      hour = timeArr[0]; minute = timeArr[1]; second = timeArr[2];

      CronExpression cexp3 = null;
      if ("true".equalsIgnoreCase(BankAdapter.getConfig("setsun")))
        cexp3 = new CronExpression(second + " " + minute + " " + hour + " ? * * ");
      else {
        cexp3 = new CronExpression(second + " " + minute + " " + hour + " ? * MON-FRI");
      }
      trigger3.setCronExpression(cexp3);
      scheduler.scheduleJob(jobDetail3, trigger3);
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
}