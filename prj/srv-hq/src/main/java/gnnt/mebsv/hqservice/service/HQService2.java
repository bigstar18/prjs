/**
 * 
 */
package gnnt.mebsv.hqservice.service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import gnnt.mebsv.hqservice.model.ClientSocket;
import gnnt.mebsv.hqservice.model.ProductDataVO;

/**
 * @author hxx
 *
 */
public class HQService2 extends HQService {
	static final String[] ignoreProperties = new String[] { "yesterBalancePrice", "closePrice", "openPrice", "highPrice", "lowPrice", "curPrice",
			"totalAmount", "totalMoney", "curAmount", "consignRate", "amountRate", "balancePrice", "reserveCount" };

	@Override
	public byte[] GetQuoteList(ClientSocket paramClientSocket) throws IOException {
		ProductDataVO[] arrayOfProductDataVO = this.quotation.getModifiedProductData(paramClientSocket);
		if (arrayOfProductDataVO.length == 0)
			return new byte[0];

		processIndices(arrayOfProductDataVO);

		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
		localDataOutputStream.writeByte(5);
		localDataOutputStream.writeInt(arrayOfProductDataVO.length);

		for (int i = 0; i < arrayOfProductDataVO.length; i++) {
			localDataOutputStream.writeUTF(arrayOfProductDataVO[i].marketID);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[i].code);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].yesterBalancePrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].closePrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].openPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].highPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].lowPrice);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].curPrice);
			localDataOutputStream.writeLong(arrayOfProductDataVO[i].totalAmount);
			localDataOutputStream.writeDouble(arrayOfProductDataVO[i].totalMoney);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].curAmount);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].consignRate);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].amountRate);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].balancePrice);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].reserveCount);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].buyAmount[0]);
			localDataOutputStream.writeInt(arrayOfProductDataVO[i].sellAmount[0]);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].buyPrice[0]);
			localDataOutputStream.writeFloat(arrayOfProductDataVO[i].sellPrice[0]);
			localDataOutputStream.writeUTF(arrayOfProductDataVO[i].expStr);
			if (arrayOfProductDataVO[i].lUpdateTime > paramClientSocket.quoteListTime)
				paramClientSocket.quoteListTime = arrayOfProductDataVO[i].lUpdateTime;
		}

		localDataOutputStream.flush();
		localDataOutputStream.close();
		localByteArrayOutputStream.flush();
		localByteArrayOutputStream.close();

		return localByteArrayOutputStream.toByteArray();
	}

	// 100001综合指数

	// 100002钱币指数
	// 50XXXX纸币券
	// 70XXXX流通纪念币
	// 80XXXX金币、银币

	// 100003邮票指数
	// 601XXXX邮票（小型张、小全张等）
	// 602XXXX邮票（版票）
	// 603XXXX邮票（本票票）
	// 604XXXX邮票（待定品类）
	// 605XXX邮资封片

	// 100004电话卡指数
	// 90XXXX电话卡
	private void processIndices(ProductDataVO[] datas) {
		ProductDataVO all = new ProductDataVO();
		ProductDataVO coin = new ProductDataVO();
		ProductDataVO stamp = new ProductDataVO();
		ProductDataVO phonecard = new ProductDataVO();
		int countCoin = 0;
		int countStamp = 0;
		int countPhonecard = 0;
		ProductDataVO srcAll = null, srcCoin = null, srcStamp = null, srcPhonecard = null;

		for (int i = 0; i < datas.length; i++) {
			ProductDataVO data = datas[i];
			String code = data.code;

			if ("100001".equals(code)) {
				srcAll = data;
			} else if ("100002".equals(code)) {
				srcCoin = data;
			} else if ("100003".equals(code)) {
				srcStamp = data;
			} else if ("100004".equals(code)) {
				srcPhonecard = data;
			} else if (code.startsWith("50") || code.startsWith("70") || code.startsWith("80")) {
				countCoin++;
				addProps(data, coin);
			} else if (code.startsWith("60")) {
				countStamp++;
				addProps(data, stamp);
			} else if (code.startsWith("90")) {
				countPhonecard++;
				addProps(data, phonecard);
			}
		}

		countAvg(coin, countCoin);
		countAvg(stamp, countStamp);
		countAvg(phonecard, countPhonecard);

		addProps(coin, all);
		addProps(stamp, all);
		addProps(phonecard, all);
		countAvg(all, 3);

		copyProperties(coin, srcCoin);
		copyProperties(stamp, srcStamp);
		copyProperties(phonecard, srcPhonecard);
		copyProperties(all, srcAll);
	}

	private void addProps(ProductDataVO source, ProductDataVO target) {
		// +=
		target.yesterBalancePrice = (source.yesterBalancePrice + target.yesterBalancePrice);
		target.closePrice = (source.closePrice + target.closePrice);
		target.openPrice = (source.openPrice + target.openPrice);
		target.highPrice = (source.highPrice + target.highPrice);
		target.lowPrice = (source.lowPrice + target.lowPrice);
		target.curPrice = (source.curPrice + target.curPrice);
		target.totalAmount = (source.totalAmount + target.totalAmount);
		target.totalMoney = (source.totalMoney + target.totalMoney);
		target.curAmount = (source.curAmount + target.curAmount);
		target.consignRate = (source.consignRate + target.consignRate);
		target.amountRate = (source.amountRate + target.amountRate);
		target.balancePrice = (source.balancePrice + target.balancePrice);
		target.reserveCount = (source.reserveCount + target.reserveCount);
	}

	private void countAvg(ProductDataVO source, int count) {
		source.yesterBalancePrice = (source.yesterBalancePrice) / count;
		source.closePrice = (source.closePrice) / count;
		source.openPrice = (source.openPrice) / count;
		source.highPrice = (source.highPrice) / count;
		source.lowPrice = (source.lowPrice) / count;
		source.curPrice = (source.curPrice) / count;
		source.totalAmount = (source.totalAmount) / count;
		source.totalMoney = (source.totalMoney) / count;
		source.curAmount = (source.curAmount) / count;
		source.consignRate = (source.consignRate) / count;
		source.amountRate = (source.amountRate) / count;
		source.balancePrice = (source.balancePrice) / count;
		source.reserveCount = (source.reserveCount) / count;
	}

	// 多线程可能有误差，反馈不及时
	private void copyProperties(ProductDataVO source, ProductDataVO target) {
		// BeanUtils.copyProperties(source, target, ignoreProperties);
		target.yesterBalancePrice = (source.yesterBalancePrice);
		target.closePrice = (source.closePrice);
		target.openPrice = (source.openPrice);
		target.highPrice = (source.highPrice);
		target.lowPrice = (source.lowPrice);
		target.curPrice = (source.curPrice);
		target.totalAmount = (source.totalAmount);
		target.totalMoney = (source.totalMoney);
		target.curAmount = (source.curAmount);
		target.consignRate = (source.consignRate);
		target.amountRate = (source.amountRate);
		target.balancePrice = (source.balancePrice);
		target.reserveCount = (source.reserveCount);
		// target.buyAmount = (source.buyAmount);
		// target.sellAmount = (source.sellAmount);
		// target.buyPrice = (source.buyPrice);
		// target.sellPrice = (source.sellPrice);
	}

}
