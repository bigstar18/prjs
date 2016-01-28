
package gnnt.MEBS.checkLogon.dao.warehouse;

import gnnt.MEBS.checkLogon.dao.BaseDAOJdbc;
import gnnt.MEBS.checkLogon.dao.ObjectRowMapper;
import gnnt.MEBS.checkLogon.po.warehouse.DictionaryPO;
import gnnt.MEBS.checkLogon.po.warehouse.User;

import java.sql.Types;
import java.util.List;

/**
 * <P>类说明：仓库系统验证用到的 DAO
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-29下午01:20:15|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class WarehouseCheckDAO extends BaseDAOJdbc{

	/**
	 * 
	 * 通过 key 值获取字典信息
	 * <br/><br/>
	 * @param key 字典配置 key 值
	 * @return
	 */
	public DictionaryPO getDictionaryByKey(String key){
		String sql = "select * from L_Dictionary where key=?";
		Object[] params = new Object[]{key};

		logger.debug("sql:"+sql);
		for(int i=0;i<params.length;i++){
			logger.debug("params["+i+"]:"+params[i]);
		}

		List<DictionaryPO> list = getJdbcTemplate().query(sql, params,new ObjectRowMapper<DictionaryPO>(new DictionaryPO()));

		if(list != null && list.size() > 0){
			return list.get(0);
		}

		return null;
	}

	/**
	 * 
	 * 通过用户编号查询用户信息
	 * <br/><br/>
	 * @param userID 用户编号
	 * @return User
	 */
	public User getUserByID(String userID){
		String sql = "select * from W_USER where userID=?";
		Object[] params = new Object[]{userID};

		logger.debug("sql:"+sql);
		for(int i=0;i<params.length;i++){
			logger.debug("params["+i+"]: "+params[i]);
		}

		List<User> list = getJdbcTemplate().query(sql, params, new ObjectRowMapper<User>(new User()));

		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * 修改用户密码
	 * <br/><br/>
	 * @param userID 用户编号
	 * @param password 加密后的新密码
	 */
	public void updateUserPassword(String userID,String password){
		String sql = "update W_USER set PASSWORD=? where UserID=?";
		Object[] params = new Object[]{password,userID};
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR};

		logger.debug("sql:"+sql);
		for(int i=0;i<params.length;i++){
			logger.debug("params["+i+"]: "+params[i]);
		}

		getJdbcTemplate().update(sql, params, types);
	}

	/**
	 * 
	 * 查询当天用户登录的错误次数
	 * <br/><br/>
	 * @param userID
	 * @return
	 */
	public int getErrorLoginErrorNum(String userID){
		String sql = "select count(*) from W_ErrorLoginLog where UserID=? and trunc(loginDate)=trunc(sysdate)";
		Object[] params = new Object[]{userID};

		logger.debug("sql:"+sql);
		for(int i=0;i<params.length;i++){
			logger.debug("params["+i+"]: "+params[i]);
		}

		return getJdbcTemplate().queryForInt(sql,params);
	}

	/**
	 * 
	 * 写操作日志
	 * <br/><br/>
	 * @param operator 操作人
	 * @param warehouseID 仓库编号
	 * @param operateType 操作类型
	 * @param operateIP 操作 IP
	 * @param operatorContent 操作内容
	 * @param operatorResult 操作结果 1 成功  0 失败
	 * @param operatorType 操作人类型
	 */
	public void addGlobalLog(String operator,String warehouseID,int operateType
			,String operateIP,String operatorContent,int operatorResult,String operatorType){
		String sql = "insert into W_GLOBALLOG_ALL " +
				"(ID,OPERATOR,warehouseID,OPERATETIME,OPERATETYPE,OPERATEIP,OPERATECONTENT,OPERATERESULT,OPERATORTYPE) " +
				"values (SEQ_W_GLOBALLOG_ALL.Nextval,?,?,sysdate,?,?,?,?,?)";
		Object[] params = new Object[]{operator,warehouseID,operateType,operateIP,operatorContent,operatorResult,operatorType};
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR};

		logger.debug("sql:"+sql);
		for(int i=0;i<params.length;i++){
			logger.debug("params["+i+"]: "+params[i]);
		}

		getJdbcTemplate().update(sql,params,types);
	}

	/**
	 * 
	 * 清空交易员的错误信息<br/>
	 * 注：这里也会清空今天以前的所有交易员的错误登录信息
	 * <br/><br/>
	 * @param userID
	 */
	public void clearErrorLogonLog(String userID){
		String sql = "delete from W_ErrorLoginLog where UserID=? or trunc(loginDate)=trunc(sysdate)";
		Object[] params = new Object[]{userID};

		logger.debug("sql:"+sql);
		for(int i=0;i<params.length;i++){
			logger.debug("params["+i+"]: "+params[i]);
		}

		getJdbcTemplate().update(sql, params);
	}
	
	/**
	 * 添加错误日志<br/>
	 * <br/>
	 *
	 * @param userID
	 * 			登录用户
	 * @param warehouseID
	 * 			仓库ID
	 */
	public void insertErrorLoginlog(String userID,String warehouseID,String ip){
		String sql = "insert into w_ErrorLoginLog(ERRORLOGINID,USERID,logindate,WAREHOUSEID,ip) values(SEQ_W_ERRORLOGINLOG.nextval,?,sysdate,?,?)";
		Object[] params = new Object[]{userID,warehouseID,ip};
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};

		logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++){
			logger.debug("params[" + i + "]: " + params[i]);
		}

		getJdbcTemplate().update(sql,params,types);
	}
}

