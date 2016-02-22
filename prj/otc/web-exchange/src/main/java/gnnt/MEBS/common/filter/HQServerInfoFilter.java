package gnnt.MEBS.common.filter;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.Arith;
import gnnt.MEBS.base.util.DateUtil;
import gnnt.MEBS.base.util.SpringContextHelper;
import gnnt.MEBS.trade.model.Commodity;
import gnnt.MEBS.trade.model.HQServerInfo;
import gnnt.MEBS.trade.model.vo.HQServerInfoVO;
import gnnt.MEBS.trade.service.HQServerInfoService;
import gnnt.MEBS.trade.service.TQLogService;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HQServerInfoFilter
  implements Filter
{
  protected final transient Log logger = LogFactory.getLog(HQServerInfoFilter.class);
  public static Hashtable<String, HQServerInfoVO> serverInfoTable = null;
  
  public void destroy()
  {
    serverInfoTable = null;
  }
  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    chain.doFilter(request, response);
  }
  
  public void init(FilterConfig filterConfig)
    throws ServletException
  {
    serverInfoTable = new Hashtable();
    

    HQServerInfoService hqServerInfoService = (HQServerInfoService)
      SpringContextHelper.getBean("hqServerInfoService");
    QueryConditions qc = new QueryConditions();
    PageInfo pageInfo = new PageInfo(1, 10000, "primary.serverRank", false);
    List<HQServerInfo> hqServerInfoList = hqServerInfoService.getList(qc, 
      pageInfo);
    
    TQLogService tqLogService = (TQLogService)
      SpringContextHelper.getBean("tqLogService");
    List<Commodity> tcExchangeRateList = tqLogService.getExList();
    if ((hqServerInfoList != null) && (hqServerInfoList.size() > 0)) {
      for (HQServerInfo hqServerInfo : hqServerInfoList)
      {
        HQServerInfoVO hqServerInfoVO = new HQServerInfoVO();
        if ((tcExchangeRateList != null) && (tcExchangeRateList.size() > 0))
        {
          Map<String, Map<String, Object>> priceMap = new HashMap();
          for (Commodity commodity : tcExchangeRateList)
          {
            Map<String, Object> priceChangeMap = new HashMap();
            priceChangeMap.put("price", Double.valueOf(0.0D));
            priceChangeMap.put("date", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            priceMap.put(commodity.getId(), priceChangeMap);
          }
          hqServerInfoVO.setCommodityMap(priceMap);
        }
        hqServerInfoVO.setStatus(Integer.valueOf(1));
        

        hqServerInfoVO.setServerAddr(hqServerInfo.getServerAddr());
        hqServerInfoVO.setServerPort(hqServerInfo.getServerPort());
        String url = filterConfig.getServletContext().getRealPath("/") + 
          "trade/serverInfoMonitor/" + hqServerInfo.getId() + 
          ".mp3";
        File file = new File(url);
        hqServerInfoVO.setIsExistMusic(Boolean.valueOf(file.exists()));
        hqServerInfoVO.setIsUsed(hqServerInfo.getIsUsed());
        serverInfoTable.put(hqServerInfo.getId(), hqServerInfoVO);
        
        HQServerInfoThread hqServerInfoThread = new HQServerInfoThread(
          hqServerInfo.getId(), this);
        hqServerInfoThread.start();
      }
    }
  }
  
  public boolean processMsg(String hqServerInfoID, String msg)
  {
    this.logger.debug("process  msg=" + msg);
    String[] strs = msg.split("\\|", -1);
    if (strs.length < 4)
    {
      this.logger.error("msg format error!msg content=" + msg + 
        " the number of segments < 4：" + strs.length);
      return false;
    }
    if (strs[2].equals("7000"))
    {
      this.logger.debug("msg needn't process FUNCODE is 7000 !");
      
      HQServerInfoVO hqServerInfoVO = (HQServerInfoVO)serverInfoTable.get(hqServerInfoID);
      hqServerInfoVO.setStatus(Integer.valueOf(Integer.parseInt(strs[3])));
      







      return true;
    }
    if (!strs[2].equals("6000"))
    {
      this.logger.error("msg needn't process FUNCODE isn't 6000 !msg FUNCODE=" + 
        strs[2]);
      return false;
    }
    if (strs.length != 20)
    {
      this.logger.error("FUNCODE=6000 but msg format error !msg content=" + 
        msg + " the number of segments != 20：" + strs.length);
      return false;
    }
    if (!strs[4].equals("3"))
    {
      this.logger.error("msg needn't process,SUBFUNCODE isn't 3 !msg SUBFUNCODE=" + 
        strs[4]);
      return false;
    }
    HQServerInfoVO hqServerInfoVO = (HQServerInfoVO)serverInfoTable.get(hqServerInfoID);
    

    Map<String, Map<String, Object>> priceMap = hqServerInfoVO.getCommodityMap();
    Set<String> key = priceMap.keySet();
    for (Iterator it = key.iterator(); it.hasNext();)
    {
      String priceMapKey = (String)it.next();
      
      Map<String, Object> changeMap = (Map)priceMap.get(priceMapKey);
      if (priceMapKey.equals(strs[6]))
      {
        Double newPrice = Double.valueOf(strs[12]);
        if (Arith.sub(newPrice.doubleValue(), ((Double)changeMap.get("price")).doubleValue()) != 0.0D) {
          changeMap.put("date", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
        changeMap.put("price", Double.valueOf(strs[12]));
      }
    }
    return true;
  }
}
