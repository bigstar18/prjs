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
						alert("�޽��׽ڣ�");
					}
				}
			}
		}
		
		//save
		function save_onclick() {
			if(!isFormChanged(null, null)){
				alert("û���޸�����");
				return false;
			}
			var relWeek = "${notTradeDayVO.week}";
			if (relWeek != null && relWeek != "") {
				var relWeeks = relWeek.split(",");
				var flag1 = false;
				for (j = 0; j < relWeeks.length; j++) {
					if ("1" == relWeeks[j]) {
						flag1 = true;
					}
				}
				if(flag1 == false){
					var weeks = document.forms(0).week1;
					for (i = 0; i < weeks.length; i++) {
						var week = weeks[i];
						if(week.checked == true){
							flag1 = true;
						}
					}
					if(flag1 == false){
						alert("�����ձ����н��׽ڣ�");
						return false;
					}
				}
				
				var flag2 = false;
				for (j = 0; j < relWeeks.length; j++) {
					if ("2" == relWeeks[j]) {
						flag2 = true;
					}
				}
				if(flag2 == false){
					var weeks = document.forms(0).week2;
					for (i = 0; i < weeks.length; i++) {
						var week = weeks[i];
						if(week.checked == true){
							flag2 = true;
						}
					}
					if(flag2 == false){
						alert("�����ձ����н��׽ڣ�");
						return false;
					}
				}
				
				var flag3 = false;
				for (j = 0; j < relWeeks.length; j++) {
					if ("3" == relWeeks[j]) {
						flag3 = true;
					}
				}
				if(flag3 == false){
					var weeks = document.forms(0).week3;
					for (i = 0; i < weeks.length; i++) {
						var week = weeks[i];
						if(week.checked == true){
							flag3 = true;
						}
					}
					if(flag3 == false){
						alert("�����ձ����н��׽ڣ�");
						return false;
					}
				}
				
				var flag4 = false;
				for (j = 0; j < relWeeks.length; j++) {
					if ("4" == relWeeks[j]) {
						flag4 = true;
					}
				}
				if(flag4 == false){
					var weeks = document.forms(0).week4;
					for (i = 0; i < weeks.length; i++) {
						var week = weeks[i];
						if(week.checked == true){
							flag4 = true;
						}
					}
					if(flag4 == false){
						alert("�����ձ����н��׽ڣ�");
						return false;
					}
				}
				
				var flag5 = false;
				for (j = 0; j < relWeeks.length; j++) {
					if ("5" == relWeeks[j]) {
						flag5 = true;
					}
				}
				if(flag5 == false){
					var weeks = document.forms(0).week5;
					for (i = 0; i < weeks.length; i++) {
						var week = weeks[i];
						if(week.checked == true){
							flag5 = true;
						}
					}
					if(flag5 == false){
						alert("�����ձ����н��׽ڣ�");
						return false;
					}
				}
				
				var flag6 = false;
				for (j = 0; j < relWeeks.length; j++) {
					if ("6" == relWeeks[j]) {
						flag6 = true;
					}
				}
				if(flag6 == false){
					var weeks = document.forms(0).week6;
					for (i = 0; i < weeks.length; i++) {
						var week = weeks[i];
						if(week.checked == true){
							flag6 = true;
						}
					}
					if(flag6 == false){
						alert("�����ձ����н��׽ڣ�");
						return false;
					}
				}
				
				var flag7 = false;
				for (j = 0; j < relWeeks.length; j++) {
					if ("7" == relWeeks[j]) {
						flag7 = true;
					}
				}
				if(flag7 == false){
					var weeks = document.forms(0).week7;
					for (i = 0; i < weeks.length; i++) {
						var week = weeks[i];
						if(week.checked == true){
							flag7 = true;
						}
					}
					if(flag7 == false){
						alert("�����ձ����н��׽ڣ�");
						return false;
					}
				}
			}
			if (affirm("��ȷ��Ҫ�ύ��")) {
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
								<b>ÿ�ս��׽����� </b>
							</legend>
							<table width="100%" border="0" align="center" class="common"
								cellpadding="0" cellspacing="2">
								<!-- ������Ϣ -->
								<tr class="common">
									<td colspan="4">
										<span id="baseinfo">
											<table cellSpacing="4" cellPadding="4" width="100%" border="1" align="center" class="common">
													<tr>
													<td align="right" width="118">
													����ʱ��:
													</td>
													<c:forEach items="${tradeTimeList }" var="tradeTime">
														<c:if test="${tradeTime.sectionId == 1 }">
															<td align="left">
																${tradeTime.startTime} - ${tradeTime.endTime}
															</td>
														</c:if>
														<c:if test="${tradeTime.sectionId == 2 }">
															<td align="left">
																${tradeTime.startTime} - ${tradeTime.endTime}
															</td>
														</c:if>
														<c:if test="${tradeTime.sectionId == 3 }">
															<td align="left">
																${tradeTime.startTime} - ${tradeTime.endTime}
															</td>
														</c:if>
														<c:if test="${tradeTime.sectionId == 4 }">
															<td align="left">
																${tradeTime.startTime} - ${tradeTime.endTime}
															</td>
														</c:if>
													</c:forEach>
													</tr>
													<tr>
														<td align="right" width="118">
															����һ��
														</td>
														<c:forEach items="${daySectionList }" var="daySection">
															<c:if test="${daySection.weekDay == 2 }">
																<td align="left">
																	<input type="checkbox" name="week${daySection.weekDay }" value="${daySection.sectionId}" 
																	<c:if test="${daySection.status == 0}">
																		checked 
																	</c:if> style="border: 0px" />
																	���׽�${daySection.sectionId}&nbsp;
																</td>
															</c:if>
														</c:forEach>
													</tr>
													<tr>
														<td align="right" width="118">
															���ڶ���
														</td>
														<c:forEach items="${daySectionList }" var="daySection">
															<c:if test="${daySection.weekDay == 3 }">
																<td align="left">
																	<input type="checkbox" name="week${daySection.weekDay }" value="${daySection.sectionId}" 
																	<c:if test="${daySection.status == 0}">
																		checked 
																	</c:if> style="border: 0px" />
																	���׽�${daySection.sectionId}&nbsp;
																</td>
															</c:if>
														</c:forEach>
													</tr>
													<tr>
														<td align="right" width="118">
															��������
														</td>
														<c:forEach items="${daySectionList }" var="daySection">
															<c:if test="${daySection.weekDay == 4 }">
																<td align="left">
																	<input type="checkbox" name="week${daySection.weekDay }" value="${daySection.sectionId}" 
																	<c:if test="${daySection.status == 0}">
																		checked 
																	</c:if> style="border: 0px" />
																	���׽�${daySection.sectionId}&nbsp;
																</td>
															</c:if>
														</c:forEach>
													</tr>
													<tr>
														<td align="right" width="118">
															�����ģ�
														</td>
														<c:forEach items="${daySectionList }" var="daySection">
															<c:if test="${daySection.weekDay == 5 }">
																<td align="left">
																	<input type="checkbox" name="week${daySection.weekDay }" value="${daySection.sectionId}" 
																	<c:if test="${daySection.status == 0}">
																		checked 
																	</c:if> style="border: 0px" />
																	���׽�${daySection.sectionId}&nbsp;
																</td>
															</c:if>
														</c:forEach>
													</tr>
													<tr>
														<td align="right" width="118">
															�����壺
														</td>
														<c:forEach items="${daySectionList }" var="daySection">
															<c:if test="${daySection.weekDay == 6 }">
																<td align="left">
																	<input type="checkbox" name="week${daySection.weekDay }" value="${daySection.sectionId}" 
																	<c:if test="${daySection.status == 0}">
																		checked 
																	</c:if> style="border: 0px" />
																	���׽�${daySection.sectionId}&nbsp;
																</td>
															</c:if>
														</c:forEach>
													</tr>
													<tr>
														<td align="right" width="118">
															��������
														</td>
														<c:forEach items="${daySectionList }" var="daySection">
															<c:if test="${daySection.weekDay == 7 }">
																<td align="left">
																	<input type="checkbox" name="week${daySection.weekDay }" value="${daySection.sectionId}" 
																	<c:if test="${daySection.status == 0}">
																		checked 
																	</c:if> style="border: 0px" />
																	���׽�${daySection.sectionId}&nbsp;
																</td>
															</c:if>
														</c:forEach>
													</tr>
													<tr>
														<td align="right" width="118">
															�����գ�
														</td>
														<c:forEach items="${daySectionList }" var="daySection">
															<c:if test="${daySection.weekDay == 1 }">
																<td align="left">
																	<input type="checkbox" name="week${daySection.weekDay }" value="${daySection.sectionId}" 
																	<c:if test="${daySection.status == 0}">
																		checked 
																	</c:if> style="border: 0px" />
																	���׽�${daySection.sectionId}&nbsp;
																</td>
															</c:if>
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
										<input name="update" type="button" class="btn_sec"  onclick="save_onclick();" value="�ύ">
									</td>
									<td>
										<input type="button" class="btn_sec"  onclick="window.close()" value="�ر�">
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