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
			<div class="content">1.δע��ֵ���ע����Ϊ��ע��ֵ�����ע��ֵ��������ڡ������ֵ������ף���������ת��ֵ����н��ա���ע��ֵ������԰�����⣬Ҳ���ɲ�֡�
			<br />2.�б���չʾ�������вֵ����������õ�ע��ֵ����Ѿ��ڽ����������Ĳֵ������ڻ��ｻ�յ�ע��ֵ���</div>
		</div>
		<div class="main_copy">
			<form id="frm" action="${frontPath}/bill/register/list.action" method="post">
			<div class="sort">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="30" width="312">
						<label>�ֵ��ţ�
							<input type="text" id="stockID_q" maxLength="<fmt:message key="stockID_q" bundle="${proplength}" />" name="stockIdPra" value="${stockId}"  onkeyup ="this.value=this.value.replace(' ','')"/>
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
									<td width="100" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="usable" href="#">����</a>
									</td>
									<td width="100" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="financing" href="#">���ʲֵ�</a>
									</td>
									<td width="100" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="sell" href="#">���ֵ�</a>
									</td>
									<td width="100" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="trade" href="#">���ղֵ�</a>
									</td>
									<td width="100" height="32" align="center" valign="bottom" class="c_titlebt">
										<a id="frozen" href="#">����ֵ�</a>
									</td>									
									<td>&nbsp;</td>
								</tr>
							</table>
							<input id="operation" name="operation" type="hidden" value="${operation}"/>
						</td>
					</tr>
					<tr>
						<td>
					<div class="tb_bg">
						<c:if test="${operation == ''}">
							<jsp:include page="stock_register_msg.jsp"></jsp:include>
						</c:if>
						<c:if test="${operation == '1'}">
							<jsp:include page="stock_financing_msg.jsp"></jsp:include>
						</c:if>
						<c:if test="${operation == '2'}">
							<jsp:include page="stock_sell_msg.jsp"></jsp:include>
						</c:if>
						<c:if test="${operation == '3'}">
							<jsp:include page="stock_trade_msg.jsp"></jsp:include>
						</c:if>
						<c:if test="${operation == '4'}">
							<jsp:include page="stock_frozen_msg.jsp"></jsp:include>
						</c:if>
					</div>
						</td>
				</tr>
			</table>
					<jsp:include page="/front/frame/pageinfo.jsp"></jsp:include>
				</div>
			</form>
			<iframe  name="frame" width=0 height=0  application='yes'></iframe>
		</div>
	</div>
</body>
</html>

<script>
jQuery(document).ready(function() {
	//������������ť��ѡ����ʽ
	if(${operation == ''}){
		$("#usable").parent().attr("class","c_titlebt_on");
	} else if(${operation =='1'}){
		$("#financing").parent().attr("class","c_titlebt_on");
	}else if(${operation =='2'}){
		$("#sell").parent().attr("class","c_titlebt_on");
	}else if(${operation =='3'}){
		$("#trade").parent().attr("class","c_titlebt_on");
	}else if(${operation =='4'}){
		$("#frozen").parent().attr("class","c_titlebt_on");
	}
	
	//�鿴��ť
	$("#view").click(function(){
		$("#frm").attr("target","");
		$("#frm").attr("action","${frontPath}/bill/register/list.action");
		$("#frm").submit();
	});
	//���òֵ���Ϣ
	$("#usable").click(function() {
		$("#operation").attr("value","");
		$("#view").click();
	});
	//���ʲֵ���Ϣ
	$("#financing").click(function() {
		$("#operation").attr("value","1");
		$("#view").click();
	});
	//���ֵ���Ϣ
	$("#sell").click(function() {
		$("#operation").attr("value","2");
		$("#view").click();
	});
	//���ղֵ���Ϣ
	$("#trade").click(function() {
		$("#operation").attr("value","3");
		$("#view").click();
	});
	//����ֵ���Ϣ
	$("#frozen").click(function() {
		$("#operation").attr("value","4");
		$("#view").click();
	});
});

//ע���ֵ�
function logoutStock(stockID){
	if(affirm("ȷ��ע���òֵ�?")){
		frm.target="frame";
		frm.action="${frontPath}/bill/register/logoutStock.action?stockId=" + stockID;
		frm.submit();
	}
}

function showMsgBoxCallbak(result){
	window.location="${frontPath}/bill/register/list.action?sortColumns=" + '${sortColumns}'; 
}
</script>
<%@include file="/front/public/jsp/commonmsg.jsp"%>