package gnnt.mebsv.hqservice.model.HQVO;

import java.io.DataInputStream;
import java.io.IOException;

import gnnt.mebsv.hqservice.model.ProductInfoVO;

public class CMDProductInfoVO extends CMDVO {
	public int date;
	public int time;

	public CMDProductInfoVO() {
		this.cmd = 3;
	}

	public static ProductInfoListVO getObj(DataInputStream paramDataInputStream) throws IOException {
		ProductInfoListVO localProductInfoListVO = new ProductInfoListVO();
		localProductInfoListVO.date = paramDataInputStream.readInt();
		localProductInfoListVO.time = paramDataInputStream.readInt();
		int i = paramDataInputStream.readInt();
		ProductInfoVO[] arrayOfProductInfoVO = new ProductInfoVO[i];
		for (int j = 0; j < arrayOfProductInfoVO.length; j++) {
			arrayOfProductInfoVO[j] = new ProductInfoVO();
			arrayOfProductInfoVO[j].marketID = paramDataInputStream.readUTF();
			arrayOfProductInfoVO[j].code = paramDataInputStream.readUTF();
			arrayOfProductInfoVO[j].status = paramDataInputStream.readInt();
			arrayOfProductInfoVO[j].fUnit = paramDataInputStream.readFloat();
			arrayOfProductInfoVO[j].name = paramDataInputStream.readUTF();
			arrayOfProductInfoVO[j].pyName = new String[paramDataInputStream.readInt()];
			for (int k = 0; k < arrayOfProductInfoVO[j].pyName.length; k++)
				arrayOfProductInfoVO[j].pyName[k] = paramDataInputStream.readUTF();
			arrayOfProductInfoVO[j].tradeSecNo = new int[paramDataInputStream.readInt()];
			for (int k = 0; k < arrayOfProductInfoVO[j].tradeSecNo.length; k++)
				arrayOfProductInfoVO[j].tradeSecNo[k] = paramDataInputStream.readInt();
			if (arrayOfProductInfoVO[j].fUnit <= 0.0F)
				arrayOfProductInfoVO[j].fUnit = 1.0F;
		}
		localProductInfoListVO.productInfos = arrayOfProductInfoVO;
		return localProductInfoListVO;
	}
}