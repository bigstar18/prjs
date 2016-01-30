package gnnt.MEBS.common.front.model.translate;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;

public class GetNameByFieldFromResource
  implements IGetNameByField
{
  public String getName(Field paramField)
  {
    Locale localLocale = Locale.SIMPLIFIED_CHINESE;
    String str1 = paramField.getDeclaringClass().getSimpleName();
    String str2 = "";
    try
    {
      ResourceBundle localResourceBundle = ResourceBundle.getBundle("ApplicationResources", localLocale);
      str2 = localResourceBundle.getString(str1 + "." + paramField.getName());
    }
    catch (Exception localException)
    {
      str2 = "";
    }
    return str2;
  }
}
