package org.directwebremoting.dwrp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.Container;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.FormField;
import org.directwebremoting.extend.ServerException;
import org.directwebremoting.util.LocalUtil;

public class Batch
{
  private final boolean get;
  private final String scriptSessionId;
  private final String dwrSessionId;
  private final String page;
  private final String windowName;
  private final Map<String, FormField> extraParameters;
  protected static final String THROW = "throw";
  private static final FileUpload UPLOADER;
  
  public Batch(HttpServletRequest request)
    throws ServerException
  {
    this.get = "GET".equals(request.getMethod());
    if (this.get) {
      this.extraParameters = parseGet(request);
    } else {
      this.extraParameters = parsePost(request);
    }
    this.scriptSessionId = extractParameter("scriptSessionId", null);
    if (this.scriptSessionId.contains("/")) {
      this.dwrSessionId = this.scriptSessionId.substring(0, this.scriptSessionId.indexOf('/'));
    } else {
      this.dwrSessionId = "";
    }
    this.page = LocalUtil.urlDecode(extractParameter("page", "throw"));
    this.windowName = extractParameter("windowName", "throw");
  }
  
  public Batch(Map<String, FormField> allParameters, boolean get)
  {
    this.extraParameters = allParameters;
    this.get = get;
    
    this.scriptSessionId = extractParameter("scriptSessionId", null);
    if (this.scriptSessionId.contains("/")) {
      this.dwrSessionId = this.scriptSessionId.substring(0, this.scriptSessionId.indexOf('/'));
    } else {
      this.dwrSessionId = "";
    }
    this.page = extractParameter("page", "throw");
    this.windowName = extractParameter("windowName", "throw");
  }
  
  protected String extractParameter(String paramName, String defaultValue)
  {
    FormField formField = (FormField)this.extraParameters.remove(paramName);
    if (formField != null) {
      return formField.getString();
    }
    if (defaultValue == "throw") {
      throw new IllegalArgumentException("Failed to find parameter: " + paramName);
    }
    return defaultValue;
  }
  
  private Map<String, FormField> parsePost(HttpServletRequest req)
    throws ServerException
  {
    Map<String, FormField> paramMap;
    Map<String, FormField> paramMap;
    if (isMultipartContent(req)) {
      paramMap = UPLOADER.parseRequest(req);
    } else {
      paramMap = parseBasicPost(req);
    }
    if (paramMap.size() == 1) {
      parseBrokenMacPost(paramMap);
    }
    return paramMap;
  }
  
  public static boolean isMultipartContent(HttpServletRequest request)
  {
    if (!"post".equals(request.getMethod().toLowerCase())) {
      return false;
    }
    String contentType = request.getContentType();
    if (contentType == null) {
      return false;
    }
    if (contentType.toLowerCase().startsWith("multipart/")) {
      return true;
    }
    return false;
  }
  
  private static void parseBrokenMacPost(Map<String, FormField> paramMap)
  {
    log.debug("Using Broken Safari POST mode");
    

    Iterator<Map.Entry<String, FormField>> it = paramMap.entrySet().iterator();
    if (!it.hasNext()) {
      throw new IllegalStateException("No entries in non empty map!");
    }
    Map.Entry<String, FormField> entry = (Map.Entry)it.next();
    String key = (String)entry.getKey();
    String value = ((FormField)entry.getValue()).getString();
    String line = key + "=" + value;
    
    StringTokenizer st = new StringTokenizer(line, "\n");
    while (st.hasMoreTokens())
    {
      String part = st.nextToken();
      part = LocalUtil.urlDecode(part);
      
      parsePostLine(part, paramMap);
    }
  }
  
  private static void parsePostLine(String line, Map<String, FormField> paramMap)
  {
    if (line.length() == 0) {
      return;
    }
    int sep = line.indexOf("=");
    if (sep == -1)
    {
      paramMap.put(line, null);
    }
    else
    {
      String key = line.substring(0, sep);
      String value = line.substring(sep + "=".length());
      
      paramMap.put(key, new FormField(value));
    }
  }
  
  private Map<String, FormField> parseGet(HttpServletRequest req)
    throws ServerException
  {
    Map<String, FormField> convertedMap = new HashMap();
    Map<String, String[]> paramMap = req.getParameterMap();
    for (Map.Entry<String, String[]> entry : paramMap.entrySet())
    {
      String key = (String)entry.getKey();
      String[] array = (String[])entry.getValue();
      if (array.length == 1)
      {
        convertedMap.put(key, new FormField(array[0]));
      }
      else
      {
        log.error("Multiple values for key: " + key);
        throw new ServerException("Multiple values for key. See console for more information");
      }
    }
    return convertedMap;
  }
  
  public boolean isGet()
  {
    return this.get;
  }
  
  public String getScriptSessionId()
  {
    return this.scriptSessionId;
  }
  
  public String getDwrSessionId()
  {
    return this.dwrSessionId;
  }
  
  public String getPage()
  {
    return this.page;
  }
  
  public String getWindowName()
  {
    return this.windowName;
  }
  
  public Map<String, FormField> getExtraParameters()
  {
    return this.extraParameters;
  }
  
  static
  {
    Container container = WebContextFactory.get().getContainer();
    UPLOADER = (FileUpload)container.getBean(FileUpload.class);
  }
  
  private static final Log log = LogFactory.getLog(Batch.class);
  
  /* Error */
  private Map<String, FormField> parseBasicPost(HttpServletRequest req)
    throws ServerException
  {
    // Byte code:
    //   0: new 206	java/util/HashMap
    //   3: dup
    //   4: invokespecial 208	java/util/HashMap:<init>	()V
    //   7: astore_2
    //   8: aconst_null
    //   9: astore_3
    //   10: aload_1
    //   11: invokeinterface 209 1 0
    //   16: astore 4
    //   18: aload 4
    //   20: ifnull +29 -> 49
    //   23: new 212	java/io/BufferedReader
    //   26: dup
    //   27: new 214	java/io/InputStreamReader
    //   30: dup
    //   31: aload_1
    //   32: invokeinterface 216 1 0
    //   37: aload 4
    //   39: invokespecial 220	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   42: invokespecial 223	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   45: astore_3
    //   46: goto +24 -> 70
    //   49: new 212	java/io/BufferedReader
    //   52: dup
    //   53: new 214	java/io/InputStreamReader
    //   56: dup
    //   57: aload_1
    //   58: invokeinterface 216 1 0
    //   63: invokespecial 226	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   66: invokespecial 223	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   69: astore_3
    //   70: aload_3
    //   71: invokevirtual 229	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   74: astore 5
    //   76: aload 5
    //   78: ifnonnull +72 -> 150
    //   81: aload_2
    //   82: invokeinterface 232 1 0
    //   87: ifeq +180 -> 267
    //   90: aload_1
    //   91: invokeinterface 236 1 0
    //   96: astore 6
    //   98: goto +39 -> 137
    //   101: aload 6
    //   103: invokeinterface 240 1 0
    //   108: checkcast 76	java/lang/String
    //   111: astore 7
    //   113: aload_2
    //   114: aload 7
    //   116: new 144	org/directwebremoting/extend/FormField
    //   119: dup
    //   120: aload_1
    //   121: aload 7
    //   123: invokeinterface 246 2 0
    //   128: invokespecial 249	org/directwebremoting/extend/FormField:<init>	(Ljava/lang/String;)V
    //   131: invokeinterface 250 3 0
    //   136: pop
    //   137: aload 6
    //   139: invokeinterface 254 1 0
    //   144: ifne -43 -> 101
    //   147: goto +120 -> 267
    //   150: aload 5
    //   152: bipush 38
    //   154: invokevirtual 105	java/lang/String:indexOf	(I)I
    //   157: iconst_m1
    //   158: if_icmpeq +62 -> 220
    //   161: getstatic 54	org/directwebremoting/dwrp/Batch:log	Lorg/apache/commons/logging/Log;
    //   164: ldc_w 257
    //   167: invokeinterface 259 2 0
    //   172: new 265	java/util/StringTokenizer
    //   175: dup
    //   176: aload 5
    //   178: ldc_w 267
    //   181: invokespecial 269	java/util/StringTokenizer:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   184: astore 6
    //   186: goto +23 -> 209
    //   189: aload 6
    //   191: invokevirtual 272	java/util/StringTokenizer:nextToken	()Ljava/lang/String;
    //   194: astore 7
    //   196: aload 7
    //   198: invokestatic 118	org/directwebremoting/util/LocalUtil:urlDecode	(Ljava/lang/String;)Ljava/lang/String;
    //   201: astore 7
    //   203: aload 7
    //   205: aload_2
    //   206: invokestatic 275	org/directwebremoting/dwrp/Batch:parsePostLine	(Ljava/lang/String;Ljava/util/Map;)V
    //   209: aload 6
    //   211: invokevirtual 279	java/util/StringTokenizer:hasMoreTokens	()Z
    //   214: ifne -25 -> 189
    //   217: goto -147 -> 70
    //   220: aload 5
    //   222: aload_2
    //   223: invokestatic 275	org/directwebremoting/dwrp/Batch:parsePostLine	(Ljava/lang/String;Ljava/util/Map;)V
    //   226: goto -156 -> 70
    //   229: astore 4
    //   231: aload 4
    //   233: invokevirtual 282	java/lang/Exception:printStackTrace	()V
    //   236: new 63	org/directwebremoting/extend/ServerException
    //   239: dup
    //   240: ldc_w 287
    //   243: aload 4
    //   245: invokespecial 289	org/directwebremoting/extend/ServerException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   248: athrow
    //   249: astore 8
    //   251: aload_3
    //   252: ifnull +12 -> 264
    //   255: aload_3
    //   256: invokevirtual 292	java/io/BufferedReader:close	()V
    //   259: goto +5 -> 264
    //   262: astore 9
    //   264: aload 8
    //   266: athrow
    //   267: aload_3
    //   268: ifnull +12 -> 280
    //   271: aload_3
    //   272: invokevirtual 292	java/io/BufferedReader:close	()V
    //   275: goto +5 -> 280
    //   278: astore 9
    //   280: aload_2
    //   281: areturn
    // Line number table:
    //   Java source line #188	-> byte code offset #0
    //   Java source line #190	-> byte code offset #8
    //   Java source line #193	-> byte code offset #10
    //   Java source line #194	-> byte code offset #18
    //   Java source line #196	-> byte code offset #23
    //   Java source line #200	-> byte code offset #49
    //   Java source line #205	-> byte code offset #70
    //   Java source line #207	-> byte code offset #76
    //   Java source line #209	-> byte code offset #81
    //   Java source line #214	-> byte code offset #90
    //   Java source line #215	-> byte code offset #98
    //   Java source line #217	-> byte code offset #101
    //   Java source line #218	-> byte code offset #113
    //   Java source line #215	-> byte code offset #137
    //   Java source line #222	-> byte code offset #147
    //   Java source line #225	-> byte code offset #150
    //   Java source line #229	-> byte code offset #161
    //   Java source line #230	-> byte code offset #172
    //   Java source line #231	-> byte code offset #186
    //   Java source line #233	-> byte code offset #189
    //   Java source line #234	-> byte code offset #196
    //   Java source line #236	-> byte code offset #203
    //   Java source line #231	-> byte code offset #209
    //   Java source line #242	-> byte code offset #220
    //   Java source line #203	-> byte code offset #226
    //   Java source line #246	-> byte code offset #229
    //   Java source line #248	-> byte code offset #231
    //   Java source line #249	-> byte code offset #236
    //   Java source line #252	-> byte code offset #249
    //   Java source line #253	-> byte code offset #251
    //   Java source line #257	-> byte code offset #255
    //   Java source line #259	-> byte code offset #262
    //   Java source line #264	-> byte code offset #264
    //   Java source line #253	-> byte code offset #267
    //   Java source line #257	-> byte code offset #271
    //   Java source line #259	-> byte code offset #278
    //   Java source line #265	-> byte code offset #280
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	282	0	this	Batch
    //   0	282	1	req	HttpServletRequest
    //   7	274	2	paramMap	Map<String, FormField>
    //   9	263	3	in	java.io.BufferedReader
    //   16	22	4	charEncoding	String
    //   229	15	4	ex	java.lang.Exception
    //   74	147	5	line	String
    //   96	42	6	en	java.util.Enumeration<String>
    //   184	26	6	st	StringTokenizer
    //   111	11	7	name	String
    //   194	10	7	part	String
    //   249	16	8	localObject	Object
    //   262	1	9	localIOException	java.io.IOException
    //   278	1	9	localIOException1	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   10	229	229	java/lang/Exception
    //   10	249	249	finally
    //   255	259	262	java/io/IOException
    //   271	275	278	java/io/IOException
  }
}
