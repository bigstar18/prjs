package gnnt.MEBS.timebargain.broker.service.tariff;

import gnnt.MEBS.common.broker.common.Page;
import gnnt.MEBS.common.broker.common.PageRequest;
import gnnt.MEBS.timebargain.broker.dao.tariff.TariffDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("com_tariffService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false, rollbackFor={Exception.class})
public class TariffService
{
  private final transient Log logger = LogFactory.getLog(TariffService.class);

  @Autowired
  @Qualifier("com_tariffDao")
  private TariffDao dao;

  public Page getPage(PageRequest<?> paramPageRequest, String paramString)
  {
    this.logger.debug("enter getPage");
    return this.dao.getPage(paramPageRequest, paramString);
  }

  public Page getPageSurroundSql(PageRequest<?> paramPageRequest, String paramString1, String paramString2, String paramString3)
  {
    this.logger.debug("enter getPageSurroundSql");
    return this.dao.getPageSurroundSql(paramPageRequest, paramString1, paramString2, paramString3);
  }
}