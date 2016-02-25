package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.zcjs.dao.MoneyDoDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_moneyDoService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MoneyDoService
{
  private final transient Log logger = LogFactory.getLog(MoneyDoService.class);
  @Autowired
  @Qualifier("z_moneyDoDao")
  private MoneyDoDao moneyDoDao;
  
  public boolean checkFirmFunds(String paramString, double paramDouble)
  {
    double d = this.moneyDoDao.getRealFunds(paramString, 1);
    this.logger.debug(paramString + "交易商资金:" + d);
    this.logger.debug("检查资金：" + paramDouble);
    return d >= paramDouble;
  }
  
  public double updateFirmFunds(String paramString1, String paramString2, double paramDouble, String paramString3, String paramString4)
  {
    this.logger.debug("firmId:" + paramString1 + "      amount:" + paramDouble);
    double d = this.moneyDoDao.updateFundsFull(paramString1, paramString2, paramDouble, paramString3, paramString4);
    return d;
  }
  
  public double updateFirmFunds(String paramString1, String paramString2, double paramDouble)
  {
    double d = this.moneyDoDao.updateFundsFull(paramString1, paramString2, paramDouble, null, null);
    return d;
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public double getFirmFunds(String paramString)
  {
    return this.moneyDoDao.getRealFunds(paramString, 1);
  }
  
  public double updateFrozenFunds(String paramString, double paramDouble)
  {
    return this.moneyDoDao.updateFrozenFunds(paramString, paramDouble, "3");
  }
}
