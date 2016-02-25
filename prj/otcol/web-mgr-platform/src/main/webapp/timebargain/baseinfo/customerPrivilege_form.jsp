<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.util.SysData" %>
<%@ page import="gnnt.MEBS.timebargain.manage.model.*" %>
<%
		LookupManager lookupMgr = (LookupManager)SysData.getBean("lookupManager");
		request.setAttribute("breedSelect", lookupMgr
				.getSelectLabelValueByTable("T_A_BREED", "breedName",
						"breedID"," order by breedID "));
		request.setAttribute("commoditySelect", lookupMgr
				.getSelectLabelValueByTable("T_commodity", "commodityID",
						"commodityID"," order by commodityID "));
%>

<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>
		������Ȩ��
		</title>
<style type="text/css">
<!--
.yin {
	visibility:hidden;
	position:absolute;
	
}
.xian{
	visibility:visible;
}
-->
</style>
		<script type="text/javascript"> 
function window_onload()
{
    highlightFormElements();
    kind_click();
    if (customerForm.kind.value != "") {
    	if (customerForm.crud.value == "update") {
    		
    		if (customerForm.kind.value == "1") {
				setDisabled(customerForm.kindID);
			}
   			if (customerForm.kind.value == "2") {
   				setDisabled(customerForm.commodityID);
   			}
   			setDisabled(customerForm.kind);
   		}
    }
   
}
//save
function save_onclick()
{
  if (confirm("��ȷ��Ҫ�ύ��")) {
  	if (customerForm.kind.value == "") {
  		alert("Ȩ�����಻��Ϊ�գ�");
  		return false;
  	}else if (customerForm.kind.value == "1") {
  		if (customerForm.kindID.value == "") {
  			alert("����Ʒ�ֲ���Ϊ�գ�");
  			return false;
  		}
  	}else if (customerForm.kind.value == "2") {
  		if (customerForm.commodityID.value == "") {
  			alert("������Ʒ����Ϊ�գ�");
  			return false;
  		}
  	}
  	if (customerForm.privilegeCode_B.value == "") {
   		alert("��Ȩ�޲���Ϊ�գ�");
   		customerForm.privilegeCode_B.focus();
   		return false;
   }
    if (customerForm.privilegeCode_S.value == "") {
   		alert("����Ȩ�޲���Ϊ�գ�");
   		customerForm.privilegeCode_S.focus();
   		return false;
   }
   customerForm.submit();	
   customerForm.save.disabled = true;
  }
   
}
//cancel
function cancel_onclick()
{
	window.close();
   //top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/firm.do?funcflg=firmPrivilege&firmID="/>" + customerForm.typeID.value;
}

		function kind_click(){
			if (customerForm.kind.value == "1") {
				document.getElementById("breed").className = 'xian';
				document.getElementById("commodity").className = 'yin';
			}
			if (customerForm.kind.value == "2") {
				document.getElementById("commodity").className = 'xian';
				document.getElementById("breed").className = 'yin';
			}
			if (customerForm.kind.value == "") {
				document.getElementById("commodity").className = 'yin';
				document.getElementById("breed").className = 'yin';
			}
			
		}
</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="350" width="400" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/firm.do?funcflg=saveFirmPrivilege"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>������Ȩ��ά��
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
											Ȩ�����ࣺ
									</td>
									<td>
										<html:select property="kind" style="width:80" onchange="kind_click()">
										   <html:option value=""></html:option>
				                           <html:option value="1">Ʒ��</html:option>
					                       <html:option value="2">��Ʒ</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
									
								</tr>
								<%
									String type = (String)request.getAttribute("type");
								%>
								<tr id="breed" class="<%if("1".equals(type)){%>xian<%}else{%>yin<%}%>">
									<td align="right" >����Ʒ�֣�</td>
									<td>
									<html:select property="kindID"  style="width:80" >
										<%
											if (request.getAttribute("breedSelect") != null) {
												List list = (List)request.getAttribute("breedSelect");
												for (int i = 0; i < list.size(); i++) {
													LabelValue lv = (LabelValue)list.get(i);
													String breedID = lv.getValue();
													String breedName = lv.getLabel();
												%>
													<html:option value="<%=breedID%>"><%=breedName%></html:option>
												<%
												}
											}
										%>
									</html:select>
									<span class="req">*</span>
									</td>
								</tr>
								<tr id="commodity" class="<%if("2".equals(type)){%>xian<%}else{%>yin<%}%>">
									<td align="right" >������Ʒ��</td>
									<td>
									<html:select property="commodityID"  style="width:80" >
										<%
											if (request.getAttribute("commoditySelect") != null) {
												List list = (List)request.getAttribute("commoditySelect");
												for (int i = 0; i < list.size(); i++) {
													LabelValue lv2 = (LabelValue)list.get(i);
													String commodityID = lv2.getValue();
												%>
													<html:option value="<%=commodityID%>"><%=commodityID%></html:option>
												<%
												}
											}
										%>
									</html:select>
									<span class="req">*</span>
									</td>
								</tr>
									
								<tr>
									<td align="right">
											��Ȩ�ޣ�
									</td>
									<td>
										<html:select property="privilegeCode_B" style="width:120">
										   <html:option value=""></html:option>
				                           <html:option value="101">ȫȨ</html:option>
					                       <html:option value="102">ֻ�ɶ���</html:option>
					                       <html:option value="103">ֻ��ת��</html:option>
					                       <html:option value="104">��Ȩ</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
								</tr>									
								<tr>
									<td align="right">
											����Ȩ�ޣ�
									</td>
									<td>
										<html:select property="privilegeCode_S" style="width:120">
										   <html:option value=""></html:option>
				                           <html:option value="201">ȫȨ</html:option>
					                       <html:option value="202">ֻ�ɶ���</html:option>
					                       <html:option value="203">ֻ��ת��</html:option>
					                       <html:option value="204">��Ȩ</html:option>
			                            </html:select> <span class="req">*</span>
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center">
										<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											�ύ
										</html:button>
										<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											�ر�
										</html:button>
									</td>
								</tr>
							</table>
						</fieldset>
						
						<html:hidden property="crud"/>
						<html:hidden property="id"/>
						<html:hidden property="typeID" />
					</html:form>
				</td>
			</tr>
		</table>

		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
