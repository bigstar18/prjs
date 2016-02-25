<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
	<%
		String date = request.getParameter("date");
		String code = request.getParameter("code");
		String id = request.getParameter("id");
	%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
			<%@ include file="/timebargain/widgets/calendar/calendar.jsp" %>	
		<title>
		</title>
		<script type="text/javascript"> 
			function window_onload() {
			    highlightFormElements();
			    setReadOnly(deductForm.deductDate);
			    setReadOnly(deductForm.commodityID);
			    
			}
			//save
			function save_onclick() {
			   if(trim(deductForm.deductDate.value) == "") {
			       alert("强减日期不能为空！");
			       deductForm.deductDate.focus();
			 	   return false;
			   }
			   if(trim(deductForm.commodityID.value) == "") {
			       alert("商品代码不能为空！");
			       deductForm.commodityID.focus();
			 	   return false;
			   }
			   if(trim(deductForm.customerID.value) == "") {
			       alert("交易客户代码不能为空！");
			       deductForm.customerID.focus();
			 	   return false;
			   }
			   if(trim(deductForm.BS_Flag.value) == "") {
			       alert("买卖标志不能为空！");
			       deductForm.BS_Flag.focus();
			 	   return false;
			   }
			   if(trim(deductForm.keepQty.value) == "") {
			       alert("保留数量不能为空！");
			       deductForm.keepQty.focus();
			 	   return false;
			   }
			   deductForm.submit();	
			}
			//cancel
			function cancel_onclick() {
			   window.history.back(-1);
			}
			
			function setRq(obj) {
			  obj.value = pTop("<c:url value="/timebargain/common/calendar.htm"/>",222,252);  
			}
		</script>
	</head>
	<body leftmargin="11" topmargin="0" onLoad="return window_onload()"	onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="170" width="780" align="left">
			<tr>
				<td>
					<html:form action="/timebargain/deduct/deduct.do?funcflg=saveKeepFirmUpdate" method="POST" styleClass="form" target="ListFrame">
					<fieldset class="pickList" ><legend class="common"><b>强减保留交易商</b></legend>
						<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
							<tr>
								<td align="right">强减日期：</td>
								<td>	
									<input type="text" id="deductDate" name="deductDate" ondblclick="setRq(this);" value="<c:out value='<%=date%>'/>" title="双击选择日期" maxlength="10" style="ime-mode:disabled;width:111" />
									<span class="req">*</span>
								</td>
								<td align="right">商品代码：</td>
								<td>
									<html:text property="commodityID" value="<%=code%>" styleClass="text" maxlength="16" size="12"></html:text>
									<span class="req">*</span>
								</td>
								<td align="right">交易客户代码：</td>
								<td>
									<html:text property="customerID" styleClass="text" maxlength="16" size="12"></html:text>
									<span class="req">*</span>
								</td>
							</tr>
							<tr>
								<td align="right">买卖标志：</td>
								<td>
									<html:select property="BS_Flag" style="width:95" >
										<html:option value=""></html:option>
				                        <html:option value="1">买</html:option>
					                    <html:option value="2">卖</html:option>
			                  	 	</html:select>
									<span class="req">*</span>
								</td>
								<td align="right">保留数量：</td>
								<td>
									<html:text property="keepQty" onkeypress="return numberPass()" styleClass="text" maxlength="16" size="12"></html:text>
								</td>
								<td>&nbsp;</td>
								<td  align="center">
									<html:button property="save" styleClass="button" onclick="javascript:return save_onclick();">添加</html:button>
								</td>
							</tr>
						</table>
					</fieldset>
					<html:hidden property="crud"/>
					<html:hidden property="deductID" value="<%=id%>"/>
					</html:form>
				</td>
			</tr>
		</table>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
