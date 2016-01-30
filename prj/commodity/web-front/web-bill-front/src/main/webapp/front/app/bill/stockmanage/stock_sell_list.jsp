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
			<form id="frm" action="${frontPath}/bill/exit/stockBySeller.action" method="post">
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
								<td><div class="ordercol" id="breedName">Ʒ��</div></td>
								<td><div class="ordercol" id="warehouseId">�ֿ���</div></td>
								<td><div class="ordercol" id="quantity">��Ʒ����</div></td>
								<td>��λ</td>
								<td><div class="ordercol" id="sellTime">����ʱ��</div></td>
								<td><div class="ordercol" id="received">�ջ�״̬</div></td>
								<td><div class="ordercol" id="receivedDate">�ջ�ʱ��</div></td>
								<td><div class="ordercol">����</div></td>
							
							</tr>
							<c:forEach var="sellstock" items="${pageInfo.result}">
							<tr>  
								<td>
									<a href="${frontPath}/bill/exit/detail.action?entity.stockId=${sellstock.stockId}&status=${stockStatus}">${sellstock.stockId}</a>
								</td>
								<td>${sellstock.breedName}</td>
								<td>${sellstock.warehouseId}</td>
								<td><fmt:formatNumber pattern="#0.00" value="${sellstock.quantity }"></fmt:formatNumber></td>
								<td>${sellstock.unit}</td>
								<td><fmt:formatDate value='${sellstock.sellTime}' pattern="yyyy-MM-dd" /></td>
								<td>
								<c:if test="${sellstock.received eq 1}">���ջ�</c:if>
								<c:if test="${sellstock.received ne 1}">δ�ջ�</c:if>
								</td>
								<td><fmt:formatDate value='${sellstock.receivedDate}' pattern="yyyy-MM-dd" /></td>
								<td>
								<c:if test="${sellstock.invoiceStatus gt 0}">
									<a href="#" onclick="showInvoice(${sellstock.stockId})">�鿴��Ʊ</a>
								</c:if>
								<c:if test="${sellstock.invoiceStatus eq 0}">--&nbsp;&nbsp;--</c:if>
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

function showInvoice(stockId){
		var url="${basePath}/front/bill/exit/showInvoice.action?stockId="+stockId;
		if(showDialog(url, "", 500, 600)){
			frm.submit();
		}
}
</script>
<%@include file="/front/public/jsp/commonmsg.jsp"%>