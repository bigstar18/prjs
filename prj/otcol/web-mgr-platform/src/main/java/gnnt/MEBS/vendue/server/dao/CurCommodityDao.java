package gnnt.MEBS.vendue.server.dao;

import gnnt.MEBS.vendue.server.beans.busbeans.CurCommodityCompositBean;
import gnnt.MEBS.vendue.server.beans.busbeans.PartitionCurCommodity;
import gnnt.MEBS.vendue.server.beans.dtobeans.SysCurStatus;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurCommodityDao
  extends BaseDao
{
  private Timestamp newestModityTime = null;
  private static final String sqlFetchCommodity = "select A.Tradepartition as sysCurStatusTradepartition, A.Section as sysCurStatusSection, A.Status as sysCurStatusStatus, A.Starttime as sysCurStatusStarttime, A.Endtime as sysCurStatusEndtime, A.Modifytime as sysCurStatusModifytime,  B.code, B.tradepartition, B.commodityid, B.section, B.lpflag, B.bargainflag, B.modifytime, C.id, C.firsttime, C.createtime, C.status, C.splitid, C.category, C.beginprice, C.stepprice, C.amount, C.tradeunit, C.alertprice, C.s_security, C.s_fee, C.b_security, C.b_fee, C.minamount, D.commid, D.str1, D.str2, D.str3, D.str4, D.str5, D.str6, D.str7, D.str8, D.str9, D.str10, D.str11, D.str12, D.str13, D.str14, D.str15, D.str16, D.str17, D.str18, D.str19, D.str20, D.num1, D.num2, D.num3, D.num4, D.num5, D.num6, D.num7, D.num8, D.num9, D.num10  from v_sysCurStatus A, v_curCommodity B, v_Commodity C, v_Commext D  where   A.tradePartition = B.tradePartition(+) and B.commodityid = C.id and B.commodityid = D.commID(+) and B.modifytime > ? and A.section = B.section and A.status = 2  union select A.Tradepartition as sysCurStatusTradepartition, A.Section as sysCurStatusSection, A.Status as sysCurStatusStatus, A.Starttime as sysCurStatusStarttime, A.Endtime as sysCurStatusEndtime, A.Modifytime as sysCurStatusModifytime,  null as code, null as tradepartition, null as commodityid, null as section, null as lpflag, null as bargainflag, null as modifytime, null as id, null as firsttime, null as createtime, null as status, null as splitid, null as category, null as beginprice, null as stepprice, null as amount, null as tradeunit, null as alertprice, null as s_security, null as s_fee, null as b_security, null as b_fee, null as minamount, null as commid, null as str1, null as str2, null as str3, null as str4, null as str5, null as str6, null as str7, null as str8, null as str9, null as str10, null as str11, null as str12, null as str13, null as str14, null as str15, null as str16, null as str17, null as str18, null as str19, null as str20, null as num1, null as num2, null as num3, null as num4, null as num5, null as num6, null as num7, null as num8, null as num9, null as num10  from v_sysCurStatus A  order by sysCurStatusTradepartition, code ";
  
  public Timestamp getNewestModityTime()
  {
    return this.newestModityTime;
  }
  
  public Map getAllPartitionCurCommodity(Timestamp paramTimestamp)
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    if (paramTimestamp == null) {
      paramTimestamp = Timestamp.valueOf("1900-01-01 00:00:00");
    }
    Connection localConnection = getConnection();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = localConnection.prepareStatement("select A.Tradepartition as sysCurStatusTradepartition, A.Section as sysCurStatusSection, A.Status as sysCurStatusStatus, A.Starttime as sysCurStatusStarttime, A.Endtime as sysCurStatusEndtime, A.Modifytime as sysCurStatusModifytime,  B.code, B.tradepartition, B.commodityid, B.section, B.lpflag, B.bargainflag, B.modifytime, C.id, C.firsttime, C.createtime, C.status, C.splitid, C.category, C.beginprice, C.stepprice, C.amount, C.tradeunit, C.alertprice, C.s_security, C.s_fee, C.b_security, C.b_fee, C.minamount, D.commid, D.str1, D.str2, D.str3, D.str4, D.str5, D.str6, D.str7, D.str8, D.str9, D.str10, D.str11, D.str12, D.str13, D.str14, D.str15, D.str16, D.str17, D.str18, D.str19, D.str20, D.num1, D.num2, D.num3, D.num4, D.num5, D.num6, D.num7, D.num8, D.num9, D.num10  from v_sysCurStatus A, v_curCommodity B, v_Commodity C, v_Commext D  where   A.tradePartition = B.tradePartition(+) and B.commodityid = C.id and B.commodityid = D.commID(+) and B.modifytime > ? and A.section = B.section and A.status = 2  union select A.Tradepartition as sysCurStatusTradepartition, A.Section as sysCurStatusSection, A.Status as sysCurStatusStatus, A.Starttime as sysCurStatusStarttime, A.Endtime as sysCurStatusEndtime, A.Modifytime as sysCurStatusModifytime,  null as code, null as tradepartition, null as commodityid, null as section, null as lpflag, null as bargainflag, null as modifytime, null as id, null as firsttime, null as createtime, null as status, null as splitid, null as category, null as beginprice, null as stepprice, null as amount, null as tradeunit, null as alertprice, null as s_security, null as s_fee, null as b_security, null as b_fee, null as minamount, null as commid, null as str1, null as str2, null as str3, null as str4, null as str5, null as str6, null as str7, null as str8, null as str9, null as str10, null as str11, null as str12, null as str13, null as str14, null as str15, null as str16, null as str17, null as str18, null as str19, null as str20, null as num1, null as num2, null as num3, null as num4, null as num5, null as num6, null as num7, null as num8, null as num9, null as num10  from v_sysCurStatus A  order by sysCurStatusTradepartition, code ");
      localPreparedStatement.setTimestamp(1, paramTimestamp);
      localResultSet = localPreparedStatement.executeQuery();
      Long localLong = null;
      PartitionCurCommodity localPartitionCurCommodity = null;
      List localList = null;
      while (localResultSet.next())
      {
        long l = localResultSet.getLong("sysCurStatusTradepartition");
        Object localObject1;
        if ((localLong == null) || (localLong.longValue() != l))
        {
          localPartitionCurCommodity = new PartitionCurCommodity();
          localHashMap.put(String.valueOf(l), localPartitionCurCommodity);
          localLong = new Long(l);
          localList = localPartitionCurCommodity.getCurCommodityCompositBeanList();
          localObject1 = new SysCurStatus();
          ((SysCurStatus)localObject1).setEndTime(localResultSet.getTimestamp("sysCurStatusEndtime"));
          ((SysCurStatus)localObject1).setModifyTime(localResultSet.getTimestamp("sysCurStatusModifytime"));
          ((SysCurStatus)localObject1).setSection(new Long(localResultSet.getLong("sysCurStatusSection")));
          ((SysCurStatus)localObject1).setStartTime(localResultSet.getTimestamp("sysCurStatusStarttime"));
          ((SysCurStatus)localObject1).setStatus(new Long(localResultSet.getLong("sysCurStatusStatus")));
          ((SysCurStatus)localObject1).setTradePartition(new Long(l));
          localPartitionCurCommodity.setPartitionId(new Long(l));
          localPartitionCurCommodity.setSysCurStatus((SysCurStatus)localObject1);
        }
        if (localResultSet.getString("code") != null)
        {
          localObject1 = new CurCommodityCompositBean();
          setCompositBean(localResultSet, (CurCommodityCompositBean)localObject1);
          localList.add(localObject1);
          if ((this.newestModityTime == null) || (((CurCommodityCompositBean)localObject1).getModifytime().compareTo(this.newestModityTime) > 0)) {
            this.newestModityTime = ((CurCommodityCompositBean)localObject1).getModifytime();
          }
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
    return localHashMap;
  }
  
  private void setCompositBean(ResultSet paramResultSet, CurCommodityCompositBean paramCurCommodityCompositBean)
    throws Exception
  {
    paramCurCommodityCompositBean.setAlertprice(new Double(paramResultSet.getDouble("alertprice")));
    paramCurCommodityCompositBean.setBeginprice(new Double(paramResultSet.getDouble("beginprice")));
    paramCurCommodityCompositBean.setB_fee(new Double(paramResultSet.getDouble("b_fee")));
    paramCurCommodityCompositBean.setS_fee(new Double(paramResultSet.getDouble("s_fee")));
    paramCurCommodityCompositBean.setMinamount(new Double(paramResultSet.getDouble("minamount")));
    paramCurCommodityCompositBean.setNum1(new Double(paramResultSet.getDouble("num1")));
    paramCurCommodityCompositBean.setNum10(new Double(paramResultSet.getDouble("num10")));
    paramCurCommodityCompositBean.setNum2(new Double(paramResultSet.getDouble("num2")));
    paramCurCommodityCompositBean.setNum3(new Double(paramResultSet.getDouble("num3")));
    paramCurCommodityCompositBean.setNum4(new Double(paramResultSet.getDouble("num4")));
    paramCurCommodityCompositBean.setNum5(new Double(paramResultSet.getDouble("num5")));
    paramCurCommodityCompositBean.setNum6(new Double(paramResultSet.getDouble("num6")));
    paramCurCommodityCompositBean.setNum7(new Double(paramResultSet.getDouble("num7")));
    paramCurCommodityCompositBean.setNum8(new Double(paramResultSet.getDouble("num8")));
    paramCurCommodityCompositBean.setNum9(new Double(paramResultSet.getDouble("num9")));
    paramCurCommodityCompositBean.setB_security(new Double(paramResultSet.getDouble("b_security")));
    paramCurCommodityCompositBean.setS_security(new Double(paramResultSet.getDouble("s_security")));
    paramCurCommodityCompositBean.setStepprice(new Double(paramResultSet.getDouble("stepprice")));
    paramCurCommodityCompositBean.setTradeunit(new Double(paramResultSet.getDouble("tradeunit")));
    paramCurCommodityCompositBean.setAmount(new Long(paramResultSet.getLong("amount")));
    paramCurCommodityCompositBean.setBargainflag(new Long(paramResultSet.getLong("bargainflag")));
    paramCurCommodityCompositBean.setCategory(new Long(paramResultSet.getLong("category")));
    paramCurCommodityCompositBean.setCommid(new Long(paramResultSet.getLong("commid")));
    paramCurCommodityCompositBean.setCommodityid(new Long(paramResultSet.getLong("commodityid")));
    paramCurCommodityCompositBean.setId(new Long(paramResultSet.getLong("id")));
    paramCurCommodityCompositBean.setLpflag(new Long(paramResultSet.getLong("lpflag")));
    paramCurCommodityCompositBean.setSection(new Long(paramResultSet.getLong("section")));
    paramCurCommodityCompositBean.setStatus(new Long(paramResultSet.getLong("status")));
    paramCurCommodityCompositBean.setTradepartition(new Long(paramResultSet.getLong("tradepartition")));
    paramCurCommodityCompositBean.setCode(paramResultSet.getString("code"));
    paramCurCommodityCompositBean.setSplitid(paramResultSet.getString("splitid"));
    paramCurCommodityCompositBean.setStr1(paramResultSet.getString("str1"));
    paramCurCommodityCompositBean.setStr10(paramResultSet.getString("str10"));
    paramCurCommodityCompositBean.setStr11(paramResultSet.getString("str11"));
    paramCurCommodityCompositBean.setStr12(paramResultSet.getString("str12"));
    paramCurCommodityCompositBean.setStr13(paramResultSet.getString("str13"));
    paramCurCommodityCompositBean.setStr14(paramResultSet.getString("str14"));
    paramCurCommodityCompositBean.setStr15(paramResultSet.getString("str15"));
    paramCurCommodityCompositBean.setStr16(paramResultSet.getString("str16"));
    paramCurCommodityCompositBean.setStr17(paramResultSet.getString("str17"));
    paramCurCommodityCompositBean.setStr18(paramResultSet.getString("str18"));
    paramCurCommodityCompositBean.setStr19(paramResultSet.getString("str19"));
    paramCurCommodityCompositBean.setStr2(paramResultSet.getString("str2"));
    paramCurCommodityCompositBean.setStr20(paramResultSet.getString("str20"));
    paramCurCommodityCompositBean.setStr3(paramResultSet.getString("str3"));
    paramCurCommodityCompositBean.setStr4(paramResultSet.getString("str4"));
    paramCurCommodityCompositBean.setStr5(paramResultSet.getString("str5"));
    paramCurCommodityCompositBean.setStr6(paramResultSet.getString("str6"));
    paramCurCommodityCompositBean.setStr7(paramResultSet.getString("str7"));
    paramCurCommodityCompositBean.setStr8(paramResultSet.getString("str8"));
    paramCurCommodityCompositBean.setStr9(paramResultSet.getString("str9"));
    paramCurCommodityCompositBean.setCreatetime(paramResultSet.getTimestamp("createtime"));
    paramCurCommodityCompositBean.setFirsttime(paramResultSet.getTimestamp("firsttime"));
    paramCurCommodityCompositBean.setModifytime(paramResultSet.getTimestamp("modifytime"));
  }
  
  public long getNumOfAllCommodity()
    throws Exception
  {
    long l = 0L;
    Connection localConnection = getConnection();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = localConnection.prepareStatement("select count(*) from v_curCommodity ");
      localResultSet = localPreparedStatement.executeQuery();
      localResultSet.next();
      l = localResultSet.getLong(1);
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
    return l;
  }
  
  public Map getAllPartion()
    throws Exception
  {
    HashMap localHashMap = new HashMap();
    Connection localConnection = getConnection();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = localConnection.prepareStatement("select Tradepartition, Section, Status, Starttime, Endtime, Modifytime from v_sysCurStatus ");
      localResultSet = localPreparedStatement.executeQuery();
      while (localResultSet.next())
      {
        PartitionCurCommodity localPartitionCurCommodity = new PartitionCurCommodity();
        SysCurStatus localSysCurStatus = new SysCurStatus();
        localSysCurStatus.setTradePartition(new Long(localResultSet.getLong("Tradepartition")));
        localSysCurStatus.setSection(new Long(localResultSet.getLong("Section")));
        localSysCurStatus.setStatus(new Long(localResultSet.getLong("Status")));
        localPartitionCurCommodity.setSysCurStatus(localSysCurStatus);
        localHashMap.put(localSysCurStatus.getTradePartition().toString(), localPartitionCurCommodity);
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
    return localHashMap;
  }
  
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
  
  public static void main(String[] paramArrayOfString)
  {
    System.out.println("select A.Tradepartition as sysCurStatusTradepartition, A.Section as sysCurStatusSection, A.Status as sysCurStatusStatus, A.Starttime as sysCurStatusStarttime, A.Endtime as sysCurStatusEndtime, A.Modifytime as sysCurStatusModifytime,  B.code, B.tradepartition, B.commodityid, B.section, B.lpflag, B.bargainflag, B.modifytime, C.id, C.firsttime, C.createtime, C.status, C.splitid, C.category, C.beginprice, C.stepprice, C.amount, C.tradeunit, C.alertprice, C.s_security, C.s_fee, C.b_security, C.b_fee, C.minamount, D.commid, D.str1, D.str2, D.str3, D.str4, D.str5, D.str6, D.str7, D.str8, D.str9, D.str10, D.str11, D.str12, D.str13, D.str14, D.str15, D.str16, D.str17, D.str18, D.str19, D.str20, D.num1, D.num2, D.num3, D.num4, D.num5, D.num6, D.num7, D.num8, D.num9, D.num10  from v_sysCurStatus A, v_curCommodity B, v_Commodity C, v_Commext D  where   A.tradePartition = B.tradePartition(+) and B.commodityid = C.id and B.commodityid = D.commID(+) and B.modifytime > ? and A.section = B.section and A.status = 2  union select A.Tradepartition as sysCurStatusTradepartition, A.Section as sysCurStatusSection, A.Status as sysCurStatusStatus, A.Starttime as sysCurStatusStarttime, A.Endtime as sysCurStatusEndtime, A.Modifytime as sysCurStatusModifytime,  null as code, null as tradepartition, null as commodityid, null as section, null as lpflag, null as bargainflag, null as modifytime, null as id, null as firsttime, null as createtime, null as status, null as splitid, null as category, null as beginprice, null as stepprice, null as amount, null as tradeunit, null as alertprice, null as s_security, null as s_fee, null as b_security, null as b_fee, null as minamount, null as commid, null as str1, null as str2, null as str3, null as str4, null as str5, null as str6, null as str7, null as str8, null as str9, null as str10, null as str11, null as str12, null as str13, null as str14, null as str15, null as str16, null as str17, null as str18, null as str19, null as str20, null as num1, null as num2, null as num3, null as num4, null as num5, null as num6, null as num7, null as num8, null as num9, null as num10  from v_sysCurStatus A  order by sysCurStatusTradepartition, code ");
  }
}
