<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    query_onclick();
}
//query_onclick
function query_onclick()
{
  virtualFundForm.submit();
  virtualFundForm.query.disabled = true;  
}
//requery_onclick

</script>
	</head>

	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">	
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<form id="virtualFundForm" action="<%=basePath%>/timebargain/virtualFundCheList.spr"
						method="POST" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>��ѯ����
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									
									<td align="right">
											�����̴��룺
									</td>
									<td>
										<input type="text"  name="firmID" maxlength="16" style="ime-mode:disabled" styleClass="text" size="10"/>
									</td>
			                       <td>&nbsp;</td><td>&nbsp;</td>
			                       <td align="right">
											״̬��
									</td>
									<td>
										<select name="status" >
											<option value=""></option>
											<option value="1">�����</option>
											<option value="2">���ͨ��</option>
											<option value="3">��˲�ͨ��</option>
										</select>
									</td>
			                       <td>&nbsp;</td><td>&nbsp;</td>
									<td align="right">
										<input type="button" name="query" value="ִ�в�ѯ" style="width:60" 
											onclick="javascript:return query_onclick();"/>
										
									</td>
									<td>
										
									</td>																	
								</tr>
							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>

		<%@ include file="../../common/messages.jsp"%>
	</body>
</html>
