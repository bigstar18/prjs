<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
   String nowDate = Tools.fmtDate(new Date());
%>
<html>
	<head>
		<title>交易商总账单合计</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//查看信息详情
			function fundsDetail(firmId){
				var url = "${basePath}/finance/firmfunds/fundsdetail.action?firmId="+firmId;
				showDialog(url, "", 400, 500);
			}
			//执行查询列表
			function dolistquery() {

				 var startDate = document.getElementById("beginDate").value;
				 var endDate =  document.getElementById("endDate").value;

				  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
				  {
					if(startDate == ""){
						alert("开始日期不能为空！");
						frm.beginDate.focus();
						return false;
						
					}
					if(endDate == ""){
						alert("结束日期不能为空！");
						frm.endDate.focus();
						return false;
						
					}
					if ( startDate > '<%=nowDate%>' ) { 
					        alert("开始日期不能大于当天日期!"); 
					        frm.beginDate.focus();
					        return false; 
					} 
					if ( startDate > endDate ) { 
				        alert("开始日期不能大于结束日期!"); 
				        return false; 
				    } 
				  }
				frm.submit();
			}
			
		//-->
		</SCRIPT>
	</head>
	<body>
	
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/finance/firmfunds/clientLedgerSum.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="width: 1000">
													<tr>
													<td class="table3_td_1" align="left" >
															&nbsp;&nbsp;&nbsp;开始日期:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="beginDate" name="beginDate"
																	class="wdate" maxlength="10"
																	name="beginDate"
																	value='${beginDate}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1tjcx" align="left">
														&nbsp;&nbsp;&nbsp;结束日期:&nbsp;
															<label>
																<input type="text" style="width: 120px" id="endDate" name="endDate"
																	class="wdate" maxlength="10"
																	name="endDate"
																	value='${endDate}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
														交易商代码:&nbsp;
															<label>
																<input type="text" class="validate[maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />]] input_text" id="id"  name="firmId" value="${firmId}" />
															</label>
														</td>
														<td class="table3_td_1" align="left" >
														&nbsp;&nbsp;系统名称:&nbsp;
															<label>
																<select type="text" class="input_text" id="moduleId"
																	name="moduleId">
																	<option value="">全部</option>
																	<c:forEach var="list" items="${moduleList}">
																		<option value="${list.moduleId}">${list.name }</option>
																	</c:forEach>
																</select>
															</label>
														</td>
														<script>
															document.getElementById("moduleId").value="${moduleId}";
														</script>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>查询</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">重置</button>
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
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="listValue" var="map"
											action="${basePath}/finance/firmfunds/clientLedgerSum.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="clientLedger.xls" csvFileName="clientLedger.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column property="FIRMID" title="交易商代码" style="text-align:center;" ellipsis="true"/>
												<ec:column property="LASTBALANCE" title="期初余额"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${map['LASTBALANCE'] }" pattern="#,##0.00"/>
												</ec:column>
												<c:set var="moduleMoney" value="0"></c:set>
												<c:forEach items="${fieldList}" var="lf">
												<c:set var="flag" value="-1"></c:set>
												<c:if test="${lf.fieldSign==-1}"><c:set var="name" value="-${lf.name}"></c:set></c:if>
												<c:if test="${lf.fieldSign==1}"><c:set var="name" value="+${lf.name}"></c:set></c:if>
												<c:set var="code" value="${lf.code}"></c:set>
									 				<ec:column property="_" title="${name}" style="text-align:right;" width="120">
														<fmt:formatNumber value="${map[code]}" pattern="#,##0.00"/>
													</ec:column>
									 			</c:forEach>
									 			<c:if test="${moduleId!=null&&moduleId!=''}">
									 				<ec:column property="_" title="+其他交易系统"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${map['OTHER']}" pattern="#,##0.00"/>
												</ec:column>
									 			</c:if>
												<ec:column property="TODAYBALANCE" title="期末余额"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${map['TODAYBALANCE']}" pattern="#,##0.00"/>
												</ec:column>
												<c:if test="${moduleId==null||moduleId==''||moduleId=='15'}">
												<ec:column property="LASTWARRANTY" title="期初担保金"  style="text-align:right;" width="120">
													<fmt:formatNumber value="${map['LASTWARRANTY']}" pattern="#,##0.00"/>
												</ec:column>
												<ec:column property="TODAYWARRANTY" title="期末担保金" style="text-align:right;" width="120">
													<fmt:formatNumber value="${map['TODAYWARRANTY']}" pattern="#,##0.00"/>
										  		</ec:column>
										  		</c:if>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>