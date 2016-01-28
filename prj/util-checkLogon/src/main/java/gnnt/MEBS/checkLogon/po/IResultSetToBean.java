package gnnt.MEBS.checkLogon.po;

import java.sql.ResultSet;

/**
 * 记录集反射到 bean
 * @author xuejt
 *
 */
public interface IResultSetToBean {
	/**
	 * 记录集反射到bean 
	 * @param bean Clone Class
	 * @param rs   记录集
	 * @return     将结果集填充到 Clone对象后的实例
	 */
	public Clone resultSetToBean(Clone bean, ResultSet rs);
}
