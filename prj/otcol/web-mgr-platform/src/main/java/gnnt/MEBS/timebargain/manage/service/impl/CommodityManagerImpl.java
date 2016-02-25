package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.CommodityDAO;
import gnnt.MEBS.timebargain.manage.dao.StatQueryDAO;
import gnnt.MEBS.timebargain.manage.model.Commodity;
import gnnt.MEBS.timebargain.manage.service.CommodityManager;
import gnnt.MEBS.timebargain.manage.service.TariffManager;
import java.util.List;

public class CommodityManagerImpl
  extends BaseManager
  implements CommodityManager
{
  private CommodityDAO dao;
  private StatQueryDAO statQueryDAO;
  
  public void setStatQueryDAO(StatQueryDAO paramStatQueryDAO)
  {
    this.statQueryDAO = paramStatQueryDAO;
  }
  
  public void setCommodityDAO(CommodityDAO paramCommodityDAO)
  {
    this.dao = paramCommodityDAO;
  }
  
  public Commodity getCommodityByIdCURorHIS(Commodity paramCommodity)
  {
    return this.dao.getCommodityByIdCURorHIS(paramCommodity);
  }
  
  public Commodity getCommodityById(String paramString)
  {
    return this.dao.getCommodityById(paramString);
  }
  
  public Commodity getCommodityIDById(String paramString)
  {
    return this.dao.getCommodityIDById(paramString);
  }
  
  public List getCommoditys(Commodity paramCommodity)
  {
    return this.dao.getCommoditys(paramCommodity);
  }
  
  public List getCommoditys(Commodity paramCommodity, String paramString)
  {
    return this.dao.getCommoditys(paramCommodity, paramString);
  }
  
  public void insertCommodity(Commodity paramCommodity, TariffManager paramTariffManager, String paramString)
  {
    this.dao.insertCommodity(paramCommodity);
    paramTariffManager.insertCommodityTariff(paramCommodity, paramString);
  }
  
  public void updateCommodity(Commodity paramCommodity, TariffManager paramTariffManager, String paramString)
  {
    this.dao.updateCommodity(paramCommodity);
    Commodity localCommodity = getCommodityById(paramCommodity.getCommodityID());
    if ((localCommodity.getFeeRate_B() != paramCommodity.getFeeRate_B()) || (localCommodity.getFeeAlgr() != paramCommodity.getFeeAlgr()) || (localCommodity.getFeeRate_S() != paramCommodity.getFeeRate_S()) || (localCommodity.getSettleFeeAlgr() != paramCommodity.getSettleFeeAlgr()) || (localCommodity.getForceCloseFeeAlgr() != paramCommodity.getForceCloseFeeAlgr()) || (localCommodity.getForceCloseFeeRate_B() != paramCommodity.getForceCloseFeeRate_B()) || (localCommodity.getForceCloseFeeRate_S() != paramCommodity.getForceCloseFeeRate_S()) || (localCommodity.getTodayCloseFeeRate_B() != paramCommodity.getTodayCloseFeeRate_B()) || (localCommodity.getTodayCloseFeeRate_S() != localCommodity.getTodayCloseFeeRate_S()) || (localCommodity.getSettleFeeRate_B() != paramCommodity.getSettleFeeRate_B()) || (localCommodity.getSettleFeeRate_S() != paramCommodity.getSettleFeeRate_S()) || (localCommodity.getHistoryCloseFeeRate_B() != paramCommodity.getHistoryCloseFeeRate_B()) || (localCommodity.getHistoryCloseFeeRate_S() != paramCommodity.getHistoryCloseFeeRate_S())) {
      paramTariffManager.updateCommodityTariff(paramCommodity, paramString);
    }
  }
  
  public void deleteCommodityById(TariffManager paramTariffManager, String paramString)
  {
    this.dao.deleteCommodityById(paramString);
    paramTariffManager.deleteCommodityTariff(paramString);
  }
  
  public List getCommoditysHis(Commodity paramCommodity, String paramString)
  {
    return this.dao.getCommoditysHis(paramCommodity, paramString);
  }
  
  public void updateCommodityStatus(Commodity paramCommodity)
  {
    this.dao.updateCommodityStatus(paramCommodity);
  }
  
  public List getCurCommoditys(Commodity paramCommodity, int paramInt1, int paramInt2)
  {
    return this.statQueryDAO.getCurCommoditys(paramCommodity, paramInt1, paramInt2);
  }
  
  public List getHisCommoditys(Commodity paramCommodity, int paramInt1, int paramInt2)
  {
    return this.statQueryDAO.getHisCommoditys(paramCommodity, paramInt1, paramInt2);
  }
  
  public String getSystemStatus()
  {
    return this.dao.getSystemStatus();
  }
  
  public void updateCommodityMargin(Commodity paramCommodity)
  {
    this.dao.updateCommodityMargin(paramCommodity);
  }
  
  public void updateLastPrice(Commodity paramCommodity)
  {
    this.dao.updateLastPrice(paramCommodity);
  }
  
  public List getCommodityList()
  {
    return this.dao.getCommodityList();
  }
  
  public int queryCommodityCount()
  {
    return this.statQueryDAO.queryCommodityCount();
  }
  
  public int queryHisCommodityCount()
  {
    return this.statQueryDAO.queryHisCommodityCount();
  }
}
