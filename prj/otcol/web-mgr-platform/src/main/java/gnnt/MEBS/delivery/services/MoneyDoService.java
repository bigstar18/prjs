package gnnt.MEBS.delivery.services;

import gnnt.MEBS.delivery.dao.MoneyDoDao;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_moneyDoService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MoneyDoService
{
  private final transient Log logger = LogFactory.getLog(MoneyDoService.class);
  @Autowired
  @Qualifier("w_moneyDoDao")
  private MoneyDoDao moneyDoDao;
  @Autowired
  @Qualifier("w_toolService")
  private ToolService toolService;
  
  public boolean checkFirmFunds(String paramString, double paramDouble)
  {
    double d = this.moneyDoDao.getRealFunds(paramString, 1);
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
  
  public int updateSettleMargin(String paramString1, String paramString2, double paramDouble)
  {
    return this.moneyDoDao.updateSettleMargin(paramString1, paramString2, paramDouble);
  }
  
  public long reverseFundflow(String paramString1, long paramLong, String paramString2, String paramString3)
  {
    long l = -1L;
    List localList = this.moneyDoDao.getFundflowList(paramString1, paramLong);
    if (localList.size() > 0)
    {
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap2 = (Map)localList.get(i);
        String str1 = (String)localMap2.get("FIRMID");
        String str2 = (String)localMap2.get("OPRCODE");
        double d = ((BigDecimal)localMap2.get("AMOUNT")).doubleValue();
        this.moneyDoDao.updateFundsFull(str1, str2, -d, paramString1, paramString2);
        boolean bool = this.toolService.existOrNotMargin(str2);
        this.logger.debug("sign:" + bool);
        if (bool) {
          this.moneyDoDao.updateSettleMargin(paramString3, str1, -d);
        }
        l = ((BigDecimal)localMap2.get("fundflowid")).longValue();
        this.logger.debug("lastFundflowId:" + l);
      }
      localList = this.moneyDoDao.getFundflowList(paramString1, paramLong);
      Map localMap1 = (Map)localList.get(localList.size() - 1);
      l = ((BigDecimal)localMap1.get("fundflowid")).longValue();
      this.logger.debug("lastFundflowId:" + l);
    }
    return l;
  }
}
