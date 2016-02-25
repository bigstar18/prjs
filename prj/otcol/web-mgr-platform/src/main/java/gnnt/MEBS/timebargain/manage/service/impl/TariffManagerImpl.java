package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.TariffDAO;
import gnnt.MEBS.timebargain.manage.model.Commodity;
import gnnt.MEBS.timebargain.manage.model.Tariff;
import gnnt.MEBS.timebargain.manage.service.TariffManager;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TariffManagerImpl
  implements TariffManager
{
  private TariffDAO dao;
  
  public String insertTariff(Tariff paramTariff)
  {
    if (!this.dao.repeat(paramTariff))
    {
      double d = paramTariff.getTariffRate();
      for (int i = 0; i < paramTariff.getCommodityList().size(); i++)
      {
        Commodity localCommodity = (Commodity)paramTariff.getCommodityList().get(i);
        paramTariff.setCommodityID(localCommodity.getCommodityID());
        paramTariff.setFeeAlgr(localCommodity.getFeeAlgr().shortValue());
        paramTariff.setFeeRate_B(localCommodity.getFeeRate_B().doubleValue() * d);
        paramTariff.setFeeRate_S(localCommodity.getFeeRate_S().doubleValue() * d);
        paramTariff.setTodayCloseFeeRate_B(localCommodity.getTodayCloseFeeRate_B().doubleValue() * d);
        paramTariff.setTodayCloseFeeRate_S(localCommodity.getTodayCloseFeeRate_S().doubleValue() * d);
        paramTariff.setHistoryCloseFeeRate_B(localCommodity.getHistoryCloseFeeRate_B().doubleValue() * d);
        paramTariff.setHistoryCloseFeeRate_S(localCommodity.getHistoryCloseFeeRate_S().doubleValue() * d);
        paramTariff.setSettleFeeAlgr(localCommodity.getSettleFeeAlgr().shortValue());
        paramTariff.setSettleFeeRate_B(localCommodity.getSettleFeeRate_B().doubleValue());
        paramTariff.setSettleFeeRate_S(localCommodity.getSettleFeeRate_S().doubleValue());
        paramTariff.setForceCloseFeeAlgr(localCommodity.getForceCloseFeeAlgr().shortValue());
        paramTariff.setForceCloseFeeRate_B(localCommodity.getForceCloseFeeRate_B().doubleValue() * d);
        paramTariff.setForceCloseFeeRate_S(localCommodity.getForceCloseFeeRate_S().doubleValue() * d);
        this.dao.addTariff(paramTariff);
      }
      return "操作成功";
    }
    return "套餐重复";
  }
  
  public void deleteTariffById(String paramString)
  {
    if (!this.dao.inuse(paramString)) {
      this.dao.deleteTariffById(paramString);
    } else {
      throw new RuntimeException();
    }
  }
  
  public List getTariffList()
  {
    return this.dao.getTariffList();
  }
  
  public void setTariffDAO(TariffDAO paramTariffDAO)
  {
    this.dao = paramTariffDAO;
  }
  
  public void updateTariff(Tariff paramTariff)
  {
    this.dao.updateTariff(paramTariff);
  }
  
  public List getTariffById(String paramString)
  {
    return this.dao.getTariffById(paramString);
  }
  
  public Map getTariffByCommodityId(String paramString1, String paramString2)
  {
    return this.dao.getTariffByCommodityId(paramString1, paramString2);
  }
  
  public void insertCommodityTariff(Commodity paramCommodity, String paramString)
  {
    List localList = getTariffPage();
    if (localList != null) {
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap = (Map)localList.get(i);
        double d = ((BigDecimal)localMap.get("TariffRate")).doubleValue();
        Tariff localTariff = new Tariff();
        localTariff.setTariffID((String)localMap.get("TariffID"));
        localTariff.setTariffName((String)localMap.get("TariffName"));
        localTariff.setTariffRate(d);
        localTariff.setCreateUser((String)localMap.get("CREATEUSER"));
        localTariff.setStatus(((BigDecimal)localMap.get("Status")).shortValue());
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localTariff.setCreateTime(localSimpleDateFormat.format((Timestamp)localMap.get("CreateTime")));
        String str = localSimpleDateFormat.format(new Date());
        localTariff.setModifyTime(str);
        localTariff.setModifyUser(paramString);
        localTariff.setBrokerID((String)localMap.get("brokerID"));
        localTariff.setCommodityID(paramCommodity.getCommodityID());
        localTariff.setFeeAlgr(paramCommodity.getFeeAlgr().shortValue());
        localTariff.setFeeRate_B(paramCommodity.getFeeRate_B().doubleValue() * d);
        localTariff.setFeeRate_S(paramCommodity.getFeeRate_S().doubleValue() * d);
        localTariff.setTodayCloseFeeRate_B(paramCommodity.getTodayCloseFeeRate_B().doubleValue() * d);
        localTariff.setTodayCloseFeeRate_S(paramCommodity.getTodayCloseFeeRate_S().doubleValue() * d);
        localTariff.setHistoryCloseFeeRate_B(paramCommodity.getHistoryCloseFeeRate_B().doubleValue() * d);
        localTariff.setHistoryCloseFeeRate_S(paramCommodity.getHistoryCloseFeeRate_S().doubleValue() * d);
        localTariff.setSettleFeeAlgr(paramCommodity.getSettleFeeAlgr().shortValue());
        localTariff.setSettleFeeRate_B(paramCommodity.getSettleFeeRate_B().doubleValue());
        localTariff.setSettleFeeRate_S(paramCommodity.getSettleFeeRate_S().doubleValue());
        localTariff.setForceCloseFeeAlgr(paramCommodity.getForceCloseFeeAlgr().shortValue());
        localTariff.setForceCloseFeeRate_B(paramCommodity.getForceCloseFeeRate_B().doubleValue() * d);
        localTariff.setForceCloseFeeRate_S(paramCommodity.getForceCloseFeeRate_S().doubleValue() * d);
        this.dao.addTariff(localTariff);
      }
    }
  }
  
  public void deleteCommodityTariff(String paramString)
  {
    List localList = getTariffPage();
    if (localList != null) {
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap = (Map)localList.get(i);
        this.dao.deleteCommodity(paramString, (String)localMap.get("TariffID"));
      }
    }
  }
  
  public List getTariffListQuery(String paramString1, String paramString2)
  {
    return this.dao.getTariffListQuery(paramString1, paramString2);
  }
  
  public List getTariffPage()
  {
    return this.dao.getTariffPage();
  }
  
  public void updateCommodityTariff(Commodity paramCommodity, String paramString)
  {
    List localList = getTariffPage();
    if (localList != null) {
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap = (Map)localList.get(i);
        double d = ((BigDecimal)localMap.get("TariffRate")).doubleValue();
        Tariff localTariff = new Tariff();
        localTariff.setTariffID((String)localMap.get("TariffID"));
        localTariff.setTariffName((String)localMap.get("TariffName"));
        localTariff.setTariffRate(d);
        localTariff.setCreateUser((String)localMap.get("CREATEUSER"));
        localTariff.setStatus(((BigDecimal)localMap.get("Status")).shortValue());
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localTariff.setCreateTime(localSimpleDateFormat.format((Timestamp)localMap.get("CreateTime")));
        String str = localSimpleDateFormat.format(new Date());
        localTariff.setModifyTime(str);
        localTariff.setModifyUser(paramString);
        localTariff.setBrokerID((String)localMap.get("brokerID"));
        localTariff.setCommodityID(paramCommodity.getCommodityID());
        localTariff.setFeeAlgr(paramCommodity.getFeeAlgr().shortValue());
        localTariff.setFeeRate_B(paramCommodity.getFeeRate_B().doubleValue() * d);
        localTariff.setFeeRate_S(paramCommodity.getFeeRate_S().doubleValue() * d);
        localTariff.setTodayCloseFeeRate_B(paramCommodity.getTodayCloseFeeRate_B().doubleValue() * d);
        localTariff.setTodayCloseFeeRate_S(paramCommodity.getTodayCloseFeeRate_S().doubleValue() * d);
        localTariff.setHistoryCloseFeeRate_B(paramCommodity.getHistoryCloseFeeRate_B().doubleValue() * d);
        localTariff.setHistoryCloseFeeRate_S(paramCommodity.getHistoryCloseFeeRate_S().doubleValue() * d);
        localTariff.setSettleFeeAlgr(paramCommodity.getSettleFeeAlgr().shortValue());
        localTariff.setSettleFeeRate_B(paramCommodity.getSettleFeeRate_B().doubleValue());
        localTariff.setSettleFeeRate_S(paramCommodity.getSettleFeeRate_S().doubleValue());
        localTariff.setForceCloseFeeAlgr(paramCommodity.getForceCloseFeeAlgr().shortValue());
        localTariff.setForceCloseFeeRate_B(paramCommodity.getForceCloseFeeRate_B().doubleValue() * d);
        localTariff.setForceCloseFeeRate_S(paramCommodity.getForceCloseFeeRate_S().doubleValue() * d);
        this.dao.updateTariff(localTariff);
      }
    }
  }
}
