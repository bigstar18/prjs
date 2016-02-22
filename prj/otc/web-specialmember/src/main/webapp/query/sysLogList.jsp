
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<html xmlns:MEBS>
	<head>
		<title>系统日志查询</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
		<IMPORT namespace="MEBS"
			implementation="${basePath}/common/jslib/calendar.htc">
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath }/query/querySysLogSearch/list.action?sortName=operateTime&sortOrder=true"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout: fixed">
													<input type="hidden" name="queryType" id="queryType"
														value="H">
													<tr>
														<td class="table3_td_1" align="left">
															开始日期:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="beginDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.operateTime[>=][date]"
																	value='${oldParams["primary.operateTime[>=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															结束日期:&nbsp;
															<label>
																<input type="text" style="width: 100px" id="endDate"
																	class="wdate" maxlength="10"
																	name="${GNNT_}primary.operateTime[<=][date]"
																	value='${oldParams["primary.operateTime[<=][date]"]}'
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
															</label>
														</td>
														<td class="table3_td_1" align="left">
															操作人:&nbsp;
															<label>
																<input type="text" name="${GNNT_}primary.operator[like]"
																	id="operateType"
																	value="${oldParams['primary.operator[like]'] }"
																	class="input_textmin" />
															</label>
														</td>
														<td align="left" class="table3_td_1">
															操作类型：&nbsp;
															<select id="moudleId"
																name="${GNNT_}primary.operateType[=][long]"
																class="input_textmin">
																<option value="">
																	请选择
																</option>
																<c:forEach items="${logCataLogList}" var="logCataLog">
																	<option value="${logCataLog.cataLogId}">
																		${logCataLog.catalogName }
																	</option>
																</c:forEach>
															</select>
															<script type="text/javascript">
frm.moudleId.value='${oldParams['primary.operateType[=][long]'] }';
													</script>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onClick="search1()">
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onClick="myReset()">
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
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="globalLog"
											action="${basePath }/query/querySysLogSearch/list.action?sortName=operateTime&sortOrder=true"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed;">
											<ec:row style="text-align:center;" rowId="${globalLog.id}">
												<ec:column width="4%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="operator[like]" title="操作人" width="8%"
													style="text-align:left; " value="${globalLog.operator}"
													ellipsis="true" />
												<ec:column property="operateIp[like]" title="操作人IP" width="8%"
													style="text-align:left; " value="${globalLog.operateIp}"
													ellipsis="true" />
												<ec:column property="operateTime[=][date]" title="操作时间"
													width="18%" style="text-align:left; ">
													${datefn:formatdate(globalLog.operateTime)}
												</ec:column>
												<ec:column property="logCataLog.catalogName[=][String]"
													title="操作类型" width="20%" style="text-align:left;"
													value="${globalLog.operateName}" ellipsis="true" />
												<ec:column property="operateContent[=][String]" title="操作内容"
													width="40%" style="text-align:left;"
													value="${globalLog.operateContent}" ellipsis="true" />
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<!-- 编辑和过滤所使用的 通用的文本框模板 -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>
	</body>
</html>

<SCRIPT type="text/javascript">
		function search1(){
			checkTotalQueryDate(frm.beginDate.value,frm.endDate.value);
		}
		function forView(content, id) {
			document.getElementById(id).title = content;
		}
		</SCRIPT>