<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>销户回收站</title>
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
								action="${basePath}/account/customerRecycle/list.action"
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
														交易账号:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" class="input_text"
																id="customerId"
																name="${GNNT_}primary.customerNo[like]"
																value="${oldParams['primary.customerNo[like]'] }" />
														</label>
													</td>
													<td class="table3_td" align="right">
														客户名称:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" class="input_text"
																id="customerName"
																name="${GNNT_}primary.name[like]"
																value="${oldParams['primary.name[like]'] }" />
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
						<div class="div_gn">
							<button  class="anniu_btn" id="huany">还原</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="customer"
											action="${basePath}/account/customerRecycle/list.action"
											title="" minHeight="345" listWidth="150%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}"
												ondblclick="return showCustomer('${customer.customerNo}');">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													value="${customer.customerNo}" width="2%" viewsAllowed="html" />
												<ec:column property="id[like]" width="7%" title="交易账号"
													style="text-align:center; " value="${customer.id}" />
												<ec:column property="name[Like]" width="7%" title="客户名称"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${customer.name}" tipTitle="${customer.name}"/>
												<ec:column property="memberName[like]" width="7%"
													title="所属会员" style="text-align:center;overflow:hidden;text-overflow:ellipsis"
													value="${customer.memberName}" tipTitle="${customer.memberName}"/>
												<ec:column property="customer.papersType[=][int]" width="7%"
													title="证件类型" style="text-align:center;"
													editTemplate="ecs_t_papersType">
													<c:set var="papersTypeKey">
														<c:out value="${customer.papersType}"></c:out>
													</c:set>
		  							${papersTypeMap[papersTypeKey]}
							</ec:column>
												<ec:column property="papersName[like]" width="7%" title="证件号码"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${customer.papersName}" tipTitle="${customer.papersName}"/>
												<ec:column property="address[like]" width="7%" title="地址"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${customer.address}" tipTitle="${customer.address}"/>
												<ec:column property="phone[like]" width="7%" title="电话"
													style="text-align:center;" value="${customer.phone}" />
												<ec:column property="fax[like]" width="7%" title="传真"
													style="text-align:center;" value="${customer.fax}" />
												<ec:column property="postCode[like]" width="7%" title="邮编"
													style="text-align:center;" value="${customer.postCode}" />
												<ec:column property="email[like]" width="7%" title="电子邮箱"
													style="text-align:center;" value="${customer.email}" />
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

		<!-- 编辑和过滤所使用的证件类型模板 -->
		<textarea id="ecs_t_papersType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="customer.papersType[=][int]">
			<ec:options items="papersTypeMap" />
		</select>
	    </textarea>

		<SCRIPT type="text/javascript">
		function showCustomer(id){
			var url="${basePath}/account/customerRecycle/forwardUpdate.action?obj.customerNo="+id;
			var result=ecsideDialog(url,window,800,800);
			if(result>0)
			{
	 			  ec.submit();	
			}
		}
		function select1(){
			var url="${basePath}/account/customerRecycle/list.action";
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>