<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ include file="/timebargain/widgets/calendar/calendar.jsp" %>


<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<title>
		</title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    
    if(customerForm.crud.value == "create")
    {  
      customerForm.customerID.focus();
    }
    else if(customerForm.crud.value == "update")
    {
      //setReadOnly(customerForm.customerID);   
      customerForm.customerName.focus();
      
      if (customerForm.status.value == "2") {
      	setDisabled(customerForm.groupID);
      	setReadOnly(customerForm.customerID);
      	setReadOnly(customerForm.customerName);
      	setReadOnly(customerForm.phone);
      	setReadOnly(customerForm.address);
      	setReadOnly(customerForm.note);
      	setReadOnly(customerForm.validDate);
      	customerForm.back.disabled = true;
      	customerForm.goback.disabled = false;
      }else{
      	setEnabled(customerForm.status);
      	setEnabled(customerForm.groupID);
      	setReadOnly(customerForm.customerID);
      	setReadWrite(customerForm.customerName);
      	setReadWrite(customerForm.phone);
      	setReadWrite(customerForm.address);
      	setReadWrite(customerForm.note);
      	setReadWrite(customerForm.validDate);
      	customerForm.back.disabled = false;
      	customerForm.goback.disabled = true;
      }
    }
}

function back_onclick(){
	var customerID = customerForm.customerID.value;
	document.location.href = "<c:url value="/timebargain/baseinfo/firm.do?funcflg=back&customerID="/>" + customerID;
	customerForm.back.disabled = true;
    customerForm.goback.disabled = false;
}

function goback_onclick(){
	var customerID = customerForm.customerID.value;
	document.location.href = "<c:url value="/timebargain/baseinfo/firm.do?funcflg=goback&customerID="/>" + customerID;
	customerForm.back.disabled = true;
    customerForm.goback.disabled = false;
}

//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if(!tmp_baseinfo_up)
    {
      baseinfo_onclick();
    }
    if(!tmp_paraminfo_up)
    {
      paraminfo_onclick();
    }
 // if(validateCustomerForm(customerForm))
//  {
    if(customerForm.crud.value == "create")
    {
        if(trim(customerForm.password.value).length < 4)
        {
        	alert("<fmt:message key="customerForm.password.lenmemo"/>");
	        customerForm.password.focus();
	        return false;
        }
        if(trim(customerForm.password.value) != trim(customerForm.confirmPassword.value))
        {
        	alert("<fmt:message key="customerForm.password.comparememo"/>");
	        customerForm.confirmPassword.focus();
	        return false;
        }


            

  
    }
    else if(customerForm.crud.value == "update")
    {
    }    
    
    
    if (customerForm.status.value == "") {
    	alert("状态不能为空！");
    	document.forms(0).status.focus();
    	return false;
    }
    if (customerForm.groupID.value == "") {
    	alert("所属组不能为空！");
    	document.forms(0).groupID.focus();
    	return false;
    }
    
    customerForm.submit();
    customerForm.save.disabled = true;
  //}
	}
    
}
//cancel
function cancel_onclick()
{
   //document.location.href = "<c:url value="/baseinfo/customer.jsp"/>";
   //parent.TopFrame.query_onclick();
   window.history.back(-1);
}
function modifyPwd()
{
   pTop("<c:url value="/timebargain/common/modifyPwd1.jsp?type=customer&prikey="/>" + trim(customerForm.customerID.value),300,190);  
}
//---------------------------start baseinfo-------------------------------
var tmp_baseinfo;
var tmp_baseinfo_up = true;
function baseinfo_onclick()
{
  if (tmp_baseinfo_up)
  {
    tmp_baseinfo_up = false;
    customerForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    if(customerForm.crud.value == "create")
    {
    	customerForm.tmp_password.value = customerForm.password.value;
    	customerForm.tmp_confirmPassword.value = customerForm.confirmPassword.value;
    }
    tmp_baseinfo = baseinfo.innerHTML;
    baseinfo.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    customerForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo.innerHTML = tmp_baseinfo;
    if(customerForm.crud.value == "create")
    {
    	customerForm.password.value = customerForm.tmp_password.value;
    	customerForm.confirmPassword.value = customerForm.tmp_confirmPassword.value;
    }
  }
}
//-----------------------------end baseinfo-----------------------------
//---------------------------start paraminfo-------------------------------
var tmp_paraminfo;
var tmp_paraminfo_up = true;
function paraminfo_onclick()
{
  if (tmp_paraminfo_up)
  {
    tmp_paraminfo_up = false;
    customerForm.paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Down.gif"/>";
    tmp_paraminfo = paraminfo.innerHTML;
    paraminfo.innerHTML = "";
  }
  else
  {
    tmp_paraminfo_up = true;
    customerForm.paraminfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    paraminfo.innerHTML = tmp_paraminfo;
  }
}
//-----------------------------end paraminfo-----------------------------
//---------------------------start mapinfo-------------------------------
var tmp_mapinfo;
var tmp_mapinfo_up = true;

//-----------------------------end mapinfo-----------------------------
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/firm.do?funcflg=save"
						method="POST" styleClass="form" target="MainFrame">
						<fieldset class="pickList" >
							<legend class="common">
								<b>设置交易商信息
								</b>
							</legend>
<table width="100%" border="0" align="center"  class="common" cellpadding="0" cellspacing="2">
<!-- 基本信息 -->
        <tr class="common">
          <td colspan="4">
            <fieldset class="pickList">
              <legend>
                <table cellspacing="0" cellpadding="0" border="0" width="100%" class="common">
                  <col width="55"></col><col></col><col width="6"></col>
                  <tr>
                    <td><b><fmt:message key="customerForm.group.baseinfo"/></b></td>
                    <td><hr width="99%" class="pickList"/></td>
                    <td ><img id="baseinfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo">
<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">
								<tr>																		
									<td align="right">
											交易商ID：
									</td>
									<td>
										<html:text property="customerID" maxlength="16"
											styleClass="text" />
										<span class="req">*</span>
									</td>
									<td align="right">
											交易商名称：
									</td>
									<td>
										<html:text property="customerName" maxlength="32" styleClass="text"/>
										<span class="req">&nbsp;</span>
									</td>
								</tr>	
							<c:if test="${param['crud'] == 'create'}">      
								<tr>								
									
									<td>
										<html:hidden property="password" value="111111" />
										
									</td>
									
									<td>
										<html:hidden property="confirmPassword" value="111111" />
										
									</td>
								</tr>	
								<input type="hidden" name="tmp_password" value=""><input type="hidden" name="tmp_confirmPassword" value="">
							</c:if>	
								<tr>									
										
									<c:if test="${param['crud'] == 'update'}">
										<%
										
											String type12 = (String)request.getAttribute("type9");
											String status = (String)request.getAttribute("status");
											String name = (String)request.getAttribute("name");
											if ("2".equals(type12)) {
												%>
													<td align="right">
											<fmt:message key="customerForm.Status" />：
										</td>
													<td><%=name%>
													<html:hidden property="status" value="<%=status%>"/></td>
												<%
											}else {
												%>
										<td align="right">
											<fmt:message key="customerForm.Status" />：
										</td>
									<td>
										<html:select property="status" style="width:150">
										   <html:option value=""></html:option>
				                           <html:option value="0"><fmt:message key="customerForm.Status.option.zc"/></html:option>
					                       <html:option value="1"><fmt:message key="customerForm.Status.option.jzjy"/></html:option>
			                            </html:select> <span class="req">*</span>
									</td>
												<%
											}
										%>
										
									</c:if>
									<c:if test="${param['crud'] == 'create'}">
										<td align="right">
											<fmt:message key="customerForm.Status" />：
										</td>
									<td>
										<html:select property="status" style="width:150">
										   <html:option value=""></html:option>
				                           <html:option value="0"><fmt:message key="customerForm.Status.option.zc"/></html:option>
					                       <html:option value="1"><fmt:message key="customerForm.Status.option.jzjy"/></html:option>
			                            </html:select> <span class="req">*</span>
									</td>
									</c:if>
									
									
									<td align="right">
											<fmt:message key="customerForm.GroupName" />：
									</td>
									<td>
										<html:select property="groupID"  style="width:150">
                                          <html:options collection="customerGroupSelect" property="value" labelProperty="label"/>
                                        </html:select> <span class="req">*</span>
									</td>								
								</tr>																					
								<tr>
									<td align="right">
											<fmt:message key="customerForm.Phone" />：
									</td>
									<td>
										<html:text property="phone" maxlength="64" styleClass="text" />
									</td>
									
									<td align="right">
											有效期：
									</td>
									<td>
										<html:text  property="validDate" styleId="settleDate5" maxlength="64"   styleClass="text" /><input type="button" class="calendarImgButton" style="width:16" onclick="return showCalendar('settleDate5','%Y-%m-%d', '24', true);">
									</td>
									
								</tr>
								<tr width="500">
									<td align="right">
											<fmt:message key="customerForm.Address" />：
									</td>
									<td colspan="3">
										<html:text property="address" maxlength="64" styleClass="text" size="61"/>
									</td>
								</tr>
								<tr>
									<td align="right">
											<fmt:message key="customerForm.Note" />：
									</td>
									<td colspan="3">
										<html:textarea property="note" rows="3" cols="55" style="width:437" styleClass="text" />
									</td>
								</tr>
</table >
</span>
            </fieldset>
          </td>
        </tr>					
      				
								<tr>
									<td colspan="4" height="3">	
									
								</td>
								</tr>																																											
								<tr>
									<td colspan="4" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											<fmt:message key="button.save" />
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											<fmt:message key="button.return" />
										</html:button>
										
									</td>
								</tr>
							</table>
						</fieldset>
						
						<html:hidden property="crud" />
						<html:hidden property="oldPassword" />
						<c:if test="${param['crud'] == 'update'}">
							<html:button property="back" styleClass="button"
											onclick="javascript:return back_onclick();">
											退市
										</html:button>
							<html:button property="goback" styleClass="button"
											onclick="javascript:return goback_onclick();">
											正常
										</html:button>	
						</c:if>
					</html:form>
				</td>
			</tr>
		</table>

		<html:javascript formName="customerForm" cdata="false"
			dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
