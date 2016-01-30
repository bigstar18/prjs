package gnnt.MEBS.integrated.mgr.action.commoditymanage;

import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.integrated.mgr.model.commodity.BreedProps;
import gnnt.MEBS.integrated.mgr.model.commodity.Category;
import gnnt.MEBS.integrated.mgr.model.commodity.CategoryProperty;
import gnnt.MEBS.integrated.mgr.model.commodity.PropertyType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("categoryPropertyAction")
@Scope("request")
public class CategoryPropertyAction
  extends StandardAction
{
  private static final long serialVersionUID = 1L;
  @Resource(name="categoryPropTypeMap")
  protected Map<Integer, String> categoryPropTypeMap;
  private String categoryId;
  
  public String getCategoryId()
  {
    return this.categoryId;
  }
  
  public void setCategoryId(String paramString)
  {
    this.categoryId = paramString;
  }
  
  public Map<Integer, String> getCategoryPropTypeMap()
  {
    return this.categoryPropTypeMap;
  }
  
  public void setCategoryPropTypeMap(Map<Integer, String> paramMap)
  {
    this.categoryPropTypeMap = paramMap;
  }
  
  public String forwardAddPoperty()
  {
    String str = this.request.getParameter("categoryId");
    this.request.setAttribute("categoryId", str);
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("status", "=", Integer.valueOf(0));
    PageRequest localPageRequest = new PageRequest(1, 100, localQueryConditions, " order by sortNo ");
    Page localPage = getService().getPage(localPageRequest, new PropertyType());
    if (localPage != null) {
      this.request.setAttribute("propertyTypeList", localPage.getResult());
    }
    return "success";
  }
  
  public String addProperty()
  {
    this.logger.debug("enter addProperty");
    CategoryProperty localCategoryProperty = (CategoryProperty)this.entity;
    String str = this.request.getParameter("categoryId");
    Category localCategory = new Category();
    localCategory.setCategoryId(Long.valueOf(Long.parseLong(str)));
    localCategoryProperty.setCategory(localCategory);
    getService().add(localCategoryProperty);
    addReturnValue(1, 119901L);
    writeOperateLog(1071, "添加代码为：" + str + "分类下属性名称为" + localCategoryProperty.getPropertyName() + "的属性", 1, "");
    return "success";
  }
  
  public String updateProperty()
    throws Exception
  {
    this.logger.debug("enter updateProperty");
    getService().update(this.entity);
    CategoryProperty localCategoryProperty = (CategoryProperty)this.entity;
    if ("N".equals(localCategoryProperty.getHasValueDict()))
    {
      PageRequest localPageRequest = getPageRequest(this.request);
      QueryConditions localQueryConditions = new QueryConditions();
      localQueryConditions.addCondition("propertyId", "=", localCategoryProperty.getPropertyId());
      localPageRequest.setFilters(localQueryConditions);
      Page localPage = getService().getPage(localPageRequest, new BreedProps());
      List localList = localPage.getResult();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        StandardModel localStandardModel = (StandardModel)localIterator.next();
        getService().delete(localStandardModel);
      }
    }
    addReturnValue(1, 119902L);
    writeOperateLog(1071, "修改代码为：" + localCategoryProperty.getPropertyId() + ",属性名称为(" + localCategoryProperty.getPropertyName() + ")的属性", 1, "");
    return "success";
  }
  
  public String removeProperty()
  {
    String str1 = this.request.getParameter("categoryId");
    String str2 = this.request.getParameter("ids");
    String[] arrayOfString = str2.split(",");
    Long[] arrayOfLong = new Long[arrayOfString.length];
    int i = 0;
    for (int j = 0; j < arrayOfString.length; j++)
    {
      arrayOfLong[j] = Long.valueOf(Long.parseLong(arrayOfString[j]));
      PageRequest localPageRequest = new PageRequest(" and primary.categoryProperty.propertyId=" + Long.parseLong(arrayOfString[j]));
      Page localPage = getService().getPage(localPageRequest, new BreedProps());
      if ((localPage.getResult() != null) && (localPage.getResult().size() > 0))
      {
        i = 1;
        break;
      }
    }
    if (i != 0)
    {
      addReturnValue(-1, 111603L);
      writeOperateLog(1071, "删除代码为：(" + str2 + ")的属性信息", 0, ApplicationContextInit.getErrorInfo("-1008"));
    }
    else
    {
      getService().deleteBYBulk(this.entity, arrayOfLong);
      addReturnValue(1, 111609L);
      this.categoryId = str1;
      writeOperateLog(1071, "删除代码为：(" + str2 + ")的属性信息", 1, "");
    }
    return "success";
  }
  
  public String viewPropertyById()
    throws Exception
  {
    super.viewById();
    QueryConditions localQueryConditions = new QueryConditions();
    localQueryConditions.addCondition("status", "=", Integer.valueOf(0));
    PageRequest localPageRequest = new PageRequest(1, 100, localQueryConditions, " order by sortNo ");
    Page localPage = getService().getPage(localPageRequest, new PropertyType());
    if (localPage != null) {
      this.request.setAttribute("propertyTypeList", localPage.getResult());
    }
    return "success";
  }
}
