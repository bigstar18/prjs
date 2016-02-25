<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.LabelValue"%>

<%
	String type = (String) request.getAttribute("type");
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

function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/baseinfo/directFirmBreed.jsp"/>";
}
//-----------------------------end settleinfo-----------------------------
</script>
	</head>
<% request.setAttribute("firmId1",request.getParameter("firmId"));
	request.setAttribute("breedId1",request.getParameter("breedId"));
 %>
	<body leftmargin="0" topmargin="0">
		<html:form
			action="/timebargain/baseinfo/directFirmBreed.do?funcflg=directFirmBreedMod"
			method="POST" styleClass="form" target="ListFrame1">
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
										交易商代码
									</td>
									<td>
									<html:text property="firmId" size="10" maxlength="16" readonly="true"></html:text>
									</td>
								</tr>
								<tr>
									<td align="right">
										商品品种
									</td>
         						   <td >
											<!-- property:form里的属性 -->
											<html:select property="breedId">
											<!-- collection :在范围对象中存在的集合，property：需要传递给后台的值，labelproperty：显示在页面的值 -->
											<html:option value=""></html:option>
											<html:options collection="breedList" property="breedId"
													labelProperty="breedName" />
											</html:select>									            
          						  </td> 
								</tr>
							</table>

							<table border="0" align="center" cellpadding="5" cellspacing="5"
								class="common">
								<tr>
									<td>
										<input type="button" class="button" value="提交"
											onclick="javascript:return save_onclick();"/>
										<input type="button" class="button" value="返回"
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
