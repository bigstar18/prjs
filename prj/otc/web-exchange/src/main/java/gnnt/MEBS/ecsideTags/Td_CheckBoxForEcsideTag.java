package gnnt.MEBS.ecsideTags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class Td_CheckBoxForEcsideTag
  extends BaseTag
{
  private static final long serialVersionUID = 3174234039143531071L;
  
  public int doStartTag()
    throws JspException
  {
    return 1;
  }
  
  public int doEndTag()
    throws JspException
  {
    try
    {
      JspWriter out = this.pageContext.getOut();
      out.print("<td valign='middle'  columnName='checkboxId'  width='44'  class='tableHeader' onmouseover='ECSideUtil.lightHeader(this,\"ec\");'  onmouseout='ECSideUtil.unlightHeader(this,\"ec\");' ><span class='columnSeparator' >&#160;</span><div class='headerTitle' ><span class='checkboxHeader'  onclick='ECSideUtil.checkAll(this,\"checkboxId\",\"ec\");' >&#160;</span></div></td>");
    }
    catch (IOException ex)
    {
      throw new JspTagException("错误");
    }
    return 6;
  }
}
