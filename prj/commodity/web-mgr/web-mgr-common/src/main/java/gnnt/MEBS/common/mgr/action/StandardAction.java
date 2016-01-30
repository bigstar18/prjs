package gnnt.MEBS.common.mgr.action;

import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.common.mgr.common.ReturnValue;
import gnnt.MEBS.common.mgr.model.LogCatalog;
import gnnt.MEBS.common.mgr.model.OperateLog;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.AbstractService;
import gnnt.MEBS.common.mgr.service.QueryService;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.Tools;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 标准Action类
 * 
 * @author xuejt
 * 
 */
@Controller("com_standardAction")
@Scope("request")
public class StandardAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = -2324265780415999469L;

	protected transient final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 变量开始前缀的变量名
	 */
	public static final String PARAMETERPREFIX = "gnnt_";

	/**
	 * 分页大小的变量名
	 */
	public static final String PAGESIZE = "pageSize";

	/**
	 * 当前页码的变量名
	 */
	public static final String PAGENUMBER = "pageNo";

	/**
	 * 排序列的变量名
	 */
	public static final String SORTCOLUMNS = "sortColumns";

	/**
	 * 是否使用查询库查询
	 */
	public static final String ISQUERYDB = "isQueryDB";

	/**
	 * 页面信息变量名
	 */
	public static final String PAGEINFO = "pageInfo";

	/**
	 * 页面信息变量名
	 */
	public static final String OLDPARAMETER = "oldParams";

	/**
	 * HttpServletRequest
	 */
	protected HttpServletRequest request;

	/**
	 * HttpServletResponse
	 */
	protected HttpServletResponse response;

	/**
	 * 业务对象实例
	 */
	protected StandardModel entity;

	/**
	 * 注入标准service实例
	 */
	@Autowired
	@Qualifier("com_standardService")
	private StandardService standardService;

	/**
	 * 注入标准queryservice实例
	 */
	@Autowired
	@Qualifier("com_queryService")
	private QueryService queryService;

	/**
	 * 通过struts配置文件设置业务对象名称 <br>
	 * 通过设置的名称 实例化业务对象实例 <br>
	 * 使用action类前 必须初始化业务对象实例否则增删改查等操作以及struts的反填操作都无法完成 <br>
	 * 配置方法如下
	 * <p>
	 * <action name="actionName" class="action" method="method"> <br>
	 * <param name="entityName">业务对象完整路径包括包名以及类名</param> <br>
	 * <result>*****.jsp</result> <br>
	 * </action> <br>
	 * 
	 * @param entityName
	 *            业务对象实体类对应的名称
	 */
	public void setEntityName(String entityName) {
		if (entityName == null || entityName.length() == 0) {
			throw new IllegalArgumentException("业务对象实体类名称不允许为null!");
		}
		if (entity == null) {
			try {
				entity = (StandardModel) Class.forName(entityName)
						.newInstance();
			} catch (Exception e) {
				logger
						.error("实例化" + entityName + "发生错误，错误信息："
								+ e.getMessage());
				logger.error(Tools.getExceptionTrace(e));
				throw new IllegalArgumentException("无法实例化" + entityName + "!");

			}
		}
	}

	/**
	 * ServletRequestAware 接口的方法 初始化Action实例时容器会调用此方法
	 */
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * ServletResponseAware 接口的方法 初始化Action实例时容器会调用此方法
	 */
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * 获取Action使用的service实例
	 * 
	 * @return service对象
	 */
	public StandardService getService() {
		return this.standardService;
	}
	
	/**
	 * 获取抽象Service
	 * 
	 * @param pageRequest
	 * @return 根据request中的是否使用查询库返回不同的Service
	 */
	public AbstractService getAbstractService(PageRequest<?> pageRequest) {
		// 根据传入的参数获取service
		AbstractService service = pageRequest.isQueryDB() ? getQueryService()
				: getService();
		return service;
	}

	/**
	 * 获取Action使用的queryService实例
	 * 
	 * @return queryService对象
	 */
	public QueryService getQueryService() {
		return this.queryService;
	}

	/**
	 * 设置Action使用的service实例
	 * 
	 * @param standardService
	 */
	public void setStandardService(StandardService standardService) {
		this.standardService = standardService;
	}

	/**
	 * 设置业务对象实例 <br>
	 * 使用struts2时 业务对象实例由struts组装；但是为了在junit测试时可以显示设置业务对象实例所以提供此方法
	 * 
	 * @param entity
	 */
	public void setEntity(StandardModel entity) {
		this.entity = entity;
	}

	/**
	 * 获取业务对象实例 <br>
	 * 为了使用junit测试方便所以提供此方法
	 * 
	 * @param entity
	 */
	public StandardModel getEntity() {
		return entity;
	}

	/**
	 * 审核类型；如果审核类型不为空则在添加、修改、删除操作时不直接执行操作而是将操作写入数据库等待审核
	 */
	private String auditType;

	/**
	 * 审核类型；如果审核类型不为空则在添加、修改、删除操作时不直接执行操作而是将操作写入数据库等待审核
	 * 
	 * @param auditType
	 *            审核类型；如果需要 通过struts配置文件传入
	 */
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	/**
	 * 审核类型；如果审核类型不为空则在添加、修改、删除操作时不直接执行操作而是将操作写入数据库等待审核
	 * 
	 */
	public String getAuditType() {
		return this.auditType;
	}

	/**
	 * 定向到添加界面
	 * 
	 * @return
	 */
	public String forwardAdd() {
		return SUCCESS;
	}

	/**
	 * 向数据库添加一条对应于一个业务对象实例的记录
	 * 
	 * @return
	 */
	public String add() {
		logger.debug("enter add");
		getService().add(entity);
		// 添加成功
		addReturnValue(1, 119901);
		return SUCCESS;
	}

	/**
	 * 通过ID获取业务对象实例
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String viewById() throws Exception {
		logger.debug("enter viewById");
		PageRequest<QueryConditions> pageRequest = getPageRequest(request);

		// 根据传入的参数获取service
		AbstractService service = pageRequest.isQueryDB() ? getQueryService()
				: getService();
		entity = service.get(entity);

		return SUCCESS;
	}

	/**
	 * 向数据库更新一条对应于一个业务对象实例的记录
	 * 
	 * @return
	 */
	public String update() {
		logger.debug("enter update");
		getService().update(entity);
		// 修改成功
		addReturnValue(1, 119902);
		return SUCCESS;
	}

	/**
	 * 通过主键数组 批量删除数据
	 * 
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public String delete() throws SecurityException, NoSuchFieldException {
		logger.debug("enter delete");
		String[] ids = request.getParameterValues("ids");
		if (ids == null || ids.length == 0) {
			throw new IllegalArgumentException("删除主键数组不能为空！");
		}

		if (entity == null) {
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
		}

		if (entity.fetchPKey() == null || entity.fetchPKey().getKey() == null
				|| entity.fetchPKey().getKey().length() == 0) {
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
		}

		/**
		 * 主键类型
		 */
		Type keyType = entity.getClass().getDeclaredField(
				entity.fetchPKey().getKey()).getType();

		/**
		 * 主键数组类型应该与主键类型一致；所以进行类型转换
		 */
		Object[] objIds;
		// 如果是Long型
		if (keyType.equals(Long.class)) {
			objIds = new Long[ids.length];
			for (int i = 0; i < ids.length; i++) {
				objIds[i] = Long.valueOf(ids[i]);
			}
		}
		// 如果是整型
		else if (keyType.equals(Integer.class)) {
			objIds = new Integer[ids.length];
			for (int i = 0; i < ids.length; i++) {
				objIds[i] = Integer.valueOf(ids[i]);
			}
		} else {
			objIds = ids;
		}

		getService().deleteBYBulk(entity, objIds);

		// 批量删除成功
		addReturnValue(1, 119903);
		return SUCCESS;
	}

	/**
	 * 通过entity集合 批量删除数据
	 * 
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String deleteCollection() throws SecurityException,
			NoSuchFieldException, InstantiationException,
			IllegalAccessException {
		logger.debug("enter delete");
		String[] ids = request.getParameterValues("ids");
		if (ids == null || ids.length == 0) {
			throw new IllegalArgumentException("删除主键数组不能为空！");
		}

		if (entity == null) {
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许集合 批量删除数据！");
		}

		if (entity.fetchPKey() == null || entity.fetchPKey().getKey() == null
				|| entity.fetchPKey().getKey().length() == 0) {
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过集合 批量删除数据！");
		}

		/**
		 * 主键类型
		 */
		Type keyType = entity.getClass().getDeclaredField(
				entity.fetchPKey().getKey()).getType();

		/**
		 * 主键数组类型应该与主键类型一致；所以进行类型转换
		 */
		Object[] objIds;
		// 如果是Long型
		if (keyType.equals(Long.class)) {
			objIds = new Long[ids.length];
			for (int i = 0; i < ids.length; i++) {
				objIds[i] = Long.valueOf(ids[i]);
			}
		}
		// 如果是整型
		else if (keyType.equals(Integer.class)) {
			objIds = new Integer[ids.length];
			for (int i = 0; i < ids.length; i++) {
				objIds[i] = Integer.valueOf(ids[i]);
			}
		} else {
			objIds = ids;
		}

		// 待删除的业务对象集合
		Collection<StandardModel> standardModelList = new LinkedList<StandardModel>();
		for (Object object : objIds) {
			// 实例化业务对象
			StandardModel model = entity.getClass().newInstance();
			// 获取主键字段名
			String key = model.fetchPKey().getKey();
			// 获取主键字段
			Field filed = model.getClass().getDeclaredField(key);
			// 如果主键字段不可访问设置可访问
			if (!filed.isAccessible()) {
				filed.setAccessible(true);
			}
			// 设置字段值
			filed.set(model, object);
			// 当嵌套对象级联删除时需要重新获取此对象
			model = standardService.get(model);
			// 如果传入的id不存在
			if (model == null) {
				continue;
			}
			// 将业务对象添加到集合中
			standardModelList.add(model);
		}

		getService().deleteBYBulk(standardModelList);

		// 批量删除成功
		addReturnValue(1, 119903);

		return SUCCESS;
	}

	/**
	 * 根据传入的查询条件以及分页信息获取页面信息 将页面信息写入Attribute
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		logger.debug("enter list");
		PageRequest<QueryConditions> pageRequest = getPageRequest(request);

		// 根据传入的参数获取service
		AbstractService service = pageRequest.isQueryDB() ? getQueryService()
				: getService();
		
		Page<StandardModel> page = service.getPage(pageRequest, entity);
		request.setAttribute(PAGEINFO, page);
		// 将旧的变量值填到Attribute中以方便将值反填到界面的查询条件内
		request.setAttribute(OLDPARAMETER, getParametersStartingWith(request,
				PARAMETERPREFIX));
		return SUCCESS;
	}

	/**
	 * 添加返回信息到Attitude
	 * 
	 * @param result
	 *            返回结果 -1:失败 0:警告 1:成功
	 * 
	 * @param code
	 *            返回代码 通过返回代码到配置文件获取信息写入到ReturnValue中
	 */
	public void addReturnValue(int result, long code) {
		addReturnValue(result, code, null);
	}

	/**
	 * 添加返回信息到Attitude
	 * 
	 * @param result
	 *            返回结果 -1:失败 0:警告 1:成功
	 * @param args
	 *            格式化字符串参数
	 * 
	 * @param code
	 *            返回代码 通过返回代码到配置文件获取信息写入到ReturnValue中
	 */
	public void addReturnValue(int result, long code, Object[] args) {
		addReturnValue(result, code, args, -1);
	}

	/**
	 * 添加返回信息到Attitude
	 * 
	 * @param result
	 *            返回结果 -1:失败 0:警告 1:成功
	 * @param args
	 *            格式化字符串参数
	 * 
	 * @param code
	 *            返回代码 通过返回代码到配置文件获取信息写入到ReturnValue中
	 * @param resultType
	 *            返回设置不同操作的标识符
	 */
	public void addReturnValue(int result, long code, Object[] args, int resultType) {
		ReturnValue returnValue = new ReturnValue();
		returnValue.setResult(result);
		returnValue.setResultType(resultType);
		if (args == null)
			returnValue.addInfo(code);
		else
			returnValue.addInfo(code, args);
		// 将返回结果写入Attribute
		request.setAttribute(Global.RETURNVALUE, returnValue);
	}

	/**
	 * 写操作日志
	 * 
	 * @param catalogID
	 *            日志类别号
	 * @param content
	 *            操作内容
	 * @param operateResult
	 *            操作结果 1：成功 0：失败
	 * @param mark
	 *            备注
	 */
	public void writeOperateLog(int catalogID, String content,
			int operateResult, String mark) {
		OperateLog operateLog = new OperateLog();
		operateLog.setLogType(1);
		// 当前用户
		User user = (User) request.getSession().getAttribute(
				Global.CURRENTUSERSTR);

		// 设置日志内容
		operateLog.setOperator(user.getUserId());
		operateLog.setOperateIp(user.getIpAddress());
		try {
			operateLog.setOperateTime(getService().getSysDate());
		} catch (Exception e) {
			logger.error(Tools.getExceptionTrace(e));
		}
		LogCatalog lCatalog = new LogCatalog();
		lCatalog.setCatalogID(catalogID);
		operateLog.setLogCatalog(lCatalog);
		operateLog.setOperatorType(user.getType());
		operateLog.setOperateContent(content);
		operateLog.setOperateResult(operateResult);
		operateLog.setMark(mark);
		getService().add(operateLog);
	}

	/**
	 * 从request获取分页请求信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 分页请求信息
	 * @throws Exception
	 */
	protected PageRequest<QueryConditions> getPageRequest(
			HttpServletRequest request) throws Exception {
		PageRequest<QueryConditions> pageRequest = new PageRequest<QueryConditions>();
		// 获取并设置页码
		pageRequest.setPageNumber(Tools.strToInt(request
				.getParameter(PAGENUMBER), PageRequest.DEFAULT_PAGE_NUMBER));

		// 获取并设置分页大小
		pageRequest.setPageSize(Tools.strToInt(request.getParameter(PAGESIZE),
				PageRequest.DEFAULT_PAGE_SIZE));

		// 获取并设置排序列
		pageRequest.setSortColumns(request.getParameter(SORTCOLUMNS));

		// 获取是否使用查询库进行查询
		pageRequest.setQueryDB(Tools.strToBoolean(request
				.getParameter(ISQUERYDB)));

		// 获取并这只查询条件集合
		pageRequest.setFilters(getQueryConditionsFromRequest(request));

		return pageRequest;
	}

	/**
	 * 从request对象中取出查询条件，查询字段PARAMETERPREFIX开头（gnnt_），中括号包含操作符和类型，
	 * 比如gnnt_accountCode[=][ date]
	 * 
	 * @param request
	 * @return query conditions
	 * @throws Exception
	 */
	protected QueryConditions getQueryConditionsFromRequest(
			HttpServletRequest request) throws Exception {
		// 初始化查询条件集合
		QueryConditions qc = new QueryConditions();

		logger.debug("enter getQueryConditionsFromReq...");
		// 获取Parameters中的变量名及值
		Map<String, Object> mapForParameters = getParametersStartingWith(
				request, PARAMETERPREFIX);
		// 获取Parameters中的变量名及值
		Map<String, String> mapForAttribute = getAttributeStartingWith(request,
				PARAMETERPREFIX);
		// 将Attribute中变量名及值合并到Parameters里
		mapForParameters.putAll(mapForAttribute);

		// 如果mapForParameters集合不为空并且集合数量大于0 则遍历集合
		if (mapForParameters != null && mapForParameters.size() > 0) {
			logger.debug("parameter size:" + mapForParameters.size());

			// 关于正则表达式的说明 m.group(1)匹配[前的字符串(.+?)
			// m.group(2) 匹配第一对中括号内字符串 \\[(.+)]
			// m.group(3) 匹配第二对中括号内字符串 \\[(.+)]
			// 关于"\\["的说明
			// 第一个反斜杠为第二反斜杠的转义字符
			// 第二个反斜杠是说[为原意字符

			// 具有操作符和类型的查询条件 gnnt_accountCode[=][date]
			Pattern pOperatorDatatype = Pattern
					.compile("(.+?)\\[(.+)]\\[(.+)]");
			// 具有操作符的查询条件 gnnt_accountCode[=]
			Pattern pOperator = Pattern.compile("(.+?)\\[(.+)]");

			String param, op, type;
			for (Iterator<String> it = mapForParameters.keySet().iterator(); it
					.hasNext();) {
				String key = it.next();
				Object value = mapForParameters.get(key);
				logger.debug("parameter:" + key);
				Matcher m = pOperatorDatatype.matcher(key);
				// group(0)对应的是整个正则表达式匹配部分，group(1)~group(groupCount)则是小括号内匹配部分。
				if (m.matches()) {
					param = m.group(1);
					op = m.group(2);
					type = m.group(3);
				} else {
					m = pOperator.matcher(key);
					if (m.matches()) {
						param = m.group(1);
						op = m.group(2);
						type = null;
					} else {
						param = null;
						op = null;
						type = null;
					}
				}

				if (param != null && value != null
						&& ((String) value).trim().length() > 0) {

					if (value instanceof String)
						value = value.toString().trim();

					if (qc == null) {
						qc = new QueryConditions();
					}
					if (type != null) {
						value = Tools.convert(value, type);
						if (type.equals("simpledate") && op.equals("=")) {
							param = "trunc(" + param + ")";
						}
					}
					logger.debug("field:" + param);
					logger.debug("operator:" + op);
					logger.debug("datatype:" + type);
					logger.debug("value:" + value);
					qc.addCondition(param, op, value);
				}
			}
		}

		return qc;
	}

	/**
	 * 从request的parameters中获取 以prefix为前缀的 变量名和值
	 * 
	 * @param ServletRequest
	 * @param 变量名开始前缀
	 * @return key：parameter名称（不包含前缀） parameter值
	 */
	protected Map<String, Object> getParametersStartingWith(
			ServletRequest request, String prefix) {
		Assert.notNull(request, "Request must not be null");
		Enumeration<?> paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 从request的Attribute中获取 以prefix为前缀的 变量名和值
	 * 
	 * @param request
	 *            ServletRequest
	 * @param prefix
	 *            变量名开始前缀
	 * @return key：parameter名称（不包含前缀） parameter值
	 */
	protected Map<String, String> getAttributeStartingWith(
			ServletRequest request, String prefix) {
		Enumeration<?> namesEnumeration = request.getAttributeNames();
		Map<String, String> params = new TreeMap<String, String>();
		// 如果前缀为null 则查询所有有值的变量集合
		if (prefix == null) {
			prefix = "";
		}
		// 遍历Attribute 变量
		while (namesEnumeration != null && namesEnumeration.hasMoreElements()) {
			String paramName = (String) namesEnumeration.nextElement();
			// 比较变量名和前缀是否匹配
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				// 获取变量名
				String unprefixed = paramName.substring(prefix.length());
				// 获取对应的值
				String value = (String) request.getAttribute(paramName);
				// 如果值为空 则不作处理
				if (value == null) {
					// Do nothing, no values found at all.
				}
				// 值不为空则添加到map中
				else {
					params.put(unprefixed, value.trim());
				}
			}
		}
		return params;
	}
}
