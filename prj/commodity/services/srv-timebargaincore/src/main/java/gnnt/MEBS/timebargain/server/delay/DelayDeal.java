package gnnt.MEBS.timebargain.server.delay;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.DelayDAO;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.DelayQuotation;
import gnnt.MEBS.timebargain.server.model.DelayStatus;
import gnnt.MEBS.timebargain.server.model.DelayTradeTime;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.SysLog;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DelayDeal
{
  private Log log = LogFactory.getLog(getClass());
  private DelayTradeTimeTask delayTradeTimeTask;
  private DelayOrderProcess delayOrderProcess;
  private DelayStatus delayStatus;
  private DelayDAO delayDAO;
  private ServerDAO serverDAO;
  private Market market;
  private Map neutralSideMap;
  private boolean existDelayTradeTime = false;
  private Server server;
  private DelayMatcher delayMatcher;
  private static DelayDeal instance;
  private int delaySettleMatchType;
  private static final int DELAYSETTLEMATCHTYPE_REALTIME = 0;
  private static final int DELAYSETTLEMATCHTYPE_FINISH = 1;
  private int delayNeutralMatchType;
  private static final int DELAY_NEUTRAL_MATCHTYPE_REALTIME = 0;
  private static final int DELAY_NEUTRAL_MATCHTYPE_FINISH = 1;
  
  public static DelayDeal getInstance()
  {
    if (instance == null) {
      instance = new DelayDeal();
    }
    return instance;
  }
  
  public void init(Server paramServer)
  {
    this.server = paramServer;
    this.delayDAO = ((DelayDAO)DAOBeanFactory.getBean("delayDAO"));
    this.serverDAO = ((ServerDAO)DAOBeanFactory.getBean("serverDAO"));
    this.existDelayTradeTime = isHasDelayTradeTime();
    if (this.existDelayTradeTime) {
      start(paramServer);
    }
  }
  
  public void start(Server paramServer)
  {
    this.delayStatus = this.delayDAO.getDelayStatus();
    this.market = paramServer.getServerInit().getMarket();
    if (this.delayTradeTimeTask != null) {
      this.delayTradeTimeTask.close();
    }
    this.delayTradeTimeTask = new DelayTradeTimeTask();
    this.delayTradeTimeTask.start();
    this.delayOrderProcess = DelayOrderProcess.getInstance();
    this.delayOrderProcess.init(paramServer);
    if (this.delayMatcher != null) {
      this.delayMatcher.close();
    }
    this.delayMatcher = new DelayMatcher();
    this.delayMatcher.init(paramServer);
    this.delaySettleMatchType = Integer.parseInt(DAOBeanFactory.getConfig("DelaySettleMatchType"));
    this.delayNeutralMatchType = Integer.parseInt(DAOBeanFactory.getConfig("DelayNeutralMatchType"));
    if ((this.delaySettleMatchType == 0) || (this.delayNeutralMatchType == 0)) {
      this.delayMatcher.start();
    }
  }
  
  private boolean isHasDelayTradeTime()
  {
    boolean bool = false;
    List localList = this.delayDAO.getDelayTradeTimes();
    if ((localList == null) || (localList.size() == 0)) {
      bool = false;
    } else {
      for (int i = 0; i < localList.size(); i++)
      {
        DelayTradeTime localDelayTradeTime = (DelayTradeTime)localList.get(i);
        if (localDelayTradeTime.getStatus().shortValue() == 1)
        {
          bool = true;
          break;
        }
      }
    }
    return bool;
  }
  
  public boolean isExistDelayTradeTime()
  {
    return this.existDelayTradeTime;
  }
  
  public void statusListener()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.delayStatus.getSectionID() != null)
    {
      this.log.info("延期交易处于第" + this.delayStatus.getSectionID() + "交易节！");
      localStringBuffer.append("延期交易处于第【").append(this.delayStatus.getSectionID()).append("】交易节！");
    }
    this.delayDAO.updateDelayStatus(this.delayStatus);
    int i = this.delayStatus.getStatus();
    switch (i)
    {
    case 0: 
      this.log.info("延期交易初始化完成！");
      this.delayTradeTimeTask.setRunStatus(0);
      this.delayOrderProcess.setConsignerOrderStatus(1);
      this.delayOrderProcess.setTraderOrderStatus(1);
      this.delayMatcher.setRunStatus(1);
      this.delayMatcher.setNeutralRunStatus(1);
      break;
    case 4: 
      this.log.info("延期交易暂停交易！");
      this.delayTradeTimeTask.setRunStatus(1);
      this.delayOrderProcess.setConsignerOrderStatus(1);
      this.delayOrderProcess.setTraderOrderStatus(1);
      this.delayMatcher.setRunStatus(1);
      this.delayMatcher.setNeutralRunStatus(1);
      break;
    case 1: 
      this.log.info("延期交易交收申报中！");
      this.delayTradeTimeTask.setRunStatus(0);
      this.delayOrderProcess.setConsignerOrderStatus(0);
      this.delayOrderProcess.setTraderOrderStatus(0);
      if (this.delaySettleMatchType == 0) {
        this.delayMatcher.setRunStatus(0);
      } else {
        this.delayMatcher.setRunStatus(1);
      }
      this.delayMatcher.setNeutralRunStatus(1);
      break;
    case 3: 
      this.log.info("延期交易准备进入中立仓申报。。。");
      this.log.info("［准备进入中立仓申报时］交收申报成交。。。");
      this.delayMatcher.settleMatch();
      this.log.info("［准备进入中立仓申报时］交收申报成交完毕！");
      if (this.market.getDelayQuoShowType().shortValue() == 1)
      {
        this.log.info("延期交易更新交收申报和中立仓行情[实时更新行情]。。。");
        this.delayDAO.updateDelayQuotation();
        this.log.info("延期交易更新交收申报和中立仓行情[实时更新行情]完毕！");
      }
      else
      {
        this.log.info("延期交易更新交收申报行情[申报结束后更新]。。。");
        this.delayDAO.updateDelaySettleQuotation();
        this.log.info("延期交易更新交收申报行情[申报结束后更新]完毕！");
      }
      updateNeutralSide();
      this.log.info("延期交易中立仓申报中！");
      this.delayTradeTimeTask.setRunStatus(0);
      this.delayOrderProcess.setConsignerOrderStatus(0);
      this.delayOrderProcess.setTraderOrderStatus(0);
      this.delayMatcher.setRunStatus(1);
      if (this.delayNeutralMatchType == 0) {
        this.delayMatcher.setNeutralRunStatus(0);
      } else {
        this.delayMatcher.setNeutralRunStatus(1);
      }
      break;
    case 2: 
      this.log.info("延期交易节间休息！");
      this.delayOrderProcess.setConsignerOrderStatus(1);
      this.delayOrderProcess.setTraderOrderStatus(1);
      this.delayMatcher.setRunStatus(1);
      this.delayMatcher.setNeutralRunStatus(1);
      try
      {
        Thread.sleep(500L);
      }
      catch (InterruptedException localInterruptedException) {}
      this.log.info("节间休息时进行交收配对。。。");
      this.delayMatcher.settleMatch();
      this.log.info("节间休息时交收配对完毕！");
      if (this.market.getDelayQuoShowType().shortValue() == 0)
      {
        this.log.info("延期交易节间休息时更新延期行情。。。");
        this.delayDAO.updateDelayQuotation();
        this.log.info("延期交易节间休息时更新延期行情完毕！");
      }
      updateNeutralSide();
      this.delayTradeTimeTask.setRunStatus(0);
      break;
    case 5: 
      this.log.info("延期交易交易结束！");
      this.delayTradeTimeTask.setRunStatus(2);
      this.delayOrderProcess.setConsignerOrderStatus(1);
      this.delayOrderProcess.setTraderOrderStatus(1);
      this.delayMatcher.setRunStatus(1);
      this.delayMatcher.setNeutralRunStatus(1);
      if (this.market.getDelayQuoShowType().shortValue() == 0)
      {
        this.log.info("延期交易交易结束时更新延期行情。。。");
        this.delayDAO.updateDelayQuotation();
        this.log.info("延期交易交易结束时更新延期行情完毕！");
      }
      if (this.delaySettleMatchType == 1)
      {
        this.log.info("延期交易交易结束时进行交收配对。。。");
        this.delayMatcher.settleMatch();
        this.log.info("延期交易交易结束时交收配对完毕！");
      }
      this.log.info("延期交易交易结束时进行中立仓配对。。。");
      this.delayMatcher.neutralMatch();
      this.log.info("延期交易交易结束时中立仓配对完毕！");
      this.log.info("延期交易交易结束时未成交的委托自动撤单。。。");
      this.delayOrderProcess.autoWithdraw();
      this.log.info("延期交易交易结束时未成交的委托自动撤单完毕！");
      break;
    default: 
      this.log.error("未知延期交易状态[" + i + "]");
    }
    localStringBuffer.append("延期交易状态【").append(DelayStatus.DELAY_STATUS[i]).append("】");
    this.serverDAO.insertSysLog(new SysLog(localStringBuffer.toString(), 1501, 1));
  }
  
  public void stop()
  {
    if (this.delayTradeTimeTask != null)
    {
      this.delayTradeTimeTask.close();
      this.delayTradeTimeTask = null;
    }
    if (this.delayMatcher != null)
    {
      this.delayMatcher.close();
      this.delayMatcher = null;
    }
    this.delayOrderProcess = null;
    this.delayDAO = null;
  }
  
  public void changeDelayTradeDate(Date paramDate)
  {
    this.delayStatus.setTradeDate(paramDate);
    this.delayStatus.setStatus(0);
    this.delayStatus.setSectionID(null);
    this.delayStatus.setNote(null);
    this.delayStatus.setRecoverTime(null);
  }
  
  public DelayStatus getDelayStatus()
  {
    return this.delayStatus;
  }
  
  public DelayTradeTimeTask getDelayTradeTimeTask()
  {
    return this.delayTradeTimeTask;
  }
  
  public DelayOrderProcess getDelayOrderProcess()
  {
    return this.delayOrderProcess;
  }
  
  public Map getNeutralSideMap()
  {
    return this.neutralSideMap;
  }
  
  public void updateNeutralSide()
  {
    this.neutralSideMap = this.delayDAO.getNeutralSideMap();
    if (this.neutralSideMap != null)
    {
      Set localSet = this.neutralSideMap.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        DelayQuotation localDelayQuotation = (DelayQuotation)this.neutralSideMap.get(str);
        this.delayDAO.updateNeutralSide(localDelayQuotation);
      }
    }
  }
  
  public void close()
    throws Exception
  {
    if (this.delayStatus.getStatus() != 5) {
      throw new Exception("延期交易未结束，不能闭市操作！");
    }
    this.log.info("延期交易闭市操作时未成交的委托自动撤单。。。");
    this.delayOrderProcess.autoWithdraw();
    this.log.info("延期交易闭市操作时未成交的委托自动撤单完毕！");
  }
  
  public void initDelayOperate()
  {
    changeDelayTradeDate(this.server.getServerInit().getClearDate());
    this.delayTradeTimeTask.init(this.server);
    statusListener();
  }
  
  public void refreshDelayTradeTime()
  {
    if (!this.existDelayTradeTime)
    {
      this.existDelayTradeTime = isHasDelayTradeTime();
      if (this.existDelayTradeTime)
      {
        start(this.server);
        initDelayOperate();
      }
      return;
    }
    this.delayTradeTimeTask.refreshTradeTime();
  }
}
