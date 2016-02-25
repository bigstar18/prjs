<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="gnnt.MEBS.timebargain.manage.model.LabelValue"%>
<html>
	<head>
		<LINK REL="stylesheet" type="text/css"
			href="<c:url value="/timebargain/styles/common.css"/>" />
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/global.js"/>"></script>
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/open.js"/>"></script>
		<script language="JavaScript"
			src="<c:url value="/timebargain/scripts/Date.js"/>"></script>
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

//联动

//save
function save_onclick()
{		

	if (confirm("您确定要提交吗？")) {
		if (document.forms(0).typeid.value == "") {
			alert("交易商代码不能为空!");
			document.forms(0).typeid.focus();
			return false;
		}
		if (document.forms(0).kindid.value == "") {
			alert("商品代码不能为空!");
			document.forms(0).kindid.focus();
			return false;
		}
		
		if (document.forms(0).privilegecode_b.value=="") {
			alert("买方权限不能为空");
			document.forms(0).privilegecode_b.focus();
			return false;
		}
		if (document.forms(0).privilegecode_s.value=="") {
			alert("卖方权限不能为空");
			document.forms(0).privilegecode_s.focus();
			return false;
		}
		document.forms(0).submit();
    	document.forms(0).save.disabled = true;
	}
		
	
    
 }

//cancel
function cancel_onclick()
{
   window.close();
}

</script>
	</head>

	<body leftmargin="0" topmargin="0">
					<form action="/mgr/timebargain/delay/delay.do?funcflg=privilegeUpdate" method="POST" id="form1" name="form1">
				
							<input type="hidden" value="${settlePrivilege.id }" name="id" id="id"/>
							<table width="50%" border="0" height="100%" align="center" class="common"
								cellpadding="0" cellspacing="2">
								<!-- 基本信息 -->
								<tr class="common">
									<td colspan="4">
										<fieldset class="pickList">
											<legend>
												<table cellspacing="0" cellpadding="0" border="0"
													width="400" class="common">
													<col width="110"></col>
													<col></col>
													<col width="16"></col>
													<tr>
														<td>
															<b>添加商品交收权限</b>
														</td>
														<td>
															<hr width="99%" class="pickList" />
														</td>
													</tr>
												</table>
											</legend>
											<table cellSpacing="0" cellPadding="0" width="100%"
												border="0" align="center" class="common">
												<tr >
													<td align="center">
														交易商代码:
													</td>
													<td align="center">
														<input type="text" id="typeid" name="typeid"
															style="width: 120" maxlength="16" title=""
															styleClass="text" value="${settlePrivilege.typeid }" readonly="true"/>

													</td>
											
													<td align="center">
														商品代码:
													</td>
													<td align="center">
														<c:forEach  items="${commoditySelect}" var="result">
															<c:if test="${settlePrivilege.kindid==result.value }">
																<input type="hidden" name="kindid" value="${result.value}"/>
																<input type="text" readonly="true" value="${result.label}" style="width: 120"/>
															</c:if> 
														</c:forEach>
													</td>
												</tr>
												<tr>
													<td align="center">
														买方权限:
													</td>
													<td align="center">
														<select id="privilegecode_b" name="privilegecode_b" style="width: 120">
<%--															<option></option>--%>
															<option value="101" <c:if test="${settlePrivilege.privilegecode_b==101 }"> selected="selected"</c:if>>全权</option>
															<option value="102" <c:if test="${settlePrivilege.privilegecode_b==102 }"> selected="selected"</c:if>>只可交收申报</option>
															<option value="103" <c:if test="${settlePrivilege.privilegecode_b==103 }"> selected="selected"</c:if>>只可中立仓申报</option>
															<option value="104" <c:if test="${settlePrivilege.privilegecode_b==104 }"> selected="selected"</c:if>>无权</option>															
														</select>
													</td>							
													<td align="center">
														卖方权限:
													</td>
													<td align="center">
														<select id="privilegecode_s" name="privilegecode_s" style="width: 120">
<%--															<option></option>--%>
															<option value="101" <c:if test="${settlePrivilege.privilegecode_s==101 }"> selected="selected"</c:if>>全权</option>
															<option value="102" <c:if test="${settlePrivilege.privilegecode_s==102 }"> selected="selected"</c:if>>只可交收申报</option>
															<option value="103" <c:if test="${settlePrivilege.privilegecode_s==103 }"> selected="selected"</c:if>>只可中立仓申报</option>
															<option value="104" <c:if test="${settlePrivilege.privilegecode_s==104 }"> selected="selected"</c:if>>无权</option>														
														</select>
													</td>
												</tr>
											</table>										
										</fieldset>
									</td>
								</tr>
								<tr>
									<td align="right">
									<html:button property="save" styleClass="button"
											onclick="javascript:return save_onclick();">
											提交
										</html:button>&nbsp;&nbsp;
									</td>
									<td align="left">
										&nbsp;&nbsp;<html:button property="cancel" styleClass="button"
											onclick="javascript:return cancel_onclick();">
											返回
										</html:button>
									</td>
									
								</tr>
							</table>
						</form>


		<script type="text/javascript" src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
