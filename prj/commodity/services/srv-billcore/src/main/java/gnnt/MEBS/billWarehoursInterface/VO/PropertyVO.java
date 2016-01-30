package gnnt.MEBS.billWarehoursInterface.VO;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

public class PropertyVO
  implements Serializable
{
  private static final long serialVersionUID = -2368728968064579277L;
  private List<PropertyMap> propertyList;
  
  @XmlElement(name="REC")
  public List<PropertyMap> getPropertyList()
  {
    return this.propertyList;
  }
  
  public void setPropertyList(List<PropertyMap> propertyList)
  {
    this.propertyList = propertyList;
  }
}
