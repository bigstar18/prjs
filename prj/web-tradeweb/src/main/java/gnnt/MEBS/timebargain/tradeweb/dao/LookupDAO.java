package gnnt.MEBS.timebargain.tradeweb.dao;

import java.util.List;

public abstract interface LookupDAO
  extends DAO
{
  public abstract List getBmk(String paramString)
    throws Exception;
  
  public abstract List getBmkTj(String paramString1, String paramString2)
    throws Exception;
  
  public abstract String getMcByTj(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract List getTable(String paramString)
    throws Exception;
}
