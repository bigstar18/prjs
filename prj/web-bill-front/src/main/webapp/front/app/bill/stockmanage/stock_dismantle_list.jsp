<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>��ֵ��б�</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
	</head>
	<body onload="getFocusID('stockID_q');">
	<div class="main">
		<jsp:include page="/front/frame/current.jsp"></jsp:include>
		<div class="warning">
			<div class="title font_orange_14b">��ܰ��ʾ :</div>
			<div class="content">1.�������Ļ���ֵ��������ͬ������ƥ��ʱ�������ύδע��ֵ��Ĳ�����룬�ɽ��׹�����˺������������ʵĲֵ��������������ĳ�ʺ�ͬת����ʵĲֵ���
			<br />2.�б���չʾ������صĲ�ֵ�ҵ�񣬰����𵥳ɹ�����ʧ���Լ������еĲ𵥡�</div>
		</div>
		<div class="main_copy">
			<form id="frm" action="${frontPath}/bill/split/list.action" method="post">
			<div class="sort">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30" width="312">
						<label>�ֵ��ţ�
							<input type="text" id="stockID_q" maxLength="<fmt:message key="stockID_q" bundle="${proplength}" />" name="${GNNT_}primary.stock.stockId[=]" value="${oldParams['primary.stock.stockId[=]']}"  onkeyup ="this.value=this.value.replace(' ','')"/>
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
										<a id="splited" href="#">�Ѳ�ֵ�</a>
									</td>
									<td width="140" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="spliting" href="#">��������</a>
									</td>
									<td width="140" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="failed" href="#">��ʧ��</a>
									</td>
									<td width="140" height="32" align="center" valign="bottom">
										
									</td>
									<td>&nbsp;</td>
								</tr>
							</table>
							<input id="status" name="status" type="hidden" value="${status}"/>
						</td>
					</tr>
					<tr>
						<td>
					<div class="tb_bg">
						<table border="0" cellspacing="0" cellpadding="0" class="content">
							<tr class="font_b tr_bg">
								<td><div class="ordercol" id="stock.stockId">�ֵ���</div></td>
								<td><div class="ordercol" id="dismantleId">�𵥱��</div></td>
								<td><div class="ordercol" id="newStockId">�²ֵ���</div></td>
								<td><div class="ordercol" id="realStockCode">�ֿ�ԭʼƾ֤��</div></td>
								<td><div class="ordercol" id="amount">��Ʒ����</div></td>
								<td><div class="ordercol" id="processTime">����ʱ��</div></td>
							</tr>
							<c:forEach var="dismantle" items="${pageInfo.result}">
							<tr>
								<td>
									<a href="${frontPath}/bill/split/detail.action?entity.stockId=${dismantle.stock.stockId}">${dismantle.stock.stockId}</a>
								</td>
								<td>${dismantle.dismantleId }</td>
								<td>${empty dismantle.newStockId ? '--' : dismantle.newStockId}</td>
								<td>${empty dismantle.realStockCode ? '--' : dismantle.realStockCode}</td>
								<td><fmt:formatNumber pattern="#0.00" value="${dismantle.amount}"></fmt:formatNumber></td>
								
								<td>
									<c:if test="${not empty dismantle.processTime}">
										<fmt:formatDate value='${dismantle.processTime}' pattern='yyyy-MM-dd HH:mm:ss' />
									</c:if>
									<c:if test="${empty dismantle.processTime}">
										--
									</c:if>
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
	if(${status=='1'}){
		$("#splited").parent().attr("class","c_titlebt_on");
	} else if(${status=='0'}){
		$("#spliting").parent().attr("class","c_titlebt_on");
	}else{
		$("#failed").parent().attr("class","c_titlebt_on");
	}
	//�鿴��ť
	$("#view").click(function(){
		$("#frm").submit();
	});
	//�鿴�Ѳ�ֵ���Ϣ
	$("#splited").click(function() {
		$("#status").attr("value","1");
		$("#view").click();
	});
	//�鿴���вֵ���Ϣ
	$("#spliting").click(function() {
		$("#status").attr("value","0");
		$("#view").click();
	});
	//�鿴���вֵ���Ϣ
	$("#failed").click(function() {
		$("#status").attr("value","2");
		$("#view").click();
	});
});
</script>
<%@include file="/front/public/jsp/commonmsg.jsp"%>