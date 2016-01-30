package gnnt.MEBS.common.front.service;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.QueryConditions;
import gnnt.MEBS.common.front.model.integrated.MFirm;
import gnnt.MEBS.common.front.model.integrated.User;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_userService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class UserService
  extends StandardService
{
  public User getTraderByID(String paramString)
  {
    return getTraderByID(paramString, true, true);
  }
  
  public User getTraderByID(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.logger.debug("enter getUserByID");
    User localUser1 = new User();
    localUser1.setTraderID(paramString);
    User localUser2 = (User)super.get(localUser1);
    localUser2.getBelongtoFirm().getFirmModuleSet();
    if (localUser2 != null)
    {
      if (paramBoolean1) {
        localUser2.getRightSet().size();
      }
      if (paramBoolean2) {
        localUser2.getRoleSet().size();
      }
    }
    return localUser2;
  }
  
  public User getTraderByUserID(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.logger.debug("enter getTraderByUserID");
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("userID", "=", paramString);
    PageRequest localPageRequest = new PageRequest(1, 2, localQueryConditions, "");
    Page localPage = getPage(localPageRequest, new User());
    User localUser = null;
    if ((localPage != null) && (localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
      localUser = (User)localPage.getResult().get(0);
    }
    if (localUser != null)
    {
      localUser.getBelongtoFirm().getFirmModuleSet();
      if (paramBoolean1) {
        localUser.getRightSet().size();
      }
      if (paramBoolean2) {
        localUser.getRoleSet().size();
      }
    }
    return localUser;
  }
}
