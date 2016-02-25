package gnnt.MEBS.vendue.server;

import gnnt.MEBS.vendue.server.dao.TradeDAO;
import gnnt.MEBS.vendue.server.vo.BargainVO;
import gnnt.MEBS.vendue.server.vo.BroadcastVO;
import gnnt.MEBS.vendue.server.vo.CommodityVO;
import gnnt.MEBS.vendue.server.vo.CurSubmitVO;
import gnnt.MEBS.vendue.server.vo.TradeUserVO;
import java.sql.SQLException;
import java.util.Vector;
import javax.naming.NamingException;

public abstract class SubmitAction
{
  protected int PARTITIONID;
  protected Object TRADELOCK = new Object();
  protected TradeDAO TRADEDAO;
  
  public abstract Vector getSubmit_v(String paramString)
    throws SQLException;
  
  protected abstract void setPartition(int paramInt);
  
  protected abstract void setTradeDAO(TradeDAO paramTradeDAO);
  
  public abstract CurSubmitVO[] getSubmit(String paramString)
    throws SQLException;
  
  public abstract BargainVO[] getBargainList(String paramString)
    throws SQLException;
  
  public abstract BargainVO getBargain(long paramLong)
    throws SQLException;
  
  public abstract BargainVO[] getMarketBargain()
    throws SQLException;
  
  public abstract int submit(String paramString1, String paramString2, String paramString3, double paramDouble, long paramLong)
    throws Exception, SQLException, ClassNotFoundException, IllegalAccessException, NamingException, InstantiationException;
  
  public abstract String getLastXML(String paramString1, String paramString2)
    throws SQLException;
  
  public abstract BroadcastVO getBroadcast(long paramLong)
    throws SQLException;
  
  public abstract CommodityVO[] getCurCommodityList();
  
  public abstract CommodityVO getCurCommodity(String paramString);
  
  public abstract TradeUserVO getTradeUser(String paramString)
    throws SQLException;
  
  public abstract int chgTraderPwd(String paramString1, String paramString2, String paramString3)
    throws SQLException;
}
