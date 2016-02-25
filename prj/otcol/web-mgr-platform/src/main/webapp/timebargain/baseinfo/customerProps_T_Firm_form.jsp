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
    setReadOnly(tradePropsForm.customerID);
    tradePropsForm.maxHoldQty.focus();
    
    if (tradePropsForm.maxHoldQty.value == "-1") {
      	changeManner101(2);
    }else {
      	changeManner101(1);
    }
    setReadOnly(tradePropsForm.maxOverdraft);
    setReadOnly(tradePropsForm.virtualFunds);
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if (tradePropsForm.maxHoldQty.value < -1 || tradePropsForm.maxHoldQty.value > 9999999999) {
			alert("最大订货总量不能在 -1 和 9999999999 之外！");
		}
		if (tradePropsForm.customerID.value == "") {
			alert("交易商代码不能为空！");
			return false;
		}
		if (tradePropsForm.maxHoldQty.value == "") {
			alert("最大订货总量不能为空！");
			return false;
		}
		if (tradePropsForm.minClearDeposit.value == "") {
			alert("最低结算准备金不能为空！");
			return false;
		}
		if (tradePropsForm.maxOverdraft.value == "") {
			alert("质押资金不能为空！");
			return false;
		}
		//if (tradePropsForm.virtualFunds.value == "") {
		//	alert("虚拟资金不能为空！");
		//	return false;
		//}
		if (tradePropsForm.type101[1].checked == true) {
			if (tradePropsForm.maxHoldQty.value < 0) {
				alert("最大订货总量不能为负！");
				return false;
			}
		}
    	tradePropsForm.submit();
    	tradePropsForm.save.disabled = true;
	}
  
}


function set_save_disabled(bVal)
{
  tradePropsForm.save.disabled = bVal;
  //clearForm(tradePropsForm);
  tradePropsForm.maxHoldQty.focus();
}

 function changeManner101(id){
 	if (id == "1") {
 		setReadWrite(tradePropsForm.maxHoldQty);
 	}
 	if (id == "2") {
 		tradePropsForm.maxHoldQty.value = "-1";
 		setReadOnly(tradePropsForm.maxHoldQty);
 	}
 }
 
 function cancle_onclick(){
 	document.location.href = "<c:url value="/timebargain/menu/Props.do"/>";
 }

</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="55%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/tradePropsThree.do?funcflg=saveTFirm"
						method="POST" styleClass="form" target="mainFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  设置交易商交易属性
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											交易商代码
									</td>
									<td>
										<html:text property="customerID" maxlength="10"  style="ime-mode:disabled" />
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<%
									String typeMaxHoldQty = (String)request.getAttribute("typeMaxHoldQty");
								%>
								<tr>
									<td align="right">
											最大订货总量 <input type="radio" name="type101" value="2" onclick="changeManner101(2);" <%if("2".equals(typeMaxHoldQty)){out.println("checked");} %> style="border:0px;">不限制
													 <input type="radio" name="type101" value="1" onclick="changeManner101(1);" <%if("1".equals(typeMaxHoldQty)){out.println("checked");} %> style="border:0px;">限制
									</td>
									<td>
										<html:text property="maxHoldQty" maxlength="10" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>
								<tr>
									<td align="right">
											最低结算准备金
									</td>
									<td>
										<html:text property="minClearDeposit" maxlength="13" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>								
								<tr>
									<td align="right">
											质押资金
									</td>
									<td>
										<html:text property="maxOverdraft" maxlength="15" onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
										<span class="req">*</span>
									</td>
									<td></td>
								</tr>  
								<!--去掉原虚拟资金 -->
								 <html:hidden property="virtualFunds"  onkeypress="return onlyNumberInput()" style="ime-mode:disabled" styleClass="Number"/>
																																																	
								<tr>
									<td colspan="3" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>
										<html:reset  property="reset" onclick="javascript:tradePropsForm.maxHoldQty.focus();" styleClass="button">重置</html:reset>
										<html:button property="cancle" styleClass="button"
											onclick="javascript:return cancle_onclick();">
											返回
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud"/>
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
