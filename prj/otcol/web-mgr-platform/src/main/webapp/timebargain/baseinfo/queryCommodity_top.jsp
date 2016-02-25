<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css" href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
	commodityForm.oper.value = '<c:out value="${param['oper']}"/>';
    highlightFormElements();
    query_onclick();
}
//query_onclick
function query_onclick()
{
  if (commodityForm.year.value != "") {
  	  var Date1 = new Date(commodityForm.year.value,0,0); 
  	  //alert(Date1.getFullYear()+1); 
 	  if (Date1.getFullYear()+1 != commodityForm.year.value || commodityForm.year.value.length != 4) {
  	  	alert("年份格式不正确！");
  	  	return false;
    }
  }

  if (commodityForm.month.value != "") {
  	var Date1 = new Date(0,commodityForm.month.value,0);
  	var month = commodityForm.month.value - 1;
  	if (Date1.getMonth()+1 != commodityForm.month.value || commodityForm.month.value.length != 2) {
  		alert("月份格式不正确！");
  		return false;
 	 }
  }
  commodityForm.submit();
}

function reset_onclick(){
	commodityForm.year.value = "";
	commodityForm.month.value = "";
}

</script>
	</head>

	<body leftmargin="13" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="100%" width="100%" cellpadding="0" cellspacing="0" class="common" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/queryCommodity.do?funcflg=queryCommodity"
						method="POST" styleClass="form" target="ListFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>查询条件
								</b>
							</legend>
							<table border="0"  cellpadding="0" cellspacing="0"
								class="common">

								<tr>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td align="right">年：</td>     
						            <td>  
										<html:text property="year" styleClass="text" maxlength="16" size="12"onkeypress="notSpace()"></html:text>
									</td>
									
									<td>&nbsp;</td><td>&nbsp;</td>
									<td>&nbsp;</td><td>&nbsp;</td>
									<td align="right">月：</td>
                                    <td>
                                        <html:text property="month" styleClass="text" maxlength="16" size="12" onkeypress="notSpace()"></html:text>
                                    </td>		
                                    <td>&nbsp;</td><td>&nbsp;</td>
									<td align="right">
										<html:button property="query" style="width:60" styleClass="button"
											onclick="javascript:return query_onclick();">
											查询
										</html:button>&nbsp;&nbsp;
										<html:button property="reset" style="width:60" styleClass="button"
											onclick="javascript:reset_onclick();">
											重置
										</html:button>
									</td>																	
								</tr>
							</table>
						</fieldset>
						<html:hidden property="oper"/>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
