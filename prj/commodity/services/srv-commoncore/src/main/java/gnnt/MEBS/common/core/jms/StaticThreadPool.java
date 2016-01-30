package gnnt.MEBS.common.core.jms;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class StaticThreadPool
{
  private Log logger = LogFactory.getLog(StaticThreadPool.class);
  private static volatile StaticThreadPool instance;
  public static int worker_num = 10;
  private static int taskCounter = 0;
  private static List<Task> taskQueue = Collections.synchronizedList(new LinkedList());
  public PoolWorker[] workers;
  
  private StaticThreadPool()
  {
    this.workers = new PoolWorker[worker_num];
    for (int i = 0; i < this.workers.length; i++) {
      this.workers[i] = new PoolWorker(i);
    }
  }
  
  private StaticThreadPool(int paramInt)
  {
    worker_num = paramInt;
    this.workers = new PoolWorker[worker_num];
    for (int i = 0; i < this.workers.length; i++) {
      this.workers[i] = new PoolWorker(i);
    }
  }
  
  public static synchronized StaticThreadPool getInstance()
  {
    if (instance == null) {
      synchronized (StaticThreadPool.class)
      {
        if (instance == null) {
          instance = new StaticThreadPool();
        }
      }
    }
    return instance;
  }
  
  public void addTask(Task paramTask)
  {
    synchronized (taskQueue)
    {
      if (taskCounter > 2147483646) {
        taskCounter = 0;
      }
      paramTask.setTaskId(++taskCounter);
      paramTask.setSubmitTime(new Date());
      taskQueue.add(paramTask);
      taskQueue.notifyAll();
    }
    this.logger.info("Submit Task<" + paramTask.getTaskId() + ">: " + paramTask.info());
  }
  
  public void batchAddTask(Task[] paramArrayOfTask)
  {
    if ((paramArrayOfTask == null) || (paramArrayOfTask.length == 0)) {
      return;
    }
    synchronized (taskQueue)
    {
      for (int j = 0; j < paramArrayOfTask.length; j++) {
        if (paramArrayOfTask[j] != null)
        {
          paramArrayOfTask[j].setTaskId(++taskCounter);
          paramArrayOfTask[j].setSubmitTime(new Date());
          taskQueue.add(paramArrayOfTask[j]);
        }
      }
      taskQueue.notifyAll();
    }
    for (int i = 0; i < paramArrayOfTask.length; i++) {
      if (paramArrayOfTask[i] != null) {
        this.logger.info("Submit Task<" + paramArrayOfTask[i].getTaskId() + ">: " + paramArrayOfTask[i].info());
      }
    }
  }
  
  public String getInfo()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("\nTask Queue Size:" + taskQueue.size());
    for (int i = 0; i < this.workers.length; i++) {
      localStringBuffer.append("\nWorker " + i + " is " + (this.workers[i].isWaiting() ? "Waiting." : "Running."));
    }
    return localStringBuffer.toString();
  }
  
  public synchronized void destroy()
  {
    for (int i = 0; i < worker_num; i++)
    {
      this.workers[i].stopWorker();
      this.workers[i] = null;
    }
    synchronized (taskQueue)
    {
      taskQueue.notifyAll();
      taskQueue.clear();
    }
  }
  
  public static void main(String[] paramArrayOfString) {}
  
  public void exit()
  {
    destroy();
  }
  
  private class PoolWorker
    extends Thread
  {
    private int index = -1;
    private boolean isRunning = true;
    private boolean isWaiting = true;
    
    public PoolWorker(int paramInt)
    {
      this.index = paramInt;
      start();
    }
    
    public void stopWorker()
    {
      this.isRunning = false;
    }
    
    public boolean isWaiting()
    {
      return this.isWaiting;
    }
    
    public void run()
    {
      while (this.isRunning)
      {
        Task localTask = null;
        synchronized (StaticThreadPool.taskQueue)
        {
          while ((StaticThreadPool.taskQueue.isEmpty()) && (this.isRunning)) {
            try
            {
              StaticThreadPool.taskQueue.wait();
            }
            catch (InterruptedException localInterruptedException)
            {
              localInterruptedException.printStackTrace();
              StaticThreadPool.this.logger.error(localInterruptedException);
            }
          }
          localTask = (Task)StaticThreadPool.taskQueue.remove(0);
        }
        if (localTask != null)
        {
          this.isWaiting = false;
          try
          {
            localTask.setBeginExceuteTime(new Date());
            StaticThreadPool.this.logger.debug("Worker<" + this.index + "> start execute Task<" + localTask.getTaskId() + ">");
            if (localTask.getBeginExceuteTime().getTime() - localTask.getSubmitTime().getTime() > 1000L) {
              StaticThreadPool.this.logger.debug("longer waiting time. " + localTask.info() + ",<" + this.index + ">,time:" + (localTask.getBeginExceuteTime().getTime() - localTask.getSubmitTime().getTime()));
            }
            if (localTask.needExecuteImmediate()) {
              new Thread(localTask).start();
            } else {
              localTask.run();
            }
            localTask.setFinishTime(new Date());
            StaticThreadPool.this.logger.debug("Worker<" + this.index + "> finish task<" + localTask.getTaskId() + ">");
            if (localTask.getFinishTime().getTime() - localTask.getBeginExceuteTime().getTime() > 1000L) {
              StaticThreadPool.this.logger.debug("longer execution time. " + localTask.info() + ",<" + this.index + ">,time:" + (localTask.getFinishTime().getTime() - localTask.getBeginExceuteTime().getTime()));
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            StaticThreadPool.this.logger.error("task<" + localTask.getTaskId() + ">" + "execution exception" + localException.toString());
          }
          this.isWaiting = true;
          localTask = null;
        }
      }
    }
  }
}
