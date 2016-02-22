<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>资金划转列表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/bankFunds/poundageChange/transList.action"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td height="40" width="1000">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="300" align="left">
														发生时间:&nbsp;从
														<input type="text" style="width: 100px" id="beginDate"
															class="wdate" maxlength="10"
															name="${GNNT_}createTime[>=][date]"
															value='${oldParams["createTime[>=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
														到
														<input type="text" style="width: 100px" id="endDate"
															class="wdate" maxlength="10"
															name="${GNNT_}createTime[<=][date]"
															value='${oldParams["createTime[<=][date]"]}'
															onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})">
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="select()">查询</button>
														<button class="btn_cz" onclick="bankReset()">重置</button>
													</td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
							</form>
						</div>
						<div class="div_gn">
							<%--<button class="anniu_btn" onclick="change('${original_bankCode}')" id="updateFunds">
								划转
							</button>
							&nbsp;&nbsp;
							--%>
							<button class="anniu_btn" onclick="toBack()" id="toBack">
								返回
							</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="bankFundTrans"
											action="${basePath}/bankFunds/poundageChange/transList.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv" style="table-layout:fixed">

											<ec:row recordKey="${bankFundTrans.transID}">
												<ec:column width="3%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="banks.bankName[like]" width="20%"
													title="银行名称" style="text-align:left; "
													value="${bankFundTrans.bankName}" />
												<ec:column property="amount" width="20%" sortable="false" filterable="false"
													title="发生额" calc="total" calcTitle="合计：" style="text-align:left; " format="0.00">
													<fmt:formatNumber value="${bankFundTrans.amount}" type="currency" pattern="0.00"/>
												</ec:column>
												<ec:column property="createTime[date]" width="20%"
													title="发生时间" style="text-align:left;"
													value="${datefn:formatdate(bankFundTrans.createTime) }" />
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
		<textarea id="ecs_t_status" rows="" cols="" style="display: none">
			<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="transType[=]">
				<ec:options items="transTypeMap" />
			</select>
	    </textarea>

		<SCRIPT type="text/javascript">
		function change(id) {
			var url = "${basePath}/bankFunds/poundageChange/forwardUpdate.action?bankCode="+id;
			ecsideDialog(url);
		}
		function select(){
			frm.action = "${basePath}/bankFunds/poundageChange/transList.action?sortName=primary.createTime desc ,primary.createTime&original_bankCode=${original_bankCode}";
			var s = new Date(Date.parse(frm.beginDate.value.replace(/-/g, "/")));
			var e = new Date(Date.parse(frm.endDate.value.replace(/-/g, "/")));
			if(s > e) {
				alert("查询起始日期不能大于结束日期");
				return false;
			} else {
				frm.submit();
			}
		}
		function bankReset(){
			document.getElementById('beginDate').value = "";
			document.getElementById('endDate').value = "";
			frm.action = "${basePath}/bankFunds/poundageChange/transList.action?sortName=primary.createTime desc ,primary.createTime&original_bankCode=${original_bankCode}";
			frm.submit();
		}
		function toBack(){
			//frm.bankCode.value = "";
			frm.beginDate.value = "";
			frm.endDate.value = "";
			frm.action = "${basePath}/bankFunds/poundageChange/list.action?sortName=bankCode";
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>