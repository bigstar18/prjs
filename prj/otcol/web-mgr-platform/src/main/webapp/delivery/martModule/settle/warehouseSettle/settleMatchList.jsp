<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../../public/session.jsp"%>

<html xmlns:MEBS>
<body onload="initBody('${returnRefresh}')">
<form name="frm" method="post" action="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleMatchList&_moduleid[%3D]=${moduleId }">
	<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<input type="hidden" id="matchId" name="matchId">
		<input type="hidden" id="moduleId" name="moduleId" value="${moduleId}">
	<fieldset>
		<legend class="common"><b>��ѯ����</b></legend>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr class="common" height="35">
			<td align="right">${FIRMID }��</td>
			<td>
				<input type="text" id="firmId" name="_firmId[=]" value="<c:out value='${oldParams["firmId[=]"]}'/>" class="text" style="width: 150px">
							
			</td>
			<td align="left" width="70">ִ�н����</td>
			<td>
				<select id="result" name="_result[=]" class="normal">
					<option value="">ȫ��</option>
					<option value="1">��Լ</option>
					<option value="2">��ΥԼ</option>
					<option value="3">����ΥԼ</option>
					<option value="4">˫��ΥԼ</option>
					<option value="5">��������</option>
				</select>
				<script type="text/javascript">
					frm.result.value="<c:out value='${oldParams["result[=]"] }'/>";
				</script>
			</td>
			</tr>
			<tr height="35">
			<td align="right">״̬��</td>
			<td>
				<select id="smstatus" name="_status[=]" class="normal">
					<option value="">ȫ��</option>
					<option value="0">δ����</option>
					<option value="1">������</option>
					<option value="2">�������</option>
					<option value="3">����</option>
				</select>
				<script type="text/javascript">
					frm.smstatus.value="<c:out value='${oldParams["status[=]"] }'/>";
				</script>
			</td>
			<td align="left" colspan="2">
				<input type="button" class="smlbtn" value="��ѯ" onclick="doQuery();">&nbsp;
				<input type="button" class="smlbtn" value="����" onclick="resetForm();">&nbsp;
			</td>
			</tr>
		</table>
		</span>
	</fieldset>
	<br>
	<%@ include file="settleMatchTable.jsp" %>
	<!-- 
	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="35">
            <td><div align="right">
				<input type="button" class="bigbtn" value="��������Ϣ" onclick="addSettleMatch()">
			</div></td>
        </tr>
    </table>
     -->
</body>
</html>
<script type="text/javascript">

	function addSettleMatch()
	{
		frm.action="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleMatchAddForward";
		frm.submit();	
	}
	function doQuery(){
		frm.submit();
	}
	function viewSettle(vid)
	{
		frm.matchId.value = vid;
		frm.action="<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleView&moduleId=${moduleId}";
		frm.submit();	
	}
	function resetForm(){
		//frm.moduleId.value = "";
		frm.result.value = "";
		frm.firmId.value = "";
		frm.smstatus.value = "";		
		frm.submit();
	}
</script>