<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>客户激活状态列表</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						</div>
						<div class="div_tj">
							<form name="frm"
								action="${basePath}/account/customerActiveStatus/list.action?sortName=primary.name"
								method="post">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthcdmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															交易账号:&nbsp;
															<label>
																<input type="text" class="input_text" id="customerId"
																	name="${GNNT_}primary.customerNo[like]"
																	value="${oldParams['primary.customerNo[like]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															客户名称:&nbsp;
															<label>
																<input type="text" class="input_text" id="customerName"
																	name="${GNNT_}primary.name[like]"
																	value="${oldParams['primary.name[like]'] }" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															会员编号:&nbsp;
															<label>
																<input type="text" class="input_text" id="memberInfoId"
																	name="${GNNT_}memberInfo.id[like]"
																	value="${oldParams['memberInfo.id[like]'] }" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick="select1()">
																查询
															</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset()">
																重置
															</button>
															&nbsp;&nbsp;
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
							<button class="anniu_btn" onclick="active()">
								批量激活
							</button>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="customer"
											action="${basePath}/account/customerActiveStatus/list.action"
											minHeight="345" listWidth="100%" retrieveRowsCallback="limit"
											sortRowsCallback="limit" filterRowsCallback="limit"
											toolbarContent="navigation|pagejump |pagesize|refresh|extend|status"
											style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													style="text-align:center; " value="${customer.customerNo}" width="2%"
													viewsAllowed="html" />
												<ec:column width="4%" property="_0" title="序号"
													value="${GLOBALROWCOUNT}" sortable="false"
													filterable="false" />
												<ec:column property="customerNo[Like]" width="12%"
													title="交易账号" style="text-align:left;">
													<a href="#" class="blank_a"
														onclick="return update('${customer.customerNo}');"><font
														color="#880000">${customer.customerNo}</font> </a>
												</ec:column>
												<ec:column property="primary.name[Like]" width="8%"
													title="客户名称" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.name}" tipTitle="${customer.name}"/>
												<ec:column property="memberInfo.name[like]" width="8%"
													title="所属会员" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.memberName}" tipTitle="${customer.memberName}"/>
												<ec:column property="primary.createTime[=][Date]" width="12%"
													title="开户时间" style="text-align:left;" value="${datefn:formatdate(customer.createTime)}" filterable="false">
												</ec:column>
												<ec:column property="primary.useFunds[=][bigdecimal]" width="10%"
													title="可用资金" style="text-align:right;">
													<fmt:formatNumber value="${customer.useFunds}" pattern="#,##0.00"/>
												</ec:column>
												<ec:column property="primary.customerStatus.status[=]" width="8%"
													title="客户状态" style="text-align:left;"
													editTemplate="ecs_t_statusType">
		  												${firmStatusMap[customer.status]}
												</ec:column>
												<ec:column property="-1" width="15%" filterable="false" ellipsis="true"
													title="备注" style="text-align:left;">
		  												<c:choose>
		  													<c:when test="${customer.useFunds < market[0].customerOpenMinFunds}">
		  														<c:out value="资金不足，至少需再入金 "/>
		  														<fmt:formatNumber value="${market[0].customerOpenMinFunds - customer.useFunds}" pattern="#,##0.00"/>
		  													</c:when>
		  													<c:otherwise>
		  														<c:out value="可以激活"/>
		  													</c:otherwise>
		  												</c:choose>
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

		<!-- 编辑和过滤所使用的客户状态模板 -->
		<textarea id="ecs_t_statusType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="customer.status[=][int]">
			<ec:options items="firmStatusMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
	
		function update(id){
			var url = "${basePath}/account/customerActiveStatus/forwardUpdate.action?obj.customerNo="+id;
			ecsideDialog(url,"",580,220);
		}
		function active(){
			var collCheck=document.getElementsByName("ids");
			var url = "${basePath}/account/customerActiveStatus/customerActive.action";
			if(isSelNothing(collCheck) == -1)
			{
				alert ( "没有可以操作的数据！" );
				return false;
			}
			if(isSelNothing(collCheck))
			{
				alert ( "请选择需要操作的数据！" );
				return false;
			}
			if(confirm("你确定要激活吗？"))
			{
				ec.action=url;
				ec.submit();
			}
		}
		function select1(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>