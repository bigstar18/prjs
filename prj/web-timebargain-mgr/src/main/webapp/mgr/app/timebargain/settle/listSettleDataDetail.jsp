<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
		<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
		<script src="${basePath}/mgr/app/timebargain/js/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
		<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
		
		<style type="text/css">
			a:link {text-decoration:none;} 
			a:active:{text-decoration:none;} 
			a:visited {text-decoration:none;}
			a:hover { text-decoration:none;}
		</style>
		<script type="text/javascript">
			$(function(){
				var settleDate = $("#settleDate");
				if(settleDate.val() == ""){
					settleDate.val('${settleDateDefault}');
				}
				$("#frm").validationEngine({
					promptPosition:'bottomRight'
				}); 
			});
		</script>
		</head>
		<body>
		<div id="main_body">
			<table class="table1_style" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
					    <div class="div_cx">
							<form id = "frm" name="frm" action="${basePath}/timebargain/bill/listSettleDataDetail.action" method="post">
								<table border="0" cellpadding="0" cellspacing="0" class="table2_style">
									<tr>
										<td class="table5_td_width">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td class="table3_td_1" align="center">
															��������:
															<input type="text" class="wdate" id="settleDate"  style="width: 106px" name="${GNNT_}settleDate" value="${oldParams['settleDate'] }"			
																onfocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
														</td>
														<td class="table3_td_1" align="center">
															�����̴���:&nbsp;
															<input id="firmID" name="${GNNT_}gageBill.firmId" type="text" value="${oldParams['gageBill.firmId'] }" class="validate[custom[noSpecialChar]] input_text"/>
														</td>
														<td align="center">��Ʒ���룺&nbsp;
															<input id="commodityID" name="${GNNT_}gageBill.commodityID" type="text" value="${oldParams['gageBill.commodityID'] }" class="validate[custom[noSpecialChar]] input_text"/>
														</td>
												    </tr>
												    
												    <tr>
												    	<td class="table3_td_1" align="center">
															������־:
															<select style="width: 100px" name = "${GNNT_}bsFlag">
																<option value = "0">
																	ȫ��
																</option>
																<c:forEach items = "${bs_flagMap }" var = "flag">
																	<c:choose>
													  					<c:when test = "${flag.key == oldParams['bsFlag']}">
													  						<option value="${flag.key }" selected="selected">${flag.value }</option>
													  					</c:when>
													  					<c:otherwise>
													  						<option value="${flag.key }">${flag.value }</option>
													  					</c:otherwise>
													  				</c:choose>
																	
																</c:forEach>
															</select>
														</td>
														<td class="table3_td_1" align="center">
															��������:
															<select style="width: 100px" name = "${GNNT_}settleType">
																<option value = "0">ȫ������</option>
																<c:forEach items = "${settleTypeMap }" var = "type">
																
																	<c:choose>
													  					<c:when test = "${type.key == oldParams['settleType']}">
													  						<option value="${type.key }" selected="selected">${type.value }</option>
													  					</c:when>
													  					<c:otherwise>
													  						<option value="${type.key }">${type.value }</option>
													  					</c:otherwise>
													  				</c:choose>
																	
																</c:forEach>
															</select>
														</td>
												    	<td class="table3_td_1" align="center">
												    		<input id="query" class="btn_sec" type="submit" value = "��ѯ"/>
															&nbsp;&nbsp;
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
						<br />
						<div class="div_list">
							<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%">
								<tr>
									<td>
										<ec:table items="pageInfo.result" var="settle"
											action="${basePath}/timebargain/bill/listSettleDataDetail.action"											
											autoIncludeParameters="${empty param.autoInc}"
											xlsFileName="�����б�.xls" csvFileName="�����б�.csv"
											showPrint="true" listWidth="150%"
											minHeight="345"  style="table-layout:fixed;">
											<ec:row>
									  			<ec:column property="SETTLEPROCESSDATE" title="��������" cell="date" width="5%" style="text-align:center;"/> 
									  			<ec:column property="FIRMID" title="�����̴���" width="10%" ellipsis="true" style="text-align:center;" /> 
									  			<ec:column property="COMMODITYID" title="��Ʒ����" width="5%" style="text-align:center;"/> 
									  			<ec:column property="BS_FLAG" title="������־" width="5%" style="text-align:center;">
									  				<c:choose>
									  					<c:when test = "${settle.BS_FLAG == 1}">
									  						��
									  					</c:when>
									  					<c:otherwise>
									  						��
									  					</c:otherwise>
									  				</c:choose>
									  			</ec:column>	
									  			
									  			<ec:column property="SETTLEQTY" title="��������" width="5%" calcTitle= "�ϼ�" calc="total" style="text-align:center;"/> 
									  			<ec:column property="A_HOLDNO" title="��������"  width="10%"  style="text-align:center;"/>
									  			<ec:column property="PRICE" title="������" width="5%" cell="currency" style="text-align:right;"/>
									  			<ec:column property="SETTLEPRICE" title="���ռ�" width="5%" cell="currency" style="text-align:right;"/>
									  			<ec:column property="SETTLEMARGIN" title="���ձ�֤��" calc="total" width="5%" cell="currency" style="text-align:right;" format="#,##0.00" />
									  			<ec:column property="PAYOUT" title="���ջ���" calc="total" width="5%" cell="currency" style="text-align:right;" format="#,##0.00"/>
									  			<ec:column property="SETTLEFEE" title="����������" calc="total" width="5%" cell="currency" style="text-align:right;" format="#,##0.00"/>
									  			<ec:column property="SETTLE_PL" title="����ӯ��" width="5%" cell="currency" calc="total" style="text-align:right;" format="#,##0.00"/> 
									  			<ec:column property="SETTLEADDEDTAX" title="��ֵ˰�۲�" cell="currency" calc="total" width="5%" style="text-align:right;" format="#,##0.00"/>
									  			<ec:column property="_1" title="��������"   width="5%" style="text-align:center;">
									  					<c:choose>
									  						
									  						<c:when test="${settle.SETTLETYPE==1}">
									  							���ڽ���
									  						</c:when>
									  						<c:when test="${settle.SETTLETYPE==2}">
									  							��ǰ����
									  						</c:when>
									  						<c:when test="${settle.SETTLETYPE==3}">
									  							���ڽ���
									  						</c:when>
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
		</body>
</html>
