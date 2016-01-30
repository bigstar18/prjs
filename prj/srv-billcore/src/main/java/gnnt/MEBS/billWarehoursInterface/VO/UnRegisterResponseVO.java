package gnnt.MEBS.billWarehoursInterface.VO;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GNNT")
public class UnRegisterResponseVO
  extends ResponseVO
{
  private static final long serialVersionUID = 4257612093863245909L;
  
  public UnRegisterResponseVO()
  {
    setProtocolName(ProtocolName.unregister);
  }
}
