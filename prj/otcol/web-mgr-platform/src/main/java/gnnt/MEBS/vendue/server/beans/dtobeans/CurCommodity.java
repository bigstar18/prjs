package gnnt.MEBS.vendue.server.beans.dtobeans;

import java.sql.Timestamp;

public class CurCommodity
{
  private Long tradePartition;
  private String code;
  private Long commodityID;
  private Long section;
  private Long lpFlag;
  private Long bargainFlag;
  private Timestamp modifyTime;
  
  public Long getBargainFlag()
  {
    return this.bargainFlag;
  }
  
  public void setBargainFlag(Long paramLong)
  {
    this.bargainFlag = paramLong;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public Long getCommodityID()
  {
    return this.commodityID;
  }
  
  public void setCommodityID(Long paramLong)
  {
    this.commodityID = paramLong;
  }
  
  public Long getLpFlag()
  {
    return this.lpFlag;
  }
  
  public void setLpFlag(Long paramLong)
  {
    this.lpFlag = paramLong;
  }
  
  public Timestamp getModifyTime()
  {
    return this.modifyTime;
  }
  
  public void setModifyTime(Timestamp paramTimestamp)
  {
    this.modifyTime = paramTimestamp;
  }
  
  public Long getSection()
  {
    return this.section;
  }
  
  public void setSection(Long paramLong)
  {
    this.section = paramLong;
  }
  
  public Long getTradePartition()
  {
    return this.tradePartition;
  }
  
  public void setTradePartition(Long paramLong)
  {
    this.tradePartition = paramLong;
  }
}
