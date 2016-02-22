package gnnt.MEBS.commodity.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.commodity.model.StepDictionary;
import gnnt.MEBS.commodity.service.StepDictionaryService;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class StepDictionaryAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(StepDictionaryAction.class);
  @Autowired
  @Qualifier("stepDictionaryService")
  private StepDictionaryService stepDictionaryService;
  
  public InService getService()
  {
    return this.stepDictionaryService;
  }
  
  public String forwardAdd()
  {
    List<StepDictionary> list = this.stepDictionaryService.getList(null, null);
    this.request.setAttribute("stepDicList", list);
    return getReturnValue();
  }
  
  public String forwardAddFunds()
  {
    List<StepDictionary> list = this.stepDictionaryService.getFundsList(null, null);
    this.request.setAttribute("stepDicList", list);
    return getReturnValue();
  }
  
  public String delete()
  {
    this.logger.debug("enter delete");
    String[] ids = this.request.getParameterValues("ids");
    int resultValue = 1;
    if ((ids != null) && (ids.length > 0)) {
      for (String id : ids)
      {
        String[] ids1 = id.split(",");
        StepDictionary ste = new StepDictionary();
        ste.setLadderCode(ids1[0]);
        ste.setStepNo(Integer.valueOf(Integer.parseInt(ids1[1])));
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(ste);
        this.logger.debug("enter delete operateLog:" + this.obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        StepDictionary obj = (StepDictionary)getService().get(ste);
        resultValue = getService().delete(obj);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String fundsList()
  {
    this.logger.debug("enter fundsList");
    Map<String, Object> map = EcsideUtil.getQurey(this.request, "stepNo", false);
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    this.resultList = this.stepDictionaryService.getFundsList(qc, pageInfo);
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    return getReturnValue();
  }
}
