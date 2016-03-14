package gnnt.MEBS.common.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.common.dao.RightDao;
import gnnt.MEBS.common.model.Right;
import gnnt.MEBS.common.model.Role;
import gnnt.MEBS.common.model.User;
import gnnt.MEBS.common.security.util.IntegrateToMap;
import gnnt.MEBS.common.security.util.LikeHashMap;
import gnnt.MEBS.common.util.query.PageInfo;
import gnnt.MEBS.common.util.query.QueryConditions;

@Service("rightService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class RightService {
	@Autowired
	@Qualifier("rightDao")
	private RightDao rightDao;

	public void setRightDao(RightDao paramRightDao) {
		this.rightDao = paramRightDao;
	}

	public void addRight(Right paramRight) {
		this.rightDao.addRight(paramRight);
	}

	public Right getRightById(long paramLong) {
		return this.rightDao.getRightById(paramLong);
	}

	public void updateRight(Right paramRight) {
		this.rightDao.updateRight(paramRight);
	}

	public void deleteRight(Right paramRight) {
		this.rightDao.deleteRight(paramRight);
	}

	public Right getRightByFilter(long paramLong) {
		Right localRight = null;
		localRight = this.rightDao.getRightByFilter(paramLong, -1, 0);
		localRight.toRightSetIterator();
		return localRight;
	}

	public void updateDynamicRight(long paramLong) {
		this.rightDao.updateDynamicRight(paramLong);
	}

	public Map<String, Right> loadSpecialRight() {
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("type", "=", Integer.valueOf(-2));
		List localList = this.rightDao.getRightList(localQueryConditions, null);
		HashMap localHashMap = null;
		if (localList != null) {
			localHashMap = new HashMap();
			Iterator localIterator = localList.iterator();
			while (localIterator.hasNext()) {
				Right localRight = (Right) localIterator.next();
				if ((localRight.getUrl() != null) && (!"".equals(localRight.getUrl().trim()))) {
					localHashMap.put(localRight.getUrl(), localRight);
				}
			}
		}
		return localHashMap;
	}

	public LikeHashMap loadWildcardRight(boolean paramBoolean) {
		LikeHashMap localLikeHashMap = null;
		QueryConditions localQueryConditions = new QueryConditions();
		if (paramBoolean) {
			localQueryConditions.addCondition("url", "like", "%*%");
		} else {
			localQueryConditions.addCondition("url", "notLike", "%*%");
			localQueryConditions.addCondition("type", "!=", "-2");
		}
		List localList = getRightList(localQueryConditions, null);
		if (localList != null) {
			localLikeHashMap = new LikeHashMap();
			Iterator localIterator = localList.iterator();
			while (localIterator.hasNext()) {
				Right localRight = (Right) localIterator.next();
				localLikeHashMap.put(localRight.getUrl(), localRight);
			}
		}
		return localLikeHashMap;
	}

	public Right loadRightById(long paramLong) {
		Right localRight = this.rightDao.loadRightById(paramLong);
		localRight.toRightSetIterator();
		return localRight;
	}

	public List<Right> getRightList(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		return this.rightDao.getRightList(paramQueryConditions, paramPageInfo);
	}

	public Map<Right, Integer> compareRightsWithUser(Right paramRight, User paramUser) {
		Map<Right, Integer> localObject = new HashMap();
		if (paramUser != null) {
			Set localSet1 = paramUser.getRightSet();
			Set localSet2 = paramUser.getRoleSet();
			HashSet localHashSet = new HashSet();
			Iterator localIterator1 = localSet2.iterator();
			while (localIterator1.hasNext()) {
				Role localRole = (Role) localIterator1.next();
				Set localSet3 = localRole.getRightSet();
				Iterator localIterator2 = localSet3.iterator();
				while (localIterator2.hasNext()) {
					Right localRight = (Right) localIterator2.next();
					localHashSet.add(localRight);
				}
			}
			localObject = IntegrateToMap.sortRight(paramRight, localSet1, localHashSet);
		}
		return localObject;
	}

	public Map<Right, Integer> compareRightsWithRole(Right paramRight, Role paramRole) {
		Map<Right, Integer> localObject = new HashMap();
		if (paramRole != null) {
			Set localSet = paramRole.getRightSet();
			localObject = IntegrateToMap.sortRight(paramRight, null, localSet);
		}
		return localObject;
	}

	public Right getTreeRight() {
		Right localRight = null;
		localRight = this.rightDao.loadTreeRight(0L, -2, 0);
		return localRight;
	}
}
