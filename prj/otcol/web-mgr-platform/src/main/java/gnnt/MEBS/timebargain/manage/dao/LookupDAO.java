package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.Breed;
import java.util.List;

public abstract interface LookupDAO
  extends DAO
{
  public abstract List getRoles()
    throws Exception;
  
  public abstract List getSections(Breed paramBreed);
  
  public abstract List getSectionsC(Breed paramBreed);
  
  public abstract List getBmk(String paramString)
    throws Exception;
  
  public abstract List getModuleFunc()
    throws Exception;
  
  public abstract List getBmkTj(String paramString1, String paramString2)
    throws Exception;
  
  public abstract String getMcByTj(String paramString1, String paramString2, String paramString3)
    throws Exception;
  
  public abstract List getBmkTjDistinctCommodityID(String paramString1, String paramString2);
}
