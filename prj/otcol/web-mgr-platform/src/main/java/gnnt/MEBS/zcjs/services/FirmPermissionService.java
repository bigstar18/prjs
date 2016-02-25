package gnnt.MEBS.zcjs.services;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.FirmPermissionDao;
import gnnt.MEBS.zcjs.model.FirmPermission;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("z_firmPermissionService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class FirmPermissionService
{
  @Autowired
  @Qualifier("z_firmPermissionDao")
  private FirmPermissionDao firmPermissionDao;
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo)
  {
    return this.firmPermissionDao.getTableList(paramQueryConditions, paramPageInfo);
  }
  
  @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
  public FirmPermission getFirmPermissionById(String paramString)
  {
    return this.firmPermissionDao.getFirmPermissionById(paramString);
  }
  
  public void update(FirmPermission paramFirmPermission)
  {
    this.firmPermissionDao.update(paramFirmPermission);
  }
}
