package gnnt.MEBS.common.services.impl;

import gnnt.MEBS.common.dao.MenuDao;
import gnnt.MEBS.common.model.Menu;
import java.util.List;

public class MenuServiceImpl
{
  private MenuDao menuDao;
  
  public void setMenuDao(MenuDao paramMenuDao)
  {
    this.menuDao = paramMenuDao;
  }
  
  public List getMenuByFilter(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.menuDao.getMenuByFilter(paramInt1, paramInt2, paramInt3);
  }
  
  public Menu getMenuById(long paramLong)
  {
    return this.menuDao.getMenuById(paramLong, -1, 0, 0);
  }
}
