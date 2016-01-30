package gnnt.MEBS.bill.front.model.stockmanage;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;

public class BiInvoiceinform
  extends StandardModel
{
  private static final long serialVersionUID = -5735576733020375211L;
  private String stockid;
  private String invoicetype;
  private String companyname;
  private String address;
  private String dutyparagraph;
  private String bank;
  private String bankaccount;
  private String name;
  private String phone;
  
  public BiInvoiceinform() {}
  
  public BiInvoiceinform(String stockid)
  {
    this.stockid = stockid;
  }
  
  public BiInvoiceinform(String stockid, String invoicetype, String companyname, String address, String dutyparagraph, String bank, String bankaccount, String name, String phone)
  {
    this.stockid = stockid;
    this.invoicetype = invoicetype;
    this.companyname = companyname;
    this.address = address;
    this.dutyparagraph = dutyparagraph;
    this.bank = bank;
    this.bankaccount = bankaccount;
    this.name = name;
    this.phone = phone;
  }
  
  public String getStockid()
  {
    return this.stockid;
  }
  
  public void setStockid(String stockid)
  {
    this.stockid = stockid;
  }
  
  public String getInvoicetype()
  {
    return this.invoicetype;
  }
  
  public void setInvoicetype(String invoicetype)
  {
    this.invoicetype = invoicetype;
  }
  
  public String getCompanyname()
  {
    return this.companyname;
  }
  
  public void setCompanyname(String companyname)
  {
    this.companyname = companyname;
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String address)
  {
    this.address = address;
  }
  
  public String getDutyparagraph()
  {
    return this.dutyparagraph;
  }
  
  public void setDutyparagraph(String dutyparagraph)
  {
    this.dutyparagraph = dutyparagraph;
  }
  
  public String getBank()
  {
    return this.bank;
  }
  
  public void setBank(String bank)
  {
    this.bank = bank;
  }
  
  public String getBankaccount()
  {
    return this.bankaccount;
  }
  
  public void setBankaccount(String bankaccount)
  {
    this.bankaccount = bankaccount;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public StandardModel.PrimaryInfo fetchPKey()
  {
    return new StandardModel.PrimaryInfo("stockid", this.stockid);
  }
}
