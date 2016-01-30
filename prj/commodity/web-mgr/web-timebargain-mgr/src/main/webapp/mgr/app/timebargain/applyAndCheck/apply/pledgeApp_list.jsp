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
	function add_pledge(){
		document.location.href = "${basePath}/timebargain/apply/pledgeAppList.action";
	}
	
	function check_pledge(id){
		pTop("<%=basePath%>/timebargain/pledge/app/pledgeChe_U_App.jsp?id=" + id,500,500);
	}
</script>

<title>
default
</title>

</head>
<body>
<div id="main_body">
	<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="div_cx">
					<form id="frm" name="frm" action="${basePath}/timebargain/apply/pledgeAppList.action" method="post">
						<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
							<tr>
								<td class="table5_td_width">
									<div class="div2_top">
										<table class="table3_style" border="0" cellspacing="0" cellpadding="0" style="margin-top: 5px">
											<tr>
												<td align="right">
														�����̴��룺
												</td>
												<td>
													<input type="text" id="firmID" name="${GNNT_}primary.FirmID[=]" style="width:111;ime-mode:disabled" maxlength="<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />" title=""class="validate[onlyLetterNumber] input_text" value="${oldParams['primary.FirmID[=]']}"/>
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
			    <div class="div_gn">
					<rightButton:rightButton name="�ύ����" onclick="addForward();" className="anniu_btn" action="/timebargain/apply/addPledgeAppforward.action" id="add"></rightButton:rightButton>
				</div>
				<div class="div_list">
					<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
						<tr>
							<td>
								<ec:table items="pageInfo.result" var="pledge"
									action="${basePath}/timebargain/apply/pledgeAppList.action"											
									autoIncludeParameters="${empty param.autoInc}"
									xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
									showPrint="true" listWidth="100%"
									minHeight="345"  style="table-layout:fixed;">
									<ec:row>
									    <ec:column property="BILLID" title="�ֵ���" width="80"  style="text-align:center;"/>
								        <ec:column property="BILLFUND" title="��Ѻ���" width="120" cell="currency" format="###,###,##0.##" style="text-align:right;"/>
								        <ec:column property="FIRMID" title="�����̴���" ellipsis="true" width="170" style="text-align:center;">
								        </ec:column>
										<ec:column property="COMMODITYNAME" title="Ʒ������" width="90"  style="text-align:center;"/>
										<ec:column property="QUANTITY" title="��Ѻ����" width="120" cell="number" format="###,###,##0.####" style="text-align:right;"/>
										<ec:column property="STATUS" title="��ǰ״̬" editTemplate="ecs_t_status" mappingItem="VIRTUALFUNDSTATUS" width="90" style="text-align:center;"/>
										<ec:column property="TYPE" title="�������" editTemplate="ecs_t_type" mappingItem="OPERATECHECK" width="90" style="text-align:center;"/>
										<ec:column property="PROPOSER" title="������" width="90" style="text-align:center;"/>
										<ec:column property="APPLYTIME" title="����ʱ��" cell="date" width="90" style="text-align:center;"/>
										
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
	<textarea id="ecs_t_type" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="type" >
			<ec:options items="OPERATECHECK" />
		</select>
	</textarea>	
	<textarea id="ecs_t_status" rows="" cols="" style="display:none" >
		<select onblur="ECSideUtil.updateEditCell(this)" style="width:100%;" name="status" >
			<ec:options items="VIRTUALFUNDSTATUS" />
		</select>
	</textarea>	

</body>

</html>
