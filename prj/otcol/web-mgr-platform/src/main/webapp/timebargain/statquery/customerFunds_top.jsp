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
  top.MainFrame.location.href = "<c:url value="/timebargain/statquery/customerFunds.jsp"/>";
}
function isQryHis_onclick()
{
  if(statQueryForm.isQryHis.checked)
  {
    setReadWrite(statQueryForm.beginDate);
    setReadWrite(statQueryForm.endDate);
    statQueryForm.isQryHis.value = true;
  }
  else
  {
    setReadOnly(statQueryForm.beginDate);
    setReadOnly(statQueryForm.endDate);
    statQueryForm.isQryHis.value = false;
  }
  statQueryForm.isQryHisHidd.value = statQueryForm.isQryHis.value;
}
</script>
	</head>

	<body leftmargin="6" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<c:import url="/timebargain/common/waitbar.jsp" charEncoding="GBK"/>
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=listCustomerFunds"
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
											&nbsp;&nbsp;交易商代码：
									</td>
									<td>
										<input type="text" id="FirmID" name="_a.FirmID[=]" style="width:111" maxlength="16" 
											styleClass="text" onkeypress="onlyNumberAndCharInput()" />
									</td>
			                        <td align="right">
											&nbsp;&nbsp;交易商名称：
									</td>
									<td>
										<input type="text" id="FirmName" name="_m.name[like]" title="可输入模式匹配符查询" style="width:111" maxlength="32" styleClass="text" onkeypress="onlyNumberAndCharInput()"/>
									</td>
									<td align="right">
											&nbsp;&nbsp;交易商类别：
									</td>
									<td>
										<select name="_m.firmCategoryId[=]" id="firmCategoryId">
											<option value="">全部</option>
											<c:forEach items="${firmCategoryList }" var="result">
												<option value="${result.id}">${result.name}</option>
											</c:forEach>
										</select>
									</td>
			                        <td align="right">
										&nbsp;<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>
										
									</td>																																		
								</tr>
					<!--  			<tr>
									<td></td>
									<td>
			                            <input type="checkbox" name="isQryHis" id="isQryHis" onclick="isQryHis_onclick()" value="false" class="NormalInput"/><label for="isQryHis" class="hand"><fmt:message key="statQuery.query.history"/></label>
										<input type="hidden" id="isQryHisHidd" name="_isQryHis[=]" value="false">
			                        </td>
									
									<td align="right">									
										<fmt:message key="statQuery.beginDate" />：
									</td>
									<td>
										<input type="text" id="beginDate" name="_a.ClearDate[>=][date]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${lastDay}'/>" title="<fmt:message key="title.dblclick.seldate" />" styleId="beginDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" readonly="true" class="ReadOnlyString"/>
									</td>
									<td align="right">
										<fmt:message key="statQuery.endDate" />：
									</td>
									<td>
										<input type="text" id="endDate" name="_a.ClearDate[<=][datetime]" ondblclick="if(!this.readOnly){setRq(this);}" value="<c:out value='${lastDay}'/>" title="<fmt:message key="title.dblclick.seldate" />" styleId="endDate" maxlength="10" style="ime-mode:disabled;width:111" onkeypress="return numberPass()" readonly="true" class="ReadOnlyString"/>
									</td>
									<td align="right">
										&nbsp;<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											<fmt:message key="button.query" />
										</html:button>
										
									</td>
									<td>
										
									</td>																	
								</tr>	-->
							</table>
						</fieldset>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
