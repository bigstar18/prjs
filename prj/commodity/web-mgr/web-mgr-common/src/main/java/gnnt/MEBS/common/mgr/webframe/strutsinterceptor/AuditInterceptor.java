package gnnt.MEBS.common.mgr.webframe.strutsinterceptor;

import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.common.Global;
import gnnt.MEBS.common.mgr.model.Apply;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.model.User;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 审核拦截器
 * 
 * @author xuejt
 * 
 */
public class AuditInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 6829986728782120166L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext
				.get(StrutsStatics.HTTP_REQUEST);

		// action对象
		StandardAction action = (StandardAction) invocation.getAction();

		// 实体对象
		StandardModel entity = action.getEntity();

		// 如果配置了审核属性
		if (action.getAuditType() != null && action.getAuditType().length() > 0) {
			// 根据方法名判断action类型
			String method = invocation.getProxy().getMethod();
			if (method.equals("viewById") || method.equals("forwardAdd")) {
				request.setAttribute("auditType", action.getAuditType());
				return invocation.invoke();
			} else if (method.equals("add") || method.equals("update")
					|| method.equals("delete")
					|| method.equals("deleteCollection")) {

				User user = (User) request.getSession().getAttribute(
						Global.CURRENTUSERSTR);

				/********** 构建申请对象 ************/
				Apply apply = new Apply();
				apply.setOperateType("add");
				apply.setApplyType(action.getAuditType());
				apply.setApplyUser(user.getUserId());
				apply.setEntityClass(entity.getClass().getName());
				apply.setCreateTime(new Date());
				apply.setStatus(0);

				if (method.equals("add")) {
					String discribe = "添加数据：" + entity.translate();

					apply.setOperateType("add");
					apply.setContent(entity.serialize());
					apply.setDiscribe(discribe);

					// 添加数据成功，等待审核!
					action.addReturnValue(1, 119904);
				} else if (method.startsWith("delete")) {
					String[] ids = request.getParameterValues("ids");

					if (ids == null || ids.length == 0) {
						throw new IllegalArgumentException("删除主键数组不能为空！");
					}

					if (entity == null) {
						throw new IllegalArgumentException(
								"业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
					}

					if (entity.fetchPKey() == null
							|| entity.fetchPKey().getKey() == null
							|| entity.fetchPKey().getKey().length() == 0) {
						throw new IllegalArgumentException(
								"业务对象未设置主键，不允许通过主键数组批量删除！");
					}

					String content = "";
					for (String id : ids) {
						content += id + ",";
					}
					
					content=content.substring(0,content.length()-1);
					
					String discribe = "删除数据："
							+ content;

					apply.setContent(content);

					if (method.equals("delete")) {
						apply.setOperateType("delete");
					} else {
						apply.setOperateType("deleteCollection");
					}
					apply.setDiscribe(discribe);

					// 删除数据成功，等待审核!
					action.addReturnValue(1, 119906);
				} else if (method.equals("update")) {
					// 先从数据库中查询出修改前的值
					StandardModel model = action.getService().get(entity);
					String discribe ="更新数据,"+model.compareTranslate(entity);;

					apply.setOperateType("update");
					apply.setContent(entity.serialize());
					apply.setDiscribe(discribe);
					// 更新数据成功，等待审核!
					action.addReturnValue(1, 119905);
				}

				// 将申请对象写入数据库
				action.getService().add(apply);
				return ActionSupport.SUCCESS;
			} else {
				return invocation.invoke();
			}
		} else {
			return invocation.invoke();
		}
	}
}
