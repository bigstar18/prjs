package gnnt.MEBS.timebargain.manage.webapp.form;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.ValidatorForm;

public class BaseForm
  extends ValidatorForm
  implements Serializable
{
  private static final long serialVersionUID = 3257005453799404851L;
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  public boolean equals(Object paramObject)
  {
    return EqualsBuilder.reflectionEquals(this, paramObject);
  }
  
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this);
  }
  
  public ActionErrors validate(ActionMapping paramActionMapping, HttpServletRequest paramHttpServletRequest)
  {
    String str1 = paramActionMapping.getParameter();
    if (str1 != null)
    {
      String str2 = paramHttpServletRequest.getParameter(str1);
      MessageResources localMessageResources = (MessageResources)paramHttpServletRequest.getAttribute("org.apache.struts.action.MESSAGE");
      String str3 = localMessageResources.getMessage("button.cancel");
      String str4 = localMessageResources.getMessage("button.delete");
      if ((str2 != null) && ((str2.equalsIgnoreCase(str3)) || (str2.equalsIgnoreCase(str4)))) {
        return null;
      }
    }
    return super.validate(paramActionMapping, paramHttpServletRequest);
  }
}
