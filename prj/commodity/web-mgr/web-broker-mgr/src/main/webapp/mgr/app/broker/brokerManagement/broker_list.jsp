<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>


<title>�����������б�</title>
<script type="text/javascript">
<!--
	//�����Ϣ��ת
	function addForward(){
		//��ȡ����Ȩ�޵� URL
		var addUrl=document.getElementById('add').action;
		//��ȡ������תURL
		var url = "${basePath}"+addUrl;

		if(showDialog(url, "", 800, 700)){
			//�����ӳɹ�����ˢ���б�
			ECSideUtil.reload("ec");
		}
		
	}
	//�޸���Ϣ��ת
	function detailForward(id){
		//��ȡ����Ȩ�޵� URL
		var detailUrl = "${basePath}/broker/brokerManagement/updateBrokerforward.action";
		//��ȡ������תURL
		var url = detailUrl+ "?entity.brokerId=" + id;
		//�����޸�ҳ��
		if(showDialog(url, "", 800, 700)){
			//����޸ĳɹ�����ˢ���б�
			ECSideUtil.reload("ec");
		};
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
		 
		 var startDate = document.getElementById("beginDate").value;
		 var endDate =  document.getElementById("endDate").value;	

		  if(startDate != "" && endDate != "" || startDate == "" && endDate != "" || startDate != "" && endDate == "")
		  {
			  if(startDate == ""){
					alert("��ʼ���ڲ���Ϊ�գ�");
					frm.beginDate.focus();
					return false;
					
				}
				if(endDate == ""){
					alert("�������ڲ���Ϊ�գ�");
					frm.endDate.focus();
					return false;
					
				}
			if ( startDate > endDate ) { 
		        alert("��ʼ���ڲ��ܴ��ڽ�������!"); 
		        return false; 
		    } 
		  }
		frm.submit();
	}
	
	//�޸�������ת
	function detailForward1(id){
		//��ȡ����Ȩ�޵� URL
		var detailUrl = "${basePath}/broker/brokerManagement/updateBrokerPasswordforward.action?entity.brokerId=" + id;
		//��ȡ������תURL
		var url = detailUrl;
		//�����޸�ҳ��
		if(showDialog(url, "", 550, 350)){
			//����޸ĳɹ�����ˢ���б�
			ECSideUtil.reload("ec");
		};
	}
	// ��Ͻ��������ת
	function detailForward2(id){
		//��ȡ����Ȩ�޵� URL
		var detailUrl = "${basePath}/broker/brokerManagement/updateBrokerFirmforward.action";
		//��ȡ������תURL
		var url = detailUrl + "?brokerId=" + id;
		//�����޸�ҳ��
		document.location.href = url;
	}
	//Ȩ��������ת
	function detailForward3(id){
		//��ȡ����Ȩ�޵� URL
		var detailUrl = "${basePath}/broker/brokerManagement/updateBrokerRightforward.action";
		//��ȡ������תURL
		var url = detailUrl+ "?brokerId=" + id;
		//�����޸�ҳ��
		if(showDialog(url, "", 500, 650)){
			//����޸ĳɹ�����ˢ���б�
			ECSideUtil.reload("ec");
		};
	}
// -->
</script>
</head>
<body>
	<div id="main_body" >
	      <div class="div_cx">
					<form name="frm" action="${basePath}/broker/brokerManagement/listBroker.action" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style" >
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table4_style" border="0" cellspacing="0" cellpadding="0" style="width: 110%">
											<tr >
											   <td class="table3_td_1" align="right" style="width: 25%">
													    �����̱�ţ�
													<label>
														<input type="text" class="input_text" id="brokerId"   name="${GNNT_}primary.brokerId[like]" value="${oldParams['primary.brokerId[like]']}" />
													</label>
												</td>
												<td class="table3_td_1" align="right" style="width: 25%">
													    �������˺ţ�
													<label>
														<input type="text" class="input_text" id="firmId"   name="${GNNT_}primary.firmId[=]" value="${oldParams['primary.firmId[=]']}" />
													</label>
												</td>
												<td class="table3_td_1" align="right" style="width: 25%">
													  ���������ƣ�
													<label>
														<input type="text" class="input_text" id="brokerName"   name="${GNNT_}primary.name[like]" value="${oldParams['primary.name[like]']}" />
													</label>
												</td>
												 <td class="table3_td_1" align="right" style="width: 25%">
													  ����
													<label>
														<input type="text" class="input_text" id="areaName"   name="${GNNT_}primary.brokerArea.name[like]" value="${oldParams['primary.brokerArea.name[like]']}" />
													</label>
												</td>
												
											</tr>
										
										<tr>
										     <td class="table3_td_1" align="right" style="width: 28%">
													    �г�������Ա��
													<label> 
														<input type="text" class="input_text" id="marketManager"  name="${GNNT_}primary.marketManager[like]" value="${oldParams['primary.marketManager[like]']}" />
													</label>
												</td>
										      
												<td class="table3_td_1" align="right" >
													  &nbsp;&nbsp;&nbsp;��ʼ���ڣ�
													<label >
														<input type="text" class="wdate" id="beginDate"  style="width:120"
												       name="${GNNT_}primary.modifyTime[>=][date]"			
												       value="${oldParams['primary.modifyTime[>=][date]']}"					
												       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />	
													</label>
												</td>
												<td class="table3_td_1" align="right" >
													  �������ڣ�
													<label>
														<input type="text" class="wdate" id="endDate"   style="width:120"
												       name="${GNNT_}primary.modifyTime[<=][date]"			
												       value="${oldParams['primary.modifyTime[<=][date]']}"					
												       onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />	
													</label>
												</td>
												
												<td class="table3_td_anniu" align="right" style="width: 12%;">
													<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button><span>&nbsp;</span>
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
			<rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/broker/brokerManagement/addBrokerforward.action" id="add"></rightButton:rightButton>
			&nbsp;&nbsp;
			<rightButton:rightButton name="ɾ��" onclick="deleteList();" className="anniu_btn" action="/broker/brokerManagement/deleteBroker.action?autoInc=false" id="delete"></rightButton:rightButton>
		 </div>
		<div class="div_list">
			<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td>
						<ec:table items="pageInfo.result" var="broker"
							action="${basePath}/broker/brokerManagement/listBroker.action"											
							autoIncludeParameters="${empty param.autoInc}"
							xlsFileName="export.xls" csvFileName="export.csv"
							showPrint="true" listWidth="150%"
							minHeight="345"  style="table-layout:fixed;">
							<ec:row>
							
								<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${broker.brokerId}" width="2%" viewsAllowed="html" />
								<ec:column property="brokerId" width="4%" title="�����̱��" style="text-align:center;">
								    <a href="#" class="blank_a" onclick="detailForward('${broker.brokerId}')" title="�޸�"><font color="#880000">${broker.brokerId}</font></a> 	
								</ec:column>
								<ec:column property="firmId" title="�������˺�" width="8%" ellipsis="true" style="text-align:center;"/>
								<ec:column property="name" title="����������" width="8%" ellipsis="true" style="text-align:center;"/>
								<ec:column property="telephone" title="�绰" width="7%" style="text-align:center;"/>
								<ec:column property="mobile" title="�ֻ�" width="7%" style="text-align:center;"/>
								<ec:column property="email" title="�����ʼ�" width="9%" style="text-align:center;"/>
								<ec:column property="brokerArea" title="����" width="8%" style="text-align:center;">							  
								${broker.brokerArea.name }
								</ec:column>
								<ec:column property="marketManager" title="�г�������Ա" width="8%" style="text-align:center;"/>
								<ec:column property="memberType" title="��Ա����" width="5%" style="text-align:center;">${broker.brokerType.brokerName }</ec:column>
								<ec:column property="timeLimit" title="�Ἦ����" width="6%" style="text-align:center;">
								     <fmt:formatDate value="${broker.timeLimit }" pattern="yyyy-MM-dd" />
								</ec:column>
								<ec:column property="modifyTime" title="��������" width="6%" style="text-align:center;">
								     <fmt:formatDate value="${broker.modifyTime }" pattern="yyyy-MM-dd" />
								</ec:column>
								<ec:column property="_1" title="Ȩ������" width="5%" sortable="false" filterable="false" style="text-align:center;" >
                                   <a href="#" onclick="detailForward3('${broker.brokerId}')">�鿴</a> 
                                </ec:column>
                                <ec:column property="_1" title="��Ͻ������" width="5%" sortable="false" filterable="false" style="text-align:center;" >
                                	<c:if test="${broker.brokerType.borkerType==1 or broker.brokerType.borkerType==2}">
                                		--
                                	</c:if>
                                	<c:if test="${broker.brokerType.borkerType==0}">
                                   		<a href="#" onclick="detailForward2('${broker.brokerId}')">�鿴</a> 
                                	</c:if>
                                </ec:column>
                                <ec:column property="_1" title="�����޸�" width="5%" sortable="false" filterable="false" style="text-align:center;" >
                                   <a href="#" onclick="detailForward1('${broker.brokerId}')">�޸�</a> 
                                </ec:column>
							</ec:row>
						</ec:table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
