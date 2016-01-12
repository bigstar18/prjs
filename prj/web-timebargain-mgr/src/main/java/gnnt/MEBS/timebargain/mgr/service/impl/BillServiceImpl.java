package gnnt.MEBS.timebargain.mgr.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.bill.core.service.IKernelService;
import gnnt.MEBS.bill.core.service.ITradeService;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.common.mgr.statictools.ApplicationContextInit;
import gnnt.MEBS.timebargain.mgr.dao.BillDao;
import gnnt.MEBS.timebargain.mgr.dao.TradeParamsDao;
import gnnt.MEBS.timebargain.mgr.model.settle.BillFrozen;
import gnnt.MEBS.timebargain.mgr.model.settle.GageBill;
import gnnt.MEBS.timebargain.mgr.service.BillService;
import gnnt.MEBS.timebargain.mgr.service.TradeParamsService;

@Service("billService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class BillServiceImpl implements BillService {
	private final transient Log logger = LogFactory.getLog(TradeParamsService.class);

	@Autowired
	@Qualifier("billKernelService")
	private IKernelService kernelService;

	@Autowired
	@Qualifier("billTradeService")
	private ITradeService tradeService;

	@Autowired
	@Qualifier("com_tradeParamsDao")
	private TradeParamsDao tradeParamsDao;

	@Autowired
	@Qualifier("billDao")
	private BillDao billDao;

	public BillDao getBillDao() {
		return this.billDao;
	}

	public void setBillDao(BillDao billDao) {
		this.billDao = billDao;
	}

	public TradeParamsDao getTradeParamsDao() {
		return this.tradeParamsDao;
	}

	public void setTradeParamsDao(TradeParamsDao tradeParamsDao) {
		this.tradeParamsDao = tradeParamsDao;
	}

	public IKernelService getKernelService() {
		return this.kernelService;
	}

	public void setKernelService(IKernelService kernelService) {
		this.kernelService = kernelService;
	}

	public ITradeService getTradeService() {
		return this.tradeService;
	}

	public void setTradeService(ITradeService tradeService) {
		this.tradeService = tradeService;
	}

	public Map<String, List<?>> getBillList(String sqlName) {
		this.logger.debug("********************获取品种分类列表(getBillList)service********************");
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("BillService's getBillList");
		}
		Map billAndCmdtySortMap = new HashMap();
		billAndCmdtySortMap.put("cmdtySort", this.tradeParamsDao.executeSelect(sqlName));

		return billAndCmdtySortMap;
	}

	public List<?> getFirmList() {
		this.logger.debug("********************获取交易商代码(getFirmList)service********************");
		return this.billDao.getFirmList();
	}

	public int addBill(String[] idsAndQuantity, String commodityID, String remarkHdden) throws Exception {
		this.logger.debug("********************添加仓单service********************");
		this.logger.debug("仓单号_仓单数量【" + StringUtils.join(idsAndQuantity, ',') + "】");
		String[] billIds = new String[idsAndQuantity.length];
		long billQuantity = 0L;
		for (int i = 0; i < idsAndQuantity.length; i++) {
			String[] ss = idsAndQuantity[i].split("_");
			billIds[i] = ss[0];
			Double billQuantitytemp = Double.valueOf(ss[1]);

			if (Math.round(billQuantitytemp.doubleValue()) != billQuantitytemp.doubleValue()) {
				this.logger.debug("!------仓单总数量出现小数------!");

				return -1;
			}

			billQuantity = (long) (billQuantity + billQuantitytemp.doubleValue());
		}

		this.logger.debug("仓单总数量【" + billQuantity + "】");
		this.logger.debug("仓单号【" + StringUtils.join(billIds, ',') + "】");

		String billSQL = "select bs.stockid, bs.realstockcode, bs.breedid,bs.warehouseid, bs.quantity, bs.unit, bs.ownerfirm, bs.lasttime, bs.createtime, bs.stockstatus from BI_Stock bs where bs.stockid in ("
				+ StringUtils.join(billIds, ',') + ")";

		List billList = this.billDao.queryBySql(billSQL);
		this.logger.debug("*******根据仓单号查出仓单详细信息  成功！*******");
		if ((billList == null) || (billList.size() == 0)) {
			return -2;
		}
		GageBill gageBill = new GageBill();
		Map billMap = (Map) billList.get(0);

		String firmId = String.valueOf(billMap.get("OWNERFIRM"));

		Date currDate = new Date();

		gageBill.setCommodityID(commodityID);
		gageBill.setFirmID(firmId);
		gageBill.setQuantity(Long.valueOf(billQuantity));
		gageBill.setCreatetime(currDate);
		gageBill.setModifyTime(currDate);
		gageBill.setRemark(remarkHdden);

		this.logger.debug("将要插入抵顶表中的数据：CommodityID【" + gageBill.getCommodityID() + "】firmId【" + gageBill.getFirmID() + "】quantity【"
				+ gageBill.getQuantity() + "】");
		String gageBillPrimaryKey = this.billDao.addGageBill(gageBill);
		this.logger.debug("*******向仓单抵顶表插入数据  成功！*******");

		int validQuantity = this.billDao.existenceOfValidGageBill(firmId, commodityID);

		if (validQuantity == -1) {
			this.billDao.executeUpdateBySql("insert into T_ValidGageBill (FirmID,CommodityID,Quantity,FrozenQty) values ('" + firmId + "','"
					+ commodityID + "'," + billQuantity + ",0)");
			this.logger.debug("*******向仓单生效表插入数据  成功！*******");
		} else {
			validQuantity = (int) (validQuantity + billQuantity);
			this.billDao.executeUpdateBySql("update T_ValidGageBill vg set vg.Quantity = " + validQuantity + " where vg.FirmID = '" + firmId
					+ "' AND vg.CommodityID = '" + commodityID + "'");
			this.logger.debug("*******更新生效仓单表  成功！*******");
		}

		BillFrozen billFrozen = null;
		for (Map map : (List<Map>) billList) {
			billFrozen = new BillFrozen(gageBillPrimaryKey, String.valueOf(map.get("STOCKID")), Integer.valueOf(0), currDate);
			this.billDao.addBillFrozen(billFrozen);
		}
		this.logger.debug("*******向仓单冻结表插入数据  成功！*******");
		String modelId = ApplicationContextInit.getConfig("SelfModuleID");

		ResultVO result = this.tradeService.frozenStocks(Integer.valueOf(modelId).intValue(), billIds);

		if (result.getResult() == -1L) {
			String errorCode = result.getErrorCode();
			String errorMsg = result.getErrorInfo();

			this.logger.debug("*******调用RMI执行仓单冻结  失败！[错误码" + errorCode + "]--[错误详细信息" + errorMsg + "]*******");

			throw new RuntimeException("[错误码" + errorCode + "]--[错误详细信息" + errorMsg + "]");
		}

		this.logger.debug("*******调用RMI执行仓单冻结  成功！*******");
		return 1;
	}

	public Long billCancel(String billId, String firmId, Long billNum, String commodityId, Long operation, Long id) throws Exception {
		this.logger.debug("********************仓单撤销service********************");

		String validGageBillQuerySql = "select v.firmID, v.commodityID, v.quantity, v.frozenQty from T_ValidGageBill v where v.firmID = '" + firmId
				+ "' AND v.commodityID = '" + commodityId + "' for update";
		List validGageBillList = this.billDao.queryBySql(validGageBillQuerySql);

		Map validGageBill = (Map) validGageBillList.get(0);
		this.logger.debug("可用仓单表：FIRMID【" + validGageBill.get("FIRMID") + "】COMMODITYID【" + validGageBill.get("COMMODITYID") + "】QUANTITY【"
				+ validGageBill.get("QUANTITY") + "】FROZENQTY【" + validGageBill.get("FROZENQTY") + "】");

		Long usableQuantity = Long
				.valueOf(((BigDecimal) validGageBill.get("QUANTITY")).longValue() - ((BigDecimal) validGageBill.get("FROZENQTY")).longValue());
		if (usableQuantity.longValue() < billNum.longValue()) {
			this.logger.debug("!!!!!!!!!!!!!可用仓单数量不足!!!!!!!!!!!!!!");
			this.logger.debug("可用仓单数量为【" + usableQuantity + "】将要撤销仓单数量为【" + billNum + "】");
			return Long.valueOf(-1L);
		}

		BillFrozen billFrozen = new BillFrozen();
		billFrozen.setId(id);
		this.logger.debug("删除仓单冻结表中的数据Id为【" + billFrozen.getId() + "】");
		this.billDao.deleteBillFrozen(billFrozen);

		this.logger.debug("*******根据仓单冻结表id删除对应记录  成功！*******");

		GageBill gageBill = this.billDao.getGageBill(operation);
		Long quantity = gageBill.getQuantity();
		this.logger.debug("原抵顶表中仓单数量【" + quantity + "】将要撤销仓单数量【" + billNum + "】");
		quantity = Long.valueOf(quantity.longValue() - billNum.longValue());

		this.logger.debug("撤销后仓单数量【" + quantity + "】");

		if (quantity.longValue() < 0L) {
			return Long.valueOf(-2L);
		}

		if (quantity.longValue() == 0L) {
			this.billDao.addHisGageBill(gageBill);

			this.billDao.deleteGageBill(gageBill);
			this.logger.debug("*******删除仓单抵顶表记录  成功！*******");
		} else {
			gageBill.setQuantity(quantity);
			this.billDao.updateGageBill(gageBill);
			this.logger.debug("*******更新仓单抵顶表记录  成功！更新数量为【" + gageBill.getQuantity() + "】*******");
		}

		String sql = "update T_ValidGageBill v set v.quantity = " + (((BigDecimal) validGageBill.get("QUANTITY")).longValue() - billNum.longValue())
				+ "where v.firmID = '" + firmId + "' AND v.commodityID = '" + commodityId + "'";
		this.billDao.executeUpdateBySql(sql);
		this.logger.debug("*******更新仓单生效表中生效数量  成功！*******");

		String modelId = ApplicationContextInit.getConfig("SelfModuleID");
		ResultVO result = this.tradeService.unFrozenStocks(Integer.valueOf(modelId).intValue(), new String[] { billId });
		if (result.getResult() == -1L) {
			String errorCode = result.getErrorCode();
			String errorMsg = result.getErrorInfo();
			this.logger.debug("*******调用RMI执行仓单解冻  失败！[错误码" + errorCode + "]--[错误详细信息" + errorMsg + "]*******");
			throw new RuntimeException("[错误码" + errorCode + "]--[错误详细信息" + errorMsg + "]");
		}
		this.logger.debug("*******调用RMI执行仓单冻结  成功！*******");
		return Long.valueOf(1L);
	}
}