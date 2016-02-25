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
    sell_onclick();
}

function sell_onclick(){
	document.forms(0).sell.style.color = "red";
	document.forms(0).buy.style.color = "#26548B";
	parent.List5Frame.location.href = "<c:url value="/timebargain/statquery/statQuery.do?funcflg=listTogether&flag="/>" + "2";
}
function buy_onclick(){
	//buy.style.color = "red";
	document.forms(0).buy.style.color = "red";
	document.forms(0).sell.style.color = "#26548B";
	parent.List5Frame.location.href = "<c:url value="/timebargain/statquery/statQuery.do?funcflg=listTogether&flag="/>" + "1";
}

</script>
	</head>

	<body leftmargin="13" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=listTogether"
						method="POST" styleClass="form" target="List5Frame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>²éÑ¯Ìõ¼þ
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
	
								<tr>
						            <td align="left">
										&nbsp;&nbsp;<html:button property="query" style="width:60" styleClass="button" styleId="buy"
											onclick="javascript:return buy_onclick();">
											Âò
										</html:button>
										
									</td>  		
									 <td align="left">
										&nbsp;&nbsp;<html:button property="query" style="width:60" styleClass="button" styleId="sell"
											onclick="javascript:return sell_onclick();">
											Âô
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
