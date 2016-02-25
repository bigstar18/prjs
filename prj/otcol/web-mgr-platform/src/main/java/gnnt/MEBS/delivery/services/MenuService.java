package gnnt.MEBS.delivery.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.delivery.dao.MenuDao;
import gnnt.MEBS.delivery.model.Menu;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("w_menuService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class MenuService
{
  private final transient Log logger = LogFactory.getLog(MenuService.class);
  @Autowired
  @Qualifier("w_menuDao")
  private MenuDao menuDao;
  
  public List<Menu> getMenuList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.menuDao.getMenuList(paramQueryConditions, paramPageInfo);
  }
}
