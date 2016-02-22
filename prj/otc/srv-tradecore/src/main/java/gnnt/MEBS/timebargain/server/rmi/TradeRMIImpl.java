package gnnt.MEBS.timebargain.server.rmi;

import gnnt.MEBS.member.ActiveUser.LogonManager;
import gnnt.MEBS.member.ActiveUser.MD5;
import gnnt.MEBS.member.ActiveUser.TraderInfo;
import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.dao.FirmDAO;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.Consigner;
import gnnt.MEBS.timebargain.server.model.HoldPosition;
import gnnt.MEBS.timebargain.server.model.LimitOrder;
import gnnt.MEBS.timebargain.server.model.MarketOrder;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.Trader;
import gnnt.MEBS.timebargain.server.model.WithdrawOrder;
import gnnt.MEBS.timebargain.server.trade.AbstractOrder;
import gnnt.MEBS.timebargain.server.trade.TradeEngine;
import gnnt.MEBS.timebargain.server.util.StringUtil;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeRMIImpl
  extends UnicastRemoteObject
  implements TradeRMI
{
  private static final long serialVersionUID = 2690197650654049816L;
  Log log = LogFactory.getLog(getClass());
  private Server server;
  private LogonManager logonManager;
  private ServerInit serverInit;
  private gnnt.MEBS.timebargain.server.util.ActiveUserManager auManagerConsigner;
  
  public TradeRMIImpl(Server server)
    throws RemoteException
  {
    this.server = server;
    
    this.serverInit = server.getServerInit();
    
    this.logonManager = server.getLogonManager();
    
    this.auManagerConsigner = server.getAuManagerConsigner();
  }
  
  public TraderInfo logon(Trader trader)
    throws RemoteException
  {
    this.log.info("logon 交易员" + trader.getTraderID() + "登陆交易服务器！");
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    TraderInfo traderInfo = this.logonManager.logon(trader.getTraderID(), trader
      .getPassword(), trader.getKeyCode(), trader.getLogonIP());
    if (traderInfo.auSessionId > 0L)
    {
      this.log.info("交易员" + trader.getTraderID() + "登陆交易服务器成功！");
      

      Server.getFirmDAO().updateTraderOnLineInfo(trader.getTraderID(), 
        trader.getLogonIP(), 1);
      
      Server.getFirmDAO().addGlobalLog(trader.getTraderID(), 
        trader.getLogonIP(), 1105, 
        "登录交易服务器", 1);
      
      loadLoginUserInfoByTraderID(trader.getTraderID());
    }
    else
    {
      this.log.info("trader=" + trader + ";PWDMD5=" + MD5.getMD5(trader.getTraderID(), trader
        .getPassword()));
      this.log.info("logon return " + traderInfo.auSessionId);
    }
    return traderInfo;
  }
  
  private void loadLoginUserInfoByTraderID(String traderID)
  {
    this.log.debug("正在加载交易员[" + traderID + "]信息...");
    Trader svTrader = Server.getFirmDAO().getOneTrader(traderID);
    ServerInit.getTraderQueue().put(traderID, svTrader);
    this.log.debug("加载交易员[" + traderID + "]信息完毕！");
    
    String firmID = svTrader.getFirmID();
    this.log.debug("正在加载交易商[" + firmID + "]信息...");
    this.serverInit.loadOneFirm(firmID);
    this.log.debug("加载交易商[" + firmID + "]信息完毕！");
  }
  
  public TraderInfo remoteLogon(String traderID, String moduleId, long auSessionId, String logonIP)
    throws RemoteException
  {
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    TraderInfo traderInfo = this.logonManager.remoteLogon(traderID, moduleId, 
      auSessionId, logonIP);
    if (traderInfo.auSessionId > 0L)
    {
      this.log.debug("交易员" + traderID + "远程登陆交易服务器成功！");
      loadLoginUserInfoByTraderID(traderID);
    }
    return traderInfo;
  }
  
  public boolean isLogon(String traderID, long sessionID)
    throws RemoteException
  {
    return this.logonManager.isLogon(traderID, sessionID);
  }
  
  public int getLogonStatus(String traderID, long sessionID)
    throws RemoteException
  {
    boolean isLogon = isLogon(traderID, sessionID);
    if (isLogon) {
      return 0;
    }
    String userId = this.logonManager.getUserID(sessionID);
    if (userId == null)
    {
      String[] array = LogonManager.getActiveUserManager()
        .getAllUsersSys(traderID);
      if ((array != null) && (array.length > 0)) {
        return 2;
      }
    }
    return 1;
  }
  
  public TraderInfo getTraderInfo(String traderID)
    throws RemoteException
  {
    return this.logonManager.getTraderInfo(traderID);
  }
  
  public long consignerLogon(Consigner consigner)
    throws RemoteException
  {
    if (this.server == null) {
      return -204L;
    }
    if ((consigner.getConsignerID() == null) || 
      (consigner.getConsignerID().equals(""))) {
      return -1L;
    }
    if ((consigner.getPassword() == null) || 
      (consigner.getPassword().equals(""))) {
      return -2L;
    }
    long ret = Server.getTradeDAO().consignerLogon(consigner);
    if (ret == 0L)
    {
      ret = this.auManagerConsigner.logon(consigner.getConsignerID(), 
        consigner.getLogonIP());
      this.log.debug("代为委托员" + consigner.getConsignerID() + "登陆交易服务器成功！");
      

      Consigner c = Server.getServerDAO().getConsigner(
        consigner.getConsignerID());
      ServerInit.getConsignerQueue().put(consigner.getConsignerID(), c);
    }
    return ret;
  }
  
  public int marketOrder(long sessionID, MarketOrder marketOrder)
    throws RemoteException
  {
    this.log.info("marketOrder:" + marketOrder);
    if (this.server == null)
    {
      this.log.info("marketOrder return -204");
      return -204;
    }
    if (marketOrder == null)
    {
      this.log.info("marketOrder return -206");
      return -206;
    }
    if (marketOrder.getPrice().doubleValue() == 0.0D)
    {
      this.log.info("marketOrder return -207");
      return -207;
    }
    if (!this.logonManager.isLogon(marketOrder.getTraderID(), sessionID))
    {
      this.log.info("TraderID=" + marketOrder.getTraderID() + ", sessionID=" + 
        sessionID + ", marketOrder:" + marketOrder);
      this.log.info("marketOrder return 1");
      return 1;
    }
    Order order = new Order();
    order.setBuyOrSell(marketOrder.getBuyOrSell());
    order.setCloseMode(marketOrder.getCloseMode());
    order.setCommodityID(marketOrder.getCommodityID());
    order.setFirmID(((Trader)ServerInit.getTraderQueue().get(
      marketOrder.getTraderID())).getFirmID());
    order.setHoldNo(marketOrder.getSpecHoldNo());
    order.setOC_Flag(marketOrder.getOC_Flag());
    order.setOrderPoint(marketOrder.getOrderPoint());
    order.setOrderType(Short.valueOf((short)1));
    order.setOtherFirmID(marketOrder.getOtherFirmID());
    order.setPrice(marketOrder.getPrice());
    order.setQuantity(marketOrder.getQuantity());
    order.setTraderID(marketOrder.getTraderID());
    order.setIsslipPoint(marketOrder.isIsslippoint());
    order.setDelayTradeTime(marketOrder.getDelayTime());
    if (order.getOC_Flag() == 'O')
    {
      int rst = this.server.getTradeEngine().getMarketOrder().openOrder(order);
      if (rst != 0) {
        this.log.info("marketOrder return " + rst);
      }
      return rst;
    }
    if (order.getOC_Flag() == 'C')
    {
      int rst = this.server.getTradeEngine().getMarketOrder()
        .closeOrder(order);
      if (rst != 0) {
        this.log.info("marketOrder return " + rst);
      }
      return rst;
    }
    this.log.info("marketOrder return 2");
    return 2;
  }
  
  public int limitOrder(long sessionID, LimitOrder limitOrder)
    throws RemoteException
  {
    this.log.info("limitOrder:" + limitOrder);
    if (this.server == null)
    {
      this.log.info("limitOrder return -204");
      return -204;
    }
    if (limitOrder == null)
    {
      this.log.info("limitOrder return -206");
      return -206;
    }
    if (limitOrder.getPrice().doubleValue() == 0.0D)
    {
      this.log.info("limitOrder return -207");
      return -207;
    }
    if (!this.logonManager.isLogon(limitOrder.getTraderID(), sessionID))
    {
      this.log.info("TraderID=" + limitOrder.getTraderID() + ", sessionID=" + 
        sessionID + ", limitOrder:" + limitOrder);
      this.log.info("limitOrder return 1");
      return 1;
    }
    Order order = new Order();
    order.setBuyOrSell(limitOrder.getBuyOrSell());
    order.setCommodityID(limitOrder.getCommodityID());
    order.setFirmID(((Trader)ServerInit.getTraderQueue().get(
      limitOrder.getTraderID())).getFirmID());
    order.setOC_Flag('O');
    order.setOrderType(Short.valueOf((short)2));
    order.setOtherFirmID(limitOrder.getOtherFirmID());
    order.setPrice(limitOrder.getPrice());
    order.setQuantity(limitOrder.getQuantity());
    order.setStopLossPrice(limitOrder.getStopLossPrice());
    order.setStopProfitPrice(limitOrder.getStopProfitPrice());
    order.setTraderID(limitOrder.getTraderID());
    int rst = this.server.getTradeEngine().getLimitOrder().openOrder(order);
    if (rst != 0) {
      this.log.info("limitOrder return " + rst);
    }
    return rst;
  }
  
  public int withdrawOrder(long sessionID, WithdrawOrder withdrawOrder)
    throws RemoteException
  {
    this.log.info("withdrawOrder:withdrawOrder=" + withdrawOrder);
    if (this.server == null)
    {
      this.log.info(" withdrawOrder return -204 ");
      return -204;
    }
    if (withdrawOrder == null)
    {
      this.log.info(" withdrawOrder return -206 ");
      return -206;
    }
    if (!this.logonManager.isLogon(withdrawOrder.getTraderID(), sessionID))
    {
      this.log.info("TraderID=" + withdrawOrder.getTraderID() + ", sessionID=" + 
        sessionID + ", withdrawOrder:" + withdrawOrder);
      this.log.info(" withdrawOrder return 1 ");
      return 1;
    }
    Order order = new Order();
    order.setWithdrawID(withdrawOrder.getWithdrawID());
    order.setFirmID(((Trader)ServerInit.getTraderQueue().get(
      withdrawOrder.getTraderID())).getFirmID());
    order.setTraderID(withdrawOrder.getTraderID());
    int rst = this.server.getTradeEngine().getLimitOrder().withdrawOrder(order);
    if (rst != 0) {
      this.log.info(" withdrawOrder return  " + rst);
    }
    return rst;
  }
  
  public int consignerMarketOrder(long sessionID, MarketOrder marketOrder)
    throws RemoteException
  {
    this.log.info("consignerMarketOrder" + marketOrder);
    if (this.server == null)
    {
      this.log.info("consignerMarketOrder return -204");
      return -204;
    }
    if (marketOrder == null)
    {
      this.log.info("consignerMarketOrder return -206");
      return -206;
    }
    String firmID = marketOrder.getFirmID();
    


    this.log.debug("代为委托正在加载交易商[" + firmID + "]信息...");
    this.serverInit.loadOneFirm(firmID);
    this.log.debug("代为委托加载交易商[" + firmID + "]信息完毕！");
    
    Order order = new Order();
    order.setBuyOrSell(marketOrder.getBuyOrSell());
    order.setCloseMode(marketOrder.getCloseMode());
    order.setCommodityID(marketOrder.getCommodityID());
    order.setFirmID(marketOrder.getFirmID());
    order.setHoldNo(marketOrder.getSpecHoldNo());
    order.setOC_Flag(marketOrder.getOC_Flag());
    order.setOrderPoint(marketOrder.getOrderPoint());
    order.setOrderType(Short.valueOf((short)1));
    order.setOtherFirmID(marketOrder.getOtherFirmID());
    order.setPrice(marketOrder.getPrice());
    order.setQuantity(marketOrder.getQuantity());
    order.setConsignerID(marketOrder.getConsignerID());
    order.setConsignFirmID(marketOrder.getConsignFirmID());
    if (order.getOC_Flag() == 'O')
    {
      int rst = this.server.getTradeEngine().getMarketOrder().openOrder(order);
      if (rst != 0) {
        this.log.info("consignerMarketOrder return " + rst);
      }
      return rst;
    }
    if (order.getOC_Flag() == 'C')
    {
      int rst = this.server.getTradeEngine().getMarketOrder()
        .closeOrder(order);
      if (rst != 0) {
        this.log.info("consignerMarketOrder return " + rst);
      }
      return rst;
    }
    this.log.info("consignerMarketOrder return 2 ");
    return 2;
  }
  
  public int consignerLimitOrder(long sessionID, LimitOrder limitOrder)
    throws RemoteException
  {
    this.log.info("consignerLimitOrder:" + limitOrder);
    if (this.server == null)
    {
      this.log.info("consignerLimitOrder return -204");
      return -204;
    }
    if (limitOrder == null)
    {
      this.log.info("consignerLimitOrder return -206");
      return -206;
    }
    String firmID = limitOrder.getFirmID();
    


    this.log.debug("代为委托正在加载交易商[" + firmID + "]信息...");
    this.serverInit.loadOneFirm(firmID);
    this.log.debug("代为委托加载交易商[" + firmID + "]信息完毕！");
    
    Order order = new Order();
    order.setBuyOrSell(limitOrder.getBuyOrSell());
    order.setCommodityID(limitOrder.getCommodityID());
    order.setFirmID(limitOrder.getFirmID());
    order.setOC_Flag('O');
    order.setOrderType(Short.valueOf((short)2));
    order.setOtherFirmID(limitOrder.getOtherFirmID());
    order.setPrice(limitOrder.getPrice());
    order.setQuantity(limitOrder.getQuantity());
    order.setStopLossPrice(limitOrder.getStopLossPrice());
    order.setStopProfitPrice(limitOrder.getStopProfitPrice());
    order.setConsignerID(limitOrder.getConsignerID());
    order.setConsignFirmID(limitOrder.getConsignFirmID());
    int rst = this.server.getTradeEngine().getLimitOrder().openOrder(order);
    if (rst != 0) {
      this.log.info("consignerLimitOrder return " + rst);
    }
    return rst;
  }
  
  public int consignerWithdrawOrder(long sessionID, WithdrawOrder withdrawOrder)
    throws RemoteException
  {
    this.log.info("consignerWithdrawOrder:" + withdrawOrder);
    if (this.server == null)
    {
      this.log.info("consignerWithdrawOrder return -204");
      return -204;
    }
    if (withdrawOrder == null)
    {
      this.log.info("consignerWithdrawOrder return -206");
      return -206;
    }
    Order order = new Order();
    order.setWithdrawID(withdrawOrder.getWithdrawID());
    order.setFirmID(withdrawOrder.getFirmID());
    order.setConsignerID(withdrawOrder.getConsignerID());
    order.setConsignFirmID(withdrawOrder.getConsignFirmID());
    int rst = this.server.getTradeEngine().getLimitOrder().withdrawOrder(order);
    if (rst != 0) {
      this.log.info("consignerWithdrawOrder return " + rst);
    }
    return rst;
  }
  
  public int tradingReComputeFunds()
    throws RemoteException
  {
    if (this.server == null) {
      return -204;
    }
    if (Server.getSystemStatus().getStatus() != 4) {
      return -207;
    }
    return Server.getTradeDAO().reComputeFunds(2);
  }
  
  public void logoff(long sessionID)
    throws RemoteException
  {
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    this.logonManager.logoff(sessionID);
  }
  
  public void logoff(String tradeID, long sessionID, String ip, String note)
    throws RemoteException
  {
    this.log.info("logoff:tradeID=" + tradeID + "ip=" + ip + "note=" + 
      note);
    
    String userId = this.logonManager.getUserID(sessionID);
    
    boolean isSetOnlineStatus = true;
    if (userId == null)
    {
      String[] array = LogonManager.getActiveUserManager()
        .getAllUsersSys(tradeID);
      if ((array != null) && (array.length > 0)) {
        isSetOnlineStatus = false;
      }
    }
    if (isSetOnlineStatus)
    {
      if ((note == null) || (note.length() == 0)) {
        note = "退出交易服务器";
      }
      Server.getFirmDAO().updateTraderOnLineInfo(tradeID, ip, 0);
      Server.getFirmDAO().addGlobalLog(tradeID, ip, 
        1105, note, 1);
      logoff(sessionID);
    }
  }
  
  public void consignerLogoff(long sessionID)
    throws RemoteException
  {
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    this.auManagerConsigner.logoff(sessionID);
  }
  
  public int checkUser(String userID, String pswd)
    throws RemoteException
  {
    if ((userID == null) || (userID.equals(""))) {
      return -1;
    }
    if ((pswd == null) || (pswd.equals(""))) {
      return -2;
    }
    return this.logonManager.checkUser(userID, pswd);
  }
  
  public String getUserID(long sessionID)
    throws RemoteException
  {
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    return this.logonManager.getUserID(sessionID);
  }
  
  public String getConsignerID(long sessionID)
    throws RemoteException
  {
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    return this.auManagerConsigner.getUserID(sessionID);
  }
  
  public List getTraders()
    throws RemoteException
  {
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    List lst = new ArrayList();
    String[] sArr = this.logonManager.getAllUsers();
    for (int i = 0; i < sArr.length; i++)
    {
      String[] userArr = StringUtil.split(sArr[i], ",");
      Map map = new HashMap();
      map.put("traderID", userArr[0]);
      map.put("loginTime", userArr[1]);
      map.put("loginIP", userArr[2]);
      lst.add(map);
    }
    return lst;
  }
  
  public List getConsigners()
    throws RemoteException
  {
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    List lst = new ArrayList();
    String[] sArr = this.auManagerConsigner.getAllUsers();
    for (int i = 0; i < sArr.length; i++)
    {
      String[] userArr = StringUtil.split(sArr[i], ",");
      Map map = new HashMap();
      map.put("consignerID", userArr[0]);
      map.put("loginTime", userArr[1]);
      map.put("loginIP", userArr[2]);
      lst.add(map);
    }
    return lst;
  }
  
  public String getFirmID(String traderID)
    throws RemoteException
  {
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    return ((Trader)ServerInit.getTraderQueue().get(traderID)).getFirmID();
  }
  
  public void kickOnlineTrader(String traderID)
    throws RemoteException
  {
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    Server.getFirmDAO().updateTraderOnLineInfo(traderID, null, 0);
    this.logonManager.logoff(traderID);
    Server.getFirmDAO().addGlobalLog("System", null, 
      1105, 
      "交易员【" + traderID + "】已被强制退出", 1);
    
    this.log.debug("交易员【" + traderID + "】已被强制退出....");
  }
  
  public void kickAllTrader(String firmID)
    throws RemoteException
  {
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    Map traderMap = ServerInit.getTraderQueue();
    if (traderMap != null)
    {
      Set set = traderMap.keySet();
      Iterator iter = set.iterator();
      while (iter.hasNext())
      {
        String id = (String)iter.next();
        Trader t = (Trader)traderMap.get(id);
        if (firmID.equals(t.getFirmID()))
        {
          Server.getFirmDAO().updateTraderOnLineInfo(id, null, 0);
          this.logonManager.logoff(id);
          Server.getFirmDAO().addGlobalLog("System", null, 
            1105, 
            "kickAllTrader 交易员【" + id + "】已被强制退出", 1);
          this.log.debug("kickAllTrader method:交易员【" + id + "】已被强制退出....");
        }
      }
    }
    ServerInit.getFirmQueue().remove(firmID);
  }
  
  public int changePassowrd(String userId, String passwordOld, String password)
    throws RemoteException
  {
    this.log.info("changePassowrd:userId=" + userId);
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    int rst = Server.getFirmDAO().changePassowrd(userId, passwordOld, 
      password);
    if (rst != 0) {
      this.log.info("changePassowrd return " + rst);
    }
    return rst;
  }
  
  public int changePhonePassowrd(String userId, String passwordOld, String password)
    throws RemoteException
  {
    this.log.info("changePhonePassowrd:userId=" + userId);
    

    String firmID = getFirmID(userId);
    
    int rst = Server.getFirmDAO().changePhonePassowrd(firmID, passwordOld, 
      password);
    if (rst != 0) {
      this.log.info("changePhonePassowrd return " + rst);
    }
    return rst;
  }
  
  public long checkDelegateInfo(String memberID, String customerID, String phonePassword)
    throws RemoteException
  {
    if (this.server == null) {
      throw new RemoteException("交易服务器已关闭！");
    }
    return Server.getFirmDAO().checkDelegateInfo(memberID, customerID, 
      phonePassword);
  }
  
  public int setLossProfit(Long holdingID, Double stopLoss, Double stopProfit, String operatorID)
    throws RemoteException
  {
    this.log.info("setLossProfit:holdingID=" + holdingID + "stopLoss=" + 
      stopLoss + "stopProfit=" + stopProfit + "operatorID=" + 
      operatorID);
    if (TradeEngine.getInstance().getTraderOrderStatus() == 1)
    {
      this.log.info("系统处于不接受委托状态，拒绝设置止损止盈价！");
      this.log.info("setLossProfit return -1 ");
      return -1;
    }
    List<HoldPosition> holdList = Server.getTradeDAO().getHoldPosition(
      holdingID);
    if ((holdList == null) || (holdList.size() == 0))
    {
      this.log.info("setLossProfit return -10 ");
      return -10;
    }
    HoldPosition holdPosition = (HoldPosition)holdList.get(0);
    if (stopLoss.doubleValue() > 0.0D)
    {
      int lossRet = withdrawLossProfit(holdList, Short.valueOf((short)1), operatorID);
      if (lossRet != 0)
      {
        this.log.error("撤销原止损单失败,返回值:" + lossRet);
        return -2;
      }
      Order newOrder = new Order();
      newOrder.setBuyOrSell(Short.valueOf(holdPosition.getO_buyOrSell()));
      newOrder.setCloseMode(Short.valueOf((short)2));
      newOrder.setCommodityID(holdPosition.getCommodityID());
      newOrder.setConsignerID(operatorID);
      newOrder.setFirmID(holdPosition.getFirmID());
      newOrder.setHoldNo(holdPosition.getHoldNO());
      newOrder.setOC_Flag('C');
      newOrder.setOrderPoint(Double.valueOf(0.0D));
      newOrder.setOrderType(Short.valueOf((short)2));
      newOrder.setOtherFirmID(holdPosition.getO_firmID());
      
      newOrder.setPrice(stopLoss);
      newOrder.setQuantity(holdPosition.getHoldQty());
      newOrder.setTraderID(holdPosition.getTraderID());
      
      newOrder.setStopPLFlag((short)1);
      
      int ret = Server.getInstance().getTradeEngine().getLimitOrder()
        .closeOrder(newOrder);
      if (ret != 0)
      {
        this.log.error("添加止损委托失败，" + newOrder.toString() + "ret=" + ret);
        return ret;
      }
    }
    if (stopProfit.doubleValue() > 0.0D)
    {
      int profitRet = withdrawLossProfit(holdList, Short.valueOf((short)2), operatorID);
      if (profitRet != 0)
      {
        this.log.error("撤销原止盈单单失败,返回值:" + profitRet);
        return -3;
      }
      Order newOrder = new Order();
      newOrder.setBuyOrSell(Short.valueOf(holdPosition.getO_buyOrSell()));
      newOrder.setCloseMode(Short.valueOf((short)2));
      newOrder.setCommodityID(holdPosition.getCommodityID());
      newOrder.setConsignerID(operatorID);
      newOrder.setFirmID(holdPosition.getFirmID());
      newOrder.setHoldNo(holdPosition.getHoldNO());
      newOrder.setOC_Flag('C');
      newOrder.setOrderPoint(Double.valueOf(0.0D));
      newOrder.setOrderType(Short.valueOf((short)2));
      newOrder.setOtherFirmID(holdPosition.getO_firmID());
      
      newOrder.setPrice(stopProfit);
      newOrder.setQuantity(holdPosition.getHoldQty());
      newOrder.setTraderID(holdPosition.getTraderID());
      
      newOrder.setStopPLFlag((short)2);
      
      int ret = Server.getInstance().getTradeEngine().getLimitOrder()
        .closeOrder(newOrder);
      if (ret != 0)
      {
        this.log.error("添加止盈委托失败，" + newOrder.toString() + "ret=" + ret);
        return ret;
      }
    }
    return 0;
  }
  
  public int withdrawLossProfit(Long holdingID, Short type, String operatorID)
    throws RemoteException
  {
    this.log.info("setLossProfit:holdingID=" + holdingID + "type=" + type + 
      "operatorID=" + operatorID);
    if (TradeEngine.getInstance().getTraderOrderStatus() == 1)
    {
      this.log.info("不是交易时间，拒绝撤销止损止盈价！");
      return -99;
    }
    List<HoldPosition> holdList = Server.getTradeDAO().getHoldPosition(
      holdingID);
    
    int rst = withdrawLossProfit(holdList, type, operatorID);
    if (rst != 0) {
      this.log.info(" withdrawLossProfit return " + rst);
    }
    return rst;
  }
  
  public int withdrawLossProfit(List<HoldPosition> holdList, Short type, String operatorID)
    throws RemoteException
  {
    int ret = 0;
    if ((holdList != null) && (holdList.size() > 0))
    {
      HoldPosition holdPosition = (HoldPosition)holdList.get(0);
      this.log.info("withdrawLossProfit:holdPosition=" + holdPosition + 
        "type=" + type + "operatorID=" + operatorID);
      if (type.shortValue() == 1)
      {
        List<Long> orderList = Server.getTradeDAO().getOrderNOByPL(
          holdPosition.getHoldNO(), 1);
        if ((orderList == null) || (orderList.size() == 0)) {
          return 0;
        }
        for (Long orderno : orderList) {
          ret = this.server.getTradeEngine().withdrawOrderProcess(
            orderno, operatorID);
        }
      }
      else if (type.shortValue() == 2)
      {
        List<Long> orderList = Server.getTradeDAO().getOrderNOByPL(
          holdPosition.getHoldNO(), 2);
        if ((orderList == null) || (orderList.size() == 0)) {
          return 0;
        }
        for (Long orderno : orderList) {
          ret = this.server.getTradeEngine().withdrawOrderProcess(
            orderno, operatorID);
        }
      }
      else if (type.shortValue() == 3)
      {
        int lossRet = withdrawLossProfit(holdPosition.getHoldNO(), 
          Short.valueOf((short)1), operatorID);
        

        int profitRet = withdrawLossProfit(holdPosition.getHoldNO(), 
          Short.valueOf((short)2), operatorID);
        if ((lossRet != 0) && (profitRet != 0))
        {
          ret = -3;
          this.log.error("撤销止损止盈单全部失败,止损单返回值:" + lossRet + ";止盈单返回值:" + 
            profitRet);
        }
        else if (lossRet != 0)
        {
          ret = -4;
          this.log.error("撤销止损单失败,止损单返回值:" + lossRet + ";");
        }
        else if (profitRet != 0)
        {
          ret = -5;
          this.log.error("撤销止盈单失败,止盈单返回值:" + profitRet + ";");
        }
      }
      if (ret != 0) {
        this.log.info("withdrawLossProfit return " + ret);
      }
      return ret;
    }
    this.log.info("withdrawLossProfit return -2 ");
    return -2;
  }
}
