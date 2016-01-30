package gnnt.MEBS.integrated.mgr.progreebar;

public class State
{
  private long readedBytes = 0L;
  private long totalBytes = 0L;
  private int currentItem = 0;
  private int rate = 0;
  
  public long getReadedBytes()
  {
    return this.readedBytes;
  }
  
  public void setReadedBytes(long paramLong)
  {
    this.readedBytes = paramLong;
  }
  
  public long getTotalBytes()
  {
    return this.totalBytes;
  }
  
  public void setTotalBytes(long paramLong)
  {
    this.totalBytes = paramLong;
  }
  
  public int getCurrentItem()
  {
    return this.currentItem;
  }
  
  public void setCurrentItem(int paramInt)
  {
    this.currentItem = paramInt;
  }
  
  public int getRate()
  {
    return this.rate;
  }
  
  public void setRate(int paramInt)
  {
    this.rate = paramInt;
  }
}
