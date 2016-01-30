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
import javax.xml.bind.annotation.XmlElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResponseVO
  implements Serializable
{
  private static final long serialVersionUID = 1318639211671277980L;
  protected final transient Log logger = LogFactory.getLog(ResponseVO.class);
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
  
  private Result result = new Result();
  
  @XmlElement(name="RESULT")
  public Result getResult()
  {
    return this.result;
  }
  
  public void setResult(Result result)
  {
    this.result = result;
  }
  
  public ResponseVO getResponseVOFromXml(String xml)
  {
    try
    {
      JAXBContext context = JAXBContext.newInstance(new Class[] { getClass() });
      InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
      Unmarshaller unmarshaller = context.createUnmarshaller();
      
      return (ResponseVO)unmarshaller.unmarshal(inputStream);
    }
    catch (JAXBException e)
    {
      e.printStackTrace();
      this.logger.error("将返回字符串转换为返回包失败；失败原因" + e.getMessage());
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
      this.logger.error("将返回包转换为xml失败；失败原因" + e.getMessage());
    }
    return null;
  }
  
  public static void main(String[] args)
    throws JAXBException, IOException
  {
    RegisterResponseVO responseVO = new RegisterResponseVO();
    
    responseVO.getResult().setReturnCode(12030L);
    responseVO.getResult().setMessage("message");
    
    String xml = responseVO.getXMLFromVO();
    
    System.out.println(xml);
    
    responseVO = (RegisterResponseVO)responseVO.getResponseVOFromXml(xml);
    
    System.out.println(responseVO.getClass());
  }
}
