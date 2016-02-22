package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.VCustomerclossumTipeDao;
import gnnt.MEBS.monitor.model.VCustomerclosesumTip;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vCustomerclosesumTipService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class VCustomerclosesumTipService
{
  @Autowired
  @Qualifier("vCustomerclossumTipeDao")
  private VCustomerclossumTipeDao vCustomerclosesumTipDao;
  
  public BaseDao getDao()
  {
    return this.vCustomerclosesumTipDao;
  }
  
  public VCustomerclosesumTip getVCustomerclosesumTip(String commodityid, String organizationno, String customerno, String bsFlag, String tradetype)
  {
    VCustomerclosesumTip result = null;
    HttpServletRequest request = ServletActionContext.getRequest();
    String sqlwhere = "";
    if ((commodityid != null) && (commodityid.trim().length() > 0)) {
      sqlwhere = sqlwhere + " and commodityid='" + commodityid.trim() + "' ";
    }
    String memid = request.getSession().getAttribute("REGISTERID").toString();
    sqlwhere = sqlwhere + " and memberno='" + memid + "' ";
    

    String organ = request.getSession().getAttribute("ORGANIZATIONID").toString();
    if ((organ != null) && (organ != ""))
    {
      sqlwhere = sqlwhere + " and (customerno in (select customerNo from v_customerrelateorganization where organizationNo='" + organ + "' )) ";
    }
    else if ((organizationno != null) && (organizationno.trim().length() > 0))
    {
      String org = "";
      String[] orgs = organizationno.split(",");
      for (String str : orgs) {
        if (str.trim().length() > 0)
        {
          if (org.trim().length() > 0) {
            org = org + ",";
          }
          org = org + "'" + str.trim() + "'";
        }
      }
      sqlwhere = sqlwhere + " and (customerno in (select customerNo from v_customerrelateorganization where organizationNo in (" + org + ") )) ";
    }
    if ((customerno != null) && (customerno.trim().length() > 0)) {
      sqlwhere = sqlwhere + " and customerno like '%" + customerno.trim() + "%' ";
    }
    if ((bsFlag != null) && (bsFlag.trim().length() > 0)) {
      sqlwhere = sqlwhere + " and close_bs_flag='" + bsFlag.trim() + "' ";
    }
    if ((tradetype != null) && (tradetype.trim().length() > 0)) {
      sqlwhere = sqlwhere + " and tradetype='" + tradetype.trim() + "' ";
    }
    String sql = "select commodityid as commodityid,round(sum(buyqty),2) as buyqty,round(sum(sellqty),2) as sellqty,round(sum(buyopenmoney)/decode(sum(buyqty),0,1,sum(buyqty)),2) as buyopenprice,round(sum(buyholdmoney)/decode(sum(buyqty),0,1,sum(buyqty)),2) as buyholdprice,round(sum(sellopenmoney)/decode(sum(sellqty),0,1,sum(sellqty)),2) as sellopenprice,round(sum(sellholdmoney)/decode(sum(sellqty),0,1,sum(sellqty)),2) as sellholdprice from (select t_trade.commodityid as commodityid,t_trade.firmid as customerno,t_trade.o_firmid as memberno,decode(t_trade.tradetype,4,'自动强平',5,'手动强平',6,'指价成交',7,'指价成交','市价成交') as tradetype,decode(t_trade.bs_flag,1,'买',2,'卖') as close_bs_flag,decode(t_trade.bs_flag,1,t_trade.quantity,0) as buyqty,decode(t_trade.bs_flag,1,t_holdposition.openprice*t_trade.quantity,0) as buyopenmoney,decode(t_trade.bs_flag,1,t_holdposition.holdprice*t_trade.quantity,0) as buyholdmoney,decode(t_trade.bs_flag,2,t_trade.quantity,0) as sellqty,decode(t_trade.bs_flag,2,t_holdposition.openprice*t_trade.quantity,0) as sellopenmoney,decode(t_trade.bs_flag,2,t_holdposition.holdprice*t_trade.quantity,0) as sellholdmoney from t_trade,t_holdposition,m_firm where t_trade.holdno=t_holdposition.holdno(+) and t_trade.oc_flag='C' and t_trade.firmid=m_firm.firmid(+) and m_firm.firmtype='C') where 1=1 " + 
    





















      sqlwhere + " group by commodityid";
    
    List list = this.vCustomerclosesumTipDao.queryForList(sql);
    if ((list != null) && (list.size() > 0)) {
      result = (VCustomerclosesumTip)list.get(0);
    }
    return result;
  }
}
