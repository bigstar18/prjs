package gnnt.MEBS.account.model;

import gnnt.MEBS.base.model.Clone;
import java.util.Date;

public class NetCountInZS
  extends Clone
{
  private String customerno;
  private String protocolno;
  private String accountagreement;
  private String riskoverbook;
  private String accountprotocol;
  private String instruction;
  private Date createtime;
  
  public NetCountInZS() {}
  
  public NetCountInZS(String customerno, String protocolno, String accountagreement, String riskoverbook, String accountprotocol, String instruction, Date createtime)
  {
    this.customerno = customerno;
    this.protocolno = protocolno;
    this.accountagreement = accountagreement;
    this.riskoverbook = riskoverbook;
    this.accountprotocol = accountprotocol;
    this.instruction = instruction;
    this.createtime = createtime;
  }
  
  public String getCustomerno()
  {
    return this.customerno;
  }
  
  public void setCustomerno(String customerno)
  {
    this.customerno = customerno;
  }
  
  public String getProtocolno()
  {
    return this.protocolno;
  }
  
  public void setProtocolno(String protocolno)
  {
    this.protocolno = protocolno;
  }
  
  public String getAccountagreement()
  {
    return this.accountagreement;
  }
  
  public void setAccountagreement(String accountagreement)
  {
    this.accountagreement = accountagreement;
  }
  
  public String getRiskoverbook()
  {
    return this.riskoverbook;
  }
  
  public void setRiskoverbook(String riskoverbook)
  {
    this.riskoverbook = riskoverbook;
  }
  
  public String getAccountprotocol()
  {
    return this.accountprotocol;
  }
  
  public void setAccountprotocol(String accountprotocol)
  {
    this.accountprotocol = accountprotocol;
  }
  
  public String getInstruction()
  {
    return this.instruction;
  }
  
  public void setInstruction(String instruction)
  {
    this.instruction = instruction;
  }
  
  public Date getCreatetime()
  {
    return this.createtime;
  }
  
  public void setCreatetime(Date createtime)
  {
    this.createtime = createtime;
  }
  
  public String getId()
  {
    return this.customerno;
  }
  
  public void setPrimary(String primary)
  {
    this.customerno = primary;
  }
}
