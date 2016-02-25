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
		<script language="javascript" 
			src="<%=request.getContextPath()%>/delivery/public/jstools/jquery.js"></script>
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
    	document.forms(0).save.disabled = false;
	}
    
 }

//cancel
function cancel_onclick()
{
   document.location.href = "<c:url value="/timebargain/delay/settleprivilege.jsp"/>";
}

function dealChange(){
	var firmId = document.getElementById("dealId").value;
	$.ajax({
			type:"post",
			url:"<c:url value="/timebargain/delay/delay.do?funcflg=getFirmId"/>",
			data:"firmId=" + firmId,
			success : function(data){
						if(data==1){
						  $("#dealId").val("");
						  $("#hint").html("*没有该交易商");
						}else{
							$("#hint").html("");
						}
			}
		});
}
function getSettleprivilege(){
	var firmId = document.getElementById("dealId").value;
	var kindId = document.getElementById("kindId").value;
	$.ajax({
		type:"post",
		url:"<c:url value="/timebargain/delay/delay.do?funcflg=getSettleprivilegeByFirmIdAndCommId"/>",
		data:{
				firmId : firmId,
				kindId : kindId
			},
		success : function(data){
					if(data==1){
					  $("#dealId").val("");
					  alert("此交易商和商品已经添加");
					}
		}
	});
}
</script>
	</head>

	<body leftmargin="0" topmargin="0">

		<table border="0"  width="100%" align="center">
			<tr height="100%">

				<td height="390">
					<form action="${pageContext.request.contextPath}/timebargain/delay/delay.do?funcflg=privilegeAdd" method="POST" id="form1" name="form1">

						<fieldset class="pickList">
							<legend class="common">
								<b>商品交收权限 </b>
							</legend>

							<table width="55%" border="0" height="100%" align="center" class="common"
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
														<input id="dealId" type="text"  name="typeid" onblur="dealChange();" 
															style="width: 120" maxlength="16" title=""
															styleClass="text" align="center">
													</td>
													<td>
														<b><font color=red><span id="hint"></span></font><b/>
													</td>					
											
													<td align="center">
														商品代码:
													</td>
													<td align="center">
														<select id="kindId" name="kindid" style="width: 120" onblur="getSettleprivilege()">
															<c:forEach  items="${commoditySelect}" var="result">
																<option value="${result.value}">${result.value}</option>
															</c:forEach>
														</select>
													
													</td>
												</tr>
												<tr>
													<td align="center">
														买方权限:
													</td>
													<td align="center">
														<select id="privilegecode_b" name="privilegecode_b" style="width: 120">
<%--															<option></option>--%>
															<option value="101">全权</option>
															<option value="102" selected="selected">只可交收申报</option>
															<option value="103">只可中立仓申报</option>
															<option value="104">无权</option>															
														</select>
													</td>	
													<td>
													</td>								
													<td align="center">
														卖方权限:
													</td>
													<td align="center">
														<select id="privilegecode_s" name="privilegecode_s" style="width: 120">
<%--															<option></option>--%>
															<option value="101">全权</option>
															<option value="102" selected="selected">只可交收申报</option>
															<option value="103">只可中立仓申报</option>
															<option value="104">无权</option>														
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
							</fieldset>
						</form>
					</td>
				</tr>
			</table>


		<script type="text/javascript" src="<html:rewrite page="/timebargain/common/validator.jsp"/>"></script>
		<%@ include file="/timebargain/common/messages.jsp"%>
	</body>
</html>
