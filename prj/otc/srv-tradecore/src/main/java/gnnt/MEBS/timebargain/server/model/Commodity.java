package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Commodity
  implements Serializable
{
  private static final long serialVersionUID = 3690197650654049812L;
  private String commodityID;
  private String name;
  private short status;
  private char tradeStatus;
  private char pauseType;
  private double contractFactor;
  private double minHQMove;
  private double minPriceMove;
  private double stepMove;
  private short spreadAlgr;
  private double spreadUpLmt;
  private double spreadDownLmt;
  private int displayNum;
  private Date marketDate;
  private double lastPrice;
  private boolean acceptHQFlag = false;
  public static final short STATUS_NOTMARKET = 0;
  public static final short STATUS_MARKET = 1;
  public static final short STATUS_EXITMARKET = 2;
  public static final short SPREADALGR_PERCENT = 1;
  public static final short SPREADALGR_ABSOLUTE = 2;
  public static final short SPREADALGR_NOTLIMIT = 4;
  public static final char TRADE_STATUS_NORMAL = 'N';
  public static final char TRADE_STATUS_PAUSE = 'P';
  public static final char PAUSE_TYPE_MANUAL = 'M';
  public static final char PAUSE_TYPE_SYSTEM = 'S';
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, 
      ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(String commodityID)
  {
    this.commodityID = commodityID;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public short getStatus()
  {
    return this.status;
  }
  
  public void setStatus(short status)
  {
    this.status = status;
  }
  
  public char getTradeStatus()
  {
    return this.tradeStatus;
  }
  
  public void setTradeStatus(char tradeStatus)
  {
    this.tradeStatus = tradeStatus;
  }
  
  public double getContractFactor()
  {
    return this.contractFactor;
  }
  
  public void setContractFactor(double contractFactor)
  {
    this.contractFactor = contractFactor;
  }
  
  public double getMinHQMove()
  {
    return this.minHQMove;
  }
  
  public void setMinHQMove(double minHQMove)
  {
    this.minHQMove = minHQMove;
  }
  
  public double getMinPriceMove()
  {
    return this.minPriceMove;
  }
  
  public void setMinPriceMove(double minPriceMove)
  {
    this.minPriceMove = minPriceMove;
  }
  
  public double getStepMove()
  {
    return this.stepMove;
  }
  
  public void setStepMove(double stepMove)
  {
    this.stepMove = stepMove;
  }
  
  public short getSpreadAlgr()
  {
    return this.spreadAlgr;
  }
  
  public void setSpreadAlgr(short spreadAlgr)
  {
    this.spreadAlgr = spreadAlgr;
  }
  
  public double getSpreadUpLmt()
  {
    return this.spreadUpLmt;
  }
  
  public void setSpreadUpLmt(double spreadUpLmt)
  {
    this.spreadUpLmt = spreadUpLmt;
  }
  
  public double getSpreadDownLmt()
  {
    return this.spreadDownLmt;
  }
  
  public void setSpreadDownLmt(double spreadDownLmt)
  {
    this.spreadDownLmt = spreadDownLmt;
  }
  
  public int getDisplayNum()
  {
    return this.displayNum;
  }
  
  public void setDisplayNum(int displayNum)
  {
    this.displayNum = displayNum;
  }
  
  public Date getMarketDate()
  {
    return this.marketDate;
  }
  
  public void setMarketDate(Date marketDate)
  {
    this.marketDate = marketDate;
  }
  
  public double getLastPrice()
  {
    return this.lastPrice;
  }
  
  public void setLastPrice(double lastPrice)
  {
    this.lastPrice = lastPrice;
  }
  
  public void setAcceptHQFlag(boolean acceptHQFlag)
  {
    this.acceptHQFlag = acceptHQFlag;
  }
  
  public boolean getAcceptHQFlag()
  {
    return this.acceptHQFlag;
  }
  
  public char getPauseType()
  {
    return this.pauseType;
  }
  
  public void setPauseType(char pauseType)
  {
    this.pauseType = pauseType;
  }
}
