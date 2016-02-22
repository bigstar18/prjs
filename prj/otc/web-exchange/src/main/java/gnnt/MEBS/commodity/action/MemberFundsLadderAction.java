package gnnt.MEBS.commodity.action;

import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.util.ThreadStore;
import gnnt.MEBS.commodity.model.FundsLadder;
import gnnt.MEBS.commodity.model.StepDictionary;
import gnnt.MEBS.commodity.model.vo.FundsLadderVO;
import gnnt.MEBS.commodity.service.MemberFundsLadderService;
import gnnt.MEBS.commodity.service.StepDictionaryService;
import gnnt.MEBS.common.security.AclCtrl;
import gnnt.MEBS.config.constant.ActionConstant;
import gnnt.MEBS.config.constant.ThreadStoreConstant;
import gnnt.MEBS.globalLog.model.OperateLog;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class MemberFundsLadderAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberFundsLadderAction.class);
  @Autowired
  @Qualifier("memberFundsLadderService")
  private MemberFundsLadderService memberFundsLadderService;
  @Autowired
  @Qualifier("stepDictionaryService")
  private StepDictionaryService stepDictionaryService;
  @Resource(name="returnOperationMap")
  protected Map<String, String> returnOperationMap;
  @Resource(name="firmDisMap")
  protected Map<String, String> firmDisMap;
  
  public InService getService()
  {
    return this.memberFundsLadderService;
  }
  
  public String list()
  {
    Map<String, Object> map = EcsideUtil.getQurey(this.request, 
      "primary.memberNo", false);
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions conditions = getQueryConditions(map);
    this.resultList = this.memberFundsLadderService.getFundsLadderList(conditions, 
      pageInfo);
    pageInfo.setTotalRecords(this.resultList.size());
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    int total = this.memberFundsLadderService.dictionaryTotal();
    this.request.setAttribute("total", Integer.valueOf(total));
    List ladderList = this.stepDictionaryService.getFundsList(null, null);
    this.request.setAttribute("ladderList", ladderList);
    return "success";
  }
  
  public String viewById()
  {
    this.logger.debug("enter viewById");
    FundsLadderVO fundsLadderVO = new FundsLadderVO();
    String memberNo = this.request.getParameter("memberNo");
    fundsLadderVO.setMemberNo(memberNo);
    fundsLadderVO
      .setMemberName((String)this.firmDisMap.get(fundsLadderVO.getMemberNo()));
    List<FundsLadder> fundsLadderList = new ArrayList();
    fundsLadderList = this.memberFundsLadderService.getList(new QueryConditions(
      "primary.memberNo", "=", memberNo), new PageInfo(1, 10000, 
      "primary.stepNo", false));
    fundsLadderVO.setFundsLadderList(fundsLadderList);
    List ladderList = this.stepDictionaryService.getFundsList(null, null);
    this.request.setAttribute("ladderList", ladderList);
    this.request.setAttribute("fundsLadderVO", fundsLadderVO);
    this.obj = fundsLadderVO;
    return "success";
  }
  
  public String update()
  {
    this.logger.debug("enter update");
    String memberNo = this.request.getParameter("special.memberNo");
    String[] stepRates = this.request.getParameterValues("special.stepRate_v");
    String memberName = this.request.getParameter("special.memberName");
    List<FundsLadder> list = new ArrayList();
    
    FundsLadderVO fundsLadderVO = new FundsLadderVO();
    fundsLadderVO.setMemberNo(memberNo);
    fundsLadderVO.setMemberName(memberName);
    List<StepDictionary> ladderList = this.stepDictionaryService.getFundsList(
      null, null);
    if ((stepRates != null) && (stepRates.length > 0)) {
      for (int i = 0; i < stepRates.length; i++)
      {
        FundsLadder fundsLadder = new FundsLadder();
        fundsLadder.setMemberNo(memberNo);
        for (int j = 0; j < ladderList.size(); j++)
        {
          long stepNo = ((StepDictionary)ladderList.get(j)).getStepNo().intValue();
          if (stepNo == i + 1) {
            fundsLadder.setStepNo(stepNo);
          }
        }
        fundsLadder.setStepRate_v(new BigDecimal(
          Double.parseDouble(stepRates[i])));
        list.add(fundsLadder);
      }
    }
    fundsLadderVO.setFundsLadderList(list);
    int resultValue = this.memberFundsLadderService.update(list);
    addResultMsg(this.request, resultValue);
    this.obj = fundsLadderVO;
    return "success";
  }
  
  public String forwardAdd()
  {
    FundsLadderVO fundsLadderVO = new FundsLadderVO();
    String memberNo = "";
    fundsLadderVO.setMemberNo(memberNo);
    List<FundsLadder> fundsLadderList = new ArrayList();
    int total = this.memberFundsLadderService.dictionaryTotal();
    for (int i = 0; i < total; i++)
    {
      FundsLadder fundsLadder = new FundsLadder();
      fundsLadder.setStepNo(i + 1);
      fundsLadderList.add(fundsLadder);
    }
    fundsLadderVO.setFundsLadderList(fundsLadderList);
    List ladderList = this.stepDictionaryService.getFundsList(null, null);
    this.request.setAttribute("ladderList", ladderList);
    this.request.setAttribute("fundsLadderVO", fundsLadderVO);
    this.obj = fundsLadderVO;
    return "success";
  }
  
  public String delete()
  {
    this.logger.debug("enter delete");
    String[] ids = this.request.getParameterValues("ids");
    int resultValue = 1;
    if ((ids != null) && (ids.length > 0)) {
      for (String id : ids)
      {
        String[] primarys = id.split(",");
        FundsLadder fundsLadder = new FundsLadder();
        fundsLadder.setMemberNo(id);
        
        OperateLog operateLog = new OperateLog();
        operateLog.setObj(null);
        operateLog.setMark((String)this.request.getSession().getAttribute(ActionConstant.REGISTERID));
        this.logger.debug("enter delete operateLog:" + this.obj);
        operateLog.setOperator(AclCtrl.getLogonID(this.request));
        ThreadStore.put(ThreadStoreConstant.OPERATELOGFORLOG, operateLog);
        resultValue = getService().delete(fundsLadder);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public Map<String, String> getFirmDisMap()
  {
    return this.firmDisMap;
  }
}
