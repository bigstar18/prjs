<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/front/public/includefiles/allIncludeFiles.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>�������ʽ���ϸҳ��</title>
		<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.2.min.js"></script>
		<script type="text/javascript" src="${publicPath}/js/calendar/WdatePicker.js"></script>
		<script type="text/javascript" src="${publicPath}/js/tool.js"></script>
		<style type="text/css">
			tr td:nth-child(odd){text-align: right;}
			tr td:nth-child(even){text-align: center;}
		</style>
		
		<script type="text/javascript">
		$(function(){
		});
		
		</script>
	</head>
	<body>
<!-------------------------Body Begin------------------------->
<div class="main">
	<jsp:include page="/front/frame/current.jsp"></jsp:include>
	<div class="warning">
		<div class="title font_orange_14b">��ܰ��ʾ :</div>
		<div class="content">�ڴ�չʾ�����ʽ���ϸ��Ϣ��</div>
	</div>
	<div class="main_copy">
		<div class="content">
			<div class="tb_bg">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="content">
					<tr class="font_b">
						<td width="25%"><div class="ordercol">�ڳ���</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['LASTBALANCE'] }" pattern="#,##0.00"/></td>
						<td width="50%"colspan="2">&nbsp;</td>
					</tr>
					<tr class="font_b">
						<td width="25%"><div class="ordercol">(+)���ձ�֤��</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['CLEARMARGIN'] }" pattern="#,##0.00"/></td>
						<td width="25%"><div class="ordercol">(-)���ձ�֤��</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['RUNTIMEMARGIN'] }" pattern="#,##0.00"/></td>
					</tr>
					<tr class="font_b">
						<td width="25%"><div class="ordercol">(+)���ո�����</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['CLEARFL'] }" pattern="#,##0.00"/></td>
						<td width="25%"><div class="ordercol">(-)���ո�����</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['RUNTIMEFL'] }" pattern="#,##0.00"/></td>
					</tr>
					<tr class="font_b">
						<td width="25%"><div class="ordercol">(+)���</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['INAMOUNT'] }" pattern="#,##0.00"/></td>
						<td width="25%"><div class="ordercol">(-)����</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['OUTAMOUNT'] }" pattern="#,##0.00"/></td>
					</tr>
					<tr class="font_b">
						<td width="25%"><div class="ordercol">(+)����ת��ӯ����</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['CLOSE_PL'] }" pattern="#,##0.00"/></td>
						<td width="25%"><div class="ordercol">(-)ί�ж����ʽ�</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['ORDERFROZEN'] }" pattern="#,##0.00"/></td>
					</tr>
					<tr class="font_b">
						<td width="25%"><div class="ordercol">(+)����������仯��</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['RUNTIMEASSURE'] - firmInfo['CLEARASSURE'] }" pattern="#,##0.00"/></td>
						<td width="25%"><div class="ordercol">(-)���������ѣ�</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['TRADEFEE'] }" pattern="#,##0.00"/></td>
					</tr>
					<c:choose> 
						<c:when test="${fundDetail['MODULEID']=='15'}">
							<tr class="font_b">
								<td width="25%"><div class="ordercol">(+)�������</div></td>
								<td width="25%"><fmt:formatNumber value="${fundDetail['T_GOODMONEY'] }" pattern="#,##0.00"/></td>
								<td width="25%"><div class="ordercol">(-)����ΥԼ��</div></td>
								<td width="25%"><fmt:formatNumber value="${fundDetail['PENAL'] }" pattern="#,##0.00"/></td>
							</tr>
							<tr class="font_b">
								<td width="25%"><div class="ordercol">(+)��������ӯ����</div></td>
								<td width="25%"><fmt:formatNumber value="${fundDetail['SETTLEPL'] }" pattern="#,##0.00"/></td>
								<td width="25%"><div class="ordercol">(-)�������������ѣ�</div></td>
								<td width="25%"><fmt:formatNumber value="${fundDetail['SETTLEFEE'] }" pattern="#,##0.00"/></td>
							</tr>
							<tr class="font_b">
								<td width="25%"><div class="ordercol">(+)�������ձ�֤��</div></td>
								<td width="25%"><fmt:formatNumber value="${fundDetail['SETTLEMARGAIN'] }" pattern="#,##0.00"/></td>
								<td width="25%"></td>
								<td width="25%"></td>
							</tr>
						</c:when>
					</c:choose>
					<tr class="font_b">
						<td width="25%"><div class="ordercol">��ǰ������</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['USEFULFUND'] }" pattern="#,##0.00"/></td>
						<td width="25%" colspan="2"></td>
					</tr>
					<tr class="font_b">
						<td width="25%"><div class="ordercol">����ӯ����</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['PL'] }" pattern="#,##0.00"/></td>
						<td width="25%" colspan="2"></td>
					</tr>
					<tr class="font_b">
						<td width="25%"><div class="ordercol">���յ�����</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['RUNTIMEASSURE'] }" pattern="#,##0.00"/></td>
						<td width="25%" colspan="2"></td>
					</tr>
					<tr class="font_b">
						<td width="25%"><div class="ordercol">���ս��ձ�֤��</div></td>
						<td width="25%"><fmt:formatNumber value="${firmInfo['RUNTIMESETTLEMARGIN'] }" pattern="#,##0.00"/></td>
						<td width="25%" colspan="2"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
<!-------------------------Body End------------------------->
	</body>
</html>