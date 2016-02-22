<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>系统用户浏览</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/tradeManage/tradingParameter/searchList.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															交易节名称:&nbsp;
															<label>
																<input type="text"
																	class="input_text" name="${GNNT_}name[like]"
																value="${oldParams['name[like]'] }" id="sectionId" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="submitMember()">
																查询
															</button>&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset()">
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
						<!-- <div class="div_gn">
							<button class="anniu_btn" onclick="add()" id="addTradeTime">
								添加
							</button>
							<button class="anniu_btn" id='deleteTradeTime' onclick="deleteByCheckBox()">
								删除
							</button>
						</div> -->

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}"
											var="tradeTime"
											action="${basePath}/tradeManage/tradingParameter/searchList.action"
											minHeight="345" height="460" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">
											<ec:row recordKey="${tradeTime.sectionId}">
												<ec:column width="3%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="name[like]" title="交易节名称" width="19%"
													style="text-align:left;" value="${tradeTime.name}">
												</ec:column>
												<ec:column property="startTime[like]" title="交易开始时间"
													width="13%" style="text-align:left;"
													value="${tradeTime.startTime}" />
												<ec:column property="endTime[like]" title="交易结束时间"
													width="14%" style="text-align:left;"
													value="${tradeTime.endTime}" />
												<ec:column property="startDate[like]" title="交易起始日期"
													width="14%" style="text-align:left;"
													value="${tradeTime.startDate}" />
												<ec:column property="endDate[like]" title="交易结束日期"
													width="14%" style="text-align:left;"
													value="${tradeTime.endDate}" />
												<ec:column property="status[=][int]" title="交易节状态"
													width="14%" style="text-align:left;"
													editTemplate="ecs_t_status">
													<c:set var="statusKey">
														<c:out value="${tradeTime.status}"></c:out>
													</c:set>
											  		${tradeStatusMap[statusKey]}
											  	</ec:column>
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
		<!-- 编辑和过滤所使用的交易状态模板 -->
		<textarea id="ecs_t_status" rows="" cols="" style="display: none">
			<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="status[=][int]">
				<ec:options items="tradeStatusMap" />
			</select>
	    </textarea>
		<SCRIPT type="text/javascript">
		function add(){
			var url = "${basePath}/tradeManage/tradingParameter/forwardAdd.action";
			var result=ecsideDialog(url,window,600,500);
			if(result>0) {
	 			ec.submit();
			}
		}
		function deleteByCheckBox(){
			var url = "${basePath}/tradeManage/tradingParameter/delete.action";
			deleteEcside(ec.ids,url);
		}
		function submitMember(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>
<%@ include file="/public/footInc.jsp"%>