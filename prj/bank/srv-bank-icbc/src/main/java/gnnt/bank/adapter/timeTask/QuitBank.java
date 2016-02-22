package gnnt.bank.adapter.timeTask;

import gnnt.bank.adapter.Factory;
import gnnt.bank.adapter.ICBCBankImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuitBank
  implements Job
{
  public void execute(JobExecutionContext arg0)
    throws JobExecutionException
  {
    ICBCBankImpl adapter = (ICBCBankImpl)Factory.getInstance().getBankAdapter();
    try {
      adapter.quitBank();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}