package gnnt.MEBS.timebargain.plugin.condition.task;

import gnnt.MEBS.timebargain.plugin.condition.CalculateCenter;
import gnnt.MEBS.timebargain.plugin.condition.db.ConditionDao;
import gnnt.MEBS.timebargain.plugin.condition.model.Quotation;
import gnnt.MEBS.timebargain.plugin.condition.model.SystemStatus;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QuotationScanner
  extends Thread
{
  private final transient Log log = LogFactory.getLog(QuotationScanner.class);
  private HashMap<String, Quotation> m_quotations = new HashMap();
  private Timestamp updateTime = new Timestamp(0L);
  private CalculateCenter center;
  public ConditionDao db;
  
  public QuotationScanner(CalculateCenter paramCalculateCenter)
  {
    this.center = paramCalculateCenter;
    this.db = CalculateCenter.getDAOInstance();
  }
  
  public void run()
  {
    Object localObject = new ArrayList();
    SystemStatus localSystemStatus1 = null;
    SystemStatus localSystemStatus2 = null;
    int i = 0;
    int j = 0;
    try
    {
      for (;;)
      {
        localSystemStatus1 = this.db.getSystemStatus();
        if (localSystemStatus1 == null)
        {
          this.log.info("无法取得系统状态");
          return;
        }
        switch (localSystemStatus1.getStatus().intValue())
        {
        case 0: 
          if (i == 0)
          {
            this.center.clearMemData();
            this.center.loadData();
            i = 1;
            j = 0;
          }
          localSystemStatus2 = localSystemStatus1;
          break;
        case 5: 
          if (!localSystemStatus1.equals(localSystemStatus2)) {
            this.updateTime = new Timestamp(0L);
          }
          localObject = this.db.getRuntimeQuotation(this.updateTime);
          Iterator localIterator = ((List)localObject).iterator();
          while (localIterator.hasNext())
          {
            Quotation localQuotation1 = (Quotation)localIterator.next();
            Quotation localQuotation2 = (Quotation)this.m_quotations.get(localQuotation1.getCommodityID());
            if (localQuotation2 != null)
            {
              if ((!localQuotation2.equals(localQuotation1)) && ((localQuotation1.getPrice().doubleValue() != 0.0D) || (localQuotation1.getBuy1() != 0.0D) || (localQuotation1.getSell1() != 0.0D)))
              {
                this.log.debug("oldQuotation Price:" + localQuotation2.getPrice() + " ,Buy1:" + localQuotation2.getBuy1() + " ,Sell1:" + localQuotation2.getSell1());
                this.log.debug("newQuotation Price:" + localQuotation1.getPrice() + " ,Buy1:" + localQuotation1.getBuy1() + " ,Sell1:" + localQuotation1.getSell1());
                this.center.calculate(localQuotation1);
              }
            }
            else if ((localQuotation1.getPrice().doubleValue() != 0.0D) || (localQuotation1.getBuy1() != 0.0D) || (localQuotation1.getSell1() != 0.0D)) {
              this.center.calculate(localQuotation1);
            }
            this.m_quotations.put(localQuotation1.getCommodityID(), localQuotation1);
            if (localQuotation1.getCreateTime().getTime() > this.updateTime.getTime()) {
              this.updateTime = localQuotation1.getCreateTime();
            }
          }
          localSystemStatus2 = localSystemStatus1;
          break;
        case 7: 
          if (j == 0)
          {
            this.log.info("备份过期条件委托数据...");
            this.db.backUpOrder();
            j = 1;
            i = 0;
            this.log.info("备份成功...");
          }
          localSystemStatus2 = localSystemStatus1;
          break;
        default: 
          Thread.sleep(1000L);
          localSystemStatus2 = localSystemStatus1;
        }
        try
        {
          Thread.sleep(300L);
        }
        catch (InterruptedException localInterruptedException1)
        {
          localInterruptedException1.printStackTrace();
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      try
      {
        Thread.sleep(300L);
      }
      catch (InterruptedException localInterruptedException2)
      {
        localInterruptedException2.printStackTrace();
      }
    }
  }
}
