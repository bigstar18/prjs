<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<base target="_self" />
		<title>�ύ�������</title>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath }/css/validationengine/template.css" type="text/css" />
		<link rel="stylesheet" href="${skinPath}/css/autocomplete/jquery.autocomplete.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${publicPath}/js/firmjson.js"></script>
		<script type="text/javascript">
			if(${result!=''} && ${result!=null}){
				alert('${result }');
			}
			$(function(){
				<c:if test="${not empty json}">
					var cities =${json};
					$("#customerId_S").change().autocomplete(cities);
					$("#customerId_B").change().autocomplete(cities);
				</c:if>
			});
			
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//��Ӱ�ťע�����¼�
				$("#add").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						var qty = frm.quantity.value;
						var ids = document.getElementsByName("ids");
						var idsString ="";	
						var sum = 0;
						for(var i= 0; i< ids.length; i++){
							if(ids[i].checked){
								idsString = idsString + ids[i].value.split('_')[0] + ",";
								var num = ids[i].value.split('_')[1];
								if((num*1)%1 !=0){
									alert("��ѡ�ֵ���������ΪС��,������ѡ��");
									return;
								}
								sum = (sum*1) + (num*1);
							}
						}
						if(qty==sum){
							var addUrl = document.getElementById('add').action;
							//��ȡ������תURL
							frm.action = "${basePath}"+addUrl+"?ids=" + idsString;
							frm.submit();
						}else{
							alert("��ѡ�ֵ����������ڽ�������,������ѡ��");
						}
					}
				});
				
				
				$("#search").click(function(){
					//��֤��Ϣ
					if(frm.commodityId.value==""){
						alert("��Ʒ���벻��Ϊ��");
						return;
					}
					if(frm.customerId_S.value==""){
						alert("�����������벻��Ϊ��");
						return;
					}
					if(frm.quantity.value==""){
						alert("������������Ϊ��");
						return;
					}
					if(isNaN(frm.quantity.value)){
						alert("������������Ϊ����");
						return;
					}
					frm.submit();
				});
			});
			
			function onload(){
				var priceType = frm.priceType.value;
				if(priceType==1){
					document.getElementById("priceDivContext").style.display = "inline";
					document.getElementById("priceDivInput").style.display = "inline";
				}
			
			}			
			
			function showSettlePrice(commodityId){
				if(commodityId!=""){
					var oldAjaxAsync = $.ajaxSettings.async;
					var url = "${basePath}/ajaxcheck/aheadSettle/getSettlePriceType.action?commodityId=" + commodityId;
					$.ajaxSettings.async = false;
					$.getJSON(url,null,function call(result){
						if(result==1){
							document.getElementById("priceDivContext").style.display = "inline";
							document.getElementById("priceDivInput").style.display = "inline";
							frm.priceType.value = "1";
						}else if(result==0){
							frm.price.value = "0";
							document.getElementById("priceDivContext").style.display = "none";
							document.getElementById("priceDivInput").style.display = "none";
							frm.priceType.value = "0";
						}else{
							alert("��Ʒ���벻����");
							frm.price.value = "0";
							document.getElementById("priceDivContext").style.display = "none";
							document.getElementById("priceDivInput").style.display = "none";
							frm.priceType.value = "";
						}
					});
					$.ajaxSettings.async = oldAjaxAsync;
				}		
			}	
		</script>
	</head>
	<body onload="onload();">
		<div class="div_cx">
			<table border="0" width="100%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								��ܰ��ʾ :��д��Ʒ���롢������������ͽ���������ѯ���ֵ���Ȼ��ѡ�ֵ�����д�򷽶������롢�۸�ͱ�ע����Ϣ���ύ��
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<table border="0" width="100%" align="center">
							<tr>
								<td>
									<div class="div_cxtj">
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC">
											��ǰ��������
										</div>
										<div class="div_cxtjR"></div>
									</div>
									<div>
										<form id="frm" method="post" enctype="multipart/form-data" action="${basePath}/timebargain/aheadSettle/getBillList.action">
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right" width="15%">
														<span class="required">*</span>
														��Ʒ���룺
													</td>
													<td  width="15%">
														<select name="entity.commodityId" id="commodityId"  onchange="showSettlePrice(this.value)"
															class="input_text_pwdmin">
															<option value="">��ѡ��</option>
															<c:forEach items="${list}" var="props">
																<option value="${props['COMMODITYID']}" <c:if test="${commodityId==props['COMMODITYID']}">selected</c:if>>
																	${props['COMMODITYID']}
																</option>
															</c:forEach>
														</select>	
														<input type="hidden" id="priceType" name="priceType" value="${priceType }"/>
													</td>
													<td align="right" width="15%">
														<span class="required">*</span>
														�����������룺
													</td>
													<td width="15%">
														<input type="text" id="customerId_S" name="entity.customerId_S" value="${customerId_S }"
															class="validate[required,maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />]] input_text datepicker"/>
													</td>
													<td align="right" width="15%">
														<span class="required">*</span>
														����������
													</td>
													<td width="15%">
														<input type="text" id="quantity" name="entity.quantity" value="${quantity }"
															class="validate[required,custom[integer],maxSize[10]] input_text datepicker"/>
													</td>
													<td width="10%">
														<rightButton:rightButton name="��ѯ" onclick="" className="btn_sec" action="/timebargain/aheadSettle/getBillList.action" id="search"></rightButton:rightButton>
													</td>
												</tr>
												<tr>
													<td align="right" width="15%">
														<span class="required">*</span>
														�򷽶������룺
													</td>
													<td  width="15%">
														<input type="text" id="customerId_B" name="entity.customerId_B" value="${customerId_B }"
															class="validate[required,maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />]] input_text datepicker"/>
													</td>
													<td align="right" width="15%" style="display: none;" id="priceDivContext">
														<span class="required">*</span>
														�۸�
													</td>
													<td width="15%" style="display: none;" id="priceDivInput">
														<input type="text" id="price" name="entity.price" value="${price }"
															class="validate[required,maxSize[10]] input_text datepicker"/>
													</td>
													<td align="right" width="15%">
														<span class="required">*</span>
														��ע��
													</td>
													<td width="15%">
														<input type="text" id="remark1" name="entity.remark1" value="${remark1 }"
															class="validate[required,maxSize[126]] input_text datepicker"/>
													</td>
												</tr>
											</table>
										</form>
										<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
										<tr>
											<td>
												<ec:table items="pageInfo.result" var="bill"
													action="${basePath}/timebargain/aheadSettle/getBillList.action"											
													autoIncludeParameters="${empty param.autoInc}"
													xlsFileName="demo.xls" csvFileName="demo.csv"
													showPrint="true" listWidth="100%"
													minHeight="345"  style="table-layout:fixed;"
													toolbarContent="status" sortable="false"
													rowsDisplayed="${size}">
													<ec:row>
														<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${bill.STOCKID}_${bill.STOCKNUM}" width="5%" viewsAllowed="html" />
														<ec:column property="STOCKID"  alias="remarkHdd" width="15%" title="�ֵ���" style="text-align:center;" />
														<ec:column property="WAREHOUSEID" title="�ֿ���" width="15%" style="text-align:center;"/>
														<ec:column property="BREEDNAME" title="Ʒ������" width="15%" style="text-align:center;"/>
														<ec:column property="QTYUNIT" title="��Ʒ����" width="15%" style="text-align:center;">
														${bill.QUANTITY}&nbsp;${bill.UNIT}
														</ec:column>
														<ec:column property="STOCKNUM" title="�ֵ�����" width="15%" style="text-align:center;"/>
														<ec:column property="LASTTIME" title="�����ʱ��" width="20%" style="text-align:center;">
														  <fmt:formatDate value="${bill.LASTTIME }" pattern="yyyy-MM-dd HH:mm:ss" />
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
					</td>
				</tr>
				<tr>
					<td align="right">
						<rightButton:rightButton name="���" onclick="" className="btn_sec" action="/timebargain/aheadSettle/addAheadSettle.action" id="add"></rightButton:rightButton>
						&nbsp;&nbsp;
						<button class="btn_sec" onClick="window.close();">�ر�</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>