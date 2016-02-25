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
    breedForm.sortID.focus();
    query_onclick();
}
//query_onclick
function query_onclick()
{
  breedForm.submit();
}

</script>
	</head>

	<body leftmargin="25" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/breed.do?funcflg=search"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b><fmt:message key="query.condition.title" />
								</b>
							</legend>
							<table border="0"  cellpadding="5" cellspacing="0"
								class="common">

								<tr>
									<td align="right"><fmt:message key="cmdtySortForm.sortName"/>£º</td>     
						            <td>  
										<select id="sortID" name="_c.sortID[=]" style="width:111">
                                          <c:forEach items="${cmdtySortSelect}" var="cmdtySort">
                                            <option value='<c:out value="${cmdtySort.value}"/>'><c:out value="${cmdtySort.label}"/></option>
                                          </c:forEach>
                                        </select>
									</td>
									<td align="right"><fmt:message key="marketForm.marketName"/>£º</td>
                                    <td>
                                        <select id="marketCode" name="_b.MarketCode[=]" style="width:111">
                                          <c:forEach items="${marketSelect}" var="market">
                                            <option value='<c:out value="${market.value}"/>'><c:out value="${market.label}"/></option>
                                          </c:forEach>
                                        </select>
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
