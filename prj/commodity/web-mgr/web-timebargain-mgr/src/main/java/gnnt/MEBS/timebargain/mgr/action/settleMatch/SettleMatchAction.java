package gnnt.MEBS.timebargain.mgr.action.settleMatch;

import gnnt.MEBS.bill.core.service.IKernelService;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.action.ECSideAction;
import gnnt.MEBS.timebargain.mgr.model.settleMatch.SettleMatchM;
import gnnt.MEBS.timebargain.mgr.model.settleMatch.SettlePropsM;
import gnnt.MEBS.timebargain.mgr.service.SettleMatchService;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("settleMatchAction")
@Scope("request")
public class SettleMatchAction extends ECSideAction
{

  @Resource(name="settleMatch_settleTypeMapM")
  private Map<Integer, String> settleMatch_settleTypeMapM;

  @Resource(name="settleMatch_statusMapM")
  private Map<Integer, String> settleMatch_statusMapM;

  @Resource(name="settleMatch_resultMapM")
  private Map<Integer, String> settleMatch_resultMapM;

  @Autowired
  @Qualifier("settleMatchService")
  private SettleMatchService settleMatchService;

  @Autowired
  @Qualifier("billKernelService")
  private IKernelService kernelService;

  public Map<Integer, String> getSettleMatch_settleTypeMapM()
  {
    return this.settleMatch_settleTypeMapM;
  }

  public void setSettleMatch_settleTypeMapM(Map<Integer, String> paramMap)
  {
    this.settleMatch_settleTypeMapM = paramMap;
  }

  public Map<Integer, String> getSettleMatch_statusMapM()
  {
    return this.settleMatch_statusMapM;
  }

  public void setSettleMatch_statusMapM(Map<Integer, String> paramMap)
  {
    this.settleMatch_statusMapM = paramMap;
  }

  public Map<Integer, String> getSettleMatch_resultMapM()
  {
    return this.settleMatch_resultMapM;
  }

  public void setSettleMatch_resultMapM(Map<Integer, String> paramMap)
  {
    this.settleMatch_resultMapM = paramMap;
  }

  public String forwardAddSettleMatch()
    throws Exception
  {
    this.logger.debug("------------forwardAddSettleMatch 添加跳转--------------");
    getCommodityIds();
    this.request.setAttribute("size", Integer.valueOf(0));
    return forwardAdd();
  }

  public void getCommodityIds()
    throws Exception
  {
    String str = "select distinct(commodityid) commodityId from t_settleholdposition where matchStatus!=2 and settleType<>2";
    List localList = getService().getListBySql(str);
    this.request.setAttribute("list", localList);
  }

  public String getBillList()
    throws Exception
  {
    this.logger.debug("------------getBillList 查询仓单信息--------------");
    long l = 0L;
    List localList1 = null;
    Page localPage = null;
    SettleMatchM localSettleMatchM = (SettleMatchM)this.entity;
    String str1 = localSettleMatchM.getCommodityId();
    String str2 = localSettleMatchM.getFirmID_S();
    Long localLong = localSettleMatchM.getQuantity();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String str3 = localSimpleDateFormat.format(localSettleMatchM.getSettleDate());
    this.request.setAttribute("date1", str3);
    String str4 = "select * from t_h_commodity where commodityID = '" + str1 + "' and cleardate=to_date('" + str3 + "','yyyy-MM-dd')";
    List localList2 = getService().getListBySql(str4);
    if ((localList2 == null) || (localList2.size() == 0))
    {
      str4 = "select * from t_commodity where commodityID = '" + str1 + "'";
      localList2 = getService().getListBySql(str4);
    }
    l = Long.parseLong(((Map)localList2.get(0)).get("BREEDID").toString());
    double d1 = Double.parseDouble(((Map)localList2.get(0)).get("CONTRACTFACTOR").toString());
    double d2 = localLong.longValue() * d1;
    String str5 = "select * from T_settleProps  where commodityId = '" + str1 + "'";
    List localList3 = getService().getListBySql(str5, new SettlePropsM());
    HashMap localHashMap = new HashMap();
    SettlePropsM localSettlePropsM = null;
    Object localObject = localList3.iterator();
    while (((Iterator)localObject).hasNext())
    {
      StandardModel localStandardModel = (StandardModel)((Iterator)localObject).next();
      localSettlePropsM = (SettlePropsM)localStandardModel;
      localHashMap.put(localSettlePropsM.getPropertyName(), localSettlePropsM.getPropertyValue());
    }
    localObject = null;
    try
    {
      localObject = this.kernelService.getUnusedStocks(l, localHashMap, str2, d2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    if ((localObject != null) && (((List)localObject).size() > 0))
    {
      String str6 = StringUtils.join((Collection)localObject, ',');
      String str7 = "select bs.stockid stockid,bs.warehouseid warehouseid,tc.breedname breedname,bs.quantity,bs.unit ,round(bs.quantity / tc.contractfactor,2) stockNum,bs.lasttime lasttime from BI_Stock bs,(select t.BreedID BreedID, t.ContractFactor ContractFactor,e.breedname breedname from (" + str4 + ") t, m_breed e  where t.breedId = e.breedId and t.commodityID = '" + str1 + "') tc " + "where tc.breedId = bs.breedId and stockid in (" + str6 + ")";
      localList1 = getService().getListBySql(str7);
      if ((localList1 != null) && (localList1.size() > 0))
        localPage = new Page(1, localList1.size(), localList1.size(), localList1);
    }
    else
    {
      this.request.setAttribute("result", "无任何信息！");
    }
    int i = 0;
    if (localList1 != null)
      i = localList1.size();
    this.request.setAttribute("pageInfo", localPage);
    this.request.setAttribute("size", Integer.valueOf(i));
    getCommodityIds();
    return "success";
  }

  public String addSettleMatch()
    throws Exception
  {
    this.logger.debug("------------addSettleMatch 添加交收配对--------------");
    SettleMatchM localSettleMatchM = (SettleMatchM)this.entity;
    localSettleMatchM.setStatus(Integer.valueOf(0));
    User localUser = (User)this.request.getSession().getAttribute("CurrentUser");
    String str1 = localUser.getUserId();
    int i = -1;
    String str2 = this.request.getParameter("ids");
    str2 = str2.substring(0, str2.length() - 1);
    String[] arrayOfString = str2.split(",");
    i = this.settleMatchService.addSettleMatch(arrayOfString, localSettleMatchM, str1);
    if (i == 1)
    {
      addReturnValue(1, 119901L);
      writeOperateLog(1508, "配对成功", 1, "");
      return "success";
    }
    if (i == -1)
    {
      addReturnValue(1, 150510L);
      writeOperateLog(1508, "配对失败，买方可配对持仓不足", 0, "");
      return "error";
    }
    if (i == -2)
    {
      addReturnValue(1, 150511L);
      writeOperateLog(1508, "配对失败，卖方可配对持仓不足", 0, "");
      return "error";
    }
    if (i == -3)
    {
      addReturnValue(1, 150512L);
      writeOperateLog(1508, "配对失败，冻结仓单失败", 0, "");
      return "error";
    }
    this.request.setAttribute("result", "操作失败！");
    return "error";
  }
}