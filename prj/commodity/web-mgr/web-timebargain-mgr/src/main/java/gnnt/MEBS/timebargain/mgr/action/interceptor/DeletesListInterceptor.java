package gnnt.MEBS.timebargain.mgr.action.interceptor;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import gnnt.MEBS.common.mgr.action.StandardAction;
import gnnt.MEBS.common.mgr.model.StandardModel;

public class DeletesListInterceptor extends AbstractInterceptor {
	private StandardModel entity;

	public String intercept(ActionInvocation paramActionInvocation) throws Exception {
		HttpServletRequest localHttpServletRequest = ServletActionContext.getRequest();
		localHttpServletRequest.setAttribute("deletesList", createList(localHttpServletRequest, paramActionInvocation));
		return paramActionInvocation.invoke();
	}

	private List<StandardModel> createList(HttpServletRequest paramHttpServletRequest, ActionInvocation paramActionInvocation) throws Exception {
		String[] arrayOfString = paramHttpServletRequest.getParameterValues("ids");
		StandardAction localStandardAction = (StandardAction) paramActionInvocation.getAction();
		this.entity = localStandardAction.getEntity();
		if ((arrayOfString == null) || (arrayOfString.length == 0))
			throw new IllegalArgumentException("删除主键数组不能为空！");
		if (this.entity == null)
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许集合 批量删除数据！");
		if ((this.entity.fetchPKey() == null) || (this.entity.fetchPKey().getKey() == null) || (this.entity.fetchPKey().getKey().length() == 0))
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过集合 批量删除数据！");
		Class localClass = this.entity.getClass().getDeclaredField(this.entity.fetchPKey().getKey()).getType();
		Object[] localObject1;
		int i;
		if (localClass.equals(Long.class)) {
			localObject1 = new Long[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++)
				localObject1[i] = Long.valueOf(arrayOfString[i]);
		} else if (localClass.equals(Integer.class)) {
			localObject1 = new Integer[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++)
				localObject1[i] = Integer.valueOf(arrayOfString[i]);
		} else {
			localObject1 = arrayOfString;
		}
		LinkedList localLinkedList = new LinkedList();
		for (Object localObject3 : localObject1) {
			StandardModel localStandardModel = (StandardModel) this.entity.getClass().newInstance();
			String str = localStandardModel.fetchPKey().getKey();
			Field localField = localStandardModel.getClass().getDeclaredField(str);
			if (!localField.isAccessible())
				localField.setAccessible(true);
			localField.set(localStandardModel, localObject3);
			localStandardModel = localStandardAction.getService().get(localStandardModel);
			if (localStandardModel != null)
				localLinkedList.add(localStandardModel);
		}
		return localLinkedList;
	}
}