package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.MemberInfoDao;
import gnnt.MEBS.account.dao.MemberRelationDao;
import gnnt.MEBS.account.model.MemberRelation;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
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

@Service("memberRelationService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MemberRelationService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(MemberRelationService.class);
  @Autowired
  @Qualifier("memberRelationDao")
  private MemberRelationDao memberRelationDao;
  @Autowired
  @Qualifier("memberInfoDao")
  private MemberInfoDao memberInfoDao;
  
  public BaseDao getDao()
  {
    return this.memberRelationDao;
  }
  
  public int contactSpecialMember(String memberId, String selectSpecialMem)
  {
    String[] ids = selectSpecialMem.split(",");
    int resultValue = -1;
    List<MemberRelation> memberRelationList = getList(new QueryConditions("primary.memberNo", "=", memberId), 
      new PageInfo(1, 1000000, "primary.sortNo", false));
    if (memberRelationList.size() > 0) {
      for (MemberRelation memberRelation1 : memberRelationList) {
        delete(memberRelation1);
      }
    }
    if ((ids != null) && (ids.length > 0)) {
      for (int i = 0; i < ids.length; i++) {
        if ((ids[i] != null) && (!"".equals(ids[i])))
        {
          MemberRelation relation = new MemberRelation();
          relation.setMemberNo(memberId);
          relation.setS_MemberNo(ids[i]);
          relation.setSortNo(Integer.valueOf(i));
          resultValue = add(relation);
          if (resultValue > 0) {
            resultValue = 3;
          }
        }
      }
    }
    return resultValue;
  }
}
