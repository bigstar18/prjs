<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>��Ա���ڷѽ������</title>


	</head>
	<body class="st_body">
		<br />
		<form name="frm" action="${basePath}/commodity/stepFundsDictionary/add.action"
			method="post" targetType="hidden">
			<fieldset width="50%" height="60%">
				<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;��Ա���ڷѽ������</div>
				<input type="hidden" name="obj.ladderCode" value="MemberFunds">
				<table border="0" cellspacing="0" cellpadding="4" width="90%"
					align="center">
					<tr height="35">
						<td align="right">���ݱ��: 
						</td>
						<td>
							<input class="input_text_pwdmin" type="text" id="id" name="obj.id" value="��Ա�ʽ����" readonly="readonly">
						</td>
					</tr>
					<tr height="35">
						<td align="right">���ݽ׺�: 
						</td>
						<td><select style="width: 120" name="obj.stepNo">
						<c:forEach items="${stepNoMap}" var="map">
							<c:set var="log" value="false"></c:set>
							<c:forEach items="${stepDicList}" var="list">
								<c:if test="${list.stepNo==map.key}">
									<c:set var="log" value="true"></c:set>
								</c:if>
							</c:forEach>
							<c:if test="${log==false}">
									<option value="${map.key }">${map.value}</option>
								</c:if>
						</c:forEach></select>
						</td>
					</tr>
					<tr height="35">
						<td align="right">
							����ֵ:
						</td>
						<td>
							<input class="from" type="text" id="stepValue" 
							style="text-align:right" name="obj.stepValue" value="" >
						</td>
					</tr>
				</table>
			</fieldset>
			<div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center">
						<tr>
							<td  align="center">
								<button  class="btn_sec" id="update" onclick="updateStepDictionary()">�޸�</button>
							</td>
							<td align="center">
								<button  class="btn_sec" onclick="window.close()">�ر�</button>
							</td>
						</tr>
					</table>
					</div>
			</form>
		
	</body>
</html>
<script type="text/javascript">
	function updateStepDictionary(){
			frm.submit();
	}


</script>
<%@ include file="/public/footInc.jsp"%>