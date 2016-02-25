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

public class SettleManage
{
  private static final transient Log logger = LogFactory.getLog(SettleManage.class);
  
  public static List getWarehouseMsg(String billid)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select floor(sr.weight/tab.contractfactor) weight , sr.breedid,sr.firmid,tab.breedname from S_RegStock sr,  (select breedid, breedname,ContractFactor from T_A_Breed tab) tab  where regStockId = '" + 
    

      billid + "' and tab.breedid = sr.breedid " + 
      "and sr.type<>2 and sr.frozenweight = 0";
    List result = dao.queryBySQL(sql, null);
    return result;
  }
  
  public static List getSettles(String filter)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select * from T_SettleMatch where 1=1 " + filter + " order by createtime desc";
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
  
  public static Map getFirmFunds(String firmId)
  {
    Map map = null;
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select balance-frozenfunds buyBalance from  f_firmfunds where firmid = '" + firmId + "'";
    List list = dao.queryBySQL(sql, null);
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
    String sql = "select distinct(FirmID) bFirmId from t_settleholdposition where settletype<>2 and BS_Flag=1 and commodityid='" + commodityid + "' order by FirmID";
    List result = dao.queryBySQL(sql, null);
    return result;
  }
  
  public static List getSellFirmIds(String commodityid)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select distinct(FirmID) sFirmId from t_settleholdposition where settletype<>2 and BS_Flag=2 and commodityid='" + commodityid + "' order by FirmID";
    List result = dao.queryBySQL(sql, null);
    return result;
  }
  
  public static long checkInsertSettle(String commodityid, String bFirmId, String sFirmId, long settleAmount)
  {
    long result = 0L;
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String sql = "select nvl(sum(HoldQty+GageQty),0) sAmount,nvl(max(SettlePrice),0) SettlePrice from t_settleholdposition where settletype<>2 and BS_Flag=2 and commodityid='" + 
      commodityid + "' and firmId='" + sFirmId + "'";
    List list = dao.queryBySQL(sql, null);
    long sAmount = 0L;
    long bAmount = 0L;
    double SettlePrice_b = 0.0D;
    double SettlePrice_s = 0.0D;
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      sAmount = ((BigDecimal)map.get("SAMOUNT")).longValue();
      SettlePrice_s = ((BigDecimal)map.get("SETTLEPRICE")).doubleValue();
    }
    sql = 
      "select nvl(sum(HoldQty+GageQty),0) bAmount,nvl(max(SettlePrice),0) SettlePrice from t_settleholdposition where settletype<>2 and BS_Flag=1 and commodityid='" + commodityid + "' and firmId='" + bFirmId + "'";
    list = dao.queryBySQL(sql, null);
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      bAmount = ((BigDecimal)map.get("BAMOUNT")).longValue();
      SettlePrice_b = ((BigDecimal)map.get("SETTLEPRICE")).doubleValue();
    }
    sql = "select nvl(sum(Quantity),0) bSettleAmount from T_SettleMatch where matchid not like 'A%' and CommodityID='" + commodityid + "' and FirmID_B='" + bFirmId + "' and Status<>3";
    list = dao.queryBySQL(sql, null);
    long bSettleAmount = 0L;
    long sSettleAmount = 0L;
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      bSettleAmount = ((BigDecimal)map.get("BSETTLEAMOUNT")).longValue();
    }
    sql = "select nvl(sum(Quantity),0) sSettleAmount from T_SettleMatch where matchid not like 'A%' and CommodityID='" + commodityid + "' and FirmID_S='" + sFirmId + "' and Status<>3";
    list = dao.queryBySQL(sql, null);
    if ((list != null) && (list.size() > 0))
    {
      Map map = (Map)list.get(0);
      sSettleAmount = ((BigDecimal)map.get("SSETTLEAMOUNT")).longValue();
    }
    logger.debug("bAmount:" + bAmount);
    logger.debug("sAmount:" + sAmount);
    if ((bAmount >= settleAmount + bSettleAmount) && (sAmount >= settleAmount + sSettleAmount))
    {
      result = -1L;
    }
    else if ((bAmount < settleAmount + bSettleAmount) && (sAmount >= settleAmount + sSettleAmount))
    {
      result = bAmount - bSettleAmount;
      if (result < 0L) {
        result = 0L;
      }
    }
    else if ((bAmount >= settleAmount + bSettleAmount) && (sAmount < settleAmount + sSettleAmount))
    {
      result = sAmount - sSettleAmount;
      if (result < 0L) {
        result = 0L;
      }
    }
    else if ((bAmount < settleAmount + bSettleAmount) && (sAmount < settleAmount + sSettleAmount))
    {
      long bA = bAmount - bSettleAmount;
      long sA = sAmount - sSettleAmount;
      if (bA >= sA) {
        result = sA;
      } else {
        result = bA;
      }
      if (result < 0L) {
        result = 0L;
      }
    }
    else
    {
      result = 0L;
    }
    return result;
  }
  
  public static int createSettle(String commodityid, String bFirmId, String sFirmId, long settleAmount, int type)
  {
    int result = -1;
    long r = checkInsertSettle(commodityid, bFirmId, sFirmId, settleAmount);
    if (r < 0L) {
      try
      {
        DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
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
        GoodsPayment_b = SettlePrice_b * settleAmount * ContractFactor;
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
        
        sql = "insert into T_SettleMatch (MatchID,CommodityID,ContractFactor,Quantity,HL_Amount,Status,Result,FirmID_B,BuyPrice,BuyPayout_Ref,BuyPayout,BuyMargin,TakePenalty_B,PayPenalty_B,SettlePL_B,FirmID_S,SellPrice,SellIncome_Ref,SellIncome,SellMargin,TakePenalty_S,PayPenalty_S,SettlePL_S,CreateTime,ModifyTime) values ('TS_'||seq_T_SettleMatch.nextVal,'" + 
        
          commodityid + "'," + ContractFactor + "," + settleAmount + ",0,0," + type + ",'" + bFirmId + "'," + SettlePrice_b + "," + GoodsPayment_b + "," + BuyPayout + "," + BuyMargin + ",0,0,0," + 
          "'" + sFirmId + "'," + SettlePrice_s + "," + GoodsPayment_s + ",0," + SellMargin + ",0,0,0,sysdate,sysdate)";
        dao.updateBySQL(sql);
        
        result = 1;
      }
      catch (Exception e)
      {
        e.printStackTrace();
        result = -2;
      }
    } else {
      result = -1;
    }
    return result;
  }
  
  public static int inoutSettleMoney(String matchID, double addGoodsPayment, boolean sign)
    throws SQLException
  {
    int result = -1;
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
      double BuyPayout_Ref = 0.0D;
      double SellIncome_Ref = 0.0D;
      double HL_Amount = 0.0D;
      String sql = "select * from T_SettleMatch where MatchID='" + matchID + "'";
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
        BuyPayout_Ref = rs.getDouble("BUYPAYOUT_REF");
        SellIncome_Ref = rs.getDouble("SELLINCOME_REF");
        HL_Amount = rs.getDouble("HL_AMOUNT");
        type = rs.getInt("RESULT");
      }
      rs.close();
      rs = null;
      pstmt.close();
      pstmt = null;
      if ((status == 3) && (status == 2))
      {
        result = -1;
      }
      else if (type != 1)
      {
        result = -3;
      }
      else
      {
        boolean s = false;
        String firmId = "";
        String option = "";
        double marketPayMent = SellIncome_Ref - BuyPayout_Ref;
        if (sign)
        {
          firmId = bFirmId;
          option = "208";
          if ((buyPayout + addGoodsPayment + marketPayMent >= sellIncome) && (buyPayout + addGoodsPayment >= 0.0D))
          {
            s = true;
          }
          else
          {
            result = -4;
            if (buyPayout + addGoodsPayment < 0.0D) {
              result = -6;
            }
          }
          buyPayout += addGoodsPayment;
        }
        else
        {
          firmId = sFirmId;
          option = "209";
          logger.debug("buyPayout:" + buyPayout);
          logger.debug("marketPayMent:" + marketPayMent);
          logger.debug("sellIncome:" + sellIncome);
          if ((buyPayout + marketPayMent >= sellIncome + addGoodsPayment) && (sellIncome + addGoodsPayment >= 0.0D))
          {
            s = true;
          }
          else
          {
            result = -4;
            if (sellIncome + addGoodsPayment < 0.0D) {
              result = -6;
            }
          }
          if ((SellIncome_Ref + HL_Amount >= sellIncome + addGoodsPayment) && (s))
          {
            s = true;
          }
          else if (s)
          {
            result = -5;
            s = false;
          }
          sellIncome += addGoodsPayment;
        }
        logger.debug("s:" + s);
        if (s)
        {
          logger.debug("{?=call FN_F_UpdateFundsFull('" + firmId + "','" + option + "'," + addGoodsPayment + ",'" + matchID + "','" + commodityId + "',null,null)}");
          CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + firmId + "','" + option + "'," + addGoodsPayment + ",'" + matchID + "','" + commodityId + "',null,null)}");
          cstmt.registerOutParameter(1, 8);
          cstmt.executeUpdate();
          if (status == 0) {
            status = 1;
          }
          sql = "update T_SettleMatch set status=" + status + ",BuyPayout=" + buyPayout + ",sellIncome=" + sellIncome + ",ModifyTime=sysdate where MatchID='" + matchID + "'";
          pstmt = conn.prepareStatement(sql);
          pstmt.executeUpdate();
          pstmt.close();
          pstmt = null;
          
          result = 1;
        }
      }
      conn.commit();
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
  
  public static int marginTurnGoodsPayment(String matchID, double marginTurnMoney)
    throws SQLException
  {
    int result = -1;
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
      double BuyPayout_Ref = 0.0D;
      double SellIncome_Ref = 0.0D;
      double buyMargin = 0.0D;
      String sql = "select * from T_SettleMatch where MatchID='" + matchID + "'";
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
        BuyPayout_Ref = rs.getDouble("BUYPAYOUT_REF");
        SellIncome_Ref = rs.getDouble("SELLINCOME_REF");
        buyMargin = rs.getDouble("BUYMARGIN");
        type = rs.getInt("RESULT");
      }
      rs.close();
      rs = null;
      pstmt.close();
      pstmt = null;
      if ((status == 3) && (status == 2))
      {
        result = -1;
      }
      else if (type != 1)
      {
        result = -3;
      }
      else
      {
        double marketPayMent = SellIncome_Ref - BuyPayout_Ref;
        if ((buyMargin - marginTurnMoney >= 0.0D) && (buyPayout + marginTurnMoney + marketPayMent >= sellIncome))
        {
          buyMargin -= marginTurnMoney;
          logger.debug("buyMargin:" + buyMargin);
          sql = "update T_SettleMatch set ModifyTime=sysdate,buyMargin=" + buyMargin + " where MatchID='" + matchID + "'";
          logger.debug(sql);
          pstmt = conn.prepareStatement(sql);
          pstmt.executeUpdate();
          pstmt.close();
          pstmt = null;
          
          sql = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-(" + marginTurnMoney + ") where firmId='" + bFirmId + "'";
          logger.debug(sql);
          pstmt = conn.prepareStatement(sql);
          pstmt.executeUpdate();
          pstmt.close();
          pstmt = null;
          

          CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + bFirmId + "','214'," + marginTurnMoney + ",'" + matchID + "','" + commodityId + "',null,null)}");
          cstmt.registerOutParameter(1, 8);
          cstmt.executeUpdate();
          

          String firmId = "";
          String option = "";
          
          firmId = bFirmId;
          option = "208";
          buyPayout += marginTurnMoney;
          

          CallableStatement cstmt1 = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + firmId + "','" + option + "'," + marginTurnMoney + ",'" + matchID + "','" + commodityId + "',null,null)}");
          cstmt1.registerOutParameter(1, 8);
          cstmt1.executeUpdate();
          if (status == 0) {
            status = 1;
          }
          sql = "update T_SettleMatch set status=" + status + ",BuyPayout=" + buyPayout + ",ModifyTime=sysdate where MatchID='" + matchID + "'";
          logger.debug(sql);
          pstmt = conn.prepareStatement(sql);
          pstmt.executeUpdate();
          pstmt.close();
          pstmt = null;
          
          result = 1;
        }
        else
        {
          logger.debug("buyPayout+marginTurnMoney>0:" + (buyPayout + marginTurnMoney > 0.0D));
          logger.debug("buyPayout+marginTurnMoney+marketPayMent>=sellIncome:" + (buyPayout + marginTurnMoney + marketPayMent >= sellIncome));
          if (buyMargin - marginTurnMoney < 0.0D) {
            result = -4;
          } else if (buyPayout + marginTurnMoney + marketPayMent < sellIncome) {
            result = -5;
          }
        }
      }
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
  
  public static int cancelSettle(String matchID)
    throws SQLException
  {
    int result = -1;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt1 = null;
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
      double takePenalty_B = 0.0D;
      double payPenalty_B = 0.0D;
      double takePenalty_S = 0.0D;
      double payPenalty_S = 0.0D;
      double buyMargin = 0.0D;
      double sellMargin = 0.0D;
      

      String sql = "select * from T_SettleMatch where MatchID='" + matchID + "' for update";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if (rs.next())
      {
        commodityId = rs.getString("commodityId");
        bFirmId = rs.getString("FirmID_B");
        sFirmId = rs.getString("FirmID_S");
        status = rs.getInt("Status");
        buyPayout = rs.getDouble("BuyPayout");
        sellIncome = rs.getDouble("sellIncome");
        type = rs.getInt("result");
        takePenalty_B = rs.getDouble("takePenalty_B");
        payPenalty_B = rs.getDouble("payPenalty_B");
        takePenalty_S = rs.getDouble("takePenalty_S");
        payPenalty_S = rs.getDouble("payPenalty_S");
        buyMargin = rs.getDouble("buyMargin");
        sellMargin = rs.getDouble("sellMargin");
      }
      rs.close();
      rs = null;
      pstmt.close();
      pstmt = null;
      if ((status != 3) && (status != 2))
      {
        if (status != 0)
        {
          sql = 
          


            "select fundflowid,firmid,oprcode,amount from  (select t.fundflowid,t.firmid,t.oprcode,t.amount from f_fundflow t where t.contractno='" + matchID + "') " + "union " + "(select t.fundflowid,t.firmid,t.oprcode,t.amount from f_h_fundflow t where t.contractno='" + matchID + "') " + "order by fundflowid";
          logger.debug(sql);
          pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();
          while (rs.next())
          {
            String firmId = rs.getString("FIRMID");
            String option = rs.getString("OPRCODE");
            double amount = rs.getDouble("AMOUNT");
            
            logger.debug("{?=call FN_F_UpdateFundsFull('" + firmId + "','" + option + "'," + -amount + ",'" + matchID + "','" + commodityId + "',null,null)}");
            CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + firmId + "','" + option + "'," + -amount + ",'" + matchID + "','" + commodityId + "',null,null)}");
            cstmt.registerOutParameter(1, 8);
            cstmt.executeUpdate();
            if ("214".equals(option))
            {
              sql = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin+" + amount + " where firmId='" + firmId + "'";
              logger.debug(sql);
              pstmt1 = conn.prepareStatement(sql);
              pstmt1.executeUpdate();
              pstmt1.close();
              pstmt1 = null;
            }
          }
          rs.close();
          rs = null;
          pstmt.close();
          pstmt = null;
        }
        sql = "update T_SettleMatch set ModifyTime=sysdate,status=3 where MatchID='" + matchID + "'";
        pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        pstmt.close();
        pstmt = null;
        
        result = 1;
      }
      else
      {
        result = -1;
      }
      conn.commit();
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
      if (pstmt1 != null)
      {
        pstmt1.close();
        pstmt1 = null;
      }
      if (conn != null)
      {
        conn.close();
        conn = null;
      }
    }
    return result;
  }
  
  public static int receiveOrPayPenalty(String matchID, double PenaltyMoney, boolean sign, boolean sign1)
    throws SQLException
  {
    int result = 0;
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
      double takePenalty_B = 0.0D;
      double payPenalty_B = 0.0D;
      double takePenalty_S = 0.0D;
      double payPenalty_S = 0.0D;
      double buyMargin = 0.0D;
      double sellMargin = 0.0D;
      

      String sql = "select * from T_SettleMatch where MatchID='" + matchID + "' for update";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if (rs.next())
      {
        commodityId = rs.getString("commodityId");
        bFirmId = rs.getString("FirmID_B");
        sFirmId = rs.getString("FirmID_S");
        status = rs.getInt("Status");
        buyPayout = rs.getDouble("BuyPayout");
        sellIncome = rs.getDouble("sellIncome");
        type = rs.getInt("result");
        takePenalty_B = rs.getDouble("takePenalty_B");
        payPenalty_B = rs.getDouble("payPenalty_B");
        takePenalty_S = rs.getDouble("takePenalty_S");
        payPenalty_S = rs.getDouble("payPenalty_S");
        buyMargin = rs.getDouble("buyMargin");
        sellMargin = rs.getDouble("sellMargin");
      }
      rs.close();
      rs = null;
      pstmt.close();
      pstmt = null;
      if ((status != 3) && (status != 2))
      {
        if (type != 1)
        {
          if (sellIncome == 0.0D)
          {
            if ((type == 2) && (((sign1) && (!sign)) || ((!sign1) && (sign))))
            {
              logger.debug("1");
              logger.debug("sign:" + sign + "  ");
              logger.debug(sign1 ? "买方" : "卖方");
              logger.debug("sign1:" + sign1 + "  ");
              logger.debug(sign ? "收" : "付");
              result = -3;
            }
            else if ((type == 3) && (((sign) && (sign1)) || ((!sign) && (!sign1))))
            {
              result = -3;
            }
            else if ((type == 4) && (!sign))
            {
              result = -3;
            }
            else
            {
              String option = "";
              String firmId = "";
              if (sign)
              {
                double margin = 0.0D;
                option = "217";
                if (sign1)
                {
                  firmId = bFirmId;
                  margin = buyMargin;
                  buyMargin = 0.0D;
                  takePenalty_B += PenaltyMoney;
                  if (takePenalty_B < 0.0D) {
                    result = -4;
                  }
                }
                else
                {
                  firmId = sFirmId;
                  margin = sellMargin;
                  sellMargin = 0.0D;
                  takePenalty_S += PenaltyMoney;
                  if (takePenalty_S < 0.0D) {
                    result = -4;
                  }
                }
                if (result == 0)
                {
                  sql = "update T_SettleMatch set ModifyTime=sysdate,buyMargin=" + buyMargin + ",sellMargin=" + sellMargin + " where MatchID='" + matchID + "'";
                  logger.debug(sql);
                  pstmt = conn.prepareStatement(sql);
                  pstmt.executeUpdate();
                  pstmt.close();
                  pstmt = null;
                  
                  sql = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-(" + margin + ") where firmId='" + firmId + "'";
                  logger.debug(sql);
                  pstmt = conn.prepareStatement(sql);
                  pstmt.executeUpdate();
                  pstmt.close();
                  pstmt = null;
                  

                  CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + firmId + "','214'," + margin + ",'" + matchID + "','" + commodityId + "',null,null)}");
                  cstmt.registerOutParameter(1, 8);
                  cstmt.executeUpdate();
                }
              }
              else
              {
                option = "218";
                if (sign1)
                {
                  firmId = bFirmId;
                  payPenalty_B += PenaltyMoney;
                  if (payPenalty_B < 0.0D) {
                    result = -4;
                  }
                }
                else
                {
                  firmId = sFirmId;
                  payPenalty_S += PenaltyMoney;
                  if (payPenalty_S < 0.0D) {
                    result = -4;
                  }
                }
              }
              if (result == 0)
              {
                if (status == 0) {
                  status = 1;
                }
                sql = "update T_SettleMatch set ModifyTime=sysdate,takePenalty_B=" + takePenalty_B + ",payPenalty_B=" + payPenalty_B + ",takePenalty_S=" + takePenalty_S + ",payPenalty_S=" + payPenalty_S + ",status=" + status + " where MatchID='" + matchID + "'";
                logger.debug(sql);
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
                pstmt.close();
                pstmt = null;
                

                CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + firmId + "','" + option + "'," + PenaltyMoney + ",'" + matchID + "','" + commodityId + "',null,null)}");
                cstmt.registerOutParameter(1, 8);
                cstmt.executeUpdate();
                
                result = 1;
              }
            }
          }
          else {
            result = -3;
          }
        }
        else {
          result = -3;
        }
      }
      else {
        result = -1;
      }
      conn.commit();
    }
    catch (Exception e)
    {
      conn.rollback();
      result = -2;
      e.printStackTrace();
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
  
  public static int addSettlePL(String matchID, double PLMoney, boolean sign)
    throws SQLException
  {
    int result = -1;
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
      double takePenalty_B = 0.0D;
      double payPenalty_B = 0.0D;
      double takePenalty_S = 0.0D;
      double payPenalty_S = 0.0D;
      double buyMargin = 0.0D;
      double sellMargin = 0.0D;
      double settlePL_B = 0.0D;
      double settlePL_S = 0.0D;
      

      String sql = "select * from T_SettleMatch where MatchID='" + matchID + "' for update";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if (rs.next())
      {
        commodityId = rs.getString("commodityId");
        bFirmId = rs.getString("FirmID_B");
        sFirmId = rs.getString("FirmID_S");
        status = rs.getInt("Status");
        buyPayout = rs.getDouble("BuyPayout");
        sellIncome = rs.getDouble("sellIncome");
        type = rs.getInt("result");
        takePenalty_B = rs.getDouble("takePenalty_B");
        payPenalty_B = rs.getDouble("payPenalty_B");
        takePenalty_S = rs.getDouble("takePenalty_S");
        payPenalty_S = rs.getDouble("payPenalty_S");
        buyMargin = rs.getDouble("buyMargin");
        sellMargin = rs.getDouble("sellMargin");
        settlePL_B = rs.getDouble("settlePL_B");
        settlePL_S = rs.getDouble("settlePL_S");
      }
      rs.close();
      rs = null;
      pstmt.close();
      pstmt = null;
      if ((status != 3) && (status != 2) && (PLMoney != 0.0D))
      {
        if (type != 1)
        {
          if (sellIncome == 0.0D)
          {
            String firmId = "";
            String option = "";
            if (sign)
            {
              firmId = bFirmId;
              settlePL_B += PLMoney;
            }
            else
            {
              firmId = sFirmId;
              settlePL_S += PLMoney;
            }
            if (PLMoney > 0.0D)
            {
              option = "211";
            }
            else
            {
              option = "212";
              PLMoney = -PLMoney;
            }
            CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + firmId + "','" + option + "'," + PLMoney + ",'" + matchID + "','" + commodityId + "',null,null)}");
            cstmt.registerOutParameter(1, 8);
            cstmt.executeUpdate();
            if (status == 0) {
              status = 1;
            }
            sql = "update T_SettleMatch set ModifyTime=sysdate,status=" + status + ",settlePL_B=" + settlePL_B + ",settlePL_S=" + settlePL_S + " where MatchID='" + matchID + "'";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = null;
            
            result = 1;
          }
          else
          {
            result = -3;
          }
        }
        else {
          result = -3;
        }
      }
      else {
        result = -1;
      }
      conn.commit();
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
  
  public static int HLSettle(String matchID, double HLmoney, boolean sign)
    throws SQLException
  {
    int result = -1;
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
      double takePenalty_B = 0.0D;
      double payPenalty_B = 0.0D;
      double takePenalty_S = 0.0D;
      double payPenalty_S = 0.0D;
      double buyMargin = 0.0D;
      double sellMargin = 0.0D;
      double settlePL_B = 0.0D;
      double settlePL_S = 0.0D;
      double HL_Amount = 0.0D;
      double BuyPayout_Ref = 0.0D;
      double SellIncome_Ref = 0.0D;
      
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
        takePenalty_B = rs.getDouble("TAKEPENALTY_B");
        payPenalty_B = rs.getDouble("PAYPENALTY_B");
        takePenalty_S = rs.getDouble("TAKEPENALTY_S");
        payPenalty_S = rs.getDouble("PAYPENALTY_S");
        buyMargin = rs.getDouble("BUYMARGIN");
        sellMargin = rs.getDouble("SELLMARGIN");
        settlePL_B = rs.getDouble("SETTLEPL_B");
        settlePL_S = rs.getDouble("SETTLEPL_S");
        HL_Amount = rs.getDouble("HL_AMOUNT");
        BuyPayout_Ref = rs.getDouble("BUYPAYOUT_REF");
        SellIncome_Ref = rs.getDouble("SELLINCOME_REF");
      }
      rs.close();
      rs = null;
      pstmt.close();
      pstmt = null;
      if ((status != 3) && (status != 2))
      {
        if (type == 1)
        {
          double marketPayMent = SellIncome_Ref - BuyPayout_Ref;
          logger.debug("SellIncome_Ref:" + SellIncome_Ref);
          logger.debug("HL_Amount:" + HL_Amount);
          logger.debug("sellIncome:" + sellIncome);
          if (SellIncome_Ref + HL_Amount + HLmoney < sellIncome)
          {
            result = -4;
          }
          else
          {
            if (status == 0) {
              status = 1;
            }
            if (sign) {
              HL_Amount += HLmoney;
            } else {
              HL_Amount -= HLmoney;
            }
            sql = "update T_SettleMatch set ModifyTime=sysdate,status=" + status + ",HL_Amount=" + HL_Amount + " where MatchID='" + matchID + "'";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = null;
            
            result = 1;
          }
        }
        else
        {
          result = -3;
        }
      }
      else {
        result = -1;
      }
      conn.commit();
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
  
  public static int finshSettle(String matchID)
    throws SQLException
  {
    int result = -1;
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
      double takePenalty_B = 0.0D;
      double payPenalty_B = 0.0D;
      double takePenalty_S = 0.0D;
      double payPenalty_S = 0.0D;
      double buyMargin = 0.0D;
      double sellMargin = 0.0D;
      double settlePL_B = 0.0D;
      double settlePL_S = 0.0D;
      double HL_Amount = 0.0D;
      double BuyPayout_Ref = 0.0D;
      double SellIncome_Ref = 0.0D;
      
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
        takePenalty_B = rs.getDouble("TAKEPENALTY_B");
        payPenalty_B = rs.getDouble("PAYPENALTY_B");
        takePenalty_S = rs.getDouble("TAKEPENALTY_S");
        payPenalty_S = rs.getDouble("PAYPENALTY_S");
        buyMargin = rs.getDouble("BUYMARGIN");
        sellMargin = rs.getDouble("SELLMARGIN");
        settlePL_B = rs.getDouble("SETTLEPL_B");
        settlePL_S = rs.getDouble("SETTLEPL_S");
        HL_Amount = rs.getDouble("HL_AMOUNT");
        BuyPayout_Ref = rs.getDouble("BUYPAYOUT_REF");
        SellIncome_Ref = rs.getDouble("SELLINCOME_REF");
      }
      rs.close();
      rs = null;
      pstmt.close();
      pstmt = null;
      if ((status != 3) && (status != 2))
      {
        if (type != 1)
        {
          if (buyMargin > 0.0D)
          {
            logger.debug("buyMargin:" + buyMargin);
            sql = "update T_SettleMatch set ModifyTime=sysdate,buyMargin=0 where MatchID='" + matchID + "'";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = null;
            
            sql = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-" + buyMargin + " where firmId='" + bFirmId + "'";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = null;
            

            CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + bFirmId + "','214'," + buyMargin + ",'" + matchID + "','" + commodityId + "',null,null)}");
            cstmt.registerOutParameter(1, 8);
            cstmt.executeUpdate();
          }
          if (sellMargin > 0.0D)
          {
            logger.debug("sellMargin:" + sellMargin);
            sql = "update T_SettleMatch set ModifyTime=sysdate,sellMargin=0 where MatchID='" + matchID + "'";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = null;
            
            sql = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-" + sellMargin + " where firmId='" + sFirmId + "'";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = null;
            

            CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + sFirmId + "','214'," + sellMargin + ",'" + matchID + "','" + commodityId + "',null,null)}");
            cstmt.registerOutParameter(1, 8);
            cstmt.executeUpdate();
          }
          if (buyPayout != 0.0D)
          {
            logger.debug("buyPayout:" + buyPayout);
            buyPayout = -buyPayout;
            logger.debug("{?=call FN_F_UpdateFundsFull('" + bFirmId + "','208'," + buyPayout + ",'" + matchID + "','" + commodityId + "',null,null)}");
            CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + bFirmId + "','208'," + buyPayout + ",'" + matchID + "','" + commodityId + "',null,null)}");
            cstmt.registerOutParameter(1, 8);
            cstmt.executeUpdate();
            

            sql = "update T_SettleMatch set BuyPayout=0,ModifyTime=sysdate where MatchID='" + matchID + "'";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = null;
          }
          sql = "update T_SettleMatch set ModifyTime=sysdate,status=2 where MatchID='" + matchID + "'";
          pstmt = conn.prepareStatement(sql);
          pstmt.executeUpdate();
          pstmt.close();
          pstmt = null;
          
          result = 1;
        }
        else
        {
          double marketPayMent = SellIncome_Ref - BuyPayout_Ref;
          if (SellIncome_Ref + HL_Amount != sellIncome)
          {
            logger.debug("SellIncome_Ref:" + SellIncome_Ref);
            logger.debug("HL_Amount:" + HL_Amount);
            logger.debug("sellIncome:" + sellIncome);
            result = -3;
          }
          else if (sellIncome != BuyPayout_Ref + HL_Amount + marketPayMent)
          {
            result = -4;
          }
          else if (buyPayout + marketPayMent < sellIncome)
          {
            result = -4;
          }
          else
          {
            if (buyMargin > 0.0D)
            {
              logger.debug("buyMargin:" + buyMargin);
              sql = "update T_SettleMatch set ModifyTime=sysdate,buyMargin=0 where MatchID='" + matchID + "'";
              pstmt = conn.prepareStatement(sql);
              pstmt.executeUpdate();
              pstmt.close();
              pstmt = null;
              
              sql = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-" + buyMargin + " where firmId='" + bFirmId + "'";
              pstmt = conn.prepareStatement(sql);
              pstmt.executeUpdate();
              pstmt.close();
              pstmt = null;
              

              CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + bFirmId + "','214'," + buyMargin + ",'" + matchID + "','" + commodityId + "',null,null)}");
              cstmt.registerOutParameter(1, 8);
              cstmt.executeUpdate();
            }
            if (sellMargin > 0.0D)
            {
              logger.debug("sellMargin:" + sellMargin);
              sql = "update T_SettleMatch set ModifyTime=sysdate,sellMargin=0 where MatchID='" + matchID + "'";
              pstmt = conn.prepareStatement(sql);
              pstmt.executeUpdate();
              pstmt.close();
              pstmt = null;
              
              sql = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-" + sellMargin + " where firmId='" + sFirmId + "'";
              pstmt = conn.prepareStatement(sql);
              pstmt.executeUpdate();
              pstmt.close();
              pstmt = null;
              

              CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + sFirmId + "','214'," + sellMargin + ",'" + matchID + "','" + commodityId + "',null,null)}");
              cstmt.registerOutParameter(1, 8);
              cstmt.executeUpdate();
            }
            if (buyPayout + marketPayMent != sellIncome)
            {
              double returnPayMent = buyPayout + marketPayMent - sellIncome;
              logger.debug("returnPayMent:" + returnPayMent);
              sql = "update T_SettleMatch set ModifyTime=sysdate,buyPayout=buyPayout-" + returnPayMent + " where MatchID='" + matchID + "'";
              pstmt = conn.prepareStatement(sql);
              pstmt.executeUpdate();
              pstmt.close();
              pstmt = null;
              
              returnPayMent = -returnPayMent;
              CallableStatement cstmt = conn.prepareCall("{?=call FN_F_UpdateFundsFull('" + bFirmId + "','208'," + returnPayMent + ",'" + matchID + "','" + commodityId + "',null,null)}");
              cstmt.registerOutParameter(1, 8);
              cstmt.executeUpdate();
            }
            sql = "update T_SettleMatch set ModifyTime=sysdate,status=2 where MatchID='" + matchID + "'";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = null;
            
            result = 1;
          }
        }
      }
      else {
        result = -1;
      }
      conn.commit();
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
  
  public static List getRegStockIdList(String filterSql)
  {
    String sql = " select * from s_regstock " + filterSql;
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    return dao.queryBySQL(sql);
  }
  
  public static void updateSettleMatch(String matchId, String regStockId)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    dao.updateBySQL(" update T_SettleMatch set regStockId=" + regStockId + ",status = 0 where matchid='" + matchId + "' ");
    dao.updateBySQL(" update s_regstock set frozenweight=frozenweight+(select quantity*contractfactor from T_SettleMatch where matchId = '" + matchId + "') where regstockid='" + regStockId + "' ");
  }
  
  public static void updateSettleMatchRegStock(String matchId, String regStockId)
  {
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    dao.updateBySQL(" update s_regstock set frozenweight=frozenweight-(select quantity*contractfactor from T_SettleMatch where matchId = '" + matchId + "') where regstockid=(select regstockid from T_SettleMatch where matchId = '" + matchId + "') ");
    dao.updateBySQL(" update T_SettleMatch set regStockId=" + regStockId + " where matchid='" + matchId + "' ");
    dao.updateBySQL(" update s_regstock set frozenweight=frozenweight+(select quantity*contractfactor from T_SettleMatch where matchId = '" + matchId + "') where regstockid='" + regStockId + "' ");
  }
  
  public static boolean getSettleMatchRegStockId(String matchId)
  {
    String sql = " select * from T_SettleMatch where matchid='" + matchId + "' ";
    DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    String regStockId = null;
    List list = dao.queryBySQL(sql);
    if (list.size() > 0) {
      regStockId = (String)((Map)list.get(0)).get("regStockId");
    }
    if (regStockId == null) {
      return true;
    }
    return false;
  }
}
