<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>日志列表查询</title>
		<script src="${publicPath }/js/tool.js" type="text/javascript"></script>
		<script type="text/javascript">
			function select1(){<%//执行查询功能%>
				checkQueryDate(frm.beginTime.value,frm.endTime.value);
			}
		</script>
	</head>
	<body>
		<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<div class="div_cx">
						<form name="frm" id="frm" action="${basePath}/bank/other/logList.action?sortColumns=order+by+logID+desc&isQueryDB=true" method="post">
							<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
								<tr>
									<td class="table5_td_width">
										<div style="margin:0 auto 0 10px;">
											<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														操作员代码:&nbsp;
														<label>
															<input id="firmID" name="${GNNT_}primary.logopr[allLike]" value="${oldParams['primary.logopr[allLike]'] }" class="input_text"/>
														</label>
													</td>
													<td class="table3_td_1" align="left">
														操作内容:&nbsp;
														<label>
															<input id="logcontent" name="${GNNT_}primary.logcontent[like]" value="${oldParams['primary.logcontent[like]'] }" class="input_text"/>
														</label>
													</td>
													<td colspan="2">&nbsp;</td>
												</tr>
												<tr>
													<td class="table3_td_1" align="left">
														起始日期:&nbsp;
														<label>
															<input id="beginTime" name="${GNNT_}primary.logDate[>=][date]" value="${oldParams['primary.logDate[>=][date]']}" class="wdate validate[custom[date],funcCall[checkBeginDate]] datepicker" onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</label>
													</td>
													<td class="table3_td_1" align="left">
														结束日期:&nbsp;
														<label>
															<input id="endTime" name="${GNNT_}primary.logDate[<=][datetime]" value="${oldParams['primary.logDate[<=][datetime]']}" class="wdate validate[custom[date],funcCall[checkEndDate]] datepicker" onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})"/>
														</label>
													</td>
													<td class="table3_td_anniu" align="right">
														<button class="btn_sec" id="view" onclick="select1()">查询</button>&nbsp;&nbsp;
														<button class="btn_cz" onclick=myReset();>重置</button>
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
									<ec:table items="pageInfo.result" var="log"
										action="${basePath}/bank/other/logList.action?sortColumns=order+by+logID+desc&isQueryDB=true"											
										autoIncludeParameters="${empty param.autoInc}"
										xlsFileName="firm.xls" csvFileName="firm.csv"
										showPrint="true" listWidth="100%"
										minHeight="345"  style="table-layout:fixed;">
										<ec:row>
											<ec:column width="5%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:right;"/>
											<ec:column property="logID" width="10%" title="日志编号" style="text-align:right;"/>
											<ec:column property="logopr" width="13%" title="操作员代码" style="text-align:center;"/>
											<ec:column property="logcontent" title="操作内容" width="54%" style="text-align:left;"  ellipsis="true"/>
											<ec:column property="logDate" title="操作时间" width="18%" style="text-align:center;"><fmt:formatDate value="${log.logDate}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
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