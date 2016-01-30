<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<base target=_self>
<html>
	<head>
		<title>每日交易节设置</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<SCRIPT type="text/javascript">
		jQuery(document).ready(function() {
			jQuery("#frm").validationEngine('attach');
			//修改按钮注册点击事件
			$("#update").click(function(){
				//验证信息
				if(jQuery("#frm").validationEngine('validate')){
					var vaild = affirm("您确定要操作吗？");
					if(vaild){
						//添加信息URL
						var updateDemoUrl = $(this).attr("action");
						//全 URL 路径
						var url = "${basePath}"+updateDemoUrl;
						$("#frm").attr("action",url);
						$("#frm").submit();
					}
				}
			});
		});

		function setDisabled(obj)
		{
		  obj.disabled = true;
		  obj.style.backgroundColor = "#C0C0C0";
		}
		
		function window_onload()
		{		    
		    setTimeout("isOK()",1000);			
		}
		//非交易星期设置
		function isOK(){
					
			var relWeek = "${week}";
		
			if (relWeek != null && relWeek != "") {
				var relWeeks = relWeek.split(",");
			    var weeks;
				for (j = 0; j < relWeeks.length; j++) {
					if ("1" == relWeeks[j]) {
						weeks = document.forms(0).weeks1;
					}if ("2" == relWeeks[j]) {
						weeks = document.forms(0).weeks2;
					}
					if ("3" == relWeeks[j]) {
						weeks = document.forms(0).weeks3;
					}
					if ("4" == relWeeks[j]) {
						weeks = document.forms(0).weeks4;
					}
					if ("5" == relWeeks[j]) {
						weeks = document.forms(0).weeks5;
					}
					if ("6" == relWeeks[j]) {
						weeks = document.forms(0).weeks6;
					}
					if ("7" == relWeeks[j]) {
						weeks = document.forms(0).weeks7;
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
		</SCRIPT>
	</head>
	<body onLoad="return window_onload()">
		<form id="frm" method="post" enctype="multipart/form-data" action="" targetType="hidden">
			<div class="div_cx">
				<table border="0" width="800" align="center">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示 :设置每日交易节
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table border="0" width="700" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												每日交易节设置
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
										<div>
											<table border="1" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
											  <tr>							
												   <td align="right" width="118">								
															星期一：								
													</td>								
												 <c:forEach items="${pageInfo.result }" var="mapWeek">								
													<c:if test="${mapWeek.weekDay==2}">											      										
													 <td align="left" >								
														<input type="checkbox" name="weeks2" value="${mapWeek.sectionID}" <c:if test="${mapWeek.status=='0'}">checked</c:if> style="border: 0px"/>${mapWeek.sectionID}																	
													 </td>									
													</c:if>								
												 </c:forEach>							    																		
											 </tr>
											 <tr>							
												   <td align="right" width="118">								
															星期二：								
													</td>								
												 <c:forEach items="${pageInfo.result }" var="mapWeek">								
													<c:if test="${mapWeek.weekDay==3}">											      										
													 <td align="left" >								
														<input type="checkbox" name="weeks3" value="${mapWeek.sectionID}" <c:if test="${mapWeek.status=='0'}">checked</c:if> style="border: 0px"/>${mapWeek.sectionID}																	
													 </td>									
													</c:if>								
												 </c:forEach>							    																		
											 </tr>
											 <tr>							
												   <td align="right" width="118">								
															星期三：								
													</td>								
												 <c:forEach items="${pageInfo.result }" var="mapWeek">								
													<c:if test="${mapWeek.weekDay==4}">											      										
													 <td align="left" >								
														<input type="checkbox" name="weeks4" value="${mapWeek.sectionID}" <c:if test="${mapWeek.status=='0'}">checked</c:if> style="border: 0px"/>${mapWeek.sectionID}																	
													 </td>									
													</c:if>								
												 </c:forEach>							    																		
											 </tr>
											 <tr>							
												   <td align="right" width="118">								
															星期四：								
													</td>								
												 <c:forEach items="${pageInfo.result }" var="mapWeek">								
													<c:if test="${mapWeek.weekDay==5}">											      										
													 <td align="left" >								
														<input type="checkbox" name="weeks5" value="${mapWeek.sectionID}" <c:if test="${mapWeek.status=='0'}">checked</c:if> style="border: 0px"/>${mapWeek.sectionID}																	
													 </td>									
													</c:if>								
												 </c:forEach>							    																		
											 </tr>
											 <tr>							
												   <td align="right" width="118">								
															星期五：								
													</td>								
												 <c:forEach items="${pageInfo.result }" var="mapWeek">								
													<c:if test="${mapWeek.weekDay==6}">											      										
													 <td align="left" >								
														<input type="checkbox" name="weeks6" value="${mapWeek.sectionID}" <c:if test="${mapWeek.status=='0'}">checked</c:if> style="border: 0px"/>${mapWeek.sectionID}																	
													 </td>									
													</c:if>								
												 </c:forEach>							    																		
											 </tr>
											 <tr>							
												   <td align="right" width="118">								
															星期六：								
													</td>								
												 <c:forEach items="${pageInfo.result }" var="mapWeek">								
													<c:if test="${mapWeek.weekDay==7}">											      										
													 <td align="left" >								
														<input type="checkbox" name="weeks7" value="${mapWeek.sectionID}" <c:if test="${mapWeek.status=='0'}">checked</c:if> style="border: 0px"/>${mapWeek.sectionID}																	
													 </td>									
													</c:if>								
												 </c:forEach>							    																		
											 </tr>
											<tr>							
												   <td align="right" width="118">								
															星期日：								
													</td>								
												 <c:forEach items="${pageInfo.result }" var="mapWeek">								
													<c:if test="${mapWeek.weekDay==1}">											      										
													 <td align="left" >								
														<input type="checkbox" name="weeks1" value="${mapWeek.sectionID}" <c:if test="${mapWeek.status=='0'}">checked</c:if> style="border: 0px"/>${mapWeek.sectionID}																	
													 </td>									
													</c:if>								
												 </c:forEach>							    																		
											 </tr>													
																																																							
												<tr>
													<td colspan="4" align="center">                                                  
														<rightButton:rightButton name="提交" onclick="" className="btn_sec" action="/timebargain/tradeparams/updateTradeDaySection.action" id="update"></rightButton:rightButton>
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
		</form>
	</body>
</html>