package gnnt.MEBS.timebargain.tradeweb.webapp.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.timebargain.tradeweb.model.Privilege;

public class PrivilegeController {
	private static final Log log = LogFactory.getLog(PrivilegeController.class);

	public static boolean checkSuperTrader(Privilege paramPrivilege) {
		log.debug("checkSuperTrade method");
		if (paramPrivilege != null) {
			String str = paramPrivilege.getTraderStatus();
			return "A".equals(str);
		}
		log.debug("Privilege is not load.......");
		return false;
	}

	public static boolean checkTraderPrvg(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString) {
		log.debug("check Trader Breed Privilege method");
		boolean bool = false;
		if (paramPrivilege != null) {
			Map localMap1 = paramPrivilege.getCommMap();
			Integer localInteger1 = (Integer) localMap1.get(paramString);
			String str = localInteger1.toString();
			Map localMap2;
			Integer localInteger2;
			if (paramShort1.shortValue() == 1) {
				localMap2 = paramPrivilege.getTBreedPrivilege_B();
				if (localMap2 == null) {
					return true;
				}
				localInteger2 = (Integer) localMap2.get(str);
				if (localInteger2 == null) {
					return true;
				}
				if ((localInteger2.intValue() == 101) || ((localInteger2.intValue() == 102) && (paramShort2.shortValue() == 1))
						|| ((localInteger2.intValue() == 103) && (paramShort2.shortValue() == 2))) {
					bool = true;
				}
			} else {
				localMap2 = paramPrivilege.getTBreedPrivilege_S();
				if (localMap2 == null) {
					return true;
				}
				localInteger2 = (Integer) localMap2.get(str);
				if (localInteger2 == null) {
					return true;
				}
				if ((localInteger2.intValue() == 201) || ((localInteger2.intValue() == 202) && (paramShort2.shortValue() == 1))
						|| ((localInteger2.intValue() == 203) && (paramShort2.shortValue() == 2))) {
					bool = true;
				}
			}
		}
		return bool;
	}

	public static boolean checkCommPrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString) {
		log.debug("checkCommPrivilege method");
		boolean bool = false;
		if (paramPrivilege != null) {
			Map localMap1 = paramPrivilege.getCommPrivilege_B();
			Map localMap2 = paramPrivilege.getCommPrivilege_S();
			if ((localMap1 == null) || (localMap2 == null)) {
				log.debug("没有加载商品权限映射!");
				return false;
			}
			if (paramShort1.shortValue() == 1) {
				Integer localInteger1 = (Integer) localMap1.get(paramString);
				if ((localInteger1.intValue() == 101) || ((paramShort2.shortValue() == 1) && (localInteger1.intValue() == 102))
						|| ((paramShort2.shortValue() == 2) && (localInteger1.intValue() == 103))) {
					bool = true;
				} else {
					bool = false;
				}
			} else if (paramShort1.shortValue() == 2) {
				Integer localInteger2 = (Integer) localMap2.get(paramString);
				if ((localInteger2.intValue() == 101) || ((paramShort2.shortValue() == 1) && (localInteger2.intValue() == 102))
						|| ((paramShort2.shortValue() == 2) && (localInteger2.intValue() == 103))) {
					bool = true;
				} else {
					bool = false;
				}
			}
		}
		return bool;
	}

	private static boolean checkFBreedPrivilege(Map paramMap1, Map paramMap2, Short paramShort1, Short paramShort2, Integer paramInteger,
			String paramString) {
		boolean bool = false;
		if (paramMap1 == null) {
			log.debug("没有设置交易商品品种的买权限，默认通过并检验代码的权限");
			return checkFCodePrivilege(paramMap2, paramShort1, paramShort2, paramString);
		}
		Integer localInteger = (Integer) paramMap1.get(paramInteger);
		if (paramShort1.shortValue() == 1) {
			if (((paramShort2.shortValue() == 1) && (localInteger.intValue() != 102))
					|| ((paramShort2.shortValue() == 2) && (localInteger.intValue() != 103)) || (localInteger.intValue() == 104)) {
				return false;
			}
			bool = checkFCodePrivilege(paramMap2, paramShort1, paramShort2, paramString);
		} else if (paramShort1.shortValue() == 2) {
			if (((paramShort2.shortValue() == 1) && (localInteger.intValue() != 202))
					|| ((paramShort2.shortValue() == 2) && (localInteger.intValue() != 203)) || (localInteger.intValue() == 204)) {
				return false;
			}
			bool = checkFCodePrivilege(paramMap2, paramShort1, paramShort2, paramString);
		}
		return bool;
	}

	private static boolean checkFCodePrivilege(Map paramMap, Short paramShort1, Short paramShort2, String paramString) {
		boolean bool = false;
		if (paramMap == null) {
			log.debug("没有设置交易商品的权限,默认为全权");
			return true;
		}
		Integer localInteger = (Integer) paramMap.get(paramString);
		if (paramShort1.shortValue() == 1) {
			if (localInteger.intValue() == 101) {
				bool = true;
			} else if ((localInteger.intValue() == 102) && (paramShort2.shortValue() == 1)) {
				bool = true;
			} else if ((localInteger.intValue() == 103) && (paramShort2.shortValue() == 2)) {
				bool = true;
			} else {
				bool = false;
			}
		} else if (localInteger.intValue() == 201) {
			bool = true;
		} else if ((localInteger.intValue() == 202) && (paramShort2.shortValue() == 1)) {
			bool = true;
		} else if ((localInteger.intValue() == 203) && (paramShort2.shortValue() == 2)) {
			bool = true;
		} else {
			bool = false;
		}
		return bool;
	}

	public static boolean checkTraderOperation(Privilege paramPrivilege, int paramInt) {
		if (log.isDebugEnabled()) {
			log.debug("checkTraderOperation method");
		}
		if (paramPrivilege != null) {
			List localList = paramPrivilege.getTraderPrivilege();
			if ((localList != null) && (localList.size() > 0)) {
				return localList.contains(Integer.valueOf(paramInt));
			}
			return true;
		}
		if (log.isDebugEnabled()) {
			log.debug("privilege is null");
		}
		return false;
	}

	public static boolean checkBreedPrivilege(Privilege paramPrivilege, Short paramShort, String paramString) throws Exception {
		boolean bool = false;
		if (log.isDebugEnabled()) {
			log.debug("Begin to check Breed Privilege ...");
		}
		Map localMap1 = paramPrivilege.getCommMap();
		if (localMap1 == null) {
			log.debug("没有加载商品品种映射表");
			return false;
		}
		Integer localInteger1 = (Integer) localMap1.get(paramString);
		if (localInteger1 != null) {
			Map localMap2 = paramPrivilege.getBreedPrivilege();
			if (localMap2 != null) {
				Integer localInteger2 = (Integer) localMap2.get(localInteger1);
				if ((localInteger2.intValue() == 101) || ((paramShort.shortValue() == 1) && (localInteger2.intValue() == 102))
						|| ((paramShort.shortValue() == 2) && (localInteger2.intValue() == 103))) {
					bool = true;
				} else {
					bool = false;
				}
			} else {
				log.debug("没有加载品种权限映射！");
			}
		} else {
			log.debug("找不到该商品对应的品种！");
		}
		return bool;
	}

	public static boolean checkFBreedPrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString) {
		log.debug("Begin to check Firm For Breed OrderPrivilege...");
		boolean bool = false;
		if (paramPrivilege != null) {
			Map localMap1 = paramPrivilege.getCommMap();
			if (localMap1 == null) {
				log.debug("没有设置相应的商品品种");
				return false;
			}
			Integer localInteger1 = (Integer) localMap1.get(paramString);
			String str = localInteger1.toString();
			Map localMap2;
			Integer localInteger2;
			if (paramShort1.shortValue() == 1) {
				localMap2 = paramPrivilege.getFBreedPrivilege_B();
				if (localMap2 == null) {
					log.debug("--------FBreedPriMap_B ==null--------");
					return true;
				}
				localInteger2 = (Integer) localMap2.get(str);
				if (localInteger2 == null) {
					log.debug("--------FBreedFlag_B ==null--------");
					return true;
				}
				if ((localInteger2.intValue() == 101) || ((localInteger2.intValue() == 102) && (paramShort2.shortValue() == 1))
						|| ((localInteger2.intValue() == 103) && (paramShort2.shortValue() == 2))) {
					log.debug("FBreedFlag_B:" + localInteger2 + "----------orderType:" + paramShort2);
					bool = true;
				}
			} else {
				localMap2 = paramPrivilege.getFBreedPrivilege_S();
				if (localMap2 == null) {
					return true;
				}
				localInteger2 = (Integer) localMap2.get(str);
				if (localInteger2 == null) {
					return true;
				}
				if ((localInteger2.intValue() == 201) || ((localInteger2.intValue() == 202) && (paramShort2.shortValue() == 1))
						|| ((localInteger2.intValue() == 203) && (paramShort2.shortValue() == 2))) {
					bool = true;
				}
			}
		}
		return bool;
	}

	public static boolean checkFCodePrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString) {
		log.debug("Begin to check Firm For Commodity_ID OrderPrivilege...");
		boolean bool = false;
		if (paramPrivilege != null) {
			Map localMap;
			Integer localInteger;
			if (paramShort1.shortValue() == 1) {
				localMap = paramPrivilege.getFCommPrivilege_B();
				if (localMap == null) {
					return true;
				}
				localInteger = (Integer) localMap.get(paramString);
				if (localInteger == null) {
					return true;
				}
				if ((localInteger.intValue() == 101) || ((localInteger.intValue() == 102) && (paramShort2.shortValue() == 1))
						|| ((localInteger.intValue() == 103) && (paramShort2.shortValue() == 2))) {
					bool = true;
				}
			} else {
				localMap = paramPrivilege.getFCommPrivilege_S();
				if (localMap == null) {
					return true;
				}
				localInteger = (Integer) localMap.get(paramString);
				if (localInteger == null) {
					return true;
				}
				if ((localInteger.intValue() == 201) || ((localInteger.intValue() == 202) && (paramShort2.shortValue() == 1))
						|| ((localInteger.intValue() == 203) && (paramShort2.shortValue() == 2))) {
					bool = true;
				}
			}
		}
		return bool;
	}

	public static boolean checkCusBreedPrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString1,
			String paramString2) {
		log.debug("Begin to Check Customer For Breed OrderPrivilege....");
		boolean bool = false;
		if (paramPrivilege != null) {
			Map localMap1 = paramPrivilege.getCommMap();
			if (localMap1 == null) {
				log.debug("没有设置相应的商品品种");
				return false;
			}
			Integer localInteger1 = (Integer) localMap1.get(paramString2);
			String str = localInteger1.toString();
			Map localMap2;
			Map localMap3;
			Integer localInteger2;
			if (paramShort1.shortValue() == 1) {
				localMap2 = paramPrivilege.getCusBreedPrivilege_B();
				if (localMap2 == null) {
					return true;
				}
				localMap3 = (Map) localMap2.get(paramString1);
				if (localMap3 == null) {
					return true;
				}
				localInteger2 = (Integer) localMap3.get(str);
				if (localInteger2 == null) {
					return true;
				}
				if ((localInteger2.intValue() == 101) || ((localInteger2.intValue() == 102) && (paramShort2.shortValue() == 1))
						|| ((localInteger2.intValue() == 103) && (paramShort2.shortValue() == 2))) {
					bool = true;
				}
			} else {
				localMap2 = paramPrivilege.getCusBreedPrivilege_S();
				if (localMap2 == null) {
					return true;
				}
				localMap3 = (Map) localMap2.get(paramString1);
				if (localMap3 == null) {
					return true;
				}
				localInteger2 = (Integer) localMap3.get(str);
				if (localInteger2 == null) {
					return true;
				}
				if ((localInteger2.intValue() == 201) || ((localInteger2.intValue() == 202) && (paramShort2.shortValue() == 1))
						|| ((localInteger2.intValue() == 203) && (paramShort2.shortValue() == 2))) {
					bool = true;
				}
			}
		}
		return bool;
	}

	public static boolean checkCusCommPrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString1,
			String paramString2) {
		log.debug("Begin to Check Customer For Commodity_ID OrderPrivilege....");
		boolean bool = false;
		try {
			if (paramPrivilege != null) {
				Map localMap1;
				Map localMap2;
				Integer localInteger;
				if (paramShort1.shortValue() == 1) {
					localMap1 = paramPrivilege.getCusCommPrivilege_B();
					if (localMap1 == null) {
						return true;
					}
					localMap2 = (Map) localMap1.get(paramString1);
					if (localMap2 == null) {
						return true;
					}
					localInteger = (Integer) localMap2.get(paramString2);
					if (localInteger == null) {
						return true;
					}
					if ((localInteger.intValue() == 101) || ((localInteger.intValue() == 102) && (paramShort2.shortValue() == 1))
							|| ((localInteger.intValue() == 103) && (paramShort2.shortValue() == 2))) {
						bool = true;
					}
				} else {
					localMap1 = paramPrivilege.getCusCommPrivilege_S();
					if (localMap1 == null) {
						return true;
					}
					localMap2 = (Map) localMap1.get(paramString1);
					if (localMap2 == null) {
						return true;
					}
					localInteger = (Integer) localMap2.get(paramString2);
					if (localInteger == null) {
						return true;
					}
					if ((localInteger.intValue() == 201) || ((localInteger.intValue() == 202) && (paramShort2.shortValue() == 1))
							|| ((localInteger.intValue() == 203) && (paramShort2.shortValue() == 2))) {
						bool = true;
					}
				}
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return bool;
	}

	private static boolean checkSettlePrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, int paramInt1, int paramInt2,
			String paramString1, String paramString2) {
		log.debug("Begin to Check SettlePrivilege....");
		log.debug("" + (paramInt1 == 3 ? "trader" : paramInt1 == 1 ? "firm" : "customer") + "," + (paramInt2 == 1 ? "breed" : "commodity") + "="
				+ paramString1);
		if (paramPrivilege == null) {
			log.error("privilege==null");
			return false;
		}
		Map localMap = null;
		if (paramInt2 == 1) {
			if (paramShort1.shortValue() == 1) {
				switch (paramInt1) {
				case 1:
					localMap = paramPrivilege.getfBreedSettlePrivilege_B();
					break;
				case 3:
					localMap = paramPrivilege.gettBreedSettlePrivilege_B();
					break;
				case 2:
					localMap = paramPrivilege.getCusBreedSettlePrivilege_B();
				}
			} else {
				switch (paramInt1) {
				case 1:
					localMap = paramPrivilege.getfBreedSettlePrivilege_S();
					break;
				case 3:
					localMap = paramPrivilege.gettBreedSettlePrivilege_S();
					break;
				case 2:
					localMap = paramPrivilege.getCusBreedSettlePrivilege_S();
				}
			}
		} else if (paramInt2 == 2) {
			if (paramShort1.shortValue() == 1) {
				switch (paramInt1) {
				case 1:
					localMap = paramPrivilege.getfCommSettlePrivilege_B();
					break;
				case 3:
					localMap = paramPrivilege.gettCommSettlePrivilege_B();
					break;
				case 2:
					localMap = paramPrivilege.getCusCommSettlePrivilege_B();
				}
			} else {
				switch (paramInt1) {
				case 1:
					localMap = paramPrivilege.getfCommSettlePrivilege_S();
					break;
				case 3:
					localMap = paramPrivilege.gettCommSettlePrivilege_S();
					break;
				case 2:
					localMap = paramPrivilege.getCusCommSettlePrivilege_S();
				}
			}
		}
		if ((localMap != null) && (paramInt1 == 2)) {
			localMap = (Map) localMap.get(paramString2);
		}
		if (localMap == null) {
			return false;
		}
		Integer localInteger = (Integer) localMap.get(paramString1);
		if (localInteger == null) {
			return false;
		}
		if (localInteger.intValue() == 101) {
			return true;
		}
		if (localInteger.intValue() == 104) {
			return false;
		}
		if ((paramShort2.shortValue() == 1) && (localInteger.intValue() == 102)) {
			return true;
		}
		return (paramShort2.shortValue() == 2) && (localInteger.intValue() == 103);
	}

	public static boolean checkFCommSettlePrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString) {
		return checkSettlePrivilege(paramPrivilege, paramShort1, paramShort2, 1, 2, paramString, null);
	}

	public static boolean checkTCommSettlePrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString) {
		return checkSettlePrivilege(paramPrivilege, paramShort1, paramShort2, 3, 2, paramString, null);
	}

	public static boolean checkCusCommSettlePrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString1,
			String paramString2) {
		return checkSettlePrivilege(paramPrivilege, paramShort1, paramShort2, 2, 2, paramString1, paramString2);
	}

	public static boolean checkFBreedSettlePrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString) {
		return checkSettlePrivilege(paramPrivilege, paramShort1, paramShort2, 1, 1, paramString, null);
	}

	public static boolean checkTBreedSettlePrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString) {
		return checkSettlePrivilege(paramPrivilege, paramShort1, paramShort2, 3, 1, paramString, null);
	}

	public static boolean checkCusBreedSettlePrivilege(Privilege paramPrivilege, Short paramShort1, Short paramShort2, String paramString1,
			String paramString2) {
		return checkSettlePrivilege(paramPrivilege, paramShort1, paramShort2, 2, 1, paramString1, paramString2);
	}

	public static boolean checkDirectTradePrivilege(Privilege paramPrivilege, String paramString, Short paramShort1, Short paramShort2) {
		Map localMap1 = paramPrivilege.getDirectBreeds();
		Map localMap2 = paramPrivilege.getCommMap();
		Integer localInteger = (Integer) localMap2.get(paramString);
		int i = 0;
		if (localMap1.get(paramString) == null) {
			return true;
		}
		String str = localMap1.get(paramString).toString();
		List localList = paramPrivilege.getDirectFirmBreeds();
		if ((localList != null) && (localList.size() > 0)) {
			for (int j = 0; j < localList.size(); j++) {
				Map localMap3 = (Map) localList.get(j);
				Iterator localIterator = localMap3.keySet().iterator();
				while (localIterator.hasNext()) {
					if (localInteger.toString().equalsIgnoreCase(localMap3.get(localIterator.next()).toString())) {
						i = 1;
						break;
					}
				}
			}
		}
		if ("1".equals(str)) {
			if (i != 0) {
				return true;
			}
			return ((paramShort1.shortValue() == 1) && (paramShort2.shortValue() == 2))
					|| ((paramShort1.shortValue() == 2) && (paramShort2.shortValue() == 1));
		}
		if ("2".equals(str)) {
			if (i != 0) {
				return true;
			}
			return ((paramShort1.shortValue() == 1) && (paramShort2.shortValue() == 1))
					|| ((paramShort1.shortValue() == 2) && (paramShort2.shortValue() == 2));
		}
		if ("3".equals(str)) {
			if (i != 0) {
				return (paramShort1.shortValue() == 1) && (paramShort2.shortValue() == 1);
			}
			return (paramShort1.shortValue() == 2) && (paramShort2.shortValue() == 1);
		}
		if ("4".equals(str)) {
			if (i != 0) {
				return (paramShort1.shortValue() == 2) && (paramShort2.shortValue() == 1);
			}
			return (paramShort1.shortValue() == 1) && (paramShort2.shortValue() == 1);
		}
		return false;
	}
}
