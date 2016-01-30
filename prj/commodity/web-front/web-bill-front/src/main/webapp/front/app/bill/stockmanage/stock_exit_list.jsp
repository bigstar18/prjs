<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>�ֵ��б�</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
	</head>
	<body onload="getFocusID('stockID_q');">
	<div class="main">
		<jsp:include page="/front/frame/current.jsp"></jsp:include>
		<div class="warning">
			<div class="title font_orange_14b">��ܰ��ʾ :</div>
			<div class="content">��ҳ����ʾ���ĳ���ֵ���Ϣ�����������Կ��Ϊ���ʱ�������֤��Ϣ֮һ����ע�Ᵽ�ܡ�</div>
		</div>
		<div class="main_copy">
			<form id="frm" action="${frontPath}/bill/exit/list.action" method="post">
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
									<td width="140" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="exited" href="#">����ֵ���Ϣ</a>
									</td>
									<c:if test="${haveWarehouse != 1}">
									<td width="140" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="apply" href="#">���������вֵ�</a>
									</td>
									</c:if>
								     <td width="140" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="selled"  href="#">�����ֵ���Ϣ</a>
									 </td>
									<td>&nbsp;</td>
								</tr>
							</table>
							<input id="status" name="status" type="hidden" value="${stockStatus}"/>
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
								<c:if test="${stockStatus eq 2}">
								<td><div class="ordercol" id="company">������˾</div></td>
								<td><div class="ordercol" id="logisticsOrder">������</div></td>
								<td><div class="ordercol" id="receivedDate">�ջ�ʱ��</div></td>
								</c:if>

								<td><div class="ordercol" id="lastTime">�����ʱ��</div></td>
								<td><div class="ordercol" id="createTime">����ʱ��</div></td>
								<!-- 
								<td><div class="ordercol" id="key">�����Կ</div></td>
								 -->
								<c:if test="${stockStatus eq 5}">
									<td>����</td>
								</c:if>
								<c:if test="${stockStatus eq 2}">
									<td>����</td>
								</c:if>
							</tr>
							<c:forEach var="stock" items="${pageInfo.result}">
							<tr>
								<td>
									<a href="${frontPath}/bill/exit/detail.action?entity.stockId=${stock.stockId}&status=${stockStatus}">${stock.stockId}</a>
								</td>
								<td>
								<c:if test="${stockStatus eq 2}">
								   ${stock.breedName}
								</c:if>
								<c:if test="${stockStatus ne 2}">
								   ${stock.breed.breedName}
								</c:if>
								</td>
								<td>${stock.warehouseId}</td>
								<td><fmt:formatNumber pattern="#0.00" value="${stock.quantity }"></fmt:formatNumber></td>
								<td>${stock.unit}</td>
								<c:if test="${stockStatus eq 2}">
									<td>${stock.company}</td>
									<td>${stock.logisticsOrder}</td>
									<td><fmt:formatDate value='${stock.receivedDate}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</c:if>
								<td><fmt:formatDate value='${stock.lastTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value='${stock.createTime}' pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<c:if test="${stockStatus eq 5}">
									<td><a href="#" onclick="stockOutCancel(${stock.stockId})">��������</a></td>
								</c:if>
								<c:if test="${stockStatus eq 2}">
							    	<td>
								 	<c:if test="${stock.isReceived eq 1}">
									    ���ջ�
									</c:if>
									<c:if test="${stock.isReceived ne 1}">
										<a href="#" onclick="stockReceived(${stock.stockId},${stock.invoiceStatus})">ȷ���ջ�</a>
									</c:if>
									</td>
								</c:if>
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
		</div>	</div>
</body>
</html>
<script>
jQuery(document).ready(function() {
	//����ѡ����ʽ
	if(${stockStatus=='2'}){
		$("#exited").parent().attr("class","c_titlebt_on");
	}
	<c:if test="${haveWarehouse != 1}">
	else if(${stockStatus=='5'}){
		$("#apply").parent().attr("class","c_titlebt_on");
	}
	</c:if>
	if(${reciveid=='1'}){
		$("#selled").parent().attr("class","c_titlebt_on");
	}
	//�鿴��ť
	$("#view").click(function(){
		$("#frm").submit();
	});
	//�鿴�ѳ���ֵ���Ϣ
	$("#exited").click(function() {
		$("#status").attr("value","2");
		$("#frm").attr("action","${frontPath}/bill/exit/list.action");
		$("#view").click();
	});
	<c:if test="${haveWarehouse != 1}">
	//�鿴���������еĲֵ���Ϣ
	$("#apply").click(function() {
		$("#status").attr("value","5");
		$("#frm").attr("action","${frontPath}/bill/exit/stockOutApplyList.action");
		$("#view").click();
	});
	</c:if>
	//�����ֵ��鿴
	$("#selled").click(function(){
		$("#frm").attr("action","${frontPath}/bill/exit/stockBySeller.action");
		$("#view").click();
	});
});

function stockOutCancel(stockId){
	var result = confirm("ȷ�ϳ����òֵ��ĳ���������");
	if(result){
		$("#frm").attr("action","${frontPath}/bill/exit/stockOutCancel.action?stockId="+stockId);
		$("#frm").submit();
	}
}
function stockReceived(stockId,invoiceStatus){
    var  str="��ȷ���յ����㹻�Ļ���";
    if(invoiceStatus!=0){
    	 str+="�뷢Ʊ";
    }
    if(confirm(str+"?"))
    {
    	$("#frm").attr("action","${frontPath}/bill/exit/stockConfirm.action?stockId="+stockId);
    	$("#frm").submit();
    }
   //alert(str+"?");
}
</script>
<%@include file="/front/public/jsp/commonmsg.jsp"%>