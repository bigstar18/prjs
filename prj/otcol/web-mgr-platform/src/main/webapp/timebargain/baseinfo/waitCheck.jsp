<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>

<%@ page import="gnnt.MEBS.timebargain.manage.service.CustomerManager"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.MarketManager"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.model.Customer" %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.SysData" %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.DateUtil"  %>
<%@ page import="java.util.Date"  %>
<%@ page import="java.util.List"  %>
<%@ page import="java.util.Map"  %>
<%@ page import="java.math.*"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.util.*"  %>
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
}
//query_onclick

function cancel_onclick()
{
   window.close();
}

function ok_onclick(){
	customerForm.type.value = "1";//1为通过
	customerForm.submit();
  	customerForm.query.disabled = true;  
	window.close();
}

function fail_onclick(){
	customerForm.type.value = "2";//2为不通过
	customerForm.submit();
  	customerForm.query.disabled = true;  
	window.close();
	
} 

</script>
	</head>

	<body leftmargin="2" topmargin="0" 
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="200" width="380" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<%
											CustomerManager mgr = (CustomerManager)SysData.getBean("customerManager");
											String applyID = request.getParameter("applyID");
											List list = mgr.getVirtualFundsApplyById(applyID);
											
											
											String firmID = "";
											String virtualFunds = "";
											
											String creator = "";
											if (list != null && list.size() > 0) {
												Map map = (Map)list.get(0);
												if (map.get("FirmID") != null) {
													firmID = map.get("FirmID").toString();
												}
												if (map.get("VirtualFunds") != null) {
													virtualFunds = map.get("VirtualFunds").toString();
												}
												if (map.get("Creator") != null) {
													creator = map.get("Creator").toString();
												}
												
											}
											
								%>
								
					
						<html:form action="/timebargain/baseinfo/virtualFund.do?funcflg=waitSuccessCheck"
						method="POST" styleClass="form" target="ListFrame1">
						<fieldset class="pickList" >
							<legend class="common">
								<b>审核虚拟资金申请
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" border="0" class="common">
								<tr>
									<td>
										申请单号：<%=applyID%>
									</td>
								</tr>
								<tr>
									<td width="160">
										交易商ID：<%=firmID%>
									</td>
								</tr>
								<tr>
									<td>
										虚拟资金：<%=virtualFunds%>
									</td>
								</tr>
								<tr>
									<td>
										创建人：<%=creator%>
									</td>
								</tr>
								
								<tr>
									
									<td>
										备注：<html:textarea property="remark2" rows="3" cols="55"  style="width:150" styleClass="text" />
										
									</td>
									<td>&nbsp;&nbsp;</td>
								</tr>
								
								<tr>
								
								
						            <td align="center" width="200">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return ok_onclick();">
											通过
										</html:button>
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return fail_onclick();">
											不通过
										</html:button>
										<html:button property="close" style="width:60" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											关闭
										</html:button>
									</td>  		                                                           							
								</tr>
								
							</table>
						</fieldset>
								<html:hidden property="firmID" value="<%=firmID%>"/>
								<html:hidden property="virtualFunds" value="<%=virtualFunds%>"/>
								<html:hidden property="applyID" value="<%=applyID%>"/>
								<html:hidden property="type"/>
					</html:form>
				
					
					
					
					
					
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
