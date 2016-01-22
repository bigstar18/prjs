package gnnt.bank.adapter.bankBusiness.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class REQ_BODY_F619
{

  @XmlElement
  public String busiNum;

  @XmlElement
  public String uploadTime;

  @XmlElement
  public String upFileName;

  @XmlElement
  public String upFileSize;

  @XmlElement
  public String fileType;

  @XmlElement
  public String fcsSerialNum;

  @XmlElement
  public String filed1;

  @XmlElement
  public String filed2;

  @XmlElement
  public String filed3;
}