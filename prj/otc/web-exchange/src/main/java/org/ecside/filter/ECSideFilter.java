package org.ecside.filter;

import gnnt.MEBS.base.util.ThreadStore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecside.common.log.LogHandler;
import org.ecside.core.Preferences;
import org.ecside.core.TableModelUtils;
import org.ecside.core.context.HttpServletRequestContext;
import org.ecside.core.context.ServletInitContext;
import org.ecside.core.context.WebContext;
import org.ecside.easyda.DataAccessModel;
import org.ecside.easyda.DataExportModel;
import org.ecside.easylist.AbstractEasyListModel;
import org.ecside.easylist.EasyDataAccessHandler;
import org.ecside.preferences.TableProperties;
import org.ecside.resource.MimeUtils;
import org.ecside.util.ECSideUtils;
import org.ecside.util.ExceptionUtils;
import org.ecside.util.RequestUtils;
import org.ecside.view.ViewResolver;

public class ECSideFilter
  implements Filter
{
  protected static Log logger = LogFactory.getLog(ECSideFilter.class);
  protected static String encoding = "GBK";
  protected static boolean useEncoding = true;
  protected static boolean useEasyDataAccess = true;
  protected static boolean responseHeadersSetBeforeDoFilter = true;
  public String servletRealPath;
  public ServletContext servletContext;
  protected FilterConfig filterConfig = null;
  protected EasyDataAccessHandler easyDataAccessHandler;
  
  public void init(FilterConfig filterConfig)
    throws ServletException
  {
    this.filterConfig = filterConfig;
    this.servletContext = filterConfig.getServletContext();
    this.servletRealPath = this.servletContext.getRealPath("/");
    
    initEncoding();
    initEasyList();
    initProperties();
    

    String responseHeadersSetBeforeDoFilter = filterConfig.getInitParameter("responseHeadersSetBeforeDoFilter");
    if (StringUtils.isNotBlank(responseHeadersSetBeforeDoFilter)) {
      responseHeadersSetBeforeDoFilter = Boolean.valueOf(responseHeadersSetBeforeDoFilter).booleanValue();
    }
  }
  
  public void initProperties()
    throws ServletException
  {
    WebContext context = new ServletInitContext(this.servletContext);
    Preferences preferences = new TableProperties();
    preferences.init(context, TableModelUtils.getPreferencesLocation(context));
    
    org.ecside.core.ECSideContext.DEFAULT_PAGE_SIZE = new Integer(preferences.getPreference("table.rowsDisplayed")).intValue();
  }
  
  public void initEasyList()
    throws ServletException
  {
    useEasyDataAccess = true;
    String useEasyDataAccessC = this.filterConfig.getInitParameter("useEasyDataAccess");
    if (("off".equalsIgnoreCase(useEasyDataAccessC)) || ("false".equalsIgnoreCase(useEasyDataAccessC)) || 
      ("no".equals(useEasyDataAccessC)) || ("0".equals(useEasyDataAccessC))) {
      useEasyDataAccess = false;
    }
    this.easyDataAccessHandler = new EasyDataAccessHandler();
  }
  
  public void initEncoding()
    throws ServletException
  {
    String encodingValue = this.filterConfig.getInitParameter("encoding");
    String useEncodingC = this.filterConfig.getInitParameter("useEncoding");
    if ((encodingValue != null) && (encodingValue.length() > 0)) {
      encoding = encodingValue;
    }
    if ((useEncodingC == null) || (useEncodingC.equalsIgnoreCase("true")) || 
      (useEncodingC.equalsIgnoreCase("yes")) || (useEncodingC.equalsIgnoreCase("on")) || 
      (useEncodingC.equalsIgnoreCase("1"))) {
      useEncoding = true;
    } else {
      useEncoding = false;
    }
    org.ecside.core.ECSideContext.ENCODING = encoding;
  }
  
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
    throws IOException, ServletException
  {
    HttpServletRequest request = (HttpServletRequest)servletRequest;
    HttpServletResponse response = (HttpServletResponse)servletResponse;
    if (RequestUtils.isAJAXRequest(request))
    {
      doAjaxFilter(request, response, chain);
      return;
    }
    doEncoding(request, response);
    
    String easyListName = getEasyList(request);
    String easyDataAccessName = getEasyDataAccess(request);
    String easyDataExport = getEasyDataExport(request);
    
    WebContext context = new HttpServletRequestContext(request);
    boolean isExported = ExportFilterUtils.isExported(context);
    if (isExported)
    {
      doExportFilter(request, response, chain, context);
      context = null;
      return;
    }
    if (StringUtils.isNotBlank(easyDataExport))
    {
      String sqlName = getSqlName(request);
      String fileName = getExportFileName(request);
      String type = fileName.substring(fileName.indexOf('.') + 1);
      try
      {
        setResponseHeaders(request, response, fileName);
        

        this.easyDataAccessHandler.exportList(getDataExportModelBean(request, easyDataExport), sqlName, type, request, response);
        
        response.getOutputStream().flush();
        response.getOutputStream().close();
      }
      catch (Exception e)
      {
        LogHandler.errorLog(logger, e);
      }
      return;
    }
    if (StringUtils.isNotBlank(easyListName))
    {
      this.easyDataAccessHandler.easyList(request, response, getEasyListModelBean(request, easyListName));
    }
    else if (StringUtils.isNotBlank(easyDataAccessName))
    {
      String sqlName = getSqlName(request);
      this.easyDataAccessHandler.dataAccess(getDataAccessModelBean(request, easyDataAccessName), sqlName, request, response);
    }
    chain.doFilter(request, response);
  }
  
  public void doEncoding(ServletRequest request, ServletResponse response)
    throws IOException, ServletException
  {
    if (((useEncoding) || (request.getCharacterEncoding() == null)) && 
      (encoding != null)) {
      request.setCharacterEncoding(encoding);
    }
  }
  
  public void doAjaxFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    request.setCharacterEncoding("UTF-8");
    

    String easyListName = getEasyList(request);
    String easyDataAccessName = getEasyDataAccess(request);
    
    String findAjaxZoneAtClient = request.getParameter("findAjaxZoneAtClient");
    if ((findAjaxZoneAtClient != null) && (("false".equalsIgnoreCase(findAjaxZoneAtClient)) || ("0".equals(findAjaxZoneAtClient))))
    {
      ECSideAjaxResponseWrapper bufferResponseWrapper = new ECSideAjaxResponseWrapper(response);
      try
      {
        String ectableId = request.getParameter("ec_i");
        if (easyListName != null)
        {
          this.easyDataAccessHandler.easyList(request, bufferResponseWrapper, getEasyListModelBean(request, easyListName));
        }
        else if (easyDataAccessName != null)
        {
          String sqlName = getSqlName(request);
          this.easyDataAccessHandler.dataAccess(getDataAccessModelBean(request, easyDataAccessName), sqlName, request, response);
        }
        HttpServletRequest httpRequest = request;
        HttpServletResponse httpResponse = response;
        String uriStr = httpRequest.getRequestURI();
        CharacterEncodingHttpServletRequestWrapper mrequestw = new CharacterEncodingHttpServletRequestWrapper(request, "GBK");
        chain.doFilter(mrequestw, bufferResponseWrapper);
        

        String a = bufferResponseWrapper.getBuffer();
        


        String zone = bufferResponseWrapper.findSubstring(
          ECSideUtils.getAjaxBegin(ectableId), ECSideUtils.getAjaxEnd(ectableId));
        HttpServletResponse originalResponse = bufferResponseWrapper.getOriginalResponse();
        if (zone != null) {
          originalResponse.getOutputStream().write(zone.getBytes("UTF-8"));
        }
        originalResponse.getOutputStream().flush();
        originalResponse.getOutputStream().close();
      }
      catch (Exception e)
      {
        LogHandler.errorLog(logger, e);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("Exception:<br />\n" + ExceptionUtils.formatStackTrace(e).replaceAll("\n", "<br/>\n"));
        response.getWriter().close();
      }
    }
    else
    {
      if (easyListName != null)
      {
        this.easyDataAccessHandler.easyList(request, response, getEasyListModelBean(request, easyListName));
      }
      else if (easyDataAccessName != null)
      {
        String sqlName = getSqlName(request);
        this.easyDataAccessHandler.dataAccess(getDataAccessModelBean(request, easyDataAccessName), sqlName, request, response);
      }
      chain.doFilter(request, response);
    }
  }
  
  public void doExportFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain, WebContext context)
    throws IOException, ServletException
  {
    String easyListName = getEasyList(request);
    String exportFileName = ExportFilterUtils.getExportFileName(context);
    boolean isPrint = "_print_".equals(exportFileName);
    try
    {
      if (isPrint)
      {
        if (easyListName != null) {
          this.easyDataAccessHandler.easyList(request, response, getEasyListModelBean(request, easyListName));
        }
        chain.doFilter(request, response);
      }
      else
      {
        request.setAttribute("RESPONSE_OUTPUTSTREAM_KEY", response.getOutputStream());
        HttpServletResponseWrapper responseWrapper = new ExportResponseWrapper(response);
        
        setResponseHeaders(request, response, exportFileName);
        if (((ThreadStore.get("exportAll") == null) || (ThreadStore.get("reloadExport") == null)) && 
          (easyListName != null)) {
          this.easyDataAccessHandler.easyList(request, responseWrapper, getEasyListModelBean(request, easyListName));
        }
        chain.doFilter(request, responseWrapper);
        responseWrapper = null;
      }
      if ((ThreadStore.get("exportAll") == null) || (ThreadStore.get("reloadExport") == null)) {
        handleExport(request, response, context);
      }
    }
    catch (IOException e)
    {
      throw e;
    }
  }
  
  public void handleExport(HttpServletRequest request, HttpServletResponse response, WebContext context)
  {
    try
    {
      Object viewData = request.getAttribute("viewData");
      if (viewData != null)
      {
        Preferences preferences = new TableProperties();
        preferences.init(null, TableModelUtils.getPreferencesLocation(context));
        String viewResolver = (String)request.getAttribute("viewResolver");
        Class classDefinition = Class.forName(viewResolver);
        ViewResolver handleExportViewResolver = (ViewResolver)classDefinition.newInstance();
        request.setAttribute("ecs_servletRealPath", this.servletRealPath);
        handleExportViewResolver.resolveView(request, response, preferences, viewData);
        if (!response.isCommitted())
        {
          response.getOutputStream().flush();
          response.getOutputStream().close();
        }
      }
    }
    catch (Exception e)
    {
      LogHandler.errorLog(logger, e);
    }
  }
  
  public void setResponseHeaders(HttpServletRequest request, HttpServletResponse response, String exportFileName)
  {
    String mimeType = MimeUtils.getFileMimeType(exportFileName);
    
    String[] fileNameArray = exportFileName.split("\\.");
    if (fileNameArray.length == 2)
    {
      String rightName = fileNameArray[0];
      if (ThreadStore.get("rightName") != null) {
        rightName = (String)ThreadStore.get("rightName");
      }
      DateFormat df = new SimpleDateFormat(
        "yyyy-MM-dd-HH-mm-ss");
      Date date = new Date();
      String userId = (String)request.getSession().getAttribute("CURRENUSERID");
      exportFileName = rightName + "_" + df.format(date) + "_" + userId + "." + fileNameArray[1];
    }
    if (StringUtils.isNotBlank(mimeType)) {
      response.setContentType(mimeType);
    }
    try
    {
      exportFileName = RequestUtils.encodeFileName(request, exportFileName);
    }
    catch (UnsupportedEncodingException e)
    {
      String errorMessage = "Unsupported response.getCharacterEncoding() [UTF-8].";
      LogHandler.errorLog(logger, "TDExportFilter.setResponseHeaders() - " + errorMessage);
    }
    response.setHeader("Content-Disposition", "attachment;filename=\"" + exportFileName + "\"");
    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
    response.setHeader("Pragma", "public");
    




    response.setDateHeader("Expires", System.currentTimeMillis() + 1000L);
  }
  
  protected void webProxy(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/xml");
    PrintWriter out = response.getWriter();
    URL url = new URL(request.getParameter("url"));
    BufferedReader bf = new BufferedReader(new InputStreamReader(url
      .openStream()));
    String line;
    while ((line = bf.readLine()) != null)
    {
      String line;
      out.println(line);
    }
    bf.close();
  }
  
  public String getEasyList(ServletRequest servletRequest)
  {
    String easyDataAccess = null;
    if (useEasyDataAccess) {
      try
      {
        easyDataAccess = servletRequest.getParameter("easyList");
      }
      catch (Exception e)
      {
        easyDataAccess = null;
      }
    }
    return StringUtils.isNotBlank(easyDataAccess) ? easyDataAccess : null;
  }
  
  public String getEasyDataExport(ServletRequest servletRequest)
  {
    String easyDataAccess = null;
    if (useEasyDataAccess) {
      easyDataAccess = servletRequest.getParameter("easyDataExport");
    }
    if ((StringUtils.isNotBlank(easyDataAccess)) && (easyDataAccess.indexOf('.') > 0)) {
      easyDataAccess = easyDataAccess.substring(0, easyDataAccess.indexOf('.'));
    }
    return StringUtils.isNotBlank(easyDataAccess) ? easyDataAccess : null;
  }
  
  public String getEasyDataAccess(ServletRequest servletRequest)
  {
    String easyDataAccess = null;
    if (useEasyDataAccess) {
      easyDataAccess = servletRequest.getParameter("easyDataAccess");
    }
    if ((StringUtils.isNotBlank(easyDataAccess)) && (easyDataAccess.indexOf('.') > 0)) {
      easyDataAccess = easyDataAccess.substring(0, easyDataAccess.indexOf('.'));
    }
    return StringUtils.isNotBlank(easyDataAccess) ? easyDataAccess : null;
  }
  
  public static String getExportFileName(ServletRequest servletRequest)
  {
    String fileName = servletRequest.getParameter("eFileName");
    if (StringUtils.isBlank(fileName))
    {
      String eda = servletRequest.getParameter("easyDataExport");
      if (eda.indexOf('.') > 0) {
        fileName = eda.substring(eda.indexOf('.') + 1);
      }
    }
    return StringUtils.isNotBlank(fileName) ? fileName : null;
  }
  
  public static String getSqlName(ServletRequest servletRequest)
  {
    String sqlName = servletRequest.getParameter("eSqlName");
    if (StringUtils.isBlank(sqlName))
    {
      String eda = servletRequest.getParameter("easyDataAccess");
      if (StringUtils.isBlank(eda)) {
        eda = servletRequest.getParameter("easyDataExport");
      }
      if ((StringUtils.isNotBlank(eda)) && (eda.indexOf('.') > 0)) {
        sqlName = eda.substring(eda.indexOf('.') + 1);
      }
    }
    return StringUtils.isNotBlank(sqlName) ? sqlName : null;
  }
  
  public AbstractEasyListModel getEasyListModelBean(HttpServletRequest request, String beanName)
  {
    return (AbstractEasyListModel)getBean(request, beanName);
  }
  
  public DataAccessModel getDataAccessModelBean(HttpServletRequest request, String beanName)
  {
    return (DataAccessModel)getBean(request, beanName);
  }
  
  public DataExportModel getDataExportModelBean(HttpServletRequest request, String beanName)
  {
    return (DataExportModel)getBean(request, beanName);
  }
  
  public Object getBean(HttpServletRequest request, String beanName)
  {
    return ExportFilterUtils.getBean(request, beanName);
  }
  
  public void destroy()
  {
    this.filterConfig = null;
  }
}
