package gnnt.MEBS.common.mgr.service;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.dao.AbstractDao;
import gnnt.MEBS.common.mgr.model.StandardModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 抽象Service 只提供查询相关方法
 * 
 * @author xuejt
 * 
 */
public abstract class AbstractService {
	protected transient final Log logger = LogFactory.getLog(this.getClass());

	private AbstractDao abstractDao;

	/**
	 * @param Dao
	 *            设置DAO
	 */
	public void setAbstractDao(AbstractDao abstractDao) {
		this.abstractDao = abstractDao;
	}

	/**
	 * 通过sql 批量查询<br>
	 * 使用此方法 业务对象实例不能为空 且entity.fetchPKey().getKey()有返回值
	 * 
	 * @param entity
	 *            业务对象实例
	 * @param ids
	 *            主键值数组
	 */
	public List<StandardModel> getListByBulk(StandardModel entity,
			final Object[] ids) {
		logger.debug("enter getListByBulk ids");
		return abstractDao.getListByBulk(entity, ids);
	}

	/**
	 * 获取 业务对象实例
	 * 
	 * @param entity
	 *            业务对象实例；
	 *            <UL>
	 *            <li>如果业务对象实例fetchPKey方法有返回值则通过主键查询，否则通过hibernare的配置文件中主键查询；</LI>
	 *            <li>如果既没有主键又没有配置则抛出异常</li>
	 *            <li>hibernare配置</li>
	 *            <li><composite-id></li>
	 *            <li><key-property name="composite1"></key-property></li>
	 *            <li><key-property name="composite2"></key-property></li>
	 *            <li></composite-id></li>
	 *            </UL>
	 * @return 查询结果
	 */
	public StandardModel get(StandardModel entity) {
		logger.debug("enter get");
		return abstractDao.get(entity);
	}

	/**
	 * 传入要清除生成缓存的对象从而重新加载
	 * 
	 * @param obj
	 */
	public void reload(Object obj) {
		abstractDao.getHibernateTemplate().flush();
		abstractDao.getHibernateTemplate().refresh(obj);
	}

	/**
	 * 通过标准sql语句查询<br>
	 * 查询方法统一使用get开头 以方便在spring配置文件中配置事务
	 * 
	 * @param sql
	 *            sql语句
	 * @param entity
	 *            业务对象实例
	 * @return 查询结果记录集
	 */
	public List<StandardModel> getListBySql(final String sql,
			final StandardModel entity) {
		logger
				.debug("enter getListBySql getListBySql(final String sql,final StandardModel entity)");
		return abstractDao.queryBySql(sql, entity);

	}

	/**
	 * 通过标准sql语句查询<br>
	 * 查询方法统一使用get开头 以方便在spring配置文件中配置事务
	 * 
	 * @param sql
	 *            sql语句
	 * @return 查询结果记录集 List元素为Map
	 */
	public List<Map<Object, Object>> getListBySql(final String sql) {
		logger.debug("enter getListBySql  getListBySql(final String sql) ");
		return abstractDao.queryBySql(sql);
	}

	/** 系统时间差 */
	private static long retime=0;

	/** 上次查询时间 */
	private static long lasttime=0;

	/**
	 * 通过sql语句获取系统时间<br>
	 * 查询方法统一使用get开头 以方便在spring配置文件中配置事务
	 * 
	 * @param sql
	 *            sql语句
	 * @return Date 系统时间
	 * @throws Exception
	 */
	public Date getSysDate() throws Exception {
		Date now = new Date();
		long pasttime = 1000*60;//经过多长时间查询一次
		Date date=new Date();
		if(now.getTime()-lasttime>pasttime || lasttime-now.getTime()>pasttime){
			lasttime = now.getTime();
			logger.debug("enter getSysDate  getSysDate() ");
			List<Map<Object, Object>> list = abstractDao
					.queryBySql("select to_char(sysdate,'yyyy-MM-dd HH24:MI:SS') as aa from dual");
			if (list != null && list.size() > 0) {
				Map<Object, Object> map = (Map<Object, Object>) list.get(0);
				String strDate = (String) map.get("AA");
				SimpleDateFormat formatDate = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				date = formatDate.parse(strDate);
				retime = date.getTime()-now.getTime();
			}
		}else{
			date = new Date(now.getTime()+retime);
		}
		return date;
	}
	
	/**
	 * 获取分页信息
	 * 
	 * @param pageRequest
	 *            分页请求信息
	 * @param entity
	 *            业务对象实例
	 * @return 分页信息
	 */
	public Page<StandardModel> getPage(PageRequest<?> pageRequest,
			StandardModel entity) {
		logger.debug("enter getPage");
		return abstractDao.getPage(pageRequest, entity);
	}

}
