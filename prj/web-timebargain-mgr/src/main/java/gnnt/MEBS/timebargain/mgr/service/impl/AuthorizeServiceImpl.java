package gnnt.MEBS.timebargain.mgr.service.impl;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.dao.AuthorizeDao;
import gnnt.MEBS.timebargain.mgr.model.authorize.AgentTrader;
import gnnt.MEBS.timebargain.mgr.model.authorize.OverdueHoldPosition;
import gnnt.MEBS.timebargain.mgr.service.AuthorizeService;
import gnnt.MEBS.timebargain.server.model.Order;
import gnnt.MEBS.timebargain.server.rmi.TradeRMI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("authorizeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
public class AuthorizeServiceImpl extends StandardService
  implements AuthorizeService
{

  @Autowired
  @Qualifier("authorizeDao")
  private AuthorizeDao authorizeDao;

  @Autowired
  @Qualifier("TradeRMI")
  private TradeRMI tradeRMI;

  public String getOperateFirm(String agentTraderId)
  {
    AgentTrader at = new AgentTrader();
    at.setAgentTraderId(agentTraderId);

    AgentTrader agentTrader = (AgentTrader)get(at);

    String firmIds = "";
    if (agentTrader != null)
    {
      if ((agentTrader.getOperateFirm() != null) && (agentTrader.getOperateFirm().trim().length() > 0)) {
        String[] firms = agentTrader.getOperateFirm().split(",");
        for (int i = 0; i < firms.length; i++) {
          firmIds = firmIds + "'" + firms[i] + "',";
        }
        firmIds = firmIds.substring(0, firmIds.length() - 1);
      }
    }

    return firmIds;
  }

  public String[] getOperateFirms(String agentTraderId)
  {
    AgentTrader at = new AgentTrader();
    at.setAgentTraderId(agentTraderId);

    AgentTrader agentTrader = (AgentTrader)get(at);

    String[] firmIds = null;
    if (agentTrader != null)
    {
      if ((agentTrader.getOperateFirm() != null) && (agentTrader.getOperateFirm().trim().length() > 0)) {
        firmIds = agentTrader.getOperateFirm().split(",");
      }
    }

    return firmIds;
  }

  public List holdPositionsList(String firmIds)
  {
    return this.authorizeDao.holdPositionsList(firmIds);
  }

  public long getHoldQty(OverdueHoldPosition obj)
  {
    long holdqty = 0L;

    long specHoldqty = this.authorizeDao.getSpecHoldqty(obj);
    long notradeqty = this.authorizeDao.getNotradeqty(obj);

    holdqty = obj.getHoldQty().longValue() - specHoldqty - notradeqty;

    return holdqty;
  }

  public int insertOrder(Long sessionID, Order ov)
    throws Exception
  {
    return this.tradeRMI.consignerOrder(sessionID.longValue(), ov);
  }

  public int insertCloseOrder(Long sessionID, Order ov)
    throws Exception
  {
    int ret = 0;

    OverdueHoldPosition oh = new OverdueHoldPosition();
    oh.setFirmID(ov.getFirmID());
    oh.setCustomerID(ov.getCustomerID());
    oh.setCommodityID(ov.getCommodityID());

    String bS_Flag = ov.getBuyOrSell().toString();
    String relBS_Flag = "";
    if ("1".equals(bS_Flag)) {
      relBS_Flag = "2";
    }
    if ("2".equals(bS_Flag)) {
      relBS_Flag = "1";
    }
    oh.setBSFlag(Long.valueOf(Long.parseLong(relBS_Flag)));

    OverdueHoldPosition overdueHoldPosition = (OverdueHoldPosition)get(oh);

    long holdQty = getHoldQty(overdueHoldPosition);

    long closeQty = holdQty <= overdueHoldPosition.getMaycloseQty().longValue() ? holdQty : overdueHoldPosition.getMaycloseQty().longValue();

    if (ov.getQuantity().longValue() > closeQty)
      ret = -500;
    else {
      ret = this.tradeRMI.consignerOrder(sessionID.longValue(), ov);
    }

    return ret;
  }

  public String resultOrder(int ret)
  {
    String result = "";

    switch (ret) {
    case 0:
      break;
    case 1:
      result = "校验异常！";
      break;
    case 2:
      result = "市场未启用！";
      break;
    case 3:
      result = "不是交易时间！";
      break;
    case 4:
      result = "不是代为委托员交易时间！";
      break;
    case 5:
      result = "交易员和代为委托员不能同时存在！";
      break;
    case 10:
      result = "商品处于禁止交易状态！";
      break;
    case 11:
      result = "商品不属于当前交易节！";
      break;
    case 12:
      result = "委托价格超出涨幅上限！";
      break;
    case 13:
      result = "委托价格低于跌幅下限！";
      break;
    case 14:
      result = "委托价格不在此商品最小价位变动范围内！";
      break;
    case 15:
      result = "不存在此商品！";
      break;
    case 16:
      result = "委托数量不在此商品最小变动数量范围内！";
      break;
    case 30:
      result = "此交易员不存在！";
      break;
    case 31:
      result = "此交易员没有操作该交易商的权限！";
      break;
    case 32:
      result = "此交易商不存在！";
      break;
    case 33:
      result = "此交易商为禁止交易状态！";
      break;
    case 34:
      result = "此交易商不存在！";
      break;
    case 35:
      result = "此交易商为禁止交易状态！";
      break;
    case 37:
      result = "此代为委托员没有操作该交易商的权限！";
      break;
    case 38:
      result = "此代为委托员不存在！";
      break;
    case 40:
      result = "计算交易保证金错误！";
      break;
    case 41:
      result = "计算交易手续费错误！";
      break;
    case 42:
      result = "此委托已成交或已撤单！";
      break;
    case 199:
      result = "通信故障！";
      break;
    case 200:
      result = "代码异常而失败！";
      break;
    case -1:
      result = "资金余额不足！";
      break;
    case -2:
      result = "超过交易商对此商品的最大订货量！";
      break;
    case -3:
      result = "超过交易商总的最大订货量！";
      break;
    case -4:
      result = "超过品种最大订货量！";
      break;
    case -5:
      result = "超过商品最大订货量！";
      break;
    case -6:
      result = "超过交易商对此商品的最大净订货量！";
      break;
    case -7:
      result = "超过单笔最大委托量！";
      break;
    case -11:
      result = "此委托已处于正在撤单状态！";
      break;
    case -12:
      result = "此委托已全部成交了！";
      break;
    case -13:
      result = "此委托已完成撤单了！";
      break;
    case -21:
      result = "订货不足！";
      break;
    case -22:
      result = "指定仓不足！";
      break;
    case -99:
      result = "执行存储时未找到相关数据！";
      break;
    case -100:
      result = "执行存储失败！";
      break;
    case -204:
      result = "交易服务器已关闭！";
      break;
    case -206:
      result = "委托信息不能为空！";
      break;
    default:
      result = "委托失败！";
    }

    return result;
  }
}