package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import gnnt.MEBS.timebargain.tradeweb.model.ResponseResult;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobileServlet
  extends HttpXmlServlet
{
  private static final long serialVersionUID = 3906934490856239410L;
  private final Log log = LogFactory.getLog(MobileServlet.class);
  
  public void execute(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException
  {
    ResponseResult localResponseResult = new ResponseResult();
    String str1 = null;
    String str2 = null;
    LogUserAction localLogUserAction = new LogUserAction(this.taken);
    OrderAction localOrderAction = new OrderAction();
    TradeAction localTradeAction = new TradeAction(this.taken);
    QueryAction localQueryAction = new QueryAction();
    try
    {
      str2 = readXMLFromRequestBody(paramHttpServletRequest);
      str1 = getReqNameByXml(str2);
      if (this.language == null)
      {
        this.language = getValueByTagName(str2, "LA");
        international(this.language);
      }
    }
    catch (Exception localException)
    {
      this.log.error("Servlet出错:" + localException);
      errorException(localException);
      localException.printStackTrace();
      localResponseResult.setRetCode(-203);
      localResponseResult.setMessage(this.properties.getProperty("-203"));
    }
    if (str1 == null) {
      str1 = "";
    }
    localResponseResult.setName(str1);
    if (str1.equals("userlogin"))
    {
      localLogUserAction.logon(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("userlogoff"))
    {
      localLogUserAction.logoff(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("change_password"))
    {
      localLogUserAction.change_password(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("querycommodity"))
    {
      localLogUserAction.commodity_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("ordersubmit"))
    {
      localOrderAction.order(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("ordercancel"))
    {
      localOrderAction.order_wd(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("querytrade"))
    {
      localTradeAction.tradequery(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("firm_info"))
    {
      localLogUserAction.firm_info(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("sys_time_query"))
    {
      localLogUserAction.sys_time_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("my_order_query"))
    {
      localOrderAction.my_order_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("my_weekorder_query"))
    {
      localOrderAction.my_weekorder_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("holding_query"))
    {
      localQueryAction.holding_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("market_query"))
    {
      localQueryAction.market_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("commodity_data_query"))
    {
      localQueryAction.commodity_data_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("check_user"))
    {
      localLogUserAction.check_user(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else if (str1.equals("directfirmbreed_query"))
    {
      localQueryAction.directfirmbreed_query(paramHttpServletRequest, paramHttpServletResponse, str2, localResponseResult, "mobile");
    }
    else
    {
      if ((localResponseResult.getMessage() == null) || (localResponseResult.getMessage().equals("")))
      {
        localResponseResult.setRetCode(-203);
        localResponseResult.setMessage(this.properties.getProperty("-211"));
      }
      renderXML(paramHttpServletResponse, ResponseXml.responseXml("", localResponseResult.getRetCode(), localResponseResult.getMessage()));
    }
  }
  
  protected void renderXML(HttpServletResponse paramHttpServletResponse, String paramString)
  {
    try
    {
      paramString = paramString.replaceAll("GNNT", "MEBS_MOBILE");
      System.out.println(paramString);
      paramHttpServletResponse.setContentType("text/xml;charset=GBK");
      paramHttpServletResponse.getWriter().print(paramString);
    }
    catch (IOException localIOException)
    {
      this.log.error(localIOException.getMessage(), localIOException);
    }
  }
}
