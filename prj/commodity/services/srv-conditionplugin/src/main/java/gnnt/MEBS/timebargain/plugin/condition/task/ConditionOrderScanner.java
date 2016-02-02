package gnnt.MEBS.timebargain.plugin.condition.task;

public class ConditionOrderScanner
  extends Thread
{
  private Long maxOrderNo;
  
  public void run()
  {
    try
    {
      sleep(3000L);
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
  }
}
