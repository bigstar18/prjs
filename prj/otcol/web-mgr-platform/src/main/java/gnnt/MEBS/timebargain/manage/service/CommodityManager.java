package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.dao.CommodityDAO;
import gnnt.MEBS.timebargain.manage.model.Commodity;
import java.util.List;

public abstract interface CommodityManager
{
  public abstract void setCommodityDAO(CommodityDAO paramCommodityDAO);
  
  public abstract Commodity getCommodityByIdCURorHIS(Commodity paramCommodity);
  
  public abstract Commodity getCommodityById(String paramString);
  
  public abstract Commodity getCommodityIDById(String paramString);
  
  public abstract List getCommoditys(Commodity paramCommodity);
  
  public abstract List getCommoditys(Commodity paramCommodity, String paramString);
  
  public abstract void insertCommodity(Commodity paramCommodity, TariffManager paramTariffManager, String paramString);
  
  public abstract void updateCommodity(Commodity paramCommodity, TariffManager paramTariffManager, String paramString);
  
  public abstract void deleteCommodityById(TariffManager paramTariffManager, String paramString);
  
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
