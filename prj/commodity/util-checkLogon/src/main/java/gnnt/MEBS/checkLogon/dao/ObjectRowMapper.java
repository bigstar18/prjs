package gnnt.MEBS.checkLogon.dao;



import gnnt.MEBS.checkLogon.po.Clone;
import gnnt.MEBS.checkLogon.po.IResultSetToBean;
import gnnt.MEBS.checkLogon.po.ResultSetToBeanByField;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ObjectRowMapper<T extends Clone> implements RowMapper<T> {
	private T object;
	
	//private IResultSetToBean rTb=new ResultSetToBeanBySetMethod();
	private IResultSetToBean rTb=new ResultSetToBeanByField();
	
	public IResultSetToBean getrTb() {
		return rTb;
	}

	public void setrTb(IResultSetToBean rTb) {
		this.rTb = rTb;
	}

	public ObjectRowMapper(T object) {
		this.object = object;
	}

	/*
	 * 该方法自动将数据库字段对应到Object中相应字段 要求：数据库与Object中字段名相同
	 */
	@SuppressWarnings("unchecked")
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		return (T)rTb.resultSetToBean(object, rs);
	}
}
