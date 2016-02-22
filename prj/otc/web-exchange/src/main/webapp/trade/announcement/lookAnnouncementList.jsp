<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@page import="java.util.*"%>

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
						&nbsp;
						<div class="div_cxtj">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;查询条件
						</div>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/tradeManage/lookAnnouncement/list.action"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td height="60">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td" align="right">
															操作员:&nbsp;
														</td>
														<td class="table3_td2">
															<label>
																<input type="text" class="input_text"
																	name="${GNNT_}notice.author[like]" id="messageId"
																	value="${oldParams['notice.author[like]'] }" />
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
															<button class="btn_sec" onclick="select1()">
																查询
															</button>
														</td>
														<td class="table3_td2" align="left">
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

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>

										<ec:table items="noticeList"
											autoIncludeParameters="${empty param.autoInc}" var="notice"
											action="${basePath}/tradeManage/lookAnnouncement/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">
											<ec:row recordKey="${notice.id}">
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="author[like]" title="操作员" width="33%" style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="showAnnouncement(${notice.id},${notice.okoticeId});"><font
														color="#880000">${notice.author }</font>
													</a>
												</ec:column>
												<ec:column property="title[like]" title="主题" width="33%"
													style="text-align:left;" value="${notice.title}" />
												<ec:column property="sendTime[Date]" title="发布时间"
													width="34%" style="text-align:left;"
													value="${notice.sendTime}" />
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
	<script type="text/javascript">
function select1() {
	frm.submit();
}
function showAnnouncement(id, okoticeId) {
	var url = "${basePath}/tradeManage/lookannouncementList/forwardUpdate.action?obj.id="
			+ id + "&okoticeId=" + okoticeId;
	ecsideDialog(url, null, 610, 560);
}
</script>
</html>