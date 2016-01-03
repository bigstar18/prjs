package gnnt.mebsv.hqservice.model.HQVO;

import java.io.DataInputStream;
import java.io.IOException;

import gnnt.mebsv.hqservice.model.ProductDataVO;
import gnnt.mebsv.hqservice.model.TradeTimeVO;

public class CMDQuoteVO extends CMDVO {
	public String[][] codeList = new String[0][0];
	public byte isAll = 0;

	public CMDQuoteVO() {
		this.cmd = 4;
	}

	public static ProductDataVO[] getObj(DataInputStream paramDataInputStream) throws IOException {
		int i = paramDataInputStream.readByte();
		int j = paramDataInputStream.readInt();
		ProductDataVO[] arrayOfProductDataVO = new ProductDataVO[j];
		for (int k = 0; k < arrayOfProductDataVO.length; k++) {
			arrayOfProductDataVO[k] = new ProductDataVO();
			arrayOfProductDataVO[k].marketID = paramDataInputStream.readUTF();
			arrayOfProductDataVO[k].code = paramDataInputStream.readUTF();
			int m = paramDataInputStream.readInt();
			int n = paramDataInputStream.readInt();
			arrayOfProductDataVO[k].time = TradeTimeVO.HHmmssToDateTime(m, n).getTime();
			arrayOfProductDataVO[k].closePrice = paramDataInputStream.readFloat();
			arrayOfProductDataVO[k].openPrice = paramDataInputStream.readFloat();
			arrayOfProductDataVO[k].highPrice = paramDataInputStream.readFloat();
			arrayOfProductDataVO[k].lowPrice = paramDataInputStream.readFloat();
			arrayOfProductDataVO[k].curPrice = paramDataInputStream.readFloat();
			arrayOfProductDataVO[k].totalAmount = paramDataInputStream.readLong();
			arrayOfProductDataVO[k].totalMoney = paramDataInputStream.readDouble();
			arrayOfProductDataVO[k].curAmount = paramDataInputStream.readInt();
			arrayOfProductDataVO[k].consignRate = paramDataInputStream.readFloat();
			arrayOfProductDataVO[k].amountRate = paramDataInputStream.readFloat();
			arrayOfProductDataVO[k].balancePrice = paramDataInputStream.readFloat();
			arrayOfProductDataVO[k].reserveCount = paramDataInputStream.readInt();
			arrayOfProductDataVO[k].yesterBalancePrice = paramDataInputStream.readFloat();
			arrayOfProductDataVO[k].reserveChange = paramDataInputStream.readInt();
			if (i == 1) {
				arrayOfProductDataVO[k].inAmount = paramDataInputStream.readInt();
				arrayOfProductDataVO[k].outAmount = paramDataInputStream.readInt();
				arrayOfProductDataVO[k].buyAmount = new int[paramDataInputStream.readInt()];
				for (int i1 = 0; i1 < arrayOfProductDataVO[k].buyAmount.length; i1++)
					arrayOfProductDataVO[k].buyAmount[i1] = paramDataInputStream.readInt();
				arrayOfProductDataVO[k].sellAmount = new int[paramDataInputStream.readInt()];
				for (int i1 = 0; i1 < arrayOfProductDataVO[k].sellAmount.length; i1++)
					arrayOfProductDataVO[k].sellAmount[i1] = paramDataInputStream.readInt();
				arrayOfProductDataVO[k].buyPrice = new float[paramDataInputStream.readInt()];
				for (int i1 = 0; i1 < arrayOfProductDataVO[k].buyPrice.length; i1++)
					arrayOfProductDataVO[k].buyPrice[i1] = paramDataInputStream.readFloat();
				arrayOfProductDataVO[k].sellPrice = new float[paramDataInputStream.readInt()];
				for (int i1 = 0; i1 < arrayOfProductDataVO[k].sellPrice.length; i1++)
					arrayOfProductDataVO[k].sellPrice[i1] = paramDataInputStream.readFloat();
			}
			arrayOfProductDataVO[k].expStr = paramDataInputStream.readUTF();
		}
		return arrayOfProductDataVO;
	}
}