<%@ page contentType="text/html;charset=GBK"%>
<%@page import="gnnt.MEBS.broker.mgr.model.configparam.BrokerRewardProps"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
<head>

<title>����Ӷ�������б�</title>
<script type="text/javascript">
<!--
//�����Ϣ��ת
function addForward(){
	//��ȡ����Ȩ�޵� URL
	var addUrl=document.getElementById('add').action;
	//��ȡ������תURL
	var url = "${basePath}"+addUrl;

	document.location.href = url;
	
}

//�޸���Ϣ��ת
function detailForward(moduleId,rewardType,brokerId,commodityId){
	//��ȡ����Ȩ�޵� URL
	var detailUrl = "${basePath}/config/special/detail.action?entity.moduleId=";
	//��ȡ������תURL
	var url = detailUrl+moduleId+"&entity.rewardType="+rewardType+"&entity.brokerId="+brokerId+"&entity.commodityId="+commodityId;

	document.location.href = url;
	
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


function dolistquery() {
	frm.submit();
}
// -->
</script>
</head>
<body>
<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="div_cx">
							<form name="frm" action="${basePath}/config/special/specialParamList.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">
															ģ������:&nbsp;															
															<label>
																<select id="moduleId" name="${GNNT_ }primary.moduleId[=][int]"  class="normal" style="width: 120px">
																<%--
																	<option value="">ȫ��</option>
																	<option value="15" <c:if test="${entity.moduleId == 15}">selected</c:if>>${request.shortName }</option>
																--%>		
																<option value="">ȫ��</option>
																 <c:forEach items="${brTradeModule }" var="d">
																		<option value="${d.moduleId }">${d.cnName }</option>
																</c:forEach>								
																</select>
															</label>
															 <script >
																frm.moduleId.value = "${oldParams['primary.moduleId[=][int]']}";
					  										</script>
					  										
															<%-- <label>
																<input name="${GNNT_ }primary.moduleId[=][int]" class="input_text" value="${oldParams['primary.moduleId[=][int]']}"/>
															</label>--%>
															
														</td>
														<td class="table3_td_1" align="left">
															<button class="btn_sec" id="view" onclick=dolistquery();>��ѯ</button>	
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
						<br />
						<div class="div_gn">
							<rightButton:rightButton name="���" onclick="addForward();" className="anniu_btn" action="/config/special/addforward.action?flag=common" id="add"></rightButton:rightButton>
							&nbsp;&nbsp;
							<rightButton:rightButton name="ɾ��" onclick="deleteList();" className="anniu_btn" action="/config/special/delete.action" id="delete"></rightButton:rightButton>
						</div>
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="special"
											action="${basePath}/config/special/specialParamList.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
											showPrint="true" listWidth="100%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
											    <ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${special.brokerId},${special.moduleId},${special.commodityId},${special.rewardType}" width="5%" viewsAllowed="html" />
											    <ec:column property="brokerId" title="�����̱��" width="10%" sortable="false" style="text-align:center;">
											   		 <a href="#" class="blank_a" onclick="return detailForward('${special.moduleId}','${special.rewardType }','${special.brokerId }','${special.commodityId }');"><font color="#880000">${special.brokerId}</font></a>
												</ec:column>
												<ec:column property="-1" width="20%" title="ģ��" style="text-align:center;" sortable="false">
												<c:forEach items="${brTradeModule }" var="ddd">
													<c:if test="${special.moduleId == ddd.moduleId}">
															${ddd.cnName }
													</c:if>
												</c:forEach>
												</ec:column>
												<ec:column property="commodityId" title="��Ʒ" width="10%" sortable="false" style="text-align:center;">
													${special.commodityId}
												</ec:column>
												<ec:column property="rewardType" title="Ӷ������" width="10%" sortable="false" style="text-align:center;">${config_rewardTypeMap[special.rewardType]}</ec:column>
												<ec:column property="rewardRate" title="������Ӷ�����" width="15%" style="text-align:center;">
													${special.rewardRateTemp }%
												</ec:column>
												<ec:column property="firstPayRate" title="����׸�����" width="15%" style="text-align:center;">
													${special.firstPayRateTemp }%
												</ec:column>
												<ec:column property="secondPayRate" title="���β�����" width="15%" style="text-align:center;">
													${special.secondPayRateTemp }%
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
			<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" style="width: 100%;" name="" />
		</textarea>
</body>

</html>
