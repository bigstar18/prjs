<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<c:import url="/timebargain/common/date.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
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
  if(agencyForm.beginDate.value=="")
  {
    alert("开始日期不能为空！");
    agencyForm.beginDate.focus();
    return false;
  }
  if(agencyForm.endDate.value=="")
  {
    alert("结束日期不能为空！");
    agencyForm.endDate.focus();
    return false;
  }
  if(!isDateFormat(agencyForm.beginDate.value))
  {
    alert("开始日期格式不正确！");
    agencyForm.beginDate.focus();
    return false;
  }
  if(!isDateFormat(agencyForm.endDate.value))
  {
    alert("结束日期格式不正确！");
    agencyForm.endDate.focus();
    return false;
  }
  var begin = agencyForm.beginDate.value;
  var end = agencyForm.endDate.value;
  var b = begin.replace("","-");
  var e = end.replace("","-");
  if(e<b){
	alert("结束日期小于开始日期！");
	return false;
  }

  wait.style.visibility = "visible";
  agencyForm.submit();
  agencyForm.query.disabled = true;  
}
//requery_onclick
function requery_onclick()
{
  top.mainFrame.location.href = "<c:url value="/timebargain/onlinebrowse/sysLog.jsp"/>";
}
//del_onclick
function del_onclick()
{
  if(confirm("您确定要提交吗？"))
  {
    agencyForm.action = "<c:url value="/timebargain/onlinebrowse/onlinebrowse.do?funcflg=sysLog_delete"/>";
    wait.style.visibility = "visible";
    agencyForm.submit();
    agencyForm.del.disabled = true; 
    agencyForm.action = "<c:url value="/timebargain/onlinebrowse/onlinebrowse.do?funcflg=sysLog_list"/>";
  }
}
</script>
	</head>
 
	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/onlinebrowse/onlinebrowse.do?funcflg=sysLog_list&querylogtype=${param.querylogtype}"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">
								<tr>
									<td align="right">
										日志类型：
									</td>
									<td>
									
										<select id="actionID" name="_action[=]" style="width:127">
											<c:if test="${param.querylogtype=='0'}"> 
											<option value="">查询所有</option>  
											<option value="01">普通日志</option>
											<option value="02">风险控制</option>	
											<option value="03">系统日志</option>	
											</c:if>	
											<c:if test="${param.querylogtype=='1'}"> 
											<option value="">查询所有</option>
											<option value="04">后台管理登陆日志</option>
											<option value="05">加盟商登陆日志</option>
											<option value="06">仓库管理登陆日志</option>	
											</c:if>					
										</select>
										 
									</td>								
								    <td align="right">
										&nbsp;&nbsp;开始日期：
									</td>
									<td>
										<input type="text" id="beginDate" name="_createtime[>=][date]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${today}'/>" title="双击选择日期" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>
									<td align="right">
										&nbsp;&nbsp;结束日期：
									</td>
									<td>
										<input type="text" id="endDate" name="_createtime[<=][datetime]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${today}'/>" title="双击选择日期" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()"/>
									</td>	
									<td align="right">
										&nbsp;&nbsp;<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
									</td>	
									<td align="right">
										&nbsp;&nbsp;<html:reset property="query" style="width:60" styleClass="button"
											onclick="javascript:return reset();">
											重置
										</html:reset>
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
