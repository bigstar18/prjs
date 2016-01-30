package gnnt.MEBS.common.mgr.service;

import gnnt.MEBS.common.mgr.dao.QueryDao;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 实现查询相关方法<br>
 * 使用查询数据源进行查询
 * 
 * @author xuejt
 * 
 */
@Service("com_queryService")
public class QueryService extends AbstractService {
	protected transient final Log logger = LogFactory.getLog(this.getClass());

	@Resource(name = "com_queryDao")
	public void setDao(QueryDao queryDao) {
		super.setAbstractDao(queryDao);
	}
}
