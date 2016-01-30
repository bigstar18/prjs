package gnnt.MEBS.billWarehoursInterface.VO;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;

public class PropertyMap
  implements Serializable
{
  private static final long serialVersionUID = -4059848094859479589L;
  private String name;
  private String value;
  
  @XmlElement(name="NAME")
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @XmlElement(name="VALUE")
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
}
