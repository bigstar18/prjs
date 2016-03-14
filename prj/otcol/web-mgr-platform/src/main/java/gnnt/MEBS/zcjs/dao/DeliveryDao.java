package gnnt.MEBS.zcjs.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import gnnt.MEBS.base.copy.CommonRowMapper;
import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.model.Delivery;

public class DeliveryDao extends DaoHelperImpl {
	public final transient Log logger = LogFactory.getLog(DeliveryDao.class);

	public void insert(Delivery paramDelivery) {
		String str = "insert into Z_Delivery (deliveryId,tradeNo,breedId,quantity,price,firmId_s,sellMargin,firmId_b,buyMargin,type,regStockId,status) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] arrayOfObject = { Long.valueOf(paramDelivery.getDeliveryId()), Long.valueOf(paramDelivery.getTradeNo()),
				Long.valueOf(paramDelivery.getBreedId()), Double.valueOf(paramDelivery.getQuantity()), Double.valueOf(paramDelivery.getPrice()),
				paramDelivery.getFirmId_s(), Double.valueOf(paramDelivery.getSellMargin()), paramDelivery.getFirmId_b(),
				Double.valueOf(paramDelivery.getBuyMargin()), Integer.valueOf(paramDelivery.getType()), paramDelivery.getRegStockId(),
				Integer.valueOf(paramDelivery.getStatus()) };
		int[] arrayOfInt = { 2, 2, 2, 2, 2, 12, 2, 12, 12, 2, 12, 2 };
		updateBySQL(str, arrayOfObject, arrayOfInt);
	}

	public long getId() {
		long l = 0L;
		String str = "select SEQ_Z_delivery.nextVal from dual";
		l = queryForInt(str, null);
		return l;
	}

	public List getTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		String str = "select * from Z_Delivery";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		this.logger.debug("sql: " + str);
		return queryBySQL(str, arrayOfObject, paramPageInfo);
	}

	public double getSum(QueryConditions paramQueryConditions) {
		String str = "select nvl(sum(quantity),0) from z_delivery ";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		return ((Double) queryForObject(str, arrayOfObject, Double.class)).doubleValue();
	}

	public Delivery getObject(long paramLong) {
		String str = "select * from Z_Delivery where deliveryId=?";
		Object[] arrayOfObject = { Long.valueOf(paramLong) };
		int[] arrayOfInt = { 2 };
		List localList = queryBySQL(str, arrayOfObject, arrayOfInt, null, new CommonRowMapper(new Delivery()));
		Delivery localDelivery = null;
		if ((localList.size() > 0) && (localList != null)) {
			localDelivery = (Delivery) localList.get(0);
		}
		return localDelivery;
	}

	public void update(Delivery paramDelivery) {
		String str = "update Z_Delivery set tradeNo=?,breedId=?,quantity=?,price=?,firmId_s=?,sellMargin=?,firmId_b=?,buyMargin=?,type=?,regStockId=?,status=?  where deliveryId=? ";
		Object[] arrayOfObject = { Long.valueOf(paramDelivery.getTradeNo()), Long.valueOf(paramDelivery.getBreedId()),
				Double.valueOf(paramDelivery.getQuantity()), Double.valueOf(paramDelivery.getPrice()), paramDelivery.getFirmId_s(),
				Double.valueOf(paramDelivery.getSellMargin()), paramDelivery.getFirmId_b(), Double.valueOf(paramDelivery.getBuyMargin()),
				Integer.valueOf(paramDelivery.getType()), paramDelivery.getRegStockId(), Integer.valueOf(paramDelivery.getStatus()),
				Long.valueOf(paramDelivery.getDeliveryId()) };
		int[] arrayOfInt = { 2, 2, 2, 2, 12, 2, 12, 2, 2, 12, 2, 2 };
		updateBySQL(str, arrayOfObject, arrayOfInt);
	}

	public List<Delivery> getObjectList(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		String str = "select * from z_delivery";
		Object[] arrayOfObject = null;
		if ((paramQueryConditions != null) && (paramQueryConditions.getFieldsSqlClause() != null)) {
			arrayOfObject = paramQueryConditions.getValueArray();
			str = str + " where " + paramQueryConditions.getFieldsSqlClause();
		}
		return queryBySQL(str, arrayOfObject, paramPageInfo, new CommonRowMapper(new Delivery()));
	}

	public int createSettleMatch(Delivery paramDelivery, String paramString, double paramDouble1, double paramDouble2) {
		CreateSettleMatch localCreateSettleMatch = new CreateSettleMatch(getDataSource());
		HashMap localHashMap = new HashMap();
		localHashMap.put("p_moduleid", Integer.valueOf(3));
		localHashMap.put("p_breedid", Long.valueOf(paramDelivery.getBreedId()));
		localHashMap.put("p_weight", Double.valueOf(paramDouble1));
		localHashMap.put("p_result", Integer.valueOf(paramDelivery.getType()));
		localHashMap.put("p_commodityId", "");
		localHashMap.put("p_firmID_B", paramDelivery.getFirmId_b());
		localHashMap.put("p_buyPrice", Double.valueOf(paramDelivery.getPrice() / paramDouble2));
		localHashMap.put("p_buypayout_ref", Double.valueOf(paramDelivery.getPrice() * paramDouble1 / paramDouble2));
		localHashMap.put("p_buyMargin", Double.valueOf(paramDelivery.getBuyMargin()));
		localHashMap.put("p_buyPayout", Integer.valueOf(0));
		localHashMap.put("p_firmID_S", paramDelivery.getFirmId_s());
		localHashMap.put("p_sellPrice", Double.valueOf(paramDelivery.getPrice() / paramDouble2));
		localHashMap.put("p_sellIncome_Ref", Double.valueOf(paramDelivery.getPrice() * paramDouble1 / paramDouble2));
		localHashMap.put("p_sellMargin", Double.valueOf(paramDelivery.getSellMargin()));
		localHashMap.put("p_regStockID", paramDelivery.getRegStockId());
		localHashMap.put("p_contractId", Long.valueOf(paramDelivery.getTradeNo()));
		localHashMap.put("p_xml", "");
		localHashMap.put("p_operator", paramString);
		localHashMap.put("p_module", Integer.valueOf(3));
		Map localMap = localCreateSettleMatch.execute(localHashMap);
		int i = ((BigDecimal) localMap.get("ret")).intValue();
		return i;
	}

	private class CreateSettleMatch extends StoredProcedure {
		private static final String SFUNC_NAME = "FN_S_createSettleMatch";

		public CreateSettleMatch(DataSource paramDataSource) {
			super(paramDataSource, "FN_S_createSettleMatch");
			setFunction(true);
			declareParameter(new SqlOutParameter("ret", 2));
			declareParameter(new SqlParameter("p_moduleid", 12));
			declareParameter(new SqlParameter("p_breedid", 2));
			declareParameter(new SqlParameter("p_weight", 2));
			declareParameter(new SqlParameter("p_result", 2));
			declareParameter(new SqlParameter("p_commodityId", 12));
			declareParameter(new SqlParameter("p_firmID_B", 12));
			declareParameter(new SqlParameter("p_buyPrice", 2));
			declareParameter(new SqlParameter("p_buypayout_ref", 2));
			declareParameter(new SqlParameter("p_buyMargin", 2));
			declareParameter(new SqlParameter("p_buyPayout", 2));
			declareParameter(new SqlParameter("p_firmID_S", 12));
			declareParameter(new SqlParameter("p_sellPrice", 2));
			declareParameter(new SqlParameter("p_sellIncome_Ref", 2));
			declareParameter(new SqlParameter("p_sellMargin", 2));
			declareParameter(new SqlParameter("p_regStockID", 12));
			declareParameter(new SqlParameter("p_contractId", 2));
			declareParameter(new SqlParameter("p_xml", 12));
			declareParameter(new SqlParameter("p_operator", 12));
			declareParameter(new SqlParameter("p_module", 12));
			compile();
		}

		public Map execute(Map paramMap) {
			return super.execute(paramMap);
		}
	}
}
