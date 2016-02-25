package gnnt.MEBS.delivery.command.standardBehavior;

import gnnt.MEBS.delivery.command.Information;
import gnnt.MEBS.delivery.command.Receive;
import gnnt.MEBS.delivery.command.model.SettleAddObject;
import gnnt.MEBS.delivery.model.inner.SettleMatchRelated;
import gnnt.MEBS.delivery.model.settle.SettleMatch;
import gnnt.MEBS.delivery.model.workflow.RegStock;
import gnnt.MEBS.delivery.services.RegStockService;
import gnnt.MEBS.delivery.services.SettleMatchService;
import gnnt.MEBS.delivery.util.SysData;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SettleAdd
  implements Receive
{
  private final transient Log logger = LogFactory.getLog(SettleAdd.class);
  @Autowired
  @Qualifier("w_regStockService")
  private RegStockService regStockService;
  @Autowired
  @Qualifier("w_settleMatchService")
  private SettleMatchService settleMatchService;
  private List settleAddUseModuleList;
  
  public void setSettleAddUseModuleList(List paramList)
  {
    this.settleAddUseModuleList = paramList;
  }
  
  public int deal(Information paramInformation)
  {
    int i = -1;
    SettleAddObject localSettleAddObject = (SettleAddObject)paramInformation.getObject();
    SettleMatch localSettleMatch = localSettleAddObject.getSettleMatch();
    String str1 = localSettleAddObject.getOperator();
    String str2 = localSettleAddObject.getSettleMatchOldId();
    i = judgeSettleMatchOld(str2, localSettleMatch);
    if (i == 0)
    {
      if (localSettleMatch.getResult() == 1)
      {
        String str3 = localSettleMatch.getRegStockId();
        if (str3 == null)
        {
          this.logger.debug("no regStockId");
          return -12;
        }
        i = judgeRegStock(str3, localSettleMatch.getWeight());
      }
      if (i == 0)
      {
        long l = this.settleMatchService.createSettleMatch(localSettleMatch, str1);
        if (l < 0L)
        {
          this.logger.debug("insert error");
          i = -12;
        }
        else
        {
          SettleMatchRelated localSettleMatchRelated = new SettleMatchRelated();
          String str4 = SysData.getConfig("matchPrefix");
          localSettleMatchRelated.setChildMatchId(str4 + l);
          localSettleMatchRelated.setParentMatchId(str2);
          this.settleMatchService.addRelated(localSettleMatchRelated);
          i = 1;
        }
      }
    }
    return i;
  }
  
  private int judgeSettleMatchOld(String paramString, SettleMatch paramSettleMatch)
  {
    int i = 0;
    if (paramString == null)
    {
      this.logger.debug("no settleMatchOldId");
      i = -12;
    }
    else
    {
      SettleMatch localSettleMatch = this.settleMatchService.getSettleMatchLock(paramString);
      if (localSettleMatch == null)
      {
        this.logger.debug("no settleMatchOld");
        i = -12;
      }
      else
      {
        String str = localSettleMatch.getModuleId();
        if (!this.settleAddUseModuleList.contains(str))
        {
          this.logger.debug("moduleid error");
          i = -12;
        }
        if (i == 0)
        {
          double d1 = localSettleMatch.getWeight();
          double d2 = this.settleMatchService.getRelatedChildAmount(paramString);
          double d3 = paramSettleMatch.getWeight();
          if (new BigDecimal(d1).compareTo(new BigDecimal(d2).add(new BigDecimal(d3))) < 0)
          {
            this.logger.debug("Related error");
            i = -12;
          }
        }
      }
    }
    return i;
  }
  
  private int judgeRegStock(String paramString, double paramDouble)
  {
    int i = 0;
    RegStock localRegStock = this.regStockService.getRegStockForUpdate(paramString);
    double d1 = localRegStock.getWeight();
    double d2 = localRegStock.getFrozenWeight();
    if (new BigDecimal(d1).compareTo(new BigDecimal(d2).add(new BigDecimal(paramDouble))) < 0) {
      i = -12;
    }
    return i;
  }
}
