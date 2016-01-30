// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi

package gnnt.MEBS.common.broker.webframe.strutsinterceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import gnnt.MEBS.common.broker.action.StandardAction;
import gnnt.MEBS.common.broker.model.Apply;
import gnnt.MEBS.common.broker.model.Broker;
import gnnt.MEBS.common.broker.model.StandardModel;

public class AuditInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 0x5ec8fcbe35b0c4e6L;

	public AuditInterceptor() {
	}

	public String intercept(ActionInvocation actioninvocation) throws Exception {
		ActionContext actioncontext = actioninvocation.getInvocationContext();
		HttpServletRequest httpservletrequest = (HttpServletRequest) actioncontext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		StandardAction standardaction = (StandardAction) actioninvocation.getAction();
		StandardModel standardmodel = standardaction.getEntity();
		if (standardaction.getAuditType() != null && standardaction.getAuditType().length() > 0) {
			String s = actioninvocation.getProxy().getMethod();
			if (s.equals("viewById") || s.equals("forwardAdd")) {
				httpservletrequest.setAttribute("auditType", standardaction.getAuditType());
				return actioninvocation.invoke();
			}
			if (s.equals("add") || s.equals("update") || s.equals("delete") || s.equals("deleteCollection")) {
				Broker broker = (Broker) httpservletrequest.getSession().getAttribute("CurrentUser");
				Apply apply = new Apply();
				apply.setOperateType("add");
				apply.setApplyType(standardaction.getAuditType());
				apply.setApplyUser(broker.getBrokerId());
				apply.setEntityClass(standardmodel.getClass().getName());
				apply.setCreateTime(new Date());
				apply.setStatus(Integer.valueOf(0));
				if (s.equals("add")) {
					String s1 = (new StringBuilder()).append("添加数据：").append(standardmodel.translate()).toString();
					apply.setOperateType("add");
					apply.setContent(standardmodel.serialize());
					apply.setDiscribe(s1);
					standardaction.addReturnValue(1, 0x1d460L);
				} else if (s.startsWith("delete")) {
					String as[] = httpservletrequest.getParameterValues("ids");
					if (as == null || as.length == 0)
						throw new IllegalArgumentException("删除主键数组不能为空！");
					if (standardmodel == null)
						throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
					if (standardmodel.fetchPKey() == null || standardmodel.fetchPKey().getKey() == null
							|| standardmodel.fetchPKey().getKey().length() == 0)
						throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
					String s2 = "";
					String as1[] = as;
					int i = as1.length;
					for (int j = 0; j < i; j++) {
						String s5 = as1[j];
						s2 = (new StringBuilder()).append(s2).append(s5).append(",").toString();
					}

					s2 = s2.substring(0, s2.length() - 1);
					String s4 = (new StringBuilder()).append("删除数据：").append(s2).toString();
					apply.setContent(s2);
					if (s.equals("delete"))
						apply.setOperateType("delete");
					else
						apply.setOperateType("deleteCollection");
					apply.setDiscribe(s4);
					standardaction.addReturnValue(1, 0x1d462L);
				} else if (s.equals("update")) {
					StandardModel standardmodel1 = standardaction.getService().get(standardmodel);
					String s3 = (new StringBuilder()).append("更新数据,").append(standardmodel1.compareTranslate(standardmodel)).toString();
					apply.setOperateType("update");
					apply.setContent(standardmodel.serialize());
					apply.setDiscribe(s3);
					standardaction.addReturnValue(1, 0x1d461L);
				}
				standardaction.getService().add(apply);
				return "success";
			} else {
				return actioninvocation.invoke();
			}
		} else {
			return actioninvocation.invoke();
		}
	}
}
