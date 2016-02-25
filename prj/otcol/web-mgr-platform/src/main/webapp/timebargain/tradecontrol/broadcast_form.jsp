<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<html>
	<head>
		<%@ include file="/timebargain/common/meta.jsp"%>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/Date.js"/>"></script>
		<c:import url="/timebargain/common/dblCustomerID.jsp" />
		<title></title>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    if(broadcastForm.crud.value == "create"){
    	broadcastForm.sendTime.value = "20:00:00";
    }
    //alert('<%=(String) request.getAttribute("userID")%>');
    broadcastForm.author.value = '<%=(String) request.getAttribute("userID")%>';
    broadcastForm.title.focus();
}
//save
function save_onclick()
{
	if (confirm("您确定要提交吗？")) {
	if(broadcastForm.isQryHis.checked == true){
	  	broadcastForm.type.value = "true";
	}else {
	  	broadcastForm.type.value = "false";
	}
	
  	if (broadcastForm.title.value == "") {
    	alert("标题不能为空！");
    	broadcastForm.title.focus();
    	return false;
    }
    if (broadcastForm.content.value == "") {
    	alert("内容不能为空！");
    	broadcastForm.content.focus();
    	return false;
    }
    var contentLength = parseInt(broadcastForm.content.value.length);
    if (contentLength > 256) {
    	alert("内容过长，限制最多输入256个字符！");
    	broadcastForm.content.focus();
    	return false;
    }
  	
    if (broadcastForm.type.value == "false") {
    	if (broadcastForm.status.value == "") {
    		alert("状态不能为空！");
    		broadcastForm.title.focus();
    		return false;
    	}
    }
    
    if (broadcastForm.isQryHis.checked == false) {
    	if (broadcastForm.sendTime.value == "") {
    		alert("发送时间不能为空！");
    		return false;
    	}
    	
    	if (broadcastForm.sendTime.value != "") {
    		if (!isTime(broadcastForm.sendTime.value)) {
    			alert("发送时间格式不正确！");
    			return false;
    		}
    	}
    } 
    broadcastForm.submit();
    broadcastForm.save.disabled = true;
    
  
   }
}

function setCustomer(customerID,customerName)
{
  broadcastForm.customerID.value = customerID;
  customerID_onchange();
}
function customerID_dblclick()
{
  dblCustomerID();
}
function customerID_onchange()
{
  if(trim(broadcastForm.customerID.value) == "")
  {
    return false;
  }
  parent.HiddFrame.location.href = "<c:url value="/timebargain/common/customerQuery/customerQuery.do?funcflg=customerChg&customerID="/>" + broadcastForm.customerID.value +
   "&forwardName=broadcast";
}

//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/tradecontrol/broadcast.do"/>";
}

function isQryHis_onclick()
{
  if(broadcastForm.isQryHis.checked)
  {
    broadcastForm.status.value=0;
    broadcastForm.sendTime.value="";
 	 setReadOnly(broadcastForm.sendTime);
 	 setDisabled(broadcastForm.status);
  	broadcastForm.type.value = "true";
  }
else
  {
  	broadcastForm.sendTime.value="20:00:00";
  	setReadWrite(broadcastForm.sendTime);
    setEnabled(broadcastForm.status);
    broadcastForm.type.value = "false";
  }
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="70%" align="center">
			<tr>
				<td>
					<html:form
						action="/timebargain/tradecontrol/broadcast.do?funcflg=saveWait"
						method="POST" styleClass="form" target="ListFrame">
						<fieldset class="pickList">
							<legend class="common">
								<b> 发广播消息 </b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
										标题：
									</td>
									<td>
										<html:text property="title" maxlength="32" size="52"
											styleClass="text" />
										<span class="req">*</span>
									</td>

								</tr>
								<tr>
									<td align="right">
										内容：
									</td>
									<td>
										<html:textarea property="content" cols="50" rows="5"
											styleClass="text"></html:textarea>
										<span class="req">*</span>
									</td>
								</tr>
								<c:if test="${ param['crud'] eq 'create' }">
									<tr>
										<td align="right">
											交易商代码：
										</td>
										<td>
											<html:textarea property="customerID"  cols="50"
												styleClass="textarea" onchange="customerID_onchange()" />

										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<span class="req">(不填时向所有交易用户发送,多个交易商请以英文状态下半角 ,(逗号)
												进行分隔)</span>
										</td>
									</tr>
								</c:if>
								<tr>
									<td>
										立即发送：
									</td>
									<td>
										<input type="checkbox" name="isQryHis" id="isQryHis"
											onclick="isQryHis_onclick()" value="false"
											class="NormalInput" />
									</td>
								</tr>
								<tr>
									<td>
										状态：
									</td>
									<td>
										<html:select property="status">
											<html:option value="0">有效</html:option>
											<html:option value="1">无效</html:option>
										</html:select>
									</td>
								</tr>
								<tr>
									<td>
										发送时间：
									</td>
									<td>
										<html:text property="sendTime" onkeypress="onlyDate()" maxlength="16" />HH:MM:SS
									</td>
								</tr>


								<tr>
									<td colspan="2" align="center">
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
						<html:hidden property="id" />
						<html:hidden property="author" />
						<c:if test="${ param['crud'] eq 'update' }">
							<html:hidden property="customerID" />
						</c:if>
						<html:hidden property="type" />
						<input type="hidden" name="name" value="" />

					</html:form>
				</td>
			</tr>
		</table>

		<script type="text/javascript"
			src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
