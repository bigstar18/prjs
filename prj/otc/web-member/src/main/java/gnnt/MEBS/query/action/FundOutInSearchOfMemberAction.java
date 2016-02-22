package gnnt.MEBS.query.action;

import gnnt.MEBS.account.model.CustomerVO;
import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.service.InService;
import gnnt.MEBS.query.service.FundOutInSearchOfMemberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("fundOutInSearchOfMemberAction")
@Scope("request")
public class FundOutInSearchOfMemberAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(FundOutInSearchOfMemberAction.class);
  @Autowired
  @Qualifier("fundOutInSearchOfMemberService")
  private FundOutInSearchOfMemberService fundOutInSearchOfMemberService;
  
  public InService getService()
  {
    return this.fundOutInSearchOfMemberService;
  }
  
  public void init()
  {
    this.logger.debug("enter init");
    this.logger.debug("this.name:" + getClass().getName());
    if (getService() != null)
    {
      this.classType = CustomerVO.class;
      this.logger.debug("class.name:" + this.classType.getName());
      try
      {
        this.obj = ((Clone)this.classType.newInstance());
      }
      catch (InstantiationException e)
      {
        e.printStackTrace();
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();
      }
      this.logger.debug("end if");
    }
  }
}
