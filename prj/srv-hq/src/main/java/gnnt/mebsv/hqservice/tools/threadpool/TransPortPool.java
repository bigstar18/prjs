package gnnt.mebsv.hqservice.tools.threadpool;

import gnnt.MEBS.util.Configuration;
import gnnt.mebsv.hqservice.model.OutPutObj;
import gnnt.mebsv.hqservice.tools.output.TransPortTask;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TransPortPool
{
  private static TransPortPool transPortPool;
  private Properties params;
  private ExecutorService threadPoolExecutor;
  private BlockingQueue<Runnable> cacheQueue = new SynchronousQueue();
  private static int CACHE_SIZE = 0;
  private static int CORE_SIZE = 10;
  private static int MAX_SIZE = 300;
  private static int KEEP_TIME = 60;
  private static int OUT_TIME = 120;
  private RejectedExecutionHandler rejHandler;

  private TransPortPool()
  {
    InitThreadPoolConfig();
    if (CACHE_SIZE != 0)
      this.cacheQueue = new LinkedBlockingDeque(CACHE_SIZE);
    this.rejHandler = new ThreadPoolExecutor.CallerRunsPolicy();
    this.threadPoolExecutor = new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE, KEEP_TIME, TimeUnit.SECONDS, this.cacheQueue, this.rejHandler);
  }

  private void InitThreadPoolConfig()
  {
    try
    {
      this.params = new Configuration().getSection("MEBS.TransPortThreadPool");
      CORE_SIZE = Integer.parseInt(this.params.getProperty("CoreThreadSize"));
      MAX_SIZE = Integer.parseInt(this.params.getProperty("MaxThreadSize"));
      KEEP_TIME = Integer.parseInt(this.params.getProperty("KeepAliveTime"));
      OUT_TIME = Integer.parseInt(this.params.getProperty("OutTime"));
      CACHE_SIZE = Integer.parseInt(this.params.getProperty("CacheTaskeSize"));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static TransPortPool getInstance()
  {
    if (transPortPool == null)
      synchronized (TransPortPool.class)
      {
        if (transPortPool == null)
          transPortPool = new TransPortPool();
      }
    return transPortPool;
  }

  public ExecutorService getThreadPoolExecutor()
  {
    return this.threadPoolExecutor;
  }

  public void setThreadPoolExecutor(ExecutorService paramExecutorService)
  {
    this.threadPoolExecutor = null;
    this.threadPoolExecutor = paramExecutorService;
  }

  public void Excute(OutPutObj paramOutPutObj)
  {
    try
    {
      this.threadPoolExecutor.submit(new TransPortTask(paramOutPutObj.getSocket(), paramOutPutObj.getData())).get(OUT_TIME, TimeUnit.SECONDS);
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
    catch (ExecutionException localExecutionException)
    {
      localExecutionException.printStackTrace();
    }
    catch (TimeoutException localTimeoutException)
    {
      localTimeoutException.printStackTrace();
    }
  }

  public void Excute(Socket paramSocket, byte[] paramArrayOfByte)
  {
    try
    {
      this.threadPoolExecutor.submit(new TransPortTask(paramSocket, paramArrayOfByte)).get(OUT_TIME, TimeUnit.SECONDS);
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
    catch (ExecutionException localExecutionException)
    {
      localExecutionException.printStackTrace();
    }
    catch (TimeoutException localTimeoutException)
    {
      localTimeoutException.printStackTrace();
    }
  }
}