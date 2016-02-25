package gnnt.MEBS.bank.front.model;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.util.Date;

public class FeeInfo
  extends StandardModel
{
  private static final long serialVersionUID = -5669018684821401224L;
  @ClassDiscription(name="费用表ID", description="")
  private Long id;
  @ClassDiscription(name="结束金额", description="")
  private Double upLimit;
  @ClassDiscription(name="起始金额", description="")
  private Double downLimit;
  @ClassDiscription(name="计算方式", description="")
  private Integer tmode;
  @ClassDiscription(name="手续费", description="")
  private Double rate;
  @ClassDiscription(name="收费类型", description="")
  private String type;
  @ClassDiscription(name="记录时间", description="")
  private Date createTime;
  @ClassDiscription(name="修改时间", description="")
  private Date updateTime;
  @ClassDiscription(name="用户ID", description="记录人,交易商,银行")
  private String userID;
  @ClassDiscription(name="最大金额", description="")
  private Double maxRateValue;
  @ClassDiscription(name="最小金额", description="")
  private Double minRateValue;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public Double getUpLimit()
  {
    return this.upLimit;
  }
  
  public void setUpLimit(Double upLimit)
  {
    this.upLimit = upLimit;
  }
  
  public Double getDownLimit()
  {
    return this.downLimit;
  }
  
  public void setDownLimit(Double downLimit)
  {
    this.downLimit = downLimit;
  }
  
  public Integer getTmode()
  {
    return this.tmode;
  }
  
  public void setTmode(Integer tmode)
  {
    this.tmode = tmode;
  }
  
  public Double getRate()
  {
    return this.rate;
  }
  
  public void setRate(Double rate)
  {
    this.rate = rate;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public Date getCreateTime()
  {
    return this.createTime;
  }
  
  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }
  
  public Date getUpdateTime()
  {
    return this.updateTime;
  }
  
  public void setUpdateTime(Date updateTime)
  {
    this.updateTime = updateTime;
  }
  
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String userID)
  {
    this.userID = userID;
  }
  
  public Double getMaxRateValue()
  {
    return this.maxRateValue;
  }
  
  public void setMaxRateValue(Double maxRateValue)
  {
    this.maxRateValue = maxRateValue;
  }
  
  public Double getMinRateValue()
  {
    return this.minRateValue;
  }
  
  public void setMinRateValue(Double minRateValue)
  {
    this.minRateValue = minRateValue;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("id", this.id);
  }
}
