package gnnt.MEBS.bill.core.operation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import gnnt.MEBS.bill.core.Server;
import gnnt.MEBS.bill.core.bo.StockOutApplyBO;
import gnnt.MEBS.bill.core.bo.StockOutAuditBO;
import gnnt.MEBS.bill.core.dao.WareHouseStockDAO;
import gnnt.MEBS.bill.core.exception.BillCoreException;
import gnnt.MEBS.bill.core.operation.IManageStock;
import gnnt.MEBS.bill.core.po.DismantlePO;
import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.po.OutStockPO;
import gnnt.MEBS.bill.core.po.StockOperationPO;
import gnnt.MEBS.bill.core.po.StockPO;
import gnnt.MEBS.bill.core.po.WarehousePO;
import gnnt.MEBS.bill.core.util.Arith;
import gnnt.MEBS.bill.core.vo.AddStockResultVO;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.bill.core.vo.StockOperation;
import gnnt.MEBS.bill.core.vo.StockQiantityResultVO;
import gnnt.MEBS.bill.core.vo.StockVO;
import gnnt.MEBS.billWarehoursInterface.VO.ResponseVO;
import gnnt.MEBS.billWarehoursInterface.VO.UnRegisterRequestVO;
import gnnt.MEBS.billWarehoursInterface.kernel.IWareHouseStockServer;

public class ManageStock implements IManageStock {
	IWareHouseStockServer wareHouseStockService;

	public void setWareHouseStockService(IWareHouseStockServer wareHouseStockService) {
		this.wareHouseStockService = wareHouseStockService;
	}

	public AddStockResultVO addStock(StockPO stockPO, List<GoodsPropertyPO> propertyList) {
		AddStockResultVO resultVO = new AddStockResultVO();
		resultVO.setResult(-1L);

		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		if ((propertyList == null) || (propertyList.size() == 0)) {
			resultVO.addErrorInfo(-1232L);
			return resultVO;
		}
		WarehousePO warehouse = wareHouseStockDAO.getWarehouse(stockPO.getWarehouseID());
		if (warehouse == null) {
			resultVO.addErrorInfo(-1233L, new Object[] { stockPO.getWarehouseID() });
			return resultVO;
		}
		if (warehouse.getStatus() != 0) {
			resultVO.addErrorInfo(-1234L, new Object[] { stockPO.getWarehouseID() });
			return resultVO;
		}
		String stockID = wareHouseStockDAO.insertStock(stockPO) + "";
		for (GoodsPropertyPO wGoodsPropertyPO : propertyList) {
			wGoodsPropertyPO.setStockID(stockID);
		}
		wareHouseStockDAO.insertGoodsProperty(propertyList);

		wareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301, stockPO.getOwnerFirm() + "录入仓单，仓单号：" + stockID, 1);

		resultVO.setResult(1L);
		resultVO.setStockID(stockID);

		return resultVO;
	}

	public ResultVO dismantleStock(String stockID, Double[] amountarr) {
		ResultVO resultVO = new ResultVO();
		resultVO.setResult(-1L);
		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();

		StockPO stockPO = wareHouseStockDAO.getStockPOByIDAndLocked(stockID);
		if (stockPO == null) {
			resultVO.addErrorInfo(-1252L, new Object[] { stockID });
			return resultVO;
		}
		if (stockPO.getStockStatus() != 0) {
			resultVO.addErrorInfo(-1253L, new Object[] { stockPO.getStockStatusMeaning() });
			return resultVO;
		}
		double totalAmount = 0.0D;
		Double[] arrayOfDouble1;
		int j = (arrayOfDouble1 = amountarr).length;
		for (int i = 0; i < j; i++) {
			double amount = arrayOfDouble1[i].doubleValue();
			totalAmount = Arith.add(totalAmount, amount);
		}
		if (totalAmount != stockPO.getQuantity()) {
			resultVO.addErrorInfo(-1254L, new Object[] { Double.valueOf(stockPO.getQuantity()), Double.valueOf(totalAmount) });
			return resultVO;
		}
		StockOperationPO stockOperationPO = new StockOperationPO();
		stockOperationPO.setStockID(stockPO.getStockID());
		stockOperationPO.setOperationID(StockOperation.DISMANTLE.getOperation());
		wareHouseStockDAO.insertStockOperation(stockOperationPO);
		Double[] arrayOfDouble2;
		int k = (arrayOfDouble2 = amountarr).length;
		for (j = 0; j < k; j++) {
			double amount = arrayOfDouble2[j].doubleValue();
			DismantlePO DismantlePO = new DismantlePO();
			DismantlePO.setStockID(stockID);
			DismantlePO.setAmount(amount);
			wareHouseStockDAO.insertDismantle(DismantlePO);
		}
		wareHouseStockDAO.updateStockStatus(stockID, 4);

		wareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301, "拆仓单，仓单号：" + stockID, 1);

		resultVO.setResult(1L);
		return resultVO;
	}

	public ResultVO dismantleStock(String stockID, boolean result, Map<String, String> dismantleMap) {
		ResultVO resultVO = new ResultVO();
		resultVO.setResult(-1L);
		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();

		StockPO stockPO = wareHouseStockDAO.getStockPOByIDAndLocked(stockID);
		if (stockPO == null) {
			resultVO.addErrorInfo(-1262L, new Object[] { stockID });
			return resultVO;
		}
		if (stockPO.getStockStatus() != 4) {
			resultVO.addErrorInfo(-1263L, new Object[] { stockPO.getStockStatusMeaning() });
			return resultVO;
		}
		List<DismantlePO> dismantlePOList = wareHouseStockDAO.getDismantleList(" and stockID='" + stockPO.getStockID() + "' and status=0 ");
		if ((dismantlePOList == null) || (dismantlePOList.size() == 0) || (!"0".equals(((DismantlePO) dismantlePOList.get(0)).getStatus()))) {
			resultVO.addErrorInfo(-1264L, new Object[] { stockID });
			return resultVO;
		}
		wareHouseStockDAO.deleteStockOperation(stockPO.getStockID(), StockOperation.DISMANTLE);
		if (result) {
			wareHouseStockDAO.updateStockStatus(stockPO.getStockID(), 3);
			for (DismantlePO wDismantlePO : dismantlePOList) {
				StockPO newStockPO = new StockPO();
				newStockPO.setBreedID(stockPO.getBreedID());
				newStockPO.setOwnerFirm(stockPO.getOwnerFirm());
				newStockPO.setQuantity(wDismantlePO.getAmount());
				if ((dismantleMap == null) || (dismantleMap.get(wDismantlePO.getDismantleID()) == null)) {
					resultVO.addErrorInfo(-1265L, new Object[] { Long.valueOf(wDismantlePO.getDismantleID()) });

					throw new BillCoreException(resultVO.getErrorDetailInfo());
				}
				newStockPO.setRealStockCode((String) dismantleMap.get(wDismantlePO.getDismantleID()));

				newStockPO.setStockStatus(0);
				newStockPO.setUnit(stockPO.getUnit());
				newStockPO.setWarehouseID(stockPO.getWarehouseID());

				String newStockID = wareHouseStockDAO.insertStock(newStockPO) + "";

				List<GoodsPropertyPO> propertyList = wareHouseStockDAO.getGoodsProperty(stockID);

				List<GoodsPropertyPO> newPropertyList = new ArrayList();
				for (GoodsPropertyPO wGoodsPropertyPO : propertyList) {
					GoodsPropertyPO newGoodsPropertyPO = new GoodsPropertyPO();
					newGoodsPropertyPO.setPropertyName(wGoodsPropertyPO.getPropertyName());
					newGoodsPropertyPO.setPropertyValue(wGoodsPropertyPO.getPropertyValue());
					newGoodsPropertyPO.setStockID(newStockID);
					newGoodsPropertyPO.setPropertyTypeID(wGoodsPropertyPO.getPropertyTypeID());
					newPropertyList.add(newGoodsPropertyPO);
				}
				wareHouseStockDAO.insertGoodsProperty(newPropertyList);

				wareHouseStockDAO.dismantleSucess(wDismantlePO.getDismantleID(), newStockID,
						(String) dismantleMap.get(wDismantlePO.getDismantleID()));
			}
		} else {
			for (DismantlePO wDismantlePO : dismantlePOList) {
				wareHouseStockDAO.dismantleFail(wDismantlePO.getDismantleID());
			}
			wareHouseStockDAO.updateStockStatus(stockPO.getStockID(), 0);
		}
		wareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301,
				"处理拆仓单，仓单号为:" + stockID + ";仓库处理结果:" + (result ? "拆仓单处理成功" : "拆仓单处理失败"), 1);

		resultVO.setResult(1L);
		return resultVO;
	}

	public ResultVO logoutStock(String stockID) {
		ResultVO resultVO = new ResultVO();
		resultVO.setResult(-1L);
		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		StockPO stockPO = wareHouseStockDAO.getStockPOByIDAndLocked(stockID);
		if (stockPO == null) {
			resultVO.addErrorInfo(-1272L, new Object[] { stockID });
			return resultVO;
		}
		if (stockPO.getStockStatus() != 1) {
			resultVO.addErrorInfo(-1273L, new Object[] { stockPO.getStockStatusMeaning() });
			return resultVO;
		}
		List<StockOperationPO> list = wareHouseStockDAO.getStockOperation(stockPO.getStockID());
		if (list.size() > 0) {
			String operationStr = "";
			for (StockOperationPO o : list) {
				if (operationStr.length() == 0) {
					operationStr = StockOperation.getName(o.getOperationID());
				} else {
					operationStr = operationStr + "、" + StockOperation.getName(o.getOperationID());
				}
			}
			resultVO.addErrorInfo(-1274L, new Object[] { operationStr, stockPO.getStockID() });
			return resultVO;
		}
		wareHouseStockDAO.updateStockStatus(stockID, 0);

		wareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301, "注销仓单，仓单号：" + stockID, 1);

		resultVO.setResult(1L);
		return resultVO;
	}

	public ResultVO stockOutApply(StockOutApplyBO stockOutApplyBO) {
		ResultVO result = new ResultVO();
		result.setResult(-1L);

		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();

		StockPO stockPO = wareHouseStockDAO.getStockPOByIDAndLocked(stockOutApplyBO.getStockID());
		if (stockPO == null) {
			result.addErrorInfo(-1703L, new Object[] { stockOutApplyBO.getStockID() });
			return result;
		}
		if (stockPO.getStockStatus() != 0) {
			result.addErrorInfo(-1704L, new Object[] { stockPO.getStockID(), stockPO.getStockStatusMeaning() });
			return result;
		}
		wareHouseStockDAO.updateStockStatus(stockPO.getStockID(), 5);

		Random random = new Random();
		long t1 = 0x7FFFFFFF & System.currentTimeMillis();

		long key = t1 << 32 | Math.abs(random.nextInt());

		OutStockPO outStock = new OutStockPO();
		outStock.setIdnumber(stockOutApplyBO.getIdnumber());
		outStock.setDeliveryPerson(stockOutApplyBO.getDeliveryPerson());
		outStock.setKey(key + "");
		outStock.setStatus(0);
		outStock.setStockID(stockPO.getStockID());

		outStock.setAddress(stockOutApplyBO.getAddress());
		outStock.setPhone(stockOutApplyBO.getPhone());
		outStock.setDeliveryStatus(Integer.valueOf(stockOutApplyBO.getDeliveryStatus()));

		wareHouseStockDAO.addOutStock(outStock);

		result.setResult(key);

		wareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301, "仓单出库申请，仓单号：" + stockPO.getStockID(), 1);

		return result;
	}

	public ResultVO stockOutUpdate(StockOutApplyBO stockOutApplyBO) {
		ResultVO result = new ResultVO();

		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();

		OutStockPO outStock = new OutStockPO();
		outStock.setIdnumber(stockOutApplyBO.getIdnumber());
		outStock.setDeliveryPerson(stockOutApplyBO.getDeliveryPerson());
		outStock.setKey(stockOutApplyBO.getKey());
		outStock.setStatus(0);
		outStock.setStockID(stockOutApplyBO.getStockID());

		outStock.setAddress(stockOutApplyBO.getAddress());
		outStock.setPhone(stockOutApplyBO.getPhone());
		outStock.setDeliveryStatus(Integer.valueOf(stockOutApplyBO.getDeliveryStatus()));

		wareHouseStockDAO.updateOutStock(outStock);

		result.setResult(1L);

		wareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301, "仓单出库申请信息修改，仓单号：" + stockOutApplyBO.getStockID(), 1);

		return result;
	}

	public ResultVO stockOutAudit(StockOutAuditBO stockOutAuditBO) {
		ResultVO result = new ResultVO();
		result.setResult(-1L);

		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();

		StockPO stockPO = wareHouseStockDAO.getStockPOByIDAndLocked(stockOutAuditBO.getStockID());
		if (stockPO == null) {
			result.addErrorInfo(-1803L, new Object[] { stockOutAuditBO.getStockID() });
			return result;
		}
		if (stockPO.getStockStatus() != 5) {
			result.addErrorInfo(-1804L, new Object[] { stockPO.getStockID(), stockPO.getStockStatusMeaning() });
			return result;
		}
		OutStockPO outStock = wareHouseStockDAO.getUnAuditOutStockByStockID(stockOutAuditBO.getStockID());
		if (outStock == null) {
			result.addErrorInfo(-1808L, new Object[] { stockPO.getStockID() });
			return result;
		}
		if (!stockOutAuditBO.getKey().equals(outStock.getKey())) {
			result.addErrorInfo(-1805L);
			return result;
		}
		if (!stockOutAuditBO.getDeliveryPerson().equals(outStock.getDeliveryPerson())) {
			result.addErrorInfo(-1806L);
			return result;
		}
		wareHouseStockDAO.updateStockStatus(stockPO.getStockID(), 2);

		wareHouseStockDAO.updateOutStockStatus(outStock.getOutStockID(), 2);

		wareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301, "仓单出库，仓单号：" + stockPO.getStockID(), 1);

		result.setResult(1L);
		return result;
	}

	public ResultVO withdrowStockOutApply(String stockID) {
		ResultVO resultVO = new ResultVO();
		resultVO.setResult(-1L);

		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();

		StockPO stockPO = wareHouseStockDAO.getStockPOByIDAndLocked(stockID);
		if (stockPO == null) {
			resultVO.addErrorInfo(-1902L, new Object[] { stockID });
			return resultVO;
		}
		if (stockPO.getStockStatus() != 5) {
			resultVO.addErrorInfo(-1903L, new Object[] { stockPO.getStockID(), stockPO.getStockStatusMeaning() });
			return resultVO;
		}
		OutStockPO outStock = wareHouseStockDAO.getUnAuditOutStockByStockID(stockID);
		if (outStock == null) {
			resultVO.addErrorInfo(-1904L, new Object[] { stockPO.getStockID() });
			return resultVO;
		}
		wareHouseStockDAO.updateStockStatus(stockID, 0);

		wareHouseStockDAO.updateOutStockStatus(outStock.getOutStockID(), 1);

		resultVO.setResult(1L);
		return resultVO;
	}

	public ResultVO registerStock(String stockID) {
		ResultVO resultVO = new ResultVO();
		resultVO.setResult(-1L);
		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		StockPO stockPO = wareHouseStockDAO.getStockPOByIDAndLocked(stockID);
		if (stockPO == null) {
			resultVO.addErrorInfo(-1282L, new Object[] { stockID });
			return resultVO;
		}
		if (stockPO.getStockStatus() != 0) {
			resultVO.addErrorInfo(-1283L, new Object[] { stockPO.getStockStatusMeaning() });
			return resultVO;
		}
		wareHouseStockDAO.updateStockStatus(stockID, 1);

		wareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301, "注册仓单，仓单号：" + stockID, 1);

		resultVO.setResult(1L);
		return resultVO;
	}

	public ResultVO stockOut(String stockID, String userID, String userName, String password) {
		ResultVO resultVO = new ResultVO();
		resultVO.setResult(-1L);

		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();

		StockPO stockPO = wareHouseStockDAO.getStockPOByIDAndLocked(stockID);
		if (stockPO == null) {
			resultVO.addErrorInfo(-1292L, new Object[] { stockID });
			return resultVO;
		}
		if (stockPO.getStockStatus() != 0) {
			resultVO.addErrorInfo(-1293L, new Object[] { stockPO.getStockStatusMeaning() });
			return resultVO;
		}
		wareHouseStockDAO.updateStockStatus(stockID, 2);

		Random random = new Random();
		long t1 = 0x7FFFFFFF & System.currentTimeMillis();

		long key = t1 << 32 | Math.abs(random.nextInt());

		OutStockPO outStock = new OutStockPO();
		outStock.setIdnumber(null);
		outStock.setDeliveryPerson(null);
		outStock.setKey(key + "");
		outStock.setStatus(2);
		outStock.setStockID(stockPO.getStockID());
		wareHouseStockDAO.addOutStock(outStock);

		resultVO.setResult(key);

		wareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301, "仓单直接出库，仓单号：" + stockID, 1);

		UnRegisterRequestVO request = new UnRegisterRequestVO();
		request.setPwd(password);
		request.setQuantity(stockPO.getQuantity());
		request.setStockID(stockPO.getRealStockCode());
		request.setUserID(userID);
		request.setUserName(userName);

		ResponseVO response = this.wareHouseStockService.stockOut(request);
		if ((response == null) || (response.getResult() == null)) {
			throw new BillCoreException("调用仓库系统失败，仓库系统返回信息为空");
		}
		if (response.getResult().getReturnCode() != 0L) {
			if ((response.getResult().getMessage() != null) && (response.getResult().getMessage().trim().length() > 0)) {
				throw new BillCoreException(response.getResult().getMessage());
			}
			throw new BillCoreException("仓库系统处理失败");
		}
		return resultVO;
	}

	public List<String> getUnusedStocks(long breedID, Map<String, String> propertys, String firmID, double quantity) {
		List<String> result = new ArrayList();
		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();

		List<StockVO> stockList = wareHouseStockDAO.getUnusedStocksVOList(breedID, firmID, quantity);
		if ((stockList == null) || (stockList.size() <= 0)) {
			return result;
		}
		List<Map<String, Object>> propertyList = wareHouseStockDAO.getCategoryPropertyByBreedID(breedID);
		if ((propertyList != null) && (propertyList.size() > 0)) {
			for (Map<String, Object> map : propertyList) {
				if ((map.get("stockcheck") != null) && (map.get("stockcheck").equals("Y"))) {
					String propValue = (String) propertys.get(map.get("propertyname").toString());
					List<StockVO> removeStock = new ArrayList();
					for (StockVO stockVO : stockList) {
						List<GoodsPropertyPO> props = stockVO.getStockPropertys();
						if ((props == null) || (props.size() <= 0)) {
							removeStock.add(stockVO);
						} else {
							String stockP = null;
							for (GoodsPropertyPO goodsPropertyPO : props) {
								if (map.get("propertyname").toString().equals(goodsPropertyPO.getPropertyName())) {
									stockP = goodsPropertyPO.getPropertyValue();
									break;
								}
							}
							if (((propValue == null) && (stockP != null)) || ((propValue != null) && (stockP == null))
									|| ((propValue != null) && (stockP != null) && (!propValue.equals(stockP)))) {
								removeStock.add(stockVO);
							}
						}
					}
					for (StockVO stockVO : removeStock) {
						stockList.remove(stockVO);
					}
				} else if ((map.get("stockcheck") != null) && (map.get("stockcheck").equals("M"))) {
					String propValue = (String) propertys.get(map.get("propertyname").toString());
					List<StockVO> removeStock = new ArrayList();
					for (StockVO stockVO : stockList) {
						List<GoodsPropertyPO> props = stockVO.getStockPropertys();
						if ((props == null) || (props.size() <= 0)) {
							removeStock.add(stockVO);
						} else {
							String stockP = null;
							for (GoodsPropertyPO goodsPropertyPO : props) {
								if (map.get("propertyname").toString().equals(goodsPropertyPO.getPropertyName())) {
									stockP = goodsPropertyPO.getPropertyValue();
									break;
								}
							}
							if (((propValue == null) && (stockP != null)) || ((propValue != null) && (stockP == null))
									|| ((propValue != null) && (stockP != null) && (!("|" + propValue + "|").contains("|" + stockP + "|")))) {
								removeStock.add(stockVO);
							}
						}
					}
					for (StockVO stockVO : removeStock) {
						stockList.remove(stockVO);
					}
				}
			}
		}
		for (StockVO stockVO : stockList) {
			result.add(stockVO.getStock().getStockID());
		}
		return result;
	}

	public List<String> getUnusedStocks(int moduleid, String firmID) {
		List<String> result = new ArrayList();
		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();

		List<StockPO> stockList = wareHouseStockDAO.getUnusedStocksVOList(moduleid, firmID);
		if ((stockList == null) || (stockList.size() <= 0)) {
			return result;
		}
		for (StockPO stock : stockList) {
			result.add(stock.getStockID());
		}
		return result;
	}

	public StockQiantityResultVO getQuantityByStockIDs(List<String> stockIDs) {
		StockQiantityResultVO result = new StockQiantityResultVO();
		result.setResult(-1L);

		WareHouseStockDAO wareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();

		double quantity = 0.0D;
		for (String stockID : stockIDs) {
			StockPO stockPO = wareHouseStockDAO.getStockPOByID(stockID);
			if (stockPO == null) {
				result.addErrorInfo(-1402L, new Object[] { stockID });
				return result;
			}
			quantity += stockPO.getQuantity();
		}
		result.setQuantity(quantity);
		result.setResult(1L);
		return result;
	}
}
