package gnnt.mebsv.hqservice.model;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientSocket
{
  public Socket socket;
  public String clientVersion;
  public int iStatus = 99;
  public String strCode = "";
  public long quoteListTime = 0L;
  public List vpSize = new ArrayList();
  public String strMarket = "00";
  public int billLastDate = 0;
  public int billLastTime = 0;
  public byte type;
  public long billUpDateTime;
  public Map<String, Long> billLastTotalAMap = new HashMap();
  public Map<String, Long> differTypebillLastTotalAMap = new HashMap();
  public int result;
  public Map codeList = new HashMap();
  public int minLastDate;
  public int minLastTime;
  public byte minType;
  public byte isAll = 1;
  public long lastSysTime;
  public int billDataPage;
  public int messageUT;

  public String toString()
  {
    return "MarketID:" + this.strMarket + " Code:" + this.strCode + " Status:" + this.iStatus;
  }
}