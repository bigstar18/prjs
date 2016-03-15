package cn.com.agree.eteller.generic.tags;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.log4j.Logger;

public class DateTag
  extends SimpleTagSupport
{
  private static final Logger logger = Logger.getLogger(DateTag.class);
  private static SimpleDateFormat srcFmt = new SimpleDateFormat();
  private static SimpleDateFormat destFmt = new SimpleDateFormat();
  private String date;
  private String srcPattern;
  private String destPattern;
  
  public void doTag()
    throws JspException, IOException
  {
    PageContext ctx = (PageContext)getJspContext();
    JspWriter out = ctx.getOut();
    
    srcFmt.applyPattern(this.srcPattern);
    destFmt.applyPattern(this.destPattern);
    try
    {
      out.print(destFmt.format(srcFmt.parse(this.date)));
    }
    catch (ParseException e)
    {
      logger.warn(e);
      e.printStackTrace();
      
      out.print(this.date);
    }
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  public String getSrcPattern()
  {
    return this.srcPattern;
  }
  
  public void setSrcPattern(String srcPattern)
  {
    this.srcPattern = srcPattern;
  }
  
  public String getDestPattern()
  {
    return this.destPattern;
  }
  
  public void setDestPattern(String destPattern)
  {
    this.destPattern = destPattern;
  }
}
