package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.action.Rights;
import gnnt.MEBS.monitor.dao.VMemberclosesumDao;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vMemberclosesumService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VMemberclosesumService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(VMemberclosesumService.class);
  @Autowired
  @Qualifier("vMemberclosesumDao")
  private VMemberclosesumDao vMemberclosesumDao;
  
  public BaseDao getDao()
  {
    return this.vMemberclosesumDao;
  }
  
  public List getListAll(String pid, String mid, String prd, String bsFlag)
  {
    String sqlwhere = "";
    Rights rts = new Rights();
    sqlwhere = sqlwhere + Rights.addsearchsql("memberno", mid);
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("commodityname", prd) + ")";
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("close_bs_flag", bsFlag) + ")";
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("smemberno", pid) + ")";
    
    sqlwhere = sqlwhere + " and (" + Rights.addsql() + ")";
    String sql = "select  commodityname,to_char(sum(buyqty),'fm999,999,999,999,990') as buyqty,to_char(sum(sellqty),'fm999,999,999,999,990') as sellqty,to_char(round(sum(buyopenprice)/decode(sum(buyqty),0,1,sum(buyqty)),2),'fm999,999,999,999,990.00') as buyopenprice,to_char(round(sum(buyholdprice)/decode(sum(buyqty),0,1,sum(buyqty)),2),'fm999,999,999,999,990.00') as buyholdprice,to_char(round(sum(buycloseprice)/decode(sum(buyqty),0,1,sum(buyqty)),2),'fm999,999,999,999,990.00') as buycloseprice,to_char(round(sum(buyclosepl),2),'fm999,999,999,999,990.00') as buyclosepl,to_char(round(sum(sellopenprice)/decode(sum(sellqty),0,1,sum(sellqty)),2),'fm999,999,999,999,990.00') as sellopenprice,to_char(round(sum(sellholdprice)/decode(sum(sellqty),0,1,sum(sellqty)),2),'fm999,999,999,999,990.00') as sellholdprice,to_char(round(sum(sellcloseprice)/decode(sum(sellqty),0,1,sum(sellqty)),2),'fm999,999,999,999,990.00') as sellcloseprice,to_char(round(sum(sellclosepl),2),'fm999,999,999,999,990.00') as sellclosepl,to_char(round(sum(tradefee),2),'fm999,999,999,999,990.00') as tradefee,to_char(round(sum(netqty),2),'fm999,999,999,999,990') as netqty,to_char(round(sum(allpl),2),'fm999,999,999,999,990.00') as allpl,to_char(round(sum(netpl),2),'fm999,999,999,999,990.00') as netpl from v_memberclosesum where " + 
    












      sqlwhere + " group by commodityname";
    List list = null;
    list = this.vMemberclosesumDao.queryForList(sql);
    return list;
  }
}
