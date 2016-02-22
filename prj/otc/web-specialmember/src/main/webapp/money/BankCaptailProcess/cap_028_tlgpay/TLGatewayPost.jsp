<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="gnnt.trade.bank.util.Tool" %>
	<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
		
	</head>
	<body>
	<center> <h2>֧��������Ϣǩ��</h2></center>	
	<%	
	//ҳ�����Ҫ�����inputCharsetһ�£�����������յ�����ֵ�еĺ���Ϊ�����������֤ǩ��ʧ�ܡ�
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
	String extTL=Tool.delNull(request.getParameter("extTL"));//ͨ���̻���չҵ���ֶΣ���v2.2.0�汾֮���ʹ�õ��ģ����ڿ�ͨ���˵�ҵ��
	String issuerId=Tool.delNull(request.getParameter("issuerId"));
	String pan=Tool.delNull(request.getParameter("pan"));
	String sign="";
	
	//��ֱ��telpshx������payerTelephone��payerName��payerIDCard��pan�ĸ��ֶβ���Ϊ��
	//����payerIDCard��pan��ʹ�ù�Կ���ܣ�PKCS1��ʽ�������Base64����
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
	
	//���충�������������signMsg��
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
	//requestOrder.setExtTL(extTL);//ͨ���̻���չҵ���ֶΣ���v2.2.0�汾֮���ʹ�õ��ģ����ڿ�ͨ���˵�ҵ��
	requestOrder.setPan(pan);
	requestOrder.setKey(key); //keyΪMD5��Կ����Կ����ͨ��֧�����ػ�Ա������վ�����á�

	String strSrcMsg = requestOrder.getSrc(); // �˷�������debug������ͨ�����ע�͡�
	String strSignMsg = requestOrder.doSign(); // ǩ������ΪsignMsg�ֶ�ֵ��
	%>
	
	<!--
		1����������ͨ��post��ʽ��get��ʽ�ύ������ʹ��post��ʽ��
		   �ύ֧���������ʹ��http��https��ʽ������ʹ��https��ʽ��
		2��ͨ��֧�����ص�ַ���̻��ż�keyֵ���ڽ������ʱ��ͨ���ṩ��
		   ͨ��֧�����ص�ַ���̻��ţ��ڽ�������ʱ��ͨ���ṩ��keyֵ��ͨ��֧�����ػ�Ա������վ�����á�
	-->
	<center>
	<!--================= post ��ʽ�ύ֧������ start =====================-->
    <!--================= ���Ե�ַΪ http://ceshi.allinpay.com/gateway/index.do =====================-->
	<!--================= ������ַ���ڲ��Ի�����ͨ�����ҵ����Ա��ȡ =====================-->
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
	<!--================= post ��ʽ�ύ֧������ end =====================-->
	</center>
	ҳ����ת�У����Ժ�...
	</html>
	<script language="javascript">
	form2.submit();
	</script>
