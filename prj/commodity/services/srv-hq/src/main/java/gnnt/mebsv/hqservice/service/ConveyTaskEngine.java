package gnnt.mebsv.hqservice.service;

import gnnt.mebsv.hqservice.model.OutPutObj;
import gnnt.mebsv.hqservice.tools.threadpool.TransPortPool;
import java.util.concurrent.BlockingQueue;

public class ConveyTaskEngine extends Thread
{
  TransPortPool poolManager = TransPortPool.getInstance();
  private BlockingQueue<OutPutObj> outPutTask;
  private boolean runFlag = true;

  public ConveyTaskEngine(BlockingQueue<OutPutObj> paramBlockingQueue)
  {
    this.outPutTask = paramBlockingQueue;
  }

  public void run()
  {
    while (this.runFlag)
      try
      {
        OutPutObj localOutPutObj = (OutPutObj)this.outPutTask.take();
        if (localOutPutObj.getData().length > 0)
          this.poolManager.Excute(localOutPutObj);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
  }

  public void shutdown()
  {
    this.runFlag = false;
  }
}