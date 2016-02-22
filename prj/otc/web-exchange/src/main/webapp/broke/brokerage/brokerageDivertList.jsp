<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>居间转会员列表</title>
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
								action="${basePath}/broke/brokerageDivert/list.action"
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
														居间代码:&nbsp;
														<label>
															<input type="text" id="brokerageNo"
																name="${GNNT_}primary.brokerageNo[like]"
																value="${oldParams['primary.brokerageNo[like]'] }"
																class="input_text" />
														</label>
													</td>
													<td class="table3_td_1" align="left">
														居间名称:&nbsp;
														<label>
															<input type="text" id="brokerageName"
																name="${GNNT_}primary.name[like]"
																value="${oldParams['primary.name[like]'] }"
																class="input_text" />
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="select1()">查询</button>
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
											action="${basePath}/broke/brokerageDivert/list.action"
											title="" minHeight="345" listWidth="100%" height="460"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">

											<ec:row recordKey="${brokerage.brokerageNo}">
												<ec:column width="4%" property="_0" title="序号" value="${TOTALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="primary.brokerageNo[like]" width="12%"
													title="居间代码" style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="return forwardDivert('${brokerage.brokerageNo}');"><font
														color="#880000">${brokerage.brokerageNo}</font>
													</a>
												</ec:column>
												<ec:column property="primary.name[Like]" width="12%"
													title="居间名称" style="text-align:left;"
													value="${brokerage.name}" />
												<ec:column property="primary.memberNo[Like]" width="12%"
													title="会员编号" style="text-align:left;"
													value="${brokerage.memberNo}" />
												<ec:column property="primary.telephone[like]" width="12%"
													title="联系电话" style="text-align:left;"
													value="${brokerage.telephone}" />
												<ec:column property="primary.mobile[like]" width="12%"
													title="手机" style="text-align:left;"
													value="${brokerage.mobile}" />
												<ec:column property="primary.email[like]" width="12%"
													title="电子邮箱" style="text-align:left;"
													value="${brokerage.email}" tipTitle="${brokerage.email}" />
												<ec:column property="primary.address[like]" width="13%"
													title="通讯地址" style="text-align:left;"
													value="${brokerage.address}" tipTitle="${brokerage.address}"/>
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

		function forwardDivert(id){
			var url="${basePath}/broke/brokerageDivert/forwardUpdate.action?obj.brokerageNo="+id;
			ecsideDialog(url,"",580,260);
		}
		function select1(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>