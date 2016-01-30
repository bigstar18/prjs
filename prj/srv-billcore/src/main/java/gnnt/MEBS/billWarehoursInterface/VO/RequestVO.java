package gnnt.MEBS.billWarehoursInterface.VO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@XmlRootElement(name="RequestVO")
public class RequestVO
  implements Serializable
{
  private static final long serialVersionUID = 7122395125412906471L;
  protected static final transient Log logger = LogFactory.getLog(RequestVO.class);
  private ProtocolName protocolName;
  
  @XmlAttribute(name="name")
  public ProtocolName getProtocolName()
  {
    return this.protocolName;
  }
  
  public void setProtocolName(ProtocolName protocolName)
  {
    this.protocolName = protocolName;
  }
  
  public static RequestVO getRequestVOFromXml(String xml)
  {
    try
    {
      JAXBContext context = JAXBContext.newInstance(new Class[] { RequestVO.class });
      InputStream inputStream = new ByteArrayInputStream(xml.replaceAll(
        "GNNT", "RequestVO").getBytes());
      Unmarshaller unmarshaller = context.createUnmarshaller();
      
      RequestVO requestVO = (RequestVO)unmarshaller
        .unmarshal(inputStream);
      switch (requestVO.getProtocolName())
      {
      case register: 
        context = JAXBContext.newInstance(new Class[] { RegisterRequestVO.class });
        break;
      case unregister: 
        context = JAXBContext.newInstance(new Class[] { UnRegisterRequestVO.class });
        break;
      }
      inputStream = new ByteArrayInputStream(xml.getBytes());
      unmarshaller = context.createUnmarshaller();
      
      return (RequestVO)unmarshaller.unmarshal(inputStream);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
      logger.error("将请求字符串转换为请求包失败；失败原因" + e.getMessage());
    }
    return null;
  }
  
  public String getXMLFromVO()
  {
    try
    {
      JAXBContext context = JAXBContext.newInstance(new Class[] { getClass() });
      Marshaller ms = context.createMarshaller();
      ms.setProperty("jaxb.encoding", "GBK");
      Writer writer = new StringWriter();
      ms.marshal(this, writer);
      return writer.toString();
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
      logger.error("将请求包转换为xml失败；失败原因" + e.getMessage());
    }
    return null;
  }
  
  public static void main(String[] args)
    throws JAXBException, IOException
  {
    RegisterRequestVO requestVO = new RegisterRequestVO();
    
    requestVO.setCategoryName("category");
    
    String xml = requestVO.getXMLFromVO();
    
    System.out.println(xml);
    
    requestVO = (RegisterRequestVO)getRequestVOFromXml(xml);
    
    System.out.println(requestVO.getClass());
  }
}
