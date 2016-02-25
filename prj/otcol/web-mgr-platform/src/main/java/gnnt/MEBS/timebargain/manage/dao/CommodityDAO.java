package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.Commodity;
import java.util.List;

public abstract interface CommodityDAO
  extends DAO
{
  public abstract Commodity getCommodityByIdCURorHIS(Commodity paramCommodity);
  
  public abstract Commodity getCommodityById(String paramString);
  
  public abstract Commodity getCommodityIDById(String paramString);
  
  public abstract List getCommoditys(Commodity paramCommodity);
  
  public abstract List getCommoditys(Commodity paramCommodity, String paramString);
  
  public abstract void insertCommodity(Commodity paramCommodity);
  
  public abstract void updateCommodity(Commodity paramCommodity);
  
  public abstract void deleteCommodityById(String paramString);
  
  public abstract List getCommoditysHis(Commodity paramCommodity, String paramString);
  
  public abstract void updateCommodityStatus(Commodity paramCommodity);
  
  public abstract List getCurCommoditys(Commodity paramCommodity, int paramInt1, int paramInt2);
  
  public abstract List getHisCommoditys(Commodity paramCommodity, int paramInt1, int paramInt2);
  
  public abstract String getSystemStatus();
  
  public abstract void updateCommodityMargin(Commodity paramCommodity);
  
  public abstract void updateLastPrice(Commodity paramCommodity);
  
  public abstract int queryCommodityCount();
  
  public abstract int queryHisCommodityCount();
  
  public abstract List getCommodityList();
}
