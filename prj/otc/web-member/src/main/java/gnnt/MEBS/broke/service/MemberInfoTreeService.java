package gnnt.MEBS.broke.service;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.broke.dao.MemberInfoOnlyDao;
import gnnt.MEBS.broke.dao.MemberInfoTreeDao;
import gnnt.MEBS.broke.dao.OrganizationTreeDao;
import gnnt.MEBS.broke.model.BrokerageTree;
import gnnt.MEBS.broke.model.MemberInfoOnly;
import gnnt.MEBS.broke.model.MemberInfoTree;
import gnnt.MEBS.broke.model.OrganizationTree;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberInfoTreeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberInfoTreeService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(MemberInfoTreeService.class);
  @Autowired
  @Qualifier("memberInfoTreeDao")
  private MemberInfoTreeDao memberInfoTreeDao;
  @Autowired
  @Qualifier("memberInfoOnlyDao")
  private MemberInfoOnlyDao memberInfoOnlyDao;
  @Autowired
  @Qualifier("organizationTreeDao")
  private OrganizationTreeDao organizationTreeDao;
  
  public BaseDao getDao()
  {
    return this.memberInfoTreeDao;
  }
  
  public String treeForRight(String memberNo)
  {
    this.logger.debug("enter list");
    MemberInfoTree memberInfoTree = new MemberInfoTree();
    memberInfoTree.setId(memberNo);
    memberInfoTree = (MemberInfoTree)get(memberInfoTree);
    
    String treeString = "<li><input type=\"radio\" name=\"checks\" id=\"checkM\" value=\"" + memberInfoTree.getId() + "\"> " + "<label id=\"" + memberInfoTree.getId() + "_memberInfoTree\">" + 
      memberInfoTree.getName() + "</label>";
    if ((memberInfoTree.getOrganizationSet() != null) && (memberInfoTree.getOrganizationSet().size() > 0))
    {
      treeString = treeString + "<ul>";
      for (OrganizationTree orgainzation : memberInfoTree.getOrganizationSet()) {
        treeString = treeString + orgainzation.forRightTree();
      }
      treeString = treeString + "</ul>";
    }
    treeString = treeString + "</li>";
    return treeString;
  }
  
  public String treeForOrg(String organizationNo)
  {
    this.logger.debug("enter list");
    OrganizationTree organizationTree = new OrganizationTree();
    organizationTree = (OrganizationTree)this.organizationTreeDao.getById(organizationNo);
    String treeString = organizationTree.forTree();
    return treeString;
  }
  
  public String treeRightForOrg(String organizationNo)
  {
    this.logger.debug("enter list");
    OrganizationTree organizationTree = new OrganizationTree();
    organizationTree = (OrganizationTree)this.organizationTreeDao.getById(organizationNo);
    String treeString = organizationTree.forRightTree();
    return treeString;
  }
  
  public String treeRightForOrgChildren(String organizationNo)
  {
    this.logger.debug("enter list");
    String treeString = "";
    OrganizationTree organizationTree = new OrganizationTree();
    organizationTree = (OrganizationTree)this.organizationTreeDao.getById(organizationNo);
    for (OrganizationTree organization : organizationTree.getOrganizationSet()) {
      treeString = treeString + organization.forRightTree();
    }
    return treeString;
  }
  
  public String organizationTreePart(String organizationNo, String parentOrgNo)
  {
    this.logger.debug("enter list");
    String treeString = "";
    OrganizationTree organizationTree = new OrganizationTree();
    if (!organizationNo.equals(parentOrgNo))
    {
      organizationTree = (OrganizationTree)this.organizationTreeDao.getById(organizationNo);
      for (OrganizationTree organization : organizationTree.getOrganizationSet()) {
        treeString = treeString + organization.organizationTreePart(parentOrgNo);
      }
    }
    return treeString;
  }
  
  public String memOrganizationTreePart(String memberNo, String partNo)
  {
    this.logger.debug("enter list");
    MemberInfoTree memberInfoTree = new MemberInfoTree();
    memberInfoTree.setId(memberNo);
    memberInfoTree = (MemberInfoTree)get(memberInfoTree);
    
    String treeString = "<li><input type=\"radio\" name=\"checks\" id=\"checkM\" value=\"" + memberInfoTree.getId() + "\"> " + "<label id=\"" + memberInfoTree.getId() + "_memberInfoTree\">" + 
      memberInfoTree.getName() + "</label>";
    if ((memberInfoTree.getOrganizationSet() != null) && (memberInfoTree.getOrganizationSet().size() > 0))
    {
      treeString = treeString + "<ul>";
      for (OrganizationTree orgainzation : memberInfoTree.getOrganizationSet()) {
        if (!orgainzation.getId().equals(partNo)) {
          treeString = treeString + orgainzation.organizationTreePart(partNo);
        }
      }
      treeString = treeString + "</ul>";
    }
    treeString = treeString + "</li>";
    return treeString;
  }
  
  public List<MemberInfoOnly> getMemberInfoTreeList(QueryConditions conditions, PageInfo pageInfo)
  {
    return this.memberInfoOnlyDao.getList(conditions, pageInfo);
  }
  
  public String treeString()
  {
    PageInfo pageInfo = new PageInfo(1, 1000000, "id", false);
    List<MemberInfoTree> list = getList(null, pageInfo);
    String treeString = "";
    for (MemberInfoTree m : list) {
      treeString = treeString + m.forTree();
    }
    return treeString;
  }
  
  public String treeForMemberInfo(String organizationNo, String memberInfoId)
  {
    String treeString = "";
    if ((organizationNo != null) && (!"".equals(organizationNo)))
    {
      this.logger.debug("organizationNo:" + organizationNo);
      treeString = treeForOrg(organizationNo);
    }
    else
    {
      MemberInfoTree memberInfoTree = (MemberInfoTree)getById(memberInfoId);
      treeString = memberInfoTree.forTree();
    }
    return treeString;
  }
  
  public String treeForMemberInfo1(String memberNo)
  {
    String treeString = "";
    MemberInfoTree memberInfoTree = (MemberInfoTree)getById(memberNo);
    Set<OrganizationTree> organizationSet = memberInfoTree.getOrganizationSet();
    Set<BrokerageTree> brokerageSet = memberInfoTree.getBrokerageSet();
    if ((organizationSet != null) && (organizationSet.size() > 0))
    {
      treeString = treeString + "<ul>";
      for (OrganizationTree organization : organizationSet) {
        treeString = treeString + organization.forTree();
      }
      treeString = treeString + "</ul>";
    }
    if ((brokerageSet != null) && (brokerageSet.size() > 0))
    {
      treeString = treeString + "<ul>";
      for (BrokerageTree brokerage : brokerageSet) {
        treeString = treeString + brokerage.forTree();
      }
      treeString = treeString + "</ul>";
    }
    return treeString;
  }
  
  public String zTreeForMemberInfo(String organizationNo, String memberInfoId)
  {
    String treeString = "";
    if ((organizationNo != null) && (!"".equals(organizationNo)))
    {
      this.logger.debug("organizationNo:" + organizationNo);
      OrganizationTree organizationTree = new OrganizationTree();
      organizationTree = (OrganizationTree)this.organizationTreeDao.getById(organizationNo);
      treeString = organizationTree.forZTree(memberInfoId);
    }
    else
    {
      MemberInfoTree memberInfoTree = (MemberInfoTree)getById(memberInfoId);
      treeString = memberInfoTree.forZTree();
    }
    return treeString;
  }
  
  public String zTreeForAllMemberInfo()
  {
    String treeString = "";
    PageInfo pageInfo = new PageInfo(1, 100000000, "id", false);
    List<MemberInfoTree> memberInfoTreeList = this.memberInfoTreeDao.getList(null, pageInfo);
    if ((memberInfoTreeList != null) && (memberInfoTreeList.size() > 0))
    {
      for (MemberInfoTree memberInfoTree : memberInfoTreeList) {
        treeString = treeString + memberInfoTree.forZTree() + ",";
      }
      treeString = treeString.substring(0, treeString.length() - 1);
    }
    return treeString;
  }
}
