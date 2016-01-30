<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>日志列表</title>
		<script type="text/javascript" src="<%=publicPath%>/js/jquery-1.6.min.js"></script>
	</head>
	<body  onload="change('${type }');getFocus('operator');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/log/list.action?sortColumns=order+by+id&isQueryDB=true"
								method="post">
								<input type="hidden" name="pageSize" value="${pageInfo.pageSize}">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
													<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;查询范围:&nbsp;
															<label>
																<select name="type" size="1" id="type"
																	style="width: 120" onchange="changeOn()">
																	<option value="D">
																		当前
																	</option>
																	<option value="H">
																		历史
																	</option>
																</select>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															开始日期:&nbsp;
															<label>
																<input type="text" class="input_text wdate" id="beginDate"
																	name="${GNNT_}primary.operateTime[>=][date]"
																	value="${oldParams['primary.operateTime[>=][date]']}" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															结束日期:&nbsp;
															<label>
																<input type="text" class="input_text wdate" id="endDate"
																	name="${GNNT_}primary.operateTime[<=][datetime]"
																	value="${oldParams['primary.operateTime[<=][datetime]'] }" 
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
															</label>
														</td>
														</tr>
														<tr>
														<td class="table3_td_1" align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作人:&nbsp;
															<label>
																<input type="text" class="input_text" id="operator"
																	name="${GNNT_}primary.operator[allLike]"
																	value="${oldParams['primary.operator[allLike]'] }"  maxLength="<fmt:message key='operator_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<!-- 
														<td align="left" class="table3_td_1">
															操作模块:&nbsp;
															<select id="moudleId"
																name="${GNNT_}primary.logCatalog.tradeModule.moduleId[=][int]"
																class="input_text" onchange="getLogCatalogs()">
																<option value="">
																	全部
																</option>
															</select>
															<script type="text/javascript">
																document.getElementById('moudleId').value="${oldParams['primary.logCatalog.logMoudle.moudleID[=][int]'] }";
															</script>
														</td>
														 -->
														<td align="left" class="table3_td_1" id="opetype">
															操作类型:&nbsp;
															<select id="catalogID"
																name="${GNNT_}primary.logCatalog.catalogID[=][int]" value="${oldParams['primary.logCatalog.catalogID[=][int]'] }" class="input_text">
																<option value="">
																	全部
																</option>
																<c:forEach items="${logList}" var="opetype">
																	<c:if test="${oldParams['primary.logCatalog.catalogID[=][int]']== opetype.catalogID}">
																		<option value="${opetype.catalogID }" selected="selected">${opetype.catalogName }</option>
																	</c:if>
																	<c:if test="${oldParams['primary.logCatalog.catalogID[=][int]']!= opetype.catalogID}">
																		<option value="${opetype.catalogID }">${opetype.catalogName }</option>
																	</c:if>
																</c:forEach>
															</select>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=select1();>
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick=myReset();>
																重置
															</button>
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
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="operateLog" 
											action="${basePath}/log/list.action?sortColumns=order+by+id&isQueryDB=true"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="operateLog.xls" csvFileName="operateLog.csv"
											showPrint="true" listWidth="100%"
											minHeight="345" style="table-layout:fixed;">
											<ec:row>
												<ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="operator" title="操作人" width="10%" ellipsis="true"  style="text-align:left;"/>
												<ec:column property="operateIp" title="操作人Ip" width="10%" style="text-align:left;"/>
												<ec:column property="operateTime" title="操作时间" width="13%" style="text-align:left;" sortable="true" filterable="false" >
													<fmt:formatDate value="${operateLog.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="logCatalog.tradeModule.cnName" title="操作模块" width="10%" >
												</ec:column>
												 <ec:column property="operatorType" title="操作人类型" width="10%">
													${com_operatorTypeMap[operateLog.operatorType]}
												</ec:column>
												<ec:column property="logCatalog.catalogName" title="操作类型" width="10%" style="text-align:center;" ellipsis="true" />
												<ec:column property="operateResult" title="操作结果" width="6%">
													<c:if test="${operateLog.operateResult ==1}">
														操作成功
													</c:if>
													<c:if test="${operateLog.operateResult ==0}">
														操作失败
													</c:if>
												</ec:column>
												<ec:column property="operateContent" title="操作内容" width="30%" ellipsis="true" style="text-align:center;"/>
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
			<!-- 编辑状态所用模板 -->
			<textarea id="ecs_t_status" rows="" cols="" style="display: none">
			<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="status">
				<ec:options items="CUSTOMER_STATUS" />
			</select>
		</textarea>
		<SCRIPT type="text/javascript">
			
			function select1(){
				frm.submit();
			}
			
			function changeOn(){
				var todayHis=document.getElementById("type").value;
				change(todayHis);
			}
			function change(value){
				if( ${oldParams['primary.operateTime[>=][date]']==null}&& ${oldParams['primary.operateTime[<=][datetime]']==null}){
					frm.beginDate.value=new Date().format("yyyy-MM-dd");
					frm.endDate.value=new Date().format("yyyy-MM-dd");
				}
				if(value=='D')
				{
				  frm.type.value="D";
				}
				else if(value=='H')
				{
				   frm.type.value="H";
				}
			}
		</SCRIPT>
	</body>
</html>