package gnnt.MEBS.logonService.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gnnt.MEBS.logonService.po.Clone;
import gnnt.MEBS.logonService.po.relect.IResultSetToBean;
import gnnt.MEBS.logonService.po.relect.ResultSetToBeanByField;

public class ObjectRowMapper<T extends Clone> implements RowMapper<T> {
	private T object;
	private IResultSetToBean rTb = new ResultSetToBeanByField();

	public IResultSetToBean getrTb() {
		return this.rTb;
	}

	public void setrTb(IResultSetToBean paramIResultSetToBean) {
		this.rTb = paramIResultSetToBean;
	}

	public ObjectRowMapper(T paramT) {
		this.object = paramT;
	}

	/*
	 * 该方法自动将数据库字段对应到Object中相应字段 要求：数据库与Object中字段名相同
	 */
	@SuppressWarnings("unchecked")
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		return (T) rTb.resultSetToBean(object, rs);
	}
}