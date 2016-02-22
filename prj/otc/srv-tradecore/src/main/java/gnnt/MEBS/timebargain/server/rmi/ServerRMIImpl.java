package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.TradeDate;
import gnnt.MEBS.timebargain.server.TradeDateFactory;
import gnnt.MEBS.timebargain.server.TradeTimeTask;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.Market;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.quotation.QuotationEngine;
import gnnt.MEBS.timebargain.server.trade.TradeEngine;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;

public class ServerRMIImpl
  extends UnicastRemoteObject
  implements ServerRMI
{
  private static final long serialVersionUID = 2690197650654049814L;
  Log log = LogFactory.getLog(getClass());
  private Server server;
  private ServerDAO serverDAO;
  
  public ServerRMIImpl(Server server)
    throws RemoteException
  {
    this.server = server;
    this.serverDAO = Server.getServerDAO();
  }
  
  public void start()
    throws RemoteException
  {
    this.log.info("start");
    


    String curDbDate = DateUtil.formatDate(this.serverDAO.getCurDbDate(), 
      "yyyy-MM-dd");
    String strClearDate = DateUtil.formatDate(Server.getSystemStatus()
      .getClearDate(), "yyyy-MM-dd");
    if (!this.server.initServer()) {
      throw new RemoteException("交易服务器初始化失败！");
    }
  }
  
  public void close()
    throws RemoteException
  {
    this.log.info("close");
    if (Server.getSystemStatus().getStatus() == 1) {
      throw new RemoteException("交易服务器已闭市！");
    }
    try
    {
      this.log.info("交易服务器闭市操作");
      this.server.close();
      this.log.info("闭市操作完成");
    }
    catch (Exception e)
    {
      throw new RemoteException(e.getMessage());
    }
  }
  
  public void ctlTrade(int status)
    throws RemoteException
  {
    this.log.info("ctlTrade");
    if (status == 0)
    {
      if ((Server.getSystemStatus().getStatus() == 5) || 
        (Server.getSystemStatus().getStatus() == 8) || 
        (Server.getSystemStatus().getStatus() == 6))
      {
        Server.getSystemStatus().setStatus(
          4);
        Server.getSystemStatus().setPauseType('M');
        this.server.statusListener();
      }
      else
      {
        throw new RemoteException("非交易时间，不允许暂停！");
      }
    }
    else if ((status == 1) && 
      (Server.getSystemStatus().getStatus() == 4))
    {
      if (Server.getSystemStatus().getPauseType() == 'S') {
        throw new RemoteException("系统在设定时间内没有收到行情数据暂停交易，不允许手工恢复交易！");
      }
      this.server.getTradeTimeTask().recoverRun();
    }
  }
  
  public void timingContinueTrade(String recoverTime)
    throws RemoteException
  {
    this.log.info("timingContinueTrade recoverTime=" + recoverTime);
    try
    {
      DateUtil.convertStringToDate("HH:mm:ss", recoverTime);
    }
    catch (Exception e)
    {
      throw new RemoteException(recoverTime + "时间格式不正确！");
    }
    if (Server.getSystemStatus().getStatus() != 4) {
      throw new RemoteException("系统不处于暂停状态不能设置恢复时间!");
    }
    if (Server.getSystemStatus().getPauseType() == 'S') {
      throw new RemoteException("由于在设定时间内没有收到行情系统暂停交易，无法自动恢复所以不能设置自动恢复时间");
    }
    Server.getSystemStatus().setRecoverTime(recoverTime);
    this.serverDAO.updateSystemRecoverTime(recoverTime);
  }
  
  public void tradeEnd()
    throws RemoteException
  {
    this.log.info("tradeEnd");
    Server.getSystemStatus().setStatus(
      7);
    this.server.statusListener();
  }
  
  public void recoverTrade()
    throws RemoteException
  {
    this.log.info("recoverTrade");
    if (Server.getSystemStatus().getStatus() != 7) {
      throw new RemoteException("不是交易结束状态，不能恢复交易！");
    }
    if (!this.server.getServerInit().isCrossDay()) {
      if (!DateUtil.formatDate(Server.getSystemStatus().getClearDate(), "yyyy-MM-dd").equals(
        DateUtil.formatDate(this.serverDAO.getCurDbDate(), 
        "yyyy-MM-dd"))) {
        throw new RemoteException("日期已跨天，不能恢复交易！");
      }
    }
    Server.getSystemStatus().setStatus(5);
    this.server.statusListener();
  }
  
  public void getMaxDelayTime()
    throws RemoteException
  {
    this.server.getServerInit().loadMaxDelayTime();
  }
  
  public SystemStatus getSystemStatus()
    throws RemoteException
  {
    return Server.getSystemStatus();
  }
  
  public boolean isTradeDate()
    throws RemoteException
  {
    this.log.info("start isTradeDate");
    if (!this.server.getServerInit().getTradeDateInterface().checkTradeDay(
      this.serverDAO.getCurDbDate())) {
      if (Server.getSystemStatus().getStatus() == 3) {
        return false;
      }
    }
    return true;
  }
  
  public void refreshTradeTime()
    throws RemoteException
  {
    this.log.info("start refreshTradeTime");
    

    boolean crossDay = this.server.getServerInit().judgeCrossDay();
    TradeDate tradeDateInterface;
    TradeDate tradeDateInterface;
    if (crossDay) {
      tradeDateInterface = 
        TradeDateFactory.createTradeDate((short)1);
    } else {
      tradeDateInterface = 
        TradeDateFactory.createTradeDate((short)0);
    }
    this.server.getServerInit().setTradeDateInterface(tradeDateInterface);
    
    this.server.getServerInit().loadTradeTimeList();
    this.server.getServerInit().loadCommodityQueue();
    
    this.server.getTradeTimeTask().refreshTradeTime();
    try
    {
      this.server.getServerInit().reScheduleOpenServerJob();
    }
    catch (SchedulerException e)
    {
      e.printStackTrace();
      this.log.error("reScheduleOpenServerJob Error,ErrorMessage=" + 
        e.getMessage());
      throw new RemoteException("reScheduleOpenServerJob Error");
    }
    catch (ParseException e)
    {
      e.printStackTrace();
      e.printStackTrace();
      this.log.error("reScheduleOpenServerJob Error,ErrorMessage=" + 
        e.getMessage());
      throw new RemoteException("reScheduleOpenServerJob Error");
    }
    try
    {
      this.server.getServerInit().reScheduleBalanceJob();
    }
    catch (SchedulerException e)
    {
      e.printStackTrace();
      this.log.error("reScheduleBalanceJob Error,ErrorMessage=" + 
        e.getMessage());
      throw new RemoteException("reScheduleBalanceJob Error");
    }
    catch (ParseException e)
    {
      e.printStackTrace();
      this.log.error("reScheduleBalanceJob Error,ErrorMessage=" + 
        e.getMessage());
      throw new RemoteException("reScheduleBalanceJob Error");
    }
    this.log.debug("end refreshTradeTime");
  }
  
  public void refreshCommodityQueue()
    throws RemoteException
  {
    this.log.info("refreshCommodityQueue");
    this.server.getServerInit().loadCommodityQueue();
  }
  
  public void refreshExchageRate()
    throws RemoteException
  {
    this.log.info("refreshExchageRate");
    this.server.getQuotationEngine().loadExchageRateMap();
  }
  
  public void refreshMemory()
    throws RemoteException
  {
    this.log.info("refreshMemory");
    try
    {
      this.server.getServerInit().loadInitData();
    }
    catch (SchedulerException e)
    {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    }
    catch (ParseException e)
    {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    }
    this.server.getTradeEngine().refreshMemory();
    this.server.getQuotationEngine().refreshMemory();
  }
  
  public Date getCurDbDate()
    throws RemoteException
  {
    return this.serverDAO.getCurDbDate();
  }
  
  public int balance()
    throws RemoteException
  {
    this.log.info("balance");
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    try
    {
      return this.server.balance();
    }
    catch (Exception e)
    {
      throw new RemoteException(e.getMessage());
    }
  }
  
  public void loadOneMember2Queue(String firmID)
    throws RemoteException
  {
    this.log.info("loadOneMember2Queue");
    if ((firmID == null) || (firmID.length() == 0)) {
      throw new RemoteException("会员ID不能为空！");
    }
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    try
    {
      this.server.getServerInit().loadOneMember2Queue(firmID);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    }
  }
  
  public void setCommodityStatus(String commodityID, char status)
    throws RemoteException
  {
    this.log.info("setCommodityStatus");
    if ((commodityID == null) || (commodityID.length() == 0)) {
      throw new RemoteException("商品ID不能为空！");
    }
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    try
    {
      this.server.getServerInit().setCommodityStatus(commodityID, status);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    }
  }
  
  public Market getMarketInfo()
  {
    return this.server.getServerInit().getMarket();
  }
  
  public void loadMarketStartInfo()
    throws RemoteException
  {
    Market market = this.serverDAO.getMarket();
    if (this.server.getServerInit().getMarket().getRunMode() != market
      .getRunMode()) {
      this.server.getServerInit().getMarket().setRunMode(market.getRunMode());
    }
    if (this.server.getServerInit().getMarket().getInitPreSecs() != market
      .getInitPreSecs()) {
      try
      {
        this.server.getServerInit().reScheduleOpenServerJob();
        
        this.server.getServerInit().getMarket().setInitPreSecs(
          market.getInitPreSecs());
      }
      catch (SchedulerException e)
      {
        e.printStackTrace();
        this.log.error("reScheduleOpenServerJob Error,ErrorMessage=" + 
          e.getMessage());
        throw new RemoteException("reScheduleOpenServerJob Error");
      }
      catch (ParseException e)
      {
        e.printStackTrace();
        e.printStackTrace();
        this.log.error("reScheduleOpenServerJob Error,ErrorMessage=" + 
          e.getMessage());
        throw new RemoteException("reScheduleOpenServerJob Error");
      }
    }
  }
  
  public void loadMarketBalanceInfo()
    throws RemoteException
  {
    Market market = this.serverDAO.getMarket();
    if (this.server.getServerInit().getMarket().getClearRunMode() != market
      .getClearRunMode()) {
      this.server.getServerInit().getMarket().setClearRunMode(
        market.getClearRunMode());
    }
    if (this.server.getServerInit().getMarket().getClearDelaySecs() != market
      .getClearDelaySecs()) {
      try
      {
        this.server.getServerInit().reScheduleBalanceJob();
        this.server.getServerInit().getMarket().setClearDelaySecs(
          market.getClearDelaySecs());
      }
      catch (SchedulerException e)
      {
        e.printStackTrace();
        this.log.error("reScheduleBalanceJob Error,ErrorMessage=" + 
          e.getMessage());
        throw new RemoteException("reScheduleBalanceJob Error");
      }
      catch (ParseException e)
      {
        e.printStackTrace();
        this.log.error("reScheduleBalanceJob Error,ErrorMessage=" + 
          e.getMessage());
        throw new RemoteException("reScheduleBalanceJob Error");
      }
    }
  }
}
