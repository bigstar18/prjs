<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/common/public/common.jsp"%>
<%@ page import="gnnt.MEBS.member.ActiveUser.ActiveUserManager"%>
<body>
	<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cxtj">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;查询条件
						</div>
						<div class="div_tj">
						<form name="frm"
							action="<%=basePath%>/userOnLine/commonUserForcedOffline.action"
							method="post">
							<input type="hidden" name="orderField" value="${orderFiled}">
							<input type="hidden" name="orderDesc" value="${orderType}">
							<input type="hidden" name="pageSize"
								value="<c:out value="${pageInfo.pageSize}"/>">
							<input type="hidden" id="pageNo" name="pageNo"
								value="<c:out value="${pageInfo.pageNo}"/>">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td height="60">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td" align="right">
														用户代码:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input name="userId" type="text" class="input_text"
															value="${userId }" />
														</label>
													</td>
													<td class="table3_td" align="right">
														&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															&nbsp;
														</label>
													</td>
													<td class="table3_td" align="right">
															<button  class="btn_sec" onclick="doQuery()">查询</button>&nbsp;
													</td>
													<td class="table3_td2" align="left">
														<button  class="btn_cz" onclick="resetInfo()">重置</button>
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
							<c:forEach items="${onLineUsersMap }" var="onLineUsers">
								<c:set var="totalNumber" value="${onLineUsers.key }" />
								<c:set var="usersList" value="${onLineUsers.value }" />
							</c:forEach>
							<font style="font-size: 12px;">在线管理员总数:${totalNumber }</font>
							<br>
							<div class="div_gn">
							<button onclick="deleteRec(frm,tb,'ck')"
										id='updateUserOnLine' class="anniu_btnmax">
								强制下线
							</button>
							<input type="hidden" name="opt">
						   </div>
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%" height="300">
								<tHead>
									<tr height="25" align=center>
										<td class="panel_tHead_LB">
											&nbsp;
										</td>
										<td class="panel_tHead_MB" align=left>
											<input class=checkbox_1 type="checkbox" id="checkAll"
												onclick="selectAll(tb,'ck')">
										</td>
										<td class="panel_tHead_MB" align=left>
											用户代码
										</td>
										<td class="panel_tHead_MB" align=left>
											登录时间
										</td>
										<td class="panel_tHead_MB" align=left>
											登录IP
										</td>
										<td class="panel_tHead_RB">
											&nbsp;
										</td>
									</tr>
								</tHead>
								<tBody>
									<c:forEach items="${usersList }" var="user">
										<tr onclick="selectTr();" align=center height="25">
											<td class="panel_tBody_LB">
												&nbsp;
											</td>
											<td class="underLine" align=left>
												<input name="ck" type="checkbox" value="${user.sessionId }">
											</td>
											<td class="underLine" align=left>
												${user.userId }
											</td>
											<td class="underLine" align=left>
												${user.logonTime }
											</td>
											<td class="underLine" align=left>
												${user.logonIp }
											</td>
											<td class="panel_tBody_RB">
												&nbsp;
											</td>
										</tr>
									</c:forEach>
								</tBody>
								<tFoot>
									<tr height="100%">
										<td class="panel_tBody_LB">
											&nbsp;
										</td>
										<td colspan="4">
											&nbsp;
										</td>
										<td class="panel_tBody_RB">
											&nbsp;
										</td>
									</tr>
									<tr height="22">
										<td class="panel_tBody_LB">
											&nbsp;
										</td>
										<td colspan="4" align="right" class="pagerext">
											<%
												//@ include file="../public/pagerInc.jsp"
											%>
										</td>
										<td class="panel_tBody_RB">
											&nbsp;
										</td>
									</tr>
									<tr height="22">
										<td class="panel_tFoot_LB">
											&nbsp;
										</td>
										<td class="panel_tFoot_MB" colspan="4"></td>
										<td class="panel_tFoot_RB">
											&nbsp;
										</td>
									</tr>
								</tFoot>
							</table>
						</div>
					</td>
				</tr>
			</table>
			</div>
</body>
<script>
function doQuery() {
	frm.action = "<%=basePath%>/userOnLine/commonUserOnLineList.action";
	frm.submit();
}
function resetInfo() {
	document.getElementById("userId").value = "";
	frm.submit();
}
</script>