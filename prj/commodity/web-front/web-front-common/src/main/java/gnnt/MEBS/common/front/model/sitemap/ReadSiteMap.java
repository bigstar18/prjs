package gnnt.MEBS.common.front.model.sitemap;

import gnnt.MEBS.common.front.statictools.filetools.XMLWork;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class ReadSiteMap
{
  private static volatile ReadSiteMap instance;
  private HttpServletRequest request;
  private Map<String, String> sitemap;
  
  public static ReadSiteMap getInstance(HttpServletRequest paramHttpServletRequest)
  {
    if (instance == null) {
      synchronized (ReadSiteMap.class)
      {
        if (instance == null) {
          instance = new ReadSiteMap(paramHttpServletRequest);
        }
      }
    }
    return instance;
  }
  
  private ReadSiteMap(HttpServletRequest paramHttpServletRequest)
  {
    this.request = paramHttpServletRequest;
    this.sitemap = readSiteMapXml();
  }
  
  public Map<String, String> getSiteMap()
  {
    return this.sitemap;
  }
  
  public void reloadSiteMap()
  {
    this.sitemap = instance.readSiteMapXml();
  }
  
  private Map<String, String> readSiteMapXml()
  {
    String str = getClass().getClassLoader().getResource("").getPath() + "/sitemap.xml";
    try
    {
      str = getClass().getClassLoader().getResource("").toURI().getPath() + "/sitemap.xml";
    }
    catch (URISyntaxException localURISyntaxException)
    {
      localURISyntaxException.printStackTrace();
    }
    SiteMapModel localSiteMapModel = (SiteMapModel)XMLWork.reader(SiteMapModel.class, XMLWork.readXMLFile(str));
    return getSitemap(localSiteMapModel);
  }
  
  private Map<String, String> getSitemap(SiteMapModel paramSiteMapModel)
  {
    HashMap localHashMap = new HashMap();
    if ((paramSiteMapModel != null) && (paramSiteMapModel.siteMapNode != null) && (paramSiteMapModel.siteMapNode.size() > 0))
    {
      Iterator localIterator = paramSiteMapModel.siteMapNode.iterator();
      while (localIterator.hasNext())
      {
        SiteMapNode localSiteMapNode = (SiteMapNode)localIterator.next();
        if ((localSiteMapNode.node != null) && (localSiteMapNode.node.size() > 0))
        {
          localHashMap.putAll(getNodeSiteMap(localSiteMapNode, ""));
        }
        else if ((localSiteMapNode.title != null) && (localSiteMapNode.title.trim().length() > 0))
        {
          String str = localSiteMapNode.title;
          if ((localSiteMapNode.href != null) && (localSiteMapNode.href.trim().length() > 0)) {
            str = "<a href='" + getURL(localSiteMapNode.href) + "'>" + str + "</a>";
          }
          if ((localSiteMapNode.path != null) && (localSiteMapNode.path.trim().length() > 0)) {
            localHashMap.put(localSiteMapNode.path.trim(), str);
          }
        }
      }
    }
    return localHashMap;
  }
  
  private Map<String, String> getNodeSiteMap(SiteMapNode paramSiteMapNode, String paramString)
  {
    HashMap localHashMap = new HashMap();
    String str1 = paramSiteMapNode.path;
    String str2 = paramSiteMapNode.href;
    String str3 = paramSiteMapNode.title;
    if ((str3 == null) || (str3.trim().length() == 0)) {
      return localHashMap;
    }
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      paramString = paramString + "&nbsp;&gt;&nbsp;";
    } else {
      paramString = "";
    }
    if ((str2 != null) && (str2.trim().length() > 0)) {
      paramString = paramString + "<a href='" + getURL(str2) + "'>" + str3 + "</a>";
    } else {
      paramString = paramString + str3;
    }
    if ((str1 != null) && (str1.trim().length() > 0)) {
      localHashMap.put(str1.trim(), paramString);
    }
    if ((paramSiteMapNode.node != null) && (paramSiteMapNode.node.size() > 0))
    {
      Iterator localIterator = paramSiteMapNode.node.iterator();
      while (localIterator.hasNext())
      {
        SiteMapNode localSiteMapNode = (SiteMapNode)localIterator.next();
        localHashMap.putAll(getNodeSiteMap(localSiteMapNode, paramString));
      }
    }
    return localHashMap;
  }
  
  private String getURL(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    String str = "http[s]?://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*";
    Pattern localPattern = Pattern.compile(str);
    Matcher localMatcher = localPattern.matcher(paramString);
    if (localMatcher.matches()) {
      return paramString;
    }
    if ((!paramString.startsWith("/")) && (!paramString.startsWith("\\"))) {
      paramString = paramString + "/";
    }
    return this.request.getContextPath() + paramString;
  }
}
