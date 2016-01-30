package gnnt.MEBS.common.front.dao.jdbc.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import gnnt.MEBS.common.front.common.Page;
import gnnt.MEBS.common.front.common.PageRequest;
import gnnt.MEBS.common.front.common.QueryConditions;
import gnnt.MEBS.common.front.dao.jdbc.DAO;

public class BaseDAOJdbc extends JdbcDaoSupport implements DAO {
	protected final Log log = LogFactory.getLog(getClass());

	public Page<?> getPage(String paramString, PageRequest<?> paramPageRequest, RowMapper<?> paramRowMapper) {
		Object[] arrayOfObject = null;
		if (paramPageRequest != null) {
			Object localObject;
			if ((paramPageRequest.getFilters() instanceof String)) {
				localObject = (String) paramPageRequest.getFilters();
				paramString = paramString + (String) localObject;
			} else if ((paramPageRequest.getFilters() instanceof QueryConditions)) {
				localObject = (QueryConditions) paramPageRequest.getFilters();
				if ((localObject != null) && (((QueryConditions) localObject).getFieldsSqlClause() != null)) {
					arrayOfObject = ((QueryConditions) localObject).getValueArray();
					paramString = paramString + " and " + ((QueryConditions) localObject).getFieldsSqlClause();
				}
			}
		}
		return queryBySQL(paramString, arrayOfObject, paramPageRequest, paramRowMapper);
	}

	public Page<?> queryBySQL(String paramString, Object[] paramArrayOfObject, PageRequest<?> paramPageRequest, RowMapper<?> paramRowMapper) {
		this.logger.debug("Query Start!");
		int i = 0;
		this.logger.debug("select count(*) cnt from (" + paramString + ") t");
		if (paramArrayOfObject != null) {
			i = getJdbcTemplate().queryForInt("select count(*) cnt from (" + paramString + ") t", paramArrayOfObject);
		} else {
			i = getJdbcTemplate().queryForInt("select count(*) cnt from (" + paramString + ") t");
		}
		if (paramPageRequest != null) {
			int j = paramPageRequest.getPageSize() > 0 ? 1 : 0;
			String localObject2 = " ";
			if ((paramPageRequest.getSortColumns() != null) && (paramPageRequest.getSortColumns().length() > 0)) {
				localObject2 = " " + paramPageRequest.getSortColumns();
			}
			int k = (paramPageRequest.getPageNumber() - 1) * paramPageRequest.getPageSize() + 1;
			int m = paramPageRequest.getPageNumber() * paramPageRequest.getPageSize();
			if (j != 0) {
				String str = "select * from (select rownum num,t.* from ( select * from (" + paramString + ") " + (String) localObject2 + ") t ) "
						+ "where num between " + k + " and " + m;
				paramString = str;
			} else {
				paramString = paramString + (String) localObject2;
			}
		}
		Object localObject1 = null;
		if ((paramPageRequest != null) && (i == 0)) {
			localObject1 = new ArrayList();
		} else {
			if (paramArrayOfObject != null) {
				if (paramRowMapper != null) {
					localObject1 = getJdbcTemplate().query(paramString, paramArrayOfObject, paramRowMapper);
				} else {
					localObject1 = getJdbcTemplate().queryForList(paramString, paramArrayOfObject);
				}
			} else if (paramRowMapper != null) {
				localObject1 = getJdbcTemplate().query(paramString, paramRowMapper);
			} else {
				localObject1 = getJdbcTemplate().queryForList(paramString);
			}
			this.logger.debug(paramString);
		}
		Object localObject2 = new Page(paramPageRequest.getPageNumber(), paramPageRequest.getPageSize(), i, (List) localObject1);
		return (Page<?>) localObject2;
	}
}
