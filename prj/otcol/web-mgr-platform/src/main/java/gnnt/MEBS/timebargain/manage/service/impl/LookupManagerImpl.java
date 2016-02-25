package gnnt.MEBS.timebargain.manage.service.impl;

import gnnt.MEBS.timebargain.manage.dao.LookupDAO;
import gnnt.MEBS.timebargain.manage.dao.LookupUseBackDsDAO;
import gnnt.MEBS.timebargain.manage.model.Breed;
import gnnt.MEBS.timebargain.manage.model.LabelValue;
import gnnt.MEBS.timebargain.manage.service.LookupManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LookupManagerImpl
  extends BaseManager
  implements LookupManager
{
  private LookupDAO dao;
  private LookupUseBackDsDAO lookupUseBackDsDAO;
  
  public void setLookupUseBackDsDAO(LookupUseBackDsDAO paramLookupUseBackDsDAO)
  {
    this.lookupUseBackDsDAO = paramLookupUseBackDsDAO;
  }
  
  public void setLookupDAO(LookupDAO paramLookupDAO)
  {
    this.dao = paramLookupDAO;
  }
  
  public List getAllRoles()
    throws Exception
  {
    List localList = this.dao.getRoles();
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < localList.size(); i++)
    {
      Map localMap = (Map)localList.get(i);
      localArrayList.add(new LabelValue((String)localMap.get("rolename"), (String)localMap.get("roleid")));
    }
    return localArrayList;
  }
  
  public List getAllSection(Breed paramBreed)
    throws Exception
  {
    List localList = this.dao.getSections(paramBreed);
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < localList.size(); i++)
    {
      Map localMap = (Map)localList.get(i);
      localArrayList.add(new LabelValue("第" + localMap.get("sectionID").toString() + "节", localMap.get("sectionID").toString()));
    }
    return localArrayList;
  }
  
  public List getAllSectionC(Breed paramBreed)
    throws Exception
  {
    List localList = this.dao.getSectionsC(paramBreed);
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < localList.size(); i++)
    {
      Map localMap = (Map)localList.get(i);
      localArrayList.add(new LabelValue("第" + localMap.get("sectionID").toString() + "节", localMap.get("sectionID").toString()));
    }
    return localArrayList;
  }
  
  private List getNullLabelValue(List paramList)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    if (paramList != null) {
      for (int i = 0; i < paramList.size(); i++)
      {
        Map localMap = (Map)paramList.get(i);
        localArrayList.add(new LabelValue((String)localMap.get("mc"), (String)localMap.get("bm")));
      }
    }
    return localArrayList;
  }
  
  private List getLabelValue(List paramList)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    if (paramList != null)
    {
      if (paramList.size() > 0) {
        localArrayList.add(new LabelValue("", ""));
      }
      for (int i = 0; i < paramList.size(); i++)
      {
        Map localMap = (Map)paramList.get(i);
        localArrayList.add(new LabelValue((String)localMap.get("mc"), (String)localMap.get("bm")));
      }
    }
    return localArrayList;
  }
  
  public List getBmk(String paramString)
    throws Exception
  {
    return getLabelValue(this.dao.getBmk(paramString));
  }
  
  public List getModuleFunc()
    throws Exception
  {
    return this.dao.getModuleFunc();
  }
  
  public List getNullBmk(String paramString)
    throws Exception
  {
    return getNullLabelValue(this.dao.getBmk(paramString));
  }
  
  public List getSelectLabelValueByList(List paramList, String paramString1, String paramString2)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    if (paramList != null)
    {
      if (paramList.size() > 0) {
        localArrayList.add(new LabelValue("", ""));
      }
      for (int i = 0; i < paramList.size(); i++)
      {
        Map localMap = (Map)paramList.get(i);
        localArrayList.add(new LabelValue((String)localMap.get(paramString1), (String)localMap.get(paramString2)));
      }
    }
    return localArrayList;
  }
  
  public List getNullSelectLabelValueByList(List paramList, String paramString1, String paramString2)
    throws Exception
  {
    ArrayList localArrayList = new ArrayList();
    if (paramList != null) {
      for (int i = 0; i < paramList.size(); i++)
      {
        Map localMap = (Map)paramList.get(i);
        localArrayList.add(new LabelValue((String)localMap.get(paramString1), (String)localMap.get(paramString2)));
      }
    }
    return localArrayList;
  }
  
  public List getSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception
  {
    List localList = this.dao.getBmkTj(paramString1, paramString4);
    ArrayList localArrayList = new ArrayList();
    if (localList != null)
    {
      if (localList.size() > 0) {
        localArrayList.add(new LabelValue("", ""));
      }
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap = (Map)localList.get(i);
        localArrayList.add(new LabelValue(localMap.get(paramString2).toString(), localMap.get(paramString3).toString()));
      }
    }
    return localArrayList;
  }
  
  public List getSelectLabelValueByTableDistinctCommodityID(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception
  {
    List localList = this.lookupUseBackDsDAO.getBmkTjDistinctCommodityID(paramString1, paramString4);
    ArrayList localArrayList = new ArrayList();
    if (localList != null)
    {
      if (localList.size() > 0) {
        localArrayList.add(new LabelValue("请选择", "请选择"));
      }
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap = (Map)localList.get(i);
        localArrayList.add(new LabelValue(localMap.get(paramString2).toString(), localMap.get(paramString3).toString()));
      }
    }
    return localArrayList;
  }
  
  public List getSelectLabelValueByTable(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    List localList = this.dao.getBmkTj(paramString1, null);
    ArrayList localArrayList = new ArrayList();
    if (localList != null)
    {
      if (localList.size() > 0) {
        localArrayList.add(new LabelValue("", ""));
      }
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap = (Map)localList.get(i);
        localArrayList.add(new LabelValue((String)localMap.get(paramString2), (String)localMap.get(paramString3)));
      }
    }
    return localArrayList;
  }
  
  public List getSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, short paramShort)
    throws Exception
  {
    return getSelectLabelValueByTable(paramString1, paramString2, paramString3, null, paramShort);
  }
  
  public List getSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, String paramString4, short paramShort)
    throws Exception
  {
    List localList = this.dao.getBmkTj(paramString1, paramString4);
    ArrayList localArrayList = new ArrayList();
    if (localList != null)
    {
      if (localList.size() > 0) {
        localArrayList.add(new LabelValue("", ""));
      }
      int i;
      Map localMap;
      if (paramShort == 1) {
        for (i = 0; i < localList.size(); i++)
        {
          localMap = (Map)localList.get(i);
          localArrayList.add(new LabelValue((String)localMap.get(paramString2), String.valueOf((BigDecimal)localMap.get(paramString3))));
        }
      } else {
        for (i = 0; i < localList.size(); i++)
        {
          localMap = (Map)localList.get(i);
          localArrayList.add(new LabelValue((String)localMap.get(paramString2), (String)localMap.get(paramString3)));
        }
      }
    }
    return localArrayList;
  }
  
  public List getNullSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, String paramString4, short paramShort)
    throws Exception
  {
    List localList = this.dao.getBmkTj(paramString1, paramString4);
    ArrayList localArrayList = new ArrayList();
    if (localList != null)
    {
      int i;
      Map localMap;
      if (paramShort == 1) {
        for (i = 0; i < localList.size(); i++)
        {
          localMap = (Map)localList.get(i);
          localArrayList.add(new LabelValue((String)localMap.get(paramString2), String.valueOf((BigDecimal)localMap.get(paramString3))));
        }
      } else {
        for (i = 0; i < localList.size(); i++)
        {
          localMap = (Map)localList.get(i);
          localArrayList.add(new LabelValue((String)localMap.get(paramString2), (String)localMap.get(paramString3)));
        }
      }
    }
    return localArrayList;
  }
  
  public String getMcByTj(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    return this.dao.getMcByTj(paramString1, paramString2, paramString3);
  }
  
  public List getNullSelectLabelValueByTable(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception
  {
    List localList = this.dao.getBmkTj(paramString1, paramString4);
    ArrayList localArrayList = new ArrayList();
    if (localList != null) {
      for (int i = 0; i < localList.size(); i++)
      {
        Map localMap = (Map)localList.get(i);
        localArrayList.add(new LabelValue((String)localMap.get(paramString2), (String)localMap.get(paramString3)));
      }
    }
    return localArrayList;
  }
}
