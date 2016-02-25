package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.dao.LookupDAO;
import gnnt.MEBS.timebargain.manage.model.Breed;
import java.util.List;

public abstract interface LookupManager
{
  public abstract void setLookupDAO(LookupDAO paramLookupDAO);
  
  public abstract List getAllRoles()
    throws Exception;
  
  public abstract List getAllSection(Breed paramBreed)
    throws Exception;
  
  public abstract List getAllSectionC(Breed paramBreed)
    throws Exception;
  
  public abstract List getBmk(String paramString)
    throws Exception;
  
  public abstract List getModuleFunc()
    throws Exception;
  
  public abstract List getNullBmk(String paramString)
    throws Exception;
  
  public abstract List getSelectLabelValueByTable(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract List getSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception;
  
  public abstract List getSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, short paramShort)
    throws Exception;
  
  public abstract List getSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, String paramString4, short paramShort)
    throws Exception;
  
  public abstract List getNullSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, String paramString4, short paramShort)
    throws Exception;
  
  public abstract List getSelectLabelValueByList(List paramList, String paramString1, String paramString2)
    throws Exception;
  
  public abstract List getNullSelectLabelValueByList(List paramList, String paramString1, String paramString2)
    throws Exception;
  
  public abstract String getMcByTj(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract List getNullSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception;
  
  public abstract List getSelectLabelValueByTableDistinctCommodityID(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception;
}
