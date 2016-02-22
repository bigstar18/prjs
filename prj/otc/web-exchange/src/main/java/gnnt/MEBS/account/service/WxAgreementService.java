package gnnt.MEBS.account.service;

import gnnt.MEBS.account.dao.WxAgreementDao;
import gnnt.MEBS.account.model.WxAgreement;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("wxAgreementService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class WxAgreementService
  extends BaseService<WxAgreement>
{
  private final transient Log logger = LogFactory.getLog(WxAgreementService.class);
  @Autowired
  @Qualifier("wxAgreementDao")
  private WxAgreementDao wxAgreementDao;
  
  public BaseDao getDao()
  {
    return this.wxAgreementDao;
  }
}
