package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import gnnt.MEBS.config.constant.NumberDigits;
import java.math.BigDecimal;

public class FundsLadder
  extends Clone
{
  private String memberNo;
  private Long stepNo;
  private BigDecimal stepRate;
  
  @ClassDiscription(name="会员交易商Id", key=true, keyWord=true)
  public String getMemberNo()
  {
    return this.memberNo;
  }
  
  public void setMemberNo(String memberNo)
  {
    this.memberNo = memberNo;
  }
  
  @ClassDiscription(name="阶梯阶号", key=true, keyWord=true)
  public Long getStepNo()
  {
    return this.stepNo;
  }
  
  public void setStepNo(long stepNo)
  {
    this.stepNo = Long.valueOf(stepNo);
  }
  
  @ClassDiscription(name="出金阈值比例")
  public String getStepRate_log()
  {
    return formatDecimals(getStepRate().multiply(new BigDecimal(100)), NumberDigits.STEPRATE - 2) + "%";
  }
  
  public BigDecimal getStepRate()
  {
    return formatDecimals(this.stepRate, NumberDigits.STEPRATE);
  }
  
  public void setStepRate(BigDecimal stepRate)
  {
    this.stepRate = stepRate;
  }
  
  public BigDecimal getStepRate_v()
  {
    return formatDecimals(getStepRate().multiply(new BigDecimal(100)), NumberDigits.STEPRATE - 2);
  }
  
  public void setStepRate_v(BigDecimal stepRate_v)
  {
    this.stepRate = stepRate_v.divide(new BigDecimal(100));
  }
  
  public String getId()
  {
    return null;
  }
  
  public void setPrimary(String primary) {}
}
