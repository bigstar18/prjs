package gnnt.MEBS.common.services;

import gnnt.MEBS.common.dao.MenuDao;
import gnnt.MEBS.common.model.Menu;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("menuService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class MenuService
{
  @Autowired
  @Qualifier("menuDao")
  private MenuDao menuDao;
  
  public List getMenuByFilter(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.menuDao.getMenuByFilter(paramInt1, paramInt2, paramInt3);
  }
  
  public Menu getMenuById(long paramLong)
  {
    return this.menuDao.getMenuById(paramLong, -1, 0, 0);
  }
}
