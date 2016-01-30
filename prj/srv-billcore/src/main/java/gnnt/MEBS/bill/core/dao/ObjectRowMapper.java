package gnnt.MEBS.bill.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import gnnt.MEBS.bill.core.po.Clone;
import gnnt.MEBS.bill.core.reflect.IResultSetToBean;
import gnnt.MEBS.bill.core.reflect.ResultSetToBeanByField;

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

	public T mapRow(ResultSet paramResultSet, int paramInt) throws SQLException {
		return (T) this.rTb.resultSetToBean(this.object, paramResultSet);
	}
}
