<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>�ͻ���Ϣ�б�</title>
		<SCRIPT type="text/javascript">
		<!-- 
			//�����Ϣ��ת
			function addCustomerForward(){
				//��ȡ����Ȩ�޵� URL
				var addUrl=document.getElementById('add').action;
				//��ȡ������תURL
				var url = "${basePath}"+addUrl;
				//�������ҳ��
				if(showDialog(url, "", 800, 550)){
					//�����ӳɹ�����ˢ���б�
					document.getElementById("view").click();
				}
			}
			//�޸���Ϣ��ת
			function updateCustomerForward(customerId){
				//��ȡ����Ȩ�޵� URL
				var updateUrl = document.getElementById('update'+customerId).action;
				//��ȡ������תURL
				var url = "${basePath}"+updateUrl;
				//�� URL ��Ӳ���
				if(url.indexOf("?")>0){
					url += "&entity.customerId="+customerId;
				}else{
					url += "?entity.customerId="+customerId;
				}
				//�����޸�ҳ��
				if(showDialog(url, "", 800, 550)){
					//����޸ĳɹ�����ˢ���б�
					document.getElementById("view").click();
				};
			}
			//����ɾ����Ϣ
			function delateCustomerList(){
				//��ȡ����Ȩ�޵� URL
				var delateUrl = document.getElementById('delate').action;
				//��ȡ������תURL
				var url = "${basePath}"+delateUrl;
				//ִ��ɾ������
				updateRMIEcside(ec.ids,url);
			}
			//�鿴��Ϣ����
			function customerDetails(customerId){
				var url = "${basePath}/dem/customerplay/customerdetails.action?entity.customerId="+customerId;
				showDialog(url, "", 700, 450);
			}
			//ִ�в�ѯ�б�
			function dolistquery() {
				frm.submit();
			}
		//-->
		</SCRIPT>
	</head>
	<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/dem/customerplay/customerlist.action?sortColumns=order+by+customerId+desc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="left">
															<fmt:message key ="Customer.customerId" bundle="${PropsFieldDic}"/>:&nbsp;
															<label>
																<input type="text" class="input_text" id="id"  checked="checked" name="${GNNT_}primary.customerId[=][Long]" value="${oldParams['primary.customerId[=][Long]']}" maxLength="<fmt:message key='Customer.customerId_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															<fmt:message key ="User.userId" bundle="${PropsFieldDic}"/>:&nbsp;
															<label>
																<input type="text" class="input_text" id="userID" name="${GNNT_}primary.user.userId[allLike]" value="${oldParams['primary.user.userId[allLike]'] }" maxLength="<fmt:message key='User.userId_q' bundle='${PropsFieldLength}'/>"/>
															</label>
														</td>
														<td class="table3_td_1" align="left">
															<fmt:message key ="Customer.status" bundle="${PropsFieldDic}"/>:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${customer_statusMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.status.value = "<c:out value='${oldParams["primary.status[=]"] }'/>";
					  										</script>
														</td>
													</tr>
													<tr>
														<td class="table3_td_1" align="left">&nbsp;</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">����</button>
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
							<rightButton:rightButton name="���" onclick="addCustomerForward();" className="anniu_btn" action="/dem/customerplay/addcustomerforward.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="ɾ��" onclick="delateCustomerList();" className="anniu_btn" action="/dem/customerplay/delatecustomerlist.action" id="delate"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="customer"
											action="${basePath}/dem/customerplay/customerlist.action?sortColumns=order+by+customerId+desc"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="demo.xls" csvFileName="demo.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${customer.customerId}" width="5%" viewsAllowed="html" />
												<ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" />
												<ec:column property="prop" width="10%" title="Customer.customerId" style="text-align:left;">
													<a href="#" class="blank_a" onclick="return customerDetails('${customer.customerId}');"><font color="#880000">${customer.customerId}</font></a>
												</ec:column>
												<ec:column property="user.name" title="User.name" width="10%" style="text-align:left;"  ellipsis="true"/>
												<ec:column property="name" title="Customer.name" width="10%" style="text-align:left;"  ellipsis="true"/>
												<ec:column property="prop" title="Customer.status" width="10%" style="text-align:center;">${customer_statusMap[customer.status]}</ec:column>
												<ec:column property="prop" title="Customer.modifyTime" width="10%" style="text-align:center;"><fmt:formatDate value="${customer.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
												<ec:column property="prop" title="�޸���Ϣ" width="10%" style="text-align:center;">
													<rightButton:rightButton name="�޸�" onclick="updateCustomerForward('${customer.customerId}');" className="anniu_btn" action="/dem/customerplay/updatecustomerforward.action" id="update${customer.customerId}"></rightButton:rightButton>
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
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
		<textarea id="ecs_t_input" rows="" cols="" style="display: none">
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
	</body>
</html>