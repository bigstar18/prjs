package gnnt.MEBS.timebargain.mgr.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import gnnt.MEBS.bill.core.service.ITradeService;
import gnnt.MEBS.bill.core.vo.DeliveryVO;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.common.mgr.statictools.Arith;
import gnnt.MEBS.timebargain.mgr.dao.MatchDisposeDao;
import gnnt.MEBS.timebargain.mgr.model.settle.SettleMatch;
import gnnt.MEBS.timebargain.mgr.model.settle.SettleMatchFundManage;
import gnnt.MEBS.timebargain.mgr.model.settle.SettleMatchLog;
import gnnt.MEBS.timebargain.mgr.service.MatchDisposeService;

@Service("matchDisposeService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class MatchDisposeServiceImpl implements MatchDisposeService {

	@Autowired
	@Qualifier("matchDisposeDao")
	private MatchDisposeDao matchDisposeDao;

	@Autowired
	@Qualifier("billTradeService")
	private ITradeService tradeService;

	public double getFirmFunds(String firmID) throws Exception {
		double result = 0.0D;

		Object[] params = { firmID, Integer.valueOf(1) };

		Object obj = this.matchDisposeDao.getFirmFunds("{?=call FN_F_GetRealFunds(?,?)}", params);

		result = new Double(obj.toString()).doubleValue();

		return result;
	}

	public String getOperator(String matchID) {
		String operator = "";
		SettleMatchLog settleMatchLog = null;

		List list = this.matchDisposeDao.getSettleMatchLog(matchID);
		if ((list != null) && (list.size() > 0)) {
			settleMatchLog = (SettleMatchLog) list.get(0);
			operator = settleMatchLog.getOperator();
		}

		return operator;
	}

	public int getPriceType(String commodityId, Date clearDate, int settleType) {
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		int priceType = -1;
		if (settleType == 1)
			sql.append("SettlePriceType ");
		else if (settleType == 2)
			sql.append("aheadsettlepricetype ");
		else if (settleType == 3) {
			sql.append("DelaySettlePriceType ");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String clearDate1 = sdf.format(clearDate);
		List list = this.matchDisposeDao.queryBySql(sql.toString() + " priceType from T_H_Commodity where commodityId='" + commodityId
				+ "' and cleardate=to_date('" + clearDate1 + "','yyyy-MM-dd')");
		if ((list == null) || (list.size() == 0)) {
			list = this.matchDisposeDao.queryBySql(sql.toString() + " priceType from T_Commodity where commodityId='" + commodityId + "'");
		}
		Map map = (Map) list.get(0);
		priceType = Integer.parseInt(map.get("PRICETYPE").toString());
		if (settleType == 2) {
			if (priceType == 0)
				priceType = 2;
		} else if ((settleType == 3) && (priceType == 1)) {
			priceType = 2;
		}
		return priceType;
	}

	public int updateHL_Amount(SettleMatch settleMatch) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0)
				|| ((settleMatchDB.getStatus().intValue() == 1) && (settleMatchDB.getIsTransfer().intValue() == 0))) {
			settleMatchDB.setHL_Amount(settleMatch.getHL_Amount());
			settleMatchDB.setModifier(settleMatch.getModifier());
			settleMatchDB.setModifyTime(settleMatch.getModifyTime());

			if (settleMatchDB.getStatus().intValue() == 0) {
				settleMatchDB.setStatus(Integer.valueOf(1));
			}
			this.matchDisposeDao.updateSettleMatch(settleMatchDB);

			SettleMatchLog settleMatchLog = new SettleMatchLog();
			settleMatchLog.setMatchID(settleMatch.getMatchID());
			settleMatchLog.setOperateLog("设置升贴水为:" + settleMatch.getHL_Amount() + ",配对ID:" + settleMatch.getMatchID());
			settleMatchLog.setOperator(settleMatch.getModifier());
			settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
			this.matchDisposeDao.add(settleMatchLog);
			result = 1;
		} else {
			result = settleMatchDB.getStatus().intValue();
			if ((settleMatchDB.getStatus().intValue() == 1) && (settleMatchDB.getIsTransfer().intValue() == 1)) {
				result = 4;
			}
		}
		return result;
	}

	public int marginToPayout(SettleMatch settleMatch, double thisPayMent) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0)
				|| ((settleMatchDB.getStatus().intValue() == 1) && (settleMatchDB.getIsTransfer().intValue() == 0))) {
			double sellIncome_Ref = settleMatchDB.getSellIncome_Ref().doubleValue();
			double buyPayout_Ref = settleMatchDB.getBuyPayout_Ref().doubleValue();
			double buyPayout = settleMatchDB.getBuyPayout().doubleValue();
			double sellIncome = settleMatchDB.getSellIncome().doubleValue();
			double buyMargin = settleMatchDB.getBuyMargin().doubleValue();
			double hl_Amout = settleMatchDB.getHL_Amount().doubleValue();

			if ((Arith.sub(buyMargin, thisPayMent) >= 0.0D) && (thisPayMent <= Arith.sub(Arith.add(buyPayout_Ref, hl_Amout), buyPayout))
					&& (Arith.add(Arith.add(buyPayout, thisPayMent), Arith.sub(sellIncome_Ref, buyPayout_Ref)) >= Arith.format(sellIncome, 2))) {
				String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";

				Object[] paramsMargin = { settleMatchDB.getFirmID_B(), "15014", Double.valueOf(thisPayMent), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };

				Object[] paramsPayout = { settleMatchDB.getFirmID_B(), "15008", Double.valueOf(thisPayMent), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsMargin).toString();
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPayout).toString();

				SettleMatchFundManage fundLog1 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_B(), "15014",
						thisPayMent, settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				SettleMatchFundManage fundLog2 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_B(), "15008",
						thisPayMent, settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				this.matchDisposeDao.add(fundLog1);
				this.matchDisposeDao.add(fundLog2);

				this.matchDisposeDao.updateSettleMargin(settleMatchDB.getFirmID_B(), thisPayMent);

				settleMatchDB.setBuyMargin(Double.valueOf(buyMargin - thisPayMent));
				settleMatchDB.setBuyPayout(Double.valueOf(buyPayout + thisPayMent));
				settleMatchDB.setModifier(settleMatch.getModifier());
				settleMatchDB.setModifyTime(settleMatch.getModifyTime());

				if (settleMatchDB.getStatus().intValue() == 0) {
					settleMatchDB.setStatus(Integer.valueOf(1));
				}
				this.matchDisposeDao.updateSettleMatch(settleMatchDB);

				SettleMatchLog settleMatchLog = new SettleMatchLog();
				settleMatchLog.setMatchID(settleMatch.getMatchID());
				settleMatchLog.setOperateLog("保证金转货款,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent);
				settleMatchLog.setOperator(settleMatch.getModifier());
				settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
				this.matchDisposeDao.add(settleMatchLog);

				result = 1;
			} else {
				result = 5;
			}
		} else {
			result = settleMatchDB.getStatus().intValue();
			if ((settleMatchDB.getStatus().intValue() == 1) && (settleMatchDB.getIsTransfer().intValue() == 1)) {
				result = 4;
			}
		}
		return result;
	}

	public int incomePayout(SettleMatch settleMatch, double thisPayMent) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0)
				|| ((settleMatchDB.getStatus().intValue() == 1) && (settleMatchDB.getIsTransfer().intValue() == 0))) {
			double sellIncome_Ref = settleMatchDB.getSellIncome_Ref().doubleValue();
			double buyPayout_Ref = settleMatchDB.getBuyPayout_Ref().doubleValue();
			double buyPayout = settleMatchDB.getBuyPayout().doubleValue();
			double sellIncome = settleMatchDB.getSellIncome().doubleValue();
			double hl_Amout = settleMatchDB.getHL_Amount().doubleValue();

			if ((Arith.add(Arith.add(buyPayout, thisPayMent), Arith.sub(sellIncome_Ref, buyPayout_Ref)) >= Arith.format(sellIncome, 2))
					&& (Arith.add(buyPayout, thisPayMent) >= 0.0D)) {
				String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";

				Object[] paramsPayout = { settleMatchDB.getFirmID_B(), "15008", Double.valueOf(thisPayMent), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPayout).toString();

				SettleMatchFundManage fundLog = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_B(), "15008",
						thisPayMent, settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				this.matchDisposeDao.add(fundLog);

				settleMatchDB.setBuyPayout(Double.valueOf(buyPayout + thisPayMent));
				settleMatchDB.setModifier(settleMatch.getModifier());
				settleMatchDB.setModifyTime(settleMatch.getModifyTime());

				if (settleMatchDB.getStatus().intValue() == 0) {
					settleMatchDB.setStatus(Integer.valueOf(1));
				}
				this.matchDisposeDao.updateSettleMatch(settleMatchDB);

				SettleMatchLog settleMatchLog = new SettleMatchLog();
				settleMatchLog.setMatchID(settleMatch.getMatchID());
				settleMatchLog.setOperateLog("收货款,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent);
				settleMatchLog.setOperator(settleMatch.getModifier());
				settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
				this.matchDisposeDao.add(settleMatchLog);

				result = 1;
			} else {
				result = 5;
			}
		} else {
			result = settleMatchDB.getStatus().intValue();
			if ((settleMatchDB.getStatus().intValue() == 1) && (settleMatchDB.getIsTransfer().intValue() == 1)) {
				result = 4;
			}
		}
		return result;
	}

	public int payPayout(SettleMatch settleMatch, double thisPayMent) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0) || (settleMatchDB.getStatus().intValue() == 1)) {
			double sellIncome_Ref = settleMatchDB.getSellIncome_Ref().doubleValue();
			double buyPayout_Ref = settleMatchDB.getBuyPayout_Ref().doubleValue();
			double buyPayout = settleMatchDB.getBuyPayout().doubleValue();
			double sellIncome = settleMatchDB.getSellIncome().doubleValue();

			if ((Arith.add(buyPayout, Arith.sub(sellIncome_Ref, buyPayout_Ref)) >= Arith.add(sellIncome, thisPayMent))
					&& (Arith.add(sellIncome, thisPayMent) >= 0.0D)) {
				String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";

				Object[] paramsPayout2 = { settleMatchDB.getFirmID_S(), "15009", Double.valueOf(thisPayMent), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPayout2).toString();

				SettleMatchFundManage fundLog = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_S(), "15009",
						thisPayMent, settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				this.matchDisposeDao.add(fundLog);

				settleMatchDB.setSellIncome(Double.valueOf(sellIncome + thisPayMent));
				settleMatchDB.setModifier(settleMatch.getModifier());
				settleMatchDB.setModifyTime(settleMatch.getModifyTime());

				if (settleMatchDB.getStatus().intValue() == 0) {
					settleMatchDB.setStatus(Integer.valueOf(1));
				}
				this.matchDisposeDao.updateSettleMatch(settleMatchDB);

				SettleMatchLog settleMatchLog = new SettleMatchLog();
				settleMatchLog.setMatchID(settleMatch.getMatchID());
				settleMatchLog.setOperateLog("付货款,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent);
				settleMatchLog.setOperator(settleMatch.getModifier());
				settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
				this.matchDisposeDao.add(settleMatchLog);

				result = 1;
			} else {
				result = 5;
			}
		} else {
			result = settleMatchDB.getStatus().intValue();
		}
		return result;
	}

	public int settleTransfer(SettleMatch settleMatch) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 1) && (settleMatchDB.getIsTransfer().intValue() == 0)) {
			double buyPayout_Ref = settleMatchDB.getBuyPayout_Ref().doubleValue();
			double buyPayout = settleMatchDB.getBuyPayout().doubleValue();
			double hl_Amout = settleMatchDB.getHL_Amount().doubleValue();

			if (Arith.sub(Arith.add(buyPayout_Ref, hl_Amout), buyPayout) == 0.0D) {
				result = check_doubleTransfer(settleMatch.getMatchID());
				if (result != 0) {
					return 7;
				}

				settleMatchDB.setIsTransfer(Integer.valueOf(1));
				settleMatchDB.setModifier(settleMatch.getModifier());
				settleMatchDB.setModifyTime(settleMatch.getModifyTime());
				this.matchDisposeDao.updateSettleMatch(settleMatchDB);

				SettleMatchLog settleMatchLog = new SettleMatchLog();
				settleMatchLog.setMatchID(settleMatch.getMatchID());
				settleMatchLog.setOperateLog("货权转移,配对ID:" + settleMatch.getMatchID());
				settleMatchLog.setOperator(settleMatch.getModifier());
				settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
				this.matchDisposeDao.add(settleMatchLog);

				result = 1;

				DeliveryVO DeliveryVO = this.tradeService.performDelivery(15, settleMatchDB.getMatchID(), settleMatchDB.getFirmID_S(),
						settleMatchDB.getFirmID_B());

				String sql = "select stockID from BI_tradestock where TRADENO='" + settleMatchDB.getMatchID() + "'";
				List list_trade = this.matchDisposeDao.queryBySql(sql);

				if ((!list_trade.isEmpty()) || (list_trade.size() > 0)) {
					for (Map map : (List<Map>) list_trade) {
						String stockId = (String) map.get("STOCKID");
						String sql_ship = "insert into BI_BUSINESSRELATIONSHIP values('" + stockId + "','" + settleMatchDB.getFirmID_B() + "','"
								+ settleMatchDB.getFirmID_S() + "',0,null,sysdate)";
						this.matchDisposeDao.executeUpdateBySql(sql_ship);
					}
				}
				if (DeliveryVO.getResult() == -1L) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					result = 6;
				}
			} else {
				result = 5;
			}
		} else {
			result = settleMatchDB.getStatus().intValue();
			if ((settleMatchDB.getStatus().intValue() == 1) && (settleMatchDB.getIsTransfer().intValue() == 1)) {
				result = 4;
			}
		}
		return result;
	}

	public int settleFinish_agreement(SettleMatch settleMatch) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());
		double sellIncome_Ref = settleMatchDB.getSellIncome_Ref().doubleValue();
		double sellIncome = settleMatchDB.getSellIncome().doubleValue();
		double hl_Amout = settleMatchDB.getHL_Amount().doubleValue();

		if ((settleMatchDB.getStatus().intValue() == 1) && (settleMatchDB.getIsTransfer().intValue() == 1)) {
			if (Arith.sub(Arith.add(sellIncome_Ref, hl_Amout), sellIncome) == 0.0D) {
				String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";
				if (settleMatchDB.getBuyMargin().doubleValue() != 0.0D) {
					Object[] paramsBuyMargin = { settleMatchDB.getFirmID_B(), "15014", settleMatchDB.getBuyMargin(), settleMatchDB.getMatchID(),
							settleMatchDB.getCommodityID() };
					this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsBuyMargin).toString();

					SettleMatchFundManage fundLogB = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_B(), "15014",
							settleMatchDB.getBuyMargin().doubleValue(), settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
					this.matchDisposeDao.add(fundLogB);

					this.matchDisposeDao.updateSettleMargin(settleMatchDB.getFirmID_B(), settleMatchDB.getBuyMargin().doubleValue());

					settleMatchDB.setBuyMargin(Double.valueOf(0.0D));
				}
				if (settleMatchDB.getSellMargin().doubleValue() != 0.0D) {
					Object[] paramsSellMargin = { settleMatchDB.getFirmID_S(), "15014", settleMatchDB.getSellMargin(), settleMatchDB.getMatchID(),
							settleMatchDB.getCommodityID() };
					this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsSellMargin).toString();

					SettleMatchFundManage fundLogS = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_S(), "15014",
							settleMatchDB.getSellMargin().doubleValue(), settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
					this.matchDisposeDao.add(fundLogS);

					this.matchDisposeDao.updateSettleMargin(settleMatchDB.getFirmID_S(), settleMatchDB.getSellMargin().doubleValue());

					settleMatchDB.setSellMargin(Double.valueOf(0.0D));
				}

				settleMatchDB.setStatus(Integer.valueOf(2));
				settleMatchDB.setModifier(settleMatch.getModifier());
				settleMatchDB.setModifyTime(settleMatch.getModifyTime());
				this.matchDisposeDao.updateSettleMatch(settleMatchDB);

				SettleMatchLog settleMatchLog = new SettleMatchLog();
				settleMatchLog.setMatchID(settleMatch.getMatchID());
				settleMatchLog.setOperateLog("处理完成,配对ID:" + settleMatch.getMatchID());
				settleMatchLog.setOperator(settleMatch.getModifier());
				settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
				this.matchDisposeDao.add(settleMatchLog);
				result = 1;
			} else {
				result = 5;
			}
		} else {
			result = settleMatchDB.getStatus().intValue();
			if ((settleMatchDB.getStatus().intValue() == 1) && (settleMatchDB.getIsTransfer().intValue() == 0)) {
				result = 4;
			}
		}
		return result;
	}

	public int backSMargin(SettleMatch settleMatch, double thisPayMent) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0) || (settleMatchDB.getStatus().intValue() == 1)) {
			double sellMargin = settleMatchDB.getSellMargin().doubleValue();

			double balance = getBalanceLock(settleMatchDB.getFirmID_S());

			if ((Arith.sub(sellMargin, thisPayMent) >= 0.0D) || (balance >= -thisPayMent)) {
				String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";

				Object[] paramsMargin = { settleMatchDB.getFirmID_S(), "15014", Double.valueOf(thisPayMent), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsMargin).toString();

				SettleMatchFundManage fundLog1 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_S(), "15014",
						thisPayMent, settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				this.matchDisposeDao.add(fundLog1);

				this.matchDisposeDao.updateSettleMargin(settleMatchDB.getFirmID_S(), thisPayMent);

				settleMatchDB.setSellMargin(Double.valueOf(sellMargin - thisPayMent));
				settleMatchDB.setModifier(settleMatch.getModifier());
				settleMatchDB.setModifyTime(settleMatch.getModifyTime());

				if (settleMatchDB.getStatus().intValue() == 0) {
					settleMatchDB.setStatus(Integer.valueOf(1));
				}
				this.matchDisposeDao.updateSettleMatch(settleMatchDB);

				SettleMatchLog settleMatchLog = new SettleMatchLog();
				settleMatchLog.setMatchID(settleMatch.getMatchID());
				settleMatchLog.setOperateLog("退卖方保证金,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent);
				settleMatchLog.setOperator(settleMatch.getModifier());
				settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
				this.matchDisposeDao.add(settleMatchLog);

				result = 1;
			} else {
				result = 5;
			}
		} else {
			result = settleMatchDB.getStatus().intValue();
		}
		return result;
	}

	public int payPenaltyToS(SettleMatch settleMatch, double thisPayMent) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0) || (settleMatchDB.getStatus().intValue() == 1)) {
			String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";

			if ((thisPayMent <= Arith.sub(settleMatchDB.getTakePenalty_B().doubleValue(), settleMatchDB.getPayPenalty_S().doubleValue()))
					&& (Arith.add(thisPayMent, settleMatchDB.getPayPenalty_S().doubleValue()) >= 0.0D)) {
				double balance = getBalanceLock(settleMatchDB.getFirmID_S());
				if ((thisPayMent > 0.0D) || (balance >= -thisPayMent)) {
					Object[] paramsPayout1 = { settleMatchDB.getFirmID_S(), "15018", Double.valueOf(thisPayMent), settleMatchDB.getMatchID(),
							settleMatchDB.getCommodityID() };
					this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPayout1).toString();

					SettleMatchFundManage fundLog2 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_S(), "15018",
							thisPayMent, settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
					this.matchDisposeDao.add(fundLog2);

					settleMatchDB.setPayPenalty_S(Double.valueOf(settleMatchDB.getPayPenalty_S().doubleValue() + thisPayMent));
					settleMatchDB.setModifier(settleMatch.getModifier());
					settleMatchDB.setModifyTime(settleMatch.getModifyTime());

					if (settleMatchDB.getStatus().intValue() == 0) {
						settleMatchDB.setStatus(Integer.valueOf(1));
					}
					this.matchDisposeDao.updateSettleMatch(settleMatchDB);

					SettleMatchLog settleMatchLog = new SettleMatchLog();
					settleMatchLog.setMatchID(settleMatch.getMatchID());
					settleMatchLog.setOperateLog("付卖方违约金,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent);
					settleMatchLog.setOperator(settleMatch.getModifier());
					settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
					this.matchDisposeDao.add(settleMatchLog);

					result = 1;
				} else {
					return 4;
				}
			} else {
				return 5;
			}
		} else {
			result = settleMatchDB.getStatus().intValue();
		}
		return result;
	}

	public int payPenaltyToB(SettleMatch settleMatch, double thisPayMent) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0) || (settleMatchDB.getStatus().intValue() == 1)) {
			String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";

			if ((thisPayMent <= Arith.sub(settleMatchDB.getTakePenalty_S().doubleValue(), settleMatchDB.getPayPenalty_B().doubleValue()))
					&& (Arith.add(thisPayMent, settleMatchDB.getPayPenalty_B().doubleValue()) >= 0.0D)) {
				double balance = getBalanceLock(settleMatchDB.getFirmID_B());
				if ((thisPayMent > 0.0D) || (balance >= -thisPayMent)) {
					Object[] paramsPayout1 = { settleMatchDB.getFirmID_B(), "15018", Double.valueOf(thisPayMent), settleMatchDB.getMatchID(),
							settleMatchDB.getCommodityID() };
					this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPayout1).toString();

					SettleMatchFundManage fundLog2 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_B(), "15018",
							thisPayMent, settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
					this.matchDisposeDao.add(fundLog2);

					settleMatchDB.setPayPenalty_B(Double.valueOf(settleMatchDB.getPayPenalty_B().doubleValue() + thisPayMent));
					settleMatchDB.setModifier(settleMatch.getModifier());
					settleMatchDB.setModifyTime(settleMatch.getModifyTime());

					if (settleMatchDB.getStatus().intValue() == 0) {
						settleMatchDB.setStatus(Integer.valueOf(1));
					}
					this.matchDisposeDao.updateSettleMatch(settleMatchDB);

					SettleMatchLog settleMatchLog = new SettleMatchLog();
					settleMatchLog.setMatchID(settleMatch.getMatchID());
					settleMatchLog.setOperateLog("付买方违约金,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent);
					settleMatchLog.setOperator(settleMatch.getModifier());
					settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
					this.matchDisposeDao.add(settleMatchLog);

					result = 1;
				} else {
					return 4;
				}
			} else {
				return 5;
			}
		} else {
			result = settleMatchDB.getStatus().intValue();
		}
		return result;
	}

	public int takePenaltyFromB(SettleMatch settleMatch, double thisPayMent) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0) || (settleMatchDB.getStatus().intValue() == 1)) {
			String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";

			double balance = getBalanceLock(settleMatchDB.getFirmID_B());
			if (thisPayMent > 0.0D) {
				if (balance >= Arith.sub(thisPayMent, settleMatchDB.getBuyMargin().doubleValue())) {
					if (settleMatchDB.getBuyMargin().doubleValue() != 0.0D) {
						Object[] paramsMargin = { settleMatchDB.getFirmID_B(), "15014", settleMatchDB.getBuyMargin(), settleMatchDB.getMatchID(),
								settleMatchDB.getCommodityID() };
						this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsMargin).toString();

						SettleMatchFundManage fundLog1 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_B(), "15014",
								settleMatchDB.getBuyMargin().doubleValue(), settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
						this.matchDisposeDao.add(fundLog1);

						this.matchDisposeDao.updateSettleMargin(settleMatchDB.getFirmID_B(), settleMatchDB.getBuyMargin().doubleValue());

						settleMatchDB.setBuyMargin(Double.valueOf(0.0D));
					}
				} else
					return 4;
			} else if (Arith.add(settleMatchDB.getTakePenalty_B().doubleValue(), thisPayMent) < settleMatchDB.getPayPenalty_S().doubleValue()) {
				return 5;
			}

			Object[] paramsPayout1 = { settleMatchDB.getFirmID_B(), "15017", Double.valueOf(thisPayMent), settleMatchDB.getMatchID(),
					settleMatchDB.getCommodityID() };
			this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPayout1).toString();

			SettleMatchFundManage fundLog2 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_B(), "15017", thisPayMent,
					settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
			this.matchDisposeDao.add(fundLog2);

			settleMatchDB.setTakePenalty_B(Double.valueOf(settleMatchDB.getTakePenalty_B().doubleValue() + thisPayMent));
			settleMatchDB.setModifier(settleMatch.getModifier());
			settleMatchDB.setModifyTime(settleMatch.getModifyTime());

			if (settleMatchDB.getStatus().intValue() == 0) {
				settleMatchDB.setStatus(Integer.valueOf(1));
			}
			this.matchDisposeDao.updateSettleMatch(settleMatchDB);

			SettleMatchLog settleMatchLog = new SettleMatchLog();
			settleMatchLog.setMatchID(settleMatch.getMatchID());
			settleMatchLog.setOperateLog("收买方违约金,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent);
			settleMatchLog.setOperator(settleMatch.getModifier());
			settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
			this.matchDisposeDao.add(settleMatchLog);

			result = 1;
		} else {
			result = settleMatchDB.getStatus().intValue();
		}
		return result;
	}

	public int takePenaltyFromS(SettleMatch settleMatch, double thisPayMent) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0) || (settleMatchDB.getStatus().intValue() == 1)) {
			String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";

			double balance = getBalanceLock(settleMatchDB.getFirmID_S());
			if (thisPayMent > 0.0D) {
				if (balance >= Arith.sub(thisPayMent, settleMatchDB.getSellMargin().doubleValue())) {
					if (settleMatchDB.getSellMargin().doubleValue() != 0.0D) {
						Object[] paramsMargin = { settleMatchDB.getFirmID_S(), "15014", settleMatchDB.getSellMargin(), settleMatchDB.getMatchID(),
								settleMatchDB.getCommodityID() };
						this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsMargin).toString();

						SettleMatchFundManage fundLog1 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_S(), "15014",
								settleMatchDB.getSellMargin().doubleValue(), settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
						this.matchDisposeDao.add(fundLog1);

						this.matchDisposeDao.updateSettleMargin(settleMatchDB.getFirmID_S(), settleMatchDB.getSellMargin().doubleValue());

						settleMatchDB.setSellMargin(Double.valueOf(0.0D));
					}
				} else
					return 4;
			} else if (Arith.add(settleMatchDB.getTakePenalty_S().doubleValue(), thisPayMent) < settleMatchDB.getPayPenalty_B().doubleValue()) {
				return 5;
			}

			Object[] paramsPayout1 = { settleMatchDB.getFirmID_S(), "15017", Double.valueOf(thisPayMent), settleMatchDB.getMatchID(),
					settleMatchDB.getCommodityID() };
			this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPayout1).toString();

			SettleMatchFundManage fundLog2 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_S(), "15017", thisPayMent,
					settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
			this.matchDisposeDao.add(fundLog2);

			settleMatchDB.setTakePenalty_S(Double.valueOf(settleMatchDB.getTakePenalty_S().doubleValue() + thisPayMent));
			settleMatchDB.setModifier(settleMatch.getModifier());
			settleMatchDB.setModifyTime(settleMatch.getModifyTime());

			if (settleMatchDB.getStatus().intValue() == 0) {
				settleMatchDB.setStatus(Integer.valueOf(1));
			}
			this.matchDisposeDao.updateSettleMatch(settleMatchDB);

			SettleMatchLog settleMatchLog = new SettleMatchLog();
			settleMatchLog.setMatchID(settleMatch.getMatchID());
			settleMatchLog.setOperateLog("收卖方违约金,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent);
			settleMatchLog.setOperator(settleMatch.getModifier());
			settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
			this.matchDisposeDao.add(settleMatchLog);

			result = 1;
		} else {
			result = settleMatchDB.getStatus().intValue();
		}
		return result;
	}

	public int settlePL_B(SettleMatch settleMatch, double thisPayMent) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0) || (settleMatchDB.getStatus().intValue() == 1)) {
			double balance = getBalanceLock(settleMatchDB.getFirmID_B());

			String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";
			if (thisPayMent > 0.0D) {
				Object[] paramsPL1 = { settleMatchDB.getFirmID_B(), "15011", Double.valueOf(thisPayMent), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPL1).toString();

				SettleMatchFundManage fundLog1 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_B(), "15011",
						thisPayMent, settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				this.matchDisposeDao.add(fundLog1);
			} else if (Arith.add(balance, thisPayMent) >= 0.0D) {
				Object[] paramsPL2 = { settleMatchDB.getFirmID_B(), "15012", Double.valueOf(-thisPayMent), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPL2).toString();

				SettleMatchFundManage fundLog2 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_B(), "15012",
						-thisPayMent, settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				this.matchDisposeDao.add(fundLog2);
			} else {
				return 4;
			}

			settleMatchDB.setSettlePL_B(Double.valueOf(settleMatchDB.getSettlePL_B().doubleValue() + thisPayMent));
			settleMatchDB.setModifier(settleMatch.getModifier());
			settleMatchDB.setModifyTime(settleMatch.getModifyTime());

			if (settleMatchDB.getStatus().intValue() == 0) {
				settleMatchDB.setStatus(Integer.valueOf(1));
			}
			this.matchDisposeDao.updateSettleMatch(settleMatchDB);

			SettleMatchLog settleMatchLog = new SettleMatchLog();
			settleMatchLog.setMatchID(settleMatch.getMatchID());
			settleMatchLog.setOperateLog("买方交收盈亏,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent);
			settleMatchLog.setOperator(settleMatch.getModifier());
			settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
			this.matchDisposeDao.add(settleMatchLog);

			result = 1;
		} else {
			result = settleMatchDB.getStatus().intValue();
		}
		return result;
	}

	public int settlePL_S(SettleMatch settleMatch, double thisPayMent) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0) || (settleMatchDB.getStatus().intValue() == 1)) {
			double balance = getBalanceLock(settleMatchDB.getFirmID_B());

			String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";
			if (thisPayMent > 0.0D) {
				Object[] paramsPL1 = { settleMatchDB.getFirmID_S(), "15011", Double.valueOf(thisPayMent), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPL1).toString();

				SettleMatchFundManage fundLog1 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_S(), "15011",
						thisPayMent, settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				this.matchDisposeDao.add(fundLog1);
			} else if (Arith.add(balance, thisPayMent) >= 0.0D) {
				Object[] paramsPL2 = { settleMatchDB.getFirmID_S(), "15012", Double.valueOf(-thisPayMent), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPL2).toString();

				SettleMatchFundManage fundLog2 = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_S(), "15012",
						-thisPayMent, settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				this.matchDisposeDao.add(fundLog2);
			} else {
				return 4;
			}

			settleMatchDB.setSettlePL_S(Double.valueOf(settleMatchDB.getSettlePL_S().doubleValue() + thisPayMent));
			settleMatchDB.setModifier(settleMatch.getModifier());
			settleMatchDB.setModifyTime(settleMatch.getModifyTime());

			if (settleMatchDB.getStatus().intValue() == 0) {
				settleMatchDB.setStatus(Integer.valueOf(1));
			}
			this.matchDisposeDao.updateSettleMatch(settleMatchDB);

			SettleMatchLog settleMatchLog = new SettleMatchLog();
			settleMatchLog.setMatchID(settleMatch.getMatchID());
			settleMatchLog.setOperateLog("卖方交收盈亏,配对ID:" + settleMatch.getMatchID() + " 金额:" + thisPayMent);
			settleMatchLog.setOperator(settleMatch.getModifier());
			settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
			this.matchDisposeDao.add(settleMatchLog);

			result = 1;
		} else {
			result = settleMatchDB.getStatus().intValue();
		}
		return result;
	}

	public int settleFinish_default(SettleMatch settleMatch) throws Exception {
		int result = -1;

		SettleMatch settleMatchDB = this.matchDisposeDao.getSettleMatchLock(settleMatch.getMatchID());

		if ((settleMatchDB.getStatus().intValue() == 0) || (settleMatchDB.getStatus().intValue() == 1)) {
			String procedureName = "{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }";
			if (settleMatchDB.getBuyMargin().doubleValue() != 0.0D) {
				Object[] paramsBuyMargin = { settleMatchDB.getFirmID_B(), "15014", settleMatchDB.getBuyMargin(), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsBuyMargin).toString();

				SettleMatchFundManage fundLogB = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_B(), "15014",
						settleMatchDB.getBuyMargin().doubleValue(), settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				this.matchDisposeDao.add(fundLogB);

				this.matchDisposeDao.updateSettleMargin(settleMatchDB.getFirmID_B(), settleMatchDB.getBuyMargin().doubleValue());

				settleMatchDB.setBuyMargin(Double.valueOf(0.0D));
			}
			if (settleMatchDB.getSellMargin().doubleValue() != 0.0D) {
				Object[] paramsSellMargin = { settleMatchDB.getFirmID_S(), "15014", settleMatchDB.getSellMargin(), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsSellMargin).toString();

				SettleMatchFundManage fundLogS = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_S(), "15014",
						settleMatchDB.getSellMargin().doubleValue(), settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				this.matchDisposeDao.add(fundLogS);

				this.matchDisposeDao.updateSettleMargin(settleMatchDB.getFirmID_S(), settleMatchDB.getSellMargin().doubleValue());

				settleMatchDB.setSellMargin(Double.valueOf(0.0D));
			}
			if (settleMatchDB.getBuyPayout().doubleValue() != 0.0D) {
				Object[] paramsPayout = { settleMatchDB.getFirmID_B(), "15009", settleMatchDB.getBuyPayout(), settleMatchDB.getMatchID(),
						settleMatchDB.getCommodityID() };
				this.matchDisposeDao.executeProcedure("{?=call FN_F_UpdateFundsFull(?,?,?,?,?,?,?) }", paramsPayout).toString();

				SettleMatchFundManage fundLogP = new SettleMatchFundManage(settleMatchDB.getMatchID(), settleMatchDB.getFirmID_B(), "15009",
						settleMatchDB.getBuyPayout().doubleValue(), settleMatch.getModifyTime(), settleMatchDB.getCommodityID());
				this.matchDisposeDao.add(fundLogP);
				settleMatchDB.setBuyPayout(Double.valueOf(0.0D));
			}

			settleMatchDB.setStatus(Integer.valueOf(2));
			settleMatchDB.setModifier(settleMatch.getModifier());
			settleMatchDB.setModifyTime(settleMatch.getModifyTime());
			this.matchDisposeDao.updateSettleMatch(settleMatchDB);

			SettleMatchLog settleMatchLog = new SettleMatchLog();
			settleMatchLog.setMatchID(settleMatchDB.getMatchID());
			settleMatchLog.setOperateLog("处理完成,配对ID:" + settleMatchDB.getMatchID());
			settleMatchLog.setOperator(settleMatch.getModifier());
			settleMatchLog.setUpdateTime(new Timestamp(settleMatch.getModifyTime().getTime()));
			this.matchDisposeDao.add(settleMatchLog);

			result = 1;
		} else {
			result = settleMatchDB.getStatus().intValue();
		}
		return result;
	}

	public int settleCancel(SettleMatch settleMatch, String operator) throws Exception {
		int result = -1;
		String procedureName = "{?=call FN_T_SettleCancel(?,?) }";
		Object[] params = { settleMatch.getMatchID(), operator };
		result = Integer.parseInt(this.matchDisposeDao.executeProcedure("{?=call FN_T_SettleCancel(?,?) }", params).toString());
		if ((result == 1) && (settleMatch.getResult().intValue() == 1)) {
			ResultVO resultVO = this.tradeService.performRealeseGoods(15, settleMatch.getMatchID());
			if (resultVO.getResult() == -1L) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				result = 6;
			}
		}

		return result;
	}

	public double getBalanceLock(String firmId) throws Exception {
		double balance = 0.0D;
		String funcName = "{?=call FN_F_GetRealFunds(?,?) }";

		Object[] paramsBalance = { firmId, Integer.valueOf(1) };
		balance = Double.parseDouble(this.matchDisposeDao.executeProcedure("{?=call FN_F_GetRealFunds(?,?) }", paramsBalance).toString());
		return balance;
	}

	public int check_doubleTransfer(String matchId) throws Exception {
		int count = 0;

		String sql = "select stockID from BI_tradestock where TRADENO='" + matchId + "' and status=0";
		List list_trade = this.matchDisposeDao.queryBySql(sql);

		for (Map map : (List<Map>) list_trade) {
			String stockId = (String) map.get("STOCKID");

			String sql_tradeNo = "select TradeNo from (select tradeNo from BI_tradestock where stockid='" + stockId
					+ "' and status=1  order by releaseTime desc) where rownum =1";
			List list_tradeNo = this.matchDisposeDao.queryBySql(sql_tradeNo);

			if ((list_tradeNo.isEmpty()) || (list_tradeNo.size() < 1))
				break;
			String tradeNo = (String) ((Map) list_tradeNo.get(0)).get("TRADENO");

			String sql_match = "select * from T_settleMatch where status in(2,3) and matchid='" + tradeNo + "'";
			List list_match = this.matchDisposeDao.queryBySql(sql_match);

			if ((list_match.isEmpty()) || (list_match.size() < 1)) {
				count++;
				break;
			}
		}

		if (count != 0) {
			return count;
		}
		return 0;
	}

	public MatchDisposeDao getMatchDisposeDao() {
		return this.matchDisposeDao;
	}
}