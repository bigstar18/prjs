<jsp:directive.page import="gnnt.MEBS.timebargain.manage.service.AheadSettleManager"/>
<jsp:directive.page import="gnnt.MEBS.timebargain.manage.model.AheadSettle"/>
<%@ page import="gnnt.MEBS.timebargain.manage.service.StatQueryManager"  %>
<%@ page import="gnnt.MEBS.timebargain.server.model.SystemStatus"  %>
<%@page import="gnnt.MEBS.timebargain.manage.util.SysData"%>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<c:if test="${not empty returnMsg }">
	<script type="text/javascript" defer="defer"> 
		alert("<c:out value='${returnMsg }'/>");
		window.returnValue=0;
		window.close();
	</script>
</c:if>
<%	
StatQueryManager stat = (StatQueryManager) SysData.getBean("statQueryManager");
SystemStatus sys = stat.getSystemStatusObject();
int  status = sys.getStatus();
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<c:import url="/timebargain/statquery/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>��ǰ��������</title>
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
	var status =<%=sys.getStatus()%> 
	if(status !=1){
		alert('ֻ���ڱ���״̬�½������');
		window.returnValue=0;
		window.close();
	}else{
		aheadSettleForm.status.value = "2";//2Ϊͨ��
		aheadSettleForm.submit();
  		aheadSettleForm.ok.disabled = true;
  		aheadSettleForm.fail.disabled = true;  
	}
}

function fail_onclick(){
	var status =<%=sys.getStatus()%> 
	if(status !=1){
		alert('ֻ���ڱ���״̬�½������');
		window.returnValue=0;
		window.close();
	}else{
		aheadSettleForm.status.value = "3";//3Ϊ��ͨ��
		aheadSettleForm.submit();
		aheadSettleForm.ok.disabled = true;
  		aheadSettleForm.fail.disabled = true;  
	}
	
	
} 

</script>
	</head>

	<body leftmargin="2" topmargin="0" 
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="300" width="480" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<%
					AheadSettleManager asm = (AheadSettleManager)SysData.getBean("aheadSettleManager");
					String applyID = request.getParameter("applyID");
					AheadSettle as = asm.getAheadSettle(applyID);
					request.setAttribute("aheadSettle",as);
					%>
				<html:form action="/timebargain/aheadSettle/aheadSettleAudit.do?funcflg=aheadSettleAudit" method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								<c:choose>
									<c:when test="${aheadSettle.applyType==1 }">
										��ǰ����
									</c:when>
								</c:choose>
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" border="0" class="common">
								<tr>
									<td>�ֵ��ţ�${aheadSettle.applyID }</td>
								</tr>
								<tr>
									<td width="160">��Ʒ���룺${aheadSettle.commodityID }</td>
								</tr>
								<tr>
									<td>�����������룺${aheadSettle.customerID_S }</td>
								</tr>
								<tr>
									<td>�򷽶������룺${aheadSettle.customerID_B }</td>
								</tr>
								<tr>
									<td>
										����������<fmt:formatNumber value="${aheadSettle.quantity }" pattern="###,###"/> 
									</td>
								</tr>
								<tr>
									<td>
										�����ֶ�������<fmt:formatNumber value="${aheadSettle.gageQty }" pattern="###,###"/> 
									</td>
								</tr>
								<tr>
									<td>
										���ռ۸� 
										 <c:if test="${aheadSettle.price=='0' }">�������۽��� </c:if>
										<c:if test="${aheadSettle.price!='0' }">	
										<fmt:formatNumber value="${aheadSettle.price }" pattern="###,###.##"/> 
									     </c:if>
									</td>
								</tr>
								<tr>
									<td>
										�������ڣ�<fmt:formatDate value="${aheadSettle.createTime }" pattern="yyyy-MM-dd"/>
									</td>
								</tr>
								<tr>
									<td>
										�����ˣ�${aheadSettle.creator }
									</td>
								</tr>
								<tr>
									<td>
										��˱�ע��<textarea name="remark2" rows="3" cols="55"  style="width:260" styleClass="text" ></textarea>
									</td>
									<td>&nbsp;&nbsp;</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
						            <td align="center" width="200">
										<html:button property="ok" style="width:60" styleClass="button"
											onclick="javascript:return ok_onclick();">
											ͨ��
										</html:button>
										<html:button property="fail" style="width:60" styleClass="button"
											onclick="javascript:return fail_onclick();">
											��ͨ��
										</html:button>
										<html:button property="close" style="width:60" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
									</td>  		                                                           							
								</tr>
							</table>
						</fieldset>
								<html:hidden property="applyID" value="${aheadSettle.applyID }"/>
								<html:hidden property="status"/>
					</html:form>
				</td>
			</tr>
		</table>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>