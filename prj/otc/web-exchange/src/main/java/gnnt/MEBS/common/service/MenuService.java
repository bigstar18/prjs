package gnnt.MEBS.common.service;

import gnnt.MEBS.common.dao.MenuDao;
import gnnt.MEBS.common.model.Menu;
import gnnt.MEBS.packaging.dao.BaseDao;
import gnnt.MEBS.packaging.service.BaseService;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("menuService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MenuService
  extends BaseService
{
  private final transient Log logger = LogFactory.getLog(MenuService.class);
  @Autowired
  @Qualifier("menuDao")
  private MenuDao menuDao;
  
  public BaseDao getDao()
  {
    return this.menuDao;
  }
  
  public List getMenuByFilter(int type1, int type2, int visible)
  {
    return this.menuDao.getMenuByFilter(type1, type2, visible);
  }
  
  public Menu getMenuById(long id)
  {
    return this.menuDao.getMenuById(id, -1, 0, 0);
  }
}
