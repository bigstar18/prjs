package gnnt.MEBS.timebargain.tradeweb.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import gnnt.MEBS.timebargain.plugin.condition.model.ConditionOrder;
import gnnt.MEBS.timebargain.tradeweb.dao.ConditionDAO;
import gnnt.MEBS.timebargain.tradeweb.model.Privilege;
import gnnt.MEBS.timebargain.tradeweb.service.ConditionOrderManager;
import gnnt.MEBS.timebargain.tradeweb.webapp.util.SortCondition;

public class ConditionOrderManagerImpl extends BaseManager implements ConditionOrderManager {
	private ConditionDAO conditionDAO;
	public static final String KEY_UPDATETIME = "KEY_UPDATETIME";
	public static final String KEY_SUCCESSTIME = "KEY_SUCCESSTIME";
	private static Map<String, Map<String, Object>> conditionOrderUpdateTimeCache = new ConcurrentHashMap();
	private static long conditionOrderListUpdateTime = 0L;

	public static void clearConditionOrderUpdateTimeCache() {
		conditionOrderUpdateTimeCache.clear();
		conditionOrderListUpdateTime = 0L;
	}

	public void setConditionDAO(ConditionDAO paramConditionDAO) {
		this.conditionDAO = paramConditionDAO;
	}

	public List<?> comty_code_query(String paramString) {
		List localList = this.conditionDAO.comty_code_query(paramString);
		return localList;
	}

	public List<?> conditionOrder_query(Privilege paramPrivilege, String paramString1, String paramString2, String paramString3, String paramString4,
			String paramString5) {
		List localList = this.conditionDAO.conditionOrder_query(paramPrivilege, paramString1, paramString2, paramString3, paramString4, paramString5);
		return localList;
	}

	public List<?> conditionOrder_query(Privilege paramPrivilege, String paramString1, String paramString2, String paramString3, String paramString4,
			String paramString5, String paramString6) {
		List localList = this.conditionDAO.conditionOrder_query(paramPrivilege, paramString1, paramString2, paramString3, paramString4, paramString5,
				paramString6);
		return localList;
	}

	public List<?> conditionOrderPageQuery(Privilege paramPrivilege, SortCondition paramSortCondition, Map paramMap) {
		return this.conditionDAO.conditionOrderPageQuery(paramPrivilege, paramSortCondition, paramMap);
	}

	public int getFirmType(String paramString) {
		return this.conditionDAO.getFirmType(paramString);
	}

	public ConditionOrder singl_order_query(String paramString) {
		return this.conditionDAO.singl_order_query(paramString);
	}

	public List getTradeDate() {
		return this.conditionDAO.getTradeDate();
	}

	public int selectConditionOrdeCount(String paramString1, String paramString2) {
		return this.conditionDAO.selectConditionOrdeCount(paramString1, paramString2);
	}

	public int selectConditionOrderCountFromCache(String paramString1, String paramString2) {
		Map localMap = (Map) conditionOrderUpdateTimeCache.get(paramString2);
		long l1 = 0L;
		try {
			l1 = Long.valueOf(paramString1).longValue();
		} catch (Exception localException) {
		}
		if (localMap == null) {
			return 0;
		}
		long l2 = ((Long) localMap.get("KEY_UPDATETIME")).longValue();
		long l3 = ((Long) localMap.get("KEY_SUCCESSTIME")).longValue();
		int i = 0;
		if (l3 > l1) {
			i = 2;
		} else if (l2 > l1) {
			i = 1;
		}
		return i;
	}

	public void conditionOrderUpdateTime_query() {
		List localList = this.conditionDAO.queryConditionOrderUpdateTime("" + conditionOrderListUpdateTime);
		long l1 = conditionOrderListUpdateTime;
		long l2 = conditionOrderListUpdateTime;
		if (localList != null) {
			Iterator localIterator = localList.iterator();
			String str = null;
			long l3 = 0L;
			long l4 = 0L;
			Long localLong1 = null;
			Long localLong2 = null;
			Map localObject = null;
			Timestamp localTimestamp = null;
			while (localIterator.hasNext()) {
				Map localMap = (Map) localIterator.next();
				str = (String) localMap.get("FIRMID");
				localTimestamp = (Timestamp) localMap.get("UPDATETIME");
				if (localTimestamp == null) {
					l3 = 0L;
				} else {
					l3 = localTimestamp.getTime();
				}
				localTimestamp = (Timestamp) localMap.get("SUCCESSTIME");
				if (localTimestamp == null) {
					l4 = 0L;
				} else {
					l4 = localTimestamp.getTime();
				}
				localObject = (Map) conditionOrderUpdateTimeCache.get(str);
				if (l3 > l1) {
					l1 = l3;
				}
				if (l4 > l2) {
					l2 = l4;
				}
				if (localObject == null) {
					localObject = new HashMap();
					((Map) localObject).put("KEY_UPDATETIME", Long.valueOf(l3));
					((Map) localObject).put("KEY_SUCCESSTIME", Long.valueOf(l4));
					conditionOrderUpdateTimeCache.put(str, localObject);
				} else {
					localLong1 = (Long) ((Map) localObject).get("KEY_UPDATETIME");
					localLong2 = (Long) ((Map) localObject).get("KEY_SUCCESSTIME");
					if (l3 > localLong1.longValue()) {
						((Map) localObject).put("KEY_UPDATETIME", Long.valueOf(l3));
					}
					if (l4 > localLong2.longValue()) {
						((Map) localObject).put("KEY_SUCCESSTIME", Long.valueOf(l4));
					}
				}
			}
		}
		conditionOrderListUpdateTime = l1 > l2 ? l1 : l2;
	}

	public List<?> selectCondittion() {
		return this.conditionDAO.selectCondittion();
	}

	public List<?> getRmiConf(int paramInt) {
		return this.conditionDAO.getRmiConf(paramInt);
	}
}
