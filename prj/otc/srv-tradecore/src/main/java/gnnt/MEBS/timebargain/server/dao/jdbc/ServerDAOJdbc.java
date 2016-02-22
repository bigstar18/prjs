package gnnt.MEBS.timebargain.server.dao.jdbc;

import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.CommdityPriceProtect;
import gnnt.MEBS.timebargain.server.model.Commodity;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.ExchageRate;
import gnnt.MEBS.timebargain.server.model.HQServerInfo;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.NotTradeDay;
import gnnt.MEBS.timebargain.server.model.SysLog;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.TradeTime;
import gnnt.MEBS.timebargain.server.util.Arith;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class ServerDAOJdbc
  extends BaseDAOJdbc
  implements ServerDAO
{
  private Log log = LogFactory.getLog(getClass());
  
  public List<Commodity> getCommodityList()
  {
    return getCommodityListByID(null);
  }
  
  public List<Commodity> getCommodityListByID(String commodityID)
  {
    StringBuffer sb = new StringBuffer(
      "select a.* from T_Commodity a where a.Status= 1 ");
    if ((commodityID != null) && (!commodityID.equals(""))) {
      sb.append(" and a.CommodityID='").append(commodityID).append("'");
    }
    this.log.debug("sql:" + sb.toString());
    List lst = getJdbcTemplate().queryForList(sb.toString());
    
    return getCommodityList(lst);
  }
  
  public List<Commodity> getCommodityList(Integer sectionID)
  {
    StringBuffer sb = new StringBuffer(
      "select a.* from T_Commodity a ,T_A_CommodityTradeProp b where a.Status = 1 and a.CommodityID=b.CommodityID(+) ");
    Object[] params = (Object[])null;
    List paramList = new ArrayList();
    if (sectionID != null)
    {
      sb.append(" and b.SectionID=?");
      paramList.add(sectionID);
    }
    params = paramList.toArray();
    this.log.debug("sql:" + sb.toString());
    List lst = getJdbcTemplate().queryForList(sb.toString(), params);
    
    return getCommodityList(lst);
  }
  
  private List<Commodity> getCommodityList(List commodityList)
  {
    List cmdtyList = new ArrayList();
    for (int i = 0; i < commodityList.size(); i++)
    {
      Commodity m = new Commodity();
      Map map = (Map)commodityList.get(i);
      m.setCommodityID((String)map.get("CommodityID"));
      m.setName((String)map.get("Name"));
      m.setStatus(((BigDecimal)map.get("Status")).shortValue());
      m.setTradeStatus(((String)map.get("TradeStatus")).charAt(0));
      m.setContractFactor(((BigDecimal)map.get("ContractFactor"))
        .doubleValue());
      m.setMinHQMove(((BigDecimal)map.get("MinHQMove")).doubleValue());
      m.setMinPriceMove(((BigDecimal)map.get("MinPriceMove"))
        .doubleValue());
      m.setStepMove(((BigDecimal)map.get("StepMove")).doubleValue());
      m.setLastPrice(((BigDecimal)map.get("LastPrice")).doubleValue());
      m.setSpreadAlgr(((BigDecimal)map.get("SpreadAlgr")).shortValue());
      double up = ((BigDecimal)map.get("SpreadUpLmt")).doubleValue();
      double down = ((BigDecimal)map.get("SpreadDownLmt")).doubleValue();
      if (m.getSpreadAlgr() == 1)
      {
        up = Math.round(Arith.mul(m.getLastPrice(), up) / 
          m.getMinPriceMove()) * 
          m.getMinPriceMove();
        up = Arith.add(m.getLastPrice(), up);
        down = Math.round(Arith.mul(m.getLastPrice(), down) / 
          m.getMinPriceMove()) * 
          m.getMinPriceMove();
        down = Arith.sub(m.getLastPrice(), down);
      }
      else if (m.getSpreadAlgr() == 2)
      {
        up = Arith.add(m.getLastPrice(), up);
        down = Arith.sub(m.getLastPrice(), down);
      }
      m.setSpreadUpLmt(up);
      m.setSpreadDownLmt(down);
      m.setMarketDate((Date)map.get("MarketDate"));
      m.setDisplayNum(((BigDecimal)map.get("DisplayNum")).intValue());
      String pauseType = (String)map.get("PauseType");
      if ((pauseType != null) && (pauseType.length() > 0)) {
        m.setPauseType(pauseType.toCharArray()[0]);
      }
      cmdtyList.add(m);
    }
    return cmdtyList;
  }
  
  public List<TradeTime> getTradeTimes()
  {
    List tradeTimeList = new ArrayList();
    String sql = "select * from T_A_TradeTime order by SectionID";
    this.log.debug("sql:" + sql);
    List lst = getJdbcTemplate().queryForList(sql);
    for (int i = 0; i < lst.size(); i++)
    {
      Map map = (Map)lst.get(i);
      TradeTime tradeTime = new TradeTime();
      tradeTime.setSectionID(new Integer(
        ((BigDecimal)map.get("SectionID")).intValue()));
      tradeTime.setName((String)map.get("Name"));
      tradeTime.setStartTime((String)map.get("StartTime"));
      tradeTime.setEndTime((String)map.get("EndTime"));
      tradeTime.setStatus(new Short(((BigDecimal)map.get("Status"))
        .shortValue()));
      tradeTime.setGatherBid(new Short(
        ((BigDecimal)map.get("GatherBid")).shortValue()));
      tradeTime.setBidStartTime((String)map.get("BidStartTime"));
      tradeTime.setBidEndTime((String)map.get("BidEndTime"));
      tradeTime.setModifyTime((Date)map.get("ModifyTime"));
      tradeTimeList.add(tradeTime);
    }
    return tradeTimeList;
  }
  
  public Market getMarket()
  {
    Market m = null;
    String sql = "select * from T_A_Market ";
    this.log.debug("sql:" + sql);
    List lst = getJdbcTemplate().queryForList(sql);
    if ((lst != null) && (lst.size() > 0))
    {
      Map map = (Map)lst.get(0);
      m = new Market();
      m.setRunMode(new Short(((BigDecimal)map.get("RunMode"))
        .shortValue()));
      m.setClearRunMode(new Short(((BigDecimal)map.get("ClearMode"))
        .shortValue()));
      m.setInitPreSecs(((BigDecimal)map.get("InitPreSecs")).intValue());
      m.setClearDelaySecs(((BigDecimal)map.get("ClearDelaySecs"))
        .intValue());
    }
    return m;
  }
  
  public Map<String, CommdityPriceProtect> getCommdityPriceProtect()
  {
    Map<String, CommdityPriceProtect> commdityPriceProtectMap = new HashMap();
    StringBuffer sb = new StringBuffer();
    
    sb
      .append("select a.*  from t_commditypriceprotect a, t_commodity b where a.commodityid = b.commodityid   and b.status = 1   and (b.tradestatus = 'N' or (b.tradestatus='P' and b.pausetype='S'))");
    




    List lst = getJdbcTemplate().queryForList(sb.toString());
    for (int i = 0; i < lst.size(); i++)
    {
      CommdityPriceProtect commdityPriceProtect = new CommdityPriceProtect();
      Map map = (Map)lst.get(i);
      String commodityID = (String)map.get("CommodityID");
      commdityPriceProtect.setCommodityID(commodityID);
      commdityPriceProtect.setScreeningPricePoint(
        ((BigDecimal)map.get("ScreeningPricePoint")).doubleValue());
      commdityPriceProtect.setTimeoutInterval(
        ((BigDecimal)map.get("TimeoutInterval")).longValue());
      commdityPriceProtectMap.put(commodityID, commdityPriceProtect);
    }
    return commdityPriceProtectMap;
  }
  
  public NotTradeDay getNotTradeDay()
  {
    NotTradeDay notTradeDay = null;
    String sql = "select * from T_A_NotTradeDay";
    this.log.debug("sql:" + sql);
    List lst = getJdbcTemplate().queryForList(sql);
    if ((lst != null) && (lst.size() > 0))
    {
      Map map = (Map)lst.get(0);
      notTradeDay = new NotTradeDay();
      notTradeDay.setID(new Integer(((BigDecimal)map.get("ID"))
        .intValue()));
      notTradeDay.setModifyTime((Date)map.get("ModifyTime"));
      
      String week = (String)map.get("Week");
      if (!StringUtils.isEmpty(week))
      {
        String[] weekArr = week.split(",");
        List weekList = new ArrayList();
        for (int k = 0; k < weekArr.length; k++) {
          weekList.add(weekArr[k]);
        }
        notTradeDay.setWeekList(weekList);
      }
      String day = (String)map.get("Day");
      if (!StringUtils.isEmpty(day))
      {
        String[] dayArr = day.split(",");
        List dayList = new ArrayList();
        for (int k = 0; k < dayArr.length; k++) {
          dayList.add(dayArr[k]);
        }
        notTradeDay.setDayList(dayList);
      }
    }
    return notTradeDay;
  }
  
  public SystemStatus getSystemStatus()
  {
    SystemStatus systemStatus = null;
    String sql = "select * from T_SystemStatus";
    this.log.debug("sql:" + sql);
    List lst = getJdbcTemplate().queryForList(sql);
    if ((lst != null) && (lst.size() > 0))
    {
      Map map = (Map)lst.get(0);
      systemStatus = new SystemStatus();
      systemStatus.setTradeDate((Date)map.get("TradeDate"));
      systemStatus.setNextTradeDate((Date)map.get("NextTradeDate"));
      systemStatus.setClearDate((Date)map.get("EndDate"));
      systemStatus.setStatus(((BigDecimal)map.get("Status")).intValue());
      systemStatus.setSectionID(map.get("SectionID") == null ? null : 
        new Integer(((BigDecimal)map.get("SectionID"))
        .intValue()));
      systemStatus.setNote((String)map.get("Note"));
      systemStatus.setRecoverTime((String)map.get("RecoverTime"));
      String pauseType = (String)map.get("PauseType");
      if ((pauseType != null) && (pauseType.length() > 0)) {
        systemStatus.setPauseType(pauseType.toCharArray()[0]);
      }
    }
    return systemStatus;
  }
  
  public void updateSystemStatus(SystemStatus systemStatus)
  {
    String sql = "update T_SystemStatus set TradeDate=?,NextTradeDate=?,EndDate=?,Status=?,SectionID=?,Note=?,RecoverTime=?,PauseType=? ";
    Object[] params = { systemStatus.getTradeDate(), 
      systemStatus.getNextTradeDate(), systemStatus.getClearDate(), 
      new Integer(systemStatus.getStatus()), 
      systemStatus.getSectionID(), systemStatus.getNote(), 
      systemStatus.getRecoverTime(), 
      String.valueOf(systemStatus.getPauseType()) };
    int[] types = { 91, 91, 91, 
      5, 4, 12, 12, 
      12 };
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params, types);
  }
  
  public void updateSystemRecoverTime(String RecoverTime)
  {
    String sql = "update T_SystemStatus set RecoverTime=? ";
    Object[] params = { RecoverTime };
    int[] types = { 12 };
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params, types);
  }
  
  public void updateCommodityStatusByS(String commodityID, char tradeStatus)
  {
    String sql = "update t_commodity set TradeStatus=?,PauseType='S' where commodityID=? ";
    Object[] params = { String.valueOf(tradeStatus), 
      commodityID };
    int[] types = { 12, 12 };
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params, types);
  }
  
  public void updateNextTradeDate(Date nextTradeDate)
  {
    String sql = "update T_SystemStatus set NextTradeDate=? ";
    Object[] params = { nextTradeDate };
    int[] types = { 91 };
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params, types);
  }
  
  public void insertSysLog(SysLog sysLog)
  {
    String sql = "insert into T_SysLog(ID,UserID,Action,CreateTime,Note) values(SEQ_T_SysLog.Nextval,?,?,sysdate,?)";
    Object[] params = { sysLog.getUserID(), 
      sysLog.getAction(), sysLog.getNote() };
    int[] types = { 12, 12, 12 };
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params, types);
  }
  
  public Date getCurDbDate()
  {
    String sql = "select sysdate from dual";
    
    this.log.debug("sql: " + sql);
    
    return (Date)getJdbcTemplate().queryForObject(sql, Date.class);
  }
  
  public int getTradeSecCount()
  {
    String sql = "select count(*) from T_A_TradeTime";
    this.log.debug("sql: " + sql);
    return getJdbcTemplate().queryForInt(sql);
  }
  
  public int getComtyCount()
  {
    String sql = "select count(*) from T_Commodity where status=1 ";
    this.log.debug("sql: " + sql);
    return getJdbcTemplate().queryForInt(sql);
  }
  
  public Consigner getConsigner(String consignerID)
  {
    String sql = "select * from T_Consigner where consignerID=? ";
    this.log.debug("sql:" + sql);
    
    RowMapper mapper = new RowMapper()
    {
      public Object mapRow(ResultSet rs, int rowNum)
        throws SQLException
      {
        Consigner consigner = new Consigner();
        String consignerID = rs.getString("ConsignerID");
        ServerDAOJdbc.this.log.debug("consignerID:" + consignerID);
        consigner.setConsignerID(consignerID);
        consigner.setName(rs.getString("Name"));
        consigner.setType(rs.getShort("Type"));
        consigner.setStatus(rs.getShort("Status"));
        String operateFirm = rs.getString("OperateFirm");
        if (!StringUtils.isEmpty(operateFirm))
        {
          String[] operateFirmArr = operateFirm.split(",");
          List ocList = new ArrayList();
          for (int k = 0; k < operateFirmArr.length; k++) {
            ocList.add(operateFirmArr[k]);
          }
          consigner.setOperateFirmList(ocList);
        }
        return consigner;
      }
    };
    return (Consigner)getJdbcTemplate().queryForObject(sql, 
      new Object[] { consignerID }, mapper);
  }
  
  public Date getLastTradeDate()
  {
    String sql = "select max(TradeDate) from T_SYSTEMSTATUS_H";
    
    this.log.debug("sql: " + sql);
    
    return (Date)getJdbcTemplate().queryForObject(sql, Date.class);
  }
  
  public Date getLastClearDate()
  {
    String sql = "select max(EndDate) from T_SYSTEMSTATUS_H";
    
    this.log.debug("sql: " + sql);
    
    return (Date)getJdbcTemplate().queryForObject(sql, Date.class);
  }
  
  public Map getDaySectionMap()
  {
    Map retMap = new HashMap();
    List weekLst = getJdbcTemplate()
      .queryForList(
      "select distinct WeekDay from T_A_DaySection order by WeekDay ");
    if ((weekLst != null) && (weekLst.size() > 0)) {
      for (int i = 0; i < weekLst.size(); i++)
      {
        Map map = (Map)weekLst.get(i);
        int weekDay = ((BigDecimal)map.get("WeekDay")).shortValue();
        String sql = "select * from T_A_DaySection where WeekDay=? order by SectionID ";
        Map sectionMap = new HashMap();
        List lst = getJdbcTemplate().queryForList(sql, 
          new Object[] { new Integer(weekDay) });
        if ((lst != null) && (lst.size() > 0)) {
          for (int j = 0; j < lst.size(); j++)
          {
            Map secMap = (Map)lst.get(j);
            int sectionID = ((BigDecimal)secMap.get("SectionID"))
              .intValue();
            short status = ((BigDecimal)secMap.get("Status"))
              .shortValue();
            sectionMap.put(Integer.valueOf(sectionID), Short.valueOf(status));
          }
        }
        retMap.put(Integer.valueOf(weekDay), sectionMap);
      }
    }
    return retMap;
  }
  
  public void updateTradeSectionDateStatus(TradeTime tradeTime)
  {
    String sql = "update T_A_TradeTime set StartDate=?,EndDate=?,BidStartDate=?,BidEndDate=?,Status=? where SectionID=? ";
    Object[] params = { tradeTime.getStartDate(), 
      tradeTime.getEndDate(), tradeTime.getBidStartDate(), 
      tradeTime.getBidEndDate(), tradeTime.getStatus(), 
      tradeTime.getSectionID() };
    int[] types = { 12, 12, 12, 
      12, 5, 4 };
    this.log.debug("sql: " + sql);
    for (int i = 0; i < params.length; i++) {
      this.log.debug("params[" + i + "]: " + params[i]);
    }
    getJdbcTemplate().update(sql, params, types);
  }
  
  public List<HQServerInfo> getHQServerInfoList()
  {
    List<HQServerInfo> hqList = new ArrayList();
    String sql = "select * from T_Q_HQServerInfo order by serverRank";
    this.log.debug("sql:" + sql);
    List lst = getJdbcTemplate().queryForList(sql);
    for (int i = 0; i < lst.size(); i++)
    {
      Map map = (Map)lst.get(i);
      try
      {
        HQServerInfo hQServerInfo = new HQServerInfo(((BigDecimal)map.get("serverId")).intValue(), 
          (String)map.get("ServerName"), (String)map.get("ServerAddr"), 
          ((BigDecimal)map.get("ServerPort")).intValue(), 
          ((BigDecimal)map.get("rmiPort")).intValue(), ((BigDecimal)map.get("serverRank")).intValue());
        hqList.add(hQServerInfo);
      }
      catch (IllegalArgumentException ie)
      {
        ie.printStackTrace();
      }
    }
    return hqList;
  }
  
  public List<ExchageRate> getExchageRates()
  {
    List<ExchageRate> erList = new ArrayList();
    String sql = "select * from T_C_ExchageRate a,t_Commodity b where b.status=1 and a.commodityid=b.commodityid ";
    this.log.debug("sql:" + sql);
    List lst = getJdbcTemplate().queryForList(sql);
    for (int i = 0; i < lst.size(); i++)
    {
      Map map = (Map)lst.get(i);
      ExchageRate m = new ExchageRate();
      m.setCommodityID((String)map.get("CommodityID"));
      m.setInCommodityID((String)map.get("InCommodityID"));
      m.setQuoteRate(Double.valueOf(((BigDecimal)map.get("QuoteRate")).doubleValue()));
      m.setQuoteExchangeRate(
        Double.valueOf(((BigDecimal)map.get("QuoteExchangeRate")).doubleValue()));
      m.setClearExchageRate(
        Double.valueOf(((BigDecimal)map.get("ClearExchageRate")).doubleValue()));
      m.setQuoteAgio(Double.valueOf(((BigDecimal)map.get("QuoteAgio")).doubleValue()));
      erList.add(m);
    }
    return erList;
  }
  
  public long getDelayTradeTime()
  {
    String sql = "select max(delayTradeTime) delayTradeTime from t_c_delaytrade_rt ";
    return getJdbcTemplate().queryForLong(sql);
  }
  
  public void updateHQServer(int serverId)
  {
    UpdateHQServerProcedure sfunc = new UpdateHQServerProcedure(
      getDataSource());
    Map inputs = new HashMap();
    inputs.put("p_serverId", Integer.valueOf(serverId));
    
    sfunc.execute(inputs);
  }
  
  private class UpdateHQServerProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "SP_T_updateHQServer";
    
    public UpdateHQServerProcedure(DataSource ds)
    {
      super("SP_T_updateHQServer");
      declareParameter(new SqlParameter("p_serverId", 4));
      compile();
    }
    
    public Map execute(Map map)
    {
      return super.execute(map);
    }
  }
}
