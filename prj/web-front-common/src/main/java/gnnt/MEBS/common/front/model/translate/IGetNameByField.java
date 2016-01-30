package gnnt.MEBS.common.front.model.translate;

import java.lang.reflect.Field;

public abstract interface IGetNameByField
{
  public abstract String getName(Field paramField);
}
