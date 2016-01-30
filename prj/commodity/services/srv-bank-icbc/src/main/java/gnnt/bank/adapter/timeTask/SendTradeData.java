package gnnt.bank.adapter.timeTask;

import gnnt.bank.adapter.BankAdapter;
import gnnt.bank.adapter.Factory;
import gnnt.bank.adapter.ICBCBankImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendTradeData
  implements Job
{
  public void execute(JobExecutionContext arg0)
    throws JobExecutionException
  {
    ICBCBankImpl adapter = (ICBCBankImpl)Factory.getInstance().getBankAdapter();
    try
    {
      if ("true".equals(BankAdapter.getConfig("TimeTaskQS")))
        adapter.sendTradeData();
      else
        BankAdapter.log("定时清算任务被关闭");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}