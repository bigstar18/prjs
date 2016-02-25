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
    customerForm.groupID.focus();
}
//query_onclick
function query_onclick()
{
  customerForm.submit();
}

</script>
	</head>

	<body leftmargin="10" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="100%" width="740" cellpadding="0" cellspacing="0" class="common" align="left">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/customer.do?funcflg=searchFirst&isQry=1"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b><fmt:message key="query.condition.title" />
								</b>
							</legend>
							<table border="0"  cellpadding="1" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
										组：
									</td>
									<td>
										<select name="groupID" style="width:111">
										  <c:forEach items="${customerGroupSelect}" var="customerGroup">
                                              <option value='<c:out value="${customerGroup.value}"/>'><c:out value="${customerGroup.label}"/></option>
                                          </c:forEach>
                                        </select>
									</td>
									<td align="right">
										交易商ID：
									</td>
									<td>
										<input type="text" name="firmID" style="width:111" maxlength="16" title="<fmt:message key="title.dblclick.query"/>"
											styleClass="text" />
									</td>
			                        <td align="right">
										交易商名称：
									</td>
									<td>
										<input type="text" name="firmName" title="<fmt:message key="statQuery.like.inputmode"/>" style="width:111" maxlength="32" styleClass="text"/>
									</td>		
									<td align="right">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											<fmt:message key="button.query" />
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
