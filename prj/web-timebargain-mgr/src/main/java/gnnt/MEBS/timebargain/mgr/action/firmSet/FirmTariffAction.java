package gnnt.MEBS.timebargain.mgr.action.firmSet;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.common.QueryConditions;
import gnnt.MEBS.timebargain.mgr.model.firmSet.TFirm;
import gnnt.MEBS.timebargain.mgr.service.FirmTariffService;

@Controller("firmTariffAction")
@Scope("request")
public class FirmTariffAction extends EcsideAction {
	private static final long serialVersionUID = -5980944598275231216L;

	@Resource(name = "firm_statusMap")
	private Map<Integer, String> firm_statusMap;

	@Autowired
	@Qualifier("firmTariffService")
	private FirmTariffService firmTariffService;

	public String listLirmTariff() throws Exception {
		this.logger.debug("enter listLirmTariff");
		String str = this.request.getParameter("isTariff");
		PageRequest localPageRequest = super.getPageRequest(this.request);
		QueryConditions localQueryConditions = (QueryConditions) localPageRequest.getFilters();
		if (str != null) {
			if (str.equals("Y"))
				localQueryConditions.addCondition("primary.tariffID", "!=", "none");
			else if (str.equals("N"))
				localQueryConditions.addCondition("primary.tariffID", "=", "none");
		} else
			str = "";
		listByLimit(localPageRequest);
		this.request.setAttribute("isTariff", str);
		return "success";
	}

	public String forwardUpdateFirmTariff() {
		this.logger.debug("enter forwardUpdateFirmTariff");
		this.entity = getService().get(this.entity);
		List localList = this.firmTariffService.getTariff();
		this.request.setAttribute("tariffList", localList);
		return "success";
	}

	public String updateFirmTariff() throws Exception {
		this.logger.debug("enter updateFirmTariff");
		TFirm localTFirm = (TFirm) getService().get(this.entity);
		localTFirm.setModifyTime(getService().getSysDate());
		getService().update(this.entity);
		addReturnValue(1, 119902L);
		return "success";
	}

	public String viewTariff() throws Exception {
		this.logger.debug("enter viewTariff");
		String str = this.request.getParameter("tariffID");
		this.request.setAttribute("gnnt_primary.tariffID[=]", str);
		this.request.setAttribute("tariffID", str);
		this.request.setAttribute("tariffName", "加收" + str.substring(1) + "%");
		return listByLimit();
	}

	public String closeTariff() throws Exception {
		this.logger.debug("enter closeTariff");
		String[] arrayOfString = this.request.getParameterValues("ids");
		if ((arrayOfString == null) || (arrayOfString.length == 0))
			throw new IllegalArgumentException("删除主键数组不能为空！");
		if (this.entity == null)
			throw new IllegalArgumentException("业务对象为空，所以操作表未知，不允许通过主键数组批量删除！");
		if ((this.entity.fetchPKey() == null) || (this.entity.fetchPKey().getKey() == null) || (this.entity.fetchPKey().getKey().length() == 0))
			throw new IllegalArgumentException("业务对象未设置主键，不允许通过主键数组批量删除！");
		Class localClass = this.entity.getClass().getDeclaredField(this.entity.fetchPKey().getKey()).getType();
		Object[] localObject;
		int i;
		if (localClass.equals(Long.class)) {
			localObject = new Long[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++)
				localObject[i] = Long.valueOf(arrayOfString[i]);
		} else if (localClass.equals(Integer.class)) {
			localObject = new Integer[arrayOfString.length];
			for (i = 0; i < arrayOfString.length; i++)
				localObject[i] = Integer.valueOf(arrayOfString[i]);
		} else {
			localObject = arrayOfString;
		}
		getService().updateBYBulk(this.entity, "tariffID='none',modifyTime=sysdate", (Object[]) localObject);
		addReturnValue(1, 119907L);
		return "success";
	}

	public Map<Integer, String> getFirm_statusMap() {
		return this.firm_statusMap;
	}

	public FirmTariffService getFirmTariffService() {
		return this.firmTariffService;
	}
}