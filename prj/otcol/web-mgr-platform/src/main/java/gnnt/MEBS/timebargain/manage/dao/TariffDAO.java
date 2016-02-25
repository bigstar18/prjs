package gnnt.MEBS.timebargain.manage.dao;

import gnnt.MEBS.timebargain.manage.model.Tariff;
import java.util.List;
import java.util.Map;

public abstract interface TariffDAO
{
  public abstract List getTariffList();
  
  public abstract void addTariff(Tariff paramTariff);
  
  public abstract void deleteTariffById(String paramString);
  
  public abstract List getTariffById(String paramString);
  
  public abstract Map getTariffByCommodityId(String paramString1, String paramString2);
  
  public abstract void updateTariff(Tariff paramTariff);
  
  public abstract boolean repeat(Tariff paramTariff);
  
  public abstract boolean inuse(String paramString);
  
  public abstract List getTariffListQuery(String paramString1, String paramString2);
  
  public abstract List getTariffPage();
  
  public abstract void deleteCommodity(String paramString1, String paramString2);
}
