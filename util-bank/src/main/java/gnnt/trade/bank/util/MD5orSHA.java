package gnnt.trade.bank.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5orSHA
{
	public  String version="2.0";
	public   String signType="1";//加密方式 1MD5 2SHA
	public   String VerficationCode="";//Tool.getConfig("VerficationCode");//身份验证
	public  String markCode="";//Tool.getConfig("MarkCode");//交易市场代码
	public   String backgroundMerUrl="";//Tool.getConfig("backgroundMerUrl");//交易后台URL
	public   String currencyType="156";//币种
//	public static  String marketAcount=Tool.getConfig("marketAcount");//交易市场监管账户 本域指交易市场登录G商银通平台，国付宝首页中的【您的国付宝账户】。
	public   String marketGSAcount="";//Tool.getConfig("marketGSAcount");//交易市场监管账户 本域指交易市场在国付宝平台开设的国付宝账户号
	public   String frontMerUrl="";//Tool.getConfig("frontMerUrl");//交易前台URL
	public static String md5(String message)
			throws UnsupportedEncodingException {
		return DigestUtils.md5Hex(message.getBytes("UTF-8"));
	}
	private static String sha(String message)
			throws UnsupportedEncodingException {
		return DigestUtils.shaHex(message.getBytes("UTF-8"));
	}

	
	/**
	 * 签约加密
	 * @param actionId 交易流水号
	 * @param tranDateTime 交易时间
	 * @return 密文串
	 */
	public   String rgstEncryption(String actionId,String tranDateTime){
		
		String message="version=["+version+"]signType=["+signType+"]" +
				"tranCode=[10000]merchantID=["+markCode+"]" +
						"merOrderNum=["+actionId+"]tranDateTime=["+tranDateTime+"]" +
				"merURL=["+backgroundMerUrl+"]VerficationCode=["+VerficationCode+"]";
		System.out.println("签约加密明文："+message);
		String messagePWD="";
		if(signType.equals("1")){
			try{
				messagePWD=md5(message);
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}else if(signType.equals("2")){
			try{
				messagePWD=sha(message);
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		
		return messagePWD ;
	}
	
	/**
	 * 解约加密
	 * @param account 签约协议号
	 * @param actionId 交易流水号
	 * @param tranDateTime 交易时间
	 * @return 密文串
	 */
	public  String delEncryption(String account,String actionId,String tranDateTime){
		
		String message="version=["+version+"]signType=["+signType+"]" +
			"tranCode=[10001]merchantID=["+markCode+"]" +
		"merOrderNum=["+actionId+"]tranDateTime=["+tranDateTime+"]" +
			"merURL=["+backgroundMerUrl+"]contractNo=["+account+"]" +
					"VerficationCode=["+VerficationCode+"]";
		System.out.println("解约加密明文："+message);
		String messagePWD="";
		if(signType.equals("1")){
			try{
				messagePWD=md5(message);
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}else if(signType.equals("2")){
			try{
				messagePWD=sha(message);
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		return messagePWD ;
		
	}
	
	
	/**
	 * 入金加密
	 * @param tranAmt 交易金额
	 * @param tranIP 用户浏览器IP
	 * @param isRepeatSubmit 是否允许重发
	 * @param account 签约协议号
	 * @param actionId 市场流水号
	 * @param tranDateTime 交易发生时间
	 * @return 密文串
	 */
	public  String inMoneyEncryption(double tranAmt,String tranIP,String isRepeatSubmit,String account,String actionId,String tranDateTime){
		
		String message="version=["+version+"]tranCode=[8801]" +
				"merchantID=["+markCode+"]contractNo=["+account+"]" +
						"merOrderNum=["+actionId+"]virCardNoIn=["+marketGSAcount+"]" +
				"tranAmt=["+Tool.fmtDouble2(tranAmt)+"]currencyType=["+currencyType+"]" +
						"tranDateTime=["+tranDateTime+"]frontMerUrl=["+frontMerUrl+"]" +
				"backgroundMerUrl=["+backgroundMerUrl+"]signType=["+signType+"]" +
						"isRepeatSubmit=["+isRepeatSubmit+"]tranIP=["+tranIP+"]" +
								"VerficationCode=["+VerficationCode+"]";
		System.out.println("入金加密明文："+message);
		String messagePWD="";
		if(signType.equals("1")){
			try{
				messagePWD=md5(message);
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}else if(signType.equals("2")){
			try{
				messagePWD=sha(message);
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		
		return messagePWD ;
		
	}
	
	
	/**
	 * 出金加密
	 * @param tranAmt 交易金额
	 * @param tranIP 用户浏览器IP
	 * @param isRepeatSubmit 是否允许重发
	 * @param account 签约协议号
	 * @param actionId 市场流水号
	 * @param tranDateTime 交易发生时间
	 * @return 加密串
	 */
	public  String outMoneyEncryption(double tranAmt,String tranIP,String isRepeatSubmit,String account,String actionId,String tranDateTime){
		
		String message="version=["+version+"]tranCode=[8802]" +
				"merchantID=["+markCode+"]contractNo=["+account+"]" +
						"merOrderNum=["+actionId+"]virCardNo=["+marketGSAcount+"]" +
				"tranAmt=["+Tool.fmtDouble2(tranAmt)+"]currencyType=["+currencyType+"]" +
						"tranDateTime=["+tranDateTime+"]" +
				"backgroundMerUrl=["+backgroundMerUrl+"]signType=["+signType+"]" +
						"isRepeatSubmit=["+isRepeatSubmit+"]tranIP=["+tranIP+"]" +
								"VerficationCode=["+VerficationCode+"]";
		System.out.println(message);
		String messagePWD="";
		if(signType.equals("1")){
			try{
				messagePWD=md5(message);
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}else if(signType.equals("2")){
			try{
				messagePWD=sha(message);
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		return messagePWD ;
		
	}
	
	
	/**
	 * 对账加密
	 * @param queryType
	 * @param actionId
	 * @param tranBeginTime
	 * @param tranEndTime
	 * @param pageNo
	 * @param pageSize
	 * @return 密文串
	 */
	public  String dateCheckEncryption(String queryType,String actionId,String tranBeginTime,String tranEndTime,String pageNo,String pageSize){
		
		String message="version=["+version+"]signType=["+signType+"]" +
			"tranCode=[10004]merchantID=["+markCode+"]queryType=["+queryType+"]" +
		"merOrderNum=["+actionId+"]tranBeginTime=["+tranBeginTime+"]" +
				"tranEndTime=["+tranEndTime+"]spvVirCardNo=["+marketGSAcount+"]" +
			"pageNo=["+pageNo+"]pageSize=["+pageSize+"]VerficationCode=["+VerficationCode+"]";
		
		String messagePWD="";
		if(signType.equals("1")){
			try{
				messagePWD=md5(message);
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}else if(signType.equals("2")){
			try{
				messagePWD=sha(message);
			} catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
		return messagePWD ;
		
	}
	public static void main(String[] str){
		String mw="version=[2.0]signType=[1]tranCode=[10004]merchantID=[0000035628]queryType=[]merOrderNum=[175]tranBeginTime=[20120628000000]tranEndTime=[20120628000000]spvVirCardNo=[0000000002000089403]pageNo=[1]pageSize=[3]VerficationCode=[1111aaaa]" ;
		try
		{
			
			//ab66dce14247c499312fa223b0deee8d
			//14c7389bca1ad576131d7a306dfe85d1
			System.out.println(mw);
			String miw=md5(mw);
			System.out.println(miw);
//			String mw2=delEncryption("BT1206250000000083","128","20120626143324");
//			System.out.println(mw2);
			
//			String mw3=inMoneyEncryption(100.00,"172.16.1.109","1","BT1207030000000202","271","20120703154937");
//			System.out.println(mw3);
			System.out.println(1.00+"");
			
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
