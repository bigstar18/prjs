package gnnt.MEBS.timebargain.mgr.action.settle;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.action.EcsideAction;
import gnnt.MEBS.timebargain.mgr.model.settle.TransferModel;
import gnnt.MEBS.timebargain.mgr.service.TransferService;

@Controller("transferAction")
@Scope("request")
public class TransferAction extends EcsideAction {
	private static final long serialVersionUID = 5124568190167465621L;

	@Resource(name = "BS_flagMap")
	private Map<String, String> BS_flagMap;

	@Resource(name = "Transfer_typeMap")
	private Map<String, String> Transfer_typeMap;

	@Resource(name = "Transfer_statusMap")
	private Map<String, String> Transfer_statusMap;

	@Autowired
	@Qualifier("com_transferService")
	private TransferService transferService;

	public Map<String, String> getBS_flagMap() {
		return this.BS_flagMap;
	}

	public void setBS_flagMap(Map<String, String> paramMap) {
		this.BS_flagMap = paramMap;
	}

	public Map<String, String> getTransfer_typeMap() {
		return this.Transfer_typeMap;
	}

	public void setTransfer_typeMap(Map<String, String> paramMap) {
		this.Transfer_typeMap = paramMap;
	}

	public Map<String, String> getTransfer_statusMap() {
		return this.Transfer_statusMap;
	}

	public void setTransfer_statusMap(Map<String, String> paramMap) {
		this.Transfer_statusMap = paramMap;
	}

	public TransferService getTransferService() {
		return this.transferService;
	}

	public void setTransferService(TransferService paramTransferService) {
		this.transferService = paramTransferService;
	}

	public String addTransfer() {
		TransferModel localTransferModel = (TransferModel) this.entity;
		String str1 = localTransferModel.getCommodityID();
		String str2 = localTransferModel.getCustomerID_s();
		String str3 = localTransferModel.getBs_flag().toString();
		String str4 = localTransferModel.getQuantity().toString();
		String str5 = localTransferModel.getCustomerID_b();
		String str6 = "select customerid from t_customer where customerid = '" + str5 + "'";
		List localList = getService().getListBySql(str6);
		if ((localList == null) || (localList.size() == 0)) {
			addReturnValue(-1, 150832L);
			return "success";
		}
		str6 = "select c.holdqty-c.frozenqty as qty from t_customerholdsum c where c.commodityid = '" + str1 + "' and c.customerid = '" + str2
				+ "' and c.bs_flag = " + str3;
		localList = getService().getListBySql(str6);
		if ((localList != null) && (localList.size() > 0)) {
			int i = Integer.parseInt(((Map) localList.get(0)).get("QTY").toString());
			if (Integer.parseInt(str4) > i) {
				addReturnValue(-1, 150831L);
				return "success";
			}
		} else {
			addReturnValue(-1, 150831L);
			return "success";
		}
		int i = this.transferService.addTransfer(str1, str2, str3, str4, this.entity);
		if (i < 0)
			addReturnValue(-1, 119908L);
		else
			addReturnValue(1, 119901L);
		return "success";
	}

	public String deleteTransfer() {
		String[] arrayOfString = this.request.getParameterValues("ids");
		int i = this.transferService.deleteTransfer(arrayOfString);
		if (i == -1)
			addReturnValue(-1, 119908L);
		else if (i == -2)
			addReturnValue(-1, 150801L);
		else
			addReturnValue(1, 119903L);
		return "success";
	}

	public String viewByIdTransfer() throws Exception {
		TransferModel localTransferModel = (TransferModel) getService().get(this.entity);
		this.entity = localTransferModel;
		return "success";
	}

	public String transferAudit() {
		long l = Integer.parseInt(this.request.getParameter("status"));
		String str1 = this.request.getParameter("transferID");
		String str2 = this.request.getParameter("commodityID");
		String str3 = this.request.getParameter("bs_flag");
		String str4 = this.request.getParameter("customerID_b");
		String str5 = this.request.getParameter("customerID_s");
		String str6 = this.request.getParameter("quantity");
		int i;
		if (l == 1L) {
			i = this.transferService.auditSuccess(str1, str2, str3, str4, str5, str6);
			if (i == 1)
				addReturnValue(1, 119907L);
			else if (i == -1)
				addReturnValue(1, 150802L);
			else if (i == -5)
				addReturnValue(1, 150803L);
			else if (i == -15)
				addReturnValue(1, 150804L);
		} else if (l == 2L) {
			i = this.transferService.auditFail(str1, str2, str3, str5, str6);
			if (i == 1)
				addReturnValue(1, 119907L);
			else if (i == -1)
				addReturnValue(-1, 119908L);
		}
		return "success";
	}
}