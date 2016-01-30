package gnnt.MEBS.timebargain.tradeweb.model;

import gnnt.MEBS.timebargain.tradeweb.core.TradeService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Privilege
  implements Serializable
{
  private static final long serialVersionUID = 3115962478831534414L;
  public static final int VIEW_QUOTATION = 301;
  public static final int VIEW_RESULT = 302;
  public static final int TRADER_ORDER = 303;
  public static final int QUERY_ORDER = 304;
  public static final int QUERY_INFO = 305;
  public static final String SUPER_USER = "A";
  public static final String Nomal_USER = "N";
  private String traderID;
  private String traderName;
  private String firmId;
  private String firmName;
  private String traderStatus;
  private Map firm_MarginRate;
  private Map firm_FeeRate;
  private Map firm_SettleFeeRate;
  private List traderPrivilege;
  private Map tBreedPrivilege_B;
  private Map tBreedPrivilege_S;
  private Map fBreedPrivilege_B;
  private Map fBreedPrivilege_S;
  private Map fCommPrivilege_B;
  private Map fCommPrivilege_S;
  private Map cusBreedPrivilege_B;
  private Map cusBreedPrivilege_S;
  private Map cusCommPrivilege_B;
  private Map cusCommPrivilege_S;
  private Map commPrivilege_B;
  private Map commPrivilege_S;
  private Map CommMap;
  private Map breedPrivilege;
  public static final int TYPE_FIRM = 1;
  public static final int TYPE_TRADER = 3;
  public static final int TYPE_CUSTOMER = 2;
  public static final int KIND_BREED = 1;
  public static final int KIND_COMMODITY = 2;
  public static final int PRIVILEGE_SettleBS_BOTH = 101;
  public static final int PRIVILEGE_SettleBS_SETTLE = 102;
  public static final int PRIVILEGE_SettleBS_NEUTRAL = 103;
  public static final int PRIVILEGE_SettleBS_NONE = 104;
  private Map fCommSettlePrivilege_B;
  private Map fCommSettlePrivilege_S;
  private Map tCommSettlePrivilege_B;
  private Map tCommSettlePrivilege_S;
  private Map cusCommSettlePrivilege_B;
  private Map cusCommSettlePrivilege_S;
  private Map fBreedSettlePrivilege_B;
  private Map fBreedSettlePrivilege_S;
  private Map tBreedSettlePrivilege_B;
  private Map tBreedSettlePrivilege_S;
  private Map cusBreedSettlePrivilege_B;
  private Map cusBreedSettlePrivilege_S;
  private Map directBreeds;
  private List directFirmBreeds;
  private String ids;
  private Long sessionID;
  private String clientVersion;
  private String firm_tariffId;
  
  public String getIds()
  {
    return this.ids;
  }
  
  public void setIds(String paramString)
  {
    this.ids = paramString;
  }
  
  public String getFirmId()
  {
    return this.firmId;
  }
  
  public void setFirmId(String paramString)
  {
    this.firmId = paramString;
  }
  
  public String getFirmName()
  {
    return this.firmName;
  }
  
  public void setFirmName(String paramString)
  {
    this.firmName = paramString;
  }
  
  public String getTraderName()
  {
    return this.traderName;
  }
  
  public void setTraderName(String paramString)
  {
    this.traderName = paramString;
  }
  
  public Map getBreedPrivilege()
  {
    return this.breedPrivilege;
  }
  
  public void setBreedPrivilege(Map paramMap)
  {
    this.breedPrivilege = paramMap;
  }
  
  public String getTraderID()
  {
    return this.traderID;
  }
  
  public void setTraderID(String paramString)
  {
    this.traderID = paramString;
  }
  
  public List getTraderPrivilege()
  {
    return this.traderPrivilege;
  }
  
  public void setTraderPrivilege(List paramList)
  {
    this.traderPrivilege = paramList;
  }
  
  public Map getFBreedPrivilege_B()
  {
    return this.fBreedPrivilege_B;
  }
  
  public void setFBreedPrivilege_B(Map paramMap)
  {
    this.fBreedPrivilege_B = paramMap;
  }
  
  public Map getFBreedPrivilege_S()
  {
    return this.fBreedPrivilege_S;
  }
  
  public void setFBreedPrivilege_S(Map paramMap)
  {
    this.fBreedPrivilege_S = paramMap;
  }
  
  public Map getFCommPrivilege_B()
  {
    return this.fCommPrivilege_B;
  }
  
  public void setFCommPrivilege_B(Map paramMap)
  {
    this.fCommPrivilege_B = paramMap;
  }
  
  public Map getCusBreedPrivilege_B()
  {
    return this.cusBreedPrivilege_B;
  }
  
  public void setCusBreedPrivilege_B(Map paramMap)
  {
    this.cusBreedPrivilege_B = paramMap;
  }
  
  public Map getCusBreedPrivilege_S()
  {
    return this.cusBreedPrivilege_S;
  }
  
  public void setCusBreedPrivilege_S(Map paramMap)
  {
    this.cusBreedPrivilege_S = paramMap;
  }
  
  public Map getCusCommPrivilege_B()
  {
    return this.cusCommPrivilege_B;
  }
  
  public void setCusCommPrivilege_B(Map paramMap)
  {
    this.cusCommPrivilege_B = paramMap;
  }
  
  public Map getCusCommPrivilege_S()
  {
    return this.cusCommPrivilege_S;
  }
  
  public void setCusCommPrivilege_S(Map paramMap)
  {
    this.cusCommPrivilege_S = paramMap;
  }
  
  public Map getFCommPrivilege_S()
  {
    return this.fCommPrivilege_S;
  }
  
  public void setFCommPrivilege_S(Map paramMap)
  {
    this.fCommPrivilege_S = paramMap;
  }
  
  public Map getCommPrivilege_B()
  {
    return this.commPrivilege_B;
  }
  
  public void setCommPrivilege_B(Map paramMap)
  {
    this.commPrivilege_B = paramMap;
  }
  
  public Map getCommPrivilege_S()
  {
    return this.commPrivilege_S;
  }
  
  public void setCommPrivilege_S(Map paramMap)
  {
    this.commPrivilege_S = paramMap;
  }
  
  public Map getCommMap()
  {
    return this.CommMap;
  }
  
  public void setCommMap(Map paramMap)
  {
    this.CommMap = paramMap;
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public Map getTBreedPrivilege_B()
  {
    return this.tBreedPrivilege_B;
  }
  
  public void setTBreedPrivilege_B(Map paramMap)
  {
    this.tBreedPrivilege_B = paramMap;
  }
  
  public Map getTBreedPrivilege_S()
  {
    return this.tBreedPrivilege_S;
  }
  
  public void setTBreedPrivilege_S(Map paramMap)
  {
    this.tBreedPrivilege_S = paramMap;
  }
  
  public String getTraderStatus()
  {
    return this.traderStatus;
  }
  
  public void setTraderStatus(String paramString)
  {
    this.traderStatus = paramString;
  }
  
  public Long getSessionID()
  {
    return this.sessionID;
  }
  
  public void setSessionID(Long paramLong)
  {
    this.sessionID = paramLong;
  }
  
  public Map getFirm_MarginRate()
  {
    return this.firm_MarginRate;
  }
  
  public void setFirm_MarginRate(Map paramMap)
  {
    this.firm_MarginRate = paramMap;
  }
  
  public Map getFirm_FeeRate()
  {
    HashMap localHashMap1 = new HashMap();
    try
    {
      Object localObject = new HashMap();
      if (TradeService.tariff.get(this.firm_tariffId) != null) {
        localObject = (Map)TradeService.tariff.get(this.firm_tariffId);
      }
      localHashMap1.putAll((Map)localObject);
      if (this.firm_FeeRate != null)
      {
        Iterator localIterator = this.firm_FeeRate.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          Map localMap = (Map)this.firm_FeeRate.get(str);
          HashMap localHashMap2 = new HashMap();
          localHashMap2.put("FeeAlgr", (BigDecimal)localMap.get("FeeAlgr"));
          localHashMap2.put("FeeRate_B", (BigDecimal)localMap.get("FeeRate_B"));
          localHashMap2.put("FeeRate_S", (BigDecimal)localMap.get("FeeRate_S"));
          localHashMap2.put("todayclosefeerate_b", (BigDecimal)localMap.get("todayclosefeerate_b"));
          localHashMap2.put("todayclosefeerate_s", (BigDecimal)localMap.get("todayclosefeerate_s"));
          localHashMap2.put("historyclosefeerate_b", (BigDecimal)localMap.get("historyclosefeerate_b"));
          localHashMap2.put("historyclosefeerate_s", (BigDecimal)localMap.get("historyclosefeerate_s"));
          localHashMap2.put("forceclosefeerate_b", (BigDecimal)localMap.get("forceclosefeerate_b"));
          localHashMap2.put("forceclosefeerate_s", (BigDecimal)localMap.get("forceclosefeerate_s"));
          localHashMap1.put(str, localHashMap2);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localHashMap1;
  }
  
  public void setFirm_FeeRate(Map paramMap)
  {
    this.firm_FeeRate = paramMap;
  }
  
  public Map getFirm_SettleFeeRate()
  {
    HashMap localHashMap1 = new HashMap();
    try
    {
      Object localObject = new HashMap();
      if (TradeService.tariff.get(this.firm_tariffId) != null) {
        localObject = (Map)TradeService.tariff.get(this.firm_tariffId);
      }
      localHashMap1.putAll((Map)localObject);
      if (this.firm_SettleFeeRate != null)
      {
        Iterator localIterator = this.firm_SettleFeeRate.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          Map localMap = (Map)this.firm_SettleFeeRate.get(str);
          HashMap localHashMap2 = new HashMap();
          localHashMap2.put("settlefeealgr", (BigDecimal)localMap.get("settlefeealgr"));
          localHashMap2.put("settlefeerate_b", (BigDecimal)localMap.get("settlefeerate_b"));
          localHashMap2.put("settlefeerate_s", (BigDecimal)localMap.get("settlefeerate_s"));
          localHashMap1.put(str, localHashMap2);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localHashMap1;
  }
  
  public void setFirm_SettleFeeRate(Map paramMap)
  {
    this.firm_SettleFeeRate = paramMap;
  }
  
  public Map getfCommSettlePrivilege_B()
  {
    return this.fCommSettlePrivilege_B;
  }
  
  public void setfCommSettlePrivilege_B(Map paramMap)
  {
    this.fCommSettlePrivilege_B = paramMap;
  }
  
  public Map getfCommSettlePrivilege_S()
  {
    return this.fCommSettlePrivilege_S;
  }
  
  public void setfCommSettlePrivilege_S(Map paramMap)
  {
    this.fCommSettlePrivilege_S = paramMap;
  }
  
  public Map gettCommSettlePrivilege_B()
  {
    return this.tCommSettlePrivilege_B;
  }
  
  public void settCommSettlePrivilege_B(Map paramMap)
  {
    this.tCommSettlePrivilege_B = paramMap;
  }
  
  public Map gettCommSettlePrivilege_S()
  {
    return this.tCommSettlePrivilege_S;
  }
  
  public void settCommSettlePrivilege_S(Map paramMap)
  {
    this.tCommSettlePrivilege_S = paramMap;
  }
  
  public Map getCusCommSettlePrivilege_B()
  {
    return this.cusCommSettlePrivilege_B;
  }
  
  public void setCusCommSettlePrivilege_B(Map paramMap)
  {
    this.cusCommSettlePrivilege_B = paramMap;
  }
  
  public Map getCusCommSettlePrivilege_S()
  {
    return this.cusCommSettlePrivilege_S;
  }
  
  public void setCusCommSettlePrivilege_S(Map paramMap)
  {
    this.cusCommSettlePrivilege_S = paramMap;
  }
  
  public Map getfBreedSettlePrivilege_B()
  {
    return this.fBreedSettlePrivilege_B;
  }
  
  public void setfBreedSettlePrivilege_B(Map paramMap)
  {
    this.fBreedSettlePrivilege_B = paramMap;
  }
  
  public Map getfBreedSettlePrivilege_S()
  {
    return this.fBreedSettlePrivilege_S;
  }
  
  public void setfBreedSettlePrivilege_S(Map paramMap)
  {
    this.fBreedSettlePrivilege_S = paramMap;
  }
  
  public Map gettBreedSettlePrivilege_B()
  {
    return this.tBreedSettlePrivilege_B;
  }
  
  public void settBreedSettlePrivilege_B(Map paramMap)
  {
    this.tBreedSettlePrivilege_B = paramMap;
  }
  
  public Map gettBreedSettlePrivilege_S()
  {
    return this.tBreedSettlePrivilege_S;
  }
  
  public void settBreedSettlePrivilege_S(Map paramMap)
  {
    this.tBreedSettlePrivilege_S = paramMap;
  }
  
  public Map getCusBreedSettlePrivilege_B()
  {
    return this.cusBreedSettlePrivilege_B;
  }
  
  public void setCusBreedSettlePrivilege_B(Map paramMap)
  {
    this.cusBreedSettlePrivilege_B = paramMap;
  }
  
  public Map getCusBreedSettlePrivilege_S()
  {
    return this.cusBreedSettlePrivilege_S;
  }
  
  public void setCusBreedSettlePrivilege_S(Map paramMap)
  {
    this.cusBreedSettlePrivilege_S = paramMap;
  }
  
  public Map getDirectBreeds()
  {
    return this.directBreeds;
  }
  
  public void setDirectBreeds(Map paramMap)
  {
    this.directBreeds = paramMap;
  }
  
  public List getDirectFirmBreeds()
  {
    return this.directFirmBreeds;
  }
  
  public void setDirectFirmBreeds(List paramList)
  {
    this.directFirmBreeds = paramList;
  }
  
  public String getClientVersion()
  {
    return this.clientVersion;
  }
  
  public void setClientVersion(String paramString)
  {
    this.clientVersion = paramString;
  }
  
  public String getFirm_tariffId()
  {
    return this.firm_tariffId;
  }
  
  public void setFirm_tariffId(String paramString)
  {
    this.firm_tariffId = paramString;
  }
}
