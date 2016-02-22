package gnnt.MEBS.ecsideTags;

import gnnt.MEBS.base.query.hibernate.OrderField;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class TdForEcsideTag
  extends BaseTag
{
  private static final long serialVersionUID = 3174234039143531070L;
  private String columnName;
  private String title;
  
  public String getColumnName()
  {
    return this.columnName;
  }
  
  public void setColumnName(String columnName)
  {
    this.columnName = columnName;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }
  
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
      HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
      PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
      List<OrderField> list = pageInfo.getOrderFields();
      
      String sortById = "sortBy_" + this.columnName.substring(0, this.columnName.indexOf("["));
      String order = "";
      if ((list != null) && (list.size() > 0)) {
        for (OrderField orderField : list)
        {
          String of = orderField.getOrderField();
          boolean orderDesc = orderField.getOrderDesc();
          if (("sortBy_" + of).equals(sortById)) {
            order = orderDesc ? "desc" : "asc";
          }
        }
      }
      String orderClass = "sort" + 
        order.toUpperCase();
      JspWriter out = this.pageContext.getOut();
      out.print("<td id=\"" + 
        sortById + 
        "\" valign=\"middle\" columnName=\"" + 
        this.columnName + 
        "\"  sortable=\"true\"  resizeColWidth=\"true\"  editTemplate=\"ecs_t_input\"  width=\"20%\" class=\"tableHeader  tableResizeableHeader editableColumn\"  onmouseover=\"ECSideUtil.lightHeader(this,'ec');\" onmouseout=\"ECSideUtil.unlightHeader(this,'e');\"  oncontextmenu=\"ECSideUtil.showColmunMenu(event,this,'ec');\" onmouseup=\"ECSideUtil.doSort(event,'" + 
        this.columnName + 
        "','" + 
        order + 
        "','ec');\" title=\"排序 " + 
        this.title + 
        "\">  <span onmousedown=\"ECSideUtil.StartResize(event,this,'ec');\" onmouseup=\"ECSideUtil.EndResize(event);\"  class=\"columnSeparator columnResizeableSeparator\" >&#160;</span> <div class=\"headerTitle\" >" + 
        this.title + 
        "<div id=\"age_css\" class=\"" + 
        orderClass + 
        "\" ></div></div></td> <input type=\"hidden\"  name=\"ec_f_" + 
        this.columnName + 
        "\" /> <input type=\"hidden\"  name=\"ec_s_" + 
        this.columnName + "\" />");
    }
    catch (IOException ex)
    {
      throw new JspTagException("错误");
    }
    return 6;
  }
}
