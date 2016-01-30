package gnnt.MEBS.timebargain.mgr.beanforajax;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.beanforajax.BaseAjax;
import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.timebargain.mgr.model.firmSet.Firm;
import gnnt.MEBS.timebargain.mgr.model.firmSet.TBreed;
import gnnt.MEBS.timebargain.mgr.model.firmSet.TCommodity;
import gnnt.MEBS.timebargain.mgr.model.tradeparams.Market;
import net.sf.json.JSONArray;

@Controller("ajaxFirmSet")
@Scope("request")
public class AjaxFirmSet extends BaseAjax {
	private boolean existFirm(String firmID) {
		boolean result = false;
		if ((firmID == null) || (firmID.trim().length() <= 0)) {
			return result;
		}
		PageRequest pageRequest = new PageRequest(" and primary.firmID='" + firmID + "' and primary.status = 'N'");
		Page page = getService().getPage(pageRequest, new Firm());
		if ((page.getResult() != null) && (page.getResult().size() > 0)) {
			result = true;
		}
		return result;
	}

	private boolean existTradeprivilege(int type, String typeID, int kind, String kindID) {
		boolean result = false;

		if ((kindID == null) || (kindID.trim().length() <= 0)) {
			return result;
		}

		String sql = "select * from t_a_tradeprivilege t where t.type= " + type + " and t.typeID = '" + typeID + "' and t.kind = " + kind
				+ " and t.kindid = '" + kindID + "'";
		List list = getService().getListBySql(sql);
		if ((list != null) && (list.size() > 0)) {
			result = true;
		}

		return result;
	}

	public String mouseCheckFirmByFirmId() {
		HttpServletRequest request = getRequest();

		String fieldId = request.getParameter("fieldId");
		String fieldValue = request.getParameter("fieldValue");

		this.jsonValidateReturn = createJSONArray(new Object[] { fieldId, Boolean.valueOf(existFirm(fieldValue)) });

		return "success";
	}

	public void formCheckFirmByFirmId(BaseAjax.AjaxJSONArrayResponse response) {
		HttpServletRequest request = getRequest();

		String firmID = request.getParameter("entity.firmID");
		boolean flag = existFirm(firmID);
		if (!flag)
			response.addJSON(new Object[] { createJSONArray(new Object[] { "firmID", Boolean.valueOf(false), "此交易商不存在" }) });
	}

	public String formCheckFirmByFirmId() {
		BaseAjax.AjaxJSONArrayResponse response = new BaseAjax.AjaxJSONArrayResponse(this, new Object[0]);
		formCheckFirmByFirmId(response);
		if (response.size() <= 0) {
			response.addJSON(new Object[] { createJSONArray(new Object[] { "true" }) });
		}
		this.jsonValidateReturn = response.toJSONArray();
		return "success";
	}

	public void formCheckTradeprivilege(BaseAjax.AjaxJSONArrayResponse response) {
		HttpServletRequest request = getRequest();

		String typeStr = request.getParameter("entity.type").trim();
		String typeID = request.getParameter("entity.typeID").trim();
		String kindStr = request.getParameter("entity.kind").trim();
		String kindID = request.getParameter("entity.kindID").trim();

		int type = Integer.parseInt(typeStr);
		int kind = Integer.parseInt(kindStr);

		boolean flag = existTradeprivilege(type, typeID, kind, kindID);

		if (flag)
			if (kind == 1)
				response.addJSON(new Object[] { createJSONArray(new Object[] { "breedID", Boolean.valueOf(false), "此品种已存在" }) });
			else if (kind == 2)
				response.addJSON(new Object[] { createJSONArray(new Object[] { "commodityID", Boolean.valueOf(false), "此商品已存在" }) });
	}

	public String formCheckTradeprivilege() {
		BaseAjax.AjaxJSONArrayResponse response = new BaseAjax.AjaxJSONArrayResponse(this, new Object[0]);
		formCheckTradeprivilege(response);
		if (response.size() <= 0) {
			response.addJSON(new Object[] { createJSONArray(new Object[] { "true" }) });
		}
		this.jsonValidateReturn = response.toJSONArray();
		return "success";
	}

	public String getCommodityJson() {
		HttpServletRequest request = getRequest();

		String commodityID = request.getParameter("commodityID");

		TCommodity commodity = new TCommodity();
		commodity.setCommodityID(commodityID);
		commodity = (TCommodity) getService().get(commodity);

		this.jsonValidateReturn = new JSONArray();

		this.jsonValidateReturn.add(commodity.getMarginAlgr() == null ? "" : commodity.getMarginAlgr());
		this.jsonValidateReturn.add(commodity.getMarginItem1() == null ? "" : commodity.getMarginItem1());
		this.jsonValidateReturn.add(commodity.getMarginItem2() == null ? "" : commodity.getMarginItem2());
		this.jsonValidateReturn.add(commodity.getMarginItem3() == null ? "" : commodity.getMarginItem3());
		this.jsonValidateReturn.add(commodity.getMarginItem4() == null ? "" : commodity.getMarginItem4());
		this.jsonValidateReturn.add(commodity.getMarginItem5() == null ? "" : commodity.getMarginItem5());
		this.jsonValidateReturn.add(commodity.getMarginItem1_S() == null ? "" : commodity.getMarginItem1_S());
		this.jsonValidateReturn.add(commodity.getMarginItem2_S() == null ? "" : commodity.getMarginItem2_S());
		this.jsonValidateReturn.add(commodity.getMarginItem3_S() == null ? "" : commodity.getMarginItem3_S());
		this.jsonValidateReturn.add(commodity.getMarginItem4_S() == null ? "" : commodity.getMarginItem4_S());
		this.jsonValidateReturn.add(commodity.getMarginItem5_S() == null ? "" : commodity.getMarginItem5_S());
		this.jsonValidateReturn.add(commodity.getMarginItemAssure1() == null ? "" : commodity.getMarginItemAssure1());
		this.jsonValidateReturn.add(commodity.getMarginItemAssure2() == null ? "" : commodity.getMarginItemAssure2());
		this.jsonValidateReturn.add(commodity.getMarginItemAssure3() == null ? "" : commodity.getMarginItemAssure3());
		this.jsonValidateReturn.add(commodity.getMarginItemAssure4() == null ? "" : commodity.getMarginItemAssure4());
		this.jsonValidateReturn.add(commodity.getMarginItemAssure5() == null ? "" : commodity.getMarginItemAssure5());
		this.jsonValidateReturn.add(commodity.getMarginItemAssure1_S() == null ? "" : commodity.getMarginItemAssure1_S());
		this.jsonValidateReturn.add(commodity.getMarginItemAssure2_S() == null ? "" : commodity.getMarginItemAssure2_S());
		this.jsonValidateReturn.add(commodity.getMarginItemAssure3_S() == null ? "" : commodity.getMarginItemAssure3_S());
		this.jsonValidateReturn.add(commodity.getMarginItemAssure4_S() == null ? "" : commodity.getMarginItemAssure4_S());
		this.jsonValidateReturn.add(commodity.getMarginItemAssure5_S() == null ? "" : commodity.getMarginItemAssure5_S());

		this.jsonValidateReturn.add(commodity.getType1());
		this.jsonValidateReturn.add(commodity.getType2());
		this.jsonValidateReturn.add(commodity.getType3());
		this.jsonValidateReturn.add(commodity.getType4());
		this.jsonValidateReturn.add(commodity.getType5());

		this.jsonValidateReturn.add(commodity.getSettleMarginAlgr_B() == null ? "" : commodity.getSettleMarginAlgr_B());
		this.jsonValidateReturn.add(commodity.getSettleMarginRate_B() == null ? "" : commodity.getSettleMarginRate_B());
		this.jsonValidateReturn.add(commodity.getSettleMarginAlgr_S() == null ? "" : commodity.getSettleMarginAlgr_S());
		this.jsonValidateReturn.add(commodity.getSettleMarginRate_S() == null ? "" : commodity.getSettleMarginRate_S());
		this.jsonValidateReturn.add(commodity.getPayoutAlgr() == null ? "" : commodity.getPayoutAlgr());
		this.jsonValidateReturn.add(commodity.getPayoutRate() == null ? "" : commodity.getPayoutRate());

		this.jsonValidateReturn.add(commodity.getMaxHoldQty() == null ? "" : commodity.getMaxHoldQty());
		this.jsonValidateReturn.add(commodity.getCleanMaxHoldQty() == null ? "" : commodity.getCleanMaxHoldQty());

		this.jsonValidateReturn.add(commodity.getFeeAlgr() == null ? "" : commodity.getFeeAlgr());
		this.jsonValidateReturn.add(commodity.getFeeRate_B() == null ? "" : commodity.getFeeRate_B());
		this.jsonValidateReturn.add(commodity.getFeeRate_S() == null ? "" : commodity.getFeeRate_S());
		this.jsonValidateReturn.add(commodity.getTodayCloseFeeRate_B() == null ? "" : commodity.getTodayCloseFeeRate_B());
		this.jsonValidateReturn.add(commodity.getTodayCloseFeeRate_S() == null ? "" : commodity.getTodayCloseFeeRate_S());
		this.jsonValidateReturn.add(commodity.getHistoryCloseFeeRate_B() == null ? "" : commodity.getHistoryCloseFeeRate_B());
		this.jsonValidateReturn.add(commodity.getHistoryCloseFeeRate_S() == null ? "" : commodity.getHistoryCloseFeeRate_S());
		this.jsonValidateReturn.add(commodity.getForceCloseFeeRate_B() == null ? "" : commodity.getForceCloseFeeRate_B());
		this.jsonValidateReturn.add(commodity.getForceCloseFeeRate_S() == null ? "" : commodity.getForceCloseFeeRate_S());

		this.jsonValidateReturn.add(commodity.getSettleFeeAlgr() == null ? "" : commodity.getSettleFeeAlgr());
		this.jsonValidateReturn.add(commodity.getSettleFeeRate_B() == null ? "" : commodity.getSettleFeeRate_B());
		this.jsonValidateReturn.add(commodity.getSettleFeeRate_S() == null ? "" : commodity.getSettleFeeRate_S());

		return "success";
	}

	public String getBreedJson() {
		HttpServletRequest request = getRequest();

		String breedID = request.getParameter("breedID");

		TBreed breed = new TBreed();
		breed.setBreedID(Integer.valueOf(Integer.parseInt(breedID)));
		breed = (TBreed) getService().get(breed);

		this.jsonValidateReturn = new JSONArray();

		this.jsonValidateReturn.add(breed.getMarginAlgr() == null ? "" : breed.getMarginAlgr());
		this.jsonValidateReturn.add(breed.getMarginItem1() == null ? "" : breed.getMarginItem1());
		this.jsonValidateReturn.add(breed.getMarginItem2() == null ? "" : breed.getMarginItem2());
		this.jsonValidateReturn.add(breed.getMarginItem3() == null ? "" : breed.getMarginItem3());
		this.jsonValidateReturn.add(breed.getMarginItem4() == null ? "" : breed.getMarginItem4());
		this.jsonValidateReturn.add(breed.getMarginItem5() == null ? "" : breed.getMarginItem5());
		this.jsonValidateReturn.add(breed.getMarginItem1_S() == null ? "" : breed.getMarginItem1_S());
		this.jsonValidateReturn.add(breed.getMarginItem2_S() == null ? "" : breed.getMarginItem2_S());
		this.jsonValidateReturn.add(breed.getMarginItem3_S() == null ? "" : breed.getMarginItem3_S());
		this.jsonValidateReturn.add(breed.getMarginItem4_S() == null ? "" : breed.getMarginItem4_S());
		this.jsonValidateReturn.add(breed.getMarginItem5_S() == null ? "" : breed.getMarginItem5_S());
		this.jsonValidateReturn.add(breed.getMarginItemAssure1() == null ? "" : breed.getMarginItemAssure1());
		this.jsonValidateReturn.add(breed.getMarginItemAssure2() == null ? "" : breed.getMarginItemAssure2());
		this.jsonValidateReturn.add(breed.getMarginItemAssure3() == null ? "" : breed.getMarginItemAssure3());
		this.jsonValidateReturn.add(breed.getMarginItemAssure4() == null ? "" : breed.getMarginItemAssure4());
		this.jsonValidateReturn.add(breed.getMarginItemAssure5() == null ? "" : breed.getMarginItemAssure5());
		this.jsonValidateReturn.add(breed.getMarginItemAssure1_S() == null ? "" : breed.getMarginItemAssure1_S());
		this.jsonValidateReturn.add(breed.getMarginItemAssure2_S() == null ? "" : breed.getMarginItemAssure2_S());
		this.jsonValidateReturn.add(breed.getMarginItemAssure3_S() == null ? "" : breed.getMarginItemAssure3_S());
		this.jsonValidateReturn.add(breed.getMarginItemAssure4_S() == null ? "" : breed.getMarginItemAssure4_S());
		this.jsonValidateReturn.add(breed.getMarginItemAssure5_S() == null ? "" : breed.getMarginItemAssure5_S());

		this.jsonValidateReturn.add(breed.getType1());
		this.jsonValidateReturn.add(breed.getType2());
		this.jsonValidateReturn.add(breed.getType3());
		this.jsonValidateReturn.add(breed.getType4());
		this.jsonValidateReturn.add(breed.getType5());

		this.jsonValidateReturn.add(breed.getSettleMarginAlgr_B() == null ? "" : breed.getSettleMarginAlgr_B());
		this.jsonValidateReturn.add(breed.getSettleMarginRate_B() == null ? "" : breed.getSettleMarginRate_B());
		this.jsonValidateReturn.add(breed.getSettleMarginAlgr_S() == null ? "" : breed.getSettleMarginAlgr_S());
		this.jsonValidateReturn.add(breed.getSettleMarginRate_S() == null ? "" : breed.getSettleMarginRate_S());
		this.jsonValidateReturn.add(breed.getPayoutAlgr() == null ? "" : breed.getPayoutAlgr());
		this.jsonValidateReturn.add(breed.getPayoutRate() == null ? "" : breed.getPayoutRate());

		this.jsonValidateReturn.add(breed.getMaxHoldQty() == null ? "" : breed.getMaxHoldQty());
		this.jsonValidateReturn.add(breed.getCleanMaxHoldQty() == null ? "" : breed.getCleanMaxHoldQty());

		this.jsonValidateReturn.add(breed.getFeeAlgr() == null ? "" : breed.getFeeAlgr());
		this.jsonValidateReturn.add(breed.getFeeRate_B() == null ? "" : breed.getFeeRate_B());
		this.jsonValidateReturn.add(breed.getFeeRate_S() == null ? "" : breed.getFeeRate_S());
		this.jsonValidateReturn.add(breed.getTodayCloseFeeRate_B() == null ? "" : breed.getTodayCloseFeeRate_B());
		this.jsonValidateReturn.add(breed.getTodayCloseFeeRate_S() == null ? "" : breed.getTodayCloseFeeRate_S());
		this.jsonValidateReturn.add(breed.getHistoryCloseFeeRate_B() == null ? "" : breed.getHistoryCloseFeeRate_B());
		this.jsonValidateReturn.add(breed.getHistoryCloseFeeRate_S() == null ? "" : breed.getHistoryCloseFeeRate_S());
		this.jsonValidateReturn.add(breed.getForceCloseFeeRate_B() == null ? "" : breed.getForceCloseFeeRate_B());
		this.jsonValidateReturn.add(breed.getForceCloseFeeRate_S() == null ? "" : breed.getForceCloseFeeRate_S());

		this.jsonValidateReturn.add(breed.getSettleFeeAlgr() == null ? "" : breed.getSettleFeeAlgr());
		this.jsonValidateReturn.add(breed.getSettleFeeRate_B() == null ? "" : breed.getSettleFeeRate_B());
		this.jsonValidateReturn.add(breed.getSettleFeeRate_S() == null ? "" : breed.getSettleFeeRate_S());

		return "success";
	}

	public String getMarketJson() {
		Market m = new Market();
		m.setMarketcode("99");
		Market market = (Market) getService().get(m);

		this.jsonValidateReturn = new JSONArray();

		this.jsonValidateReturn.add(market.getTradeTimeType() == null ? "" : market.getTradeTimeType());

		return "success";
	}
}