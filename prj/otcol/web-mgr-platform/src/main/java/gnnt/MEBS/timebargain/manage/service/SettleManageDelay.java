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

public class SettleManageDelay
{
  private static final transient Log logger = LogFactory.getLog(SettleManageDelay.class);
  
  public static List getSettles(String paramString)
  {
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    String str = "select * from T_SettleMatch where 1=1 and matchid not like 'A%' " + paramString + " order by createtime desc";
    List localList = localDaoHelper.queryBySQL(str, null);
    return localList;
  }
  
  public static List getSettlesNew(String paramString)
  {
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    String str = "select s.* from T_SettleMatch s,t_commodity t where 1=1  and s.matchid not like 'A%' and t.SettleWay=1 and t.commodityId=s.commodityId " + paramString + " order by createtime desc";
    List localList = localDaoHelper.queryBySQL(str, null);
    return localList;
  }
  
  public static Map getSettle(String paramString)
  {
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    String str = "select * from T_SettleMatch where matchid not like 'A%' and  MatchID='" + paramString + "'";
    List localList = localDaoHelper.queryBySQL(str, null);
    Map localMap = null;
    if ((localList != null) && (localList.size() > 0)) {
      localMap = (Map)localList.get(0);
    }
    return localMap;
  }
  
  public static Map getFirmFunds(String paramString)
  {
    Map localMap = null;
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    String str = "select balance-frozenfunds buyBalance from  f_firmfunds where firmid = '" + paramString + "'";
    List localList = localDaoHelper.queryBySQL(str, null);
    if ((localList != null) && (localList.size() > 0)) {
      localMap = (Map)localList.get(0);
    }
    return localMap;
  }
  
  public static List getCommoditys()
  {
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    String str = "select distinct(commodityid) commodityid,substr(to_char(settleprocessdate,'yyyy-mm-dd'),0,10) settleprocessdate  from t_settleholdposition where settletype<>2 order by settleprocessdate desc,commodityid desc";
    List localList = localDaoHelper.queryBySQL(str, null);
    return localList;
  }
  
  public static List getCommoditysNew()
  {
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    String str = "select distinct(s.commodityid) commodityid,substr(to_char(settleprocessdate,'yyyy-mm-dd'),0,10) settleprocessdate  from t_settleholdposition s,t_commodity t where s.commodityId=t.commodityId and t.SettleWay=1 and settletype<>2 order by settleprocessdate desc,s.commodityid desc";
    List localList = localDaoHelper.queryBySQL(str, null);
    return localList;
  }
  
  public static List getBuyFirmIds(String paramString1, String paramString2)
  {
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    String str = "select distinct(FirmID) bFirmId from t_settleholdposition t where settletype<>2 and BS_Flag=1 and trunc(SettleProcessDate)=to_date('" + paramString2 + "','yyyy-MM-dd') and commodityid='" + paramString1 + "' and " + "(select nvl(sum(HoldQty+GageQty),0) bAmount from t_settleholdposition where settletype<>2 and " + "BS_Flag=1 and commodityid='" + paramString1 + "' and firmId= t.firmId and trunc(SettleProcessDate)=to_date('" + paramString2 + "','yyyy-MM-dd'))" + ">" + "(select nvl(sum(Quantity),0) bSettleAmount from T_SettleMatch where CommodityID='" + paramString1 + "' " + "and FirmID_B= t.firmId and Status<>3 and  settleDate=to_date('" + paramString2 + "','yyyy-MM-dd'))" + "order by FirmID";
    List localList = localDaoHelper.queryBySQL(str, null);
    return localList;
  }
  
  public static List getSellFirmIds(String paramString1, String paramString2)
  {
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    String str = "select distinct(FirmID) sFirmId from t_settleholdposition t where settletype<>2 and BS_Flag=2 and trunc(SettleProcessDate)=to_date('" + paramString2 + "','yyyy-MM-dd') and commodityid='" + paramString1 + "' and " + "(select nvl(sum(HoldQty+GageQty),0) sAmount from t_settleholdposition where settletype<>2 and " + "BS_Flag=2 and commodityid='" + paramString1 + "' and firmId= t.firmId and trunc(SettleProcessDate)=to_date('" + paramString2 + "','yyyy-MM-dd'))" + ">" + "(select nvl(sum(Quantity),0) sSettleAmount from T_SettleMatch where CommodityID='" + paramString1 + "' " + "and FirmID_S=t.firmId and Status<>3 and  settleDate=to_date('" + paramString2 + "','yyyy-MM-dd'))" + "order by FirmID";
    List localList = localDaoHelper.queryBySQL(str, null);
    return localList;
  }
  
  public static long checkInsertSettle(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
  {
    long l1 = 0L;
    DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
    String str = "select nvl(sum(HoldQty+GageQty),0) sAmount,nvl(max(SettlePrice),0) SettlePrice from t_settleholdposition where settletype<>2 and BS_Flag=2 and commodityid='" + paramString1 + "' and firmId='" + paramString4 + "' and trunc(SettleProcessDate)=to_date('" + paramString2 + "','yyyy-MM-dd')";
    List localList = localDaoHelper.queryBySQL(str, null);
    long l2 = 0L;
    long l3 = 0L;
    double d1 = 0.0D;
    double d2 = 0.0D;
    Map localMap1;
    if ((localList != null) && (localList.size() > 0))
    {
      localMap1 = (Map)localList.get(0);
      l2 = ((BigDecimal)localMap1.get("SAMOUNT")).longValue();
      d2 = ((BigDecimal)localMap1.get("SETTLEPRICE")).doubleValue();
    }
    str = "select nvl(sum(HoldQty+GageQty),0) bAmount,nvl(max(SettlePrice),0) SettlePrice from t_settleholdposition where settletype<>2 and BS_Flag=1 and commodityid='" + paramString1 + "' and firmId='" + paramString3 + "' and trunc(SettleProcessDate)=to_date('" + paramString2 + "','yyyy-MM-dd')";
    localList = localDaoHelper.queryBySQL(str, null);
    if ((localList != null) && (localList.size() > 0))
    {
      localMap1 = (Map)localList.get(0);
      l3 = ((BigDecimal)localMap1.get("BAMOUNT")).longValue();
      d1 = ((BigDecimal)localMap1.get("SETTLEPRICE")).doubleValue();
    }
    str = "select nvl(sum(Quantity),0) bSettleAmount from T_SettleMatch where matchid not like 'A%' and CommodityID='" + paramString1 + "' and FirmID_B='" + paramString3 + "' and Status<>3 and  settleDate=to_date('" + paramString2 + "','yyyy-MM-dd')";
    localList = localDaoHelper.queryBySQL(str, null);
    long l4 = 0L;
    long l5 = 0L;
    Map localMap2;
    if ((localList != null) && (localList.size() > 0))
    {
      localMap2 = (Map)localList.get(0);
      l4 = ((BigDecimal)localMap2.get("BSETTLEAMOUNT")).longValue();
    }
    str = "select nvl(sum(Quantity),0) sSettleAmount from T_SettleMatch where matchid not like 'A%' and CommodityID='" + paramString1 + "' and FirmID_S='" + paramString4 + "' and Status<>3 and  settleDate=to_date('" + paramString2 + "','yyyy-MM-dd')";
    localList = localDaoHelper.queryBySQL(str, null);
    if ((localList != null) && (localList.size() > 0))
    {
      localMap2 = (Map)localList.get(0);
      l5 = ((BigDecimal)localMap2.get("SSETTLEAMOUNT")).longValue();
    }
    logger.debug("bAmount:" + l3);
    logger.debug("sAmount:" + l2);
    if ((l3 >= paramLong + l4) && (l2 >= paramLong + l5))
    {
      l1 = -1L;
    }
    else if ((l3 < paramLong + l4) && (l2 >= paramLong + l5))
    {
      l1 = l3 - l4;
      if (l1 < 0L) {
        l1 = 0L;
      }
    }
    else if ((l3 >= paramLong + l4) && (l2 < paramLong + l5))
    {
      l1 = l2 - l5;
      if (l1 < 0L) {
        l1 = 0L;
      }
    }
    else if ((l3 < paramLong + l4) && (l2 < paramLong + l5))
    {
      long l6 = l3 - l4;
      long l7 = l2 - l5;
      if (l6 >= l7) {
        l1 = l7;
      } else {
        l1 = l6;
      }
      if (l1 < 0L) {
        l1 = 0L;
      }
    }
    else
    {
      l1 = 0L;
    }
    return l1;
  }
  
  public static int createSettle(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, int paramInt)
  {
    int i = -1;
    long l1 = checkInsertSettle(paramString1, paramString2, paramString3, paramString4, paramLong);
    if (l1 < 0L) {
      try
      {
        DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
        double d1 = 0.0D;
        double d2 = 0.0D;
        double d3 = 0.0D;
        double d4 = 0.0D;
        double d5 = 0.0D;
        double d6 = 0.0D;
        double d7 = 0.0D;
        double d8 = 0.0D;
        String str = "select ContractFactor,SpecSettlePrice from T_SettleCommodity where CommodityID='" + paramString1 + "' and " + "SettleProcessDate =(select max(SettleProcessDate) SettleProcessDate from T_SettleCommodity where CommodityID='" + paramString1 + "')";
        List localList = localDaoHelper.queryBySQL(str, null);
        if ((localList != null) && (localList.size() > 0))
        {
          Map localMap1 = (Map)localList.get(0);
          d6 = ((BigDecimal)localMap1.get("CONTRACTFACTOR")).doubleValue();
        }
        double d9 = 0.0D;
        double d10 = 0.0D;
        long l2 = 0L;
        long l3 = 0L;
        str = "select sum(Payout) Payout,sum(SettleMargin) SettleMargin,sum(HoldQty+GageQty) bamount,max(SettlePrice) SettlePrice from T_SettleHoldPosition where CommodityID='" + paramString1 + "' and BS_Flag=1 and FirmID='" + paramString3 + "' and settletype<>2 and trunc(SettleProcessDate)=to_date('" + paramString2 + "','yyyy-MM-dd')";
        localList = localDaoHelper.queryBySQL(str, null);
        Map localMap2;
        if ((localList != null) && (localList.size() > 0))
        {
          localMap2 = (Map)localList.get(0);
          d9 = ((BigDecimal)localMap2.get("PAYOUT")).doubleValue();
          d10 = ((BigDecimal)localMap2.get("SETTLEMARGIN")).doubleValue();
          l2 = ((BigDecimal)localMap2.get("BAMOUNT")).longValue();
          d7 = ((BigDecimal)localMap2.get("SETTLEPRICE")).doubleValue();
        }
        d3 = d9 / l2 * paramLong;
        d4 = d10 / l2 * paramLong;
        d1 = d7 * paramLong * d6;
        str = "select sum(SettleMargin) SettleMargin,sum(HoldQty+GageQty) samount,max(SettlePrice) SettlePrice from T_SettleHoldPosition where CommodityID='" + paramString1 + "' and BS_Flag=2 and FirmID='" + paramString4 + "' and settletype<>2 and trunc(SettleProcessDate)=to_date('" + paramString2 + "','yyyy-MM-dd')";
        localList = localDaoHelper.queryBySQL(str, null);
        if ((localList != null) && (localList.size() > 0))
        {
          localMap2 = (Map)localList.get(0);
          d10 = ((BigDecimal)localMap2.get("SETTLEMARGIN")).doubleValue();
          l3 = ((BigDecimal)localMap2.get("SAMOUNT")).longValue();
          d8 = ((BigDecimal)localMap2.get("SETTLEPRICE")).doubleValue();
        }
        d5 = d10 / l3 * paramLong;
        d2 = d8 * paramLong * d6;
        str = "insert into T_SettleMatch (MatchID,CommodityID,ContractFactor,Quantity,HL_Amount,Status,Result,FirmID_B,BuyPrice,BuyPayout_Ref,BuyPayout,BuyMargin,TakePenalty_B,PayPenalty_B,SettlePL_B,FirmID_S,SellPrice,SellIncome_Ref,SellIncome,SellMargin,TakePenalty_S,PayPenalty_S,SettlePL_S,CreateTime,ModifyTime,settleDate) values ('TS_'||seq_T_SettleMatch.nextVal,'" + paramString1 + "'," + d6 + "," + paramLong + ",0,0," + paramInt + ",'" + paramString3 + "'," + d7 + "," + d1 + "," + d3 + "," + d4 + ",0,0,0," + "'" + paramString4 + "'," + d8 + "," + d2 + ",0," + d5 + ",0,0,0,sysdate,sysdate,to_date('" + paramString2 + "','yyyy-MM-dd'))";
        localDaoHelper.updateBySQL(str);
        i = 1;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        i = -2;
      }
    } else {
      i = -1;
    }
    return i;
  }
  
  public static int inoutSettleMoney(String paramString, double paramDouble, boolean paramBoolean)
    throws SQLException
  {
    int i = -1;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
      DataSource localDataSource = localDaoHelper.getDataSource();
      localConnection = localDataSource.getConnection();
      localConnection.setAutoCommit(false);
      String str1 = "";
      String str2 = "";
      String str3 = "";
      double d1 = 0.0D;
      double d2 = 0.0D;
      int j = -1;
      int k = -1;
      double d3 = 0.0D;
      double d4 = 0.0D;
      double d5 = 0.0D;
      String str4 = "select * from T_SettleMatch where MatchID='" + paramString + "'";
      localPreparedStatement = localConnection.prepareStatement(str4);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        str1 = localResultSet.getString("COMMODITYID");
        str2 = localResultSet.getString("FIRMID_B");
        str3 = localResultSet.getString("FIRMID_S");
        j = localResultSet.getInt("STATUS");
        d1 = localResultSet.getDouble("BUYPAYOUT");
        d2 = localResultSet.getDouble("SELLINCOME");
        d3 = localResultSet.getDouble("BUYPAYOUT_REF");
        d4 = localResultSet.getDouble("SELLINCOME_REF");
        d5 = localResultSet.getDouble("HL_AMOUNT");
        k = localResultSet.getInt("RESULT");
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      if ((j == 3) && (j == 2))
      {
        i = -1;
      }
      else if (k != 1)
      {
        i = -3;
      }
      else
      {
        boolean bool = false;
        String str5 = "";
        String str6 = "";
        double d6 = d4 - d3;
        if (paramBoolean)
        {
          str5 = str2;
          str6 = "208";
          if ((d1 + paramDouble + d6 >= d2) && (d1 + paramDouble >= 0.0D))
          {
            bool = true;
          }
          else
          {
            i = -4;
            if (d1 + paramDouble < 0.0D) {
              i = -6;
            }
          }
          d1 += paramDouble;
        }
        else
        {
          str5 = str3;
          str6 = "209";
          logger.debug("buyPayout:" + d1);
          logger.debug("marketPayMent:" + d6);
          logger.debug("sellIncome:" + d2);
          if ((d1 + d6 >= d2 + paramDouble) && (d2 + paramDouble >= 0.0D))
          {
            bool = true;
          }
          else
          {
            i = -4;
            if (d2 + paramDouble < 0.0D) {
              i = -6;
            }
          }
          if ((d4 + d5 >= d2 + paramDouble) && (bool))
          {
            bool = true;
          }
          else if (bool)
          {
            i = -5;
            bool = false;
          }
          d2 += paramDouble;
        }
        logger.debug("s:" + bool);
        if (bool)
        {
          logger.debug("{?=call FN_F_UpdateFundsFull('" + str5 + "','" + str6 + "'," + paramDouble + ",'" + paramString + "','" + str1 + "',null,null)}");
          CallableStatement localCallableStatement = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str5 + "','" + str6 + "'," + paramDouble + ",'" + paramString + "','" + str1 + "',null,null)}");
          localCallableStatement.registerOutParameter(1, 8);
          localCallableStatement.executeUpdate();
          if (j == 0) {
            j = 1;
          }
          str4 = "update T_SettleMatch set status=" + j + ",BuyPayout=" + d1 + ",sellIncome=" + d2 + ",ModifyTime=sysdate where MatchID='" + paramString + "'";
          localPreparedStatement = localConnection.prepareStatement(str4);
          localPreparedStatement.executeUpdate();
          localPreparedStatement.close();
          localPreparedStatement = null;
          i = 1;
        }
      }
      localConnection.commit();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localConnection.rollback();
      i = -2;
    }
    finally
    {
      localConnection.setAutoCommit(true);
      if (localResultSet != null)
      {
        localResultSet.close();
        localResultSet = null;
      }
      if (localPreparedStatement != null)
      {
        localPreparedStatement.close();
        localPreparedStatement = null;
      }
      if (localConnection != null)
      {
        localConnection.close();
        localConnection = null;
      }
    }
    return i;
  }
  
  public static int marginTurnGoodsPayment(String paramString, double paramDouble)
    throws SQLException
  {
    int i = -1;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
      DataSource localDataSource = localDaoHelper.getDataSource();
      localConnection = localDataSource.getConnection();
      localConnection.setAutoCommit(false);
      String str1 = "";
      String str2 = "";
      String str3 = "";
      double d1 = 0.0D;
      double d2 = 0.0D;
      int j = -1;
      int k = -1;
      double d3 = 0.0D;
      double d4 = 0.0D;
      double d5 = 0.0D;
      String str4 = "select * from T_SettleMatch where MatchID='" + paramString + "'";
      localPreparedStatement = localConnection.prepareStatement(str4);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        str1 = localResultSet.getString("COMMODITYID");
        str2 = localResultSet.getString("FIRMID_B");
        str3 = localResultSet.getString("FIRMID_S");
        j = localResultSet.getInt("STATUS");
        d1 = localResultSet.getDouble("BUYPAYOUT");
        d2 = localResultSet.getDouble("SELLINCOME");
        d3 = localResultSet.getDouble("BUYPAYOUT_REF");
        d4 = localResultSet.getDouble("SELLINCOME_REF");
        d5 = localResultSet.getDouble("BUYMARGIN");
        k = localResultSet.getInt("RESULT");
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      if ((j == 3) && (j == 2))
      {
        i = -1;
      }
      else if (k != 1)
      {
        i = -3;
      }
      else
      {
        double d6 = d4 - d3;
        if ((d5 - paramDouble >= 0.0D) && (d1 + paramDouble + d6 >= d2))
        {
          d5 -= paramDouble;
          logger.debug("buyMargin:" + d5);
          str4 = "update T_SettleMatch set ModifyTime=sysdate,buyMargin=" + d5 + " where MatchID='" + paramString + "'";
          logger.debug(str4);
          localPreparedStatement = localConnection.prepareStatement(str4);
          localPreparedStatement.executeUpdate();
          localPreparedStatement.close();
          localPreparedStatement = null;
          str4 = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-(" + paramDouble + ") where firmId='" + str2 + "'";
          logger.debug(str4);
          localPreparedStatement = localConnection.prepareStatement(str4);
          localPreparedStatement.executeUpdate();
          localPreparedStatement.close();
          localPreparedStatement = null;
          CallableStatement localCallableStatement1 = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str2 + "','214'," + paramDouble + ",'" + paramString + "','" + str1 + "',null,null)}");
          localCallableStatement1.registerOutParameter(1, 8);
          localCallableStatement1.executeUpdate();
          String str5 = "";
          String str6 = "";
          str5 = str2;
          str6 = "208";
          d1 += paramDouble;
          CallableStatement localCallableStatement2 = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str5 + "','" + str6 + "'," + paramDouble + ",'" + paramString + "','" + str1 + "',null,null)}");
          localCallableStatement2.registerOutParameter(1, 8);
          localCallableStatement2.executeUpdate();
          if (j == 0) {
            j = 1;
          }
          str4 = "update T_SettleMatch set status=" + j + ",BuyPayout=" + d1 + ",ModifyTime=sysdate where MatchID='" + paramString + "'";
          logger.debug(str4);
          localPreparedStatement = localConnection.prepareStatement(str4);
          localPreparedStatement.executeUpdate();
          localPreparedStatement.close();
          localPreparedStatement = null;
          i = 1;
        }
        else
        {
          logger.debug("buyPayout+marginTurnMoney>0:" + (d1 + paramDouble > 0.0D));
          logger.debug("buyPayout+marginTurnMoney+marketPayMent>=sellIncome:" + (d1 + paramDouble + d6 >= d2));
          if (d5 - paramDouble < 0.0D) {
            i = -4;
          } else if (d1 + paramDouble + d6 < d2) {
            i = -5;
          }
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localConnection.rollback();
      i = -2;
    }
    finally
    {
      localConnection.setAutoCommit(true);
      if (localResultSet != null)
      {
        localResultSet.close();
        localResultSet = null;
      }
      if (localPreparedStatement != null)
      {
        localPreparedStatement.close();
        localPreparedStatement = null;
      }
      if (localConnection != null)
      {
        localConnection.close();
        localConnection = null;
      }
    }
    return i;
  }
  
  public static int cancelSettle(String paramString)
    throws SQLException
  {
    int i = -1;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
      DataSource localDataSource = localDaoHelper.getDataSource();
      localConnection = localDataSource.getConnection();
      localConnection.setAutoCommit(false);
      String str1 = "";
      String str2 = "";
      String str3 = "";
      double d1 = 0.0D;
      double d2 = 0.0D;
      int j = -1;
      int k = -1;
      double d3 = 0.0D;
      double d4 = 0.0D;
      double d5 = 0.0D;
      double d6 = 0.0D;
      double d7 = 0.0D;
      double d8 = 0.0D;
      String str4 = "select * from T_SettleMatch where MatchID='" + paramString + "' for update";
      localPreparedStatement1 = localConnection.prepareStatement(str4);
      localResultSet = localPreparedStatement1.executeQuery();
      if (localResultSet.next())
      {
        str1 = localResultSet.getString("commodityId");
        str2 = localResultSet.getString("FirmID_B");
        str3 = localResultSet.getString("FirmID_S");
        j = localResultSet.getInt("Status");
        d1 = localResultSet.getDouble("BuyPayout");
        d2 = localResultSet.getDouble("sellIncome");
        k = localResultSet.getInt("result");
        d3 = localResultSet.getDouble("takePenalty_B");
        d4 = localResultSet.getDouble("payPenalty_B");
        d5 = localResultSet.getDouble("takePenalty_S");
        d6 = localResultSet.getDouble("payPenalty_S");
        d7 = localResultSet.getDouble("buyMargin");
        d8 = localResultSet.getDouble("sellMargin");
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement1.close();
      localPreparedStatement1 = null;
      if ((j != 3) && (j != 2))
      {
        if (j != 0)
        {
          str4 = "select fundflowid,firmid,oprcode,amount from  (select t.fundflowid,t.firmid,t.oprcode,t.amount from f_fundflow t where t.contractno='" + paramString + "') " + "union " + "(select t.fundflowid,t.firmid,t.oprcode,t.amount from f_h_fundflow t where t.contractno='" + paramString + "') " + "order by fundflowid";
          logger.debug(str4);
          localPreparedStatement1 = localConnection.prepareStatement(str4);
          localResultSet = localPreparedStatement1.executeQuery();
          while (localResultSet.next())
          {
            String str5 = localResultSet.getString("FIRMID");
            String str6 = localResultSet.getString("OPRCODE");
            double d9 = localResultSet.getDouble("AMOUNT");
            logger.debug("{?=call FN_F_UpdateFundsFull('" + str5 + "','" + str6 + "'," + -d9 + ",'" + paramString + "','" + str1 + "',null,null)}");
            CallableStatement localCallableStatement = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str5 + "','" + str6 + "'," + -d9 + ",'" + paramString + "','" + str1 + "',null,null)}");
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            if ("214".equals(str6))
            {
              str4 = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin+" + d9 + " where firmId='" + str5 + "'";
              logger.debug(str4);
              localPreparedStatement2 = localConnection.prepareStatement(str4);
              localPreparedStatement2.executeUpdate();
              localPreparedStatement2.close();
              localPreparedStatement2 = null;
            }
          }
          localResultSet.close();
          localResultSet = null;
          localPreparedStatement1.close();
          localPreparedStatement1 = null;
        }
        str4 = "update T_SettleMatch set ModifyTime=sysdate,status=3 where MatchID='" + paramString + "'";
        localPreparedStatement1 = localConnection.prepareStatement(str4);
        localPreparedStatement1.executeUpdate();
        localPreparedStatement1.close();
        localPreparedStatement1 = null;
        i = 1;
      }
      else
      {
        i = -1;
      }
      localConnection.commit();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localConnection.rollback();
      i = -2;
    }
    finally
    {
      localConnection.setAutoCommit(true);
      if (localResultSet != null)
      {
        localResultSet.close();
        localResultSet = null;
      }
      if (localPreparedStatement1 != null)
      {
        localPreparedStatement1.close();
        localPreparedStatement1 = null;
      }
      if (localPreparedStatement2 != null)
      {
        localPreparedStatement2.close();
        localPreparedStatement2 = null;
      }
      if (localConnection != null)
      {
        localConnection.close();
        localConnection = null;
      }
    }
    return i;
  }
  
  public static int receiveOrPayPenalty(String paramString, double paramDouble, boolean paramBoolean1, boolean paramBoolean2)
    throws SQLException
  {
    int i = 0;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
      DataSource localDataSource = localDaoHelper.getDataSource();
      localConnection = localDataSource.getConnection();
      localConnection.setAutoCommit(false);
      String str1 = "";
      String str2 = "";
      String str3 = "";
      double d1 = 0.0D;
      double d2 = 0.0D;
      int j = -1;
      int k = -1;
      double d3 = 0.0D;
      double d4 = 0.0D;
      double d5 = 0.0D;
      double d6 = 0.0D;
      double d7 = 0.0D;
      double d8 = 0.0D;
      String str4 = "select * from T_SettleMatch where MatchID='" + paramString + "' for update";
      localPreparedStatement = localConnection.prepareStatement(str4);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        str1 = localResultSet.getString("commodityId");
        str2 = localResultSet.getString("FirmID_B");
        str3 = localResultSet.getString("FirmID_S");
        j = localResultSet.getInt("Status");
        d1 = localResultSet.getDouble("BuyPayout");
        d2 = localResultSet.getDouble("sellIncome");
        k = localResultSet.getInt("result");
        d3 = localResultSet.getDouble("takePenalty_B");
        d4 = localResultSet.getDouble("payPenalty_B");
        d5 = localResultSet.getDouble("takePenalty_S");
        d6 = localResultSet.getDouble("payPenalty_S");
        d7 = localResultSet.getDouble("buyMargin");
        d8 = localResultSet.getDouble("sellMargin");
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      if ((j != 3) && (j != 2))
      {
        if (k != 1)
        {
          if (d2 == 0.0D)
          {
            if ((k == 2) && (((paramBoolean2) && (!paramBoolean1)) || ((!paramBoolean2) && (paramBoolean1))))
            {
              logger.debug("1");
              logger.debug("sign:" + paramBoolean1 + "  ");
              logger.debug(paramBoolean2 ? "买方" : "卖方");
              logger.debug("sign1:" + paramBoolean2 + "  ");
              logger.debug(paramBoolean1 ? "收" : "付");
              i = -3;
            }
            else if ((k == 3) && (((paramBoolean1) && (paramBoolean2)) || ((!paramBoolean1) && (!paramBoolean2))))
            {
              i = -3;
            }
            else if ((k == 4) && (!paramBoolean1))
            {
              i = -3;
            }
            else
            {
              String str5 = "";
              String str6 = "";
              if (paramBoolean1)
              {
                double d9 = 0.0D;
                str5 = "217";
                if (paramBoolean2)
                {
                  str6 = str2;
                  d9 = d7;
                  d7 = 0.0D;
                  d3 += paramDouble;
                  if (d3 < 0.0D) {
                    i = -4;
                  }
                }
                else
                {
                  str6 = str3;
                  d9 = d8;
                  d8 = 0.0D;
                  d5 += paramDouble;
                  if (d5 < 0.0D) {
                    i = -4;
                  }
                }
                if (i == 0)
                {
                  str4 = "update T_SettleMatch set ModifyTime=sysdate,buyMargin=" + d7 + ",sellMargin=" + d8 + " where MatchID='" + paramString + "'";
                  logger.debug(str4);
                  localPreparedStatement = localConnection.prepareStatement(str4);
                  localPreparedStatement.executeUpdate();
                  localPreparedStatement.close();
                  localPreparedStatement = null;
                  str4 = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-(" + d9 + ") where firmId='" + str6 + "'";
                  logger.debug(str4);
                  localPreparedStatement = localConnection.prepareStatement(str4);
                  localPreparedStatement.executeUpdate();
                  localPreparedStatement.close();
                  localPreparedStatement = null;
                  CallableStatement localCallableStatement2 = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str6 + "','214'," + d9 + ",'" + paramString + "','" + str1 + "',null,null)}");
                  localCallableStatement2.registerOutParameter(1, 8);
                  localCallableStatement2.executeUpdate();
                }
              }
              else
              {
                str5 = "218";
                if (paramBoolean2)
                {
                  str6 = str2;
                  d4 += paramDouble;
                  if (d4 < 0.0D) {
                    i = -4;
                  }
                }
                else
                {
                  str6 = str3;
                  d6 += paramDouble;
                  if (d6 < 0.0D) {
                    i = -4;
                  }
                }
              }
              if (i == 0)
              {
                if (j == 0) {
                  j = 1;
                }
                str4 = "update T_SettleMatch set ModifyTime=sysdate,takePenalty_B=" + d3 + ",payPenalty_B=" + d4 + ",takePenalty_S=" + d5 + ",payPenalty_S=" + d6 + ",status=" + j + " where MatchID='" + paramString + "'";
                logger.debug(str4);
                localPreparedStatement = localConnection.prepareStatement(str4);
                localPreparedStatement.executeUpdate();
                localPreparedStatement.close();
                localPreparedStatement = null;
                CallableStatement localCallableStatement1 = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str6 + "','" + str5 + "'," + paramDouble + ",'" + paramString + "','" + str1 + "',null,null)}");
                localCallableStatement1.registerOutParameter(1, 8);
                localCallableStatement1.executeUpdate();
                i = 1;
              }
            }
          }
          else {
            i = -3;
          }
        }
        else {
          i = -3;
        }
      }
      else {
        i = -1;
      }
      localConnection.commit();
    }
    catch (Exception localException)
    {
      localConnection.rollback();
      i = -2;
      localException.printStackTrace();
    }
    finally
    {
      localConnection.setAutoCommit(true);
      if (localResultSet != null)
      {
        localResultSet.close();
        localResultSet = null;
      }
      if (localPreparedStatement != null)
      {
        localPreparedStatement.close();
        localPreparedStatement = null;
      }
      if (localConnection != null)
      {
        localConnection.close();
        localConnection = null;
      }
    }
    return i;
  }
  
  public static int addSettlePL(String paramString, double paramDouble, boolean paramBoolean)
    throws SQLException
  {
    int i = -1;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
      DataSource localDataSource = localDaoHelper.getDataSource();
      localConnection = localDataSource.getConnection();
      localConnection.setAutoCommit(false);
      String str1 = "";
      String str2 = "";
      String str3 = "";
      double d1 = 0.0D;
      double d2 = 0.0D;
      int j = -1;
      int k = -1;
      double d3 = 0.0D;
      double d4 = 0.0D;
      double d5 = 0.0D;
      double d6 = 0.0D;
      double d7 = 0.0D;
      double d8 = 0.0D;
      double d9 = 0.0D;
      double d10 = 0.0D;
      String str4 = "select * from T_SettleMatch where MatchID='" + paramString + "' for update";
      localPreparedStatement = localConnection.prepareStatement(str4);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        str1 = localResultSet.getString("commodityId");
        str2 = localResultSet.getString("FirmID_B");
        str3 = localResultSet.getString("FirmID_S");
        j = localResultSet.getInt("Status");
        d1 = localResultSet.getDouble("BuyPayout");
        d2 = localResultSet.getDouble("sellIncome");
        k = localResultSet.getInt("result");
        d3 = localResultSet.getDouble("takePenalty_B");
        d4 = localResultSet.getDouble("payPenalty_B");
        d5 = localResultSet.getDouble("takePenalty_S");
        d6 = localResultSet.getDouble("payPenalty_S");
        d7 = localResultSet.getDouble("buyMargin");
        d8 = localResultSet.getDouble("sellMargin");
        d9 = localResultSet.getDouble("settlePL_B");
        d10 = localResultSet.getDouble("settlePL_S");
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      if ((j != 3) && (j != 2) && (paramDouble != 0.0D))
      {
        if (k != 1)
        {
          if (d2 == 0.0D)
          {
            String str5 = "";
            String str6 = "";
            if (paramBoolean)
            {
              str5 = str2;
              d9 += paramDouble;
            }
            else
            {
              str5 = str3;
              d10 += paramDouble;
            }
            if (paramDouble > 0.0D)
            {
              str6 = "211";
            }
            else
            {
              str6 = "212";
              paramDouble = -paramDouble;
            }
            CallableStatement localCallableStatement = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str5 + "','" + str6 + "'," + paramDouble + ",'" + paramString + "','" + str1 + "',null,null)}");
            localCallableStatement.registerOutParameter(1, 8);
            localCallableStatement.executeUpdate();
            if (j == 0) {
              j = 1;
            }
            str4 = "update T_SettleMatch set ModifyTime=sysdate,status=" + j + ",settlePL_B=" + d9 + ",settlePL_S=" + d10 + " where MatchID='" + paramString + "'";
            localPreparedStatement = localConnection.prepareStatement(str4);
            localPreparedStatement.executeUpdate();
            localPreparedStatement.close();
            localPreparedStatement = null;
            i = 1;
          }
          else
          {
            i = -3;
          }
        }
        else {
          i = -3;
        }
      }
      else {
        i = -1;
      }
      localConnection.commit();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localConnection.rollback();
      i = -2;
    }
    finally
    {
      localConnection.setAutoCommit(true);
      if (localResultSet != null)
      {
        localResultSet.close();
        localResultSet = null;
      }
      if (localPreparedStatement != null)
      {
        localPreparedStatement.close();
        localPreparedStatement = null;
      }
      if (localConnection != null)
      {
        localConnection.close();
        localConnection = null;
      }
    }
    return i;
  }
  
  public static int HLSettle(String paramString, double paramDouble, boolean paramBoolean)
    throws SQLException
  {
    int i = -1;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
      DataSource localDataSource = localDaoHelper.getDataSource();
      localConnection = localDataSource.getConnection();
      localConnection.setAutoCommit(false);
      String str1 = "";
      String str2 = "";
      String str3 = "";
      double d1 = 0.0D;
      double d2 = 0.0D;
      int j = -1;
      int k = -1;
      double d3 = 0.0D;
      double d4 = 0.0D;
      double d5 = 0.0D;
      double d6 = 0.0D;
      double d7 = 0.0D;
      double d8 = 0.0D;
      double d9 = 0.0D;
      double d10 = 0.0D;
      double d11 = 0.0D;
      double d12 = 0.0D;
      double d13 = 0.0D;
      String str4 = "select * from T_SettleMatch where MatchID='" + paramString + "' for update";
      localPreparedStatement = localConnection.prepareStatement(str4);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        str1 = localResultSet.getString("COMMODITYID");
        str2 = localResultSet.getString("FIRMID_B");
        str3 = localResultSet.getString("FIRMID_S");
        j = localResultSet.getInt("STATUS");
        d1 = localResultSet.getDouble("BUYPAYOUT");
        d2 = localResultSet.getDouble("SELLINCOME");
        k = localResultSet.getInt("RESULT");
        d3 = localResultSet.getDouble("TAKEPENALTY_B");
        d4 = localResultSet.getDouble("PAYPENALTY_B");
        d5 = localResultSet.getDouble("TAKEPENALTY_S");
        d6 = localResultSet.getDouble("PAYPENALTY_S");
        d7 = localResultSet.getDouble("BUYMARGIN");
        d8 = localResultSet.getDouble("SELLMARGIN");
        d9 = localResultSet.getDouble("SETTLEPL_B");
        d10 = localResultSet.getDouble("SETTLEPL_S");
        d11 = localResultSet.getDouble("HL_AMOUNT");
        d12 = localResultSet.getDouble("BUYPAYOUT_REF");
        d13 = localResultSet.getDouble("SELLINCOME_REF");
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      if ((j != 3) && (j != 2))
      {
        if (k == 1)
        {
          double d14 = d13 - d12;
          logger.debug("SellIncome_Ref:" + d13);
          logger.debug("HL_Amount:" + d11);
          logger.debug("sellIncome:" + d2);
          if (d13 + d11 + paramDouble < d2)
          {
            i = -4;
          }
          else
          {
            if (j == 0) {
              j = 1;
            }
            if (paramBoolean) {
              d11 += paramDouble;
            } else {
              d11 -= paramDouble;
            }
            str4 = "update T_SettleMatch set ModifyTime=sysdate,status=" + j + ",HL_Amount=" + d11 + " where MatchID='" + paramString + "'";
            localPreparedStatement = localConnection.prepareStatement(str4);
            localPreparedStatement.executeUpdate();
            localPreparedStatement.close();
            localPreparedStatement = null;
            i = 1;
          }
        }
        else
        {
          i = -3;
        }
      }
      else {
        i = -1;
      }
      localConnection.commit();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localConnection.rollback();
      i = -2;
    }
    finally
    {
      localConnection.setAutoCommit(true);
      if (localResultSet != null)
      {
        localResultSet.close();
        localResultSet = null;
      }
      if (localPreparedStatement != null)
      {
        localPreparedStatement.close();
        localPreparedStatement = null;
      }
      if (localConnection != null)
      {
        localConnection.close();
        localConnection = null;
      }
    }
    return i;
  }
  
  public static int finshSettle(String paramString)
    throws SQLException
  {
    int i = -1;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    try
    {
      DaoHelper localDaoHelper = (DaoHelper)SysData.getBean("daoHelper");
      DataSource localDataSource = localDaoHelper.getDataSource();
      localConnection = localDataSource.getConnection();
      localConnection.setAutoCommit(false);
      String str1 = "";
      String str2 = "";
      String str3 = "";
      double d1 = 0.0D;
      double d2 = 0.0D;
      int j = -1;
      int k = -1;
      double d3 = 0.0D;
      double d4 = 0.0D;
      double d5 = 0.0D;
      double d6 = 0.0D;
      double d7 = 0.0D;
      double d8 = 0.0D;
      double d9 = 0.0D;
      double d10 = 0.0D;
      double d11 = 0.0D;
      double d12 = 0.0D;
      double d13 = 0.0D;
      String str4 = "select * from T_SettleMatch where MatchID='" + paramString + "' for update";
      localPreparedStatement = localConnection.prepareStatement(str4);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        str1 = localResultSet.getString("COMMODITYID");
        str2 = localResultSet.getString("FIRMID_B");
        str3 = localResultSet.getString("FIRMID_S");
        j = localResultSet.getInt("STATUS");
        d1 = localResultSet.getDouble("BUYPAYOUT");
        d2 = localResultSet.getDouble("SELLINCOME");
        k = localResultSet.getInt("RESULT");
        d3 = localResultSet.getDouble("TAKEPENALTY_B");
        d4 = localResultSet.getDouble("PAYPENALTY_B");
        d5 = localResultSet.getDouble("TAKEPENALTY_S");
        d6 = localResultSet.getDouble("PAYPENALTY_S");
        d7 = localResultSet.getDouble("BUYMARGIN");
        d8 = localResultSet.getDouble("SELLMARGIN");
        d9 = localResultSet.getDouble("SETTLEPL_B");
        d10 = localResultSet.getDouble("SETTLEPL_S");
        d11 = localResultSet.getDouble("HL_AMOUNT");
        d12 = localResultSet.getDouble("BUYPAYOUT_REF");
        d13 = localResultSet.getDouble("SELLINCOME_REF");
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      if ((j != 3) && (j != 2))
      {
        if (k != 1)
        {
          CallableStatement localCallableStatement1;
          if (d7 > 0.0D)
          {
            logger.debug("buyMargin:" + d7);
            str4 = "update T_SettleMatch set ModifyTime=sysdate,buyMargin=0 where MatchID='" + paramString + "'";
            localPreparedStatement = localConnection.prepareStatement(str4);
            localPreparedStatement.executeUpdate();
            localPreparedStatement.close();
            localPreparedStatement = null;
            str4 = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-" + d7 + " where firmId='" + str2 + "'";
            localPreparedStatement = localConnection.prepareStatement(str4);
            localPreparedStatement.executeUpdate();
            localPreparedStatement.close();
            localPreparedStatement = null;
            localCallableStatement1 = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str2 + "','214'," + d7 + ",'" + paramString + "','" + str1 + "',null,null)}");
            localCallableStatement1.registerOutParameter(1, 8);
            localCallableStatement1.executeUpdate();
          }
          if (d8 > 0.0D)
          {
            logger.debug("sellMargin:" + d8);
            str4 = "update T_SettleMatch set ModifyTime=sysdate,sellMargin=0 where MatchID='" + paramString + "'";
            localPreparedStatement = localConnection.prepareStatement(str4);
            localPreparedStatement.executeUpdate();
            localPreparedStatement.close();
            localPreparedStatement = null;
            str4 = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-" + d8 + " where firmId='" + str3 + "'";
            localPreparedStatement = localConnection.prepareStatement(str4);
            localPreparedStatement.executeUpdate();
            localPreparedStatement.close();
            localPreparedStatement = null;
            localCallableStatement1 = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str3 + "','214'," + d8 + ",'" + paramString + "','" + str1 + "',null,null)}");
            localCallableStatement1.registerOutParameter(1, 8);
            localCallableStatement1.executeUpdate();
          }
          if (d1 != 0.0D)
          {
            logger.debug("buyPayout:" + d1);
            d1 = -d1;
            logger.debug("{?=call FN_F_UpdateFundsFull('" + str2 + "','208'," + d1 + ",'" + paramString + "','" + str1 + "',null,null)}");
            localCallableStatement1 = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str2 + "','208'," + d1 + ",'" + paramString + "','" + str1 + "',null,null)}");
            localCallableStatement1.registerOutParameter(1, 8);
            localCallableStatement1.executeUpdate();
            str4 = "update T_SettleMatch set BuyPayout=0,ModifyTime=sysdate where MatchID='" + paramString + "'";
            localPreparedStatement = localConnection.prepareStatement(str4);
            localPreparedStatement.executeUpdate();
            localPreparedStatement.close();
            localPreparedStatement = null;
          }
          str4 = "update T_SettleMatch set ModifyTime=sysdate,status=2 where MatchID='" + paramString + "'";
          localPreparedStatement = localConnection.prepareStatement(str4);
          localPreparedStatement.executeUpdate();
          localPreparedStatement.close();
          localPreparedStatement = null;
          i = 1;
        }
        else
        {
          double d14 = d13 - d12;
          if (d13 + d11 != d2)
          {
            logger.debug("SellIncome_Ref:" + d13);
            logger.debug("HL_Amount:" + d11);
            logger.debug("sellIncome:" + d2);
            i = -3;
          }
          else if (d2 != d12 + d11 + d14)
          {
            i = -4;
          }
          else if (d1 + d14 < d2)
          {
            i = -4;
          }
          else
          {
            CallableStatement localCallableStatement2;
            if (d7 > 0.0D)
            {
              logger.debug("buyMargin:" + d7);
              str4 = "update T_SettleMatch set ModifyTime=sysdate,buyMargin=0 where MatchID='" + paramString + "'";
              localPreparedStatement = localConnection.prepareStatement(str4);
              localPreparedStatement.executeUpdate();
              localPreparedStatement.close();
              localPreparedStatement = null;
              str4 = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-" + d7 + " where firmId='" + str2 + "'";
              localPreparedStatement = localConnection.prepareStatement(str4);
              localPreparedStatement.executeUpdate();
              localPreparedStatement.close();
              localPreparedStatement = null;
              localCallableStatement2 = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str2 + "','214'," + d7 + ",'" + paramString + "','" + str1 + "',null,null)}");
              localCallableStatement2.registerOutParameter(1, 8);
              localCallableStatement2.executeUpdate();
            }
            if (d8 > 0.0D)
            {
              logger.debug("sellMargin:" + d8);
              str4 = "update T_SettleMatch set ModifyTime=sysdate,sellMargin=0 where MatchID='" + paramString + "'";
              localPreparedStatement = localConnection.prepareStatement(str4);
              localPreparedStatement.executeUpdate();
              localPreparedStatement.close();
              localPreparedStatement = null;
              str4 = "update T_Firm set RuntimeSettleMargin=RuntimeSettleMargin-" + d8 + " where firmId='" + str3 + "'";
              localPreparedStatement = localConnection.prepareStatement(str4);
              localPreparedStatement.executeUpdate();
              localPreparedStatement.close();
              localPreparedStatement = null;
              localCallableStatement2 = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str3 + "','214'," + d8 + ",'" + paramString + "','" + str1 + "',null,null)}");
              localCallableStatement2.registerOutParameter(1, 8);
              localCallableStatement2.executeUpdate();
            }
            if (d1 + d14 != d2)
            {
              double d15 = d1 + d14 - d2;
              logger.debug("returnPayMent:" + d15);
              str4 = "update T_SettleMatch set ModifyTime=sysdate,buyPayout=buyPayout-" + d15 + " where MatchID='" + paramString + "'";
              localPreparedStatement = localConnection.prepareStatement(str4);
              localPreparedStatement.executeUpdate();
              localPreparedStatement.close();
              localPreparedStatement = null;
              d15 = -d15;
              CallableStatement localCallableStatement3 = localConnection.prepareCall("{?=call FN_F_UpdateFundsFull('" + str2 + "','208'," + d15 + ",'" + paramString + "','" + str1 + "',null,null)}");
              localCallableStatement3.registerOutParameter(1, 8);
              localCallableStatement3.executeUpdate();
            }
            str4 = "update T_SettleMatch set ModifyTime=sysdate,status=2 where MatchID='" + paramString + "'";
            localPreparedStatement = localConnection.prepareStatement(str4);
            localPreparedStatement.executeUpdate();
            localPreparedStatement.close();
            localPreparedStatement = null;
            i = 1;
          }
        }
      }
      else {
        i = -1;
      }
      localConnection.commit();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localConnection.rollback();
      i = -2;
    }
    finally
    {
      localConnection.setAutoCommit(true);
      if (localResultSet != null)
      {
        localResultSet.close();
        localResultSet = null;
      }
      if (localPreparedStatement != null)
      {
        localPreparedStatement.close();
        localPreparedStatement = null;
      }
      if (localConnection != null)
      {
        localConnection.close();
        localConnection = null;
      }
    }
    return i;
  }
}
