package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.action.Rights;
import gnnt.MEBS.monitor.dao.VCustomerhodsumDao;
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

@Service("vCustomerhodsumService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VCustomerhodsumService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(VCustomerhodsumService.class);
  @Autowired
  @Qualifier("vCustomerhodsumDao")
  private VCustomerhodsumDao vCustomerhodsumDao;
  
  public BaseDao getDao()
  {
    return this.vCustomerhodsumDao;
  }
  
  public List getListAll(String mid, String cstid, String prd, String bsFlag)
  {
    String sqlwhere = "";
    Rights rts = new Rights();
    sqlwhere = sqlwhere + Rights.addsearchsql("organizationno", mid);
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("commodityname", prd) + ")";
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("bs_flag", bsFlag) + ")";
    if ((cstid != null) && (cstid.trim().length() > 0) && (!cstid.equals("all"))) {
      sqlwhere = sqlwhere + " and (customerno like '%" + cstid + "%') ";
    }
    sqlwhere = sqlwhere + " and (" + Rights.addsql() + ")";
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsqlorg() + ")";
    
    String sql = "select commodityname,to_char(sum(buyqty),'fm999,999,999,999,990') as buyqty,to_char(sum(sellqty),'fm999,999,999,999,990') as sellqty,to_char(round(sum(holdfundsbuy)/decode(sum(buyqty*contractfactor),0,1,sum(buyqty*contractfactor)),2),'fm999,999,999,999,990.00') as envenpricebuy,to_char(round(sum(holdfundssell)/decode(sum(sellqty*contractfactor),0,1,sum(sellqty*contractfactor)),2),'fm999,999,999,999,990.00') as envenpricesell,to_char(round(sum(openfundbuy)/decode(sum(buyqty*contractfactor),0,1,sum(buyqty*contractfactor)),2),'fm999,999,999,999,990.00') as openpricebuy,to_char(round(sum(openfundsell)/decode(sum(sellqty*contractfactor),0,1,sum(sellqty*contractfactor)),2),'fm999,999,999,999,990.00') as openpricesell,to_char(round(sum(buyloss),2),'fm999,999,999,999,990.00') as buyloss,to_char(round(sum(sellloss),2),'fm999,999,999,999,990.00') as sellloss,to_char(round((sum(buyqty)-sum(sellqty)),2),'fm999,999,999,999,990') as netqty,to_char(round(max(buyprice),2),'fm999,999,999,999,990.00') as buyprice,to_char(round(max(sellprice),2),'fm999,999,999,999,990.00') as sellprice,to_char(round((sum(buyloss)+sum(sellloss)),2),'fm999,999,999,999,990.00') as netloss from v_customerholdsum where " + 
    










      sqlwhere + " group by commodityname ";
    
    List list = null;
    
    list = this.vCustomerhodsumDao.queryForList(sql);
    
    return list;
  }
}
