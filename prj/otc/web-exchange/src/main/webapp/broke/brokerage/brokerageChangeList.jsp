<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>居间转移列表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						&nbsp;
						<div class="div_cxtj">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;查询条件
						</div>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/broke/brokerageChange/list.action"
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
														居间代码:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" id="brokerageNo"
																name="${GNNT_}primary.brokerageNo[like]"
																value="${oldParams['primary.brokerageNo[like]'] }"
																class="input_text" />
														</label>
													</td>
													<td class="table3_td" align="right">
														居间名称:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" id="brokerageName"
																name="${GNNT_}primary.name[like]"
																value="${oldParams['primary.name[like]'] }"
																class="input_text" />
														</label>
													</td>
													<td class="table3_td" align="right">
														<button  class="btn_sec" onclick="select1()">查询</button>
													</td>
													<td class="table3_td2" align="left">
														<button class="btn_cz" onclick="myReset()">重置</button>
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
											autoIncludeParameters="${empty param.autoInc}" var="brokerage"
											action="${basePath}/broke/brokerageChange/list.action"
											title="" minHeight="460" listWidth="100%" height="460"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">

											<ec:row recordKey="${brokerage.brokerageNo}">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													value="${brokerage.brokerageNo}" width="2%" viewsAllowed="html" />
												<ec:column property="primary.brokerageNo[like]" width="12%"
													title="居间代码" style="text-align:left; "
													value="${brokerage.brokerageNo}" />
												<ec:column property="primary.name[Like]" width="12%"
													title="居间名称" style="text-align:left;"
													value="${brokerage.name}" />
												<ec:column property="primary.telephone[like]" width="12%"
													title="联系电话" style="text-align:left;"
													value="${brokerage.telephone}" />
												<ec:column property="primary.mobile[like]" width="12%"
													title="手机" style="text-align:left;"
													value="${brokerage.mobile}" />
												<ec:column property="primary.email[like]" width="12%"
													title="电子邮箱" style="text-align:left;"
													value="${brokerage.email}" />
												<ec:column property="primary.address[like]" width="12%"
													title="通讯地址" style="text-align:left;"
													value="${brokerage.address}" />
												<ec:column property="_" title="转移居间"
													style="text-align:center" width="11%">
													<button onclick="forwardChange('${brokerage.brokerageNo}')">
														<font color="red">转移居间</font>
													</button>
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

		<SCRIPT type="text/javascript">

		function forwardChange(id){
			var url="${basePath}/broke/brokerageChange/forwardUpdate.action?obj.brokerageNo="+id;
			ecsideDialog(url,"",800,800);
		}
		function select1(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>