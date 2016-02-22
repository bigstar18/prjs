package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.action.Rights;
import gnnt.MEBS.monitor.dao.MemberHoldSumViewDao;
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

@Service("memberHoldSumViewService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberHoldSumViewService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(MemberHoldSumViewService.class);
  @Autowired
  @Qualifier("memberHoldSumViewDao")
  private MemberHoldSumViewDao memberHoldSumViewDao;
  
  public BaseDao getDao()
  {
    return this.memberHoldSumViewDao;
  }
  
  public List getListAll(String pid, String mid, String prd, String bsFlag)
  {
    String sqlwhere = "";
    Rights rts = new Rights();
    sqlwhere = sqlwhere + Rights.addsearchsql("memberno", mid);
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("smemberno", pid) + ")";
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("commodityname", prd) + ")";
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("bs_flag", bsFlag) + ")";
    
    sqlwhere = sqlwhere + " and (" + Rights.addsql() + ")";
    

    String sql = "select commodityname,to_char(sum(buyqty),'fm999,999,999,999,990') as buyqty,to_char(sum(sellqty),'fm999,999,999,999,990') as sellqty,to_char(round(sum(holdfundsbuy)/decode(sum(buyqty*contractfactor),0,1,sum(buyqty*contractfactor)),2),'fm999,999,999,999,990.00') as envenpricebuy,to_char(round(sum(holdfundssell)/decode(sum(sellqty*contractfactor),0,1,sum(sellqty*contractfactor)),2),'fm999,999,999,999,990.00') as envenpricesell,to_char(round(sum(openfundbuy)/decode(sum(buyqty*contractfactor),0,1,sum(buyqty*contractfactor)),2),'fm999,999,999,999,990.00') as openpricebuy,to_char(round(sum(openfundsell)/decode(sum(sellqty*contractfactor),0,1,sum(sellqty*contractfactor)),2),'fm999,999,999,999,990.00') as openpricesell,to_char(round(sum(buyloss),2),'fm999,999,999,999,990.00') as buyloss,to_char(round(sum(sellloss),2),'fm999,999,999,999,990.00') as sellloss,to_char(round((sum(buyqty)-sum(sellqty)),2),'fm999,999,999,999,990') as netqty,to_char(round((sum(buyloss)+sum(sellloss)),2),'fm999,999,999,999,990.00') as netloss from v_memberholdsum where " + 
    









      sqlwhere + " group by commodityname ";
    List list = null;
    list = this.memberHoldSumViewDao.queryForList(sql);
    return list;
  }
}
