package gnnt.MEBS.finance.mgr.model;

import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.StandardModel.PrimaryInfo;
import java.util.Date;

public class FattachExplain extends StandardModel
{
  private static final long serialVersionUID = -4665243860975756668L;
  private String attachCode;
  private Long attachType;
  private Double happenMoney;
  private String attachExplain;
  private Date happenDate;
  private FattachExplainType fattachExplainType;

  public String getAttachCode()
  {
    return this.attachCode;
  }

  public void setAttachCode(String paramString)
  {
    this.attachCode = paramString;
  }

  public Double getHappenMoney()
  {
    return this.happenMoney;
  }

  public void setHappenMoney(Double paramDouble)
  {
    this.happenMoney = paramDouble;
  }

  public String getAttachExplain()
  {
    return this.attachExplain;
  }

  public void setAttachExplain(String paramString)
  {
    this.attachExplain = paramString;
  }

  public Date getHappenDate()
  {
    return this.happenDate;
  }

  public void setHappenDate(Date paramDate)
  {
    this.happenDate = paramDate;
  }

  public FattachExplainType getFattachExplainType()
  {
    return this.fattachExplainType;
  }

  public void setFattachExplainType(FattachExplainType paramFattachExplainType)
  {
    this.fattachExplainType = paramFattachExplainType;
  }

  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo( "attachCode", this.attachCode);
  }

  public Long getAttachType()
  {
    return this.attachType;
  }

  public void setAttachType(Long paramLong)
  {
    this.attachType = paramLong;
  }
}