package gnnt.MEBS.commodity.model;

import gnnt.MEBS.base.model.Clone;
import gnnt.MEBS.base.model.inner.ClassDiscription;
import java.math.BigDecimal;

public class StepDictionary
  extends Clone
{
  private String ladderCode;
  private Integer stepNo;
  private BigDecimal stepValue;
  private String note;
  
  public String getId()
  {
    return null;
  }
  
  @ClassDiscription(name="阶梯编号:", key=true, keyWord=true)
  public String getLadderCode()
  {
    return this.ladderCode;
  }
  
  public void setLadderCode(String ladderCode)
  {
    this.ladderCode = ladderCode;
  }
  
  @ClassDiscription(name="阶梯阶号:", key=true, keyWord=true)
  public Integer getStepNo()
  {
    return this.stepNo;
  }
  
  public void setStepNo(Integer stepNo)
  {
    this.stepNo = stepNo;
  }
  
  @ClassDiscription(name="阶梯值:")
  public BigDecimal getStepValue()
  {
    return this.stepValue;
  }
  
  public void setStepValue(BigDecimal stepValue)
  {
    this.stepValue = stepValue;
  }
  
  @ClassDiscription(name="备注:")
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public void setPrimary(String primary) {}
}
