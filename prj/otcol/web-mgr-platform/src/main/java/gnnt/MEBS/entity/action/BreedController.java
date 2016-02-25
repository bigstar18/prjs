package gnnt.MEBS.entity.action;

import gnnt.MEBS.base.copy.ParamUtil;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.base.query.QueryHelper;
import gnnt.MEBS.entity.model.Breed;
import gnnt.MEBS.entity.model.BreedParameters;
import gnnt.MEBS.entity.model.BreedProperty;
import gnnt.MEBS.entity.model.BreedQuality;
import gnnt.MEBS.entity.model.innerObejct.KeyValue;
import gnnt.MEBS.entity.service.BreedService;
import gnnt.MEBS.entity.util.SysData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

public class BreedController
  extends BaseController
{
  private final transient Log logger = LogFactory.getLog(BreedController.class);
  
  private BreedService getBeanOfBreedService()
  {
    BreedService localBreedService = null;
    synchronized (BreedService.class)
    {
      if (localBreedService == null) {
        localBreedService = (BreedService)SysData.getBean("e_breedService");
      }
    }
    return localBreedService;
  }
  
  public ModelAndView getBreedList(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter list========");
    QueryConditions localQueryConditions = QueryHelper.getQueryConditionsFromRequest(paramHttpServletRequest);
    PageInfo localPageInfo = QueryHelper.getPageInfoFromRequest(paramHttpServletRequest);
    if (localPageInfo == null) {
      localPageInfo = new PageInfo(1, Condition.PAGESIZE, "BreedId", false);
    }
    List localList = getBeanOfBreedService().getTableList(localQueryConditions, localPageInfo);
    Map localMap = QueryHelper.getMapFromRequest(paramHttpServletRequest);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "breed/breedList");
    localModelAndView.addObject("resultList", localList);
    localModelAndView.addObject("pageInfo", localPageInfo);
    localModelAndView.addObject("oldParams", localMap);
    return localModelAndView;
  }
  
  public ModelAndView breedAddForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter breedAddForward========");
    List localList = getBeanOfBreedService().getBreedPropertyList(null, null);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "breed/breedAdd");
    localModelAndView.addObject("propertyList", localList);
    return localModelAndView;
  }
  
  public ModelAndView breedAdd(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter breedAdd========");
    String str = "";
    Breed localBreed = new Breed();
    ParamUtil.bindData(paramHttpServletRequest, localBreed);
    List localList = getBeanOfBreedService().getBreedPropertyList(null, null);
    ArrayList localArrayList1 = null;
    Object localObject2;
    if ((localList != null) && (localList.size() > 0))
    {
      localArrayList1 = new ArrayList();
      for (int i = 0; i < localList.size(); i++)
      {
        localObject1 = new KeyValue();
        ((KeyValue)localObject1).setKey(localList.get(i));
        String[] arrayOfString = paramHttpServletRequest.getParameterValues(((BreedProperty)localList.get(i)).getPropertyKey());
        localObject2 = new ArrayList();
        for (int m = 0; m < arrayOfString.length; m++)
        {
          BreedParameters localBreedParameters = new BreedParameters();
          localBreedParameters.setBreedId(localBreed.getBreedId());
          localBreedParameters.setName(arrayOfString[m]);
          localBreedParameters.setPropertyKey(((BreedProperty)localList.get(i)).getPropertyKey());
          localBreedParameters.setNo(m);
          ((List)localObject2).add(localBreedParameters);
        }
        ((KeyValue)localObject1).setValue(localObject2);
        localArrayList1.add(localObject1);
      }
    }
    localBreed.addParametersList(localArrayList1);
    ArrayList localArrayList2 = new ArrayList();
    Object localObject1 = paramHttpServletRequest.getParameterValues("quality");
    for (int j = 0; j < localObject1.length; j++)
    {
      localObject2 = new BreedQuality();
      ((BreedQuality)localObject2).setBreedId(localBreed.getBreedId());
      ((BreedQuality)localObject2).setQualityName(localObject1[j]);
      ((BreedQuality)localObject2).setNo(j);
      localArrayList2.add(localObject2);
    }
    localBreed.addQualityList(localArrayList2);
    ModelAndView localModelAndView = null;
    try
    {
      int k = getBeanOfBreedService().addBreed(localBreed);
      if (k == -1)
      {
        str = "此品种代码已存在，请重新添加";
        localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "breedController.entity?funcflg=breedAddForward");
      }
      else if (k == -2)
      {
        str = "此品种已存在，请重新添加";
        localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "breedController.entity?funcflg=breedAddForward");
      }
      else
      {
        str = "添加成功";
        localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "breedController.entity?funcflg=getBreedList");
      }
    }
    catch (RuntimeException localRuntimeException)
    {
      str = "品种添加失败，请重新添加！";
      localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "breedController.entity?funcflg=breedAddForward");
      localRuntimeException.printStackTrace();
    }
    setResultMsg(paramHttpServletRequest, str);
    return localModelAndView;
  }
  
  public ModelAndView breedModForward(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter breedModForward========");
    String str = paramHttpServletRequest.getParameter("breedId");
    Breed localBreed = getBeanOfBreedService().getBreedById(str);
    ModelAndView localModelAndView = new ModelAndView(Condition.PATH + "breed/breedMod");
    localModelAndView.addObject("breed", localBreed);
    return localModelAndView;
  }
  
  public ModelAndView breedMod(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter breedAdd========");
    String str = "";
    Breed localBreed = new Breed();
    ParamUtil.bindData(paramHttpServletRequest, localBreed);
    List localList = getBeanOfBreedService().getBreedPropertyList(null, null);
    ArrayList localArrayList1 = null;
    Object localObject2;
    if ((localList != null) && (localList.size() > 0))
    {
      localArrayList1 = new ArrayList();
      for (int i = 0; i < localList.size(); i++)
      {
        localObject1 = new KeyValue();
        ((KeyValue)localObject1).setKey(localList.get(i));
        String[] arrayOfString = paramHttpServletRequest.getParameterValues(((BreedProperty)localList.get(i)).getPropertyKey());
        localObject2 = new ArrayList();
        for (int m = 0; m < arrayOfString.length; m++)
        {
          BreedParameters localBreedParameters = new BreedParameters();
          localBreedParameters.setBreedId(localBreed.getBreedId());
          localBreedParameters.setName(arrayOfString[m]);
          localBreedParameters.setPropertyKey(((BreedProperty)localList.get(i)).getPropertyKey());
          localBreedParameters.setNo(m);
          ((List)localObject2).add(localBreedParameters);
        }
        ((KeyValue)localObject1).setValue(localObject2);
        localArrayList1.add(localObject1);
      }
    }
    localBreed.addParametersList(localArrayList1);
    ArrayList localArrayList2 = new ArrayList();
    Object localObject1 = paramHttpServletRequest.getParameterValues("quality");
    for (int j = 0; j < localObject1.length; j++)
    {
      localObject2 = new BreedQuality();
      ((BreedQuality)localObject2).setBreedId(localBreed.getBreedId());
      ((BreedQuality)localObject2).setQualityName(localObject1[j]);
      ((BreedQuality)localObject2).setNo(j);
      localArrayList2.add(localObject2);
    }
    localBreed.addQualityList(localArrayList2);
    ModelAndView localModelAndView = null;
    try
    {
      int k = getBeanOfBreedService().updateBreed(localBreed);
      if (k == -1)
      {
        str = str = "此品种名称已存在，请修改";
        localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "breedController.entity?funcflg=breedModForward&breedId=" + localBreed.getBreedId());
      }
      else
      {
        str = "修改成功";
        localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "breedController.entity?funcflg=getBreedList");
      }
    }
    catch (RuntimeException localRuntimeException)
    {
      str = "修改品种失败，请重新修改！";
      localRuntimeException.printStackTrace();
      localModelAndView = new ModelAndView("redirect:" + Condition.PATH + "breedController.entity?funcflg=breedModForward&breedId=" + localBreed.getBreedId());
    }
    setResultMsg(paramHttpServletRequest, str);
    return localModelAndView;
  }
  
  public ModelAndView delete(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter del========");
    String[] arrayOfString1 = paramHttpServletRequest.getParameterValues("delCheck");
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    for (String str : arrayOfString1)
    {
      String[] arrayOfString3 = str.split(",");
      i = getBeanOfBreedService().checkBreedById(arrayOfString3[0]);
      if (i == 1) {
        localStringBuffer.append(arrayOfString3[0] + ",");
      }
      if (i == 2) {
        localStringBuffer.append(arrayOfString3[0] + ",");
      }
      if (i == 3) {
        localStringBuffer.append(arrayOfString3[0] + ",");
      }
      if (i == 0) {
        localArrayList.add(Integer.valueOf(Integer.parseInt(arrayOfString3[0])));
      }
    }
    if (localStringBuffer.length() > 0) {
      localStringBuffer.append("与其他数据有关联，不能删除！");
    }
    if (localArrayList.size() > 0)
    {
      for (int j = 0; j < localArrayList.size(); j++) {
        localStringBuffer.append(localArrayList.get(j) + ",");
      }
      try
      {
        getBeanOfBreedService().deleteBreedById(localArrayList);
        localStringBuffer.append("删除成功！成功删除" + localArrayList.size() + "条数据！");
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        localStringBuffer.append("删除失败！");
      }
    }
    setResultMsg(paramHttpServletRequest, localStringBuffer.toString());
    return new ModelAndView("redirect:" + Condition.PATH + "breedController.entity?funcflg=getBreedList");
  }
  
  public ModelAndView synch(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws Exception
  {
    this.logger.debug("enter synch========");
    String[] arrayOfString = paramHttpServletRequest.getParameterValues("delCheck");
    String str = "";
    try
    {
      getBeanOfBreedService().synch(arrayOfString);
      str = "同步成功，共同步" + arrayOfString.length + "条数据";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      str = "同步失败！";
    }
    setResultMsg(paramHttpServletRequest, str);
    return new ModelAndView("redirect:" + Condition.PATH + "breedController.entity?funcflg=getBreedList");
  }
}
