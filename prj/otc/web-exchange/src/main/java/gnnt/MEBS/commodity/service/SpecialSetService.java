package gnnt.MEBS.commodity.service;

import gnnt.MEBS.commodity.model.SpecialSet;
import gnnt.MEBS.packaging.service.BaseService;

public abstract class SpecialSetService<A extends SpecialSet>
  extends BaseService<A>
{
  public int update(A obj)
  {
    int num = 0;
    delete(obj);
    if (!"D".equals(obj.getOperate())) {
      add(obj);
    }
    num = 3;
    return num;
  }
}
