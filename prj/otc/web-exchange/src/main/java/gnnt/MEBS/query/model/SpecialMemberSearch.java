package gnnt.MEBS.query.model;

import gnnt.MEBS.base.model.Clone;
import java.io.Serializable;

public class SpecialMemberSearch
  extends Clone
{
  private String s_memberNo;
  private String specialMemberName;
  private Double riskMargin;
  private Double specialLossPeak;
  private Double specialLossPeakPro;
  
  public <T extends Serializable> T getId()
  {
    return null;
  }
  
  public String getSpecialMemberName()
  {
    return this.specialMemberName;
  }
  
  public void setSpecialMemberName(String specialMemberName)
  {
    this.specialMemberName = specialMemberName;
  }
  
  public Double getRiskMargin()
  {
    return this.riskMargin;
  }
  
  public void setRiskMargin(Double riskMargin)
  {
    this.riskMargin = riskMargin;
  }
  
  public Double getSpecialLossPeak()
  {
    return this.specialLossPeak;
  }
  
  public void setSpecialLossPeak(Double specialLossPeak)
  {
    this.specialLossPeak = specialLossPeak;
  }
  
  public Double getSpecialLossPeakPro()
  {
    return this.specialLossPeakPro;
  }
  
  public void setSpecialLossPeakPro(Double specialLossPeakPro)
  {
    this.specialLossPeakPro = specialLossPeakPro;
  }
  
  public void setPrimary(String primary) {}
  
  public String getS_memberNo()
  {
    return this.s_memberNo;
  }
  
  public void setS_memberNo(String no)
  {
    this.s_memberNo = no;
  }
}
