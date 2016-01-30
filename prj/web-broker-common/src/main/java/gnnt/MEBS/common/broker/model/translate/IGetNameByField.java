package gnnt.MEBS.common.broker.model.translate;

import java.lang.reflect.Field;

public abstract interface IGetNameByField
{
  public abstract String getName(Field paramField);
}