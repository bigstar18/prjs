package cn.com.agree.eteller.generic.nnatp;

public abstract interface INatp
{
  public abstract int init(int paramInt, String paramString1, String paramString2, String paramString3);
  
  public abstract void pack(String paramString1, String paramString2)
    throws Exception;
  
  public abstract void pack(String[] paramArrayOfString1, String[] paramArrayOfString2)
    throws Exception;
  
  public abstract String unpack(String paramString, int paramInt)
    throws Exception;
  
  public abstract String[] unpack(String paramString)
    throws Exception;
  
  public abstract int getRecordCount(String paramString);
  
  public abstract void exchange(String paramString)
    throws Exception;
}
