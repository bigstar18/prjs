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
						&nbsp;
						<div class="div_cxtj">
							<img src="<%=skinPath%>/cssimg/13.gif" />
							&nbsp;&nbsp;��ѯ����
						</div>
						<div class="div_tj">
						<form name="frm"
								action="${basePath}/account/customerActiveStatus/list.action?sortName=customer.name"
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
														�����˺�:&nbsp;
													</td>
													<td class="table3_td2">
														<label>
															<input type="text" class="input_text"
																id="customerId" name="${GNNT_}primary.customerNo[like]"
																value="${oldParams['primary.customerNo[like]'] }" />
														</label>
													</td>
													<td class="table3_td" align="right">
														�ͻ�����:&nbsp;
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
														<button  class="btn_sec" onclick="select1()">��ѯ</button>
													</td>
													<td class="table3_td2" align="left">
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
											action="${basePath}/account/customerActiveStatus/list.action"
											minHeight="345" listWidth="100%"
											retrieveRowsCallback="limit" sortRowsCallback="limit"
											filterRowsCallback="limit" 
											csvFileName="�����б�.csv"   style="table-layout:fixed">
											<ec:row recordKey="${customer.customerNo}"
												ondblclick="return update('${customer.customerNo}');">
												<ec:column property="customerNo[Like]" width="20%" title="�����˺�"
													style="text-align:center;" value="${customer.customerNo}" />
												<ec:column property="name[Like]" width="20%" title="�ͻ�����"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${customer.name}" tipTitle="${customer.name}"/>
												<ec:column property="memberInfo.name[like]" width="20%"
													title="������Ա" style="text-align:center;overflow:hidden;text-overflow:ellipsis"
													value="${customer.memberName}" tipTitle="${customer.memberName}"/>
												<ec:column property="address[like]" width="20%" title="��ַ"
													style="text-align:center;overflow:hidden;text-overflow:ellipsis" value="${customer.address}" tipTitle="${customer.address}"/>
												<ec:column property="customer.status[=]" width="20%"
													title="�ͻ�״̬" style="text-align:center;"
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
			var url = "${basePath}/account/customerActiveStatus/forwardUpdate.action?obj.customerNo="+id;
			ecsideDialog(url);
		}
		
		function select1(){
			frm.submit();
		}
		</SCRIPT>
	</body>
</html>