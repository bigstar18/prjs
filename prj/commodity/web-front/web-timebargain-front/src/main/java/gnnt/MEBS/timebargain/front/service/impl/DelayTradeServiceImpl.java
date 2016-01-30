/*     */ package gnnt.MEBS.timebargain.front.service.impl;

/*     */
/*     */ import gnnt.MEBS.common.front.service.StandardService;
/*     */ import gnnt.MEBS.timebargain.front.dao.DelayTradeDao;
/*     */ import gnnt.MEBS.timebargain.front.service.DelayTradeService;
/*     */ import gnnt.MEBS.timebargain.server.model.DelayOrder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;

/*     */
/*     */ @Service("delayTradeService")
/*     */ @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
/*     */ public class DelayTradeServiceImpl extends StandardService/*     */ implements DelayTradeService
/*     */ {
	/*     */
	/*     */ @Autowired
	/*     */ @Qualifier("delayTradeDao")
	/*     */ private DelayTradeDao delayDao;

	/*     */
	/*     */ public void setDelayDao(DelayTradeDao delayDao)
	/*     */ {
		/* 47 */ this.delayDao = delayDao;
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> delayQuotation_query()
	/*     */ {
		/* 54 */ StringBuilder sb = new StringBuilder();
		/* 55 */ sb.append(" select a.commodityid,a.Price, a.totalamount,a.reservecount, b.buysettleqty, b.sellsettleqty ")
				/* 56 */ .append(" ,b.BuyNeutralQty,b.SellNeutralQty,decode(b.NeutralSide,1,'买',2,'卖','-') as NeutralSide")
				/* 57 */ .append(" from t_quotation a, t_delayquotation b")/* 58 */ .append(" where a.commodityid = b.commodityid")
				/* 59 */ .append(" order by  a.commodityid");
		/* 60 */ return getListBySql(sb.toString());
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> delayOrderQuery(String firmId, String commodityId)
	/*     */ {
		/* 67 */ StringBuilder sb = new StringBuilder();
		/* 68 */ sb.append("select A_OrderNo,to_char(OrderTime,'hh24:mi:ss') time,CommodityID,CustomerID, ")
				/* 69 */ .append("case when BS_Flag=1 then '买' when BS_Flag=2 then '卖' end as flag, ")
				/* 70 */ .append("Price,Quantity,(Quantity-TradeQty) noTradeQty,Status, ")
				/* 71 */ .append("case when DelayOrderType=1 then '交收申报' when DelayOrderType=2 then '中立仓' end as Type ")
				/* 72 */ .append("from T_DelayOrders where CustomerID = '").append(firmId).append("00' ");
		/* 73 */ if ((commodityId != null) && (!commodityId.equals("")))
		/*     */ {
			/* 75 */ commodityId = commodityId.trim();
			/* 76 */ sb.append(" and CommodityID='").append(commodityId).append("'");
			/*     */ }
		/*     */
		/* 79 */ sb.append(" order by A_OrderNo,OrderTime");
		/* 80 */ this.logger.debug("delay_order_query:" + sb.toString());
		/* 81 */ return getListBySql(sb.toString());
		/*     */ }

	/*     */
	/*     */ public List<Map<Object, Object>> delayCommodityHoldingQuery(String firmId, String commodityId)
	/*     */ {
		/* 88 */ String sql = "select s.CustomerID,s.CommodityID,s.HoldQty,s.FrozenQty,  case when s.BS_Flag=1 then '买' when s.BS_Flag=2 then '卖' end as flag   from T_CustomerHoldSum s, T_Commodity c where s.commodityid = c.commodityid and c.settleway = 1 ";
		/*     */
		/* 91 */ if (firmId != null)
		/*     */ {
			/* 93 */ sql = sql + " and s.CustomerID='" + firmId + "00'";
			/*     */ }
		/* 95 */ if ((commodityId != null) && (!commodityId.equals(""))) {
			/* 96 */ commodityId = commodityId.trim();
			/* 97 */ sql = sql + " and s.CommodityID='" + commodityId + "'";
			/*     */ }
		/* 99 */ this.logger.debug("delayCommodityHoldingQuery:" + sql);
		/* 100 */ return getListBySql(sql);
		/*     */ }

	/*     */
	/*     */ public String getNeutralSideById(String commodityId)
	/*     */ {
		/* 107 */ List neutralSideList = getListBySql("select d.NeutralSide from T_DelayQuotation d where d.commodityId = '" + commodityId + "'");
		/*     */
		/* 109 */ if ((neutralSideList == null) || (neutralSideList.size() == 0)) {
			/* 110 */ return "0";
			/*     */ }
		/* 112 */ return String.valueOf(((Map) neutralSideList.get(0)).get("NEUTRALSIDE"));
		/*     */ }

	/*     */
	/*     */ public DelayOrder getDelayOrderById(String orderNo)
	/*     */ {
		/* 121 */ return this.delayDao.getDelayOrderById(orderNo);
		/*     */ }
	/*     */ }