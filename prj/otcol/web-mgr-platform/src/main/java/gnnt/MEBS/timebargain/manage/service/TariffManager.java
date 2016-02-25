package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.timebargain.manage.dao.TariffDAO;
import gnnt.MEBS.timebargain.manage.model.Commodity;
import gnnt.MEBS.timebargain.manage.model.Tariff;
import java.util.List;
import java.util.Map;

public abstract interface TariffManager
{
  public abstract void setTariffDAO(TariffDAO paramTariffDAO);
  
  public abstract List getTariffPage();
  
  public abstract List getTariffList();
  
  public abstract List getTariffListQuery(String paramString1, String paramString2);
  
  public abstract String insertTariff(Tariff paramTariff);
  
  public abstract List getTariffById(String paramString);
  
  public abstract Map getTariffByCommodityId(String paramString1, String paramString2);
  
  public abstract void deleteTariffById(String paramString);
  
  public abstract void updateTariff(Tariff paramTariff);
  
  public abstract void insertCommodityTariff(Commodity paramCommodity, String paramString);
  
  public abstract void deleteCommodityTariff(String paramString);
  
  public abstract void updateCommodityTariff(Commodity paramCommodity, String paramString);
}
