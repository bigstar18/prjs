package gnnt.MEBS.billWarehoursInterface.VO;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;

public class Result
  implements Serializable
{
  private static final long serialVersionUID = -7644922620724755795L;
  private long returnCode;
  private String message;
  
  @XmlElement(name="RETCODE")
  public long getReturnCode()
  {
    return this.returnCode;
  }
  
  public void setReturnCode(long returnCode)
  {
    this.returnCode = returnCode;
  }
  
  @XmlElement(name="MESSAGE")
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
}
