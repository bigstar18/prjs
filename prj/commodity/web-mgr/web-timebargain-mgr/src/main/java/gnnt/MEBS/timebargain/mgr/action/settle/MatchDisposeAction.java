package gnnt.MEBS.timebargain.mgr.action.settle;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.settle.SettleMatch;
import gnnt.MEBS.timebargain.mgr.service.MatchDisposeService;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("matchDisposeAction")
@Scope("request")
public class MatchDisposeAction extends EcsideAction
{
  private static final long serialVersionUID = 3442001477486139057L;

  @Resource(name="settleMatch_statusMap")
  private Map<Integer, String> settleMatch_statusMap;

  @Resource(name="settleMatch_resultMap")
  private Map<Integer, String> settleMatch_resultMap;

  @Resource(name="settleMatch_isTransferMap")
  private Map<Integer, String> settleMatch_isTransferMap;

  @Autowired
  @Qualifier("matchDisposeService")
  private MatchDisposeService matchDisposeService;

  public String matchDisposeList()
    throws Exception
  {
    this.logger.debug("enter matchDisposeList");

    PageRequest pageRequest = super.getPageRequest(this.request);

    listByLimit(pageRequest);

    return "success";
  }

  public String viewMatchDispose()
    throws Exception
  {
    this.logger.debug("enter viewMatchDispose");

    this.entity = getService().get(this.entity);

    SettleMatch settleMatch = (SettleMatch)this.entity;

    double buyBalance = this.matchDisposeService.getFirmFunds(settleMatch.getFirmID_B());

    double sellBalance = this.matchDisposeService.getFirmFunds(settleMatch.getFirmID_S());

    String operator = this.matchDisposeService.getOperator(settleMatch.getMatchID());

    int priceType = this.matchDisposeService.getPriceType(settleMatch.getCommodityID(), settleMatch.getSettleDate(), settleMatch.getSettleType().intValue());

    String result = "";

    if (settleMatch.getResult().intValue() == 1) {
      result = "1";
    }
    else if (settleMatch.getResult().intValue() == 2) {
      result = "2";
    }
    else if (settleMatch.getResult().intValue() == 3) {
      result = "3";
    }
    else if (settleMatch.getResult().intValue() == 4) {
      result = "4";
    }

    this.request.setAttribute("buyBalance", Double.valueOf(buyBalance));
    this.request.setAttribute("sellBalance", Double.valueOf(sellBalance));
    this.request.setAttribute("operator", operator);
    this.request.setAttribute("priceType", Integer.valueOf(priceType));
    return result;
  }

  public String viewMatch()
    throws Exception
  {
    this.logger.debug("enter viewMatchDispose");
    this.entity = getService().get(this.entity);
    String flag = this.request.getParameter("flag");
    return flag;
  }

  public String viewPrices()
    throws Exception
  {
    int bs_Flag = Integer.parseInt(this.request.getParameter("bs_Flag"));
    String matchID = this.request.getParameter("matchID");
    String sql = "select m.price from T_MatchSettleholdRelevance m,T_SettleHoldPosition s where m.SettleID = s.Id and m.MatchID= '" + matchID + "' and s.BS_Flag=" + bs_Flag;
    List priceList = getService().getListBySql(sql);
    this.request.setAttribute("priceList", priceList);
    Page page = new Page(1, 15, priceList.size(), priceList);
    this.request.setAttribute("pageInfo", page);
    return "success";
  }

  public String updateHL_Amount()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.updateHL_Amount(settleMatch);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "设置升贴水为:" + settleMatch.getHL_Amount() + ",配对ID:" + settleMatch.getMatchID(), 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151303L, new Object[] { "已货权转移" });
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String marginToPayout()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    double thisPayMent = Double.parseDouble(this.request.getParameter("thisPayMent"));
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.marginToPayout(settleMatch, thisPayMent);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "保证金转货款,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent, 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151303L, new Object[] { "已货权转移" });
    } else if (result == 5) {
      addReturnValue(-1, 151304L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String incomePayout()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    double thisPayMent = Double.parseDouble(this.request.getParameter("thisPayMent"));
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.incomePayout(settleMatch, thisPayMent);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "收货款,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent, 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151303L, new Object[] { "已货权转移" });
    } else if (result == 5) {
      addReturnValue(-1, 151304L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String payPayout()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    double thisPayMent = Double.parseDouble(this.request.getParameter("thisPayMent"));
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.payPayout(settleMatch, thisPayMent);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "付货款,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent, 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 5) {
      addReturnValue(-1, 151304L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String settleTransfer()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.settleTransfer(settleMatch);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "货权转移,配对ID:" + settleMatch.getMatchID(), 1, "");
    } else if (result == 0) {
      addReturnValue(-1, 151303L, new Object[] { "未处理" });
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151303L, new Object[] { "已货权转移" });
    } else if (result == 5) {
      addReturnValue(-1, 151305L);
    } else if (result == 6) {
      addReturnValue(-1, 151306L);
    } else if (result == 7) {
      addReturnValue(-1, 151303L, new Object[] { "关联仓单上次配对处理未完成" });
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String settleFinish_agreement()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.settleFinish_agreement(settleMatch);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "货权转移,配对ID:" + settleMatch.getMatchID(), 1, "");
    } else if (result == 0) {
      addReturnValue(-1, 151303L, new Object[] { "未处理" });
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151303L, new Object[] { "未货权转移" });
    } else if (result == 5) {
      addReturnValue(-1, 151303L, new Object[] { "卖方货款处理未完成" });
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String backSMargin()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    double thisPayMent = Double.parseDouble(this.request.getParameter("thisPayMent"));
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.backSMargin(settleMatch, thisPayMent);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "退卖方保证金,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent, 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 5) {
      addReturnValue(-1, 151304L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String payPenaltyToS()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    double thisPayMent = Double.parseDouble(this.request.getParameter("thisPayMent"));
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.payPenaltyToS(settleMatch, thisPayMent);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "付卖方违约金,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent, 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151308L);
    } else if (result == 5) {
      addReturnValue(-1, 151304L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String payPenaltyToB()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    double thisPayMent = Double.parseDouble(this.request.getParameter("thisPayMent"));
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.payPenaltyToB(settleMatch, thisPayMent);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "付买方违约金,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent, 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151308L);
    } else if (result == 5) {
      addReturnValue(-1, 151304L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String takePenaltyFromB()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    double thisPayMent = Double.parseDouble(this.request.getParameter("thisPayMent"));
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.takePenaltyFromB(settleMatch, thisPayMent);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "收买方违约金,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent, 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151308L);
    } else if (result == 5) {
      addReturnValue(-1, 151304L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String takePenaltyFromS()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    double thisPayMent = Double.parseDouble(this.request.getParameter("thisPayMent"));
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.takePenaltyFromS(settleMatch, thisPayMent);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "收卖方违约金,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent, 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151308L);
    } else if (result == 5) {
      addReturnValue(-1, 151304L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String settlePL_B()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    double thisPayMent = Double.parseDouble(this.request.getParameter("thisPayMent"));
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.settlePL_B(settleMatch, thisPayMent);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "买方交收盈亏处理,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent, 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151308L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String settlePL_S()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    double thisPayMent = Double.parseDouble(this.request.getParameter("thisPayMent"));
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.settlePL_S(settleMatch, thisPayMent);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "卖方交收盈亏处理,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent, 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151308L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String settleFinish_default()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    settleMatch.setModifier(user.getUserId());
    settleMatch.setModifyTime(getService().getSysDate());
    result = this.matchDisposeService.settleFinish_default(settleMatch);
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "买方违约处理完成,配对ID:" + settleMatch.getMatchID(), 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 6) {
      addReturnValue(-1, 151307L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String settleCancel()
    throws Exception
  {
    int result = -1;

    SettleMatch settleMatch = (SettleMatch)this.entity;
    User user = (User)this.request.getSession().getAttribute("CurrentUser");
    result = this.matchDisposeService.settleCancel(settleMatch, user.getUserId());
    if (result == 1) {
      addReturnValue(1, 151301L);
      writeOperateLog(1508, "撤销,配对ID:" + settleMatch.getMatchID(), 1, "");
    } else if (result == 2) {
      addReturnValue(-1, 151303L, new Object[] { "已处理完成" });
    } else if (result == 3) {
      addReturnValue(-1, 151303L, new Object[] { "已撤销" });
    } else if (result == 4) {
      addReturnValue(-1, 151303L, new Object[] { "已货权转移" });
    } else if (result == 6) {
      addReturnValue(-1, 151307L);
    } else {
      addReturnValue(-1, 151302L);
    }
    return "success";
  }

  public String settleMatchLogList()
    throws Exception
  {
    this.logger.debug("enter settleMatchLogList");

    PageRequest pageRequest = super.getPageRequest(this.request);

    String matchID = this.request.getParameter("matchID");
    QueryConditions qc = (QueryConditions)pageRequest.getFilters();
    qc.addCondition("primary.matchID", "=", matchID);

    pageRequest.setSortColumns("order by id");

    listByLimit(pageRequest);

    return "success";
  }

  public String billList()
    throws Exception
  {
    this.logger.debug("enter billList");
    String matchID = this.request.getParameter("matchID");
    SettleMatch settleMatch = new SettleMatch();
    settleMatch.setMatchID(matchID);
    settleMatch = (SettleMatch)getService().get(settleMatch);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String settleDate = sdf.format(settleMatch.getSettleDate());

    String sqlCom = "select * from t_h_commodity where commodityID = '" + settleMatch.getCommodityID() + "' and cleardate=to_date('" + settleDate + "','yyyy-MM-dd')";
    List commodityList = getService().getListBySql(sqlCom);
    if ((commodityList == null) || (commodityList.size() == 0)) {
      sqlCom = "select * from t_commodity where commodityID = '" + settleMatch.getCommodityID() + "'";
    }
    String sql = "select bs.stockid stockid,bs.warehouseid warehouseid,tc.breedname breedname,bs.quantity , bs.unit qtyUnit,bs.quantity / tc.contractfactor stockNum,bs.lasttime lasttime from BI_Stock bs,(select t.BreedID BreedID, t.ContractFactor ContractFactor,e.breedname breedname from (" + 
      sqlCom + ")  t, m_breed e  where t.breedId = e.breedId and t.commodityID = '" + settleMatch.getCommodityID() + "') tc " + 
      "where tc.breedId = bs.breedId and stockid in (select billid from T_billFrozen where Operation='" + matchID + "')";
    List billList = getService().getListBySql(sql);
    this.request.setAttribute("billList", billList);
    Page page = new Page(1, 15, billList.size(), billList);
    this.request.setAttribute("pageInfo", page);
    return "success";
  }

  public Map<Integer, String> getSettleMatch_statusMap() {
    return this.settleMatch_statusMap;
  }

  public Map<Integer, String> getSettleMatch_resultMap() {
    return this.settleMatch_resultMap;
  }

  public Map<Integer, String> getSettleMatch_isTransferMap() {
    return this.settleMatch_isTransferMap;
  }

  public MatchDisposeService getMatchDisposeService() {
    return this.matchDisposeService;
  }
}