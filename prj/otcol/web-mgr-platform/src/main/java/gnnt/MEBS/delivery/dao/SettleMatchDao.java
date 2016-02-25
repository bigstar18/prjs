package gnnt.MEBS.delivery.dao;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.model.inner.SettleMatchRelated;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.util.SysData;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class SettleMatchDao
  extends DaoHelperImpl
{
  private final transient Log log = LogFactory.getLog(SettleMatchDao.class);
  
  public long createSettleMatch(SettleMatch paramSettleMatch, String paramString)
  {
    long l = -1L;
    CreateSettleMatchProcedure localCreateSettleMatchProcedure = new CreateSettleMatchProcedure(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("p_moduleid", paramSettleMatch.getModuleId());
    localHashMap.put("p_breedid", Long.valueOf(paramSettleMatch.getBreedId()));
    localHashMap.put("p_weight", Double.valueOf(paramSettleMatch.getWeight()));
    localHashMap.put("p_result", Integer.valueOf(paramSettleMatch.getResult()));
    localHashMap.put("p_commodityId", paramSettleMatch.getCommodityId());
    localHashMap.put("p_firmID_B", paramSettleMatch.getFirmID_B());
    localHashMap.put("p_buyPrice", Double.valueOf(paramSettleMatch.getBuyPrice()));
    localHashMap.put("p_buyMargin", Double.valueOf(paramSettleMatch.getBuyMargin()));
    localHashMap.put("p_buyPayout", Double.valueOf(paramSettleMatch.getBuyPayout()));
    localHashMap.put("p_firmID_S", paramSettleMatch.getFirmID_S());
    localHashMap.put("p_sellPrice", Double.valueOf(paramSettleMatch.getSellPrice()));
    localHashMap.put("p_sellMargin", Double.valueOf(paramSettleMatch.getSellMargin()));
    localHashMap.put("p_regStockID", paramSettleMatch.getRegStockId());
    localHashMap.put("p_contractId", Long.valueOf(paramSettleMatch.getContractId()));
    localHashMap.put("p_xml", paramSettleMatch.getXml());
    localHashMap.put("p_operator", paramString);
    localHashMap.put("p_module", Integer.valueOf(0));
    Map localMap = localCreateSettleMatchProcedure.execute(localHashMap);
    l = ((BigDecimal)localMap.get("ret")).intValue();
    this.log.debug(Long.valueOf(l));
    return l;
  }
  
  public void addRelated(SettleMatchRelated paramSettleMatchRelated)
  {
    String str = "insert into s_settlematchrelated (parentmatchid,childmatchid) values (?,?)";
    Object[] arrayOfObject = { paramSettleMatchRelated.getParentMatchId(), paramSettleMatchRelated.getChildMatchId() };
    int[] arrayOfInt = { 12, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void updateSettleMatch(SettleMatch paramSettleMatch)
  {
    String str = "update S_SettleMatch set moduleId=?,breedId=?,weight=?,HL_Amount=?,status=?,result=?,firmId_B=?,buyPrice=?,buyPayout_Ref=?,buyPayout=?,buyMargin=?,settlePL_B=?,penalty_B=?,firmId_S=?,sellPrice=?,sellIncome_Ref=?,sellIncome=?,sellMargin=?,settlePL_S=?,penalty_S=?,regStockId=?,recvInvoice=?,createTime=?,modifyTime=?,commodityId=?,contractId=?,xml=?,isChangeOwner=? where matchId=?";
    Object[] arrayOfObject = { paramSettleMatch.getModuleId(), Long.valueOf(paramSettleMatch.getBreedId()), Double.valueOf(paramSettleMatch.getWeight()), Double.valueOf(paramSettleMatch.getHL_Amount()), Integer.valueOf(paramSettleMatch.getStatus()), Integer.valueOf(paramSettleMatch.getResult()), paramSettleMatch.getFirmID_B(), Double.valueOf(paramSettleMatch.getBuyPrice()), Double.valueOf(paramSettleMatch.getBuyPayout_Ref()), Double.valueOf(paramSettleMatch.getBuyPayout()), Double.valueOf(paramSettleMatch.getBuyMargin()), Double.valueOf(paramSettleMatch.getSettlePL_B()), Double.valueOf(paramSettleMatch.getPenalty_B()), paramSettleMatch.getFirmID_S(), Double.valueOf(paramSettleMatch.getSellPrice()), Double.valueOf(paramSettleMatch.getSellIncome_Ref()), Double.valueOf(paramSettleMatch.getSellIncome()), Double.valueOf(paramSettleMatch.getSellMargin()), Double.valueOf(paramSettleMatch.getSettlePL_S()), Double.valueOf(paramSettleMatch.getPenalty_S()), paramSettleMatch.getRegStockId(), Integer.valueOf(paramSettleMatch.getRecvInvoice()), paramSettleMatch.getCreateTime(), paramSettleMatch.getModifyTime(), paramSettleMatch.getCommodityId(), Long.valueOf(paramSettleMatch.getContractId()), paramSettleMatch.getXml(), Integer.valueOf(paramSettleMatch.getIsChangeOwner()), paramSettleMatch.getMatchId() };
    int[] arrayOfInt = { 12, 2, 2, 2, 2, 2, 12, 2, 2, 2, 2, 2, 2, 12, 2, 2, 2, 2, 2, 2, 12, 2, 93, 93, 12, 2, 12, 2, 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getSettleMatchList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = " select s.* from S_SettleMatch s where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    this.log.debug("//---查询所有交收信息sql:->>>" + str);
    return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new SettleMatch()));
  }
  
  public List<SettleMatch> getSettleMatch(QueryConditions paramQueryConditions)
  {
    String str = "select * from s_settleMatch where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null, new CommonRowMapper(new SettleMatch()));
  }
  
  public String getMatchId()
  {
    String str1 = "";
    String str2 = "select SEQ_S_SETTLEMATCH.nextVal from dual";
    str1 = queryForInt(str2, null) + "";
    return str1;
  }
  
  public SettleMatch getSettleMatchLock(String paramString)
  {
    SettleMatch localSettleMatch = null;
    String str = "select * from s_settlematch where matchId='" + paramString + "' for update";
    List localList = queryBySQL(str, null, null, new CommonRowMapper(new SettleMatch()));
    if ((localList != null) && (localList.size() > 0)) {
      localSettleMatch = (SettleMatch)localList.get(0);
    }
    return localSettleMatch;
  }
  
  public void updateFundflowId(String paramString, long paramLong)
  {
    String str = "update S_SettleMatch t set initXml=updateXML(xmlType(t.initXml),'/root/LASTFUNDFLOWID/text()','" + paramLong + "') where matchID=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void cancelT_SettleMatch(String paramString)
  {
    String str = "update t_settlematch set Status=-2 where MatchID=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public int balance()
  {
    BalanceProcedure localBalanceProcedure = new BalanceProcedure(getDataSource());
    HashMap localHashMap = new HashMap();
    localHashMap.put("p_checkBalance", Integer.valueOf(Integer.parseInt(SysData.getConfig("checkBalance"))));
    Map localMap = localBalanceProcedure.execute(localHashMap);
    int i = ((BigDecimal)localMap.get("ret")).intValue();
    return i;
  }
  
  public List getMatchSettleholdRelevanceList(String paramString)
  {
    String str = "select * from T_MatchSettleholdRelevance where MatchID='" + paramString + "'";
    return queryBySQL(str);
  }
  
  public void updateMatchQuantity(long paramLong1, long paramLong2)
  {
    String str = "update T_SettleHoldPosition t1 set MatchQuantity = (select t2.MatchQuantity -?  from T_SettleHoldPosition t2 where t2.id=?)  where t1.id=?";
    Object[] arrayOfObject = { Long.valueOf(paramLong2), Long.valueOf(paramLong1), Long.valueOf(paramLong1) };
    int[] arrayOfInt = { 2, 2, 2 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public void deleteMatchSettleholdRel(String paramString)
  {
    String str = "delete T_MatchSettleholdRelevance t where t.matchid=?";
    Object[] arrayOfObject = { paramString };
    int[] arrayOfInt = { 12 };
    updateBySQL(str, arrayOfObject, arrayOfInt);
  }
  
  public List getMatchSettleholdRelevanceList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    String str = "select m.* from T_MatchSettleholdRelevance m, T_SettleHoldPosition s where m.settleid=s.id ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    return queryBySQL(str, arrayOfObject, null);
  }
  
  public void releaseRegStock(String paramString1, String paramString2)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    double d1 = 0.0D;
    long l = 0L;
    try
    {
      DataSource localDataSource = getDataSource();
      localConnection = localDataSource.getConnection();
      localConnection.setAutoCommit(false);
      String str = "select * from T_SettleMatch where MatchID='" + paramString1 + "'";
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next())
      {
        d1 = localResultSet.getDouble("CONTRACTFACTOR");
        l = localResultSet.getLong("QUANTITY");
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
      double d2 = d1 * l;
      str = "update s_regstock set frozenweight=frozenweight-" + d2 + " where regstockid='" + paramString2 + "'";
      localPreparedStatement = localConnection.prepareStatement(str);
      localPreparedStatement.executeUpdate();
      localConnection.commit();
      localPreparedStatement.close();
      localPreparedStatement = null;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localConnection.rollback();
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
  }
  
  public double getContractFactor(String paramString)
    throws SQLException
  {
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Connection localConnection = null;
    double d = 0.0D;
    try
    {
      DataSource localDataSource = getDataSource();
      localConnection = localDataSource.getConnection();
      localConnection.setAutoCommit(false);
      String str = "select * from T_SettleMatch where MatchID='" + paramString + "' for update";
      localPreparedStatement = localConnection.prepareStatement(str);
      localResultSet = localPreparedStatement.executeQuery();
      if (localResultSet.next()) {
        d = localResultSet.getDouble("CONTRACTFACTOR");
      }
      localResultSet.close();
      localResultSet = null;
      localPreparedStatement.close();
      localPreparedStatement = null;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      localConnection.rollback();
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
    return d;
  }
  
  private class BalanceProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_S_Balance";
    
    public BalanceProcedure(DataSource paramDataSource)
    {
      super("FN_S_Balance");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_checkBalance", 2));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
  
  private class CreateSettleMatchProcedure
    extends StoredProcedure
  {
    private static final String SFUNC_NAME = "FN_S_createSettleMatch";
    
    public CreateSettleMatchProcedure(DataSource paramDataSource)
    {
      super("FN_S_createSettleMatch");
      setFunction(true);
      declareParameter(new SqlOutParameter("ret", 2));
      declareParameter(new SqlParameter("p_moduleid", 12));
      declareParameter(new SqlParameter("p_breedid", 2));
      declareParameter(new SqlParameter("p_weight", 2));
      declareParameter(new SqlParameter("p_result", 2));
      declareParameter(new SqlParameter("p_commodityId", 12));
      declareParameter(new SqlParameter("p_firmID_B", 12));
      declareParameter(new SqlParameter("p_buyPrice", 2));
      declareParameter(new SqlParameter("p_buyMargin", 2));
      declareParameter(new SqlParameter("p_buyPayout", 2));
      declareParameter(new SqlParameter("p_firmID_S", 12));
      declareParameter(new SqlParameter("p_sellPrice", 2));
      declareParameter(new SqlParameter("p_sellMargin", 2));
      declareParameter(new SqlParameter("p_regStockID", 12));
      declareParameter(new SqlParameter("p_contractId", 12));
      declareParameter(new SqlParameter("p_xml", 12));
      declareParameter(new SqlParameter("p_operator", 12));
      declareParameter(new SqlParameter("p_module", 12));
      compile();
    }
    
    public Map execute(Map paramMap)
    {
      return super.execute(paramMap);
    }
  }
}
