<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="gnnt.trade.bank.util.Tool" %>
	<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
		
	</head>
	<body>
	<center> <h2>支付请求信息签名</h2></center>	
	<%	
	//页面编码要与参数inputCharset一致，否则服务器收到参数值中的汉字为乱码而导致验证签名失败。
	request.setCharacterEncoding("GBK"); 

	String key=Tool.delNull(request.getParameter("MD5Key"));
	String certPath="";
	String version="v1.0";
	String language=Tool.delNull(request.getParameter("language"));
	String inputCharset= "2";
	String merchantId=Tool.delNull(request.getParameter("merchantId"));
	String pickupUrl=Tool.delNull(request.getParameter("pickupUrl"));
	String receiveUrl=Tool.delNull(request.getParameter("receiveUrl"));
	String payType=Tool.delNull(request.getParameter("payType"));
	String signType="1";
	String orderNo=Tool.delNull(request.getParameter("orderNo"));
	String orderAmount=Tool.delNull(request.getParameter("orderAmount"));
	String orderDatetime=Tool.delNull(request.getParameter("orderDatetime"));
	String orderCurrency=Tool.delNull(request.getParameter("orderCurrency"));
	String orderExpireDatetime=Tool.delNull(request.getParameter("orderExpireDatetime"));
	String payerTelephone=Tool.delNull(request.getParameter("payerTelephone"));
	String payerEmail=Tool.delNull(request.getParameter("payerEmail"));
	String payerName=Tool.delNull(request.getParameter("payerName"));
	String payerIDCard=Tool.delNull(request.getParameter("payerIDCard"));
	String pid=Tool.delNull(request.getParameter("pid"));
	String productName=Tool.delNull(request.getParameter("productName"));
	String productId=Tool.delNull(request.getParameter("productId"));
	String productNum=Tool.delNull(request.getParameter("productNum"));
	String productPrice=Tool.delNull(request.getParameter("productPrice"));
	String productDesc=Tool.delNull(request.getParameter("productDesc"));
	String ext1=Tool.delNull(request.getParameter("ext1"));
	String ext2=Tool.delNull(request.getParameter("ext2"));
	String extTL=Tool.delNull(request.getParameter("extTL"));//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
	String issuerId=Tool.delNull(request.getParameter("issuerId"));
	String pan=Tool.delNull(request.getParameter("pan"));
	String sign="";
	
	//若直连telpshx渠道，payerTelephone、payerName、payerIDCard、pan四个字段不可为空
	//其中payerIDCard、pan需使用公钥加密（PKCS1格式）后进行Base64编码
	if(null!=payerIDCard&&!"".equals(payerIDCard)){
		try{
			payerIDCard = com.allinpay.ets.client.SecurityUtil.encryptByPublicKey(certPath, payerIDCard);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	if(null!=pan&&!"".equals(pan)){
		try{
			pan = com.allinpay.ets.client.SecurityUtil.encryptByPublicKey(certPath, pan);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//构造订单请求对象，生成signMsg。
	com.allinpay.ets.client.RequestOrder requestOrder = new com.allinpay.ets.client.RequestOrder();
	if(null!=inputCharset&&!"".equals(inputCharset)){
		requestOrder.setInputCharset(Integer.parseInt(inputCharset));
	}
	requestOrder.setPickupUrl(pickupUrl);
	requestOrder.setReceiveUrl(receiveUrl);
	requestOrder.setVersion(version);
	if(null!=language&&!"".equals(language)){
		requestOrder.setLanguage(Integer.parseInt(language));
	}
	if(null!=signType&&!"".equals(signType)){
		requestOrder.setSignType(Integer.parseInt(signType));
	}
	if(null!=payType&&!"".equals(payType)){
		requestOrder.setPayType(Integer.parseInt(payType));
	}
	requestOrder.setIssuerId(issuerId);
	requestOrder.setMerchantId(merchantId);
	requestOrder.setPayerName(payerName);
	requestOrder.setPayerEmail(payerEmail);
	requestOrder.setPayerTelephone(payerTelephone);
	requestOrder.setPayerIDCard(payerIDCard);
	requestOrder.setPid(pid);
	requestOrder.setOrderNo(orderNo);
	if(null!=orderAmount&&!"".equals(orderAmount)){
		requestOrder.setOrderAmount(Long.parseLong(orderAmount));
	}
	requestOrder.setOrderCurrency(orderCurrency);
	requestOrder.setOrderDatetime(orderDatetime);
	requestOrder.setOrderExpireDatetime(orderExpireDatetime);
	requestOrder.setProductName(productName);
	if(null!=productPrice&&!"".equals(productPrice)){
		requestOrder.setProductPrice(Long.parseLong(productPrice));

	}
	if(null!=productNum&&!"".equals(productNum)){
		requestOrder.setProductNum(Integer.parseInt(productNum));
	}	
	requestOrder.setProductId(productId);
	requestOrder.setProductDesc(productDesc);
	requestOrder.setExt1(ext1);
	requestOrder.setExt2(ext2);
	//requestOrder.setExtTL(extTL);//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
	requestOrder.setPan(pan);
	requestOrder.setKey(key); //key为MD5密钥，密钥是在通联支付网关会员服务网站上设置。

	String strSrcMsg = requestOrder.getSrc(); // 此方法用于debug，测试通过后可注释。
	String strSignMsg = requestOrder.doSign(); // 签名，设为signMsg字段值。
	%>
	
	<!--
		1、订单可以通过post方式或get方式提交，建议使用post方式；
		   提交支付请求可以使用http或https方式，建议使用https方式。
		2、通联支付网关地址、商户号及key值，在接入测试时由通联提供；
		   通联支付网关地址、商户号，在接入生产时由通联提供，key值在通联支付网关会员服务网站上设置。
	-->
	<center>
	<!--================= post 方式提交支付请求 start =====================-->
    <!--================= 测试地址为 http://ceshi.allinpay.com/gateway/index.do =====================-->
	<!--================= 生产地址请在测试环境下通过后从业务人员获取 =====================-->
	<form name="form2" action="http://27.115.64.19:443/gateway/index.do" method="post">
		<input type="hidden" name="certPath" value="<%=certPath%>"/>
		<input type="hidden" name="inputCharset" value="<%=inputCharset%>"/>
		<input type="hidden" name="pickupUrl" value="<%=pickupUrl%>"/>
		<input type="hidden" name="receiveUrl" value="<%=receiveUrl%>" />
		<input type="hidden" name="version" value="<%=version%>"/>
		<input type="hidden" name="language" value="<%=language%>" />
		<input type="hidden" name="signType" value="<%=signType%>"/>
		<input type="hidden" name="merchantId" value="<%=merchantId%>" />
		<input type="hidden" name="payerName" value="<%=payerName%>"/>
		<input type="hidden" name="payerEmail" value="<%=payerEmail%>" />
		<input type="hidden" name="payerTelephone" value="<%=payerTelephone%>" />
		<input type="hidden" name="payerIDCard" value="<%=payerIDCard%>" />
		<input type="hidden" name="pid" value="<%=pid%>"/>
		<input type="hidden" name="orderNo" value="<%=orderNo%>" />
		<input type="hidden" name="orderAmount" value="<%=orderAmount%>"/>
		<input type="hidden" name="orderCurrency" value="<%=orderCurrency%>" />
		<input type="hidden" name="orderDatetime" value="<%=orderDatetime%>" />
		<input type="hidden" name="orderExpireDatetime" value="<%=orderExpireDatetime%>"/>
		<input type="hidden" name="productName" value="<%=productName%>" />
		<input type="hidden" name="productPrice" value="<%=productPrice%>" />
		<input type="hidden" name="productNum" value="<%=productNum%>"/>
		<input type="hidden" name="productId" value="<%=productId%>" />
		<input type="hidden" name="productDesc" value="<%=productDesc%>" />
		<input type="hidden" name="ext1" value="<%=ext1%>" />
		<input type="hidden" name="ext2" value="<%=ext2%>" />
		<input type="hidden" name="payType" value="<%=payType%>" />
		<input type="hidden" name="issuerId" value="<%=issuerId%>" />
		<input type="hidden" name="pan" value="<%=pan%>" />
		<input type="hidden" name="signMsg" value="<%=strSignMsg%>" />
		
	 </form>
	<!--================= post 方式提交支付请求 end =====================-->
	</center>
	页面跳转中，请稍后...
	</html>
	<script language="javascript">
	form2.submit();
	</script>
