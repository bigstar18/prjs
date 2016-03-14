package gnnt.MEBS.timebargain.manage.dao.jdbc;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gnnt.MEBS.delivery.model.Dealer;
import gnnt.MEBS.timebargain.manage.dao.SettleprivilegeDao;
import gnnt.MEBS.timebargain.manage.model.Settleprivilege;
import gnnt.MEBS.timebargain.manage.util.QueryConditions;

public class SettleprivilegeDAOJdbc extends BaseDAOJdbc implements SettleprivilegeDao {
	private Log log = LogFactory.getLog(SettleprivilegeDAOJdbc.class);

	public void deleteSettleprivilegeById(long paramLong) {
		String str = "delete from T_A_SETTLEPRIVILEGE where id = " + paramLong;
		this.log.debug("sql: " + str);
		try {
			getJdbcTemplate().update(str);
		} catch (Exception localException) {
			throw new RuntimeException("删除失败！");
		}
	}

	public List getSettleprivilege(long paramLong) {
		String str = "select * from t_a_settleprivilege where id=?";
		Object[] arrayOfObject = { Long.valueOf(paramLong) };
		List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
		return localList;
	}

	public List<Settleprivilege> getSettleprivileges(QueryConditions paramQueryConditions) {
		String str = "select * from T_A_SETTLEPRIVILEGE";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		return getJdbcTemplate().queryForList(str, arrayOfObject, Settleprivilege.class);
	}

	public long getId() {
		String str = "select max(id) from t_a_settleprivilege";
		return getJdbcTemplate().queryForLong(str);
	}

	public void insertSettleprivilege(Settleprivilege paramSettleprivilege) {
		String str = "insert into t_a_settleprivilege(id, type, typeid, kind, kindid, privilegecode_b, privilegecode_s)values(?,?,?,?,?,?,?)";
		Object[] arrayOfObject = { Long.valueOf(getId() + 1L), Integer.valueOf(1), paramSettleprivilege.getTypeId(), Integer.valueOf(2),
				paramSettleprivilege.getKindId(), Integer.valueOf(paramSettleprivilege.getPrivilegecode_b()),
				Integer.valueOf(paramSettleprivilege.getPrivilegecode_s()) };
		getJdbcTemplate().update(str, arrayOfObject);
	}

	public void updateSettleprivilege(Settleprivilege paramSettleprivilege) {
		String str = "update t_a_settleprivilege set  typeid = ?, kindid = ?,privilegecode_b = ?, privilegecode_s = ? where id = ?";
		Object[] arrayOfObject = { paramSettleprivilege.getTypeId(), paramSettleprivilege.getKindId(),
				Integer.valueOf(paramSettleprivilege.getPrivilegecode_b()), Integer.valueOf(paramSettleprivilege.getPrivilegecode_s()),
				Long.valueOf(paramSettleprivilege.getId()) };
		getJdbcTemplate().update(str, arrayOfObject);
	}

	public Dealer getDealerByfirmId(String paramString) {
		String str = "select t.firmid,t.name,t.contactman linkman,t.phone tel from m_firm t where firmId=? ";
		Object[] arrayOfObject = { paramString };
		Dealer localDealer = null;
		List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
		if (localList.size() > 0) {
			localDealer = (Dealer) localList.get(0);
		}
		return localDealer;
	}

	public List getSettleprivilegeByFirmIdAndCommId(String paramString1, String paramString2) {
		String str = "select * from t_a_settleprivilege where typeId=? and kindId=?";
		Object[] arrayOfObject = { paramString1, paramString2 };
		List localList = getJdbcTemplate().queryForList(str, arrayOfObject);
		return localList;
	}
}
