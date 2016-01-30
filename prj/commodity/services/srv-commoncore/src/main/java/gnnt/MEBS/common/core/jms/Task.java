package gnnt.MEBS.common.core.jms;

import java.util.Date;

public abstract class Task
  implements Runnable
{
  private Date generateTime = null;
  private Date submitTime = null;
  private Date beginExceuteTime = null;
  private Date finishTime = null;
  private long taskId;
  
  public void run() {}
  
  public abstract boolean needExecuteImmediate();
  
  public abstract String info();
  
  public Date getGenerateTime()
  {
    return this.generateTime;
  }
  
  public Date getBeginExceuteTime()
  {
    return this.beginExceuteTime;
  }
  
  public void setBeginExceuteTime(Date paramDate)
  {
    this.beginExceuteTime = paramDate;
  }
  
  public Date getFinishTime()
  {
    return this.finishTime;
  }
  
  public void setFinishTime(Date paramDate)
  {
    this.finishTime = paramDate;
  }
  
  public Date getSubmitTime()
  {
    return this.submitTime;
  }
  
  public void setSubmitTime(Date paramDate)
  {
    this.submitTime = paramDate;
  }
  
  public long getTaskId()
  {
    return this.taskId;
  }
  
  public void setTaskId(long paramLong)
  {
    this.taskId = paramLong;
  }
}
