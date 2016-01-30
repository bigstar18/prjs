package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Test
  extends HttpServlet
  implements Servlet
{
  protected void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    String str1 = readXMLFromRequestBody(paramHttpServletRequest);
    Document localDocument = null;
    try
    {
      DocumentBuilder localDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      localDocument = localDocumentBuilder.parse(new ByteArrayInputStream(str1.getBytes()));
    }
    catch (ParserConfigurationException localParserConfigurationException)
    {
      System.out.println(localParserConfigurationException);
    }
    catch (SAXException localSAXException)
    {
      System.out.println(localSAXException);
    }
    String str2 = prepareXMLResponse(localDocument);
    str2 = str2 + "</responses>";
    paramHttpServletResponse.setContentType("text/xml");
    paramHttpServletResponse.getWriter().print(str2);
  }
  
  private String readXMLFromRequestBody(HttpServletRequest paramHttpServletRequest)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      BufferedReader localBufferedReader = paramHttpServletRequest.getReader();
      String str = null;
      while ((str = localBufferedReader.readLine()) != null) {
        localStringBuffer.append(str);
      }
    }
    catch (Exception localException)
    {
      System.out.println("XML读取有误`…" + localException.toString());
    }
    return localStringBuffer.toString();
  }
  
  private String prepareXMLResponse(Document paramDocument)
  {
    NodeList localNodeList = paramDocument.getElementsByTagName("skill");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<responses>");
    for (int i = 0; i < localNodeList.getLength(); i++)
    {
      String str = localNodeList.item(i).getFirstChild().getNodeValue();
      localStringBuffer.append("<response>");
      localStringBuffer.append(str);
      localStringBuffer.append("</response>");
    }
    return localStringBuffer.toString();
  }
}
