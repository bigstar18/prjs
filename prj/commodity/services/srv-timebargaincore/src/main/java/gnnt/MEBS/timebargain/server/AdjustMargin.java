package gnnt.MEBS.timebargain.server;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.server.dao.DAOBeanFactory;
import gnnt.MEBS.timebargain.server.dao.ServerDAO;
import gnnt.MEBS.timebargain.server.dao.TradeDAO;
import gnnt.MEBS.timebargain.server.model.MarginAdjust;
import gnnt.MEBS.timebargain.server.model.SysLog;
import gnnt.MEBS.timebargain.server.util.DateUtil;

public class AdjustMargin {
	private Log log = LogFactory.getLog(getClass());
	private ServerInit serverInit;
	private ServerDAO serverDAO;
	private TradeDAO tradeDAO;
	private static AdjustMargin instance;

	public static AdjustMargin getInstance() {
		if (instance == null) {
			instance = new AdjustMargin();
		}
		return instance;
	}

	public void init(ServerInit paramServerInit) {
		this.serverInit = paramServerInit;
		this.serverDAO = ((ServerDAO) DAOBeanFactory.getBean("serverDAO"));
		this.tradeDAO = ((TradeDAO) DAOBeanFactory.getBean("tradeDAO"));
	}

	public boolean adjustMargin(int paramInt) {
		boolean bool1 = false;
		boolean bool2 = adjustCommodityMargin(paramInt);
		boolean bool3 = adjustFirmMargin(paramInt);
		if ((bool2 == true) || (bool3 == true)) {
			bool1 = true;
		}
		return bool1;
	}

	private boolean adjustCommodityMargin(int paramInt) {
		boolean bool = false;
		List localList = this.tradeDAO.getCommodityMarginAdjustList();
		if ((localList != null) && (localList.size() > 0)) {
			for (int i = 0; i < localList.size(); i++) {
				MarginAdjust localMarginAdjust1 = (MarginAdjust) localList.get(i);
				MarginAdjust localMarginAdjust2 = selectMarginAdjust(localMarginAdjust1, paramInt);
				if ((localMarginAdjust2 != null)
						&& ((localMarginAdjust1.getMarginRate_B().doubleValue() != localMarginAdjust2.getMarginRate_B().doubleValue())
								|| (localMarginAdjust1.getMarginRate_S().doubleValue() != localMarginAdjust2.getMarginRate_S().doubleValue())
								|| (localMarginAdjust1.getMarginAssure_B().doubleValue() != localMarginAdjust2.getMarginAssure_B().doubleValue())
								|| (localMarginAdjust1.getMarginAssure_S().doubleValue() != localMarginAdjust2.getMarginAssure_S().doubleValue()))) {
					bool = true;
					localMarginAdjust2.setCommodityID(localMarginAdjust1.getCommodityID());
					this.tradeDAO.adjustCommodityMargin(localMarginAdjust2);
					StringBuffer localStringBuffer = new StringBuffer();
					localStringBuffer.append("商品[").append(localMarginAdjust1.getCommodityID()).append("]买卖保证金，担保金比例由[")
							.append(localMarginAdjust1.getMarginRate_B().doubleValue()).append(",")
							.append(localMarginAdjust1.getMarginRate_S().doubleValue()).append(",")
							.append(localMarginAdjust1.getMarginAssure_B().doubleValue()).append(",")
							.append(localMarginAdjust1.getMarginAssure_S().doubleValue()).append("]调整为[")
							.append(localMarginAdjust2.getMarginRate_B().doubleValue()).append(",")
							.append(localMarginAdjust2.getMarginRate_S().doubleValue()).append(",")
							.append(localMarginAdjust2.getMarginAssure_B().doubleValue()).append(",")
							.append(localMarginAdjust2.getMarginAssure_S().doubleValue()).append("]");
					this.log.debug(localStringBuffer.toString());
					this.serverDAO.insertSysLog(new SysLog(localStringBuffer.toString(), 1501, 1));
				}
			}
		}
		return bool;
	}

	private boolean adjustFirmMargin(int paramInt) {
		boolean bool = false;
		List localList = this.tradeDAO.getFirmMarginAdjustList();
		if ((localList != null) && (localList.size() > 0)) {
			for (int i = 0; i < localList.size(); i++) {
				MarginAdjust localMarginAdjust1 = (MarginAdjust) localList.get(i);
				MarginAdjust localMarginAdjust2 = selectMarginAdjust(localMarginAdjust1, paramInt);
				if ((localMarginAdjust2 != null)
						&& ((localMarginAdjust1.getMarginRate_B().doubleValue() != localMarginAdjust2.getMarginRate_B().doubleValue())
								|| (localMarginAdjust1.getMarginRate_S().doubleValue() != localMarginAdjust2.getMarginRate_S().doubleValue())
								|| (localMarginAdjust1.getMarginAssure_B().doubleValue() != localMarginAdjust2.getMarginAssure_B().doubleValue())
								|| (localMarginAdjust1.getMarginAssure_S().doubleValue() != localMarginAdjust2.getMarginAssure_S().doubleValue()))) {
					bool = true;
					localMarginAdjust2.setCommodityID(localMarginAdjust1.getCommodityID());
					localMarginAdjust2.setFirmID(localMarginAdjust1.getFirmID());
					this.tradeDAO.adjustFirmMargin(localMarginAdjust2);
					StringBuffer localStringBuffer = new StringBuffer();
					localStringBuffer.append("交易商特殊商品[").append(localMarginAdjust1.getCommodityID()).append("]买卖保证金，担保金比例由[")
							.append(localMarginAdjust1.getMarginRate_B().doubleValue()).append(",")
							.append(localMarginAdjust1.getMarginRate_S().doubleValue()).append(",")
							.append(localMarginAdjust1.getMarginAssure_B().doubleValue()).append(",")
							.append(localMarginAdjust1.getMarginAssure_S().doubleValue()).append("]调整为[")
							.append(localMarginAdjust2.getMarginRate_B().doubleValue()).append(",")
							.append(localMarginAdjust2.getMarginRate_S().doubleValue()).append(",")
							.append(localMarginAdjust2.getMarginAssure_B().doubleValue()).append(",")
							.append(localMarginAdjust2.getMarginAssure_S().doubleValue()).append("]");
					this.log.debug(localStringBuffer.toString());
					this.serverDAO.insertSysLog(new SysLog(localStringBuffer.toString(), 1501, 1));
				}
			}
		}
		return bool;
	}

	private MarginAdjust selectMarginAdjust(MarginAdjust paramMarginAdjust, int paramInt) {
		MarginAdjust localMarginAdjust = null;
		TreeMap localTreeMap = new TreeMap();
		if (paramMarginAdjust.getSettleDate1() != null) {
			MarginAdjust localObject = new MarginAdjust();
			((MarginAdjust) localObject).setMarginRate_B(paramMarginAdjust.getMarginItem1());
			((MarginAdjust) localObject).setMarginRate_S(paramMarginAdjust.getMarginItem1_S());
			((MarginAdjust) localObject).setMarginAssure_B(paramMarginAdjust.getMarginItemAssure1());
			((MarginAdjust) localObject).setMarginAssure_S(paramMarginAdjust.getMarginItemAssure1_S());
			localTreeMap.put(DateUtil.formatDate(paramMarginAdjust.getSettleDate1(), "yyyy-MM-dd"), localObject);
		}
		if (paramMarginAdjust.getSettleDate2() != null) {
			MarginAdjust localObject = new MarginAdjust();
			((MarginAdjust) localObject).setMarginRate_B(paramMarginAdjust.getMarginItem2());
			((MarginAdjust) localObject).setMarginRate_S(paramMarginAdjust.getMarginItem2_S());
			((MarginAdjust) localObject).setMarginAssure_B(paramMarginAdjust.getMarginItemAssure2());
			((MarginAdjust) localObject).setMarginAssure_S(paramMarginAdjust.getMarginItemAssure2_S());
			localTreeMap.put(DateUtil.formatDate(paramMarginAdjust.getSettleDate2(), "yyyy-MM-dd"), localObject);
		}
		if (paramMarginAdjust.getSettleDate3() != null) {
			MarginAdjust localObject = new MarginAdjust();
			((MarginAdjust) localObject).setMarginRate_B(paramMarginAdjust.getMarginItem3());
			((MarginAdjust) localObject).setMarginRate_S(paramMarginAdjust.getMarginItem3_S());
			((MarginAdjust) localObject).setMarginAssure_B(paramMarginAdjust.getMarginItemAssure3());
			((MarginAdjust) localObject).setMarginAssure_S(paramMarginAdjust.getMarginItemAssure3_S());
			localTreeMap.put(DateUtil.formatDate(paramMarginAdjust.getSettleDate3(), "yyyy-MM-dd"), localObject);
		}
		if (paramMarginAdjust.getSettleDate4() != null) {
			MarginAdjust localObject = new MarginAdjust();
			((MarginAdjust) localObject).setMarginRate_B(paramMarginAdjust.getMarginItem4());
			((MarginAdjust) localObject).setMarginRate_S(paramMarginAdjust.getMarginItem4_S());
			((MarginAdjust) localObject).setMarginAssure_B(paramMarginAdjust.getMarginItemAssure4());
			((MarginAdjust) localObject).setMarginAssure_S(paramMarginAdjust.getMarginItemAssure4_S());
			localTreeMap.put(DateUtil.formatDate(paramMarginAdjust.getSettleDate4(), "yyyy-MM-dd"), localObject);
		}
		if (paramMarginAdjust.getSettleDate5() != null) {
			MarginAdjust localObject = new MarginAdjust();
			((MarginAdjust) localObject).setMarginRate_B(paramMarginAdjust.getMarginItem5());
			((MarginAdjust) localObject).setMarginRate_S(paramMarginAdjust.getMarginItem5_S());
			((MarginAdjust) localObject).setMarginAssure_B(paramMarginAdjust.getMarginItemAssure5());
			((MarginAdjust) localObject).setMarginAssure_S(paramMarginAdjust.getMarginItemAssure5_S());
			localTreeMap.put(DateUtil.formatDate(paramMarginAdjust.getSettleDate5(), "yyyy-MM-dd"), localObject);
		}
		Object localObject = getAdjustMarginDate(localTreeMap, DateUtil.formatDate(this.serverInit.getClearDate(), "yyyy-MM-dd"), paramInt);
		if (localObject != null) {
			localMarginAdjust = (MarginAdjust) localTreeMap.get(localObject);
		}
		return localMarginAdjust;
	}

	private String getAdjustMarginDate(TreeMap paramTreeMap, String paramString, int paramInt) {
		String localObject = null;
		if ((paramTreeMap == null) || (paramTreeMap.size() == 0)) {
			return localObject;
		}
		if (paramString.compareTo((String) paramTreeMap.firstKey()) == 0) {
			localObject = paramString;
			return localObject;
		}
		Iterator localIterator = paramTreeMap.keySet().iterator();
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			if (paramString.compareTo(str) == 0) {
				if ((paramInt == 1) && (this.serverInit.getMarket().getMarginFBFlag().shortValue() == 0)) {
					break;
				}
				localObject = str;
				break;
			}
			if (paramString.compareTo(str) <= 0) {
				break;
			}
			localObject = str;
		}
		return localObject;
	}
}
