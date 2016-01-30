package gnnt.MEBS.common.mgr.webframe.strutsinterceptor;

import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.common.ReturnValue;
import gnnt.MEBS.common.mgr.model.OperateLog;
import gnnt.MEBS.common.mgr.model.Right;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;
import gnnt.MEBS.common.mgr.service.RightService;
import gnnt.MEBS.common.mgr.service.StandardService;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.common.mgr.statictools.GnntBeanFactory;
import gnnt.MEBS.common.mgr.statictools.Tools;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 记录日志拦截器
 * 
 * @author xuejt
 * 
 */
public class WriteLogInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 8669997795536485487L;

	private transient final Log logger = LogFactory
			.getLog(WriteLogInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext
				.get(StrutsStatics.HTTP_REQUEST);
		// url地址
		String url = request.getServletPath();
		logger.debug("url=" + url);

		// url对应的权限
		Right right = this.getRightByUrl(Global.getRightTree(), url);

		// action对象
		StandardAction action = (StandardAction) invocation.getAction();
		// 实体对象
		StandardModel entity = action.getEntity();
		// action对应的service
		StandardService service = action.getService();

		// 日志对象
		OperateLog operateLog = null;

		// 需要记录日志
		if (right != null && "Y".equalsIgnoreCase(right.getIsWriteLog()) && right.getLogCatalog().getCatalogID() != 0) {
			operateLog = new OperateLog();
			operateLog.setLogType(1);
			// 当前用户
			User user = (User) request.getSession().getAttribute(
					Global.CURRENTUSERSTR);

			// 设置日志内容
			operateLog.setOperator(user.getUserId());
			operateLog.setOperateIp(user.getIpAddress());
			operateLog.setOperateTime(new Date());
			operateLog.setLogCatalog(right.getLogCatalog());
			operateLog.setOperatorType(user.getType());

			String operateContent = "";

			// 根据方法名判断action类型
			String method = invocation.getProxy().getMethod();
			if (method.contains("add")) {
				operateContent = getAddLogDescription(entity);
			} else if (method.contains("delete")) {
				operateContent = getDeleteLogDescription(entity, request,
						service);
			} else if (method.contains("update")) {
				operateContent = getUpdateLogDescription(entity, service);
			} else if (method.contains("viewById")) {
				operateContent = getForUpdateLogDescription(entity);
			} else if (method.contains("forwardAdd")) {
				operateContent = getForAddLogDescription(entity,right);
			} else if (method.contains("list")) {
				operateContent = getListLogDescription(right);
			} else {
				operateContent = "点击菜单" + right.getName();
			}

			// 设置操作内容
			operateLog.setOperateContent(operateContent);
			// 如果操作实体对象不为空则写入日志表
			if (entity != null) {
				operateLog.setCurrentValue(entity.serialize());
			}
		}

		// 写入attribute 供异常拦截器拦截到异常时使用
		request.setAttribute("operateLog", operateLog);

		String result = invocation.invoke();

		if (operateLog != null&&result!=null) {
			// 如果操作成功设置操作结果为成功
			if (result.equals(ActionSupport.SUCCESS)) {
				operateLog.setOperateResult(1);
			}
			// 如果不成功并且返回值不为空，则从返回值中获取失败原因写入日志的备注字段
			else {
				ReturnValue returnValue = (ReturnValue) request
						.getAttribute(Global.RETURNVALUE);
				if (returnValue != null && returnValue.getResult() == -1) {
					operateLog.setMark("失败原因:" + returnValue.getInfo());
				}
			}
			// 将日志对象写入数据库
			service.add(operateLog);
		}

		return result;
	}

	/**
	 * 获取添加操作的日志描述
	 * 
	 * @param entity
	 *            添加的实体对象
	 * @return 描述
	 */
	private String getAddLogDescription(StandardModel entity) {
		return "添加数据：" + entity.translate();
	}

	/**
	 * 获取删除操作的日志描述
	 * <p>
	 * 分两种情况 1、根据实体对象单个删除 2、通过主键数组批量删除
	 * 
	 * @param entity
	 *            删除的实体对象
	 * @param request
	 *            http请求
	 * @param service
	 *            action使用的service
	 * @return 描述
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	private String getDeleteLogDescription(StandardModel entity,
			HttpServletRequest request, StandardService service) throws SecurityException, NoSuchFieldException {
		
		String[] ids = request.getParameterValues("ids");
		/**
		 * 主键类型
		 */
		Type keyType = String.class.getGenericSuperclass();
		try {
			if(entity != null && entity.fetchPKey() != null && entity.fetchPKey().getKey() != null){
				keyType = entity.getClass().getDeclaredField(entity.fetchPKey().getKey()).getType();
			}else{
				String result = "删除数据：";
				if(ids != null){
					result += "[";
					for(int i=0;i<ids.length;i++){
						if(i>0){
							result += ",";
						}
						result += ids[i];
					}
					result += "]";
				}
				return result;
			}
		} catch (SecurityException e) {
			logger.error(Tools.getExceptionTrace(e));
		} catch (NoSuchFieldException e) {
			logger.error(Tools.getExceptionTrace(e));
		}

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
		if (objIds != null && objIds.length > 0) {
			List<StandardModel> list = service.getListByBulk(entity, objIds);
			String description = "";
			for (StandardModel standardModel : list) {
				description += "删除数据：" + standardModel.translate();
			}
			return description;
		} else {// 通过实体对象单个删除 {
			return "删除数据：" + entity.translate();
		}
	}

	/**
	 * 获取更新操作的日志描述
	 * 
	 * @param entity
	 *            更新的实体对象
	 * @param service
	 *            action使用的service
	 * @return 描述
	 */
	private String getUpdateLogDescription(StandardModel entity,
			StandardService service) {
		// 通过实体对象单个删除
		if (entity != null && entity.fetchPKey().getValue()!=null) {
			// 先从数据库中查询出修改前的值
			StandardModel model = service.get(entity);
			return "更新数据,"+model.compareTranslate(entity);
		}

		return "更新数据，未获取到实体对象";
	}

	/**
	 * 获取为更新获取实体对象日志
	 * 
	 * @param entity
	 *            实体对象
	 * @return 描述
	 */
	private String getForUpdateLogDescription(StandardModel entity) {
		return "为了更新查询数据：" + entity.translate();
	}

	/**
	 * 获取转向添加页面日志描述
	 * 
	 * @return 描述
	 */
	private String getForAddLogDescription(StandardModel entity,Right right) {
		return "打开("+right.getName()+")的添加页面";
		//return "打开("+right.getName()+")的添加页面,[" + entity.getClass().getName()+"]";
	}

	/**
	 * 获取 点击菜单获取列表日志描述
	 * 
	 * @param right
	 *            权限信息
	 * @return 描述
	 */
	private String getListLogDescription(Right right) {
		return "点击菜单" + right.getName() + "获取列表";
	}

	/**
	 * 根据权限树递归获取 url对应的权限
	 * 
	 * @param right
	 *            权限集合
	 * @param url
	 *            url地址
	 * @return url对应的权限如果没有匹配返回空
	 */
	private Right getRightByUrl(Right right, String url) {
		if (right == null) {
			return null;
		}

		// 判断本权限是否属于本系统加载的权限模块，如果不属于，则直接返回空
		boolean flag = false;
		for(Integer moduleID : Global.getModuleIDList()){
			if(right.getModuleId() != null && right.getModuleId().equals(moduleID)){
				flag = true;
				break;
			}
		}
		if(!flag){//如果设定本系统没有加载本模块
			return null;
		}

		// 如果用户拥有此url权限返回成功
		if (url.equals(right.getUrl())) {
			return right;
		}

		// 用户以* 通配符结尾 则使用正则表达式判断
		if (right.getUrl() != null && right.getUrl().endsWith("*")) {
			// 如果是菜单类型 则匹配菜单路径和访问的url是否匹配
			// 如果菜单也按照通配符匹配则出现权限增加；如拥有菜单 user/* 权限 但是不拥有 user/add*
			// 那么user/add.action也将匹配菜单权限但是用户本不应该拥有add权限
			if (right.getType() == 0) {
				if (right.getVisiturl().equals(url)) {
					return right;
				}
			} else {
				String tempUrl = right.getUrl().substring(0,
						right.getUrl().length() - 1);
				if (url.contains(tempUrl)) {
					return right;
				}
			}
		}

		// 如果有子菜单先匹配子菜单；递归遍历子菜单判断是否有匹配的权限
		if (right.getChildRightSet() != null
				&& right.getChildRightSet().size() > 0) {
			for (Right childRight : right.getChildRightSet()) {
				// 递归调用
				Right resultRight = getRightByUrl(childRight, url);
				if (resultRight != null) {
					return resultRight;
				}
			}
		}

		return null;
	}

	/**
	 * 根据权限集合获取 url对应的权限
	 * 
	 * @param rightList
	 *            权限集合
	 * @param url
	 *            url地址
	 * @return url对应的权限如果没有匹配返回空
	 * 
	 */
	private Right getRightByUrl(List<Right> rightList, String url) {
		if (rightList == null) {
			return null;
		}
		for (Right right : rightList) {
			// 如果用户拥有此url权限返回成功
			if (url.equals(right.getUrl())) {
				return right;
			}
			// 用户以* 通配符结尾 则使用正则表达式判断
			if (right.getUrl() != null && right.getUrl().endsWith("*")) {
				// 如果是菜单类型 则匹配菜单路径和访问的url是否匹配
				// 如果菜单也按照通配符匹配则出现权限增加；如拥有菜单 user/* 权限 但是不拥有 user/add*
				// 那么user/add.action也将匹配菜单权限但是用户本不应该拥有add权限
				if (right.getType() == 0) {
					if (right.getVisiturl().equals(url)) {
						return right;
					}
				} else {
					// 将* 用 .*替换 表示以right.getUrl()的*前的字符串开头即可
					Pattern pattern = Pattern.compile(right.getUrl()
							.replaceAll("\\*", ".*"));
					Matcher matcher = pattern.matcher(url);
					if (matcher.find()) {
						return right;
					}
				}
			}
		}

		return null;
	}

	/**
	 * 根据权限 获取包括该权限和子权限的权限集合
	 * 
	 * @param right
	 *            权限
	 * @return 权限包括子权限的集合
	 */
	private List<Right> getRightList(Right right) {
		List<Right> rightList = new ArrayList<Right>();
		if (right == null) {
			return null;
		}
		rightList.add(right);

		if (right.getChildRightSet() != null
				&& right.getChildRightSet().size() > 0) {
			for (Right childRight : right.getChildRightSet()) {
				// 递归调用
				rightList.addAll(getRightList(childRight));
			}
		}
		return rightList;
	}

	public static void main(String[] args) {
		// 测试算法效率 如果数据量增加效率过低 考虑一下解决方法
		// 1 在过滤器中判断权限的时候已经知道url对应的权限 将权限写入attribute在此拦截器中获取即可
		// 2 将通配符匹配的正则匹配法修改为 字符串indexof方法

		GnntBeanFactory.getBeanFactory();
		RightService rightService = (RightService) ApplicationContextInit
				.getBean("com_rightService");
		Right rightTree = rightService.loadRightTree();

		WriteLogInterceptor writeLogInterceptor = new WriteLogInterceptor();

		long startTime = System.currentTimeMillis();
		long times = 100000;
		for (int i = 0; i < times; i++) {
			writeLogInterceptor.getRightByUrl(rightTree, "/user/update.action");
			// if (right != null)
			// System.out.println(right.getUrl());
			// right = writeLogInterceptor.getRightByUrl(rightTree,
			// "/user/logon.action");
			// if (right != null)
			// System.out.println(right.getUrl());
		}
		System.out.println("使用递归算法计算" + times + "次花费时间："
				+ (System.currentTimeMillis() - startTime) + "毫秒");

		List<Right> rightList = writeLogInterceptor.getRightList(rightTree);
		startTime = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			writeLogInterceptor.getRightByUrl(rightList, "/role/delete.sss");
			// if (right != null)
			// System.out.println(right.getUrl());
			// right = writeLogInterceptor.getRightByUrl(rightTree,
			// "/user/logon.action");
			// if (right != null)
			// System.out.println(right.getUrl());
		}
		System.out.println("使用迭代算法计算" + times + "次花费时间："
				+ (System.currentTimeMillis() - startTime) + "毫秒");
	}
}
