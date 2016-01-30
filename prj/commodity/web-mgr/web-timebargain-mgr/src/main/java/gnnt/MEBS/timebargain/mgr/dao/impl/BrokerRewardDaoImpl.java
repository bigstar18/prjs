package gnnt.MEBS.timebargain.mgr.dao.impl;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.timebargain.mgr.dao.BrokerRewardDao;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Repository;

@Repository("brokerRewardDao")
public class BrokerRewardDaoImpl extends StandardDao
  implements BrokerRewardDao
{
  public List getBreedStartList()
  {
    String sql = "select breedid,breedname from t_a_breed order by breedid asc";

    return queryBySql(sql);
  }

  public List getBreedEndList()
  {
    String sql = "select breedid from t_a_breed order by breedid desc";

    return queryBySql(sql);
  }

  public List getBrokerStartList()
  {
    String sql = "select brokerId from br_broker order by brokerId asc";

    return queryBySql(sql);
  }

  public List getBrokerEndList()
  {
    String sql = "select brokerId from br_broker order by brokerId desc";

    return queryBySql(sql);
  }

  public List getFirmRewardSum(String isPartition, String startBreed, String endBreed, String startFirm, String endFirm, String beginDate, String endDate)
  {
    String sql = "select a.firmid firmid, (select name from m_firm where firmid = a.firmid) firmname, a.brokerid brokerid, (select name from br_broker where brokerid = a.brokerid) brokername, a.breedid breedid, a.cleardate cleardate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward - a.sBrokereachdivide) sumSelfGain, (a.sTradeFee - (a.sReward - a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee - a.sReward) sumFinalMarhetGain from (select r.firmid firmid, r.brokerid brokerid, r.cleardate cleardate, c.breedid breedid, sum(nvl(r.reward, 0)) sReward, sum(nvl(r.brokereachdivide, 0)) sBrokereachdivide, sum(nvl(r.reward + r.marketreward, 0)) sTradeFee from t_br_firmrewarddeail r join t_commodity c on r.commodityid = c.commodityid group by r.firmid, r.brokerid, r.cleardate, c.breedid) a where 1 = 1 ";

    if ((!"".equals(startFirm)) && (startFirm != null)) {
      sql = sql + "and a.firmid >= '" + startFirm + "' ";
    }

    if ((!"".equals(endFirm)) && (endFirm != null)) {
      sql = sql + "and a.firmid <= '" + endFirm + "' ";
    }
    if ((!"".equals(startBreed)) && (startBreed != null)) {
      sql = sql + "and a.breedid >= " + startBreed + " ";
    }

    if ((!"".equals(endBreed)) && (endBreed != null)) {
      sql = sql + "and a.breedid <= " + endBreed + " ";
    }
    if ((!"".equals(beginDate)) && (!"".equals(endDate)) && (beginDate != null) && (endDate != null)) {
      sql = sql + "and a.cleardate >= to_date('" + beginDate + "','yyyy-MM-dd') " + 
        "and a.cleardate <= to_date('" + endDate + "','yyyy-MM-dd')";
    }

    sql = sql + "order by a.firmid, a.brokerid, a.cleardate ";
    if ("N".equals(isPartition))
      sql = "select d.firmid firmid, d.firmname firmname, d.brokerid brokerid, d.brokername brokername, d.cleardate cleardate, sum(d.sumTradeFee) sumTradeFee,sum(d.sumBrokereachdivide) sumBrokereachdivide,sum(d.sumReward) sumReward,sum(d.sumSelfGain) sumSelfGain,sum(d.sumMarhetGain) sumMarhetGain,sum(d.sumFinalMarhetGain) sumFinalMarhetGain from ( " + 
        sql + " ) d " + 
        "group by d.brokerid, d.brokername, d.firmid, d.firmname, d.cleardate " + 
        "order by d.firmid, d.brokerid, d.cleardate desc ";
    else if ("Y".equals(isPartition)) {
      sql = "select d.firmid firmid, d.firmname firmname, d.brokerid brokerid, d.brokername brokername, d.cleardate cleardate, d.breedid breedid, sum(d.sumTradeFee) sumTradeFee,sum(d.sumBrokereachdivide) sumBrokereachdivide,sum(d.sumReward) sumReward,sum(d.sumSelfGain) sumSelfGain,sum(d.sumMarhetGain) sumMarhetGain,sum(d.sumFinalMarhetGain) sumFinalMarhetGain from ( " + 
        sql + " ) d " + 
        "group by d.brokerid, d.brokername, d.firmid, d.firmname, d.cleardate, d.breedid " + 
        "order by d.firmid, d.brokerid, d.cleardate desc";
    }

    return queryBySql(sql);
  }

  public List getBrokerRewardSum(String startBroker, String endBroker, String beginDate, String endDate)
  {
    String sql = "select d.brokerid brokerid, d.brokername brokername, d.cleardate cleardate, sum(d.sumTradeFee) sumTradeFee,sum(d.sumBrokereachdivide) sumBrokereachdivide, sum(d.sumReward) sumReward, sum(d.sumSelfGain) sumSelfGain, sum(d.sumMarhetGain) sumMarhetGain, sum(d.sumFinalMarhetGain) sumFinalMarhetGain from (select a.brokerid brokerid, (select name from br_broker where brokerid = a.brokerid) brokername, a.cleardate cleardate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward - a.sBrokereachdivide) sumSelfGain, (a.sTradeFee - (a.sReward - a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee - a.sReward) sumFinalMarhetGain from (select r.brokerid brokerid, r.cleardate cleardate, sum(nvl(r.reward, 0)) sReward, sum(nvl(r.brokereachdivide, 0)) sBrokereachdivide, sum(nvl(r.reward + r.marketreward, 0)) sTradeFee from T_BR_FirmRewardDeail r group by r.brokerid, r.cleardate) a where 1 = 1 ";

    if ((!"".equals(startBroker)) && (startBroker != null)) {
      sql = sql + "and a.brokerid >= '" + startBroker + "' ";
    }

    if ((!"".equals(endBroker)) && (endBroker != null)) {
      sql = sql + "and a.brokerid <= '" + endBroker + "' ";
    }
    if ((!"".equals(beginDate)) && (!"".equals(endDate)) && (beginDate != null) && (endDate != null)) {
      sql = sql + "and a.cleardate >= to_date('" + beginDate + "','yyyy-MM-dd') " + 
        "and a.cleardate <= to_date('" + endDate + "','yyyy-MM-dd')";
    }

    sql = sql + "order by a.brokerid, a.cleardate) d group by d.brokerid, d.brokername,d.cleardate order by d.brokerid, d.cleardate desc ";

    return queryBySql(sql);
  }

  public List getBreedRewardSum(String startBreed, String endBreed, String beginDate, String endDate)
  {
    String sql = "select a.breedID breedid, tb.breedname breedname, a.cleardate cleardate, sum(a.sTradeFee) sumTradeFee, sum(a.sReward) sumReward, sum(a.sBrokereachdivide) sumBrokereachdivide, sum((a.sReward - a.sBrokereachdivide)) sumSelfGain, sum((a.sTradeFee - (a.sReward - a.sBrokereachdivide))) sumMarhetGain, sum((a.sTradeFee - a.sReward)) sumFinalMarhetGain from (select r.brokerid brokerid, r.cleardate cleardate, c.breedid breedid, sum(nvl(r.reward, 0)) sReward, sum(nvl(r.brokereachdivide, 0)) sBrokereachdivide, sum(nvl(r.reward + r.marketreward, 0)) sTradeFee from t_br_firmrewarddeail r join t_commodity c on r.commodityid = c.commodityid group by r.brokerid, r.cleardate, c.breedid) a, t_a_breed tb where a.breedid = tb.breedid ";

    if ((!"".equals(startBreed)) && (startBreed != null)) {
      sql = sql + "and a.breedid >= " + startBreed + " ";
    }

    if ((!"".equals(endBreed)) && (endBreed != null)) {
      sql = sql + "and a.breedid <= " + endBreed + " ";
    }
    if ((!"".equals(beginDate)) && (!"".equals(endDate)) && (beginDate != null) && (endDate != null)) {
      sql = sql + "and a.cleardate >= to_date('" + beginDate + "','yyyy-MM-dd') " + 
        "and a.cleardate <= to_date('" + endDate + "','yyyy-MM-dd')";
    }

    sql = sql + "group by a.breedid, tb.breedname, a.cleardate order by a.cleardate desc";

    return queryBySql(sql);
  }

  public List getBrokerTradeFee(HttpServletRequest request)
  {
    String rewardtype = request
      .getParameter("gnnt_primary.rewardtype[=][Long]");
    String filter = "";
    String sql = "select s.brokerid brokerid, s.name, nvl(sum(s.sumTradeFee), 0) sumTradeFee, nvl(sum(s.sumBrokereachdivide), 0) sumBrokereachdivide, nvl(sum(s.sumReward), 0) sumReward, nvl(sum(s.sumSelfGain), 0) sumSelfGain, nvl(sum(s.sumMarhetGain), 0) sumMarhetGain, nvl(sum(s.sumFinalMarhetGain), 0) sumFinalMarhetGain from (select a.rewardtype rewardtype, a.brokerid brokerid, a.breedid breedid, (select name from br_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.cleardate cleardate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.rewardtype rewardtype, (select ta.breedid from t_a_breed ta,t_commodity tc where t.commodityid =tc.commodityid and tc.breedid = ta.breedid) breedid,t.brokerid brokerid,t.firmid firmid,t.cleardate cleardate,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl((t.reward + t.marketreward),0)) sTradeFee from t_br_firmrewarddeail t  group by t.rewardtype, t.firmid,t.brokerid,t.cleardate,t.commodityid) a where 1=1) s where 1=1";

    String beginBroker = request
      .getParameter("gnnt_primary.beginBroker[=][Long]");
    String endBroker = request
      .getParameter("gnnt_primary.endBroker[=][Long]");
    if ((beginBroker != null) && (endBroker != null) && (!"".equals(beginBroker)) && 
      (!"".equals(endBroker)))
    {
      filter = filter + " and s.brokerid >= '" + beginBroker + "'and s.brokerid <= '" + 
        endBroker + "'";
    }

    String beginDate = request
      .getParameter("gnnt_primary.occurDate[>=][date]");
    String endDate = request
      .getParameter("gnnt_primary.occurDate[<=][date]");
    if ((beginDate != null) && (endDate != null)) {
      this.logger.debug("日期不为空哦哦哦哦哦哦哦");
      if ((!"".equals(beginDate)) && (!"".equals(endDate)))
        filter = filter + " and cleardate >= to_date('" + beginDate + 
          "','yyyy-MM-dd') and cleardate <= to_date('" + 
          endDate + "','yyyy-MM-dd')";
    }
    sql = sql + filter;
    sql = sql + " group by s.brokerid, s.name ";

    this.logger.debug("!!!!!!!!!!!!!!!!!!" + sql);
    return queryBySql(sql);
  }

  public List getBreedTradeFee(HttpServletRequest request)
  {
    String rewardtype = request
      .getParameter("gnnt_primary.rewardtype[=][Long]");
    String filter = "";
    String sql = "select b.breedid breedid, b.breedname, nvl(sum(s.sumTradeFee), 0) sumTradeFee, nvl(sum(s.sumBrokereachdivide), 0) sumBrokereachdivide, nvl(sum(s.sumReward), 0) sumReward, nvl(sum(s.sumSelfGain), 0) sumSelfGain, nvl(sum(s.sumMarhetGain), 0) sumMarhetGain, nvl(sum(s.sumFinalMarhetGain), 0) sumFinalMarhetGain from (select a.rewardtype rewardtype, a.brokerid brokerid,  c.breedid breedid, (select name from br_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.cleardate cleardate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.rewardtype rewardtype,  t.brokerid brokerid,t.firmid firmid,t.commodityid,t.cleardate cleardate,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl((t.reward + t.marketreward),0)) sTradeFee from t_br_firmrewarddeail t  group by t.rewardtype, t.firmid,t.brokerid,t.cleardate,t.commodityid) a,t_commodity c where 1=1 and a.commodityid = c.commodityid) s,t_a_breed b  where 1=1 and s.breedid = b.breedid ";

    String beginBreed = request
      .getParameter("gnnt_primary.beginBreed[=][Long]");
    String endBreed = request
      .getParameter("gnnt_primary.endBreed[=][Long]");
    if ((beginBreed != null) && (endBreed != null) && (!"".equals(beginBreed)) && 
      (!"".equals(endBreed)))
    {
      filter = filter + "and s.breedid >= '" + beginBreed + "'and s.breedid <= '" + 
        endBreed + " '";
    }

    String beginDate = request
      .getParameter("gnnt_primary.occurDate[>=][date]");
    String endDate = request
      .getParameter("gnnt_primary.occurDate[<=][date]");
    if ((beginDate != null) && (endDate != null) && 
      (!"".equals(beginDate)) && (!"".equals(endDate))) {
      filter = filter + " and cleardate >= to_date('" + beginDate + 
        "','yyyy-MM-dd') and cleardate <= to_date('" + 
        endDate + "','yyyy-MM-dd')";
    }
    sql = sql + filter;

    sql = sql + " group by b.breedid, b.breedname ";

    this.logger.debug("!!!!!!!!!!!!!!!!!!" + sql);
    return queryBySql(sql);
  }

  public List getFirmTradeFee(HttpServletRequest request)
  {
    String rewardtype = request
      .getParameter("gnnt_primary.rewardtype[=][Long]");
    String filter = "";
    String sql = " select a.brokerid brokerid,  (select name from br_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.breedid breedid, (select breedname from t_a_breed where breedid = a.breedid) breedname, a.cleardate cleardate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select (select ta.breedid from t_a_breed ta,t_commodity tc where t.commodityid =tc.commodityid and tc.breedid = ta.breedid) breedid,t.brokerid brokerid,t.firmid firmid,t.cleardate cleardate,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl((t.reward + t.marketreward),0)) sTradeFee from t_br_firmrewarddeail t  group by t.firmid,t.brokerid,t.cleardate,t.commodityid) a where 1=1 ";

    String beginFirm = request
      .getParameter("gnnt_primary.beginFirm[=][Long]");
    String endFirm = request.getParameter("gnnt_primary.endFirm[=][Long]");
    if ((beginFirm != null) && (endFirm != null) && (!"".equals(beginFirm)) && 
      (!"".equals(endFirm)))
    {
      filter = filter + "and a.firmid >= '" + beginFirm + "' and a.firmid <= '" + 
        endFirm + "'";
    }

    String beginDate = request
      .getParameter("gnnt_primary.occurDate[>=][date]");
    String endDate = request
      .getParameter("gnnt_primary.occurDate[<=][date]");
    if ((beginDate != null) && (endDate != null) && 
      (!"".equals(beginDate)) && (!"".equals(endDate))) {
      filter = filter + " and cleardate >= to_date('" + beginDate + 
        "','yyyy-MM-dd') and cleardate <= to_date('" + 
        endDate + "','yyyy-MM-dd')";
    }
    sql = sql + filter;
    this.logger.debug("!!!!!!!!!!!!!!!!!!" + sql);
    return queryBySql(sql);
  }

  public List getBrokerUserFeeList(String startFirm, String endFirm, String beginDate, String endDate, String summarizingWay, String brokerid, String breedId)
  {
    String defaultSql = "select * from (select fb.firmId firmId, f.name firmName, fb.brokerId brokerId, sum(nvl(fb.quantity, 0)) quantity, sum(nvl((fb.reward + fb.marketreward), 0)) tradeFee, sum(nvl(fb.firstPay, 0)) firstPay, sum(nvl(fb.secondPay, 0)) secondPay from t_Br_FirmRewardDeail fb, M_Firm f where f.firmid = fb.firmid group by fb.firmId, f.name, fb.brokerid) where 1 = 1";
    String sql = defaultSql;

    if ("summarizingFirmAndSummarizing".equals(summarizingWay))
    {
      sql = "select * from (select fb.firmId firmId, f.name firmName, fb.brokerId brokerId, sum(nvl(fb.quantity, 0)) quantity, sum(nvl((fb.reward + fb.marketreward), 0)) tradeFee, sum(nvl(fb.firstPay, 0)) firstPay, sum(nvl(fb.secondPay, 0)) secondPay from t_Br_FirmRewardDeail fb, M_Firm f where f.firmid = fb.firmid ";

      if ((!"".equals(startFirm)) && (startFirm != null)) {
        sql = sql + "and fb.firmId >= '" + startFirm + "' ";
      }

      if ((!"".equals(endFirm)) && (endFirm != null)) {
        sql = sql + "and fb.firmId <= '" + endFirm + "' ";
      }
      if ((!"".equals(beginDate)) && (!"".equals(endDate)) && (beginDate != null) && (endDate != null)) {
        sql = sql + "and fb.cleardate >= to_date('" + beginDate + "','yyyy-MM-dd') " + 
          "and fb.cleardate <= to_date('" + endDate + "','yyyy-MM-dd')";
      }
      if ((!"".equals(brokerid)) && (brokerid != null)) {
        sql = sql + "and fb.brokerId = '" + brokerid + "' ";
      }
      sql = sql + "group by fb.firmId, f.name, fb.brokerid) where 1 = 1";
    }
    else if ("summarizingFirmAndSettleDay".equals(summarizingWay))
    {
      sql = "select * from (select fb.firmId firmId, fb.cleardate, f.name firmName, fb.brokerId brokerId, sum(nvl(fb.quantity, 0)) quantity, sum(nvl((fb.reward + fb.marketreward), 0)) tradeFee, sum(nvl(fb.firstPay, 0)) firstPay, sum(nvl(fb.secondPay, 0)) secondPay from t_Br_FirmRewardDeail fb, M_Firm f where f.firmid = fb.firmid ";

      if ((!"".equals(startFirm)) && (startFirm != null)) {
        sql = sql + "and fb.firmId >= '" + startFirm + "' ";
      }

      if ((!"".equals(endFirm)) && (endFirm != null)) {
        sql = sql + "and fb.firmId <= '" + endFirm + "' ";
      }
      if ((!"".equals(beginDate)) && (!"".equals(endDate)) && (beginDate != null) && (endDate != null)) {
        sql = sql + "and fb.cleardate >= to_date('" + beginDate + "','yyyy-MM-dd') " + 
          "and fb.cleardate <= to_date('" + endDate + "','yyyy-MM-dd')";
      }
      if ((!"".equals(brokerid)) && (brokerid != null)) {
        sql = sql + "and fb.brokerId = '" + brokerid + "' ";
      }
      sql = sql + " group by fb.firmId, fb.cleardate, f.name, fb.brokerid) where 1 = 1";
    } else if ("summarizingBreedAndSummarizing".equals(summarizingWay))
    {
      sql = "select * from (select fb.firmId firmId, f.name firmName, fb.brokerId brokerId, fb.breedid breedid, (select breedname from t_a_breed where breedid = fb.breedid) breedname, sum(nvl(fb.quantity, 0)) quantity, sum(nvl((fb.reward + fb.marketreward), 0)) tradeFee, sum(nvl(fb.firstPay, 0)) firstPay, sum(nvl(fb.secondPay, 0)) secondPay from (select * from t_br_firmrewarddeail f,t_commodity c where f.commodityid=c.commodityid) fb, M_Firm f where f.firmid = fb.firmid ";

      if ((!"".equals(startFirm)) && (startFirm != null)) {
        sql = sql + "and fb.firmId >= '" + startFirm + "' ";
      }

      if ((!"".equals(endFirm)) && (endFirm != null)) {
        sql = sql + "and fb.firmId <= '" + endFirm + "' ";
      }
      if ((!"".equals(beginDate)) && (!"".equals(endDate)) && (beginDate != null) && (endDate != null)) {
        sql = sql + "and fb.cleardate >= to_date('" + beginDate + "','yyyy-MM-dd') " + 
          "and fb.cleardate <= to_date('" + endDate + "','yyyy-MM-dd')";
      }
      if ((!"".equals(brokerid)) && (brokerid != null)) {
        sql = sql + "and fb.brokerId = '" + brokerid + "' ";
      }

      sql = sql + " group by fb.firmId, f.name, fb.brokerid, fb.breedid) where 1 = 1";

      if ((!"".equals(breedId)) && (breedId != null))
        sql = sql + "and breedid = '" + breedId + "' ";
    }
    else if ("summarizingBreedAndSettleDay".equals(summarizingWay))
    {
      sql = "select * from (select fb.firmId firmId, fb.cleardate, f.name firmName, fb.brokerId brokerId, fb.breedid breedid, (select breedname from t_a_breed where breedid = fb.breedid) breedname, sum(nvl(fb.quantity, 0)) quantity, sum(nvl((fb.reward + fb.marketreward), 0)) tradeFee, sum(nvl(fb.firstPay, 0)) firstPay, sum(nvl(fb.secondPay, 0)) secondPay from (select * from t_br_firmrewarddeail f,t_commodity c where f.commodityid=c.commodityid) fb, M_Firm f where f.firmid = fb.firmid ";

      if ((!"".equals(startFirm)) && (startFirm != null)) {
        sql = sql + "and fb.firmId >= '" + startFirm + "' ";
      }

      if ((!"".equals(endFirm)) && (endFirm != null)) {
        sql = sql + "and fb.firmId <= '" + endFirm + "' ";
      }
      if ((!"".equals(beginDate)) && (!"".equals(endDate)) && (beginDate != null) && (endDate != null)) {
        sql = sql + "and fb.cleardate >= to_date('" + beginDate + "','yyyy-MM-dd') " + 
          "and fb.cleardate <= to_date('" + endDate + "','yyyy-MM-dd')";
      }
      if ((!"".equals(brokerid)) && (brokerid != null)) {
        sql = sql + "and fb.brokerId = '" + brokerid + "' ";
      }

      sql = sql + " group by fb.firmId, fb.cleardate, f.name, fb.brokerid, fb.breedid) where 1 = 1";

      if ((!"".equals(breedId)) && (breedId != null)) {
        sql = sql + "and breedid = '" + breedId + "' ";
      }
    }

    return queryBySql(sql);
  }

  public List getHisDealDetailList(String brokerId, String breedId, String firmId, String bsFlag, String beginDate, String endDate)
  {
    String sql = "select tr.* from (select tt.* from (select * from (select m.brokerId   brokerId, t.firmId     firmId, t.clearDate  clearDate, t.a_TradeNo  tradeNo, tb.breedid breedId, tb.breedname breedName, t.bs_Flag    bsFlag, t.quantity   quantity, t.tradeFee   tradeFee from T_H_Trade         t, Br_FirmAndBroker m, t_h_commodity     tc, t_a_breed         tb where t.firmId = m.firmId and t.commodityid = tc.commodityid and tc.breedid = tb.breedid and t.clearDate = tc.clearDate) where 1 = 1) tt) tr where 1=1 ";

    if ((!"".equals(brokerId)) && (brokerId != null)) {
      sql = sql + "and tr.brokerId = '" + brokerId + "' ";
    }

    if ((!"".equals(breedId)) && (breedId != null)) {
      sql = sql + "and tr.breedid = " + breedId + " ";
    }
    if ((!"".equals(firmId)) && (firmId != null)) {
      sql = sql + "and tr.firmId = '" + firmId + "' ";
    }
    if ((!"".equals(bsFlag)) && (bsFlag != null)) {
      sql = sql + "and tr.bsFlag = '" + bsFlag + "' ";
    }
    if ((!"".equals(beginDate)) && (!"".equals(endDate)) && (beginDate != null) && (endDate != null)) {
      sql = sql + "and tr.cleardate >= to_date('" + beginDate + "','yyyy-MM-dd') " + 
        "and tr.cleardate <= to_date('" + endDate + "','yyyy-MM-dd')";
    }

    return queryBySql(sql);
  }
}