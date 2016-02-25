<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<c:import url="/timebargain/common/dblCustomerID.jsp"/>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    tradePropsForm.moduleName.value = "<c:out value="${param['moduleName']}"/>";
    tradePropsForm.customerID.focus();
}
//save
function save_onclick()
{
    if(trim(tradePropsForm.customerID.value) == "")
    {
        alert("<fmt:message key="customerForm.CustomerID"/><fmt:message key="prompt.required"/>");
        tradePropsForm.customerID.focus();
		return false;
    }
    if(trim(tradePropsForm.customerName.value) == "")
    {
		return false;
    }    
	<c:choose>
		<c:when test="${param['moduleName'] == '1'}">
		    var balance = trim(tradePropsForm.balance.value) == "" ? "0" : trim(tradePropsForm.balance.value);
		    var addInFund = trim(tradePropsForm.addInFund.value) == "" ? "0" : trim(tradePropsForm.addInFund.value);
		    var addOutFund = trim(tradePropsForm.addOutFund.value) == "" ? "0" : trim(tradePropsForm.addOutFund.value);
            if(parseFloat(balance) + parseFloat(addInFund) - parseFloat(addOutFund) < 0)
            {
                alert("<fmt:message key="tradePropsForm.balance.prompt"/>");
                tradePropsForm.addInFund.focus();
		        return false;
            }		
        </c:when>
		<c:when test="${param['moduleName'] == '2'}">
            if(trim(tradePropsForm.addVirtualFunds.value) == "")
            {
                alert("<fmt:message key="tradePropsForm.addVirtualFunds"/><fmt:message key="prompt.required"/>");
                tradePropsForm.addVirtualFunds.focus();
		        return false;
            }		
        </c:when>
		<c:when test="${param['moduleName'] == '3'}">
            if(trim(tradePropsForm.newMaxOverdraft.value) == "")
            {
                alert("<fmt:message key="tradePropsForm.newMaxOverdraft"/><fmt:message key="prompt.required"/>");
                tradePropsForm.newMaxOverdraft.focus();
		        return false;
            }			
        </c:when>
		<c:when test="${param['moduleName'] == '4'}">
            if(trim(tradePropsForm.newMinClearDeposit.value) == "")
            {
                alert("<fmt:message key="tradePropsForm.newMinClearDeposit"/><fmt:message key="prompt.required"/>");
                tradePropsForm.newMinClearDeposit.focus();
		        return false;
            }			
        </c:when>
		<c:when test="${param['moduleName'] == '5'}">
            if(trim(tradePropsForm.newMaxHoldQty.value) == "")
            {
                alert("<fmt:message key="tradePropsForm.newMaxHoldQty"/><fmt:message key="prompt.required"/>");
                tradePropsForm.newMaxHoldQty.focus();
		        return false;
            }			
        </c:when>
		<c:otherwise></c:otherwise>
	</c:choose>

    tradePropsForm.submit();
    tradePropsForm.save.disabled = true;
}

function set_save_disabled(bVal)
{
  tradePropsForm.save.disabled = bVal;
  clearForm(tradePropsForm);
  tradePropsForm.customerID.focus();
}

function customerID_dblclick()
{
  dblCustomerID();
}

function setCustomer(customerID,customerName)
{
  tradePropsForm.customerID.value = customerID;
  tradePropsForm.customerName.value = customerName;
  customerID_onchange();
}

function customerID_onchange()
{
  if(trim(tradePropsForm.customerID.value) == "")
  {
    tradePropsForm.customerName.value = "";
    return false;
  }
  parent.HiddFrame.location.href = "<c:url value="/timebargain/common/customerQuery/customerQuery.do?funcflg=customerChg&customerID="/>" + tradePropsForm.customerID.value +
    "&forwardName=tradeProps&moduleName=" + tradePropsForm.moduleName.value;
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="50%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/riskcontrol/tradeProps.do?funcflg=save&retLocation=parent.ListFrame.set_save_disabled(false)"
						method="POST" styleClass="form" target="HiddFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>
								  <c:choose>
								    <c:when test="${param['moduleName'] == '1'}"><fmt:message key="tradePropsForm.title1" /></c:when>
								    <c:when test="${param['moduleName'] == '2'}"><fmt:message key="tradePropsForm.title2" /></c:when>
								    <c:when test="${param['moduleName'] == '3'}"><fmt:message key="tradePropsForm.title3" /></c:when>
								    <c:when test="${param['moduleName'] == '4'}"><fmt:message key="tradePropsForm.title4" /></c:when>
								    <c:when test="${param['moduleName'] == '5'}"><fmt:message key="tradePropsForm.title5" /></c:when>
								    <c:otherwise></c:otherwise>
								  </c:choose>
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">

								<tr>
									<td align="right">
											<fmt:message key="customerForm.CustomerID" />
									</td>
									<td>
										<html:text property="customerID" maxlength="16" title="Ë«»÷²éÑ¯"
											styleClass="text" onchange="customerID_onchange()" ondblclick="if(!this.readOnly){customerID_dblclick();}"/>
										<span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td align="right">
											<fmt:message key="customerForm.CustomerName" />
									</td>
									<td>
										<input type="text" name="customerName" readOnly class="ReadOnlyString"/>
									</td>
								</tr>
								
								  <c:choose>
								    <c:when test="${param['moduleName'] == '1'}">
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.balance" />
									</td>
									<td>
										<input type="text" name="balance" readOnly class="ReadOnlyNumber"/>
									</td>
								</tr>	
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.InFund" />
									</td>
									<td>
										<input type="text" name="inFund" readOnly class="ReadOnlyNumber"/>
									</td>
								</tr>															
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.addInFund" />
									</td>
									<td>
										<input type="text" name="addInFund" maxlength="15" onKeyPress="return onlyNumberInput()" style="ime-mode:disabled" class="Number"/>
									</td>
								</tr>
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.OutFund" />
									</td>
									<td>
										<input type="text" name="outFund" readOnly class="ReadOnlyNumber"/>
									</td>
								</tr>
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.addOutFund" />
									</td>
									<td>
										<input type="text" name="addOutFund" maxlength="15" onKeyPress="return onlyNumberInput()" style="ime-mode:disabled" class="Number"/>
									</td>
								</tr>																
								    </c:when>
								    <c:when test="${param['moduleName'] == '2'}">
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.virtualFunds" />
									</td>
									<td>
										<input type="text" name="virtualFunds" readOnly class="ReadOnlyNumber"/>
									</td>
								</tr>								
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.addVirtualFunds" />
									</td>
									<td>
										<input type="text" name="addVirtualFunds" maxlength="15" onKeyPress="return onlyNumberInput()" style="ime-mode:disabled" class="Number"/>
										<span class="req">*</span>
									</td>
								</tr>
								    </c:when>
								    <c:when test="${param['moduleName'] == '3'}">
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.maxOverdraft" />
									</td>
									<td>
										<html:text property="maxOverdraft" readonly="true" styleClass="ReadOnlyNumber"/>
									</td>
								</tr>								
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.newMaxOverdraft" />
									</td>
									<td>
										<input type="text" name="newMaxOverdraft" maxlength="15" onKeyPress="return onlyNumberInput()" style="ime-mode:disabled" class="Number"/>
										<span class="req">*</span>
									</td>
								</tr>
								    </c:when>
								    <c:when test="${param['moduleName'] == '4'}">
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.minClearDeposit" />
									</td>
									<td>
										<html:text property="minClearDeposit" readonly="true" styleClass="ReadOnlyNumber"/>
									</td>
								</tr>								
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.newMinClearDeposit" />
									</td>
									<td>
										<input type="text" name="newMinClearDeposit" maxlength="15" onKeyPress="return onlyNumberInput()" style="ime-mode:disabled" class="Number"/>
										<span class="req">*</span>
									</td>
								</tr>
								    </c:when>
								    <c:when test="${param['moduleName'] == '5'}">
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.maxHoldQty" />
									</td>
									<td>
										<html:text property="maxHoldQty" readonly="true" styleClass="ReadOnlyNumber"/>
									</td>
								</tr>								
								<tr>
									<td align="right">
											<fmt:message key="tradePropsForm.newMaxHoldQty" />
									</td>
									<td>
										<input type="text" name="newMaxHoldQty" maxlength="10" onKeyPress="return onlyNumberInput()" style="ime-mode:disabled" class="Number"/>
										<span class="req">*</span>
									</td>
								</tr>
								    </c:when>
								    <c:otherwise></c:otherwise>
								  </c:choose>								

								
																																											
								<tr>
									<td colspan="2" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											<fmt:message key="button.save" />
										</html:button>
										<html:button  property="reset" onclick="javascript:clearForm(tradePropsForm);tradePropsForm.customerID.focus();" styleClass="button"><fmt:message key="button.reset"/></html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="moduleName" />
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
