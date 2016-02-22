package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.dao.VCustomerclosesumSimpleDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.io.PrintStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vCustomerclosesumSimpleService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VCustomerclosesumSimpleService
  extends BaseService
{
  @Autowired
  @Qualifier("vCustomerclosesumSimpleDao")
  private VCustomerclosesumSimpleDao vCustomerclosesumSimpleDao;
  
  public BaseDao getDao()
  {
    return this.vCustomerclosesumSimpleDao;
  }
  
  public List getListAll(String commodityid, String memberno, String customerno, String bsFlag, String tradetype)
  {
    String sqlwhere = "";
    if ((commodityid != null) && (commodityid.trim().length() > 0)) {
      sqlwhere = sqlwhere + " and commodityid='" + commodityid.trim() + "' ";
    }
    if ((memberno != null) && (memberno.trim().length() > 0))
    {
      String mems = "";
      String[] memb = memberno.split(",");
      for (String str : memb) {
        if ((str != null) && (str.trim().length() > 0))
        {
          if (mems.trim().length() > 0) {
            mems = mems + ",";
          }
          mems = mems + "'" + str.trim() + "'";
        }
      }
      sqlwhere = sqlwhere + " and memberno in (" + mems + ") ";
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
    String sql = "select commodityid as commodityid,round(sum(buyqty),2) as buyqty,round(sum(buymoney)/decode(sum(buyqty),0,1,sum(buyqty)),2) as buycloseprice,round(sum(buyclosepl),2) as buyclosepl,round(sum(sellqty),2) as sellqty,round(sum(sellmoney)/decode(sum(sellqty),0,1,sum(sellqty)),2) as sellcloseprice,round(sum(sellclosepl),2) as sellclosepl,sum(tradefee) as tradefee from (select t_trade.commodityid as commodityid,t_trade.o_firmid as memberno,t_trade.firmid as customerno,decode(t_trade.bs_flag,1,'买',2,'卖') as close_bs_flag,decode(t_trade.tradetype,4,'自动强平',5,'手动强平',6,'指价成交',7,'指价成交','市价成交') as tradetype,decode(t_trade.bs_flag,1,t_trade.quantity,0) as buyqty,decode(t_trade.bs_flag,1,t_trade.close_pl,0) as buyclosepl,decode(t_trade.bs_flag,1,t_trade.price*t_trade.quantity,0) as buymoney,decode(t_trade.bs_flag,2,t_trade.quantity,0) as sellqty,decode(t_trade.bs_flag,2,t_trade.close_pl,0) as sellclosepl,decode(t_trade.bs_flag,2,t_trade.price*t_trade.quantity,0) as sellmoney,-t_trade.tradefee as tradefee from t_trade,m_firm where t_trade.firmid=m_firm.firmid and m_firm.firmType='C' and t_trade.oc_flag = 'C') where 1=1 " + 
    


















      sqlwhere + " group by commodityid order by commodityid";
    
    System.out.println(sql);
    List list = null;
    list = this.vCustomerclosesumSimpleDao.queryForList(sql);
    
    return list;
  }
}
