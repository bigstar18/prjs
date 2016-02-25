package gnnt.MEBS.member.broker.dao;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.member.broker.action.TradeFeeController;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class BrokerReportDao
  extends DaoHelperImpl
{
  private final transient Log logger = LogFactory.getLog(TradeFeeController.class);
  
  public List getBrokerFeeDetailByCondition(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    String str = " select a.moduleId moduleId,a.brokerid brokerid,  (select name from m_b_broker where brokerid=a.brokerid) name, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.moduleId moduleId,  t.brokerid brokerid, t.occurdate occurdate, sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide, sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t  group by t.moduleId,t.brokerid,t.occurdate ) a where 1=1 ";
    if (paramString.equals("2")) {
      str = " select a.moduleId moduleId, a.brokerid brokerid,  (select name from m_b_broker where brokerid=a.brokerid) name, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.moduleId moduleId , t.brokerid brokerid, t.occurdate occurdate, sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide, sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t where t.moduleid='2' group by t.moduleId, t.brokerid,t.occurdate ) a where 1=1 ";
    } else if (paramString.equals("3")) {
      str = " select a.moduleId moduleId, a.brokerid brokerid,  (select name from m_b_broker where brokerid=a.brokerid) name, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.moduleId moduleId , t.brokerid brokerid, t.occurdate occurdate, sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide, sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t where t.moduleid='3' group by t.moduleId, t.brokerid,t.occurdate ) a where 1=1 ";
    } else if (paramString.equals("4")) {
      str = " select a.moduleId moduleId, a.brokerid brokerid,  (select name from m_b_broker where brokerid=a.brokerid) name, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.moduleId moduleId , t.brokerid brokerid, t.occurdate occurdate, sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide, sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t where t.moduleid='4' group by t.moduleId, t.brokerid,t.occurdate ) a where 1=1 ";
    }
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str = str + " order by a.brokerid,a.occurdate ";
    str = "select d.moduleId moduleId,d.brokerid,d.name,sum(d.sumTradeFee) sumTradeFee,sum(d.sumBrokereachdivide) sumBrokereachdivide,sum(d.sumReward) sumReward,sum(d.sumSelfGain) sumSelfGain, sum(d.sumMarhetGain) sumMarhetGain,sum(d.sumFinalMarhetGain) sumFinalMarhetGain  from (" + str + ") d group by d.brokerid,d.name,d.moduleId  order by d.brokerid";
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List getFirmFeeDetailByCondition(QueryConditions paramQueryConditions, int paramInt1, int paramInt2, String paramString)
  {
    String str1 = " select a.moduleId moduleId, a.brokerid brokerid, a.breedid breedid,a.breedName breedName, (select name from m_b_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.moduleId moduleId, t.brokerid brokerid,t.firmid firmid,t.occurdate occurdate,t.breedId breedid ,t.breedName breedName,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t  group by t.moduleId, t.firmid, t.brokerid,t.occurdate,t.breedId,t.breedName \t) a where 1=1 ";
    if (paramString.equals("2")) {
      str1 = " select a.moduleId moduleId, a.brokerid brokerid,a.breedid breedid,a.breedName breedName,  (select name from m_b_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select  t.moduleId moduleId,t.brokerid brokerid,t.firmid firmid,t.occurdate occurdate,b.breedid breedid,t.breedName breedName,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t,t_a_breed b where t.moduleid='2' and t.breedid=b.breedid group by t.moduleId,t.firmid, t.brokerid,t.occurdate,b.breedid,t.breedName) a where 1=1 ";
    } else if (paramString.equals("3")) {
      str1 = " select a.moduleId moduleId, a.brokerid brokerid, a.breedid breedid,a.breedName breedName, (select name from m_b_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.moduleId moduleId, t.brokerid brokerid,t.firmid firmid,t.occurdate occurdate,b.breedid breedid,t.breedName breedName,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t,z_breed b where t.moduleid='3' and t.breedid=b.breedid group by t.moduleId,t.firmid, t.brokerid,t.occurdate,b.breedid,t.breedName) a where 1=1 ";
    } else if (paramString.equals("4")) {
      str1 = " select a.moduleId moduleId, a.brokerid brokerid, a.breedid breedid,a.breedName breedName, (select name from m_b_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select  t.moduleId,t.brokerid brokerid,t.firmid firmid,t.occurdate occurdate,b.id breedid,b.code breedName,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t,v_commodity b where t.moduleid='4' and t.breedid=b.id group by t.firmid, t.brokerid,t.occurdate,b.id,t.moduleId,b.code) a where 1=1 ";
    }
    String str2 = (String)paramQueryConditions.getConditionValue("isPartition");
    paramQueryConditions.removeCondition("isPartition");
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str1 = str1 + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str1 = str1 + " order by a.brokerid,a.firmid,a.occurdate ";
    if ("N".equals(str2)) {
      str1 = "select d.moduleId moduleId,d.breedid breedId,d.breedName breedName,d.brokerid BROKERID,d.name NAME,d.firmid FIRMID,d.firmname FIRMNAME,sum(d.sumTradeFee) SUMTRADEFEE,sum(d.sumBrokereachdivide) SUMBROKEREACHDIVIDE,sum(d.sumReward) SUMREWARD,sum(d.sumSelfGain) SUMSELFGAIN,sum(d.sumMarhetGain) SUMMARHETGAIN,sum(d.sumFinalMarhetGain) SUMFINALMARHETGAIN from (" + str1 + ") d group by d.brokerid,d.name,d.firmid,d.firmname,d.moduleId,d.breedName,d.breedid order by d.brokerid,d.firmid";
    } else if ("Y".equals(str2)) {
      str1 = "select d.moduleId moduleId,d.breedid breedId,d.breedName breedName,d.brokerid BROKERID,d.name NAME,d.firmid FIRMID,d.firmname FIRMNAME,d.breedid breedid,sum(d.sumTradeFee) SUMTRADEFEE,sum(d.sumBrokereachdivide) SUMBROKEREACHDIVIDE,sum(d.sumReward) SUMREWARD,sum(d.sumSelfGain) SUMSELFGAIN,sum(d.sumMarhetGain) SUMMARHETGAIN,sum(d.sumFinalMarhetGain) SUMFINALMARHETGAIN  from (" + str1 + ") d group by d.brokerid,d.name,d.firmid,d.firmname,d.breedid,d.moduleId,d.breedName order by d.brokerid,d.firmid";
    }
    str1 = "select tr.* from (select tt.*,rownum r from (" + str1 + ") tt) tr where tr.r between " + paramInt1 + " and " + paramInt2;
    return getJdbcTemplate().queryForList(str1, arrayOfObject);
  }
  
  public int getFirmFeeDetailByConditionCount(QueryConditions paramQueryConditions, String paramString)
  {
    String str1 = " select a.moduleId moduleId, a.brokerid brokerid, a.breedid breedid,a.breedName breedName, (select name from m_b_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.moduleId moduleId, t.brokerid brokerid,t.firmid firmid,t.occurdate occurdate,t.breedId breedid ,t.breedName breedName,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t  group by t.moduleId, t.firmid, t.brokerid,t.occurdate,t.breedId,t.breedName \t) a where 1=1 ";
    if (paramString.equals("2")) {
      str1 = " select a.moduleId moduleId, a.brokerid brokerid,a.breedid breedid,a.breedName breedName,  (select name from m_b_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select  t.moduleId moduleId,t.brokerid brokerid,t.firmid firmid,t.occurdate occurdate,b.breedid breedid,t.breedName breedName,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t,t_a_breed b where t.moduleid='2' and t.breedid=b.breedid group by t.moduleId,t.firmid, t.brokerid,t.occurdate,b.breedid,t.breedName) a where 1=1 ";
    } else if (paramString.equals("3")) {
      str1 = " select a.moduleId moduleId, a.brokerid brokerid, a.breedid breedid,a.breedName breedName, (select name from m_b_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.moduleId moduleId, t.brokerid brokerid,t.firmid firmid,t.occurdate occurdate,b.breedid breedid,t.breedName breedName,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t,z_breed b where t.moduleid='3' and t.breedid=b.breedid group by t.moduleId,t.firmid, t.brokerid,t.occurdate,b.breedid,t.breedName) a where 1=1 ";
    } else if (paramString.equals("4")) {
      str1 = " select a.moduleId moduleId, a.brokerid brokerid, a.breedid breedid,a.breedName breedName, (select name from m_b_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.occurdate occurdate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select  t.moduleId,t.brokerid brokerid,t.firmid firmid,t.occurdate occurdate,b.id breedid,b.code breedName,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t,v_commodity b where t.moduleid='4' and t.breedid=b.id group by t.firmid, t.brokerid,t.occurdate,b.id,t.moduleId,b.code) a where 1=1 ";
    }
    String str2 = (String)paramQueryConditions.getConditionValue("isPartition");
    paramQueryConditions.removeCondition("isPartition");
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str1 = str1 + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str1 = str1 + " order by a.brokerid,a.firmid,a.occurdate ";
    if ("N".equals(str2)) {
      str1 = "select count(*) con from (" + str1 + ") d  order by d.brokerid,d.firmid";
    } else if ("Y".equals(str2)) {
      str1 = "select count(*) con from (" + str1 + ") d  order by d.brokerid,d.firmid";
    }
    List localList = getJdbcTemplate().queryForList(str1, arrayOfObject);
    if ((localList == null) || (localList.size() == 0)) {
      return 0;
    }
    Map localMap = (Map)localList.get(0);
    return Integer.parseInt(localMap.get("con") + "");
  }
  
  public List getCommodityFeeDetailByCondition(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    String str = "";
    Object[] arrayOfObject = null;
    if (paramString.equals("2"))
    {
      str = " select a.moduleId moduleId,tb.breedname , a.breedID breedid, sum(a.sTradeFee) sumTradeFee, sum(a.sReward) sumReward, sum(a.sBrokereachdivide) sumBrokereachdivide, sum((a.sReward-a.sBrokereachdivide)) sumSelfGain, sum((a.sTradeFee-(a.sReward-a.sBrokereachdivide))) sumMarhetGain, sum((a.sTradeFee-a.sReward)) sumFinalMarhetGain  from  (select  t.moduleId moduleId,t.brokerid brokerid,t.occurdate occurdate,t.breedID breedid,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t where t.moduleid='2' group by t.brokerid,t.occurdate,t.breedID,t.moduleId) a,t_a_breed tb  where  a.breedid=tb.breedid ";
      if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
      {
        arrayOfObject = paramQueryConditions.getValueArray();
        str = str + " and " + paramQueryConditions.getFieldsSqlClause();
      }
      str = str + " group by a.breedid,tb.breedname, a.moduleId order by a.breedid ";
    }
    else if (paramString.equals("3"))
    {
      str = " select a.moduleId moduleId,tb.breedname , a.breedID breedid, sum(a.sTradeFee) sumTradeFee, sum(a.sReward) sumReward, sum(a.sBrokereachdivide) sumBrokereachdivide, sum((a.sReward-a.sBrokereachdivide)) sumSelfGain, sum((a.sTradeFee-(a.sReward-a.sBrokereachdivide))) sumMarhetGain, sum((a.sTradeFee-a.sReward)) sumFinalMarhetGain  from  (select t.moduleId moduleId,  t.brokerid brokerid,t.occurdate occurdate,t.breedID breedid,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t where t.moduleid='3' group by t.brokerid,t.occurdate,t.breedID,t.moduleId) a,z_breed tb  where  a.breedid=tb.breedid ";
      if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
      {
        arrayOfObject = paramQueryConditions.getValueArray();
        str = str + " and " + paramQueryConditions.getFieldsSqlClause();
      }
      str = str + " group by a.breedid,tb.breedname,a.moduleId order by a.breedid ";
    }
    else if (paramString.equals("4"))
    {
      str = " select a.moduleId moduleId,tb.code breedname , a.breedID breedid, sum(a.sTradeFee) sumTradeFee, sum(a.sReward) sumReward, sum(a.sBrokereachdivide) sumBrokereachdivide, sum((a.sReward-a.sBrokereachdivide)) sumSelfGain, sum((a.sTradeFee-(a.sReward-a.sBrokereachdivide))) sumMarhetGain, sum((a.sTradeFee-a.sReward)) sumFinalMarhetGain  from  (select t.moduleId, t.brokerid brokerid,t.occurdate occurdate,t.breedID breedid,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t where t.moduleid='4' group by t.brokerid,t.occurdate,t.breedID,t.moduleId) a,v_commodity tb  where  a.breedid=tb.id ";
      if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
      {
        arrayOfObject = paramQueryConditions.getValueArray();
        str = str + " and " + paramQueryConditions.getFieldsSqlClause();
      }
      str = str + " group by a.breedid,tb.code, a.moduleId order by a.breedid ";
    }
    else
    {
      str = "select a.moduleId moduleId,a.breedname breedname,a.breedID breedid, sum(a.sTradeFee) sumTradeFee, sum(a.sReward) sumReward, sum(a.sBrokereachdivide) sumBrokereachdivide, sum((a.sReward-a.sBrokereachdivide)) sumSelfGain, sum((a.sTradeFee-(a.sReward-a.sBrokereachdivide))) sumMarhetGain, sum((a.sTradeFee-a.sReward)) sumFinalMarhetGain  from  (select  t.moduleId moduleId,t.brokerid brokerid,t.occurdate occurdate,t.breedID breedid,t.breedname breedname,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.TradeFee,0)) sTradeFee from m_b_firmrewarddeail t group by t.brokerid,t.occurdate,t.breedID,t.breedname,t.moduleId) a  where  1=1";
      if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
      {
        arrayOfObject = paramQueryConditions.getValueArray();
        str = str + " and " + paramQueryConditions.getFieldsSqlClause();
      }
      str = str + " group by a.breedid,a.breedname, a.moduleId order by a.breedid ";
    }
    return queryBySQL(str, arrayOfObject, paramPageInfo);
  }
  
  public List<Map> tradeFeeList(QueryConditions paramQueryConditions)
  {
    String str = "select a.*,mf.name as firmName, mb.name as brokerName from (select t.firmid,t.brokerid, sum(t.tradefee) tradeFee, sum(t.firstpay) firstPay, sum(t.secondpay) sencondPay, sum(t.reward) reward, sum(t.settleFee) settleFee from m_b_firmrewarddeail t where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str = str + " group by t.firmid,t.brokerid) a,m_firm mf, m_b_broker mb where a.firmid = mf.firmid and a.brokerid = mb.brokerid  order by a.brokerid,a.firmid";
    List localList = queryBySQL(str, arrayOfObject, null);
    return localList;
  }
  
  public List<Map> getBrokerStart()
  {
    String str = "select t.* from m_b_broker t order by brokerID ASC";
    List localList = queryBySQL(str);
    return localList;
  }
  
  public List<Map> getBrokerEnd()
  {
    String str = "select t.* from m_b_broker t order by brokerID DESC";
    List localList = queryBySQL(str);
    return localList;
  }
  
  public List<Map> getDingDanBreeds(boolean paramBoolean)
  {
    String str = "select breedId,breedName from t_a_breed";
    if (paramBoolean) {
      str = str + " order by breedId desc";
    } else {
      str = str + " order by breedId asc";
    }
    List localList = queryBySQL(str);
    return localList;
  }
  
  public List<Map> getGuaPaiCommoditys(boolean paramBoolean)
  {
    String str = "select breedId,breedName from z_breed";
    if (paramBoolean) {
      str = str + " order by breedId desc";
    } else {
      str = str + " order by breedId asc";
    }
    List localList = queryBySQL(str);
    return localList;
  }
  
  public List<Map> getJingJiaBreeds(boolean paramBoolean)
  {
    String str = "select id breedId,code breedName from v_commodity";
    if (paramBoolean) {
      str = str + " order by id desc";
    } else {
      str = str + " order by id asc";
    }
    List localList = queryBySQL(str);
    return localList;
  }
}
