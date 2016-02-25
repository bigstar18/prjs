package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.timebargain.manage.dao.DirectFirmBreedDAO;
import gnnt.MEBS.timebargain.manage.dao.DirectFirmBreedHisDAO;
import gnnt.MEBS.timebargain.manage.model.DirectFirmBreed;
import gnnt.MEBS.timebargain.manage.service.DirectFirmBreedManager;
import java.util.Date;
import java.util.List;

public class DirectFirmBreedManagerImpl
  implements DirectFirmBreedManager
{
  private DirectFirmBreedDAO directFirmBreedDAO;
  private DirectFirmBreedHisDAO directFirmBreedHisDAO;
  
  public DirectFirmBreedHisDAO getDirectFirmBreedHisDAO()
  {
    return this.directFirmBreedHisDAO;
  }
  
  public void setDirectFirmBreedHisDAO(DirectFirmBreedHisDAO paramDirectFirmBreedHisDAO)
  {
    this.directFirmBreedHisDAO = paramDirectFirmBreedHisDAO;
  }
  
  public DirectFirmBreedDAO getDirectFirmBreedDAO()
  {
    return this.directFirmBreedDAO;
  }
  
  public void setDirectFirmBreed(DirectFirmBreedDAO paramDirectFirmBreedDAO)
  {
    this.directFirmBreedDAO = this.directFirmBreedDAO;
  }
  
  public void setDirectFirmBreedDAO(DirectFirmBreedDAO paramDirectFirmBreedDAO)
  {
    this.directFirmBreedDAO = paramDirectFirmBreedDAO;
  }
  
  public List directFirmBreedGet(QueryConditions paramQueryConditions, String paramString)
  {
    List localList = null;
    if ("h".equals(paramString)) {
      localList = this.directFirmBreedHisDAO.directFirmBreedHisList(paramQueryConditions);
    } else if ("e".equals(paramString)) {
      localList = this.directFirmBreedDAO.directFirmBreedGet(paramQueryConditions);
    }
    return localList;
  }
  
  public List<DirectFirmBreed> directFirmBreedList(String paramString)
  {
    List localList = null;
    if ("h".equals(paramString)) {
      localList = this.directFirmBreedHisDAO.directFirmBreedHisList(null);
    } else if ("e".equals(paramString)) {
      localList = this.directFirmBreedDAO.directFirmBreedList();
    }
    return localList;
  }
  
  public List getFirmList(QueryConditions paramQueryConditions)
  {
    List localList = null;
    localList = this.directFirmBreedDAO.getFirmList(paramQueryConditions);
    return localList;
  }
  
  public void directFirmBreedAdd(DirectFirmBreed paramDirectFirmBreed)
  {
    this.directFirmBreedDAO.directFirmBreedSave(paramDirectFirmBreed);
  }
  
  public void directFirmBreedMod(DirectFirmBreed paramDirectFirmBreed1, DirectFirmBreed paramDirectFirmBreed2)
  {
    this.directFirmBreedDAO.directFirmBreedMod(paramDirectFirmBreed1, paramDirectFirmBreed2);
  }
  
  public void directFirmBreedDelete(DirectFirmBreed paramDirectFirmBreed)
  {
    this.directFirmBreedDAO.directFirmBreedDelete(paramDirectFirmBreed);
    this.directFirmBreedHisDAO.directFirmBreedHisInsert(paramDirectFirmBreed);
  }
  
  public List directFirmBreedHisList(QueryConditions paramQueryConditions)
  {
    return this.directFirmBreedHisDAO.directFirmBreedHisList(paramQueryConditions);
  }
  
  public void directFirmBreedHisDelete(DirectFirmBreed paramDirectFirmBreed, Date paramDate)
  {
    this.directFirmBreedHisDAO.dirctFirmBreedHisDelete(paramDirectFirmBreed, paramDate);
  }
}
