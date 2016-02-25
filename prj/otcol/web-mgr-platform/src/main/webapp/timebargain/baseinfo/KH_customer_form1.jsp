<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%
	//int marketLen = ((java.util.List)request.getAttribute("marketSelect")).size();
%>

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
      customerForm.status.focus();
    }
    else if(customerForm.crud.value == "update")
    {
    	customerForm.status.focus();

    }
	
}

//仅输入数字和字母和逗号
function suffixNamePress()
{
  if (event.keyCode == 44 || event.keyCode == 13 || (event.keyCode>=48 && event.keyCode<=57) )  //|| (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122)
  {
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}

//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
		if(!tmp_baseinfo_up)
    {
      baseinfo_onclick();
    }
   
	if (customerForm.status.value == "") {
		alert("状态不能为空！");
		return false;
	}
   
    customerForm.submit();
    customerForm.save.disabled = true;

	}
    
}
//cancel
function cancel_onclick()
{
   //window.history.back(-1);
   //parent.TopFrame.query_onclick();
   //alert(customerForm);
   document.location.href = "<c:url value="/timebargain/baseinfo/firm.do?crud=searchKH&funcflg=searchKH&firmID="/>" + customerForm.firmID.value;
}
//function modifyPwd()
//{
 //  pTop("<c:url value="/common/modifyPwd1.jsp?type=customer&prikey="/>" + trim(customerForm.customerID.value),300,190);  
//}
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
    	//customerForm.tmp_password.value = customerForm.password.value;
    	//customerForm.tmp_confirmPassword.value = customerForm.confirmPassword.value;
    }
    tmp_baseinfo = baseinfo.innerHTML;
    baseinfo.innerHTML = "";
  }
  else
  {
    tmp_baseinfo_up = true;
    customerForm.baseinfo_img.src = "<c:url value="/timebargain/images/ctl_detail_Up.gif"/>";
    baseinfo.innerHTML = tmp_baseinfo;
  //  if(customerForm.crud.value == "create")
  //  {
  //  	customerForm.password.value = customerForm.tmp_password.value;
  //  	customerForm.confirmPassword.value = customerForm.tmp_confirmPassword.value;
   // }
  }
}
//-----------------------------end baseinfo-----------------------------




</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/customer.do?funcflg=save"
						method="POST" styleClass="form" >
						<fieldset class="pickList" >
							<legend class="common">
								<b>二级客户代码维护
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
                    <td><b>基本信息</b></td>
                    <td><hr width="99%" class="pickList"/></td>
                    <td ><img id="baseinfo_img" src="<c:url value="/timebargain/images/ctl_detail_Up.gif"/>" style="cursor:hand" onclick="javascript:baseinfo_onclick()"/></td>
                  </tr>
                </table>
              </legend>
<span id="baseinfo">
<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">
									
							
								<tr>									
									<td align="right" width="200">
											状态：
									</td>
									<td>
										<html:select property="status" style="width:150">
										   <html:option value=""></html:option>
				                           <html:option value="0">正常</html:option>
					                       <html:option value="1">禁止交易</html:option>
					             
			                            </html:select> <span class="req">*</span>
									</td>	
														
								</tr>
								<c:if test="${param['crud'] == 'create'}">  
								<tr>
									<td align="right" >
											二级客户代码：
									</td>
									<td colspan="3">
										<html:textarea property="code" rows="3" cols="55" onkeypress="return suffixNamePress()" style="width:150" styleClass="text" />(格式逗号分隔，如01，02，99)
									</td>
								</tr>
								
								
								<tr>
								<td width="150"></td>
            						<td align="left">
         								 起始&nbsp;&nbsp;<html:text property="startCode" maxlength="2" onkeypress="return onlyNumberInput()" style="ime-mode:disabled;width:20;"></html:text>&nbsp;&nbsp;结束&nbsp;&nbsp;<html:text property="endCode" maxlength="2" onkeypress="return onlyNumberInput()" style="ime-mode:disabled;width:20;"></html:text>
            						</td>
        						</tr>
								</c:if>																					
						
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
											提交
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
										
									</td>
								</tr>
							</table>
						</fieldset>
						<html:hidden property="crud" />
						<html:hidden property="oldPassword" />
						<html:hidden property="customerID" />
						<html:hidden property="firmID" />
					</html:form>
				</td>
			</tr>
		</table>
		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
