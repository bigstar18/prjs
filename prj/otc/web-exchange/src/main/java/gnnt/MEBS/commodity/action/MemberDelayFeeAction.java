package gnnt.MEBS.commodity.action;

import gnnt.MEBS.account.service.MemberInfoService;
import gnnt.MEBS.base.copy.MapToObject;
import gnnt.MEBS.base.query.hibernate.PageInfo;
import gnnt.MEBS.base.query.hibernate.QueryConditions;
import gnnt.MEBS.base.query.jdbc.QueryHelper;
import gnnt.MEBS.commodity.model.TCDelayFee;
import gnnt.MEBS.commodity.model.vo.TCDelayFeeVO;
import gnnt.MEBS.commodity.service.CommodityService;
import gnnt.MEBS.commodity.service.MemberDelayFeeService;
import gnnt.MEBS.commodity.service.StepDictionaryService;
import gnnt.MEBS.packaging.action.BaseAction;
import gnnt.MEBS.packaging.action.util.EcsideUtil;
import gnnt.MEBS.packaging.service.InService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("memberDelayFeeAction")
@Scope("request")
public class MemberDelayFeeAction
  extends BaseAction
{
  private final transient Log logger = LogFactory.getLog(MemberDelayFeeAction.class);
  @Autowired
  @Qualifier("memberDelayFeeService")
  private MemberDelayFeeService memberDelayFeeService;
  @Autowired
  @Qualifier("memberInfoService")
  private MemberInfoService memberInfoService;
  @Autowired
  @Qualifier("stepDictionaryService")
  private StepDictionaryService stepDictionaryService;
  @Autowired
  @Qualifier("commodityService")
  private CommodityService commodityService;
  @Resource(name="returnOperationMap")
  protected Map<String, String> returnOperationMap;
  @Resource(name="commodityFeeAlgrMap")
  private Map commodityFeeAlgrMap;
  
  public InService getService()
  {
    return null;
  }
  
  public Map getCommodityFeeAlgrMap()
  {
    return this.commodityFeeAlgrMap;
  }
  
  public void forwardAttribute()
  {
    this.logger.debug("enter memeberDelayParameters");
    this.request.setAttribute("returnOperationMap", this.returnOperationMap);
  }
  
  public String list()
  {
    String sortName = this.request.getParameter("sortName") != null ? this.request
      .getParameter("sortName") : "primary.commodityId";
    String sortOrder = this.request.getParameter("sortOrder") != null ? this.request
      .getParameter("sortOrder") : "false";
    Map<String, Object> map = EcsideUtil.getQurey(this.request, sortName, 
      new Boolean(sortOrder).booleanValue());
    PageInfo pageInfo = getPageInfo(map);
    QueryConditions qc = getQueryConditions(map);
    List<TCDelayFeeVO> list = this.memberDelayFeeService.getList(qc, 
      pageInfo);
    this.resultList = new ArrayList();
    if ((list != null) && (list.size() > 0)) {
      for (TCDelayFeeVO tcDelayFeeVO : list)
      {
        Map mapVO = new HashMap();
        mapVO.put("commodityId", tcDelayFeeVO.getCommodityId());
        mapVO.put("memberNo", tcDelayFeeVO.getFirmId());
        mapVO.put("commodityName", tcDelayFeeVO.getCommodityName());
        mapVO.put("firmName", tcDelayFeeVO.getFirmName());
        mapVO.put("feeAlgr_v", tcDelayFeeVO.getFeeAlgr_v());
        if ((tcDelayFeeVO.getTcDelayFeeList() != null) && 
          (tcDelayFeeVO.getTcDelayFeeList().size() > 0)) {
          for (TCDelayFee delayFee : tcDelayFeeVO.getTcDelayFeeList())
          {
            mapVO.put("stepNO" + delayFee.getStepNo(), delayFee.getDelayFee_v());
            mapVO.put("mkt_stepNO" + delayFee.getStepNo(), delayFee.getMkt_delayFeeRate_v());
          }
        }
        this.resultList.add(mapVO);
      }
    }
    EcsideUtil.setRowAttributes(this.request, pageInfo.getTotalRecords());
    returnBaseMsg(pageInfo);
    int total = this.memberDelayFeeService.dictionaryTotal();
    this.request.setAttribute("total", Integer.valueOf(total));
    List ladderList = this.stepDictionaryService.getList(null, null);
    this.request.setAttribute("ladderList", ladderList);
    this.request.setAttribute("commodityFeeAlgrMap", this.commodityFeeAlgrMap);
    return getReturnValue();
  }
  
  public String viewById()
  {
    this.logger.debug("enter viewById");
    String firmId = this.request.getParameter("obj.firmId");
    String commodityId = this.request.getParameter("obj.commodityId");
    TCDelayFeeVO tcDelayFeeVo = new TCDelayFeeVO();
    tcDelayFeeVo.setCommodityId(commodityId);
    tcDelayFeeVo.setFirmId(firmId);
    tcDelayFeeVo = this.memberDelayFeeService.get(tcDelayFeeVo);
    int total = this.memberDelayFeeService.dictionaryTotal();
    this.obj = tcDelayFeeVo;
    Map mapVO = new HashMap();
    mapVO.put("commodityId", tcDelayFeeVo.getCommodityId());
    mapVO.put("commodityName", tcDelayFeeVo.getCommodityName());
    mapVO.put("firmName", tcDelayFeeVo.getFirmName());
    mapVO.put("firmId", tcDelayFeeVo.getFirmId());
    mapVO.put("feeAlgr_v", tcDelayFeeVo.getFeeAlgr_v());
    mapVO.put("operate", "P");
    if ((tcDelayFeeVo.getTcDelayFeeList() != null) && 
      (tcDelayFeeVo.getTcDelayFeeList().size() > 0)) {
      for (TCDelayFee delayFee : tcDelayFeeVo.getTcDelayFeeList())
      {
        mapVO.put("stepNO" + delayFee.getStepNo(), 
          delayFee.getDelayFee_v());
        mapVO.put("mkt_stepNO" + delayFee.getStepNo(), 
          delayFee.getMkt_delayFeeRate_v());
      }
    }
    this.request.setAttribute("total", Integer.valueOf(total));
    this.request.setAttribute("tcDelayFeeMap", mapVO);
    List ladderList = this.stepDictionaryService.getList(null, null);
    this.request.setAttribute("ladderList", ladderList);
    this.request.setAttribute("commodityFeeAlgrMap", this.commodityFeeAlgrMap);
    return getReturnValue();
  }
  
  public String update()
  {
    this.logger.debug("enter update");
    

    TCDelayFeeVO tcDelayFeeVo = new TCDelayFeeVO();
    Map map = QueryHelper.getMapFromRequest(this.request, "obj.");
    MapToObject.bindData(map, tcDelayFeeVo);
    String[] delayFees = this.request.getParameterValues("specialForAudit.delayFee_v");
    List<TCDelayFee> list = null;
    if ((delayFees != null) && (delayFees.length > 0))
    {
      list = new ArrayList();
      for (int i = 0; i < delayFees.length; i++)
      {
        TCDelayFee delayFee = new TCDelayFee();
        delayFee.setCommodityId(tcDelayFeeVo.getCommodityId());
        delayFee.setFirmId(tcDelayFeeVo.getFirmId());
        long stepNo = i + 1;
        delayFee.setStepNo(Long.valueOf(stepNo));
        delayFee.setDelayFee_v(new BigDecimal(
          Double.parseDouble(delayFees[i])));
        list.add(delayFee);
      }
    }
    tcDelayFeeVo.setTcDelayFeeList(list);
    this.obj = tcDelayFeeVo;
    int resultValue = this.memberDelayFeeService.update(tcDelayFeeVo);
    addResultMsg(this.request, resultValue);
    return "success";
  }
  
  public String forwardAdd()
  {
    this.logger.debug("enter forwardAdd");
    TCDelayFeeVO tcDelayFeeVo = new TCDelayFeeVO();
    




    int total = this.memberDelayFeeService.dictionaryTotal();
    Map mapVO = new HashMap();
    for (int i = 0; i < total; i++) {
      mapVO.put("stepNO" + i + 1, "");
    }
    this.request.setAttribute("total", Integer.valueOf(total));
    this.request.setAttribute("tcDelayFeeMap", mapVO);
    

    List ladderList = this.stepDictionaryService.getList(null, null);
    this.request.setAttribute("ladderList", ladderList);
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
        String[] primarys = id.split(",");
        TCDelayFee delayFee = new TCDelayFee();
        delayFee.setFirmId(primarys[0]);
        delayFee.setCommodityId(primarys[1]);
        resultValue = this.memberDelayFeeService.delete(delayFee);
      }
    } else {
      resultValue = -2;
    }
    addResultSessionMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  public String updateForAudit()
  {
    TCDelayFeeVO tcDelayFeeVo = new TCDelayFeeVO();
    Map map = QueryHelper.getMapFromRequest(this.request, "obj.");
    MapToObject.bindData(map, tcDelayFeeVo);
    String[] delayFees = this.request.getParameterValues("specialForAudit.delayFee_v");
    List<TCDelayFee> list = null;
    if ((delayFees != null) && (delayFees.length > 0))
    {
      list = new ArrayList();
      for (int i = 0; i < delayFees.length; i++)
      {
        TCDelayFee delayFee = new TCDelayFee();
        delayFee.setCommodityId(tcDelayFeeVo.getCommodityId());
        delayFee.setFirmId(tcDelayFeeVo.getFirmId());
        long stepNo = i + 1;
        delayFee.setStepNo(Long.valueOf(stepNo));
        delayFee.setDelayFee_v(new BigDecimal(
          Double.parseDouble(delayFees[i])));
        list.add(delayFee);
      }
    }
    tcDelayFeeVo.setTcDelayFeeList(list);
    this.obj = tcDelayFeeVo;
    int resultValue = this.memberDelayFeeService.audit(tcDelayFeeVo);
    addResultMsg(this.request, resultValue);
    return getReturnValue();
  }
  
  @PostConstruct
  public void init()
  {
    this.obj = new TCDelayFeeVO();
  }
}
