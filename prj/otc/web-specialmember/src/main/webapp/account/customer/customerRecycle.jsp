<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>销户回收站</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="right">
			<table width="1232" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="2%">
						&nbsp;
					</td>
					<td>
						&nbsp;
						<%@ include file="/public/select.jsp"%>
						<div class="right_03">
							<form name="frm"
								action="${basePath}/account/customerRecycle/getDCustomerList.action"
								method="post">
								<table width="100%" border="0" align="center" cellpadding="0"
									cellspacing="0" class="table">
									<tr>
										<td height="70">
											<div class="right_03_top">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td height="40" align="center" class="right_03zi"
															width="30%">
															交易账号:
															<input type="text" id="customerId"
																name="${GNNT_}customer.id[like]"
																value="${oldParams['customer.id[like]'] }" size="14"
																class="from" />
															&nbsp;&nbsp;
														</td>
														<td width="60%">
															客户名称:
															<input type="text" id="customerName"
																name="${GNNT_}customer.name[like]"
																value="${oldParams['customer.name[like]'] }" size="14"
																class="from" />
															&nbsp;&nbsp;
														</td>
														<td>
															<div class="right_03bj2">
																<a href="#" onclick="select1()" name="selectCustomer">查&nbsp;询</a>
															</div>
														</td>
														<td>
															<div class="right_03bj">
																<a href="#" onclick="resetSelect()" name="dd">重&nbsp;置</a>
															</div>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>

						<div class="right_04">
							<div class="right_04bj1">
								<a href="#">还&nbsp;&nbsp;原</a>
							</div>
						</div>

						<div class="right_05">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="customer"
											action="${basePath}/account/customerRecycle/getDCustomerList.action"
											title="" minHeight="340" listWidth="150%" height="340px"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"   style="table-layout:fixed">
											<ec:row recordKey="${customer.id}"
												ondblclick="return showCustomer(${customer.id});">
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids"
													value="${customer.id}" width="2%" viewsAllowed="html" />
												<ec:column property="id[like]" width="7%" title="交易账号"
													style="text-align:center; " value="${customer.id}" />
												<ec:column property="name[Like]" width="7%" title="客户名称"
													style="text-align:center;" value="${customer.name}" />
												<ec:column property="memberName[like]" width="7%"
													title="所属会员" style="text-align:center;"
													value="${customer.memberName}" />
												<ec:column property="customer.papersType[=][int]" width="7%"
													title="证件类型" style="text-align:center;"
													editTemplate="ecs_t_papersType">
													<c:set var="papersTypeKey">
														<c:out value="${customer.papersType}"></c:out>
													</c:set>
		  							${papersTypeMap[papersTypeKey]}
							</ec:column>
												<ec:column property="papersName[like]" width="7%" title="证件号码"
													style="text-align:center;" value="${customer.papersName}" />
												<ec:column property="address[like]" width="7%" title="地址"
													style="text-align:center;" value="${customer.address}" />
												<ec:column property="phone[like]" width="7%" title="电话"
													style="text-align:center;" value="${customer.phone}" />
												<ec:column property="fax[like]" width="7%" title="传真"
													style="text-align:center;" value="${customer.fax}" />
												<ec:column property="postCode[like]" width="7%" title="邮编"
													style="text-align:center;" value="${customer.postCode}" />
												<ec:column property="email[like]" width="7%" title="电子邮箱"
													style="text-align:center;" value="${customer.email}" />
												<ec:column property="operator[like]" width="7%" title="操作人"
													style="text-align:center;" value="${customer.operator}" />
												<ec:column property="customer.createTime[>=][date]"
													width="7%" title="创建时间" style="text-align:center;">
													<fmt:formatDate value="${customer.createTime}"
														pattern="yyyy年MM月dd日 " />
												</ec:column>
												<ec:column property="customer.modifyTime[>=][date]"
													width="7%" title="修改时间" style="text-align:center;">
													<fmt:formatDate value="${customer.modifyTime}"
														pattern="yyyy年MM月dd日 " />
												</ec:column>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
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
			var url="${basePath}/account/customerRecycle/updateForward.action?obj.id="+id;
			var result=ecsideDialog(url,window,800,800);
			if(result>0)
			{
	 			  ec.submit();	
			}
		}
		function select1(){
			var url="${basePath}/account/customerRecycle/getDCustomerList.action";
			frm.submit();
		}
		function resetSelect(){
			var url="${basePath}/account/customerRecycle/getDCustomerList.action";
			frm.customerId.value="";
			frm.customerName.value="";
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>