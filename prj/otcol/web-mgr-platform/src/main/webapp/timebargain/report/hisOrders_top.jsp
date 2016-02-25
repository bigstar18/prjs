<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<c:import url="/timebargain/report/customerID_change.jsp"/>
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
  if(reportForm.clearDate.value=="")
  {
    alert("查询时间不能为空！");
    reportForm.clearDate.focus();
    return false;
  }
  if(!isDateFormat(reportForm.clearDate.value))
  {
    alert("查询时间格式不正确！");
    reportForm.clearDate.focus();
    return false;
  }

  wait.style.visibility = "visible";
  reportForm.submit();
  reportForm.query.disabled = true;  
}
//dy_onclick
function dy_onclick()
{
  reportForm.action = "<c:url value="/timebargain/report/report.do?funcflg=printHisOrdersOther"/>";
  reportForm.target = "_blank";
  reportForm.exportTo.value = "pdf";
  reportForm.submit();   
  reportForm.action = "<c:url value="/timebargain/report/report.do?funcflg=listHisOrders"/>";
  reportForm.target = "ListFrame";
}
</script>
	</head>

	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/report/report.do?funcflg=listHisOrders"
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
											&nbsp;&nbsp;交易商ID：
									</td>
									<td>
										<input type="text" id="firmID" name="_a.FirmID[=]" style="width:111" maxlength="16" 
											styleClass="text" />
									</td>
			                        <td align="right">
											交易商名称：
									</td>
									<td>
										<input type="text" id="firmName" name="_m.Name[like]" title="可输入模式匹配符查询" style="width:111" maxlength="32" styleClass="text"/>
									</td>																	
								</tr>
								<tr>
									<td align="right">
										查询时间：
									</td>
									<td>
										<input type="text" id="clearDate" name="_a.ClearDate[=][date]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${today}'/>" title="双击选择日期" styleId="clearDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" />
									</td>
									<td>
										
									</td>
									<td align="right">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										<html:button property="dy" style="width:60" styleClass="button"
											onclick="javascript:return dy_onclick();" disabled="true">
											打印
										</html:button>
									</td>
									<td>
										
									</td>	
																									
								</tr>
							</table>
						</fieldset>
						<input type="hidden" name="exportTo">
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
