
<%@page import="gnnt.MEBS.timebargain.manage.util.SysData"%>
<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.ApplyGageManager"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.model.ApplyGage"  %>
<%@ page import="org.springframework.context.ApplicationContext"  %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.StatQueryManager"  %>
<%@ page import="gnnt.MEBS.timebargain.server.model.SystemStatus"  %>

<%	
StatQueryManager stat = (StatQueryManager) SysData.getBean("statQueryManager");
SystemStatus sys = stat.getSystemStatusObject();
int  status = sys.getStatus();
%>
<c:if test="${not empty returnMsg }">
	<script type="text/javascript" defer="defer"> 
		alert("<c:out value='${returnMsg }'/>");
		window.returnValue=0;
		window.close();
	</script>
</c:if>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<c:import url="/timebargain/statquery/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
						<script language="javascript"src="<c:url value="/timebargain/public/jstools/jquery.js"/>"></script>
		<title>
		抵顶业务审核
		</title>
		<script type="text/javascript"> 
	$(function(){
	var status =<%=sys.getStatus()%> 
	if(status !=1){
	alert('只能在闭市状态下进行审核');
	window.returnValue=0;
		window.close();
	}
	
	})
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
	applyGageForm.status.value = "2";//2为通过
	applyGageForm.submit();
  	applyGageForm.ok.disabled = true;
  	applyGageForm.fail.disabled = true;  
}

function fail_onclick(){
	applyGageForm.status.value = "3";//3为不通过
	applyGageForm.submit();
	applyGageForm.ok.disabled = true;
  	applyGageForm.fail.disabled = true;  
	
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
					ApplyGageManager applyGageManager = (ApplyGageManager)SysData.getBean("applyGageManager");
					String applyID = request.getParameter("applyID");
					if(applyID!=null){
					ApplyGage applyGage = applyGageManager.getApplyGage(Long.parseLong(applyID));
					request.setAttribute("applyGage",applyGage);
					}
					%>
				<html:form action="/timebargain/gageAudit/gageAudit.do?funcflg=gageAudit" method="POST" styleClass="form" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								<c:choose>
									<c:when test="${applyGage.applyType==1 }">
										正常抵顶
									</c:when>
									<c:when test="${applyGage.applyType==2 }">
										正常撤销已有抵顶
									</c:when>
									<c:when test="${applyGage.applyType==3 }">
										强制撤销已有抵顶
									</c:when>
								</c:choose>
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0" border="0" class="common">
								<tr>
									<td>仓单号：${applyGage.applyId }</td>
								</tr>
								<tr>
									<td width="160">商品代码：${applyGage.commodityID }</td>
								</tr>
								<tr>
									<td>交易商代码：${applyGage.firmID }</td>
								</tr>
								<tr>
									<td>交易二级代码：${applyGage.customerID }</td>
								</tr>
								<tr>
									<td>
										仓单数量：${applyGage.quantity }
									</td>
								</tr>
								<!-- 
								<tr>
									<td>
										当前状态：
										<c:choose>
											<c:when test="${applyGage.status==1 }">
												待审核
											</c:when>
											<c:when test="${applyGage.status==2 }">
												审核通过
											</c:when>
											<c:when test="${applyGage.status==3 }">
												审核不通过
											</c:when>
										</c:choose>
									</td>
								</tr>
								 -->
								<tr>
									<td>
										创建时间：<fmt:formatDate value="${applyGage.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
									</td>
								</tr>
								<tr>
									<td>
										创建人：${applyGage.creator }
									</td>
								</tr>
								<tr>
									<td>
										审核备注：<textarea name="remark2" rows="3" cols="55"  style="width:260" styleClass="text"></textarea>
									</td>
									<td>&nbsp;&nbsp;</td>
								</tr>
								<tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
								<tr>
						            <td align="center" width="200">
										<html:button property="ok" style="width:60" styleClass="button"
											onclick="javascript:return ok_onclick();">
											通过
										</html:button>
										<html:button property="fail" style="width:60" styleClass="button"
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
								<html:hidden property="applyId" value="${applyGage.applyId }"/>
								<html:hidden property="status"/>
					</html:form>
				</td>
			</tr>
		</table>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>