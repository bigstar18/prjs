package gnnt.MEBS.bill.core.operation.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gnnt.MEBS.bill.core.Server;
import gnnt.MEBS.bill.core.dao.WareHouseStockDAO;
import gnnt.MEBS.bill.core.exception.BillCoreException;
import gnnt.MEBS.bill.core.operation.ICustomized;
import gnnt.MEBS.bill.core.operation.IManageStock;
import gnnt.MEBS.bill.core.operation.IStockTrade;
import gnnt.MEBS.bill.core.po.GoodsPropertyPO;
import gnnt.MEBS.bill.core.po.StockPO;
import gnnt.MEBS.bill.core.util.Tool;
import gnnt.MEBS.bill.core.vo.AddStockResultVO;
import gnnt.MEBS.bill.core.vo.FrozenStockOutResultVO;
import gnnt.MEBS.bill.core.vo.ResultVO;

public class Customized_Standard implements ICustomized {
	private IManageStock manageStock;
	private IStockTrade stockTrade;

	public void setManageStock(IManageStock paramIManageStock) {
		this.manageStock = paramIManageStock;
	}

	public void setStockTrade(IStockTrade paramIStockTrade) {
		this.stockTrade = paramIStockTrade;
	}

	public AddStockResultVO addFrozenStock(StockPO paramStockPO, List<GoodsPropertyPO> paramList, int paramInt) {
		AddStockResultVO localAddStockResultVO = this.manageStock.addStock(paramStockPO, paramList);
		if (localAddStockResultVO.getResult() != 1L) {
			return localAddStockResultVO;
		}
		ResultVO localResultVO1 = this.manageStock.registerStock(localAddStockResultVO.getStockID());
		if (localResultVO1.getResult() != 1L) {
			throw new BillCoreException(localResultVO1.getErrorInfo());
		}
		String[] arrayOfString = { localAddStockResultVO.getStockID() };
		ResultVO localResultVO2 = this.stockTrade.frozenStocks(paramInt, arrayOfString);
		if (localResultVO2.getResult() != 1L) {
			throw new BillCoreException(localResultVO2.getErrorInfo());
		}
		return localAddStockResultVO;
	}

	public FrozenStockOutResultVO frozenStockOut(String[] paramArrayOfString, double paramDouble, int paramInt, String paramString) {
		FrozenStockOutResultVO localFrozenStockOutResultVO = new FrozenStockOutResultVO();
		localFrozenStockOutResultVO.setResult(-1L);
		if ((paramArrayOfString == null) || (paramArrayOfString.length <= 0)) {
			localFrozenStockOutResultVO.addErrorInfo(-2101L);
			return localFrozenStockOutResultVO;
		}
		if (paramDouble <= 0.0D) {
			localFrozenStockOutResultVO.addErrorInfo(-2102L);
			return localFrozenStockOutResultVO;
		}
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		ArrayList localArrayList1 = new ArrayList();
		double d1 = 0.0D;
		StockPO localStockPO1;
		for (String str1 : paramArrayOfString) {
			List localList1 = localWareHouseStockDAO
					.getFrozenStockList(" and moduleid=" + paramInt + " and stockID='" + str1 + "' and status=0 for update ");
			if ((localList1 == null) || (localList1.size() <= 0)) {
				localFrozenStockOutResultVO.addErrorInfo(-2103L, new Object[] { str1 });
				return localFrozenStockOutResultVO;
			}
			localStockPO1 = localWareHouseStockDAO.getStockPOByID(str1);
			if (localStockPO1 == null) {
				localFrozenStockOutResultVO.addErrorInfo(-2104L, new Object[] { str1 });
				return localFrozenStockOutResultVO;
			}
			if (localStockPO1.getStockStatus() != 1) {
				localFrozenStockOutResultVO.addErrorInfo(-2105L, new Object[] { str1 });
				return localFrozenStockOutResultVO;
			}
			d1 += localStockPO1.getQuantity();
			localArrayList1.add(localStockPO1);
			if (d1 >= paramDouble) {
				break;
			}
		}
		if (d1 < paramDouble) {
			localFrozenStockOutResultVO.addErrorInfo(-2106L, new Object[] { Tool.fmtDouble2(d1), Tool.fmtDouble2(paramDouble) });
			return localFrozenStockOutResultVO;
		}
		List list = new ArrayList();
		localFrozenStockOutResultVO.setStockList((List) list);
		double d2 = 0.0D;
		int k = localArrayList1.size();
		if (d1 > paramDouble) {
			k = localArrayList1.size() - 1;
		}
		Object localObject2;
		ResultVO localResultVO1;
		for (int m = 0; m < k; m++) {
			localStockPO1 = (StockPO) localArrayList1.get(m);
			d2 += localStockPO1.getQuantity();
			localObject2 = this.stockTrade.unFrozenStocks(paramInt, new String[] { localStockPO1.getStockID() });
			if (((ResultVO) localObject2).getResult() != 1L) {
				throw new BillCoreException(((ResultVO) localObject2).getErrorInfo());
			}
			localResultVO1 = this.manageStock.logoutStock(localStockPO1.getStockID());
			if (localResultVO1.getResult() != 1L) {
				throw new BillCoreException(localResultVO1.getErrorInfo());
			}
			localWareHouseStockDAO.stockChg(localStockPO1.getStockID(), localStockPO1.getOwnerFirm(), paramString);
			localWareHouseStockDAO.updateStockStatus(localStockPO1.getStockID(), 2);
			localWareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301, "仓单出库，仓单号：" + localStockPO1.getStockID(), 1);
		}
		if (d2 < paramDouble) {
			double d3 = paramDouble - d2;
			localObject2 = (StockPO) localArrayList1.get(localArrayList1.size() - 1);
			localResultVO1 = this.stockTrade.unFrozenStocks(paramInt, new String[] { ((StockPO) localObject2).getStockID() });
			if (localResultVO1.getResult() != 1L) {
				throw new BillCoreException(localResultVO1.getErrorInfo());
			}
			ResultVO localResultVO2 = this.manageStock.logoutStock(((StockPO) localObject2).getStockID());
			if (localResultVO2.getResult() != 1L) {
				throw new BillCoreException(localResultVO2.getErrorInfo());
			}
			localWareHouseStockDAO.updateStockStatus(((StockPO) localObject2).getStockID(), 3);
			StockPO localStockPO2 = new StockPO();
			localStockPO2.setBreedID(((StockPO) localObject2).getBreedID());
			localStockPO2.setOwnerFirm(((StockPO) localObject2).getOwnerFirm());
			localStockPO2.setQuantity(d3);
			localStockPO2.setRealStockCode(((StockPO) localObject2).getRealStockCode());
			localStockPO2.setStockStatus(0);
			localStockPO2.setUnit(((StockPO) localObject2).getUnit());
			localStockPO2.setWarehouseID(((StockPO) localObject2).getWarehouseID());
			String str2 = "" + localWareHouseStockDAO.insertStock(localStockPO2);
			List localList2 = localWareHouseStockDAO.getGoodsProperty(((StockPO) localObject2).getStockID());
			ArrayList localArrayList2 = new ArrayList();
			Object localObject3 = localList2.iterator();
			while (((Iterator) localObject3).hasNext()) {
				GoodsPropertyPO localObject4 = (GoodsPropertyPO) ((Iterator) localObject3).next();
				GoodsPropertyPO localObject5 = new GoodsPropertyPO();
				((GoodsPropertyPO) localObject5).setPropertyName(((GoodsPropertyPO) localObject4).getPropertyName());
				((GoodsPropertyPO) localObject5).setPropertyValue(((GoodsPropertyPO) localObject4).getPropertyValue());
				((GoodsPropertyPO) localObject5).setStockID("" + str2);
				((GoodsPropertyPO) localObject5).setPropertyTypeID(((GoodsPropertyPO) localObject4).getPropertyTypeID());
				localArrayList2.add(localObject5);
			}
			localWareHouseStockDAO.insertGoodsProperty(localArrayList2);
			localWareHouseStockDAO.updateStockStatus(str2, 2);
			localObject3 = new StockPO();
			((StockPO) localObject3).setBreedID(((StockPO) localObject2).getBreedID());
			((StockPO) localObject3).setOwnerFirm(((StockPO) localObject2).getOwnerFirm());
			((StockPO) localObject3).setQuantity(((StockPO) localObject2).getQuantity() - d3);
			((StockPO) localObject3).setRealStockCode(((StockPO) localObject2).getRealStockCode());
			((StockPO) localObject3).setStockStatus(0);
			((StockPO) localObject3).setUnit(((StockPO) localObject2).getUnit());
			((StockPO) localObject3).setWarehouseID(((StockPO) localObject2).getWarehouseID());
			String localObject4 = "" + localWareHouseStockDAO.insertStock((StockPO) localObject3);
			((StockPO) localObject3).setStockID((String) localObject4);
			((List) list).add(localObject3);
			localWareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301,
					"拆仓单，将仓单 " + ((StockPO) localObject2).getStockID() + " 拆分为 " + str2 + "、" + (String) localObject4 + " 两个仓单", 1);
			localWareHouseStockDAO.stockChg(str2, ((StockPO) localObject2).getOwnerFirm(), paramString);
			Object localObject5 = localArrayList2.iterator();
			while (((Iterator) localObject5).hasNext()) {
				GoodsPropertyPO localObject6 = (GoodsPropertyPO) ((Iterator) localObject5).next();
				((GoodsPropertyPO) localObject6).setStockID((String) localObject4);
			}
			localWareHouseStockDAO.insertGoodsProperty(localArrayList2);
			localObject5 = this.manageStock.registerStock((String) localObject4);
			if (((ResultVO) localObject5).getResult() != 1L) {
				throw new BillCoreException(((ResultVO) localObject5).getErrorInfo());
			}
			String[] localObject6 = { localObject4 };
			ResultVO localResultVO3 = this.stockTrade.frozenStocks(paramInt, (String[]) localObject6);
			if (localResultVO3.getResult() != 1L) {
				throw new BillCoreException(localResultVO3.getErrorInfo());
			}
		}
		for (int n = localArrayList1.size(); n < paramArrayOfString.length; n++) {
			localStockPO1 = localWareHouseStockDAO.getStockPOByID(paramArrayOfString[n]);
			if (localStockPO1 != null) {
				((List) list).add(localStockPO1);
			}
		}
		localFrozenStockOutResultVO.setResult(1L);
		return localFrozenStockOutResultVO;
	}
}
