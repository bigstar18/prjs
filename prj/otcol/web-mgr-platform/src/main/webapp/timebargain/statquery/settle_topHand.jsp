<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<c:import url="/timebargain/statquery/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
 //   statQueryForm.marketCode.focus();
    query_onclick();
}
//query_onclick
function query_onclick()
{
  wait.style.visibility = "visible";
  statQueryForm.submit();
  statQueryForm.query.disabled = true;  
}
//requery_onclick
function requery_onclick()
{
  top.MainFrame.location.href = "<c:url value="/timebargain/statquery/quotation.jsp"/>";
}


</script>
	</head>

	<body leftmargin="2" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=listHand"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b><fmt:message key="query.condition.title" />
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
                             <!--       <td align="right"><fmt:message key="marketForm.marketName"/></td>
                                    <td>
                                        <select id="marketCode" name="_b.MarketCode[=]" style="width:111">
                                          <c:forEach items="${marketSelect}" var="market">
                                            <option value='<c:out value="${market.value}"/>'><c:out value="${market.label}"/></option>
                                          </c:forEach>
                                        </select>
                                    </td>		 --> 						
									<td align="left">
											<fmt:message key="ordersForm.Uni_Cmdty_Code" />
									</td>
									<td>
										<input type="text" id="uni_Cmdty_Code" name="_c.Commodityid[like]" title="<fmt:message key="statQuery.like.inputmode"/>" style="width:111" maxlength="24"
											styleClass="text" />
									</td>
						            <td align="left"><fmt:message key="commodityForm.Name"/></td>
						            <td ><input type="text" id="name" name="_c.name[like]" title="<fmt:message key="statQuery.like.inputmode"/>" maxlength="16" style="width:111" class="text" /></td>   									
						              	
						            <td align="left">
										&nbsp;&nbsp;<html:button property="query" style="width:60" styleClass="button"
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
