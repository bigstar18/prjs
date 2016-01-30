package gnnt.MEBS.common.front.vo;

import gnnt.MEBS.common.front.statictools.filetools.XMLWork;
import java.io.PrintStream;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GNNT")
public class CheckUserXML
{
  @XmlElement(name="REQ")
  public CheckUserChild req;
  
  public static void main(String[] paramArrayOfString)
  {
    String str = "<?xml version=\"1.0\" encoding=\"gb2312\"?><GNNT><REQ name=\"check_user\"><USER_ID>xie</USER_ID><SESSION_ID>9137071206884378519</SESSION_ID><MODULE_ID>2</MODULE_ID></REQ></GNNT>";
    CheckUserXML localCheckUserXML = (CheckUserXML)XMLWork.reader(CheckUserXML.class, str);
    System.out.println(localCheckUserXML.req.moduleID);
  }
}
