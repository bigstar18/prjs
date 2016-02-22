<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<html>
	<head>
		<title></title>
	</head>
	<body leftmargin="0" topmargin="0" class="st_body">
		<form action="" name="frm" method="post" targetType="hidden">
			<table border="0" width="500" align="center">
				<tr height="100"></tr>
				<tr>
					<td>
						<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;关联特别会员</div>
						<input type="hidden" name="selectSpecialMem" id="selectSpecialMem">
						<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
				<table align='center' valign="top">
					<tr>
						<td>
							<table align="center">
								<tr>
									<td align='center' class="xbt"></td>
								</tr>
							</table>
							<br>
							<table align="center">
								<tr>
									<td width="45%"></td>
									<td width="10%" align="center"></td>
									<td width="45%"></td>
								</tr>
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
													<select multiple id="selectFrom"
														style="width: 200px; height: 250px">
														<c:forEach items="${specialMemberList}" var="result">
															<c:set var="flag" value="false"></c:set>
															<c:forEach items="${memberRelationList}"
																var="relationResult">
																<c:if test="${relationResult.s_MemberNo==result.id}">
																	<c:set var="flag" value="true"></c:set>
																</c:if>
															</c:forEach>
															<c:if test="${flag==false}">
																<option value="${result.id }">
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
										<input type="button" id="btnSelectSingle" value=">"
											class="btn" onclick="selectSingle('selectFrom','selectTo')" />
										<br>
										<input type="button" id="btnSelectAll" value=">>" class="btn"
											onclick="selectAll('selectFrom','selectTo')" />
										<br>
										<input type="button" id="btnDeselectSingle" value="<" class="
											btn" onclick="selectSingle('selectTo','selectFrom')" />
										<br>
										<input type="button" id="btnUnselectAll" value="<<" class="
											btn" onclick="selectAll('selectTo','selectFrom')" />
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
													<select multiple id="selectTo"
														style="width: 200px; height: 250px">
														<c:forEach items="${memberRelationList}"
															var="relationResult">
															<option value="${relationResult.s_MemberNo}">
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
										<input type="button" id="btnSelectSingle" value="|>"
											class="btn" onclick="selectUp()" />
										<br>
										<input type="button" id="btnSelectAll" value="|<" class="btn"
											onclick="selectDown('selectFrom','selectTo')" />
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
					<tr>
						<td width="50%" align="center">
							<div class="st_anbor">
									<button  class="btn_sec" id="updateChange" value="ContactSpecialMember"
									onClick="contactSpecialMember('${memberId}')">关联</button>
							</div>
						</td>
						<td width="50%" align="center">
							<div class="st_anbor">
								<button  class="btn_sec" onClick="window.close()">返 回</button>
							</div>
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
	var midText=objFrom.options[sel].text;	
	objFrom.options[sel].value =objFrom.options[sel-1].value;
	objFrom.options[sel].text =objFrom.options[sel-1].text;
	objFrom.options[sel - 1].value = mid;
	objFrom.options[sel - 1].text = midText;
	objFrom.options[sel - 1].selected =true;
	objFrom.options[sel].selected =false;
}

function selectDown() {
	var objFrom = $('selectTo');
	var sel = objFrom.selectedIndex;
	var length = objFrom.length;
	//			objFrom.options[objFrom.selectedIndex] = null;
	if ((sel + 1) >= length) {
		return false;
	}
	var mid = objFrom.options[sel].value;
	var midText=objFrom.options[sel].text;	
	objFrom.options[sel].value =objFrom.options[sel+1].value;
	objFrom.options[sel].text =objFrom.options[sel+1].text;
	objFrom.options[sel + 1].value = mid;
	objFrom.options[sel + 1].text = midText;
	objFrom.options[sel + 1].selected =true;
	objFrom.options[sel].selected =false;
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