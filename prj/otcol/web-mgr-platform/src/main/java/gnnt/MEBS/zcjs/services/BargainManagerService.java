package gnnt.MEBS.zcjs.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import gnnt.MEBS.base.query.PageInfo;
import gnnt.MEBS.base.query.QueryConditions;
import gnnt.MEBS.zcjs.dao.BreedDao;
import gnnt.MEBS.zcjs.dao.DeliveryDao;
import gnnt.MEBS.zcjs.dao.HisTradeDao;
import gnnt.MEBS.zcjs.dao.MoneyDoDao;
import gnnt.MEBS.zcjs.dao.RegStockDao;
import gnnt.MEBS.zcjs.dao.TradeDao;
import gnnt.MEBS.zcjs.model.Breed;
import gnnt.MEBS.zcjs.model.Delivery;
import gnnt.MEBS.zcjs.model.HisTrade;
import gnnt.MEBS.zcjs.model.ValidRegstock;
import gnnt.MEBS.zcjs.model.WarehouseRegStock;
import gnnt.MEBS.zcjs.model.innerObject.CommodityPropertyObject;
import gnnt.MEBS.zcjs.model.innerObject.KeyValue;
import gnnt.MEBS.zcjs.util.ParseXML;
import gnnt.MEBS.zcjs.util.QualityRatio;
import gnnt.MEBS.zcjs.util.QualityUnit;

@Service("z_bargainManagerService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class BargainManagerService {
	@Autowired
	@Qualifier("z_hisTradeDao")
	private HisTradeDao hisTradeDao;
	@Autowired
	@Qualifier("z_tradedao")
	private TradeDao tradeDao;
	@Autowired
	@Qualifier("z_validRegstockDao")
	private RegStockDao regStockDao;
	@Autowired
	@Qualifier("z_deliveryDao")
	private DeliveryDao deliveryDao;
	@Autowired
	@Qualifier("z_warehouseService")
	private WarehouseService warehouseService;
	@Autowired
	@Qualifier("z_breedDao")
	private BreedDao breedDao;
	@Autowired
	@Qualifier("z_moneyDoDao")
	private MoneyDoDao moneyDoDao;

	public List<Map<String, Object>> getTradeList(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		return this.tradeDao.getTableList(paramQueryConditions, paramPageInfo);
	}

	public List<Map<String, Object>> getHisTradeList(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		return this.hisTradeDao.getTableList(paramQueryConditions, paramPageInfo);
	}

	public HisTrade getHisTrade(long paramLong) {
		return this.hisTradeDao.getObject(paramLong);
	}

	public List<Map<String, Object>> getRegStockFit(HisTrade paramHisTrade, PageInfo paramPageInfo) {
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("breedID", "=", Long.valueOf(paramHisTrade.getBreedId()));
		localQueryConditions.addCondition("type", "=", Integer.valueOf(0));
		localQueryConditions.addCondition("weight", ">", Integer.valueOf(0));
		localQueryConditions.addCondition("firmID", "=", paramHisTrade.getFirmId_S());
		String str1 = paramHisTrade.getCommodityProperties();
		String str2 = paramHisTrade.getQuality();
		Breed localBreed = this.breedDao.getObject(paramHisTrade.getBreedId());
		List localList1 = ParseXML.addCommodityPropertyObjectToXml(str1);
		CommodityPropertyObject localCommodityPropertyObject = null;
		if ((localList1 != null) && (localList1.size() > 0)) {
			for (int i = 0; i < localList1.size(); i++) {
				localCommodityPropertyObject = (CommodityPropertyObject) localList1.get(i);
				localQueryConditions.addCondition(localCommodityPropertyObject.getKey(), "=", localCommodityPropertyObject.getValue());
			}
		}
		List localList2 = this.warehouseService.getWareRegStockTableList(localQueryConditions, paramPageInfo);
		ArrayList localArrayList = new ArrayList();
		if ((localList2 != null) && (localList2.size() > 0)) {
			Iterator localIterator = localList2.iterator();
			while (localIterator.hasNext()) {
				Map localMap = (Map) localIterator.next();
				double d1 = QualityRatio.ratioBack(((BigDecimal) localMap.get("INITWEIGHT")).doubleValue(), localBreed.getDeliveryRatio());
				localMap.put("INITWEIGHT", Double.valueOf(QualityUnit.unitBack(d1, localBreed.getUnitVolume())));
				double d2 = QualityRatio.ratioBack(((BigDecimal) localMap.get("WEIGHT")).doubleValue(), localBreed.getDeliveryRatio());
				localMap.put("WEIGHT", Double.valueOf(QualityUnit.unitBack(d2, localBreed.getUnitVolume())));
				double d3 = QualityRatio.ratioBack(((BigDecimal) localMap.get("FROZENWEIGHT")).doubleValue(), localBreed.getDeliveryRatio());
				localMap.put("FROZENWEIGHT", Double.valueOf(QualityUnit.unitBack(d3, localBreed.getUnitVolume())));
				localArrayList.add(localMap);
			}
		}
		return localArrayList;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class, RuntimeException.class })
	public void match(List<KeyValue> paramList, String paramString, long paramLong) throws Exception {
		ValidRegstock localValidRegstock = null;
		Delivery localDelivery = null;
		HisTrade localHisTrade = getHisTrade(paramLong);
		Breed localBreed = this.breedDao.getObject(localHisTrade.getBreedId());
		for (int i = 0; i < paramList.size(); i++) {
			KeyValue localKeyValue = (KeyValue) paramList.get(i);
			WarehouseRegStock localWarehouseRegStock = this.warehouseService.getWarehouseRegStock((String) localKeyValue.getKey());
			double d1 = QualityUnit.uintObverse(((Double) localKeyValue.getValue()).doubleValue(), localBreed.getUnitVolume());
			int j = this.warehouseService.updateRegstockFrozen((String) localKeyValue.getKey(),
					QualityRatio.ratioObverse(d1, localBreed.getDeliveryRatio()), paramString, 1);
			if (j != 1) {
				throw new Exception();
			}
			double d2 = ((Double) localKeyValue.getValue()).doubleValue() / localHisTrade.getQuantity();
			localDelivery = new Delivery();
			localDelivery.setDeliveryId(this.deliveryDao.getId());
			localDelivery.setBreedId(localHisTrade.getBreedId());
			localDelivery.setBuyMargin(localHisTrade.getTradeBail_B() * d2);
			localDelivery.setFirmId_b(localHisTrade.getFirmId_B());
			localDelivery.setFirmId_s(localHisTrade.getFirmId_S());
			localDelivery.setPrice(localHisTrade.getPrice());
			localDelivery.setQuantity(((Double) localKeyValue.getValue()).doubleValue());
			localDelivery.setRegStockId((String) localKeyValue.getKey());
			localDelivery.setSellMargin(localHisTrade.getTradeBail_S() * d2);
			localDelivery.setStatus(2);
			localDelivery.setTradeNo(localHisTrade.getTradeNo());
			localDelivery.setType(1);
			this.deliveryDao.insert(localDelivery);
			localValidRegstock = this.regStockDao.getObject((String) localKeyValue.getKey());
			if (localValidRegstock == null) {
				localValidRegstock = new ValidRegstock();
				localValidRegstock.setRegstockId(localWarehouseRegStock.getRegStockId());
				localValidRegstock.setBreedId(localWarehouseRegStock.getBreedId());
				localValidRegstock.setQuantity(((Double) localKeyValue.getValue()).doubleValue());
				localValidRegstock.setStatus(3);
				localValidRegstock.setType(2);
				localValidRegstock.setFirmId(localWarehouseRegStock.getFirmId());
				String str1 = ParseXML.getCommodityPropertyObjectXml(localWarehouseRegStock.getPropertyObjectList());
				localValidRegstock.setCommodityProperties(str1);
				String str2 = ParseXML.getQualityObjectXml(localWarehouseRegStock.getQualityObjectList());
				localValidRegstock.setQuality(str2);
				this.regStockDao.add(localValidRegstock);
			} else {
				localValidRegstock.setQuantity(localValidRegstock.getQuantity() + ((Double) localKeyValue.getValue()).doubleValue());
				localValidRegstock.setStatus(3);
				localValidRegstock.setType(2);
				this.regStockDao.update(localValidRegstock);
			}
		}
		localHisTrade.setStatus(2);
		this.hisTradeDao.update(localHisTrade);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class, RuntimeException.class })
	public void deleteMatch(String[] paramArrayOfString, String paramString) throws Exception {
		Object localObject = null;
		for (int i = 0; i < paramArrayOfString.length; i++) {
			Delivery localDelivery = this.deliveryDao.getObject(Long.parseLong(paramArrayOfString[i]));
			Breed localBreed = this.breedDao.getObject(localDelivery.getBreedId());
			if (localDelivery.getType() != 1) {
				localDelivery.setStatus(4);
				this.deliveryDao.update(localDelivery);
			} else {
				String str = localDelivery.getRegStockId();
				double d1 = localDelivery.getQuantity();
				double d2 = QualityUnit.uintObverse(d1, localBreed.getUnitVolume());
				WarehouseRegStock localWarehouseRegStock = this.warehouseService.getWarehouseRegStock(str);
				int j = this.warehouseService.updateRegstockFrozen(str, QualityRatio.ratioObverse(d2, localBreed.getDeliveryRatio()), paramString, 0);
				if (j != 1) {
					throw new Exception();
				}
				localDelivery.setStatus(4);
				this.deliveryDao.update(localDelivery);
				ValidRegstock localValidRegstock = this.regStockDao.getObject(str);
				double d3 = localValidRegstock.getQuantity();
				if (d3 > d1) {
					localValidRegstock.setQuantity(d3 - d1);
					this.regStockDao.update(localValidRegstock);
				} else {
					this.regStockDao.delete(str);
				}
			}
		}
	}

	public List<Map<String, Object>> getDeliveryTableList(QueryConditions paramQueryConditions, PageInfo paramPageInfo) {
		return this.deliveryDao.getTableList(paramQueryConditions, paramPageInfo);
	}

	public Delivery getDelivery(long paramLong) {
		return this.deliveryDao.getObject(paramLong);
	}

	public double getSum(QueryConditions paramQueryConditions) {
		return this.deliveryDao.getSum(paramQueryConditions);
	}

	public long getDeliveryId() {
		return this.deliveryDao.getId();
	}

	public void insertDelivery(Delivery paramDelivery) {
		this.deliveryDao.insert(paramDelivery);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class, RuntimeException.class })
	public void send(long paramLong, String paramString) throws Exception {
		QueryConditions localQueryConditions = new QueryConditions();
		localQueryConditions.addCondition("tradeNo", "=", Long.valueOf(paramLong));
		List localList = this.deliveryDao.getObjectList(localQueryConditions, null);
		Delivery localDelivery = null;
		Breed localBreed = null;
		int i = 0;
		HisTrade localHisTrade = getHisTrade(paramLong);
		if ((localList != null) && (localList.size() > 0)) {
			for (int j = 0; j < localList.size(); j++) {
				localDelivery = (Delivery) localList.get(j);
				localBreed = this.breedDao.getObject(localDelivery.getBreedId());
				if (localDelivery.getStatus() != 4) {
					if ((localDelivery.getRegStockId() != null) && (localDelivery.getType() == 1)) {
						double d = QualityUnit.uintObverse(localDelivery.getQuantity(), localBreed.getUnitVolume());
						ValidRegstock localValidRegstock = this.regStockDao.getObject(localDelivery.getRegStockId());
						localValidRegstock.setStatus(5);
						this.regStockDao.update(localValidRegstock);
					} else if (localDelivery.getType() == 5) {
						this.moneyDoDao.updateFundsFull(localHisTrade.getFirmId_B(), "308", -localHisTrade.getDeliveryPoundage_B(),
								localHisTrade.getTradeNo() + "", "");
						this.moneyDoDao.updateFundsFull(localHisTrade.getFirmId_S(), "308", -localHisTrade.getDeliveryPoundage_S(),
								localHisTrade.getTradeNo() + "", "");
						this.moneyDoDao.updateFundsFull(localHisTrade.getFirmId_B(), "303", -localHisTrade.getTradeBail_B(),
								localHisTrade.getTradeNo() + "", "");
						this.moneyDoDao.updateFundsFull(localHisTrade.getFirmId_S(), "303", -localHisTrade.getTradeBail_S(),
								localHisTrade.getTradeNo() + "", "");
					}
					localDelivery.setStatus(3);
					this.deliveryDao.update(localDelivery);
					double d = QualityUnit.uintObverse(localDelivery.getQuantity(), localBreed.getUnitVolume());
					d = QualityRatio.ratioObverse(d, localBreed.getDeliveryRatio());
					i = this.deliveryDao.createSettleMatch(localDelivery, paramString, d, localBreed.getDeliveryRatio());
					if (i <= 0) {
						throw new Exception();
					}
				}
			}
		}
		localHisTrade.setStatus(3);
		this.hisTradeDao.update(localHisTrade);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = { Exception.class, RuntimeException.class })
	public void matchOne(Delivery paramDelivery, String paramString, long paramLong, double paramDouble) throws Exception {
		HisTrade localHisTrade = getHisTrade(paramLong);
		Breed localBreed = this.breedDao.getObject(localHisTrade.getBreedId());
		WarehouseRegStock localWarehouseRegStock = new WarehouseRegStock();
		localWarehouseRegStock.setRegStockId(this.warehouseService.getRegStockId());
		localWarehouseRegStock.setBreedId(localHisTrade.getBreedId());
		localWarehouseRegStock.setCreateTime(new Date());
		localWarehouseRegStock.setFirmId(localHisTrade.getFirmId_S());
		localWarehouseRegStock.setWarehouseId("");
		localWarehouseRegStock.setFrozenWeight(0.0D);
		double d1 = QualityUnit.uintObverse(paramDouble, localBreed.getUnitVolume());
		localWarehouseRegStock.setInitWeight(QualityRatio.ratioObverse(d1, localBreed.getDeliveryRatio()));
		localWarehouseRegStock.setModifyTime(new Date());
		localWarehouseRegStock.setStockId("");
		double d2 = QualityUnit.uintObverse(paramDouble, localBreed.getUnitVolume());
		localWarehouseRegStock.setWeight(QualityRatio.ratioObverse(d2, localBreed.getDeliveryRatio()));
		localWarehouseRegStock.setUnitWeight(1.0D);
		localWarehouseRegStock.setType(1);
		this.warehouseService.insertRegStock(localWarehouseRegStock);
		int i = this.warehouseService.updateRegstockFrozen(localWarehouseRegStock.getRegStockId(),
				QualityRatio.ratioObverse(d2, localBreed.getDeliveryRatio()), paramString, 1);
		if (i != 1) {
			throw new Exception();
		}
		ValidRegstock localValidRegstock = new ValidRegstock();
		localValidRegstock.setRegstockId(localWarehouseRegStock.getRegStockId());
		localValidRegstock.setBreedId(localWarehouseRegStock.getBreedId());
		localValidRegstock.setQuantity(paramDouble);
		localValidRegstock.setStatus(3);
		localValidRegstock.setType(2);
		localValidRegstock.setFirmId(localWarehouseRegStock.getFirmId());
		localValidRegstock.setCommodityProperties(localHisTrade.getCommodityProperties());
		localValidRegstock.setQuality(localHisTrade.getQuality());
		this.regStockDao.add(localValidRegstock);
		paramDelivery.setRegStockId(localWarehouseRegStock.getRegStockId());
		insertDelivery(paramDelivery);
	}
}
