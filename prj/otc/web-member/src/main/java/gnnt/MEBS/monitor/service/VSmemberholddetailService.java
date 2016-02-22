package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.action.Rights;
import gnnt.MEBS.monitor.dao.VSmemberholddetailDao;
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

@Service("vSmemberholddetailService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VSmemberholddetailService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(VMemberholddetailService.class);
  @Autowired
  @Qualifier("vSmemberholddetailDao")
  private VSmemberholddetailDao vSmemberholddetailDao;
  
  public BaseDao getDao()
  {
    return this.vSmemberholddetailDao;
  }
  
  public List getListAll(String mid, String prd, String bsFlag)
  {
    String sqlwhere = "";
    Rights rts = new Rights();
    sqlwhere = sqlwhere + Rights.addsearchsql("smemberno", mid);
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("commodityname", prd) + ")";
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("bs_flag", bsFlag) + ")";
    
    sqlwhere = sqlwhere + " and (" + Rights.addsql() + ")";
    
    String sql = "select commodityname,to_char(sum(userbuyqty),'fm999,999,999,999,990') as userbuyqty,to_char(sum(usersellqty),'fm999,999,999,999,990') as usersellqty,to_char(round(sum(userbuyqty*buyprice)/decode(sum(userbuyqty),0,1,sum(userbuyqty)),2),'fm999,999,999,999,990.00') as buyprice,to_char(round(sum(usersellqty*sellprice)/decode(sum(usersellqty),0,1,sum(usersellqty)),2),'fm999,999,999,999,990.00') as sellprice,to_char(round(sum(buypl),2),'fm999,999,999,999,990.00') as buypl,to_char(round(sum(sellpl),2),'fm999,999,999,999,990.00') as sellpl,to_char(abs(sum(userbuyqty)-sum(usersellqty)),'fm999,999,999,999,990') as netqty,to_char(round(sum(netpl),2),'fm999,999,999,999,990.00') as netpl from v_smemberholdsum where " + 
    







      sqlwhere + " group by commodityname ";
    
    List list = null;
    
    list = this.vSmemberholddetailDao.queryForList(sql);
    
    return list;
  }
}
