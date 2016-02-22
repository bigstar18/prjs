package gnnt.MEBS.monitor.service;

import gnnt.MEBS.monitor.action.Rights;
import gnnt.MEBS.monitor.dao.VCustomerclosesumDao;
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

@Service("vCustomerclosesumService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class VCustomerclosesumService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(VCustomerclosesumService.class);
  @Autowired
  @Qualifier("vCustomerclosesumDao")
  private VCustomerclosesumDao vCustomerclosesumDao;
  
  public BaseDao getDao()
  {
    return this.vCustomerclosesumDao;
  }
  
  public List getListAll(String mid, String cstid, String prd, String bsFlag, String closetype)
  {
    String sqlwhere = "";
    Rights rts = new Rights();
    sqlwhere = sqlwhere + Rights.addsearchsql("organizationno", mid);
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("commodityname", prd) + ")";
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("close_bs_flag", bsFlag) + ")";
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsql("tradetype", closetype) + ")";
    if ((cstid != null) && (cstid.trim().length() > 0) && (!cstid.equals("all"))) {
      sqlwhere = sqlwhere + " and (customerno like '%" + cstid + "%') ";
    }
    sqlwhere = sqlwhere + " and (" + Rights.addsql() + ")";
    sqlwhere = sqlwhere + " and (" + Rights.addsearchsqlorg() + ")";
    
    String sql = "select  commodityname,sum(buyqty) as buyqty,sum(sellqty) as sellqty,round(sum(buyopenprice)/decode(sum(buyqty),0,1,sum(buyqty)),2) as buyopenprice,round(sum(buyholdprice)/decode(sum(buyqty),0,1,sum(buyqty)),2) as buyholdprice,round(sum(buycloseprice)/decode(sum(buyqty),0,1,sum(buyqty)),2) as buycloseprice,round(sum(buyclosepl),2) as buyclosepl,round(sum(sellopenprice)/decode(sum(sellqty),0,1,sum(sellqty)),2) as sellopenprice,round(sum(sellholdprice)/decode(sum(sellqty),0,1,sum(sellqty)),2) as sellholdprice,round(sum(sellcloseprice)/decode(sum(sellqty),0,1,sum(sellqty)),2) as sellcloseprice,round(sum(sellclosepl),2) as sellclosepl,round(sum(tradefee),2) as tradefee,round(sum(netqty),2) as netqty,round(sum(allpl),2) as allpl,round(sum(netpl),2) as netpl from v_customerclosesum where " + 
    











      sqlwhere + "  group by commodityname";
    

    List list = null;
    list = this.vCustomerclosesumDao.queryForList(sql);
    return list;
  }
}
