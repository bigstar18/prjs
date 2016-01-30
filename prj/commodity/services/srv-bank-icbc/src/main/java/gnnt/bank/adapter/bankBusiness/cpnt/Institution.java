package gnnt.bank.adapter.bankBusiness.cpnt;

import javax.xml.bind.annotation.XmlElement;

public class Institution
{

  @XmlElement
  public String InstId = "";

  @XmlElement
  public String InstNm = "";

  @XmlElement
  public String BrchId = "";

  @XmlElement
  public String BrchNm = "";

  @XmlElement
  public String SubBrchId = "";

  @XmlElement
  public String SubBrchNm = "";
}