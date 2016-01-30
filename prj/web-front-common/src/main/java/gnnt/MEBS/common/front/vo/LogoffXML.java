package gnnt.MEBS.common.front.vo;

import gnnt.MEBS.common.front.statictools.filetools.XMLWork;
import java.io.PrintStream;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="GNNT")
public class LogoffXML
{
  @XmlElement(name="REQ")
  public LogoffChild req;
  
  public static void main(String[] paramArrayOfString)
  {
    String str = "<?xml version=\"1.0\" encoding=\"gb2312\"?><GNNT><REQ name=\"check_user\"><USER_ID>xie</USER_ID><SESSION_ID>9137071206884378519</SESSION_ID><MODULE_ID>2</MODULE_ID></REQ></GNNT>";
    LogoffXML localLogoffXML = (LogoffXML)XMLWork.reader(LogoffXML.class, str);
    System.out.println(localLogoffXML.req.name);
  }
}
