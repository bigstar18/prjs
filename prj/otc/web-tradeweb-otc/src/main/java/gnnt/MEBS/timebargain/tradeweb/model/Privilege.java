package gnnt.MEBS.timebargain.tradeweb.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Privilege
  implements Serializable
{
  private static final long serialVersionUID = 3115962478831534414L;
  private String traderID;
  private String traderName;
  private String status;
  private String firmId;
  private String firmName;
  private String firmType;
  private String memberType;
  private String traderType;
  private String m_FirmID;
  private String m_FirmName;
  private boolean isLogon = true;
  private String logonIP;
  private Map<String, Map<String, BigDecimal>> firm_MarginRate;
  private Map<String, Map<String, BigDecimal>> firm_FeeRate;
  private Map<String, Map<String, BigDecimal>> firm_DelayFee;
  private Map<String, Map<String, BigDecimal>> quotePoint;
  private Map<String, Map<String, BigDecimal>> myQuotePoint;
  private Map<String, Map<String, BigDecimal>> orderPoint;
  private Map<String, Map<String, BigDecimal>> firmTradePrivilege;
  private Map<String, Integer> noDisplayPrivilege = new HashMap();
  private Map<String, Map<String, BigDecimal>> firmDelayTrade;
  private Map<String, Map<String, BigDecimal>> holdQty;
  private Long sessionID;
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String firmId)
  {
    this.firmId = firmId;
  }
  
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String firmName)
  {
    this.firmName = firmName;
  }
  
  public String getTraderName()
  {
    return this.traderName;
  }
  
  public void setTraderName(String traderName)
  {
    this.traderName = traderName;
  }
  
  public String getTraderID()
  {
    return this.traderID;
  }
  
  public void setTraderID(String traderID)
  {
    this.traderID = traderID;
  }
  
  public Map<String, Map<String, BigDecimal>> getFirmTradePrivilege()
  {
    return this.firmTradePrivilege;
  }
  
  public void setFirmTradePrivilege(Map<String, Map<String, BigDecimal>> firmTradePrivilege)
  {
    this.firmTradePrivilege = firmTradePrivilege;
  }
  
  public Map<String, Integer> getNoDisplayPrivilege()
  {
    return this.noDisplayPrivilege;
  }
  
  public void setNoDisplayPrivilege(Map<String, Integer> noDisplayPrivilege)
  {
    this.noDisplayPrivilege = noDisplayPrivilege;
  }
  
  public Map getFirm_DelayFee()
  {
    return this.firm_DelayFee;
  }
  
  public void setFirm_DelayFee(Map firmDelayFee)
  {
    this.firm_DelayFee = firmDelayFee;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, 
      ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public String getFirmType()
  {
    return this.firmType;
  }
  
  public void setFirmType(String firmType)
  {
    this.firmType = firmType;
  }
  
  public String getTraderType()
  {
    return this.traderType;
  }
  
  public void setTraderType(String traderType)
  {
    this.traderType = traderType;
  }
  
  public Long getSessionID()
  {
    return this.sessionID;
  }
  
  public void setSessionID(Long sessionID)
  {
    this.sessionID = sessionID;
  }
  
  public Map getFirm_MarginRate()
  {
    return this.firm_MarginRate;
  }
  
  public void setFirm_MarginRate(Map firm_MarginRate)
  {
    this.firm_MarginRate = firm_MarginRate;
  }
  
  public Map getFirm_FeeRate()
  {
    return this.firm_FeeRate;
  }
  
  public void setFirm_FeeRate(Map firm_FeeRate)
  {
    this.firm_FeeRate = firm_FeeRate;
  }
  
  public Map<String, Map<String, BigDecimal>> getQuotePoint()
  {
    return this.quotePoint;
  }
  
  public void setQuotePoint(Map<String, Map<String, BigDecimal>> quotePoint)
  {
    this.quotePoint = quotePoint;
  }
  
  public Map<String, Map<String, BigDecimal>> getMyQuotePoint()
  {
    return this.myQuotePoint;
  }
  
  public void setMyQuotePoint(Map<String, Map<String, BigDecimal>> myQuotePoint)
  {
    this.myQuotePoint = myQuotePoint;
  }
  
  public Map<String, Map<String, BigDecimal>> getOrderPoint()
  {
    return this.orderPoint;
  }
  
  public void setOrderPoint(Map<String, Map<String, BigDecimal>> orderPoint)
  {
    this.orderPoint = orderPoint;
  }
  
  public Map<String, Map<String, BigDecimal>> getHoldQty()
  {
    return this.holdQty;
  }
  
  public void setHoldQty(Map<String, Map<String, BigDecimal>> holdQty)
  {
    this.holdQty = holdQty;
  }
  
  public String getM_FirmID()
  {
    return this.m_FirmID;
  }
  
  public void setM_FirmID(String mFirmID)
  {
    this.m_FirmID = mFirmID;
  }
  
  public String getM_FirmName()
  {
    return this.m_FirmName;
  }
  
  public void setM_FirmName(String mFirmName)
  {
    this.m_FirmName = mFirmName;
  }
  
  public boolean isLogon()
  {
    return this.isLogon;
  }
  
  public void setLogon(boolean isLogon)
  {
    this.isLogon = isLogon;
  }
  
  public String getLogonIP()
  {
    return this.logonIP;
  }
  
  public void setLogonIP(String logonIP)
  {
    this.logonIP = logonIP;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getMemberType()
  {
    return this.memberType;
  }
  
  public void setMemberType(String memberType)
  {
    this.memberType = memberType;
  }
  
  public void setFirmDelayTrade(Map<String, Map<String, BigDecimal>> firmDelayTrade)
  {
    this.firmDelayTrade = firmDelayTrade;
  }
  
  public Map<String, Map<String, BigDecimal>> getFirmDelayTrade()
  {
    return this.firmDelayTrade;
  }
}
