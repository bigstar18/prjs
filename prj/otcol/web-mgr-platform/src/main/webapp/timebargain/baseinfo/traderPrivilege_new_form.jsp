<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*" %>


<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<title>

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
    if (traderForm.crud.value == "update") {
    	setDisabled(traderForm.kindID);
    	
    }
}
//save
function save_onclick()
{
  	if (confirm("��ȷ��Ҫ�ύ��")) {
  		if (traderForm.kindID.value == "") {
  			alert("����Ʒ�ֲ���Ϊ�գ�");
	  	    return false;
  		}
  	
  		if (traderForm.privilegeCode_B.value == "") {
   		alert("��Ȩ�޲���Ϊ�գ�");
   		traderForm.privilegeCode_B.focus();
   		return false;
	   }
	    if (traderForm.privilegeCode_S.value == "") {
	   		alert("����Ȩ�޲���Ϊ�գ�");
	   		traderForm.privilegeCode_S.focus();
	   		return false;
	   }
	   traderForm.submit();	
	   traderForm.save.disabled = true;
  	}
   
}
//cancel
function cancel_onclick()
{
   window.close();
   //top.mainFrame.location.href = "<c:url value="/timebargain/baseinfo/trader.do?funcflg=tradePrivilege&traderID="/>" + traderForm.typeID.value;
}


</script>
	</head>

	<body leftmargin="0" topmargin="0" onLoad="return window_onload()"
		onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="80%" width="50%" align="center">
			<tr>
				<td>
					<html:form action="/timebargain/baseinfo/trader.do?funcflg=saveNewTraderPrivilege"
						method="POST" styleClass="form" target="HiddFrame2">
						<fieldset class="pickList" >
							<legend class="common">
								<b>����ԱȨ��ά��
								</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								
								<tr>
									<td align="right">
											����Ʒ�֣�
									</td>
									<td>
										<html:select property="kindID"  style="width:80" >
                                          <html:options collection="breedSelect" property="value" labelProperty="label"/>
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
