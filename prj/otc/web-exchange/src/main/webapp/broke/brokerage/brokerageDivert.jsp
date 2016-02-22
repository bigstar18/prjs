<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%
	String memberNoChange = "";
%>
<html>
	<head>
		<title>居间转移会员</title>
	</head>
	<body leftmargin="0" topmargin="0" style="overflow-y: hidden">
	</br>
	<form action="${basePath}/broke/brokerageDivert/update.action" name="frm"
			method="post" targetType="hidden">
			<div style="overflow:auto;height:200px;">
				<table border="0" width="80%" align="center">
					<tr>
						<td>
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="2">
								<!-- 基本信息 -->
								<tr>
									<td colspan="4">
										<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;居间转移会员</div>
										<div class="div_tj">
											<table width="100%" border="0" class="table2_style">
												<tr height="35">
													<td align="right" >
														所属会员:
													</td>
													<td>
														
														<input type="hidden" id="memberNo" value="${obj.memberNo}">
														<input type="text" id="memberName" name="memberName" 
														value="<c:forEach items="${memberInfoList}" var="result"><c:if test="${result.id==obj.memberNo}">${result.name}</c:if></c:forEach>"
															size="6" readonly="readonly"
															class="input_text_pwdmin">
													</td>
												</tr>
												<tr height="35">
												    <td align="right">
														居间代码:
													</td>
													<td>
														<input type="text" id="customerNo" name="obj.brokerageNo" value="${obj.brokerageNo}"
															size="6" readonly="readonly"
															class="input_text_pwdmin">
													</td>
												</tr>
												<tr height="35">
													<td align="right">
														居间名称:
													</td>
													<td>
														<input type="text" name="obj.name" id="customerName"
															value="${obj.name}" readonly="readonly" class="input_text_pwdjdmax">
													</td>
												</tr>
												<tr>
													<td align="right">
														转移会员:
													</td>
													<td>
														<select name="obj.memberNo" id="memberNoChange" class="select_widmid"
															onblur="myblur('memberNoChange')"
															onfocus="myfocus('memberNoChange')" style="width: 300px;">
															<option value="">请选择</option>
															<c:forEach items="${memberInfoList}" var="result">
																<option value="${result.id}" title="${result.name}" <c:if test="${result.id==obj.memberNo}">selected="selected"</c:if>>
																	(${result.id})${result.name}
																</option>
															</c:forEach>
														</select>
														<span id="memberNoChange_vTip" ><%=memberNoChange%></span>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div>
				<table cellspacing="0" width="100%" cellpadding="0" border="0" align="center" >
					<tr>
						<td align="center">
							<button class="btn_sec" onClick="updateCustomer()" id="update" />
								保存
							</button>
						</td>
						<td align="center">
							<button  class="btn_sec" onClick="window.close()">关闭</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<br/>
		
	</body>
</html>
<script type="text/javascript">
function updateCustomer() {
	var memberNoChange=document.getElementById('memberNoChange').value;
	var memberNo=document.getElementById('memberNo').value;
	if(memberNoChange==memberNo){
		alert('所选会员不可与已关联会员相同,请重新选择！');
		return false;
	}
	var flag = myblur("all");
	if(flag){
		var vaild = affirm("您确定要操作吗？");
		if (vaild == true) {
			frm.submit();
		} else {
			return false;
		}
	}
}

function myblur(userID) {
	var flag = true;

	if ("memberNoChange" == userID) {
		flag = memberNoChange(userID);
	} else {
		if (!memberNoChange("memberNoChange"))
			flag = false;
	}
	return flag;
}
function memberNoChange(userID) {
	var innerHTML = "";
	var marg = document.getElementById("memberNoChange").value;
	var user = document.getElementById(userID);
	var vTip = document.getElementById(userID + "_vTip");
	var flag = false;
	if (isEmpty(user.value + "")) {
		innerHTML = "不能为空";
	} else {
		innerHTML = '<%=memberNoChange%>';
		flag = true;
	}
	vTip.innerHTML = innerHTML;
	if (flag) {
		vTip.className = "onFocus";
	} else {
		vTip.className = "onError";
	}
	return flag;
}
function myfocus(userID) {
	/*var vTip = document.getElementById(userID + '_vTip');
	var innerHTML = "";
	if ('memberNoChange' == userID) {
		innerHTML = "不能为空";
	}
	vTip.innerHTML = innerHTML;
	vTip.className = "onFocus";*/
}
</script>
<%@ include file="/public/footInc.jsp"%>