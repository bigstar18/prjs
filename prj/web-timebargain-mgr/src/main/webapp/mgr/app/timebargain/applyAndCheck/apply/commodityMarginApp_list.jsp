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
	function add_commodityMargin(){
		document.location.href = "${basePath}/timebargain/apply/commodityMarginAppList.action";
	}
	
	function check_commodityMargin(id){
		pTop("<%=basePath%>/timebargain/commodityMargin/app/commodityMarginChe_form_App.jsp?id=" + id,700,650);
	}
</script>

<title>
�����鿴��Ʒ��֤��
</title>

</head>
<body >
<div id="main_body">
	<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="div_cx">
					<form id="frm" name="frm" action="${basePath}/timebargain/apply/commodityMarginAppList.action?sortColumns=order+by+id+desc" method="post">
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
													<input type="text" id="commodityID" name="commodityID" style="width:111;ime-mode:disabled" maxlength="16" title=""
														class="validate[onlyLetterNumber] input_text" value="${commodityID}"/>
												</td>
												<td align="right">״̬��</td>
			                                    <td>
			                                        <select id="status" name="${GNNT_}primary.status[=][int]" style="width:111">
							                            <option value="">ȫ��</option>
								                        <option value="1" <c:if test="${oldParams['primary.status[=][int]'] == '1'}">selected</c:if>>�����</option>
								                        <option value="2" <c:if test="${oldParams['primary.status[=][int]'] == '2'}">selected</c:if>>���ͨ��</option>
								                        <option value="3" <c:if test="${oldParams['primary.status[=][int]'] == '3'}">selected</c:if>>��˲�ͨ��</option>
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
			    <div class="div_gn">
					<rightButton:rightButton name="�ύ����" onclick="addForward();" className="anniu_btn" action="/timebargain/apply/addCommodityMarginAppforward.action" id="add"></rightButton:rightButton>
				</div>
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="commodityMargin"
									action="${basePath}/timebargain/apply/commodityMarginAppList.action?sortColumns=order+by+id+desc"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
									    <ec:column property="COMMODITYID" title="��Ʒ����" width="20%" style="text-align:center;" value="${commodityMargin.dataMap['commodityID']}" filterable="false" sortable="false"/>
      									<ec:column property="MARGINALGR" title="��֤���㷨" editTemplate="ecs_t_feeAlgr" width="10%" style="text-align:center;" value="${FEEALGR[commodityMargin.dataMap['marginAlgr']]}" filterable="false" sortable="false"/>
      									<ec:column property="MARGINPRICETYPE" title="��֤���������" editTemplate="ecs_t_marginPriceType" width="10%" style="text-align:center;" value="${MARGINPRICETYPE[commodityMargin.dataMap['marginPriceType']]}" filterable="false" sortable="false"/>
										<ec:column property="PAYOUTALGR" title="���ջ����㷨" editTemplate="ecs_t_settleFeeAlgr" width="15%"  style="text-align:center;" value="${FEEALGR[commodityMargin.dataMap['payoutAlgr']]}" filterable="false" sortable="false"/>
										<ec:column property="status" title="��ǰ״̬" editTemplate="ecs_t_status" mappingItem="VIRTUALFUNDSTATUS" width="15%" style="text-align:center;"/>
										<ec:column property="proposer" title="������" width="15%" style="text-align:center;"/>
										<ec:column property="applyTime" title="����ʱ��" width="15%" style="text-align:center;">
											<fmt:formatDate pattern="yyyy-MM-dd" value="${commodityMargin.applyTime}"/>
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
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="marginAlgr" >
			<ec:options items="FEEALGR" />
		</select>
	</textarea>	
	<textarea id="ecs_t_settleFeeAlgr" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="payoutAlgr" >
			<ec:options items="SETTLEFEEALGR" />
		</select>
	</textarea>
	<textarea id="ecs_t_marginPriceType" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="marginPriceType" >
			<ec:options items="MARGINPRICETYPE" />
		</select>
	</textarea>
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="VIRTUALFUNDSTATUS" />
		</select>
	</textarea>		

</body>

</html>
