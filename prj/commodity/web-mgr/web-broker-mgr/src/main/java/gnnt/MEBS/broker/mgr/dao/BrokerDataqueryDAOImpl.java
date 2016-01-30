package gnnt.MEBS.broker.mgr.dao;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Repository;

@Repository("brokerDataqueryDAO")
public class BrokerDataqueryDAOImpl extends StandardDao
  implements BrokerDataqueryDAO
{
  public List getBrokerTradeFee(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = paramHttpServletRequest.getParameter("gnnt_primary.rewardtype[=][Long]");
    String str2 = "";
    String str3 = " select a.rewardtype rewardtype, a.brokerid brokerid, a.breedid breedid, (select name from br_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.cleardate cleardate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.rewardtype rewardtype, (select ta.breedid from t_a_breed ta,t_commodity tc where t.commodityid =tc.commodityid and tc.breedid = ta.breedid) breedid,t.brokerid brokerid,t.firmid firmid,t.cleardate cleardate,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.trademoney,0)) sTradeFee from t_br_firmrewarddeail t  group by t.rewardtype, t.firmid,t.brokerid,t.cleardate,t.commodityid) a where 1=1 ";
    String str4 = paramHttpServletRequest.getParameter("gnnt_primary.beginBroker[=][Long]");
    String str5 = paramHttpServletRequest.getParameter("gnnt_primary.endBroker[=][Long]");
    if ((str4 != null) && (str5 != null) && (!"".equals(str4)) && (!"".equals(str5)))
      str2 = str2 + "and a.brokerid >= '" + str4 + "'and a.brokerid <= '" + str5 + "'";
    String str6 = paramHttpServletRequest.getParameter("gnnt_primary.occurDate[>=][date]");
    String str7 = paramHttpServletRequest.getParameter("gnnt_primary.occurDate[<=][date]");
    if ((str6 != null) && (str7 != null))
    {
      this.logger.debug("日期不为空哦哦哦哦哦哦哦");
      if ((!"".equals(str6)) && (!"".equals(str7)))
        str2 = str2 + " and cleardate >= to_date('" + str6 + "','yyyy-MM-dd') and cleardate <= to_date('" + str7 + "','yyyy-MM-dd')";
    }
    str3 = str3 + str2;
    String str8 = "";
    String str9 = paramHttpServletRequest.getParameter("gnnt_primary.sumType[=][Long]");
    if ((!"".equals(str9)) && (str9 != null))
    {
      if ("breed".equals(str9))
        str8 = "select s.brokerid brokerid,s.breedid breedid,s.name name,nvl(sum(s.sumTradeFee),0) sumTradeFee,nvl(sum(s.sumBrokereachdivide),0) sumBrokereachdivide,nvl(sum(s.sumReward),0) sumReward,nvl(sum(s.sumSelfGain),0) sumSelfGain,nvl(sum(s.sumMarhetGain),0) sumMarhetGain,nvl(sum(s.sumFinalMarhetGain),0) sumFinalMarhetGain from(" + str3 + ") s group by s.breedid,s.brokerid,s.name";
      else if (("sumByBreed".equals(str9)) || ("sumByDay".equals(str9)))
        str8 = "select s.brokerid brokerid,s.name name,nvl(sum(s.sumTradeFee),0) sumTradeFee,nvl(sum(s.sumBrokereachdivide),0) sumBrokereachdivide,nvl(sum(s.sumReward),0) sumReward,nvl(sum(s.sumSelfGain),0) sumSelfGain,nvl(sum(s.sumMarhetGain),0) sumMarhetGain,nvl(sum(s.sumFinalMarhetGain),0) sumFinalMarhetGain from(" + str3 + ") s group by s.brokerid,s.name";
      else if ("day".equals(str9))
        str8 = "select s.brokerid brokerid,s.name name,s.cleardate cleardate,nvl(sum(s.sumTradeFee),0) sumTradeFee,nvl(sum(s.sumBrokereachdivide),0) sumBrokereachdivide,nvl(sum(s.sumReward),0) sumReward,nvl(sum(s.sumSelfGain),0) sumSelfGain,nvl(sum(s.sumMarhetGain),0) sumMarhetGain,nvl(sum(s.sumFinalMarhetGain),0) sumFinalMarhetGain from(" + str3 + ") s group by s.cleardate,s.brokerid,s.name";
      return queryBySql(str8);
    }
    this.logger.debug("!!!!!!!!!!!!!!!!!!" + str3);
    return queryBySql(str3);
  }

  public List getBreedTradeFee(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = paramHttpServletRequest.getParameter("gnnt_primary.rewardtype[=][Long]");
    String str2 = "";
    String str3 = " select a.rewardtype rewardtype, a.brokerid brokerid, a.breedid breedid,a.breedname breedname, (select name from br_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.cleardate cleardate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.rewardtype rewardtype, (select ta.breedname from t_a_breed ta,t_commodity tc where ta.breedid=tc.breedid and tc.commodityid = t.commodityid) breedname,(select ta.breedid from t_a_breed ta,t_commodity tc where t.commodityid =tc.commodityid and tc.breedid = ta.breedid) breedid,t.brokerid brokerid,t.firmid firmid,t.cleardate cleardate,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.trademoney,0)) sTradeFee from t_br_firmrewarddeail t  group by t.rewardtype, t.firmid,t.brokerid,t.cleardate,t.commodityid) a where 1=1 ";
    String str4 = paramHttpServletRequest.getParameter("gnnt_primary.beginBreed[=][Long]");
    String str5 = paramHttpServletRequest.getParameter("gnnt_primary.endBreed[=][Long]");
    if ((str4 != null) && (str5 != null) && (!"".equals(str4)) && (!"".equals(str5)))
      str2 = str2 + "and a.breedid >= " + str4 + "and a.breedid <= " + str5;
    String str6 = paramHttpServletRequest.getParameter("gnnt_primary.occurDate[>=][date]");
    String str7 = paramHttpServletRequest.getParameter("gnnt_primary.occurDate[<=][date]");
    if ((str6 != null) && (str7 != null) && (!"".equals(str6)) && (!"".equals(str7)))
      str2 = str2 + " and cleardate >= to_date('" + str6 + "','yyyy-MM-dd') and cleardate <= to_date('" + str7 + "','yyyy-MM-dd')";
    str3 = str3 + str2;
    String str8 = "";
    String str9 = paramHttpServletRequest.getParameter("gnnt_primary.sumType[=][Long]");
    if ((!"".equals(str9)) && (str9 != null))
    {
      if ("breed".equals(str9))
        str8 = "select s.brokerid brokerid,s.breedid breedid,s.firmname,s.firmid,s.breedname,s.name name,nvl(sum(s.sumTradeFee),0) sumTradeFee,nvl(sum(s.sumBrokereachdivide),0) sumBrokereachdivide,nvl(sum(s.sumReward),0) sumReward,nvl(sum(s.sumSelfGain),0) sumSelfGain,nvl(sum(s.sumMarhetGain),0) sumMarhetGain,nvl(sum(s.sumFinalMarhetGain),0) sumFinalMarhetGain from(" + str3 + ") s group by s.breedid,s.brokerid,s.name,s.breedname,s.firmname,s.firmid";
      else if (("sumByBreed".equals(str9)) || ("sumByDay".equals(str9)))
        str8 = "select s.brokerid brokerid,s.firmname,s.firmid,s.name name,nvl(sum(s.sumTradeFee),0) sumTradeFee,nvl(sum(s.sumBrokereachdivide),0) sumBrokereachdivide,nvl(sum(s.sumReward),0) sumReward,nvl(sum(s.sumSelfGain),0) sumSelfGain,nvl(sum(s.sumMarhetGain),0) sumMarhetGain,nvl(sum(s.sumFinalMarhetGain),0) sumFinalMarhetGain from(" + str3 + ") s group by s.brokerid,s.name,s.firmname,s.firmid";
      else if ("day".equals(str9))
        str8 = "select s.firmname,s.firmid,s.brokerid brokerid,s.name name,s.cleardate cleardate,nvl(sum(s.sumTradeFee),0) sumTradeFee,nvl(sum(s.sumBrokereachdivide),0) sumBrokereachdivide,nvl(sum(s.sumReward),0) sumReward,nvl(sum(s.sumSelfGain),0) sumSelfGain,nvl(sum(s.sumMarhetGain),0) sumMarhetGain,nvl(sum(s.sumFinalMarhetGain),0) sumFinalMarhetGain from(" + str3 + ") s group by s.cleardate,s.brokerid,s.name,s.firmname,s.firmid";
      return queryBySql(str8);
    }
    this.logger.debug("!!!!!!!!!!!!!!!!!!" + str3);
    return queryBySql(str3);
  }

  public List getFirmTradeFee(HttpServletRequest paramHttpServletRequest)
  {
    String str1 = paramHttpServletRequest.getParameter("gnnt_primary.rewardtype[=][Long]");
    String str2 = "";
    String str3 = " select a.rewardtype rewardtype, a.brokerid brokerid, a.breedid breedid, (select name from br_broker where brokerid=a.brokerid) name, a.firmid firmid, (select name from m_firm where firmid=a.firmid) firmname, a.cleardate cleardate, a.sTradeFee sumTradeFee, a.sBrokereachdivide sumBrokereachdivide, a.sReward sumReward, (a.sReward-a.sBrokereachdivide) sumSelfGain, (a.sTradeFee-(a.sReward-a.sBrokereachdivide)) sumMarhetGain, (a.sTradeFee-a.sReward) sumFinalMarhetGain  from  (select t.rewardtype rewardtype, (select ta.breedid from t_a_breed ta,t_commodity tc where t.commodityid =tc.commodityid and tc.breedid = ta.breedid) breedid,t.brokerid brokerid,t.firmid firmid,t.cleardate cleardate,sum(nvl(t.reward,0)) sReward, sum(nvl(t.brokereachdivide,0)) sBrokereachdivide,sum(nvl(t.trademoney,0)) sTradeFee from t_br_firmrewarddeail t  group by t.rewardtype, t.firmid,t.brokerid,t.cleardate,t.commodityid) a where 1=1 ";
    String str4 = paramHttpServletRequest.getParameter("gnnt_primary.beginFirm[=][Long]");
    String str5 = paramHttpServletRequest.getParameter("gnnt_primary.endFirm[=][Long]");
    if ((str4 != null) && (str5 != null) && (!"".equals(str4)) && (!"".equals(str5)))
      str2 = str2 + "and a.firmid >= '" + str4 + "' and a.firmid <= '" + str5 + "'";
    String str6 = paramHttpServletRequest.getParameter("gnnt_primary.occurDate[>=][date]");
    String str7 = paramHttpServletRequest.getParameter("gnnt_primary.occurDate[<=][date]");
    if ((str6 != null) && (str7 != null) && (!"".equals(str6)) && (!"".equals(str7)))
      str2 = str2 + " and cleardate >= to_date('" + str6 + "','yyyy-MM-dd') and cleardate <= to_date('" + str7 + "','yyyy-MM-dd')";
    str3 = str3 + str2;
    String str8 = "";
    String str9 = paramHttpServletRequest.getParameter("gnnt_primary.sumType[=][Long]");
    if ((!"".equals(str9)) && (str9 != null))
    {
      if ("breed".equals(str9))
        str8 = "select s.brokerid brokerid,s.firmname,s.firmid,s.breedid breedid,s.name name,nvl(sum(s.sumTradeFee),0) sumTradeFee,nvl(sum(s.sumBrokereachdivide),0) sumBrokereachdivide,nvl(sum(s.sumReward),0) sumReward,nvl(sum(s.sumSelfGain),0) sumSelfGain,nvl(sum(s.sumMarhetGain),0) sumMarhetGain,nvl(sum(s.sumFinalMarhetGain),0) sumFinalMarhetGain from(" + str3 + ") s group by s.breedid,s.brokerid,s.name,s.firmname,s.firmid";
      else if (("sumByBreed".equals(str9)) || ("sumByDay".equals(str9)))
        str8 = "select s.brokerid brokerid,s.name name,s.firmid,s.firmname,nvl(sum(s.sumTradeFee),0) sumTradeFee,nvl(sum(s.sumBrokereachdivide),0) sumBrokereachdivide,nvl(sum(s.sumReward),0) sumReward,nvl(sum(s.sumSelfGain),0) sumSelfGain,nvl(sum(s.sumMarhetGain),0) sumMarhetGain,nvl(sum(s.sumFinalMarhetGain),0) sumFinalMarhetGain from(" + str3 + ") s group by s.brokerid,s.name,s.firmid,s.firmname";
      else if ("day".equals(str9))
        str8 = "select s.brokerid brokerid,s.name name,s.firmname,s.firmid,s.cleardate cleardate,nvl(sum(s.sumTradeFee),0) sumTradeFee,nvl(sum(s.sumBrokereachdivide),0) sumBrokereachdivide,nvl(sum(s.sumReward),0) sumReward,nvl(sum(s.sumSelfGain),0) sumSelfGain,nvl(sum(s.sumMarhetGain),0) sumMarhetGain,nvl(sum(s.sumFinalMarhetGain),0) sumFinalMarhetGain from(" + str3 + ") s group by s.cleardate,s.brokerid,s.name,s.firmname,s.firmid";
      return queryBySql(str8);
    }
    return queryBySql(str3);
  }
}