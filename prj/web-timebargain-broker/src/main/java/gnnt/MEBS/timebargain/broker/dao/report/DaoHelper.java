package gnnt.MEBS.timebargain.broker.dao.report;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class DaoHelper extends JdbcDaoSupport {
	public List queryBySQL(String paramString) {
		return queryBySQL(paramString, null);
	}

	public List queryBySQL(String paramString, Object[] paramArrayOfObject, PageInfo paramPageInfo) {
		return queryBySQL(paramString, paramArrayOfObject, null, paramPageInfo, null);
	}

	public List queryBySQL(String paramString, PageInfo paramPageInfo) {
		return queryBySQL(paramString, null, null, paramPageInfo, null);
	}

	public List queryBySQL(String paramString, Object[] paramArrayOfObject, int[] paramArrayOfInt, PageInfo paramPageInfo) {
		return queryBySQL(paramString, paramArrayOfObject, null, paramPageInfo, null);
	}

	public List queryBySQL(String paramString, Object[] paramArrayOfObject, PageInfo paramPageInfo, RowMapper paramRowMapper) {
		return queryBySQL(paramString, paramArrayOfObject, null, paramPageInfo, paramRowMapper);
	}

	public List queryBySQL(String paramString, Object[] paramArrayOfObject, int[] paramArrayOfInt, PageInfo paramPageInfo, RowMapper paramRowMapper) {
		if (paramArrayOfObject != null)
			for (int i = 0; i < paramArrayOfObject.length; i++) {
				Object localObject1 = paramArrayOfObject[i];
				if ((localObject1 instanceof Date))
					paramArrayOfObject[i] = new Timestamp(((Date) localObject1).getTime());
				this.logger.debug("param[" + i + "]:" + paramArrayOfObject[i]);
			}
		this.logger.debug("Query Start!");
		int i = 0;
		if (paramPageInfo != null) {
			if (paramArrayOfObject != null)
				i = getJdbcTemplate().queryForInt("select count(*) cnt from (" + paramString + ") t", paramArrayOfObject);
			else
				i = getJdbcTemplate().queryForInt("select count(*) cnt from (" + paramString + ") t");
			this.logger.debug("select count(*) cnt from (" + paramString + ") t");
		}
		int k;
		if (paramPageInfo != null) {
			int j = paramPageInfo.getPageSize() > 0 ? 1 : 0;
			k = i - (paramPageInfo.getPageNo() - 1) * paramPageInfo.getPageSize();
			if (paramPageInfo.getOrderFields() != null) {
				List localList = paramPageInfo.getOrderFields();
				int n = (paramPageInfo.getPageNo() - 1) * paramPageInfo.getPageSize() + 1;
				int i1 = paramPageInfo.getPageNo() * paramPageInfo.getPageSize();
				Object localObject3;
				OrderField localOrderField;
				String str2;
				boolean bool;
				if (j != 0) {
					localObject3 = "";
					for (int i2 = 0; i2 < localList.size(); i2++) {
						localOrderField = (OrderField) localList.get(i2);
						str2 = localOrderField.getOrderField();
						bool = localOrderField.isOrderDesc();
						if (bool)
							localObject3 = (String) localObject3 + str2 + " desc";
						else
							localObject3 = (String) localObject3 + str2 + " asc";
						if (i2 != localList.size() - 1)
							localObject3 = (String) localObject3 + ",";
					}
					String str1 = "select * from (select rownum num,t.* from ( select * from (" + paramString + ") order by " + (String) localObject3
							+ ") t ) " + "where num between " + n + " and " + i1;
					paramString = str1;
				} else {
					localObject3 = new StringBuffer();
					for (int i3 = 0; i3 < localList.size(); i3++) {
						localOrderField = (OrderField) localList.get(i3);
						str2 = localOrderField.getOrderField();
						bool = localOrderField.isOrderDesc();
						if (bool)
							localObject3 = ((StringBuffer) localObject3).append(str2).append(" desc");
						else
							localObject3 = ((StringBuffer) localObject3).append(str2).append(" asc");
						if (i3 != localList.size() - 1)
							((StringBuffer) localObject3).append(",");
					}
					paramString = paramString + " order by " + ((StringBuffer) localObject3).toString();
				}
			}
		}
		List localObject2 = null;
		if ((paramPageInfo != null) && (i == 0)) {
			localObject2 = new ArrayList();
		} else {
			if (paramArrayOfObject != null) {
				if (paramRowMapper != null) {
					if (paramArrayOfInt != null)
						localObject2 = getJdbcTemplate().query(paramString, paramArrayOfObject, paramArrayOfInt, paramRowMapper);
					else
						localObject2 = getJdbcTemplate().query(paramString, paramArrayOfObject, paramRowMapper);
				} else if (paramArrayOfInt != null)
					localObject2 = getJdbcTemplate().queryForList(paramString, paramArrayOfObject, paramArrayOfInt);
				else
					localObject2 = getJdbcTemplate().queryForList(paramString, paramArrayOfObject);
			} else if (paramRowMapper != null)
				localObject2 = getJdbcTemplate().query(paramString, paramRowMapper);
			else
				localObject2 = getJdbcTemplate().queryForList(paramString);
			this.logger.debug(paramString);
		}
		if (paramPageInfo != null) {
			int m = paramPageInfo.getPageSize();
			if (m > 0) {
				if (i % m == 0)
					k = i / m;
				else if (i < m)
					k = 1;
				else
					k = i / m + 1;
			} else
				k = 1;
			paramPageInfo.setTotalRecords(i);
			paramPageInfo.setTotalPages(k);
		}
		return localObject2;
	}

	public void updateBySQL(String paramString) {
		this.logger.debug(paramString);
		if (paramString != null)
			getJdbcTemplate().update(paramString);
	}

	public void updateBySQL(String paramString, Object[] paramArrayOfObject, int[] paramArrayOfInt) {
		this.logger.debug(paramString);
		for (int i = 0; i < paramArrayOfObject.length; i++)
			this.logger.debug("params[" + i + "]: " + paramArrayOfObject[i]);
		if (paramString != null)
			getJdbcTemplate().update(paramString, paramArrayOfObject, paramArrayOfInt);
	}

	public int updateBySQL(String paramString, Object[] paramArrayOfObject) {
		this.logger.debug(paramString);
		int i = 0;
		for (int j = 0; j < paramArrayOfObject.length; j++)
			this.logger.debug("params[" + j + "]: " + paramArrayOfObject[j]);
		if (paramString != null)
			i = getJdbcTemplate().update(paramString, paramArrayOfObject);
		return i;
	}

	public int queryForInt(String paramString, Object[] paramArrayOfObject) {
		if (paramArrayOfObject != null)
			for (int i = 0; i < paramArrayOfObject.length; i++) {
				Object localObject = paramArrayOfObject[i];
				if ((localObject instanceof Date))
					paramArrayOfObject[i] = new Timestamp(((Date) localObject).getTime());
				this.logger.debug("param[" + i + "]:" + paramArrayOfObject[i]);
			}
		this.logger.debug(paramString);
		return getJdbcTemplate().queryForInt(paramString, paramArrayOfObject);
	}

	public int queryForInt(String paramString) {
		this.logger.debug(paramString);
		return getJdbcTemplate().queryForInt(paramString);
	}

	public long queryForLong(String paramString) {
		this.logger.debug(paramString);
		return getJdbcTemplate().queryForLong(paramString);
	}

	public Object queryForObject(String paramString, Object[] paramArrayOfObject, Class paramClass) {
		this.logger.debug(paramString);
		Object localObject = null;
		try {
			localObject = getJdbcTemplate().queryForObject(paramString, paramArrayOfObject, paramClass);
		} catch (Exception localException) {
			this.logger.error(localException);
		}
		return localObject;
	}

	public Object queryForObject(String paramString, Class paramClass) {
		this.logger.debug(paramString);
		Object localObject = null;
		try {
			localObject = getJdbcTemplate().queryForObject(paramString, paramClass);
		} catch (Exception localException) {
			this.logger.error(localException);
		}
		return localObject;
	}

	public Object queryForObject(String paramString, Object[] paramArrayOfObject, RowMapper paramRowMapper) {
		this.logger.debug(paramString);
		Object localObject = null;
		try {
			localObject = getJdbcTemplate().queryForObject(paramString, paramArrayOfObject, paramRowMapper);
		} catch (Exception localException) {
			this.logger.error(localException);
		}
		return localObject;
	}

	public List query(String paramString, Object[] paramArrayOfObject, int[] paramArrayOfInt, RowMapper paramRowMapper) {
		this.logger.debug(paramString);
		List localList = null;
		try {
			localList = getJdbcTemplate().query(paramString, paramArrayOfObject, paramArrayOfInt, paramRowMapper);
		} catch (Exception localException) {
			this.logger.error(localException);
		}
		return localList;
	}

	public void executeStoredSubprogram(String paramString) {
		if (paramString == null)
			return;
		CallableStatementCallback local1 = new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement paramAnonymousCallableStatement) throws SQLException {
				paramAnonymousCallableStatement.execute();
				return null;
			}
		};
		String str = "{call " + paramString + " }";
		this.logger.debug(str);
		getJdbcTemplate().execute(str, local1);
	}

	public int callStoredProcedure(String paramString) {
		try {
			Connection localConnection = getJdbcTemplate().getDataSource().getConnection();
			CallableStatement localCallableStatement = localConnection.prepareCall("{?=call " + paramString + "}");
			localCallableStatement.registerOutParameter(1, 4);
			localCallableStatement.execute();
			return localCallableStatement.getInt(1);
		} catch (SQLException localSQLException) {
			this.logger.error("Call Stored Procedure Failed!" + localSQLException.getMessage());
		}
		return -1;
	}
}