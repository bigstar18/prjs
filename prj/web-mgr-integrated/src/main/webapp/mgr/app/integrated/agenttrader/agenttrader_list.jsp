<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
		<title>��Ϊ����Ա��Ϣ�б�</title>
		<style type="text/css">
			.style1{
				text-align:center;
			}
		</style>
		<SCRIPT type="text/javascript"  LANGUAGE="JavaScript">
		 
			//��Ӵ�Ϊ����Ա
			function addAgentTraderForward(){
				var a=document.getElementById('add').action;
				var url = "${basePath}"+a+"?autoInc=false";
				ecsideDialog(url,"",650,380);
			}
			//�����Ϣ��ת
			function addAgentTraderForward(){
				//��ȡ����Ȩ�޵� URL
				var addUrl=document.getElementById('add').action;
				//��ȡ������תURL
				var url = "${basePath}"+addUrl;
				//�������ҳ��
				if(showDialog(url, "", 650, 380)){
					//�����ӳɹ�����ˢ���б�
					document.getElementById("view").click();
				}
			}
			//�޸���Ϣ��ת
			function updateAgentTraderForward(agentTraderId){
				//��ȡ����Ȩ�޵� URL
				var updateUrl = document.getElementById('update'+agentTraderId).action;
				//��ȡ������תURL
				var url = "${basePath}"+updateUrl;
				//�� URL ��Ӳ���
				if(url.indexOf("?")>0){
					url += "&entity.agentTraderId="+agentTraderId;
				}else{
					url += "?entity.agentTraderId="+agentTraderId;
				}
				//�����޸�ҳ��
				if(showDialog(url, "", 650, 380)){
					//����޸ĳɹ�����ˢ���б�
					document.getElementById("view").click();
				};
			}
			//����ɾ����Ϣ
			function delateAgentTraderList(){
				//��ȡ����Ȩ�޵� URL
				var delateUrl = document.getElementById('delate').action;
				//��ȡ������תURL
				var url = "${basePath}"+delateUrl;
				//ִ��ɾ������
				updateRMIEcside(ec.ids,url);
			}
			//�鿴��Ϣ����
			function agentTraderDetails(agentTraderId){
				var a=document.getElementById("agentTraderDetails").action;
				var url = "${basePath}"+a+"?entity.agentTraderId="+agentTraderId;
				//showDialog(url, "", 700, 380);
				if(showDialog(url, "", 650, 380)){
					//����޸ĳɹ�����ˢ���б�
					document.getElementById("view").click();
				};
			}
			//ִ�в�ѯ�б�
			function queryInfo() {
				frm.submit();
			}
			//�޸Ĵ�Ϊ����Ա����
			function updateAgentTraderPassword(btn){
				var a=document.getElementById("password").action;
				var url="<%=basePath%>"+a+"?entity.agentTraderId="+btn;
				var result = ecsideDialog(url,"",450,270);
			}
		</SCRIPT>
	</head>
	<body onload="getFocus('agentTraderId');">
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/trade/agenttrader/list.action?sortColumns=order+by+createTime+desc" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td width="250px" height="40px" align="left">
															��Ϊ����Ա����:&nbsp;
															<label>
																<input name="${GNNT_}agentTraderId[allLike]" id="agentTraderId" type="text" class="input_text" value="${oldParams['agentTraderId[allLike]']}" maxLength="<fmt:message key='agentTraderId' bundle='${PropsFieldLength}'/>">
															</label>
														</td>
														<td width="250px" height="40px" align="left">
															��Ϊ����Ա����:&nbsp;
															<label>
																<input name="${GNNT_}name[allLike]" id="name" type="text" class="input_text" maxlength="32" value="${oldParams['name[allLike]']}" maxLength="<fmt:message key='agentTraderName_q' bundle='${PropsFieldLength}'/>">
															</label>
														</td>
														<td width="250px" height="40px" align="left">
															��Ϊ����Ա����:&nbsp;
															<label>
																<select id="type" name="${GNNT_}primary.type[=][Long]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${agentTraderTypeMap}" var="map">
																		<option value="${map.key }">${map.value }</option>
																	</c:forEach>
																</select>
															</label>
															 <script >
																frm.type.value = "<c:out value='${oldParams["primary.type[=][Long]"] }'/>";
						  									</script>
														</td>
													</tr>
													<tr>
														<td class="table6_td_1" align="left">
															��Ϊ����Ա״̬:&nbsp;
															<label>
																<select id="status" name="${GNNT_}primary.status[=][Long]"  class="normal" style="width: 120px">
																	<option value="">ȫ��</option>
																	<c:forEach items="${agentTraderStatusMap}" var="map">
																		<option value="${map.key }">${map.value }</option>
																	</c:forEach>
																</select>
															</label>
															<script>
																frm.status.value = "<c:out value='${oldParams["primary.status[=][Long]"] }'/>";
					  										</script>
														</td>
														<td class="table3_td_anniu" align="left" >
															<button class="btn_sec" id="view" onclick=queryInfo();>��ѯ</button>
															&nbsp;&nbsp;
															<button class="btn_cz" onclick="myReset();">����</button>
														</td>
														<td>&nbsp;</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					
                        <div class="div_gn">
							<rightButton:rightButton name="���" onclick="addAgentTraderForward();" className="anniu_btn" action="/trade/agenttrader/addForwardAgentTrader.action" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="ɾ��" onclick="delateAgentTraderList();" className="anniu_btn" action="/trade/agenttrader/deleteAgentTrader.action" id="delate"></rightButton:rightButton>
							&nbsp;&nbsp;&nbsp;	
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="agentTrader"
											action="${basePath}/trade/agenttrader/list.action?sortColumns=order+by+createTime+desc"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="agentTrader.xls" csvFileName="agentTrader.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
												<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${agentTrader.agentTraderId}" width="5%" viewsAllowed="html" />
												<ec:column width="5%" property="_0" title="���" value="${GLOBALROWCOUNT}" sortable="false" filterable="false" style="text-align:center;" />
												<ec:column property="agentTraderId" title="��Ϊ����Ա����" width="10%" style="text-align:center;">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/trade/agenttrader/detailsForwardAgentTrader.action" id="agentTraderDetails" text="<font color='#880000'>${agentTrader.agentTraderId}</font>" onclick="return agentTraderDetails('${agentTrader.agentTraderId}');"/>
												</ec:column>
												<ec:column property="name" title="��Ϊ����Ա����" width="10%" style="text-align:center;">${agentTrader.name}</ec:column>
												<ec:column property="type" title="��Ϊ����Ա����" width="10%" style="text-align:center;">${agentTraderTypeMap[agentTrader.type]}</ec:column>
												<ec:column property="status" title="��Ϊ����Ա״̬" width="10%" style="text-align:center;">${agentTraderStatusMap[agentTrader.status]}</ec:column>
												<ec:column property="createTime" title="����ʱ��" width="12%" style="text-align:center;"><fmt:formatDate value="${agentTrader.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
												<ec:column property="modifyTime" title="�޸�ʱ��" width="12%" style="text-align:center;"><fmt:formatDate value="${agentTrader.modifyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></ec:column>
												<ec:column property="operateFirm" title="�ɲ���������" width="10%" style="text-align:center;">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/trade/agenttrader/updateForwardAgentTrader.action" id="update${agentTrader.agentTraderId}" text="<font color='#880000'>��  ��</font>" onclick="return updateAgentTraderForward('${agentTrader.agentTraderId}');"/>
												</ec:column>
												<ec:column property="password" title="�޸�����" width="8%" style="text-align:center;">
													<rightHyperlink:rightHyperlink href="#" className="blank_a" action="/trade/agenttrader/updatePasswordForward.action" id="password" text="<font color='#880000'>��  ��</font>" onclick="return updateAgentTraderPassword('${agentTrader.agentTraderId}');"/>
												</ec:column>
											</ec:row>
										</ec:table>
									</td>
								</tr>
							</table>
							<center>${message}</center>
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
		<!-- �༭״̬����ģ�� -->
		<textarea id="ecs_t_status" rows="" cols="" style="display: none">
	        <select onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;"
				name="status">
		        <ec:options items="CUSTOMER_STATUS" />
	        </select>
        </textarea>
	</body>
</html>