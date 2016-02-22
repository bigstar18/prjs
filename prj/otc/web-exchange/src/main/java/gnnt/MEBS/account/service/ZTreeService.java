package gnnt.MEBS.account.service;

import gnnt.MEBS.account.model.Customer;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.broke.dao.BrokerageZTreeDao;
import gnnt.MEBS.broke.dao.MemberInfoOnlyDao;
import gnnt.MEBS.broke.dao.OrganizationZTreeDao;
import gnnt.MEBS.broke.model.BrokerageAndOrganization;
import gnnt.MEBS.broke.model.BrokerageForZTree;
import gnnt.MEBS.broke.model.MemberInfoOnly;
import gnnt.MEBS.broke.model.OrganizationForZTree;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("zTreeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class ZTreeService
  extends BaseService<Customer>
{
  private final transient Log logger = LogFactory.getLog(ZTreeService.class);
  @Autowired
  @Qualifier("memberInfoOnlyDao")
  private MemberInfoOnlyDao memberInfoOnlyDao;
  @Autowired
  @Qualifier("brokerageZTreeDao")
  private BrokerageZTreeDao brokerageZTreeDao;
  @Autowired
  @Qualifier("organizationZTreeDao")
  private OrganizationZTreeDao organizationZTreeDao;
  
  public BaseDao getDao()
  {
    return this.brokerageZTreeDao;
  }
  
  public String getZTree()
  {
    String treeString = "";
    PageInfo pageInfo = new PageInfo(1, 100000000, "primary.id", false);
    List<MemberInfoOnly> memOnlyList = this.memberInfoOnlyDao.getList(null, 
      pageInfo);
    PageInfo pageInfo1 = new PageInfo(1, 100000000, "primary.name", false);
    List<OrganizationForZTree> orgZtreeList = this.organizationZTreeDao.getList(null, 
      pageInfo1);
    PageInfo pageInfo2 = new PageInfo(1, 100000000, "primary.name", false);
    List<BrokerageForZTree> broZtreeList = this.brokerageZTreeDao.getList(null, 
      pageInfo2);
    if ((memOnlyList != null) && (memOnlyList.size() > 0))
    {
      StringBuffer stringB = new StringBuffer();
      Iterator iterator = memOnlyList.iterator();
      while (iterator.hasNext())
      {
        MemberInfoOnly memOnly = (MemberInfoOnly)iterator.next();
        stringB.append("{'id':'" + memOnly.getMemberNo() + 
          "_M','pId':'0','name':'(" + memOnly.getMemberNo() + ")" + 
          memOnly.getName() + "【M】','title':'M'}" + ",");
      }
      treeString = 
        treeString + stringB.toString().substring(0, stringB.toString().length() - 1);
    }
    if ((orgZtreeList != null) && (orgZtreeList.size() > 0))
    {
      treeString = treeString + ",";
      
      StringBuffer stringB = new StringBuffer();
      Iterator iterator = orgZtreeList.iterator();
      while (iterator.hasNext())
      {
        OrganizationForZTree orgZtree = (OrganizationForZTree)iterator.next();
        String pId = orgZtree.getParentOrganizationNO() + "_O";
        if ((orgZtree.getParentOrganizationNO() == null) || 
          ("".equals(orgZtree.getParentOrganizationNO()))) {
          pId = orgZtree.getMemberNo() + "_M";
        }
        stringB.append("{'id':'" + orgZtree.getOrganizationNO() + 
          "_O','pId':'" + pId + "','name':'" + orgZtree.getName() + 
          "【O】','title':'O'}" + ",");
      }
      treeString = 
        treeString + stringB.toString().substring(0, stringB.toString().length() - 1);
    }
    if ((broZtreeList != null) && (broZtreeList.size() > 0))
    {
      treeString = treeString + ",";
      
      StringBuffer stringB = new StringBuffer();
      Iterator iterator = broZtreeList.iterator();
      while (iterator.hasNext())
      {
        BrokerageForZTree broZtree = (BrokerageForZTree)iterator.next();
        String pId = "";
        if (broZtree.getBroAndOrg() == null) {
          pId = broZtree.getMemberNo() + "_M";
        } else {
          pId = broZtree.getBroAndOrg().getOrganizationNO() + "_O";
        }
        stringB.append("{'id':'" + broZtree.getBrokerageNo() + 
          "_B','pId':'" + pId + "','name':'" + broZtree.getName() + 
          "【B】','title':'B'}" + ",");
      }
      treeString = 
        treeString + stringB.toString().substring(0, stringB.toString().length() - 1);
    }
    return treeString;
  }
}
