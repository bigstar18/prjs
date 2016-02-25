package gnnt.MEBS.timebargain.manage.dao.jdbc;

import gnnt.MEBS.timebargain.manage.dao.TariffDAO;
import gnnt.MEBS.timebargain.manage.model.Tariff;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class TariffDAOJdbc
  extends BaseDAOJdbc
  implements TariffDAO
{
  private Log log = LogFactory.getLog(TariffDAOJdbc.class);
  
  public void addTariff(Tariff paramTariff)
  {
    String str = "insert into T_A_tariff(tariffID,commodityID,tariffname,tariffRATE,FEEALGR,FEERATE_B,FEERATE_S,TODAYCLOSEFEERATE_B,TODAYCLOSEFEERATE_S,HISTORYCLOSEFEERATE_B,HISTORYCLOSEFEERATE_S,SETTLEFEEALGR,SETTLEFEERATE_B,SETTLEFEERATE_S,FORCECLOSEFEEALGR,FORCECLOSEFEERATE_B,FORCECLOSEFEERATE_S,CREATETIME,CREATEUSER,MODIFYTIME,MODIFYUSER,BROKERID,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,?,?)";
    Object[] arrayOfObject = { paramTariff.getTariffID(), paramTariff.getCommodityID(), paramTariff.getTariffName(), Double.valueOf(paramTariff.getTariffRate()), Short.valueOf(paramTariff.getFeeAlgr()), Double.valueOf(paramTariff.getFeeRate_B()), Double.valueOf(paramTariff.getFeeRate_S()), Double.valueOf(paramTariff.getTodayCloseFeeRate_B()), Double.valueOf(paramTariff.getTodayCloseFeeRate_S()), Double.valueOf(paramTariff.getHistoryCloseFeeRate_B()), Double.valueOf(paramTariff.getHistoryCloseFeeRate_S()), Short.valueOf(paramTariff.getSettleFeeAlgr()), Double.valueOf(paramTariff.getSettleFeeRate_B()), Double.valueOf(paramTariff.getSettleFeeRate_S()), Long.valueOf(paramTariff.getForceCloseFeeAlgr()), Double.valueOf(paramTariff.getForceCloseFeeRate_B()), Double.valueOf(paramTariff.getForceCloseFeeRate_S()), paramTariff.getCreateTime(), paramTariff.getCreateUser(), paramTariff.getModifyTime(), paramTariff.getModifyUser(), paramTariff.getBrokerID(), Short.valueOf(paramTariff.getStatus()) };
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public void deleteTariffById(String paramString)
  {
    String str = "delete from T_A_Tariff where tariffid='" + paramString + "'";
    getJdbcTemplate().update(str);
  }
  
  public List getTariffList()
  {
    String str = "select distinct TariffID,TariffName,TariffRate,CreateTime,CreateUser,Status,brokerID from T_A_tariff where status=0 order by TariffRate";
    return getJdbcTemplate().queryForList(str);
  }
  
  public void updateTariff(Tariff paramTariff)
  {
    String str = "update T_A_TARIFF set FEEALGR=?,FEERATE_B=?,FEERATE_S=?,TODAYCLOSEFEERATE_B=?,TODAYCLOSEFEERATE_S=?,HISTORYCLOSEFEERATE_B=?,HISTORYCLOSEFEERATE_S=?,SETTLEFEEALGR=?,SETTLEFEERATE_B=?,SETTLEFEERATE_S=?,FORCECLOSEFEEALGR=?,FORCECLOSEFEERATE_B=?,FORCECLOSEFEERATE_S=?,MODIFYTIME=to_date(?,'yyyy-MM-dd HH24:mi:ss'),MODIFYUSER=? where tariffID=? and COMMODITYID=?";
    Object[] arrayOfObject = { Short.valueOf(paramTariff.getFeeAlgr()), Double.valueOf(paramTariff.getFeeRate_B()), Double.valueOf(paramTariff.getFeeRate_S()), Double.valueOf(paramTariff.getTodayCloseFeeRate_B()), Double.valueOf(paramTariff.getTodayCloseFeeRate_S()), Double.valueOf(paramTariff.getHistoryCloseFeeRate_B()), Double.valueOf(paramTariff.getHistoryCloseFeeRate_S()), Short.valueOf(paramTariff.getSettleFeeAlgr()), Double.valueOf(paramTariff.getSettleFeeRate_B()), Double.valueOf(paramTariff.getSettleFeeRate_S()), Long.valueOf(paramTariff.getForceCloseFeeAlgr()), Double.valueOf(paramTariff.getForceCloseFeeRate_B()), Double.valueOf(paramTariff.getForceCloseFeeRate_S()), paramTariff.getModifyTime(), paramTariff.getModifyUser(), paramTariff.getTariffID(), paramTariff.getCommodityID() };
    getJdbcTemplate().update(str, arrayOfObject);
  }
  
  public boolean inuse(String paramString)
  {
    String str = "select count(*) from m_firm where tariffID='" + paramString + "'";
    return getJdbcTemplate().queryForLong(str) > 0L;
  }
  
  public boolean repeat(Tariff paramTariff)
  {
    String str = "select count(*) from T_A_Tariff where tariffID='" + paramTariff.getTariffID() + "'";
    return getJdbcTemplate().queryForLong(str) > 0L;
  }
  
  public List getTariffById(String paramString)
  {
    String str = "select TariffID,decode(t.FEEALGR,1,to_char(t.FEERATE_B * 100, 'fm9999999990.009999') || '%',to_char(t.FEERATE_B, 'fm9999999990.009999')) newFeeRate_B,decode(t.FEEALGR,1,to_char(t.FEERATE_S * 100, 'fm9999999990.009999') || '%',to_char(t.FEERATE_S, 'fm9999999990.009999')) newFeeRate_S,decode(t.FEEALGR,1,to_char(t.TodayCloseFeeRate_B * 100, 'fm9999999990.009999') || '%',to_char(t.TodayCloseFeeRate_B, 'fm9999999990.009999')) newTodayCloseFeeRate_B,decode(t.FEEALGR,1,to_char(t.TodayCloseFeeRate_S * 100, 'fm9999999990.009999') || '%',to_char(t.TodayCloseFeeRate_S, 'fm9999999990.009999')) newTodayCloseFeeRate_S,decode(t.FEEALGR,1,to_char(t.HistoryCloseFeeRate_B * 100, 'fm9999999990.009999') || '%',to_char(t.HistoryCloseFeeRate_B, 'fm9999999990.009999')) newHistoryCloseFeeRate_B,decode(t.FEEALGR,1,to_char(t.HistoryCloseFeeRate_S * 100, 'fm9999999990.009999') || '%',to_char(t.HistoryCloseFeeRate_S, 'fm9999999990.009999')) newHistoryCloseFeeRate_S,decode(t.ForceCloseFeeAlgr,1,to_char(t.ForceCloseFeeRate_B * 100, 'fm9999999990.009999') || '%',to_char(t.ForceCloseFeeRate_B, 'fm9999999990.009999')) newForceCloseFeeRate_B,decode(t.ForceCloseFeeAlgr,1,to_char(t.ForceCloseFeeRate_S * 100, 'fm9999999990.009999') || '%',to_char(t.ForceCloseFeeRate_S, 'fm9999999990.009999')) newForceCloseFeeRate_S,decode(t.SettleFeeAlgr,1,to_char(t.SettleFeeRate_B * 100, 'fm9999999990.009999') || '%',to_char(t.SettleFeeRate_B, 'fm9999999990.009999')) newSettleFeeRate_B,decode(t.SettleFeeAlgr,1,to_char(t.SettleFeeRate_S * 100, 'fm9999999990.009999') || '%',to_char(t.SettleFeeRate_S, 'fm9999999990.009999')) newSettleFeeRate_S,decode(c.FEEALGR,1,to_char(c.FEERATE_B * 100, 'fm9999999990.009999') || '%',to_char(c.FEERATE_B, 'fm9999999990.009999')) oldFeeRate_B,decode(c.FEEALGR,1,to_char(c.FEERATE_S * 100, 'fm9999999990.009999') || '%',to_char(c.FEERATE_S, 'fm9999999990.009999')) oldFeeRate_S,decode(c.FEEALGR,1,to_char(c.TodayCloseFeeRate_B * 100, 'fm9999999990.009999') || '%',to_char(c.TodayCloseFeeRate_B, 'fm9999999990.009999')) oldTodayCloseFeeRate_B,decode(c.FEEALGR,1,to_char(c.TodayCloseFeeRate_S * 100, 'fm9999999990.009999') || '%',to_char(c.TodayCloseFeeRate_S, 'fm9999999990.009999')) oldTodayCloseFeeRate_S,decode(c.FEEALGR,1,to_char(c.HistoryCloseFeeRate_B * 100, 'fm9999999990.009999') || '%',to_char(c.HistoryCloseFeeRate_B, 'fm9999999990.009999')) oldHistoryCloseFeeRate_B,decode(c.FEEALGR,1,to_char(c.HistoryCloseFeeRate_S * 100, 'fm9999999990.009999') || '%',to_char(c.HistoryCloseFeeRate_S, 'fm9999999990.009999')) oldHistoryCloseFeeRate_S,decode(c.ForceCloseFeeAlgr,1,to_char(c.ForceCloseFeeRate_B * 100, 'fm9999999990.009999') || '%',to_char(c.ForceCloseFeeRate_B, 'fm9999999990.009999')) oldForceCloseFeeRate_B,decode(c.ForceCloseFeeAlgr,1,to_char(c.ForceCloseFeeRate_S * 100, 'fm9999999990.009999') || '%',to_char(c.ForceCloseFeeRate_S, 'fm9999999990.009999')) oldForceCloseFeeRate_S,decode(c.SettleFeeAlgr,1,to_char(c.SettleFeeRate_B * 100, 'fm9999999990.009999') || '%',to_char(c.SettleFeeRate_B, 'fm9999999990.009999')) oldSettleFeeRate_B,decode(c.SettleFeeAlgr,1,to_char(c.SettleFeeRate_S * 100, 'fm9999999990.009999') || '%',to_char(c.SettleFeeRate_S, 'fm9999999990.009999')) oldSettleFeeRate_S,t.MODIFYTIME,t.MODIFYUSer,t.TariffName,TariffRate * 100 || '%' TARIFFRATE,c.name,t.commodityID from T_A_Tariff t, T_COMMODITY c where tariffID = '" + paramString + "' and t.commodityid = c.commodityid order by t.commodityid";
    return getJdbcTemplate().queryForList(str);
  }
  
  public Map getTariffByCommodityId(String paramString1, String paramString2)
  {
    String str = "select * from T_A_Tariff where tariffID='" + paramString1 + "' and commodityID='" + paramString2 + "'";
    return getJdbcTemplate().queryForMap(str);
  }
  
  public List getTariffListQuery(String paramString1, String paramString2)
  {
    StringBuffer localStringBuffer = new StringBuffer("");
    localStringBuffer.append("select distinct TariffID,TariffName,TariffRate,CreateTime,CreateUser,Status,brokerID from T_A_tariff where status=" + paramString2);
    if ((paramString1 != null) && (!"".equals(paramString1))) {
      localStringBuffer.append(" and tariffID like'%" + paramString1 + "%'");
    }
    localStringBuffer.append(" order by TariffRate");
    return getJdbcTemplate().queryForList(localStringBuffer.toString());
  }
  
  public List getTariffPage()
  {
    String str = "select distinct TariffID,TariffName,TariffRate,CreateTime,CreateUser,Status,brokerID from T_A_tariff  order by TariffRate";
    return getJdbcTemplate().queryForList(str);
  }
  
  public void deleteCommodity(String paramString1, String paramString2)
  {
    String str = "delete  T_A_tariff  where tariffID=? and commodityID=?";
    Object[] arrayOfObject = { paramString2, paramString1 };
    getJdbcTemplate().update(str, arrayOfObject);
  }
}
