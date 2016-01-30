package gnnt.MEBS.timebargain.tradeweb.service;

import gnnt.MEBS.timebargain.tradeweb.dao.LookupDAO;
import java.util.List;
import java.util.Map;

public abstract interface LookupManager
{
  public abstract void setLookupDAO(LookupDAO paramLookupDAO);
  
  public abstract List getBmk(String paramString)
    throws Exception;
  
  public abstract List getNullBmk(String paramString)
    throws Exception;
  
  public abstract List getSelectLabelValueByTable(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract List getSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception;
  
  public abstract List getSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, short paramShort)
    throws Exception;
  
  public abstract List getSelectLabelValueByList(List paramList, String paramString1, String paramString2)
    throws Exception;
  
  public abstract List getNullSelectLabelValueByList(List paramList, String paramString1, String paramString2)
    throws Exception;
  
  public abstract String getMcByTj(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract List getTable(String paramString)
    throws Exception;
  
  public abstract Map getMapByID(String paramString1, String paramString2)
    throws Exception;
}
