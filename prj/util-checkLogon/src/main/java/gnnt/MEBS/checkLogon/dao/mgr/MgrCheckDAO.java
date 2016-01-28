
package gnnt.MEBS.checkLogon.dao.mgr;

import gnnt.MEBS.checkLogon.dao.BaseDAOJdbc;
import gnnt.MEBS.checkLogon.dao.ObjectRowMapper;
import gnnt.MEBS.checkLogon.po.mgr.User;

import java.sql.Types;
import java.util.List;

/**
 * <P>类说明：后台验证用到的 DAO
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-29上午09:29:02|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class MgrCheckDAO extends BaseDAOJdbc{

	/**
	 * 
	 * 添加全局日志
	 * @param operator 操作人
	 * @param operatorIP 操作人IP
	 * @param operatorType 操作类型
	 * @param operatorContent 操作内容
	 * @param operatorResult 操作结果
	 */
	public void addGlobalLog(String operator, String operatorIP,
			int operatorType, String operatorContent, int operatorResult) {
		String sql = "insert into c_globallog_all(id,operator,operatetime,operatetype,operateip,operatecontent,operateresult) "
				+ " values(SEQ_C_GLOBALLOG.Nextval,?, sysdate,?,?,?,?)";
		Object[] params = new Object[] { operator, operatorType, operatorIP,
				operatorContent, operatorResult };
		int[] types = new int[] { Types.VARCHAR, Types.INTEGER, Types.VARCHAR,
				Types.VARCHAR, Types.INTEGER };

		logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++){
			logger.debug("params[" + i + "]: " + params[i]);
		}

		getJdbcTemplate().update(sql, params, types);
	}

	/**
	 * 
	 * 通过用户名获取用户信息
	 * <br/><br/>
	 * @param userID
	 * @return
	 */
	public User getUserByID(String userID){
		String sql = "select * from c_user t  where t.id=? ";
		Object[] params = new Object[] { userID };

		logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++)
			logger.debug("params[" + i + "]: " + params[i]);

		List<User> list = getJdbcTemplate().query(sql, new ObjectRowMapper<User>(new User()),
				params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * 修改管理员密码
	 * <br/><br/>
	 * @param userID 管理员编号
	 * @param password 新加密后密码
	 */
	public void changePassword(String userID,String password){
		String sql = "update c_user set password=? where id=? ";
		Object[] params = new Object[]{password,userID};
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR};

		logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++){
			logger.debug("params[" + i + "]: " + params[i]);
		}

		getJdbcTemplate().update(sql,params,types);
	}
}

