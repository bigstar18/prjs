<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title></title>
		<script language="JavaScript" src="<%=basePath%>/public/global.js"></script>
		<script language="JavaScript" src="<%=basePath%>/public/open.js"></script>
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
		<script language="JavaScript">
		function window_onload() {
			setTimeout("isOK()", 10);
		}
		function isOK() {
			var relWeek = "${notTradeDayVO.week}";
			if (relWeek != null && relWeek != "") {
				var relWeeks = relWeek.split(",");
				var weeks;
				for (j = 0; j < relWeeks.length; j++) {
					if ("1" == relWeeks[j]) {
						weeks = document.forms(0).week1;
					}
					if ("2" == relWeeks[j]) {
						weeks = document.forms(0).week2;
					}
					if ("3" == relWeeks[j]) {
						weeks = document.forms(0).week3;
					}
					if ("4" == relWeeks[j]) {
						weeks = document.forms(0).week4;
					}
					if ("5" == relWeeks[j]) {
						weeks = document.forms(0).week5;
					}
					if ("6" == relWeeks[j]) {
						weeks = document.forms(0).week6;
					}
					if ("7" == relWeeks[j]) {
						weeks = document.forms(0).week7;
					}
					try {
						if (weeks) {
							for (i = 0; i < weeks.length; i++) {
								var week = weeks[i];
								week.checked = false;
								setDisabled(week);
							}
						}
					} catch (e) {
						alert("无交易节！");
					}
				}
			}
		}
		
		//save
		function save_onclick() {
			if (confirm("您确定要提交吗？")) {
				document.forms(0).submit();
				document.forms(0).update.disabled = true;
			}
		}
		
		function suffixNamePress() {
			if (marketForm.addedTax.value < 1 && (event.keyCode >= 46 && event.keyCode <= 57)) {
				event.returnValue = true;
			} else {
				event.returnValue = false;
			}
		}
		</script>
	</head>
	<body onLoad="return window_onload()" onkeypress="keyEnter(event.keyCode);">
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<form action="${basePath}/tradeManage/tradingParameter/updateDaySection.action" method="post" class="form" targetType="hidden">
						<fieldset class="pickList">
							<legend class="common">
								<b>每日交易节设置 </b>
							</legend>
							<table width="100%" border="0" align="center" class="common"
								cellpadding="0" cellspacing="2">
								<!-- 基本信息 -->
								<tr class="common">
									<td colspan="4">
										<span id="baseinfo">
											<table cellSpacing="4" cellPadding="4" width="100%" border="1" align="center" class="common">
												<tr>
													<td align="right" width="118">
														星期一：
													</td>
													<c:forEach items="${mapWeek['2']}" var="list2">
														<td align="left">
															<input type="checkbox" name="week2" value="${list2.daySectionPK.sectionId}" 
															<c:if test="${list2.status == 0}">
																checked 
															</c:if> style="border: 0px" />
															${list2.daySectionPK.sectionId}&nbsp;
														</td>
													</c:forEach>
												</tr>
												<tr>
													<td align="right" width="118">
														星期二：
													</td>
													<c:forEach items="${mapWeek['3']}" var="list3">
														<td align="left">
															<input type="checkbox" name="week3" value="${list3.daySectionPK.sectionId}" 
																<c:if test="${list3.status == 0}">
																	checked 
																</c:if>
																style="border: 0px" />
																${list3.daySectionPK.sectionId}&nbsp;
														</td>
													</c:forEach>
												</tr>
												<tr>
													<td align="right" width="118">
														星期三：
													</td>
													<c:forEach items="${mapWeek['4']}" var="list4">
														<td align="left">
															<input type="checkbox" name="week4" value="${list4.daySectionPK.sectionId}" 
																<c:if test="${list4.status == 0}">
																	checked 
																</c:if>
																style="border: 0px" />
																${list4.daySectionPK.sectionId}&nbsp;
														</td>
													</c:forEach>
												</tr>
												<tr>
													<td align="right" width="118">
														星期四：
													</td>
													<c:forEach items="${mapWeek['5']}" var="list5">
														<td align="left">
															<input type="checkbox" name="week5" value="${list5.daySectionPK.sectionId}" 
																<c:if test="${list5.status == 0}">
																	checked 
																</c:if>
																style="border: 0px" />
																${list5.daySectionPK.sectionId}&nbsp;
														</td>
													</c:forEach>
												</tr>
												<tr>
													<td align="right" width="118">
														星期五：
													</td>
													<c:forEach items="${mapWeek['6']}" var="list6">
														<td align="left">
															<input type="checkbox" name="week6" value="${list6.daySectionPK.sectionId}" 
																<c:if test="${list6.status == 0}">
																	checked 
																</c:if>
																style="border: 0px" />
																${list6.daySectionPK.sectionId}&nbsp;
														</td>
													</c:forEach>
												</tr>
												<tr>
													<td align="right" width="118">
														星期六：
													</td>
														<c:forEach items="${mapWeek['7']}" var="list7">
														<td align="left">
															<input type="checkbox" name="week7" value="${list7.daySectionPK.sectionId}" 
																<c:if test="${list7.status == 0}">
																	checked 
																</c:if>
																style="border: 0px" />
																${list7.daySectionPK.sectionId}&nbsp;
														</td>
													</c:forEach>
												</tr>
												<tr>
													<td align="right" width="118">
														星期日：
													</td>
													<c:forEach items="${mapWeek['1']}" var="list1">
														<td align="left">
															<input type="checkbox" name="week1" value="${list1.daySectionPK.sectionId}" 
																<c:if test="${list1.status == 0}">
																	checked 
																</c:if>
																style="border: 0px" />
																${list1.daySectionPK.sectionId}&nbsp;
														</td>
													</c:forEach>
												</tr>
											</table>
										</span>
									</td>
								</tr>
								<tr>
									<td colspan="5" height="3">
									</td>
								</tr>
								<tr>
									<td align="center">
										<input name="update" type="button" class="button" onclick="save_onclick();" value="提交">
									</td>
									<td>
										<input type="button" class="button"  onclick="window.close()" value="关闭">
									</td>
								</tr>
							</table> 
						</fieldset>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
<%@ include file="/public/footInc.jsp"%>