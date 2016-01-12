<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<base target="_self" />
		<title>��ӽ������</title>
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
			jQuery(document).ready(function() {
				jQuery("#frm").validationEngine('attach');
				//��Ӱ�ťע�����¼�
				$("#add").click(function(){
					//��֤��Ϣ
					if(jQuery("#frm").validationEngine('validate')){
						if(frm.result.value==1){
							var qty = frm.quantity.value;
							var ids = document.getElementsByName("ids");
							var idsString ="";	
							var sum = 0;
							for(var i= 0; i< ids.length; i++){
								if(ids[i].checked){
									idsString = idsString + ids[i].value.split('_')[0] + ",";
									var num = ids[i].value.split('_')[1];
									if(((num*1)%1)!=0){
										alert("��ѡ�ֵ���������ΪС��,������ѡ��");
										return;
									}
									sum = (sum*1) + (num*1);
								}
							}
						}
						if(frm.flagFirmS.value==-1){
							alert("���������̴��벻����");
						}else if(frm.flagFirmB.value==-1){
							alert("�򷽽����̴��벻����");
						}else{
							if(frm.result.value==1){
								if(qty!=sum){
									alert("��ѡ�ֵ����������ڽ�������,������ѡ��");
									return;
									
								}
							}
							var addUrl = document.getElementById('add').action;
							//��ȡ������תURL
							frm.action = "${basePath}"+addUrl+"?ids=" + idsString;
							frm.submit();
						}
					}
				});
				
				
				$("#search").click(function(){
					//��֤��Ϣ
					if(frm.firmID_S.value==""){
						alert("���������̴��벻��Ϊ��");
						return;
					}
					if(frm.flagFirmS.value==-1){
						alert("���������̴��벻����");
						return;
					}
					if(frm.quantity.value==""){
						alert("�����������Ϊ��");
						return;
					}
					frm.submit();
				});
			});
			
			
			function checkFirmId(firmId,flag){
				if(firmId!=""){
					var oldAjaxAsync = $.ajaxSettings.async;
					var url = "${basePath}/ajaxcheck/settleMatch/checkFirmId.action?firmId=" + firmId;
					$.ajaxSettings.async = false;
					$.getJSON(url,null,function call(result){
						if(flag=="S"){
							frm.flagFirmS.value=result;
						}else{
							frm.flagFirmB.value=result;
						}
					});
					$.ajaxSettings.async = oldAjaxAsync;
				}		
			}
			
			
			function showSettleDate(commodityId){
				if(commodityId!=""){
					var oldAjaxAsync = $.ajaxSettings.async;
					var url = "${basePath}/ajaxcheck/settleMatch/getSettleWay.action?commodityId=" + commodityId;
					$.ajaxSettings.async = false;
					$.getJSON(url,null,function call(result){
						if(result!=""){
							document.getElementById("settleDateContext").style.display = "inline";
							document.getElementById("settleDateInput").style.display = "inline";
							var select = document.getElementById("settleDate");
							select.options.length=0;
							$.each(result, function(j, settleDate){
								if(document.getElementById("date1").value==settleDate){
									select.options.add(new Option(settleDate,settleDate,"","selected"));
								}else{
									select.options.add(new Option(settleDate,settleDate));
								}
							});
						}else{
							document.getElementById("settleDateContext").style.display = "none";
							document.getElementById("settleDateInput").style.display = "none";
							document.getElementById("entity.settleDate").value=null;
						}
					});
					$.ajaxSettings.async = oldAjaxAsync;
				}		
			}

			function load(){
				checkFirmId(frm.firmID_B.value,'B');
				checkFirmId(frm.firmID_S.value,'S');
				showSettleDate(frm.commodityId.value);
				if(frm.result.value==1||frm.result.value==""){
					agree();
				}else{
					default1();
				}
				//document.getElementById("billList").style.display="none";
			}

			

			function agree(){
				$("#agree").css("color","red");
				$("#default1").css("color","#5A4208");
				$("#billList").show();
				$("#searchTD").show();
				$("#matches").show();
				$("#defResultTD1").hide();
				$("#defResultTD2").hide();
				$("#breach").hide();
				frm.result.value=1;
			}

			function default1(){
				$("#agree").css("color","#5A4208");
				$("#default1").css("color","red");
				$("#billList").hide();
				$("#searchTD").hide();
				$("#matches").hide();
				$("#defResultTD1").show();
				$("#defResultTD2").show();
				$("#breach").show();
				frm.result.value=frm.dResult.value;
				
			}

			function changeResult(){
				frm.result.value=frm.dResult.value;
			}
				
		</script>
	</head>
	<body onload="load();">
		<div class="div_cx">
			<table border="0" width="100%" align="center">
				<tr>
					<td>
						<div class="warning">
							<div class="content">
								��ܰ��ʾ :��д��Ʒ���롢���������̴���,��������ͽ������ڣ����ڣ���ѯ���ֵ���Ȼ��ѡ�ֵ�����д�򷽽����̴��룬��Լ״̬�ͽ������ڵ���Ϣ���ύ��
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
										<div class="div_cxtjC" onclick="agree()" id="agree" >
											��Լ
										</div>
										<div class="div_cxtjR"></div>
										<div class="div_cxtjL"></div>
										<div class="div_cxtjC" onclick="default1()" id="default1">
											ΥԼ
										</div>
										<div class="div_cxtjR"></div>
									</div>
									<div>
										<form id="frm" method="post" enctype="multipart/form-data" action="${basePath}/timebargain/settleMatch/getBillList.action">
											<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style">
												<tr>
													<td align="right" width="15%">
														<span class="required">*</span>
														��Ʒ���룺
													</td>
													<td align="left" width="15%">
														<select name="entity.commodityId" id="commodityId"  onchange="showSettleDate(this.value)"
															class="input_text_pwdmin">
															<c:forEach items="${list}" var="props">
																<option value="${props['COMMODITYID']}" <c:if test="${entity.commodityId==props['COMMODITYID']}">selected</c:if>>
																	${props['COMMODITYID']}
																</option>
															</c:forEach>
														</select>
													</td>
													<td align="right" width="15%">
														<span class="required">*</span>
														���������̴��룺
													</td>
													<td width="15%">
														<input type="text" id="firmID_S" name="entity.firmID_S" value="${entity.firmID_S }" onblur="checkFirmId(this.value,'S');"
															class="validate[required,maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />] input_text datepicker"/>
															<input type="hidden" value="-1" name="flagFirmS"/>
													</td>
													<td align="right" width="15%" id="matches">
														<span class="required">*</span>
														���������
													</td>
													<td align="right" width="15%" id="breach">
														<span class="required">*</span>
														ΥԼ������
													</td>
													<td width="15%">
														<input type="text" id="quantity" name="entity.quantity" value="${entity.quantity }"
															class="validate[required,maxSize[10],custom[integer]] input_text datepicker" onkeyup="this.value=this.value.replace(/[^\d]/g, '')"/>
													</td>
													<td width="10%" id="searchTD">
														<rightButton:rightButton name="��ѯ" onclick="" className="btn_sec" action="/timebargain/settleMatch/getBillList.action" id="search"></rightButton:rightButton>
													</td>
												</tr>
												<tr>
													<td align="right" width="15%">
														<span class="required">*</span>
														�򷽽����̴��룺
													</td>
													<td  width="15%">
														<input type="text" id="firmID_B" name="entity.firmID_B" value="${entity.firmID_B }" onblur="checkFirmId(this.value,'B');"
															class="validate[required,maxSize[<fmt:message key='MFirm.firmId_q' bundle='${PropsFieldLength}' />] input_text datepicker"/>
														<input type="hidden" value="-1" name="flagFirmB"/>
														<input type="hidden" value="${entity.result }" id="result" name="entity.result"/>
													</td>
													<td align="right" width="15%" id="defResultTD1">
														<span class="required">*</span>
														ΥԼ״̬:
														</td>
													<td  width="15%" id="defResultTD2">
															<label>
																<select id="dResult" name="dResult"  class="normal" style="width: 120px" onchange="changeResult();">
																	<c:forEach items="${settleMatch_resultMapM}" var="map" begin="1">
																		<option value="${map.key}" <c:if test="${entity.result==map.key}">selected</c:if>>${map.value}</option>
																	</c:forEach>
																</select>
															</label>
													</td>
													<td align="right" width="15%" style="display: none;" id="settleDateContext">
														<span class="required">*</span>
														�������ڣ�
													</td>
													<td width="15%" style="display: none;" id="settleDateInput">
														<select name="entity.settleDate" id="settleDate"
															>
														</select>
													</td>
												<iuput type="hidden" id="date1" value="${date1 }"></iuput>
												</tr>
											</table>
										</form>
										<table border="0" cellspacing="0" cellpadding="4" width="100%" align="center" class="table2_style" id="billList">
										<tr>
											<td>
												<ec:table items="pageInfo.result" var="bill"
													action="${basePath}/timebargain/settleMatch/getBillList.action"											
													autoIncludeParameters="${empty param.autoInc}"
													xlsFileName="demo.xls" csvFileName="demo.csv"
													showPrint="true" listWidth="100%"
													minHeight="345"  
													toolbarContent="status" sortable="false"
													rowsDisplayed="${size}">
													<ec:row>
														<ec:column cell="checkbox" headerCell="checkbox" alias="ids" style="text-align:center; " value="${bill.STOCKID}_${bill.STOCKNUM}_" width="5%" viewsAllowed="html" />
														<ec:column property="STOCKID"  alias="remarkHdd" width="15%" title="�ֵ���" style="text-align:center;" />
														<ec:column property="WAREHOUSEID" title="�ֿ���" width="15%" style="text-align:center;"/>
														<ec:column property="BREEDNAME" title="Ʒ������" width="15%" style="text-align:center;"/>
														<ec:column property="QUANTITY" title="��Ʒ����" width="15%" style="text-align:center;">
														 <fmt:formatNumber value="${bill.QUANTITY }" pattern="#,##0.00" />(${bill.UNIT })
														</ec:column>
														<ec:column property="STOCKNUM" title="�ֵ�����" width="15%" style="text-align:center;"/>
														<ec:column property="LASTTIME" title="�����ʱ��" width="20%" style="text-align:center;">
														<fmt:formatDate value="${bill.LASTTIME}" pattern="yyyy-MM-dd HH:mm:ss" />
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
						<rightButton:rightButton name="���" onclick="" className="btn_sec" action="/timebargain/settleMatch/addSettleMatch.action" id="add"></rightButton:rightButton>
						&nbsp;&nbsp;
						<button class="btn_sec" onClick="window.close();">�ر�</button>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>