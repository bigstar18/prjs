package gnnt.MEBS.vendue.server.bus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import gnnt.MEBS.vendue.server.beans.busbeans.CurCommodityCompositBean;
import gnnt.MEBS.vendue.server.beans.busbeans.PageInfo;
import gnnt.MEBS.vendue.server.beans.busbeans.PartitionCurCommodity;
import gnnt.MEBS.vendue.server.beans.dtobeans.SysCurStatus;
import gnnt.MEBS.vendue.server.dao.CurCommodityDao;

public class CurCommodityCacheService {
	private static Map sharedMap = new HashMap();
	private Map curCommodityMap = new HashMap();
	private static Logger logger = Logger.getLogger("QuotationCachelog");
	private StringBuffer sbForLogger = new StringBuffer();
	private static Timestamp lastModifytime;
	private static long lastNumOfCurCommodity = 0L;
	private CurCommodityDao dao = new CurCommodityDao();
	private static int countInThread = 0;

	protected static Map getSharedMap() {
		return sharedMap;
	}

	public PageInfo getCommodityOfPartition(Long paramLong, Integer paramInteger1, Integer paramInteger2) {
		PageInfo localPageInfo = new PageInfo(paramInteger1, paramInteger2);
		try {
			if (paramLong == null) {
				return localPageInfo;
			}
			PartitionCurCommodity localPartitionCurCommodity = (PartitionCurCommodity) sharedMap.get(paramLong.toString());
			if (localPartitionCurCommodity == null) {
				logger.debug("板块没有在内存中发现：" + paramLong);
				localPageInfo.setResultList(new ArrayList());
				localPageInfo.setPageCount(new Integer(0));
				localPageInfo.setPageIndex(new Integer(0));
				localPageInfo.setRecordCount(new Long(0L));
				return localPageInfo;
			}
			Object localObject = localPartitionCurCommodity.getCurCommodityCompositBeanListForOutPut();
			if (localObject == null) {
				localObject = new ArrayList();
			}
			localPageInfo.setRecordCount(new Long(((List) localObject).size()));
			int i = ((List) localObject).size() / localPageInfo.getPageSize().intValue();
			if (i * localPageInfo.getPageSize().intValue() < ((List) localObject).size()) {
				i++;
			}
			localPageInfo.setPageCount(new Integer(i));
			if (localPageInfo.getPageCount().intValue() > 0) {
				if (localPageInfo.getPageIndex().intValue() >= localPageInfo.getPageCount().intValue()) {
					localPageInfo.setPageIndex(new Integer(localPageInfo.getPageCount().intValue() - 1));
				}
			} else if (localPageInfo.getPageIndex().intValue() != 0) {
				localPageInfo.setPageIndex(new Integer(0));
			}
			ArrayList localArrayList = new ArrayList();
			int j = localPageInfo.getPageIndex().intValue() * localPageInfo.getPageSize().intValue();
			for (int k = j; (k < j + localPageInfo.getPageSize().intValue()) && (k < ((List) localObject).size()); k++) {
				localArrayList.add(((List) localObject).get(k));
			}
			localPageInfo.setResultList(localArrayList);
		} catch (Exception localException) {
			localException.printStackTrace();
			logger.error(localException);
		}
		return localPageInfo;
	}

	public SysCurStatus getSysCurStatus(Long paramLong) {
		if (paramLong == null) {
			return null;
		}
		PartitionCurCommodity localPartitionCurCommodity = (PartitionCurCommodity) sharedMap.get(paramLong.toString());
		if (localPartitionCurCommodity == null) {
			return null;
		}
		return localPartitionCurCommodity.getSysCurStatus();
	}

	public void buildSharedData() {
		synchronized (this) {
			countInThread += 1;
			if (countInThread > 1) {
				countInThread -= 1;
				logger.error("同时有" + countInThread + "个线程进入，为避免引发错误! 此处理自动退出! ");
				return;
			}
			this.sbForLogger = new StringBuffer();
			this.sbForLogger.append("\n*************本次内存板块商品维护过程 开始时间:" + new Date() + "*************");
			this.sbForLogger.append("\n访问数据库获取数据, 上次最大更新时间: " + (lastModifytime == null ? "NULL" : lastModifytime.toString()));
			long l1 = System.currentTimeMillis();
			try {
				if (lastModifytime == null) {
					this.curCommodityMap.clear();
				} else if (needReFetchAllParationAndAllCurCommodity()) {
					lastModifytime = null;
					this.curCommodityMap.clear();
				} else {
					this.curCommodityMap = cloneSharedMap();
				}
				Map localMap = this.dao.getAllPartitionCurCommodity(lastModifytime);
				Timestamp localTimestamp = this.dao.getNewestModityTime();
				if (localTimestamp == null) {
					this.sbForLogger.append("\n本次访问数据库没有获得最新的商品!");
				} else {
					lastModifytime = localTimestamp;
					this.sbForLogger.append("\n本次访问数据库获得了最新的商品! 得到最大的更新时间:" + localTimestamp);
				}
				processModifiedCommodity(localMap, this.curCommodityMap);
				sharedMap = this.curCommodityMap;
			} catch (Exception localException) {
				localException = localException;
				localException.printStackTrace();
				logger.error(localException);
			} finally {
			}
			long l2 = System.currentTimeMillis();
			this.sbForLogger.append("\n本次内存板块商品维护过程结束！共花费时间:" + (l2 - l1) + "毫秒！");
			this.sbForLogger.append("\n**************************************\n");
			logger.debug(this.sbForLogger.toString());
			countInThread -= 1;
		}
	}

	private void processModifiedCommodity(Map paramMap1, Map paramMap2) {
		Iterator localIterator = paramMap1.entrySet().iterator();
		for (int i = 0; i < paramMap1.size(); i++) {
			Map.Entry localEntry = (Map.Entry) localIterator.next();
			PartitionCurCommodity localPartitionCurCommodity1 = (PartitionCurCommodity) localEntry.getValue();
			PartitionCurCommodity localPartitionCurCommodity2 = (PartitionCurCommodity) paramMap2.get(localEntry.getKey());
			CurCommodityCompositBean localCurCommodityCompositBean1;
			if (localPartitionCurCommodity2 == null) {
				localPartitionCurCommodity2 = localPartitionCurCommodity1;
				paramMap2.put(localEntry.getKey(), localPartitionCurCommodity2);
			} else {
				localPartitionCurCommodity2.setPartitionId(localPartitionCurCommodity1.getPartitionId());
				localPartitionCurCommodity2.setSysCurStatus(localPartitionCurCommodity1.getSysCurStatus());
				List localList = localPartitionCurCommodity2.getCurCommodityCompositBeanList();
				List localObject = localPartitionCurCommodity1.getCurCommodityCompositBeanList();
				for (int j = 0; j < ((List) localObject).size(); j++) {
					localCurCommodityCompositBean1 = (CurCommodityCompositBean) ((List) localObject).get(j);
					long l = localCurCommodityCompositBean1.getId().longValue();
					for (int k = localList.size() - 1; k >= 0; k--) {
						CurCommodityCompositBean localCurCommodityCompositBean2 = (CurCommodityCompositBean) localList.get(k);
						if (localCurCommodityCompositBean2.getId().longValue() == l) {
							localList.remove(k);
							break;
						}
					}
					localList.add(localCurCommodityCompositBean1);
				}
				Collections.sort(localList);
			}
			List localList = localPartitionCurCommodity2.getCurCommodityCompositBeanList();
			Object localObject = new ArrayList();
			for (int j = 0; j < localList.size(); j++) {
				localCurCommodityCompositBean1 = (CurCommodityCompositBean) localList.get(j);
				if (localCurCommodityCompositBean1.getBargainflag().longValue() == 0L) {
					((List) localObject).add(localCurCommodityCompositBean1);
				}
			}
			localPartitionCurCommodity2.setCurCommodityCompositBeanListForOutPut((List) localObject);
		}
	}

	private boolean needReFetchAllParationAndAllCurCommodity() throws Exception {
		if (numCurCommodityIsReduce()) {
			return true;
		}
		Map localMap = this.dao.getAllPartion();
		if (this.curCommodityMap.size() != localMap.size()) {
			return true;
		}
		Iterator localIterator = this.curCommodityMap.entrySet().iterator();
		for (int i = 0; i < this.curCommodityMap.size(); i++) {
			Map.Entry localEntry = (Map.Entry) localIterator.next();
			PartitionCurCommodity localPartitionCurCommodity1 = (PartitionCurCommodity) localEntry.getValue();
			Long localLong1 = localPartitionCurCommodity1.getSysCurStatus().getSection();
			Long localLong2 = localPartitionCurCommodity1.getSysCurStatus().getStatus();
			PartitionCurCommodity localPartitionCurCommodity2 = (PartitionCurCommodity) localMap.get(localEntry.getKey());
			if (localPartitionCurCommodity2 == null) {
				return true;
			}
			Long localLong3 = localPartitionCurCommodity2.getSysCurStatus().getStatus();
			Long localLong4 = localPartitionCurCommodity2.getSysCurStatus().getSection();
			if ((localLong3.longValue() != localLong2.longValue()) || (localLong4.longValue() != localLong1.longValue())) {
				return true;
			}
		}
		return false;
	}

	private boolean numCurCommodityIsReduce() throws Exception {
		long l = this.dao.getNumOfAllCommodity();
		boolean bool;
		if (lastNumOfCurCommodity > l) {
			bool = true;
		} else {
			bool = false;
		}
		lastNumOfCurCommodity = l;
		return bool;
	}

	private Map cloneSharedMap() {
		HashMap localHashMap = null;
		try {
			localHashMap = (HashMap) ((HashMap) sharedMap).clone();
			Iterator localIterator = localHashMap.entrySet().iterator();
			for (int i = 0; i < localHashMap.size(); i++) {
				Map.Entry localEntry = (Map.Entry) localIterator.next();
				PartitionCurCommodity localPartitionCurCommodity = (PartitionCurCommodity) localEntry.getValue();
				localEntry.setValue(localPartitionCurCommodity.clone());
			}
		} catch (Exception localException) {
			logger.error(localException);
			localException.printStackTrace();
		}
		return localHashMap;
	}

	public static void main(String[] paramArrayOfString) {
		RefreshMemoryThreadManager.runRefreshThread();
		CurCommodityCacheService localCurCommodityCacheService = new CurCommodityCacheService();
		try {
			Thread.sleep(3000L);
		} catch (Exception localException) {
		}
		PageInfo localPageInfo = localCurCommodityCacheService.getCommodityOfPartition(new Long(11L), new Integer(0), new Integer(20));
		localPageInfo.getPageIndex();
		localPageInfo.getPageSize();
		localPageInfo.getRecordCount();
		localPageInfo.getPageCount();
		List localList = localPageInfo.getResultList();
		for (int i = 0; i < localList.size(); i++) {
			CurCommodityCompositBean localCurCommodityCompositBean = (CurCommodityCompositBean) localList.get(i);
			System.out.println("PV=========================" + localCurCommodityCompositBean.getAlertprice());
			localCurCommodityCompositBean.getAlertprice();
			localCurCommodityCompositBean.getStr10();
			localCurCommodityCompositBean.getNum3();
		}
		SysCurStatus localSysCurStatus = localCurCommodityCacheService.getSysCurStatus(new Long(1L));
	}
}
