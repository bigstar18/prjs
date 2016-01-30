<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>δע��ֵ��б�</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script>
		jQuery(document).ready(function() {
			$("#view").click(function(){
				frm.target="";
				frm.action="${frontPath}/bill/unregister/list.action";
				frm.submit();
			});
		});
		</script>
	</head>
	<body onload="getFocusID('stockID_q');">
	<div class="main">
		<jsp:include page="/front/frame/current.jsp"></jsp:include>
		<div class="warning">
			<div class="title font_orange_14b">��ܰ��ʾ :</div>
			<div class="content">δע��ֵ���ָ�������󣬿ɽ��л������������Ȩƾ֤��δע��ֵ������ڰ�����⡢��ֲֵ�ҵ��Ҳ��������Ϊע��ֵ���</div>
		</div>
	</div>
		<div class="main_copy">
			<form id="frm" action="${frontPath}/bill/unregister/list.action" method="post">
			<div class="sort">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30" width="312">
						<label>�ֵ��ţ�
							<input type="text" id="stockID_q" maxLength="<fmt:message key="stockID_q" bundle="${proplength}" />" name="${GNNT_}primary.stockId[=]" value="${oldParams['primary.stockId[=]']}"  onkeyup ="this.value=this.value.replace(' ','')"/>
						</label>
					</td>
					<td height="30" width="312">
					</td>
					<td width="110" height="30" align="center" valign="middle">
						<a href="#" id="view"><img src="${skinPath }/image/memberadmin/searchbt1.gif" width="93" height="23" border="0" /></a>
					</td>
				</tr>
			</table>
		</div>
				<div class="content">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr valign="bottom">
						<td height="40">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="140" height="32" align="center" valign="bottom" class="c_titlebt_on">
										<a id="all" href="#">δע��ֵ�</a>
									</td>
									<td>
									</td>
									<td >
									</td>
									<td>&nbsp;</td>
								</tr>
							</table>
							<input id="status" name="${GNNT_}primary.status[=][int]" type="hidden" value="${oldParams['primary.bsFlag[=]']}"/>
						</td>
					</tr>
					<tr>
						<td>
					<div class="tb_bg">
						<table border="0" cellspacing="0" cellpadding="0" class="content">
							<tr class="font_b tr_bg">
								<td><div class="ordercol" id="stockId">�ֵ���</div></td>
								<td><div class="ordercol" id="breed.breedName">Ʒ��</div></td>
								<td><div class="ordercol" id="warehouseId">�ֿ���</div></td>
								<td><div class="ordercol" id="quantity">��Ʒ����</div></td>
								<td>��λ</td>
								<td><div class="ordercol" id="lastTime">�����ʱ��</div></td>
								<td><div class="ordercol" id="createTime">����ʱ��</div></td>
								<td>����</td>
							</tr>
							<c:forEach var="stock" items="${pageInfo.result}">
							<tr>
								<td>
								<a href="${frontPath}/bill/unregister/detail.action?entity.stockId=${stock.stockId}">${stock.stockId}</a>
								</td>
								<td>${stock.breed.breedName}</td>
								<td>${stock.warehouseId}</td>
								<td><fmt:formatNumber pattern="#0.00" value="${stock.quantity }"></fmt:formatNumber></td>
								<td>${stock.unit}</td>
								<td><fmt:formatDate value='${stock.lastTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value='${stock.createTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>
									<a href="#" id="registerStock" onclick="registerStock('${stock.stockId }')" >ע��</a>
									&nbsp;
									<a href="#" id="dismantleStock" onclick="dismantleStock('${stock.stockId }')">��</a>
									&nbsp;
									<a href="#" id="exitStock" onclick="exitStock('${stock.stockId }')"><c:if test="${haveWarehouse eq 1}">����</c:if><c:if test="${haveWarehouse eq 0}">��������</c:if></a>
								</td>
							</tr>
							</c:forEach>
						</table>
					</div>
						</td>
				</tr>
			</table>
					<jsp:include page="/front/frame/pageinfo.jsp"></jsp:include>
				</div>
			</form>
			 <iframe style="display: none;" id="frame" name="frame"></iframe>
		</div>
</body>
</html>

<script>
//ע��ֵ�
function registerStock(stockId){
	if(affirm("ȷ��ע��òֵ���")){
		frm.target="frame";
		frm.action = "${frontPath}/bill/unregister/registerStock.action?stockId=" + stockId;
		frm.submit();
	}
}
//����
function exitStock(stockId){
	var haveWarehouse =  '${haveWarehouse}';
	if(haveWarehouse==''){
		alert("�ֵ����׺���δ�������������ֵ����������ʱ�޷����в��������Ժ����ԣ�")
	}else{
		//if(haveWarehouse==1){
			 if(affirm()){
				var url = "${frontPath}/bill/unregister/forwardExitStock.action?entity.stockId=" + stockId;
			  	var result=showDialog(url,'',750,750);
			  	if(result>0){
			  		$("#view").click();
				}else{
					clearSubmitCount();
				}
			}
		<%--}else{
			if(confirm("ȷ�ϳ���òֵ���")){
				frm.action = "${frontPath}/bill/unregister/exitStock.action?stockId=" + stockId;
				frm.submit();
			}
		}--%>
	}
}
//��
function dismantleStock(stockId){
	frm.action = "${frontPath}/bill/unregister/dismantleStockToPage.action?entity.stockId=" + stockId;
	frm.submit();
}
function showMsgBoxCallbak(result){
	window.location="${frontPath}/bill/unregister/list.action?sortColumns=" + '${sortColumns}'; 
}
</script>
<%@include file="/front/public/jsp/commonmsg.jsp"%>