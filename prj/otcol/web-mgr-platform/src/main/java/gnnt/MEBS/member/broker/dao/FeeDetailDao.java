package gnnt.MEBS.member.broker.dao;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.jdbc.core.JdbcTemplate;

public class FeeDetailDao
  extends DaoHelperImpl
{
  public List brokerUserFeeList(QueryConditions paramQueryConditions, PageInfo paramPageInfo, String paramString)
  {
    this.logger.debug("Entering 'brokerUserFeeList' method");
    String str1 = "select * from (select fb.firmId firmId, f.name firmName, fb.brokerId brokerId, moduleId, sum(nvl(fb.quantity, 0)) quantity, sum(nvl(fb.tradeFee, 0)) tradeFee, sum(nvl(fb.firstPay, 0)) firstPay, sum(nvl(fb.secondPay, 0)) secondPay from M_B_FirmRewardDeail fb, M_Firm f where f.firmid = fb.firmid group by fb.firmId, f.name, fb.brokerid, moduleId) where 1 = 1";
    String str2 = str1;
    Object[] arrayOfObject = null;
    if ("summarizingFirmAndSummarizing".equals(paramString))
    {
      str2 = "select * from (select fb.firmId firmId, f.name firmName, fb.brokerId brokerId, moduleId, sum(nvl(fb.quantity, 0)) quantity, sum(nvl(fb.tradeFee, 0)) tradeFee, sum(nvl(fb.firstPay, 0)) firstPay, sum(nvl(fb.secondPay, 0)) secondPay from M_B_FirmRewardDeail fb, M_Firm f where f.firmid = fb.firmid ";
      if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
      {
        arrayOfObject = paramQueryConditions.getValueArray();
        str2 = str2 + " and " + paramQueryConditions.getFieldsSqlClause();
      }
      str2 = str2 + "group by fb.firmId, f.name, fb.brokerid, moduleId) where 1 = 1";
    }
    else if ("summarizingFirmAndSettleDay".equals(paramString))
    {
      str2 = "select * from (select fb.firmId firmId, fb.occurdate, f.name firmName, fb.brokerId brokerId, moduleId, sum(nvl(fb.quantity, 0)) quantity, sum(nvl(fb.tradeFee, 0)) tradeFee, sum(nvl(fb.firstPay, 0)) firstPay, sum(nvl(fb.secondPay, 0)) secondPay from M_B_FirmRewardDeail fb, M_Firm f where f.firmid = fb.firmid ";
      if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
      {
        arrayOfObject = paramQueryConditions.getValueArray();
        str2 = str2 + " and " + paramQueryConditions.getFieldsSqlClause();
      }
      str2 = str2 + " group by fb.firmId, fb.occurdate, f.name, fb.brokerid, moduleId) where 1 = 1";
    }
    else if ("summarizingBreedAndSummarizing".equals(paramString))
    {
      str2 = "select * from (select fb.firmId firmId, f.name firmName, fb.brokerId brokerId, fb.breedname breedName, moduleId, sum(nvl(fb.quantity, 0)) quantity, sum(nvl(fb.tradeFee, 0)) tradeFee, sum(nvl(fb.firstPay, 0)) firstPay, sum(nvl(fb.secondPay, 0)) secondPay from M_B_FirmRewardDeail fb, M_Firm f where f.firmid = fb.firmid ";
      if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
      {
        arrayOfObject = paramQueryConditions.getValueArray();
        str2 = str2 + " and " + paramQueryConditions.getFieldsSqlClause();
      }
      str2 = str2 + " group by fb.firmId, f.name, fb.brokerid, fb.breedname, moduleId) where 1 = 1";
    }
    else if ("summarizingBreedAndSettleDay".equals(paramString))
    {
      str2 = "select * from (select fb.firmId firmId, fb.occurdate, f.name firmName, fb.brokerId brokerId, fb.breedname, moduleId, sum(nvl(fb.quantity, 0)) quantity, sum(nvl(fb.tradeFee, 0)) tradeFee, sum(nvl(fb.firstPay, 0)) firstPay, sum(nvl(fb.secondPay, 0)) secondPay from M_B_FirmRewardDeail fb, M_Firm f where f.firmid = fb.firmid ";
      if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
      {
        arrayOfObject = paramQueryConditions.getValueArray();
        str2 = str2 + " and " + paramQueryConditions.getFieldsSqlClause();
      }
      str2 = str2 + " group by fb.firmId, fb.occurdate, f.name, fb.brokerid, fb.breedname, moduleId) where 1 = 1";
    }
    return queryBySQL(str2, arrayOfObject, paramPageInfo);
  }
  
  public List hisDealDetailList(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    String str = "select * from (select m.brokerId brokerId,t.firmId firmId,t.clearDate clearDate,t.a_TradeNo tradeNo,tb.breedname breedName,t.bs_Flag bsFlag,t.quantity quantity,t.tradeFee tradeFee from T_H_Trade t,M_B_FirmAndBroker m,t_h_commodity tc,t_a_breed tb  where t.firmId=m.firmId and t.commodityid=tc.commodityid and tc.breedid=tb.breedid and t.clearDate=tc.clearDate) where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str = "select tr.* from (select tt.*,rownum r from (" + str + ") tt) tr where tr.r between " + paramInt1 + " and " + paramInt2;
    this.logger.debug("sql:" + str);
    return getJdbcTemplate().queryForList(str, arrayOfObject);
  }
  
  public int hisDealDetailListCount(QueryConditions paramQueryConditions)
  {
    String str = "select count(*) con from (select m.brokerId brokerId,t.firmId firmId,t.clearDate clearDate,t.a_TradeNo tradeNo,tb.breedname breedName,t.bs_Flag bsFlag,t.quantity quantity,t.tradeFee tradeFee from T_H_Trade t,M_B_FirmAndBroker m,t_h_commodity tc,t_a_breed tb  where t.firmId=m.firmId and t.commodityid=tc.commodityid and tc.breedid=tb.breedid and t.clearDate=tc.clearDate) where 1=1 ";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql:" + str);
    List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
    if ((localList == null) || (localList.size() == 0)) {
      return 0;
    }
    Map localMap = (Map)localList.get(0);
    return Integer.parseInt(localMap.get("con") + "");
  }
  
  public List hisDealDetailZcjsList(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    String str = "select * from (select m.brokerId,t.firmid_s firmid,trunc(t.tradedate) clearDate,t.tradeNo tradeNo,zb.breedname breedName,'2' bsFlag,t.quantity quantity,t.tradepoundage_s tradeFee from z_h_trade t,m_b_firmandbroker m, z_breed zb where t.firmid_s=m.firmid and t.breedid=zb.breedid union select m.brokerid,t.firmid_b firmid,trunc(t.tradedate) clearDate,t.tradeNo tradeNo,zb.breedName breedName,'1' bsFlag,t.quantity quantity,t.tradepoundage_s tradeFee from z_h_trade t,m_b_firmandbroker m, z_breed zb where t.firmid_b=m.firmid and t.breedid=zb.breedid) where 1=1";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str = "select tr.* from (select tt.*,rownum r from (" + str + ") tt) tr where tr.r between " + paramInt1 + " and " + paramInt2;
    this.logger.debug("sql:" + str);
    return getJdbcTemplate().queryForList(str, arrayOfObject);
  }
  
  public int hisDealDetailZcjsListCount(QueryConditions paramQueryConditions)
  {
    String str = "select count(*) con from (select m.brokerId,t.firmid_s firmid,trunc(t.tradedate) clearDate,t.tradeNo tradeNo,zb.breedname breedName,'2' bsFlag,t.quantity quantity,t.tradepoundage_s tradeFee from z_h_trade t,m_b_firmandbroker m, z_breed zb where t.firmid_s=m.firmid and t.breedid=zb.breedid union select m.brokerid,t.firmid_b firmid,trunc(t.tradedate) clearDate,t.tradeNo tradeNo,zb.breedName breedName,'1' bsFlag,t.quantity quantity,t.tradepoundage_s tradeFee from z_h_trade t,m_b_firmandbroker m, z_breed zb where t.firmid_b=m.firmid and t.breedid=zb.breedid) where 1=1";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql:" + str);
    List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
    if ((localList == null) || (localList.size() == 0)) {
      return 0;
    }
    Map localMap = (Map)localList.get(0);
    return Integer.parseInt(localMap.get("con") + "");
  }
  
  public List hisDealDetailVdList(QueryConditions paramQueryConditions, int paramInt1, int paramInt2)
  {
    String str = "select * from (select m.brokerId,m.firmId,a.clearDate,TradeNo,breedName,'1' bsFlag,a.b_poundage tradeFee,quantity from (select t.contractid TradeNo,trunc(t.tradedate) clearDate,'竞价' breedName,t.amount quantity,t.s_poundage,t.b_poundage,case when trademode=0 then t.userid else vc.userid end bFirmId ,case when trademode=0 then vc.userid else t.userid end sFirmId from v_hisbargain t,v_commodity vc where vc.id=t.commodityid) a,m_b_firmandbroker m where a.bFirmid=m.firmId union select m.brokerId,m.firmId,a.clearDate,TradeNo,breedName,'2' bsFlag,a.s_poundage tradeFee,quantity from (select t.contractid TradeNo,trunc(t.tradedate) clearDate,'竞价' breedName,t.amount quantity,t.s_poundage,t.b_poundage,case when trademode=0 then t.userid else vc.userid end bFirmId ,case when trademode=0 then vc.userid else t.userid end sFirmId from v_hisbargain t,v_commodity vc where vc.id=t.commodityid) a,m_b_firmandbroker m where a.sFirmid=m.firmId) where 1=1";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    str = "select tr.* from (select tt.*,rownum r from (" + str + ") tt) tr where tr.r between " + paramInt1 + " and " + paramInt2;
    this.logger.debug("sql:" + str);
    return getJdbcTemplate().queryForList(str, arrayOfObject);
  }
  
  public int hisDealDetailVdListCount(QueryConditions paramQueryConditions)
  {
    String str = "select count(*) con from (select m.brokerId,m.firmId,a.clearDate,TradeNo,breedName,'1' bsFlag,a.b_poundage tradeFee,quantity from (select t.contractid TradeNo,trunc(t.tradedate) clearDate,'竞价' breedName,t.amount quantity,t.s_poundage,t.b_poundage,case when trademode=0 then t.userid else vc.userid end bFirmId ,case when trademode=0 then vc.userid else t.userid end sFirmId from v_hisbargain t,v_commodity vc where vc.id=t.commodityid) a,m_b_firmandbroker m where a.bFirmid=m.firmId union select m.brokerId,m.firmId,a.clearDate,TradeNo,breedName,'2' bsFlag,a.s_poundage tradeFee,quantity from (select t.contractid TradeNo,trunc(t.tradedate) clearDate,'竞价' breedName,t.amount quantity,t.s_poundage,t.b_poundage,case when trademode=0 then t.userid else vc.userid end bFirmId ,case when trademode=0 then vc.userid else t.userid end sFirmId from v_hisbargain t,v_commodity vc where vc.id=t.commodityid) a,m_b_firmandbroker m where a.sFirmid=m.firmId) where 1=1";
    Object[] arrayOfObject = null;
    if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null))
    {
      arrayOfObject = paramQueryConditions.getValueArray();
      str = str + " and " + paramQueryConditions.getFieldsSqlClause();
    }
    this.logger.debug("sql:" + str);
    List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
    if ((localList == null) || (localList.size() == 0)) {
      return 0;
    }
    Map localMap = (Map)localList.get(0);
    return Integer.parseInt(localMap.get("con") + "");
  }
}
