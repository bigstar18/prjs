<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    setReadOnly(statQueryForm.billID);
    setReadOnly(statQueryForm.billFund);
    setReadOnly(statQueryForm.firmID);
    setReadOnly(statQueryForm.commodityName);
    setReadOnly(statQueryForm.quantity);
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
	
		if (statQueryForm.billID.value == "") {
			alert("仓单号不能为空！");
			return false;
		}
		if (statQueryForm.billFund.value == "") {
			alert("仓单金额不能为空！");
			return false;
		}
		if (statQueryForm.firmID.value == "") {
			alert("交易商代码不能为空！");
			return false;
		}
		if (statQueryForm.commodityName.value == "") {
			alert("品种名称不能为空！");
			return false;
		}
		if (statQueryForm.quantity.value == "") {
			alert("质押数量不能为空！");
			return false;
		}
	
    	statQueryForm.submit();
    	statQueryForm.save.disabled = true;
	}
  
}


function cancle_onclick(){
	parent.location.href = "<c:url value="/timebargain/menu/Pledge.do"/>";
}


</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="55%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/statquery/statQuery.do?funcflg=savePledge"
						method="POST" styleClass="form" target="HiddFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  设置质押资金
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											仓单号：
									</td>
									<td>
										<html:text property="billID" maxlength="10"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								
								<tr>
									<td align="right">
											品种名称：
									</td>
									<td>
										<html:text property="commodityName" maxlength="10"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											质押数量：
									</td>
									<td>
										<html:text property="quantity" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											交易商代码：
									</td>
									<td>
										<html:text property="firmID" maxlength="10"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>	
								<tr>
									<td align="right">
											质押金额：
									</td>
									<td>
										<html:text property="billFund" maxlength="10" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
															
								
								
																																														
								<tr>
									<td colspan="3" align="center">
										
										<html:button property="save" styleClass="button"
											onclick="javascript:return cancle_onclick();">
											返回
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud"/>
						<html:hidden property="id"/>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
