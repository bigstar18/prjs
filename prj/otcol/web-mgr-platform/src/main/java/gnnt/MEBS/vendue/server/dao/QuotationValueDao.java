package gnnt.MEBS.vendue.server.dao;

import gnnt.MEBS.vendue.server.beans.busbeans.CountTime;
import gnnt.MEBS.vendue.server.beans.busbeans.PartitionQuotation;
import gnnt.MEBS.vendue.server.beans.dtobeans.Commodity;
import gnnt.MEBS.vendue.server.beans.dtobeans.CurCommodity;
import gnnt.MEBS.vendue.server.beans.dtobeans.QuotationValue;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class QuotationValueDao
  extends BaseDao
{
  private Long newestSequenceId = null;
  private List partitionValueList = null;
  private static String sqlFetchTradePartitionQuotation = "select A.tradePartition, A.ID, A.submitID, A.code, A.price, A.section, A.vaidAmount, A.userID, A.lastTime, A.countdownStart, A.countdownTime, B.status, B.starttime, B.endtime, C.commodityID, C.section as curCommoditySection, C.lpFlag, C.bargainFlag, C.modifyTime, D.ID as commodityId, D.code as commodityCode, D.firstTime, D.createTime, D.status, D.splitID, D.beginPrice, D.stepPrice, D.amount, D.tradeUnit, D.alertPrice, D.security, D.fee, D.minAmount from v_tradeQuotation A, v_sysCurStatus B, v_curCommodity C, v_commodity D where A.tradePartition = ? and B.tradePartition = A.tradePartition and C.tradePartition = A.tradePartition and C.code = A.code and D.ID = C.commodityID ";
  private static String sqlFetchCommodityInTradePartitionQuotation = "select A.tradePartition, A.ID, A.submitID, A.code, A.price, A.section, A.vaidAmount, A.userID, A.lastTime, A.countdownStart, A.countdownTime, B.status, B.starttime, B.endtime, C.commodityID, C.section as curCommoditySection, C.lpFlag, C.bargainFlag, C.modifyTime, D.ID as commodityId, D.code as commodityCode, D.firstTime, D.createTime, D.status, D.splitID, D.beginPrice, D.stepPrice, D.amount, D.tradeUnit, D.alertPrice, D.security, D.fee, D.minAmount from v_tradeQuotation A, v_sysCurStatus B, v_curCommodity C, v_commodity D where A.tradePartition = ? and A.code = ? and B.tradePartition = A.tradePartition and C.tradePartition = A.tradePartition and C.code = A.code and D.ID = C.commodityID and ROWNUM <= 1 order by A.ID desc";
  private static String sqlGetAllPartitionAndAllNewQuotationWithoutLastSeq = "select B.tradePartition, B.section as partitionSection, B.status, B.starttime, B.endtime, A.ID, A.submitID, A.code, A.price, A.vaidAmount, A.userID, A.lastTime, A.countdownStart, A.countdownTime, A.section, (select sysdate from dual) as sysdateWhenAccessing from v_tradeQuotation A, v_sysCurStatus B  where B.tradePartition = A.tradePartition and A.id in( select max(id) from v_tradeQuotation  group by tradePartition, code, section)  union  select B.tradePartition, B.section as partitionSection, B.status, B.starttime, B.endtime,  null as ID, null as submitID, null as code, null as price,  null as vaidAmount, null as userID, null as lastTime, null as countdownStart,  null as countdownTime, null as section, null as sysdateWhenAccessing from v_sysCurStatus B  order by tradePartition asc, id asc ";
  private static String sqlGetAllPartitionAndAllNewQuotationWithLastSeq = "select B.tradePartition, B.section as partitionSection, B.status, B.starttime, B.endtime, A.ID, A.submitID, A.code, A.price, A.vaidAmount, A.userID, A.lastTime, A.countdownStart, A.countdownTime, A.section, (select sysdate from dual) as sysdateWhenAccessing from v_tradeQuotation A, v_sysCurStatus B  where B.tradePartition = A.tradePartition and A.id in( select max(id) from v_tradeQuotation where id > ? group by tradePartition, code, section)  union  select B.tradePartition, B.section as partitionSection, B.status, B.starttime, B.endtime,  null as ID, null as submitID, null as code, null as price,  null as vaidAmount, null as userID, null as lastTime, null as countdownStart,  null as countdownTime, null as section, null as sysdateWhenAccessing from v_sysCurStatus B  order by tradePartition asc, id asc ";
  
  public void changeCountStarttimeInDbForTest()
    throws Exception
  {
    Connection localConnection = getConnection();
    Statement localStatement = localConnection.createStatement();
    localStatement.execute("update v_tradeQuotation set countdownStart = (select sysdate from dual) ");
    localStatement.close();
    localConnection.close();
    System.out.println("update v_tradeQuotation set countdownStart = (select sysdate from dual) ");
  }
  
  public void fetchQuotationValue(Long paramLong)
    throws Exception
  {
    Connection localConnection = getConnection();
    this.newestSequenceId = null;
    ResultSet localResultSet = null;
    String str = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      if (paramLong == null)
      {
        str = sqlGetAllPartitionAndAllNewQuotationWithoutLastSeq;
        localPreparedStatement = localConnection.prepareStatement(str);
      }
      else
      {
        str = sqlGetAllPartitionAndAllNewQuotationWithLastSeq;
        localPreparedStatement = localConnection.prepareStatement(str);
        localPreparedStatement.setLong(1, paramLong.longValue());
      }
      localResultSet = localPreparedStatement.executeQuery();
      this.partitionValueList = new ArrayList();
      List localList = null;
      Long localLong = null;
      long l1 = System.currentTimeMillis();
      while (localResultSet.next())
      {
        if ((localResultSet.getString("code") != null) && ((this.newestSequenceId == null) || (localResultSet.getLong("id") > this.newestSequenceId.longValue()))) {
          this.newestSequenceId = new Long(localResultSet.getLong("id"));
        }
        Object localObject1;
        if ((localLong == null) || (localResultSet.getLong("tradePartition") != localLong.longValue()))
        {
          localObject1 = new PartitionQuotation();
          localList = ((PartitionQuotation)localObject1).getQuotationDetailList();
          ((PartitionQuotation)localObject1).setEndTime(localResultSet.getTimestamp("endtime"));
          ((PartitionQuotation)localObject1).setStartTime(localResultSet.getTimestamp("starttime"));
          ((PartitionQuotation)localObject1).setPartitionId(new Long(localResultSet.getLong("tradePartition")));
          ((PartitionQuotation)localObject1).setStatus(new Long(localResultSet.getLong("status")));
          ((PartitionQuotation)localObject1).setSection(new Long(localResultSet.getLong("partitionSection")));
          this.partitionValueList.add(localObject1);
          localLong = new Long(localResultSet.getLong("tradePartition"));
        }
        if (localResultSet.getString("code") != null)
        {
          localObject1 = new QuotationValue();
          ((QuotationValue)localObject1).setCode(localResultSet.getString("code"));
          ((QuotationValue)localObject1).setCountDownStart(localResultSet.getTimestamp("countDownStart"));
          ((QuotationValue)localObject1).setCountDownTime(new Long(localResultSet.getLong("countDownTime")));
          ((QuotationValue)localObject1).setId(new Long(localResultSet.getLong("id")));
          ((QuotationValue)localObject1).setLastTime(localResultSet.getTimestamp("lastTime"));
          ((QuotationValue)localObject1).setPrice(new Double(localResultSet.getDouble("price")));
          ((QuotationValue)localObject1).setSection(new Long(localResultSet.getLong("section")));
          ((QuotationValue)localObject1).setSubmitId(new Long(localResultSet.getLong("submitId")));
          ((QuotationValue)localObject1).setTradePartition(new Long(localResultSet.getLong("tradePartition")));
          ((QuotationValue)localObject1).setUserID(localResultSet.getString("userId"));
          ((QuotationValue)localObject1).setValidAmount(new Long(localResultSet.getLong("vaidAmount")));
          if (localResultSet.getLong("countDownTime") > 0L)
          {
            CountTime localCountTime = new CountTime();
            long l2 = localResultSet.getTimestamp("countDownStart").getTime();
            long l3 = localResultSet.getLong("countDownTime");
            long l4 = l2 + l3 * 1000L;
            localCountTime.setCountDownStart(l2);
            localCountTime.setCountDownEnd(l4);
            localCountTime.setDbSystime(localResultSet.getTimestamp("sysdateWhenAccessing").getTime());
            localCountTime.setLocSystime(l1);
            ((QuotationValue)localObject1).setCountTime(localCountTime);
          }
          localList.add(localObject1);
        }
      }
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      closeResultSet(localResultSet);
      closeStatement(localPreparedStatement);
      closeConnection(localConnection);
    }
  }
  
  public Long getNewestSequenceId()
  {
    return this.newestSequenceId;
  }
  
  public List getPartitionValueList()
  {
    return this.partitionValueList;
  }
  
  public Commodity fetchCommodityInTradePartitionQuotation(Long paramLong, String paramString)
    throws Exception
  {
    Commodity localCommodity1 = null;
    Connection localConnection = getConnection();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = localConnection.prepareStatement(sqlFetchCommodityInTradePartitionQuotation);
      localPreparedStatement.setLong(1, paramLong.longValue());
      localPreparedStatement.setString(2, paramString);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        localCommodity1 = new Commodity();
        localCommodity1.setAlertPrice(new Double(localResultSet.getDouble("alertPrice")));
        localCommodity1.setAmount(new Long(localResultSet.getLong("amount")));
        localCommodity1.setBeginPrice(new Double(localResultSet.getDouble("beginPrice")));
        localCommodity1.setCode(localResultSet.getString("commodityCode"));
        localCommodity1.setCreateTime(localResultSet.getTimestamp("createTime"));
        localCommodity1.setFee(new Double(localResultSet.getDouble("fee")));
        localCommodity1.setFirstTime(localResultSet.getTimestamp("firstTime"));
        localCommodity1.setID(new Long(localResultSet.getLong("commodityId")));
        localCommodity1.setMinAmount(new Double(localResultSet.getDouble("minAmount")));
        localCommodity1.setSecurity(new Double(localResultSet.getDouble("security")));
        localCommodity1.setSplitID(new Long(localResultSet.getLong("splitID")));
        localCommodity1.setStatus(new Long(localResultSet.getLong("status")));
        localCommodity1.setStepPrice(new Double(localResultSet.getDouble("stepPrice")));
        localCommodity1.setTradeUnit(new Double(localResultSet.getDouble("tradeUnit")));
      }
      Commodity localCommodity2 = localCommodity1;
      return localCommodity2;
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      closeResultSet(localResultSet);
      closeStatement(localPreparedStatement);
      closeConnection(localConnection);
    }
  }
  
  public List fetchTradePartitionQuotation(Long paramLong)
    throws Exception
  {
    Connection localConnection = getConnection();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = localConnection.prepareStatement(sqlFetchTradePartitionQuotation);
      localPreparedStatement.setLong(1, paramLong.longValue());
      localResultSet = localPreparedStatement.executeQuery();
      ArrayList localArrayList = new ArrayList();
      while (localResultSet.next())
      {
        localObject1 = new QuotationValue();
        Commodity localCommodity = new Commodity();
        CurCommodity localCurCommodity = new CurCommodity();
        localCommodity.setAlertPrice(new Double(localResultSet.getDouble("alertPrice")));
        localCommodity.setAmount(new Long(localResultSet.getLong("amount")));
        localCommodity.setBeginPrice(new Double(localResultSet.getDouble("beginPrice")));
        localCommodity.setCode(localResultSet.getString("commodityCode"));
        localCommodity.setCreateTime(localResultSet.getTimestamp("createTime"));
        localCommodity.setFee(new Double(localResultSet.getDouble("fee")));
        localCommodity.setFirstTime(localResultSet.getTimestamp("firstTime"));
        localCommodity.setID(new Long(localResultSet.getLong("commodityId")));
        localCommodity.setMinAmount(new Double(localResultSet.getDouble("minAmount")));
        localCommodity.setSecurity(new Double(localResultSet.getDouble("security")));
        localCommodity.setSplitID(new Long(localResultSet.getLong("splitID")));
        localCommodity.setStatus(new Long(localResultSet.getLong("status")));
        localCommodity.setStepPrice(new Double(localResultSet.getDouble("stepPrice")));
        localCommodity.setTradeUnit(new Double(localResultSet.getDouble("tradeUnit")));
        localCurCommodity.setBargainFlag(new Long(localResultSet.getLong("bargainFlag")));
        localCurCommodity.setCode(localResultSet.getString("code"));
        localCurCommodity.setCommodityID(new Long(localResultSet.getLong("commodityID")));
        localCurCommodity.setLpFlag(new Long(localResultSet.getLong("lpFlag")));
        localCurCommodity.setModifyTime(localResultSet.getTimestamp("modifyTime"));
        localCurCommodity.setSection(new Long(localResultSet.getLong("curCommoditySection")));
        localCurCommodity.setTradePartition(new Long(localResultSet.getLong("tradePartition")));
        ((QuotationValue)localObject1).setCode(localResultSet.getString("code"));
        ((QuotationValue)localObject1).setCountDownStart(localResultSet.getTimestamp("countDownStart"));
        ((QuotationValue)localObject1).setCountDownTime(new Long(localResultSet.getLong("countDownTime")));
        ((QuotationValue)localObject1).setId(new Long(localResultSet.getLong("id")));
        ((QuotationValue)localObject1).setLastTime(localResultSet.getTimestamp("lastTime"));
        ((QuotationValue)localObject1).setPrice(new Double(localResultSet.getDouble("price")));
        ((QuotationValue)localObject1).setSection(new Long(localResultSet.getLong("section")));
        ((QuotationValue)localObject1).setSubmitId(new Long(localResultSet.getLong("submitId")));
        ((QuotationValue)localObject1).setTradePartition(new Long(localResultSet.getLong("tradePartition")));
        ((QuotationValue)localObject1).setUserID(localResultSet.getString("userId"));
        ((QuotationValue)localObject1).setValidAmount(new Long(localResultSet.getLong("vaidAmount")));
        ((QuotationValue)localObject1).setCommodity(localCommodity);
        ((QuotationValue)localObject1).setCurCommodity(localCurCommodity);
        localArrayList.add(localObject1);
      }
      Object localObject1 = localArrayList;
      return localObject1;
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      closeResultSet(localResultSet);
      closeStatement(localPreparedStatement);
      closeConnection(localConnection);
    }
  }
  
  public static void main(String[] paramArrayOfString)
  {
    System.out.println(System.currentTimeMillis());
    System.out.println(new Date().getTime());
    System.out.println(Calendar.getInstance().getTimeInMillis());
    Timestamp localTimestamp1 = Timestamp.valueOf("2006-8-22 15:45:24.123");
    System.out.println(localTimestamp1.toString());
    long l = 5L;
    Timestamp localTimestamp2 = (Timestamp)localTimestamp1.clone();
    localTimestamp2.setTime(localTimestamp1.getTime() + l * 1000L);
    System.out.println(localTimestamp2.toString());
  }
}
