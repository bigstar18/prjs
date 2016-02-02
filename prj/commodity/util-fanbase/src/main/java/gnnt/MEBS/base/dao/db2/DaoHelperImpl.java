package gnnt.MEBS.base.dao.db2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import gnnt.MEBS.base.dao.DaoHelper;
import gnnt.MEBS.base.util.OrderField;
import gnnt.MEBS.base.util.PageInfo;

public class DaoHelperImpl extends JdbcDaoSupport implements DaoHelper {
	public List queryBySQL(String sql) {
		return queryBySQL(sql, null);
	}

	public List queryBySQL(String sql, Object[] params, PageInfo pageInfo) {
		return queryBySQL(sql, params, null, pageInfo, null);
	}

	public List queryBySQL(String sql, PageInfo pageInfo) {
		return queryBySQL(sql, null, null, pageInfo, null);
	}

	public List queryBySQL(String sql, Object[] params, int[] dataTypes, PageInfo pageInfo) {
		return queryBySQL(sql, params, null, pageInfo, null);
	}

	public List queryBySQL(String sql, Object[] params, PageInfo pageInfo, RowMapper rowMapper) {
		return queryBySQL(sql, params, null, pageInfo, rowMapper);
	}

	public List queryBySQL(String sql, Object[] params, int[] dataTypes, PageInfo pageInfo, RowMapper rowMapper) {
		int totalRecords = 0;
		if (pageInfo != null) {
			if (params != null) {
				totalRecords = getJdbcTemplate().queryForInt("select count(*) cnt from (" + sql + ") t", params);
			} else {
				totalRecords = getJdbcTemplate().queryForInt("select count(*) cnt from (" + sql + ") t");
			}
		}
		if (pageInfo != null) {
			boolean isPageQuery = pageInfo.getPageSize() > 0;

			int not_shown_records = totalRecords - (pageInfo.getPageNo() - 1) * pageInfo.getPageSize();
			if (pageInfo.getOrderFields() != null) {
				List orderFields = pageInfo.getOrderFields();
				if (isPageQuery) {
					String innerOrder = "";

					String outerOrder = "";
					for (int i = 0; i < orderFields.size(); i++) {
						OrderField of = (OrderField) orderFields.get(i);

						String fieldName = of.getOrderField();

						boolean isDesc = of.isOrderDesc();
						if (isDesc) {
							innerOrder = innerOrder + fieldName + " asc";
							outerOrder = outerOrder + fieldName + " desc";
						} else {
							innerOrder = innerOrder + fieldName + " desc";
							outerOrder = outerOrder + fieldName + " asc";
						}
						if (i != orderFields.size() - 1) {
							innerOrder = innerOrder + ",";
							outerOrder = outerOrder + ",";
						}
					}
					String pageSql = "select * from (select * from (" + sql + ") queryView order by " + innerOrder + " FETCH FIRST "
							+ not_shown_records + " ROW ONLY) innerTable order by " + outerOrder + " FETCH FIRST " + pageInfo.getPageSize()
							+ " ROW ONLY";

					sql = pageSql;
				} else {
					StringBuffer orderStmt = new StringBuffer();
					for (int i = 0; i < orderFields.size(); i++) {
						OrderField of = (OrderField) orderFields.get(i);

						String orderField = of.getOrderField();

						boolean isDesc = of.isOrderDesc();
						if (isDesc) {
							orderStmt = orderStmt.append(orderField).append(" desc");
						} else {
							orderStmt = orderStmt.append(orderField).append(" asc");
						}
						if (i != orderFields.size() - 1) {
							orderStmt.append(",");
						}
					}
					sql = sql + " order by " + orderStmt.toString();
				}
			}
		}
		List resultList = null;
		if ((pageInfo != null) && (totalRecords == 0)) {
			resultList = new ArrayList();
		} else {
			if (pageInfo != null) {
				this.logger.debug(sql);
			}
			if (params != null) {
				if (rowMapper != null) {
					if (dataTypes != null) {
						resultList = getJdbcTemplate().query(sql, params, dataTypes, rowMapper);
					} else {
						resultList = getJdbcTemplate().query(sql, params, rowMapper);
					}
				} else if (dataTypes != null) {
					resultList = getJdbcTemplate().queryForList(sql, params, dataTypes);
				} else {
					resultList = getJdbcTemplate().queryForList(sql, params);
				}
			} else if (rowMapper != null) {
				resultList = getJdbcTemplate().query(sql, rowMapper);
			} else {
				resultList = getJdbcTemplate().queryForList(sql);
			}
		}
		if (pageInfo != null) {
			int pageSize = pageInfo.getPageSize();
			int pages;
			if (pageSize > 0) {
				if (totalRecords % pageSize == 0) {
					pages = totalRecords / pageSize;
				} else {
					if (totalRecords < pageSize) {
						pages = 1;
					} else {
						pages = totalRecords / pageSize + 1;
					}
				}
			} else {
				pages = 1;
			}
			pageInfo.setTotalRecords(totalRecords);
			pageInfo.setTotalPages(pages);
		}
		return resultList;
	}

	public void updateBySQL(String sql) {
		this.logger.debug(sql);
		if (sql != null) {
			getJdbcTemplate().update(sql);
		}
	}

	public void updateBySQL(String sql, Object[] params, int[] dataTypes) {
		this.logger.debug(sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		if (sql != null) {
			getJdbcTemplate().update(sql, params, dataTypes);
		}
	}

	public Object queryForObject(String sql, Object[] params, Class objClass) {
		this.logger.debug(sql);
		Object obj = null;
		try {
			obj = getJdbcTemplate().queryForObject(sql, params, objClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public int queryForInt(String sql, Object[] params) {
		this.logger.debug(sql);
		return getJdbcTemplate().queryForInt(sql, params);
	}

	public void executeStoredSubprogram(String storedSubprogram) {
		if (storedSubprogram == null) {
			return;
		}
		CallableStatementCallback cb = new CallableStatementCallback() {
			public Object doInCallableStatement(CallableStatement cs) throws SQLException {
				cs.execute();
				return null;
			}
		};
		String sql = "{call " + storedSubprogram + " }";
		this.logger.debug(sql);
		getJdbcTemplate().execute(sql, cb);
	}

	public int callStoredProcedure(String sp_call_str) {
		try {
			Connection conn = getJdbcTemplate().getDataSource().getConnection();
			CallableStatement cstmt = conn.prepareCall("{?=call " + sp_call_str + "}");
			cstmt.registerOutParameter(1, 4);
			cstmt.execute();
			return cstmt.getInt(1);
		} catch (SQLException se) {
			this.logger.error("Call Stored Procedure Failed!" + se.getMessage());
		}
		return -1;
	}
}
