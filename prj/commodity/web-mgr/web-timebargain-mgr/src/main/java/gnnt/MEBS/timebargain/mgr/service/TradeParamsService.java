package gnnt.MEBS.timebargain.mgr.service;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.statictools.Arith;
import gnnt.MEBS.timebargain.mgr.dao.TradeParamsDao;
import gnnt.MEBS.timebargain.mgr.model.delay.CommoditySettleProp;
import gnnt.MEBS.timebargain.mgr.model.delay.LabelValue;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Breed;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Commodity;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Tariff;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_tradeParamsService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class TradeParamsService
{
  private final transient Log logger = LogFactory.getLog(TradeParamsService.class);

  @Autowired
  @Qualifier("com_tradeParamsDao")
  private TradeParamsDao dao;

  public List executeSelect(String sqlName) { return this.dao.executeSelect(sqlName); }


  public void addCommodity(Commodity commodity, User user)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("enter TradeParamsService's addCommodity");
    }
    List list = new ArrayList();
    Iterator it = executeSelect("findAllTariffs").iterator();
    while (it.hasNext()) {
      Tariff tariff = new Tariff();
      Object[] row = (Object[])it.next();
      tariff.setTariffID((String)row[0]);
      tariff.setTariffName((String)row[1]);
      tariff.setTariffRate((Double)row[2]);
      tariff.setFeeAlgr(commodity.getFeeAlgr());
      tariff.setForceCloseFeeAlgr(commodity.getForceCloseFeeAlgr());

      double tariffRate = tariff.getTariffRate().doubleValue();

      tariff.setFeeRate_B(Double.valueOf(Arith.mul(commodity.getFeeRate_B().doubleValue(), tariffRate)));
      tariff.setFeeRate_S(Double.valueOf(Arith.mul(commodity.getFeeRate_S().doubleValue(), tariffRate)));
      tariff.setForceCloseFeeRate_B(Double.valueOf(Arith.mul(commodity.getForceCloseFeeRate_B().doubleValue(), tariffRate)));
      tariff.setForceCloseFeeRate_S(Double.valueOf(Arith.mul(commodity.getForceCloseFeeRate_S().doubleValue(), tariffRate)));
      tariff.setTodayCloseFeeRate_B(Double.valueOf(Arith.mul(commodity.getTodayCloseFeeRate_B().doubleValue(), tariffRate)));
      tariff.setTodayCloseFeeRate_S(Double.valueOf(Arith.mul(commodity.getTodayCloseFeeRate_S().doubleValue(), tariffRate)));
      tariff.setHistoryCloseFeeRate_B(Double.valueOf(Arith.mul(commodity.getHistoryCloseFeeRate_B().doubleValue(), tariffRate)));
      tariff.setHistoryCloseFeeRate_S(Double.valueOf(Arith.mul(commodity.getHistoryCloseFeeRate_S().doubleValue(), tariffRate)));

      tariff.setCreateUser(user.getName());
      Date date = new Date();
      tariff.setCreateTime(date);
      tariff.setModifyTime(date);
      tariff.setCommodityID(commodity.getCommodityID());
      list.add(tariff);
    }

    this.dao.addCommodity(commodity);
    this.dao.addTariffs(list);
    this.dao.executeFunctionCommodity(commodity.getCommodityID());
  }

  public void deleteCommodity(List<StandardModel> list)
    throws Exception
  {
    for (StandardModel model : list) {
      Commodity commodity = (Commodity)model;
      Object[] params = { commodity.getCommodityID() };
      String sql = "from T_FIRMHOLDSUM where CommodityID=?";
      this.dao.deleleCheck(sql, params, "有商品" + commodity.getName() + "的持仓信息，不能删除！");
      sql = "from T_Trade where CommodityID = ?";
      this.dao.deleleCheck(sql, params, "有商品" + commodity.getName() + "的成交信息，不能删除！");
    }

    this.dao.deleteCommodity(list);
  }

  public void deleteBreed(List<StandardModel> list)
    throws Exception
  {
    for (StandardModel model : list) {
      Breed breed = (Breed)model;
      Object[] params = { breed.getBreedID() };
      String sql = "from T_COMMODITY where BreedID=?";
      this.dao.deleleCheck(sql, params, "品种" + breed.getBreedName() + "还有商品，不能删除！");
    }

    this.dao.getHibernateTemplate().deleteAll(list);
  }

  public List queryTariffSimples(String sql, String tariffID) {
    return this.dao.executeSqlById(sql, tariffID);
  }

  public void addTariffs(List list, String tariffID, String tariffName, User user)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("enter TradeParamsService's addTariffs");
    }
    List tariffList = new ArrayList();
    Iterator it = list.iterator();
    Date date = new Date();
    double tariffRate = Double.parseDouble(tariffID.substring(1)) / new Double(100.0D).doubleValue() + 1.0D;
    while (it.hasNext()) {
      Tariff tariff = new Tariff();
      Commodity commodity = (Commodity)it.next();
      tariff.setTariffID(tariffID);
      tariff.setTariffName("加收" + tariffID.substring(1) + "%");
      tariff.setTariffRate(Double.valueOf(tariffRate));
      tariff.setCommodityID(commodity.getCommodityID());
      tariff.setCreateUser(user.getName());
      tariff.setFeeAlgr(commodity.getFeeAlgr());
      tariff.setFeeRate_B(Double.valueOf(Arith.mul(commodity.getFeeRate_B().doubleValue(), tariffRate)));
      tariff.setFeeRate_S(Double.valueOf(Arith.mul(commodity.getFeeRate_S().doubleValue(), tariffRate)));
      tariff.setForceCloseFeeAlgr(commodity.getForceCloseFeeAlgr());
      tariff.setForceCloseFeeRate_B(Double.valueOf(Arith.mul(commodity.getForceCloseFeeRate_B().doubleValue(), tariffRate)));
      tariff.setForceCloseFeeRate_S(Double.valueOf(Arith.mul(commodity.getForceCloseFeeRate_S().doubleValue(), tariffRate)));
      tariff.setTodayCloseFeeRate_B(Double.valueOf(Arith.mul(commodity.getTodayCloseFeeRate_B().doubleValue(), tariffRate)));
      tariff.setTodayCloseFeeRate_S(Double.valueOf(Arith.mul(commodity.getTodayCloseFeeRate_S().doubleValue(), tariffRate)));
      tariff.setHistoryCloseFeeRate_B(Double.valueOf(Arith.mul(commodity.getHistoryCloseFeeRate_B().doubleValue(), tariffRate)));
      tariff.setHistoryCloseFeeRate_S(Double.valueOf(Arith.mul(commodity.getHistoryCloseFeeRate_S().doubleValue(), tariffRate)));
      tariff.setCreateTime(date);
      tariff.setModifyTime(date);
      tariffList.add(tariff);
    }
    this.dao.addTariffs(tariffList);
  }

  public void updateCommodity(Commodity commodity, List<StandardModel> list, String userName)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("enter TradeParamsService's updateCommodity");
    }
    this.dao.update(commodity);
    updateTariff(commodity, list, userName);
  }

  public void updateTariff(Commodity commodity, List<StandardModel> list, String userName) {
    Iterator it = list.iterator();
    Date date = new Date();
    while (it.hasNext()) {
      Tariff tariff = (Tariff)it.next();
      tariff.setCreateUser(userName);
      double tariffRate = tariff.getTariffRate().doubleValue();
      tariff.setFeeAlgr(commodity.getFeeAlgr());
      tariff.setFeeRate_B(Double.valueOf(Arith.mul(commodity.getFeeRate_B().doubleValue(), tariffRate)));
      tariff.setFeeRate_S(Double.valueOf(Arith.mul(commodity.getFeeRate_S().doubleValue(), tariffRate)));
      tariff.setForceCloseFeeAlgr(commodity.getForceCloseFeeAlgr());
      tariff.setForceCloseFeeRate_B(Double.valueOf(Arith.mul(commodity.getForceCloseFeeRate_B().doubleValue(), tariffRate)));
      tariff.setForceCloseFeeRate_S(Double.valueOf(Arith.mul(commodity.getForceCloseFeeRate_S().doubleValue(), tariffRate)));
      tariff.setTodayCloseFeeRate_B(Double.valueOf(Arith.mul(commodity.getTodayCloseFeeRate_B().doubleValue(), tariffRate)));
      tariff.setTodayCloseFeeRate_S(Double.valueOf(Arith.mul(commodity.getTodayCloseFeeRate_S().doubleValue(), tariffRate)));
      tariff.setHistoryCloseFeeRate_B(Double.valueOf(Arith.mul(commodity.getHistoryCloseFeeRate_B().doubleValue(), tariffRate)));
      tariff.setHistoryCloseFeeRate_S(Double.valueOf(Arith.mul(commodity.getHistoryCloseFeeRate_S().doubleValue(), tariffRate)));
      tariff.setModifyTime(date);
      this.dao.update(tariff);
    }
  }

  public boolean checkTariff(String id)
    throws Exception
  {
    boolean result = true;

    Object[] params = { id };
    String sql = "from t_firm where tariffid = ?";

    int count = this.dao.deleleCheck(sql, params);
    if (count > 0) {
      result = false;
    }

    return result;
  }

  public void commoditySettlePropAdd(String[] settle, String[] middle, Date sysdate)
  {
    List list = new ArrayList();
    if (settle != null) {
      for (String commodityID : settle) {
        CommoditySettleProp temp = new CommoditySettleProp();
        temp.setCommodityID(commodityID);
        temp.setSectionID(new Long("1"));
        temp.setModifyTime(sysdate);
        list.add(temp);
      }
    }
    if (middle != null) {
      for (String commodityID : middle) {
        CommoditySettleProp temp = new CommoditySettleProp();
        temp.setCommodityID(commodityID);
        temp.setSectionID(new Long("2"));
        temp.setModifyTime(sysdate);
        list.add(temp);
      }
    }
    this.dao.commoditySettlePropAdd(list);
  }

  public void add(StandardModel entity)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("enter TradeParamsService's add");
    }
    this.dao.add(entity);
  }

  public void update(StandardModel entity)
  {
    if (this.logger.isDebugEnabled()) {
      this.logger.debug("enter TradeParamsService's update");
    }
    this.dao.update(entity);
  }

  public List getSelectLabelValueByTable(String tableName, String label, String value, String tjStr) throws Exception
  {
    List lst = this.dao.getBmkTj(tableName, tjStr);
    List list = new ArrayList();
    if (lst != null) {
      if (lst.size() > 0) {
        list.add(new LabelValue("", ""));
      }
      for (int i = 0; i < lst.size(); i++) {
        Map map = (Map)lst.get(i);
        list.add(new LabelValue(map.get(label).toString(), map.get(value).toString()));
      }
    }
    return list;
  }

  public Page getPage(PageRequest<?> pageRequest, String entity)
  {
    this.logger.debug("enter getPage");
    return this.dao.getPage(pageRequest, entity);
  }
}