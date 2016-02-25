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
	if (confirm("您确定要提交吗？")) {
	
     
    if (directFirmBreedForm.firmId.value == "") {
    	alert("交易商代码不能为空！");
    	directFirmBreedForm.firmId.focus();
      	return false;
    }
	if (directFirmBreedForm.breedId.value == "") {
    	alert("商品品种不能为空！");
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
								<b>主持交易商品种设置</b>
							</legend>
							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td align="right">
										专场买卖:
									</td>
									<td>
									<html:select property="breedTradeMode" onchange="oneAjax()">
										<html:option value=""></html:option>
										<html:option value="1">买专场</html:option>
										<html:option value="2">卖专场</html:option>
										<html:option value="3">买专场不可转让</html:option>
										<html:option value="4">卖专场不可转让</html:option>
									</html:select>
									</td>
								</tr>
								<tr>
									<td align="right">
										交易商代码:
									</td>
									<td>
									<html:text property="firmId" size="10" maxlength="16"></html:text>
									</td>
								</tr>
								<tr>
									<td align="right">
										商品品种:
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
										<input type="button" class="button" value="提交"
											onclick="javascript:return save_onclick();"/>
										<input type="button" class="button" value="关闭"
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
