<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.LabelValue"%>

<%
	String type = (String) request.getAttribute("type");
%>
<%
String paths = request.getContextPath();
String basePathh = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+paths+"/";
%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script type="text/javascript"
			src="<c:url value="/timebargain/widgets/dwr/interface/commodityManager.js"/>"></script>

		<script type="text/javascript"
			src="<c:url value="/timebargain/widgets/dwr/engine.js"/>"></script>
		<script type="text/javascript"
			src="<c:url value="/timebargain/widgets/dwr/util.js"/>"></script>
		<title></title>
		<style type="text/css">
<!--
.yin {
	visibility: hidden;
	position: absolute;
}

.xian {
	visibility: visible;
}
-->
</style>
		<script type="text/javascript"> 


function save_onclick()
{
	if (confirm("��ȷ��Ҫ�ύ��")) {
	
     
    if (directFirmBreedForm.firmId.value == "") {
    	alert("�����̴��벻��Ϊ�գ�");
    	directFirmBreedForm.firmId.focus();
      	return false;
    }
	if (directFirmBreedForm.breedId.value == "") {
    	alert("��ƷƷ�ֲ���Ϊ�գ�");
    	directFirmBreedForm.breedId.focus();
      	return false;
    }
    directFirmBreedForm.submit();
	}
  
}
	var request = new ActiveXObject("Microsoft.XMLHTTP");


	function oneAjax(){
	
		var value=directFirmBreedForm.breedTradeMode.value;
		request.onreadystatechange = stateChanged;
		request.open("post","<%=basePathh%>/timebargain/baseinfo/directFirmBreed.do?funcflg=breedListByBreedTradeMod&breedTradeMode="+value,false);
		request.send();
		request.abort();
	}
	function stateChanged() 
	{ 
 	 	if (request.readyState==4)
 	 	{ 
  			var result = request.responseText;
  			document.getElementById("breed").innerHTML=result;
  		}
	}

function cancel_onclick()
{
   window.close();
}
//-----------------------------end settleinfo-----------------------------
</script>
	</head>

	<body leftmargin="0" topmargin="0">
		<html:form
			action="/timebargain/baseinfo/directFirmBreed.do?funcflg=directFirmBreedAdd"
			method="POST" styleClass="form" target="HiddFrame2">
			<table border="0" height="40%" width="60%" align="center">
				<tr>
					<td>
						<fieldset class="pickList">
							<legend class="common">
								<b>���ֽ�����Ʒ������</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
										ר������:
									</td>
									<td>
									<html:select property="breedTradeMode" onchange="oneAjax()">
										<html:option value=""></html:option>
										<html:option value="1">��ר��</html:option>
										<html:option value="2">��ר��</html:option>
										<html:option value="3">��ר������ת��</html:option>
										<html:option value="4">��ר������ת��</html:option>
									</html:select>
									</td>
								</tr>
								<tr>
									<td align="right">
										�����̴���:
									</td>
									<td>
									<html:text property="firmId" size="10" maxlength="16"></html:text>
									</td>
								</tr>
								<tr>
									<td align="right">
										��ƷƷ��:
									</td>
         						   <td >
         						   			<span id = "breed">
													
											</span>
																            
          						  </td> 
								</tr>
							</table>

							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td>
										<input type="button" class="button" value="�ύ"
											onclick="javascript:return save_onclick();"/>
										<input type="button" class="button" value="�ر�"
											onclick="javascript:return cancel_onclick();"/>
									</td>
								</tr>
							</table>
						</fieldset>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
