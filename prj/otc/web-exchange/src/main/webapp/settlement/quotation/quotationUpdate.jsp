<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>������ϸ��Ϣ�޸�</title>
	</head>
	<body class="st_body">
		<form name="frm" action="${basePath}/settlement/quotation/update.action"
			method="post" targetType="hidden">
		  <div>
			<table border="0" width="80%" align="center">
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;��ϸ��Ϣ</div>
				<tr>
				<td>
				<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					
					<tr height="35">
						<td align="right">��Ʒ����:</td>
						<td>
						<input class="input_text_pwdmin" type="hidden" id="commodityId" name="obj.commodityId" value="${obj.commodityId}" readonly="readonly">
						<input class="input_text_pwdmin" type="text" id="commodityName" name="obj.commodityName" value="${obj.commodityName}" readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">���̼�:</td>
						<td>
						<input class="input_text" type="text" id="closePrice" onBlur="myblur('price')"  name="obj.closePrice"
						 value= 	<c:choose>
								   <c:when  test="${obj.minPriceMove==1}">
								  	<fmt:formatNumber value="${obj.closePrice}" pattern="#" />
								   </c:when>
								   <c:otherwise>
										<fmt:formatNumber value="${obj.closePrice}" pattern="#0.00" />
								   </c:otherwise>
								   </c:choose>
							/>
						</td>
					</tr>
					<tr height="35">
						<td align="right">��߼�:</td>
						<td>
						<input class="input_text_pwdmin" type="text" id="highPrice" name="obj.highPrice"
						 value= <c:choose>
								   <c:when  test="${obj.minPriceMove==1}">
								  	<fmt:formatNumber value="${obj.highPrice}" pattern="#" />
								   </c:when>
								   <c:otherwise>
										<fmt:formatNumber value="${obj.highPrice}" pattern="#0.00" />
								   </c:otherwise>
								   </c:choose>
								readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">��ͼ�:</td>
						<td>
						<input class="input_text_pwdmin" type="text" id="lowPrice" name="obj.lowPrice" 
						value=<c:choose>
								   <c:when  test="${obj.minPriceMove==1}">
								  	<fmt:formatNumber value="${obj.lowPrice}" pattern="#" />
								   </c:when>
								   <c:otherwise>
										<fmt:formatNumber value="${obj.lowPrice}" pattern="#0.00" />
								   </c:otherwise>
								   </c:choose>
								readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">�����:</td>
						<td>
						<input type="text" id="price" onBlur="myblur('price')"  class="input_text" name="obj.price"
						 	value=<c:choose>
									   <c:when  test="${obj.minPriceMove==1}">
									  	<fmt:formatNumber value="${obj.price}" pattern="#" />
									   </c:when>
									   <c:otherwise>
											<fmt:formatNumber value="${obj.price}" pattern="#0.00" />
									   </c:otherwise>
								   </c:choose> 
						/>
						</td>
						<td align="left" height="40" width="40%"><div id="price_vTip"></div></td>
					</tr>
					<tr height="35">
						<td align="right">���̼�:</td>
						<td>
						<input class="input_text_pwdmin" type="text" id="openPrice" name="obj.openPrice" 
						value=<c:choose>
								   <c:when  test="${obj.minPriceMove==1}">
								  	<fmt:formatNumber value="${obj.openPrice}" pattern="#" />
								   </c:when>
								   <c:otherwise>
										<fmt:formatNumber value="${obj.openPrice}" pattern="#0.00" />
								   </c:otherwise>
								   </c:choose>
							 readonly="readonly">
						</td>
					</tr>
				</table>
			</td>
			</tr>
			</table>
			</div>
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" >
						<tr>
							<td  align="center">
								<button  class="btn_sec" id="update" onClick="updateT()">�޸�</button>
							</td>
							<td align="center">
								<button  class="btn_sec" onClick="window.close()">�ر�</button>
							</td>
						</tr>
					</table>
					</div>
			</form>
		
	</body>
</html>
<script type="text/javascript">

function myblur(userID){
	var flag = true;
	if(userID == "price"){
		flag = price(userID);
	}else {
		if(!price("price")){
				flag = false;
			}
		}
	return flag;
}
function price(userID){
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID+"_vTip");
	var innerHTML = "";
	var flag = false;
	if(isEmpty(user.value)){
		innerHTML = "����Ϊ��";
	}else if(!flote(user.value,2)){
		innerHTML = "��������λС�����ڵ�����";
	}else{
		innerHTML = "";
		flag = true;
	}
	if(flag){
		vTip.className="";
	}else{
		vTip.className="onError";
	}
	vTip.innerHTML=innerHTML;
	return flag;
}
function updateT(){
	if(!myblur("all")){return false;}
	if(!isFormChanged(null,null)){
			alert("û���޸�����");
			return false;}
		var vaild = affirm("��ȷ��Ҫ������");
		if(vaild==true){
		    frm.submit();
	    }
}

</script>
<%@ include file="/public/footInc.jsp"%>