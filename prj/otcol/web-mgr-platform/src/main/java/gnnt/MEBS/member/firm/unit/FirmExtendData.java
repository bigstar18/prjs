package gnnt.MEBS.member.firm.unit;

import java.util.Map;

public class FirmExtendData
  extends Firm
{
  private String businessLicenseNo;
  private String registeredCapital;
  private String taxRegistrationNo;
  private String organizationCode;
  private String legalRepresentative;
  private String LRphoneNo;
  private String Contacter;
  private String ContacterPhoneNo;
  private String businessContacter;
  private String enterpriseKind;
  private String Period;
  private String initialPassword;
  private String businessScope;
  private String transactionsNo;
  private String accountName;
  private String bankName;
  private String bankProvince;
  private String bankCity;
  private String bankId;
  
  public String getBusinessContacter()
  {
    return (String)this.extendMap.get("businessContacter");
  }
  
  public void setBusinessContacter(String paramString)
  {
    this.extendMap.put("businessContacter", paramString);
  }
  
  public String getBusinessLicenseNo()
  {
    return (String)this.extendMap.get("businessLicenseNo");
  }
  
  public void setBusinessLicenseNo(String paramString)
  {
    this.extendMap.put("businessLicenseNo", paramString);
  }
  
  public String getBusinessScope()
  {
    return (String)this.extendMap.get("businessScope");
  }
  
  public void setBusinessScope(String paramString)
  {
    this.extendMap.put("businessScope", paramString);
  }
  
  public String getContacter()
  {
    return (String)this.extendMap.get("Contacter");
  }
  
  public void setContacter(String paramString)
  {
    this.extendMap.put("Contacter", paramString);
  }
  
  public String getContacterPhoneNo()
  {
    return (String)this.extendMap.get("ContacterPhoneNo");
  }
  
  public void setContacterPhoneNo(String paramString)
  {
    this.extendMap.put("ContacterPhoneNo", paramString);
  }
  
  public String getEnterpriseKind()
  {
    return (String)this.extendMap.get("enterpriseKind");
  }
  
  public void setEnterpriseKind(String paramString)
  {
    this.extendMap.put("enterpriseKind", paramString);
  }
  
  public String getInitialPassword()
  {
    return (String)this.extendMap.get("initialPassword");
  }
  
  public void setInitialPassword(String paramString)
  {
    this.extendMap.put("initialPassword", paramString);
  }
  
  public String getLegalRepresentative()
  {
    return (String)this.extendMap.get("legalRepresentative");
  }
  
  public void setLegalRepresentative(String paramString)
  {
    this.extendMap.put("legalRepresentative", paramString);
  }
  
  public String getLRphoneNo()
  {
    return (String)this.extendMap.get("LRphoneNo");
  }
  
  public void setLRphoneNo(String paramString)
  {
    this.extendMap.put("LRphoneNo", paramString);
  }
  
  public String getOrganizationCode()
  {
    return (String)this.extendMap.get("organizationCode");
  }
  
  public void setOrganizationCode(String paramString)
  {
    this.extendMap.put("organizationCode", paramString);
  }
  
  public String getRegisteredCapital()
  {
    return (String)this.extendMap.get("registeredCapital");
  }
  
  public void setRegisteredCapital(String paramString)
  {
    this.extendMap.put("registeredCapital", paramString);
  }
  
  public String getTaxRegistrationNo()
  {
    return (String)this.extendMap.get("taxRegistrationNo");
  }
  
  public void setTaxRegistrationNo(String paramString)
  {
    this.extendMap.put("taxRegistrationNo", paramString);
  }
  
  public String getTransactionsNo()
  {
    return (String)this.extendMap.get("transactionsNo");
  }
  
  public void setTransactionsNo(String paramString)
  {
    this.extendMap.put("transactionsNo", paramString);
  }
  
  public String getAccountName()
  {
    return (String)this.extendMap.get("accountName");
  }
  
  public void setAccountName(String paramString)
  {
    this.extendMap.put("accountName", paramString);
  }
  
  public String getBankName()
  {
    return (String)this.extendMap.get("bankName");
  }
  
  public void setBankName(String paramString)
  {
    this.extendMap.put("bankName", paramString);
  }
  
  public String getBankProvince()
  {
    return (String)this.extendMap.get("bankProvince");
  }
  
  public void setBankProvince(String paramString)
  {
    this.extendMap.put("bankProvince", paramString);
  }
  
  public String getBankCity()
  {
    return (String)this.extendMap.get("bankCity");
  }
  
  public void setBankCity(String paramString)
  {
    this.extendMap.put("bankCity", paramString);
  }
  
  public String getBankId()
  {
    return (String)this.extendMap.get("bankId");
  }
  
  public void setBankId(String paramString)
  {
    this.extendMap.put("bankId", paramString);
  }
}
