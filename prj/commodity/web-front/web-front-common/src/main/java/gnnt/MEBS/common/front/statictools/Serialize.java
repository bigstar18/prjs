package gnnt.MEBS.common.front.statictools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.front.Role;
import gnnt.MEBS.common.front.model.integrated.User;
import gnnt.MEBS.common.front.webFrame.strutsInterceptor.WriteLogInterceptor;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Serialize
{
  private static final transient Log logger = LogFactory.getLog(WriteLogInterceptor.class);
  
  public static String serializeToXml(StandardModel paramStandardModel)
  {
    XStream localXStream = new XStream();
    return localXStream.toXML(paramStandardModel);
  }
  
  public static StandardModel deSerializeFromXml(String paramString)
  {
    try
    {
      XStream localXStream = new XStream(new DomDriver());
      return (StandardModel)localXStream.fromXML(paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      logger.warn("str=" + paramString + ";不能反序列化");
    }
    return null;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    User localUser = new User();
    localUser.setName("name");
    HashSet localHashSet = new HashSet();
    Role localRole = new Role();
    localRole.setName("roleName");
    localHashSet.add(localRole);
    localUser.setRoleSet(localHashSet);
    String str = serializeToXml(localUser);
    System.out.println(str);
  }
}
