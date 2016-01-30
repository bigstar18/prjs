
package gnnt.MEBS.checkLogon.dao.front;

import gnnt.MEBS.checkLogon.dao.BaseDAOJdbc;
import gnnt.MEBS.checkLogon.dao.ObjectRowMapper;
import gnnt.MEBS.checkLogon.po.front.DictionaryPO;
import gnnt.MEBS.checkLogon.po.front.TraderPO;

import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * <P>类说明：用户验证用到的 DAO
 * <br/>
 * <br/>
 * </p>
 * 修改记录:
 * <br/>
 * <ul>
 * 
 * <li> 创建类                    |2014-4-24下午08:02:39|金网安泰 </li>
 * 
 * </ul>
 * @author liuzx
 */
public class FrontCheckDAO extends BaseDAOJdbc{

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
	 * 通过用户名获取交易员代码
	 * <br/><br/>
	 * @param userID 用户名
	 * @return String 交易员代码
	 */
	public String getTraderIDByUserID(String userID){
		String sql = "select traderID from M_Trader t  where t.userID=? ";
		Object[] params = new Object[] { userID };

		logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++){
			logger.debug("params[" + i + "]: " + params[i]);
		}

		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql,params);
		if (list != null && list.size() > 0) {
			return (String) list.get(0).get("traderID");
		}
		return null;
	}

	/**
	 * 
	 * 通过交易员编号查询交易员信息
	 * <br/><br/>
	 * @param traderID 交易员编号
	 * @return TraderPO 交易员信息
	 */
	public TraderPO getTraderByID(String traderID) {
		String sql = "select t.*,m.name as firmName from m_trader t,m_firm m where t.firmid=m.firmid and t.traderid=? ";
		Object[] params = new Object[] { traderID };

		logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++){
			logger.debug("params[" + i + "]: " + params[i]);
		}

		List<TraderPO> list = getJdbcTemplate().query(sql, new ObjectRowMapper<TraderPO>(new TraderPO()),
				params);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 
	 * 查询交易商状态
	 * <br/><br/>
	 * @param firmID 交易商代码
	 * @return String 状态 
	 */
	public String getFirmStatus(String firmID) {
		String sql = "select Status from M_Firm t  where t.FirmID=? ";
		Object[] params = new Object[] { firmID };

		logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++){
			logger.debug("params[" + i + "]: " + params[i]);
		}

		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql,params);
		if (list != null && list.size() > 0) {
			return (String)list.get(0).get("Status");
		}
		return null;
	}

	/**
	 * 
	 * 查询交易员模块权限状态
	 * <br/><br/>
	 * @param traderID 交易员代码
	 * @param moduleID 模块编号
	 * @return String 权限 Y 表示有权限
	 */
	public String getTraderModuleEnable(String traderID,int moduleID){
		String sql = "select Enabled from M_TraderModule m where  m.traderid=? and m.moduleid=?";
		Object[] params = new Object[]{traderID,moduleID};

		logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++){
			logger.debug("params[" + i + "]: " + params[i]);
		}

		List<Map<String,Object>> list = getJdbcTemplate().queryForList(sql,params);

		if(list != null && list.size() > 0){
			return (String)list.get(0).get("Enabled");
		}

		return null;
	}

	/**
	 * 
	 * 修改交易员登陆后的登录信息
	 * <br/><br/>
	 * @param trustKey 信任 KEY
	 * @param lastIP 上次登录 IP
	 * @param traderID 交易员代码
	 */
	public void updateTraderLogonInfo(String trustKey,String lastIP,String traderID){
		String sql = "update m_trader set trustkey=?,lastip=?,lasttime = sysdate where traderid = ?";
		Object[] params = new Object[]{trustKey,lastIP,traderID};
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};

		logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++){
			logger.debug("params[" + i + "]: " + params[i]);
		}

		getJdbcTemplate().update(sql,params,types);
	}

	/**
	 * 
	 * 修改交易员密码
	 * <br/><br/>
	 * @param traderID 交易员代码
	 * @param password 交易员密码
	 */
	public void updateTraderPassword(String traderID,String password){
		String sql = "update m_trader set password=?,forceChangePwd=0 where traderid = ?";
		Object[] params = new Object[]{password,traderID};
		int[] types = new int[]{Types.VARCHAR,Types.VARCHAR};

		logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++){
			logger.debug("params[" + i + "]: " + params[i]);
		}

		getJdbcTemplate().update(sql,params,types);
	}

	/**
	 * 
	 * 添加错误登录记录
	 * <br/><br/>
	 * @param traderID 交易员代码
	 * @param moduleID 模块编号
	 * @param logonIP 登录 IP 地址
	 */
	public void insertErrorLoginlog(String traderID,int moduleID,String logonIP){
		String sql = "insert into M_ErrorLoginLog(moduleid,logindate,traderId,ip) values(?,sysdate,?,?)";
		Object[] params = new Object[]{moduleID,traderID,logonIP};
		int[] types = new int[]{Types.NUMERIC,Types.VARCHAR,Types.VARCHAR};

		logger.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++){
			logger.debug("params[" + i + "]: " + params[i]);
		}

		getJdbcTemplate().update(sql,params,types);
	}

	/**
	 * 
	 * 通过交易员代码查询交易员当天错误登录次数
	 * <br/><br/>
	 * @param traderID 交易员代码
	 * @return int 错误登录次数
	 */
	public int getErrorLoginErrorNum(String traderID){
		String sql = "select count(*) from M_ErrorLoginLog e where e.TraderID=? and trunc(e.loginDate)=trunc(sysdate)";
		Object[] params = new Object[]{traderID};

		logger.debug("sql:"+sql);
		for(int i=0;i<params.length;i++){
			logger.debug("params["+i+"]:"+params[i]);
		}

		return getJdbcTemplate().queryForInt(sql,params);
	}

	/**
	 * 
	 * 清空交易员的错误信息<br/>
	 * 注：这里也会清空今天以前的所有交易员的错误登录信息
	 * <br/><br/>
	 * @param traderID 交易员代码
	 */
	public void clearErrorLogonLog(String traderID){
		String sql = "delete from M_ErrorLoginLog where TraderID=? or trunc(loginDate)<trunc(sysdate)";
		Object[] params = new Object[]{traderID};
		int[] types = new int[]{Types.VARCHAR};

		logger.debug("sql:"+sql);
		for(int i=0;i<params.length;i++){
			logger.debug("params["+i+"]:"+params[i]);
		}

		getJdbcTemplate().update(sql,params,types);
	}
}

