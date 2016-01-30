package gnnt.bank.adapter.bankBusiness.cpnt;

import gnnt.bank.adapter.bankBusiness.enumElmt.FileBusinessCode;
import javax.xml.bind.annotation.XmlElement;

public class FileInfo
{

  @XmlElement
  public String BusCode = FileBusinessCode.DETAIL.getValue();

  @XmlElement
  public String BusDate = "";

  @XmlElement
  public String Host = "";

  @XmlElement
  public String FileName = "";

  @XmlElement
  public long FileLen;

  @XmlElement
  public String FileTime = "";

  @XmlElement
  public String FileMac = "";
}