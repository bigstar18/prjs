<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>


<html>
	<head>
		<title>系统用户浏览</title>
		<script language="JavaScript" src="../../public/global.js"></script>
		<script language="JavaScript" src="../../public/open.js"></script>
	</head>
	<body class="st_body">
		<br />
		<table border="0" width="100%" align="center">
		<tr>
		<td>
		<form name="myForm" action="${basePath}/tradeManage/announcement/add.action" method="post">
			<input type="hidden" name="notice.title" value="${notice.title}" />
			<input type="hidden" name="notice.author" value="${notice.author}" />
			<fmt:formatDate pattern="yyyy-MM-dd" value="${notice.expiryTime}" var="dteObject" />
			<input type="hidden" name="notice.expiryTime" value="${dteObject}" />
			<input type="hidden" name="notice.content" value="${notice.content}" />
			<fieldset class="pickList">
				<legend class="common,st_title2">
					<b>选择发送对象 </b>
				</legend>
				<table width="100%" border="0" align="center" class="common" cellpadding="0" cellspacing="2">
					<tr class="common">
						<td colspan="4">
							
							<fieldset class="pickList">
								<legend>
									<table cellspacing="0" cellpadding="0" border="0" width="100%" class="common">
										<col width="60"></col>
										<col></col>
										<col width="6"></col>
										<tr>
											<td>
												<b>特别会员</b>
											</td>
											<td>
												<hr width="99%" class="pickList" />
											</td>
											<td>
												<img id="baseinfoSpecial_img" src="<%=skinPath%>/images1/ctl_detail_Up.gif" style="cursor: hand" onclick="javascript:baseinfoSpecial_onclick()" />
											</td>
										</tr>
									</table>
								</legend>
								<span id="baseinfoSpecial">
									<input type="hidden" name="specialType" value="specialMemeber" />
									<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">
										<tr>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											<td>
												向全部特别会员发送：&nbsp;
												<input type="checkbox" name="isAllSpecial" id="isAllSpecial" onclick="isAllSpecials()" value="N" class="NormalInput"/>
											</td>
										</tr>
										<tr>
											<td></td>
											<td>
											指定特别会员：
											<c:forEach var="specialMember" items="${specialMemberList}">
												<c:out value="${specialMember.name}"></c:out>:<input type="checkbox" value="${specialMember.id}" name="specialMember" class="NormalInput">
												---->指定角色:<input type="checkbox" class="NormalInput" id="smDefault${specialMember.id}" onclick="specialMemberdefault('smDefault${specialMember.id}',${specialMember.id});">
														<input type="text" value="" id="${specialMember.id}" name="${specialMember.id}" style="display: none" />
											</c:forEach>
											</td>
										</tr>
										<tr>
											<td></td>
											<td>
												<div>
													<table class="common">
														<tr>
															<td>
																指定特别会员管理员：
															</td>
															<td>
																<textarea rows="10" cols="40" name="specialsManager" id="specialsManager"></textarea>
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</span>
							</fieldset>
							
							<fieldset class="pickList">
								<legend>
									<table cellspacing="0" cellpadding="0" border="0" width="100%" class="common">
										<col width="60"></col>
										<col></col>
										<col width="6"></col>
										<tr>
											<td>
												<b>综合会员</b>
											</td>
											<td>
												<hr width="99%" class="pickList" />
											</td>
											<td>
												<img id="baseinfoMember_img" src="<%=skinPath%>/images1/ctl_detail_Up.gif" style="cursor: hand" onclick="javascript:baseinfoMember_onclick()" />
											</td>
										</tr>
									</table>
								</legend>
								<span id="baseinfoMember">
									<input type="hidden" name="memberType" value="member" />
									<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">
										<tr>
											<td>&nbsp;&nbsp;&nbsp;
											</td>
											<td>
												向全部综合会员发送：&nbsp;
												<input type="checkbox" name="isAllMember" id="isAllMember" value="N" onclick="isAllMembers()" class="NormalInput"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td></td>
											<td>
												指定综合会员：
												<c:forEach var="memberInfo" items="${memberInfoList}">
													<c:out value="${memberInfo.name}"></c:out>:<input type="checkbox" value="${memberInfo.id}" name="memberInfo" class="NormalInput">
													---->指定角色:<input type="checkbox" class="NormalInput" id="mDefault${memberInfo.id}" onclick="memberdefault('mDefault${memberInfo.id}',${memberInfo.id});">
														<input type="text" value="" id="${memberInfo.id}" name="${memberInfo.id}" style="display: none" />
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td></td>
											<td>
												<div>
													<table class="common">
														<tr>
															<td>
																指定综合会员管理员：
															</td>
															<td>
																<textarea rows="10" cols="40" name="manager" id="manager"></textarea>
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</span>
							</fieldset>
							
							<fieldset class="pickList">
								<legend>
									<table cellspacing="0" cellpadding="0" border="0" width="100%" class="common">
										<col width="100"></col>
										<col></col>
										<col width="6"></col>
										<tr>
											<td>
												<b>综合会员客户</b>
											</td>
											<td>
												<hr width="99%" class="pickList" />
											</td>
											<td>
												<img id="baseinfoTrader_img" src="<%=skinPath%>/images1/ctl_detail_Up.gif" style="cursor: hand" onclick="javascript:baseinfoTrader_onclick()" />
											</td>
										</tr>
									</table>
								</legend>
								<span id="baseinfoTrader">
									<input type="hidden" name="traderType" value="trader" />
									<table cellSpacing="0" cellPadding="0" width="100%" border="0" align="center" class="common">
										<tr>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											<td>
												向全部客户发送：&nbsp;
												<input type="checkbox" name="isAllTrader" id="isAllTrader" onclick="isAllTraders()" class="NormalInput"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
										<tr>
											<td></td>
											<td>
												指定综合会员客户：
												<c:forEach var="memberInfo" items="${memberInfoList}">
													<c:out value="${memberInfo.name}"></c:out>:<input type="checkbox" value="${memberInfo.id}" name="trader" class="NormalInput">
												</c:forEach>
											</td>
										</tr>
										<tr>
											<td></td>
											<td>
												<div>
													<table class="common">
														<tr>
															<td>
																指定客户：
															</td>
															<td>
																<textarea rows="10" cols="40" name="traderManager" id="traderManager"></textarea>
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</span>
							</fieldset>
						</td>
					</tr>
				</table>
			</fieldset>
			</form>
			</td>
			</tr>
			</table>
		<br />
		<table width="100%">
			<tr align="center">
				<td align="right" width="40%">
					<div class="st_anbor"><a href="#" onclick="formSubmit();" class="st_an">提交</a></div>
				</td>
				<td align="left">
					<div class="st_anbor"><a href="#" onclick="returnBack();" class="st_an">返回</a></div>
				</td>
			</tr>
		</table>
	</body>
</html>

<%@ include file="/public/footInc.jsp"%>
<script type="text/javascript">

	function returnBack() {
		myForm.action = "${basePath}/tradeManage/announcement/show.action";
		myForm.submit();
	}

	function formSubmit() {
		myForm.submit();
	}
	function isAllMembers() {
		if(myForm.isAllMember.checked) {
		 	var memberInfos = document.myForm.memberInfo;
		 	myForm.manager.value="";
		 	setReadOnly(myForm.manager);
		 	if (memberInfos.length) {
			 	for (i = 0; i < memberInfos.length; i++) {
					memberInfos[i].checked = true;
				}
		 	} else {
		 		document.myForm.memberInfo.checked = true;
		 	}
	    } else {
		  	var memberInfos = document.myForm.memberInfo;
		  	setReadWrite(myForm.manager);
		  	if (memberInfos.length) {
			  	for (i = 0; i < memberInfos.length; i++) {
					memberInfos[i].checked = false;
				}
		  	} else {
		  		document.myForm.memberInfo.checked = false;
		  	}
	   }
	}
	function isAllSpecials() {
		if(myForm.isAllSpecial.checked) {
		 	var specialMembers = document.myForm.specialMember;
		 	myForm.specialsManager.value="";
		 	setReadOnly(myForm.specialsManager);
		 	if (specialMembers.length) {
		 		for (i = 0; i < specialMembers.length; i++) {
					specialMembers[i].checked = true;
				}
		 	} else {
		 		document.myForm.specialMember.checked = true;
		 	}
		 	
	    } else {
		  	var specialMembers = document.myForm.specialMember;
		  	setReadWrite(myForm.specialsManager);
		  	if (specialMembers.length) {
			  	for (i = 0; i < specialMembers.length; i++) {
					specialMembers[i].checked = false;
				}
		  	} else {
		  		document.myForm.specialMember.checked = false;
		  	}
	   }
	}
	
	function specialMemberdefault(smid,id) {
		if(document.getElementById(smid).checked) {
			document.getElementById(id).style.display="";
		} else {
			document.getElementById(id).style.display = "none";
		}
	}
	function memberdefault(mid,id) {
		if(document.getElementById(mid).checked) {
			document.getElementById(id).style.display="";
		} else {
			document.getElementById(id).style.display = "none";
		}
	}
	function isAllTraders() {
		if(myForm.isAllTrader.checked) {
		 	var traders = document.myForm.trader;
		 	myForm.traderManager.value="";
		 	setReadOnly(myForm.traderManager);
		 	document.myForm.isAllTrader.value = "Y";
		 	if(traders.length) {
			 	for (i = 0; i < traders.length; i++) {
					traders[i].checked = true;
				}
		 	} else {
		 		document.myForm.trader.checked = true;
		 	}
	    } else {
		  	var traders = document.myForm.trader;
		  	setReadWrite(myForm.traderManager);
		  	document.myForm.isAllTrader.value = "";
		  	if(traders.length) {
			  	for (i = 0; i < traders.length; i++) {
					traders[i].checked = false;
				}
		  	} else {
		  		document.myForm.trader.checked = false;
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
