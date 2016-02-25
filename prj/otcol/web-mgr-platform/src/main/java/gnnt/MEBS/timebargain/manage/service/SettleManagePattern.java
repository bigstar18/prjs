package gnnt.MEBS.timebargain.manage.service;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.util.SysData;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SettleManagePattern
{
  private static final transient Log logger = LogFactory.getLog(SettleManagePattern.class);
  public static Object LOCK = new Object();
  
  public static List getSettles(String filter)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select * from T_SettleMatch where 1=1  " + filter + " order by matchId desc";
    List result = dao.queryBySQL(sql, null);
    return result;
  }
  
  public static List getSettlesNew(String filter)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select distinct (s.matchid) a, s.*  from T_SettleMatch s, t_commodity t1 where 1 = 1  " + 
    
      filter + 
      " and s.commodityid = t1.commodityid " + 
      " and s.matchid like 'ATS%' " + 
      " union select distinct (s.matchid) a, s.* " + 
      " from T_SettleMatch s, T_SettleCommodity t " + 
      " where 1=1  " + filter + 
      "and t.commodityId = s.commodityId " + 
      " and t.SettleWay = 0 " + 
      "order by a desc";
    List result = dao.queryBySQL(sql, null);
    return result;
  }
  
  public static Map getSettle(String id)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select * from T_SettleMatch where MatchID='" + id + "'";
    List list = dao.queryBySQL(sql, null);
    Map map = null;
    if ((list != null) && (list.size() > 0)) {
      map = (Map)list.get(0);
    }
    return map;
  }
  
  public static List getCommoditys()
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select distinct(commodityid) commodityid,settleprocessdate from t_settleholdposition where settletype<>2 order by settleprocessdate desc,commodityid desc";
    
    List result = dao.queryBySQL(sql, null);
    return result;
  }
  
  public static List getCommoditysNew()
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select distinct(s.commodityid) commodityid,s.settleprocessdate from t_settleholdposition s,T_SettleCommodity t where s.commodityId=t.commodityId and t.SettleWay=0 and s.settletype<>2 order by settleprocessdate desc,s.commodityid desc";
    List result = dao.queryBySQL(sql, null);
    return result;
  }
  
  public static List getBuyFirmIds(String commodityid)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select distinct(FirmID) bFirmId from t_settleholdposition t where settletype<>2 and BS_Flag=1 and commodityid='" + 
      commodityid + "' " + 
      
      "and (select nvl(sum(HoldQty+GageQty),0) bAmount from t_settleholdposition where settletype<>2 and " + 
      "BS_Flag=1 and commodityid='" + commodityid + "' and firmId= t.firmId) " + 
      
      " > " + 
      
      "(select nvl(sum(Quantity),0) bSettleAmount from T_SettleMatch where matchid not like 'A%' and CommodityID='" + commodityid + "' " + 
      "and FirmID_B= t.firmId and Status>=0) ";
    
    sql = sql + "order by FirmID";
    List result = dao.queryBySQL(sql, null);
    return result;
  }
  
  public static List getSellFirmIds(String commodityid)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select distinct(FirmID) sFirmId from t_settleholdposition t where settletype<>2 and BS_Flag=2 and commodityid='" + 
      commodityid + "' " + 
      
      "and (select nvl(sum(HoldQty+GageQty),0) sAmount from t_settleholdposition where settletype<>2 and " + 
      "BS_Flag=2 and commodityid='" + commodityid + "' and firmId= t.firmId ) " + 
      
      " > " + 
      
      "(select nvl(sum(Quantity),0) sSettleAmount from T_SettleMatch where matchid not like 'A%' and CommodityID='" + commodityid + "' " + 
      "and FirmID_S=t.firmId and Status>=0 ) ";
    
    sql = sql + "order by FirmID";
    List result = dao.queryBySQL(sql, null);
    return result;
  }
  
  public static List getRegStocks(String commodityid, String sellFirmId)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select t.breedid from t_settlecommodity t where t.commodityid='" + commodityid + "' and rownum=1";
    logger.debug(sql);
    int breedid = dao.queryForInt(sql, null);
    sql = "select t.regstockid from s_regstock t where t.breedid='" + breedid + "' and t.firmid='" + sellFirmId + "' and t.weight-t.frozenweight>0 and type <> 3 order by createTime desc";
    logger.debug(sql);
    List result = dao.queryBySQL(sql, null);
    return result;
  }
  
  public static double checkInsertSettle(String commodityid, String bFirmId, String sFirmId, String regStockID, long settleAmount, int type)
  {
    double result = 0.0D;
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select nvl(sum(HoldQty+GageQty),0) sAmount,nvl(max(SettlePrice),0) SettlePrice from t_settleholdposition where settletype<>2 and BS_Flag=2 and commodityid='" + 
      commodityid + "' and firmId='" + sFirmId + "'";
    logger.debug(sql);
    List list = dao.queryBySQL(sql, null);
    double sAmount = 0.0D;
    double bAmount = 0.0D;
    double SettlePrice_b = 0.0D;
    double SettlePrice_s = 0.0D;
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      sAmount = ((BigDecimal)map.get("SAMOUNT")).doubleValue();
      SettlePrice_s = ((BigDecimal)map.get("SETTLEPRICE")).doubleValue();
    }
    sql = 
      "select nvl(sum(HoldQty+GageQty),0) bAmount,nvl(max(SettlePrice),0) SettlePrice from t_settleholdposition where settletype<>2 and BS_Flag=1 and commodityid='" + commodityid + "' and firmId='" + bFirmId + "'";
    logger.debug(sql);
    list = dao.queryBySQL(sql, null);
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      bAmount = ((BigDecimal)map.get("BAMOUNT")).doubleValue();
      SettlePrice_b = ((BigDecimal)map.get("SETTLEPRICE")).doubleValue();
    }
    sql = "select nvl(sum(Quantity),0) bSettleAmount from T_SettleMatch where matchid not like 'A%' and CommodityID='" + commodityid + "' and FirmID_B='" + bFirmId + "' and Status>=0";
    logger.debug(sql);
    list = dao.queryBySQL(sql, null);
    double bSettleAmount = 0.0D;
    double sSettleAmount = 0.0D;
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      bSettleAmount = ((BigDecimal)map.get("BSETTLEAMOUNT")).doubleValue();
    }
    sql = "select nvl(sum(Quantity),0) sSettleAmount from T_SettleMatch where matchid not like 'A%' and CommodityID='" + commodityid + "' and FirmID_S='" + sFirmId + "' and Status>=0";
    logger.debug(sql);
    list = dao.queryBySQL(sql, null);
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      sSettleAmount = ((BigDecimal)map.get("SSETTLEAMOUNT")).doubleValue();
    }
    logger.debug("bAmount:" + bAmount);
    logger.debug("sAmount:" + sAmount);
    logger.debug("bSettleAmount:" + bSettleAmount);
    logger.debug("sSettleAmount:" + sSettleAmount);
    
    double ContractFactor = 0.0D;
    sql = "select ContractFactor,SpecSettlePrice from T_SettleCommodity where CommodityID='" + commodityid + "' and " + 
      "SettleProcessDate =(select max(SettleProcessDate) SettleProcessDate from T_SettleCommodity where CommodityID='" + commodityid + "')";
    list = dao.queryBySQL(sql, null);
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      ContractFactor = ((BigDecimal)map.get("CONTRACTFACTOR")).doubleValue();
    }
    double mayAmount = 0.0D;
    if (type == 1)
    {
      sql = "select t.weight-t.frozenweight mayAmount from s_regstock t where t.regstockid='" + regStockID + "' and t.firmid='" + sFirmId + "'";
      list = dao.queryBySQL(sql, null);
      if ((list != null) && (list.size() > 0))
      {
        Map map = (Map)list.get(0);
        mayAmount = ((BigDecimal)map.get("MAYAMOUNT")).doubleValue();
      }
    }
    logger.debug("mayAmount:" + mayAmount);
    
    double amount = settleAmount * ContractFactor;
    logger.debug("amount:" + amount);
    if ((bAmount >= settleAmount + bSettleAmount) && (sAmount >= settleAmount + sSettleAmount) && (mayAmount < amount) && (type == 1))
    {
      result = mayAmount / ContractFactor;
    }
    else if ((bAmount < settleAmount + bSettleAmount) && (sAmount < settleAmount + sSettleAmount) && (mayAmount < amount) && (type == 1))
    {
      double bA = bAmount - bSettleAmount;
      double sA = sAmount - sSettleAmount;
      double mA = mayAmount / ContractFactor;
      if (bA >= sA) {
        result = sA;
      } else {
        result = bA;
      }
      if (result > mA) {
        result = mA;
      }
      if (result < 0.0D) {
        result = 0.0D;
      }
    }
    else if ((bAmount >= settleAmount + bSettleAmount) && (sAmount >= settleAmount + sSettleAmount) && ((mayAmount >= amount) || (type != 1)))
    {
      result = -1.0D;
    }
    else if ((bAmount < settleAmount + bSettleAmount) && (sAmount >= settleAmount + sSettleAmount) && ((mayAmount >= amount) || (type != 1)))
    {
      result = bAmount - bSettleAmount;
      if (result < 0.0D) {
        result = 0.0D;
      }
    }
    else if ((bAmount >= settleAmount + bSettleAmount) && (sAmount < settleAmount + sSettleAmount) && ((mayAmount >= amount) || (type != 1)))
    {
      result = sAmount - sSettleAmount;
      if (result < 0.0D) {
        result = 0.0D;
      }
    }
    else if ((bAmount < settleAmount + bSettleAmount) && (sAmount < settleAmount + sSettleAmount) && ((mayAmount < amount) || (type != 1)))
    {
      double bA = bAmount - bSettleAmount;
      double sA = sAmount - sSettleAmount;
      if (bA >= sA) {
        result = sA;
      } else {
        result = bA;
      }
      if (result < 0.0D) {
        result = 0.0D;
      }
    }
    else
    {
      result = 0.0D;
    }
    return result;
  }
  
  public static double checkQualitySettle(String commodityid, String bFirmId, String sFirmId, String regStockID, int type)
  {
    double result = 0.0D;
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select nvl(sum(HoldQty+GageQty),0) sAmount,nvl(max(SettlePrice),0) SettlePrice from t_settleholdposition where settletype<>2 and BS_Flag=2 and commodityid='" + 
      commodityid + "' and firmId='" + sFirmId + "'";
    logger.debug(sql);
    List list = dao.queryBySQL(sql, null);
    double sAmount = 0.0D;
    double bAmount = 0.0D;
    double SettlePrice_b = 0.0D;
    double SettlePrice_s = 0.0D;
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      sAmount = ((BigDecimal)map.get("SAMOUNT")).doubleValue();
      SettlePrice_s = ((BigDecimal)map.get("SETTLEPRICE")).doubleValue();
    }
    sql = 
      "select nvl(sum(HoldQty+GageQty),0) bAmount,nvl(max(SettlePrice),0) SettlePrice from t_settleholdposition where settletype<>2 and BS_Flag=1 and commodityid='" + commodityid + "' and firmId='" + bFirmId + "'";
    logger.debug(sql);
    
    list = dao.queryBySQL(sql, null);
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      bAmount = ((BigDecimal)map.get("BAMOUNT")).doubleValue();
      SettlePrice_b = ((BigDecimal)map.get("SETTLEPRICE")).doubleValue();
    }
    sql = "select nvl(sum(Quantity),0) bSettleAmount from T_SettleMatch where matchid not like 'A%' and CommodityID='" + commodityid + "' and FirmID_B='" + bFirmId + "' and Status>=0";
    logger.debug(sql);
    list = dao.queryBySQL(sql, null);
    double bSettleAmount = 0.0D;
    double sSettleAmount = 0.0D;
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      bSettleAmount = ((BigDecimal)map.get("BSETTLEAMOUNT")).doubleValue();
    }
    sql = "select nvl(sum(Quantity),0) sSettleAmount from T_SettleMatch where matchid not like 'A%' and CommodityID='" + commodityid + "' and FirmID_S='" + sFirmId + "' and Status>=0";
    logger.debug(sql);
    list = dao.queryBySQL(sql, null);
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      sSettleAmount = ((BigDecimal)map.get("SSETTLEAMOUNT")).doubleValue();
    }
    logger.debug("bAmount:" + bAmount);
    logger.debug("sAmount:" + sAmount);
    logger.debug("bSettleAmount:" + bSettleAmount);
    logger.debug("sSettleAmount:" + sSettleAmount);
    
    double ContractFactor = 1.0D;
    sql = "select ContractFactor,SpecSettlePrice from T_SettleCommodity where CommodityID='" + commodityid + "' and " + 
      "SettleProcessDate =(select max(SettleProcessDate) SettleProcessDate from T_SettleCommodity where CommodityID='" + commodityid + "')";
    list = dao.queryBySQL(sql, null);
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      ContractFactor = ((BigDecimal)map.get("CONTRACTFACTOR")).doubleValue();
    }
    double mayAmount = 0.0D;
    if ((type == 1) && (regStockID != null))
    {
      sql = "select t.weight-t.frozenweight mayAmount from s_regstock t where t.regstockid='" + regStockID + "' and t.firmid='" + sFirmId + "'";
      list = dao.queryBySQL(sql, null);
      if ((list != null) && (list.size() > 0))
      {
        Map map = (Map)list.get(0);
        mayAmount = ((BigDecimal)map.get("MAYAMOUNT")).doubleValue();
      }
    }
    logger.debug("mayAmount:" + mayAmount);
    




    double bA = bAmount - bSettleAmount;
    
    double sA = sAmount - sSettleAmount;
    
    double mA = mayAmount / ContractFactor;
    if (bA >= sA) {
      result = sA;
    } else {
      result = bA;
    }
    if ((result > mA) && (type == 1) && (regStockID != null)) {
      result = mA;
    }
    if (result < 0.0D) {
      result = 0.0D;
    }
    return result;
  }
  
  public static int createSettle(String commodityid, String bFirmId, String sFirmId, String regStockID, long settleAmount, int type)
    throws SQLException
  {
    int result = -1;
    synchronized (LOCK)
    {
      double r = checkInsertSettle(commodityid, bFirmId, sFirmId, regStockID, settleAmount, type);
      if (r < 0.0D)
      {
        Connection conn = null;
        try
        {
          DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
          DataSource dataSource = dao.getDataSource();
          conn = dataSource.getConnection();
          conn.setAutoCommit(false);
          double GoodsPayment_b = 0.0D;
          double GoodsPayment_s = 0.0D;
          double BuyPayout = 0.0D;
          double BuyMargin = 0.0D;
          double SellMargin = 0.0D;
          double ContractFactor = 0.0D;
          double SettlePrice_b = 0.0D;
          double SettlePrice_s = 0.0D;
          String sql = "select ContractFactor,SpecSettlePrice from T_SettleCommodity where CommodityID='" + commodityid + "' and " + 
            "SettleProcessDate =(select max(SettleProcessDate) SettleProcessDate from T_SettleCommodity where CommodityID='" + commodityid + "')";
          
          List list = dao.queryBySQL(sql, null);
          if ((list != null) && (list.size() > 0))
          {
            Map map = (Map)list.get(0);
            ContractFactor = ((BigDecimal)map.get("CONTRACTFACTOR")).doubleValue();
          }
          double Payout = 0.0D;
          double SettleMargin = 0.0D;
          long bAmount = 0L;
          long sAmount = 0L;
          


          sql = "select sum(Payout) Payout,sum(SettleMargin) SettleMargin,sum(HoldQty+GageQty) bamount,max(SettlePrice) SettlePrice from T_SettleHoldPosition where CommodityID='" + commodityid + "' and BS_Flag=1 and FirmID='" + bFirmId + "' and settletype<>2";
          list = dao.queryBySQL(sql, null);
          if ((list != null) && (list.size() > 0))
          {
            Map map = (Map)list.get(0);
            Payout = ((BigDecimal)map.get("PAYOUT")).doubleValue();
            SettleMargin = ((BigDecimal)map.get("SETTLEMARGIN")).doubleValue();
            bAmount = ((BigDecimal)map.get("BAMOUNT")).longValue();
            SettlePrice_b = ((BigDecimal)map.get("SETTLEPRICE")).doubleValue();
          }
          BuyPayout = Payout / bAmount * settleAmount;
          BuyMargin = SettleMargin / bAmount * settleAmount;
          



          sql = "select sum(SettleMargin) SettleMargin,sum(HoldQty+GageQty) samount,max(SettlePrice) SettlePrice from T_SettleHoldPosition where CommodityID='" + commodityid + "' and BS_Flag=2 and FirmID='" + sFirmId + "' and settletype<>2";
          list = dao.queryBySQL(sql, null);
          if ((list != null) && (list.size() > 0))
          {
            Map map = (Map)list.get(0);
            SettleMargin = ((BigDecimal)map.get("SETTLEMARGIN")).doubleValue();
            sAmount = ((BigDecimal)map.get("SAMOUNT")).longValue();
            SettlePrice_s = ((BigDecimal)map.get("SETTLEPRICE")).doubleValue();
          }
          SellMargin = SettleMargin / sAmount * settleAmount;
          GoodsPayment_s = SettlePrice_s * settleAmount * ContractFactor;
          
          String data = "{?=call FN_S_createT_SettleMatch('" + commodityid + "'," + ContractFactor + "," + settleAmount + ",0,'" + type + "','" + bFirmId + "','" + sFirmId + "'," + 
            SettlePrice_b + "," + SettlePrice_s + "," + BuyPayout + ",0," + BuyMargin + "," + SellMargin + ",''" + ",'" + regStockID + "')}";
          CallableStatement cstmt = conn.prepareCall(data);
          cstmt.registerOutParameter(1, 2);
          cstmt.executeUpdate();
          result = cstmt.getInt(1);
          
          double amount = settleAmount * ContractFactor;
          sql = "update s_regstock set frozenweight=frozenweight+" + amount + " where regstockid='" + regStockID + "'";
          dao.updateBySQL(sql);
        }
        catch (Exception e)
        {
          e.printStackTrace();
          conn.rollback();
          result = -2;
        }
        finally
        {
          conn.setAutoCommit(true);
          if (conn != null)
          {
            conn.close();
            conn = null;
          }
        }
      }
      else
      {
        result = -1;
      }
    }
    return result;
  }
  
  public static int verifySettle(String matchID, String operator)
    throws SQLException
  {
    int result = 1;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    try
    {
      DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
      DataSource dataSource = dao.getDataSource();
      conn = dataSource.getConnection();
      conn.setAutoCommit(false);
      String commodityId = "";
      String bFirmId = "";
      String sFirmId = "";
      double buyPayout = 0.0D;
      
      int status = -1;
      int type = -1;
      double contractFactor = 0.0D;
      long quantity = 0L;
      double buyPrice = 0.0D;
      double buyMargin = 0.0D;
      double sellPrice = 0.0D;
      double sellIncome_Ref = 0.0D;
      double sellMargin = 0.0D;
      double buypayout_ref = 0.0D;
      String regStockID = "";
      String sql = "select * from T_SettleMatch where MatchID='" + matchID + "' for update";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if (rs.next())
      {
        commodityId = rs.getString("COMMODITYID");
        bFirmId = rs.getString("FIRMID_B");
        sFirmId = rs.getString("FIRMID_S");
        status = rs.getInt("STATUS");
        buyPayout = rs.getDouble("BUYPAYOUT");
        
        type = rs.getInt("RESULT");
        contractFactor = rs.getDouble("CONTRACTFACTOR");
        quantity = rs.getLong("QUANTITY");
        buypayout_ref = rs.getDouble("BUYPAYOUT_REF");
        sellIncome_Ref = rs.getDouble("SELLINCOME_REF");
        buyPrice = rs.getDouble("BUYPRICE");
        buyMargin = rs.getDouble("BUYMARGIN");
        sellPrice = rs.getDouble("SELLPRICE");
        sellMargin = rs.getDouble("SELLMARGIN");
        regStockID = rs.getString("REGSTOCKID");
      }
      rs.close();
      rs = null;
      pstmt.close();
      pstmt = null;
      
      logger.debug("status:" + status);
      if (status == 0)
      {
        long breedid = 0L;
        sql = "select t.breedid from t_settlecommodity t where t.commodityid='" + commodityId + "' and rownum=1";
        sql = sql + "union select t.breedid from t_commodity t where t.commodityid='" + commodityId + "' and rownum=1";
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        if (rs.next()) {
          breedid = rs.getLong(1);
        }
        rs.close();
        rs = null;
        pstmt.close();
        pstmt = null;
        

        double amount = contractFactor * quantity;
        



        String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><MATCHID>" + matchID + "</MATCHID></root>";
        


        String data = "{?=call FN_S_createSettleMatch('2'," + breedid + "," + amount + "," + type + ",'" + commodityId + "','" + bFirmId + "'," + buyPrice + "," + buypayout_ref + "," + 
          buyMargin + "," + buyPayout + ",'" + sFirmId + "'," + sellPrice + "," + sellIncome_Ref + "," + sellMargin + ",?,null,?,'" + operator + "','2')}";
        logger.debug(data);
        
        CallableStatement cstmt = conn.prepareCall(data);
        cstmt.registerOutParameter(1, 8);
        cstmt.setString(2, regStockID);
        cstmt.setString(3, xml);
        cstmt.executeUpdate();
        result = cstmt.getInt(1);
        
        logger.debug("result:" + result);
        if (result < 0)
        {
          status = -1;
          
          sql = "update s_regstock set frozenweight=frozenweight-" + amount + " where regstockid='" + regStockID + "'";
          pstmt = conn.prepareStatement(sql);
          pstmt.executeUpdate();
          pstmt.close();
          pstmt = null;
        }
        else
        {
          status = 1;
        }
        sql = "update T_SettleMatch set status=" + status + ",Modifier='" + operator + "',ModifyTime=sysdate where matchID='" + matchID + "'";
        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        pstmt.close();
        pstmt = null;
      }
      else
      {
        result = -5;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      conn.rollback();
      result = -6;
    }
    finally
    {
      conn.setAutoCommit(true);
      if (rs != null)
      {
        rs.close();
        rs = null;
      }
      if (pstmt != null)
      {
        pstmt.close();
        pstmt = null;
      }
      if (conn != null)
      {
        conn.close();
        conn = null;
      }
    }
    return result;
  }
  
  public static int verifySettleDown(String matchID, String operator)
    throws SQLException
  {
    int result = 1;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    try
    {
      DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
      DataSource dataSource = dao.getDataSource();
      conn = dataSource.getConnection();
      conn.setAutoCommit(false);
      String commodityId = "";
      String bFirmId = "";
      String sFirmId = "";
      double buyPayout = 0.0D;
      double sellIncome = 0.0D;
      int status = -1;
      int type = -1;
      double contractFactor = 0.0D;
      long quantity = 0L;
      double buyPrice = 0.0D;
      double buyMargin = 0.0D;
      double sellPrice = 0.0D;
      double sellMargin = 0.0D;
      String regStockID = "";
      String sql = "select * from T_SettleMatch where MatchID='" + matchID + "' for update";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if (rs.next())
      {
        commodityId = rs.getString("COMMODITYID");
        bFirmId = rs.getString("FIRMID_B");
        sFirmId = rs.getString("FIRMID_S");
        status = rs.getInt("STATUS");
        buyPayout = rs.getDouble("BUYPAYOUT");
        sellIncome = rs.getDouble("SELLINCOME");
        type = rs.getInt("RESULT");
        contractFactor = rs.getDouble("CONTRACTFACTOR");
        quantity = rs.getLong("QUANTITY");
        buyPrice = rs.getDouble("BUYPRICE");
        buyMargin = rs.getDouble("BUYMARGIN");
        sellPrice = rs.getDouble("SELLPRICE");
        sellMargin = rs.getDouble("SELLMARGIN");
        regStockID = rs.getString("REGSTOCKID");
      }
      rs.close();
      rs = null;
      pstmt.close();
      pstmt = null;
      
      logger.debug("status:" + status);
      if (status == 0)
      {
        status = -2;
        sql = "update T_SettleMatch set status=" + status + ",Modifier='" + operator + "',ModifyTime=sysdate where matchID='" + matchID + "'";
        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        pstmt.close();
        pstmt = null;
        
        sql = "select * from T_MatchSettleholdRelevance where MatchID='" + matchID + "'";
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        while (rs.next())
        {
          sql = "update T_SettleHoldPosition t1 set MatchQuantity = (select t2.MatchQuantity-" + rs.getString("Quantity") + " from T_SettleHoldPosition t2 where t2.id='" + rs.getString("SettleID") + "')  " + " where t1.id='" + rs.getString("SettleID") + "'";
          pstmt = conn.prepareStatement(sql);
          pstmt.executeUpdate();
          sql = "delete T_MatchSettleholdRelevance t where t.matchid='" + matchID + "'";
          pstmt = conn.prepareStatement(sql);
          pstmt.executeUpdate();
        }
        rs.close();
        rs = null;
        pstmt.close();
        pstmt = null;
        



        double amount = contractFactor * quantity;
        sql = "update s_regstock set frozenweight=frozenweight-" + amount + " where regstockid='" + regStockID + "'";
        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        pstmt.close();
        pstmt = null;
      }
      else
      {
        result = -5;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      conn.rollback();
      result = -6;
    }
    finally
    {
      conn.setAutoCommit(true);
      if (rs != null)
      {
        rs.close();
        rs = null;
      }
      if (pstmt != null)
      {
        pstmt.close();
        pstmt = null;
      }
      if (conn != null)
      {
        conn.close();
        conn = null;
      }
    }
    return result;
  }
}
