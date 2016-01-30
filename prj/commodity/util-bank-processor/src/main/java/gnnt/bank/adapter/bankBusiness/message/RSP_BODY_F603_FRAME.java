package gnnt.bank.adapter.bankBusiness.message;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RSP_BODY_F603_FRAME
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @XmlElement
  public String uploadTime;

  @XmlElement
  public String upFileName;

  @XmlElement
  public String upFileSize;

  @XmlElement
  public String upFilePath;

  @XmlElement
  public String logSign;

  @XmlElement
  public String memConfSign;

  @XmlElement
  public String logPath;

  @XmlElement
  public String totalRecord;

  @XmlElement
  public String succRecord;

  @XmlElement
  public String failRecord;

  @XmlElement
  public String result;

  @XmlElement
  public String filed1;

  @XmlElement
  public String filed2;

  @XmlElement
  public String filed3;
}