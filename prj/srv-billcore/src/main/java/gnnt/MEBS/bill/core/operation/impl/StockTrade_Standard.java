package gnnt.MEBS.bill.core.operation.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import gnnt.MEBS.bill.core.Server;
import gnnt.MEBS.bill.core.dao.WareHouseStockDAO;
import gnnt.MEBS.bill.core.exception.BillCoreException;
import gnnt.MEBS.bill.core.operation.IStockTrade;
import gnnt.MEBS.bill.core.po.FinancingStockPO;
import gnnt.MEBS.bill.core.po.FrozenStockPO;
import gnnt.MEBS.bill.core.po.PledgeStockPO;
import gnnt.MEBS.bill.core.po.StockOperationPO;
import gnnt.MEBS.bill.core.po.StockPO;
import gnnt.MEBS.bill.core.po.TradeStockPO;
import gnnt.MEBS.bill.core.vo.FinancingApplyVO;
import gnnt.MEBS.bill.core.vo.ResultVO;
import gnnt.MEBS.bill.core.vo.StockOperation;
import gnnt.MEBS.bill.core.vo.TransferGoodsVO;

public class StockTrade_Standard implements IStockTrade {
	public ResultVO sellStock(int paramInt, String paramString1, String paramString2) {
		ResultVO localResultVO = new ResultVO();
		localResultVO.setResult(-1L);
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		StockPO localStockPO = localWareHouseStockDAO.getStockPOByIDAndLocked(paramString2);
		if (localStockPO == null) {
			localResultVO.addErrorInfo(-1100L, new Object[] { paramString2 });
			return localResultVO;
		}
		if (localStockPO.getStockStatus() != 1) {
			localResultVO.addErrorInfo(-1101L, new Object[] { paramString2 });
			return localResultVO;
		}
		List localList = localWareHouseStockDAO.getStockOperation(localStockPO.getStockID());
		if (localList.size() > 0) {
			String localObject1 = "";
			Iterator localObject2 = localList.iterator();
			while (((Iterator) localObject2).hasNext()) {
				StockOperationPO localStockOperationPO = (StockOperationPO) ((Iterator) localObject2).next();
				if (((String) localObject1).length() == 0) {
					localObject1 = StockOperation.getName(localStockOperationPO.getOperationID());
				} else {
					localObject1 = (String) localObject1 + "、" + StockOperation.getName(localStockOperationPO.getOperationID());
				}
			}
			localResultVO.addErrorInfo(-1102L, new Object[] { localObject1, localStockPO.getStockID() });
			return localResultVO;
		}
		Object localObject1 = new StockOperationPO();
		((StockOperationPO) localObject1).setStockID(localStockPO.getStockID());
		((StockOperationPO) localObject1).setOperationID(StockOperation.SELL.getOperation());
		localWareHouseStockDAO.insertStockOperation((StockOperationPO) localObject1);
		Object localObject2 = new PledgeStockPO();
		((PledgeStockPO) localObject2).setStockID(localStockPO.getStockID());
		((PledgeStockPO) localObject2).setModuleid(paramInt);
		((PledgeStockPO) localObject2).setOrderID(paramString1);
		((PledgeStockPO) localObject2).setStatus(0L);
		localWareHouseStockDAO.insertPledgeStock((PledgeStockPO) localObject2);
		localWareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301,
				localStockPO.getOwnerFirm() + "卖仓单，参与交易的委托号:" + paramString1 + ";参与交易的仓单号:" + localStockPO.getStockID() + ";", 1);
		localResultVO.setResult(1L);
		return localResultVO;
	}

	public void withdrawSellStock(int paramInt, String paramString) {
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		List localList = localWareHouseStockDAO
				.getPledgeStockList(" and moduleid=" + paramInt + " and orderID='" + paramString + "' and status=0 for update ");
		if ((localList == null) || (localList.size() <= 0)) {
			return;
		}
		PledgeStockPO localPledgeStockPO = (PledgeStockPO) localList.get(0);
		localWareHouseStockDAO.realesePledgeStock(paramInt, paramString);
		localWareHouseStockDAO.deleteStockOperation(localPledgeStockPO.getStockID(), StockOperation.SELL);
	}

	public TransferGoodsVO sellStockToDelivery(int paramInt, String paramString1, String paramString2, String paramString3) {
		TransferGoodsVO localTransferGoodsVO = new TransferGoodsVO();
		localTransferGoodsVO.setResult(-1L);
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		List localList = localWareHouseStockDAO
				.getPledgeStockList(" and moduleid=" + paramInt + " and orderID='" + paramString1 + "' and status=0 for update ");
		PledgeStockPO localPledgeStockPO = null;
		if ((localList != null) && (localList.size() > 0)) {
			localPledgeStockPO = (PledgeStockPO) localList.get(0);
		}
		if (localPledgeStockPO == null) {
			localTransferGoodsVO.addErrorInfo(-1223L, new Object[] { Integer.valueOf(paramInt), paramString1 });
			return localTransferGoodsVO;
		}
		StockPO localStockPO = localWareHouseStockDAO.getStockPOByID(localPledgeStockPO.getStockID());
		if (!localStockPO.getOwnerFirm().equals(paramString3)) {
			localTransferGoodsVO.addErrorInfo(-1222L);
			return localTransferGoodsVO;
		}
		localWareHouseStockDAO.realesePledgeStock(paramInt, paramString1);
		localWareHouseStockDAO.deleteStockOperation(localPledgeStockPO.getStockID(), StockOperation.SELL);
		localTransferGoodsVO = transferGoods(paramInt, paramString2, new String[] { localPledgeStockPO.getStockID() }, paramString3);
		if (localTransferGoodsVO.getResult() < 0L) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return localTransferGoodsVO;
	}

	public TransferGoodsVO transferGoods(int paramInt, String paramString1, String[] paramArrayOfString, String paramString2) {
		TransferGoodsVO localTransferGoodsVO = new TransferGoodsVO();
		localTransferGoodsVO.setResult(-1L);
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		ArrayList localArrayList = new ArrayList();
		Object localObject2;
		Object localObject3;
		Object localObject4;
		for (int i = 0; i < paramArrayOfString.length; i++) {
			StockPO localStockPO = localWareHouseStockDAO.getStockPOByIDAndLocked(paramArrayOfString[i]);
			if (localStockPO == null) {
				localTransferGoodsVO.addErrorInfo(-1100L, new Object[] { paramArrayOfString[i] });
				return localTransferGoodsVO;
			}
			if (!localStockPO.getOwnerFirm().equals(paramString2)) {
				localTransferGoodsVO.addErrorInfo(-1140L);
				return localTransferGoodsVO;
			}
			if (localStockPO.getStockStatus() == 0) {
				localTransferGoodsVO.addErrorInfo(-1133L, new Object[] { localStockPO.getStockID() });
				return localTransferGoodsVO;
			}
			if (localStockPO.getStockStatus() == 2) {
				localTransferGoodsVO.addErrorInfo(-1134L, new Object[] { localStockPO.getStockID() });
				return localTransferGoodsVO;
			}
			if (localStockPO.getStockStatus() != 1) {
				localTransferGoodsVO.addErrorInfo(-1136L, new Object[] { localStockPO.getStockID() });
				return localTransferGoodsVO;
			}
			List localObject1 = localWareHouseStockDAO.getStockOperation(paramArrayOfString[i]);
			if (((List) localObject1).size() > 0) {
				localObject2 = "";
				localObject3 = ((List) localObject1).iterator();
				while (((Iterator) localObject3).hasNext()) {
					localObject4 = (StockOperationPO) ((Iterator) localObject3).next();
					if (((String) localObject2).length() == 0) {
						localObject2 = StockOperation.getName(((StockOperationPO) localObject4).getOperationID());
					} else {
						localObject2 = (String) localObject2 + "、" + StockOperation.getName(((StockOperationPO) localObject4).getOperationID());
					}
				}
				localTransferGoodsVO.addErrorInfo(-1135L, new Object[] { localObject2, localStockPO.getStockID() });
				return localTransferGoodsVO;
			}
			localArrayList.add(localStockPO);
		}
		double d = 0.0D;
		Object localObject1 = localArrayList.iterator();
		while (((Iterator) localObject1).hasNext()) {
			localObject2 = (StockPO) ((Iterator) localObject1).next();
			localObject3 = new StockOperationPO();
			((StockOperationPO) localObject3).setStockID(((StockPO) localObject2).getStockID());
			((StockOperationPO) localObject3).setOperationID(StockOperation.DELIVERY.getOperation());
			localWareHouseStockDAO.insertStockOperation((StockOperationPO) localObject3);
			localObject4 = new TradeStockPO();
			((TradeStockPO) localObject4).setStockID(((StockPO) localObject2).getStockID());
			((TradeStockPO) localObject4).setModuleid(paramInt);
			((TradeStockPO) localObject4).setTradeNO(paramString1);
			((TradeStockPO) localObject4).setStatus(0L);
			localWareHouseStockDAO.insertTradeStock((TradeStockPO) localObject4);
			localWareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301, ((StockPO) localObject2).getOwnerFirm()
					+ "将仓单用于交收，参与交易的模块号：" + paramInt + "合同号:" + paramString1 + ";参与交易的仓单号:" + ((StockPO) localObject2).getStockID() + ";", 1);
			d += ((StockPO) localObject2).getQuantity();
		}
		localTransferGoodsVO.setTradeNO(paramString1);
		localTransferGoodsVO.setQuantity(d);
		localTransferGoodsVO.setResult(1L);
		return localTransferGoodsVO;
	}

	public double realeseGoods(int paramInt, String paramString) {
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		List localList = localWareHouseStockDAO
				.getTradeStockList(" and moduleid=" + paramInt + " and tradeNO='" + paramString + "' and status=0 for update ");
		double d = 0.0D;
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			TradeStockPO localTradeStockPO = (TradeStockPO) localIterator.next();
			localWareHouseStockDAO.deleteStockOperation(localTradeStockPO.getStockID(), StockOperation.DELIVERY);
			StockPO localStockPO = localWareHouseStockDAO.getStockPOByID(localTradeStockPO.getStockID());
			d += localStockPO.getQuantity();
		}
		localWareHouseStockDAO.realeseTradeStockByTN(paramInt, paramString);
		return d;
	}

	public double delivery(int paramInt, String paramString1, String paramString2, String paramString3) {
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		List localList = localWareHouseStockDAO
				.getTradeStockList(" and moduleid=" + paramInt + " and tradeNO='" + paramString1 + "' and status=0 for update ");
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			TradeStockPO localTradeStockPO = (TradeStockPO) localIterator.next();
			stockChg(new String[] { localTradeStockPO.getStockID() }, paramString2, paramString3);
		}
		return realeseGoods(paramInt, paramString1);
	}

	public double delivery(int paramInt, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3) {
		stockChg(paramArrayOfString, paramString2, paramString3);
		return realeseGoods(paramInt, paramString1, paramArrayOfString);
	}

	public void stockChg(String[] paramArrayOfString, String paramString1, String paramString2) {
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		for (String str : paramArrayOfString) {
			localWareHouseStockDAO.stockChg(str, paramString1, paramString2);
		}
	}

	public double realeseGoods(int paramInt, String paramString, String[] paramArrayOfString) {
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		double d = 0.0D;
		for (int i = 0; i < paramArrayOfString.length; i++) {
			List localList = localWareHouseStockDAO.getTradeStockList(" and moduleid=" + paramInt + " and tradeNO='" + paramString + "' and stockID='"
					+ paramArrayOfString[i] + "' and status=0 for update ");
			if (localList.size() > 0) {
				localWareHouseStockDAO.deleteStockOperation(paramArrayOfString[i], StockOperation.DELIVERY);
				localWareHouseStockDAO.realeseTradeStockBySI(paramArrayOfString[i]);
				StockPO localStockPO = localWareHouseStockDAO.getStockPOByID(paramArrayOfString[i]);
				d += localStockPO.getQuantity();
			} else {
				throw new BillCoreException("没有相应的交收仓单，仓单号：" + paramArrayOfString[i]);
			}
		}
		return d;
	}

	public long getFinancingStockID() {
		return Server.getInstance().getWareHouseStockDAO().getFinancingStockID();
	}

	public FinancingApplyVO startFinancing(String paramString, long paramLong) {
		FinancingApplyVO localFinancingApplyVO = new FinancingApplyVO();
		localFinancingApplyVO.setResult(-1L);
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		StockPO localStockPO = localWareHouseStockDAO.getStockPOByIDAndLocked(paramString);
		if (localStockPO == null) {
			localFinancingApplyVO.addErrorInfo(-1100L, new Object[] { paramString });
			return localFinancingApplyVO;
		}
		if (localStockPO.getStockStatus() != 1) {
			localFinancingApplyVO.addErrorInfo(-1101L, new Object[] { paramString });
			return localFinancingApplyVO;
		}
		List localList = localWareHouseStockDAO.getStockOperation(localStockPO.getStockID());
		if (localList.size() > 0) {
			String localObject1 = "";
			Iterator localObject2 = localList.iterator();
			while (((Iterator) localObject2).hasNext()) {
				StockOperationPO localStockOperationPO = (StockOperationPO) ((Iterator) localObject2).next();
				if (((String) localObject1).length() == 0) {
					localObject1 = StockOperation.getName(localStockOperationPO.getOperationID());
				} else {
					localObject1 = (String) localObject1 + "、" + StockOperation.getName(localStockOperationPO.getOperationID());
				}
			}
			localFinancingApplyVO.addErrorInfo(-1202L, new Object[] { localObject1, localStockPO.getStockID() });
			return localFinancingApplyVO;
		}
		Object localObject1 = new StockOperationPO();
		((StockOperationPO) localObject1).setStockID(paramString);
		((StockOperationPO) localObject1).setOperationID(StockOperation.FINANCING.getOperation());
		localWareHouseStockDAO.insertStockOperation((StockOperationPO) localObject1);
		Object localObject2 = new FinancingStockPO();
		((FinancingStockPO) localObject2).setStockID(paramString);
		((FinancingStockPO) localObject2).setFinancingstockID(paramLong);
		localWareHouseStockDAO.addFinancingStock((FinancingStockPO) localObject2);
		localWareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301, paramString + "参与融资：" + ";融资仓单号:" + paramLong + ";", 1);
		localFinancingApplyVO.setResult(1L);
		localFinancingApplyVO.setFinancingStockID(paramLong);
		return localFinancingApplyVO;
	}

	public ResultVO rejectFinancing(long paramLong) {
		ResultVO localResultVO = new ResultVO();
		localResultVO.setResult(-1L);
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		FinancingStockPO localFinancingStockPO = localWareHouseStockDAO.getFinancingStockPOBuIDAndLocked(paramLong);
		if (localFinancingStockPO == null) {
			localResultVO.addErrorInfo(-1213L, new Object[] { Long.valueOf(paramLong) });
			return localResultVO;
		}
		if (localFinancingStockPO.getStatus().equals("N")) {
			localResultVO.addErrorInfo(-1214L, new Object[] { Long.valueOf(paramLong) });
			return localResultVO;
		}
		localWareHouseStockDAO.disableFinancingStock(paramLong);
		localWareHouseStockDAO.deleteStockOperation(localFinancingStockPO.getStockID(), StockOperation.FINANCING);
		localWareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301,
				localFinancingStockPO.getStockID() + "拒绝融资：" + ";融资仓单号:" + paramLong + ";", 1);
		localResultVO.setResult(1L);
		return localResultVO;
	}

	public ResultVO endFinancing(long paramLong) {
		ResultVO localResultVO = new ResultVO();
		localResultVO.setResult(-1L);
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		FinancingStockPO localFinancingStockPO = localWareHouseStockDAO.getFinancingStockPOBuIDAndLocked(paramLong);
		if (localFinancingStockPO == null) {
			localResultVO.addErrorInfo(-1222L, new Object[] { Long.valueOf(paramLong) });
			return localResultVO;
		}
		if (localFinancingStockPO.getStatus().equals("N")) {
			localResultVO.addErrorInfo(-1223L, new Object[] { Long.valueOf(paramLong) });
			return localResultVO;
		}
		localWareHouseStockDAO.disableFinancingStock(paramLong);
		localWareHouseStockDAO.deleteStockOperation(localFinancingStockPO.getStockID(), StockOperation.FINANCING);
		localWareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301,
				localFinancingStockPO.getStockID() + "融资成功：" + ";融资仓单号:" + paramLong + ";", 1);
		localResultVO.setResult(1L);
		return localResultVO;
	}

	public ResultVO frozenStocks(int paramInt, String[] paramArrayOfString) {
		ResultVO localResultVO = new ResultVO();
		localResultVO.setResult(-1L);
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		ArrayList localArrayList = new ArrayList();
		StockPO localStockPO;
		Object localObject1;
		Object localObject2;
		for (int i = 0; i < paramArrayOfString.length; i++) {
			localStockPO = localWareHouseStockDAO.getStockPOByIDAndLocked(paramArrayOfString[i]);
			if (localStockPO == null) {
				localResultVO.addErrorInfo(-1504L, new Object[] { paramArrayOfString[i] });
				return localResultVO;
			}
			if (localStockPO.getStockStatus() != 1) {
				localResultVO.addErrorInfo(-1505L, new Object[] { localStockPO.getStockID() });
				return localResultVO;
			}
			localObject1 = localWareHouseStockDAO.getStockOperation(paramArrayOfString[i]);
			if (((List) localObject1).size() > 0) {
				localObject2 = "";
				Iterator localIterator2 = ((List) localObject1).iterator();
				while (localIterator2.hasNext()) {
					StockOperationPO localStockOperationPO = (StockOperationPO) localIterator2.next();
					if (((String) localObject2).length() == 0) {
						localObject2 = StockOperation.getName(localStockOperationPO.getOperationID());
					} else {
						localObject2 = (String) localObject2 + "、" + StockOperation.getName(localStockOperationPO.getOperationID());
					}
				}
				localResultVO.addErrorInfo(-1506L, new Object[] { localStockPO.getStockID(), localObject2 });
				return localResultVO;
			}
			localArrayList.add(localStockPO);
		}
		Iterator localIterator1 = localArrayList.iterator();
		while (localIterator1.hasNext()) {
			localStockPO = (StockPO) localIterator1.next();
			localObject1 = new StockOperationPO();
			((StockOperationPO) localObject1).setStockID(localStockPO.getStockID());
			((StockOperationPO) localObject1).setOperationID(StockOperation.FROZEN.getOperation());
			localWareHouseStockDAO.insertStockOperation((StockOperationPO) localObject1);
			localObject2 = new FrozenStockPO();
			((FrozenStockPO) localObject2).setStockID(localStockPO.getStockID());
			((FrozenStockPO) localObject2).setModuleID(paramInt);
			((FrozenStockPO) localObject2).setStatus(0);
			localWareHouseStockDAO.insertFrozenStockPO((FrozenStockPO) localObject2);
			localWareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301,
					"冻结仓单，系统模块号：" + paramInt + "仓单编号：" + localStockPO.getStockID() + ";", 1);
		}
		localResultVO.setResult(1L);
		return localResultVO;
	}

	public ResultVO unFrozenStocks(int paramInt, String[] paramArrayOfString) {
		ResultVO localResultVO = new ResultVO();
		localResultVO.setResult(-1L);
		WareHouseStockDAO localWareHouseStockDAO = Server.getInstance().getWareHouseStockDAO();
		for (int i = 0; i < paramArrayOfString.length; i++) {
			List localList = localWareHouseStockDAO
					.getFrozenStockList(" and moduleid=" + paramInt + " and stockID='" + paramArrayOfString[i] + "' and status=0 for update ");
			if (localList.size() > 0) {
				localWareHouseStockDAO.deleteStockOperation(paramArrayOfString[i], StockOperation.FROZEN);
				localWareHouseStockDAO.realeseFrozenStockPO(paramArrayOfString[i]);
				localWareHouseStockDAO.addGlobalLog("系统", Server.getInstance().getIp(), 1301,
						"解冻仓单，系统模块号：" + paramInt + "仓单编号：" + paramArrayOfString[i] + ";", 1);
			} else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				localResultVO.setResult(-2L);
				localResultVO.addErrorInfo(-1604L, new Object[] { Integer.valueOf(paramInt), paramArrayOfString[i] });
				return localResultVO;
			}
		}
		localResultVO.setResult(1L);
		return localResultVO;
	}
}
