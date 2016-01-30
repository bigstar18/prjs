package gnnt.MEBS.billWarehoursInterface.VO;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GNNT")
public class RegisterResponseVO
  extends ResponseVO
{
  private static final long serialVersionUID = 9100744743357688415L;
  
  public RegisterResponseVO()
  {
    setProtocolName(ProtocolName.register);
  }
}
