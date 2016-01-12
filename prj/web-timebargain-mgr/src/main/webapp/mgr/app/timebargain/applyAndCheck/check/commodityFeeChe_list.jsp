<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>	

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#frm").validationEngine('attach', {promptPosition : "bottomRight"});
	});
	
	function dolistquery() {
		if(jQuery("#frm").validationEngine('validateform')){
			frm.submit();
		}
	}
	function addForward(){
		//��ȡ����Ȩ�޵� URL
		var addUrl=document.getElementById('add').action;
		//��ȡ������תURL
		var url = "${basePath}"+addUrl;

		document.location.href = url;
		
	}
	function add_commodityFee(){
		document.location.href = "${basePath}/timebargain/apply/commodityFeeAppList.action";
	}
	
	function check_commodityFee(id){
		//��ȡ����Ȩ�޵� URL
		var detailUrl = "${basePath}/timebargain/check/updateCommodityFeeCheforward.action?entity.id=";
		//��ȡ������תURL
		var url = detailUrl + id;
		//�����޸�ҳ��
		if(showDialog(url, "", 600, 550)){
			//����޸ĳɹ�����ˢ���б�
			ECSideUtil.reload("ec");
		}
	}
</script>

<title>
�����鿴��Ʒ������
</title>
</head>
<body >
<div id="main_body">
	<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="div_cx">
					<form id="frm" name="frm" action="${basePath}/timebargain/check/commodityFeeCheList.action" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="margin-top: 5px">
											<tr>
												<td align="right">
														��Ʒ���룺
												</td>
												<td>
													<input type="text" id="commodityID" name="${GNNT_}primary.commodityID[=]" style="width:111;ime-mode:disabled" maxlength="16" title=""
														class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.commodityID[=]']}"/>
												</td>
												<td align="right">״̬��</td>
			                                    <td>
			                                        <select id="status" name="${GNNT_}primary.Status[=]" style="width:111">
							                            <option value="">ȫ��</option>
								                        <option value="1" <c:if test="${oldParams['primary.Status[=]'] == '1'}">selected</c:if>>�����</option>
								                        <option value="2" <c:if test="${oldParams['primary.Status[=]'] == '2'}">selected</c:if>>���ͨ��</option>
								                        <option value="3" <c:if test="${oldParams['primary.Status[=]'] == '3'}">selected</c:if>>��˲�ͨ��</option>
						                            </select>			                            
			                                    </td>																			
											
												<td class="table3_td_1" align="left">
													<button class="btn_sec" id="view" onclick="dolistquery();">��ѯ</button>	
												    &nbsp;&nbsp;
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
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="commodityFee"
									action="${basePath}/timebargain/check/commodityFeeCheList.action"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
									    <ec:column property="COMMODITYID" title="��Ʒ����" width="80"  style="text-align:center;"/>
									    <ec:column property="FEEALGR" title="�����������㷨" editTemplate="ecs_t_feeAlgr" mappingItem="FEEALGR" width="120" style="text-align:right;"/>
									    <ec:column property="SETTLEFEEALGR" title="�����������㷨" editTemplate="ecs_t_settleFeeAlgr" mappingItem="FEEALGR" width="120" style="text-align:center;"/>
										<ec:column property="LOWESTSETTLEFEE" title="��ͽ��������ѽ��" width="140"  style="text-align:center;"/>
										<ec:column property="STATUS" title="��ǰ״̬" editTemplate="ecs_t_status" mappingItem="VIRTUALFUNDSTATUS" width="90" style="text-align:center;"/>
										<ec:column property="PROPOSER" title="������" width="90" style="text-align:center;"/>
										<ec:column property="APPLYTIME" title="����ʱ��" cell="date" width="90" style="text-align:center;"/>
										<ec:column property="_1" title="�����鿴"  width="90" style="text-align:center;">
										<c:choose>
												<c:when test="${commodityFee.STATUS == 1}">
													<%--<a href="#" onclick="check_commodityFee('<c:out value="${commodityFee.ID}"/>')"><img height="20" title="�����鿴" src="<%=skinPath%>/image/app/timebargain/commodity.gif"/></a> --%>
													<rightHyperlink:rightHyperlink text="<img title='�����鿴' src='${skinPath}/image/app/timebargain/commodity.gif'/>" href="#" onclick="check_commodityFee('${commodityFee.ID}')" action="/timebargain/check/updateCommodityFeeCheforward.action"/> 
												</c:when>
												<c:otherwise>
													<%-- <a href="#" onclick="check_commodityFee('<c:out value="${commodityFee.ID}"/>')"><img height="20" title="�����鿴" src="<%=skinPath%>/image/app/timebargain/commodity.gif"/></a>--%>
													<rightHyperlink:rightHyperlink text="<img title='�����鿴' src='${skinPath}/image/app/timebargain/commodity.gif'/>" href="#" onclick="check_commodityFee('${commodityFee.ID}')" action="/timebargain/check/updateCommodityFeeCheforward.action"/>
												</c:otherwise>
										</c:choose>
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
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
	<!-- �༭״̬����ģ�� -->
	<textarea id="ecs_t_feeAlgr" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="feeAlgr" >
			<ec:options items="FEEALGR" />
		</select>
	</textarea>	
	<textarea id="ecs_t_settleFeeAlgr" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="settleFeeAlgr" >
			<ec:options items="SETTLEFEEALGR" />
		</select>
	</textarea>
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="VIRTUALFUNDSTATUS" />
		</select>
	</textarea>		

</body>

</html>
