<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="org.springframework.context.ApplicationContext"  %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"  %>
	
	<%
		String date = request.getParameter("date");
		String code = request.getParameter("code");
		String qty = request.getParameter("qty");
		String id = request.getParameter("id");
		if (qty == null || "".equals(qty)) {
			qty = "0";
		}
		System.out.println("date:"+date);
		System.out.println("code:"+code);
		System.out.println("qty:"+qty);
	%>
	
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
    
    setReadOnly(deductForm.deductDate);
    setReadOnly(deductForm.commodityID);
    setReadOnly(deductForm.deductQty);
}
//query_onclick
function deduct_onchange(){
	if (deductForm.deductDate.value == "") {
		alert("强减日期不能为空！");
		deductForm.deductDate.focus();
		return false;
	}
	if (deductForm.commodityID.value == "") {
		alert("商品代码不能为空！");
		deductForm.commodityID.focus();
		return false;
	}
	deductForm.submit();
}


</script>
	</head>

	<body leftmargin="2" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="70%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/deduct/deduct.do?funcflg=nextDeductDetail"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>强减明细
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									<td align="left">
											时间：
									</td>
									<td>
										<html:text property="deductDate" value="<%=date%>" styleClass="text" maxlength="16" size="12"></html:text>
									</td>
									<td>&nbsp;</td>
									<td align="right">
											商品代码：
									</td>
									<td>
										<html:text property="commodityID" value="<%=code%>" styleClass="text" maxlength="16" size="12"></html:text>
									</td>
									<td>&nbsp;</td>
									<td align="right">
											应强减数量：
									</td>
									<td>
										<html:text property="deductQty" value="<%=qty%>" styleClass="text" maxlength="16" size="12"></html:text>
									</td>		                                                           							
								</tr>
								
							</table>
						</fieldset>
						<html:hidden property="deductID" value="<%=id%>"/>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
