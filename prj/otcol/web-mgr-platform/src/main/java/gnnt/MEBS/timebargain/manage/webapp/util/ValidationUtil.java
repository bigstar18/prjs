package gnnt.MEBS.timebargain.manage.webapp.util;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

public class ValidationUtil
{
  public static boolean validateTwoFields(Object paramObject, ValidatorAction paramValidatorAction, Field paramField, ActionMessages paramActionMessages, HttpServletRequest paramHttpServletRequest)
  {
    String str1 = ValidatorUtils.getValueAsString(paramObject, paramField.getProperty());
    String str2 = paramField.getVarValue("secondProperty");
    String str3 = ValidatorUtils.getValueAsString(paramObject, str2);
    if (!GenericValidator.isBlankOrNull(str1)) {
      try
      {
        if (!str1.equals(str3))
        {
          paramActionMessages.add(paramField.getKey(), Resources.getActionMessage(paramHttpServletRequest, paramValidatorAction, paramField));
          return false;
        }
      }
      catch (Exception localException)
      {
        paramActionMessages.add(paramField.getKey(), Resources.getActionMessage(paramHttpServletRequest, paramValidatorAction, paramField));
        return false;
      }
    }
    return true;
  }
}
