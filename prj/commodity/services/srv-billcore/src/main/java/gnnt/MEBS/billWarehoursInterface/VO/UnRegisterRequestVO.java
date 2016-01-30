package gnnt.MEBS.billWarehoursInterface.VO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GNNT")
public class UnRegisterRequestVO
  extends RequestVO
{
  private static final long serialVersionUID = -8232313680918984296L;
  private String stockID;
  private double quantity;
  private String userID;
  private String pwd;
  private String userName;
  
  @XmlElement(name="STOCKID")
  public String getStockID()
  {
    return this.stockID;
  }
  
  public void setStockID(String stockID)
  {
    this.stockID = stockID;
  }
  
  @XmlElement(name="QUANTITY")
  public double getQuantity()
  {
    return this.quantity;
  }
  
  public void setQuantity(double quantity)
  {
    this.quantity = quantity;
  }
  
  @XmlElement(name="USERID")
  public String getUserID()
  {
    return this.userID;
  }
  
  public void setUserID(String userID)
  {
    this.userID = userID;
  }
  
  @XmlElement(name="PWD")
  public String getPwd()
  {
    return this.pwd;
  }
  
  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }
  
  @XmlElement(name="USERNAME")
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public UnRegisterRequestVO()
  {
    setProtocolName(ProtocolName.unregister);
  }
}
