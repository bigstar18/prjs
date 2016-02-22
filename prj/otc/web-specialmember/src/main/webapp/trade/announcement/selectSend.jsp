<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>


<html>
	<head>
		<title>系统用户浏览</title>
		<script language="JavaScript" src="../../public/global.js">
</script>
		<script language="JavaScript" src="../../public/open.js">
</script>
	</head>
	<body>
		<div id="main_body">
			<form name="frm"
				action="${basePath}/tradeManage/announcement/add.action"
				method="post">
				<input type="hidden" name="notice.title" value="${notice.title}" />
				<input type="hidden" name="notice.author" value="${notice.author}" />
				<input type="hidden" name="notice.expiryDates" value="${notice.expiryDates}" />
				<input type="hidden" name="notice.expiryTime" value="${datefn:formatdate(notice.expiryTime)}" />
				<input type="hidden" name="notice.sendTime" value="${datefn:formatdate(notice.sendTime)}" />
				<input type="hidden" name="notice.content" value="${notice.content}" />
				<input type="hidden" name="notice.sendType" value="${notice.sendType}" />
				<table class="table1_style" border="0" cellspacing="0"
					cellpadding="0">
					<tr>
						<td>
							选择发送对象
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
							<div class="div_cxtj">
								<img src="<%=skinPath%>/cssimg/13.gif" />
								特别会员
							</div>

							<div class="div_tj">
								<input type="hidden" name="specialType" value="specialMemeber" />
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td height="60">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="gg_td" align="right">
															发送给默认角色:&nbsp;
														</td>
														<td class="gg_td2">
															<label>
																<input type="checkbox" name="isAllSpecial"
																	id="isAllSpecial" onclick="isAllSpecials()" value="N"
																	class="NormalInput" />
															</label>
														</td>
													</tr>
													<c:if test="${fn:length(roleList) > 0}">
														<tr>
															<td class="gg_td" align="right" valign="top">
																选择指定角色:&nbsp;
															</td>
															<td class="gg_td" align="left">
																<table>
																<c:forEach var="role" items="${roleList}" varStatus="status">
																	<c:if test="${status.count%2==1}">
																		<tr>
																	</c:if>
																	<td align="right" width="1%">
																		<input type="checkbox" value="${role.id}" name="role" class="NormalInput">
																	</td>
																	<td class="gg_td" align="left">
																		<c:out value="${role.name}"></c:out>
																	</td>
																	<c:if test="${status.count%2==0}">
																		</tr>
																	</c:if>
																</c:forEach>
																</table>
															</td>
														</tr>
													</c:if>
													<tr style="display:none;">
														<td class="gg_td" align="right">
															指定特别会员管理员:&nbsp;
														</td>
														<td class="gg_td2">
															<label>
																<textarea rows="10" cols="40" name="specialsManager"
																	id="specialsManager"></textarea>
															</label>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</div>
							<div class="div_cxtj">
								<img src="<%=skinPath%>/cssimg/13.gif" />
								综合会员
							</div>
							<div class="div_tj">
								<input type="hidden" name="memberType" value="member" />
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td height="60">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="gg_td" align="right">
															向全部综合会员发送:&nbsp;
														</td>
														<td class="gg_td2">
															<label>
																<input type="checkbox" name="isAllMember"
																	id="isAllMember" value="N" onclick="isAllMembers()"
																	class="NormalInput" />
															</label>
														</td>
													</tr>
													<c:if test="${fn:length(memberInfoList) > 0}">
														<tr>
															<td class="gg_td" align="right" valign="top">
																指定综合会员:&nbsp;
															</td>
															<td class="gg_td2" align="left">
																<table>
																<c:forEach var="memberInfo" items="${memberInfoList}"varStatus="status">
																	<c:if test="${status.count%2==1}">
																		<tr>
																	</c:if>
																	<td align="right" width="1%">
																		<input type="checkbox" value="${memberInfo.id}" name="memberInfo" class="NormalInput">
																	</td>
																	<td class="gg_td" align="left">
																		<c:out value="${memberInfo.name}"></c:out>
																	</td>
																	<c:if test="${status.count%2==0}">
																		</tr>
																	</c:if>
																</c:forEach>
																</table>
															</td>
														</tr>
													</c:if>
													<tr style="display:none;">
														<td class="gg_td" align="right">
															指定综合会员管理员:&nbsp;
														</td>
														<td class="gg_td2">
															<label>
																<textarea rows="10" cols="40" name="manager"
																	id="manager"></textarea>
															</label>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="div_list">
			<table border="0" cellspacing="0" cellpadding="0" width="100%"
				align="center">
				<tr valign="top">
					<td align="center">
						<button class="btn_sec" onclick="formSubmit()">
							发送
						</button>
						<button class="btn_sec" onclick="returnBack()">
							返回
						</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>

<%@ include file="/public/footInc.jsp"%>
<script type="text/javascript">

function returnBack() {
	frm.action = "${basePath}/tradeManage/announcement/show.action";
	frm.submit();
}

function formSubmit() {
	var result = -1;
    var allInput = document.getElementsByTagName("input");
    if(allInput.length > 0){
    	result = 0;
     for (var i = 0; i < allInput.length; i++){
         if (allInput[i].type == "checkbox"){
         	if(allInput[i].checked){
          	result = 1;
         	}
         }
     }
    }
	if(result == 0&&frm.specialsManager.value==""&&frm.manager.value==""){
		alert('请选择发送对象！');
		return false;
	}
	if(window.confirm("您确定要操作吗？")) {
		frm.submit();
	}
}
function isAllMembers() {
	if (frm.isAllMember.checked) {
		var memberInfos = document.frm.memberInfo;
		frm.manager.value = "";
		setReadOnly(frm.manager);
		if (memberInfos.length) {
			for (i = 0; i < memberInfos.length; i++) {
				memberInfos[i].checked = true;
			}
		} else {
			document.frm.memberInfo.checked = true;
		}
	} else {
		var memberInfos = document.frm.memberInfo;
		setReadWrite(frm.manager);
		if (memberInfos.length) {
			for (i = 0; i < memberInfos.length; i++) {
				memberInfos[i].checked = false;
			}
		} else {
			document.frm.memberInfo.checked = false;
		}
	}
}
function isAllSpecials() {
	if (frm.isAllSpecial.checked) {
		var roles = document.frm.role;
		frm.specialsManager.value = "";
		setReadOnly(frm.specialsManager);
		if (roles.length) {
			for (i = 0; i < roles.length; i++) {
				roles[i].disabled = true;
			}
		} else {
			document.frm.role.disabled = true;
		}

	} else {
		var roles = document.frm.role;
		setReadWrite(frm.specialsManager);
		if (roles.length) {
			for (i = 0; i < roles.length; i++) {
				roles[i].disabled = false;
			}
		} else {
			document.frm.role.disabled = false;
		}
	}
}

function specialMemberdefault(smid, id) {
	if (document.getElementById(smid).checked) {
		document.getElementById(id).style.display = "";
	} else {
		document.getElementById(id).style.display = "none";
	}
}
function memberdefault(mid, id) {
	if (document.getElementById(mid).checked) {
		document.getElementById(id).style.display = "";
	} else {
		document.getElementById(id).style.display = "none";
	}
}
function isAllTraders() {
	if (frm.isAllTrader.checked) {
		var traders = document.frm.trader;
		frm.traderManager.value = "";
		setReadOnly(frm.traderManager);
		document.frm.isAllTrader.value = "Y";
		if (traders.length) {
			for (i = 0; i < traders.length; i++) {
				traders[i].checked = true;
			}
		} else {
			document.frm.trader.checked = true;
		}
	} else {
		var traders = document.frm.trader;
		setReadWrite(frm.traderManager);
		document.frm.isAllTrader.value = "";
		if (traders.length) {
			for (i = 0; i < traders.length; i++) {
				traders[i].checked = false;
			}
		} else {
			document.frm.trader.checked = false;
		}
	}
}
var tmp_baseinfo1;
var tmp_baseinfo_up1 = true;
var tmp_baseinfo2;
var tmp_baseinfo_up2 = true;
var tmp_baseinfo3;
var tmp_baseinfo_up3 = true;
function baseinfoMember_onclick() {
	if (tmp_baseinfo_up1) {
		tmp_baseinfo_up1 = false;
		document.forms(0).baseinfoMember_img.src = "<%=skinPath%>/images1/ctl_detail_Down.gif";
		tmp_baseinfo1 = baseinfoMember.innerHTML;
		baseinfoMember.innerHTML = "";
	} else {
		tmp_baseinfo_up1 = true;
		document.forms(0).baseinfoMember_img.src = "<%=skinPath%>/images1/ctl_detail_Up.gif";
		baseinfoMember.innerHTML = tmp_baseinfo1;
	}
}
function baseinfoSpecial_onclick() {
	if (tmp_baseinfo_up2) {
		tmp_baseinfo_up2 = false;
		document.forms(0).baseinfoSpecial_img.src = "<%=skinPath%>/images1/ctl_detail_Down.gif";
		tmp_baseinfo2 = baseinfoSpecial.innerHTML;
		baseinfoSpecial.innerHTML = "";
	} else {
		tmp_baseinfo_up2 = true;
		document.forms(0).baseinfoSpecial_img.src = "<%=skinPath%>/images1/ctl_detail_Up.gif";
		baseinfoSpecial.innerHTML = tmp_baseinfo2;
	}
}
function baseinfoTrader_onclick() {
	if (tmp_baseinfo_up3) {
		tmp_baseinfo_up3 = false;
		document.forms(0).baseinfoTrader_img.src = "<%=skinPath%>/images1/ctl_detail_Down.gif";
		tmp_baseinfo3 = baseinfoTrader.innerHTML;
		baseinfoTrader.innerHTML = "";
	} else {
		tmp_baseinfo_up3 = true;
		document.forms(0).baseinfoTrader_img.src = "<%=skinPath%>/images1/ctl_detail_Up.gif";
		baseinfoTrader.innerHTML = tmp_baseinfo3;
	}
}
</script>
