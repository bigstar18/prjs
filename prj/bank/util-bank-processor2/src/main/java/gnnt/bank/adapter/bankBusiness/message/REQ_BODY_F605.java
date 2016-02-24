package gnnt.bank.adapter.bankBusiness.message;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class REQ_BODY_F605
{
  @XmlElement
  public String opeType;
  @XmlElement
  public String memNum;
  @XmlElement
  public String memName;
  @XmlElement
  public String ecifNum;
  @XmlElement
  public String memType;
  @XmlElement
  public String busiNum;
  @XmlElement
  public String regisName;
  @XmlElement
  public String identiType;
  @XmlElement
  public String identiNum;
  @XmlElement
  public String busiLicense;
  @XmlElement
  public String taxRegisNum;
  @XmlElement
  public String remark;
  @XmlElement
  public String memState;
  @XmlElement
  public List<REQ_BODY_F605_FRAME> frame = new ArrayList();
  
  public void add(REQ_BODY_F605_FRAME frame)
  {
    this.frame.add(frame);
  }
}
