package gnnt.MEBS.timebargain.server.quotation;

import gnnt.MEBS.timebargain.server.Server;
import gnnt.MEBS.timebargain.server.ServerInit;
import gnnt.MEBS.timebargain.server.TradeTimeTask;
import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.RiskcontrolDAO;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.HQServerInfo;
import gnnt.MEBS.timebargain.server.model.Member;
import gnnt.MEBS.timebargain.server.model.Quotation;
import gnnt.MEBS.timebargain.server.model.Quotation_RT;
import gnnt.MEBS.timebargain.server.model.QuotePoint_RT;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.trade.TradeEngine;
import gnnt.MEBS.timebargain.server.util.Arith;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QuotationInterfaceImpl_S_A
  implements QuotationInterface
{
  private Log log = LogFactory.getLog(getClass());
  private PriceInterface priceInterface;
  private long timeout;
  private ReceiveHQTimeOutThread receiveHQTimeOutThread = null;
  
  public void init()
  {
    if (this.receiveHQTimeOutThread == null)
    {
      this.receiveHQTimeOutThread = new ReceiveHQTimeOutThread();
      this.timeout = Long.parseLong(
        DAOBeanFactory.getConfig("ReceiveHQTimeOut"));
      this.receiveHQTimeOutThread.init(this.timeout);
      this.receiveHQTimeOutThread.setDaemon(true);
      this.receiveHQTimeOutThread.start();
    }
  }
  
  public void setPriceInterface(PriceInterface priceInterface)
  {
    this.priceInterface = priceInterface;
  }
  
  public void ctlTrade(int status)
  {
    SystemStatus systemStatus = Server.getSystemStatus();
    if (status == 0)
    {
      if ((systemStatus.getStatus() == 5) || 
        (systemStatus.getStatus() == 8) || 
        (systemStatus.getStatus() == 6))
      {
        systemStatus.setStatus(4);
        systemStatus.setPauseType('S');
        Server.getInstance().statusListener();
      }
    }
    else if ((status == 1) && 
      (systemStatus.getStatus() == 4) && 
      (systemStatus.getPauseType() == 'S')) {
      Server.getInstance().getTradeTimeTask().recoverRun();
    }
  }
  
  public void setQuotation(Quotation quotation)
  {
    ArrayList<String> list = (ArrayList)QuotationEngine.getThirdCmdtyMappingMap().get(
      quotation.getCommodityID());
    if ((list == null) && (list.size() == 0))
    {
      this.log.debug("商品表中不存在此行情的商品【" + quotation.getCommodityID() + 
        "】，拒绝接收此行情！");
      return;
    }
    for (int i = 0; i < list.size(); i++)
    {
      Quotation quo = copyQuotation(quotation, (String)list.get(i));
      setQuotation(quo, quo.getCommodityID());
    }
  }
  
  public void setQuotation(Quotation quotation, String commodityID)
  {
    if ((Server.getSystemStatus().getStatus() == 4) && 
      (Server.getSystemStatus().getPauseType() == 'S'))
    {
      this.log.info("系统处于没有收到行情的暂停状态，收到行情数据恢复交易；");
      ctlTrade(1);
      this.receiveHQTimeOutThread.setTime();
    }
    if (TradeEngine.getInstance().getTraderOrderStatus() == 1)
    {
      this.log.debug("系统处于不接受委托状态，拒绝接收此行情！");
      return;
    }
    quotation.setCommodityID(commodityID);
    
    quotation.setCurPrice(this.priceInterface.convertPrice(quotation
      .getCommodityID(), quotation.getCurPrice()));
    
    Map<String, Member> memberQueue = ServerInit.getMemberQueue();
    for (Member member : memberQueue.values())
    {
      if (member.getQuotationMap() == null) {
        throw new IllegalArgumentException("会员 " + member.getM_FirmID() + 
          " 的商品" + quotation.getCommodityID() + 
          " 商品行情运行时map为空,为了避免交易出现错误抛出异常！");
      }
      if (member.getQuotePointMap() == null) {
        throw new IllegalArgumentException("会员 " + member.getM_FirmID() + 
          " 的商品" + quotation.getCommodityID() + 
          " 商品报价点差map为空,为了避免交易出现错误抛出异常！");
      }
      Quotation_RT quotation_RT = (Quotation_RT)member.getQuotationMap().get(
        quotation.getCommodityID());
      QuotePoint_RT quotePoint_RT = (QuotePoint_RT)member.getQuotePointMap().get(
        quotation.getCommodityID());
      if (quotation_RT == null) {
        throw new IllegalArgumentException("会员 " + member.getM_FirmID() + 
          " 的商品" + quotation.getCommodityID() + 
          " 行情运行时信息为空,为了避免交易出现错误抛出异常！");
      }
      if (quotePoint_RT == null) {
        throw new IllegalArgumentException("会员 " + member.getM_FirmID() + 
          " 的商品" + quotation.getCommodityID() + 
          " 点差信息为空,为了避免交易出现错误抛出异常！");
      }
      quotation_RT.setCurPrice_B(Arith.add(quotation.getCurPrice().doubleValue(), 
        quotePoint_RT.getQuotePoint_B()));
      quotation_RT.setCurPrice_S(Arith.sub(quotation.getCurPrice().doubleValue(), 
        quotePoint_RT.getQuotePoint_S()));
      quotation_RT.setUpdateTime(quotation.getUpdateTime());
    }
    this.receiveHQTimeOutThread.setTime();
    

    Server.getRiskcontrolDAO().hqUpdate(quotation.getCommodityID(), 
      quotation.getCurPrice().doubleValue());
    

    QuotationObservable quotationObservable = 
      (QuotationObservable)QuotationEngine.getQuotationObservableMap().get(quotation.getCommodityID());
    quotationObservable.changeCurPrice(quotation.getCurPrice());
  }
  
  public Quotation copyQuotation(Quotation quotation, String commodityId)
  {
    Quotation quo = new Quotation();
    quo.setCommodityID(commodityId);
    quo.setClosePrice(quotation.getClosePrice());
    quo.setCurPrice(quotation.getCurPrice());
    quo.setHighPrice(quotation.getHighPrice());
    quo.setLowPrice(quotation.getLowPrice());
    quo.setOpenPrice(quotation.getOpenPrice());
    quo.setUpdateTime(quotation.getUpdateTime());
    return quo;
  }
  
  class ReceiveHQTimeOutThread
    extends Thread
  {
    boolean isRun = true;
    Date setDate = null;
    private long timeout;
    
    ReceiveHQTimeOutThread() {}
    
    public void init(long timeout)
    {
      this.timeout = timeout;
    }
    
    void setTime()
    {
      this.setDate = new Date();
    }
    
    public void run()
    {
      label197:
      while (this.isRun) {
        try
        {
          if ((this.setDate != null) && 
            (TradeEngine.getInstance().getTraderOrderStatus() == 0))
          {
            if (System.currentTimeMillis() - this.setDate.getTime() <= this.timeout) {
              break label197;
            }
            QuotationInterfaceImpl_S_A.this.log.warn("ReceiveHQTimeOutThread checked timeout close socket");
            QuotationInterfaceImpl_S_A.this.ctlTrade(0);
          }
        }
        catch (Exception e)
        {
          QuotationInterfaceImpl_S_A.this.log.error("ReceiveHQTimeOutThread checked timeout occur Error:" + 
            e.getMessage());
          e.printStackTrace();
          try
          {
            sleep(this.timeout);
          }
          catch (InterruptedException e)
          {
            QuotationInterfaceImpl_S_A.this.log.warn("ReceiveHQTimeOutThread InterruptedException occur Error:" + 
              e.getMessage());
            e.printStackTrace();
          }
        }
        finally
        {
          try
          {
            sleep(this.timeout);
          }
          catch (InterruptedException e)
          {
            QuotationInterfaceImpl_S_A.this.log.warn("ReceiveHQTimeOutThread InterruptedException occur Error:" + 
              e.getMessage());
            e.printStackTrace();
          }
        }
      }
    }
    
    public void shutdown()
    {
      this.isRun = false;
      try
      {
        interrupt();
      }
      catch (Exception localException) {}
    }
  }
  
  public List<HQServerInfo> getHQServerInfoList()
  {
    return Server.getServerDAO().getHQServerInfoList();
  }
  
  public void dispose()
  {
    if (this.receiveHQTimeOutThread != null)
    {
      this.receiveHQTimeOutThread.shutdown();
      
      this.receiveHQTimeOutThread = null;
    }
  }
  
  public boolean getTraderOrderStatus()
  {
    if (TradeEngine.getInstance().getTraderOrderStatus() == 0) {
      return true;
    }
    return false;
  }
  
  public void setCurServerInfo(HQServerInfo curServerInfo) {}
}
