<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<c:import url="/timebargain/statquery/customerID_change.jsp"/>
		<c:import url="/timebargain/common/date.jsp"/>
		<c:import url="/common/getCommodityIdListForJs.jsp"/>
		<script language="VBScript" src="<c:url value="/timebargain/scripts/vbfunction.vbs"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
 //   statQueryForm.groupID.focus();
    query_onclick();
    initPar('uni_Cmdty_Code');
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
  top.MainFrame.location.href = "<c:url value="/timebargain/statquery/holdPosition.jsp"/>";
}
function isQryHis_onclick()
{
  if(statQueryForm.isQryHis.checked)
  {
    setReadWrite(statQueryForm.beginDate);
    statQueryForm.isQryHis.value = true;
  }
  else
  {
    setReadOnly(statQueryForm.beginDate);
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
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=listBrokerFund"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
							<table border="0" align="center" cellpadding="0" cellspacing="0"
								class="common">

								<tr>
								<td></td>
									<td align="right">
											加盟商代码：
									</td>
									<td>
										<input type="text" id="BrokerID" name="_br1.BrokerID[=]" style="width:111" maxlength="16" title=""
											styleClass="text" />
									</td>
									<td align="right">
											&nbsp;&nbsp;加盟商名称：
									</td>
									<td>
										<input type="text" id="Name" name="_br1.name[like]" style="width:111" maxlength="16" title=""
											styleClass="text" />
									</td>
									<td align="left">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											&nbsp;&nbsp;执行查询
										</html:button>
										
									</td>
										<td></td>										
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
