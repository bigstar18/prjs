/*     */ package gnnt.MEBS.timebargain.front.action;

/*     */
/*     */ import gnnt.MEBS.common.front.action.StandardAction;
/*     */ import gnnt.MEBS.common.front.common.Page;
/*     */ import gnnt.MEBS.common.front.common.PageRequest;
/*     */ import gnnt.MEBS.common.front.common.QueryConditions;
/*     */ import gnnt.MEBS.common.front.model.integrated.MFirm;
/*     */ import gnnt.MEBS.common.front.model.integrated.User;
/*     */ import gnnt.MEBS.common.front.service.StandardService;
/*     */ import gnnt.MEBS.common.front.statictools.ActionUtil;
/*     */ import gnnt.MEBS.common.front.statictools.Tools;
/*     */ import gnnt.MEBS.timebargain.front.model.DelayOrderHistory;
/*     */ import gnnt.MEBS.timebargain.front.service.DelayTradeService;
/*     */ import gnnt.MEBS.timebargain.server.model.DelayOrder;
/*     */ import gnnt.MEBS.timebargain.server.rmi.DelayRMI;
/*     */ import java.math.BigDecimal;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Controller;

/*     */
/*     */ @Controller("delayTradeAction")
/*     */ @Scope("request")
/*     */ public class DelayTradeAction extends StandardAction
/*     */ {
	/*     */ private static final long serialVersionUID = -7980403952928739624L;
	/*     */
	/*     */ @Autowired
	/*     */ @Qualifier("delayTradeService")
	/*     */ private DelayTradeService delayTradeService;
	/*     */
	/*     */ @Resource(name = "com_buySellFlag")
	/*     */ private Map<String, String> buySellFlag;
	/*     */
	/*     */ @Resource(name = "delay_delayOrderTypes")
	/*     */ private Map<String, String> delayOrderTypes;
	/*     */
	/*     */ @Resource(name = "delay_status")
	/*     */ private Map<String, String> status;
	/*     */
	/*     */ @Resource(name = "delay_withdrawType")
	/*     */ private Map<String, String> withdrawType;
	/* 45 */ private List<Map<Object, Object>> resultData = new ArrayList();
	/* 46 */ private String resultMsg = "";
	/*     */
	/*     */ @Autowired
	/*     */ @Qualifier("DelayRMI")
	/*     */ private DelayRMI delayRMI;

	/*     */
	/* 52 */ public String getResultMsg() {
		return this.resultMsg;
	}

	/*     */
	/*     */ public void setResultMsg(String resultMsg)
	/*     */ {
		/* 56 */ this.resultMsg = resultMsg;
		/*     */ }

	/*     */
	/*     */ public DelayTradeService getDelayTradeService() {
		/* 60 */ return this.delayTradeService;
		/*     */ }

	/*     */
	/*     */ public void setDelayTradeService(DelayTradeService delayTradeService) {
		/* 64 */ this.delayTradeService = delayTradeService;
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> getResultData() {
		/* 68 */ return this.resultData;
		/*     */ }

	/*     */
	/*     */ public void setResultData(List<Map<Object, Object>> resultData) {
		/* 72 */ this.resultData = resultData;
		/*     */ }

	/*     */
	/*     */ public Map<String, String> getBuySellFlag() {
		/* 76 */ return this.buySellFlag;
		/*     */ }

	/*     */
	/*     */ public void setBuySellFlag(Map<String, String> buySellFlag) {
		/* 80 */ this.buySellFlag = buySellFlag;
		/*     */ }

	/*     */
	/*     */ public Map<String, String> getDelayOrderTypes() {
		/* 84 */ return this.delayOrderTypes;
		/*     */ }

	/*     */
	/*     */ public void setDelayOrderTypes(Map<String, String> delayOrderTypes) {
		/* 88 */ this.delayOrderTypes = delayOrderTypes;
		/*     */ }

	/*     */
	/*     */ public Map<String, String> getStatus() {
		/* 92 */ return this.status;
		/*     */ }

	/*     */
	/*     */ public void setStatus(Map<String, String> status) {
		/* 96 */ this.status = status;
		/*     */ }

	/*     */
	/*     */ public Map<String, String> getWithdrawType() {
		/* 100 */ return this.withdrawType;
		/*     */ }

	/*     */
	/*     */ public void setWithdrawType(Map<String, String> withdrawType) {
		/* 104 */ this.withdrawType = withdrawType;
		/*     */ }

	/*     */
	/*     */ public void setDelayRMI(DelayRMI delayRMI) {
		/* 108 */ this.delayRMI = delayRMI;
		/*     */ }

	/*     */
	/*     */ public String delayTrade() {
		/* 112 */ List commodityIdList = comdtyCodeListQuery();
		/* 113 */ Map marketinfo = queryMarketInfo();
		/* 114 */ this.resultData = delayQuotation_query();
		/*     */
		/* 116 */ this.request.setAttribute("marketinfo", marketinfo);
		/* 117 */ this.request.setAttribute("commodityIdList", commodityIdList);
		/* 118 */ return "success";
		/*     */ }

	/*     */
	/*     */ public String delayRealTimeInfo() {
		/* 122 */ this.resultData = delayQuotation_query();
		/* 123 */ return "success";
		/*     */ }

	/*     */
	/*     */ public String delayOrderQuery() {
		/* 127 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 128 */ String commodityId = this.request.getParameter("commodityId");
		/*     */
		/* 130 */ this.resultData = this.delayTradeService.delayOrderQuery(user.getBelongtoFirm().getFirmID(), commodityId);
		/* 131 */ return "success";
		/*     */ }

	/*     */
	/*     */ public String cancelDelayOrder() {
		/* 135 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 136 */ Long sessionId = user.getSessionId();
		/* 137 */ String[] orderNos = this.request.getParameterValues("orderNos[]");
		/* 138 */ for (int i = 0; i < orderNos.length; i++) {
			/* 139 */ DelayOrder delayOrder = this.delayTradeService.getDelayOrderById(orderNos[i]);
			/* 140 */ if ((delayOrder.getStatus().shortValue() == 1) || (delayOrder.getStatus().shortValue() == 2)) {
				/* 141 */ delayOrder.setWithdrawID(Long.valueOf(Long.parseLong(orderNos[i])));
				/* 142 */ delayOrder.setDelayOrderType(new Short("4"));
				/* 143 */ String msg = submitDelayOrder(sessionId.longValue(), delayOrder);
				/* 144 */ this.logger.info("request checkBox:" + orderNos[i] + ",msg=" + this.resultMsg);
				/* 145 */ this.resultMsg = (this.resultMsg + "委托号:" + orderNos[i] + "，处理结果：" + ("".equals(msg) ? "成功" : msg) + "\r\n");
				/*     */ } else {
				/* 147 */ this.resultMsg = (this.resultMsg + "委托号:" + orderNos[i] + "，处理结果：状态不符，撤销失败\r\n");
				/*     */ }
			/*     */ }
		/* 150 */ return "success";
		/*     */ }

	/*     */
	/*     */ public String capitalQuery() {
		/* 154 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 155 */ String sql = "select (a.balance - a.frozenfunds) usefulFund,  a.frozenfunds from F_FIRMFUNDS  a where firmid = '" +
				/* 156 */ user.getBelongtoFirm().getFirmID() + "'";
		/* 157 */ this.resultData = getService().getListBySql(sql);
		/* 158 */ return "success";
		/*     */ }

	/*     */
	/*     */ public String delayCommodityHoldingQuery() {
		/* 162 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 163 */ String commodityId = this.request.getParameter("commodityId");
		/*     */
		/* 165 */ this.resultData = this.delayTradeService.delayCommodityHoldingQuery(user.getBelongtoFirm().getFirmID(), commodityId);
		/*     */
		/* 167 */ return "success";
		/*     */ }

	/*     */
	/*     */ public String delayOrder() {
		/* 171 */ String commodityId = this.request.getParameter("commodityId");
		/* 172 */ String delayOrderType = this.request.getParameter("delayOrderType");
		/* 173 */ String bsFlag = this.request.getParameter("bsFlag");
		/* 174 */ String quantity = this.request.getParameter("quantity");
		/* 175 */ if (Short.valueOf(delayOrderType).shortValue() == 2) {
			/* 176 */ bsFlag = this.delayTradeService.getNeutralSideById(commodityId);
			/*     */ }
		/* 178 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 179 */ DelayOrder delayOrder = new DelayOrder();
		/* 180 */ delayOrder.setCommodityID(commodityId);
		/* 181 */ delayOrder.setDelayOrderType(Short.valueOf(delayOrderType));
		/* 182 */ delayOrder.setBuyOrSell(Short.valueOf(bsFlag));
		/* 183 */ delayOrder.setTraderID(user.getTraderID());
		/* 184 */ delayOrder.setCustomerID(user.getBelongtoFirm().getFirmID() + "00");
		/* 185 */ delayOrder.setQuantity(Long.valueOf(quantity));
		/* 186 */ delayOrder.setFirmID(user.getBelongtoFirm().getFirmID());
		/*     */
		/* 188 */ Long sessionId = user.getSessionId();
		/*     */
		/* 190 */ boolean result = checkSettlePrivilege(delayOrder.getBuyOrSell(), delayOrder.getFirmID(), delayOrder.getDelayOrderType(),
				/* 191 */ delayOrder.getCommodityID());
		/* 192 */ if (result) {
			/* 193 */ if (checkNeutralQty(delayOrder)) {
				/* 194 */ this.resultMsg = submitDelayOrder(sessionId.longValue(), delayOrder);
				/* 195 */ if ("".equals(this.resultMsg)) {
					/* 196 */ this.resultMsg = "申报成功！";
					/*     */ }
				/*     */ }
			/*     */ }
			/*     */ else
		/*     */ {
			/* 202 */ this.resultMsg =
			/* 203 */ ("没有" + (delayOrder.getBuyOrSell().shortValue() == 1 ? "买" : delayOrder.getDelayOrderType().shortValue() == 1 ? "卖" : "") + (
			/* 203 */ delayOrder.getDelayOrderType().shortValue() == 1 ? "交收申报" : "中立仓申报") + "权限！");
			/*     */ }
		/* 205 */ return "success";
		/*     */ }

	/*     */
	/*     */ public String delayOrderHistory() {
		/* 209 */ this.logger.debug("历史委托查询");
		/* 210 */ User user = (User) this.request.getSession().getAttribute("CurrentUser");
		/* 211 */ PageRequest pageRequest = null;
		/*     */ try {
			/* 213 */ pageRequest = ActionUtil.getPageRequest(this.request);
			/*     */ } catch (Exception e) {
			/* 215 */ this.logger.error(Tools.getExceptionTrace(e));
			/*     */ }
		/* 217 */ ((QueryConditions) pageRequest.getFilters()).addCondition("traderID", "=", user.getTraderID());
		/* 218 */ Page page = getService().getPage(pageRequest, new DelayOrderHistory());
		/* 219 */ this.request.setAttribute("pageInfo", page);
		/* 220 */ this.request.setAttribute("oldParams", ActionUtil.getParametersStartingWith(this.request, "gnnt_"));
		/*     */
		/* 222 */ return "success";
		/*     */ }

	/*     */
	/*     */ private boolean checkNeutralQty(DelayOrder delayOrder) {
		/* 226 */ if (delayOrder.getDelayOrderType().shortValue() != 2) {
			/* 227 */ return true;
			/*     */ }
		/* 229 */ String sql = "SELECT d.BuySettleQty, d.SellSettleQty, d.BuyNeutralQty, d.SellNeutralQty  from T_DelayQuotation d where d.CommodityID = '"
				+
				/* 230 */ delayOrder.getCommodityID() + "'";
		/* 231 */ List delayQuotationList = getService().getListBySql(sql);
		/* 232 */ if ((delayQuotationList == null) || (delayQuotationList.size() == 0)) {
			/* 233 */ throw new RuntimeException("商品 [" + delayOrder.getCommodityID() + "] 在递延交收行情表中未找到记录!");
			/*     */ }
		/* 235 */ Map delayQuotationMap = (Map) delayQuotationList.get(0);
		/* 236 */ int bsq = ((BigDecimal) delayQuotationMap.get("BUYSETTLEQTY")).intValue();
		/* 237 */ int ssq = ((BigDecimal) delayQuotationMap.get("SELLSETTLEQTY")).intValue();
		/* 238 */ int bnq = ((BigDecimal) delayQuotationMap.get("BUYNEUTRALQTY")).intValue();
		/* 239 */ int snq = ((BigDecimal) delayQuotationMap.get("SELLNEUTRALQTY")).intValue();
		/* 240 */ int bs_sq = bsq + bnq - (ssq + snq) < 0 ? ssq + snq - (bsq + bnq) : bsq + bnq - (ssq + snq);
		/* 241 */ if (delayOrder.getQuantity().longValue() > bs_sq) {
			/* 242 */ return false;
			/*     */ }
		/* 244 */ return true;
		/*     */ }

	/*     */
	/*     */ private boolean checkSettlePrivilege(Short buyOrSell, String firmID, Short delayorderType, String commodityID) {
		/* 248 */ String sql = "select a.PrivilegeCode_B, a.PrivilegeCode_S From t_a_SettlePrivilege a Where a.Kind = 2 and a.Type='1' and a.typeID = '"
				+
				/* 250 */ firmID + "' and KindID = '" + commodityID + "'";
		/*     */
		/* 252 */ List settlePrivilegList = getService().getListBySql(sql);
		/* 253 */ if ((settlePrivilegList == null) || (settlePrivilegList.size() == 0)) {
			/* 254 */ return false;
			/*     */ }
		/* 256 */ Iterator localIterator = settlePrivilegList.iterator();
		/* 257 */ if (localIterator.hasNext()) {
			/* 258 */ Map settlePrivilegMap = (Map) localIterator.next();
			/* 259 */ if (1 == buyOrSell.shortValue()) {
				/* 260 */ String buyPrivilegeCode = String.valueOf(settlePrivilegMap.get("PRIVILEGECODE_B"));
				/* 261 */ if ("101".equals(buyPrivilegeCode)) {
					/* 262 */ return true;
					/*     */ }
				/* 264 */ if (("102".equals(buyPrivilegeCode)) && (1 == Short.valueOf(delayorderType.shortValue()).shortValue())) {
					/* 265 */ return true;
					/*     */ }
				/* 267 */ if (("103".equals(buyPrivilegeCode)) && (2 == Short.valueOf(delayorderType.shortValue()).shortValue())) {
					/* 268 */ return true;
					/*     */ }
				/* 270 */ return false;
				/*     */ }
			/* 272 */ String sellPrivilegeCode = String.valueOf(settlePrivilegMap.get("PRIVILEGECODE_S"));
			/* 273 */ if ("101".equals(sellPrivilegeCode)) {
				/* 274 */ return true;
				/*     */ }
			/* 276 */ if (("102".equals(sellPrivilegeCode)) && (1 == Short.valueOf(delayorderType.shortValue()).shortValue())) {
				/* 277 */ return true;
				/*     */ }
			/* 279 */ if (("103".equals(sellPrivilegeCode)) && (2 == Short.valueOf(delayorderType.shortValue()).shortValue())) {
				/* 280 */ return true;
				/*     */ }
			/* 282 */ return false;
			/*     */ }
		/* 284 */ return false;
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> comdtyCodeListQuery() {
		/* 288 */ String sql = "select CommodityID from T_Commodity where SettleWay = 1";
		/* 289 */ return getService().getListBySql(sql);
		/*     */ }

	/*     */
	/*     */ public Map<Object, Object> queryMarketInfo() {
		/* 293 */ String sql = "select * from T_a_market";
		/* 294 */ return (Map) getService().getListBySql(sql).get(0);
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> delayQuotation_query() {
		/* 298 */ return this.delayTradeService.delayQuotation_query();
		/*     */ }

	/*     */
	/*     */ private String submitDelayOrder(long sessionID, DelayOrder ov) {
		/* 302 */ int ret = 65286;
		/* 303 */ String message = "";
		/*     */ try {
			/* 305 */ this.logger.info("------------>ready for submitDelayOrder.......");
			/* 306 */ ret = this.delayRMI.order(sessionID, ov, "web");
			/* 307 */ this.logger.info("------------>submitDelayOrder over....ret=" + ret);
			/*     */ } catch (RemoteException e) {
			/* 309 */ e.printStackTrace();
			/* 310 */ message = "RemoteException : " + e.getMessage();
			/*     */ } catch (Exception e) {
			/* 312 */ e.printStackTrace();
			/* 313 */ message = "Remot Application Error : " + e.getMessage();
			/*     */ }
		/* 315 */ switch (ret) {
		/*     */ case 0:
			/* 317 */ break;
		/*     */ case 1:
			/* 319 */ message = "身份不合法！";
			/* 320 */ break;
		/*     */ case 2:
			/* 322 */ message = "市场未启用！";
			/* 323 */ break;
		/*     */ case 3:
			/* 325 */ message = "现在不是交易时间！";
			/* 326 */ break;
		/*     */ case 4:
			/* 328 */ message = "不是代为委托员交易时间！";
			/* 329 */ break;
		/*     */ case 5:
			/* 331 */ message = "交易员和代为委托员不能同时存在！";
			/* 332 */ break;
		/*     */ case 10:
			/* 334 */ message = "商品处于禁止交易状态！";
			/* 335 */ break;
		/*     */ case 11:
			/* 337 */ message = "商品不属于当前交易节！";
			/* 338 */ break;
		/*     */ case 12:
			/* 340 */ message = "委托价格超出涨幅上限！";
			/* 341 */ break;
		/*     */ case 13:
			/* 343 */ message = "委托价格低于跌幅下限！";
			/* 344 */ break;
		/*     */ case 14:
			/* 346 */ message = "委托价格不在此商品最小价位变动范围内！";
			/* 347 */ break;
		/*     */ case 15:
			/* 349 */ message = "不存在此商品！";
			/* 350 */ break;
		/*     */ case 16:
			/* 352 */ message = "委托数量不在此商品最小交收变动数量范围内！";
			/* 353 */ break;
		/*     */ case 17:
			/* 355 */ message = "委托数量少于商品最小交收数量！";
			/* 356 */ break;
		/*     */ case 30:
			/* 358 */ message = "此交易员不存在！";
			/* 359 */ break;
		/*     */ case 31:
			/* 361 */ message = "此交易员没有操作该客户的权限！";
			/* 362 */ break;
		/*     */ case 32:
			/* 364 */ message = "此交易客户不存在！";
			/* 365 */ break;
		/*     */ case 33:
			/* 367 */ message = "此交易客户为禁止交易状态！";
			/* 368 */ break;
		/*     */ case 34:
			/* 370 */ message = "此交易商不存在！";
			/* 371 */ break;
		/*     */ case 35:
			/* 373 */ message = "此交易商为禁止交易状态！";
			/* 374 */ break;
		/*     */ case 37:
			/* 376 */ message = "此代为委托员没有操作该交易商的权限！";
			/* 377 */ break;
		/*     */ case 38:
			/* 379 */ message = "此代为委托员不存在！";
			/* 380 */ break;
		/*     */ case 40:
			/* 382 */ message = "计算交易保证金错误！";
			/* 383 */ break;
		/*     */ case 41:
			/* 385 */ message = "计算交易手续费错误！";
			/* 386 */ break;
		/*     */ case 42:
			/* 388 */ message = "此委托已成交或已撤单！";
			/* 389 */ break;
		/*     */ case 50:
			/* 391 */ message = "不是交收申报时间！";
			/* 392 */ break;
		/*     */ case 51:
			/* 394 */ message = "不是中立仓申报时间！";
			/* 395 */ break;
		/*     */ case 52:
			/* 397 */ message = "此商品不允许下中立仓！";
			/* 398 */ break;
		/*     */ case 53:
			/* 400 */ message = "中立仓申报方向不对！";
			/* 401 */ break;
		/*     */ case 54:
			/* 403 */ message = "未知被撤委托类型！";
			/* 404 */ break;
		/*     */ case 55:
			/* 406 */ message = "该商品禁止交收申报！";
			/* 407 */ break;
		/*     */ case 56:
			/* 409 */ message = "该商品禁止中立仓申报！";
			/* 410 */ break;
		/*     */ case 200:
			/* 412 */ message = "代码异常而失败！";
			/* 413 */ break;
		/*     */ case -1:
			/* 415 */ message = "持仓不足！";
			/* 416 */ break;
		/*     */ case -2:
			/* 418 */ message = "资金余额不足！";
			/* 419 */ break;
		/*     */ case -7:
			/* 421 */ message = "超过单笔最大委托量!";
			/* 422 */ break;
		/*     */ case -31:
			/* 424 */ message = "持仓不足！";
			/* 425 */ break;
		/*     */ case -32:
			/* 427 */ message = "仓单数量不足！";
			/* 428 */ break;
		/*     */ case -99:
			/* 430 */ message = "执行存储时未找到相关数据！";
			/* 431 */ break;
		/*     */ case -100:
			/* 433 */ message = "执行存储失败！";
			/* 434 */ break;
		/*     */ case -204:
			/* 436 */ message = "交易服务器已关闭！";
			/* 437 */ break;
		/*     */ case -206:
			/* 439 */ message = "委托信息不能为空！";
			/* 440 */ break;
		/*     */ default:
			/* 442 */ ret = 65333;
			/* 443 */ message = "未知异常！";
			/*     */ }
		/* 445 */ return message;
		/*     */ }
	/*     */ }