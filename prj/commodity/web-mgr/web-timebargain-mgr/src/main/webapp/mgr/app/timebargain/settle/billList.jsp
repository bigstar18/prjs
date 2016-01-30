<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
	    <base target="_self" />
		<title>��ѯ�ֵ�</title>
		
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script src="${basePath}/mgr/app/timebargain/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript">
			$(function(){
				<c:if test="${not empty json}">
					var cities =${json};
					$("#firmID").change().autocomplete(cities);
				</c:if>
				$("#frm").validationEngine({
					promptPosition:'bottomRight'
				}); 

				$("#add").click(function(){
					var addUrl = $(this).attr('action')
					var url = "${basePath}"+addUrl+"?firmID=${oldParams['stock.firmID']}&commodityID=${oldParams['stock.commodityID']}";
					var remark = $("#remark").html();
					$("#remarkHdden").val(remark);

					var billIds= [];
					//��֤�ֵ������Ƿ�ΪС��
					$("[name='ids']:checked").each(function(i){
						var billNum = $(this).val().split('_');
						if(billNum[1].indexOf('.') != -1){
							billIds.push(billNum[0]);
						}
					});
					if(billIds.length != 0){
						alert("[" + billIds + "]�ֵ��вֵ���������С����������Ӳֵ���");
						return false;
					}
					//ִ����Ӳ���
					updateRMIEcside(ec.ids,url);

				});
				if("${pageInfo.result[0]['STOCKID']}" != ""){
					$("#remarkTitle").show();
					$("#remark").show();
				}
				if("${firmMsg}" != ""){
					alert("${firmMsg}");
				}
			});
		</script>
	</head>
<body>
		<form id="frm" name="frm" method="post" action="${basePath }/timebargain/bill/getBillList.action">
			<div class="div_cx">
				<table border="0" width="100%" align="center">
					<tr>
						<td>
							<table border="0" width="100%" align="center">
								<tr>
									<td>
										<div class="div_cxtj">
											<div class="div_cxtjL"></div>
											<div class="div_cxtjC">
												��ѯ�ֵ�
											</div>
											<div class="div_cxtjR"></div>
										</div>
										<div style="clear: both;"></div>
												<div>
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right">
														<span class="required">*</span>
														�����̴��룺
													</td>
													<td>
													   <input type="text" id="firmID" 
															class="validate[required,custom[noSpecialChar]] input_text datepicker" name = "${GNNT_}stock.firmID" value="${oldParams['stock.firmID']}"/>
													</td>
													
													<td align="center">
														<span class="required">*</span>
														��Ʒ���ƣ�
													</td>
													<td>
													   <select style="width:100" name = "${GNNT_}stock.commodityID">
															<c:forEach items="${list}" var="props">
																<c:choose>
												  					<c:when test = "${props['COMMODITYID'] == oldParams['stock.commodityID']}">
												  						<option value="${props['COMMODITYID']}" selected="selected">
																			${props['NAME']}
																		</option>
												  					</c:when>
												  					<c:otherwise>
												  						<option value="${props['COMMODITYID']}" >
																			${props['NAME']}
																		</option>
												  					</c:otherwise>
												  				</c:choose>
																
															</c:forEach>
							   							</select> 
													</td>
													<td align="left">
													<input id="query" class="btn_sec" type="submit" value = "��ѯ"/>
													</td>  
												</tr>												

											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			
		</form>
		
		
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr><td>
					<div class="div_list">
						<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td class="table3_td_1" align="left">
									<button class="btn_sec" id="add" action = "/timebargain/bill/addGageBill.action" >���</button>
								</td>
								<td align="right" id = "remarkTitle" style="display: none;">��ע��</td>
								<td align="left" width="20%">
									<textarea id="remark" name="code" rows="2" cols="16" style="width:150; display: none;" /></textarea>
								</td>
							</tr>
							<tr>
								<td colspan="3">
								
									<ec:table items="pageInfo.result" var="bill"
										action="${basePath}/timebargain/bill/getBillList.action"	
										autoIncludeParameters="${empty param.autoInc}"
										xlsFileName="export.xls" csvFileName="export.csv"
										showPrint="true" listWidth="100%"
										minHeight="345"  style="table-layout:fixed;">
										<ec:row>
											<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${bill.STOCKID}_${bill.BILLNUM }" width="5%" viewsAllowed="html" />
											<ec:column property="STOCKID"  alias="remarkHdd" width="15%" title="�ֵ���" style="text-align:center;" />
											<ec:column property="WAREHOUSEID" title="�ֿ���" width="15%" style="text-align:center;"/>
											<ec:column property="BREEDNAME" title="��ƷƷ������" width="20%" style="text-align:center;"/>
											<ec:column property="QUANTITYUNIT" title="��Ʒ����" width="10%" style="text-align:center;"/>
											<ec:column property="BILLNUM" title="�ֵ�����" width="15%" style="text-align:center;"/>
											<ec:column property="LASTTIME" title="�����ʱ��" width="25%" style="text-align:center;"/>
											
										</ec:row>
										 <ec:extend><input type = "hidden" id = "remarkHdden" name = "remarkHdden" value = ""/> </ec:extend>
									</ec:table>
								</td>
							</tr>
						</table>
					</div>	
					
				</td></tr>
			</table>
		</div>
	</body>
		<!-- �༭�͹�����ʹ�õ� ͨ�õ��ı���ģ�� -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>
</html>

