package gnnt.MEBS.bill.core.dao.jdbc;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import gnnt.MEBS.bill.core.dao.ObjectRowMapper;
import gnnt.MEBS.bill.core.dao.WareHouseStockDAO;
import gnnt.MEBS.bill.core.po.DismantlePO;
import gnnt.MEBS.bill.core.po.FinancingStockPO;
import gnnt.MEBS.bill.core.po.FrozenStockPO;
import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.po.OutStockPO;
import gnnt.MEBS.bill.core.po.PledgeStockPO;
import gnnt.MEBS.bill.core.po.StockOperationPO;
import gnnt.MEBS.bill.core.po.StockPO;
import gnnt.MEBS.bill.core.po.TradeModelPO;
import gnnt.MEBS.bill.core.po.TradeStockPO;
import gnnt.MEBS.bill.core.po.WarehousePO;
import gnnt.MEBS.bill.core.vo.StockOperation;
import gnnt.MEBS.bill.core.vo.StockVO;

public class WareHouseStockDAOJdbc extends BaseDAOJdbc implements WareHouseStockDAO {
	public TradeModelPO getTradeModel(int moduleID) {
		TradeModelPO result = null;
		String sql = "select * from c_trademodule where moduleid=? ";

		Object[] params = { Integer.valueOf(moduleID) };
		List<TradeModelPO> list = getJdbcTemplate().query(sql, params, new ObjectRowMapper(new TradeModelPO()));
		if (list.size() > 0) {
			result = (TradeModelPO) list.get(0);
		}
		return result;
	}

	public long insertStock(StockPO stockPO) {
		this.log.debug("****************insertStock START******************");
		long stockID = getJdbcTemplate().queryForLong("select SEQ_BI_Stock.nextval from dual");

		String sql = " insert into BI_stock(stockid,realStockCode,breedid,warehouseid,quantity,ownerfirm,lasttime, createtime,stockstatus,unit) values(?,?,?,?,?,?,sysDate,sysDate,0,?)";

		Object[] params = { Long.valueOf(stockID), stockPO.getRealStockCode(), Long.valueOf(stockPO.getBreedID()), stockPO.getWarehouseID(),
				Double.valueOf(stockPO.getQuantity()), stockPO.getOwnerFirm(), stockPO.getUnit() };

		int[] dataTypes = { 2, 12, 2, 12, 2, 12, 12 };

		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);
		this.log.debug("****************insertStock END******************");
		return stockID;
	}

	public void updateStockStatus(String stockID, int status) {
		String sql = "update BI_stock set stockStatus=?,lasttime=sysDate  where StockID=?";

		Object[] params = { Integer.valueOf(status), stockID };

		int[] dataTypes = { 2, 12 };

		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);
	}

	public void addOutStock(OutStockPO outStock) {
		String sql = "insert into BI_OutStock (outStockID,stockID,key,deliveryPerson,idnumber,status,createTime,address,phone,deliveryStatus) values (SEQ_BI_OUTSTOCK.nextval,?,?,?,?,?,sysdate,?,?,?)";

		Object[] params = { outStock.getStockID(), outStock.getKey(), outStock.getDeliveryPerson(), outStock.getIdnumber(),
				Integer.valueOf(outStock.getStatus()), outStock.getAddress(), outStock.getPhone(), outStock.getDeliveryStatus() };

		int[] dataTypes = { 12, 12, 12, 12, 4, 12, 12, 4 };

		this.log.debug("sql:" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);
	}

	public void updateOutStock(OutStockPO outStock) {
		String sql = "update BI_OutStock set deliveryPerson = ?,idnumber = ?,status = ?,createTime = sysdate,address = ?,phone = ?,deliveryStatus = ? where stockID = ? and key = ?";

		Object[] params = { outStock.getDeliveryPerson(), outStock.getIdnumber(), Integer.valueOf(outStock.getStatus()), outStock.getAddress(),
				outStock.getPhone(), outStock.getDeliveryStatus(), outStock.getStockID(), outStock.getKey() };

		int[] dataTypes = { 12, 12, 4, 12, 12, 4, 12, 12 };

		this.log.debug("sql:" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);
	}

	public OutStockPO getUnAuditOutStockByStockID(String stockID) {
		OutStockPO result = null;

		String sql = "select * from BI_OutStock where stockID=? and status=0 order by createTime desc";

		Object[] params = { stockID };

		this.log.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		List<OutStockPO> list = getJdbcTemplate().query(sql, params, new ObjectRowMapper(new OutStockPO()));
		if ((list != null) && (list.size() > 0)) {
			result = (OutStockPO) list.get(0);
		}
		return result;
	}

	public void updateOutStockStatus(long outStockID, int status) {
		String sql = "update BI_OutStock set processTime=sysdate,status=? where outStockID=?";

		Object[] params = { Integer.valueOf(status), Long.valueOf(outStockID) };

		int[] dataTypes = { 4, 2 };

		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);
	}

	public WarehousePO getWarehouse(String warehouseID) {
		String sql = "select * from bi_warehouse where warehouseID=? ";
		Object[] params = { warehouseID };
		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		List<WarehousePO> list = getJdbcTemplate().query(sql, params, new ObjectRowMapper(new WarehousePO()));
		if ((list != null) && (list.size() > 0)) {
			return (WarehousePO) list.get(0);
		}
		return null;
	}

	public WarehousePO getWarehouseByWarehouseName(String warehouseName) {
		String sql = "select * from bi_warehouse t where t.status = 0 and  warehouseName=?";
		Object[] params = { warehouseName };
		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		List<WarehousePO> list = getJdbcTemplate().query(sql, params, new ObjectRowMapper(new WarehousePO()));
		if ((list != null) && (list.size() > 0)) {
			return (WarehousePO) list.get(0);
		}
		return null;
	}

	public StockPO getStockPOByID(String stockID) {
		return getStockPOByID(stockID, false);
	}

	public StockPO getStockPOByIDAndLocked(String stockID) {
		this.logger.debug("根据仓单号 " + stockID + " 查询仓单信息，并锁定——>开始");
		StockPO result = getStockPOByID(stockID, true);
		this.logger.debug("根据仓单号 " + stockID + " 查询仓单信息，并锁定——>完成");
		return result;
	}

	private StockPO getStockPOByID(String stockID, boolean locked) {
		StockPO stockPO = null;
		String sql = "select * from BI_Stock t where stockID=?";
		if (locked) {
			sql = sql + " for update ";
		}
		Object[] params = { stockID };
		List<StockPO> list = getJdbcTemplate().query(sql, params, new ObjectRowMapper(new StockPO()));
		if (list.size() > 0) {
			stockPO = (StockPO) list.get(0);
		}
		return stockPO;
	}

	public List<GoodsPropertyPO> getGoodsProperty(String stockID) {
		String sql = "select * from BI_GoodsProperty t where stockID=? ";
		Object[] params = { stockID };
		List<GoodsPropertyPO> list = getJdbcTemplate().query(sql, params, new ObjectRowMapper(new GoodsPropertyPO()));
		return list;
	}

	public int[] insertGoodsProperty(final List<GoodsPropertyPO> list) {
		String sql = "insert into BI_GoodsProperty values(?,?,?,?)";
		return getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				GoodsPropertyPO goodsPropertyPO = (GoodsPropertyPO) list.get(i);
				ps.setString(1, goodsPropertyPO.getStockID());
				ps.setString(2, goodsPropertyPO.getPropertyName());
				ps.setString(3, goodsPropertyPO.getPropertyValue());
				ps.setLong(4, goodsPropertyPO.getPropertyTypeID());
			}

			public int getBatchSize() {
				return list.size();
			}
		});
	}

	public List<StockOperationPO> getStockOperation(String stockID) {
		String sql = "select * from BI_StockOperation t where stockID=? ";
		Object[] params = { stockID };
		List<StockOperationPO> list = getJdbcTemplate().query(sql, params, new ObjectRowMapper(new StockOperationPO()));
		return list;
	}

	public boolean getIncStockOperation(String stockID, StockOperation stockOperation) {
		String sql = "select * from BI_StockOperation t where stockID=?  and OperationID=? ";
		Object[] params = { stockID, Integer.valueOf(stockOperation.getOperation()) };
		List<StockOperationPO> list = getJdbcTemplate().query(sql, params, new ObjectRowMapper(new StockOperationPO()));
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	public void insertStockOperation(StockOperationPO w_StockOperationPO) {
		String sql = "insert into BI_StockOperation(StockID,OperationID) values(?,?)";

		Object[] params = { w_StockOperationPO.getStockID(), Integer.valueOf(w_StockOperationPO.getOperationID()) };
		getJdbcTemplate().update(sql, params);
	}

	public void deleteStockOperation(String stockID, StockOperation stockOperation) {
		String sql = "delete from BI_StockOperation where stockID=? and operationID=? ";
		Object[] params = { stockID, Integer.valueOf(stockOperation.getOperation()) };
		getJdbcTemplate().update(sql, params);
	}

	public void insertTradeStock(TradeStockPO w_TradeStockPO) {
		String sql = "insert into BI_TradeStock(TradeStockID,StockID,Moduleid,TradeNO,CreateTime,ReleaseTime,Status) values(SEQ_BI_TradeStock.nextval,?,?,?,sysdate,null,?)";

		Object[] params = { w_TradeStockPO.getStockID(), Integer.valueOf(w_TradeStockPO.getModuleid()), w_TradeStockPO.getTradeNO(),
				Long.valueOf(w_TradeStockPO.getStatus()) };
		getJdbcTemplate().update(sql, params);
	}

	public void realeseTradeStockByTN(int moduleid, String tradeNO) {
		this.log.debug("****************realeseTradeStock START******************");

		String sql = "update BI_TradeStock set ReleaseTime=sysDate,Status=1 where moduleid=? and tradeNO=?  and status=0 ";

		Object[] params = { Integer.valueOf(moduleid), tradeNO };

		int[] dataTypes = { 2, 12 };

		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);

		this.log.debug("****************realeseTradeStock END******************");
	}

	public void realeseTradeStockBySI(String stockID) {
		this.log.debug("****************realeseTradeStock START******************");

		String sql = "update BI_TradeStock set ReleaseTime=sysDate,Status=1 where stockID=? and status=0 ";

		Object[] params = { stockID };

		int[] dataTypes = { 12 };

		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);

		this.log.debug("****************realeseTradeStock END******************");
	}

	public List<TradeStockPO> getTradeStockList(String filter) {
		String sql = "select * from BI_TradeStock where 1=1 " + filter;
		List<TradeStockPO> list = getJdbcTemplate().query(sql, new ObjectRowMapper(new TradeStockPO()));
		return list;
	}

	public void stockChg(String stockID, String srcFirm, String tarFirm) {
		this.log.debug("****************stockChg START******************");
		String sql = "update BI_Stock set LastTime=sysDate,OwnerFirm=? where stockID=? ";

		Object[] params = { tarFirm, stockID };

		int[] dataTypes = { 12, 12 };
		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);

		String insertSql = "insert into BI_StockChgLog(ID,StockID,SrcFirm,TarFirm,CreateTime) values(SEQ_BI_StockChgLog.nextval,?,?,?,sysdate)";

		Object[] insertParams = { stockID, srcFirm, tarFirm };
		getJdbcTemplate().update(insertSql, insertParams);
		this.log.debug("****************stockChg END******************");
	}

	public void insertPledgeStock(PledgeStockPO pledgeStockPO) {
		String sql = "insert into BI_PledgeStock(PLEDGESTOCK,StockID,Moduleid,OrderID,CreateTime,ReleaseTime,Status) values(SEQ_BI_PledgeStock.nextval,?,?,?,sysdate,null,?)";

		Object[] params = { pledgeStockPO.getStockID(), Integer.valueOf(pledgeStockPO.getModuleid()), pledgeStockPO.getOrderID(),
				Long.valueOf(pledgeStockPO.getStatus()) };
		getJdbcTemplate().update(sql, params);
	}

	public List<PledgeStockPO> getPledgeStockList(String filter) {
		String sql = "select * from BI_PledgeStock where 1=1 " + filter;
		List<PledgeStockPO> list = getJdbcTemplate().query(sql, new ObjectRowMapper(new PledgeStockPO()));
		return list;
	}

	public void realesePledgeStock(int moduleid, String orderID) {
		this.log.debug("****************realesePledgeStock START******************");

		String sql = "update BI_PledgeStock set ReleaseTime=sysDate,Status=1 where moduleid=? and OrderID=? and status=0 ";

		Object[] params = { Integer.valueOf(moduleid), orderID };

		int[] dataTypes = { 2, 12 };

		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);

		this.log.debug("****************realesePledgeStock END******************");
	}

	public void insertDismantle(DismantlePO wDismantlePO) {
		String sql = "insert into BI_Dismantle(DismantleID,StockID,Amount,ApplyTime,Status) values(SEQ_BI_Dismantle.nextval,?,?,sysdate,0)";

		Object[] params = { wDismantlePO.getStockID(), Double.valueOf(wDismantlePO.getAmount()) };
		getJdbcTemplate().update(sql, params);
	}

	public List<DismantlePO> getDismantleList(String filter) {
		String sql = "select * from BI_Dismantle where 1=1 " + filter;
		List<DismantlePO> list = getJdbcTemplate().query(sql, new ObjectRowMapper(new DismantlePO()));
		return list;
	}

	public void dismantleSucess(long dismantleID, String newStockID, String realStockCode) {
		this.log.debug("****************dismantleSucess START******************");

		String sql = "update BI_Dismantle set NewStockID=?,realStockCode=?,ProcessTime=sysDate,Status=1 where DismantleID=? ";

		Object[] params = { newStockID, realStockCode, Long.valueOf(dismantleID) };

		int[] dataTypes = { 12, 12, 2 };

		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);
		this.log.debug("****************dismantleSucess END******************");
	}

	public void dismantleFail(long dismantleID) {
		this.log.debug("****************dismantleFail START******************");

		String sql = "update BI_Dismantle set ProcessTime=sysDate,Status=2 where DismantleID=? ";

		Object[] params = { Long.valueOf(dismantleID) };

		int[] dataTypes = { 2 };

		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);

		this.log.debug("****************dismantleFail END******************");
	}

	public void addGlobalLog(String operator, String operatorIP, int operatetype, String operatorContent, int operatorResult) {
		String sql = "insert into c_globallog_all(id,operator,operatetime,operatetype,operateip,operatecontent,operateresult,logType)  values(SEQ_C_GLOBALLOG.Nextval,?, sysdate,?,?,?,?,3)";

		Object[] params = { operator, Integer.valueOf(operatetype), operatorIP, operatorContent, Integer.valueOf(operatorResult) };
		int[] types = { 12, 4, 12, 12, 4 };
		this.log.debug("sql: " + sql);
		for (int i = 0; i < params.length; i++) {
			this.log.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, types);
	}

	public FinancingStockPO getFinancingStockPOBuID(long financingStockID) {
		return getFinancingStockPOBuID(financingStockID, false);
	}

	public FinancingStockPO getFinancingStockPOBuIDAndLocked(long financingStockID) {
		return getFinancingStockPOBuID(financingStockID, true);
	}

	private FinancingStockPO getFinancingStockPOBuID(long financingStockID, boolean locked) {
		FinancingStockPO financingStockPO = null;
		String sql = "select * from BI_FinancingStock t where financingStockID=?";
		if (locked) {
			sql = sql + " for update ";
		}
		Object[] params = { Long.valueOf(financingStockID) };
		List<FinancingStockPO> list = getJdbcTemplate().query(sql, params, new ObjectRowMapper(new FinancingStockPO()));
		if (list.size() > 0) {
			financingStockPO = (FinancingStockPO) list.get(0);
		}
		return financingStockPO;
	}

	public long getFinancingStockID() {
		return getJdbcTemplate().queryForLong("select SEQ_BI_FinancingStock.nextval from dual");
	}

	public long addFinancingStock(FinancingStockPO financingStockPO) {
		this.log.debug("**************addFinancingStock****************");
		long financingstockID = financingStockPO.getFinancingstockID();

		String sql = "insert into BI_FinancingStock(FinancingStockID,stockID,createTime,Status) values (?,?,sysdate,'Y')";

		Object[] params = { Long.valueOf(financingstockID), financingStockPO.getStockID() };
		int[] dataTypes = { 2, 12 };

		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);
		return financingstockID;
	}

	public void disableFinancingStock(long financingStockID) {
		this.log.debug("**************disableFinancingStock****************");
		String sql = "update BI_FinancingStock set Status='N',ReleaseTime=sysdate where FinancingStockID=? ";
		Object[] params = { Long.valueOf(financingStockID) };
		int[] dataTypes = { 2 };
		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.log.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);
	}

	public String getFirmName(String firmID) {
		String sql = "select name from M_Firm where firmID=? ";
		Object[] params = { firmID };
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, params);
		if ((list == null) || (list.size() == 0)) {
			return null;
		}
		return (String) ((Map) list.get(0)).get("name");
	}

	public String getBIFirmPassword(String firmID) {
		String sql = "select password from BI_Firm where firmID=? ";
		Object[] params = { firmID };
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, params);
		if ((list == null) || (list.size() == 0)) {
			return null;
		}
		return (String) ((Map) list.get(0)).get("password");
	}

	public long getBreedIDByCategoryNameBreedName(String categoryName, String breedName) {
		String sql = "select breedid from m_breed b,m_category c where b.categoryid=c.categoryid and b.status=1 and c.status=1 and b.breedname=? and c.categoryname=? ";
		Object[] params = { breedName, categoryName };
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, params);
		if ((list == null) || (list.size() == 0)) {
			return -1L;
		}
		return ((BigDecimal) ((Map) list.get(0)).get("breedid")).longValue();
	}

	public List<Map<String, Object>> getPropertyByBreedID(long breedID) {
		String sql = "select propertyname,isnecessary from m_property p,m_breed b where p.categoryid=b.categoryid and b.breedid=? ";
		Object[] params = { Long.valueOf(breedID) };
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, params);
		return list;
	}

	public List<Map<String, Object>> getCategoryPropertyByBreedID(long breedID) {
		String sql = "select p.* from m_breed b,m_property p where p.categoryid=b.categoryid and breedid=?";
		Object[] params = { Long.valueOf(breedID) };
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, params);
		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.log.debug("params[" + i + "]: " + params[i]);
		}
		return list;
	}

	public List<StockVO> getUnusedStocksVOList(long breedID, String firmID, double quantity) {
		String sql = "select s.*,g.propertyname as propertyname,g.propertyvalue as propertyvalue from bi_stock s,bi_goodsproperty g where g.stockID(+)=s.stockID and not exists (select stockid from BI_StockOperation so where so.stockID=s.stockid) and s.stockstatus=1 and s.breedid=? and s.ownerfirm=? and s.quantity<=?";

		Object[] params = { Long.valueOf(breedID), firmID, Double.valueOf(quantity) };
		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.log.debug("params[" + i + "]: " + params[i]);
		}
		return (List) getJdbcTemplate().query(sql, params, new StockVOResultSetExtractor());
	}

	private class StockVOResultSetExtractor implements ResultSetExtractor<Object> {
		private StockVOResultSetExtractor() {
		}

		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<StockVO> result = new ArrayList();

			Map<String, StockVO> alreadyHaveStock = new HashMap();
			while (rs.next()) {
				String stockID = rs.getString("stockID");
				StockVO vo = (StockVO) alreadyHaveStock.get(stockID);
				if (vo == null) {
					vo = new StockVO();
					alreadyHaveStock.put(stockID, vo);
					StockPO po = new StockPO();
					po.setStockID(stockID);
					po.setBreedID(rs.getLong("breedID"));
					po.setCreateTime(rs.getDate("createTime"));
					po.setLastTime(rs.getDate("lastTime"));
					po.setOwnerFirm(rs.getString("ownerFirm"));
					po.setQuantity(rs.getDouble("quantity"));
					po.setRealStockCode(rs.getString("realStockCode"));
					po.setStockStatus(rs.getInt("stockStatus"));
					po.setUnit(rs.getString("unit"));
					po.setWarehouseID(rs.getString("warehouseID"));
					vo.setStock(po);
				}
				GoodsPropertyPO property = new GoodsPropertyPO();
				property.setStockID(stockID);
				property.setPropertyName(rs.getString("propertyName"));
				property.setPropertyValue(rs.getString("propertyValue"));
				vo.addStockProperty(property);
			}
			result.addAll(alreadyHaveStock.values());
			return result;
		}
	}

	public List<StockPO> getUnusedStocksVOList(int moduleid, String firmID) {
		String sql = "select s.* from bi_stock s,m_breed b,m_category c where s.breedid=b.breedid and b.categoryid=c.categoryid  and not exists (select stockid from BI_StockOperation so where so.stockID=s.stockid) and s.stockstatus=1  and (b.belongmodule like '%|"
				+

		moduleid + "|%' or b.belongmodule like '%|" + moduleid + "' or b.belongmodule like '" + moduleid + "|%' or b.belongmodule='" + moduleid
				+ "') " + " and (c.belongmodule like '%|" + moduleid + "|%' or c.belongmodule like '%|" + moduleid + "' or c.belongmodule like '"
				+ moduleid + "|%' or c.belongmodule='" + moduleid + "') " + " and s.ownerfirm='" + firmID + "' ";
		this.log.debug("sql:" + sql);
		List<StockPO> list = getJdbcTemplate().query(sql, new ObjectRowMapper(new StockPO()));
		return list;
	}

	public long insertFrozenStockPO(FrozenStockPO frozenStockPO) {
		this.log.debug("****************insertFrozenStockPO START******************");
		long frozenStockID = getJdbcTemplate().queryForLong("select SEQ_BI_FROZENSTOCK.nextval from dual");

		String sql = " insert into BI_FrozenStock(FrozenStockID,StockID,moduleid,Status,CreateTime) values(?,?,?,?,sysDate)";

		Object[] params = { Long.valueOf(frozenStockID), frozenStockPO.getStockID(), Integer.valueOf(frozenStockPO.getModuleID()),
				Integer.valueOf(frozenStockPO.getStatus()) };

		int[] dataTypes = { 2, 12, 2, 2 };

		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.logger.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);
		this.log.debug("****************insertFrozenStockPO END******************");
		return frozenStockID;
	}

	public List<FrozenStockPO> getFrozenStockList(String queryCondition) {
		this.log.debug("****************getFrozenStockList START******************");
		String sql = "select * from BI_FrozenStock where 1=1 " + queryCondition;
		List<FrozenStockPO> list = getJdbcTemplate().query(sql, new ObjectRowMapper(new FrozenStockPO()));
		this.log.debug("****************getFrozenStockList END******************");
		return list;
	}

	public void realeseFrozenStockPO(String stockID) {
		this.log.debug("****************realeseFrozenStockPO START******************");
		String sql = "update BI_FrozenStock set status=1,releaseTime=sysdate where stockID=? ";
		Object[] params = { stockID };
		int[] dataTypes = { 12 };
		this.log.debug("sql=" + sql);
		for (int i = 0; i < params.length; i++) {
			this.log.debug("params[" + i + "]: " + params[i]);
		}
		getJdbcTemplate().update(sql, params, dataTypes);
		this.log.debug("****************realeseFrozenStockPO END******************");
	}

	public void warehouseMoveHistory(Date date) {
		WarehouseMoveHistoryProcedure sfunc = new WarehouseMoveHistoryProcedure(getDataSource());
		Map<String, Object> inputs = new HashMap();
		inputs.put("p_EndDate", date);
		Map<String, Object> map = sfunc.execute(inputs);
		System.out.println(map);
	}

	private class WarehouseMoveHistoryProcedure extends StoredProcedure {
		private static final String SFUNC_NMAE = "SP_W_MoveHistory";

		public WarehouseMoveHistoryProcedure(DataSource ds) {
			super(ds, "SP_W_MoveHistory");
			declareParameter(new SqlParameter("p_EndDate", 93));
			compile();
		}

		public Map<String, Object> execute(Map<String, ?> map) {
			return super.execute(map);
		}
	}
}
