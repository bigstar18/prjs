package gnnt.MEBS.common.broker.model.translate;

import java.lang.reflect.Field;

public class GetNameByFieldFromThis
  implements IGetNameByField
{
  public String getName(Field paramField)
  {
    return paramField.getName();
  }
}