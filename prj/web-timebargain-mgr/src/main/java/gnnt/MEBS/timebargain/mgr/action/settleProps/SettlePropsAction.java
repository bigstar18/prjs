package gnnt.MEBS.timebargain.mgr.action.settleProps;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import gnnt.MEBS.common.mgr.common.Page;
import gnnt.MEBS.common.mgr.common.PageRequest;
import gnnt.MEBS.common.mgr.model.StandardModel;
import gnnt.MEBS.common.mgr.statictools.Tools;
import gnnt.MEBS.timebargain.mgr.action.ECSideAction;
import gnnt.MEBS.timebargain.mgr.model.settleProps.BreedP;
import gnnt.MEBS.timebargain.mgr.model.settleProps.BreedProps;
import gnnt.MEBS.timebargain.mgr.model.settleProps.Category;
import gnnt.MEBS.timebargain.mgr.model.settleProps.CategoryProperty;
import gnnt.MEBS.timebargain.mgr.model.settleProps.CommodityP;
import gnnt.MEBS.timebargain.mgr.model.settleProps.SettleCommodityP;
import gnnt.MEBS.timebargain.mgr.model.settleProps.SettlePropsP;
import gnnt.MEBS.timebargain.mgr.service.SettlePropsService;
import net.sf.json.JSONArray;

@Controller("settlePropsAction")
@Scope("request")
public class SettlePropsAction extends ECSideAction {
	private static final long serialVersionUID = -1L;

	@Autowired
	@Qualifier("settlePropsService")
	private SettlePropsService settlePropsService;
	private JSONArray jsonReturn;
	private List<SettlePropsP> goodsPropertys;

	public List<SettlePropsP> getGoodsPropertys() {
		return this.goodsPropertys;
	}

	public void setGoodsPropertys(List<SettlePropsP> paramList) {
		this.goodsPropertys = paramList;
	}

	public JSONArray getJsonReturn() {
		return this.jsonReturn;
	}

	public void setJsonReturn(JSONArray paramJSONArray) {
		this.jsonReturn = paramJSONArray;
	}

	public String commodityList() throws Exception {
		String str = this.request.getParameter("flag");
		if ((str != null) && (str.equals("S"))) {
			this.entity = new SettleCommodityP();
			str = "S";
		} else {
			this.entity = new CommodityP();
			str = "C";
		}
		this.request.setAttribute("flag", str);
		list();
		return "success";
	}

	public String viewSettlePropsById() throws Exception {
		String str1 = this.request.getParameter("commodityId");
		String str2 = this.request.getParameter("flag");
		Object localObject;
		if ((str2 != null) && (str2.equals("S"))) {
			localObject = new SettleCommodityP();
			((SettleCommodityP) localObject).setCommodityId(str1);
			this.entity = ((StandardModel) localObject);
			str2 = "S";
		} else {
			localObject = new CommodityP();
			((CommodityP) localObject).setCommodityId(str1);
			this.entity = ((StandardModel) localObject);
			str2 = "C";
		}
		this.request.setAttribute("flag", str2);
		viewById();
		return "success";
	}

	public String deleteSettleProps() {
		String str = this.request.getParameter("commodityId");
		List localList = getService().getListBySql("select * from T_settleProps where commodityId='" + str + "'", new SettlePropsP());
		getService().deleteBYBulk(localList);
		addReturnValue(1, 150520L);
		return "success";
	}

	public String getPropertyValueByBreedID() {
		this.logger.debug("通过品名编号获取品名属性信息");
		String str = this.request.getParameter("commodityId");
		List localList = getService().getListBySql("select * from T_settleProps where commodityId='" + str + "'", new SettlePropsP());
		long l1 = Tools.strToLong(this.request.getParameter("breedId"), -1000L);
		long l2 = Tools.strToLong(this.request.getParameter("sortId"), -1000L);
		this.jsonReturn = new JSONArray();
		PageRequest localPageRequest = new PageRequest(
				" and primary.type='leaf'and primary.status=1 and primary.belongModule like '%15%' and primary.categoryId=" + l2);
		localPageRequest.setSortColumns(" order by  primary.parentCategory.sortNo,primary.sortNo");
		Page localPage = getService().getPage(localPageRequest, new Category());
		if ((localPage.getResult() != null) && (localPage.getResult().size() > 0)) {
			Iterator localIterator1 = localPage.getResult().iterator();
			while (localIterator1.hasNext()) {
				StandardModel localStandardModel = (StandardModel) localIterator1.next();
				Category localCategory = (Category) localStandardModel;
				Iterator localIterator2 = localCategory.getBreedSet().iterator();
				while (localIterator2.hasNext()) {
					BreedP localBreedP = (BreedP) localIterator2.next();
					if (l1 == localBreedP.getBreedId().longValue()) {
						Iterator localIterator3 = localCategory.getPropertySet().iterator();
						while (localIterator3.hasNext()) {
							CategoryProperty localCategoryProperty = (CategoryProperty) localIterator3.next();
							JSONArray localJSONArray = new JSONArray();
							localJSONArray.add(localCategoryProperty.getPropertyName());
							localJSONArray.add(localCategoryProperty.getHasValueDict());
							Object localObject2;
							Object localObject3;
							if ("Y".equalsIgnoreCase(localCategoryProperty.getHasValueDict())) {
								JSONArray localObject1 = new JSONArray();
								localObject2 = localBreedP.getPropsSet().iterator();
								while (((Iterator) localObject2).hasNext()) {
									localObject3 = (BreedProps) ((Iterator) localObject2).next();
									if (((BreedProps) localObject3).getCategoryProperty().getPropertyId() == localCategoryProperty.getPropertyId())
										((JSONArray) localObject1).add(((BreedProps) localObject3).getPropertyValue());
								}
								localJSONArray.add(localObject1);
							}
							localJSONArray.add(localCategoryProperty.getIsNecessary());
							localJSONArray.add(localCategoryProperty.getFieldType());
							Object localObject1 = localList.iterator();
							while (((Iterator) localObject1).hasNext()) {
								localObject2 = (StandardModel) ((Iterator) localObject1).next();
								localObject3 = (SettlePropsP) localObject2;
								if (localCategoryProperty.getPropertyName().equals(((SettlePropsP) localObject3).getPropertyName())) {
									localJSONArray.add(((SettlePropsP) localObject3).getPropertyValue());
									localJSONArray.add(((SettlePropsP) localObject3).getNote());
								}
							}
							localJSONArray.add(localCategoryProperty.getStockCheck());
							this.jsonReturn.add(localJSONArray);
						}
						this.logger.debug("获取属性名称或者值");
					}
				}
			}
		}
		return "success";
	}

	public String addSettleProps() {
		SettlePropsP localSettlePropsP = (SettlePropsP) this.entity;
		try {
			this.settlePropsService.addSettleProps(localSettlePropsP, this.goodsPropertys);
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		addReturnValue(1, 111501L);
		return "success";
	}
}