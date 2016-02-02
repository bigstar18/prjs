package gnnt.MEBS.timebargain.server;

import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.model.SystemStatus;
import gnnt.MEBS.timebargain.server.model.TradeTime;
import gnnt.MEBS.timebargain.server.util.DateUtil;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TradeDateToday
  implements TradeDate
{
  private final Log log = LogFactory.getLog(getClass());
  private ServerDAO serverDAO = (ServerDAO)DAOBeanFactory.getBean("serverDAO");
  
  public Date calClearDate()
  {
    return this.serverDAO.getCurDbDate();
  }
  
  public List getTradeTimes(Date paramDate)
  {
    String str = DateUtil.formatDate(paramDate, "yyyy-MM-dd");
    ArrayList localArrayList = new ArrayList();
    List localList = this.serverDAO.getTradeTimes();
    for (int i = 0; i < localList.size(); i++)
    {
      TradeTime localTradeTime = (TradeTime)localList.get(i);
      try
      {
        if (localTradeTime.getGatherBid().shortValue() == 1)
        {
          localTradeTime.setBidStartTimeMillis(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", str + " " + localTradeTime.getBidStartTime()).getTime());
          localTradeTime.setBidEndTimeMillis(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", str + " " + localTradeTime.getBidEndTime()).getTime());
          localTradeTime.setBidStartDate(str);
          localTradeTime.setBidEndDate(str);
        }
        localTradeTime.setStartTimeMillis(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", str + " " + localTradeTime.getStartTime()).getTime());
        localTradeTime.setEndTimeMillis(DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", str + " " + localTradeTime.getEndTime()).getTime());
        localTradeTime.setStartDate(str);
        localTradeTime.setEndDate(str);
      }
      catch (ParseException localParseException)
      {
        this.log.error("装载交易节信息时解析日期失败，原因：" + localParseException.getMessage());
        System.out.println(DateUtil.getCurDateTime() + "   装载交易节信息时解析日期失败，原因：" + localParseException.getMessage());
      }
      this.serverDAO.updateTradeSectionDateStatus(localTradeTime);
      if (localTradeTime.getStatus().shortValue() == 1) {
        localArrayList.add(localTradeTime);
      }
    }
    return localArrayList;
  }
  
  public Date getRecoverDateByTime(String paramString)
    throws ParseException
  {
    SystemStatus localSystemStatus = this.serverDAO.getSystemStatus();
    String str = DateUtil.formatDate(localSystemStatus.getTradeDate(), "yyyy-MM-dd");
    return DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss", str + " " + paramString);
  }
}
