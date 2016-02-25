<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    customerForm.firmID.focus();
}
//query_onclick
function query_onclick()
{
  customerForm.submit();
}

</script>
	</head>

	<body leftmargin="14" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="left">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/firm.do?isQry=1"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
							<table border="0"  cellpadding="1" cellspacing="0"
								class="common">
								<tr>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td align="right">
										交易商代码：
									</td>
									<td>
										<input type="text" name="firmID" style="width:111" maxlength="16" 
											styleClass="text" onkeypress="onlyNumberAndCharInput()"/>
									</td>
									<td>&nbsp;</td><td>&nbsp;</td>
			                        <td align="right">
										交易商名称：
									</td>
									<td>
										<input type="text" name="firmName" title="可输入模式匹配符查询" style="width:111" maxlength="32" styleClass="text" onkeypress="onlyNumberAndCharInput()"/>
									</td>	
									<td>&nbsp;</td>	
									<td align="right">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										
									</td>																	
								</tr>
							</table>
						</fieldset>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
