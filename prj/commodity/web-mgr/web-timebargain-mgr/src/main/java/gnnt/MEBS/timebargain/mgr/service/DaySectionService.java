package gnnt.MEBS.timebargain.mgr.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.TradeTime;

@Service("com_daySectionService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class })
public class DaySectionService extends StandardService {
	public void updateDaySection(String week) {
		if ((week != null) && (!week.equals(""))) {
			String sql = "update t_a_daysection d  set d.status = 1 where  d.weekday in (" + week + ")";
			executeUpdateBySql(sql);
		}
	}

	public void insertSection(TradeTime tradeTime) {
		for (int i = 1; i < 8; i++) {
			String sql = "insert into T_A_DaySection (WeekDay,SectionID,Status,ModifyTime) values (" + i + ", " + tradeTime.getSectionID()
					+ ",0,sysdate)";
			executeUpdateBySql(sql);
		}
	}

	public void deleteDaySection(Object[] ids) {
		if (ids != null)
			for (Object id : ids) {
				String sectionId = id.toString();
				String sql = " delete from T_A_DaySection where SectionID = " + sectionId + " ";
				executeUpdateBySql(sql);
			}
	}

	public void updateDaySection(Map mapWeek) {
		for (int i = 1; i < 8; i++) {
			String ri = i + "";
			String[] weeks = (String[]) mapWeek.get(ri);
			if (weeks != null) {
				String sectionIDs = "";
				for (int j = 0; j < weeks.length; j++) {
					String sectionID = weeks[j];
					if (j != weeks.length - 1)
						sectionIDs = sectionIDs + sectionID + ",";
					else {
						sectionIDs = sectionIDs + sectionID;
					}

					String sql = "update T_A_DaySection set Status = 0, ModifyTime = sysdate where WeekDay = " + ri + " and SectionID = " + sectionID;
					executeUpdateBySql(sql);
				}

				List list = getListBySql("select sectionID from T_A_DaySection where WeekDay = " + ri + " and SectionID not in (" + sectionIDs + ")");
				if ((list != null) && (list.size() > 0)) {
					for (int k = 0; k < list.size(); k++) {
						Map map = (Map) list.get(k);
						String sectionIDN = (String) map.get("SECTIONID");

						String sql = "update T_A_DaySection set Status = 1, ModifyTime = sysdate where WeekDay = " + ri + " and SectionID = "
								+ sectionIDN;
						executeUpdateBySql(sql);
					}

				}

			} else {
				String sql = "update T_A_DaySection set Status = 1, ModifyTime = sysdate where WeekDay = " + ri;
				executeUpdateBySql(sql);
			}
		}
	}

	public int checkTradeTime(String sectionId) {
		int result = 0;

		List breedTradePropList = getListBySql("select * from T_A_BreedTradeProp  where sectionid = " + sectionId);
		List commodityTradePropList = getListBySql("select * from T_A_CommodityTradeProp where sectionid = " + sectionId);

		if ((breedTradePropList != null) && (breedTradePropList.size() > 0))
			result = -1;
		else if ((commodityTradePropList != null) && (commodityTradePropList.size() > 0)) {
			result = -2;
		}

		return result;
	}
}