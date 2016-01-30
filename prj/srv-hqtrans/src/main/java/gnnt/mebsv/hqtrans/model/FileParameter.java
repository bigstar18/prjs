package gnnt.mebsv.hqtrans.model;

public class FileParameter
{
  private int timeIntervalCheck;
  private String m_strHistoryDataPath;
  private String m_strLocalCache = "Cache";
  
  public int getTimeIntervalCheck()
  {
    return this.timeIntervalCheck;
  }
  
  public void setTimeIntervalCheck(int paramInt)
  {
    this.timeIntervalCheck = paramInt;
  }
  
  public String getM_strHistoryDataPath()
  {
    return this.m_strHistoryDataPath;
  }
  
  public void setM_strHistoryDataPath(String paramString)
  {
    this.m_strHistoryDataPath = paramString;
  }
  
  public String getM_strLocalCache()
  {
    return this.m_strLocalCache;
  }
  
  public void setM_strLocalCache(String paramString)
  {
    this.m_strLocalCache = paramString;
  }
}
