package gnnt.MEBS.vendue.server.dao;

public class Count
{
  private static Count singleObject = new Count();
  private int count = 0;
  
  public static Count getSingleInstance()
  {
    return singleObject;
  }
  
  public synchronized void increase()
  {
    this.count += 1;
  }
  
  public synchronized void reduce()
  {
    this.count -= 1;
  }
  
  public synchronized int getCount()
  {
    return this.count;
  }
}
