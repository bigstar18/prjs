<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>��Ա��ȡ�������޸�</title>
	</head>
	<body class="st_body">
		<form name="frm" action="${basePath}/account/takeFee/update.action" method="post" targetType="hidden">	
			    <input type="hidden" name="obj.commodityId" value="${obj.commodityId }">
				<input type="hidden"  name="obj.m_FirmId" value="${obj.m_FirmId }">
			<div class="div_scromin">
			   <table border="0" width="100%" align="center">
				<tr height="100"></tr>
				<tr>
					<td>
						<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;��ȡ�������޸�</div>
						<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="35">
						<td align="right">
							��Ʒ����:
						</td>
						<td>
							<input class="from" type="text" id="commodityId" name="obj.commId" value="${obj.commodityId}" readonly="readonly" style="background-color:#bebebe">
						</td>
						<td align="right">
							��Ʒ����:
						</td>
						<td>
							<input class="from" type="text" id="name" name="obj.commodityName" value="${obj.commodityName}" readonly="readonly" style="background-color:#bebebe">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							��Ա����:
						</td>
						<td>
							<input class="from" type="text" id="memberName" name="obj.memberName" value="${obj.memberName}" readonly="readonly" style="background-color:#bebebe">
						</td>
						<td align="right">
							�������㷨:
						</td>
						<td>
							<select id="feeAlgr" name="obj.feeAlgr" style="width: 120">
							 <c:forEach items="${feeAlgrMap}" var="maps">
								  	<option value="${maps.key}" <c:if test="${obj.feeAlgr==maps.key}">selected="selected"</c:if>>${maps.value }</option>
							  	</c:forEach>
							  </select>
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							��ȡ��ʽ:
						</td>
						<td>
							 <select id="feeMode" name="obj.feeMode" style="width: 120">
							 <c:forEach items="${takeFeeMap }" var="maps">
								  	<option value="${maps.key}" <c:if test="${obj.feeMode==maps.key}">selected="selected"</c:if>>${maps.value }</option>
							  	</c:forEach>
							  </select>
						</td>
						<td align="right">
							������ϵ��:
						</td>
						<td>
							<input class="from" type="text" id="feeRate" name="obj.feeRate_v" value="${obj.feeRate_v}">
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							��������ȡ������:
						</td>
						<td>
							<input class="from" type="text" id="mkt_FeeRate" name="obj.mkt_FeeRate_v" value="${obj.mkt_FeeRate_v}">
						</td>
					</tr>
						
				</table>
				</td>
				</tr>
				</table>
				</div>
				<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<button  class="btn_sec" onClick="updateTakeFeeStatus()" id="update">�޸�</button>
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
	function updateTakeFeeStatus(){
		if(parseFloat(frm.feeRate.value)<parseFloat(frm.mkt_FeeRate.value))
	   {
		    alert("��������ȡ�����ѱ���С��������ϵ��");
		    return false;
	   }
	
		frm.submit();
	}


</script>
<%@ include file="/public/footInc.jsp"%>