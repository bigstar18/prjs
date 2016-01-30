package gnnt.MEBS.common.mgr.service;

import gnnt.MEBS.common.mgr.dao.StandardDao;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.statictools.Tools;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 标准Service 实现增删改查等功能
 * 
 * @author xuejt
 * 
 */
@Service("com_standardService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class StandardService extends AbstractService {
	protected transient final Log logger = LogFactory.getLog(this.getClass());

	private StandardDao standardDao;

	@Resource(name = "com_standardDao")
	public void setDao(StandardDao standardDao) {
		super.setAbstractDao(standardDao);
		this.standardDao=standardDao;
	}

	/**
	 * 获取Dao 对象
	 * 
	 * @return 标准Dao实例
	 */
	public StandardDao getDao() {
		return standardDao;
	}

	/**
	 * 向数据库添加一条对应于一个业务对象实例的记录
	 * 
	 * @param entity
	 *            业务对象实例
	 */
	public void add(StandardModel entity) {
		logger.debug("enter add");
		standardDao.add(entity);
	}

	/**
	 * 向数据库更新一条对应于一个业务对象实例的记录
	 * 
	 * @param entity
	 *            业务对象实例
	 */
	public void update(StandardModel entity) {
		logger.debug("enter update");
		update(entity, false);
	}

	/**
	 * 向数据库更新一条对应于一个业务对象实例的记录
	 * 
	 * @param entity
	 *            业务对象实例
	 * @param isForceUpdate
	 *            是否强制更新 true：强制更新，将传入的业务对象直接写入数据库
	 *            false：非强制更新，传入的业务对象中为null的字段不更新到数据库
	 * 
	 * 
	 */
	public void update(StandardModel entity, boolean isForceUpdate) {
		// **********注********
		// 只能将传入的业务对象合并到游离态对象然后使用游离态对象进行更新反之则报错；因为hibernate不允许session中存在两个一样的游离态对象

		// 如果非强制更新则先从数据库中查询出原业务对象，然后将传入的非null字段替换到原业务对象的相应字段；更新到数据库
		if (!isForceUpdate) {
			// 克隆出业务对象，因为在调用this.get(entity)后
			// entity已经成为游离态对象所以字段已经和数据库对应,造成原业务对象则丢失
			StandardModel model = entity.clone();
			// 先从数据库中查询出游离态对象 ；entity变为游离态业务对象
			entity = this.get(entity);
			// 如果游离态业务对象不为空 则将传入的业务对象合并到游离态
			if (entity != null)
				Tools.CombinationValue(model, entity);
			// 如果数据库中没有entity对应的数据则将传入的对象赋给游离态对象
			else {
				entity = model;
			}
		}

		standardDao.update(entity);
	}

	/**
	 * 从数据库删除一条对应于一个业务对象的记录
	 * 
	 * @param entity
	 *            业务对象实例
	 */
	public void delete(StandardModel entity) {
		logger.debug("enter delete");
		standardDao.delete(entity);
	}

	/**
	 * 从数据库中删除业务对象实例集合<BR/>
	 * 内部操作为循环删除，所以性能较低；如果操作量过大并且业务对象包含主键请使用bulkDelete方法批量删除
	 * 
	 * @param entities
	 *            业务对象数据集合
	 */
	public void deleteBYBulk(Collection<StandardModel> entities) {
		logger.debug("enter deleteBYBulk entities");
		standardDao.deleteBYBulk(entities);
	}

	/**
	 * 通过sql 批量删除<br>
	 * 使用此方法 业务对象实例不能为空 且entity.fetchPKey().getKey()有返回值
	 * 
	 * @param entity
	 *            业务对象实例
	 * @param ids
	 *            主键值数组
	 */
	public void deleteBYBulk(StandardModel entity, final Object[] ids) {
		logger.debug("enter deleteBYBulk ids");
		if (ids == null) {
			return;
		}

		if (entity == null) {
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
		}

		if (entity.fetchPKey() == null || entity.fetchPKey().getKey() == null
				|| entity.fetchPKey().getKey().length() == 0) {
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
		}

		standardDao.deleteBYBulk(entity, ids);
	}

	/**
	 * 通过sql批量修改某列的值
	 * 
	 * @param entity
	 *            实体对象
	 * @param columnAssignSql
	 *            要修改的列以及对应的值 (列名=列值)
	 * @param ids
	 *            主键值数组
	 */
	public void updateBYBulk(StandardModel entity, String columnAssignSql,
			final Object[] ids) {
		logger.debug("enter updateBYBulk ids");
		if (ids == null) {
			return;
		}
		if ("".equals(columnAssignSql)) {
			throw new IllegalArgumentException("要修改的列以及对应的值未知，不允许通过主键数组批量修改！");
		}
		if (entity == null) {
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量修改！");
		}

		if (entity.fetchPKey() == null || entity.fetchPKey().getKey() == null
				|| entity.fetchPKey().getKey().length() == 0) {
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量修改！");
		}

		standardDao.updateBYBulk(entity, columnAssignSql, ids);
	}

	/**
	 * 通过sql来针对数据库操作
	 * 
	 * @param sql
	 *            传入sql对数据库操作
	 */
	public void executeUpdateBySql(final String sql) {
		standardDao.executeUpdateBySql(sql);
	}

	/**
	 * 执行存储过程
	 * 
	 * @param procedureName
	 *            存储过程名称
	 * @param map
	 *            key:存储过程变量名 value:变量对应的值
	 * @return 如果查询结果有多个值则抛出错误； 如果查询结果有且只有一个值,返回一个object； 如果没值,返回null
	 * @throws Exception
	 */
	public Object executeProcedure(final String procedureName,
			final Object[] params) throws Exception {
		return standardDao.executeProcedure(procedureName, params);
	}


}
