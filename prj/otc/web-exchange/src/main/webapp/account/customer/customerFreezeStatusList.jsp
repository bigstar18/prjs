<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>

<html>
	<head>
		<title>�ͻ�����״̬�б�</title>
		<%@ include file="/public/ecsideLoad.jsp"%>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/customerFreezeStatus/list.action?sortName=primary.name"
								method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								class="table2_style">
								<tr>
									<td  class="table2_td_width">
										<div class="div2_top">
											<table class="table3_style" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td class="table3_td_1" align="left">
														�����˺�:&nbsp;
														<label>
															<input type="text" class="input_text"
																id="customerId" name="${GNNT_}primary.customerNo[like]"
																value="${oldParams['primary.customerNo[like]'] }" />
														</label>
													</td>
													<td class="table3_td_1" align="left">
														�ͻ�����:&nbsp;
														<label>
															<input type="text" class="input_text"
																id="customerName"
																name="${GNNT_}primary.name[like]"
																value="${oldParams['primary.name[like]'] }"/>
														</label>
													</td>
													<td class="table3_td_anniu" align="left">
														<button  class="btn_sec" onclick="select1()">��ѯ</button>&nbsp;&nbsp;
														<button class="btn_cz" onclick="myReset()">����</button>
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
											autoIncludeParameters="${empty param.autoInc}" var="customer"
											action="${basePath}/account/customerFreezeStatus/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											toolbarContent="navigation|pagejump |pagesize|refresh|extend|status"
											style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}">
												<ec:column width="4%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="customerNo[Like]" width="8%" title="�����˺�"
													style="text-align:left;">
													<a href="#" class="blank_a"
														onclick="return update('${customer.customerNo}');"><font
														color="#880000">${customer.customerNo}</font>
													</a>
												</ec:column>
												<ec:column property="primary.name[Like]" width="8%" title="�ͻ�����"
													style="text-align:left;overflow:hidden;text-overflow:ellipsis" value="${customer.name}" tipTitle="${customer.name}"/>
												<ec:column property="memberInfo.name[like]" width="20%"
													title="������Ա" style="text-align:left;overflow:hidden;text-overflow:ellipsis"
													value="${customer.memberName}" tipTitle="${customer.memberName}"/>
												<ec:column property="primary.useFunds[=][bigdecimal]" width="20%"
													title="�����ʽ�" style="text-align:right;">
													<fmt:formatNumber value="${customer.useFunds}" pattern="#,##0.00"/>
												</ec:column>
												<ec:column property="primary.customerStatus.status[=]" width="8%"
													title="�ͻ�״̬" style="text-align:left;"
													editTemplate="ecs_t_statusType">
		  							${firmStatusMap[customer.status]}
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
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value=""
				onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="" />
		</textarea>

		<!-- �༭�͹�����ʹ�õĿͻ�״̬ģ�� -->
		<textarea id="ecs_t_statusType" rows="" cols="" style="display: none">
		<select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="customer.status[=][int]">
			<ec:options items="firmStatusMap" />
		</select>
	    </textarea>
		<SCRIPT type="text/javascript">
	
		function update(id){
			var url = "${basePath}/account/customerFreezeStatus/forwardUpdate.action?obj.customerNo="+id;
			ecsideDialog(url,"",580,220);
		}
		
		function select1(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>