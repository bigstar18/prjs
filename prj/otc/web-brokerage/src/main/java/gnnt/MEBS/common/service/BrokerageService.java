package gnnt.MEBS.common.service;

import gnnt.MEBS.common.dao.BrokerageDao;
import gnnt.MEBS.common.model.Brokerage;
import gnnt.MEBS.common.util.MD5;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("brokerageService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class BrokerageService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(BrokerageService.class);
  @Autowired
  @Qualifier("brokerageDao")
  private BrokerageDao brokerageDao;
  
  public BaseDao getDao()
  {
    return this.brokerageDao;
  }
  
  public String authenticateUser(Brokerage user, String securityCode, String securityCodeFilledIn)
  {
    String result = null;
    boolean passOrNot = true;
    if ((securityCode != null) && (!securityCode.toUpperCase().equals(securityCodeFilledIn.toUpperCase())))
    {
      result = "验证码错误,请重新登录!";
      passOrNot = false;
    }
    if (passOrNot)
    {
      Brokerage queryedUser = (Brokerage)this.brokerageDao.getById(user.getBrokerageNo());
      if (queryedUser == null)
      {
        result = "用户不存在,请重新登录!";
        passOrNot = false;
      }
      else if (!queryedUser.getPassword().equals(MD5.getMD5(user.getBrokerageNo(), user.getPassword())))
      {
        result = "密码错误,请重新登录!";
        passOrNot = false;
      }
      else
      {
        result = "default";
      }
    }
    return result;
  }
}
