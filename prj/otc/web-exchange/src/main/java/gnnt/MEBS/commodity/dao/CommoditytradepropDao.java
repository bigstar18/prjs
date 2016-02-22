package gnnt.MEBS.commodity.dao;

import gnnt.MEBS.commodity.model.Commoditytradeprop;
import gnnt.MEBS.packaging.dao.BaseDao;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("commoditytradepropDao")
public class CommoditytradepropDao
  extends BaseDao
{
  public Class getEntityClass()
  {
    return new Commoditytradeprop().getClass();
  }
  
  public List<Commoditytradeprop> getList(int sectionid)
  {
    String hql = "from Commoditytradeprop as bla where bla.id.sectionid=" + sectionid;
    return super.getListByHql(null, null, hql);
  }
  
  public void save(Commoditytradeprop a)
  {
    super.add(a);
  }
  
  public void del(Commoditytradeprop a)
  {
    super.delete(a);
  }
}
