package gnnt.MEBS.common.broker.model.translate;

import java.lang.reflect.Field;

public class GetNameByFieldFromAnnotation
  implements IGetNameByField
{
  public String getName(Field paramField)
  {
    ClassDiscription localClassDiscription = (ClassDiscription)paramField.getAnnotation(ClassDiscription.class);
    String str = "";
    if (localClassDiscription != null)
      str = localClassDiscription.name();
    return str;
  }
}