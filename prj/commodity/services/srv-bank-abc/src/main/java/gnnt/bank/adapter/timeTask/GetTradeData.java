package gnnt.bank.adapter.timeTask;

import gnnt.bank.adapter.ABCBankImpl;
import gnnt.bank.adapter.BankAdapter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class GetTradeData
  implements Job
{
  public void execute(JobExecutionContext arg0)
    throws JobExecutionException
  {
    BankAdapter.log("定时任务下载明细文件");
    ABCBankImpl abc = (ABCBankImpl)BankAdapter.getInstance();
    String fileName = abc.downDayDataFile(null);

    BankAdapter.log("文件名：" + fileName);
  }
}