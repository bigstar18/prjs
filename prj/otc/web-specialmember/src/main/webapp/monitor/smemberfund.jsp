<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>持仓监控</title>
<link href="../common/skinstyle/default/common/commoncss/mainstyle.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="../js/jquery-1.5.2.min.js"></script>
<script language="javascript" type="text/javascript" src="../js/jquery.tablesorter.min.js"></script>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">

								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_widthmax">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0" style="table-layout:fixed">
													<tr>
														<td width="225" align="left">
															客户归属:&nbsp;
															<label>
																<input type="text" id="memberNames"
																	name="${GNNT_}memberNames"
																	value="${oldParams['memberNames']}"
																	readonly=true size="8" class="input_text">
																	<a href="javascript:clickText();"><img align="absmiddle" src="<%=skinPath%>/cssimg/kh.gif"></a>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															交易账号:&nbsp;
															<label>
																<input type="text" id="customerId"
																	name="${GNNT_}primary.customerNo[like]"
																	value="${oldParams['primary.customerNo[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_1" align="left">
															客户名称:&nbsp;
															<label>
																<input type="text" id="customerName"
																	name="${GNNT_}primary.name[like]"
																	value="${oldParams['primary.name[like]'] }"
																	class="input_text" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onClick="select1()">查询</button>&nbsp;&nbsp;
														<button class="btn_cz" onClick="myReset();">重置</button>
													</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>

						</div>
						<div class="div_gn">
							<button class="anniu_btn" onClick="addCustomer()"
								id="addCustomer">
								添加
							</button>
						</div>

						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0"
								width="100%">
								<tr>
									<td>
										<ec:table items="resultList"
											autoIncludeParameters="${empty param.autoInc}" var="customer"
											action="${basePath}/account/customer/customerList.action"
											title="" minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="导出列表.csv"  style="table-layout:fixed">

											<ec:row recordKey="${customer.customerNo}">
												<ec:column property="1" width="3%"
													title=" " style="text-align:center; " sortable="false" filterable="false">
													<img src="<%=skinPath%>/cssimg/edit.gif" />
												</ec:column>
												<ec:column width="4%" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="primary.customerNo[like]" width="13%"
													title="交易账号" style="text-align:left; ">
													<a href="#" class="blank_a"
														onclick="forwardUpdate('${customer.customerNo}');"><font
														color="#880000">${customer.customerNo}</font>
													</a>
												</ec:column>
												<ec:column property="primary.name[Like]" width="20%"
													title="客户名称" style="text-align:left;">
													<nobr>
														${customer.name}
													</nobr>
												</ec:column>
												<ec:column property="memberInfo.name[like]" width="27%"
													title="所属会员" style="text-align:left;"
													value="${customer.memberName}" />
												<ec:column property="customerStatus.status[=]" width="9%"
													title="客户状态" style="text-align:center;"
													editTemplate="ecs_t_statusType">
		  												${firmStatusMap[customer.status]}
												</ec:column>
												<ec:column property="primary.openstatus[=]" width="9%"
													title="签约状态" style="text-align:center;"
													editTemplate="ecs_t_openstatus">
													<c:set var="ostatus">
														<c:out value="${customer.openstatus}"></c:out>
													</c:set>
	  												${openstatusMap[ostatus]}
												</ec:column>
												<ec:column property="2" title="电话口令"
													style="text-align:center" width="9%">
													<a href="javascript:forwardUpdatePhonePW('${customer.customerNo}');" class="blank_a"><font
														color="#880000">修改</font>
													</a>
												</ec:column>
												<ec:column property="3" title="交易口令"
													style="text-align:center" width="9%">
													<a href="javascript:forwardUpdatePW('${customer.customerNo}');" class="blank_a"><font
														color="#880000">修改</font>
													</a>
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

		<!-- 编辑和过滤所使用的证件类型模板 -->
		<textarea id="ecs_t_papersType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="customer.papersType[=][int]">
			<ec:options items="papersTypeMap" />
		</select>
	    </textarea>
		<!-- 编辑和过滤所使用的客户状态模板 -->
		<textarea id="ecs_t_statusType" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="customer.status[=][int]">
			<ec:options items="firmStatusMap" />
		</select>
	    </textarea>
		<textarea id="ecs_t_openstatus" rows="" cols="" style="display: none">
		<select onBlur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="openstatus[=][int]">
			<ec:options items="openstatusMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
		
		function clickText(){
			var url="${basePath}/broke/memberInfoTree/forTree.action";
			/*if(checkie()){
				result=window.showModalDialog(url,window,'dialogWidth=500px;dialogHeight=630px');
			}else{
				result=window.showModalDialog(url,window,'dialogWidth=500px;dialogHeight=570px');
			}*/
			ecsideDialog(url,window,400,570);
			
		}
		function forwardUpdate(id){
			var url="${basePath}/account/customer/forwardUpdate.action?obj.customerNo="+id;
			ecsideDialog(url,"",700,507);
		}
		
		function addCustomer(){
			var url="${basePath}/account/customer/forwardAdd.action";
			//ec.action=url;
			//ec.submit();
			ecsideDialog(url,"",700,507);
		}
		function select1(){
			frm.submit();
		}
		
		
		function deleteByCheckBox(){
			var url="${basePath}/account/customer/delete.action";
			deleteEcside(ec.ids,url);
		}
		function forwardUpdatePW(id){
			var url="${basePath}/account/customer/forwardUpdatePassward.action?obj.customerNo="+id;
			ecsideDialog(url,"",580,260);
		}
		function forwardUpdatePhonePW(id){
			var url="${basePath}/account/customer/forwardUpdatePasswardPhone.action?obj.customerNo="+id;
			ecsideDialog(url,"",580,260);
		}
		</SCRIPT>
	</body>
</html>