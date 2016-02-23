package gnnt.trade.bank.data.ceb;


import gnnt.trade.bank.data.ceb.vo.Chk01;
import gnnt.trade.bank.data.ceb.vo.Chk02;
import gnnt.trade.bank.util.Common;
import gnnt.trade.bank.util.FileUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Vector;


public class FileProcessor {

	/**
	 * 文件路径
	 */
	private String path;
	/**
	 * 转账交易明细对账文件 字段长度数组
	 */
	private static final int[] fieldlen_CHK01 = {8,8,4,8,6,20,20,32,14,32,1,5,3,1,16};
	/**
	 * 客户资金台账资金余额明细表（STL02）字段长度数组
	 */
	private static final int[] fieldlen_STL02 = {8,8,4,8,6,20,20,32,14,32,1,5,3,1,16};
	/**
	 * 客户资金台账开销户文档（STL03）字段长度数组
	 */
	private static final int[] fieldlen_STL03 = {4,8,8,20,30,60,3,1,1,10,10,20};
	
	public FileProcessor(String path){
		this.path = path;
	}
	/**
	 * 获取文件内容
	 */
	private String getContent(){
		StringBuffer content = new StringBuffer();
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(this.path)));
			String data = null;
			while((data = br.readLine())!=null)
			{				
				content.append("\n" + data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return content.toString();
	}
	
	/**
	 * 由文件内容生成转账明细对象Chk01
	 * @param args
	 */
	public Vector<Chk01> getCHK01() throws NumberFormatException{
		Vector<Chk01> vector = new Vector<Chk01>();
		String content = getContent();		
		String[] contentArr = content.split("\\|");
		int unitLenth = 15;		
		for(int i=0;i<contentArr.length/unitLenth;i++){			
			Chk01 chk01 = new Chk01();			
			chk01.bankId = contentArr[i*unitLenth+0].trim();
			chk01.futuresId = contentArr[i*unitLenth+1].trim();
			chk01.branchId = contentArr[i*unitLenth+2].trim();
			chk01.transferDate = contentArr[i*unitLenth+3].trim();
			chk01.transfeTime = contentArr[i*unitLenth+4].trim();
			chk01.bkSeq = contentArr[i*unitLenth+5].trim();
			chk01.ftSeq = contentArr[i*unitLenth+6].trim();
			chk01.bkAcct = contentArr[i*unitLenth+7].trim();
			chk01.ftAcct = contentArr[i*unitLenth+8].trim();
			chk01.custName = contentArr[i*unitLenth+9].trim();
			chk01.tradSrc = contentArr[i*unitLenth+10].trim();
			chk01.busCd = contentArr[i*unitLenth+11].trim();
			chk01.ccy = contentArr[i*unitLenth+12].trim();
			chk01.cashExCd = contentArr[i*unitLenth+13].trim();
			chk01.trfAmt = Long.parseLong(contentArr[i*unitLenth+14].trim());
			vector.add(chk01);
		}
		return vector;		
	}
	/**
	 * 由文件内容生成转账明细对象Chk02
	 * @param args
	 */
	public Vector<Chk02> getCHK02() throws NumberFormatException{
		Vector<Chk02> vector = new Vector<Chk02>();
		String content = getContent();		
		String[] contentArr = content.split("\\|");
		int unitLenth = 10;		
		for(int i=0;i<contentArr.length/unitLenth;i++){			
			Chk02 chk02 = new Chk02();	
			chk02.bankId = contentArr[i*unitLenth+0].trim();
			chk02.futuresId = contentArr[i*unitLenth+1].trim();
			chk02.branchId = contentArr[i*unitLenth+2].trim();
			chk02.transferDate = contentArr[i*unitLenth+3].trim();
			chk02.bkAcct = contentArr[i*unitLenth+4].trim();
			chk02.ftAcct = contentArr[i*unitLenth+5].trim();
			chk02.custName = contentArr[i*unitLenth+6].trim();
			chk02.state=contentArr[i*unitLenth+9].trim();
			vector.add(chk02);
		}
		return vector;		
	}

	private static String getFileType(String name){
		String fileType = name.split("_")[1];
		return fileType;
	}
	
	/**
	 * 由文件内容生成交收明细文件
	 * @param args
	 * @throws IOException 
	 */
	public String setChk01(List list,Date date) throws IOException{
		StringBuffer content = new StringBuffer();
		for(int i=0;i<list.size();i++){
			Chk01 chk01 = (Chk01) list.get(i);
			content.append( Common.fmtStrField(Common.delNull(chk01.bankId),fieldlen_CHK01[0]," ",1)  + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.futuresId),fieldlen_CHK01[1]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.branchId),fieldlen_CHK01[2]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.transferDate),fieldlen_CHK01[3]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.transfeTime),fieldlen_CHK01[4]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.bkSeq),fieldlen_CHK01[5]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.ftSeq),fieldlen_CHK01[6]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.bkAcct),fieldlen_CHK01[7]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.ftAcct),fieldlen_CHK01[8]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.custName),fieldlen_CHK01[9]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.tradSrc),fieldlen_CHK01[10]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.busCd),fieldlen_CHK01[11]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.ccy),fieldlen_CHK01[12]," ",1) + "|" );
			content.append( Common.fmtStrField(Common.delNull(chk01.cashExCd),fieldlen_CHK01[13],"0",0) + "|" );
			content.append( Common.fmtStrField(chk01.trfAmt,fieldlen_CHK01[14],"0",0) + "|" );
			content.append( "\n" );
		}
//		System.out.println(content);
		String fileName = "S_CHK01_" + Common.df7.format(date);
		FileUtil.write(content.toString(),path+"/"+fileName);
		
		return fileName;
	}
}
