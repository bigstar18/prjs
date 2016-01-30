package gnnt.MEBS.common.core.sysscheduler;

import java.util.Date;

public abstract interface ISysScheduler
{
  public abstract void loadInitData();
  
  public abstract long getDiffTime();
  
  public abstract void moveHistory(Date paramDate);
}
