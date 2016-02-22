<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title>关联特别会员</title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form action="" name="frm" method="post" targetType="hidden">
			<table border="0" width="100%" align="center">
				<tr>
					<td>
						<div class="st_title">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;关联特别会员
						</div>
						<input type="hidden" name="selectSpecialMem" id="selectSpecialMem">
						<table border="0" cellspacing="0" cellpadding="4" width="100%"
							align="center" class="st_bor">
							<table align='center' valign="top">
								<tr>
									<td>
										<table align="center">
											<tr>
												<td width="45%">
													<TABLE>
														<TR>
															<td>
																请选择要转单分配的特别会员
															</td>
														</TR>
														<TR>
															<TD>
																<select multiple id="selectFrom" name="obj.selectFrom"
																	style="width: 300px; height: 250px">
																	<c:forEach items="${specialMemberList}" var="result">
																		<c:set var="flag" value="false"></c:set>
																		<c:forEach items="${memberRelationList}"
																			var="relationResult">
																			<c:if test="${relationResult.s_MemberNo==result.id}">
																				<c:set var="flag" value="true"></c:set>
																			</c:if>
																		</c:forEach>
																		<c:if test="${flag==false}">
																			<option value="${result.id }" title="${result.name}">
																				${result.name}
																			</option>
																		</c:if>
																	</c:forEach>
																</select>
															</TD>
														</TR>
													</TABLE>
												</td>
												<td width="10%" align="center">
													<button class="btn_min" id="updateChange"
														onclick="selectSingle('selectFrom','selectTo')">
														>
													</button>
													<br>
													<button class="btn_min" id="updateChange"
														onclick="selectAll('selectFrom','selectTo')">
														>>
													</button>
													<br>
													<button class="btn_min" id="updateChange"
														onclick="selectSingle('selectTo','selectFrom')">
														<
													</button>
													<br>
													<button class="btn_min" id="updateChange"
														onclick="selectAll('selectTo','selectFrom')">
														<<
													</button>
												</td>
												<td width="45%">
													<TABLE valign="top">
														<TR>
															<TD>
																所要转单分配的特别会员
															</TD>
														</TR>
														<TR>
															<td>
																<select multiple id="selectTo" name="obj.selectTo"
																	style="width: 300px; height: 250px">
																	<c:forEach items="${memberRelationList}"
																		var="relationResult">
																		<option value="${relationResult.s_MemberNo}" title="${result.name}">
																			<c:forEach items="${specialMemberList}" var="result">
																				<c:if test="${relationResult.s_MemberNo==result.id}">
																${result.name}
															</c:if>
																			</c:forEach>
																		</option>
																	</c:forEach>
																</select>
															</td>
														</tr>
													</TABLE>
												</td>
												<td width="10%" align="center">
													<button class="btn_min" id="updateChange"
														onclick="selectUp()">
														∧
													</button>
													<br>
													<button class="btn_min" id="updateChange"
														onclick="selectDown()">
														∨
													</button>
													<br>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</table>
					</td>
				</tr>
			</table>
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			    <tr><td height="20">&nbsp;</td></tr>
				<tr>
					<td align="center">
							<button class="btn_sec" id="updateChange"
								value="ContactSpecialMember"
								onClick="contactSpecialMember('${memberId}')">
								关联
							</button>
					</td>
					<td align="center">
							<button class="btn_sec" onClick="window.close()">
								关闭
							</button>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script type="text/javascript">
function contactSpecialMember(id) {
	frm.action = "${basePath}/account/memberChangeOrder/update.action?memberId="
			+ id;
	var objTo = $('selectTo');
	var value = "";
	if(objTo.options.length==0){
		alert("必须至少有一个默认特别会员");
		return false;
	}
	//关联的特别会员
	for ( var i = 0; i < objTo.options.length; i++) {
		value += objTo.options[i].value;
		if (i < objTo.options.length - 1) {
			value += ",";
		}
	}
	
	document.getElementById("selectSpecialMem").value = value;
	frm.submit();
}
function $(id) {
	return document.getElementById(id);
}

function selectSingle(from, to) {
	var objFrom = $(from);
	if (objFrom.selectedIndex > -1) {
		var objTo;
		objTo = $(to);
		var opt = objFrom.options[objFrom.selectedIndex];
		if (!hasSelectedOption(opt, to)) {
			objTo.options[objTo.options.length] = new Option(opt.text,
					opt.value);
			objFrom.options[objFrom.selectedIndex] = null;
		}
	}
}

function selectAll(from, to) {
	var objFrom = $(from);
	var objTo;
	objTo = $(to);
	for ( var i = 0; i < objFrom.options.length; i++) {
		var opt = objFrom.options[i];
		if (!hasSelectedOption(opt, to)) {
			objTo.options[objTo.options.length] = new Option(opt.text,
					opt.value);
		}
	}
	var len = objFrom.options.length;
	for ( var i = 0; i < len; i++) {
		objFrom.options[0] = null;
	}
}

function hasSelectedOption(opt, to) {
	var isExist = false;
	var objTo;
	objTo = $(to);
	for ( var j = 0; j < objTo.options.length; j++) {
		if (opt.value == objTo.options[j].value) {
			isExist = true;
			break;
		}
	}
	return isExist;
}
function selectUp() {
	var objFrom = $('selectTo');
	var sel = objFrom.selectedIndex;
	var length = objFrom.length;
	//			objFrom.options[objFrom.selectedIndex] = null;
	if ((sel - 1) <= -1) {
		return false;
	}
	var mid = objFrom.options[sel].value;
	var midText = objFrom.options[sel].text;
	objFrom.options[sel].value = objFrom.options[sel - 1].value;
	objFrom.options[sel].text = objFrom.options[sel - 1].text;
	objFrom.options[sel - 1].value = mid;
	objFrom.options[sel - 1].text = midText;
	objFrom.options[sel - 1].selected = true;
	objFrom.options[sel].selected = false;
}

function selectDown() {
	var objFrom = $('selectTo');
	var sel = objFrom.selectedIndex;
	var length = objFrom.length;
	//			objFrom.options[objFrom.selectedIndex] = null;
	if ((sel + 1) >= length) {
		return false;

	}
	if (sel < 0) {
		return false;
	}
	var mid = objFrom.options[sel].value;
	var midText = objFrom.options[sel].text;
	objFrom.options[sel].value = objFrom.options[sel + 1].value;
	objFrom.options[sel].text = objFrom.options[sel + 1].text;
	objFrom.options[sel + 1].value = mid;
	objFrom.options[sel + 1].text = midText;
	objFrom.options[sel + 1].selected = true;
	objFrom.options[sel].selected = false;
}
//用于提交
function getSelectedOptions2() {
	var objTo = $('selectTo');
	var text = "";
	var value = "";
	//关联的特别会员
	for ( var i = 0; i < objTo.options.length; i++) {
		value += objTo.options[i].value;
		if (i < objTo.options.length - 1) {
			value += ",";
		}
	}
	alert(value);

}
</script>
<%@ include file="/public/footInc.jsp"%>