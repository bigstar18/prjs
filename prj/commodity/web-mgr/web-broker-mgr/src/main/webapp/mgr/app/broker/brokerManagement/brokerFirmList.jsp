<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>


<title>�������б�</title>
<script type="text/javascript">
<!--
	//�����Ϣ��ת
	function addForward(){
		//��ȡ����Ȩ�޵� URL
		var addUrl=document.getElementById('add').action;
		//��ȡ������תURL
		var url = "${basePath}"+addUrl;
        url = url + "?brokerId=" + '${brokerId}';
		if(showDialog(url, "", 550, 300)){
			//�����ӳɹ�����ˢ���б�
			ECSideUtil.reload("ec");
		}
		
	}
	//����ɾ����Ϣ
	function deleteList(){
		//��ȡ����Ȩ�޵� URL
		var deleteUrl = document.getElementById('delete').action;
		//��ȡ������תURL
		var url = "${basePath}"+deleteUrl;
		//ִ��ɾ������
		updateRMIEcside(ec.ids,url);
	}
	//ִ�в�ѯ�б�
	function dolistquery() {
		frm.submit();
	}
	//����
	function goback(){
		
		//��ȡ����Ȩ�޵� URL
		document.location.href = "${basePath}/broker/brokerManagement/listBroker.action";
	
		}
// -->
</script>
</head>
<body>
	<div id="main_body">
	      <div class="div_cx">
					<form name="frm" action="${basePath}/broker/brokerManagement/updateBrokerFirmforward.action?brokerId=${brokerId}" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
						 
						  
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table4_style" border="0" cellspacing="0" cellpadding="0">
											<tr>											   
												<td class="table3_td_1" align="left">
													    �����̴��룺
													<label>
														<input type="text" class="input_text" id="firmId"   name="${GNNT_}primary.firmId[like]" value="${oldParams['primary.firmId[like]']}" />
													</label>
												</td>
												<td class="table3_td_1" align="left">
													    ���������ƣ�
													<label>
														<input type="text" class="input_text" id="firm.name"   name="${GNNT_}primary.firm.name[like]" value="${oldParams['primary.firm.name[like]']}" />
													</label>
												</td>		
												<td class="table3_td_1" align="left">
															&nbsp;&nbsp;���ͣ�
															<label>
																<select id="type" name="${GNNT_}primary.firm.type[=][int]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${typeMap}" var="map">
																		<option value="${map.key}">${map.value}</option>
																	</c:forEach>
																	
																</select>
															</label>
															 <script >
																frm.type.value = "<c:out value='${oldParams["primary.firm.type[=][int]"] }'/>";
					  										</script>
												</td>		
												<td class="table3_td_anniu" align="left">
													<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>
													<button class="btn_cz" onclick="myReset();">����</button>
												</td>	
											</tr>
											<tr>
											    <td class="table3_td_1" align="left">
													    ��ϵ�ˣ�
													<label>
														<input type="text" class="input_text" id="firm.contactMan"   name="${GNNT_}primary.firm.contactMan[like]" value="${oldParams['primary.firm.contactMan[like]']}" />
													</label>
												 </td>	
												 <td class="table3_td_1" align="left">
													    ��ϵ�绰��
													<label>
														<input type="text" class="input_text" id="firm.phone"   name="${GNNT_}primary.firm.phone[like]" value="${oldParams['primary.firm.phone[like]']}" />
													</label>
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
			<rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/broker/brokerManagement/addBrokerFirmforward.action" id="add"></rightButton:rightButton>
			&nbsp;&nbsp;
			<rightButton:rightButton name="ɾ��" onclick="deleteList();" className="anniu_btn" action="/broker/brokerManagement/deleteBrokerFirm.action?autoInc=false&brokerId=${brokerId}" id="delete"></rightButton:rightButton>
			
								
		 </div>
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<ec:table items="pageInfo.result" var="broker"
							action="${basePath}/broker/brokerManagement/updateBrokerFirmforward.action?brokerId=${brokerId }"											
							autoIncludeParameters="${empty param.autoInc}"
							xlsFileName="export.xls" csvFileName="export.csv"
							showPrint="true" listWidth="100%"
							minHeight="345"  style="table-layout:fixed;">
							<ec:row>
							
								<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${broker.firmId}" width="3%" viewsAllowed="html" />
								<ec:column property="firmId" width="10%" title="�����̴���" style="text-align:center;"/>
								<ec:column property="firm.name" title="����������" width="10%" style="text-align:center;"/>
								<ec:column property="firm.fullName" title="������ȫ��" width="10%" style="text-align:center;"/>
								<ec:column property="firm.contactMan" title="��ϵ��" width="10%" style="text-align:center;"/>
								<ec:column property="firm.phone" title="��ϵ�绰" width="10%" style="text-align:center;"/>
								<ec:column property="firm.type" title="����" width="5%" style="text-align:center;">
								  ${typeMap[broker.firm.type]}
								</ec:column>
			
	 					   </ec:row>
							
							<ec:extend >
  			                   <a href="#" onclick="goback()">���ؼ������б�</a>
  		                    </ec:extend>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
