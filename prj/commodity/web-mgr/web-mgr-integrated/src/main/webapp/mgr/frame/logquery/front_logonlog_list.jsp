<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>前台登录日志类表</title>
	</head>
	<body  onload="change('${type }');getFocus('operator');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/logquery/queryOperateLogSearch/frontList.action?sortColumns=order+by+operateTime+desc&isQueryDB=true"
								method="post">
								<input type="hidden" name="pageSize" value="${pageInfo.pageSize}">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table4_td_width">
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
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
															</label>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1" align="left">
															交易员代码:&nbsp;
															<label>
																<input type="text" class="input_text" id="operator"
																	name="${GNNT_}primary.operator[allLike]"
																	value="${oldParams['primary.operator[allLike]'] }" maxLength="<fmt:message key='operatorID_q' bundle='${PropsFieldLength}'/>"/>
															</label>
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
											action="${basePath}/logquery/queryOperateLogSearch/frontList.action?sortColumns=order+by+operateTime+desc&isQueryDB=true"
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="operateLog.xls" csvFileName="operateLog.csv"
											showPrint="true" listWidth="100%"
											minHeight="345" style="table-layout:fixed;">
											<ec:row>
												<ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="operator" title="交易员代码" width="8%" style="text-align:left;" ellipsis="true"/>
												<ec:column property="operateIp" title="登录IP" width="12%" style="text-align:left;"/>
												<ec:column property="operateTime" title="登录时间" width="19%" style="text-align:center;">
													<fmt:formatDate value="${operateLog.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
												</ec:column>
												<ec:column property="logCatalog.catalogName" title="操作类型" width="10%" style="text-align:center;" />
												<ec:column property="operateContent" title="操作内容" width="30%" ellipsis="true" style="text-align:center;" alias="[allLike]"/>
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
			function select1() {
					checkQueryDate(frm.beginDate.value,frm.endDate.value);
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