<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>

<%
	pageContext.setAttribute("today",gnnt.MEBS.timebargain.manage.util.DateUtil.getCurDate());
%>
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

  wait.style.visibility = "visible";
  agencyForm.submit();
  agencyForm.query.disabled = true;  
}
//requery_onclick

//del_onclick

</script>
	</head>

	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/onlinebrowse/onlinebrowse.do?funcflg=traderLog_list"
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
										交易员代码：
									</td>
									<td>
										<input type="text" id="traderID" name="_traderID[=]" style="width:111"  />
									</td>
									
									<td align="right">
										日志类型：
									</td>
									<td>
										<select id="oprType" name="_oprType[=]" style="width:111">
											<option value=""></option>
											<option value="1">登录</option>
											<option value="2">注销</option>	
										</select>
									</td>
									
									<td align="right">
										&nbsp;&nbsp;开始日期：
									</td>
									<td>
										<input type="text" id="beginDate" name="_occurTime[>=][date]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${today}'/>" title="双击选择日期" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>
									<td align="right">
										&nbsp;&nbsp;结束日期：
									</td>
									<td>
										<input type="text" id="endDate" name="_occurTime[<=][datetime]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${today}'/>" title="双击选择日期" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()"/>
									</td>
									<td align="right">
										&nbsp;&nbsp;<html:button property="query" style="width:60" styleClass="button"
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
