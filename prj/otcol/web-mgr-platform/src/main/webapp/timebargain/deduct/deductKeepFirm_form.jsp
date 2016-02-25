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
			       alert("ǿ�����ڲ���Ϊ�գ�");
			       deductForm.deductDate.focus();
			 	   return false;
			   }
			   if(trim(deductForm.commodityID.value) == "") {
			       alert("��Ʒ���벻��Ϊ�գ�");
			       deductForm.commodityID.focus();
			 	   return false;
			   }
			   if(trim(deductForm.customerID.value) == "") {
			       alert("���׿ͻ����벻��Ϊ�գ�");
			       deductForm.customerID.focus();
			 	   return false;
			   }
			   if(trim(deductForm.BS_Flag.value) == "") {
			       alert("������־����Ϊ�գ�");
			       deductForm.BS_Flag.focus();
			 	   return false;
			   }
			   if(trim(deductForm.keepQty.value) == "") {
			       alert("������������Ϊ�գ�");
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
					<fieldset class="pickList" ><legend class="common"><b>ǿ������������</b></legend>
						<table border="0" align="center" cellpadding="5" cellspacing="5" class="common">
							<tr>
								<td align="right">ǿ�����ڣ�</td>
								<td>	
									<input type="text" id="deductDate" name="deductDate" ondblclick="setRq(this);" value="<c:out value='<%=date%>'/>" title="˫��ѡ������" maxlength="10" style="ime-mode:disabled;width:111" />
									<span class="req">*</span>
								</td>
								<td align="right">��Ʒ���룺</td>
								<td>
									<html:text property="commodityID" value="<%=code%>" styleClass="text" maxlength="16" size="12"></html:text>
									<span class="req">*</span>
								</td>
								<td align="right">���׿ͻ����룺</td>
								<td>
									<html:text property="customerID" styleClass="text" maxlength="16" size="12"></html:text>
									<span class="req">*</span>
								</td>
							</tr>
							<tr>
								<td align="right">������־��</td>
								<td>
									<html:select property="BS_Flag" style="width:95" >
										<html:option value=""></html:option>
				                        <html:option value="1">��</html:option>
					                    <html:option value="2">��</html:option>
			                  	 	</html:select>
									<span class="req">*</span>
								</td>
								<td align="right">����������</td>
								<td>
									<html:text property="keepQty" onkeypress="return numberPass()" styleClass="text" maxlength="16" size="12"></html:text>
								</td>
								<td>&nbsp;</td>
								<td  align="center">
									<html:button property="save" styleClass="button" onclick="javascript:return save_onclick();">���</html:button>
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
