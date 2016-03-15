package cn.com.pingan.b2bic.web;

import java.util.List;

public class SignVo
{
  private String signMode;
  private String certDn;
  private List<String> signTradeCode;
  
  public String getSignMode()
  {
    return this.signMode;
  }
  
  public void setSignMode(String signMode)
  {
    this.signMode = signMode;
  }
  
  public String getCertDn()
  {
    return this.certDn;
  }
  
  public void setCertDn(String certDn)
  {
    this.certDn = certDn;
  }
  
  public List<String> getSignTradeCode()
  {
    return this.signTradeCode;
  }
  
  public void setSignTradeCode(List<String> signTradeCode)
  {
    this.signTradeCode = signTradeCode;
  }
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("signMode=").append(this.signMode).append("certDn=")
      .append(this.certDn).append(", ");
    sb.append("signTradeCode=").append(this.signTradeCode);
    return sb.toString();
  }
}
